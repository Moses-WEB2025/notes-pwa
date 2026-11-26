package com.miui.todo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.FrameLayout;

/* JADX INFO: loaded from: classes4.dex */
public class EditRootLayout extends FrameLayout {
    private OnKeyEventPreImeListener mImeListener;

    public interface OnKeyEventPreImeListener {
        boolean onKeyEventPreIme(KeyEvent keyEvent);
    }

    public EditRootLayout(Context context) {
        super(context);
    }

    public EditRootLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public EditRootLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public void setKeyEventPreImeListener(OnKeyEventPreImeListener onKeyEventPreImeListener) {
        this.mImeListener = onKeyEventPreImeListener;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEventPreIme(KeyEvent keyEvent) {
        if (this.mImeListener.onKeyEventPreIme(keyEvent)) {
            return true;
        }
        return super.dispatchKeyEventPreIme(keyEvent);
    }
}
