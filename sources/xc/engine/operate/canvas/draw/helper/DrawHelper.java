package xc.engine.operate.canvas.draw.helper;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import xc.engine.api.PenEngine;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.operate.canvas.draw.infinite.PageItemsManager;
import xc.engine.operate.canvas.draw.scale.ScaleInfo;
import xc.engine.utils.ScaleDataUtil;

/* JADX INFO: loaded from: classes6.dex */
public class DrawHelper {
    private static final String TAG = "DrawHelper";
    private boolean isCanvasVisibleSizeChanged;
    public boolean isScaleInfoUpdated;
    private float mBitmapAreaHeight;
    public Paint mBitmapPaint;
    public ReentrantLock mLock;
    public PageItemsManager mPageItemsManager;
    public final PenEngine mPenEngine;
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

    public DrawHelper(PenEngine penEngine, ReentrantLock reentrantLock) {
        this.mPenEngine = penEngine;
        this.mLock = reentrantLock;
        Paint paint = new Paint();
        this.mBitmapPaint = paint;
        paint.setFilterBitmap(true);
        this.mBitmapPaint.setDither(true);
        this.mBitmapPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        this.mVisibleHeight = penEngine.getCanvasOperator().getVisibleHeight();
        this.mVisibleWidth = penEngine.getCanvasOperator().getVisibleWidth();
        this.mVisibleBitmapAreasCount = (int) Math.ceil(this.mVisibleHeight / this.mBitmapAreaHeight);
        this.mPageItemsManager = new PageItemsManager(penEngine, true);
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

    public void updateCanvasMatrix(ScaleInfo scaleInfo, ScaleInfo scaleInfo2, boolean z) {
        if (z) {
            this.mCanvasMatrix.setScale(scaleInfo.getScaleRate(), scaleInfo.getScaleRate(), 0.0f, 0.0f);
            this.mCanvasMatrix.postTranslate(scaleInfo.getTranslate().x, scaleInfo.getTranslate().y);
            this.mCanvasMatrix.postTranslate(scaleInfo2.getTranslate().x, scaleInfo2.getTranslate().y);
            this.mCanvasMatrix.postScale(scaleInfo2.getScaleRate(), scaleInfo2.getScaleRate(), scaleInfo2.getScaleCenter().x, scaleInfo2.getScaleCenter().y);
        }
    }

    public void updateScaleMatrix(ScaleInfo scaleInfo, ScaleInfo scaleInfo2) {
        this.isScaleInfoUpdated = this.mCurScaleInfo.getScaleRate() < (scaleInfo.getScaleRate() * scaleInfo2.getScaleRate()) * 1.3f;
        this.mScaleMatrix.reset();
        if (ScaleDataUtil.isScaleChange(scaleInfo, this.mCurScaleInfo)) {
            this.mScaleMatrix.setTranslate(-this.mCurScaleInfo.getTranslate().x, -this.mCurScaleInfo.getTranslate().y);
            this.mScaleMatrix.postScale(1.0f / this.mCurScaleInfo.getScaleRate(), 1.0f / this.mCurScaleInfo.getScaleRate(), 0.0f, 0.0f);
            this.mScaleMatrix.postScale(scaleInfo.getScaleRate(), scaleInfo.getScaleRate(), 0.0f, 0.0f);
            this.mScaleMatrix.postTranslate(scaleInfo.getTranslate().x, scaleInfo.getTranslate().y);
        }
        this.mScaleMatrix.postTranslate(scaleInfo2.getTranslate().x, scaleInfo2.getTranslate().y);
        this.mScaleMatrix.postScale(scaleInfo2.getScaleRate(), scaleInfo2.getScaleRate(), scaleInfo2.getScaleCenter().x, scaleInfo2.getScaleCenter().y);
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

    /* JADX WARN: Code duplicated, block: B:12:0x004e  */
    private BitmapIndex obtainBitmap(int i) {
        BitmapIndex bitmapIndex;
        float fMax = Math.max(this.mVisibleWidth, this.mPenEngine.mCanvasOperator.getVisibleFixedWidth());
        RemoveBitmapPool.RemovedCache removedCache = this.mRemovedBitmapPool.removedCaches.get(0);
        if (removedCache == null || removedCache.cachedBitmapIndexList.isEmpty()) {
            bitmapIndex = null;
        } else {
            ArrayList<BitmapIndex> arrayList = removedCache.cachedBitmapIndexList;
            int size = arrayList.size() - 1;
            if (size >= 0) {
                try {
                    bitmapIndex = arrayList.remove(size);
                } catch (IndexOutOfBoundsException e) {
                    Log.e(TAG, "obtainBitmap: " + e.getMessage());
                    bitmapIndex = null;
                }
            } else {
                bitmapIndex = null;
            }
        }
        if (bitmapIndex == null) {
            bitmapIndex = new BitmapIndex();
            if (this.mVisibleWidth <= 0.0f) {
                return null;
            }
            bitmapIndex.bitmap = Bitmap.createBitmap((int) fMax, ((int) this.mBitmapAreaHeight) + 8, Bitmap.Config.ARGB_8888);
        }
        bitmapIndex.id = i;
        float f = i * this.mBitmapAreaHeight;
        Rect rect = bitmapIndex.mRect;
        rect.set(0, (int) f, (int) Math.ceil(fMax + 0.0f), (int) Math.ceil(f + this.mBitmapAreaHeight));
        bitmapIndex.isLoaded = false;
        this.mScreenContentRect.union(rect.left, rect.top, rect.right, rect.bottom + 8);
        return bitmapIndex;
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
