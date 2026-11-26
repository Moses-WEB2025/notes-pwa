package com.miui.notes.widget;

import android.app.ActivityManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.text.TextUtils;
import ch.qos.logback.core.CoreConstants;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.model.NoteEntity;
import com.miui.notes.model.NoteModel;
import com.miui.notes.provider.DataCursorWrapper;
import com.miui.notes.provider.NoteCursorWrapper;
import com.miui.notes.provider.Notes;
import com.miui.notes.provider.NotesDatabaseHelper;
import com.miui.notes.tool.util.EncryptFailedException;
import com.miui.notes.tool.util.EncryptUtil;
import com.miui.richeditor.schema.NoteSchema;
import com.miui.todo.data.provider.TodoDatabaseHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
public class WidgetProcessDatabaseUtils {
    private static final int RESULT_NO_PARENT_ID = -100;
    private static final int RESULT_OK = 0;
    private static final String TAG = "WidgetProcessDatabaseUtils";

    public static void insertPersonalAssistantNote(Context context, ContentValues contentValues) {
        NoteEntity noteEntity = new NoteEntity();
        String asString = contentValues.getAsString("snippet");
        if (asString == null) {
            return;
        }
        noteEntity.setContent(asString);
        String asString2 = contentValues.getAsString("title");
        if (asString2 != null) {
            noteEntity.setTitle(asString2);
        }
        noteEntity.setParentId(0L);
        noteEntity.applyWidgetId(contentValues.getAsInteger("widget_id").intValue());
        noteEntity.applyWidgetType(contentValues.getAsInteger("widget_type").intValue());
        noteEntity.insertNoteByDB(context);
    }

    public static void insertNote(Context context, ContentValues contentValues) {
        SQLiteDatabase writableDatabase = NotesDatabaseHelper.getInstance(context).getWritableDatabase();
        updateSystemFolderTimeIfNeed(context, contentValues);
        if (insertNoteAtomic(context, writableDatabase, contentValues) <= 0 || !contentValues.containsKey("parent_id")) {
            return;
        }
        updateFolder(writableDatabase, contentValues.getAsInteger("parent_id").intValue());
    }

    public static Cursor queryNote(Context context, String[] strArr, String str, String[] strArr2, String str2) {
        return new NoteCursorWrapper(context, NotesDatabaseHelper.getInstance(context).getReadableDatabase().query("note", strArr, str, strArr2, null, null, str2));
    }

    public static Cursor queryData(Context context, String[] strArr, String str, String[] strArr2, String str2) {
        return new DataCursorWrapper(context, NotesDatabaseHelper.getInstance(context).getReadableDatabase().query(join("data", "note"), appendColumn(addPrefix("data", strArr), "parent_id", "origin_parent_id"), str, strArr2, null, null, str2));
    }

    public static Cursor queryNotesNumbers(Context context) {
        return NotesDatabaseHelper.getInstance(context).getReadableDatabase().rawQuery("SELECT * FROM note WHERE type=0 AND deletion_tag<>1 AND parent_id <> -3 AND parent_id <> -4", null);
    }

    public static Cursor queryTodoNumbers(Context context) {
        return TodoDatabaseHelper.getInstance(context).getReadableDatabase().rawQuery("SELECT * FROM todo WHERE is_finish = 0 AND local_status != 3", null);
    }

    public static int update(Context context, ContentValues contentValues, String str, String[] strArr) {
        SQLiteDatabase writableDatabase = NotesDatabaseHelper.getInstance(context).getWritableDatabase();
        verifyNoteValues(contentValues);
        updateSystemFolderTimeIfNeed(context, contentValues);
        insertPlainTextValue(contentValues);
        HashSet<Long> updatedFolderIds = getUpdatedFolderIds(writableDatabase, contentValues, str, strArr);
        int iUpdate = writableDatabase.update("note", contentValues, str, strArr);
        printCommonUpdate("URI_NOTE", contentValues, str, strArr);
        if (iUpdate > 0) {
            updateFolder(writableDatabase, updatedFolderIds);
        }
        return iUpdate;
    }

