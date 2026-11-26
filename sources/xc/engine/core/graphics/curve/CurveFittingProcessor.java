package xc.engine.core.graphics.curve;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import java.util.List;
import java.util.Objects;
import xc.engine.api.PenEngine;
import xc.engine.core.algorithm.recognize.data.RecognizeData;
import xc.engine.core.graphics.curve.info.CurveInfo;
import xc.engine.core.graphics.pen.BasePenPropConfig;
import xc.engine.data.CsPoint;
import xc.engine.data.curve.CurvePointListOperator;
import xc.engine.data.curve.ICurvePointList;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.utils.PenEngineUtils;
import xc.engine.utils.ScreenInfo;

/* JADX INFO: loaded from: classes6.dex */
public class CurveFittingProcessor extends CurveInfo {
    private static final String TAG = "CurveFittingProcessor";
    private final float PATH_DISTANCE_THRESHOLD;
    private final PointF mBasePoint;
    public float mCanvasScaleRate;
    private final long mCsPointList;
    public CsPoint mCurPoint;
    private final DataCurve mDataCurve;
    public final float mDrawRatio;
    private boolean mIsInterpolateCurvePoint;
    private final Matrix mMatrix;
    private final float[] mMatrixValues;
    public final Path mPath;
    private final float mPathLimitLength;
    private final PathMeasure mPathMeasure;
    private final BasePenPropConfig mPenPropConfig;
    private final float mSpaceFactor;
    public float mStartPathDistance;
    public PointF mStartPoint;

    public CurveFittingProcessor(PenEngine penEngine, DataCurve dataCurve, boolean z) {
        this(penEngine, dataCurve, new Path(), z);
    }

    public CurveFittingProcessor(PenEngine penEngine, DataCurve dataCurve, Path path, boolean z, boolean z2) {
        enableScale(z);
        if (z2) {
            this.mDrawRatio = getDrawRatio(dataCurve);
        } else {
            this.mDrawRatio = getDrawRatio(penEngine.getCanvasOperator());
        }
        this.mCsPointList = getCurvePoints(dataCurve);
        this.mCanvasScaleRate = getScaleRate(penEngine.getCanvasOperator());
        this.mBasePoint = getCurvePoint(dataCurve);
        this.mDataCurve = dataCurve;
        this.mPenPropConfig = PenEngineUtils.getPenPropConfig(this.mDrawRatio, dataCurve.getPenType(), dataCurve.getCurvePenWidth(), dataCurve.getSensitivity(), dataCurve.getTipSize());
        this.mSpaceFactor = PenEngineUtils.getPenSpaceFactor(dataCurve.getPenType());
        this.mMatrix = new Matrix();
        this.mPathMeasure = new PathMeasure();
        this.mMatrixValues = new float[9];
        this.mPath = path;
        ScreenInfo screenInfo = penEngine.getScreenInfo();
        Objects.requireNonNull(penEngine.getScreenInfo());
        this.PATH_DISTANCE_THRESHOLD = screenInfo.a(1.5f, 3.0f);
        this.mStartPoint = new PointF();
        this.mPathLimitLength = 420.0f;
    }

    public CurveFittingProcessor(DataCurve dataCurve) {
        enableScale(true);
        this.mDrawRatio = 1.0f;
        this.mCsPointList = dataCurve.mOriginPoints;
        this.mCanvasScaleRate = 1.0f;
        this.mBasePoint = new PointF(0.0f, 0.0f);
        this.mDataCurve = dataCurve;
        this.mPenPropConfig = PenEngineUtils.getPenPropConfig(1.0f, dataCurve.getPenType(), dataCurve.getCurvePenWidth(), dataCurve.getSensitivity(), dataCurve.getTipSize());
        this.mSpaceFactor = PenEngineUtils.getPenSpaceFactor(dataCurve.getPenType());
        this.mMatrix = new Matrix();
        this.mPathMeasure = new PathMeasure();
        this.mMatrixValues = new float[9];
        this.mPath = new Path();
        this.PATH_DISTANCE_THRESHOLD = 3.45f;
        this.mStartPoint = new PointF();
        this.mPathLimitLength = 420.0f;
    }

