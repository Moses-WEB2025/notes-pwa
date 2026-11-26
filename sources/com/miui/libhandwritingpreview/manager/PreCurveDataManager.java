package com.miui.libhandwritingpreview.manager;

import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import com.miui.libhandwritingpreview.PreCurveGenTask;
import com.miui.libhandwritingpreview.engine.PenPreEngine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToLongFunction;
import xc.engine.core.graphics.curve.CurveGenExecutor;
import xc.engine.core.graphics.curve.ICurveDataManager;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.ICurveChangeListener;
import xc.engine.utils.FeatureManager;

/* JADX INFO: loaded from: classes3.dex */
public class PreCurveDataManager extends ICurveDataManager {
    private int[] mColumnCounters;
    private CurveDataPool[] mCurveDataPool;
    private CurveDataPool[] mNegativeCurveDataPool;
    private final PenPreEngine mPenEngine;
    private int[] mRowCounters;
    private final String TAG = "PreCurveDataManager";
    private final ReadWriteLock mReadWriteLock = new ReentrantReadWriteLock();
    public final CurveGenExecutor mCurveGenExecutor = new CurveGenExecutor();
    public int mMaxPage = 0;
    private final int mPageHeight = 1600;
    private final List<ICurveChangeListener> mCurveChangeListeners = new ArrayList();

    private static class CurveDataPool {
        final HashSet<DataCurve> mDataCurveList;

        private CurveDataPool() {
            this.mDataCurveList = new HashSet<>(8);
        }
    }

    public PreCurveDataManager(PenPreEngine penPreEngine) {
        this.mPenEngine = penPreEngine;
        init();
    }

    public final void init() {
        int i = this.mMaxPage;
        this.mRowCounters = new int[i * 1600];
        this.mColumnCounters = new int[1000];
        this.mCurveDataPool = new CurveDataPool[(i * 1600) / 100];
        int i2 = 0;
        while (true) {
            CurveDataPool[] curveDataPoolArr = this.mCurveDataPool;
            if (i2 < curveDataPoolArr.length) {
                curveDataPoolArr[i2] = new CurveDataPool();
                i2++;
            } else {
                initInfiniteData();
                return;
            }
        }
    }

    private void initInfiniteData() {
        this.mNegativeCurveDataPool = new CurveDataPool[this.mCurveDataPool.length];
        int i = 0;
        while (true) {
            CurveDataPool[] curveDataPoolArr = this.mNegativeCurveDataPool;
            if (i >= curveDataPoolArr.length) {
                return;
            }
            curveDataPoolArr[i] = new CurveDataPool();
            i++;
        }
    }

    @Override // xc.engine.core.graphics.curve.ICurveDataManager
    public void addCurveChangeListener(ICurveChangeListener iCurveChangeListener) {
        if (iCurveChangeListener == null || this.mCurveChangeListeners.contains(iCurveChangeListener)) {
            return;
        }
        this.mCurveChangeListeners.add(iCurveChangeListener);
    }

    @Override // xc.engine.core.graphics.curve.ICurveDataManager
    public void removeCurveChangeListener(ICurveChangeListener iCurveChangeListener) {
        this.mCurveChangeListeners.remove(iCurveChangeListener);
    }

    private void notifyCurveChanged(DataCurve dataCurve, String str) {
        Iterator<ICurveChangeListener> it = this.mCurveChangeListeners.iterator();
        while (it.hasNext()) {
            try {
                it.next().onCurveChanged(dataCurve, str);
            } catch (Exception e) {
                Log.e(this.TAG, "Notice onCurveChanged failed: " + e.getMessage());
            }
        }
    }

    private Rect limitRectOnInfiniteCanvas(RectF rectF) {
        if (rectF == null) {
            rectF = new RectF();
        }
        Rect rect = new Rect();
        rect.left = Math.max(1, (int) (rectF.left - 0.0f));
        rect.top = (int) rectF.top;
        rect.right = Math.min(999, (int) (rectF.right + 0.0f));
        rect.bottom = Math.min((this.mMaxPage * 1600) - 1, (int) (rectF.bottom + 0.0f));
        return rect;
    }

