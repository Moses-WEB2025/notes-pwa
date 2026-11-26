package xc.engine.core.graphics.curve;

import android.graphics.PointF;
import android.graphics.RectF;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import xc.engine.api.PenEngine;
import xc.engine.data.pagedata.BaseData;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.DataShape;
import xc.engine.data.shape.ShapeType;
import xc.engine.operate.canvas.draw.scale.ScaleInfo;
import xc.engine.operate.canvas.track.shape.ShapeCurveInfo;
import xc.engine.utils.PenEngineUtils;

/* JADX INFO: loaded from: classes6.dex */
public class CurveDataListManager {
    private final PenEngine mPenEngine;

    public CurveDataListManager(PenEngine penEngine) {
        this.mPenEngine = penEngine;
    }

    public void doMove(List<DataCurve> list, float f, float f2) {
        Iterator<DataCurve> it = list.iterator();
        while (it.hasNext()) {
            it.next().doMove(f, f2);
        }
    }

    public PointF a(BaseData baseData, float f, float f2, boolean z) {
        if (baseData.getType() == 28) {
            DataShape dataShape = (DataShape) baseData;
            PointF pointFC = dataShape.c(f, f2);
            synchronized (dataShape.mLockObj) {
                if (z) {
                    new ShapeCurveInfo(this.mPenEngine, dataShape, true, false).optimizeAndTransformShapePath();
                    new ShapeCurveInfo(this.mPenEngine, dataShape, false, false).optimizeAndTransformShapePath();
                } else {
                    new ShapeCurveInfo(this.mPenEngine, dataShape, false, false).optimizeAndTransformShapePath();
                }
                throw th;
            }
            PenEngineUtils.calculateShapeSavePoints(dataShape);
            return pointFC;
        }
        return new PointF();
    }

    public void doShapeScale(BaseData baseData, float f, float f2, float f3) {
        baseData.doScale(f, f2, f3);
        DataCurve dataCurve = (DataCurve) baseData;
        this.mPenEngine.mCurveDataManager.executeCurveGenTask(dataCurve);
        if (this.mPenEngine.mCanvasOperator.isScale) {
            this.mPenEngine.mCurveDataManager.executeScalableCurveGenTask(dataCurve);
        }
        PenEngineUtils.calculateShapeSavePoints((DataShape) baseData);
    }

