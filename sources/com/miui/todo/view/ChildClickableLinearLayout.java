package com.miui.todo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/* JADX INFO: loaded from: classes4.dex */
public class ChildClickableLinearLayout extends LinearLayout {
    private boolean mIsChildClickable;

    public ChildClickableLinearLayout(Context context) {
        super(context);
        this.mIsChildClickable = true;
    }

    public ChildClickableLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsChildClickable = true;
    }

    public ChildClickableLinearLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsChildClickable = true;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return !this.mIsChildClickable;
    }

    public void setChildClickable(boolean z) {
        this.mIsChildClickable = z;
    }
}
