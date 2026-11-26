package com.miui.common.tool.softinputhelper;

import android.view.View;
import android.view.WindowInsets;

/* JADX INFO: loaded from: classes2.dex */
public class KeyboardPaddingProcessor implements KeyboardHeightListener {
    @Override // com.miui.common.tool.softinputhelper.KeyboardHeightListener
    public void onKeyboardHeightChangedEnd(View view, WindowInsets windowInsets, int i) {
    }

    @Override // com.miui.common.tool.softinputhelper.KeyboardHeightListener
    public void onKeyboardHeightChanged(View view, WindowInsets windowInsets, int i, int i2) {
        updateViewPaddingBottom(view, windowInsets, i);
    }

    protected void updateViewPaddingBottom(View view, WindowInsets windowInsets, int i) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), i);
    }
}