    public CurveFittingProcessor(PenEngine penEngine, DataCurve dataCurve, Path path, boolean z) {
        this(penEngine, dataCurve, path, z, false);
    }

    public void processCurveFitting(CsPoint csPoint) {
        addPointToPath(this.mPath, csPoint.getX() * this.mDrawRatio, csPoint.getY() * this.mDrawRatio);
        interpolateCurvePoints(this.mPath, csPoint, this.mCsPointList);
        this.mCurPoint = csPoint;
    }

    public void processCurveFitting(CsPoint csPoint, List<CsPoint> list) {
        addPointToPath(this.mPath, csPoint.getX() * this.mDrawRatio, csPoint.getY() * this.mDrawRatio);
        interpolateCurvePoints(this.mPath, csPoint, this.mCsPointList, list);
        this.mCurPoint = csPoint;
    }

    public void endCurveFitting() {
        CsPoint csPoint;
        if (CurvePointListOperator.size(this.mCsPointList) == 0 && (csPoint = this.mCurPoint) != null) {
            PointF pointF = this.mBasePoint;
            CurvePointListOperator.insertPoint(this.mCsPointList, (csPoint.getX() * this.mDrawRatio) + pointF.x, (csPoint.getY() * this.mDrawRatio) + pointF.y, csPoint.getPressure(), csPoint.getTilt(), this.mCurPoint.getTime(), this.mCurPoint.getOrientation(), this.mCurPoint.getVelocity(), (short) 1, this.mCurPoint.getRotateAngle());
        }
        if (this.mEnableScale) {
            return;
        }
        this.mDataCurve.mDrawRatio = this.mDrawRatio;
    }

    public void fillCurvePoints() {
        synchronized (this.mDataCurve) {
            DataCurve dataCurve = this.mDataCurve;
            if (dataCurve.size(dataCurve.mSavePoints) == 0) {
                return;
            }
            CurvePointListOperator.clearLine(this.mCsPointList);
            if (RecognizeData.isClose(this.mDataCurve.mCurveType)) {
                shapeCurveFitting(this.mDataCurve);
            } else {
                DataCurve dataCurve2 = this.mDataCurve;
                int size = dataCurve2.size(dataCurve2.mSavePoints);
                DataCurve dataCurve3 = this.mDataCurve;
                ICurvePointList curvePointList = dataCurve3.getCurvePointList(dataCurve3.mSavePoints);
                this.mIsInterpolateCurvePoint = false;
                prepareForCurveFitting(new CsPoint(curvePointList.get(0)));
                for (int i = 1; i < size; i++) {
                    CsPoint csPoint = new CsPoint(curvePointList.get(i));
                    int i2 = size - 2;
                    if (i == i2 && !this.mIsInterpolateCurvePoint && new CsPoint(curvePointList.get(i2)).getPressure() <= 0.0f) {
                        int i3 = size - 1;
                        if (new CsPoint(curvePointList.get(i3)).getPressure() > 0.0f) {
                            csPoint.setPressure(new CsPoint(curvePointList.get(i3)).getPressure());
                        } else {
                            csPoint.setPressure(0.78f);
                        }
                    }
                    processCurveFitting(csPoint);
                }
                endCurveFitting();
                this.mIsInterpolateCurvePoint = false;
            }
            if (!this.mEnableScale) {
                this.mDataCurve.mDrawRatio = this.mDrawRatio;
            }
        }
    }

