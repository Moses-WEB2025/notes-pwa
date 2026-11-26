package com.miui.notes.ui.view;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import miuix.recyclerview.widget.RecyclerView;
import miuix.springback.view.SpringBackLayout;

/* JADX INFO: loaded from: classes4.dex */
public class BaseNoteRecyclerView extends RecyclerView {
    private int mDownX;
    private int mDownY;
    protected int mScaleTouchSlop;
    private final Point touchPoint;

    public BaseNoteRecyclerView(Context context) {
        this(context, null);
    }

    public BaseNoteRecyclerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BaseNoteRecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.touchPoint = new Point();
        this.mScaleTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override // androidx.recyclerview.widget.SpringRecyclerView, androidx.recyclerview.widget.RemixRecyclerView, androidx.recyclerview.widget.RecyclerView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.touchPoint.set((int) motionEvent.getX(), (int) motionEvent.getY());
        return super.onTouchEvent(motionEvent);
    }

    @Override // androidx.recyclerview.widget.SpringRecyclerView, androidx.recyclerview.widget.RemixRecyclerView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        boolean zOnInterceptTouchEvent = super.onInterceptTouchEvent(motionEvent);
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mDownX = x;
            this.mDownY = y;
            return zOnInterceptTouchEvent;
        }
        if (action == 2) {
            int i = this.mDownX - x;
            int i2 = this.mDownY - y;
            if ((Math.abs(i2) > this.mScaleTouchSlop || Math.abs(i) > this.mScaleTouchSlop) && Math.abs(i) < Math.abs(i2)) {
                requestDisallowParentInterceptTouchEvent(true);
                return true;
            }
        }
        return zOnInterceptTouchEvent;
    }

    public void requestDisallowParentInterceptTouchEvent(boolean z) {
        ViewParent parent = getParent();
        parent.requestDisallowInterceptTouchEvent(z);
        while (parent != null) {
            if (parent instanceof SpringBackLayout) {
                ((SpringBackLayout) parent).internalRequestDisallowInterceptTouchEvent(z);
            }
            parent = parent.getParent();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
    protected int getChildDrawingOrder(int i, int i2) {
        return super.getChildDrawingOrder(i, i2);
    }

    public Point getTouchPoint() {
        return this.touchPoint;
    }
}
