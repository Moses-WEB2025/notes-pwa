package com.miui.notes.tool;

import android.content.Context;
import android.util.AttributeSet;
import miuix.popupwidget.widget.GuidePopupWindow;

/* JADX INFO: loaded from: classes4.dex */
public class QuickNoteTipPopWindow extends GuidePopupWindow {
    public static Boolean isDismiss = false;

    public QuickNoteTipPopWindow(Context context) {
        super(context);
    }

    public QuickNoteTipPopWindow(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public QuickNoteTipPopWindow(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public QuickNoteTipPopWindow(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // miuix.popupwidget.widget.GuidePopupWindow, android.widget.PopupWindow
    public void dismiss() {
        super.dismiss();
        isDismiss = true;
    }
}
