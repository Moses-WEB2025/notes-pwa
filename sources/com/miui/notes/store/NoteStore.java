package com.miui.notes.store;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import cn.kuaipan.android.utils.SQLUtility;
import com.miui.common.tool.DbQueryHelper;
import com.miui.common.tool.Logger;
import com.miui.common.tool.TextProcessUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.adapter.BindContext;
import com.miui.notes.model.NoteEntity;
import com.miui.notes.model.NoteEntityHelper;
import com.miui.notes.model.NoteModel;
import com.miui.notes.provider.Notes;
import com.miui.notes.provider.NotesProvider;
import com.miui.notes.tool.DataUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.tool.util.CursorLoaderBuilder;
import com.miui.notes.ui.AlarmReceiver;
import com.miui.pad.feature.notes.multihandwrite.MultiHandWriteDataUtils;
import com.xiaomi.micloudsdk.utils.PermissionUtils;
import java.util.Collection;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import miuix.os.Build;

/* JADX INFO: loaded from: classes4.dex */
public class NoteStore {
    private static final String EMPTY_SELECTION = "(note_content_type<>'multi_handwrite' AND snippet='' AND (title=''  OR title is null ) OR note_content_type='multi_handwrite' AND is_empty='0') AND (parent_id>=0 OR parent_id=-2 OR parent_id=-4 OR parent_id=-5 OR parent_id=-6 OR parent_id=-8) AND type=0";
    private static final String EXPIRED_TRASH_SELECTION = "parent_id=? AND type=0 AND moved_date <= ?";
    private static final String EXPIRED_TRASH_UNDELETION = "parent_id=? AND type=0 AND deletion_tag = 0";
    public static final int MAX_BATCH_SIZE = 500;
    static final String QUERY_ALL_NOTES_NO_DELETED_NO_FOLDER_SELECTION = "type=0 AND deletion_tag <> 1 AND parent_id = 0";
    private static final String QUERY_ALL_NOTES_NO_DELETED_NO_TRASH_NO_PRIVATE_SELECTION = "type=0 AND deletion_tag <> 1 AND parent_id <> -3 AND parent_id <> -4";
    private static final String QUERY_ALL_NOTES_NO_DELETED_NO_TRASH_SELECTION = "type=0 AND deletion_tag <> 1 AND parent_id <> -3";
    static final String QUERY_ALL_NOTES_NO_DELETED_SELECTION = "type=0 AND deletion_tag <> 1";
    private static final String QUERY_SELECTION = "parent_id=? AND type=0";
    private static final String RANGE_SELECTION = "_id IN (%s) AND type=0";
    public static final String SORT_BY_CREATED_DATE = "stick_date DESC, created_date DESC,modified_date DESC";
    public static final String SORT_BY_MODIFIED_DATE = "stick_date DESC, modified_date DESC";
    private static final String TAG = "NoteStore";
    private static final long TRASH_EXPIRY_DATE_DURATION = TimeUnit.DAYS.toMillis(30);

    public static NoteModel parse(Cursor cursor) {
        return new NoteModel(cursor.getLong(0), cursor.getLong(1), cursor.getInt(12), cursor.getInt(2), cursor.getString(4), cursor.getInt(6), cursor.getInt(7), cursor.getLong(8), cursor.getLong(9), cursor.getLong(10), cursor.getLong(11), cursor.getLong(13), cursor.getString(16), cursor.getString(17), cursor.getString(18), cursor.getString(19), cursor.getString(20), cursor.getString(21));
    }

    public static long getId(Cursor cursor) {
        return cursor.getLong(0);
    }

    public static String getNoteType(Cursor cursor) {
        return cursor.getString(18);
    }

    public static long getModifiedTime(Cursor cursor) {
        return cursor.getLong(9);
    }

    public static int hideExpiredTrashNotes(Context context) {
        Uri uri = Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER;
        String[] strArr = {String.valueOf(-3), String.valueOf(System.currentTimeMillis() - TRASH_EXPIRY_DATE_DURATION)};
        ContentValues contentValues = new ContentValues();
        contentValues.put(Notes.Note.DELETION_TAG, (Integer) 1);
        int iUpdate = context.getContentResolver().update(uri, contentValues, EXPIRED_TRASH_SELECTION, strArr);
        Logger.INSTANCE.i(TAG, "hideExpiredTrashNotes-> " + iUpdate);
        return iUpdate;
    }

