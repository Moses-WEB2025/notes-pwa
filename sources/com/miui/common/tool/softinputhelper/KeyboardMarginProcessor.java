package com.miui.common.tool.softinputhelper;

import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;

/* JADX INFO: loaded from: classes2.dex */
public class KeyboardMarginProcessor implements KeyboardHeightListener {
    @Override // com.miui.common.tool.softinputhelper.KeyboardHeightListener
    public void onKeyboardHeightChangedEnd(View view, WindowInsets windowInsets, int i) {
    }

    @Override // com.miui.common.tool.softinputhelper.KeyboardHeightListener
    public void onKeyboardHeightChanged(View view, WindowInsets windowInsets, int i, int i2) {
        updateViewMarginBottom(view, windowInsets, i);
    }

    protected void updateViewMarginBottom(View view, WindowInsets windowInsets, int i) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        marginLayoutParams.bottomMargin = i;
        view.setLayoutParams(marginLayoutParams);
    }
}
