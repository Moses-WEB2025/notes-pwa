package com.miui.handwriting.common.softinputhelper;

import android.view.View;

/* JADX INFO: loaded from: classes3.dex */
public abstract class BaseKeyboardHeightProvider {
    protected View mContentView;
    protected View mDecorView;
    protected boolean mEnable = true;
    protected KeyboardHeightListener mKeyboardHeightListener;

    public abstract void release();

    public BaseKeyboardHeightProvider(View view, View view2, KeyboardHeightListener keyboardHeightListener) {
        this.mDecorView = null;
        this.mContentView = view;
        if (view2 != null) {
            this.mDecorView = view2;
        } else {
            this.mDecorView = view;
        }
        this.mKeyboardHeightListener = keyboardHeightListener;
    }

    protected void notifyKeyboardHeightChanged(int i, int i2) {
        KeyboardHeightListener keyboardHeightListener = this.mKeyboardHeightListener;
        if (keyboardHeightListener != null) {
            keyboardHeightListener.onKeyboardHeightChanged(this.mContentView, i, i2);
        }
    }

    protected void notifyKeyboardHeightChangedProgress(int i, int i2) {
        KeyboardHeightListener keyboardHeightListener = this.mKeyboardHeightListener;
        if (keyboardHeightListener != null) {
            keyboardHeightListener.onKeyboardHeightChangedProgress(this.mContentView, i, i2);
        }
    }

    protected int getScreenOrientation() {
        return this.mContentView.getResources().getConfiguration().orientation;
    }

    public void setEnable(boolean z) {
        this.mEnable = z;
    }
}
