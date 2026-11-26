package com.miui.libhandwritingpreview.manager;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.Log;
import com.miui.libhandwritingpreview.engine.PenPreEngine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.operate.canvas.draw.infinite.PageItem;

/* JADX INFO: loaded from: classes3.dex */
public class PrePageItemsManager {
    private static final int CACHE_SIZE = 16;
    private static final float ITEM_SIZE = 500.0f;
    private static final float ITEM_SIZE_FACTOR = 0.25f;
    private static final String TAG = "PrePageItemsManager";
    private int mColumnCount;
    private final Paint mDebugPaint;
    private final TextPaint mDebugTextPaint;
    private float mFragItemHeight;
    private float mFragItemWidth;
    private final boolean mIsItemHeightFixed;
    private boolean mIsScrollEnd;
    private final PenPreEngine mPenEngine;
    private int mRowCount;
    private float mScaledOffsetLeft;
    private float mScaledOffsetTop;
    private int mVisibleHeight;
    private float mVisibleLeft;
    private float mVisibleTop;
    private int mVisibleWidth;
    private int mLoadDirection = 1;
    private boolean mIsDebugEnable = false;
    private final List<PageItem> mSnapshotLeftItems = new ArrayList();
    private final Map<Rect, PageItem> mMemoryQuickRemovedItems = new HashMap();
    private boolean mDataFirstLoaded = false;
    private final ReentrantLock mLock = new ReentrantLock();
    private final List<PageItem> mVisiblePageItems = new ArrayList(64);
    private final List<PageItem> mRedrawPageItems = new ArrayList();
    private final Map<Rect, PageItem> mRetiredPageItems = new LinkedHashMap<Rect, PageItem>(16, 0.75f, true) { // from class: com.miui.libhandwritingpreview.manager.PrePageItemsManager.1
        @Override // java.util.LinkedHashMap
        protected boolean removeEldestEntry(Map.Entry<Rect, PageItem> entry) {
            PageItem value;
            boolean z = size() > 16;
            if (z && (value = entry.getValue()) != null && value.getBitmap() != null && !value.getBitmap().isRecycled()) {
                value.getBitmap().eraseColor(0);
            }
            return z;
        }
    };
    private float mCanvasScaleRate = 1.0f;
    private final int mPreloadRowsOrColumns = 2;
    private final Rect mAbsoluteVisibleRect = new Rect();
    private final Rect mScaledVisibleRect = new Rect();
    private final Rect mScaledPreloadRect = new Rect();

