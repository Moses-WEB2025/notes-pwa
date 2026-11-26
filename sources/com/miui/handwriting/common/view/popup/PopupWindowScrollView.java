package com.miui.handwriting.common.view.popup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/* JADX INFO: loaded from: classes3.dex */
public class PopupWindowScrollView extends ScrollView {
    private float mDownPositionX;
    private float mDownPositionY;

    public PopupWindowScrollView(Context context) {
        super(context);
        this.mDownPositionX = 0.0f;
        this.mDownPositionY = 0.0f;
    }

    public PopupWindowScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDownPositionX = 0.0f;
        this.mDownPositionY = 0.0f;
    }

    public PopupWindowScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDownPositionX = 0.0f;
        this.mDownPositionY = 0.0f;
    }

    @Override // android.widget.ScrollView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mDownPositionX = x;
            this.mDownPositionY = y;
        } else if (action == 2 && Math.abs(x - this.mDownPositionX) > Math.abs(y - this.mDownPositionY)) {
            return false;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }
}
