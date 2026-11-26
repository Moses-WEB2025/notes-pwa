package com.miui.notes.tool.tapshareutils;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.Utils;
import com.miui.notes.NoteApp;
import com.miui.notes.backup.NoteManager;
import com.miui.notes.backup.NoteProtos;
import com.miui.notes.backup.SyncRootProtos;
import com.miui.notes.excerpt.ExcerptDataUtils;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.richeditor.schema.NoteSchema;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/* JADX INFO: loaded from: classes4.dex */
public class TapShareRestorer {
    private static final String ATTACHMENT_ZIP_DIRECTORY = "attachment/";
    private static final String TAG = "TapShareRestorer";

    public static WorkingNote restoreSingleNoteFromUri(Context context, Uri uri) {
        List<Long> listRestoreNotesFromUris = restoreNotesFromUris(context, Collections.singletonList(uri));
        if (listRestoreNotesFromUris != null && !listRestoreNotesFromUris.isEmpty()) {
            try {
                return WorkingNote.loadNote("_id=" + listRestoreNotesFromUris.get(0), null);
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, "Failed to load restored note: " + e.getMessage());
            }
        }
        return null;
    }

    public static List<Long> restoreNotesFromUris(Context context, List<Uri> list) {
        if (context == null || list == null || list.isEmpty()) {
            Logger.INSTANCE.d(TAG, "Invalid context or zipUris");
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<Uri> it = list.iterator();
        while (it.hasNext()) {
            Long lRestoreNoteFromUri = restoreNoteFromUri(context, it.next());
            if (lRestoreNoteFromUri != null && lRestoreNoteFromUri.longValue() > 0) {
                arrayList.add(lRestoreNoteFromUri);
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return arrayList;
    }

    private static Long restoreNoteFromUri(Context context, Uri uri) {
        ZipInputStream zipInputStream;
        if (context == null || uri == null) {
            Logger.INSTANCE.d(TAG, "Invalid context or zipUri");
            return null;
        }
        NoteManager noteManager = new NoteManager(context);
        try {
            InputStream inputStreamOpenInputStream = context.getContentResolver().openInputStream(uri);
            if (inputStreamOpenInputStream != null) {
                try {
                    zipInputStream = new ZipInputStream(inputStreamOpenInputStream);
                } catch (Throwable th) {
                    if (inputStreamOpenInputStream != null) {
                        try {
                            inputStreamOpenInputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } else {
                zipInputStream = null;
            }
            try {
                if (inputStreamOpenInputStream == null || zipInputStream == null) {
                    Logger.INSTANCE.d(TAG, "Failed to open input stream for URI: ");
                    if (zipInputStream != null) {
                        zipInputStream.close();
                    }
                    if (inputStreamOpenInputStream == null) {
                        return null;
                    }
                } else {
                    ZipData zipDataExtractZipData = extractZipData(context, zipInputStream);
                    if (zipDataExtractZipData.noteEntity == null) {
                        Logger.INSTANCE.d(TAG, "Failed to parse note entity from ZIP file");
                        if (zipInputStream != null) {
                            zipInputStream.close();
                        }
                        if (inputStreamOpenInputStream != null) {
                        }
                        return null;
                    }
                    Long lValueOf = Long.valueOf(noteManager.insertNoteEntityAndReturnId(zipDataExtractZipData.noteEntity));
                    Logger.INSTANCE.d(TAG, "insertNoteId: " + lValueOf);
                    processAttachmentsForRestore(context, noteManager, zipDataExtractZipData.attachmentPaths);
                    if (zipInputStream != null) {
                        zipInputStream.close();
                    }
                    if (inputStreamOpenInputStream != null) {
                        inputStreamOpenInputStream.close();
                    }
                    return lValueOf;
                }
                inputStreamOpenInputStream.close();
                return null;
            } catch (Throwable th3) {
                if (zipInputStream != null) {
                    try {
                        zipInputStream.close();
                    } catch (Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                }
                throw th3;
            }
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "Error restoring note from URI: " + e.getMessage());
            return null;
        }
    }

    private static ZipData extractZipData(Context context, ZipInputStream zipInputStream) throws IOException {
        ArrayList arrayList = new ArrayList();
        NoteProtos.NoteEntity noteEntityExtractNoteEntity = null;
        while (true) {
            ZipEntry nextEntry = zipInputStream.getNextEntry();
            if (nextEntry != null) {
                String name = nextEntry.getName();
                if (isNoteEntry(name)) {
                    noteEntityExtractNoteEntity = extractNoteEntity(zipInputStream);
                } else if (isAttachmentEntry(name)) {
                    String strExtractAttachmentPath = extractAttachmentPath(context, zipInputStream, name);
                    if (strExtractAttachmentPath != null) {
                        arrayList.add(strExtractAttachmentPath);
                    }
                } else if (name.startsWith(ATTACHMENT_ZIP_DIRECTORY)) {
                    Logger.INSTANCE.w(TAG, "Skipping unsafe attachment ZIP entry");
                }
                zipInputStream.closeEntry();
            } else {
                return new ZipData(noteEntityExtractNoteEntity, arrayList);
            }
        }
    }

    private static boolean isNoteEntry(String str) {
        return str.startsWith(".") && str.endsWith(".note");
    }

    static boolean isAttachmentEntry(String str) {
        if (str == null || !str.startsWith(ATTACHMENT_ZIP_DIRECTORY)) {
            return false;
        }
        return TapShareFileUtils.isSafeAttachmentName(str.substring(ATTACHMENT_ZIP_DIRECTORY.length()));
    }

    private static NoteProtos.NoteEntity extractNoteEntity(ZipInputStream zipInputStream) throws IOException {
        SyncRootProtos.SyncRoot from;
        NoteProtos.Notes note;
        byte[] bytesFromZipEntry = TapShareFileUtils.readBytesFromZipEntry(zipInputStream);
        if (bytesFromZipEntry == null || (from = SyncRootProtos.SyncRoot.parseFrom(bytesFromZipEntry)) == null || !from.hasNote() || (note = from.getNote()) == null || note.getNoteCount() == 0) {
            return null;
        }
        return note.getNote(0);
    }

    private static String extractAttachmentPath(Context context, ZipInputStream zipInputStream, String str) {
        return TapShareFileUtils.saveAttachmentFromZipEntry(context, zipInputStream, str.substring(ATTACHMENT_ZIP_DIRECTORY.length()));
    }

    private static class ZipData {
        final List<String> attachmentPaths;
        final NoteProtos.NoteEntity noteEntity;

        ZipData(NoteProtos.NoteEntity noteEntity, List<String> list) {
            this.noteEntity = noteEntity;
            this.attachmentPaths = list;
        }
    }

    /* JADX WARN: Not initialized variable reg: 5, insn: 0x0096: MOVE (r3 I:??[OBJECT, ARRAY]) = (r5 I:??[OBJECT, ARRAY]), block:B:31:0x0096 */
    public static WorkingNote restoreNoteFromIOS(Context context, Uri uri) throws Throwable {
        File fileCreateTempFile;
        File file;
        File file2 = null;
        workingNoteLoadNote = null;
        WorkingNote workingNoteLoadNote = null;
        if (context == null || uri == null) {
            Logger.INSTANCE.d(TAG, "Invalid context or zipUri");
            return null;
        }
        ArrayList arrayList = new ArrayList();
        try {
            try {
                fileCreateTempFile = File.createTempFile("ios_note_", ".zip", context.getCacheDir());
                try {
                    TapShareFileUtils.copyUriToFile(context, uri, fileCreateTempFile);
                    NoteSchema.SimpleBuilder simpleBuilder = new NoteSchema.SimpleBuilder(NoteApp.getInstance());
                    processZipFileForIOS(fileCreateTempFile, simpleBuilder, context, arrayList);
                    String ancient = simpleBuilder.toAncient();
                    if (TextUtils.isEmpty(ancient)) {
                        Logger.INSTANCE.e(TAG, "No content generated from iOS ZIP file");
                        TapShareFileUtils.cleanupTempFiles(fileCreateTempFile, arrayList);
                        return null;
                    }
                    WorkingNote workingNoteCreateEmptyNote = WorkingNote.createEmptyNote(0L, 0, -1, 0);
                    workingNoteCreateEmptyNote.setWorkingText(ancient);
                    Long lValueOf = Long.valueOf(workingNoteCreateEmptyNote.saveNoteWithId(true));
                    if (lValueOf != null && lValueOf.longValue() > 0) {
                        workingNoteLoadNote = WorkingNote.loadNote("_id=" + lValueOf, null);
                    }
                    TapShareFileUtils.cleanupTempFiles(fileCreateTempFile, arrayList);
                    return workingNoteLoadNote;
                } catch (Exception e) {
                    e = e;
                    Logger.INSTANCE.e(TAG, "Error in restoreNoteFromIOS: " + e.getMessage());
                    TapShareFileUtils.cleanupTempFiles(fileCreateTempFile, arrayList);
                    return null;
                }
            } catch (Exception e2) {
                e = e2;
                fileCreateTempFile = null;
            } catch (Throwable th) {
                th = th;
                TapShareFileUtils.cleanupTempFiles(file2, arrayList);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            file2 = file;
            TapShareFileUtils.cleanupTempFiles(file2, arrayList);
            throw th;
        }
    }

    private static void processZipFileForIOS(File file, NoteSchema.SimpleBuilder simpleBuilder, Context context, List<File> list) throws IOException {
        ZipFile zipFile = new ZipFile(file);
        try {
            Enumeration<? extends ZipEntry> enumerationEntries = zipFile.entries();
            while (enumerationEntries.hasMoreElements()) {
                processZipEntryForIOS(zipFile, enumerationEntries.nextElement(), simpleBuilder, context, list);
            }
            zipFile.close();
        } catch (Throwable th) {
            try {
                zipFile.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private static void processZipEntryForIOS(ZipFile zipFile, ZipEntry zipEntry, NoteSchema.SimpleBuilder simpleBuilder, Context context, List<File> list) throws IOException {
        String name = zipEntry.getName();
        if (isTextEntry(name)) {
            processTextEntryForIOS(zipFile, zipEntry, simpleBuilder);
        } else if (isImageEntry(name)) {
            processImageEntryForIOS(zipFile, zipEntry, simpleBuilder, context, list);
        } else if (isAudioEntry(name)) {
            processAudioEntryForIOS(zipFile, zipEntry, simpleBuilder, context, list);
        }
    }

    private static boolean isTextEntry(String str) {
        return str.endsWith(".txt");
    }

    private static boolean isImageEntry(String str) {
        String lowerCase = str.toLowerCase();
        return lowerCase.endsWith(".png") || lowerCase.endsWith(Utils.JPG_SUFFIX) || lowerCase.endsWith(AttachmentUtils.IMAGE_SUFFIX_JPEG) || lowerCase.endsWith(".heic");
    }

    private static boolean isAudioEntry(String str) {
        return str.endsWith(".m4a");
    }

    /* JADX WARN: Code duplicated, block: B:29:0x003d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    private static void processTextEntryForIOS(ZipFile zipFile, ZipEntry zipEntry, NoteSchema.SimpleBuilder simpleBuilder) throws IOException {
        InputStream inputStream = zipFile.getInputStream(zipEntry);
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                byte[] bArr = new byte[1024];
                while (true) {
                    int i = inputStream.read(bArr);
                    if (i == -1) {
                        break;
                    } else {
                        byteArrayOutputStream.write(bArr, 0, i);
                    }
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Throwable th) {
                            th.addSuppressed(th);
                        }
                    }
                    throw th;
                }
                simpleBuilder.appendText(new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8));
                byteArrayOutputStream.close();
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Throwable th2) {
                try {
                    byteArrayOutputStream.close();
                } catch (Throwable th3) {
                    th2.addSuppressed(th3);
                }
                throw th2;
            }
        } catch (Throwable th4) {
            if (inputStream != null) {
                inputStream.close();
            }
            throw th4;
        }
    }

    /* JADX WARN: Code duplicated, block: B:37:0x007d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    private static void processImageEntryForIOS(ZipFile zipFile, ZipEntry zipEntry, NoteSchema.SimpleBuilder simpleBuilder, Context context, List<File> list) throws IOException {
        try {
            String name = zipEntry.getName();
            String strSubstring = "";
            int iLastIndexOf = name.lastIndexOf(46);
            if (iLastIndexOf > 0) {
                strSubstring = name.substring(iLastIndexOf);
            }
            File file = new File(context.getCacheDir(), "temp_img_" + System.currentTimeMillis() + "_" + name.hashCode() + strSubstring);
            list.add(file);
            InputStream inputStream = zipFile.getInputStream(zipEntry);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int i = inputStream.read(bArr);
                        if (i == -1) {
                            break;
                        } else {
                            fileOutputStream.write(bArr, 0, i);
                        }
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (Throwable th) {
                                th.addSuppressed(th);
                            }
                        }
                        throw th;
                    }
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    ExcerptDataUtils.addImage(file.getAbsolutePath(), simpleBuilder);
                } catch (Throwable th2) {
                    try {
                        fileOutputStream.close();
                    } catch (Throwable th3) {
                        th2.addSuppressed(th3);
                    }
                    throw th2;
                }
            } catch (Throwable th4) {
                if (inputStream != null) {
                    inputStream.close();
                }
                throw th4;
            }
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "Error processing image file: " + zipEntry.getName() + ", error: " + e.getMessage());
        }
    }

    /* JADX WARN: Code duplicated, block: B:40:0x007b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    private static void processAudioEntryForIOS(ZipFile zipFile, ZipEntry zipEntry, NoteSchema.SimpleBuilder simpleBuilder, Context context, List<File> list) throws IOException {
        try {
            File file = new File(context.getCacheDir(), "temp_audio_" + System.currentTimeMillis() + "_" + zipEntry.getName().hashCode() + ".m4a");
            list.add(file);
            InputStream inputStream = zipFile.getInputStream(zipEntry);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int i = inputStream.read(bArr);
                        if (i == -1) {
                            break;
                        } else {
                            fileOutputStream.write(bArr, 0, i);
                        }
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (Throwable th) {
                                th.addSuppressed(th);
                            }
                        }
                        throw th;
                    }
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    String strSaveAudioFile = AttachmentUtils.saveAudioFile(NoteApp.getInstance(), file);
                    if (TextUtils.isEmpty(strSaveAudioFile)) {
                        return;
                    }
                    simpleBuilder.appendAudio(strSaveAudioFile);
                } catch (Throwable th2) {
                    try {
                        fileOutputStream.close();
                    } catch (Throwable th3) {
                        th2.addSuppressed(th3);
                    }
                    throw th2;
                }
            } catch (Throwable th4) {
                if (inputStream != null) {
                    inputStream.close();
                }
                throw th4;
            }
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "Error processing M4A file: " + zipEntry.getName() + ", error: " + e.getMessage());
        }
    }

    private static void processAttachmentsForRestore(Context context, NoteManager noteManager, List<String> list) {
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            processSingleAttachmentForRestore(context, noteManager, it.next());
        }
    }

    private static void processSingleAttachmentForRestore(Context context, NoteManager noteManager, String str) {
        try {
            String name = new File(str).getName();
            Uri uriFindAttachmentUri = findAttachmentUri(noteManager, str, name);
            if (uriFindAttachmentUri == null) {
                Logger.INSTANCE.e(TAG, "Attachment URI not found for path: " + str + ", fileName: " + name);
            } else {
                copyAttachmentData(context, str, uriFindAttachmentUri);
            }
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "Error processing attachment: " + e.getMessage());
        }
    }

    private static Uri findAttachmentUri(NoteManager noteManager, String str, String str2) {
        Uri attachesUriByName = noteManager.getAttachesUriByName(str);
        return attachesUriByName == null ? noteManager.getAttachesUriByName(str2) : attachesUriByName;
    }

    private static void copyAttachmentData(Context context, String str, Uri uri) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(str);
        try {
            OutputStream outputStreamOpenOutputStream = context.getContentResolver().openOutputStream(uri);
            try {
                if (outputStreamOpenOutputStream == null) {
                    Logger.INSTANCE.e(TAG, "Failed to open output stream for URI: " + uri);
                } else {
                    TapShareFileUtils.copyStream(fileInputStream, outputStreamOpenOutputStream);
                    Logger.INSTANCE.d(TAG, "Attachment written successfully: " + str);
                }
                if (outputStreamOpenOutputStream != null) {
                    outputStreamOpenOutputStream.close();
                }
                fileInputStream.close();
            } catch (Throwable th) {
                if (outputStreamOpenOutputStream != null) {
                    try {
                        outputStreamOpenOutputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (Throwable th3) {
            try {
                fileInputStream.close();
            } catch (Throwable th4) {
                th3.addSuppressed(th4);
            }
            throw th3;
        }
    }
}
