package xc.engine.operate.canvas.track.curve;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import xc.engine.api.CreationPageSDK;
import xc.engine.api.PenEngine;
import xc.engine.cbridge.MiCurveNative;
import xc.engine.core.algorithm.recognize.data.RecognizeData;
import xc.engine.core.algorithm.recognize.data.RecognizeWithArrowData;
import xc.engine.core.fast.GLFrontBufferedWrapperRenderer;
import xc.engine.core.fast.data.CurveSegment;
import xc.engine.core.fast.draw.TouchType;
import xc.engine.core.graphics.curve.CurveFittingProcessor;
import xc.engine.core.graphics.curve.info.CurveInfo;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.core.graphics.engine.systems.MathSystem;
import xc.engine.core.graphics.engine.systems.PageSystem;
import xc.engine.data.CsPoint;
import xc.engine.data.CurveProp;
import xc.engine.data.curve.CurvePointListOperator;
import xc.engine.data.curve.ICurvePoint;
import xc.engine.data.curve.ICurvePointList;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.DataSet;
import xc.engine.math.iink.manager.MathDataManager;
import xc.engine.operate.canvas.CanvasOperator;
import xc.engine.operate.canvas.draw.scale.ScaleInfo;
import xc.engine.operate.canvas.track.IOperator;
import xc.engine.operate.step.type.StepEditWrite;
import xc.engine.operate.touch.HoverPoint;
import xc.engine.operate.touch.TouchPoint;
import xc.engine.utils.FeatureManager;
import xc.engine.utils.PenEngineUtils;
import xc.engine.utils.ScreenInfo;

/* JADX INFO: loaded from: classes6.dex */
public class CurveOperator extends CurveInfo implements IOperator {
    private static final long MOVE_THRESHOLD_TIME = 12;
    private static final String TAG = "CurveOperator";
    private DataCurve curveForAdditiveDrawing;
    private TouchPoint[] lastOriginEstPoints;
    private CanvasOperator mCanvasOperator;
    private RecognizeData mCurRecognizeShapeData;
    private CurveFittingProcessor mCurveFittingProcessor;
    private CurveProp mCurveProp;
    private DataCurve mDataCurve;
    private Runnable mFastRenderTask;
    DataCurve mFrontShapeCurve;
    private GLFrontBufferedWrapperRenderer mGlFastRenderer;
    private boolean mIsAdsorbent;
    private final PenEngine mPenEngine;
    private float mSavaRatio;
    private ShapeRecognizeCurveInfo mShapeRecognizeCurveInfo;
    private CsPoint mStartCsPoint;
    private DataCurve shapeCurveForAdditiveDrawing;
    private final float[] mMatrixPoints = new float[9];
    private final Path mCurrentPath = new Path();
    private float mRatioA = 1.0f;
    private final PathMeasure mPathMeasure = new PathMeasure();
    private float mStartLength = 0.0f;
    private final Path mPathA = new Path();
    private TouchPoint mCurTouchPoint = null;
    private final float mSegmentLength = 420.0f;
    private final Matrix mMatrix = new Matrix();
    RectF mDrawRect = null;
    private long lastMoveTime = 0;

    @Override // xc.engine.operate.canvas.track.IOperator
    public void onDraw(Canvas canvas) {
    }

    @Override // xc.engine.operate.canvas.track.IOperator
    public void setEraseTime(long j) {
    }

    public CurveOperator(PenEngine penEngine, CurveProp curveProp, boolean z) {
        this.mPenEngine = penEngine;
        enableScale(z);
        init(curveProp, z);
    }

    private void init(CurveProp curveProp, boolean z) {
        this.mCurveProp = curveProp;
        CanvasOperator canvasOperator = this.mPenEngine.getCanvasOperator();
        this.mCanvasOperator = canvasOperator;
        this.mSavaRatio = canvasOperator.getSaveRatio();
        ScreenInfo screenInfo = this.mPenEngine.getScreenInfo();
        Objects.requireNonNull(this.mPenEngine.getScreenInfo());
        this.mRatioA = screenInfo.a(1.5f, this.mSavaRatio);
        this.mDataCurve = new DataCurve(this.mCurveProp, System.currentTimeMillis());
        this.curveForAdditiveDrawing = new DataCurve(this.mCurveProp, System.currentTimeMillis());
        this.shapeCurveForAdditiveDrawing = new DataCurve(this.mCurveProp, System.currentTimeMillis());
        ScaleInfo scaleInfo = this.mCanvasOperator.getScaleInfo();
        this.mDataCurve.setValue(scaleInfo.getScaleRate(), scaleInfo.getTranslate(), this.mCanvasOperator.getDrawRatio());
        this.curveForAdditiveDrawing.setValue(scaleInfo.getScaleRate(), scaleInfo.getTranslate(), this.mCanvasOperator.getDrawRatio());
        this.shapeCurveForAdditiveDrawing.setValue(scaleInfo.getScaleRate(), scaleInfo.getTranslate(), this.mCanvasOperator.getDrawRatio());
        this.mShapeRecognizeCurveInfo = new ShapeRecognizeCurveInfo(this.mDataCurve, z);
        this.mCurveFittingProcessor = new CurveFittingProcessor(this.mPenEngine, this.mDataCurve, z);
        if (FeatureManager.getInstance().frontBufferEnable()) {
            this.mGlFastRenderer = this.mPenEngine.mFrontBufferedRenderProcessor.getMFrontBufferRenderer();
        }
    }

