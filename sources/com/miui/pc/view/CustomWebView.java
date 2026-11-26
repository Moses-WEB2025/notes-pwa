package com.miui.pc.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.webkit.WebView;
import com.miui.notes.base.utils.Logger;
import com.miui.richeditor.OnKeyDownAndShortCutListener;
import com.miui.todo.utils.MetaReflectUtil;

/* JADX INFO: loaded from: classes4.dex */
public class CustomWebView extends WebView {
    private OnKeyDownAndShortCutListener mKeyShortCutListener;

    public CustomWebView(Context context) {
        super(context);
    }

    public CustomWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CustomWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.webkit.WebView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            Logger.INSTANCE.e("CustomWebView", "down事件处理前  buttonstate " + motionEvent.getButtonState() + "  actionbutton " + motionEvent.getActionButton());
        }
        try {
            Class[] clsArr = {Integer.TYPE};
            MetaReflectUtil.getDeclaredMethod(MotionEvent.class, "setButtonState", clsArr).invoke(motionEvent, 0);
            MetaReflectUtil.getDeclaredMethod(MotionEvent.class, "setActionButton", clsArr).invoke(motionEvent, 0);
        } catch (Exception unused) {
        }
        if (motionEvent.getAction() == 0) {
            Logger.INSTANCE.e("CustomWebView", "down事件处理后  buttonstate " + motionEvent.getButtonState() + "  actionbutton " + motionEvent.getActionButton());
        }
        if (motionEvent.getAction() == 3) {
            try {
                MotionEvent.class.getDeclaredMethod("setAction", Integer.TYPE).invoke(motionEvent, 1);
            } catch (Exception unused2) {
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onKeyPreIme(int i, KeyEvent keyEvent) {
        OnKeyDownAndShortCutListener onKeyDownAndShortCutListener;
        OnKeyDownAndShortCutListener onKeyDownAndShortCutListener2;
        if (keyEvent.getAction() == 0 && (onKeyDownAndShortCutListener2 = this.mKeyShortCutListener) != null) {
            onKeyDownAndShortCutListener2.onKeyDown(i, keyEvent);
        }
        if (keyEvent.getAction() == 1 && (onKeyDownAndShortCutListener = this.mKeyShortCutListener) != null) {
            onKeyDownAndShortCutListener.onKeyUp(i, keyEvent);
        }
        return super.onKeyPreIme(i, keyEvent);
    }

    @Override // android.view.View
    public boolean onKeyShortcut(int i, KeyEvent keyEvent) {
        return super.onKeyShortcut(i, keyEvent);
    }

    @Override // android.webkit.WebView, android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return super.onKeyDown(i, keyEvent);
    }

    public void setKeyShortCutListener(OnKeyDownAndShortCutListener onKeyDownAndShortCutListener) {
        this.mKeyShortCutListener = onKeyDownAndShortCutListener;
    }
}
