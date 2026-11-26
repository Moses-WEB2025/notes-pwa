package com.miui.libhandwritingpreview.drawer;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import com.miui.libhandwritingpreview.engine.PenPreEngine;
import xc.engine.operate.canvas.CanvasSizeInfo;
import xc.engine.operate.canvas.draw.scale.ScaleInfo;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PreScaleMultiDrawer extends PreBaseMultiDrawer {
    private final String TAG;
    protected RectF mScaleScreenRectF;
    protected float mScaleVisibleTop;

    public PreScaleMultiDrawer(PenPreEngine penPreEngine) {
        super(penPreEngine);
        this.TAG = "PreScaleMultiDrawer";
        this.mScaleScreenRectF = new RectF();
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void doScale(ScaleInfo scaleInfo) {
        if (this.mIsVisible) {
            this.isScaleDrawing = false;
            float scaleRate = scaleInfo.getScaleRate();
            PointF translate = scaleInfo.getTranslate();
            PointF scaleCenter = scaleInfo.getScaleCenter();
            this.mScaleInfo.setScaleRate(scaleRate);
            this.mScaleInfo.getTranslate().set(translate);
            this.mScaleInfo.getScaleCenter().set(scaleCenter);
            float scaleRate2 = this.mBaseScaleInfo.getScaleRate();
            PointF translate2 = this.mBaseScaleInfo.getTranslate();
            this.mScaleVisibleTop = this.mPenEngine.getCurveEngine().getCamera().getCurrentTop();
            float f = 1.0f - scaleRate;
            float f2 = translate2.x + (translate.x * scaleRate) + ((scaleCenter.x - translate2.x) * f);
            float f3 = translate2.y + (translate.y * scaleRate) + ((scaleCenter.y - translate2.y) * f);
            this.mScrollingScaleInfo.setScaleRate(scaleRate * scaleRate2);
            this.mScrollingScaleInfo.getTranslate().set((int) f2, (int) f3);
            this.mScrollingScaleInfo.getScaleCenter().set(0.0f, 0.0f);
            Log.d(this.TAG, "doScale, mScaleInfo.getTranslate: " + this.mBaseScaleInfo.getTranslate() + ", mScaleVisibleTop: " + this.mScaleVisibleTop);
        }
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void doScaleUp() {
        if (this.mIsVisible) {
            this.isScaleDrawing = true;
            float scaleRate = this.mScaleInfo.getScaleRate();
            PointF translate = this.mScaleInfo.getTranslate();
            PointF scaleCenter = this.mScaleInfo.getScaleCenter();
            float scaleRate2 = this.mBaseScaleInfo.getScaleRate();
            PointF translate2 = this.mBaseScaleInfo.getTranslate();
            float f = 1.0f - scaleRate;
            float f2 = translate2.x + (translate.x * scaleRate) + ((scaleCenter.x - translate2.x) * f);
            float f3 = translate2.y + (translate.y * scaleRate) + ((scaleCenter.y - translate2.y) * f);
            this.mBaseScaleInfo.setScaleRate(scaleRate * scaleRate2);
            this.mBaseScaleInfo.getTranslate().set((int) f2, (int) f3);
            this.mBaseScaleInfo.getScaleCenter().set(0.0f, 0.0f);
            this.mScaleInfo.setScaleRate(1.0f);
            this.mScaleInfo.getTranslate().set(0.0f, 0.0f);
            this.mScaleInfo.getScaleCenter().set(0.0f, 0.0f);
            CanvasSizeInfo canvasSizeInfo = this.mPenEngine.mCanvasOperator.mCanvasSizeInfo;
            if (this.mBaseScaleInfo.getTranslate().y > canvasSizeInfo.getVisibleTop() && !this.mIsInfinite) {
                this.mBaseScaleInfo.getTranslate().y = canvasSizeInfo.getVisibleTop();
            }
            this.mScaleVisibleTop = this.mPenEngine.getCurveEngine().getCamera().getCurrentTop();
            if (this.mIsInfinite) {
                return;
            }
            this.mScaleVisibleTop = Math.max(0.0f, this.mScaleVisibleTop);
        }
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void resetScale() {
        this.mBaseScaleInfo = new ScaleInfo();
        this.mScaleInfo = new ScaleInfo();
        this.mScrollingScaleInfo = new ScaleInfo();
        this.mScaleScreenRectF.setEmpty();
        if (this.mDrawHelper != null) {
            this.mDrawHelper.mScaleMatrix = new Matrix();
            this.mDrawHelper.mCanvasMatrix = new Matrix();
        }
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public ScaleInfo getDrawerScaleInfo() {
        return this.mBaseScaleInfo;
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public ScaleInfo getScaleInfoWhenScrolling() {
        return this.mScrollingScaleInfo;
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public float getScaleVisibleTop() {
        return this.mScaleVisibleTop;
    }

    public void setScaleMode(boolean z) {
        this.mEnableScale = z;
        this.mPenEngine.mDrawStateManager.onCanvasScaleModeChange(z);
        if (z) {
            this.mScaleVisibleTop = this.mPenEngine.getCurveEngine().getCamera().getCurrentTop();
            return;
        }
        this.mVisibleTop = this.mScaleVisibleTop;
        this.mReentrantLock.lock();
        if (this.mIsInfinite) {
            this.mDrawHelper.mPageItemsManager.recyclePageItems();
        }
        this.mReentrantLock.unlock();
        resetScale();
        this.mDrawHelper.setScaleVisibleTop(-1.0f, this.mScaleVisibleTop, false);
        this.mPenEngine.mCanvasOperator.mCanvasOperateListener.onCanvasDataChanged(null, 11);
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void onCanvasVisibleSizeChanged(float f, float f2) {
        this.mScaleVisibleTop = this.mPenEngine.getCurveEngine().getCamera().getCurrentTop();
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void setInfiniteCanvasDebugMode(boolean z) {
        this.mDrawHelper.mPageItemsManager.setDebugEnable(z);
    }

    @Override // com.miui.libhandwritingpreview.drawer.PreBaseMultiDrawer, xc.engine.operate.canvas.draw.IMultiDrawer
    public void setInfiniteCanvasMode(boolean z) {
        super.setInfiniteCanvasMode(z);
        if (z) {
            this.mDrawHelper.setScaleVisibleTop(this.mBaseScaleInfo.getScaleRate(), this.mScaleVisibleTop, true);
        } else {
            this.mDrawHelper.mPageItemsManager.recyclePageItems();
        }
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void resetInfiniteCanvasData() {
        if (this.mIsInfinite) {
            this.mDrawHelper.mPageItemsManager.recyclePageItems();
        }
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void onPageDataLoaded() {
        if (this.mDrawHelper != null) {
            if (this.mDrawHelper.mPageItemsManager != null) {
                this.mDrawHelper.mPageItemsManager.setDataFirstLoaded(true);
            } else {
                Log.d("", "onPageDataLoaded mDrawHelper.mPageItemsManager is null");
            }
            this.mDrawHelper.setScaleVisibleTop(this.mBaseScaleInfo.getScaleRate(), this.mScaleVisibleTop, true);
            return;
        }
        Log.d("", "onPageDataLoaded mDrawHelper is null");
    }
}
