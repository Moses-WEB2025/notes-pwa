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
public class StepDeletePoint extends StepEditCurve implements IStep {
    private static final String TAG = "StepDeletePoint";
    public PenEngine mPenEngine;
    public int[] newDataID;
    public final List<DataCurve> newDataList;
    public int[] oldDataID;
    public final List<DataCurve> oldDataList;
    public RectF updateRect;

    public StepDeletePoint(PenEngine penEngine, List list, List list2) {
        super(penEngine);
        this.oldDataList = new ArrayList(list);
        this.newDataList = new ArrayList(list2);
        this.mPenEngine = penEngine;
        calcRect();
    }

    @Override // xc.engine.operate.step.type.IStep
    public int getPageIndex() {
        if (this.oldDataList.isEmpty()) {
            return 0;
        }
        return this.oldDataList.get(0).pageIndex;
    }

    @Override // xc.engine.operate.step.type.IStep
    public void setPageIndex(int i) {
        PointF deltaOffsetWS = this.mPenEngine.getCurveEngine().getPageSystem().getDeltaOffsetWS(getPageIndex(), i);
        float saveRatio = this.mPenEngine.mCanvasOperator.getSaveRatio();
        this.updateRect.offset(deltaOffsetWS.x * saveRatio, deltaOffsetWS.y * saveRatio);
    }

    @Override // xc.engine.operate.step.type.IStep
    public void resetContext(PenEngine penEngine) {
        this.mPenEngine = penEngine;
    }

    @Override // xc.engine.operate.step.type.IStep
    public void unserialize(SparseArray<DataCurve> sparseArray) {
        Log.d(TAG, "unserialize ");
        int[] iArr = this.oldDataID;
        if (iArr != null && iArr.length > 0) {
            this.oldDataList.clear();
            int[] iArr2 = this.oldDataID;
            int length = iArr2.length;
            for (int i = 0; i < length; i++) {
                int i2 = iArr2[i];
                this.oldDataList.add(i2 == Integer.MIN_VALUE ? null : sparseArray.get(i2));
            }
        }
        int[] iArr3 = this.newDataID;
        if (iArr3 == null || iArr3.length <= 0) {
            return;
        }
        this.newDataList.clear();
        int[] iArr4 = this.newDataID;
        int length2 = iArr4.length;
        for (int i3 = 0; i3 < length2; i3++) {
            int i4 = iArr4[i3];
            this.newDataList.add(i4 == Integer.MIN_VALUE ? null : sparseArray.get(i4));
        }
    }

    @Override // xc.engine.operate.step.type.IStep
    public void serialize() {
        Log.d(TAG, "serialize ");
        List<DataCurve> list = this.oldDataList;
        if (list != null && !list.isEmpty()) {
            this.oldDataID = new int[this.oldDataList.size()];
            for (int i = 0; i < this.oldDataList.size(); i++) {
                DataCurve dataCurve = this.oldDataList.get(i);
                this.oldDataID[i] = dataCurve == null ? Integer.MIN_VALUE : dataCurve.mCount;
            }
            this.oldDataList.clear();
        }
        List<DataCurve> list2 = this.newDataList;
        if (list2 == null || list2.isEmpty()) {
            return;
        }
        this.newDataID = new int[this.newDataList.size()];
        for (int i2 = 0; i2 < this.newDataList.size(); i2++) {
            DataCurve dataCurve2 = this.newDataList.get(i2);
            this.newDataID[i2] = dataCurve2 == null ? Integer.MIN_VALUE : dataCurve2.mCount;
        }
        this.newDataList.clear();
    }

