package com.miui.duokantext;

import android.graphics.Canvas;
import android.view.View;

/* JADX INFO: loaded from: classes2.dex */
public class SimpleTextRender extends BaseTextRender {
    private long mNativeHandle;
    private String mText;

    private native void nativeDestroy();

    private native void nativeDrawText(Canvas canvas, int i);

    private native void nativeDrawTextByJString(Canvas canvas, String str);

    private native int nativeGetFirstLineBaseLine();

    private native void nativeInit();

    private native void nativeMeasure(int i, int i2);

    private native void nativeRequestLayout();

    private native void nativeSetAlignType(int i);

    private native void nativeSetFont(String str, int i);

    private native void nativeSetLineGap(double d);

    private native void nativeSetParaSpacing(double d);

    private native void nativeSetTabStop(double d);

    private native void nativeSetTextColor(int i);

    public SimpleTextRender() {
        nativeInit();
    }

    public void relayout() {
        nativeRequestLayout();
    }

    public void setText(String str) {
        this.mText = str;
        nativeRequestLayout();
    }

    public void drawText(Canvas canvas, int i) {
        nativeDrawText(canvas, i);
    }

    @Override // com.miui.duokantext.BaseTextRender, com.miui.duokantext.TextRender
    public void drawText(Canvas canvas) {
        nativeDrawTextByJString(canvas, this.mText);
    }

    public void setTextWidth(int i) {
        nativeMeasure(View.MeasureSpec.makeMeasureSpec(i, 1073741824), 0);
    }

    @Override // com.miui.duokantext.BaseTextRender
    public void measure(int i, int i2) {
        nativeMeasure(i, i2);
    }

    public void setFont(String str, int i) {
        nativeSetFont(str, i);
    }

    public void setJustifyAlign() {
        nativeSetAlignType(0);
    }

    public void setLeftAlign() {
        nativeSetAlignType(1);
    }

    public void setCenterAlign() {
        nativeSetAlignType(2);
    }

    public void setRightAlign() {
        nativeSetAlignType(3);
    }

    public void setTabStop(double d) {
        nativeSetTabStop(d);
    }

    public void setLineGap(double d) {
        nativeSetLineGap(d);
    }

    public void setParaSpacing(double d) {
        nativeSetParaSpacing(d);
    }

    public void setTextColor(int i) {
        nativeSetTextColor(i);
    }

    @Override // com.miui.duokantext.BaseTextRender
    public int getFirstLineBaseLine() {
        return nativeGetFirstLineBaseLine();
    }

    @Override // com.miui.duokantext.BaseTextRender
    public void destroy() {
        nativeDestroy();
        this.mNativeHandle = 0L;
    }
}
