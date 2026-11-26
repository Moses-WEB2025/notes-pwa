package com.miui.floatwindow.feature.todo;

import com.miui.todo.base.OnDragItemListener;
import com.miui.todo.base.todolist.BaseListItemTouchHelperCallback;

/* JADX INFO: loaded from: classes2.dex */
public class FwTodoListItemTouchHelperCallback extends BaseListItemTouchHelperCallback {
    private FwTodoListAdapterBindContext mBindContext;

    public FwTodoListItemTouchHelperCallback(OnDragItemListener onDragItemListener, FwTodoListAdapterBindContext fwTodoListAdapterBindContext) {
        super(onDragItemListener);
        this.mBindContext = fwTodoListAdapterBindContext;
    }

    @Override // com.miui.todo.base.todolist.BaseListItemTouchHelperCallback, androidx.recyclerview.widget.ItemTouchHelper.Callback
    public boolean isLongPressDragEnabled() {
        return this.mBindContext.getEditId() == -1;
    }
}
