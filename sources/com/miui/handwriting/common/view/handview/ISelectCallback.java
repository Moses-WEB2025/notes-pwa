package com.miui.handwriting.common.view.handview;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;

/* JADX INFO: loaded from: classes3.dex */
public interface ISelectCallback {
    void dismissSelectPopWindow();

    void drawContourAntLine(Canvas canvas, Path path);

    void drawRectangularAntLine(Canvas canvas, Path path, float[] fArr, boolean z, int i);

    void drawShapeAntLine(Canvas canvas, Path path, float[] fArr, boolean z, boolean z2);

    void onPastePopWindow(float f, float f2, boolean z);

    void onSelectPopWindow(RectF rectF, boolean z, boolean z2, boolean z3);
}
