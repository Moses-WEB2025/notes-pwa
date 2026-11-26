package com.miui.todo.data.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import com.miui.notes.R;
import com.miui.todo.data.Todo;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.utils.TodoUtils;

/* JADX INFO: loaded from: classes4.dex */
public class TodoDataUtils {
    private static final int AUDIO_FILE_FIELD_INDEX = 47;
    private static final int AUDIO_FILE_NAME_INDEX = 10;
    private static final int AUDIO_FILE_SIZA_INDEX = 52;
    private static final int CATEGORY_INDEX = 16;
    private static final int COLOR_LABEL_INDEX = 53;
    private static final int CREATE_TIMED_INDEX = 8;
    public static final int CUSTOM_SORT_ID_INDEX = 11;
    private static final int EXPIRE_TIME_INDEX = 3;
    private static final int FIRST_REMIND_TIME_INDEX = 48;
    private static final int FOLDER_ID_INDEX = 49;
    private static final int HIDE_TYPE_INDEX = 13;
    public static final int ID_CONTENT = 1;
    public static final int ID_INDEX = 0;
    private static final int INPUT_TYPE_INDEX = 9;
    private static final int IS_FINISH_INDEX = 5;
    private static final int LABEL_INDEX = 17;
    public static final int LAST_MODIFIED_TIME_INDEX = 7;
    private static final int LIST_TYPE_INDEX = 54;
    public static final int LOCAL_STATUS_INDEX = 50;
    private static final int MARK_FINISH_TIME_INDEX = 6;
    private static final int PLAIN_TEXT_INDEX = 12;
    private static final int PRIORITY_INDEX = 14;
    private static final int REMIND_REPEAT_INDEX = 15;
    private static final int REMIND_TIME_INDEX = 4;
    private static final int REMIND_TYPE_INDEX = 2;
    private static final int SOURCE_INDEX = 51;
    private static final int SYNC_ETAG_INDEX = 45;
    public static final int SYNC_ID_INDEX = 18;
    private static final int SYNC_STATUS_INDEX = 46;

    private TodoDataUtils() {
    }

    public static TodoEntity getTodoEntity(Cursor cursor) {
        return getTodoEntityWithId(cursor, cursor.getLong(0));
    }

    public static TodoEntity getTodoEntityWithId(Cursor cursor, long j) {
        return new TodoEntity(j, cursor.getString(1), cursor.getInt(2), cursor.getLong(4), cursor.getLong(3), cursor.getInt(5), cursor.getLong(6), cursor.getLong(7), cursor.getLong(8), cursor.getInt(9), cursor.getString(10), cursor.getLong(11), cursor.getString(12), cursor.getInt(13), cursor.getInt(15), cursor.getLong(18), cursor.getLong(45), cursor.getInt(46), cursor.getString(47), cursor.getLong(48), cursor.getInt(49), cursor.getInt(50), cursor.getInt(51), cursor.getLong(52), cursor.getInt(53), cursor.getInt(54));
    }

    public static ContentValues getContentValues(TodoEntity todoEntity, boolean z) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        ContentValues contentValues = new ContentValues();
        contentValues.put("content", todoEntity.getContent());
        contentValues.put(Todo.REMIND_TYPE, Integer.valueOf(todoEntity.getRemindType()));
        contentValues.put(Todo.REMIND_TIME, Long.valueOf(todoEntity.getRemindTime()));
        contentValues.put(Todo.EXPIRE_TIME, Long.valueOf(todoEntity.getExpireTime()));
        contentValues.put(Todo.IS_FINISH, Integer.valueOf(todoEntity.getIsFinish()));
        contentValues.put(Todo.MARK_FINISH_TIME, Long.valueOf(todoEntity.getMarkFinishTime()));
        contentValues.put(Todo.CREATE_TIME, Long.valueOf(todoEntity.getCreateTime()));
        if (z) {
            contentValues.put(Todo.LAST_MODIFIED_TIME, Long.valueOf(jCurrentTimeMillis));
        } else {
            contentValues.put(Todo.LAST_MODIFIED_TIME, Long.valueOf(todoEntity.getLastModifiedTime()));
        }
        contentValues.put(Todo.INPUT_TYPE, Integer.valueOf(todoEntity.getInputType()));
        contentValues.put(Todo.AUDIO_FILE_NAME, todoEntity.getAudioFileName());
        contentValues.put(Todo.CUSTOM_SORT_ID, Long.valueOf(todoEntity.getCustomSortId()));
        contentValues.put("plain_text", todoEntity.getPlainText());
        contentValues.put(Todo.HIDE_TYPE, Integer.valueOf(todoEntity.getHideType()));
        contentValues.put(Todo.REMIND_REPEAT_TYPE, Integer.valueOf(todoEntity.getRemindRepeatType()));
        contentValues.put("sync_id", Long.valueOf(todoEntity.getSyncId()));
        contentValues.put(Todo.SYNC_ETAG, Long.valueOf(todoEntity.getSyncEtag()));
        contentValues.put(Todo.SERVER_STATUS, Integer.valueOf(todoEntity.getServerStatus()));
        contentValues.put(Todo.AUDIO_FILE_FIELD, todoEntity.getAudioFileField());
        contentValues.put(Todo.FIRST_REMIND_TIME, Long.valueOf(todoEntity.getFirstRemindTime()));
        contentValues.put("folder_id", Integer.valueOf(todoEntity.getFolderId()));
        contentValues.put("local_status", Integer.valueOf(todoEntity.getLocalStatus()));
        contentValues.put("source", Integer.valueOf(todoEntity.getSource()));
        contentValues.put(Todo.AUDIO_FILE_SIZE, Long.valueOf(todoEntity.getAudioFileSize()));
        contentValues.put(Todo.COLOR_LABEL, Integer.valueOf(todoEntity.getColorLabel()));
        contentValues.put(Todo.LIST_TYPE, Integer.valueOf(todoEntity.getListType()));
        return contentValues;
    }

    public static TodoEntity getAlertTodoInfo(Context context, long j) {
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse(Todo.URI_TODO_OUTER), null, "id=" + j, null, null);
        if (cursorQuery != null) {
            try {
                if (cursorQuery.moveToNext()) {
                    TodoEntity todoEntity = getTodoEntity(cursorQuery);
                    if (todoEntity.getIsFinish() == 1) {
                        return null;
                    }
                    if (TextUtils.isEmpty(todoEntity.getPlainText())) {
                        todoEntity.setPlainText(TodoUtils.getString(R.string.todo_audio_default_content));
                    }
                    return todoEntity;
                }
            } finally {
                cursorQuery.close();
            }
        }
        return null;
    }
}
