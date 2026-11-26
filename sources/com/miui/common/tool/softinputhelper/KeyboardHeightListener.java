package com.miui.common.tool.softinputhelper;

import android.view.View;
import android.view.WindowInsets;

/* JADX INFO: loaded from: classes2.dex */
public interface KeyboardHeightListener {
    void onKeyboardHeightChanged(View view, WindowInsets windowInsets, int i, int i2);

    void onKeyboardHeightChangedEnd(View view, WindowInsets windowInsets, int i);
}
