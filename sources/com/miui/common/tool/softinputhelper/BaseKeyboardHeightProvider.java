package com.miui.common.tool.softinputhelper;

import android.os.Build;
import android.view.View;
import android.view.WindowInsets;
import com.miui.common.tool.DisplayUtils;

/* JADX INFO: loaded from: classes2.dex */
public abstract class BaseKeyboardHeightProvider {
    protected View mContentView;
    protected View mDecorView;
    protected boolean mEnable = true;
    protected boolean mForceMode = true;
    protected int mImeHeight = 0;
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

    protected void notifyKeyboardHeightChanged(WindowInsets windowInsets, int i, int i2) {
        KeyboardHeightListener keyboardHeightListener = this.mKeyboardHeightListener;
        if (keyboardHeightListener != null) {
            keyboardHeightListener.onKeyboardHeightChanged(this.mContentView, windowInsets, i, i2);
        }
    }

    protected void notifyKeyboardHeightChangedEnd(int i, WindowInsets windowInsets) {
        KeyboardHeightListener keyboardHeightListener = this.mKeyboardHeightListener;
        if (keyboardHeightListener != null) {
            keyboardHeightListener.onKeyboardHeightChangedEnd(this.mContentView, windowInsets, i);
        }
    }

    protected void removeKeyboardHeightListener() {
        this.mKeyboardHeightListener = null;
    }

    protected int getScreenOrientation() {
        return DisplayUtils.getRealScreenOrientation(this.mContentView.getContext());
    }

    public void setEnable(boolean z) {
        this.mEnable = z;
    }

    public void setForceMode(boolean z) {
        this.mForceMode = z;
    }

    public int getImeHeight() {
        return this.mImeHeight;
    }

    public void update() {
        KeyboardHeightListener keyboardHeightListener = this.mKeyboardHeightListener;
        if (keyboardHeightListener != null) {
            keyboardHeightListener.onKeyboardHeightChanged(this.mContentView, null, (getImeHeight() != 0 || Build.VERSION.SDK_INT < 30 || this.mContentView.getRootWindowInsets() == null) ? getImeHeight() : this.mContentView.getRootWindowInsets().getInsets(WindowInsets.Type.ime()).bottom, getScreenOrientation());
            this.mKeyboardHeightListener.onKeyboardHeightChangedEnd(this.mContentView, null, getScreenOrientation());
        }
    }
}
