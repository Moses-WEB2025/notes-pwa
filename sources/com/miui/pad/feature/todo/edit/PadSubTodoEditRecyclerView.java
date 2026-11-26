package com.miui.pad.feature.todo.edit;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import com.miui.notes.base.utils.Logger;
import miuix.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes4.dex */
public class PadSubTodoEditRecyclerView extends RecyclerView {
    public PadSubTodoEditRecyclerView(Context context) {
        super(context);
    }

    public PadSubTodoEditRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PadSubTodoEditRecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.ViewParent
    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
        Logger.INSTANCE.i("PadSubTodoEditRecyclerView", "rect " + rect.toString() + " child = " + view.toString());
        requestLayout();
        return super.requestChildRectangleOnScreen(view, rect, z);
    }
}