    public static Loader<Cursor> query(LoaderManager loaderManager, long j, BindContext bindContext, int i, LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks) {
        Uri uriBuild;
        Logger.INSTANCE.d(TAG, "query: folderId=" + j);
        String searchToken = bindContext.getSearchToken();
        int searchType = bindContext.getSearchType();
        Uri uriAppendFlag = Notes.Note.CONTENT_URI;
        String[] strArr = {String.valueOf(j)};
        boolean zIsEmpty = TextUtils.isEmpty(searchToken);
        String str = EXPIRED_TRASH_UNDELETION;
        if (!zIsEmpty) {
            boolean z = searchType == 1 || TextProcessUtils.hasPhoneNumbers(searchToken);
            if (j != -3) {
                String str2 = "type=0 AND deletion_tag <> 1 AND parent_id <> -3 AND parent_id <> -4" + DbQueryHelper.wrapAndSelection(getSearchContentSelection(searchToken, searchType, z)).toString();
                Logger.INSTANCE.d("SearchTest", "all folder no trash no private search : " + str2);
                str = str2;
                uriAppendFlag = uriAppendFlag.buildUpon().appendQueryParameter(NotesProvider.CALLER_IS_CALL_NOTE, String.valueOf(z)).build();
                strArr = null;
            } else {
                if (z) {
                    uriBuild = uriAppendFlag.buildUpon().appendQueryParameter(NotesProvider.CALLER_IS_CALL_NOTE, String.valueOf(true)).build();
                } else {
                    uriBuild = Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER;
                }
                String str3 = EXPIRED_TRASH_UNDELETION + DbQueryHelper.wrapAndSelection(getSearchContentSelection(searchToken, searchType, z)).toString();
                Logger.INSTANCE.d("SearchTest", "trash search : " + str3);
                uriAppendFlag = uriBuild;
                str = str3;
            }
        } else {
            if (j == -4) {
                uriAppendFlag = NotesProvider.appendFlag(uriAppendFlag, NotesProvider.CALLER_IS_PRIVACY);
            } else if (j == -3) {
                uriAppendFlag = Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER;
            } else if (j == -4097) {
                str = QUERY_ALL_NOTES_NO_DELETED_SELECTION;
                strArr = null;
            }
            str = QUERY_SELECTION;
        }
        return new CursorLoaderBuilder(loaderManager, i).setUri(uriAppendFlag).setProjection(ItemStore.PROJECTION).setSelection(str).setSelectionArgs(strArr).setSortOrder(PreferenceUtils.getNoteSortWay(NoteApp.getInstance(), 1) == 0 ? "stick_date DESC, created_date DESC,modified_date DESC" : "stick_date DESC, modified_date DESC").setCallbacks(loaderCallbacks).build();
    }

    public static Loader<Cursor> aiQuery(LoaderManager loaderManager, String str, int i, LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks) {
        return new CursorLoaderBuilder(loaderManager, i).setUri(Notes.Note.CONTENT_URI).setProjection(ItemStore.PROJECTION).setSelection("type=0 AND deletion_tag <> 1 AND parent_id <> -3 AND parent_id <> -4 AND " + str).setSelectionArgs(null).setSortOrder(null).setCallbacks(loaderCallbacks).build();
    }

    public static Loader<Cursor> widgetSelectQuery(Context context, LoaderManager loaderManager, long j, BindContext bindContext, int i, LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks, String str) {
        String str2;
        String searchToken = bindContext.getSearchToken();
        int searchType = bindContext.getSearchType();
        Uri uriBuild = Notes.Note.CONTENT_URI;
        if (!TextUtils.isEmpty(searchToken)) {
            boolean z = searchType == 1 || TextProcessUtils.hasPhoneNumbers(searchToken);
            str2 = "type=0 AND deletion_tag <> 1 AND parent_id <> -3 AND parent_id <> -4" + DbQueryHelper.wrapAndSelection(getSearchContentSelection(searchToken, searchType, z)).toString();
            Logger.INSTANCE.d("SearchTest", "all folder no trash no private search : " + str2);
            uriBuild = uriBuild.buildUpon().appendQueryParameter(NotesProvider.CALLER_IS_CALL_NOTE, String.valueOf(z)).build();
        } else {
            str2 = QUERY_ALL_NOTES_NO_DELETED_SELECTION;
        }
        return new CursorLoaderBuilder(loaderManager, i).setUri(uriBuild).setProjection(ItemStore.PROJECTION).setSelection(str2 + str).setSelectionArgs(null).setSortOrder(PreferenceUtils.getNoteSortWay(context, 1) == 0 ? "stick_date DESC, created_date DESC,modified_date DESC" : "stick_date DESC, modified_date DESC").setCallbacks(loaderCallbacks).build();
    }