    public PrePageItemsManager(PenPreEngine penPreEngine, boolean z) {
        this.mPenEngine = penPreEngine;
        this.mIsItemHeightFixed = z;
        Paint paint = new Paint();
        this.mDebugPaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3.0f);
        paint.setColor(-7829368);
        paint.setAlpha(60);
        TextPaint textPaint = new TextPaint();
        this.mDebugTextPaint = textPaint;
        textPaint.setColor(-7829368);
        textPaint.setTextSize(32.0f);
    }

    private void buildCheckerboard() {
        recyclePageItems();
        ArrayList arrayList = new ArrayList();
        float f = this.mColumnCount == 1 ? this.mVisibleWidth : this.mFragItemWidth;
        float f2 = this.mRowCount == 1 ? this.mVisibleHeight : this.mFragItemHeight;
        float f3 = this.mVisibleTop;
        int i = this.mPreloadRowsOrColumns;
        float f4 = f3 - ((i * 0.5f) * f2);
        float f5 = this.mVisibleLeft - ((i * 0.5f) * f);
        for (int i2 = 0; i2 < this.mRowCount; i2++) {
            for (int i3 = 0; i3 < this.mColumnCount; i3++) {
                float f6 = (i3 * f) + f5;
                float f7 = (i2 * f2) + f4;
                Rect rect = new Rect((int) f6, (int) f7, (int) (f6 + f), (int) (f7 + f2));
                PageItem pageItemObtainPageItem = obtainPageItem(rect);
                pageItemObtainPageItem.getPositionRect().set(rect);
                if (pageItemObtainPageItem.getBitmap() == null || pageItemObtainPageItem.getBitmap().isRecycled()) {
                    pageItemObtainPageItem.setBitmap(Bitmap.createBitmap((int) (f + 8.0f), (int) (8.0f + f2), Bitmap.Config.ARGB_8888));
                }
                arrayList.add(pageItemObtainPageItem);
            }
        }
        Log.d(TAG, "buildCheckerboard, w: " + this.mVisibleWidth + ", h: " + this.mVisibleHeight + ", items: " + arrayList.size());
        this.mVisiblePageItems.addAll(arrayList);
    }

    public void setDataFirstLoaded(boolean z) {
        this.mDataFirstLoaded = z;
    }

    public void startRenderingPageItems() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.mLock.lock();
        preDrawPageItems(new ArrayList(this.mVisiblePageItems), true);
        this.mLock.unlock();
        Log.d(TAG, "startRenderingPageItems, load items: " + this.mVisiblePageItems.size() + ", cost time: " + (System.currentTimeMillis() - jCurrentTimeMillis));
    }

    public List<PageItem> getInterestingPageItems(Rect rect) {
        ArrayList arrayList = new ArrayList();
        for (PageItem pageItem : this.mVisiblePageItems) {
            if (pageItem.getBitmap() != null && !pageItem.getBitmap().isRecycled() && new Rect(pageItem.getPositionRect()).intersect(rect)) {
                arrayList.add(pageItem);
            }
        }
        return arrayList;
    }

    public void calculatePreloadItems(float f, float f2) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        String str = TAG;
        Log.d(str, "calculatePreloadItems, origin visible items: " + this.mVisiblePageItems.size() + ", left: " + f + ", top: " + f2 + ", w: " + this.mVisibleWidth + ", h: " + this.mVisibleHeight);
        this.mAbsoluteVisibleRect.set((int) f, (int) f2, (int) (f + this.mVisibleWidth), (int) (f2 + this.mVisibleHeight));
        Rect rect = this.mScaledVisibleRect;
        float f3 = this.mScaledOffsetLeft;
        rect.set((int) f3, (int) this.mScaledOffsetTop, (int) Math.ceil(f3 + (this.mVisibleWidth / this.mCanvasScaleRate)), (int) Math.ceil(this.mScaledOffsetTop + (this.mVisibleHeight / this.mCanvasScaleRate)));
        Rect rect2 = this.mScaledVisibleRect;
        float f4 = this.mFragItemWidth;
        int i = this.mPreloadRowsOrColumns;
        Rect rectBorderExpand = borderExpand(rect2, f4 * i * 0.5f, this.mFragItemHeight * i * 0.5f);
        this.mLock.lock();
        int iRemoveInvisiblePageItems = removeInvisiblePageItems(rectBorderExpand);
        if (iRemoveInvisiblePageItems > 0) {
            Log.d(str, "calculatePreloadItems, remove unused item size: " + iRemoveInvisiblePageItems);
        }
        updateRowAndColumnsSize(this.mVisibleWidth, this.mVisibleHeight);
        Log.d(str, "calculatePreloadItems, update rows: " + this.mRowCount + ", columns: " + this.mColumnCount);
        Rect pageUnionRect = getPageUnionRect(this.mVisiblePageItems);
        this.mRedrawPageItems.clear();
        if (pageUnionRect.isEmpty() || this.mVisiblePageItems.isEmpty()) {
            if (this.mIsScrollEnd) {
                Log.i(str, "calculatePreloadItems, get empty & rebuild...");
                rebuildCheckerboardWithPreloadArea(rectBorderExpand);
            }
        } else {
            expandCheckerboard(pageUnionRect, rectBorderExpand);
        }
        this.mScaledPreloadRect.set(rectBorderExpand);
        List<PageItem> listExtractUnpreparedItems = extractUnpreparedItems();
        this.mVisiblePageItems.addAll(this.mRedrawPageItems);
        if (!listExtractUnpreparedItems.isEmpty()) {
            this.mRedrawPageItems.addAll(listExtractUnpreparedItems);
        }
        Log.d(str, "calculatePreloadItems, total exist items: " + this.mVisiblePageItems.size() + ", unprepared items: " + listExtractUnpreparedItems.size());
        preDrawPageItems(new ArrayList(this.mRedrawPageItems), false);
        this.mLock.unlock();
        Log.d(str, "calculatePreloadItems, cost time: " + (System.currentTimeMillis() - jCurrentTimeMillis) + "ms");
    }

    private void rebuildCheckerboardWithPreloadArea(Rect rect) {
        Rect rect2 = new Rect();
        int iFloor = (int) (((int) Math.floor(rect.left / this.mFragItemWidth)) * this.mFragItemWidth);
        float fFloor = (int) Math.floor(rect.top / this.mFragItemHeight);
        float f = this.mFragItemHeight;
        int i = (int) (fFloor * f);
        rect2.set(iFloor, i, (int) (iFloor + this.mFragItemWidth), (int) (i + f));
        this.mVisiblePageItems.add(obtainPageItem(rect2));
        expandCheckerboard(rect2, rect);
    }

    private void expandCheckerboard(Rect rect, Rect rect2) {
        Rect rect3 = new Rect(rect);
        float f = this.mFragItemWidth;
        float f2 = this.mFragItemHeight;
        Log.d(TAG, "expandCheckerboard, sharedRect: " + rect + ", transRect: " + rect2 + ", stepX: " + f + ", stepY: " + f2 + ", scale: " + this.mCanvasScaleRate);
        if (f == 0.0f || f2 == 0.0f) {
            throw new IllegalStateException("expandCheckerboard check failed for step is zero.");
        }
        int i = 0;
        int i2 = 0;
        while (rect3.left > rect2.left) {
            int i3 = (int) (rect3.left - f);
            int i4 = rect.top;
            while (i4 < rect.bottom) {
                Rect rect4 = new Rect(i3, i4, rect3.left, (int) (i4 + f2));
                PageItem pageItemObtainPageItem = obtainPageItem(rect4);
                if (pageItemObtainPageItem.getPositionRect().isEmpty()) {
                    pageItemObtainPageItem.setPositionRect(rect4);
                }
                this.mRedrawPageItems.add(pageItemObtainPageItem);
                i4 = rect4.bottom;
                i2++;
                if (i4 >= rect.bottom) {
                    break;
                }
            }
            rect3.left = i3;
        }
        int i5 = 0;
        while (rect3.top > rect2.top) {
            int i6 = (int) (rect3.top - f2);
            int i7 = rect3.left;
            while (i7 < rect.right) {
                Rect rect5 = new Rect(i7, i6, (int) (i7 + f), rect3.top);
                PageItem pageItemObtainPageItem2 = obtainPageItem(rect5);
                if (pageItemObtainPageItem2.getPositionRect().isEmpty()) {
                    pageItemObtainPageItem2.setPositionRect(rect5);
                }
                this.mRedrawPageItems.add(pageItemObtainPageItem2);
                i7 = rect5.right;
                i5++;
                if (i7 >= rect.right) {
                    break;
                }
            }
            rect3.top = i6;
        }
        int i8 = 0;
        while (rect3.right < rect2.right) {
            int i9 = (int) (rect3.right + f);
            int i10 = rect3.top;
            while (i10 < rect3.bottom) {
                Rect rect6 = new Rect(rect3.right, i10, i9, (int) (i10 + f2));
                PageItem pageItemObtainPageItem3 = obtainPageItem(rect6);
                if (pageItemObtainPageItem3.getPositionRect().isEmpty()) {
                    pageItemObtainPageItem3.setPositionRect(rect6);
                }
                this.mRedrawPageItems.add(pageItemObtainPageItem3);
                i10 = rect6.bottom;
                i8++;
                if (i10 >= rect3.bottom) {
                    break;
                }
            }
            rect3.right = i9;
        }
        while (rect3.bottom < rect2.bottom) {
            int i11 = (int) (rect3.bottom + f2);
            int i12 = rect3.left;
            while (i12 < rect3.right) {
                Rect rect7 = new Rect(i12, rect3.bottom, (int) (i12 + f), i11);
                PageItem pageItemObtainPageItem4 = obtainPageItem(rect7);
                if (pageItemObtainPageItem4.getPositionRect().isEmpty()) {
                    pageItemObtainPageItem4.setPositionRect(rect7);
                }
                this.mRedrawPageItems.add(pageItemObtainPageItem4);
                i12 = rect7.right;
                i++;
                if (i12 >= rect3.right) {
                    break;
                }
            }
            rect3.bottom = i11;
        }
        Log.d(TAG, "expandCheckerboard, expand added item size left: " + i2 + ", top: " + i5 + ", right: " + i8 + ", bottom: " + i);
    }

    private List<PageItem> extractUnpreparedItems() {
        if (this.mVisiblePageItems.isEmpty()) {
            return new ArrayList();
        }
        ArrayList<PageItem> arrayList = new ArrayList(this.mVisiblePageItems);
        ArrayList arrayList2 = new ArrayList();
        for (PageItem pageItem : arrayList) {
            if (pageItem.getStatus() != 1) {
                arrayList2.add(pageItem);
            }
        }
        return arrayList2;
    }

    public List<PageItem> getVisiblePageItems() {
        return new ArrayList(this.mVisiblePageItems);
    }

    public PageItem obtainPageItem(Rect rect) {
        PageItem pageItemRemove = !this.mMemoryQuickRemovedItems.isEmpty() ? this.mMemoryQuickRemovedItems.remove(rect) : null;
        if (pageItemRemove == null && !this.mRetiredPageItems.isEmpty() && (pageItemRemove = this.mRetiredPageItems.remove(rect)) != null) {
            Log.d(TAG, "obtainPageItem, get from removed cache.");
        }
        if (pageItemRemove == null) {
            pageItemRemove = createPageItem(rect);
        }
        if (pageItemRemove.getBitmap() != null && !pageItemRemove.getBitmap().isRecycled()) {
            return pageItemRemove;
        }
        pageItemRemove.setBitmap(Bitmap.createBitmap((int) (this.mFragItemWidth + 8.0f), (int) (this.mFragItemHeight + 8.0f), Bitmap.Config.ARGB_8888));
        return pageItemRemove;
    }

    private PageItem findFirstPageItem(int i, int i2) {
        for (PageItem pageItem : this.mVisiblePageItems) {
            if (pageItem.getId() == PageItem.generateUniqueKey(i, i2)) {
                return pageItem;
            }
        }
        return null;
    }

    private PageItem createPageItem(Rect rect) {
        PageItem pageItem = new PageItem(0, rect, Bitmap.createBitmap((int) (this.mFragItemWidth + 8.0f), (int) (this.mFragItemHeight + 8.0f), Bitmap.Config.ARGB_8888));
        pageItem.updateIndex((int) (rect.centerX() / this.mFragItemWidth), (int) (rect.centerY() / this.mFragItemHeight));
        return pageItem;
    }

    public int removeInvisiblePageItems(Rect rect) {
        int i;
        this.mMemoryQuickRemovedItems.clear();
        synchronized (this.mVisiblePageItems) {
            Iterator<PageItem> it = this.mVisiblePageItems.iterator();
            i = 0;
            while (it.hasNext()) {
                PageItem next = it.next();
                if (!isPageItemVisible(next, rect)) {
                    it.remove();
                    next.clearCurvesData();
                    next.setIntersectingCurves(null);
                    this.mRetiredPageItems.put(next.getPositionRect(), next);
                    this.mMemoryQuickRemovedItems.put(next.getPositionRect(), next);
                    i++;
                }
            }
        }
        int i2 = this.mColumnCount;
        int i3 = this.mPreloadRowsOrColumns;
        if (i < (i2 - i3) * (this.mRowCount - i3)) {
            this.mMemoryQuickRemovedItems.clear();
        }
        return i;
    }

    public boolean isPageItemVisible(PageItem pageItem, Rect rect) {
        return new Rect(rect).intersect(pageItem.getPositionRect());
    }

    private boolean isPageItemVisible(Rect rect, Rect rect2) {
        return ((float) rect.left) * this.mCanvasScaleRate < ((float) rect2.right) && ((float) rect.right) * this.mCanvasScaleRate > ((float) rect2.left) && ((float) rect.top) * this.mCanvasScaleRate < ((float) rect2.bottom) && ((float) rect.bottom) * this.mCanvasScaleRate > ((float) rect2.top);
    }

    public void recyclePageItems() {
        this.mLock.lock();
        if (!this.mVisiblePageItems.isEmpty()) {
            for (PageItem pageItem : new ArrayList(this.mVisiblePageItems)) {
                if (pageItem.getBitmap() != null && !pageItem.getBitmap().isRecycled()) {
                    pageItem.getBitmap().recycle();
                }
            }
            this.mVisiblePageItems.clear();
        }
        if (!this.mRetiredPageItems.isEmpty()) {
            Iterator<Map.Entry<Rect, PageItem>> it = this.mRetiredPageItems.entrySet().iterator();
            while (it.hasNext()) {
                PageItem value = it.next().getValue();
                if (value.getBitmap() != null && !value.getBitmap().isRecycled()) {
                    value.getBitmap().recycle();
                }
            }
            this.mRetiredPageItems.clear();
        }
        releaseQuickMemoryItems();
        this.mLock.unlock();
    }

    private void releaseQuickMemoryItems() {
        if (this.mMemoryQuickRemovedItems.isEmpty()) {
            return;
        }
        Iterator<Map.Entry<Rect, PageItem>> it = this.mMemoryQuickRemovedItems.entrySet().iterator();
        while (it.hasNext()) {
            PageItem value = it.next().getValue();
            if (value.getBitmap() != null && !value.getBitmap().isRecycled()) {
                value.getBitmap().recycle();
            }
        }
        this.mMemoryQuickRemovedItems.clear();
    }

    public void setRendDirection(int i) {
        this.mLoadDirection = i;
    }

    private void preDrawPageItems(List<PageItem> list, boolean z) {
        PenPreEngine penPreEngine;
        if (!z || (penPreEngine = this.mPenEngine) == null || penPreEngine.mCanvasOperator.mCanvasOperateListener == null) {
            return;
        }
        this.mPenEngine.mCanvasOperator.mCanvasOperateListener.onCanvasDataChanged(null, 12);
    }

    public void onCanvasSizeChanged(float f, float f2) {
        String str = TAG;
        Log.d(str, "onCanvasSizeChanged, old size: [" + this.mVisibleWidth + "," + this.mVisibleHeight + "], new size: [" + f + "," + f2 + "]");
        int i = (int) f;
        if (this.mVisibleWidth == i && this.mVisibleHeight == ((int) f2)) {
            return;
        }
        this.mVisibleWidth = i;
        this.mVisibleHeight = (int) f2;
        this.mLock.lock();
        this.mCanvasScaleRate = Math.min(this.mPenEngine.mCanvasOperator.getScaleInfo().getScaleRate(), 1.0f);
        if (this.mPenEngine.mCanvasOperator.isScale) {
            this.mVisibleTop = -this.mPenEngine.mDrawerWrapper.getDrawerScaleInfo().getTranslate().y;
            this.mVisibleLeft = -this.mPenEngine.mDrawerWrapper.getDrawerScaleInfo().getTranslate().x;
        } else {
            this.mVisibleTop = 0.0f;
            this.mVisibleLeft = 0.0f;
        }
        if (this.mIsItemHeightFixed) {
            this.mFragItemWidth = ITEM_SIZE;
            this.mFragItemHeight = ITEM_SIZE;
        } else {
            this.mFragItemWidth = (int) Math.ceil(f * ITEM_SIZE_FACTOR);
            this.mFragItemHeight = (int) Math.ceil(ITEM_SIZE_FACTOR * f2);
        }
        this.mScaledOffsetLeft = this.mVisibleLeft / this.mPenEngine.mDrawerWrapper.getDrawerScaleInfo().getScaleRate();
        this.mScaledOffsetTop = this.mVisibleTop / this.mPenEngine.mDrawerWrapper.getDrawerScaleInfo().getScaleRate();
        updateVisibleCheckerboardRect(this.mVisibleLeft, this.mVisibleTop);
        updateRowAndColumnsSize(f, f2);
        this.mLock.unlock();
        Log.d(str, "onCanvasSizeChanged, row size: " + this.mRowCount + ", column size: " + this.mColumnCount);
        if (this.mPenEngine.mCanvasOperator.isApplyingInfiniteCanvas) {
            buildCheckerboard();
            if (this.mDataFirstLoaded) {
                startRenderingPageItems();
                return;
            } else {
                if (this.mPenEngine.mCanvasOperator.mCanvasOperateListener != null) {
                    this.mPenEngine.mCanvasOperator.mCanvasOperateListener.onCanvasDataChanged(null, 12);
                    return;
                }
                return;
            }
        }
        recyclePageItems();
    }

    public void onCanvasOffsetChanged(float f, float f2, float f3, boolean z) {
        Log.d(TAG, "onCanvasOffsetChanged, left: " + f + ", top: " + f2 + ", scale: " + f3 + ", scrollEnd: " + z + ", isInfiniteEnable: " + this.mPenEngine.mCanvasOperator.isApplyingInfiniteCanvas);
        this.mCanvasScaleRate = f3;
        this.mIsScrollEnd = z;
        this.mScaledOffsetLeft = f / this.mPenEngine.mDrawerWrapper.getDrawerScaleInfo().getScaleRate();
        this.mScaledOffsetTop = f2 / this.mPenEngine.mDrawerWrapper.getDrawerScaleInfo().getScaleRate();
        if (this.mVisibleLeft == f && this.mVisibleTop == f2 && !z) {
            return;
        }
        moveOrScaleCanvas(f, f2);
    }

    private void moveOrScaleCanvas(float f, float f2) {
        calculatePreloadItems(f, f2);
        this.mVisibleTop = f2;
        this.mVisibleLeft = f;
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0087  */
    public void updateFragmentsPixels(Rect rect, int[] iArr) {
        int[] iArr2;
        for (PageItem pageItem : this.mVisiblePageItems) {
            if (pageItem.getBitmap() == null || pageItem.getBitmap().isRecycled()) {
                iArr2 = iArr;
            } else {
                Rect rect2 = new Rect(pageItem.getPositionRect());
                rect2.bottom += 8;
                if (rect2.intersect(rect)) {
                    try {
                        iArr2 = iArr;
                        try {
                            pageItem.getBitmap().setPixels(iArr2, (rect2.left - rect.left) + ((rect2.top - rect.top) * rect.width()), rect.width(), rect2.left, rect2.top - pageItem.getPositionRect().top, rect2.width(), rect2.height());
                            pageItem.setStatus(1);
                        } catch (Exception e) {
                            e = e;
                            Log.e(TAG, "error in updateFragmentsPixels: " + e);
                        }
                    } catch (Exception e2) {
                        e = e2;
                        iArr2 = iArr;
                    }
                } else {
                    iArr2 = iArr;
                }
            }
            iArr = iArr2;
        }
    }

    public void rendOnInfiniteCanvas(Canvas canvas, Paint paint) {
        Bitmap bitmap;
        if (this.mVisiblePageItems.isEmpty()) {
            return;
        }
        this.mSnapshotLeftItems.clear();
        this.mSnapshotLeftItems.addAll(this.mVisiblePageItems);
        this.mLock.lock();
        for (PageItem pageItem : this.mSnapshotLeftItems) {
            if (pageItem != null && (bitmap = pageItem.getBitmap()) != null && !bitmap.isRecycled() && pageItem.getStatus() == 1) {
                Rect positionRect = pageItem.getPositionRect();
                canvas.drawBitmap(bitmap, positionRect.left, positionRect.top, paint);
                paint.setStrokeWidth(4.0f);
                paint.setColor(-16776961);
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawRect(positionRect, paint);
            }
        }
        this.mLock.unlock();
        this.mSnapshotLeftItems.clear();
    }

    public void clearCanvas() {
        if (this.mVisiblePageItems.isEmpty()) {
            return;
        }
        this.mLock.lock();
        this.mSnapshotLeftItems.clear();
        this.mSnapshotLeftItems.addAll(this.mVisiblePageItems);
        for (PageItem pageItem : this.mSnapshotLeftItems) {
            if (pageItem != null) {
                List<DataCurve> intersectingCurves = pageItem.getIntersectingCurves();
                if (intersectingCurves != null) {
                    intersectingCurves.clear();
                }
                if (pageItem.getBitmap() != null && !pageItem.getBitmap().isRecycled()) {
                    pageItem.getBitmap().eraseColor(0);
                    pageItem.setStatus(0);
                }
            }
        }
        this.mLock.unlock();
        this.mSnapshotLeftItems.clear();
    }

    public void setDebugEnable(boolean z) {
        this.mIsDebugEnable = z;
    }

    public void applyDebugItemsDisplayArea(Canvas canvas) {
        int i;
        int i2;
        int i3;
        PageItem pageItem;
        if (this.mIsDebugEnable) {
            this.mSnapshotLeftItems.clear();
            this.mSnapshotLeftItems.addAll(new ArrayList(this.mVisiblePageItems));
            int i4 = 0;
            if (this.mSnapshotLeftItems.isEmpty() || (pageItem = this.mSnapshotLeftItems.get(0)) == null) {
                i = 0;
                i2 = 0;
                i3 = 0;
            } else {
                i4 = pageItem.getPositionRect().left;
                i2 = pageItem.getPositionRect().top;
                i3 = pageItem.getPositionRect().right;
                i = pageItem.getPositionRect().bottom;
            }
            for (PageItem pageItem2 : this.mSnapshotLeftItems) {
                if (pageItem2 != null) {
                    Rect positionRect = pageItem2.getPositionRect();
                    if (i4 > positionRect.left) {
                        i4 = positionRect.left;
                    }
                    if (i2 > positionRect.top) {
                        i2 = positionRect.top;
                    }
                    if (i3 < positionRect.right) {
                        i3 = positionRect.right;
                    }
                    if (i < positionRect.bottom) {
                        i = positionRect.bottom;
                    }
                    if (positionRect != null && !positionRect.isEmpty()) {
                        canvas.drawRect(pageItem2.getPositionRect(), this.mDebugPaint);
                        canvas.drawText(pageItem2.getIdString(), positionRect.centerX(), positionRect.centerY(), this.mDebugTextPaint);
                    }
                }
            }
            this.mSnapshotLeftItems.clear();
        }
    }

    /* JADX WARN: Code duplicated, block: B:25:0x00a0  */
    public void freshPageItemsWithRect(int[] iArr, Rect rect) {
        int[] iArr2;
        if (this.mVisiblePageItems.isEmpty()) {
            return;
        }
        this.mLock.lock();
        ArrayList arrayList = new ArrayList(this.mVisiblePageItems);
        int i = 0;
        while (i < arrayList.size()) {
            PageItem pageItem = (PageItem) arrayList.get(i);
            if (pageItem != null) {
                Rect rect2 = new Rect(pageItem.getPositionRect());
                rect2.right += 8;
                rect2.bottom += 8;
                Rect rect3 = new Rect(rect2);
                if (!rect3.intersect(rect) || pageItem.getBitmap() == null || pageItem.getBitmap().isRecycled()) {
                    iArr2 = iArr;
                } else {
                    try {
                        iArr2 = iArr;
                        try {
                            pageItem.getBitmap().setPixels(iArr2, (rect3.left - rect.left) + ((rect3.top - rect.top) * rect.width()), rect.width(), rect3.left - pageItem.getPositionRect().left, rect3.top - pageItem.getPositionRect().top, rect3.width(), rect3.height());
                            pageItem.setStatus(1);
                        } catch (Exception e) {
                            e = e;
                            Log.e(TAG, "error in freshPageItemsWithRect: ", e);
                        }
                    } catch (Exception e2) {
                        e = e2;
                        iArr2 = iArr;
                    }
                }
            } else {
                iArr2 = iArr;
            }
            i++;
            iArr = iArr2;
        }
        this.mLock.unlock();
    }

    public Rect getScaledVisibleRect(boolean z) {
        return new Rect(z ? this.mScaledVisibleRect : this.mAbsoluteVisibleRect);
    }

    public Rect calculateLoadedCheckerboardRect() {
        return getPageUnionRect(this.mVisiblePageItems);
    }

    public Rect getPageUnionRect(List<PageItem> list) {
        ArrayList arrayList = new ArrayList(list);
        Rect rect = new Rect();
        if (arrayList.size() > 0) {
            rect.set(((PageItem) arrayList.get(0)).getPositionRect());
            for (int i = 1; i < arrayList.size(); i++) {
                rect.union(((PageItem) arrayList.get(i)).getPositionRect());
            }
        }
        return rect;
    }

    private void updateVisibleCheckerboardRect(float f, float f2) {
        this.mAbsoluteVisibleRect.set((int) f, (int) f2, (int) (f + this.mVisibleWidth), (int) (f2 + this.mVisibleHeight));
        Rect rect = this.mScaledVisibleRect;
        float f3 = this.mScaledOffsetLeft;
        rect.set((int) f3, (int) this.mScaledOffsetTop, (int) Math.ceil(f3 + (this.mVisibleWidth / this.mCanvasScaleRate)), (int) Math.ceil(this.mScaledOffsetTop + (this.mVisibleHeight / this.mCanvasScaleRate)));
        Rect rect2 = this.mScaledVisibleRect;
        float f4 = this.mFragItemWidth;
        int i = this.mPreloadRowsOrColumns;
        this.mScaledPreloadRect.set(borderExpand(rect2, f4 * i * 0.5f, this.mFragItemHeight * i * 0.5f));
    }

    private void updateRowAndColumnsSize(float f, float f2) {
        this.mRowCount = ((int) Math.ceil((f2 / this.mCanvasScaleRate) / this.mFragItemHeight)) + this.mPreloadRowsOrColumns;
        this.mColumnCount = ((int) Math.ceil((f / this.mCanvasScaleRate) / this.mFragItemWidth)) + this.mPreloadRowsOrColumns;
    }

    private Rect borderExpand(Rect rect, float f, float f2) {
        Rect rect2 = new Rect();
        double d = f2;
        rect2.top = (int) (Math.floor(rect.top) - d);
        rect2.bottom = (int) (Math.ceil(rect.bottom) + d);
        double d2 = f;
        rect2.left = (int) (Math.floor(rect.left) - d2);
        rect2.right = (int) (Math.ceil(rect.right) + d2);
        return rect2;
    }
}
