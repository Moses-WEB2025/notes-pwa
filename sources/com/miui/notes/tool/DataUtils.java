package com.miui.notes.tool;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import ch.qos.logback.core.CoreConstants;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.provider.Notes;
import com.miui.notes.provider.NotesProvider;
import com.miui.notes.widget.WidgetProcessDatabaseUtils;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/* JADX INFO: loaded from: classes4.dex */
public class DataUtils {
    private static final int BATCH_COUNT = 500;
    private static final char COLLECTION_DELIMITER_CHAR = ',';
    private static final String EXTRA_PROJECTION = "projection";
    private static final String EXTRA_SELECTION = "selection";
    private static final String EXTRA_SELECTION_ARGS = "selectionArgs";
    private static final String EXTRA_SORT_ORDER = "sortOrder";
    private static final String EXTRA_URI = "uri";
    private static final String FOLDER_TYPE_SELECTION = "type=1";
    public static final String TAG = "DataUtils";

    public static Bundle getSelectionBundle(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("uri", uri);
        bundle.putStringArray(EXTRA_PROJECTION, strArr);
        bundle.putString(EXTRA_SELECTION, str);
        bundle.putStringArray(EXTRA_SELECTION_ARGS, strArr2);
        bundle.putString(EXTRA_SORT_ORDER, str2);
        return bundle;
    }

    public static void batchDeleteNotes(ContentResolver contentResolver, HashSet<Long> hashSet) {
        int i;
        if (hashSet == null || hashSet.isEmpty()) {
            LogUtils.d(TAG, "Invalid parameters ids: " + hashSet);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("_id IN (");
        int length = sb.length();
        Iterator<Long> it = hashSet.iterator();
        loop0: while (true) {
            i = 0;
            do {
                if (!it.hasNext()) {
                    break loop0;
                }
                sb.append(it.next()).append(',');
                i++;
            } while (i < 500);
            sb.setLength(sb.length() - 1);
            sb.append(CoreConstants.RIGHT_PARENTHESIS_CHAR);
            doBatchDeleteNotes(contentResolver, sb.toString());
            sb.setLength(length);
        }
        if (i > 0) {
            sb.setLength(sb.length() - 1);
            sb.append(CoreConstants.RIGHT_PARENTHESIS_CHAR);
            doBatchDeleteNotes(contentResolver, sb.toString());
        }
    }

    private static void doBatchDeleteNotes(ContentResolver contentResolver, String str) {
        if (contentResolver.delete(Notes.Note.CONTENT_URI, str, null) <= 0) {
            LogUtils.d(TAG, "delete notes failed, selection:" + str);
        }
    }

    public static boolean checkVisibleFolderName(ContentResolver contentResolver, String str) {
        Cursor cursorQuery = contentResolver.query(Notes.Note.CONTENT_URI, null, "type=1 AND subject=? ", new String[]{str}, null);
        if (cursorQuery == null) {
            return false;
        }
        int count = cursorQuery.getCount();
        Logger.INSTANCE.d(TAG, "checkVisibleFolderName " + str + " ,count:" + count);
        boolean z = count > 0;
        cursorQuery.close();
        return z;
    }

    public static int updateNoteWidgetId(Context context, long j, long j2, long j3) {
        ContentValues contentValues = new ContentValues(2);
        contentValues.put("widget_id", Long.valueOf(j2));
        contentValues.put("widget_type", Long.valueOf(j3));
        try {
            if (WidgetProcessDatabaseUtils.appIsRunning(context)) {
                return context.getContentResolver().update(ContentUris.withAppendedId(Notes.Note.CONTENT_URI, j), contentValues, null, null);
            }
            return WidgetProcessDatabaseUtils.updateItem(context, j, contentValues, null, null);
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, "update widget:" + e.getMessage());
            return WidgetProcessDatabaseUtils.updateItem(context, j, contentValues, null, null);
        }
    }

    public static void deleteWidget(Context context, ContentValues contentValues, String str, String[] strArr) {
        if (WidgetProcessDatabaseUtils.appIsRunning(context)) {
            try {
                context.getContentResolver().update(Notes.Note.CONTENT_URI, contentValues, str, strArr);
                return;
            } catch (Exception e) {
                Logger.INSTANCE.d(TAG, "delete Widget:" + e.getMessage());
                WidgetProcessDatabaseUtils.update(context, contentValues, str, strArr);
                return;
            }
        }
        WidgetProcessDatabaseUtils.update(context, contentValues, str, strArr);
    }

