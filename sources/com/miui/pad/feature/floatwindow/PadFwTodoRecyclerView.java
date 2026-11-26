package com.miui.pad.feature.floatwindow;

import android.content.Context;
import android.util.AttributeSet;
import com.miui.common.tool.UIUtils;
import com.miui.floatwindow.feature.todo.FwTodoRecyclerView;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes4.dex */
public class PadFwTodoRecyclerView extends FwTodoRecyclerView {
    public PadFwTodoRecyclerView(Context context) {
        super(context);
    }

    public PadFwTodoRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PadFwTodoRecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.miui.floatwindow.feature.todo.FwTodoRecyclerView
    protected void updateMaxHeight() {
        this.mMaxHeight = UIUtils.getRealHeight(getContext());
        this.mMaxHeight = ((this.mMaxHeight - (getResources().getDimensionPixelSize(R.dimen.pad_global_window_margin_top) * 2)) - (getResources().getDimensionPixelOffset(R.dimen.float_todo_recycler_view_margin_top) + getResources().getDimensionPixelOffset(R.dimen.float_todo_add_icon_size))) - getResources().getDimensionPixelOffset(R.dimen.float_todo_recycler_view_margin_top);
    }
}