    public float doShapeEdit(BaseData baseData, int i, PointF pointF, float f, float f2, boolean z) {
        float f3;
        float f4;
        float f5;
        float f6;
        float fAbs;
        float f7;
        float fAbs2 = 1.0f;
        if (baseData.getType() != 28) {
            return 1.0f;
        }
        DataShape dataShape = (DataShape) baseData;
        float shapeScaleLimitMin = ShapeType.getShapeScaleLimitMin(dataShape.isLineOrArrow());
        if (i == 5 || i == 7) {
            if (dataShape.O) {
                float[] fArr = dataShape.mAnchorPoint4;
                dataShape.P = Math.abs(fArr[0] - fArr[2]);
                if (dataShape.isLineOrArrow()) {
                    float[] fArr2 = dataShape.mAnchorPoint4;
                    double dPow = Math.pow(fArr2[0] - fArr2[2], 2.0d);
                    float[] fArr3 = dataShape.mAnchorPoint4;
                    dataShape.P = (float) Math.sqrt(dPow + Math.pow(fArr3[1] - fArr3[3], 2.0d));
                }
            }
            float f8 = dataShape.P * f;
            if (f8 > shapeScaleLimitMin) {
                float[] fArr4 = dataShape.mAnchorPoint4;
                dataShape.P = Math.abs(fArr4[0] - fArr4[2]);
                if (dataShape.isLineOrArrow()) {
                    float[] fArr5 = dataShape.mAnchorPoint4;
                    double dPow2 = Math.pow(fArr5[0] - fArr5[2], 2.0d);
                    float[] fArr6 = dataShape.mAnchorPoint4;
                    dataShape.P = (float) Math.sqrt(dPow2 + Math.pow(fArr6[1] - fArr6[3], 2.0d));
                }
                f3 = f;
            } else {
                dataShape.P = f8;
                f3 = 1.0f;
            }
            f4 = f3;
            f5 = 1.0f;
        } else if (i == 6 || i == 8) {
            if (dataShape.O) {
                float[] fArr7 = dataShape.mAnchorPoint4;
                dataShape.P = Math.abs(fArr7[1] - fArr7[3]);
                if (dataShape.isLineOrArrow()) {
                    float[] fArr8 = dataShape.mAnchorPoint4;
                    double dPow3 = Math.pow(fArr8[0] - fArr8[2], 2.0d);
                    float[] fArr9 = dataShape.mAnchorPoint4;
                    dataShape.P = (float) Math.sqrt(dPow3 + Math.pow(fArr9[1] - fArr9[3], 2.0d));
                }
            }
            float f9 = dataShape.P * f;
            if (f9 > shapeScaleLimitMin) {
                float[] fArr10 = dataShape.mAnchorPoint4;
                dataShape.P = Math.abs(fArr10[1] - fArr10[3]);
                if (dataShape.isLineOrArrow()) {
                    float[] fArr11 = dataShape.mAnchorPoint4;
                    double dPow4 = Math.pow(fArr11[0] - fArr11[2], 2.0d);
                    float[] fArr12 = dataShape.mAnchorPoint4;
                    dataShape.P = (float) Math.sqrt(dPow4 + Math.pow(fArr12[1] - fArr12[3], 2.0d));
                }
                f7 = f;
            } else {
                dataShape.P = f9;
                f7 = 1.0f;
            }
            f4 = f7;
            f5 = f7;
            f3 = 1.0f;
        } else {
            if (dataShape.O) {
                float[] fArr13 = dataShape.mAnchorPoint4;
                float fAbs3 = Math.abs(fArr13[0] - fArr13[2]);
                float[] fArr14 = dataShape.mAnchorPoint4;
                dataShape.P = Math.min(fAbs3, Math.abs(fArr14[1] - fArr14[3]));
                if (dataShape.isLineOrArrow()) {
                    float[] fArr15 = dataShape.mAnchorPoint4;
                    double dPow5 = Math.pow(fArr15[0] - fArr15[2], 2.0d);
                    float[] fArr16 = dataShape.mAnchorPoint4;
                    dataShape.P = (float) Math.sqrt(dPow5 + Math.pow(fArr16[1] - fArr16[3], 2.0d));
                }
            }
            float f10 = dataShape.P * f;
            if (f10 > shapeScaleLimitMin) {
                float[] fArr17 = dataShape.mAnchorPoint4;
                float fAbs4 = Math.abs(fArr17[0] - fArr17[2]);
                float[] fArr18 = dataShape.mAnchorPoint4;
                dataShape.P = Math.min(fAbs4, Math.abs(fArr18[1] - fArr18[3]));
                if (dataShape.isLineOrArrow()) {
                    float[] fArr19 = dataShape.mAnchorPoint4;
                    double dPow6 = Math.pow(fArr19[0] - fArr19[2], 2.0d);
                    float[] fArr20 = dataShape.mAnchorPoint4;
                    dataShape.P = (float) Math.sqrt(dPow6 + Math.pow(fArr20[1] - fArr20[3], 2.0d));
                }
                f3 = f;
            } else {
                dataShape.P = f10;
                f3 = 1.0f;
            }
            f5 = f3;
            f4 = f5;
        }
        float f11 = pointF.x;
        float f12 = pointF.y;
        float f13 = dataShape.mDrawRatio;
        float f14 = f11 / f13;
        float f15 = f12 / f13;
        dataShape.mMatrix1.reset();
        dataShape.mMatrix1.postRotate(dataShape.mRotateDegrees, dataShape.mRect.centerX(), dataShape.mRect.centerY());
        dataShape.mMatrix1.mapPoints(dataShape.mAnchorPoint4);
        dataShape.mMatrix1.reset();
        dataShape.mMatrix1.postRotate(-dataShape.mRotateDegrees, f14, f15);
        dataShape.mMatrix1.postScale(f3, f5, f14, f15);
        dataShape.mMatrix1.postRotate(dataShape.mRotateDegrees + f2, f14, f15);
        dataShape.mMatrix1.mapPoints(dataShape.mAnchorPoint8);
        dataShape.mMatrix1.mapPoints(dataShape.mAnchorPoint4);
        dataShape.fixCoordinates();
        dataShape.mRotateDegrees = (dataShape.mRotateDegrees + f2) % 360.0f;
        dataShape.mMatrix1.reset();
        dataShape.mMatrix1.postRotate(-dataShape.mRotateDegrees, dataShape.mRect.centerX(), dataShape.mRect.centerY());
        dataShape.mMatrix1.mapPoints(dataShape.mAnchorPoint4);
        if (dataShape.isLineOrArrow()) {
            f6 = 1.0f;
        } else {
            float penSize = dataShape.mShapeProp.getPenSize() / 2.0f;
            if (f3 != 1.0f) {
                float[] fArr21 = dataShape.mAnchorPoint4;
                float fAbs5 = ((Math.abs(fArr21[0] - fArr21[2]) / 2.0f) + (penSize * f3)) - penSize;
                float[] fArr22 = dataShape.mAnchorPoint4;
                fAbs = fAbs5 / (Math.abs(fArr22[0] - fArr22[2]) / 2.0f);
            } else {
                fAbs = 1.0f;
            }
            if (f5 != 1.0f) {
                float[] fArr23 = dataShape.mAnchorPoint4;
                float fAbs6 = ((Math.abs(fArr23[1] - fArr23[3]) / 2.0f) + (penSize * f5)) - penSize;
                float[] fArr24 = dataShape.mAnchorPoint4;
                fAbs2 = fAbs6 / (Math.abs(fArr24[1] - fArr24[3]) / 2.0f);
            }
            dataShape.mMatrix1.reset();
            dataShape.mMatrix1.postScale(fAbs, fAbs2, dataShape.mRect.centerX(), dataShape.mRect.centerY());
            dataShape.mMatrix1.mapPoints(dataShape.mAnchorPoint4);
            f6 = fAbs2;
            fAbs2 = fAbs;
        }
        float[] fArr25 = dataShape.mAnchorPoint4;
        dataShape.initRectF(new RectF(fArr25[0], fArr25[1], fArr25[2], fArr25[3]));
        PointF pointF2 = dataShape.mFilletPointF;
        pointF2.x = pointF2.x * f3 * fAbs2;
        pointF2.y = pointF2.y * f5 * f6;
        dataShape.getEditPoints();
        dataShape.c(0.0f, 0.0f);
        dataShape.O = false;
        synchronized (dataShape.mLockObj) {
            new ShapeCurveInfo(this.mPenEngine, (DataShape) baseData, false, false).optimizeAndTransformShapePath();
        }
        PenEngineUtils.calculateShapeSavePoints(dataShape);
        return f4;
    }

