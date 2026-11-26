package com.miui.todo.data.provider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import com.miui.notes.NoteApp;
import com.miui.notes.base.utils.Logger;
import com.miui.todo.data.Todo;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.data.utils.TodoDataUtils;

/* JADX INFO: loaded from: classes4.dex */
public class TodoProviderAccessUtils {
    public static long addItem(TodoEntity todoEntity) {
        return addItem(todoEntity, false);
    }

    public static long addItem(TodoEntity todoEntity, boolean z) {
        return addItem(TodoDataUtils.getContentValues(todoEntity, true), z, false);
    }

    public static long addItem(ContentValues contentValues, boolean z, boolean z2) {
        Uri uriInsert;
        Logger.INSTANCE.d("TodoProviderAccessUtils", "addItem isFromSync," + z + ",isFromSync " + z2);
        if (z) {
            if (z2) {
                contentValues.put("local_status", (Integer) 4);
            } else {
                contentValues.put("local_status", (Integer) 1);
            }
            uriInsert = NoteApp.getInstance().getContentResolver().insert(Uri.parse(TodoProvider.URI_TODO_PRIVATE_SYNC), contentValues);
        } else {
            uriInsert = NoteApp.getInstance().getContentResolver().insert(Uri.parse(TodoProvider.URI_TODO_PRIVATE), contentValues);
        }
        if (uriInsert == null) {
            return -1L;
        }
        return Long.parseLong(uriInsert.getPathSegments().get(1));
    }

    public static int deleteItem(long j) {
        return deleteItem(j, false);
    }

    public static int deleteItem(long j, boolean z) {
        Uri uriWithAppendedId;
        if (z) {
            uriWithAppendedId = ContentUris.withAppendedId(Uri.parse(TodoProvider.URI_TODO_PRIVATE_SYNC), j);
        } else {
            uriWithAppendedId = ContentUris.withAppendedId(Uri.parse(TodoProvider.URI_TODO_PRIVATE), j);
        }
        return NoteApp.getInstance().getContentResolver().delete(uriWithAppendedId, null, null);
    }

    public static int updateItem(TodoEntity todoEntity) {
        return updateItem(todoEntity, false);
    }

    public static int updateItem(TodoEntity todoEntity, boolean z) {
        return updateItem(todoEntity.getId(), TodoDataUtils.getContentValues(todoEntity, true), z);
    }

    public static int updateItem(long j, ContentValues contentValues, boolean z) {
        Uri uriWithAppendedId;
        if (z) {
            uriWithAppendedId = ContentUris.withAppendedId(Uri.parse(TodoProvider.URI_TODO_PRIVATE_SYNC), j);
        } else {
            uriWithAppendedId = ContentUris.withAppendedId(Uri.parse(TodoProvider.URI_TODO_PRIVATE), j);
        }
        return NoteApp.getInstance().getContentResolver().update(uriWithAppendedId, contentValues, null, null);
    }

    private static Cursor queryItemCursor(long j) {
        return NoteApp.getInstance().getContentResolver().query(ContentUris.withAppendedId(Uri.parse(TodoProvider.URI_TODO_PRIVATE), j), null, null, null, null);
    }

    public static TodoEntity queryItemEntity(long j) {
        Cursor cursorQueryItemCursor = queryItemCursor(j);
        if (cursorQueryItemCursor == null) {
            return null;
        }
        if (cursorQueryItemCursor.moveToNext()) {
            TodoEntity todoEntityWithId = TodoDataUtils.getTodoEntityWithId(cursorQueryItemCursor, j);
            cursorQueryItemCursor.close();
            return todoEntityWithId;
        }
        cursorQueryItemCursor.close();
        return null;
    }

    public static Cursor queryAllItem() {
        return NoteApp.getInstance().getContentResolver().query(Uri.parse(TodoProvider.URI_TODO_PRIVATE), null, null, null, null);
    }

    public static Cursor queryAllRealFinishItem() {
        return NoteApp.getInstance().getContentResolver().query(Uri.parse(TodoProvider.URI_TODO_PRIVATE), null, "is_finish = 1 AND local_status != 3 AND local_status != 4", null, null);
    }

    public static Cursor queryAllRealUnFinishItem() {
        return NoteApp.getInstance().getContentResolver().query(Uri.parse(TodoProvider.URI_TODO_PRIVATE), null, "is_finish = 0 AND local_status != 3 AND local_status != 4", null, null);
    }

