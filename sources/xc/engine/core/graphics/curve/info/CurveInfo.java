package xc.engine.core.graphics.curve.info;

import android.graphics.Path;
import android.graphics.PointF;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.DataShape;
import xc.engine.operate.canvas.CanvasOperator;

/* JADX INFO: loaded from: classes6.dex */
public class CurveInfo {
    private final PointF basePoint = new PointF();
    public boolean mEnableScale;

    public void enableScale(boolean z) {
        this.mEnableScale = z;
    }

    public Path getShapePath(DataShape dataShape) {
        return this.mEnableScale ? dataShape.mScaledShapePath : dataShape.mShapePath;
    }

    public float getDrawRatio(CanvasOperator canvasOperator) {
        return this.mEnableScale ? canvasOperator.getDrawRatio() * canvasOperator.mPenEngine.getCurveEngine().getScaleRatio() : canvasOperator.getDrawRatio();
    }

    public float getDrawRatio(DataCurve dataCurve) {
        return this.mEnableScale ? dataCurve.mDrawRatio * dataCurve.mScaleRate : dataCurve.mDrawRatio;
    }

    public PointF getCurvePoint(DataCurve dataCurve) {
        return this.mEnableScale ? dataCurve.mBasePoint : this.basePoint;
    }

    public PointF getCurvePoint(CanvasOperator canvasOperator) {
        if (this.mEnableScale) {
            this.basePoint.set(canvasOperator.getScaleInfo().getTranslate());
        } else {
            this.basePoint.set(0.0f, 0.0f);
        }
        return this.basePoint;
    }

    public float getScaleRate(CanvasOperator canvasOperator) {
        if (this.mEnableScale) {
            return canvasOperator.mPenEngine.getCurveEngine().getScaleRatio();
        }
        return 1.0f;
    }

    public long getCurvePoints(DataCurve dataCurve) {
        return this.mEnableScale ? dataCurve.mScaledPoints : dataCurve.mOriginPoints;
    }
}