    private void shapeCurveFitting(DataCurve dataCurve) {
        int iMax;
        int size = dataCurve.size(dataCurve.mSavePoints);
        float[] fArr = new float[size];
        this.mPath.reset();
        ICurvePointList curvePointList = dataCurve.getCurvePointList(dataCurve.mSavePoints);
        this.mPath.moveTo(curvePointList.getX(0) * this.mDrawRatio, curvePointList.getY(0) * this.mDrawRatio);
        fArr[0] = 0.0f;
        CsPoint csPoint = new CsPoint(curvePointList.get(0));
        this.mCurPoint = csPoint;
        float pressure = csPoint.getPressure();
        float tilt = this.mCurPoint.getTilt();
        for (int i = 1; i < size; i++) {
            addPointToPath(this.mPath, curvePointList.getX(i) * this.mDrawRatio, curvePointList.getY(i) * this.mDrawRatio);
            this.mCurPoint = new CsPoint(curvePointList.get(i));
            this.mPathMeasure.setPath(this.mPath, false);
            fArr[i] = this.mPathMeasure.getLength();
            pressure += new CsPoint(curvePointList.get(i)).getPressure();
            tilt += new CsPoint(curvePointList.get(i)).getTilt();
        }
        this.mPath.close();
        float f = size;
        float spaceDistance = this.mPenPropConfig.getSpaceDistance(pressure / f, tilt / f);
        this.mPathMeasure.setPath(this.mPath, false);
        int length = (int) (((double) (this.mPathMeasure.getLength() / spaceDistance)) + 0.5d);
        float length2 = this.mPathMeasure.getLength() / length;
        this.mStartPathDistance = 0.0f;
        CsPoint csPoint2 = null;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            int i4 = i2;
            while (true) {
                if (i4 >= size) {
                    iMax = -1;
                    break;
                } else {
                    if (fArr[i4] > this.mStartPathDistance) {
                        iMax = Math.max(i4, 0);
                        break;
                    }
                    i4++;
                }
            }
            if (iMax > i2) {
                DataCurve dataCurve2 = this.mDataCurve;
                csPoint2 = new CsPoint(dataCurve2.getCurvePointList(dataCurve2.mSavePoints).get(iMax));
                i2 = iMax;
            }
            if (csPoint2 == null) {
                return;
            }
            float f2 = this.mStartPathDistance + length2;
            this.mStartPathDistance = f2;
            if (this.mPathMeasure.getMatrix(f2, this.mMatrix, 3)) {
                this.mMatrix.getValues(this.mMatrixValues);
                CurvePointListOperator.insertPoint(this.mCsPointList, this.mMatrixValues[2] + this.mBasePoint.x, this.mMatrixValues[5] + this.mBasePoint.y, csPoint2.getPressure(), csPoint2.getTilt(), csPoint2.getTime(), csPoint2.getOrientation(), csPoint2.getVelocity(), (short) 1, csPoint2.getRotateAngle());
            }
        }
    }

    public void prepareForCurveFitting(CsPoint csPoint) {
        float x = csPoint.getX() * this.mDrawRatio;
        float y = csPoint.getY() * this.mDrawRatio;
        this.mPath.reset();
        this.mStartPathDistance = 0.0f;
        this.mPath.moveTo(x, y);
        CsPoint csPoint2 = new CsPoint(csPoint);
        PointF pointF = this.mBasePoint;
        csPoint2.setX(x + pointF.x);
        csPoint2.setY(y + pointF.y);
        CurvePointListOperator.insertPoint(this.mCsPointList, csPoint2.getX(), csPoint2.getY(), csPoint2.getPressure(), csPoint2.getTilt(), csPoint2.getTime(), csPoint2.getOrientation(), csPoint2.getVelocity(), (short) 1, csPoint2.getRotateAngle());
        this.mCurPoint = csPoint;
        this.mStartPoint = new PointF(csPoint.getX(), csPoint.getY());
    }

    public final void addPointToPath(Path path, float f, float f2) {
        CsPoint csPoint = this.mCurPoint;
        float x = ((csPoint.getX() * this.mDrawRatio) + f) / 2.0f;
        float y = ((csPoint.getY() * this.mDrawRatio) + f2) / 2.0f;
        float fAbs = Math.abs(this.mStartPoint.x - f);
        float fAbs2 = Math.abs(this.mStartPoint.y - f2);
        float f3 = (fAbs * fAbs) + (fAbs2 * fAbs2);
        float f4 = this.PATH_DISTANCE_THRESHOLD;
        if (f3 > f4 * f4) {
            path.quadTo(csPoint.getX() * this.mDrawRatio, csPoint.getY() * this.mDrawRatio, x, y);
        } else {
            path.lineTo(x, y);
        }
        PointF pointF = this.mStartPoint;
        float f5 = this.mDrawRatio;
        pointF.set(x / f5, y / f5);
    }

    public final void interpolateCurvePoints(Path path, CsPoint csPoint, long j) {
        if (j == 0) {
            return;
        }
        interpolateCurvePoints(path, csPoint, j, null);
    }

    public final void interpolateCurvePoints(Path path, CsPoint csPoint, long j, List<CsPoint> list) {
        float f;
        float f2;
        this.mPathMeasure.setPath(path, false);
        float length = this.mPathMeasure.getLength();
        boolean z = true;
        if (length > this.mPathLimitLength) {
            Path path2 = new Path();
            PathMeasure pathMeasure = this.mPathMeasure;
            if (pathMeasure.getSegment(this.mStartPathDistance, pathMeasure.getLength(), path2, true)) {
                length -= this.mStartPathDistance;
                this.mStartPathDistance = 0.0f;
                this.mPathMeasure.setPath(path2, false);
                path.set(path2);
            }
        }
        float pressure = this.mCurPoint.getPressure();
        float tilt = this.mCurPoint.getTilt();
        float rotateAngle = this.mCurPoint.getRotateAngle();
        float pressure2 = csPoint.getPressure() - this.mCurPoint.getPressure();
        float tilt2 = csPoint.getTilt() - this.mCurPoint.getTilt();
        float f3 = length - this.mStartPathDistance;
        float velocity = this.mCurPoint.getVelocity();
        float velocity2 = (csPoint.getVelocity() - this.mCurPoint.getVelocity()) / (length - this.mStartPathDistance);
        float rotateAngle2 = csPoint.getRotateAngle() - this.mCurPoint.getRotateAngle();
        if (rotateAngle2 > 180.0f) {
            rotateAngle2 -= 360.0f;
        } else if (rotateAngle2 < -180.0f) {
            rotateAngle2 += 360.0f;
        }
        float f4 = rotateAngle;
        float f5 = velocity;
        while (true) {
            float spaceDistance = this.mPenPropConfig.getSpaceDistance(pressure, tilt);
            float f6 = this.mStartPathDistance;
            if (spaceDistance + f6 > this.mSpaceFactor + length) {
                return;
            }
            this.mIsInterpolateCurvePoint = z;
            float f7 = f6 + spaceDistance;
            if (this.mPathMeasure.getMatrix(f7, this.mMatrix, 3)) {
                this.mStartPathDistance = f7;
                this.mMatrix.getValues(this.mMatrixValues);
                PointF pointF = this.mBasePoint;
                if (list != null) {
                    f2 = tilt;
                    f = pressure;
                    list.add(new CsPoint(this.mMatrixValues[2] + pointF.x, this.mMatrixValues[5] + pointF.y, f, f2, csPoint.getTime(), csPoint.getOrientation(), f5, f4));
                } else {
                    f = pressure;
                    f2 = tilt;
                }
                float f8 = f4;
                float f9 = f5;
                float f10 = f2;
                float f11 = f;
                CurvePointListOperator.insertPoint(j, this.mMatrixValues[2] + pointF.x, this.mMatrixValues[5] + pointF.y, f11, f10, csPoint.getTime(), csPoint.getOrientation(), f9, (short) 1, f8);
                float f12 = spaceDistance / f3;
                tilt = f10 + (f12 * tilt2);
                f4 = f8 + (f12 * rotateAngle2);
                f5 = f9 + (spaceDistance * velocity2);
                pressure = f11 + (f12 * pressure2);
            } else {
                length = length;
                float f13 = spaceDistance / f3;
                pressure += f13 * pressure2;
                tilt += f13 * tilt2;
            }
            length = length;
            z = true;
        }
    }

    public PointF getBasePoint() {
        return new PointF(this.mBasePoint);
    }
}