    private static String getSearchContentSelection(String str, int i, boolean z) {
        StringBuilder sb = new StringBuilder();
        if (i == 1) {
            sb.append(generateCallNoteSearchSelection(str));
        } else {
            String strGenerateNoteContentSearchSelection = generateNoteContentSearchSelection(str);
            if (z) {
                sb.append((CharSequence) wrapBracketSelection(strGenerateNoteContentSearchSelection)).append((CharSequence) wrapOrSelection(generateCallNoteSearchSelection(str)));
            } else {
                sb.append(strGenerateNoteContentSearchSelection);
            }
        }
        return sb.toString();
    }

    private static StringBuilder wrapAndSelection(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(" AND ( ").append(str).append(" ) ");
        return sb;
    }

    private static StringBuilder wrapOrSelection(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(" OR ( ").append(str).append(" ) ");
        return sb;
    }

    private static StringBuilder wrapBracketSelection(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(" ( ").append(str).append(" ) ");
        return sb;
    }

    private static String generateCallNoteSearchSelection(String str) {
        StringBuilder sb = new StringBuilder();
        String[] strArrSplit = DbQueryHelper.formattedKeyWords(str).trim().split(TextProcessUtils.ONE_OR_MULTI_SPACE_REG);
        for (int i = 0; i < strArrSplit.length; i++) {
            if (i > 0) {
                sb.append(" OR ");
            }
            sb.append(" LIKE ('%").append(strArrSplit[i]).append("%',data3,'/')");
        }
        return sb.toString();
    }

    private static String generateNoteContentSearchSelection(String str) {
        StringBuilder sb = new StringBuilder();
        String str2 = DbQueryHelper.formattedKeyWords(str);
        sb.append(generateNoteContentSelectionByContentType("plain_text", str2));
        sb.append(" OR ");
        sb.append(generateNoteContentSelectionByContentType(Notes.Note.MIND_CONTENT_PLAIN_TEXT, str2));
        sb.append(" OR ");
        sb.append(generateNoteContentSelectionByContentType("title", str2));
        return sb.toString();
    }

    private static String generateNoteContentSelectionByContentType(String str, String str2) {
        StringBuilder sb = new StringBuilder("LIKE('%");
        sb.append(str2).append("%',").append(str).append(",'/')");
        return sb.toString();
    }

    private static String generateNormalNoteContentSelection(String str) {
        StringBuilder sb = new StringBuilder();
        String str2 = DbQueryHelper.formattedKeyWords(str);
        sb.append(generateNoteContentSelectionByContentType("plain_text", str2));
        sb.append(" OR ");
        sb.append(generateNoteContentSelectionByContentType("title", str2));
        return sb.toString();
    }

    private static String generateMindNoteContentSelection(String str) {
        StringBuilder sb = new StringBuilder();
        String str2 = DbQueryHelper.formattedKeyWords(str);
        sb.append(generateNoteContentSelectionByContentType(Notes.Note.MIND_CONTENT_PLAIN_TEXT, str2));
        sb.append(" OR ");
        sb.append(generateNoteContentSelectionByContentType("title", str2));
        return sb.toString();
    }

    public static int delete(Context context, Collection<Long> collection) {
        return delete(context, DataUtils.toArray(collection));
    }

    public static int delete(Context context, long[] jArr) {
        return delete(context, jArr, false);
    }

    public static int delete(Context context, long[] jArr, boolean z) {
        int i = 0;
        int iDelete = 0;
        while (i < jArr.length) {
            int iMin = Math.min(i + 500, jArr.length);
            iDelete += delete(context, jArr, i, iMin, z);
            i = iMin;
        }
        if (iDelete > 0) {
            Notes.Utils.updateAllWidgets(context);
        }
        return iDelete;
    }

    public static int delete(Context context, long[] jArr, int i, int i2, boolean z) {
        String str = String.format(Locale.US, RANGE_SELECTION, DataUtils.join(jArr, i, i2));
        if (z) {
            Uri uri = Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER;
            ContentValues contentValues = new ContentValues();
            contentValues.put(Notes.Note.DELETION_TAG, (Integer) 1);
            return context.getContentResolver().update(uri, contentValues, str, null);
        }
        return context.getContentResolver().delete(Notes.Note.CONTENT_URI, str, null);
    }

