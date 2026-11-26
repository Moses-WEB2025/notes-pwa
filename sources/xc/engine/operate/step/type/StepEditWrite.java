package xc.engine.operate.step.type;

import android.graphics.RectF;
import android.util.Log;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import xc.engine.api.PenEngine;
import xc.engine.data.pagedata.DataCurve;

/* JADX INFO: loaded from: classes6.dex */
public class StepEditWrite extends StepEditCurve implements IStep {
    private static final String TAG = "StepEditWrite";
    public int[] dataCountsArray;
    public final int e;
    public RectF mCurveRectF;
    public final List<DataCurve> mDataCurveList;
    public PenEngine mPenEngine;

    @Override // xc.engine.operate.step.type.IStep
    public void setPageIndex(int i) {
    }

    public StepEditWrite(PenEngine penEngine, DataCurve dataCurve, int i) {
        super(penEngine);
        ArrayList arrayList = new ArrayList(1);
        this.mDataCurveList = arrayList;
        arrayList.add(dataCurve);
        this.e = i;
        this.mPenEngine = penEngine;
        calculateCurveRect();
    }

    @Override // xc.engine.operate.step.type.IStep
    public int getPageIndex() {
        if (this.mDataCurveList.isEmpty()) {
            return 0;
        }
        return this.mDataCurveList.get(0).pageIndex;
    }

    public StepEditWrite(PenEngine penEngine, List<DataCurve> list, int i) {
        super(penEngine);
        this.mDataCurveList = new ArrayList(list);
        this.e = i;
        this.mPenEngine = penEngine;
        calculateCurveRect();
    }

    @Override // xc.engine.operate.step.type.IStep
    public void resetContext(PenEngine penEngine) {
        this.mPenEngine = penEngine;
    }

    @Override // xc.engine.operate.step.type.IStep
    public void unserialize(SparseArray<DataCurve> sparseArray) {
        int[] iArr = this.dataCountsArray;
        if (iArr == null || iArr.length == 0) {
            return;
        }
        this.mDataCurveList.clear();
        for (int i : this.dataCountsArray) {
            if (i == Integer.MIN_VALUE) {
                this.mDataCurveList.add(null);
            } else {
                this.mDataCurveList.add(sparseArray.get(i));
            }
        }
    }

    @Override // xc.engine.operate.step.type.IStep
    public void serialize() {
        List<DataCurve> list = this.mDataCurveList;
        if (list == null || list.isEmpty()) {
            return;
        }
        this.dataCountsArray = new int[this.mDataCurveList.size()];
        for (int i = 0; i < this.mDataCurveList.size(); i++) {
            DataCurve dataCurve = this.mDataCurveList.get(i);
            if (dataCurve == null) {
                this.dataCountsArray[i] = Integer.MIN_VALUE;
            } else {
                this.dataCountsArray[i] = dataCurve.mCount;
            }
        }
        this.mDataCurveList.clear();
    }

    @Override // xc.engine.operate.step.type.IStep
    public RectF undo() {
        Log.d(TAG, "undo ");
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.mPenEngine.mCurveDataManager.removeDataAndGetRectF(this.mDataCurveList);
        Iterator<DataCurve> it = this.mDataCurveList.iterator();
        while (it.hasNext()) {
            it.next().mDataState = 3;
        }
        calculateCurveRect();
        Log.d(TAG, "undo cost " + (System.currentTimeMillis() - jCurrentTimeMillis));
        return new RectF(this.mCurveRectF);
    }

    @Override // xc.engine.operate.step.type.IStep
    public RectF redo() {
        Log.d(TAG, "redo ");
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.mPenEngine.mCurveDataManager.addDataAndGetRectF(this.mDataCurveList);
        Iterator<DataCurve> it = this.mDataCurveList.iterator();
        while (it.hasNext()) {
            it.next().mDataState = 1;
        }
        calculateCurveRect();
        syncScaleState(this.mPenEngine, this.mDataCurveList);
        Log.d(TAG, "redo cost " + (System.currentTimeMillis() - jCurrentTimeMillis));
        return new RectF(this.mCurveRectF);
    }

    private void updateContent(DataCurve dataCurve, int i) {
        if (i == 1) {
            this.mPenEngine.mDataAccess.getDataSet().updateVisibleCount(dataCurve, false, dataCurve.pageIndex);
        } else if (i == 3) {
            this.mPenEngine.mDataAccess.getDataSet().updateDeleteCount(dataCurve, dataCurve.pageIndex);
        } else {
            this.mPenEngine.mDataAccess.getDataSet().updateVisibleCount(dataCurve, true, dataCurve.pageIndex);
        }
    }

    private void calculateCurveRect() {
        if (this.mDataCurveList.isEmpty()) {
            return;
        }
        this.mCurveRectF = this.mDataCurveList.get(0).getSaveRect();
        this.mDataCurveList.get(0).translateRectByPenPoint(this.mCurveRectF, false);
        updateContent(this.mDataCurveList.get(0), this.mDataCurveList.get(0).getDataState());
        for (int i = 1; i < this.mDataCurveList.size(); i++) {
            RectF saveRect = this.mDataCurveList.get(i).getSaveRect();
            this.mDataCurveList.get(i).translateRectByPenPoint(saveRect, false);
            this.mCurveRectF.union(saveRect);
            updateContent(this.mDataCurveList.get(i), this.mDataCurveList.get(i).getDataState());
        }
    }
}
