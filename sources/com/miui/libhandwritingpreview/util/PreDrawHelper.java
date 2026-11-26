package com.miui.libhandwritingpreview.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import com.miui.libhandwritingpreview.engine.PenPreEngine;
import com.miui.libhandwritingpreview.manager.PrePageItemsManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.operate.canvas.draw.helper.BitmapIndex;
import xc.engine.operate.canvas.draw.helper.RemoveBitmapPool;
import xc.engine.operate.canvas.draw.scale.ScaleInfo;

/* JADX INFO: loaded from: classes3.dex */
public class PreDrawHelper {
    private static final String TAG = "PreDrawHelper";
    private boolean isCanvasVisibleSizeChanged;
    public boolean isScaleInfoUpdated;
    private float mBitmapAreaHeight;
    public Paint mBitmapPaint;
    public ReentrantLock mLock;
    public PrePageItemsManager mPageItemsManager;
    public final PenPreEngine mPenEngine;
    private RemoveBitmapPool mRemovedBitmapPool;
    private int mVisibleBitmapAreasCount;
    private float mVisibleHeight;
    private float mVisibleTop;
    private float mVisibleWidth;
    public Matrix mCanvasMatrix = new Matrix();
    public Matrix mScaleMatrix = new Matrix();
    private final Rect mVisibleRect = new Rect();
    public Rect mScreenContentRect = new Rect();
    public RectF mRemoveRectF = new RectF();
    public ScaleInfo mCurScaleInfo = new ScaleInfo();
    private volatile float mVisibleScale = 1.0f;
    public int mLoadDirection = 1;

    public PreDrawHelper(PenPreEngine penPreEngine, ReentrantLock reentrantLock) {
        this.mPenEngine = penPreEngine;
        this.mLock = reentrantLock;
        Paint paint = new Paint();
        this.mBitmapPaint = paint;
        paint.setFilterBitmap(true);
        this.mBitmapPaint.setDither(true);
        this.mBitmapPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        this.mVisibleHeight = penPreEngine.getCanvasOperator().getVisibleHeight();
        this.mVisibleWidth = penPreEngine.getCanvasOperator().getVisibleWidth();
        this.mVisibleBitmapAreasCount = (int) Math.ceil(this.mVisibleHeight / this.mBitmapAreaHeight);
        this.mPageItemsManager = new PrePageItemsManager(penPreEngine, true);
    }

    public final void removeView(BitmapIndex bitmapIndex) {
        if (bitmapIndex.mRect.top == this.mScreenContentRect.top) {
            this.mScreenContentRect.top = bitmapIndex.mRect.bottom;
            this.mRemoveRectF.set(0.0f, 0.0f, this.mVisibleWidth, bitmapIndex.mRect.bottom);
        } else if (bitmapIndex.mRect.bottom == this.mScreenContentRect.bottom) {
            this.mScreenContentRect.bottom = bitmapIndex.mRect.top;
            this.mRemoveRectF.set(0.0f, bitmapIndex.mRect.top, this.mVisibleWidth, 1.0E7f);
        }
        this.mLock.lock();
        bitmapIndex.isLoaded = false;
        Bitmap bitmap = bitmapIndex.bitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.eraseColor(0);
        }
        bitmapIndex.mRect.setEmpty();
        this.mLock.unlock();
        List<DataCurve> list = bitmapIndex.intersectingCurves;
        if (list != null && list.size() > 0) {
            for (DataCurve dataCurve : bitmapIndex.intersectingCurves) {
                if (dataCurve.size(dataCurve.mOriginPoints) != 0 && this.mRemoveRectF.contains(dataCurve.getDrawRect())) {
                    synchronized (dataCurve.mLockObj) {
                        dataCurve.clearPoints(dataCurve.mOriginPoints);
                    }
                }
            }
        }
        RemoveBitmapPool.RemovedCache removedCache = this.mRemovedBitmapPool.removedCaches.get(0);
        if (removedCache == null) {
            removedCache = new RemoveBitmapPool.RemovedCache();
            this.mRemovedBitmapPool.removedCaches.put(0, removedCache);
        }
        ArrayList<BitmapIndex> arrayList = removedCache.cachedBitmapIndexList;
        if (this.mRemovedBitmapPool.removedCaches.get(0).maxSize <= arrayList.size()) {
            return;
        }
        if (arrayList.contains(bitmapIndex)) {
            throw new IllegalStateException("The removed index already exists!");
        }
        bitmapIndex.isLoaded = false;
        bitmapIndex.intersectingCurves = null;
        arrayList.add(bitmapIndex);
    }

    public void onCanvasVisibleSizeChanged(float f, float f2, boolean z) {
        Log.i(TAG, "onCanvasVisibleSizeChanged, w: " + f + ", h: " + f2 + ", changed: " + z);
        this.isCanvasVisibleSizeChanged = true;
        this.mVisibleHeight = f2;
        this.mVisibleWidth = f;
        if (!this.mPenEngine.mCanvasOperator.isApplyingInfiniteCanvas) {
            this.mVisibleWidth = Math.max(this.mPenEngine.mCanvasOperator.getVisibleFixedWidth(), f);
        }
        this.mBitmapAreaHeight = 300.0f;
        if (z) {
            this.mVisibleScale = Math.min(this.mPenEngine.mCanvasOperator.getScaleInfo().getScaleRate(), 1.0f);
            this.mVisibleTop = (int) (this.mPenEngine.mCanvasOperator.isScale ? this.mPenEngine.mDrawerWrapper.getScaleVisibleTop() : 0.0f);
        }
        Math.ceil((f2 / this.mVisibleScale) / this.mBitmapAreaHeight);
        if (this.mPenEngine.mSinglePage) {
            this.mVisibleBitmapAreasCount = 2;
        } else {
            this.mVisibleBitmapAreasCount = 0;
        }
        this.mPageItemsManager.onCanvasSizeChanged(f, f2);
        if (!z || this.mPenEngine.mCanvasOperator.isApplyingInfiniteCanvas || this.mPenEngine.mCanvasOperator.mCanvasOperateListener == null) {
            return;
        }
        this.mPenEngine.mCanvasOperator.mCanvasOperateListener.onCanvasDataChanged(null, 12);
    }

    public void setScaleVisibleTop(float f, float f2, boolean z) {
        if (!this.isCanvasVisibleSizeChanged || this.mVisibleWidth == 0.0f || this.mVisibleHeight == 0.0f) {
            return;
        }
        if ((f > this.mVisibleScale && z) || f == -1.0f) {
            this.mVisibleScale = f != -1.0f ? Math.min(f, 1.0f) : 1.0f;
        } else if (f < this.mVisibleScale && f < 1.0f) {
            this.mVisibleScale = f;
        }
        if (this.mPenEngine.mCanvasOperator.isApplyingInfiniteCanvas) {
            this.mPageItemsManager.onCanvasOffsetChanged(-this.mPenEngine.mDrawerWrapper.getDrawerScaleInfo().getTranslate().x, -this.mPenEngine.mDrawerWrapper.getDrawerScaleInfo().getTranslate().y, this.mVisibleScale, z);
        }
    }
}
