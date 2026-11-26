package com.miui.todo.base;

import androidx.recyclerview.widget.RecyclerView;
import com.miui.todo.base.todolist.BaseTodoListAdapter;

/* JADX INFO: loaded from: classes4.dex */
public class BaseListAdapterBindContext {
    private EditableRecyclerViewWrapper<?> mEditableRecyclerViewWrapper;
    private String mSearchToken;
    private BaseTodoListAdapter mTodoListAdapter;
    private int mSearchType = 0;
    private int mMarkIndex = -1;
    private int mLastMarkIndex = -1;
    private long mLastFinishId = -1;
    public Long pLastClickCheckTime = null;
    public int pLastClickCheckType = 0;
    private boolean mNeedLayoutToShort = false;

    public void setEditableRecyclerViewWrapper(EditableRecyclerViewWrapper<?> editableRecyclerViewWrapper) {
        this.mEditableRecyclerViewWrapper = editableRecyclerViewWrapper;
    }

    public String getSearchToken() {
        return this.mSearchToken;
    }

    public void setSearchToken(String str) {
        this.mSearchToken = str;
    }

    public void setSearchType(int i) {
        this.mSearchType = i;
    }

    public int getSearchType() {
        return this.mSearchType;
    }

    public void setMarkIndex(int i) {
        this.mMarkIndex = i;
    }

    public int getMarkIndex() {
        return this.mMarkIndex;
    }

    public void setLastMarkIndex(int i) {
        this.mLastMarkIndex = i;
    }

    public int getLastMarkIndex() {
        return this.mLastMarkIndex;
    }

    public void setLastFinishId(long j) {
        this.mLastFinishId = j;
    }

    public long getLastFinishId() {
        return this.mLastFinishId;
    }

    public boolean isItemIdChecked(long j) {
        EditableRecyclerViewWrapper<?> editableRecyclerViewWrapper = this.mEditableRecyclerViewWrapper;
        return editableRecyclerViewWrapper != null && editableRecyclerViewWrapper.isItemIdChecked(j);
    }

    public boolean isInActionMode() {
        EditableRecyclerViewWrapper<?> editableRecyclerViewWrapper = this.mEditableRecyclerViewWrapper;
        return editableRecyclerViewWrapper != null && editableRecyclerViewWrapper.isInActionMode();
    }

    public void setActionModeMenuEnable(boolean z) {
        EditableRecyclerViewWrapper<?> editableRecyclerViewWrapper = this.mEditableRecyclerViewWrapper;
        if (editableRecyclerViewWrapper != null) {
            editableRecyclerViewWrapper.setActionModeMenuEnable(z);
        }
    }

    public void setListAdapter(BaseTodoListAdapter baseTodoListAdapter) {
        this.mTodoListAdapter = baseTodoListAdapter;
    }

    public BaseTodoListAdapter getTodoListAdapter() {
        return this.mTodoListAdapter;
    }

    public RecyclerView getRecyclerView() {
        return this.mEditableRecyclerViewWrapper.getView();
    }

    public void setNeedLayoutToShort(boolean z) {
        this.mNeedLayoutToShort = z;
    }

    public boolean isNeedLayoutToShort() {
        return this.mNeedLayoutToShort;
    }
}