    @Override // xc.engine.core.graphics.curve.ICurveDataManager
    public RectF addDataToPool(DataCurve dataCurve) {
        RectF saveRect = dataCurve.getSaveRect();
        dataCurve.translateRectByPenPoint(saveRect, false);
        boolean z = this.mPenEngine.mCanvasOperator.isApplyingInfiniteCanvas;
        Rect rectLimitRectOnInfiniteCanvas = z ? limitRectOnInfiniteCanvas(saveRect) : limitRect(saveRect);
        for (int i = rectLimitRectOnInfiniteCanvas.left - 1; i <= rectLimitRectOnInfiniteCanvas.right; i++) {
            int[] iArr = this.mColumnCounters;
            iArr[i] = iArr[i] + 1;
        }
        if (rectLimitRectOnInfiniteCanvas.top > 0) {
            for (int i2 = rectLimitRectOnInfiniteCanvas.top - 1; i2 <= rectLimitRectOnInfiniteCanvas.bottom; i2++) {
                int[] iArr2 = this.mRowCounters;
                iArr2[i2] = iArr2[i2] + 1;
            }
            int iMin = Math.min(rectLimitRectOnInfiniteCanvas.bottom / 100, this.mCurveDataPool.length - 1);
            this.mReadWriteLock.writeLock().lock();
            for (int i3 = rectLimitRectOnInfiniteCanvas.top / 100; i3 <= iMin; i3++) {
                this.mCurveDataPool[i3].mDataCurveList.add(dataCurve);
            }
            this.mReadWriteLock.writeLock().unlock();
        } else {
            int i4 = rectLimitRectOnInfiniteCanvas.top / 100;
            int i5 = rectLimitRectOnInfiniteCanvas.bottom / 100;
            this.mReadWriteLock.writeLock().lock();
            int i6 = i4 < 0 ? -i4 : 0;
            if (i5 < 0) {
                int i7 = -i5;
                if (i7 <= i6) {
                    while (i7 <= i6) {
                        this.mNegativeCurveDataPool[i7].mDataCurveList.add(dataCurve);
                        i7++;
                    }
                }
            } else if (z) {
                for (int i8 = 0; i8 <= i5; i8++) {
                    this.mCurveDataPool[i8].mDataCurveList.add(dataCurve);
                }
                for (int i9 = 0; i9 <= i6; i9++) {
                    this.mNegativeCurveDataPool[i9].mDataCurveList.add(dataCurve);
                }
            }
            this.mReadWriteLock.writeLock().unlock();
        }
        notifyMathDataChange(dataCurve, "ADD");
        return dataCurve.getDrawRect();
    }

    @Override // xc.engine.core.graphics.curve.ICurveDataManager
    public RectF removeDataFromPool(DataCurve dataCurve) {
        RectF saveRect = dataCurve.getSaveRect();
        dataCurve.translateRectByPenPoint(saveRect, false);
        boolean z = this.mPenEngine.mCanvasOperator.isApplyingInfiniteCanvas;
        Rect rectLimitRectOnInfiniteCanvas = z ? limitRectOnInfiniteCanvas(saveRect) : limitRect(saveRect);
        for (int i = rectLimitRectOnInfiniteCanvas.left - 1; i <= rectLimitRectOnInfiniteCanvas.right; i++) {
            int[] iArr = this.mColumnCounters;
            iArr[i] = iArr[i] - 1;
        }
        if (rectLimitRectOnInfiniteCanvas.top > 0) {
            for (int i2 = rectLimitRectOnInfiniteCanvas.top - 1; i2 <= rectLimitRectOnInfiniteCanvas.bottom; i2++) {
                int[] iArr2 = this.mRowCounters;
                iArr2[i2] = iArr2[i2] - 1;
            }
            int iMin = Math.min(rectLimitRectOnInfiniteCanvas.bottom / 100, this.mCurveDataPool.length - 1);
            this.mReadWriteLock.writeLock().lock();
            for (int i3 = rectLimitRectOnInfiniteCanvas.top / 100; i3 <= iMin; i3++) {
                this.mCurveDataPool[i3].mDataCurveList.remove(dataCurve);
            }
            this.mReadWriteLock.writeLock().unlock();
        } else if (z) {
            int i4 = rectLimitRectOnInfiniteCanvas.top / 100;
            int i5 = rectLimitRectOnInfiniteCanvas.bottom / 100;
            this.mReadWriteLock.writeLock().lock();
            int i6 = i4 < 0 ? -i4 : 0;
            if (i5 < 0) {
                int i7 = -i5;
                if (i7 <= i6) {
                    while (i7 <= i6) {
                        this.mNegativeCurveDataPool[i7].mDataCurveList.remove(dataCurve);
                        i7++;
                    }
                }
            } else {
                for (int i8 = 0; i8 <= i5; i8++) {
                    this.mCurveDataPool[i8].mDataCurveList.remove(dataCurve);
                }
                for (int i9 = 0; i9 <= i6; i9++) {
                    this.mNegativeCurveDataPool[i9].mDataCurveList.remove(dataCurve);
                }
            }
            this.mReadWriteLock.writeLock().unlock();
        }
        notifyMathDataChange(dataCurve, "REMOVE");
        return dataCurve.getDrawRect();
    }

