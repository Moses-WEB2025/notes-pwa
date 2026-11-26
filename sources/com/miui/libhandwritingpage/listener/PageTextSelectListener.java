package com.miui.libhandwritingpage.listener;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.view.View;
import com.miui.libhandwritingpage.newbean.beanTouch.TouchPosition;
import java.util.List;
import xc.engine.data.pagedata.DataCurve;

/* JADX INFO: loaded from: classes3.dex */
public interface PageTextSelectListener {
    void dismissCropPopWindow();

    void dismissSelectPopWindow();

    void drawFollowAntLine(Canvas canvas, Path path);

    void drawRectangularAntLine(Canvas canvas, Path path, float[] fArr, boolean z, int i);

    void drawRotateAndScaleButton(Canvas canvas, int i, Path path, View view, boolean z);

    void drawShapeAntLine(Canvas canvas, Path path, float[] fArr, boolean z, boolean z2);

    void onDeleteData();

    void onExitTextEdit();

    void onPastePopWindow(float f, float f2, boolean z);

    void onSelectPopWindow(RectF rectF, boolean z, boolean z2, boolean z3);

    void onSelectSaveRectF(RectF rectF, List<TouchPosition> list, int i, List<DataCurve> list2, List<Point> list3);

    void showCropPopWindow(RectF rectF);
}