    public static int updateItem(Context context, long j, ContentValues contentValues, String str, String[] strArr) {
        SQLiteDatabase writableDatabase = NotesDatabaseHelper.getInstance(context).getWritableDatabase();
        verifyNoteValues(contentValues);
        updateSystemFolderTimeIfNeed(context, contentValues);
        insertPlainTextValue(contentValues);
        String str2 = "_id=" + j + parseSelection(str);
        HashSet<Long> updatedFolderIds = getUpdatedFolderIds(writableDatabase, contentValues, str2, strArr);
        int iUpdate = writableDatabase.update("note", contentValues, str2, strArr);
        printCommonUpdate("URI_NOTE_ITEM", contentValues, str2, strArr);
        if (iUpdate > 0) {
            updateFolder(writableDatabase, updatedFolderIds);
        }
        return iUpdate;
    }

    private static void updateFolder(SQLiteDatabase sQLiteDatabase, HashSet<Long> hashSet) {
        updateFolder(sQLiteDatabase, String.format("%s IN (%s)", "_id", TextUtils.join(",", hashSet)));
    }

    private static void printCommonUpdate(String str, ContentValues contentValues, String str2, String[] strArr) {
        if (contentValues.containsKey(Notes.Note.LOCAL_MODIFIED)) {
            Logger.INSTANCE.i(TAG, "update " + str + ", uid=" + Binder.getCallingUid() + ", pid=" + Binder.getCallingPid() + ", localModified=" + contentValues.get(Notes.Note.LOCAL_MODIFIED).toString() + ", selection=" + str2 + ", selectionArgs=" + Arrays.toString(strArr));
        }
        if (contentValues.containsKey("snippet") && TextUtils.isEmpty(contentValues.get("snippet").toString())) {
            Logger.INSTANCE.i(TAG, "update " + str + ", uid=" + Binder.getCallingUid() + ", pid=" + Binder.getCallingPid() + ", snippet is update to ''!, selection=" + str2 + ", selectionArgs=" + Arrays.toString(strArr));
        }
    }

    private static HashSet<Long> getUpdatedFolderIds(SQLiteDatabase sQLiteDatabase, ContentValues contentValues, String str, String[] strArr) {
        HashSet<Long> hashSet = new HashSet<>();
        if (contentValues != null && contentValues.containsKey("parent_id")) {
            hashSet.add(contentValues.getAsLong("parent_id"));
        }
        Cursor cursorQuery = sQLiteDatabase.query(true, "note", new String[]{"parent_id"}, str, strArr, null, null, null, null);
        if (cursorQuery == null) {
            return hashSet;
        }
        while (cursorQuery.moveToNext()) {
            try {
                long j = cursorQuery.getLong(0);
                if (j > 0) {
                    hashSet.add(Long.valueOf(j));
                }
            } finally {
                cursorQuery.close();
            }
        }
        return hashSet;
    }

    private static String parseSelection(String str) {
        return !TextUtils.isEmpty(str) ? " AND (" + str + CoreConstants.RIGHT_PARENTHESIS_CHAR : "";
    }

    private static void verifyNoteValues(ContentValues contentValues) {
        if (contentValues.containsKey("subject") && contentValues.getAsString("subject") == null) {
            throw new IllegalArgumentException("Folder's subject can't be null");
        }
    }

    private static String join(String str, String str2) {
        return str + " inner join " + str2 + " on " + str2 + "._id=note_id";
    }

    private static String[] addPrefix(String str, String[] strArr) {
        if (strArr == null) {
            return null;
        }
        for (int i = 0; i < strArr.length; i++) {
            String str2 = strArr[i];
            if ("_id".equals(str2)) {
                String[] strArr2 = new String[strArr.length];
                System.arraycopy(strArr, 0, strArr2, 0, strArr.length);
                strArr2[i] = str + "." + str2;
                return strArr2;
            }
        }
        return strArr;
    }