    @Override // xc.engine.operate.step.type.IStep
    public RectF undo() {
        Log.d(TAG, " undo ");
        for (DataCurve dataCurve : this.newDataList) {
            dataCurve.mDataState = 3;
            this.mPenEngine.mDataAccess.getDataSet().updateDeleteCount(dataCurve, dataCurve.pageIndex);
        }
        RectF rectFRemoveDataAndGetRectF = this.mPenEngine.mCurveDataManager.removeDataAndGetRectF(this.newDataList);
        float saveRatio = this.mPenEngine.mCanvasOperator.getSaveRatio();
        rectFRemoveDataAndGetRectF.left *= saveRatio;
        rectFRemoveDataAndGetRectF.top *= saveRatio;
        rectFRemoveDataAndGetRectF.right *= saveRatio;
        rectFRemoveDataAndGetRectF.bottom *= saveRatio;
        for (DataCurve dataCurve2 : this.oldDataList) {
            dataCurve2.mDataState = 1;
            this.mPenEngine.mDataAccess.getDataSet().updateVisibleCount(dataCurve2, false, dataCurve2.pageIndex);
        }
        updateCurvesForScale(this.mPenEngine, this.oldDataList);
        RectF rectFAddDataAndGetRectF = this.mPenEngine.mCurveDataManager.addDataAndGetRectF(this.oldDataList);
        rectFAddDataAndGetRectF.left *= saveRatio;
        rectFAddDataAndGetRectF.top *= saveRatio;
        rectFAddDataAndGetRectF.right *= saveRatio;
        rectFAddDataAndGetRectF.bottom *= saveRatio;
        if (this.mIsScale && !this.updateRect.isEmpty()) {
            rectFRemoveDataAndGetRectF.union(this.updateRect);
        }
        updatePageModify();
        if (!this.mIsScale) {
            rectFRemoveDataAndGetRectF = rectFAddDataAndGetRectF;
        }
        return new RectF(rectFRemoveDataAndGetRectF);
    }

    @Override // xc.engine.operate.step.type.IStep
    public RectF redo() {
        Log.d(TAG, " redo ");
        for (DataCurve dataCurve : this.oldDataList) {
            dataCurve.mDataState = 3;
            this.mPenEngine.mDataAccess.getDataSet().updateDeleteCount(dataCurve, dataCurve.pageIndex);
        }
        RectF rectFRemoveDataAndGetRectF = this.mPenEngine.mCurveDataManager.removeDataAndGetRectF(this.oldDataList);
        float saveRatio = this.mPenEngine.mCanvasOperator.getSaveRatio();
        rectFRemoveDataAndGetRectF.left *= saveRatio;
        rectFRemoveDataAndGetRectF.top *= saveRatio;
        rectFRemoveDataAndGetRectF.right *= saveRatio;
        rectFRemoveDataAndGetRectF.bottom *= saveRatio;
        for (DataCurve dataCurve2 : this.newDataList) {
            dataCurve2.mDataState = 1;
            this.mPenEngine.mDataAccess.getDataSet().updateVisibleCount(dataCurve2, false, dataCurve2.pageIndex);
        }
        updateCurvesForScale(this.mPenEngine, this.newDataList);
        RectF rectFAddDataAndGetRectF = this.mPenEngine.mCurveDataManager.addDataAndGetRectF(this.newDataList);
        rectFAddDataAndGetRectF.left *= saveRatio;
        rectFAddDataAndGetRectF.top *= saveRatio;
        rectFAddDataAndGetRectF.right *= saveRatio;
        rectFAddDataAndGetRectF.bottom *= saveRatio;
        if (this.mIsScale && !this.updateRect.isEmpty()) {
            rectFRemoveDataAndGetRectF.union(this.updateRect);
        }
        updatePageModify();
        if (!this.mIsScale) {
            rectFRemoveDataAndGetRectF = rectFAddDataAndGetRectF;
        }
        return new RectF(rectFRemoveDataAndGetRectF);
    }

    private void calcRect() {
        Log.d(TAG, " calcRect ");
        DataCurve dataCurve = this.oldDataList.get(0);
        RectF saveRect = dataCurve.getSaveRect();
        dataCurve.translateRectByPenPoint(saveRect, false);
        this.mPenEngine.mDataAccess.getDataSet().updateDeleteCount(dataCurve, getPageIndex());
        for (int i = 1; i < this.oldDataList.size(); i++) {
            RectF saveRect2 = this.oldDataList.get(i).getSaveRect();
            this.oldDataList.get(i).translateRectByPenPoint(saveRect2, false);
            saveRect.union(saveRect2);
            this.mPenEngine.mDataAccess.getDataSet().updateDeleteCount(this.oldDataList.get(i), getPageIndex());
        }
        this.updateRect = saveRect;
        for (DataCurve dataCurve2 : this.newDataList) {
            this.mPenEngine.mDataAccess.getDataSet().updateVisibleCount(dataCurve2, true, dataCurve2.pageIndex);
        }
    }

    private void updatePageModify() {
        if (this.newDataList.get(0).pageIndex == getPageIndex() || this.mPenEngine.mEditOperator.mEditListener == null) {
            return;
        }
        this.mPenEngine.mEditOperator.mEditListener.onStepChanged(9, this.newDataList.get(0).pageIndex);
    }
}
