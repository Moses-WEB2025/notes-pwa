package com.miui.pc.feature.todo;

import androidx.recyclerview.widget.RecyclerView;
import com.miui.todo.base.BaseListAdapterBindContext;

/* JADX INFO: loaded from: classes4.dex */
public class PcTodoListAdapterBindContext extends BaseListAdapterBindContext {
    private EditableTodoListWrapper<?> mEditableRecyclerViewWrapper;

    @Override // com.miui.todo.base.BaseListAdapterBindContext
    public void setActionModeMenuEnable(boolean z) {
    }

    public void setEditableRecyclerViewWrapper(EditableTodoListWrapper<?> editableTodoListWrapper) {
        this.mEditableRecyclerViewWrapper = editableTodoListWrapper;
    }

    @Override // com.miui.todo.base.BaseListAdapterBindContext
    public boolean isItemIdChecked(long j) {
        EditableTodoListWrapper<?> editableTodoListWrapper = this.mEditableRecyclerViewWrapper;
        return editableTodoListWrapper != null && editableTodoListWrapper.isItemIdChecked(j);
    }

    @Override // com.miui.todo.base.BaseListAdapterBindContext
    public boolean isInActionMode() {
        EditableTodoListWrapper<?> editableTodoListWrapper = this.mEditableRecyclerViewWrapper;
        return editableTodoListWrapper != null && editableTodoListWrapper.isInActionMode();
    }

    @Override // com.miui.todo.base.BaseListAdapterBindContext
    public RecyclerView getRecyclerView() {
        return this.mEditableRecyclerViewWrapper.getView();
    }
}