    public void doScale(List<DataCurve> list, float f, float f2, float f3) {
        ArrayList arrayList = new ArrayList();
        for (DataCurve dataCurve : list) {
            dataCurve.doScale(f, f2, f3);
            arrayList.add(dataCurve);
            if (dataCurve.getType() == 28) {
                PenEngineUtils.calculateShapeSavePoints((DataShape) dataCurve);
            }
        }
        this.mPenEngine.mCurveDataManager.batchExecCurveGenTask(arrayList);
        if (this.mPenEngine.mCanvasOperator.isScale) {
            this.mPenEngine.mCurveDataManager.batchExecScalableCurveGenTask(arrayList);
        }
        arrayList.clear();
    }

    public void doScaleAndRotate(List<DataCurve> list, float f, float f2, float f3, float f4) {
        ArrayList arrayList = new ArrayList();
        for (DataCurve dataCurve : list) {
            if (dataCurve.getType() == 1) {
                dataCurve.doScaleAndRotate(f, f2, f3, f4);
            } else {
                dataCurve.doScale(f, f2, f3);
                dataCurve.doRotate(f, f2, f4);
            }
            arrayList.add(dataCurve);
            if (dataCurve.getType() == 28) {
                PenEngineUtils.calculateShapeSavePoints((DataShape) dataCurve);
            }
        }
        this.mPenEngine.mCurveDataManager.batchExecCurveGenTask(arrayList);
        if (this.mPenEngine.mCanvasOperator.isScale) {
            this.mPenEngine.mCurveDataManager.batchExecScalableCurveGenTask(arrayList);
        }
        arrayList.clear();
    }

    public void genCurveScaledPoints(List<DataCurve> list, ScaleInfo scaleInfo) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).clearPoints(list.get(i).mScaledPoints);
            list.get(i).mScaleRate = scaleInfo.getScaleRate();
            list.get(i).mBasePoint.set(0.0f, 0.0f);
        }
        this.mPenEngine.mCurveDataManager.batchExecScalableCurveGenTask(list);
    }

    public void doShapeRotate(BaseData baseData, float f, float f2, float f3) {
        baseData.doRotate(f, f2, f3);
        DataCurve dataCurve = (DataCurve) baseData;
        this.mPenEngine.mCurveDataManager.executeCurveGenTask(dataCurve);
        if (this.mPenEngine.mCanvasOperator.isScale) {
            this.mPenEngine.mCurveDataManager.executeScalableCurveGenTask(dataCurve);
        }
        PenEngineUtils.calculateShapeSavePoints((DataShape) baseData);
    }

    public void doRotate(List<DataCurve> list, float f, float f2, float f3) {
        ArrayList arrayList = new ArrayList();
        for (DataCurve dataCurve : list) {
            dataCurve.doRotate(f, f2, f3);
            arrayList.add(dataCurve);
            if (dataCurve.getType() == 28) {
                PenEngineUtils.calculateShapeSavePoints((DataShape) dataCurve);
            }
        }
        this.mPenEngine.mCurveDataManager.batchExecCurveGenTask(arrayList);
        if (this.mPenEngine.mCanvasOperator.isScale) {
            this.mPenEngine.mCurveDataManager.batchExecScalableCurveGenTask(arrayList);
        }
        arrayList.clear();
    }

    public void pointsInterpolation(List<? extends BaseData> list, PointF pointF, PointF pointF2, boolean z, ScaleInfo scaleInfo) {
        for (BaseData baseData : list) {
            baseData.scale(pointF, pointF2);
            if (baseData instanceof DataCurve) {
                if (z) {
                    DataCurve dataCurve = (DataCurve) baseData;
                    dataCurve.mScaleRate = scaleInfo.getScaleRate();
                    dataCurve.mBasePoint.set(0.0f, 0.0f);
                    this.mPenEngine.mCurveDataManager.executeScalableCurveGenTask(dataCurve);
                } else {
                    this.mPenEngine.mCurveDataManager.executeCurveGenTask((DataCurve) baseData);
                }
            }
        }
    }
}