    private boolean isDifferentPoint(TouchPoint touchPoint, TouchPoint touchPoint2) {
        if (touchPoint != null && touchPoint2 != null) {
            return (!this.mIsAdsorbent && Float.compare(touchPoint.getX(), touchPoint2.getX()) == 0 && Float.compare(touchPoint.getY(), touchPoint2.getY()) == 0 && Float.compare(touchPoint.getPressure(), touchPoint2.getPressure()) == 0) ? false : true;
        }
        Log.w(TAG, "isDifferentPoint: touchPoint is null");
        return false;
    }

    private boolean setPointToPointPath(boolean z, TouchPoint touchPoint, TouchPoint touchPoint2) {
        float x = touchPoint.getX() * this.mSavaRatio;
        float y = touchPoint.getY() * this.mSavaRatio;
        float x2 = touchPoint2.getX() * this.mSavaRatio;
        float y2 = touchPoint2.getY() * this.mSavaRatio;
        if (z) {
            x = (x + x2) / 2.0f;
            y = (y + y2) / 2.0f;
        }
        if (isUseBezier(x, y, x2, y2)) {
            this.mCurrentPath.quadTo(x2, y2, x, y);
            return true;
        }
        this.mCurrentPath.lineTo(x, y);
        return true;
    }

    private boolean isUseBezier(float f, float f2, float f3, float f4) {
        float fAbs = Math.abs(f3 - f);
        float fAbs2 = Math.abs(f4 - f2);
        float f5 = this.mRatioA;
        return fAbs > f5 || fAbs2 > f5;
    }

    private RectF calculateDrawRect(List<CsPoint> list, float f) {
        RectF rectF = new RectF();
        if (list.size() > 0) {
            CsPoint csPoint = list.get(0);
            rectF.set(csPoint.getX(), csPoint.getY(), csPoint.getX(), csPoint.getY());
        }
        for (int i = 1; i < list.size(); i++) {
            CsPoint csPoint2 = list.get(i);
            rectF.union(csPoint2.getX(), csPoint2.getY());
        }
        rectF.left *= f;
        rectF.top *= f;
        rectF.right *= f;
        rectF.bottom *= f;
        rectF.offset(getCurvePoint(this.mDataCurve).x, getCurvePoint(this.mDataCurve).y);
        return rectF;
    }

    private float segmentPath(float f) {
        if (f <= 420.0f) {
            return f;
        }
        Path path = new Path();
        PathMeasure pathMeasure = this.mPathMeasure;
        if (!pathMeasure.getSegment(this.mStartLength, pathMeasure.getLength(), path, true)) {
            return f;
        }
        float f2 = f - this.mStartLength;
        this.mStartLength = 0.0f;
        this.mPathMeasure.setPath(path, false);
        this.mCurrentPath.set(path);
        return f2;
    }

    private List<CsPoint> interpolatePoints(List<CsPoint> list) {
        long jNanoTime = System.nanoTime();
        ArrayList arrayList = new ArrayList();
        int size = list.size();
        Iterator<CsPoint> it = list.iterator();
        while (it.hasNext()) {
            this.mCurveFittingProcessor.processCurveFitting(it.next(), arrayList);
        }
        list.clear();
        Log.i(TAG, "interpolatePoints cost: " + ((System.nanoTime() - jNanoTime) / 1000000.0f) + "ms, exec times: " + size + ", points: " + arrayList.size());
        return arrayList;
    }

    @Override // xc.engine.operate.canvas.track.IOperator
    public void applyAdsorbent(boolean z) {
        this.mIsAdsorbent = z;
    }

    @Override // xc.engine.operate.canvas.track.IOperator
    public void cancel() {
        onCancelII();
    }

    @Override // xc.engine.operate.canvas.track.IOperator
    public void hoverMove(HoverPoint hoverPoint, boolean z, float f, int i) {
        CsPoint csPoint = new CsPoint(hoverPoint.getX(), hoverPoint.getY(), hoverPoint.getPressure(), hoverPoint.getTilt(), hoverPoint.getOrientation());
        if (this.mPenEngine.hasBindSurfaceView) {
            postFastRenderingForHover(csPoint, z, i, TouchType.TOUCH_HOVER);
        }
    }

    @Override // xc.engine.operate.canvas.track.IOperator
    public void hoverExit() {
        if (this.mPenEngine.hasBindSurfaceView) {
            postFastRenderingForHover(null, false, 0, TouchType.TOUCH_HOVER_EXIT);
        }
    }

    private boolean addPointsToPath(TouchPoint[] touchPointArr, int i) {
        boolean pointToPointPath;
        if (touchPointArr == null || touchPointArr.length == 0) {
            return true;
        }
        TouchPoint touchPoint = this.mCurTouchPoint;
        if (touchPointArr.length > 1) {
            pointToPointPath = false;
            for (int i2 = 0; i2 < touchPointArr.length - 1; i2++) {
                TouchPoint touchPoint2 = touchPointArr[i2];
                if (isDifferentPoint(touchPoint2, touchPoint)) {
                    pointToPointPath = setPointToPointPath(true, touchPoint2, touchPoint);
                    touchPoint = touchPoint2;
                }
            }
        } else {
            pointToPointPath = false;
        }
        TouchPoint touchPoint3 = touchPointArr[touchPointArr.length - 1];
        if (isDifferentPoint(touchPoint3, touchPoint)) {
            return setPointToPointPath(i == 2, touchPoint3, touchPoint);
        }
        return pointToPointPath;
    }

