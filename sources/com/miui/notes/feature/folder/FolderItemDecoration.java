package com.miui.notes.feature.folder;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes4.dex */
public class FolderItemDecoration extends RecyclerView.ItemDecoration {
    private int lastSpace;
    private int space;

    public FolderItemDecoration(int i, int i2) {
        this.space = i;
        this.lastSpace = i2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        rect.left = 0;
        rect.right = 0;
        if (recyclerView != null && recyclerView.getAdapter() != null && recyclerView.getChildAdapterPosition(view) == recyclerView.getAdapter().getItemCount() - 1) {
            rect.bottom = this.lastSpace;
        } else {
            rect.bottom = this.space;
        }
        if (recyclerView != null && recyclerView.getAdapter() != null && recyclerView.getChildAdapterPosition(view) == 0) {
            rect.top = this.lastSpace;
        } else {
            rect.top = 0;
        }
    }
}
