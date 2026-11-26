package xc.engine.operate.step.type;

import android.graphics.PointF;
import android.graphics.RectF;
import android.util.SparseArray;
import xc.engine.api.PenEngine;
import xc.engine.core.graphics.curve.CurveDataListManager;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.DataShape;

/* JADX INFO: loaded from: classes6.dex */
public class StepShapeDataEdit implements IStep {
    public DataShape dataShape;
    public float e;
    public PenEngine engine;
    public float f;
    public int mShapeEditType;
    public PointF ratioPoint;
    public RectF ratioRect;

    @Override // xc.engine.operate.step.type.IStep
    public void serialize() {
    }

    public StepShapeDataEdit(PenEngine penEngine, DataShape dataShape, RectF rectF, PointF pointF, float f, float f2, int i) {
        this.engine = penEngine;
        this.dataShape = dataShape;
        this.ratioRect = new RectF(rectF);
        this.ratioPoint = new PointF(pointF.x, pointF.y);
        this.e = f;
        this.f = f2;
        this.mShapeEditType = i;
    }

    @Override // xc.engine.operate.step.type.IStep
    public int getPageIndex() {
        return this.dataShape.pageIndex;
    }

    @Override // xc.engine.operate.step.type.IStep
    public void setPageIndex(int i) {
        PointF deltaOffsetWS = this.engine.getCurveEngine().getPageSystem().getDeltaOffsetWS(getPageIndex(), i);
        float saveRatio = this.engine.mCanvasOperator.getSaveRatio();
        this.ratioRect.offset(deltaOffsetWS.x * saveRatio, deltaOffsetWS.y * saveRatio);
        this.ratioPoint.offset(deltaOffsetWS.x * saveRatio, deltaOffsetWS.y * saveRatio);
    }

    @Override // xc.engine.operate.step.type.IStep
    public void resetContext(PenEngine penEngine) {
        this.engine = penEngine;
    }

    @Override // xc.engine.operate.step.type.IStep
    public void unserialize(SparseArray<DataCurve> sparseArray) {
        DataCurve dataCurve;
        if (sparseArray == null || sparseArray.size() == 0 || (dataCurve = sparseArray.get(this.dataShape.mCount)) == null || !(dataCurve instanceof DataShape)) {
            return;
        }
        this.dataShape = (DataShape) dataCurve;
    }

    @Override // xc.engine.operate.step.type.IStep
    public RectF undo() {
        this.engine.mCurveDataManager.removeDataFromPool(this.dataShape);
        CurveDataListManager curveDataListManager = new CurveDataListManager(this.engine);
        PointF pointF = new PointF(this.ratioPoint.x / this.engine.mCanvasOperator.getSaveRatio(), this.ratioPoint.y / this.engine.mCanvasOperator.getSaveRatio());
        DataShape dataShape = this.dataShape;
        dataShape.O = true;
        curveDataListManager.doShapeEdit(dataShape, this.mShapeEditType, pointF, 1.0f / this.e, -this.f, this.engine.mTouchEventOperator.isScale());
        this.engine.mCurveDataManager.addDataToPool(this.dataShape);
        return this.ratioRect;
    }

    @Override // xc.engine.operate.step.type.IStep
    public RectF redo() {
        this.engine.mCurveDataManager.removeDataFromPool(this.dataShape);
        CurveDataListManager curveDataListManager = new CurveDataListManager(this.engine);
        PointF pointF = new PointF(this.ratioPoint.x / this.engine.mCanvasOperator.getSaveRatio(), this.ratioPoint.y / this.engine.mCanvasOperator.getSaveRatio());
        DataShape dataShape = this.dataShape;
        dataShape.O = true;
        curveDataListManager.doShapeEdit(dataShape, this.mShapeEditType, pointF, this.e, this.f, this.engine.mTouchEventOperator.isScale());
        this.engine.mCurveDataManager.addDataToPool(this.dataShape);
        return this.ratioRect;
    }
}
