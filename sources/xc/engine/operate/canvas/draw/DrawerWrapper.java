package xc.engine.operate.canvas.draw;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import xc.engine.api.PenEngine;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.DataShape;
import xc.engine.operate.canvas.draw.scale.ScaleInfo;

/* JADX INFO: loaded from: classes6.dex */
public class DrawerWrapper implements IMultiDrawer {
    private final String TAG = "DrawerWrapper";
    public boolean isInitialized = true;
    public IMultiDrawer mDrawer = new EmptyDrawer();
    private final PenEngine mPenEngine;
    private float mVisibleHeight;
    private float mVisibleWidth;

    public DrawerWrapper(PenEngine penEngine) {
        this.mPenEngine = penEngine;
    }

    public void initDrawer() {
        this.mDrawer = new MultiDrawer(this.mPenEngine);
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void drawDeletePoint(RectF rectF) {
        this.mDrawer.drawDeletePoint(rectF);
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void refreshData(RectF rectF) {
        this.mDrawer.refreshData(rectF);
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void onCanvasSizeChanged() {
        this.mDrawer.onCanvasSizeChanged();
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void doScale(ScaleInfo scaleInfo) {
        if (this.isInitialized) {
            this.mDrawer.doScale(scaleInfo);
        }
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void doScaleUp() {
        if (this.isInitialized) {
            this.mDrawer.doScaleUp();
        }
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void resetScale() {
        if (this.isInitialized) {
            this.mDrawer.resetScale();
        }
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public ScaleInfo getDrawerScaleInfo() {
        return !this.isInitialized ? new ScaleInfo() : this.mDrawer.getDrawerScaleInfo();
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public ScaleInfo getScaleInfoWhenScrolling() {
        return !this.isInitialized ? new ScaleInfo() : this.mDrawer.getScaleInfoWhenScrolling();
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public float getScaleVisibleTop() {
        if (this.isInitialized) {
            return this.mDrawer.getScaleVisibleTop();
        }
        return 0.0f;
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void rendToBitmap(Bitmap bitmap, RectF rectF) {
        if (this.isInitialized) {
            this.mDrawer.rendToBitmap(bitmap, rectF);
        }
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void showPredict(boolean z, int i) {
        if (this.isInitialized) {
            this.mDrawer.showPredict(z, i);
        }
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void clearRunnable() {
        if (this.isInitialized) {
            this.mDrawer.clearRunnable();
        }
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void setLoadDirection(int i) {
        this.mDrawer.setLoadDirection(i);
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void clearCanvas() {
        this.mDrawer.clearCanvas();
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void updateVisibleWidth(float f) {
        if (this.isInitialized) {
            this.mDrawer.updateVisibleWidth(f);
        }
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public Matrix getScaleMatrix() {
        if (this.isInitialized) {
            return this.mDrawer.getScaleMatrix();
        }
        return null;
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void refreshForSelect(RectF rectF) {
        if (this.isInitialized) {
            this.mDrawer.refreshForSelect(rectF);
        }
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void setInfiniteCanvasMode(boolean z) {
        if (this.isInitialized) {
            this.mDrawer.setInfiniteCanvasMode(z);
        }
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void setInfiniteCanvasDebugMode(boolean z) {
        if (this.isInitialized) {
            this.mDrawer.setInfiniteCanvasDebugMode(z);
        }
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void resetInfiniteCanvasData() {
        if (this.isInitialized) {
            this.mDrawer.resetInfiniteCanvasData();
        }
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void onPageDataLoaded() {
        if (this.isInitialized) {
            this.mDrawer.onPageDataLoaded();
        }
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public float getDrawRatio() {
        if (this.isInitialized) {
            return this.mDrawer.getDrawRatio();
        }
        return 1.0f;
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public float getSaveRatio() {
        if (this.isInitialized) {
            return this.mDrawer.getSaveRatio();
        }
        return 1.0f;
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void refreshSelectCurveData(RectF rectF) {
        if (this.mVisibleWidth == 0.0f || this.mVisibleHeight == 0.0f || !this.isInitialized || rectF.isEmpty()) {
            return;
        }
        this.mDrawer.refreshSelectCurveData(rectF);
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void onCanvasVisibleOffsetChanged(float f, float f2) {
        if (this.isInitialized) {
            this.mDrawer.onCanvasVisibleOffsetChanged(f, f2);
        }
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void refreshCurveData(RectF rectF) {
        if (this.mVisibleWidth == 0.0f || this.mVisibleHeight == 0.0f || !this.isInitialized || rectF.isEmpty()) {
            return;
        }
        this.mDrawer.refreshCurveData(rectF);
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void refreshCurveData(RectF rectF, boolean z) {
        if (this.mVisibleWidth == 0.0f || this.mVisibleHeight == 0.0f || !this.isInitialized || rectF.isEmpty()) {
            return;
        }
        this.mDrawer.refreshCurveData(rectF, z);
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void drawShapeCurve(DataShape dataShape) {
        if (this.isInitialized) {
            this.mDrawer.drawShapeCurve(dataShape);
        }
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void release() {
        if (this.isInitialized) {
            this.mDrawer.release();
        }
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public float getPointMaxSize() {
        if (this.isInitialized) {
            return this.mDrawer.getPointMaxSize();
        }
        return 0.0f;
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void initPreviewBitmap(DataCurve dataCurve, Bitmap bitmap) {
        if (this.isInitialized) {
            this.mDrawer.initPreviewBitmap(dataCurve, bitmap);
        }
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void onCanvasVisibleSizeChanged(float f, float f2) {
        if (this.isInitialized) {
            this.mVisibleWidth = f;
            this.mVisibleHeight = f2;
            this.mDrawer.onCanvasVisibleSizeChanged(f, f2);
        }
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void setShapeCurve(DataCurve dataCurve) {
        if (this.isInitialized) {
            this.mDrawer.setShapeCurve(dataCurve);
        }
    }
}
