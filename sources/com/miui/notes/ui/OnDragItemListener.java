package com.miui.notes.ui;

import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes4.dex */
public interface OnDragItemListener {
    void onDragEnd(RecyclerView.ViewHolder viewHolder, int i, boolean z);

    void onDragMerge(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2);

    boolean onDragMove(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2);

    void onDragMoveStart(RecyclerView.ViewHolder viewHolder, int i);

    boolean onDragStart(RecyclerView.ViewHolder viewHolder, int i);
}
