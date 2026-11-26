package com.miui.pad.feature.todo.list;

import com.miui.pc.feature.todo.TwoColumnsTodoDataDepository;
import com.miui.todo.base.BaseSchedulerProvider;
import com.miui.todo.feature.todolist.TodoListContract;
import com.miui.todo.feature.todolist.TodoListPresenter;

/* JADX INFO: loaded from: classes4.dex */
public class PadTodoListPresenter extends TodoListPresenter {
    public PadTodoListPresenter(TodoListContract.View view, BaseSchedulerProvider baseSchedulerProvider) {
        super(view, baseSchedulerProvider);
    }

    @Override // com.miui.todo.feature.todolist.TodoListPresenter, com.miui.todo.base.todolist.BaseTodoListPresenter
    protected void initDataSource() {
        this.mDepository = TwoColumnsTodoDataDepository.getInstance();
        this.mDataSource = this.mDepository.getDataSource();
        this.mDataSource.getBrowseConfig().pShowGroup = false;
    }

    @Override // com.miui.todo.base.todolist.BaseTodoListPresenter, com.miui.todo.base.todolist.IBaseTodoListContract.Presenter
    /* JADX INFO: renamed from: isInEdit */
    public boolean getMIsInEdit() {
        return this.mIsInEdit;
    }
}