    @Override // xc.engine.core.graphics.curve.ICurveDataManager
    public RectF addDataAndGetRectF(List<DataCurve> list) {
        RectF rectFAddDataToPool;
        this.mReadWriteLock.writeLock().lock();
        try {
            ArrayList arrayList = new ArrayList(list);
            if (arrayList.isEmpty()) {
                rectFAddDataToPool = new RectF();
            } else {
                Log.d(this.TAG, "addDataAndGetRectF: " + arrayList.size());
                rectFAddDataToPool = addDataToPool((DataCurve) arrayList.get(0));
                for (int i = 1; i < arrayList.size(); i++) {
                    rectFAddDataToPool.union(addDataToPool((DataCurve) arrayList.get(i)));
                }
            }
            this.mReadWriteLock.writeLock().unlock();
            return rectFAddDataToPool;
        } catch (Throwable th) {
            this.mReadWriteLock.writeLock().unlock();
            throw th;
        }
    }

    @Override // xc.engine.core.graphics.curve.ICurveDataManager
    public RectF removeDataAndGetRectF(List<DataCurve> list) {
        RectF rectFRemoveDataFromPool;
        this.mReadWriteLock.writeLock().lock();
        try {
            ArrayList arrayList = new ArrayList(list);
            if (arrayList.isEmpty()) {
                rectFRemoveDataFromPool = new RectF();
            } else {
                Log.d(this.TAG, "removeDataAndGetRectF: " + arrayList.size());
                rectFRemoveDataFromPool = removeDataFromPool((DataCurve) arrayList.get(0));
                for (int i = 1; i < arrayList.size(); i++) {
                    rectFRemoveDataFromPool.union(removeDataFromPool((DataCurve) arrayList.get(i)));
                }
            }
            this.mReadWriteLock.writeLock().unlock();
            return rectFRemoveDataFromPool;
        } catch (Throwable th) {
            this.mReadWriteLock.writeLock().unlock();
            throw th;
        }
    }

    private void notifyMathDataChange(DataCurve dataCurve, String str) {
        if (FeatureManager.getInstance().isEnableMathRecognition()) {
            try {
                this.mReadWriteLock.writeLock().lock();
                notifyCurveChanged(dataCurve, str);
            } finally {
                this.mReadWriteLock.writeLock().unlock();
            }
        }
    }

