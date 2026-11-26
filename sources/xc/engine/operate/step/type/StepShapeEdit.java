package xc.engine.operate.step.type;

import android.graphics.PointF;
import android.graphics.RectF;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.List;
import xc.engine.api.PenEngine;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.operate.canvas.draw.scale.ScaleInfo;

/* JADX INFO: loaded from: classes6.dex */
public class StepShapeEdit implements IStep {
    public PointF centerPoint;
    public int[] dataCountsArray;
    public List<DataCurve> mDataCurveList;
    public PenEngine mPenEngine;
    public PointF ratioPoint;
    public RectF ratioRect;

    public StepShapeEdit(PenEngine penEngine, List<DataCurve> list, RectF rectF, PointF pointF, PointF pointF2) {
        this.mPenEngine = penEngine;
        this.mDataCurveList = new ArrayList(list);
        this.ratioRect = new RectF(rectF);
        this.ratioPoint = new PointF(pointF.x, pointF.y);
        this.centerPoint = new PointF(pointF2.x, pointF2.y);
    }

    @Override // xc.engine.operate.step.type.IStep
    public int getPageIndex() {
        if (this.mDataCurveList.isEmpty()) {
            return 0;
        }
        return this.mDataCurveList.get(0).pageIndex;
    }

    @Override // xc.engine.operate.step.type.IStep
    public void setPageIndex(int i) {
        PointF deltaOffsetWS = this.mPenEngine.getCurveEngine().getPageSystem().getDeltaOffsetWS(getPageIndex(), i);
        float saveRatio = this.mPenEngine.mCanvasOperator.getSaveRatio();
        this.ratioRect.offset(deltaOffsetWS.x * saveRatio, deltaOffsetWS.y * saveRatio);
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
        List<DataCurve> list = this.mDataCurveList;
        if (list == null) {
            this.mDataCurveList = new ArrayList();
        } else {
            list.clear();
        }
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
        this.mPenEngine.mCurveDataManager.removeDataAndGetRectF(this.mDataCurveList);
        PenEngine penEngine = this.mPenEngine;
        List<DataCurve> list = this.mDataCurveList;
        PointF pointF = new PointF(this.ratioPoint.x / this.mPenEngine.mCanvasOperator.getSaveRatio(), this.ratioPoint.y / this.mPenEngine.mCanvasOperator.getSaveRatio());
        PointF pointF2 = this.centerPoint;
        PointF pointF3 = new PointF(1.0f / pointF2.x, 1.0f / pointF2.y);
        boolean zIsScale = this.mPenEngine.mTouchEventOperator.isScale();
        ScaleInfo scaleInfo = this.mPenEngine.mCanvasOperator.getScaleInfo();
        for (DataCurve dataCurve : list) {
            dataCurve.scale(pointF, pointF3);
            if (zIsScale) {
                dataCurve.mScaleRate = scaleInfo.getScaleRate();
                dataCurve.mBasePoint.set(0.0f, 0.0f);
                penEngine.mCurveDataManager.executeScalableCurveGenTask(dataCurve);
            } else {
                penEngine.mCurveDataManager.executeCurveGenTask(dataCurve);
            }
        }
        this.mPenEngine.mCurveDataManager.addDataAndGetRectF(this.mDataCurveList);
        return this.ratioRect;
    }

    @Override // xc.engine.operate.step.type.IStep
    public RectF redo() {
        this.mPenEngine.mCurveDataManager.removeDataAndGetRectF(this.mDataCurveList);
        PenEngine penEngine = this.mPenEngine;
        List<DataCurve> list = this.mDataCurveList;
        PointF pointF = new PointF(this.ratioPoint.x / this.mPenEngine.mCanvasOperator.getSaveRatio(), this.ratioPoint.y / this.mPenEngine.mCanvasOperator.getSaveRatio());
        PointF pointF2 = this.centerPoint;
        boolean zIsScale = this.mPenEngine.mTouchEventOperator.isScale();
        ScaleInfo scaleInfo = this.mPenEngine.mCanvasOperator.getScaleInfo();
        for (DataCurve dataCurve : list) {
            dataCurve.scale(pointF, pointF2);
            if (zIsScale) {
                dataCurve.mScaleRate = scaleInfo.getScaleRate();
                dataCurve.mBasePoint.set(0.0f, 0.0f);
                penEngine.mCurveDataManager.executeScalableCurveGenTask(dataCurve);
            } else {
                penEngine.mCurveDataManager.executeCurveGenTask(dataCurve);
            }
        }
        this.mPenEngine.mCurveDataManager.addDataAndGetRectF(this.mDataCurveList);
        return this.ratioRect;
    }
}
