package com.miui.todo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.miui.todo.data.bean.TodoEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class TodoProviderDemo {
    public static Uri insertItem(Context context, String str, int i, long j) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("content", str);
        contentValues.put(Todo.REMIND_TYPE, Integer.valueOf(i));
        contentValues.put(Todo.REMIND_TIME, Long.valueOf(j));
        return context.getContentResolver().insert(Uri.parse(Todo.URI_TODO_OUTER_INSERT), contentValues);
    }

    public static Uri insertSubItem(Context context, String str, int i, long j) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("content", str);
        contentValues.put(Todo.REMIND_TYPE, Integer.valueOf(i));
        contentValues.put(Todo.REMIND_TIME, Long.valueOf(j));
        contentValues.put(Todo.LIST_TYPE, (Integer) 1);
        return context.getContentResolver().insert(Uri.parse(Todo.URI_TODO_OUTER_INSERT), contentValues);
    }

    public static Cursor queryAllItem(Context context) {
        return context.getContentResolver().query(Uri.parse(Todo.URI_TODO_OUTER), null, null, null, null);
    }

    public static Cursor queryItem(Context context, long j, long j2) {
        return context.getContentResolver().query(Uri.parse(Todo.URI_TODO_OUTER), null, "remind_time BETWEEN " + j + " AND " + j2, null, null);
    }

    public static Cursor queryItemUnFinished(Context context, long j, long j2) {
        return context.getContentResolver().query(Uri.parse(Todo.URI_TODO_OUTER), null, "remind_time BETWEEN " + j + " AND " + j2 + " AND is_finish == 0", null, null);
    }

    public static Cursor searchTodoItem(Context context, String str) {
        return context.getContentResolver().query(Uri.parse(Todo.URI_TODO_OUTER), null, "plain_text like '%" + str + "%'", null, Todo.CREATE_TIME);
    }

    public static Cursor queryItemCursor(Context context, long j) {
        return context.getContentResolver().query(Uri.parse(Todo.URI_TODO_OUTER), null, "id = " + j, null, null);
    }

    public static int changeItemFinishStatus(Context context, long j, boolean z) {
        ContentValues contentValues = new ContentValues();
        long jCurrentTimeMillis = System.currentTimeMillis();
        contentValues.put("id", Long.valueOf(j));
        contentValues.put(Todo.LAST_MODIFIED_TIME, Long.valueOf(jCurrentTimeMillis));
        if (!z) {
            contentValues.put(Todo.IS_FINISH, (Integer) 0);
        } else {
            contentValues.put(Todo.MARK_FINISH_TIME, Long.valueOf(jCurrentTimeMillis));
            contentValues.put(Todo.IS_FINISH, (Integer) 1);
        }
        return context.getContentResolver().update(Uri.parse(Todo.URI_TODO_OUTER_FINISH), contentValues, null, null);
    }

    public static int changeItemFinishStatus(Context context, long j, int i, boolean z) {
        ContentValues contentValues = new ContentValues();
        long jCurrentTimeMillis = System.currentTimeMillis();
        contentValues.put("id", Long.valueOf(j));
        contentValues.put(Todo.LAST_MODIFIED_TIME, Long.valueOf(jCurrentTimeMillis));
        if (!z) {
            contentValues.put(Todo.IS_FINISH, (Integer) 0);
        } else {
            contentValues.put(Todo.MARK_FINISH_TIME, Long.valueOf(jCurrentTimeMillis));
            contentValues.put(Todo.IS_FINISH, (Integer) 1);
        }
        contentValues.put(Todo.SUB_TODO_INDEX, Integer.valueOf(i));
        return context.getContentResolver().update(Uri.parse(Todo.URI_TODO_OUTER_FINISH), contentValues, null, null);
    }

    public static int changeItemFlexStatus(Context context, long j, boolean z) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", Long.valueOf(j));
        contentValues.put(Todo.IS_FLEX, Boolean.valueOf(z));
        return context.getContentResolver().update(Uri.parse(Todo.URI_TODO_OUTER_FLEX), contentValues, null, null);
    }

    public static List<TodoEntity> queryAodItem(Context context, long j, long j2) {
        Cursor cursorQueryItem = queryItem(context, j, j2);
        ArrayList arrayList = new ArrayList();
        if (cursorQueryItem != null) {
            while (cursorQueryItem.moveToNext()) {
                arrayList.add(new TodoEntity(cursorQueryItem.getLong(cursorQueryItem.getColumnIndex("id")), cursorQueryItem.getString(cursorQueryItem.getColumnIndex("content")), cursorQueryItem.getInt(cursorQueryItem.getColumnIndex(Todo.REMIND_TYPE)), cursorQueryItem.getLong(cursorQueryItem.getColumnIndex(Todo.REMIND_TIME)), cursorQueryItem.getLong(cursorQueryItem.getColumnIndex(Todo.EXPIRE_TIME)), cursorQueryItem.getInt(cursorQueryItem.getColumnIndex(Todo.IS_FINISH)), cursorQueryItem.getLong(cursorQueryItem.getColumnIndex(Todo.MARK_FINISH_TIME)), cursorQueryItem.getLong(cursorQueryItem.getColumnIndex(Todo.LAST_MODIFIED_TIME)), cursorQueryItem.getLong(cursorQueryItem.getColumnIndex(Todo.CREATE_TIME)), cursorQueryItem.getInt(cursorQueryItem.getColumnIndex(Todo.INPUT_TYPE)), cursorQueryItem.getString(cursorQueryItem.getColumnIndex(Todo.AUDIO_FILE_NAME)), cursorQueryItem.getLong(cursorQueryItem.getColumnIndex(Todo.CUSTOM_SORT_ID)), cursorQueryItem.getColumnName(cursorQueryItem.getColumnIndex("plain_text")), cursorQueryItem.getInt(cursorQueryItem.getColumnIndex(Todo.HIDE_TYPE)), cursorQueryItem.getInt(cursorQueryItem.getColumnIndex(Todo.REMIND_REPEAT_TYPE)), cursorQueryItem.getLong(cursorQueryItem.getColumnIndex("sync_id")), cursorQueryItem.getLong(cursorQueryItem.getColumnIndex(Todo.SYNC_ETAG)), cursorQueryItem.getInt(cursorQueryItem.getColumnIndex(Todo.SERVER_STATUS)), cursorQueryItem.getString(cursorQueryItem.getColumnIndex(Todo.AUDIO_FILE_FIELD)), cursorQueryItem.getLong(cursorQueryItem.getColumnIndex(Todo.FIRST_REMIND_TIME)), cursorQueryItem.getInt(cursorQueryItem.getColumnIndex("folder_id")), cursorQueryItem.getInt(cursorQueryItem.getColumnIndex("local_status")), cursorQueryItem.getInt(cursorQueryItem.getColumnIndex("source")), cursorQueryItem.getLong(cursorQueryItem.getColumnIndex(Todo.AUDIO_FILE_SIZE)), cursorQueryItem.getInt(cursorQueryItem.getColumnIndex(Todo.COLOR_LABEL)), cursorQueryItem.getInt(cursorQueryItem.getColumnIndex(Todo.LIST_TYPE))));
            }
            cursorQueryItem.close();
        }
        Collections.sort(arrayList, new scheduleOneDayComparator());
        return arrayList;
    }

    static class scheduleOneDayComparator implements Comparator<TodoEntity> {
        scheduleOneDayComparator() {
        }

        @Override // java.util.Comparator
        public int compare(TodoEntity todoEntity, TodoEntity todoEntity2) {
            if (todoEntity.getRemindType() != todoEntity2.getRemindType()) {
                return todoEntity.getRemindType() == 1 ? -1 : 1;
            }
            return todoEntity.getCreateTime() - todoEntity2.getCreateTime() > 0 ? -1 : 1;
        }
    }
}
