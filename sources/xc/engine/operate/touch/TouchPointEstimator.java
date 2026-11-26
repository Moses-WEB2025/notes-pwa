package xc.engine.operate.touch;

import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import java.util.ArrayList;
import java.util.List;
import xc.engine.api.CreationPageSDK;
import xc.engine.api.PenEngine;
import xc.engine.core.algorithm.recognize.data.RecognizeData;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.operate.canvas.draw.scale.ScaleInfo;
import xc.engine.operate.rule.GlobalRulerOperator;
import xc.engine.operate.rule.RulerOperator;
import xc.engine.utils.PreferenceUtils;

/* JADX INFO: loaded from: classes6.dex */
public class TouchPointEstimator {
    public static final int WHAT_RECOGNIZE = 2001;
    public boolean isApplyAdsorbentMode;
    public volatile boolean isShapeRecognizeSucceed;
    public TouchPoint mCurTouchPoint;
    public Handler mHandler;
    public PenEngine mPenEngine;
    public float mPressureDiff;
    private final String TAG = "TouchPointEstimator";
    public volatile boolean isDrawing = false;
    public final PathMeasure mPathMeasure = new PathMeasure();
    public float BASE_ORIENTATION = 1.5707964f;
    public TouchPoint mCurEstimatedPoint = null;
    public float curPressure = 0.0f;
    public float mCurOrientation = 0.0f;
    public List<TouchPoint> mRecognizeRecordingPoints = new ArrayList();
    public final PointF mRecognizeStartPoint = new PointF();
    public boolean hasEstimatedPoints = false;
    public Runnable mShapeRecognizeTask = new ShapeRecognizeTask();
    public ScaleInfo mScaleInfo = new ScaleInfo();
    private int mLastTouchDegree = 0;
    private int mLastEstimatedDegree = 0;
    public int currentCurvePageIndex = 0;
    public VelocityTracker mVelocityTracker = VelocityTracker.obtain();

