package com.miui.duokantext;

import android.graphics.Canvas;

/* JADX INFO: loaded from: classes2.dex */
public abstract class BaseTextRender implements TextRender {
    private static boolean DEBUG = false;
    protected int mTextHeight;
    protected int mTextWidth;

    public static native String getDefaultFontFile();

    private static native void nativeEnableDebug(boolean z);

    public abstract void destroy();

    @Override // com.miui.duokantext.TextRender
    public void drawText(Canvas canvas) {
    }

    public int getFirstLineBaseLine() {
        return -1;
    }

    public void measure(int i, int i2) {
    }

    static {
        System.loadLibrary("duokantext");
    }

    @Override // com.miui.duokantext.TextRender
    public int getMeasuredTextHeight() {
        return this.mTextHeight;
    }

    @Override // com.miui.duokantext.TextRender
    public int getMeasuredTextWidth() {
        return this.mTextWidth;
    }

    protected void setMeasuredDimension(int i, int i2) {
        this.mTextWidth = i;
        this.mTextHeight = i2;
    }

    public static void enableDebug(boolean z) {
        DEBUG = z;
        nativeEnableDebug(z);
    }

    public static boolean isDebug() {
        return DEBUG;
    }
}
