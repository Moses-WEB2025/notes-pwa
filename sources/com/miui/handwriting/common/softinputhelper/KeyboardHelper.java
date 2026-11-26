package com.miui.handwriting.common.softinputhelper;

import android.app.Activity;
import android.view.View;

/* JADX INFO: loaded from: classes3.dex */
public class KeyboardHelper {
    public static int IME_MASK = 8;
    private BaseKeyboardHeightProvider mHeightProvider;

    public static boolean isKeyboardVisibleInMultiWindowMode(Activity activity) {
        if (activity == null || activity.getWindow() == null || activity.getWindow().getDecorView().getRootWindowInsets() == null) {
            return false;
        }
        return activity.getWindow().getDecorView().getRootWindowInsets().isVisible(IME_MASK);
    }

    public static boolean isKeyboardVisible(Activity activity) {
        if (activity == null || activity.getWindow() == null || activity.getWindow().getDecorView().getRootWindowInsets() == null) {
            return false;
        }
        return activity.getWindow().getDecorView().getRootWindowInsets().isVisible(IME_MASK);
    }

    public KeyboardHelper(View view, View view2, KeyboardHeightListener keyboardHeightListener) {
        this.mHeightProvider = null;
        this.mHeightProvider = new RKeyboardHeightProvider(view, view2, keyboardHeightListener);
    }

    public void setEnable(boolean z) {
        BaseKeyboardHeightProvider baseKeyboardHeightProvider = this.mHeightProvider;
        if (baseKeyboardHeightProvider != null) {
            baseKeyboardHeightProvider.setEnable(z);
        }
    }

    public void release() {
        BaseKeyboardHeightProvider baseKeyboardHeightProvider = this.mHeightProvider;
        if (baseKeyboardHeightProvider != null) {
            baseKeyboardHeightProvider.release();
        }
    }
}
