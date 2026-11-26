package xc.engine.operate.canvas;

import android.graphics.Bitmap;
import android.view.Surface;
import xc.engine.data.CurveProp;
import xc.engine.data.IPenPropTransform;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.DataShape;
import xc.engine.operate.canvas.draw.scale.ScaleInfo;

/* JADX INFO: loaded from: classes6.dex */
public interface ICanvasOperator {
    void doScale(ScaleInfo scaleInfo);

    void doScaleUp();

    void forceRedraw();

    void generateBrushPreviewBitmap(CurveProp curveProp, Bitmap bitmap);

    float getCanvasDrawWidth();

    ScaleInfo getCanvasScaleInfo();

    int getCurCurveColor(DataCurve dataCurve);

    float getDrawRatio();

    float getSaveRatio();

    float getScaleVisibleTop();

    int getShapeFillColor(DataShape dataShape);

    float getVisibleFixedHeight();

    float getVisibleFixedWidth();

    float getVisibleHeight();

    float getVisibleWidth();

    void initDrawer();

    void initPreviewBitmap(CurveProp curveProp, Bitmap bitmap);

    boolean isColorWheelMode();

    void renderingUpdate();

    void resetInfiniteCanvas();

    void resetScale();

    void setCanSelectInvisibleData(boolean z);

    void setCanvasDrawWidth(float f);

    void setCanvasOperateListener(ICanvasOperateListener iCanvasOperateListener);

    void setCanvasSize(float f, float f2);

    void setInfiniteCanvasDebuggable(boolean z);

    void setInfiniteCanvasMode(boolean z);

    void setLoadDirection(int i);

    void setMaxPage(int i);

    void setPenPropTransform(IPenPropTransform iPenPropTransform);

    void setScaleMode(boolean z);

    void setSurface(Surface surface);

    void setVisibleFixedHeight(float f);

    void setVisibleFixedWidth(float f);

    void setVisibleSize(float f, float f2);
}
