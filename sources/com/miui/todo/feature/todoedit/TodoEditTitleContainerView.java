package com.miui.todo.feature.todoedit;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/* JADX INFO: loaded from: classes4.dex */
public class TodoEditTitleContainerView extends RelativeLayout {
    private int mMaxHeight;

    public TodoEditTitleContainerView(Context context) {
        super(context);
        this.mMaxHeight = -1;
    }

    public TodoEditTitleContainerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMaxHeight = -1;
    }

    public TodoEditTitleContainerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mMaxHeight = -1;
    }

    public TodoEditTitleContainerView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mMaxHeight = -1;
    }

    @Override // android.widget.RelativeLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        if (this.mMaxHeight == -1) {
            super.onMeasure(i, i2);
            return;
        }
        View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        int i3 = this.mMaxHeight;
        if (size > i3) {
            super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(i3, Integer.MIN_VALUE));
        } else {
            super.onMeasure(i, i2);
        }
    }

    public void setMaxHeight(int i) {
        this.mMaxHeight = i;
    }
}
