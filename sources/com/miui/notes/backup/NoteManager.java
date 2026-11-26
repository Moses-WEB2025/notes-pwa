package com.miui.notes.backup;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.excerpt.NotifyUtils;
import com.miui.notes.model.NoteModel;
import com.miui.notes.provider.Notes;
import com.miui.notes.provider.NotesProvider;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.notes.tool.util.EncryptFailedException;
import com.miui.notes.tool.util.EncryptUtil;
import com.miui.richeditor.schema.NoteSchema;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: NoteManager.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u009e\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010)\n\u0002\u0010'\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010!\n\u0002\b\u0003\u0018\u0000 S2\u00020\u0001:\u0001SB\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u000b2\b\u0010\u0013\u001a\u0004\u0018\u00010\nJ\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017J\u0018\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u0012\u0010\u001a\u001a\u0004\u0018\u00010\u00152\b\u0010\u001b\u001a\u0004\u0018\u00010\nJ \u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#H\u0002J\u001a\u0010$\u001a\u00020\u00192\u0006\u0010%\u001a\u00020&2\b\b\u0002\u0010\u0018\u001a\u00020\u0019H\u0007JD\u0010'\u001a\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u0017\u0018\u00010(j\f\u0012\u0006\u0012\u0004\u0018\u00010\u0017\u0018\u0001`)2\u0006\u0010*\u001a\u00020\u00172\b\u0010+\u001a\u0004\u0018\u00010\n2\b\u0010\u001b\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J:\u0010,\u001a\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u0017\u0018\u00010(j\f\u0012\u0006\u0012\u0004\u0018\u00010\u0017\u0018\u0001`)2\u0006\u0010*\u001a\u00020\u00172\b\u0010-\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u001a\u0010.\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0010\u0010/\u001a\u0004\u0018\u00010&2\u0006\u0010\u0016\u001a\u00020\u0017J\u0018\u0010/\u001a\u0004\u0018\u00010&2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J \u00100\u001a\u00020\u001d2\u0006\u00101\u001a\u0002022\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#H\u0002J \u00103\u001a\u00020\u001d2\u0006\u00101\u001a\u0002022\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0018\u00104\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020!2\u0006\u00105\u001a\u000206H\u0002J\u0010\u00107\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020!H\u0003J\u000e\u00108\u001a\u00020\u001d2\u0006\u00109\u001a\u00020\u0015J\u0010\u0010:\u001a\u00020;2\u0006\u00109\u001a\u00020\u0015H\u0002J\u001a\u0010<\u001a\u00020\u001d2\u0006\u0010%\u001a\u00020&2\b\b\u0002\u0010\u0018\u001a\u00020\u0019H\u0007J\u001a\u0010=\u001a\u00020\u00172\u0006\u0010%\u001a\u00020&2\b\b\u0002\u0010\u0018\u001a\u00020\u0019H\u0007J\u0006\u0010>\u001a\u00020\u001dJ\u0018\u0010?\u001a\u00020;2\u0006\u0010%\u001a\u00020&2\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0010\u0010@\u001a\u00020\u00172\u0006\u0010%\u001a\u00020&H\u0002J\"\u0010A\u001a\u00020\u001d2\u0006\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u0019H\u0002J\"\u0010E\u001a\u00020;2\u0006\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u0019H\u0002J \u0010F\u001a\u00020\u001d2\u0006\u0010G\u001a\u00020;2\u0006\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020\u0017H\u0002J\u0012\u0010H\u001a\u00020\u00192\b\u0010I\u001a\u0004\u0018\u00010\nH\u0002J \u0010J\u001a\u00020\u001d2\u0006\u0010G\u001a\u00020;2\u0006\u0010B\u001a\u00020C2\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0018\u0010K\u001a\u00020\u001d2\u0006\u0010G\u001a\u00020;2\u0006\u0010B\u001a\u00020CH\u0002J.\u0010L\u001a\u00020\u001d2\u0006\u0010M\u001a\u00020\u00172\b\u0010I\u001a\u0004\u0018\u00010\n2\b\u0010N\u001a\u0004\u0018\u00010\n2\b\u0010O\u001a\u0004\u0018\u00010\nH\u0002J\"\u0010P\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0Q2\u0006\u0010R\u001a\u00020#2\b\b\u0002\u0010\u0018\u001a\u00020\u0019H\u0007R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R2\u0010\b\u001a&\u0012\u0006\u0012\u0004\u0018\u00010\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\tj\u0012\u0012\u0006\u0012\u0004\u0018\u00010\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b`\fX\u0082\u000e¢\u0006\u0002\n\u0000R)\u0010\r\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0006\u0012\u0004\u0018\u00010\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0018\u00010\u000f0\u000e8F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011¨\u0006T"}, d2 = {"Lcom/miui/notes/backup/NoteManager;", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;)V", "mResolver", "Landroid/content/ContentResolver;", "mAttach2Uri", "Ljava/util/HashMap;", "", "Landroid/net/Uri;", "Lkotlin/collections/HashMap;", "attachesIterator", "", "", "getAttachesIterator", "()Ljava/util/Iterator;", "getAttachesUriByName", "name", "loadNoteFolder", "Lcom/miui/notes/backup/NoteProtos$NoteFolder;", "id", "", "isPrivacyNote", "", "loadNoteFolderByTitle", "title", "setNoteFolderField", "", "noteFolder", "Lcom/miui/notes/backup/NoteProtos$NoteFolder$Builder;", "cursor", "Landroid/database/Cursor;", "index", "", "shouldRestore", "note", "Lcom/miui/notes/backup/NoteProtos$NoteEntity;", "getNoteIdByNoteContent", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "folderId", "snippet", "getNoteIdByMindContent", "mindContent", "getPhoneNumByNoteId", "loadNoteEntity", "setNoteEntityField", "noteEntity", "Lcom/miui/notes/backup/NoteProtos$NoteEntity$Builder;", "loadNoteData", "setTextField", "noteData", "Lcom/miui/notes/backup/NoteProtos$NoteData$Builder;", "backupAttachmentIfNeed", "addNoteFolder", "folder", "prepareFolderContentValues", "Landroid/content/ContentValues;", "insertNoteEntity", "insertNoteEntityAndReturnId", "deleteAllData", "prepareNoteContentValues", "getNoteFolderId", "insertNoteData", "data", "Lcom/miui/notes/backup/NoteProtos$NoteData;", NotifyUtils.KEY_NOTE_ID, "prepareDataContentValues", "putBasicNoteInfo", "values", "isTextData", "mimeType", "putNoteContent", "putOptionalDataFields", "restoreAttachmentIfNeed", "dataId", "content", "dataType", "prepareIdsByType", "", "typeId", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class NoteManager {
    private static final String TAG = "NoteManager";
    private HashMap<String, Uri> mAttach2Uri;
    private ContentResolver mResolver;

    public final void insertNoteEntity(NoteProtos.NoteEntity note) {
        Intrinsics.checkNotNullParameter(note, "note");
        insertNoteEntity$default(this, note, false, 2, null);
    }

    public final long insertNoteEntityAndReturnId(NoteProtos.NoteEntity note) {
        Intrinsics.checkNotNullParameter(note, "note");
        return insertNoteEntityAndReturnId$default(this, note, false, 2, null);
    }

    public final List<String> prepareIdsByType(int i) {
        return prepareIdsByType$default(this, i, false, 2, null);
    }

    public final boolean shouldRestore(NoteProtos.NoteEntity note) throws IOException {
        Intrinsics.checkNotNullParameter(note, "note");
        return shouldRestore$default(this, note, false, 2, null);
    }

    public NoteManager(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        ContentResolver contentResolver = context.getContentResolver();
        Intrinsics.checkNotNullExpressionValue(contentResolver, "getContentResolver(...)");
        this.mResolver = contentResolver;
        this.mAttach2Uri = new HashMap<>();
    }

    public final Iterator<Map.Entry<String, Uri>> getAttachesIterator() {
        return this.mAttach2Uri.entrySet().iterator();
    }

    public final Uri getAttachesUriByName(String name) {
        return this.mAttach2Uri.get(name);
    }

    public final NoteProtos.NoteFolder loadNoteFolder(long id) throws Throwable {
        NoteProtos.NoteFolder noteFolderLoadNoteFolder = loadNoteFolder(id, false);
        Intrinsics.checkNotNull(noteFolderLoadNoteFolder);
        return noteFolderLoadNoteFolder;
    }

    public final NoteProtos.NoteFolder loadNoteFolder(long id, boolean isPrivacyNote) throws Throwable {
        Exception exc;
        Throwable th;
        Uri uriAppendFlag;
        NoteProtos.NoteFolder.Builder builderNewBuilder = NoteProtos.NoteFolder.newBuilder();
        Cursor cursor = null;
        try {
            try {
                if (isPrivacyNote) {
                    uriAppendFlag = NotesProvider.appendFlag(Notes.Note.CONTENT_URI, NotesProvider.CALLER_IS_PRIVACY);
                } else {
                    uriAppendFlag = Notes.Note.CONTENT_URI;
                }
                Cursor cursorQuery = this.mResolver.query(uriAppendFlag, null, "_id= ? ", new String[]{String.valueOf(id)}, null);
                if (cursorQuery != null) {
                    try {
                        if (cursorQuery.moveToFirst()) {
                            for (int columnCount = cursorQuery.getColumnCount() - 1; -1 < columnCount; columnCount--) {
                                Intrinsics.checkNotNull(builderNewBuilder);
                                setNoteFolderField(builderNewBuilder, cursorQuery, columnCount);
                            }
                            cursorQuery.close();
                        }
                    } catch (Exception e) {
                        exc = e;
                        cursor = cursorQuery;
                        Logger.INSTANCE.e(TAG, "Fail to load note folder,exception:" + exc);
                        if (cursor != null) {
                            cursor.close();
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        cursor = cursorQuery;
                        if (cursor == null) {
                            throw th;
                        }
                        cursor.close();
                        throw th;
                    }
                    return builderNewBuilder.build();
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return null;
            } catch (Exception e2) {
                exc = e2;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public final NoteProtos.NoteFolder loadNoteFolderByTitle(String title) throws Throwable {
        Throwable th;
        Exception exc;
        NoteProtos.NoteFolder.Builder builderNewBuilder = NoteProtos.NoteFolder.newBuilder();
        Cursor cursor = null;
        try {
            try {
                Cursor cursorQuery = this.mResolver.query(Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER, null, "subject= ? and type = 1", new String[]{title}, null);
                if (cursorQuery != null) {
                    try {
                        if (cursorQuery.moveToFirst()) {
                            for (int columnCount = cursorQuery.getColumnCount() - 1; -1 < columnCount; columnCount--) {
                                Intrinsics.checkNotNull(builderNewBuilder);
                                setNoteFolderField(builderNewBuilder, cursorQuery, columnCount);
                            }
                            if (cursorQuery.getLong(cursorQuery.getColumnIndex("parent_id")) == -3) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("parent_id", (Integer) 0);
                                contentValues.put(Notes.Note.LOCAL_MODIFIED, (Integer) 1);
                                if (this.mResolver.update(Notes.Note.CONTENT_URI.buildUpon().appendPath(builderNewBuilder.getLuid()).build(), contentValues, null, null) <= 0) {
                                    cursorQuery.close();
                                    return null;
                                }
                            }
                            cursorQuery.close();
                        }
                    } catch (Exception e) {
                        exc = e;
                        cursor = cursorQuery;
                        Logger.INSTANCE.e(TAG, "Fail to load note folder by title: " + title + ",exception:" + exc);
                        if (cursor != null) {
                            cursor.close();
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        cursor = cursorQuery;
                        if (cursor == null) {
                            throw th;
                        }
                        cursor.close();
                        throw th;
                    }
                    return builderNewBuilder.build();
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return null;
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Exception e2) {
            exc = e2;
        }
    }

    private final void setNoteFolderField(NoteProtos.NoteFolder.Builder noteFolder, Cursor cursor, int index) {
        String columnName = cursor.getColumnName(index);
        if (Intrinsics.areEqual(columnName, "_id")) {
            noteFolder.setLuid(String.valueOf(cursor.getLong(index)));
            return;
        }
        if (Intrinsics.areEqual(columnName, Notes.Note.ALERTED_DATE)) {
            noteFolder.setAlertDate(cursor.getLong(index));
            return;
        }
        if (Intrinsics.areEqual(columnName, Notes.Note.BG_COLOR_ID)) {
            noteFolder.setBgColorId(cursor.getInt(index));
            return;
        }
        if (Intrinsics.areEqual(columnName, "created_date")) {
            noteFolder.setCreatedDate(cursor.getLong(index));
            return;
        }
        if (Intrinsics.areEqual(columnName, "modified_date")) {
            noteFolder.setModifiedDate(cursor.getLong(index));
            return;
        }
        if (Intrinsics.areEqual(columnName, Notes.Note.HAS_ATTACHMENT)) {
            noteFolder.setHasAttachment(cursor.getInt(index) != 0);
        } else {
            if (!Intrinsics.areEqual(columnName, "subject") || cursor.getString(index) == null) {
                return;
            }
            noteFolder.setSubject(cursor.getString(index));
        }
    }

    public static /* synthetic */ boolean shouldRestore$default(NoteManager noteManager, NoteProtos.NoteEntity noteEntity, boolean z, int i, Object obj) throws IOException {
        if ((i & 2) != 0) {
            z = false;
        }
        return noteManager.shouldRestore(noteEntity, z);
    }

    public final boolean shouldRestore(NoteProtos.NoteEntity note, boolean isPrivacyNote) throws IOException, EncryptFailedException {
        NoteManager noteManager;
        boolean z;
        ArrayList<Long> noteIdByNoteContent;
        Intrinsics.checkNotNullParameter(note, "note");
        String data3 = null;
        for (NoteProtos.NoteData noteData : note.getDataList()) {
            boolean zEquals = TextUtils.equals(noteData.getMimeType(), Notes.CallData.CONTENT_ITEM_TYPE);
            boolean zEquals2 = TextUtils.equals(noteData.getDataContentType(), "multi_handwrite");
            if (!zEquals && !zEquals2) {
                String content = noteData.getContent();
                String title = note.getTitle();
                if (TextUtils.isEmpty(content) && TextUtils.isEmpty(title)) {
                    return false;
                }
            } else if (zEquals) {
                data3 = noteData.getData3();
            }
        }
        long noteFolderId = isPrivacyNote ? -4L : getNoteFolderId(note);
        Logger.INSTANCE.d(TAG, "noteContentType: " + note.getNoteContentType());
        if (!TextUtils.isEmpty(note.getNoteContentType()) && (Intrinsics.areEqual(note.getNoteContentType(), "handwrite") || Intrinsics.areEqual(note.getNoteContentType(), "multi_handwrite"))) {
            Logger.INSTANCE.d(TAG, "handwrite always shouldRestore，return");
            return true;
        }
        if (!TextUtils.isEmpty(note.getNoteContentType()) && Intrinsics.areEqual(note.getNoteContentType(), NoteModel.NoteType.TYPE_MIND)) {
            noteIdByNoteContent = getNoteIdByMindContent(noteFolderId, note.getMindContent(), isPrivacyNote);
            noteManager = this;
            z = isPrivacyNote;
        } else {
            noteManager = this;
            z = isPrivacyNote;
            noteIdByNoteContent = noteManager.getNoteIdByNoteContent(noteFolderId, note.getSnippet(), note.getTitle(), z);
        }
        if (noteIdByNoteContent == null) {
            return true;
        }
        String str = data3;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Iterator<Long> it = noteIdByNoteContent.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            Long next = it.next();
            Intrinsics.checkNotNull(next);
            if (TextUtils.equals(str, noteManager.getPhoneNumByNoteId(next.longValue(), z))) {
                return false;
            }
        }
        return true;
    }

    private final ArrayList<Long> getNoteIdByNoteContent(long folderId, String snippet, String title, boolean isPrivacyNote) throws EncryptFailedException {
        Uri uriAppendFlag;
        ArrayList<Long> arrayList;
        Exception exc;
        if (isPrivacyNote) {
            uriAppendFlag = NotesProvider.appendFlag(Notes.Note.CONTENT_URI, NotesProvider.CALLER_IS_PRIVACY);
        } else {
            uriAppendFlag = Notes.Note.CONTENT_URI;
        }
        Uri uri = uriAppendFlag;
        if (isPrivacyNote) {
            snippet = EncryptUtil.encrypt(BaseNoteApp.INSTANCE.getInstance(), snippet);
        }
        Cursor cursorQuery = this.mResolver.query(uri, new String[]{"_id"}, "snippet=? AND title=? AND type=? AND parent_id=?", new String[]{snippet, title, "0", String.valueOf(folderId)}, null);
        if (cursorQuery != null) {
            try {
                try {
                    if (cursorQuery.moveToFirst()) {
                        arrayList = new ArrayList<>();
                        do {
                            try {
                                arrayList.add(Long.valueOf(cursorQuery.getLong(0)));
                            } catch (Exception e) {
                                exc = e;
                            }
                        } while (cursorQuery.moveToNext());
                        cursorQuery.close();
                        return arrayList;
                    }
                } catch (Throwable th) {
                    if (cursorQuery == null) {
                        throw th;
                    }
                    cursorQuery.close();
                    throw th;
                }
            } catch (Exception e2) {
                arrayList = null;
                exc = e2;
            }
            Logger.INSTANCE.e(TAG, "Fail to get note id by snippet, folderId=" + folderId + ",exception:" + exc);
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return arrayList;
        }
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return null;
    }

    private final ArrayList<Long> getNoteIdByMindContent(long folderId, String mindContent, boolean isPrivacyNote) {
        Uri uriAppendFlag;
        ArrayList<Long> arrayList;
        Exception exc;
        if (isPrivacyNote) {
            uriAppendFlag = NotesProvider.appendFlag(Notes.Note.CONTENT_URI, NotesProvider.CALLER_IS_PRIVACY);
        } else {
            uriAppendFlag = Notes.Note.CONTENT_URI;
        }
        Cursor cursorQuery = this.mResolver.query(uriAppendFlag, new String[]{"_id"}, "mind_content=? AND type=? AND parent_id=?", new String[]{mindContent, "0", String.valueOf(folderId)}, null);
        try {
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.moveToFirst()) {
                        arrayList = new ArrayList<>();
                        do {
                            try {
                                arrayList.add(Long.valueOf(cursorQuery.getLong(0)));
                            } catch (Exception e) {
                                exc = e;
                            }
                        } while (cursorQuery.moveToNext());
                        cursorQuery.close();
                        return arrayList;
                    }
                } catch (Exception e2) {
                    arrayList = null;
                    exc = e2;
                }
                Logger.INSTANCE.e(TAG, "Fail to get note id by snippet, folderId=" + folderId + ",exception:" + exc);
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return arrayList;
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return null;
        } catch (Throwable th) {
            if (cursorQuery == null) {
                throw th;
            }
            cursorQuery.close();
            throw th;
        }
    }

    private final String getPhoneNumByNoteId(long id, boolean isPrivacyNote) {
        Uri uriAppendFlag;
        if (isPrivacyNote) {
            uriAppendFlag = NotesProvider.appendFlag(Notes.CallData.CONTENT_URI, NotesProvider.CALLER_IS_PRIVACY);
        } else {
            uriAppendFlag = Notes.CallData.CONTENT_URI;
        }
        Cursor cursorQuery = this.mResolver.query(uriAppendFlag, new String[]{"data3"}, "note_id=? AND mime_type=?", new String[]{String.valueOf(id), Notes.CallData.CONTENT_ITEM_TYPE}, null);
        try {
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.moveToFirst()) {
                        String string = cursorQuery.getString(0);
                        cursorQuery.close();
                        return string;
                    }
                } catch (Exception e) {
                    Logger.INSTANCE.e(TAG, "Fail to get phone number by note id,exception:" + e);
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return null;
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return null;
        } catch (Throwable th) {
            if (cursorQuery == null) {
                throw th;
            }
            cursorQuery.close();
            throw th;
        }
    }

    public final NoteProtos.NoteEntity loadNoteEntity(long id) throws IOException {
        return loadNoteEntity(id, false);
    }

    public final NoteProtos.NoteEntity loadNoteEntity(long id, boolean isPrivacyNote) throws Throwable {
        Throwable th;
        Uri uriAppendFlag;
        String subject;
        NoteProtos.NoteEntity.Builder builderNewBuilder = NoteProtos.NoteEntity.newBuilder();
        Cursor cursor = null;
        try {
            try {
                if (isPrivacyNote) {
                    uriAppendFlag = NotesProvider.appendFlag(Notes.Note.CONTENT_URI, NotesProvider.CALLER_IS_PRIVACY);
                } else {
                    uriAppendFlag = Notes.Note.CONTENT_URI;
                }
                com.miui.common.tool.Logger.INSTANCE.d(TAG, "query note " + id);
                Cursor cursorQuery = this.mResolver.query(uriAppendFlag, null, "_id= ? ", new String[]{String.valueOf(id)}, null);
                if (cursorQuery != null) {
                    try {
                        if (cursorQuery.moveToFirst()) {
                            for (int columnCount = cursorQuery.getColumnCount() - 1; -1 < columnCount; columnCount--) {
                                Intrinsics.checkNotNull(builderNewBuilder);
                                setNoteEntityField(builderNewBuilder, cursorQuery, columnCount);
                            }
                            long j = cursorQuery.getLong(cursorQuery.getColumnIndexOrThrow("parent_id"));
                            long j2 = cursorQuery.getLong(cursorQuery.getColumnIndexOrThrow("origin_parent_id"));
                            if (j == -2) {
                                builderNewBuilder.setFolderType(-2);
                            } else {
                                if (j <= 0) {
                                    j = 0;
                                }
                                if (j2 <= 0) {
                                    j2 = j;
                                }
                                if (j2 > 0 && (subject = loadNoteFolder(j2).getSubject()) != null && subject.length() > 0) {
                                    builderNewBuilder.setFolderTitle(subject);
                                }
                            }
                            cursorQuery.close();
                            Intrinsics.checkNotNull(builderNewBuilder);
                            loadNoteData(builderNewBuilder, id, isPrivacyNote);
                            return builderNewBuilder.build();
                        }
                    } catch (Exception e) {
                        e = e;
                        cursor = cursorQuery;
                        Logger.INSTANCE.e(TAG, "Fail to load note entity, id=" + id + ",exception:" + e);
                        if (cursor != null) {
                            cursor.close();
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        cursor = cursorQuery;
                        if (cursor == null) {
                            throw th;
                        }
                        cursor.close();
                        throw th;
                    }
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return null;
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    private final void setNoteEntityField(NoteProtos.NoteEntity.Builder noteEntity, Cursor cursor, int index) {
        String columnName = cursor.getColumnName(index);
        if (Intrinsics.areEqual(columnName, "_id")) {
            noteEntity.setLuid(String.valueOf(cursor.getLong(index)));
            return;
        }
        if (Intrinsics.areEqual(columnName, Notes.Note.ALERTED_DATE)) {
            noteEntity.setAlertDate(cursor.getLong(index));
            return;
        }
        if (Intrinsics.areEqual(columnName, Notes.Note.BG_COLOR_ID)) {
            noteEntity.setBgColorId(cursor.getInt(index));
            return;
        }
        if (Intrinsics.areEqual(columnName, "theme_id")) {
            noteEntity.setThemeId(cursor.getInt(index));
            return;
        }
        if (Intrinsics.areEqual(columnName, "created_date")) {
            noteEntity.setCreatedDate(cursor.getLong(index));
            return;
        }
        if (Intrinsics.areEqual(columnName, "modified_date")) {
            noteEntity.setModifiedDate(cursor.getLong(index));
            return;
        }
        if (Intrinsics.areEqual(columnName, Notes.Note.HAS_ATTACHMENT)) {
            noteEntity.setHasAttachment(cursor.getInt(index) != 0);
            return;
        }
        if (Intrinsics.areEqual(columnName, "snippet") && cursor.getString(index) != null) {
            noteEntity.setSnippet(cursor.getString(index));
            return;
        }
        if (Intrinsics.areEqual(columnName, Notes.Note.STICK_DATE)) {
            noteEntity.setStickDate(cursor.getLong(index));
            return;
        }
        if (Intrinsics.areEqual(columnName, "title")) {
            noteEntity.setTitle(cursor.getString(index));
            return;
        }
        if (Intrinsics.areEqual(columnName, Notes.Note.NOTE_CONTENT_TYPE)) {
            noteEntity.setNoteContentType(cursor.getString(index));
            return;
        }
        if (Intrinsics.areEqual(columnName, Notes.Note.MIND_CONTENT)) {
            noteEntity.setMindContent(cursor.getString(index));
            return;
        }
        if (Intrinsics.areEqual(columnName, Notes.Note.MIND_CONTENT_PLAIN_TEXT)) {
            noteEntity.setMindContentPlainText(cursor.getString(index));
            return;
        }
        if (Intrinsics.areEqual(columnName, "ent_name")) {
            noteEntity.setEntName(cursor.getString(index));
            return;
        }
        if (Intrinsics.areEqual(columnName, Notes.Note.IMAGES)) {
            noteEntity.setImages(cursor.getString(index));
            return;
        }
        if (Intrinsics.areEqual(columnName, "thumbnail")) {
            noteEntity.setThumbnail(cursor.getString(index));
        } else if (Intrinsics.areEqual(columnName, "recognized_file_names")) {
            noteEntity.setRecognizedFileNames(cursor.getString(index));
        } else if (Intrinsics.areEqual(columnName, Notes.Note.IS_EMPTY)) {
            noteEntity.setIsEmpty(cursor.getInt(index));
        }
    }

    /* JADX WARN: Bottom block not found for handler: all -> 0x0049 */
    /* JADX WARN: Code restructure failed: missing block: B:30:?, code lost:
    
        throw r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void loadNoteData(com.miui.notes.backup.NoteProtos.NoteEntity.Builder r10, long r11, boolean r13) throws java.io.IOException {
        /*
            r9 = this;
            java.lang.String r1 = ",exception:"
            r2 = 0
            if (r13 == 0) goto Le
            android.net.Uri r13 = com.miui.notes.provider.Notes.CallData.CONTENT_URI     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4c
            java.lang.String r0 = "caller_is_privacy"
            android.net.Uri r13 = com.miui.notes.provider.NotesProvider.appendFlag(r13, r0)     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4c
            goto L10
        Le:
            android.net.Uri r13 = com.miui.notes.provider.Notes.CallData.CONTENT_URI     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4c
        L10:
            r4 = r13
            android.content.ContentResolver r3 = r9.mResolver     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4c
            java.lang.String r6 = "note_id= ? "
            r13 = 1
            java.lang.String[] r7 = new java.lang.String[r13]     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4c
            java.lang.String r13 = java.lang.String.valueOf(r11)     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4c
            r0 = 0
            r7[r0] = r13     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4c
            r8 = 0
            r5 = 0
            android.database.Cursor r2 = r3.query(r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4c
            if (r2 == 0) goto L43
            boolean r13 = r2.moveToFirst()     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4c
            if (r13 == 0) goto L43
        L2d:
            com.miui.notes.backup.NoteProtos$NoteData$Builder r13 = com.miui.notes.backup.NoteProtos.NoteData.newBuilder()     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4c
            kotlin.jvm.internal.Intrinsics.checkNotNull(r13)     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4c
            r9.setTextField(r2, r13)     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4c
            r9.backupAttachmentIfNeed(r2)     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4c
            r10.addData(r13)     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4c
            boolean r13 = r2.moveToNext()     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4c
            if (r13 != 0) goto L2d
        L43:
            if (r2 == 0) goto L48
            r2.close()
        L48:
            return
        L49:
            r0 = move-exception
            r9 = r0
            goto L91
        L4c:
            r0 = move-exception
            r9 = r0
            com.miui.notes.base.utils.Logger r10 = com.miui.notes.base.utils.Logger.INSTANCE     // Catch: java.lang.Throwable -> L49
            java.lang.String r13 = "NoteManager"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L49
            r0.<init>()     // Catch: java.lang.Throwable -> L49
            java.lang.String r3 = "Fail to load note data, id="
            java.lang.StringBuilder r0 = r0.append(r3)     // Catch: java.lang.Throwable -> L49
            java.lang.StringBuilder r0 = r0.append(r11)     // Catch: java.lang.Throwable -> L49
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch: java.lang.Throwable -> L49
            java.lang.StringBuilder r0 = r0.append(r9)     // Catch: java.lang.Throwable -> L49
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> L49
            r10.e(r13, r0)     // Catch: java.lang.Throwable -> L49
            java.io.IOException r10 = new java.io.IOException     // Catch: java.lang.Throwable -> L49
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L49
            r13.<init>()     // Catch: java.lang.Throwable -> L49
            java.lang.String r0 = "Cannot load note data, id="
            java.lang.StringBuilder r13 = r13.append(r0)     // Catch: java.lang.Throwable -> L49
            java.lang.StringBuilder r11 = r13.append(r11)     // Catch: java.lang.Throwable -> L49
            java.lang.StringBuilder r11 = r11.append(r1)     // Catch: java.lang.Throwable -> L49
            java.lang.StringBuilder r9 = r11.append(r9)     // Catch: java.lang.Throwable -> L49
            java.lang.String r9 = r9.toString()     // Catch: java.lang.Throwable -> L49
            r10.<init>(r9)     // Catch: java.lang.Throwable -> L49
            throw r10     // Catch: java.lang.Throwable -> L49
        L91:
            if (r2 == 0) goto L96
            r2.close()
        L96:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.miui.notes.backup.NoteManager.loadNoteData(com.miui.notes.backup.NoteProtos$NoteEntity$Builder, long, boolean):void");
    }

    private final void setTextField(Cursor cursor, NoteProtos.NoteData.Builder noteData) {
        int columnCount = cursor.getColumnCount();
        while (true) {
            columnCount--;
            if (-1 >= columnCount) {
                return;
            }
            String columnName = cursor.getColumnName(columnCount);
            if (Intrinsics.areEqual(columnName, Notes.Data.MIME_TYPE) && cursor.getString(columnCount) != null) {
                noteData.setMimeType(cursor.getString(columnCount));
            } else if (Intrinsics.areEqual(columnName, "created_date")) {
                noteData.setCreatedDate(cursor.getLong(columnCount));
            } else if (Intrinsics.areEqual(columnName, "modified_date")) {
                noteData.setModifiedDate(cursor.getLong(columnCount));
            } else if (Intrinsics.areEqual(columnName, "content") && cursor.getString(columnCount) != null) {
                noteData.setContent(cursor.getString(columnCount));
            } else if (Intrinsics.areEqual(columnName, "data1")) {
                noteData.setData1(cursor.getLong(columnCount));
            } else if (Intrinsics.areEqual(columnName, Notes.Data.DATA2)) {
                noteData.setData2(cursor.getLong(columnCount));
            } else if (Intrinsics.areEqual(columnName, "data3") && cursor.getString(columnCount) != null) {
                noteData.setData3(cursor.getString(columnCount));
            } else if (Intrinsics.areEqual(columnName, Notes.Data.DATA4) && cursor.getString(columnCount) != null) {
                noteData.setData4(cursor.getString(columnCount));
            } else if (Intrinsics.areEqual(columnName, Notes.Data.DATA5) && cursor.getString(columnCount) != null) {
                noteData.setData5(cursor.getString(columnCount));
            } else if (Intrinsics.areEqual(columnName, Notes.Data.DATA_CONTENT_TYPE) && cursor.getString(columnCount) != null) {
                noteData.setDataContentType(cursor.getString(columnCount));
            }
        }
    }

    private final void backupAttachmentIfNeed(Cursor cursor) {
        String string = cursor.getString(cursor.getColumnIndex(Notes.Data.MIME_TYPE));
        if (Intrinsics.areEqual(Notes.TextData.CONTENT_ITEM_TYPE, string) || Intrinsics.areEqual(Notes.CallData.CONTENT_ITEM_TYPE, string)) {
            return;
        }
        int i = cursor.getInt(cursor.getColumnIndex("_id"));
        String string2 = cursor.getString(cursor.getColumnIndex("content"));
        String string3 = cursor.getString(cursor.getColumnIndex(Notes.Data.DATA_CONTENT_TYPE));
        Uri uriWithAppendedId = ContentUris.withAppendedId(Notes.CallData.CONTENT_URI, i);
        Intrinsics.checkNotNullExpressionValue(uriWithAppendedId, "withAppendedId(...)");
        if (Intrinsics.areEqual("multi_handwrite", string3)) {
            String attachmentPath = AttachmentUtils.getAttachmentPath(BaseNoteApp.INSTANCE.getInstance(), string2);
            Logger.INSTANCE.d(TAG, "backupAttachmentIfNeed:attachmentPath=" + attachmentPath + " noteUri = " + uriWithAppendedId);
            this.mAttach2Uri.put(attachmentPath, uriWithAppendedId);
        } else {
            Logger.INSTANCE.d(TAG, "backupAttachmentIfNeed:content=" + string2 + " noteUri = " + uriWithAppendedId);
            this.mAttach2Uri.put(string2, uriWithAppendedId);
        }
    }

    public final void addNoteFolder(NoteProtos.NoteFolder folder) throws IOException {
        Intrinsics.checkNotNullParameter(folder, "folder");
        if (!TextUtils.isEmpty(folder.getSubject())) {
            synchronized (Reflection.getOrCreateKotlinClass(NoteManager.class)) {
                this.mResolver.insert(Notes.Note.CONTENT_URI, prepareFolderContentValues(folder));
            }
            return;
        }
        throw new IOException("Cannot create note folder which has empty title");
    }

    private final ContentValues prepareFolderContentValues(NoteProtos.NoteFolder folder) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", (Integer) 1);
        contentValues.put(Notes.Note.ALERTED_DATE, Long.valueOf(folder.getAlertDate()));
        contentValues.put(Notes.Note.BG_COLOR_ID, Integer.valueOf(folder.getBgColorId()));
        contentValues.put("created_date", Long.valueOf(folder.getCreatedDate()));
        contentValues.put("modified_date", Long.valueOf(folder.getModifiedDate()));
        contentValues.put(Notes.Note.HAS_ATTACHMENT, Integer.valueOf(folder.getHasAttachment() ? 1 : 0));
        String subject = folder.getSubject();
        if (subject != null) {
            contentValues.put("subject", subject);
        }
        return contentValues;
    }

    public static /* synthetic */ void insertNoteEntity$default(NoteManager noteManager, NoteProtos.NoteEntity noteEntity, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        noteManager.insertNoteEntity(noteEntity, z);
    }

    public final void insertNoteEntity(NoteProtos.NoteEntity note, boolean isPrivacyNote) {
        Intrinsics.checkNotNullParameter(note, "note");
        synchronized (Reflection.getOrCreateKotlinClass(NoteManager.class)) {
            Uri uriInsert = this.mResolver.insert(Notes.Note.CONTENT_URI, prepareNoteContentValues(note, isPrivacyNote));
            Intrinsics.checkNotNull(uriInsert);
            long id = ContentUris.parseId(uriInsert);
            for (NoteProtos.NoteData noteData : note.getDataList()) {
                Intrinsics.checkNotNull(noteData);
                insertNoteData(noteData, id, isPrivacyNote);
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public static /* synthetic */ long insertNoteEntityAndReturnId$default(NoteManager noteManager, NoteProtos.NoteEntity noteEntity, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return noteManager.insertNoteEntityAndReturnId(noteEntity, z);
    }

    public final long insertNoteEntityAndReturnId(NoteProtos.NoteEntity note, boolean isPrivacyNote) {
        long id;
        Intrinsics.checkNotNullParameter(note, "note");
        synchronized (Reflection.getOrCreateKotlinClass(NoteManager.class)) {
            Uri uriInsert = this.mResolver.insert(Notes.Note.CONTENT_URI, prepareNoteContentValues(note, isPrivacyNote));
            Intrinsics.checkNotNull(uriInsert);
            id = ContentUris.parseId(uriInsert);
            for (NoteProtos.NoteData noteData : note.getDataList()) {
                Intrinsics.checkNotNull(noteData);
                insertNoteData(noteData, id, isPrivacyNote);
            }
        }
        return id;
    }

    public final void deleteAllData() {
        synchronized (Reflection.getOrCreateKotlinClass(TodoManager.class)) {
            this.mResolver.delete(Notes.appendSyncAdapterFlag(Notes.Note.CONTENT_URI), null, null);
            this.mResolver.delete(Notes.Data.CONTENT_URI, null, null);
        }
    }

    private final ContentValues prepareNoteContentValues(NoteProtos.NoteEntity note, boolean isPrivacyNote) {
        String strEncrypt;
        String str = "";
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", (Integer) 0);
        contentValues.put(Notes.Note.ALERTED_DATE, Long.valueOf(note.getAlertDate()));
        contentValues.put(Notes.Note.BG_COLOR_ID, Integer.valueOf(note.getBgColorId()));
        contentValues.put("theme_id", Integer.valueOf(note.getThemeId()));
        contentValues.put("created_date", Long.valueOf(note.getCreatedDate()));
        contentValues.put("modified_date", Long.valueOf(note.getModifiedDate()));
        contentValues.put(Notes.Note.HAS_ATTACHMENT, Integer.valueOf(note.getHasAttachment() ? 1 : 0));
        if (note.getSnippet() != null) {
            String strExtractPlainText = NoteSchema.extractPlainText(note.getSnippet());
            if (isPrivacyNote) {
                try {
                    strEncrypt = EncryptUtil.encrypt(BaseNoteApp.INSTANCE.getInstance(), note.getSnippet());
                    Intrinsics.checkNotNullExpressionValue(strEncrypt, "encrypt(...)");
                    try {
                        String strEncrypt2 = EncryptUtil.encrypt(BaseNoteApp.INSTANCE.getInstance(), strExtractPlainText);
                        Intrinsics.checkNotNullExpressionValue(strEncrypt2, "encrypt(...)");
                        str = strEncrypt2;
                    } catch (Throwable th) {
                        th = th;
                        Logger.INSTANCE.e(TAG, "encrypt error: " + th);
                    }
                } catch (Throwable th2) {
                    th = th2;
                    strEncrypt = "";
                }
                contentValues.put("plain_text", str);
                contentValues.put("snippet", strEncrypt);
            } else {
                contentValues.put("snippet", note.getSnippet());
                contentValues.put("plain_text", strExtractPlainText);
            }
        }
        if (isPrivacyNote) {
            contentValues.put("parent_id", (Integer) (-4));
        } else {
            contentValues.put("parent_id", Long.valueOf(getNoteFolderId(note)));
        }
        contentValues.put(Notes.Note.STICK_DATE, Long.valueOf(note.getStickDate()));
        contentValues.put("title", note.getTitle());
        contentValues.put(Notes.Note.NOTE_CONTENT_TYPE, note.getNoteContentType());
        contentValues.put(Notes.Note.MIND_CONTENT, note.getMindContent());
        contentValues.put(Notes.Note.MIND_CONTENT_PLAIN_TEXT, note.getMindContentPlainText());
        contentValues.put("ent_name", note.getEntName());
        contentValues.put(Notes.Note.IMAGES, note.getImages());
        contentValues.put("thumbnail", note.getThumbnail());
        contentValues.put("recognized_file_names", note.getRecognizedFileNames());
        contentValues.put(Notes.Note.IS_EMPTY, Integer.valueOf(note.getIsEmpty()));
        return contentValues;
    }

    private final long getNoteFolderId(NoteProtos.NoteEntity note) {
        NoteProtos.NoteFolder noteFolderLoadNoteFolderByTitle;
        if (note.hasFolderType() && note.getFolderType() == -2) {
            return -2L;
        }
        if (!note.hasFolderTitle()) {
            return 0L;
        }
        String folderTitle = note.getFolderTitle();
        if (TextUtils.isEmpty(folderTitle) || (noteFolderLoadNoteFolderByTitle = loadNoteFolderByTitle(folderTitle)) == null) {
            return 0L;
        }
        String luid = noteFolderLoadNoteFolderByTitle.getLuid();
        Intrinsics.checkNotNullExpressionValue(luid, "getLuid(...)");
        return Long.parseLong(luid);
    }

    static /* synthetic */ void insertNoteData$default(NoteManager noteManager, NoteProtos.NoteData noteData, long j, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        noteManager.insertNoteData(noteData, j, z);
    }

    private final void insertNoteData(NoteProtos.NoteData data, long noteId, boolean isPrivacyNote) {
        Uri uriInsert = this.mResolver.insert(Notes.CallData.CONTENT_URI, prepareDataContentValues(data, noteId, isPrivacyNote));
        Intrinsics.checkNotNull(uriInsert);
        restoreAttachmentIfNeed(ContentUris.parseId(uriInsert), data.getMimeType(), data.getContent(), data.getDataContentType());
    }

    static /* synthetic */ ContentValues prepareDataContentValues$default(NoteManager noteManager, NoteProtos.NoteData noteData, long j, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return noteManager.prepareDataContentValues(noteData, j, z);
    }

    private final ContentValues prepareDataContentValues(NoteProtos.NoteData data, long noteId, boolean isPrivacyNote) {
        ContentValues contentValues = new ContentValues();
        putBasicNoteInfo(contentValues, data, noteId);
        putNoteContent(contentValues, data, isPrivacyNote);
        putOptionalDataFields(contentValues, data);
        return contentValues;
    }

    private final void putBasicNoteInfo(ContentValues values, NoteProtos.NoteData data, long noteId) {
        values.put("note_id", Long.valueOf(noteId));
        if (data.getMimeType() != null) {
            values.put(Notes.Data.MIME_TYPE, data.getMimeType());
        }
        values.put("created_date", Long.valueOf(data.getCreatedDate()));
        values.put("modified_date", Long.valueOf(data.getModifiedDate()));
    }

    private final boolean isTextData(String mimeType) {
        return Intrinsics.areEqual(mimeType, Notes.TextData.CONTENT_ITEM_TYPE);
    }

    private final void putNoteContent(ContentValues values, NoteProtos.NoteData data, boolean isPrivacyNote) {
        String strEncrypt;
        if (data.getContent() != null) {
            if (!isPrivacyNote || !isTextData(data.getMimeType())) {
                values.put("content", data.getContent());
                return;
            }
            try {
                strEncrypt = EncryptUtil.encrypt(BaseNoteApp.INSTANCE.getInstance(), data.getContent());
                Intrinsics.checkNotNullExpressionValue(strEncrypt, "encrypt(...)");
            } catch (Throwable th) {
                Logger.INSTANCE.e(TAG, "encrypt error: " + th);
                strEncrypt = "";
            }
            values.put("content", strEncrypt);
        }
    }

    private final void putOptionalDataFields(ContentValues values, NoteProtos.NoteData data) {
        if (data.hasData1()) {
            values.put("data1", Long.valueOf(data.getData1()));
        }
        if (data.hasData2()) {
            values.put(Notes.Data.DATA2, Long.valueOf(data.getData2()));
        }
        if (data.hasData3()) {
            values.put("data3", data.getData3());
        }
        if (data.hasData4()) {
            values.put(Notes.Data.DATA4, data.getData4());
        }
        if (data.hasData5()) {
            values.put(Notes.Data.DATA5, data.getData5());
        }
        if (data.hasDataContentType()) {
            values.put(Notes.Data.DATA_CONTENT_TYPE, data.getDataContentType());
        }
    }

    private final void restoreAttachmentIfNeed(long dataId, String mimeType, String content, String dataType) {
        if (Intrinsics.areEqual(Notes.TextData.CONTENT_ITEM_TYPE, mimeType) || Intrinsics.areEqual(Notes.CallData.CONTENT_ITEM_TYPE, mimeType)) {
            return;
        }
        Uri uriWithAppendedId = ContentUris.withAppendedId(Notes.CallData.CONTENT_URI, dataId);
        Intrinsics.checkNotNullExpressionValue(uriWithAppendedId, "withAppendedId(...)");
        if (Intrinsics.areEqual("multi_handwrite", dataType)) {
            String attachmentPath = AttachmentUtils.getAttachmentPath(BaseNoteApp.INSTANCE.getInstance(), content);
            Logger.INSTANCE.d(TAG, "restoreAttachmentIfNeed:attachmentPath=" + attachmentPath + " contentUri = " + uriWithAppendedId);
            Intrinsics.checkNotNull(attachmentPath);
            String separator = File.separator;
            Intrinsics.checkNotNullExpressionValue(separator, "separator");
            String strSubstring = attachmentPath.substring(0, StringsKt.lastIndexOf$default((CharSequence) attachmentPath, separator, 0, false, 6, (Object) null));
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            File file = new File(strSubstring);
            if (!file.exists()) {
                file.mkdirs();
                file.setExecutable(true, false);
            }
            this.mAttach2Uri.put(attachmentPath, uriWithAppendedId);
            String name = new File(attachmentPath).getName();
            this.mAttach2Uri.put(name, uriWithAppendedId);
            Logger.INSTANCE.d(TAG, "restoreAttachmentIfNeed: added keys - attachmentPath=" + attachmentPath + ", fileName=" + name);
            return;
        }
        Logger.INSTANCE.d(TAG, "restoreAttachmentIfNeed:content=" + content + " contentUri = " + uriWithAppendedId);
        this.mAttach2Uri.put(content, uriWithAppendedId);
        if (content != null) {
            String name2 = new File(content).getName();
            this.mAttach2Uri.put(name2, uriWithAppendedId);
            Logger.INSTANCE.d(TAG, "restoreAttachmentIfNeed: added keys - content=" + content + ", fileName=" + name2);
        }
    }

    public static /* synthetic */ List prepareIdsByType$default(NoteManager noteManager, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        return noteManager.prepareIdsByType(i, z);
    }

    public final List<String> prepareIdsByType(int typeId, boolean isPrivacyNote) {
        Cursor cursorQuery;
        ArrayList arrayList = new ArrayList();
        Cursor cursor = null;
        try {
            if (isPrivacyNote) {
                cursorQuery = this.mResolver.query(NotesProvider.appendFlag(Notes.Note.CONTENT_URI, NotesProvider.CALLER_IS_PRIVACY), new String[]{"_id"}, "type = " + typeId + " AND parent_id = -4", null, null);
            } else {
                cursorQuery = this.mResolver.query(Notes.Note.CONTENT_URI, new String[]{"_id"}, "type = " + typeId + " AND parent_id != -4", null, null);
            }
            cursor = cursorQuery;
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndexOrThrow = cursor.getColumnIndexOrThrow("_id");
                while (!cursor.isAfterLast()) {
                    arrayList.add(String.valueOf(cursor.getLong(columnIndexOrThrow)));
                    cursor.moveToNext();
                }
                cursor.close();
                return arrayList;
            }
            if (cursor != null) {
                cursor.close();
                return arrayList;
            }
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "Fail to prepare ids by type, typeId=" + typeId + ",exception:" + e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return arrayList;
    }
}
