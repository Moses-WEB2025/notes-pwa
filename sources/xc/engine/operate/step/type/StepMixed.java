package xc.engine.operate.step.type;

import android.graphics.PointF;
import android.graphics.RectF;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.List;
import xc.engine.api.PenEngine;
import xc.engine.core.graphics.curve.CurveDataListManager;
import xc.engine.data.pagedata.DataCurve;

/* JADX INFO: loaded from: classes6.dex */
public class StepMixed implements IStep {
    public int[] c;
    public float degrees;
    public List<DataCurve> mDataCurves;
    public PenEngine mPenEngine;
    public PointF ratioPoint;
    public RectF ratioRect;
    public float scale;

    public StepMixed(PenEngine penEngine, List<DataCurve> list, RectF rectF, PointF pointF, float f, float f2) {
        this.mPenEngine = penEngine;
        this.mDataCurves = new ArrayList(list);
        this.ratioRect = new RectF(rectF);
        this.ratioPoint = new PointF(pointF.x, pointF.y);
        this.scale = f;
        this.degrees = f2;
    }

    @Override // xc.engine.operate.step.type.IStep
    public int getPageIndex() {
        if (this.mDataCurves.isEmpty()) {
            return 0;
        }
        return this.mDataCurves.get(0).pageIndex;
    }

    @Override // xc.engine.operate.step.type.IStep
    public void setPageIndex(int i) {
        PointF deltaOffsetWS = this.mPenEngine.getCurveEngine().getPageSystem().getDeltaOffsetWS(getPageIndex(), i);
        float saveRatio = this.mPenEngine.mCanvasOperator.getSaveRatio();
        this.ratioPoint.offset(deltaOffsetWS.x * saveRatio, deltaOffsetWS.y * saveRatio);
        this.ratioRect.offset(deltaOffsetWS.x * saveRatio, deltaOffsetWS.y * saveRatio);
    }

    @Override // xc.engine.operate.step.type.IStep
    public void resetContext(PenEngine penEngine) {
        this.mPenEngine = penEngine;
    }

    @Override // xc.engine.operate.step.type.IStep
    public void unserialize(SparseArray<DataCurve> sparseArray) {
        int[] iArr = this.c;
        if (iArr == null || iArr.length == 0) {
            return;
        }
        List<DataCurve> list = this.mDataCurves;
        if (list == null) {
            this.mDataCurves = new ArrayList();
        } else {
            list.clear();
        }
        for (int i : this.c) {
            if (i == Integer.MIN_VALUE) {
                this.mDataCurves.add(null);
            } else {
                this.mDataCurves.add(sparseArray.get(i));
            }
        }
    }

    @Override // xc.engine.operate.step.type.IStep
    public void serialize() {
        List<DataCurve> list = this.mDataCurves;
        if (list == null || list.isEmpty()) {
            return;
        }
        this.c = new int[this.mDataCurves.size()];
        for (int i = 0; i < this.mDataCurves.size(); i++) {
            DataCurve dataCurve = this.mDataCurves.get(i);
            if (dataCurve == null) {
                this.c[i] = Integer.MIN_VALUE;
            } else {
                this.c[i] = dataCurve.mCount;
            }
        }
        this.mDataCurves.clear();
    }

    @Override // xc.engine.operate.step.type.IStep
    public RectF undo() {
        PenEngine penEngine = this.mPenEngine;
        penEngine.mCurveDataManager.removeDataAndGetRectF(this.mDataCurves);
        CurveDataListManager curveDataListManager = new CurveDataListManager(penEngine);
        float saveRatio = this.ratioPoint.x / penEngine.mCanvasOperator.getSaveRatio();
        float saveRatio2 = this.ratioPoint.y / this.mPenEngine.mCanvasOperator.getSaveRatio();
        curveDataListManager.doRotate(this.mDataCurves, saveRatio, saveRatio2, -this.degrees);
        curveDataListManager.doScale(this.mDataCurves, saveRatio, saveRatio2, 1.0f / this.scale);
        this.mPenEngine.mCurveDataManager.addDataAndGetRectF(this.mDataCurves);
        this.mPenEngine.updateDataCurvePageIndex(this.mDataCurves);
        return this.ratioRect;
    }

    @Override // xc.engine.operate.step.type.IStep
    public RectF redo() {
        this.mPenEngine.mCurveDataManager.removeDataAndGetRectF(this.mDataCurves);
        PenEngine penEngine = this.mPenEngine;
        CurveDataListManager curveDataListManager = new CurveDataListManager(penEngine);
        curveDataListManager.doRotate(this.mDataCurves, this.ratioPoint.x / penEngine.mCanvasOperator.getSaveRatio(), this.ratioPoint.y / this.mPenEngine.mCanvasOperator.getSaveRatio(), this.degrees);
        curveDataListManager.doScale(this.mDataCurves, this.ratioPoint.x / this.mPenEngine.mCanvasOperator.getSaveRatio(), this.ratioPoint.y / this.mPenEngine.mCanvasOperator.getSaveRatio(), this.scale);
        this.mPenEngine.mCurveDataManager.addDataAndGetRectF(this.mDataCurves);
        this.mPenEngine.updateDataCurvePageIndex(this.mDataCurves);
        return this.ratioRect;
    }
}
