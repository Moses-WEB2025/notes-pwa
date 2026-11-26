package com.miui.notes.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import com.miui.common.view.ParentStateListener;
import miuix.springback.view.SpringBackLayout;

/* JADX INFO: loaded from: classes4.dex */
public class NoteSpringBackLayout extends SpringBackLayout {
    private int mDownX;
    private int mDownY;
    private int mScaledTouchSlop;
    private ParentStateListener mStateListener;

    public NoteSpringBackLayout(Context context) {
        this(context, null);
    }

    public NoteSpringBackLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    public void setStateListener(ParentStateListener parentStateListener) {
        this.mStateListener = parentStateListener;
    }

    @Override // miuix.springback.view.SpringBackLayout, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        ParentStateListener parentStateListener;
        int action = motionEvent.getAction();
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        if (action == 0) {
            this.mDownX = x;
            this.mDownY = y;
        } else if (action == 1) {
            int i = this.mDownX - x;
            int i2 = this.mDownY - y;
            int i3 = this.mScaledTouchSlop;
            if (i < i3 && i2 < i3 && (parentStateListener = this.mStateListener) != null) {
                parentStateListener.onClick(this);
            }
        }
        return super.onInterceptTouchEvent(motionEvent);
    }
}
