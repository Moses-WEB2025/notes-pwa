package com.miui.duokantext;

import android.graphics.Canvas;

/* JADX INFO: loaded from: classes2.dex */
public interface TextRender {
    void drawText(Canvas canvas);

    int getMeasuredTextHeight();

    int getMeasuredTextWidth();
}