    public static int[] getWidgetInfoByNote(Context context, long j) {
        Cursor cursorQuery = context.getContentResolver().query(NotesProvider.appendFlag(Notes.Note.CONTENT_URI, NotesProvider.CALLER_IS_PRIVACY), new String[]{"widget_id", "widget_type"}, "_id=" + j, null, null);
        if (cursorQuery == null) {
            return null;
        }
        try {
            if (cursorQuery.moveToNext()) {
                return new int[]{cursorQuery.getInt(0), cursorQuery.getInt(1)};
            }
            return null;
        } finally {
            cursorQuery.close();
        }
    }

    public static long insertNoteFolder(Context context, String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("subject", str);
        contentValues.put("type", (Integer) 1);
        contentValues.put(Notes.Note.LOCAL_MODIFIED, (Integer) 1);
        return ContentUris.parseId(context.getContentResolver().insert(Notes.Note.CONTENT_URI, contentValues));
    }

    public static boolean restoreNoteFolder(Context context, String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("subject", str);
        contentValues.put(Notes.Note.LOCAL_MODIFIED, (Integer) 1);
        contentValues.put("parent_id", (Integer) 0);
        long jCurrentTimeMillis = System.currentTimeMillis();
        contentValues.put("created_date", Long.valueOf(jCurrentTimeMillis));
        contentValues.put("modified_date", Long.valueOf(jCurrentTimeMillis));
        return context.getContentResolver().update(Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER, contentValues, "subject=? AND type=? AND parent_id=?", new String[]{str, Integer.toString(1), Long.toString(-3L)}) > 0;
    }

    public static long getVisibleFolderIdBySubject(Context context, String str) {
        Cursor cursorQuery = context.getContentResolver().query(Notes.Note.CONTENT_URI, new String[]{"_id"}, "subject=? AND type=?", new String[]{str, Integer.toString(1)}, null);
        if (cursorQuery == null) {
            return -1L;
        }
        try {
            if (cursorQuery.moveToNext()) {
                return cursorQuery.getLong(0);
            }
            return -1L;
        } finally {
            cursorQuery.close();
        }
    }

    public static String verifyNoteAlert(Context context, long j, long j2) {
        Cursor cursorQuery = context.getContentResolver().query(NotesProvider.appendFlag(Notes.Note.CONTENT_URI, NotesProvider.CALLER_IS_PRIVACY), new String[]{"snippet", "parent_id"}, "_id=" + j + " AND alert_date=" + j2 + " AND type=0 AND parent_id!=-3", null, null);
        if (cursorQuery == null) {
            return null;
        }
        try {
            if (!cursorQuery.moveToNext()) {
                return null;
            }
            if (cursorQuery.getLong(1) == -4) {
                return context.getString(R.string.alert_title_hidden_note);
            }
            return cursorQuery.getString(0);
        } finally {
            cursorQuery.close();
        }
    }

    public static void updateAlertTag(Context context, long j) {
        Uri uriAppendFlag = NotesProvider.appendFlag(Notes.Note.CONTENT_URI, NotesProvider.CALLER_IS_PRIVACY);
        ContentValues contentValues = new ContentValues();
        contentValues.put(Notes.Note.ALERT_TAG, (Integer) 2);
        context.getContentResolver().update(uriAppendFlag, contentValues, "_id = ?", new String[]{String.valueOf(j)});
    }

    public static String join(long[] jArr) {
        return join(jArr, 0, jArr.length);
    }

    public static String join(long[] jArr, int i, int i2) {
        if (i >= i2) {
            return "";
        }
        if (i + 1 == i2) {
            return String.valueOf(jArr[i]);
        }
        StringBuilder sb = new StringBuilder();
        while (i < i2) {
            sb.append(jArr[i]).append(',');
            i++;
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    public static long[] toArray(Collection<Long> collection) {
        long[] jArr = new long[collection.size()];
        Iterator<Long> it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            jArr[i] = it.next().longValue();
            i++;
        }
        return jArr;
    }
}
