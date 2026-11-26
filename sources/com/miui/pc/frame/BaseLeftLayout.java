package com.miui.pc.frame;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import com.miui.richeditor.OnKeyDownAndShortCutListener;

/* JADX INFO: loaded from: classes4.dex */
public class BaseLeftLayout extends LinearLayout {
    private OnKeyDownAndShortCutListener mKeyShortCutListener;

    public BaseLeftLayout(Context context) {
        super(context);
    }

    public BaseLeftLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BaseLeftLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public BaseLeftLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        OnKeyDownAndShortCutListener onKeyDownAndShortCutListener = this.mKeyShortCutListener;
        if (onKeyDownAndShortCutListener != null) {
            onKeyDownAndShortCutListener.onKeyDown(i, keyEvent);
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        OnKeyDownAndShortCutListener onKeyDownAndShortCutListener = this.mKeyShortCutListener;
        if (onKeyDownAndShortCutListener != null) {
            onKeyDownAndShortCutListener.onKeyUp(i, keyEvent);
        }
        return super.onKeyUp(i, keyEvent);
    }

    @Override // android.view.View
    public boolean onKeyShortcut(int i, KeyEvent keyEvent) {
        OnKeyDownAndShortCutListener onKeyDownAndShortCutListener = this.mKeyShortCutListener;
        if (onKeyDownAndShortCutListener != null) {
            onKeyDownAndShortCutListener.onKeyShortcut(i, keyEvent);
        }
        return super.onKeyShortcut(i, keyEvent);
    }

    public void setKeyShortCutListener(OnKeyDownAndShortCutListener onKeyDownAndShortCutListener) {
        this.mKeyShortCutListener = onKeyDownAndShortCutListener;
    }
}
