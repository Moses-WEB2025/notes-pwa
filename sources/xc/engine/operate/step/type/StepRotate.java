package xc.engine.operate.step.type;

import android.graphics.PointF;
import android.graphics.RectF;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.List;
import xc.engine.api.PenEngine;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.DataShape;
import xc.engine.utils.PenEngineUtils;

/* JADX INFO: loaded from: classes6.dex */
public class StepRotate implements IStep {
    public int[] dataCountsArray;
    public List<DataCurve> dataCurveList;
    public float degrees;
    public PenEngine engine;
    public PointF ratioPoint;
    public RectF ratioRect;

    public StepRotate(PenEngine penEngine, List<DataCurve> list, RectF rectF, PointF pointF, float f) {
        this.engine = penEngine;
        this.dataCurveList = new ArrayList(list);
        this.ratioRect = new RectF(rectF);
        this.ratioPoint = new PointF(pointF.x, pointF.y);
        this.degrees = f;
    }

    @Override // xc.engine.operate.step.type.IStep
    public int getPageIndex() {
        if (this.dataCurveList.isEmpty()) {
            return 0;
        }
        return this.dataCurveList.get(0).pageIndex;
    }

    @Override // xc.engine.operate.step.type.IStep
    public void setPageIndex(int i) {
        PointF deltaOffsetWS = this.engine.getCurveEngine().getPageSystem().getDeltaOffsetWS(getPageIndex(), i);
        float saveRatio = this.engine.mCanvasOperator.getSaveRatio();
        this.ratioRect.offset(deltaOffsetWS.x * saveRatio, deltaOffsetWS.y * saveRatio);
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
        List<DataCurve> list = this.dataCurveList;
        if (list == null) {
            this.dataCurveList = new ArrayList();
        } else {
            list.clear();
        }
        for (int i : this.dataCountsArray) {
            if (i == Integer.MIN_VALUE) {
                this.dataCurveList.add(null);
            } else {
                this.dataCurveList.add(sparseArray.get(i));
            }
        }
    }

    @Override // xc.engine.operate.step.type.IStep
    public void serialize() {
        List<DataCurve> list = this.dataCurveList;
        if (list == null || list.isEmpty()) {
            return;
        }
        this.dataCountsArray = new int[this.dataCurveList.size()];
        for (int i = 0; i < this.dataCurveList.size(); i++) {
            DataCurve dataCurve = this.dataCurveList.get(i);
            if (dataCurve == null) {
                this.dataCountsArray[i] = Integer.MIN_VALUE;
            } else {
                this.dataCountsArray[i] = dataCurve.mCount;
            }
        }
        this.dataCurveList.clear();
    }

    @Override // xc.engine.operate.step.type.IStep
    public RectF undo() {
        this.engine.mCurveDataManager.removeDataAndGetRectF(this.dataCurveList);
        PenEngine penEngine = this.engine;
        List<DataCurve> list = this.dataCurveList;
        float saveRatio = this.ratioPoint.x / penEngine.mCanvasOperator.getSaveRatio();
        float saveRatio2 = this.ratioPoint.y / this.engine.mCanvasOperator.getSaveRatio();
        float f = -this.degrees;
        ArrayList arrayList = new ArrayList();
        for (DataCurve dataCurve : list) {
            dataCurve.doRotate(saveRatio, saveRatio2, f);
            arrayList.add(dataCurve);
            if (dataCurve.getType() == 28) {
                PenEngineUtils.calculateShapeSavePoints((DataShape) dataCurve);
            }
        }
        penEngine.mCurveDataManager.batchExecCurveGenTask(arrayList);
        if (penEngine.mCanvasOperator.isScale) {
            penEngine.mCurveDataManager.batchExecScalableCurveGenTask(arrayList);
        }
        arrayList.clear();
        this.engine.mCurveDataManager.addDataAndGetRectF(this.dataCurveList);
        return this.ratioRect;
    }

    @Override // xc.engine.operate.step.type.IStep
    public RectF redo() {
        this.engine.mCurveDataManager.removeDataAndGetRectF(this.dataCurveList);
        PenEngine penEngine = this.engine;
        List<DataCurve> list = this.dataCurveList;
        float saveRatio = this.ratioPoint.x / penEngine.mCanvasOperator.getSaveRatio();
        float saveRatio2 = this.ratioPoint.y / this.engine.mCanvasOperator.getSaveRatio();
        float f = this.degrees;
        ArrayList arrayList = new ArrayList();
        for (DataCurve dataCurve : list) {
            dataCurve.doRotate(saveRatio, saveRatio2, f);
            arrayList.add(dataCurve);
            if (dataCurve.getType() == 28) {
                PenEngineUtils.calculateShapeSavePoints((DataShape) dataCurve);
            }
        }
        penEngine.mCurveDataManager.batchExecCurveGenTask(arrayList);
        if (penEngine.mCanvasOperator.isScale) {
            penEngine.mCurveDataManager.batchExecScalableCurveGenTask(arrayList);
        }
        arrayList.clear();
        this.engine.mCurveDataManager.addDataAndGetRectF(this.dataCurveList);
        return this.ratioRect;
    }
}