    private void touchPointToCsPoint(TouchPoint touchPoint, List<CsPoint> list, int i) {
        char c;
        if (touchPoint == null) {
            return;
        }
        this.mPathMeasure.setPath(this.mCurrentPath, false);
        float fSegmentPath = segmentPath(this.mPathMeasure.getLength());
        float f = this.mStartLength;
        if (fSegmentPath - f == 0.0f) {
            return;
        }
        float f2 = Math.abs(f - fSegmentPath) < 4.2f ? 1.2f : 4.2f;
        float pressure = this.mCurTouchPoint.getPressure();
        float tilt = this.mCurTouchPoint.getTilt();
        float velocity = this.mCurTouchPoint.getVelocity();
        float postureDegree = this.mCurTouchPoint.getPostureDegree();
        float postureDegree2 = touchPoint.getPostureDegree();
        float f3 = f2 / (fSegmentPath - this.mStartLength);
        float pressure2 = (touchPoint.getPressure() - pressure) * f3;
        float tilt2 = (touchPoint.getTilt() - tilt) * f3;
        float velocity2 = (touchPoint.getVelocity() - velocity) * f3;
        float f4 = 360.0f;
        float f5 = f3 * ((((postureDegree2 - postureDegree) + 540.0f) % 360.0f) - 180.0f);
        float f6 = pressure;
        float f7 = tilt;
        float f8 = velocity;
        while (true) {
            float f9 = this.mStartLength + f2;
            if (f9 > fSegmentPath) {
                c = 5;
                break;
            }
            if (!this.mPathMeasure.getMatrix(f9, this.mMatrix, 3)) {
                c = 5;
                break;
            }
            this.mStartLength = f9;
            this.mMatrix.getValues(this.mMatrixPoints);
            float[] fArr = this.mMatrixPoints;
            float f10 = f4;
            list.add(new CsPoint(fArr[2], fArr[5], f6, f7, touchPoint.getTime() - this.mDataCurve.mTime, touchPoint.getOrientation(), f8, (postureDegree + f10) % f10));
            f6 += pressure2;
            f7 += tilt2;
            f8 += velocity2;
            postureDegree += f5;
            f4 = f10;
        }
        float f11 = f4;
        if (i == 1 && this.mStartLength - f2 < fSegmentPath && this.mPathMeasure.getMatrix(fSegmentPath, this.mMatrix, 3)) {
            this.mStartLength = fSegmentPath;
            this.mMatrix.getValues(this.mMatrixPoints);
            float[] fArr2 = this.mMatrixPoints;
            list.add(new CsPoint(fArr2[2], fArr2[c], f6, f7, touchPoint.getTime() - this.mDataCurve.mTime, touchPoint.getOrientation(), f8, (postureDegree2 + f11) % f11));
        }
    }