    public static void deleteAllItem() {
        NoteApp.getInstance().getContentResolver().delete(Uri.parse(TodoProvider.URI_TODO_PRIVATE), null, null);
    }

    public static Cursor queryNeedUploadAccessory() {
        return NoteApp.getInstance().getContentResolver().query(Uri.parse(TodoProvider.URI_TODO_PRIVATE), null, "audo_file_field == '' AND audio_file_name != ''", null, null);
    }

    public static Cursor queryAllUnFinishItem() {
        return NoteApp.getInstance().getContentResolver().query(Uri.parse(TodoProvider.URI_TODO_PRIVATE), null, "is_finish = 0", null, Todo.CUSTOM_SORT_ID);
    }

    public static Cursor queryAllUnFinishItemReverse() {
        return NoteApp.getInstance().getContentResolver().query(Uri.parse(TodoProvider.URI_TODO_PRIVATE), null, "is_finish = 0", null, "custom_sort_id DESC");
    }

    public static Cursor queryAllFinishItemReverse() {
        return NoteApp.getInstance().getContentResolver().query(Uri.parse(TodoProvider.URI_TODO_PRIVATE), null, "is_finish = 1", null, "mark_finish_time DESC");
    }

    public static Cursor queryNeedUpdateItem() {
        return NoteApp.getInstance().getContentResolver().query(Uri.parse(TodoProvider.URI_TODO_PRIVATE), null, "local_status = 0 OR local_status = 2 OR local_status = 3", null, null);
    }

    public static Cursor queryNeedShowItem() {
        return NoteApp.getInstance().getContentResolver().query(Uri.parse(TodoProvider.URI_TODO_PRIVATE), null, "local_status = 0 OR local_status = 2", null, null);
    }

    public static Cursor queryContentItem(String str) {
        return NoteApp.getInstance().getContentResolver().query(Uri.parse(Todo.URI_TODO_OUTER), null, "content =? ", new String[]{str}, Todo.CREATE_TIME);
    }

    public static Cursor queryPlainTextItem(String str) {
        return NoteApp.getInstance().getContentResolver().query(Uri.parse(Todo.URI_TODO_OUTER), null, "plain_text =? ", new String[]{str}, Todo.CREATE_TIME);
    }

    public static int clearCloudItemSync() {
        return NoteApp.getInstance().getContentResolver().delete(Uri.parse(TodoProvider.URI_TODO_PRIVATE_SYNC), "local_status = 1 OR local_status = 4", null);
    }

    public static void clearItemSyncInfo() {
        NoteApp.getInstance().getContentResolver().delete(Uri.parse(TodoProvider.URI_TODO_PRIVATE_SYNC), "local_status = 3 OR local_status = 4", null);
        TodoDatabaseAccessUtils.UpdateLocalAllNew();
    }

    public static int clearItemWithoutContentSync() {
        try {
            return NoteApp.getInstance().getContentResolver().delete(Uri.parse(TodoProvider.URI_TODO_PRIVATE_SYNC), "Todo.CONTENT is null", null);
        } catch (Exception e) {
            Logger.INSTANCE.i("TodoProviderAccessUtils", "error in clearItemWithoutContentSync " + e);
            return 0;
        }
    }

    public static int clearItemWithJustSort() {
        return NoteApp.getInstance().getContentResolver().delete(Uri.parse(TodoProvider.URI_TODO_PRIVATE_SYNC), "local_status = 4", null);
    }

    public static Cursor queryItemBySyncId(long j) {
        return NoteApp.getInstance().getContentResolver().query(Uri.parse(TodoProvider.URI_TODO_PRIVATE), null, "sync_id = " + j, null, null);
    }

    public static Cursor queryAllItemsSyncIdAndId() {
        return NoteApp.getInstance().getContentResolver().query(Uri.parse(TodoProvider.URI_TODO_PRIVATE), new String[]{"id", "sync_id"}, null, null, null);
    }

    public static Cursor queryItemByCreateTime(long j) {
        return NoteApp.getInstance().getContentResolver().query(Uri.parse(TodoProvider.URI_TODO_PRIVATE), null, "create_time = " + j, null, null);
    }
}
