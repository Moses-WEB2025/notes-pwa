package com.miui.todo.base;

import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes4.dex */
public interface OnDragItemListener {
    void onDragEnd(RecyclerView.ViewHolder viewHolder, int i, boolean z);

    boolean onDragMove(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2);

    boolean onDragStart(RecyclerView.ViewHolder viewHolder, int i);
}
