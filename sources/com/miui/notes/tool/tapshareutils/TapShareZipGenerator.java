package com.miui.notes.tool.tapshareutils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.Utils;
import com.miui.notes.backup.NoteProtos;
import com.miui.notes.backup.SyncRootProtos;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.provider.Notes;
import com.miui.notes.provider.NotesProvider;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.notes.tool.util.CompatFileProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class TapShareZipGenerator {
    private static final String ATTACHMENT_ZIP_DIRECTORY = "attachment/";
    private static final String IMAGE_ZIP_DIRECTORY = "images/";
    private static final String TAG = "TapShareZipGenerator";

    public static Uri generateZipUri(Context context, WorkingNote workingNote, File file) throws IOException {
        if (context == null || workingNote == null || file == null) {
            return null;
        }
        File file2 = new File(file, TapShareFileUtils.generateZipFileName(workingNote.getTitle(), workingNote.getNoteId()));
        try {
            ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(file2));
            try {
                addTextFileToZip(zipOutputStream, workingNote);
                addNoteEntityToZip(context, zipOutputStream, workingNote.getNoteId(), file, workingNote.getFolderId());
                addAttachmentsToZip(context, zipOutputStream, workingNote.getNoteId());
                Uri uriForFile = CompatFileProvider.getUriForFile(context, "com.miui.notes", file2);
                zipOutputStream.close();
                return uriForFile;
            } catch (Throwable th) {
                try {
                    zipOutputStream.close();
                    throw th;
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                    throw th;
                }
            }
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "Error creating ZIP file: " + e.getMessage());
            if (!file2.delete()) {
                Logger.INSTANCE.w(TAG, "Failed to delete zip file: " + file2.getAbsolutePath());
            }
            throw new IOException("Failed to create ZIP file", e);
        }
    }

    private static void addTextFileToZip(ZipOutputStream zipOutputStream, WorkingNote workingNote) throws IOException {
        String title = workingNote.getTitle();
        String content = workingNote.getContent();
        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(content)) {
            return;
        }
        ZipEntry zipEntry = new ZipEntry("id" + workingNote.getNoteId() + "_note.txt");
        zipEntry.setTime(System.currentTimeMillis());
        zipOutputStream.putNextEntry(zipEntry);
        String strExtractPlainText = TapShareDataModel.extractPlainText(content);
        if (!TextUtils.isEmpty(title)) {
            zipOutputStream.write(title.getBytes(StandardCharsets.UTF_8));
            zipOutputStream.write(StringUtils.LF.getBytes(StandardCharsets.UTF_8));
        }
        if (!TextUtils.isEmpty(strExtractPlainText)) {
            zipOutputStream.write(strExtractPlainText.getBytes(StandardCharsets.UTF_8));
        }
        zipOutputStream.closeEntry();
    }

    private static void addNoteEntityToZip(Context context, ZipOutputStream zipOutputStream, long j, File file, long j2) throws Throwable {
        NoteProtos.NoteEntity noteEntityLoadWorkingNote = TapShareNoteLoader.loadWorkingNote(context, Long.valueOf(j));
        if (noteEntityLoadWorkingNote == null) {
            Logger.INSTANCE.d(TAG, "addNoteEntityToZip: note is null return");
            return;
        }
        if (j2 == -4) {
            Logger.INSTANCE.d(TAG, "Converting private note to unclassified note for sharing: noteId=" + j);
            NoteProtos.NoteEntity.Builder builder = noteEntityLoadWorkingNote.toBuilder();
            builder.clearFolderType();
            builder.clearFolderTitle();
            noteEntityLoadWorkingNote = builder.build();
        }
        NoteProtos.Notes.Builder builderNewBuilder = NoteProtos.Notes.newBuilder();
        builderNewBuilder.addNote(noteEntityLoadWorkingNote);
        SyncRootProtos.SyncRoot syncRootBuild = SyncRootProtos.SyncRoot.newBuilder().setNote(builderNewBuilder.build()).build();
        String str = "." + j + ".note";
        File file2 = new File(file, str);
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        try {
            syncRootBuild.writeTo(fileOutputStream);
            fileOutputStream.close();
            TapShareFileUtils.addFileToZip(zipOutputStream, file2, str);
            if (file2.delete()) {
                return;
            }
            Logger.INSTANCE.w(TAG, "Failed to delete temp note file: " + file2.getAbsolutePath());
        } catch (Throwable th) {
            try {
                fileOutputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private static void addAttachmentsToZip(Context context, ZipOutputStream zipOutputStream, long j) throws IOException {
        Cursor cursorQueryAttachmentsCursor = queryAttachmentsCursor(context, j);
        if (cursorQueryAttachmentsCursor == null) {
            return;
        }
        try {
            processAttachmentsCursor(context, zipOutputStream, cursorQueryAttachmentsCursor);
        } finally {
            cursorQueryAttachmentsCursor.close();
        }
    }

    private static Cursor queryAttachmentsCursor(Context context, long j) {
        ContentResolver contentResolver = context.getContentResolver();
        String[] strArr = {String.valueOf(j), Notes.TextData.CONTENT_ITEM_TYPE};
        String[] strArr2 = {"content", Notes.Data.MIME_TYPE};
        Cursor cursorQuery = contentResolver.query(Notes.Data.CONTENT_URI, strArr2, "note_id=? AND mime_type!=?", strArr, null);
        if (cursorQuery == null || cursorQuery.getCount() == 0) {
            Logger.INSTANCE.d(TAG, "Normal query failed for attachments, trying privacy query for note: " + j);
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            cursorQuery = contentResolver.query(NotesProvider.appendFlag(Notes.Data.CONTENT_URI, NotesProvider.CALLER_IS_PRIVACY), strArr2, "note_id=? AND mime_type!=?", strArr, null);
        }
        Logger.INSTANCE.d(TAG, "queryAttachmentsCursor: cursor=" + cursorQuery + ", count=" + (cursorQuery != null ? cursorQuery.getCount() : 0));
        return cursorQuery;
    }

    private static void processAttachmentsCursor(Context context, ZipOutputStream zipOutputStream, Cursor cursor) throws IOException {
        if (cursor == null || cursor.getCount() == 0) {
            return;
        }
        int columnIndex = cursor.getColumnIndex("content");
        if (columnIndex == -1) {
            Logger.INSTANCE.e(TAG, "Invalid content column index");
            return;
        }
        HashSet hashSet = new HashSet();
        while (cursor.moveToNext()) {
            String string = cursor.getString(columnIndex);
            if (!TextUtils.isEmpty(string)) {
                if (hashSet.contains(string)) {
                    Logger.INSTANCE.d(TAG, "Skipping duplicate attachment: " + string);
                } else {
                    hashSet.add(string);
                    processAttachment(context, zipOutputStream, cursor, columnIndex);
                }
            }
        }
    }

    private static void processAttachment(Context context, ZipOutputStream zipOutputStream, Cursor cursor, int i) {
        File attachmentDir;
        String string = cursor.getString(i);
        if (TextUtils.isEmpty(string)) {
            return;
        }
        String attachmentPath = AttachmentUtils.getAttachmentPath(context, string);
        if (TextUtils.isEmpty(attachmentPath)) {
            return;
        }
        File file = new File(attachmentPath);
        try {
            if (AudioUtils.isMp3Suffix(string)) {
                attachmentDir = AudioUtils.obtainMp3FileDir(AudioUtils.NOTE_MP3_DIR_NAME);
            } else {
                attachmentDir = AttachmentUtils.getAttachmentDir(context);
            }
            if (!TapShareFileUtils.isFileInsideDirectory(attachmentDir, file)) {
                Logger.INSTANCE.e(TAG, "Attachment path escapes expected directory");
                return;
            }
            if (!file.isFile()) {
                Logger.INSTANCE.w(TAG, "Attachment file does not exist");
                return;
            }
            String name = file.getName();
            if (!TapShareFileUtils.isSafeAttachmentName(name)) {
                Logger.INSTANCE.e(TAG, "Unsafe attachment name");
            } else {
                addAttachmentEntriesToZip(zipOutputStream, file, name, AttachmentUtils.tryGetImageMimeType(file));
            }
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "Failed to add attachment to ZIP: " + string + ", error: " + e.getMessage());
        }
    }

    static void addAttachmentEntriesToZip(ZipOutputStream zipOutputStream, File file, String str, String str2) throws IOException {
        if (!TapShareFileUtils.isSafeAttachmentName(str)) {
            throw new IOException("Unsafe attachment name");
        }
        TapShareFileUtils.addFileToZip(zipOutputStream, file, ATTACHMENT_ZIP_DIRECTORY + str);
        String imageFileExtension = getImageFileExtension(str2);
        if (imageFileExtension != null) {
            TapShareFileUtils.addFileToZip(zipOutputStream, file, IMAGE_ZIP_DIRECTORY + str + imageFileExtension);
        }
    }

    static String getImageFileExtension(String str) {
        if (str == null) {
            return null;
        }
        String lowerCase = str.trim().toLowerCase(Locale.ROOT);
        lowerCase.hashCode();
        switch (lowerCase) {
            case "image/avif":
                return ".avif";
            case "image/heic":
                return ".heic";
            case "image/heif":
                return ".heif";
            case "image/jpeg":
            case "image/jpg":
                return Utils.JPG_SUFFIX;
            case "image/webp":
                return ".webp";
            case "image/bmp":
            case "image/x-bmp":
            case "image/x-ms-bmp":
                return ".bmp";
            case "image/gif":
                return ".gif";
            case "image/png":
                return ".png";
            default:
                return null;
        }
    }
}
