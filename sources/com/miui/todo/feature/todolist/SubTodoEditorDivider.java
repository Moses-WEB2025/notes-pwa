package com.miui.todo.feature.todolist;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes4.dex */
public class SubTodoEditorDivider extends RecyclerView.ItemDecoration {
    private int mBottom;
    private int mTop;

    public SubTodoEditorDivider(int i, int i2) {
        this.mTop = i;
        this.mBottom = i2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        super.getItemOffsets(rect, view, recyclerView, state);
        rect.top = this.mTop;
        rect.bottom = this.mBottom;
    }
}
