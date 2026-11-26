package xc.engine.operate.step.type;

import android.graphics.RectF;
import java.util.ArrayList;
import java.util.List;
import xc.engine.api.PenEngine;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.operate.canvas.draw.scale.ScaleInfo;
import xc.engine.utils.ScaleDataUtil;

/* JADX INFO: loaded from: classes6.dex */
public class StepEditCurve {
    public RectF b = new RectF();
    public float mDrawRatio;
    public boolean mIsScale;
    public ScaleInfo mScaleInfo;

    public StepEditCurve(PenEngine penEngine) {
        this.mScaleInfo = ScaleDataUtil.cloneScaleInfo(penEngine.getCanvasOperator().getScaleInfo());
        this.mDrawRatio = penEngine.getCanvasOperator().getDrawRatio();
    }

    protected void syncScaleState(PenEngine penEngine, List<DataCurve> list) {
        ScaleInfo scaleInfo = penEngine.mCanvasOperator.getScaleInfo();
        float drawRatio = penEngine.mCanvasOperator.getDrawRatio();
        if (penEngine.mCanvasOperator.isScale) {
            int i = 0;
            if (drawRatio == this.mDrawRatio && this.mScaleInfo.getScaleRate() == scaleInfo.getScaleRate() && this.mScaleInfo.getTranslate().equals(scaleInfo.getTranslate())) {
                ArrayList arrayList = new ArrayList();
                while (i < list.size()) {
                    if (list.get(i).size(list.get(i).mScaledPoints) == 0) {
                        list.get(i).mScaleRate = scaleInfo.getScaleRate();
                        list.get(i).mBasePoint.set(0.0f, 0.0f);
                        arrayList.add(list.get(i));
                    }
                    i++;
                }
                penEngine.mCurveDataManager.batchExecScalableCurveGenTask(arrayList);
                return;
            }
            while (i < list.size()) {
                list.get(i).mScaleRate = scaleInfo.getScaleRate();
                list.get(i).mBasePoint.set(0.0f, 0.0f);
                i++;
            }
            penEngine.mCurveDataManager.batchExecScalableCurveGenTask(list);
            this.mDrawRatio = drawRatio;
        }
    }

    protected void updateCurvesForScale(PenEngine penEngine, List<DataCurve> list) {
        ScaleInfo scaleInfo = penEngine.mCanvasOperator.getScaleInfo();
        float drawRatio = penEngine.mCanvasOperator.getDrawRatio();
        int i = 0;
        this.mIsScale = false;
        if (penEngine.mCanvasOperator.isScale) {
            if (drawRatio == this.mDrawRatio && this.mScaleInfo.getScaleRate() == scaleInfo.getScaleRate() && this.mScaleInfo.getTranslate().equals(scaleInfo.getTranslate())) {
                ArrayList arrayList = new ArrayList();
                while (i < list.size()) {
                    if (list.get(i).size(list.get(i).mScaledPoints) == 0) {
                        list.get(i).mScaleRate = scaleInfo.getScaleRate();
                        list.get(i).mBasePoint.set(0.0f, 0.0f);
                        arrayList.add(list.get(i));
                    }
                    i++;
                }
                penEngine.mCurveDataManager.batchExecScalableCurveGenTask(arrayList);
            } else {
                while (i < list.size()) {
                    list.get(i).mScaleRate = scaleInfo.getScaleRate();
                    list.get(i).mBasePoint.set(0.0f, 0.0f);
                    i++;
                }
                penEngine.mCurveDataManager.batchExecScalableCurveGenTask(list);
                this.mDrawRatio = drawRatio;
            }
            this.b.set(ScaleDataUtil.getCompatScreenInCanvasRect(scaleInfo, penEngine.mCanvasOperator.mCanvasSizeInfo, penEngine.mCanvasOperator.isApplyingInfiniteCanvas));
            this.b.left /= scaleInfo.getScaleRate() * drawRatio;
            this.b.top /= scaleInfo.getScaleRate() * drawRatio;
            this.b.right /= scaleInfo.getScaleRate() * drawRatio;
            this.b.bottom /= drawRatio * scaleInfo.getScaleRate();
            this.mIsScale = true;
        }
    }
}
