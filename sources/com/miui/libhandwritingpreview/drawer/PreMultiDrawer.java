package com.miui.libhandwritingpreview.drawer;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.Log;
import com.miui.libhandwritingpreview.engine.PenPreEngine;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.DataShape;
import xc.engine.operate.canvas.draw.scale.ScaleInfo;

/* JADX INFO: loaded from: classes3.dex */
public class PreMultiDrawer extends PreScaleMultiDrawer {
    private final String TAG;

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void refreshForSelect(RectF rectF) {
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void rendToBitmap(Bitmap bitmap, RectF rectF) {
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void showPredict(boolean z, int i) {
    }

    public PreMultiDrawer(PenPreEngine penPreEngine) {
        super(penPreEngine);
        this.TAG = "PreMultiDrawer";
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void refreshData(RectF rectF) {
        Log.d(this.TAG, "refreshData");
        if (this.mIsVisible) {
            this.mPenEngine.getCurveEngine().getCurveSystem().updateArea(rectF, true, true);
        }
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void onCanvasSizeChanged() {
        if (this.mDrawHelper == null || !this.mEnableScale) {
            return;
        }
        boolean z = (this.mPenEngine.mCanvasOperator.getVisibleFixedWidth() == 0.0f || (this.mPenEngine.mCanvasOperator.getVisibleFixedWidth() == this.mVisibleWidth && this.mPenEngine.mCanvasOperator.mPreviousFixedWidth == this.mPenEngine.mCanvasOperator.getVisibleFixedWidth())) ? false : true;
        Log.d(this.TAG, "onCanvasSizeChanged, layout width: " + this.mVisibleWidth + ", fixedWidth: " + this.mPenEngine.mCanvasOperator.getVisibleFixedWidth() + ", changed: " + z);
        if (z) {
            this.mDrawHelper.onCanvasVisibleSizeChanged(this.mVisibleWidth, this.mVisibleHeight, true);
        }
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public float getPointMaxSize() {
        if (this.mPenEngine.getCurveEngine() != null) {
            return this.mPenEngine.getCurveEngine().getMPointMaxSize();
        }
        return 0.0f;
    }

    @Override // com.miui.libhandwritingpreview.drawer.PreScaleMultiDrawer, xc.engine.operate.canvas.draw.IMultiDrawer
    public void doScale(ScaleInfo scaleInfo) {
        super.doScale(scaleInfo);
    }

    @Override // com.miui.libhandwritingpreview.drawer.PreScaleMultiDrawer, xc.engine.operate.canvas.draw.IMultiDrawer
    public void doScaleUp() {
        super.doScaleUp();
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public float getDrawRatio() {
        float f = this.mPenEngine.mCanvasOperator.mCanvasDrawWidth;
        return f == 0.0f ? this.mVisibleWidth / 1000.0f : f / 1000.0f;
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public float getSaveRatio() {
        float f = this.mPenEngine.mCanvasOperator.mCanvasDrawWidth;
        return f == 0.0f ? 1000.0f / this.mVisibleWidth : 1000.0f / f;
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void clearRunnable() {
        Log.d(this.TAG, "clearRunnable");
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void updateVisibleWidth(float f) {
        this.mVisibleWidth = f * 1000.0f;
    }

    @Override // com.miui.libhandwritingpreview.drawer.PreScaleMultiDrawer
    public void setScaleMode(boolean z) {
        super.setScaleMode(z);
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void drawShapeCurve(DataShape dataShape) {
        Log.d(this.TAG, "drawShapeCurve");
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public Matrix getScaleMatrix() {
        if (this.mDrawHelper != null) {
            return this.mDrawHelper.mScaleMatrix;
        }
        return null;
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void refreshSelectCurveData(RectF rectF) {
        Log.d(this.TAG, "refreshSelectCurveData");
        boolean z = this.mIsVisible;
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void drawDeletePoint(RectF rectF) {
        Log.d(this.TAG, "drawDeletePoint");
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void initPreviewBitmap(DataCurve dataCurve, Bitmap bitmap) {
        Log.d(this.TAG, "initPreviewBitmap");
    }

    @Override // com.miui.libhandwritingpreview.drawer.PreScaleMultiDrawer, xc.engine.operate.canvas.draw.IMultiDrawer
    public void onCanvasVisibleSizeChanged(float f, float f2) {
        Log.d(this.TAG, "onCanvasVisibleSizeChanged");
        if (f == 0.0f || f2 == 0.0f || this.mDrawHelper == null) {
            this.mIsVisible = false;
            this.mPenEngine.mDrawStateManager.onCanvasVisible(false);
            return;
        }
        this.mIsVisible = true;
        this.mPenEngine.mDrawStateManager.onCanvasVisible(true);
        double d = f;
        boolean z = this.mVisibleWidth != ((float) ((int) Math.ceil(d)));
        this.mVisibleWidth = (int) Math.ceil(d);
        this.mVisibleHeight = (int) Math.ceil(f2);
        super.onCanvasVisibleSizeChanged(f, f2);
        this.mDrawHelper.onCanvasVisibleSizeChanged(this.mVisibleWidth, this.mVisibleHeight, z);
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void setShapeCurve(DataCurve dataCurve) {
        Log.d(this.TAG, "setShapeCurve");
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void onCanvasVisibleOffsetChanged(float f, float f2) {
        if (this.mDrawHelper == null) {
            return;
        }
        this.mVisibleTop = f2;
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void refreshCurveData(RectF rectF) {
        Log.d(this.TAG, "refreshCurveData2");
        if (this.mIsVisible) {
            this.mPenEngine.getCurveEngine().getCurveSystem().updateArea(rectF, true, true);
        }
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void refreshCurveData(RectF rectF, boolean z) {
        Log.d(this.TAG, "refreshCurveData1");
        if (this.mIsVisible) {
            this.mPenEngine.getCurveEngine().getCurveSystem().updateArea(rectF, true, true);
        }
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void release() {
        this.mIsVisible = false;
        this.mPenEngine.mDrawStateManager.onCanvasVisible(false);
        if (this.mShapeBitmap != null && !this.mShapeBitmap.isRecycled()) {
            this.mShapeBitmap.recycle();
        }
        if (this.mDrawHelper != null) {
            this.mDrawHelper.mPageItemsManager.recyclePageItems();
        }
        this.mPenEngine.mDrawStateManager.onCanvasRelease();
        this.mShapeBitmap = null;
        this.mDrawHelper = null;
    }
}
