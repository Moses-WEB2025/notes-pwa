package xc.engine.operate.step.type;

import android.graphics.PointF;
import android.graphics.RectF;
import android.util.SparseArray;
import xc.engine.api.PenEngine;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.DataShape;
import xc.engine.operate.canvas.track.shape.ShapeCurveInfo;
import xc.engine.utils.PenEngineUtils;

/* JADX INFO: loaded from: classes6.dex */
public class StepShapeRadiusEdit implements IStep {
    public PenEngine a;
    public DataShape b;
    public RectF c;
    public PointF d;

    @Override // xc.engine.operate.step.type.IStep
    public void serialize() {
    }

    public StepShapeRadiusEdit(PenEngine penEngine, DataShape dataShape, RectF rectF, PointF pointF) {
        this.a = penEngine;
        this.b = dataShape;
        this.c = new RectF(rectF);
        this.d = new PointF(pointF.x, pointF.y);
    }

    @Override // xc.engine.operate.step.type.IStep
    public int getPageIndex() {
        return this.b.pageIndex;
    }

    @Override // xc.engine.operate.step.type.IStep
    public void setPageIndex(int i) {
        PointF deltaOffsetWS = this.a.getCurveEngine().getPageSystem().getDeltaOffsetWS(getPageIndex(), i);
        float saveRatio = this.a.mCanvasOperator.getSaveRatio();
        this.c.offset(deltaOffsetWS.x * saveRatio, deltaOffsetWS.y * saveRatio);
    }

    @Override // xc.engine.operate.step.type.IStep
    public void resetContext(PenEngine penEngine) {
        this.a = penEngine;
    }

    @Override // xc.engine.operate.step.type.IStep
    public void unserialize(SparseArray<DataCurve> sparseArray) {
        DataCurve dataCurve;
        if (sparseArray == null || sparseArray.size() == 0 || (dataCurve = sparseArray.get(this.b.mCount)) == null || !(dataCurve instanceof DataShape)) {
            return;
        }
        this.b = (DataShape) dataCurve;
    }

    @Override // xc.engine.operate.step.type.IStep
    public RectF undo() {
        this.a.mCurveDataManager.removeDataFromPool(this.b);
        PenEngine penEngine = this.a;
        PointF pointF = new PointF(this.d.x / this.a.mCanvasOperator.getSaveRatio(), this.d.y / this.a.mCanvasOperator.getSaveRatio());
        DataShape dataShape = this.b;
        float f = -pointF.x;
        float f2 = -pointF.y;
        boolean zIsScale = this.a.mTouchEventOperator.isScale();
        this.a.mCanvasOperator.getScaleInfo();
        if (dataShape.getType() == 28) {
            dataShape.c(f, f2);
            synchronized (dataShape.mLockObj) {
                if (zIsScale) {
                    new ShapeCurveInfo(penEngine, dataShape, true, false).optimizeAndTransformShapePath();
                }
                new ShapeCurveInfo(penEngine, dataShape, false, false).optimizeAndTransformShapePath();
            }
            if (dataShape.getType() == 28) {
                PenEngineUtils.calculateShapeSavePoints(dataShape);
            }
        } else {
            new PointF();
        }
        this.a.mCurveDataManager.addDataToPool(this.b);
        return this.c;
    }

    @Override // xc.engine.operate.step.type.IStep
    public RectF redo() {
        this.a.mCurveDataManager.removeDataFromPool(this.b);
        PenEngine penEngine = this.a;
        PointF pointF = new PointF(this.d.x / this.a.mCanvasOperator.getSaveRatio(), this.d.y / this.a.mCanvasOperator.getSaveRatio());
        DataShape dataShape = this.b;
        float f = pointF.x;
        float f2 = pointF.y;
        boolean zIsScale = this.a.mTouchEventOperator.isScale();
        this.a.mCanvasOperator.getScaleInfo();
        if (dataShape.getType() == 28) {
            dataShape.c(f, f2);
            synchronized (dataShape.mLockObj) {
                if (zIsScale) {
                    new ShapeCurveInfo(penEngine, dataShape, true, false).optimizeAndTransformShapePath();
                }
                new ShapeCurveInfo(penEngine, dataShape, false, false).optimizeAndTransformShapePath();
            }
            if (dataShape.getType() == 28) {
                PenEngineUtils.calculateShapeSavePoints(dataShape);
            }
        } else {
            new PointF();
        }
        this.a.mCurveDataManager.addDataToPool(this.b);
        return this.c;
    }
}
