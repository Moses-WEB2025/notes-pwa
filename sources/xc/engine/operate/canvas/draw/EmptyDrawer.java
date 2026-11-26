package xc.engine.operate.canvas.draw;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.DataShape;
import xc.engine.operate.canvas.draw.scale.ScaleInfo;

/* JADX INFO: loaded from: classes6.dex */
public class EmptyDrawer implements IMultiDrawer {
    private float mDrawRatio;

    private void forceRedraw() {
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void clearCanvas() {
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void clearRunnable() {
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void doScale(ScaleInfo scaleInfo) {
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void doScaleUp() {
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void drawDeletePoint(RectF rectF) {
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void drawShapeCurve(DataShape dataShape) {
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public float getPointMaxSize() {
        return 0.0f;
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public float getSaveRatio() {
        return 0.0f;
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public Matrix getScaleMatrix() {
        return null;
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public float getScaleVisibleTop() {
        return 0.0f;
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void initPreviewBitmap(DataCurve dataCurve, Bitmap bitmap) {
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void onCanvasSizeChanged() {
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void onCanvasVisibleOffsetChanged(float f, float f2) {
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void onCanvasVisibleSizeChanged(float f, float f2) {
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void onPageDataLoaded() {
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void refreshCurveData(RectF rectF) {
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void refreshCurveData(RectF rectF, boolean z) {
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void refreshData(RectF rectF) {
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void refreshForSelect(RectF rectF) {
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void refreshSelectCurveData(RectF rectF) {
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void release() {
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void rendToBitmap(Bitmap bitmap, RectF rectF) {
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void resetInfiniteCanvasData() {
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void resetScale() {
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void setInfiniteCanvasDebugMode(boolean z) {
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void setInfiniteCanvasMode(boolean z) {
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void setLoadDirection(int i) {
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void setShapeCurve(DataCurve dataCurve) {
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void showPredict(boolean z, int i) {
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public float getDrawRatio() {
        return this.mDrawRatio;
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public ScaleInfo getDrawerScaleInfo() {
        return new ScaleInfo();
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public ScaleInfo getScaleInfoWhenScrolling() {
        return new ScaleInfo();
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void updateVisibleWidth(float f) {
        this.mDrawRatio = f;
    }
}
