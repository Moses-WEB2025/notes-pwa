package com.miui.handwriting.common.view;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import com.miui.libhandwritingpage.utils.Utils;
import miuix.androidbasewidget.widget.EditText;

/* JADX INFO: loaded from: classes3.dex */
public class EllipsizeEditText extends EditText {
    private static final String TAG = "EllipsizeEditText";
    private KeyListener mKeyListener;

    public EllipsizeEditText(Context context) {
        super(context);
        init();
    }

    public EllipsizeEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public EllipsizeEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        this.mKeyListener = getKeyListener();
        setKeyListener(null);
        setEllipsize(TextUtils.TruncateAt.END);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
        Log.i(TAG, "onFocusChanged " + z);
        if (z) {
            setKeyListener(this.mKeyListener);
            setEllipsize(null);
            Utils.showSoftInput(this);
        } else {
            setKeyListener(null);
            setEllipsize(TextUtils.TruncateAt.END);
            Utils.hideSoftInput(this);
        }
    }

    @Override // miuix.androidbasewidget.widget.EditText, android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            int iMax = Math.max(getOffsetForPosition((int) motionEvent.getX(), (int) motionEvent.getY()), 0);
            requestFocus();
            setSelection(iMax);
        }
        return super.onTouchEvent(motionEvent);
    }
}
