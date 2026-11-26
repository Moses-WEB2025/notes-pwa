package com.miui.todo.base;

import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes4.dex */
public interface OnDragItemStateListener {
    void onCancelDrag(RecyclerView.ViewHolder viewHolder);

    void onEndDrag(RecyclerView.ViewHolder viewHolder);

    void onStartDrag(RecyclerView.ViewHolder viewHolder);
}
