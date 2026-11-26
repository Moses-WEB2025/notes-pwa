package com.miui.notes.component;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/* JADX INFO: loaded from: classes4.dex */
public class GestureFrameLayout extends FrameLayout {
    private GestureOperation mOperation;

    public interface GestureOperation {
        void computeScroll();

        void dispatchDraw(Canvas canvas);

        boolean onInterceptTouchEvent(MotionEvent motionEvent);

        boolean onTouchEvent(MotionEvent motionEvent);
    }

    public GestureFrameLayout(Context context) {
        super(context);
    }

    public GestureFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public GestureFrameLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setOperation(GestureOperation gestureOperation) {
        this.mOperation = gestureOperation;
    }

    @Override // android.view.View
    public boolean isOpaque() {
        return this.mOperation != null || super.isOpaque();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        GestureOperation gestureOperation = this.mOperation;
        if (gestureOperation != null) {
            gestureOperation.dispatchDraw(canvas);
        }
    }

    @Override // android.view.View
    public void computeScroll() {
        super.computeScroll();
        GestureOperation gestureOperation = this.mOperation;
        if (gestureOperation != null) {
            gestureOperation.computeScroll();
        }
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        GestureOperation gestureOperation = this.mOperation;
        if (gestureOperation != null) {
            return gestureOperation.onInterceptTouchEvent(motionEvent) || super.onInterceptTouchEvent(motionEvent);
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        GestureOperation gestureOperation = this.mOperation;
        if (gestureOperation != null) {
            return gestureOperation.onTouchEvent(motionEvent) || super.onTouchEvent(motionEvent);
        }
        return super.onTouchEvent(motionEvent);
    }
}
