package com.miui.floatwindow.feature.todo;

import androidx.recyclerview.widget.RecyclerView;
import com.miui.todo.base.BaseListAdapterBindContext;
import com.miui.todo.base.IEditTodoView;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.feature.todolist.WorkingTodo;

/* JADX INFO: loaded from: classes2.dex */
public class FwTodoListAdapterBindContext extends BaseListAdapterBindContext {
    private RecyclerView mRecyclerView;
    public int maxContentPanelWidth;
    private IEditTodoView mEditTodoView = null;
    private WorkingTodo mWorkingTodoData = null;
    private long mEditId = -1;

    public void refreshEditView(IEditTodoView iEditTodoView) {
        this.mEditTodoView = iEditTodoView;
    }

    public void bindWorkingTodo(WorkingTodo workingTodo) {
        this.mWorkingTodoData = workingTodo;
    }

    public WorkingTodo getWorkingTodo() {
        return this.mWorkingTodoData;
    }

    public IEditTodoView getEditView() {
        return this.mEditTodoView;
    }

    public void setEditContent(TodoEntity todoEntity, long j) {
        this.mEditId = j;
    }

    public long getEditId() {
        return this.mEditId;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    @Override // com.miui.todo.base.BaseListAdapterBindContext
    public RecyclerView getRecyclerView() {
        return this.mRecyclerView;
    }
}
