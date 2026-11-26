package com.miui.todo.feature.todolist;

import com.miui.todo.base.todolist.IBaseTodoListContract;
import com.miui.todo.data.bean.RemindTimeConfig;
import com.miui.todo.data.bean.TodoEntity;

/* JADX INFO: loaded from: classes4.dex */
public class TodoListContract {

    public interface Presenter extends IBaseTodoListContract.Presenter {
        void deleteTodos(long[] jArr);

        void enterAudioEditMode();

        void enterSearchMode();

        void exitSearchMode();

        android.view.View.OnClickListener getEditViewClickListener();

        TodoEditListener getTodoEditorListener();

        void hideTodo(TodoEntity todoEntity, int i, boolean z);

        boolean moveTodo(TodoEntity todoEntity, int i, int i2);

        void queryTodo(String str);

        void queryTodo(String str, int i);

        void refreshCacheData();

        void setEditTodoFromIntent(long j);

        void setShowListType(int i, boolean z);

        void setTodosRemindCustom(RemindTimeConfig remindTimeConfig, long[] jArr);
    }

    public interface View extends IBaseTodoListContract.View {
        void exitEdit(boolean z);

        boolean getIsNewTodo();

        void onDeleteTodos();

        void onMarkTodo(int i);

        void setIsNewTodo(boolean z);
    }
}
