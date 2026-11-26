package com.miui.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/* JADX INFO: loaded from: classes2.dex */
public class ShieldEventLayout extends LinearLayout {
    private boolean isShieldEvent;

    public ShieldEventLayout(Context context) {
        super(context);
        this.isShieldEvent = false;
    }

    public ShieldEventLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isShieldEvent = false;
    }

    public void setShieldEvent(boolean z) {
        this.isShieldEvent = z;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.isShieldEvent) {
            return true;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }
}
