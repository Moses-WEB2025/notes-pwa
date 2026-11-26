package com.miui.todo.data.provider;

import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import com.miui.notes.NoteApp;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.tool.SyncUtils;
import com.miui.todo.cloudservice.LogUtil;
import com.miui.todo.data.bean.TodoBaseEntity;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.data.mode.SubModeImpl;
import com.miui.todo.data.utils.DragSort;
import com.miui.todo.data.utils.TimeUtils;

/* JADX INFO: loaded from: classes4.dex */
public class TodoDao {
    private static final String TAG = "TodoDao";

    public static long addItem(TodoBaseEntity todoBaseEntity) {
        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setContent(todoBaseEntity.getContent());
        todoEntity.setRemindType(todoBaseEntity.getRemindType());
        todoEntity.setRemindTime(todoBaseEntity.getRemindTime());
        todoEntity.setFirstRemindTime(todoBaseEntity.getFirstRemindTime());
        if (todoBaseEntity.getRemindType() == 1) {
            todoEntity.setExpireTime(TimeUtils.getDateEndMillis(todoBaseEntity.getRemindTime()));
            todoEntity.setRemindTime(TimeUtils.getDateRemindMillis(todoBaseEntity.getRemindTime()));
        } else if (todoBaseEntity.getRemindType() == 2) {
            todoEntity.setExpireTime(todoBaseEntity.getRemindTime());
        } else if (todoBaseEntity.getRemindType() == 0) {
            todoEntity.setExpireTime(0L);
            todoEntity.setRemindTime(0L);
        }
        todoEntity.setInputType(todoBaseEntity.getInputType());
        if (todoBaseEntity.getInputType() == 0) {
            todoEntity.setAudioFileName("");
        } else {
            todoEntity.setAudioFileName(todoBaseEntity.getAudioFileName());
        }
        if (todoBaseEntity.getListType() == 0) {
            todoEntity.setPlainText(todoBaseEntity.getPlainText());
        } else {
            todoEntity.setPlainText(new SubModeImpl(todoBaseEntity.getContent()).getPlainTextForSearch());
        }
        todoEntity.setRemindRepeatType(todoBaseEntity.getRemindRepeatType());
        long jCurrentTimeMillis = System.currentTimeMillis();
        todoEntity.setIsFinish(0);
        todoEntity.setMarkFinishTime(0L);
        todoEntity.setCreateTime(jCurrentTimeMillis);
        todoEntity.setLastModifiedTime(jCurrentTimeMillis);
        todoEntity.setCustomSortId(DragSort.getBigSortId());
        todoEntity.setHideType(0);
        todoEntity.setSyncId(0L);
        todoEntity.setSyncEtag(0L);
        todoEntity.setServerStatus(0);
        todoEntity.setAudioFileField("");
        todoEntity.setFolderId(0);
        todoEntity.setLocalStatus(0);
        todoEntity.setSource(0);
        todoEntity.setAudioFileSize(0L);
        todoEntity.setColorLabel(todoBaseEntity.getColorLabel());
        todoEntity.setListType(todoBaseEntity.getListType());
        LogUtil.d("CloudDebug6", "TodoDao addItem " + todoEntity.getPlainText());
        return TodoProviderAccessUtils.addItem(todoEntity);
    }

    public static int RealdeleteItem(long j) {
        if (queryItemEntity(j) == null) {
            return 0;
        }
        return TodoProviderAccessUtils.deleteItem(j);
    }

    public static int deleteItem(long j) {
        TodoEntity todoEntityQueryItemEntity = queryItemEntity(j);
        if (todoEntityQueryItemEntity == null) {
            return 0;
        }
        todoEntityQueryItemEntity.setLocalStatus(3);
        return TodoProviderAccessUtils.updateItem(todoEntityQueryItemEntity);
    }

    public static int hideItem(long j, boolean z) {
        TodoEntity todoEntityQueryItemEntity = queryItemEntity(j);
        if (todoEntityQueryItemEntity == null) {
            return 0;
        }
        if (z) {
            todoEntityQueryItemEntity.setHideType(1);
        } else {
            todoEntityQueryItemEntity.setHideType(0);
        }
        return TodoProviderAccessUtils.updateItem(todoEntityQueryItemEntity);
    }

    public static TodoEntity queryItemEntity(long j) {
        return TodoProviderAccessUtils.queryItemEntity(j);
    }

    public static Cursor queryAllItem() {
        return TodoProviderAccessUtils.queryAllItem();
    }