    public class ShapeRecognizeTask implements Runnable {
        public ShapeRecognizeTask() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (TouchPointEstimator.this.mRecognizeRecordingPoints.size() == 0) {
                GlobalRulerOperator globalRulerOperator = TouchPointEstimator.this.mPenEngine.mGlobalRulerOperator;
                if (!TouchPointEstimator.this.isDrawing || TouchPointEstimator.this.isShapeRecognizeSucceed) {
                    return;
                }
                if (globalRulerOperator == null || !globalRulerOperator.isAdsorbent()) {
                    TouchPointEstimator.this.doShapeRecognize();
                    return;
                }
                return;
            }
            float x = TouchPointEstimator.this.mRecognizeRecordingPoints.get(0).getX();
            float y = TouchPointEstimator.this.mRecognizeRecordingPoints.get(0).getY();
            float fMax = y;
            float fMin = fMax;
            float fMin2 = x;
            for (TouchPoint touchPoint : TouchPointEstimator.this.mRecognizeRecordingPoints) {
                fMin2 = Math.min(touchPoint.getX(), fMin2);
                fMin = Math.min(touchPoint.getY(), fMin);
                x = Math.max(touchPoint.getX(), x);
                fMax = Math.max(touchPoint.getY(), fMax);
            }
            float fAbs = Math.abs(x - fMin2);
            float fAbs2 = Math.abs(fMax - fMin);
            if (fAbs < TouchPointEstimator.this.mPenEngine.mAlgorithm.getShapeRecognizeParams().getDistanceOffset() && fAbs2 < TouchPointEstimator.this.mPenEngine.mAlgorithm.getShapeRecognizeParams().getDistanceOffset()) {
                TouchPoint touchPoint2 = TouchPointEstimator.this.mRecognizeRecordingPoints.get(TouchPointEstimator.this.mRecognizeRecordingPoints.size() - 1);
                float fAbs3 = Math.abs(touchPoint2.getX() - TouchPointEstimator.this.mRecognizeStartPoint.x);
                float fAbs4 = Math.abs(touchPoint2.getY() - TouchPointEstimator.this.mRecognizeStartPoint.y);
                if (!TouchPointEstimator.this.isShapeRecognizeSucceed || fAbs3 > TouchPointEstimator.this.mPenEngine.mAlgorithm.getShapeRecognizeParams().getDistanceOffset() || fAbs4 > TouchPointEstimator.this.mPenEngine.mAlgorithm.getShapeRecognizeParams().getDistanceOffset()) {
                    TouchPointEstimator.this.mRecognizeStartPoint.set(TouchPointEstimator.this.mRecognizeRecordingPoints.get(0).getX(), TouchPointEstimator.this.mRecognizeRecordingPoints.get(0).getY());
                    TouchPointEstimator.this.doShapeRecognize();
                }
            } else {
                if (TouchPointEstimator.this.isShapeRecognizeSucceed) {
                    TouchPointEstimator.this.mPenEngine.mTouchEventOperator.setShape(null);
                }
                TouchPointEstimator.this.isShapeRecognizeSucceed = false;
            }
            TouchPointEstimator.this.mRecognizeRecordingPoints.clear();
            Message messageObtain = Message.obtain(TouchPointEstimator.this.mHandler, TouchPointEstimator.this.mShapeRecognizeTask);
            messageObtain.what = 2001;
            TouchPointEstimator.this.mHandler.sendMessageDelayed(messageObtain, TouchPointEstimator.this.mPenEngine.mAlgorithm.getShapeRecognizeParams().getTimeLot());
        }
    }

    public TouchPointEstimator(PenEngine penEngine) {
        this.mPenEngine = penEngine;
        this.mHandler = new Handler(this.mPenEngine.getEngineHandler().getLooper());
    }

    protected void reset() {
        this.mCurEstimatedPoint = null;
        this.curPressure = 0.0f;
        this.mHandler.removeMessages(2001);
        this.mHandler.post(new Runnable() { // from class: xc.engine.operate.touch.TouchPointEstimator$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4664lambda$reset$0$xcengineoperatetouchTouchPointEstimator();
            }
        });
        this.mPenEngine.mTouchEventOperator.onInnerCancelEvent();
        this.mPenEngine.mTouchEventOperator.setShape(null);
        this.isShapeRecognizeSucceed = false;
    }

    /* JADX INFO: renamed from: lambda$reset$0$xc-engine-operate-touch-TouchPointEstimator, reason: not valid java name */
    /* synthetic */ void m4664lambda$reset$0$xcengineoperatetouchTouchPointEstimator() {
        this.mRecognizeRecordingPoints.clear();
    }

    private float calculateNonStylusPressure(float f) {
        float xVelocity = this.mVelocityTracker.getXVelocity();
        float yVelocity = this.mVelocityTracker.getYVelocity();
        float fMin = Math.min(1.0f, Math.max((4.0f - (((float) Math.sqrt((xVelocity * xVelocity) + (yVelocity * yVelocity))) * this.mPenEngine.mScreenInfo.mDensity)) / 4.0f, 0.1f));
        float f2 = this.mPressureDiff;
        float f3 = (f * 0.687f) + (0.313f * f2 * fMin);
        if (f3 - f2 > 0.19f) {
            f3 = f2 + 0.19f;
        }
        if (f2 - f3 > 0.19f) {
            f3 = f2 - 0.19f;
        }
        this.mPressureDiff = f3;
        return f3;
    }

    private float calculateVelocity() {
        float xVelocity = this.mVelocityTracker.getXVelocity();
        float yVelocity = this.mVelocityTracker.getYVelocity();
        float fPow = (float) Math.pow(1.0d - Math.pow(1.600000023841858d, (((float) Math.sqrt((xVelocity * xVelocity) + (yVelocity * yVelocity))) * this.mPenEngine.mScreenInfo.mPhysicalSize) * (-1.0f)), 2.0d);
        if (fPow < 0.1f) {
            fPow = 1.0E-6f;
        }
        return Math.min(1.0f, fPow);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doShapeRecognize() {
        if (this.isApplyAdsorbentMode) {
            return;
        }
        RecognizeData recognizeData = null;
        this.mPenEngine.mTouchEventOperator.setShape(null);
        RecognizeData trajectoryRecognizeResult = this.mPenEngine.mAlgorithm.getRecognizeEngine().getTrajectoryRecognizeResult();
        if (trajectoryRecognizeResult != null) {
            this.mPathMeasure.setPath(trajectoryRecognizeResult.getPathResult(), false);
            if (this.mPathMeasure.getLength() >= this.mPenEngine.mAlgorithm.getShapeRecognizeParams().getMiniLineLength()) {
                this.isShapeRecognizeSucceed = true;
                recognizeData = trajectoryRecognizeResult;
            }
        } else {
            recognizeData = trajectoryRecognizeResult;
        }
        this.mPenEngine.mTouchEventOperator.setShape(recognizeData);
    }

    private void doEstimated(TouchPoint touchPoint, TouchPoint touchPoint2) {
        GlobalRulerOperator globalRulerOperator;
        this.mCurTouchPoint = touchPoint;
        xc.engine.core.algorithm.TouchPoint[] estimatedPoints = (!this.mPenEngine.mAlgorithm.isEnableEstimate() || ((globalRulerOperator = this.mPenEngine.mGlobalRulerOperator) != null && globalRulerOperator.isAdsorbent()) || !this.mPenEngine.mAlgorithm.getEstimatedGenerator().onTouchMoveEvent(touchPoint.getX(), touchPoint.getY(), touchPoint.getPressure(), touchPoint.getTilt(), touchPoint.getOrientation(), touchPoint.getTime(), touchPoint.isHistory())) ? null : this.mPenEngine.mAlgorithm.getEstimatedGenerator().getEstimatedPoints();
        int i = 1;
        TouchPoint[] touchPointArr = {touchPoint};
        int i2 = this.mLastEstimatedDegree;
        int postureDegree = touchPoint.getPostureDegree();
        ArrayList arrayList = new ArrayList();
        boolean z = estimatedPoints != null;
        this.hasEstimatedPoints = z;
        if (!z) {
            this.mPenEngine.mTouchEventOperator.onMoveEvent(touchPointArr, (TouchPoint[]) arrayList.toArray(new TouchPoint[0]), this.isApplyAdsorbentMode, touchPoint2);
            return;
        }
        TouchPoint[] touchPointArr2 = new TouchPoint[estimatedPoints.length];
        int i3 = 0;
        while (i3 < estimatedPoints.length) {
            int i4 = i3 + 1;
            float length = i2 + ((i4 / (estimatedPoints.length + i)) * (postureDegree - i2));
            xc.engine.core.algorithm.TouchPoint[] touchPointArr3 = estimatedPoints;
            TouchPoint touchPoint3 = new TouchPoint(estimatedPoints[i3].x, estimatedPoints[i3].y, estimatedPoints[i3].downTime);
            touchPointArr2[i3] = touchPoint3;
            touchPoint3.setPressure(touchPoint.getPressure());
            touchPointArr2[i3].setOrientation(touchPointArr3[i3].orientation);
            touchPointArr2[i3].setTilt(touchPoint.getTilt());
            touchPointArr2[i3].setPostureDegree((int) length);
            i = 1;
            touchPointArr2[i3].setPredictPoint(true);
            arrayList.add(touchPointArr2[i3]);
            i3 = i4;
            estimatedPoints = touchPointArr3;
        }
        this.mPenEngine.mTouchEventOperator.onMoveEvent(touchPointArr, (TouchPoint[]) arrayList.toArray(new TouchPoint[0]), this.isApplyAdsorbentMode, touchPoint2);
    }

    /* JADX WARN: Code duplicated, block: B:35:0x010d  */
    /* JADX WARN: Code duplicated, block: B:37:0x0141  */
    /* JADX WARN: Code duplicated, block: B:40:0x0164  */
    /* JADX WARN: Code duplicated, block: B:46:0x0175 A[SYNTHETIC] */
    protected boolean onTouchEvent(MotionEvent motionEvent) {
        int i;
        RulerOperator rulerOperator;
        int historySize;
        int i2;
        float f;
        float f2;
        float historicalPressure;
        float historicalAxisValue;
        float historicalOrientation;
        float f3;
        long time;
        int action = motionEvent.getAction();
        this.mScaleInfo = this.mPenEngine.mCanvasOperator.getScaleInfo();
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        TouchPoint touchPoint = new TouchPoint(x, y, motionEvent.getPressure());
        if (action == 0) {
            this.mVelocityTracker.clear();
            this.mVelocityTracker.addMovement(motionEvent);
            this.mVelocityTracker.computeCurrentVelocity(1);
            this.curPressure = 0.0f;
            if (this.mPenEngine.getCurveEngine().getPageMode() != MiCurveEngine.EPageMode.Infinite) {
                this.currentCurvePageIndex = this.mPenEngine.getCurveEngine().getPageSystem().getPageIndexByScreenSpacePoint(new PointF(x, y));
            }
        }
        int postureDegree = getPostureDegree();
        this.mLastTouchDegree = postureDegree;
        float fCalculateVelocity = calculateVelocity();
        float axisValue = motionEvent.getAxisValue(25);
        float orientation = motionEvent.getOrientation() + this.BASE_ORIENTATION;
        if (motionEvent.getAction() == 2 && Math.abs(orientation - this.mCurOrientation) >= 0.4487989505128276d) {
            orientation = this.mCurOrientation;
        }
        float f4 = orientation;
        float fCalculateWritingPressure = calculateWritingPressure(motionEvent);
        this.curPressure = fCalculateWritingPressure;
        this.mCurOrientation = f4;
        long eventTime = (this.mPenEngine.mCurrentTime + motionEvent.getEventTime()) - this.mPenEngine.mLastUptime;
        if (motionEvent.getToolType(0) == 1) {
            axisValue = 0.62f;
            i = 0;
        } else {
            i = postureDegree;
        }
        float f5 = axisValue;
        TouchPoint touchPoint2 = new TouchPoint(x, y, fCalculateWritingPressure, eventTime, fCalculateVelocity, i);
        touchPoint2.setOrientation(f4);
        touchPoint2.setTilt(f5);
        touchPoint2.setHistory(false);
        GlobalRulerOperator globalRulerOperator = this.mPenEngine.mGlobalRulerOperator;
        if (globalRulerOperator != null) {
            rulerOperator = globalRulerOperator.mRulerOperator;
            if (rulerOperator != null && rulerOperator.mEnable) {
                this.isApplyAdsorbentMode = globalRulerOperator.convertTouchPoint(touchPoint2, motionEvent);
            }
            if ((globalRulerOperator != null || rulerOperator == null || !rulerOperator.mEnable || !globalRulerOperator.isAdsorbent()) && motionEvent.getHistorySize() != 0) {
                historySize = motionEvent.getHistorySize();
                for (i2 = 0; i2 < historySize; i2++) {
                    PointF worldSpacePointFromScreenSpacePoint = this.mPenEngine.getCurveEngine().getWorldSpacePointFromScreenSpacePoint(new PointF(motionEvent.getHistoricalX(i2), motionEvent.getHistoricalY(i2)));
                    f = worldSpacePointFromScreenSpacePoint.x;
                    f2 = worldSpacePointFromScreenSpacePoint.y;
                    historicalPressure = motionEvent.getHistoricalPressure(i2);
                    historicalAxisValue = motionEvent.getHistoricalAxisValue(25, i2);
                    historicalOrientation = motionEvent.getHistoricalOrientation(i2) + this.BASE_ORIENTATION;
                    if (motionEvent.getToolType(0) != 2) {
                        historicalPressure = calculateNonStylusPressure(0.78f);
                    }
                    f3 = historicalPressure;
                    time = touchPoint2.getTime() - (motionEvent.getEventTime() - motionEvent.getHistoricalEventTime(i2));
                    if (this.mPenEngine.mAlgorithm.isEnableEstimate()) {
                        this.mPenEngine.mAlgorithm.getEstimatedGenerator().onTouchMoveEvent(f, f2, f3, historicalAxisValue, historicalOrientation, time, true);
                    }
                }
            }
            handleTouchEvent(motionEvent, touchPoint2, touchPoint);
            return true;
        }
        rulerOperator = null;
        this.isApplyAdsorbentMode = false;
        PointF worldSpacePointFromScreenSpacePoint2 = this.mPenEngine.getCurveEngine().getWorldSpacePointFromScreenSpacePoint(new PointF(x, y));
        touchPoint2.setX(worldSpacePointFromScreenSpacePoint2.x);
        touchPoint2.setY(worldSpacePointFromScreenSpacePoint2.y);
        if (globalRulerOperator != null) {
            historySize = motionEvent.getHistorySize();
            while (i2 < historySize) {
                PointF worldSpacePointFromScreenSpacePoint3 = this.mPenEngine.getCurveEngine().getWorldSpacePointFromScreenSpacePoint(new PointF(motionEvent.getHistoricalX(i2), motionEvent.getHistoricalY(i2)));
                f = worldSpacePointFromScreenSpacePoint3.x;
                f2 = worldSpacePointFromScreenSpacePoint3.y;
                historicalPressure = motionEvent.getHistoricalPressure(i2);
                historicalAxisValue = motionEvent.getHistoricalAxisValue(25, i2);
                historicalOrientation = motionEvent.getHistoricalOrientation(i2) + this.BASE_ORIENTATION;
                if (motionEvent.getToolType(0) != 2) {
                    historicalPressure = calculateNonStylusPressure(0.78f);
                }
                f3 = historicalPressure;
                time = touchPoint2.getTime() - (motionEvent.getEventTime() - motionEvent.getHistoricalEventTime(i2));
                if (this.mPenEngine.mAlgorithm.isEnableEstimate()) {
                    this.mPenEngine.mAlgorithm.getEstimatedGenerator().onTouchMoveEvent(f, f2, f3, historicalAxisValue, historicalOrientation, time, true);
                }
            }
        } else {
            historySize = motionEvent.getHistorySize();
            while (i2 < historySize) {
                PointF worldSpacePointFromScreenSpacePoint4 = this.mPenEngine.getCurveEngine().getWorldSpacePointFromScreenSpacePoint(new PointF(motionEvent.getHistoricalX(i2), motionEvent.getHistoricalY(i2)));
                f = worldSpacePointFromScreenSpacePoint4.x;
                f2 = worldSpacePointFromScreenSpacePoint4.y;
                historicalPressure = motionEvent.getHistoricalPressure(i2);
                historicalAxisValue = motionEvent.getHistoricalAxisValue(25, i2);
                historicalOrientation = motionEvent.getHistoricalOrientation(i2) + this.BASE_ORIENTATION;
                if (motionEvent.getToolType(0) != 2) {
                    historicalPressure = calculateNonStylusPressure(0.78f);
                }
                f3 = historicalPressure;
                time = touchPoint2.getTime() - (motionEvent.getEventTime() - motionEvent.getHistoricalEventTime(i2));
                if (this.mPenEngine.mAlgorithm.isEnableEstimate()) {
                    this.mPenEngine.mAlgorithm.getEstimatedGenerator().onTouchMoveEvent(f, f2, f3, historicalAxisValue, historicalOrientation, time, true);
                }
            }
        }
        handleTouchEvent(motionEvent, touchPoint2, touchPoint);
        return true;
    }

    protected boolean onHoverEvent(MotionEvent motionEvent, boolean z, float f) {
        if (motionEvent.getToolType(0) == 2) {
            if (motionEvent.getAction() == 9) {
                this.mPenEngine.mTouchEventOperator.hoverEnterEvent(this.isApplyAdsorbentMode);
                return true;
            }
            if (motionEvent.getAction() == 7) {
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                float axisValue = motionEvent.getAxisValue(25);
                float orientation = motionEvent.getOrientation() + this.BASE_ORIENTATION;
                HoverPoint hoverPoint = new HoverPoint(x, y, 0.1f);
                hoverPoint.setTilt(axisValue);
                hoverPoint.setOrientation(orientation);
                this.mPenEngine.mTouchEventOperator.hoverMoveEvent(hoverPoint, this.isApplyAdsorbentMode, z, f, getPostureDegree());
                return true;
            }
            if (motionEvent.getAction() == 10) {
                this.mPenEngine.mTouchEventOperator.hoverExitEvent();
            }
        }
        return true;
    }

    private void handleTouchEvent(MotionEvent motionEvent, final TouchPoint touchPoint, TouchPoint touchPoint2) {
        GlobalRulerOperator globalRulerOperator;
        if (motionEvent.getAction() == 3) {
            if (this.isDrawing) {
                this.isDrawing = false;
                reset();
                return;
            }
            return;
        }
        if (motionEvent.getAction() == 1) {
            if (this.isDrawing) {
                this.mVelocityTracker.addMovement(motionEvent);
                this.mVelocityTracker.computeCurrentVelocity(1);
                this.mPenEngine.mAlgorithm.getRecognizeEngine().addMove(touchPoint.getX(), touchPoint.getY(), touchPoint.getTime(), motionEvent.getAction());
                onUp(touchPoint);
                this.isDrawing = false;
                return;
            }
            return;
        }
        if (motionEvent.getAction() == 2) {
            if (this.isDrawing) {
                this.mVelocityTracker.addMovement(motionEvent);
                this.mVelocityTracker.computeCurrentVelocity(1);
                if (this.mPenEngine.mAlgorithm.isShapeRecognizeEnable() && this.mPenEngine.mAlgorithm.getShapeRecognizeParams().getTimeLot() > 0 && ((globalRulerOperator = this.mPenEngine.mGlobalRulerOperator) == null || !globalRulerOperator.isAdsorbent())) {
                    this.mHandler.post(new Runnable() { // from class: xc.engine.operate.touch.TouchPointEstimator$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m4661x340b2498(touchPoint);
                        }
                    });
                }
                this.mPenEngine.mAlgorithm.getRecognizeEngine().addMove(touchPoint.getX(), touchPoint.getY(), touchPoint.getTime(), motionEvent.getAction());
                doEstimated(touchPoint, touchPoint2);
                return;
            }
            return;
        }
        if (motionEvent.getAction() == 0) {
            if (this.isDrawing) {
                reset();
            }
            this.isDrawing = true;
            this.isShapeRecognizeSucceed = false;
            if (this.mPenEngine.mAlgorithm.isShapeRecognizeEnable() && this.mPenEngine.mAlgorithm.getShapeRecognizeParams().getTimeLot() > 0 && (this.mPenEngine.mGlobalRulerOperator == null || !this.mPenEngine.mGlobalRulerOperator.isAdsorbent())) {
                this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.touch.TouchPointEstimator$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m4662xceabe719(touchPoint);
                    }
                });
            }
            this.mCurEstimatedPoint = null;
            this.mCurTouchPoint = touchPoint;
            this.mPenEngine.mAlgorithm.getRecognizeEngine().clearContent();
            this.mPenEngine.mAlgorithm.getRecognizeEngine().addMove(touchPoint.getX(), touchPoint.getY(), touchPoint.getTime(), 0);
            if (this.mPenEngine.mAlgorithm.isEnableEstimate()) {
                this.mPenEngine.mAlgorithm.getEstimatedGenerator().onTouchDownEvent(touchPoint.getX(), touchPoint.getY(), touchPoint.getPressure(), touchPoint.getTilt(), touchPoint.getOrientation(), touchPoint.getTime());
            }
            this.mPenEngine.mTouchEventOperator.onDownEvent(touchPoint, this.isApplyAdsorbentMode, touchPoint2);
        }
    }

    /* JADX INFO: renamed from: lambda$handleTouchEvent$1$xc-engine-operate-touch-TouchPointEstimator, reason: not valid java name */
    /* synthetic */ void m4661x340b2498(TouchPoint touchPoint) {
        this.mRecognizeRecordingPoints.add(touchPoint);
        if (this.mHandler.hasMessages(2001)) {
            return;
        }
        Message messageObtain = Message.obtain(this.mHandler, this.mShapeRecognizeTask);
        messageObtain.what = 2001;
        this.mHandler.sendMessageDelayed(messageObtain, this.mPenEngine.mAlgorithm.getShapeRecognizeParams().getTimeLot());
    }

    /* JADX INFO: renamed from: lambda$handleTouchEvent$2$xc-engine-operate-touch-TouchPointEstimator, reason: not valid java name */
    /* synthetic */ void m4662xceabe719(TouchPoint touchPoint) {
        this.mHandler.removeMessages(2001);
        this.mRecognizeRecordingPoints.clear();
        this.mRecognizeStartPoint.set(touchPoint.getX(), touchPoint.getY());
        this.mRecognizeRecordingPoints.add(touchPoint);
        Message messageObtain = Message.obtain(this.mHandler, this.mShapeRecognizeTask);
        messageObtain.what = 2001;
        this.mHandler.sendMessageDelayed(messageObtain, this.mPenEngine.mAlgorithm.getShapeRecognizeParams().getTimeLot());
    }

    private void onUp(TouchPoint touchPoint) {
        GlobalRulerOperator globalRulerOperator;
        TouchPoint touchPoint2;
        if (this.hasEstimatedPoints && (touchPoint2 = this.mCurEstimatedPoint) != null) {
            touchPoint.setX(touchPoint2.getX());
            touchPoint.setY(this.mCurEstimatedPoint.getY());
        }
        this.mCurEstimatedPoint = null;
        touchPoint.setOrientation(this.mCurTouchPoint.getOrientation());
        touchPoint.setPressure(0.0f);
        this.mCurTouchPoint = touchPoint;
        this.mHandler.removeMessages(2001);
        this.mHandler.post(new Runnable() { // from class: xc.engine.operate.touch.TouchPointEstimator$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4663lambda$onUp$3$xcengineoperatetouchTouchPointEstimator();
            }
        });
        if (this.mPenEngine.mAlgorithm.isShapeRecognizeEnable() && this.isShapeRecognizeSucceed && (((globalRulerOperator = this.mPenEngine.mGlobalRulerOperator) == null || !globalRulerOperator.isAdsorbent()) && Math.abs(touchPoint.getX() - this.mRecognizeStartPoint.x) > this.mPenEngine.mAlgorithm.getShapeRecognizeParams().getDistanceOffset() * 3.0f && Math.abs(touchPoint.getY() - this.mRecognizeStartPoint.y) > this.mPenEngine.mAlgorithm.getShapeRecognizeParams().getDistanceOffset() * 3.0f)) {
            doShapeRecognize();
        }
        this.isShapeRecognizeSucceed = false;
        this.mPenEngine.mTouchEventOperator.onUpEvent(new TouchPoint[]{touchPoint});
        this.curPressure = 0.0f;
        this.mCurEstimatedPoint = null;
    }

    /* JADX INFO: renamed from: lambda$onUp$3$xc-engine-operate-touch-TouchPointEstimator, reason: not valid java name */
    /* synthetic */ void m4663lambda$onUp$3$xcengineoperatetouchTouchPointEstimator() {
        this.mRecognizeRecordingPoints.clear();
    }

    private float calculateWritingPressure(MotionEvent motionEvent) {
        float pressure = motionEvent.getPressure();
        if (motionEvent.getAction() == 0) {
            this.mPressureDiff = pressure;
        } else {
            float f = this.mPressureDiff;
            pressure = (pressure * 0.687f) + (0.313f * f);
            if (pressure - f > 0.19f) {
                pressure = f + 0.19f;
            } else if (f - pressure > 0.19f) {
                pressure = f - 0.19f;
            }
            if (Float.isNaN(pressure) || pressure < 0.07f) {
                pressure = 0.07f;
            } else if (Float.isInfinite(pressure)) {
                pressure = 1.0f;
            }
            this.mPressureDiff = pressure;
        }
        return motionEvent.getToolType(0) != 2 ? calculateNonStylusPressure(0.78f) : pressure;
    }

    public int getPostureDegree() {
        return PreferenceUtils.getV(CreationPageSDK.sApplication);
    }
}
