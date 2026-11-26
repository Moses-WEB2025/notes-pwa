package xc.engine.operate.step.type;

import android.graphics.PointF;
import android.graphics.RectF;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.List;
import xc.engine.api.PenEngine;
import xc.engine.data.pagedata.DataCurve;

/* JADX INFO: loaded from: classes6.dex */
public class StepEditClear extends StepEditCurve implements IStep {
    public int[] dataCountsArray;
    public PenEngine engine;
    public final List<DataCurve> mDataList;
    private int pageIndex;
    public final RectF saveRect;

    public StepEditClear(PenEngine penEngine, int i) {
        super(penEngine);
        this.mDataList = new ArrayList(0);
        this.saveRect = new RectF();
        this.engine = penEngine;
        this.pageIndex = i;
    }

    @Override // xc.engine.operate.step.type.IStep
    public int getPageIndex() {
        return this.pageIndex;
    }

    @Override // xc.engine.operate.step.type.IStep
    public void setPageIndex(int i) {
        PointF deltaOffsetWS = this.engine.getCurveEngine().getPageSystem().getDeltaOffsetWS(getPageIndex(), i);
        float saveRatio = this.engine.mCanvasOperator.getSaveRatio();
        this.saveRect.offset(deltaOffsetWS.x * saveRatio, deltaOffsetWS.y * saveRatio);
        this.pageIndex = i;
    }

    @Override // xc.engine.operate.step.type.IStep
    public void resetContext(PenEngine penEngine) {
        this.engine = penEngine;
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
        if (list == null || list.isEmpty()) {
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
        for (DataCurve dataCurve : this.mDataList) {
            dataCurve.mDataState = 1;
            this.engine.mDataAccess.getDataSet().updateVisibleCount(dataCurve, false, dataCurve.pageIndex);
        }
        this.engine.mCurveDataManager.addDataAndGetRectF(this.mDataList);
        syncScaleState(this.engine, this.mDataList);
        return new RectF(this.saveRect);
    }

    @Override // xc.engine.operate.step.type.IStep
    public RectF redo() {
        this.engine.mCurveDataManager.removeDataAndGetRectF(this.mDataList);
        for (DataCurve dataCurve : this.mDataList) {
            dataCurve.mDataState = 3;
            this.engine.mDataAccess.getDataSet().updateDeleteCount(dataCurve, dataCurve.pageIndex);
        }
        return new RectF(this.saveRect);
    }
}
