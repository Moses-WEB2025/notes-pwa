package com.miui.todo.base.todolist;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.todo.base.IBaseContract;
import com.miui.todo.data.bean.RemindTimeConfig;
import com.miui.todo.data.bean.TodoBaseEntity;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.feature.todolist.SubTodoListListener;
import com.miui.todo.feature.todolist.WorkingTodo;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public interface IBaseTodoListContract {

    public interface Presenter extends IBaseContract.Presenter {
        void cancelRemindCustom();

        void deleteTodo(TodoEntity todoEntity, int i);

        void enterEditMode(TodoEntity todoEntity, int i, boolean z, boolean z2);

        void executePendingDataTask();

        void exitEditMode(boolean z, boolean z2);

        void getAndUpdateDataToView();

        SubTodoListListener getSubTodoListListener();

        WorkingTodo getWorkingTodo();

        boolean isInEdit();

        void setRemindCustom();

        void setRemindTimeInDragMode(RemindTimeConfig remindTimeConfig);

        void setRemindTimeInEditMode(RemindTimeConfig remindTimeConfig);

        void setTodoFinishState(TodoEntity todoEntity, int i, int i2);

        void updateTodo(TodoEntity todoEntity, int i, boolean z);
    }

    public interface View extends IBaseContract.View<Presenter> {
        void doAllFinishAnim(TodoEntity todoEntity, int i, android.view.View view);

        void doFinishAnim(TodoEntity todoEntity, int i, android.view.View view);

        void finishTodoInData(TodoEntity todoEntity, int i);

        BaseTodoListAdapter getListAdapter();

        RecyclerView getListView();

        Context getViewContext();

        void onAddNewTodo(boolean z);

        void onDataEmpty(boolean z);

        void onDataLoaded();

        void onDeleteTodo();

        void onEnterEditMode(TodoBaseEntity todoBaseEntity, int i, boolean z, boolean z2);

        void onExitEditMode(int i, boolean z);

        void onNewTodoAdded(TodoEntity todoEntity, int i, boolean z);

        void onShowSearchResult(List<TodoEntity> list);

        void onUpdateTodo(int i, boolean z);

        void onUpdateTodoData();

        void onUpdateTodoFinishState(boolean z, boolean z2, int i);

        void refreshTodoList();

        void showRemindTimePickerDialog(Long l, int i);

        void updateRemindCustomTag(boolean z, RemindTimeConfig remindTimeConfig);
    }
}