    private static String[] appendColumn(String[] strArr, String... strArr2) {
        if (strArr == null) {
            return null;
        }
        String[] strArr3 = new String[strArr.length + strArr2.length];
        System.arraycopy(strArr, 0, strArr3, 0, strArr.length);
        System.arraycopy(strArr2, 0, strArr3, strArr.length, strArr2.length);
        return strArr3;
    }

    private static void updateSystemFolderTimeIfNeed(Context context, ContentValues contentValues) {
        SQLiteDatabase writableDatabase = NotesDatabaseHelper.getInstance(context).getWritableDatabase();
        Long asLong = contentValues.getAsLong("parent_id");
        if (asLong == null || -2 != asLong.longValue()) {
            return;
        }
        ContentValues contentValues2 = new ContentValues();
        long jCurrentTimeMillis = System.currentTimeMillis();
        contentValues2.put("created_date", Long.valueOf(jCurrentTimeMillis));
        contentValues2.put("modified_date", Long.valueOf(jCurrentTimeMillis));
        writableDatabase.update("note", contentValues2, String.format(Locale.US, "%s=%d AND %s=%d", "_id", asLong, Notes.Note.NOTES_COUNT, 0), null);
    }

    private static long insertNoteAtomic(Context context, SQLiteDatabase sQLiteDatabase, ContentValues contentValues) {
        sQLiteDatabase.beginTransaction();
        try {
            int iCheckIfParentIdExist = checkIfParentIdExist(sQLiteDatabase, contentValues);
            if (iCheckIfParentIdExist == 0) {
                ArrayList<ContentValues> arrayListRemoveDataValuesFromNoteValues = Notes.Utils.removeDataValuesFromNoteValues(contentValues);
                insertPlainTextValue(contentValues);
                boolean zIsPrivateNote = isPrivateNote(contentValues);
                if (zIsPrivateNote) {
                    contentValues = encryptSnippet(context, contentValues);
                }
                long jInsert = sQLiteDatabase.insert("note", null, contentValues);
                if (jInsert > 0) {
                    if (arrayListRemoveDataValuesFromNoteValues != null) {
                        for (ContentValues contentValuesEncryptContent : arrayListRemoveDataValuesFromNoteValues) {
                            if (zIsPrivateNote && Notes.TextData.CONTENT_ITEM_TYPE.equals(contentValuesEncryptContent.getAsString(Notes.Data.MIME_TYPE))) {
                                contentValuesEncryptContent = encryptContent(context, contentValuesEncryptContent);
                            }
                            contentValuesEncryptContent.put("note_id", Long.valueOf(jInsert));
                            sQLiteDatabase.insert("data", null, contentValuesEncryptContent);
                        }
                    }
                    sQLiteDatabase.setTransactionSuccessful();
                }
                printAtomicUpdate("insert", jInsert, contentValues);
                return jInsert;
            }
            return iCheckIfParentIdExist;
        } finally {
            sQLiteDatabase.endTransaction();
        }
    }

    private static int checkIfParentIdExist(SQLiteDatabase sQLiteDatabase, ContentValues contentValues) {
        if (contentValues.containsKey("parent_id")) {
            Long asLong = contentValues.getAsLong("parent_id");
            if (asLong.longValue() > 0) {
                Cursor cursorQuery = sQLiteDatabase.query("note", new String[]{"_id"}, "_id=" + asLong + " AND type=1", null, null, null, null);
                if (cursorQuery != null) {
                    try {
                        if (cursorQuery.getCount() > 0) {
                            cursorQuery.close();
                            return 0;
                        }
                        cursorQuery.close();
                    } catch (Throwable th) {
                        cursorQuery.close();
                        throw th;
                    }
                }
                Logger.INSTANCE.e(TAG, "Cann't find parent_id = " + asLong);
                return -100;
            }
        }
        return 0;
    }

    private static void insertPlainTextValue(ContentValues contentValues) {
        String asString = contentValues.getAsString("snippet");
        if (TextUtils.isEmpty(asString)) {
            return;
        }
        contentValues.put("plain_text", NoteSchema.extractPlainTextForNotes(asString));
    }