    public static int updateItem(long j, TodoEntity todoEntity) {
        TodoEntity todoEntityQueryItemEntity = queryItemEntity(j);
        if (todoEntityQueryItemEntity == null) {
            Logger.INSTANCE.e(TAG, "updateItem originEntity is null! ");
            return -1;
        }
        if (todoEntity == null) {
            Logger.INSTANCE.e(TAG, "updateItem newEntity is null! ");
            return -1;
        }
        Logger.INSTANCE.i(TAG, " originEntity.getLocalStatus()" + todoEntityQueryItemEntity.getLocalStatus());
        if (todoEntityQueryItemEntity.getSyncId() != 0) {
            Logger.INSTANCE.e(TAG, " has synced during edit，so update" + todoEntityQueryItemEntity.getSyncId());
            todoEntity.setLocalStatus(2);
            todoEntity.setSyncId(todoEntityQueryItemEntity.getSyncId());
            todoEntity.setSyncEtag(todoEntityQueryItemEntity.getSyncEtag());
        }
        if (todoEntityQueryItemEntity.getIsFinish() != todoEntity.getIsFinish() && todoEntityQueryItemEntity.getIsFinish() == 1) {
            todoEntity.setCustomSortId(DragSort.getBigSortId());
        }
        if (todoEntity.getListType() == 1) {
            todoEntity.setPlainText(new SubModeImpl(todoEntity.getContent()).getPlainTextForSearch());
        }
        return TodoProviderAccessUtils.updateItem(todoEntity);
    }

    public static int updateItemFinishStatus(long j, boolean z) {
        TodoEntity todoEntityQueryItemEntity = queryItemEntity(j);
        if (todoEntityQueryItemEntity != null) {
            if (z) {
                todoEntityQueryItemEntity.setMarkFinishTime(System.currentTimeMillis());
                todoEntityQueryItemEntity.setRemindRepeatType(0);
            }
            todoEntityQueryItemEntity.setIsFinish(z ? 1 : 0);
        }
        return updateItem(j, todoEntityQueryItemEntity);
    }

    public static void dragItem(long j, long j2, long j3) {
        TodoEntity todoEntityQueryItemEntity = queryItemEntity(j);
        TodoEntity todoEntityQueryItemEntity2 = j2 == -1 ? null : queryItemEntity(j2);
        TodoEntity todoEntityQueryItemEntity3 = j3 != -1 ? queryItemEntity(j3) : null;
        if (todoEntityQueryItemEntity == null) {
            return;
        }
        if (todoEntityQueryItemEntity2 == null && todoEntityQueryItemEntity3 == null) {
            return;
        }
        if (todoEntityQueryItemEntity2 == null) {
            long littleSortId = DragSort.getLittleSortId(todoEntityQueryItemEntity3.getCustomSortId());
            TodoEntity todoEntityQueryItemEntity4 = queryItemEntity(j3);
            if (littleSortId == -1 && todoEntityQueryItemEntity4 != null) {
                DragSort.UpdateAllUnFinished();
                littleSortId = DragSort.getLittleSortId(todoEntityQueryItemEntity4.getCustomSortId());
            }
            TodoDatabaseAccessUtils.UpdateCustomSortId(todoEntityQueryItemEntity.getId(), littleSortId);
            return;
        }
        if (todoEntityQueryItemEntity3 == null) {
            TodoDatabaseAccessUtils.UpdateCustomSortId(todoEntityQueryItemEntity.getId(), DragSort.getBigSortId());
            return;
        }
        long customSortId = todoEntityQueryItemEntity2.getCustomSortId();
        long customSortId2 = todoEntityQueryItemEntity3.getCustomSortId();
        if (customSortId >= customSortId2) {
            Logger.INSTANCE.e(TAG, "error params in dragItem ");
            return;
        }
        long middleSortId = DragSort.getMiddleSortId(customSortId, customSortId2);
        if (middleSortId == -1) {
            DragSort.UpdateAllUnFinished();
            middleSortId = DragSort.getMiddleSortId(queryItemEntity(j2).getCustomSortId(), queryItemEntity(j3).getCustomSortId());
        }
        TodoDatabaseAccessUtils.UpdateCustomSortId(todoEntityQueryItemEntity.getId(), middleSortId);
    }

    public static void deleteBatch(String[] strArr) {
        TodoDatabaseAccessUtils.deleteBatch(strArr);
    }

    public static void localDeleteBatch(String[] strArr) {
        TodoDatabaseAccessUtils.localDeleteBatch(strArr);
        NoteApp.getInstance().getContentResolver().notifyChange(Uri.parse(TodoProvider.URI_TODO_PRIVATE), (ContentObserver) null, false);
        Bundle bundle = new Bundle();
        bundle.putInt("push_type", 2);
        SyncUtils.requestSync(NoteApp.getInstance(), bundle, false);
    }

    public static void setBatchTime(String[] strArr, int i, long j, long j2) {
        TodoDatabaseAccessUtils.setBatchTime(strArr, i, j, j2);
    }
}