    public static int deleteEmpty(Context context, String str) {
        String str2;
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (!TextUtils.isEmpty(str)) {
            str2 = "((note_content_type<>'multi_handwrite' AND snippet='' AND (title=''  OR title is null ) OR note_content_type='multi_handwrite' AND is_empty='0') AND (parent_id>=0 OR parent_id=-2 OR parent_id=-4 OR parent_id=-5 OR parent_id=-6 OR parent_id=-8) AND type=0) AND (" + str + ")";
        } else {
            str2 = EMPTY_SELECTION;
        }
        int iDelete = context.getContentResolver().delete(Notes.Note.CONTENT_URI, str2, null);
        Log.d(TAG, "deleteEmpty count=" + iDelete);
        if (iDelete > 0) {
            Notes.Utils.updateAllWidgets(context);
        }
        Log.d(TAG, "deleteEmpty.." + (System.currentTimeMillis() - jCurrentTimeMillis));
        return iDelete;
    }

    public static Cursor queryEmpty(Context context, long j) {
        return context.getContentResolver().query(Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER, new String[]{Notes.Note.NOTE_CONTENT_TYPE}, "(note_content_type<>'multi_handwrite' AND snippet='' AND (title=''  OR title is null ) OR note_content_type='multi_handwrite' AND is_empty='0') AND (parent_id>=0 OR parent_id=-2 OR parent_id=-4 OR parent_id=-5 OR parent_id=-6 OR parent_id=-8) AND type=0 AND _id=?", new String[]{String.valueOf(j)}, null);
    }

    public static Cursor queryById(Context context, long j) {
        return context.getContentResolver().query(Notes.Note.CONTENT_URI, ItemStore.PROJECTION, "type=0 AND deletion_tag <> 1 AND parent_id <> -3 AND parent_id <> -4 AND _id=" + j, null, null);
    }

    public static class DeleteEmptyRunnable implements Runnable {
        @Override // java.lang.Runnable
        public void run() {
            Thread.currentThread().setName("DeleteEmptyRunnable");
            NoteStore.deleteEmpty(NoteApp.getInstance(), null);
            Logger.INSTANCE.d(NoteStore.TAG, "DeleteEmptyRunnable doInBackground ..run");
        }
    }

    public static int move(Context context, Collection<Long> collection, long j) {
        return move(context, DataUtils.toArray(collection), j);
    }

    public static int move(Context context, long[] jArr, long j) {
        int iMove = 0;
        int i = 0;
        while (i < jArr.length) {
            int iMin = Math.min(i + 500, jArr.length);
            Context context2 = context;
            iMove += move(context2, jArr, j, i, iMin);
            context = context2;
            i = iMin;
        }
        FolderStore.INSTANCE.updateFolderModifiedTime(context, j);
        return iMove;
    }

    public static int move(Context context, long[] jArr, long j, int i, int i2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("parent_id", Long.valueOf(j));
        contentValues.put(Notes.Note.LOCAL_MODIFIED, (Integer) 1);
        String strJoin = DataUtils.join(jArr, i, i2);
        Logger.INSTANCE.d("ViewFolderChooser", "Range" + strJoin);
        int iUpdate = context.getContentResolver().update(Notes.Note.CONTENT_URI, contentValues, String.format(Locale.US, RANGE_SELECTION, strJoin), null);
        Logger.INSTANCE.d("ViewFolderChooser", "count" + iUpdate);
        return iUpdate;
    }

    public static int stick(Context context, Collection<Long> collection, boolean z) {
        return stick(context, DataUtils.toArray(collection), z);
    }

    public static int stick(Context context, long[] jArr, boolean z) {
        int i = 0;
        int iStick = 0;
        while (i < jArr.length) {
            int iMin = Math.min(i + 500, jArr.length);
            iStick += stick(context, jArr, z, i, iMin);
            i = iMin;
        }
        return iStick;
    }

