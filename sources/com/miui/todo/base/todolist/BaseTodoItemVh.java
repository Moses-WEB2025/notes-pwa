package com.miui.todo.base.todolist;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.notes.ui.widget.ViewHolderEditableCallback;
import com.miui.notes.ui.widget.ViewHolderRecycledCallback;
import com.miui.todo.base.BaseListAdapterBindContext;
import com.miui.todo.base.OnDragItemStateListener;
import com.miui.todo.base.OnSwipeItemListener;
import com.miui.todo.feature.todolist.TodoItemOperationListener;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BaseTodoItemVh extends RecyclerView.ViewHolder implements ViewHolderEditableCallback, ViewHolderRecycledCallback {
    private BaseListAdapterBindContext bindContext;
    protected OnDragItemStateListener mDragListener;
    protected TodoItemOperationListener mItemOperationListener;
    protected View.OnClickListener mOtherViewClickListener;
    protected OnSwipeItemListener mSwipeListener;

    public void onAttached(BaseListAdapterBindContext baseListAdapterBindContext) {
    }

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
    }

    public BaseTodoItemVh(View view) {
        super(view);
    }

    public void setOtherViewClickListener(View.OnClickListener onClickListener) {
        this.mOtherViewClickListener = onClickListener;
    }

    public void setOnSwipeItemListener(OnSwipeItemListener onSwipeItemListener) {
        this.mSwipeListener = onSwipeItemListener;
    }

    public void setOnDragItemListener(OnDragItemStateListener onDragItemStateListener) {
        this.mDragListener = onDragItemStateListener;
    }

    public void setItemOperationListener(TodoItemOperationListener todoItemOperationListener) {
        this.mItemOperationListener = todoItemOperationListener;
    }

    public void onDettached(BaseListAdapterBindContext baseListAdapterBindContext) {
        this.bindContext = baseListAdapterBindContext;
    }
}
