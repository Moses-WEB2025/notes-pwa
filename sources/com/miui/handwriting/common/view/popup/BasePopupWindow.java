package com.miui.handwriting.common.view.popup;

import android.content.Context;
import android.view.View;

/* JADX INFO: loaded from: classes3.dex */
public class BasePopupWindow extends miuix.popupwidget.widget.ArrowPopupWindow {
    public BasePopupWindow(Context context) {
        super(context);
    }

    @Override // miuix.popupwidget.widget.ArrowPopupWindow
    protected void onPrepareWindow() {
        super.onPrepareWindow();
        setIsLaidOutInScreen(true);
    }

    public void setOnKeyListener(View.OnKeyListener onKeyListener) {
        getContentView().setFocusableInTouchMode(true);
        getContentView().setOnKeyListener(onKeyListener);
    }
}
