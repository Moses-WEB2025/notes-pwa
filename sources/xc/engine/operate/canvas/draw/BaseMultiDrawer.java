package xc.engine.operate.canvas.draw;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.Log;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import xc.engine.api.PenEngine;
import xc.engine.core.graphics.curve.info.ShapeDrawInfo;
import xc.engine.operate.canvas.draw.helper.BitmapIndex;
import xc.engine.operate.canvas.draw.helper.DrawHelper;
import xc.engine.operate.canvas.draw.infinite.PageItem;
import xc.engine.operate.canvas.draw.scale.ScaleInfo;

/* JADX INFO: loaded from: classes6.dex */
public abstract class BaseMultiDrawer implements IMultiDrawer {
    public static final String TAG = "BaseMultiDrawer";
    protected volatile boolean isScaleDrawing;
    protected volatile boolean isScaleOperating;
    protected boolean isShowPredict;
    protected BackgroundInfo mBackgroundInfo;
    protected volatile ScaleInfo mBaseScaleInfo;
    protected volatile Rect mCurCellRect;
    public DrawHelper mDrawHelper;
    protected boolean mEnableScale;
    protected int mLoadDirection;
    protected final PenEngine mPenEngine;
    protected Paint mPredictBitmapPaint;
    protected int mPredictCurveColor;
    protected int mPredictRenderHeight;
    public final ReentrantLock mReentrantLock;
    protected volatile ScaleInfo mScaleInfo;
    protected volatile ScaleInfo mScrollingScaleInfo;
    protected volatile Bitmap mShapeBitmap;
    protected Paint mShapeBitmapPaint;
    protected ShapeDrawInfo mShapeDrawInfo;
    protected volatile Rect mShapeRenderRect;
    protected float mVisibleHeight;
    protected float mVisibleTop;
    protected float mVisibleWidth;
    protected boolean mIsVisible = true;
    protected volatile boolean isNoNeedPartition = false;
    protected boolean mIsInfinite = false;

    public BaseMultiDrawer(PenEngine penEngine) {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.mReentrantLock = reentrantLock;
        this.mPredictRenderHeight = 100;
        this.mBaseScaleInfo = new ScaleInfo();
        this.mScaleInfo = new ScaleInfo();
        this.mScrollingScaleInfo = new ScaleInfo();
        this.isScaleOperating = false;
        this.isScaleDrawing = false;
        this.mLoadDirection = 1;
        this.mPenEngine = penEngine;
        this.mDrawHelper = new DrawHelper(penEngine, reentrantLock);
        Paint paint = new Paint();
        this.mShapeBitmapPaint = paint;
        paint.setColor(-16777216);
        this.mShapeBitmapPaint.setStyle(Paint.Style.STROKE);
        this.mShapeBitmapPaint.setStrokeWidth(3.0f);
        this.mShapeBitmapPaint.setAntiAlias(true);
        this.mCurCellRect = new Rect();
        this.mShapeRenderRect = new Rect();
        Paint paint2 = new Paint();
        this.mPredictBitmapPaint = paint2;
        paint2.setAntiAlias(true);
        this.mPredictBitmapPaint.setDither(true);
        this.mPredictBitmapPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        this.mShapeDrawInfo = new ShapeDrawInfo(penEngine);
        this.mBackgroundInfo = new BackgroundInfo();
    }

    int[] obtainCellAreaPixels(Rect rect, List<BitmapIndex> list) {
        int[] iArr = new int[rect.width() * rect.height()];
        for (BitmapIndex bitmapIndex : list) {
            if (bitmapIndex.bitmap != null && !bitmapIndex.bitmap.isRecycled()) {
                Rect rect2 = new Rect(bitmapIndex.mRect);
                if (rect2.intersect(rect)) {
                    int iWidth = rect2.width();
                    int iHeight = rect2.height();
                    int i = iWidth * iHeight;
                    int[] iArr2 = new int[i];
                    bitmapIndex.bitmap.getPixels(iArr2, 0, iWidth, Math.max(rect2.left - bitmapIndex.mRect.left, 0), Math.max(rect2.top - bitmapIndex.mRect.top, 0), iWidth, iHeight);
                    System.arraycopy(iArr2, 0, iArr, (rect2.top - rect.top) * rect.width(), i);
                }
            }
        }
        return iArr;
    }

    int[] obtainInfiniteFragmentsPixels(Rect rect, List<PageItem> list) {
        int[] iArr = new int[rect.width() * rect.height()];
        for (PageItem pageItem : list) {
            if (pageItem.getBitmap() != null && !pageItem.getBitmap().isRecycled()) {
                Rect rect2 = new Rect(pageItem.getPositionRect());
                if (rect2.intersect(rect)) {
                    int iWidth = rect2.width();
                    int iHeight = rect2.height();
                    int i = iWidth * iHeight;
                    int[] iArr2 = new int[i];
                    pageItem.getBitmap().getPixels(iArr2, 0, iWidth, Math.max(rect2.left - pageItem.getPositionRect().left, 0), Math.max(rect2.top - pageItem.getPositionRect().top, 0), iWidth, iHeight);
                    System.arraycopy(iArr2, 0, iArr, (rect2.top - rect.top) * rect.width(), i);
                }
            }
        }
        return iArr;
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void setLoadDirection(int i) {
        this.mLoadDirection = i;
        DrawHelper drawHelper = this.mDrawHelper;
        if (drawHelper != null) {
            drawHelper.mLoadDirection = i;
        }
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void clearCanvas() {
        DrawHelper drawHelper = this.mDrawHelper;
        if (drawHelper == null) {
            return;
        }
        drawHelper.mLock.lock();
        try {
            if (this.mIsInfinite) {
                this.mDrawHelper.mPageItemsManager.clearCanvas();
            }
        } catch (Throwable th) {
            this.mDrawHelper.mLock.unlock();
            this.mDrawHelper.mPenEngine.mCanvasOperator.mCanvasOperateListener.onCanvasDataChanged(null, 11);
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:23:0x007f  */
    void updateCellPixels(Rect rect, int[] iArr, List<BitmapIndex> list) {
        int[] iArr2;
        int i = 0;
        while (i < list.size()) {
            BitmapIndex bitmapIndex = list.get(i);
            if (bitmapIndex.bitmap == null || bitmapIndex.bitmap.isRecycled()) {
                iArr2 = iArr;
            } else {
                Rect rect2 = new Rect(bitmapIndex.mRect);
                if (i + 1 < list.size()) {
                    rect2.bottom += 8;
                }
                if (rect2.intersect(rect)) {
                    try {
                        iArr2 = iArr;
                        try {
                            bitmapIndex.bitmap.setPixels(iArr2, (rect2.left - rect.left) + ((rect2.top - rect.top) * rect.width()), rect.width(), rect2.left, rect2.top - bitmapIndex.mRect.top, rect2.width(), rect2.height());
                            bitmapIndex.isLoaded = true;
                        } catch (Exception e) {
                            e = e;
                            Log.e(TAG, "error in updateCellPixels is " + e);
                        }
                    } catch (Exception e2) {
                        e = e2;
                        iArr2 = iArr;
                    }
                } else {
                    iArr2 = iArr;
                }
            }
            i++;
            iArr = iArr2;
        }
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void setInfiniteCanvasMode(boolean z) {
        this.mIsInfinite = z;
    }
}
