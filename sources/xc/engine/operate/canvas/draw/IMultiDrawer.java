package xc.engine.operate.canvas.draw;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.DataShape;
import xc.engine.operate.canvas.draw.scale.ScaleInfo;

/* JADX INFO: loaded from: classes6.dex */
public interface IMultiDrawer {
    void clearCanvas();

    void clearRunnable();

    void doScale(ScaleInfo scaleInfo);

    void doScaleUp();

    void drawDeletePoint(RectF rectF);

    void drawShapeCurve(DataShape dataShape);

    float getDrawRatio();

    ScaleInfo getDrawerScaleInfo();

    float getPointMaxSize();

    float getSaveRatio();

    ScaleInfo getScaleInfoWhenScrolling();

    Matrix getScaleMatrix();

    float getScaleVisibleTop();

    void initPreviewBitmap(DataCurve dataCurve, Bitmap bitmap);

    void onCanvasSizeChanged();

    void onCanvasVisibleOffsetChanged(float f, float f2);

    void onCanvasVisibleSizeChanged(float f, float f2);

    void onPageDataLoaded();

    void refreshCurveData(RectF rectF);

    void refreshCurveData(RectF rectF, boolean z);

    void refreshData(RectF rectF);

    void refreshForSelect(RectF rectF);

    void refreshSelectCurveData(RectF rectF);

    void release();

    void rendToBitmap(Bitmap bitmap, RectF rectF);

    void resetInfiniteCanvasData();

    void resetScale();

    void setInfiniteCanvasDebugMode(boolean z);

    void setInfiniteCanvasMode(boolean z);

    void setLoadDirection(int i);

    void setShapeCurve(DataCurve dataCurve);

    void showPredict(boolean z, int i);

    void updateVisibleWidth(float f);
}
