package com.miui.libhandwritingpreview;

import android.graphics.Path;
import android.graphics.PointF;
import com.miui.libhandwritingpreview.operator.PreCanvasOperator;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.DataShape;

/* JADX INFO: loaded from: classes3.dex */
public class PreCurveInfo {
    private final PointF basePoint = new PointF();
    public boolean mEnableScale;

    public void enableScale(boolean z) {
        this.mEnableScale = z;
    }

    public Path getShapePath(DataShape dataShape) {
        return this.mEnableScale ? dataShape.mScaledShapePath : dataShape.mShapePath;
    }

    public float getDrawRatio(PreCanvasOperator preCanvasOperator) {
        return this.mEnableScale ? preCanvasOperator.getDrawRatio() * preCanvasOperator.mPenEngine.getCurveEngine().getScaleRatio() : preCanvasOperator.getDrawRatio();
    }

    public float getDrawRatio(DataCurve dataCurve) {
        return this.mEnableScale ? dataCurve.mDrawRatio * dataCurve.mScaleRate : dataCurve.mDrawRatio;
    }

    public PointF getCurvePoint(DataCurve dataCurve) {
        return this.mEnableScale ? dataCurve.mBasePoint : this.basePoint;
    }

    public PointF getCurvePoint(PreCanvasOperator preCanvasOperator) {
        if (this.mEnableScale) {
            this.basePoint.set(preCanvasOperator.getScaleInfo().getTranslate());
        } else {
            this.basePoint.set(0.0f, 0.0f);
        }
        return this.basePoint;
    }

    public float getScaleRate(PreCanvasOperator preCanvasOperator) {
        if (this.mEnableScale) {
            return preCanvasOperator.mPenEngine.getCurveEngine().getScaleRatio();
        }
        return 1.0f;
    }

    public long getCurvePoints(DataCurve dataCurve) {
        return this.mEnableScale ? dataCurve.mScaledPoints : dataCurve.mOriginPoints;
    }
}
