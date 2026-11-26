package com.miui.todo.feature.todolist;

import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes4.dex */
public class SubTodoListBindContext {
    private SubTodoListAdapter mAdapter;
    private int mEditIndex = -1;
    private RecyclerView mRecyclerView;
    private String mSearchToken;

    public SubTodoListBindContext(RecyclerView recyclerView, SubTodoListAdapter subTodoListAdapter) {
        this.mRecyclerView = recyclerView;
        this.mAdapter = subTodoListAdapter;
    }

    public SubTodoListBindContext(RecyclerView recyclerView, SubTodoListAdapter subTodoListAdapter, String str) {
        this.mRecyclerView = recyclerView;
        this.mAdapter = subTodoListAdapter;
        this.mSearchToken = str;
    }

    public SubTodoListAdapter getAdapter() {
        return this.mAdapter;
    }

    public RecyclerView getBindView() {
        return this.mRecyclerView;
    }

    public void setSearchToken(String str) {
        this.mSearchToken = str;
    }

    public String getSearchToken() {
        return this.mSearchToken;
    }
}