    @Override // xc.engine.core.graphics.curve.ICurveDataManager
    public void executeCurveGenTask(DataCurve dataCurve) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        PreCurveGenTask preCurveGenTask = new PreCurveGenTask(dataCurve, this.mPenEngine, countDownLatch);
        if (!this.mCurveGenExecutor.mExecutorService.isShutdown()) {
            this.mCurveGenExecutor.mExecutorService.execute(preCurveGenTask);
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException unused) {
        }
    }

    @Override // xc.engine.core.graphics.curve.ICurveDataManager
    public RectF calculateDrawRect() {
        if (this.mPenEngine.mCanvasOperator.isApplyingInfiniteCanvas) {
            return calculateInfiniteDrawRect(getCachedCurveList());
        }
        int i = 0;
        while (true) {
            int[] iArr = this.mColumnCounters;
            if (i >= iArr.length || iArr[i] != 0) {
                break;
            }
            i++;
        }
        int i2 = 0;
        while (true) {
            int[] iArr2 = this.mRowCounters;
            if (i2 >= iArr2.length || iArr2[i2] != 0) {
                break;
            }
            i2++;
        }
        int length = this.mColumnCounters.length - 1;
        while (length >= 0 && this.mColumnCounters[length] == 0) {
            length--;
        }
        int length2 = this.mRowCounters.length - 1;
        while (length2 >= 0 && this.mRowCounters[length2] == 0) {
            length2--;
        }
        RectF rectF = new RectF(i % this.mColumnCounters.length, i2 % this.mRowCounters.length, Math.max(length, 0), Math.max(length2, 0));
        if (!rectF.isEmpty()) {
            rectF.right += 1.0f;
            rectF.bottom += 1.0f;
        }
        return rectF;
    }

    private RectF calculateInfiniteDrawRect(List<DataCurve> list) {
        if (list == null || list.isEmpty()) {
            return new RectF();
        }
        RectF rectF = new RectF();
        DataCurve dataCurve = list.get(0);
        RectF saveRect = dataCurve.getSaveRect();
        dataCurve.translateRectByPenPoint(saveRect, false);
        rectF.set(saveRect);
        for (int i = 1; i < list.size(); i++) {
            DataCurve dataCurve2 = list.get(i);
            RectF saveRect2 = dataCurve2.getSaveRect();
            dataCurve2.translateRectByPenPoint(saveRect2, false);
            rectF.union(saveRect2);
        }
        return rectF;
    }

    @Override // xc.engine.core.graphics.curve.ICurveDataManager
    public Rect limitRect(RectF rectF) {
        if (rectF == null) {
            rectF = new RectF();
        }
        Rect rect = new Rect();
        rect.left = Math.max(1, (int) (rectF.left - 0.0f));
        rect.top = Math.max(1, (int) (rectF.top - 0.0f));
        rect.right = Math.min(999, (int) (rectF.right + 0.0f));
        rect.bottom = Math.min((this.mMaxPage * 1600) - 1, (int) (rectF.bottom + 0.0f));
        return rect;
    }

    @Override // xc.engine.core.graphics.curve.ICurveDataManager
    public void executeScalableCurveGenTask(DataCurve dataCurve) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        PreCurveGenTask preCurveGenTask = new PreCurveGenTask(dataCurve, this.mPenEngine, countDownLatch);
        preCurveGenTask.isScale = true;
        if (!this.mCurveGenExecutor.mExecutorService.isShutdown()) {
            this.mCurveGenExecutor.mExecutorService.execute(preCurveGenTask);
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException unused) {
        }
    }

    @Override // xc.engine.core.graphics.curve.ICurveDataManager
    public void clear() {
        this.mReadWriteLock.writeLock().lock();
        for (CurveDataPool curveDataPool : this.mCurveDataPool) {
            curveDataPool.mDataCurveList.clear();
        }
        CurveDataPool[] curveDataPoolArr = this.mNegativeCurveDataPool;
        if (curveDataPoolArr != null) {
            for (CurveDataPool curveDataPool2 : curveDataPoolArr) {
                curveDataPool2.mDataCurveList.clear();
            }
        }
        this.mReadWriteLock.writeLock().unlock();
        this.mRowCounters = new int[this.mMaxPage * 1600];
        this.mColumnCounters = new int[1000];
    }

    @Override // xc.engine.core.graphics.curve.ICurveDataManager
    public void batchExecCurveGenTask(List<DataCurve> list) {
        CountDownLatch countDownLatch = new CountDownLatch(list.size());
        for (DataCurve dataCurve : new ArrayList(list)) {
            if (dataCurve.mDataState == 3) {
                countDownLatch.countDown();
            } else {
                CurveGenExecutor curveGenExecutor = this.mCurveGenExecutor;
                PreCurveGenTask preCurveGenTask = new PreCurveGenTask(dataCurve, this.mPenEngine, countDownLatch);
                if (!curveGenExecutor.mExecutorService.isShutdown()) {
                    try {
                        curveGenExecutor.mExecutorService.execute(preCurveGenTask);
                    } catch (RejectedExecutionException e) {
                        Log.e(this.TAG, "Task rejected, thread pool is shutting down: " + e.getMessage());
                        countDownLatch.countDown();
                    }
                }
            }
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException unused) {
        }
    }

    @Override // xc.engine.core.graphics.curve.ICurveDataManager
    public void batchExecScalableCurveGenTask(List<DataCurve> list) {
        CountDownLatch countDownLatch = new CountDownLatch(list.size());
        for (DataCurve dataCurve : new ArrayList(list)) {
            if (dataCurve.mDataState == 3) {
                countDownLatch.countDown();
            } else {
                CurveGenExecutor curveGenExecutor = this.mCurveGenExecutor;
                PreCurveGenTask preCurveGenTask = new PreCurveGenTask(dataCurve, this.mPenEngine, countDownLatch);
                preCurveGenTask.isScale = true;
                if (!curveGenExecutor.mExecutorService.isShutdown()) {
                    curveGenExecutor.mExecutorService.execute(preCurveGenTask);
                }
            }
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException unused) {
        }
    }

    @Override // xc.engine.core.graphics.curve.ICurveDataManager
    public List<DataCurve> getCachedCurveList() {
        HashSet hashSet = new HashSet();
        this.mReadWriteLock.readLock().lock();
        int i = 0;
        int i2 = 0;
        while (true) {
            CurveDataPool[] curveDataPoolArr = this.mCurveDataPool;
            if (i2 >= curveDataPoolArr.length) {
                break;
            }
            hashSet.addAll(curveDataPoolArr[i2].mDataCurveList);
            i2++;
        }
        if (this.mNegativeCurveDataPool != null) {
            while (true) {
                CurveDataPool[] curveDataPoolArr2 = this.mNegativeCurveDataPool;
                if (i >= curveDataPoolArr2.length) {
                    break;
                }
                hashSet.addAll(curveDataPoolArr2[i].mDataCurveList);
                i++;
            }
        }
        this.mReadWriteLock.readLock().unlock();
        ArrayList arrayList = new ArrayList(hashSet);
        Collections.sort(arrayList, new PreCurveDataManager$$ExternalSyntheticLambda0());
        return arrayList;
    }

    public static int compareDataId(DataCurve dataCurve, DataCurve dataCurve2) {
        return Integer.compare(dataCurve.mId, dataCurve2.mId);
    }

    public final List<DataCurve> scanIntersectDataCurve(RectF rectF, boolean z) {
        if (rectF == null) {
            return new ArrayList();
        }
        boolean z2 = this.mPenEngine.mCanvasOperator.isApplyingInfiniteCanvas;
        Rect rectLimitRectOnInfiniteCanvas = z2 ? limitRectOnInfiniteCanvas(rectF) : limitRect(rectF);
        HashSet<DataCurve> hashSet = new HashSet<>();
        if (rectLimitRectOnInfiniteCanvas.top > 0) {
            int iMin = Math.min(rectLimitRectOnInfiniteCanvas.bottom / 100, this.mCurveDataPool.length - 1);
            this.mReadWriteLock.readLock().lock();
            for (int i = rectLimitRectOnInfiniteCanvas.top / 100; i <= iMin; i++) {
                collectIntersectCurves(hashSet, this.mCurveDataPool[i].mDataCurveList, rectF);
            }
            this.mReadWriteLock.readLock().unlock();
        } else if (z2) {
            int i2 = rectLimitRectOnInfiniteCanvas.top / 100;
            int i3 = rectLimitRectOnInfiniteCanvas.bottom / 100;
            this.mReadWriteLock.readLock().lock();
            int i4 = i2 < 0 ? -i2 : 0;
            if (i3 < 0) {
                i = i3 < 0 ? -i3 : 0;
                if (i <= i4) {
                    while (i <= i4) {
                        collectIntersectCurves(hashSet, this.mNegativeCurveDataPool[i].mDataCurveList, rectF);
                        i++;
                    }
                }
            } else {
                for (int i5 = 0; i5 <= i3; i5++) {
                    collectIntersectCurves(hashSet, this.mCurveDataPool[i5].mDataCurveList, rectF);
                }
                for (int i6 = 0; i6 <= i4; i6++) {
                    collectIntersectCurves(hashSet, this.mNegativeCurveDataPool[i6].mDataCurveList, rectF);
                }
            }
            this.mReadWriteLock.readLock().unlock();
        }
        ArrayList arrayList = new ArrayList(hashSet);
        arrayList.sort(new PreCurveDataManager$$ExternalSyntheticLambda1());
        if (z) {
            batchExecCurveGenTask(arrayList);
        }
        return arrayList;
    }

    private void collectIntersectCurves(HashSet<DataCurve> hashSet, HashSet<DataCurve> hashSet2, RectF rectF) {
        for (DataCurve dataCurve : hashSet2) {
            RectF saveRect = dataCurve.getSaveRect();
            dataCurve.translateRectByPenPoint(saveRect, false);
            if (RectF.intersects(saveRect, rectF)) {
                hashSet.add(dataCurve);
            }
        }
    }

    @Override // xc.engine.core.graphics.curve.ICurveDataManager
    public List<List<DataCurve>> getIntersectingCurves(RectF rectF) {
        ArrayList arrayList;
        boolean z = this.mPenEngine.mCanvasOperator.isApplyingInfiniteCanvas;
        Rect rectLimitRectOnInfiniteCanvas = z ? limitRectOnInfiniteCanvas(rectF) : limitRect(rectF);
        HashSet<DataCurve> hashSet = new HashSet<>();
        if (rectLimitRectOnInfiniteCanvas.top > 0) {
            int iMin = Math.min(rectLimitRectOnInfiniteCanvas.bottom / 100, this.mCurveDataPool.length - 1);
            this.mReadWriteLock.readLock().lock();
            for (int i = rectLimitRectOnInfiniteCanvas.top / 100; i <= iMin; i++) {
                collectNoDeletedIntersectCurves(hashSet, this.mCurveDataPool[i].mDataCurveList, rectF);
            }
            this.mReadWriteLock.readLock().unlock();
        } else if (z) {
            int i2 = rectLimitRectOnInfiniteCanvas.top / 100;
            int i3 = rectLimitRectOnInfiniteCanvas.bottom / 100;
            this.mReadWriteLock.readLock().lock();
            int i4 = i2 < 0 ? -i2 : 0;
            if (i3 < 0) {
                int i5 = i3 < 0 ? -i3 : 0;
                if (i5 <= i4) {
                    while (i5 <= i4) {
                        collectNoDeletedIntersectCurves(hashSet, this.mNegativeCurveDataPool[i5].mDataCurveList, rectF);
                        i5++;
                    }
                }
            } else {
                for (int i6 = 0; i6 <= i3; i6++) {
                    collectNoDeletedIntersectCurves(hashSet, this.mCurveDataPool[i6].mDataCurveList, rectF);
                }
                for (int i7 = 0; i7 <= i4; i7++) {
                    collectNoDeletedIntersectCurves(hashSet, this.mNegativeCurveDataPool[i7].mDataCurveList, rectF);
                }
            }
            this.mReadWriteLock.readLock().unlock();
        }
        ArrayList arrayList2 = new ArrayList(hashSet);
        Collections.sort(arrayList2, new PreCurveDataManager$$ExternalSyntheticLambda1());
        batchExecCurveGenTask(arrayList2);
        ArrayList arrayList3 = new ArrayList();
        if (arrayList2.size() > 0) {
            arrayList = new ArrayList();
            arrayList.add((DataCurve) arrayList2.get(0));
            arrayList3.add(arrayList);
        } else {
            arrayList = null;
        }
        for (int i8 = 1; i8 < arrayList2.size(); i8++) {
            DataCurve dataCurve = (DataCurve) arrayList2.get(i8);
            if (dataCurve.getType() == ((DataCurve) arrayList.get(0)).getType()) {
                arrayList.add(dataCurve);
            } else {
                arrayList = new ArrayList();
                arrayList.add(dataCurve);
                arrayList3.add(arrayList);
            }
        }
        return arrayList3;
    }

    @Override // xc.engine.core.graphics.curve.ICurveDataManager
    public List<DataCurve> getDesiredCurvesInArea(RectF rectF, Predicate<DataCurve> predicate) {
        boolean z = this.mPenEngine.mCanvasOperator.isApplyingInfiniteCanvas;
        Rect rectLimitRectOnInfiniteCanvas = z ? limitRectOnInfiniteCanvas(rectF) : limitRect(rectF);
        HashSet<DataCurve> hashSet = new HashSet<>();
        if (rectLimitRectOnInfiniteCanvas.top > 0) {
            int iMin = Math.min(rectLimitRectOnInfiniteCanvas.bottom / 100, this.mCurveDataPool.length - 1);
            this.mReadWriteLock.readLock().lock();
            for (int i = rectLimitRectOnInfiniteCanvas.top / 100; i <= iMin; i++) {
                collectNoDeletedIntersectCurves(hashSet, this.mCurveDataPool[i].mDataCurveList, rectF, predicate);
            }
            this.mReadWriteLock.readLock().unlock();
        } else if (z) {
            int i2 = rectLimitRectOnInfiniteCanvas.top / 100;
            int i3 = rectLimitRectOnInfiniteCanvas.bottom / 100;
            this.mReadWriteLock.readLock().lock();
            int i4 = i2 < 0 ? -i2 : 0;
            if (i3 < 0) {
                int i5 = -i3;
                if (i5 <= i4) {
                    while (i5 <= i4) {
                        collectNoDeletedIntersectCurves(hashSet, this.mNegativeCurveDataPool[i5].mDataCurveList, rectF, predicate);
                        i5++;
                    }
                }
            } else {
                for (int i6 = 0; i6 <= i3; i6++) {
                    collectNoDeletedIntersectCurves(hashSet, this.mCurveDataPool[i6].mDataCurveList, rectF, predicate);
                }
                for (int i7 = 0; i7 <= i4; i7++) {
                    collectNoDeletedIntersectCurves(hashSet, this.mNegativeCurveDataPool[i7].mDataCurveList, rectF, predicate);
                }
            }
            this.mReadWriteLock.readLock().unlock();
        }
        ArrayList arrayList = new ArrayList(hashSet);
        arrayList.sort(Comparator.comparingLong(new ToLongFunction() { // from class: com.miui.libhandwritingpreview.manager.PreCurveDataManager$$ExternalSyntheticLambda2
            @Override // java.util.function.ToLongFunction
            public final long applyAsLong(Object obj) {
                return ((DataCurve) obj).getTime();
            }
        }).thenComparing(new Function() { // from class: com.miui.libhandwritingpreview.manager.PreCurveDataManager$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Integer.valueOf(((DataCurve) obj).mId);
            }
        }));
        batchExecCurveGenTask(arrayList);
        return arrayList;
    }

    @Override // xc.engine.core.graphics.curve.ICurveDataManager
    public boolean doesAreaHasCurve(RectF rectF, Predicate<DataCurve> predicate) {
        boolean z = this.mPenEngine.mCanvasOperator.isApplyingInfiniteCanvas;
        Rect rectLimitRectOnInfiniteCanvas = z ? limitRectOnInfiniteCanvas(rectF) : limitRect(rectF);
        if (rectLimitRectOnInfiniteCanvas.top > 0) {
            int iMin = Math.min(rectLimitRectOnInfiniteCanvas.bottom / 100, this.mCurveDataPool.length - 1);
            this.mReadWriteLock.readLock().lock();
            for (int i = rectLimitRectOnInfiniteCanvas.top / 100; i <= iMin; i++) {
                if (hasNoDeletedIntersectCurve(this.mCurveDataPool[i].mDataCurveList, rectF, predicate)) {
                    this.mReadWriteLock.readLock().unlock();
                    return true;
                }
            }
            this.mReadWriteLock.readLock().unlock();
        } else if (z) {
            int i2 = rectLimitRectOnInfiniteCanvas.top / 100;
            int i3 = rectLimitRectOnInfiniteCanvas.bottom / 100;
            this.mReadWriteLock.readLock().lock();
            int i4 = i2 < 0 ? -i2 : 0;
            if (i3 < 0) {
                int i5 = -i3;
                if (i5 <= i4) {
                    while (i5 <= i4) {
                        if (hasNoDeletedIntersectCurve(this.mNegativeCurveDataPool[i5].mDataCurveList, rectF, predicate)) {
                            this.mReadWriteLock.readLock().unlock();
                            return true;
                        }
                        i5++;
                    }
                }
            } else {
                for (int i6 = 0; i6 <= i3; i6++) {
                    if (hasNoDeletedIntersectCurve(this.mCurveDataPool[i6].mDataCurveList, rectF, predicate)) {
                        this.mReadWriteLock.readLock().unlock();
                        return true;
                    }
                }
                for (int i7 = 0; i7 <= i4; i7++) {
                    if (hasNoDeletedIntersectCurve(this.mNegativeCurveDataPool[i7].mDataCurveList, rectF, predicate)) {
                        this.mReadWriteLock.readLock().unlock();
                        return true;
                    }
                }
            }
            this.mReadWriteLock.readLock().unlock();
        }
        return false;
    }

    @Override // xc.engine.core.graphics.curve.ICurveDataManager
    public List<DataCurve> getCurvesInArea(RectF rectF) {
        boolean z = this.mPenEngine.mCanvasOperator.isApplyingInfiniteCanvas;
        Rect rectLimitRectOnInfiniteCanvas = z ? limitRectOnInfiniteCanvas(rectF) : limitRect(rectF);
        HashSet hashSet = new HashSet();
        if (rectLimitRectOnInfiniteCanvas.top > 0) {
            int iMax = Math.max(rectLimitRectOnInfiniteCanvas.top / 100, 0);
            int iMin = Math.min(rectLimitRectOnInfiniteCanvas.bottom / 100, this.mCurveDataPool.length - 1);
            Log.d(this.TAG, String.format("getCurvesInArea: %d %d", Integer.valueOf(iMax), Integer.valueOf(iMin)));
            this.mReadWriteLock.readLock().lock();
            while (iMax <= iMin) {
                hashSet.addAll(this.mCurveDataPool[iMax].mDataCurveList);
                iMax++;
            }
            this.mReadWriteLock.readLock().unlock();
        } else if (z) {
            int i = rectLimitRectOnInfiniteCanvas.top / 100;
            int i2 = rectLimitRectOnInfiniteCanvas.bottom / 100;
            this.mReadWriteLock.readLock().lock();
            int i3 = i < 0 ? -i : 0;
            if (i2 < 0) {
                int i4 = -i2;
                if (i4 <= i3) {
                    while (i4 <= i3) {
                        hashSet.addAll(this.mNegativeCurveDataPool[i4].mDataCurveList);
                        i4++;
                    }
                }
            } else {
                for (int i5 = 0; i5 <= i2; i5++) {
                    hashSet.addAll(this.mCurveDataPool[i5].mDataCurveList);
                }
                for (int i6 = 0; i6 <= i3; i6++) {
                    hashSet.addAll(this.mNegativeCurveDataPool[i6].mDataCurveList);
                }
            }
            this.mReadWriteLock.readLock().unlock();
        }
        ArrayList arrayList = new ArrayList(hashSet);
        arrayList.sort(new PreCurveDataManager$$ExternalSyntheticLambda0());
        return arrayList;
    }

    private boolean hasNoDeletedIntersectCurve(HashSet<DataCurve> hashSet, RectF rectF, Predicate<DataCurve> predicate) {
        for (DataCurve dataCurve : hashSet) {
            RectF saveRect = dataCurve.getSaveRect();
            dataCurve.translateRectByPenPoint(saveRect, false);
            if (RectF.intersects(saveRect, rectF) && dataCurve.mDataState != 3 && predicate.test(dataCurve)) {
                return true;
            }
        }
        return false;
    }

    private void collectNoDeletedIntersectCurves(HashSet<DataCurve> hashSet, HashSet<DataCurve> hashSet2, RectF rectF, Predicate<DataCurve> predicate) {
        for (DataCurve dataCurve : hashSet2) {
            RectF saveRect = dataCurve.getSaveRect();
            dataCurve.translateRectByPenPoint(saveRect, false);
            if (RectF.intersects(saveRect, rectF) && dataCurve.mDataState != 3 && predicate.test(dataCurve)) {
                hashSet.add(dataCurve);
            }
        }
    }

    private boolean hasNoDeletedIntersectCurve(HashSet<DataCurve> hashSet, RectF rectF) {
        for (DataCurve dataCurve : hashSet) {
            RectF saveRect = dataCurve.getSaveRect();
            dataCurve.translateRectByPenPoint(saveRect, false);
            if (RectF.intersects(saveRect, rectF) && dataCurve.mDataState != 3) {
                return true;
            }
        }
        return false;
    }

    private void collectNoDeletedIntersectCurves(HashSet<DataCurve> hashSet, HashSet<DataCurve> hashSet2, RectF rectF) {
        for (DataCurve dataCurve : hashSet2) {
            RectF saveRect = dataCurve.getSaveRect();
            dataCurve.translateRectByPenPoint(saveRect, false);
            if (RectF.intersects(saveRect, rectF) && dataCurve.mDataState != 3) {
                hashSet.add(dataCurve);
            }
        }
    }

    public static int curveCompare(DataCurve dataCurve, DataCurve dataCurve2) {
        return Integer.compare(dataCurve.mId, dataCurve2.mId);
    }
}
