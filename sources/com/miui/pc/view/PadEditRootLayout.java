package com.miui.pc.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import com.miui.notes.base.utils.Logger;

/* JADX INFO: loaded from: classes4.dex */
public class PadEditRootLayout extends FrameLayout {
    private OnKeyEventPreImeListener mImeListener;

    public interface OnKeyEventPreImeListener {
        boolean onKeyEventPreIme(KeyEvent keyEvent);
    }

    public PadEditRootLayout(Context context) {
        super(context);
    }

    public PadEditRootLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PadEditRootLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public void setKeyEventPreImeListener(OnKeyEventPreImeListener onKeyEventPreImeListener) {
        this.mImeListener = onKeyEventPreImeListener;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        Logger.INSTANCE.i("PadEditRootLayout", "onTouchEvent " + motionEvent.toString());
        return super.onTouchEvent(motionEvent);
    }
}