    private static int stick(Context context, long[] jArr, boolean z, int i, int i2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Notes.Note.STICK_DATE, Long.valueOf(z ? System.currentTimeMillis() : 0L));
        contentValues.put(Notes.Note.LOCAL_MODIFIED, (Integer) 1);
        return context.getContentResolver().update(Notes.Note.CONTENT_URI, contentValues, String.format(Locale.US, RANGE_SELECTION, DataUtils.join(jArr, i, i2)), null);
    }

    public static int hide(Context context, long j) {
        return hide(context, new long[]{j});
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0072  */
    public static int hide(Context context, long[] jArr) {
        int i = 0;
        if (jArr != null && jArr.length > 0) {
            int length = jArr.length;
            int i2 = 0;
            while (i < length) {
                long j = jArr[i];
                NoteEntity noteEntityLoad = NoteEntityHelper.load(context, "_id=" + j, null);
                if (noteEntityLoad != null && noteEntityLoad.getParentId() != -4) {
                    noteEntityLoad.setParentId(-4L);
                    if (TextUtils.equals("multi_handwrite", noteEntityLoad.getNoteType())) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("parent_id", Long.valueOf(noteEntityLoad.getParentId()));
                        contentValues.put("modified_date", Long.valueOf(System.currentTimeMillis()));
                        contentValues.put("snippet", noteEntityLoad.getContent());
                        if (MultiHandWriteDataUtils.updateValues(contentValues, j)) {
                            i2++;
                        }
                    } else if (noteEntityLoad.syncNote(context)) {
                        i2++;
                    }
                }
                i++;
            }
            i = i2;
        }
        if (i > 0) {
            Notes.Utils.updateAllWidgets(context);
        }
        return i;
    }

    public static int unhide(Context context, long j) {
        return unhide(context, new long[]{j});
    }

    /* JADX WARN: Code duplicated, block: B:26:0x0085  */
    public static int unhide(Context context, long[] jArr) {
        int i = 0;
        if (jArr != null && jArr.length > 0) {
            int i2 = 0;
            for (long j : jArr) {
                NoteEntity noteEntityLoad = NoteEntityHelper.load(context, "_id=" + j, null);
                if (noteEntityLoad != null && noteEntityLoad.getParentId() == -4) {
                    if (TextUtils.equals("multi_handwrite", noteEntityLoad.getNoteType())) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("parent_id", (Integer) 0);
                        contentValues.put("modified_date", Long.valueOf(System.currentTimeMillis()));
                        contentValues.put("snippet", noteEntityLoad.getContent());
                        if (MultiHandWriteDataUtils.updateValues(contentValues, j)) {
                            i2++;
                        }
                    } else {
                        try {
                            if (TextUtils.isEmpty(noteEntityLoad.getCallNumber())) {
                                noteEntityLoad.setParentId(0L);
                            } else {
                                noteEntityLoad.setParentId(-2L);
                            }
                        } catch (Exception unused) {
                            noteEntityLoad.setParentId(0L);
                        }
                        if (noteEntityLoad.syncNote(context)) {
                            i2++;
                        }
                    }
                }
            }
            i = i2;
        }
        if (i > 0) {
            Notes.Utils.updateAllWidgets(context);
        }
        return i;
    }

    public static int restore(Context context, long[] jArr) {
        int i = 0;
        int iRestore = 0;
        while (i < jArr.length) {
            int iMin = Math.min(i + 500, jArr.length);
            iRestore += restore(context, jArr, i, iMin);
            i = iMin;
        }
        return iRestore;
    }

    public static void setupRestoreAlarms(Context context, long[] jArr) {
        Cursor cursorQuery = context.getContentResolver().query(NotesProvider.appendFlag(Notes.Note.CONTENT_URI, NotesProvider.CALLER_IS_PRIVACY), new String[]{"_id", Notes.Note.ALERTED_DATE}, "alert_date>? AND type=0 AND " + String.format(Locale.US, RANGE_SELECTION, DataUtils.join(jArr, 0, Math.min(500, jArr.length))), new String[]{String.valueOf(System.currentTimeMillis())}, null);
        try {
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.moveToFirst()) {
                        while (true) {
                            Context context2 = context;
                            AlarmReceiver.scheduleAlert(context2, cursorQuery.getLong(0), cursorQuery.getLong(1), false);
                            if (!cursorQuery.moveToNext()) {
                                break;
                            } else {
                                context = context2;
                            }
                        }
                    }
                } catch (Exception e) {
                    Logger.INSTANCE.e(TAG, "setupRestoreAlarms error " + e);
                    e.printStackTrace();
                }
            }
        } finally {
            cursorQuery.close();
        }
    }

    private static int restore(Context context, long[] jArr, int i, int i2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Notes.Note.LOCAL_MODIFIED, (Integer) 1);
        String str = String.format(Locale.US, RANGE_SELECTION, DataUtils.join(jArr, i, i2));
        int iUpdate = context.getContentResolver().update(NotesProvider.getRestoreUri(i2 - i), contentValues, str, null);
        if (iUpdate > 0) {
            Notes.Utils.updateAllWidgets(context);
            setupRestoreAlarms(context, jArr);
        }
        return iUpdate;
    }

    public static long getNextNote(Context context, long j, long j2) {
        String str;
        if (PreferenceUtils.getNoteSortWay(context, 1) != 0) {
            str = "modified_date";
        } else {
            str = "created_date";
        }
        String[] strArr = {"_id", str};
        String str2 = "parent_id=" + j + " AND type=0";
        String strConcat = str.concat(SQLUtility.SORT_DESC);
        Uri uriAppendFlag = Notes.Note.CONTENT_URI;
        if (j == -4) {
            uriAppendFlag = NotesProvider.appendFlag(uriAppendFlag, NotesProvider.CALLER_IS_PRIVACY);
        }
        Cursor cursorQuery = context.getContentResolver().query(uriAppendFlag, strArr, str2, null, strConcat);
        long j3 = -1;
        if (cursorQuery == null) {
            return -1L;
        }
        while (cursorQuery.moveToNext() && cursorQuery.getLong(1) >= j2) {
            try {
            } catch (Throwable th) {
                cursorQuery.close();
                throw th;
            }
        }
        if (!cursorQuery.isAfterLast() || cursorQuery.moveToPosition(0)) {
            j3 = cursorQuery.getLong(0);
        }
        cursorQuery.close();
        return j3;
    }

    public static void clearSyncInfoIfNeed(Context context) {
        if (Build.IS_INTERNATIONAL_BUILD && !PermissionUtils.isGdprPermissionGranted(context)) {
            Logger.INSTANCE.d(TAG, "!isGdprPermissionGranted");
            clearSyncInfo(context);
        } else {
            Logger.INSTANCE.d(TAG, "isGdprPermissionGranted");
        }
    }

    public static void clearSyncInfo(Context context) {
        Uri uri = Notes.Note.CONTENT_URI;
        ContentValues contentValues = new ContentValues();
        contentValues.put("sync_id", (Long) 0L);
        contentValues.put("sync_tag", "");
        contentValues.put(Notes.Note.SYNC_DATA1, (Integer) 0);
        contentValues.put(Notes.Note.SYNC_DATA2, (Integer) 0);
        contentValues.put(Notes.Note.SYNC_DATA3, (Integer) 0);
        contentValues.put(Notes.Note.SYNC_DATA4, (Integer) 0);
        contentValues.put(Notes.Note.SYNC_DATA5, (Integer) 0);
        contentValues.put(Notes.Note.SYNC_DATA6, "");
        contentValues.put(Notes.Note.SYNC_DATA7, "");
        contentValues.put(Notes.Note.SYNC_DATA8, "");
        contentValues.put("origin_parent_id", (Integer) 0);
        contentValues.put(Notes.Note.LOCAL_MODIFIED, (Integer) 1);
        contentValues.put("account_id", (Long) 0L);
        int iUpdate = context.getContentResolver().update(uri, contentValues, "sync_id <> 0", null);
        if (iUpdate >= 0) {
            Logger.INSTANCE.d(TAG, "Clear note sync info successfully,count:" + iUpdate);
        } else {
            Logger.INSTANCE.d(TAG, "Fail to clear note sync info.");
        }
        Uri uri2 = Notes.Data.CONTENT_URI;
        contentValues.clear();
        contentValues.put("file_id", "");
        contentValues.put(Notes.Data.DIRTY, (Integer) 1);
        contentValues.put("data1", (Long) 0L);
        contentValues.put(Notes.Data.DATA2, (Long) 0L);
        contentValues.put("data3", "");
        contentValues.put(Notes.Data.DATA4, "");
        contentValues.put(Notes.Data.DATA5, "");
        if (context.getContentResolver().update(uri2, contentValues, null, null) >= 0) {
            Logger.INSTANCE.d(TAG, "Clear data sync info successfully");
        } else {
            Logger.INSTANCE.e(TAG, "Fail to clear data sync info.");
        }
    }
}
