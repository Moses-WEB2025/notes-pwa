package com.miui.todo.feature.todolist;

/* JADX INFO: loaded from: classes4.dex */
public class SubTodoEditListBindContext {
    public static final int INVALID_CURSOR_INDEX = -1;
    private SubTodoEditAdapter mAdapter;
    private int mEditIndex = -1;
    private int mDoEditIndex = -1;
    private int mMergeIndex = -1;
    private int mSplitDataIndex = -1;
    private int mCursorIndexOnMergeToNormalTodo = -1;

    public SubTodoEditListBindContext(SubTodoEditAdapter subTodoEditAdapter) {
        this.mAdapter = subTodoEditAdapter;
    }

    public void setDoEditIndex(int i) {
        this.mDoEditIndex = i;
    }

    public int getDoEditIndex() {
        return this.mDoEditIndex;
    }

    public void setEditIndex(int i) {
        this.mEditIndex = i;
    }

    public int getEditIndex() {
        return this.mEditIndex;
    }

    public void setMergeIndex(int i) {
        this.mMergeIndex = i;
    }

    public void setCursorIndexOnMergeToNormalTodo(int i) {
        this.mCursorIndexOnMergeToNormalTodo = i;
    }

    public int getMergeIndex() {
        return this.mMergeIndex;
    }

    public void setSplitDataIndex(int i) {
        this.mSplitDataIndex = i;
    }

    public int getCursorIndexOnMergeToNormalTodo() {
        return this.mCursorIndexOnMergeToNormalTodo;
    }

    public int getSplitDataEditIndex() {
        return this.mSplitDataIndex;
    }

    public SubTodoEditAdapter getAdapter() {
        return this.mAdapter;
    }
}