    @Override // xc.engine.operate.canvas.track.IOperator
    public void onCancelII() {
        GLFrontBufferedWrapperRenderer gLFrontBufferedWrapperRenderer;
        if (!this.mPenEngine.hasBindSurfaceView || (gLFrontBufferedWrapperRenderer = this.mGlFastRenderer) == null) {
            return;
        }
        gLFrontBufferedWrapperRenderer.execOnGLThread(new Runnable() { // from class: xc.engine.operate.canvas.track.curve.CurveOperator$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4631xf36bc5b2();
            }
        });
        this.mPenEngine.getCurveEngine().getFrontBufferSystem().onCancel();
    }

    /* JADX INFO: renamed from: lambda$onCancelII$0$xc-engine-operate-canvas-track-curve-CurveOperator, reason: not valid java name */
    /* synthetic */ void m4631xf36bc5b2() {
        if (this.mPenEngine.mFrontBufferedDrawer != null) {
            this.mPenEngine.mFrontBufferedDrawer.clearContentForce();
        }
    }

    @Override // xc.engine.operate.canvas.track.IOperator
    public void onDown(TouchPoint touchPoint, TouchPoint touchPoint2) {
        this.mDataCurve.mTime = touchPoint.getTime();
        this.mCurrentPath.reset();
        this.mCurrentPath.moveTo(touchPoint.getX() * this.mSavaRatio, touchPoint.getY() * this.mSavaRatio);
        this.mCurRecognizeShapeData = null;
        this.mCurTouchPoint = touchPoint;
        CsPoint csPoint = new CsPoint(touchPoint.getX() * this.mSavaRatio, touchPoint.getY() * this.mSavaRatio, touchPoint.getPressure(), touchPoint.getTilt(), touchPoint.getTime() - this.mDataCurve.mTime, touchPoint.getOrientation(), touchPoint.getVelocity(), touchPoint.getPostureDegree());
        this.mCurveFittingProcessor.prepareForCurveFitting(csPoint);
        DataCurve dataCurve = this.mDataCurve;
        dataCurve.insertPoint(dataCurve.mSavePoints, csPoint.getX(), csPoint.getY(), csPoint.getPressure(), csPoint.getTilt(), csPoint.getOrientation(), csPoint.getVelocity(), csPoint.getTime(), (short) 1, csPoint.getRotateAngle());
        if (!this.mEnableScale || CurvePointListOperator.size(getCurvePoints(this.mDataCurve)) <= 0) {
            this.mStartCsPoint = new CsPoint(touchPoint.getX(), touchPoint.getY(), touchPoint.getPressure(), touchPoint.getTime());
        } else {
            this.mStartCsPoint = new CsPoint(CurvePointListOperator.newCurvePointList(getCurvePoints(this.mDataCurve)).get(0));
        }
        PointF pointFCalCurveOutlineThickness = this.mDataCurve.calCurveOutlineThickness(true, this.mEnableScale);
        this.mDrawRect = new RectF(this.mStartCsPoint.getX() - pointFCalCurveOutlineThickness.x, this.mStartCsPoint.getY() - pointFCalCurveOutlineThickness.y, this.mStartCsPoint.getX() + pointFCalCurveOutlineThickness.x, this.mStartCsPoint.getY() + pointFCalCurveOutlineThickness.y);
        ICurvePointList iCurvePointListNewCurvePointList = CurvePointListOperator.newCurvePointList(getCurvePoints(this.mDataCurve));
        ArrayList<CsPoint> arrayList = new ArrayList();
        for (int i = 0; i < iCurvePointListNewCurvePointList.size(); i++) {
            iCurvePointListNewCurvePointList.get(i).setRotateAngle(touchPoint.getPostureDegree());
            arrayList.add(new CsPoint(iCurvePointListNewCurvePointList.get(i)));
        }
        if (this.mPenEngine.getCurveEngine().getPageMode() != MiCurveEngine.EPageMode.Infinite) {
            this.mDataCurve.pageIndex = this.mPenEngine.mTouchEventOperator.mTouchPointEstimator.currentCurvePageIndex;
            PageSystem.PageData pageData = this.mPenEngine.getCurveEngine().getPageSystem().getPageData(this.mDataCurve.pageIndex);
            this.mDataCurve.renderOffsetX = pageData.getOriginXInWS();
            this.mDataCurve.renderOffsetY = pageData.getOriginYInWS();
        }
        for (CsPoint csPoint2 : arrayList) {
            DataCurve dataCurve2 = this.curveForAdditiveDrawing;
            dataCurve2.insertPoint(dataCurve2.mOriginPoints, csPoint2.getX(), csPoint2.getY(), csPoint2.getPressure(), csPoint2.getTilt(), csPoint2.getOrientation(), csPoint2.getVelocity(), csPoint2.getTime(), (short) 1, csPoint2.getRotateAngle());
        }
        if (this.mPenEngine.hasBindSurfaceView) {
            postFastRendering(this.curveForAdditiveDrawing.mOriginPoints, 0, arrayList.size(), 0L, 0L);
        }
        this.mPenEngine.getCurveEngine().getFrontBufferSystem().onDown();
        this.mPenEngine.getCurveEngine().getHighLightSystem().shutdownHighlight();
    }

    private boolean shouldUpdate(long j) {
        long jNanoTime = System.nanoTime() / 1000000;
        if (jNanoTime - this.lastMoveTime < j) {
            return false;
        }
        this.lastMoveTime = jNanoTime;
        return true;
    }

    @Override // xc.engine.operate.canvas.track.IOperator
    public void onMove(TouchPoint[] touchPointArr, TouchPoint[] touchPointArr2, TouchPoint touchPoint) {
        boolean z;
        RectF rectFCalculateDrawRect;
        TouchPoint touchPoint2;
        if (this.mPenEngine.hasBindSurfaceView && FeatureManager.getInstance().frameControlEnable() && !shouldUpdate(MOVE_THRESHOLD_TIME)) {
            return;
        }
        System.nanoTime();
        ArrayList arrayList = new ArrayList();
        boolean z2 = false;
        if (touchPointArr != null && touchPointArr.length > 2 && this.mIsAdsorbent) {
            int length = touchPointArr.length;
            int i = 0;
            while (i < length) {
                TouchPoint touchPoint3 = touchPointArr[i];
                if (touchPoint3 != null && (touchPoint2 = this.mCurTouchPoint) != null) {
                    setPointToPointPath(z2, touchPoint3, touchPoint2);
                    this.mPathMeasure.setPath(this.mCurrentPath, z2);
                    this.mStartLength = segmentPath(this.mPathMeasure.getLength());
                    arrayList.add(new CsPoint(this.mSavaRatio * touchPoint3.getX(), this.mSavaRatio * touchPoint3.getY(), touchPoint3.getPressure(), touchPoint3.getTilt(), touchPoint3.getTime() - this.mDataCurve.mTime, touchPoint3.getOrientation(), touchPoint3.getVelocity(), touchPoint3.getPostureDegree()));
                    this.mCurTouchPoint = touchPoint3;
                }
                i++;
                z2 = false;
            }
            z = true;
        } else {
            z = true;
            if (!addPointsToPath(touchPointArr, 2)) {
                return;
            }
            if (touchPointArr != null && touchPointArr.length > 0) {
                TouchPoint touchPoint4 = touchPointArr[touchPointArr.length - 1];
                touchPointToCsPoint(touchPoint4, arrayList, 2);
                this.mCurTouchPoint = touchPoint4;
            }
        }
        if (arrayList.isEmpty()) {
            rectFCalculateDrawRect = new RectF(this.mStartCsPoint.getX(), this.mStartCsPoint.getY(), this.mStartCsPoint.getX(), this.mStartCsPoint.getY());
        } else {
            rectFCalculateDrawRect = calculateDrawRect(arrayList, getDrawRatio(this.mDataCurve));
        }
        rectFCalculateDrawRect.union((this.mCurveFittingProcessor.mCurPoint.getX() * getDrawRatio(this.mDataCurve)) + getCurvePoint(this.mDataCurve).x, (this.mCurveFittingProcessor.mCurPoint.getY() * getDrawRatio(this.mDataCurve)) + getCurvePoint(this.mDataCurve).y);
        rectFCalculateDrawRect.union((this.mCurveFittingProcessor.mStartPoint.x * getDrawRatio(this.mDataCurve)) + getCurvePoint(this.mDataCurve).x, (this.mCurveFittingProcessor.mStartPoint.y * getDrawRatio(this.mDataCurve)) + getCurvePoint(this.mDataCurve).y);
        rectFCalculateDrawRect.union(this.mStartCsPoint.getX(), this.mStartCsPoint.getY());
        for (Iterator it = arrayList.iterator(); it.hasNext(); it = it) {
            CsPoint csPoint = (CsPoint) it.next();
            DataCurve dataCurve = this.mDataCurve;
            dataCurve.insertPoint(dataCurve.mSavePoints, csPoint.getX(), csPoint.getY(), csPoint.getPressure(), csPoint.getTilt(), csPoint.getOrientation(), csPoint.getVelocity(), csPoint.getTime(), (short) 1, csPoint.getRotateAngle());
        }
        List<CsPoint> arrayList2 = new ArrayList<>();
        if (!arrayList.isEmpty()) {
            arrayList2 = interpolatePoints(arrayList);
            if (!arrayList2.isEmpty()) {
                this.mStartCsPoint = arrayList2.get(arrayList2.size() - 1);
            }
        }
        if (touchPointArr2 != null) {
            for (TouchPoint touchPoint5 : touchPointArr2) {
                rectFCalculateDrawRect.union((touchPoint5.getX() * this.mDataCurve.mScaleRate) + this.mDataCurve.mBasePoint.x, (touchPoint5.getY() * this.mDataCurve.mScaleRate) + this.mDataCurve.mBasePoint.y);
            }
        }
        this.mPathA.set(this.mCurveFittingProcessor.mPath);
        long jCreateLine = CurvePointListOperator.createLine();
        if (touchPointArr2 != null) {
            float f = this.mCurveFittingProcessor.mStartPathDistance;
            CsPoint csPoint2 = this.mCurveFittingProcessor.mCurPoint;
            for (int i2 = 0; i2 < touchPointArr2.length; i2++) {
                TouchPoint touchPoint6 = touchPointArr2[i2];
                if (i2 == touchPointArr2.length - 1 && touchPoint6.isPredictPoint()) {
                    this.mPathA.lineTo(touchPoint6.getX() * this.mCurveFittingProcessor.mCanvasScaleRate, touchPoint6.getY() * this.mCurveFittingProcessor.mCanvasScaleRate);
                } else {
                    this.mCurveFittingProcessor.addPointToPath(this.mPathA, touchPoint6.getX() * this.mCurveFittingProcessor.mCanvasScaleRate, touchPoint6.getY() * this.mCurveFittingProcessor.mCanvasScaleRate);
                }
                CurveFittingProcessor curveFittingProcessor = this.mCurveFittingProcessor;
                curveFittingProcessor.interpolateCurvePoints(this.mPathA, curveFittingProcessor.mCurPoint, jCreateLine);
                this.mCurveFittingProcessor.mCurPoint = new CsPoint((touchPoint6.getX() * this.mCurveFittingProcessor.mCanvasScaleRate) / this.mCurveFittingProcessor.mDrawRatio, (touchPoint6.getY() * this.mCurveFittingProcessor.mCanvasScaleRate) / this.mCurveFittingProcessor.mDrawRatio, touchPoint6.getPressure(), touchPoint6.getTilt(), touchPoint6.getTime(), 0.0f, 1.0f, touchPoint6.getPostureDegree());
            }
            this.mCurveFittingProcessor.mStartPathDistance = f;
            this.mCurveFittingProcessor.mCurPoint = csPoint2;
        }
        this.mDataCurve.expandRectByCurveOutlineThickness(rectFCalculateDrawRect, z, this.mEnableScale);
        this.mDrawRect.union(rectFCalculateDrawRect);
        ICurvePointList iCurvePointListNewCurvePointList = CurvePointListOperator.newCurvePointList(jCreateLine);
        if (!arrayList2.isEmpty()) {
            float rotateAngle = arrayList2.get(0).getRotateAngle();
            for (int i3 = 0; i3 < iCurvePointListNewCurvePointList.size(); i3++) {
                iCurvePointListNewCurvePointList.get(i3).setRotateAngle(rotateAngle);
            }
        }
        if (touchPointArr2 != null && touchPointArr2.length > 0) {
            this.lastOriginEstPoints = touchPointArr2;
        } else {
            this.lastOriginEstPoints = null;
        }
        DataCurve dataCurve2 = this.curveForAdditiveDrawing;
        int size = dataCurve2.size(dataCurve2.mOriginPoints);
        for (Iterator<CsPoint> it2 = arrayList2.iterator(); it2.hasNext(); it2 = it2) {
            CsPoint next = it2.next();
            DataCurve dataCurve3 = this.curveForAdditiveDrawing;
            dataCurve3.insertPoint(dataCurve3.mOriginPoints, next.getX(), next.getY(), next.getPressure(), next.getTilt(), next.getOrientation(), next.getVelocity(), next.getTime(), (short) 1, next.getRotateAngle());
        }
        if (this.mPenEngine.hasBindSurfaceView) {
            long j = this.curveForAdditiveDrawing.mOriginPoints;
            int size2 = arrayList2.size();
            DataCurve dataCurve4 = this.mFrontShapeCurve;
            postFastRendering(j, size, size2, jCreateLine, dataCurve4 == null ? 0L : dataCurve4.mScaledPoints);
        }
        this.mPenEngine.getCurveEngine().getHighLightSystem().shutdownHighlight();
    }

    @Override // xc.engine.operate.canvas.track.IOperator
    public void onUp(TouchPoint[] touchPointArr) {
        RectF rectFCalculateDrawRect;
        DataCurve dataCurve = this.mShapeRecognizeCurveInfo.mRecognizedDataCurve;
        boolean z = dataCurve == null;
        DataCurve dataCurve2 = z ? this.mDataCurve : dataCurve;
        long j = 20000000;
        if (20000000 < ((long) dataCurve2.size(dataCurve2.mSavePoints)) + this.mPenEngine.mDataAccess.getDataSet().mExtraTotalPoint) {
            onCancelII();
            this.mPenEngine.mErrorListener.onError(new Exception(CreationPageSDK.ALL_POINT_LIMIT));
            return;
        }
        if (!this.mPenEngine.mDataAccess.getDataSet().isPointLimit(dataCurve2.size(dataCurve2.mSavePoints), CreationPageSDK.getMaxPoints(), dataCurve2.pageIndex)) {
            onCancelII();
            this.mPenEngine.mErrorListener.onError(new Exception(CreationPageSDK.PAGE_POINT_LIMIT));
            return;
        }
        if (z) {
            DataCurve dataCurve3 = this.mDataCurve;
            ICurvePointList curvePointList = dataCurve3.getCurvePointList(dataCurve3.mSavePoints);
            if (curvePointList.size() > 0) {
                ICurvePoint iCurvePoint = curvePointList.get(curvePointList.size() - 1);
                this.mCurTouchPoint.setX(iCurvePoint.getX() / this.mSavaRatio);
                this.mCurTouchPoint.setY(iCurvePoint.getY() / this.mSavaRatio);
            }
            TouchPoint[] touchPointArr2 = this.lastOriginEstPoints;
            if (touchPointArr2 == null || touchPointArr2.length <= 0) {
                touchPointArr2 = touchPointArr;
            }
            addPointsToPath(touchPointArr2, 1);
            TouchPoint touchPoint = touchPointArr2[touchPointArr2.length - 1];
            ArrayList<CsPoint> arrayList = new ArrayList();
            touchPointToCsPoint(touchPoint, arrayList, 1);
            if (arrayList.isEmpty()) {
                rectFCalculateDrawRect = new RectF(this.mStartCsPoint.getX(), this.mStartCsPoint.getY(), this.mStartCsPoint.getX(), this.mStartCsPoint.getY());
            } else {
                rectFCalculateDrawRect = calculateDrawRect(arrayList, getDrawRatio(dataCurve2));
                rectFCalculateDrawRect.union(this.mStartCsPoint.getX(), this.mStartCsPoint.getY());
            }
            this.mDrawRect.union(rectFCalculateDrawRect);
            for (CsPoint csPoint : arrayList) {
                DataCurve dataCurve4 = this.mDataCurve;
                dataCurve4.insertPoint(dataCurve4.mSavePoints, csPoint.getX(), csPoint.getY(), csPoint.getPressure(), csPoint.getTilt(), csPoint.getOrientation(), csPoint.getVelocity(), csPoint.getTime(), (short) 1, csPoint.getRotateAngle());
                j = j;
            }
            if (j < ((long) dataCurve2.size(dataCurve2.mSavePoints)) + this.mPenEngine.mDataAccess.getDataSet().mExtraTotalPoint) {
                onCancelII();
                this.mPenEngine.mErrorListener.onError(new Exception(CreationPageSDK.ALL_POINT_LIMIT));
                return;
            }
            if (!this.mPenEngine.mDataAccess.getDataSet().isPointLimit(dataCurve2.size(dataCurve2.mSavePoints), CreationPageSDK.getMaxPoints(), dataCurve2.pageIndex)) {
                onCancelII();
                this.mPenEngine.mErrorListener.onError(new Exception(CreationPageSDK.PAGE_POINT_LIMIT));
                return;
            }
            for (CsPoint csPoint2 : interpolatePoints(arrayList)) {
                DataCurve dataCurve5 = this.curveForAdditiveDrawing;
                dataCurve5.insertPoint(dataCurve5.mOriginPoints, csPoint2.getX(), csPoint2.getY(), csPoint2.getPressure(), csPoint2.getTilt(), csPoint2.getOrientation(), csPoint2.getVelocity(), csPoint2.getTime(), (short) 1, csPoint2.getRotateAngle());
            }
            this.mCurveFittingProcessor.endCurveFitting();
            this.mDataCurve.calculateBoundingRect();
            this.mDataCurve.expandRectByCurveOutlineThickness(rectFCalculateDrawRect, true, this.mEnableScale);
            DataSet dataSet = this.mPenEngine.mDataAccess.getDataSet();
            this.mDataCurve.mId = dataSet.mId;
            dataSet.insertDataCurve(this.mDataCurve);
            dataSet.addId(1);
            this.mPenEngine.mCurveDataManager.addDataToPool(this.mDataCurve);
        } else {
            if (this.mPenEngine.hasBindSurfaceView) {
                postFastRendering(0L, 0, -1, 0L, this.mFrontShapeCurve.mScaledPoints);
            }
            this.mDataCurve.calculateBoundingRect();
            RectF saveRect = this.mDataCurve.getSaveRect();
            saveRect.set(PenEngineUtils.calculateUnionRectF(saveRect, dataCurve.getSaveRect()));
            this.mDataCurve.translateRectByPenPoint(new RectF(saveRect.left * this.mDataCurve.mDrawRatio, saveRect.top * this.mDataCurve.mDrawRatio, saveRect.right * this.mDataCurve.mDrawRatio, saveRect.bottom * this.mDataCurve.mDrawRatio), true);
            DataSet dataSet2 = this.mPenEngine.mDataAccess.getDataSet();
            dataCurve.mId = dataSet2.mId;
            dataCurve.pageIndex = this.mDataCurve.pageIndex;
            dataCurve.renderOffsetX = this.mDataCurve.renderOffsetX;
            dataCurve.renderOffsetY = this.mDataCurve.renderOffsetY;
            dataSet2.insertDataCurve(dataCurve);
            dataSet2.addId(1);
            this.mPenEngine.mCurveDataManager.addDataToPool(dataCurve);
            this.mDataCurve.releasePointList();
        }
        this.mPenEngine.getCurveEngine().getFrontBufferSystem().onUp();
        if (z) {
            this.mPenEngine.getCurveEngine().getCurveSystem().updateNewCurve(this.mDataCurve.mRect, this.curveForAdditiveDrawing);
            this.shapeCurveForAdditiveDrawing.releasePointList();
        } else {
            this.mPenEngine.getCurveEngine().getCurveSystem().updateNewCurve(this.mDataCurve.mRect, this.shapeCurveForAdditiveDrawing);
            this.curveForAdditiveDrawing.releasePointList();
        }
        GLFrontBufferedWrapperRenderer gLFrontBufferedWrapperRenderer = this.mGlFastRenderer;
        if (gLFrontBufferedWrapperRenderer != null) {
            gLFrontBufferedWrapperRenderer.commitToMultiBufferedLayer();
        }
        this.mPenEngine.mErrorListener.onSuccess();
        setRecognizeShapeData(null);
        this.mPenEngine.mStepManager.addStep(new StepEditWrite(this.mPenEngine, dataCurve2, 1));
        MathDataManager mathDataManager = this.mPenEngine.getCurveEngine().getMathDataManager();
        try {
            if (!mathDataManager.getHasUserStartedDrawing()) {
                mathDataManager.setUserStartedDrawing(true);
                Log.d("MathSystem", "First curve completed - enabling auto selection");
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to set user started drawing state", e);
        }
        try {
            MathSystem mathSystem = this.mPenEngine.getCurveEngine().getMathSystem();
            if (mathSystem != null) {
                mathSystem.setFromUndoRedo(false);
                mathSystem.setHasJustCleared(false);
            }
        } catch (Exception e2) {
            Log.e(TAG, "Failed to reset flags", e2);
        }
    }

    private void postFastRendering(long j, int i, int i2, long j2, long j3) {
        if (FeatureManager.getInstance().frontBufferEnable()) {
            this.mGlFastRenderer.renderOnFrontBuffer(CurveSegment.INSTANCE.constructForPenDrawing(j, i, i2, j2, j3));
        }
    }

    private void postFastRenderingForHover(CsPoint csPoint, boolean z, int i, TouchType touchType) {
        if (FeatureManager.getInstance().frontBufferEnable()) {
            if (touchType == TouchType.TOUCH_HOVER_EXIT) {
                this.mGlFastRenderer.renderOnFrontBuffer(CurveSegment.INSTANCE.constructForExit());
            } else if (z) {
                this.mGlFastRenderer.renderOnFrontBuffer(CurveSegment.INSTANCE.constructForEraserHover(csPoint));
            } else {
                this.mGlFastRenderer.renderOnFrontBuffer(CurveSegment.INSTANCE.constructForPenHover(csPoint, i));
            }
        }
    }

    public void setRecognizeShapeData(RecognizeData recognizeData) {
        if (this.mCurRecognizeShapeData == null && recognizeData == null) {
            return;
        }
        this.mCurRecognizeShapeData = recognizeData;
        ScaleInfo drawerScaleInfo = this.mPenEngine.mDrawerWrapper.mDrawer.getDrawerScaleInfo();
        drawerScaleInfo.setScaleRate(this.mPenEngine.getCurveEngine().getScaleRatio());
        drawerScaleInfo.getTranslate().set(this.mPenEngine.getCurveEngine().getViewportOffset());
        RecognizeData recognizeData2 = this.mCurRecognizeShapeData;
        if (recognizeData2 == null || recognizeData2.getRecognizeCurveType() == 0) {
            this.mShapeRecognizeCurveInfo.mRecognizedDataCurve = null;
            this.mFrontShapeCurve = null;
            this.mShapeRecognizeCurveInfo.ReleaseRecognizedDataCurve();
        } else {
            RecognizeData recognizeData3 = this.mCurRecognizeShapeData;
            if (recognizeData3 instanceof RecognizeWithArrowData) {
                RecognizeWithArrowData recognizeWithArrowData = (RecognizeWithArrowData) recognizeData3;
                float fMax = Math.max(this.mShapeRecognizeCurveInfo.mOriginDataCurve.getCurveProp().getPenSize(), 7.0f);
                recognizeWithArrowData.setArrowBottom((int) (1.6f * fMax));
                recognizeWithArrowData.setArrowHeight((int) (fMax * 3.8f));
            }
            this.mShapeRecognizeCurveInfo.ReleaseRecognizedDataCurve();
            this.mShapeRecognizeCurveInfo.mRecognizedDataCurve = new DataCurve(this.mShapeRecognizeCurveInfo.mOriginDataCurve.getCurveProp(), System.currentTimeMillis());
            this.mShapeRecognizeCurveInfo.mRecognizedDataCurve.mCurveType = this.mCurRecognizeShapeData.getRecognizeCurveType();
            this.mShapeRecognizeCurveInfo.mRecognizedDataCurve.mDrawRatio = this.mShapeRecognizeCurveInfo.mOriginDataCurve.mDrawRatio;
            this.mShapeRecognizeCurveInfo.mRecognizedDataCurve.mScaleRate = this.mPenEngine.mCanvasOperator.getScaleInfo().getScaleRate();
            this.mShapeRecognizeCurveInfo.mRecognizedDataCurve.mBasePoint.set(0.0f, 0.0f);
            this.mShapeRecognizeCurveInfo.mRecognizedDataCurve.mPenTypeValue = this.mShapeRecognizeCurveInfo.mOriginDataCurve.mPenTypeValue;
            float f = 1.0f / this.mShapeRecognizeCurveInfo.mOriginDataCurve.mDrawRatio;
            Path path = new Path();
            Matrix matrix = new Matrix();
            matrix.setScale(f, f);
            this.mCurRecognizeShapeData.getPathResult().transform(matrix, path);
            path.set(this.mCurRecognizeShapeData.getPathResult());
            this.mShapeRecognizeCurveInfo.generateCurvePoints(path);
            this.mShapeRecognizeCurveInfo.mRecognizedDataCurve.calculateBoundingRect();
            try {
                if (this.mShapeRecognizeCurveInfo.mRecognizedDataCurve != null) {
                    DataCurve dataCurveClone = this.mShapeRecognizeCurveInfo.mRecognizedDataCurve.clone();
                    this.mFrontShapeCurve = dataCurveClone;
                    dataCurveClone.calculateBoundingRect();
                }
                path.reset();
                new CurveFittingProcessor(this.mPenEngine, this.mShapeRecognizeCurveInfo.mRecognizedDataCurve, path, this.mShapeRecognizeCurveInfo.mEnableScale).fillCurvePoints();
                if (this.mFrontShapeCurve != null) {
                    new CurveFittingProcessor(this.mPenEngine, this.mFrontShapeCurve, path, this.mShapeRecognizeCurveInfo.mEnableScale).fillCurvePoints();
                }
            } catch (CloneNotSupportedException unused) {
                Log.d(TAG, "clone error");
                return;
            }
        }
        if (this.mPenEngine.hasBindSurfaceView) {
            DataCurve dataCurve = this.mFrontShapeCurve;
            if (dataCurve != null) {
                adjustCurveCoordinateToScreen(dataCurve, drawerScaleInfo);
                postFastRendering(0L, 0, -1, 0L, this.mFrontShapeCurve.mScaledPoints);
            } else {
                postFastRendering(0L, 0, -1, 0L, 0L);
            }
        }
    }

    private RectF calculateShapeDrawRect(DataCurve dataCurve) {
        ICurvePointList curvePointList = dataCurve.getCurvePointList(dataCurve.mScaledPoints);
        ICurvePoint iCurvePoint = curvePointList.get(0);
        ICurvePoint iCurvePoint2 = curvePointList.get(1);
        RectF rectF = new RectF(Math.min(iCurvePoint.getX(), iCurvePoint2.getX()), Math.min(iCurvePoint.getY(), iCurvePoint2.getY()), Math.max(iCurvePoint.getX(), iCurvePoint2.getX()), Math.max(iCurvePoint.getY(), iCurvePoint2.getY()));
        for (int i = 2; i < curvePointList.size(); i++) {
            ICurvePoint iCurvePoint3 = curvePointList.get(i);
            rectF.union(iCurvePoint3.getX(), iCurvePoint3.getY());
        }
        return rectF;
    }

    private void adjustCurveCoordinateToScreen(DataCurve dataCurve, ScaleInfo scaleInfo) {
        PointF translate = scaleInfo.getTranslate();
        float f = translate.x;
        float f2 = translate.y;
        ICurvePointList curvePointList = dataCurve.getCurvePointList(dataCurve.mScaledPoints);
        int size = curvePointList.size();
        float[] fArr = new float[size];
        float[] fArr2 = new float[size];
        for (int i = 0; i < size; i++) {
            float x = curvePointList.getX(i) + f;
            float y = curvePointList.getY(i) + f2;
            fArr[i] = x;
            fArr2[i] = y;
            curvePointList.setX(i, x);
            curvePointList.setY(i, y);
        }
        curvePointList.setNativeX(fArr, 0, size);
        curvePointList.setNativeY(fArr2, 0, size);
        MiCurveNative.duplicatePoints(this.mFrontShapeCurve.mScaledPoints, this.shapeCurveForAdditiveDrawing.mOriginPoints);
    }

    public DataCurve getCurDataCurve() {
        return this.mDataCurve;
    }
}
