package com.miui.pc.feature.todo;

import com.miui.todo.base.BaseSchedulerProvider;
import com.miui.todo.feature.todolist.TodoListContract;
import com.miui.todo.feature.todolist.TodoListPresenter;

/* JADX INFO: loaded from: classes4.dex */
public class PcTodoListPresenter extends TodoListPresenter {
    public PcTodoListPresenter(TodoListContract.View view, BaseSchedulerProvider baseSchedulerProvider) {
        super(view, baseSchedulerProvider);
        this.mDataSource = TwoColumnsTodoDataDepository.getInstance().getDataSource();
        this.mDataSource.getBrowseConfig().pShowGroup = false;
    }

    @Override // com.miui.todo.base.todolist.BaseTodoListPresenter, com.miui.todo.base.todolist.IBaseTodoListContract.Presenter
    /* JADX INFO: renamed from: isInEdit */
    public boolean getMIsInEdit() {
        return this.mIsInEdit;
    }
}
