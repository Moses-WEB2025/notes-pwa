package com.miui.floatwindow.feature.todo;

import android.database.ContentObserver;
import android.net.Uri;
import android.text.TextUtils;
import com.miui.todo.base.BaseSchedulerProvider;
import com.miui.todo.base.todolist.BaseTodoListPresenter;
import com.miui.todo.data.TodoDataDepository;
import com.miui.todo.data.bean.TodoBaseEntity;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.data.mode.CustomSortMode;
import com.miui.todo.data.provider.TodoProvider;
import com.miui.todo.feature.todolist.WorkingTodo;

/* JADX INFO: loaded from: classes2.dex */
public class FwTodoListPresenter extends BaseTodoListPresenter implements FwTodoListContract.Presenter {
    @Override // com.miui.todo.base.todolist.BaseTodoListPresenter, com.miui.todo.base.IBaseContract.Presenter
    public void destroy() {
    }

    public FwTodoListPresenter(FwTodoListContract.View view, BaseSchedulerProvider baseSchedulerProvider) {
        super(view, baseSchedulerProvider);
        this.mView.setPresenter(this);
    }

    public FwTodoListContract.View getView() {
        return (FwTodoListContract.View) this.mView;
    }

    @Override // com.miui.todo.base.todolist.BaseTodoListPresenter
    protected void initDataSource() {
        this.mDepository = TodoDataDepository.getInstance();
        this.mDataSource = this.mDepository.getDataSource();
    }

    @Override // com.miui.todo.base.todolist.BaseTodoListPresenter, com.miui.todo.base.IBaseContract.Presenter
    public void subscribe() {
        super.subscribe();
    }

    @Override // com.miui.todo.base.todolist.BaseTodoListPresenter, com.miui.todo.base.IBaseContract.Presenter
    public void unsubscribe() {
        if (this.mDisposables.isEmpty()) {
            return;
        }
        for (int i = 0; i < this.mDisposables.size(); i++) {
            if (!this.mDisposables.get(i).isDisposed()) {
                this.mDisposables.get(i).dispose();
            }
        }
        this.mDisposables.clear();
    }

    @Override // com.miui.floatwindow.feature.todo.FwTodoListContract.Presenter
    public void addNewTodo(boolean z) {
        if (this.mDataSource instanceof CustomSortMode) {
            if (!z) {
                this.mWorkingTodo = new WorkingTodo(z);
                TodoEntity todoEntity = new TodoEntity();
                todoEntity.setInputType(0);
                this.mDataSource.getList(0).add(0, todoEntity);
            }
            this.mView.onAddNewTodo(z);
        }
    }

    @Override // com.miui.todo.base.todolist.BaseTodoListPresenter
    protected void addTodoToDb(TodoBaseEntity todoBaseEntity, boolean z) {
        if (TextUtils.isEmpty(todoBaseEntity.getContent())) {
            this.mUnsavedTodo = null;
            removeTempData();
        } else {
            if (this.mDataSource.getList(0).size() > 0 && this.mDataSource.getList(0).get(0).getId() == 0) {
                this.mDataSource.getList(0).remove(0);
            }
            super.addTodoToDb(todoBaseEntity, z);
        }
    }

    @Override // com.miui.todo.base.todolist.BaseTodoListPresenter, com.miui.todo.base.todolist.IBaseTodoListContract.Presenter
    public void deleteTodo(TodoEntity todoEntity, int i) {
        this.mDataSource.deleteItem(todoEntity, getView().getListAdapter().getItemInfo(i).mSubPosition);
        getView().onDeleteTodo();
    }

    private void removeTempData() {
        if ((this.mDataSource instanceof CustomSortMode) && this.mDataSource.getList(0).size() > 0 && this.mDataSource.getList(0).get(0).getId() == 0) {
            this.mDataSource.getList(0).remove(0);
            this.mView.getViewContext().getContentResolver().notifyChange(Uri.parse(TodoProvider.URI_TODO_PRIVATE), (ContentObserver) null, false);
        }
        this.mView.onNewTodoAdded(null, 0, false);
    }
}