    private static boolean isPrivateNote(ContentValues contentValues) {
        Long asLong = contentValues.getAsLong("parent_id");
        long jLongValue = asLong != null ? asLong.longValue() : -4098L;
        Long asLong2 = contentValues.getAsLong("origin_parent_id");
        return NoteModel.isHidden(jLongValue, asLong2 != null ? asLong2.longValue() : -4098L);
    }

    private static void updateFolder(SQLiteDatabase sQLiteDatabase, long j) {
        updateFolder(sQLiteDatabase, String.format(Locale.US, "%s=%d", "_id", Long.valueOf(j)));
    }

    private static void updateFolder(SQLiteDatabase sQLiteDatabase, String str) {
        StringBuilder sb = new StringBuilder("UPDATE note SET bg_color_id=(  SELECT t.bg_color_id   FROM note AS t   WHERE t.parent_id=note._id   ORDER BY t.modified_date DESC LIMIT 1) WHERE type!=0  AND notes_count>0  AND (");
        sb.append(str).append(")");
        sQLiteDatabase.execSQL(sb.toString());
    }

    private static ContentValues encryptSnippet(Context context, ContentValues contentValues) {
        return encryptSnippet(context, contentValues, false);
    }

    private static ContentValues encryptSnippet(Context context, ContentValues contentValues, boolean z) {
        if (!z) {
            contentValues = new ContentValues(contentValues);
        }
        if (contentValues.containsKey("snippet")) {
            try {
                contentValues.put("snippet", EncryptUtil.encrypt(context, contentValues.getAsString("snippet")));
            } catch (EncryptFailedException e) {
                throw new RuntimeException("can not encrypt notes", e);
            }
        }
        if (!contentValues.containsKey("plain_text")) {
            return contentValues;
        }
        try {
            contentValues.put("plain_text", EncryptUtil.encrypt(context, contentValues.getAsString("plain_text")));
            return contentValues;
        } catch (EncryptFailedException e2) {
            throw new RuntimeException("can not encrypt notes", e2);
        }
    }

    private static ContentValues encryptContent(Context context, ContentValues contentValues) {
        return encryptContent(context, contentValues, false);
    }

    private static ContentValues encryptContent(Context context, ContentValues contentValues, boolean z) {
        if (!z) {
            contentValues = new ContentValues(contentValues);
        }
        if (!contentValues.containsKey("content")) {
            return contentValues;
        }
        try {
            contentValues.put("content", EncryptUtil.encrypt(context, contentValues.getAsString("content")));
            return contentValues;
        } catch (EncryptFailedException e) {
            throw new RuntimeException("can not encrypt notes", e);
        }
    }

    private static void printAtomicUpdate(String str, long j, ContentValues contentValues) {
        Long asLong = contentValues.getAsLong("parent_id");
        Long asLong2 = contentValues.getAsLong("origin_parent_id");
        Long asLong3 = contentValues.getAsLong("sync_id");
        String asString = contentValues.getAsString("sync_tag");
        Long asLong4 = contentValues.getAsLong("created_date");
        Long asLong5 = contentValues.getAsLong("modified_date");
        Integer asInteger = contentValues.getAsInteger(Notes.Note.LOCAL_MODIFIED);
        String asString2 = contentValues.getAsString("snippet");
        Logger.INSTANCE.i(TAG, "Success to " + str + " note atomic, uid=" + Binder.getCallingUid() + ", pid=" + Binder.getCallingPid() + ", id=" + j + ", parentId=" + asLong + ", originParentId=" + asLong2 + ", syncId=" + asLong3 + ", syncTag=" + asString + ", createdDate=" + asLong4 + ", modifiedDate=" + asLong5 + ", localModified=" + asInteger + ", size=" + (asString2 == null ? "null" : String.valueOf(asString2.length())));
    }

    public static boolean appIsRunning(Context context) {
        try {
            Iterator<ActivityManager.RunningAppProcessInfo> it = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses().iterator();
            while (it.hasNext()) {
                if (it.next().processName.equals("com.miui.notes")) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
