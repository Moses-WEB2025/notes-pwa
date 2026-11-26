package com.miui.todo.data.provider;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import ch.qos.logback.core.CoreConstants;
import com.miui.notes.NoteApp;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.todo.cloudservice.LocalSortEntity;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class TodoDatabaseAccessUtils {
    static void deleteBatch(String[] strArr) {
        TodoDatabaseHelper.getInstance(NoteApp.getInstance()).getReadableDatabase().execSQL(String.format("delete from todo where id in (%s);", TextUtils.join(", ", strArr)));
    }

    static void localDeleteBatch(String[] strArr) {
        TodoDatabaseHelper.getInstance(NoteApp.getInstance()).getReadableDatabase().execSQL(String.format("update todo set local_status = 3 where id in (%s);", TextUtils.join(", ", strArr)));
    }

    static void setBatchTime(String[] strArr, int i, long j, long j2) {
        TodoDatabaseHelper.getInstance(NoteApp.getInstance()).getReadableDatabase().execSQL(String.format("update todo set remind_type = " + i + ",remind_time = " + j + ",expire_time = " + j2 + " where id in (%s);", TextUtils.join(", ", strArr)));
    }

    public static long UpdateAllUnFinished() {
        Cursor cursorQueryAllUnFinishItem = TodoProviderAccessUtils.queryAllUnFinishItem();
        long j = 0;
        if (cursorQueryAllUnFinishItem.getCount() == 0) {
            cursorQueryAllUnFinishItem.close();
            return 0L;
        }
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("(");
        int i = 0;
        while (cursorQueryAllUnFinishItem.moveToNext()) {
            i++;
            j = ((long) i) * 1048576;
            long j2 = cursorQueryAllUnFinishItem.getLong(0);
            sb.append(" when ");
            sb.append(j2);
            sb.append(" then ");
            sb.append(j);
            sb2.append(j2);
            sb2.append(CoreConstants.COMMA_CHAR);
        }
        cursorQueryAllUnFinishItem.close();
        int length = sb2.length();
        sb2.replace(length - 1, length, ")");
        TodoDatabaseHelper.getInstance(NoteApp.getInstance()).getReadableDatabase().execSQL("update todo set custom_sort_id = case id " + ((Object) sb) + " end where id in " + ((Object) sb2));
        return j;
    }

    public static long BatchUpdateSort(List<LocalSortEntity> list) {
        long j = 0;
        if (list != null && list.size() != 0) {
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            sb2.append("(");
            for (LocalSortEntity localSortEntity : list) {
                long customSortId = localSortEntity.getCustomSortId();
                if (j < customSortId) {
                    j = customSortId;
                }
                long id = localSortEntity.getId();
                sb.append(" when ");
                sb.append(id);
                sb.append(" then ");
                sb.append(customSortId);
                sb2.append(id);
                sb2.append(CoreConstants.COMMA_CHAR);
            }
            int length = sb2.length();
            sb2.replace(length - 1, length, ")");
            TodoDatabaseHelper.getInstance(NoteApp.getInstance()).getReadableDatabase().execSQL("update todo set custom_sort_id = case id " + ((Object) sb) + " end where id in " + ((Object) sb2));
        }
        return j;
    }

    public static void UpdateCustomSortId(long j, long j2) {
        TodoDatabaseHelper.getInstance(NoteApp.getInstance()).getReadableDatabase().execSQL("update todo set custom_sort_id = " + j2 + " where id = " + j);
    }

    public static void UpdateLocalAllNew() {
        SQLiteDatabase readableDatabase = TodoDatabaseHelper.getInstance(NoteApp.getInstance()).getReadableDatabase();
        readableDatabase.execSQL("update todo set local_status = 0");
        readableDatabase.execSQL("update todo set SYNC_ID = 0");
    }

    public static void updateUnFinishedArray(List<Long> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("(");
        for (int i = 0; i < list.size(); i++) {
            long jLongValue = list.get(i).longValue();
            sb.append(" when ");
            sb.append(jLongValue);
            sb.append(" then ");
            sb.append(((long) i) * 1048576);
            sb2.append(jLongValue);
            sb2.append(CoreConstants.COMMA_CHAR);
        }
        int length = sb2.length();
        sb2.replace(length - 1, length, ")");
        TodoDatabaseHelper.getInstance(NoteApp.getInstance()).getWritableDatabase().execSQL("update todo set custom_sort_id = case id " + ((Object) sb) + " end where id in " + ((Object) sb2));
        PreferenceUtils.setMaxSortID(((long) list.size()) * 1048576);
    }
}
