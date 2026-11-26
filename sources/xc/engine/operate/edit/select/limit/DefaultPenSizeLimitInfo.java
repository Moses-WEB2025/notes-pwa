package xc.engine.operate.edit.select.limit;

import java.util.List;
import xc.engine.api.PenEngine;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.DataShape;
import xc.engine.data.shape.ShapeType;

/* JADX INFO: loaded from: classes6.dex */
public class DefaultPenSizeLimitInfo implements ILimitInfo {
    private float b;
    private List<DataCurve> c;
    private float d;
    private float e = ShapeType.getShapeScaleLimitMin(false);
    private int f = 0;
    private PenEngine mPenEngine;

    @Override // xc.engine.operate.edit.select.limit.ILimitInfo
    public int getLimitType() {
        return 0;
    }

    public DefaultPenSizeLimitInfo(PenEngine penEngine) {
        this.d = penEngine.getDrawerWrapper().getPointMaxSize();
        this.mPenEngine = penEngine;
    }

    private float getScaleValue(float[] fArr, boolean z, boolean z2) {
        if (z) {
            return (float) Math.sqrt(Math.pow(fArr[0] - fArr[2], 2.0d) + Math.pow(fArr[1] - fArr[3], 2.0d));
        }
        if (z2) {
            return Math.min(Math.abs(fArr[0] - fArr[2]), Math.abs(fArr[1] - fArr[3]));
        }
        return Math.max(Math.abs(fArr[0] - fArr[2]), Math.abs(fArr[1] - fArr[3]));
    }

    @Override // xc.engine.operate.edit.select.limit.ILimitInfo
    public boolean a(List<DataCurve> list, float f, int i) {
        this.b = f;
        float fB = this.mPenEngine.mCanvasOperator.b();
        this.c = list;
        for (DataCurve dataCurve : list) {
            if (dataCurve.getType() == 28) {
                DataShape dataShape = (DataShape) dataCurve;
                if (this.b < 1.0f) {
                    this.e = ShapeType.getShapeScaleLimitMin(dataShape.isLineOrArrow());
                    if (getScaleValue(dataShape.mAnchorPoint4, dataShape.isLineOrArrow(), true) * f <= this.e) {
                        return true;
                    }
                } else {
                    if ((i & 8) == 8) {
                        this.e = ShapeType.getShapeScaleLimitMin(false);
                        if (dataCurve.getCurvePenWidth() * f >= this.e) {
                            this.f = 1;
                            return true;
                        }
                    }
                    if (i != 132) {
                        continue;
                    } else {
                        this.e = ShapeType.getShapeScaleLimitMin(dataShape.isLineOrArrow());
                        if (getScaleValue(dataShape.mAnchorPoint4, dataShape.isLineOrArrow(), true) * f >= ShapeType.getShapeScaleLimitMax()) {
                            this.f = 2;
                            return true;
                        }
                    }
                }
            } else if (dataCurve.getCurveProp().getPenSize() * f * fB >= this.d / 2.0f) {
                return true;
            }
        }
        return false;
    }

    @Override // xc.engine.operate.edit.select.limit.ILimitInfo
    public float getScaleValue() {
        if (this.b <= 1.0f) {
            float fMin = this.e;
            for (DataCurve dataCurve : this.c) {
                if (dataCurve.getType() == 28) {
                    DataShape dataShape = (DataShape) dataCurve;
                    fMin = Math.min(fMin, getScaleValue(dataShape.mAnchorPoint4, dataShape.isLineOrArrow(), true));
                }
            }
            return this.e / fMin;
        }
        int i = this.f;
        if (i == 0) {
            float fMax = this.d / 2.0f;
            for (int i2 = 0; i2 < this.c.size(); i2++) {
                fMax = Math.max(fMax, this.c.get(i2).getCurveProp().getPenSize());
            }
            return Math.min(this.b, ((this.d / 2.0f) / this.mPenEngine.mCanvasOperator.b()) / fMax);
        }
        if (i == 1) {
            float fMax2 = 0.0f;
            for (DataCurve dataCurve2 : this.c) {
                if (dataCurve2.getType() == 28) {
                    fMax2 = Math.max(fMax2, dataCurve2.getCurvePenWidth());
                }
            }
            if (fMax2 <= 0.0f) {
                return 1.0f;
            }
            return this.e / fMax2;
        }
        float fMax3 = 0.0f;
        for (DataCurve dataCurve3 : this.c) {
            if (dataCurve3.getType() == 28) {
                DataShape dataShape2 = (DataShape) dataCurve3;
                fMax3 = Math.max(fMax3, getScaleValue(dataShape2.mAnchorPoint4, dataShape2.isLineOrArrow(), false));
            }
        }
        if (fMax3 <= 0.0f) {
            return 1.0f;
        }
        return ShapeType.getShapeScaleLimitMax() / fMax3;
    }
}
