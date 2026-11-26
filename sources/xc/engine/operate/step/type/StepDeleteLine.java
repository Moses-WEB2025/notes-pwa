package xc.engine.operate.step.type;

import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.List;
import xc.engine.api.PenEngine;
import xc.engine.data.pagedata.DataCurve;

/* JADX INFO: loaded from: classes6.dex */
public class StepDeleteLine extends StepEditCurve implements IStep {
    private static final String TAG = "StepDeleteLine";
    public int[] dataCountsArray;
    public final List<DataCurve> mDataList;
    public PenEngine mPenEngine;
    public RectF saveRect;

    public StepDeleteLine(PenEngine penEngine, List<DataCurve> list) {
        super(penEngine);
        this.mDataList = new ArrayList(list);
        this.mPenEngine = penEngine;
        b();
    }

    @Override // xc.engine.operate.step.type.IStep
    public int getPageIndex() {
        if (this.mDataList.isEmpty()) {
            return 0;
        }
        return this.mDataList.get(0).pageIndex;
    }

    @Override // xc.engine.operate.step.type.IStep
    public void setPageIndex(int i) {
        PointF deltaOffsetWS = this.mPenEngine.getCurveEngine().getPageSystem().getDeltaOffsetWS(getPageIndex(), i);
        float saveRatio = this.mPenEngine.mCanvasOperator.getSaveRatio();
        this.saveRect.offset(deltaOffsetWS.x * saveRatio, deltaOffsetWS.y * saveRatio);
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
        this.mDataList.clear();
        for (int i : this.dataCountsArray) {
            if (i == Integer.MIN_VALUE) {
                this.mDataList.add(null);
            } else {
                this.mDataList.add(sparseArray.get(i));
            }
        }
    }

    @Override // xc.engine.operate.step.type.IStep
    public void serialize() {
        List<DataCurve> list = this.mDataList;
        if (list == null || list.size() == 0) {
            return;
        }
        this.dataCountsArray = new int[this.mDataList.size()];
        for (int i = 0; i < this.mDataList.size(); i++) {
            DataCurve dataCurve = this.mDataList.get(i);
            if (dataCurve == null) {
                this.dataCountsArray[i] = Integer.MIN_VALUE;
            } else {
                this.dataCountsArray[i] = dataCurve.mCount;
            }
        }
        this.mDataList.clear();
    }

    @Override // xc.engine.operate.step.type.IStep
    public RectF undo() {
        Log.d(TAG, "undo ");
        this.mPenEngine.mCurveDataManager.addDataAndGetRectF(this.mDataList);
        for (int i = 0; i < this.mDataList.size(); i++) {
            this.mDataList.get(i).mDataState = 1;
            this.mPenEngine.mDataAccess.getDataSet().updateVisibleCount(this.mDataList.get(i), false, this.mDataList.get(i).pageIndex);
        }
        syncScaleState(this.mPenEngine, this.mDataList);
        return this.saveRect;
    }

    @Override // xc.engine.operate.step.type.IStep
    public RectF redo() {
        this.mPenEngine.mCurveDataManager.removeDataAndGetRectF(this.mDataList);
        for (int i = 0; i < this.mDataList.size(); i++) {
            this.mDataList.get(i).mDataState = 3;
            this.mPenEngine.mDataAccess.getDataSet().updateDeleteCount(this.mDataList.get(i), this.mDataList.get(i).pageIndex);
        }
        return this.saveRect;
    }

    private void b() {
        DataCurve dataCurve = this.mDataList.get(0);
        RectF saveRect = dataCurve.getSaveRect();
        this.saveRect = saveRect;
        dataCurve.translateRectByPenPoint(saveRect, false);
        this.mPenEngine.mDataAccess.getDataSet().updateDeleteCount(dataCurve, dataCurve.pageIndex);
        for (int i = 1; i < this.mDataList.size(); i++) {
            RectF saveRect2 = this.mDataList.get(i).getSaveRect();
            this.mDataList.get(i).translateRectByPenPoint(saveRect2, false);
            this.saveRect.union(saveRect2);
            this.mPenEngine.mDataAccess.getDataSet().updateDeleteCount(this.mDataList.get(i), this.mDataList.get(i).pageIndex);
        }
    }
}
