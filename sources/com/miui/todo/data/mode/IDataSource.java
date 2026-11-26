package com.miui.todo.data.mode;

import com.miui.todo.data.bean.TodoBaseEntity;
import com.miui.todo.data.bean.TodoEntity;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public interface IDataSource {
    TodoEntity addItem(TodoBaseEntity todoBaseEntity);

    TodoEntity addItemToDb(TodoBaseEntity todoBaseEntity);

    void afterDragItem(int i, int i2, boolean z);

    void cancelHideItem(TodoEntity todoEntity, int i);

    boolean changeGroupExpand(int i, boolean z, boolean z2);

    void deleteBatch(int[] iArr);

    void deleteItem(TodoEntity todoEntity, int i);

    void dragItem(TodoEntity todoEntity, TodoEntity todoEntity2, TodoEntity todoEntity3, int i);

    void dragItemCache(TodoEntity todoEntity, TodoEntity todoEntity2, TodoEntity todoEntity3, int i);

    void dragItemDb(TodoEntity todoEntity, TodoEntity todoEntity2, TodoEntity todoEntity3);

    void executePendingTask();

    DataBrowseConfig getBrowseConfig();

    int getCategoryItemCount();

    int getChangeCountAfterFinish(int i);

    TodoEntity getChildItem(int i);

    TodoEntity getChildItem(DataGroupInfo dataGroupInfo);

    DataGroupInfo getDataGroupInfo(int i);

    int getFinishItemCount();

    int getItemCount();

    int getItemGroupType(int i);

    List<TodoEntity> getList(int i);

    int getListRealItemCount();

    int getRealFinishItemCount();

    int getRealType(long j);

    int getTotalIndexByItem(TodoEntity todoEntity);

    int getTotalItemCount();

    void hideItem(TodoEntity todoEntity, int i);

    void refreshData();

    void setBatchTime(int[] iArr, int i, long j);

    void setShowFinished(boolean z);

    void setTimeItem(TodoEntity todoEntity, int i);

    void swapItemCache(int i, int i2);

    void unFinishItem(TodoEntity todoEntity, int i);

    void updateItem(TodoEntity todoEntity, int i);

    void updateNotChangeFinish(TodoEntity todoEntity, int i);
}
