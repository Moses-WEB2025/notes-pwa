package com.miui.handwriting.common.view.popup;

import android.content.Context;
import android.view.View;

/* JADX INFO: loaded from: classes3.dex */
public class BaseAppPopupWindow extends ArrowPopupWindow {
    private static final String TAG = "BasePopupWindow";

    public BaseAppPopupWindow(Context context) {
        super(context);
    }

    public void setOnKeyListener(View.OnKeyListener onKeyListener) {
        getContentView().setFocusableInTouchMode(true);
        getContentView().setOnKeyListener(onKeyListener);
    }
}
