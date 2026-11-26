package com.miui.notes.ui.widget;

import android.content.Context;
import android.util.AttributeSet;

/* JADX INFO: loaded from: classes4.dex */
public class FloatingActionButton extends miuix.miuixbasewidget.widget.FloatingActionButton {
    public FloatingActionButton(Context context) {
        super(context);
    }

    public FloatingActionButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public FloatingActionButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // miuix.miuixbasewidget.widget.FloatingActionButton, android.view.View
    public void setAlpha(float f) {
        super.setAlpha(f);
        if (getDrawable() != null) {
            getDrawable().setAlpha((int) (f * 255.0f));
        }
        invalidate();
    }
}
