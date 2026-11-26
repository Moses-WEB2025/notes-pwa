package xc.engine.operate.touch;

import android.util.Log;
import android.view.MotionEvent;
import com.miui.creation.logger.EngLog;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import xc.engine.api.PenEngine;
import xc.engine.api.PenEngineHandlerWrapper;
import xc.engine.core.algorithm.estimate.EstimatedGenerator;
import xc.engine.core.algorithm.recognize.data.RecognizeData;
import xc.engine.core.graphics.pen.PenType;
import xc.engine.data.CurveProp;
import xc.engine.data.shape.ShapeProp;
import xc.engine.operate.canvas.draw.MultiDrawer;
import xc.engine.operate.canvas.track.IOperator;
import xc.engine.operate.canvas.track.curve.CurveOperator;
import xc.engine.operate.canvas.track.delete.DeleteLineOperator;
import xc.engine.operate.canvas.track.delete.DeletePointOperator;
import xc.engine.operate.canvas.track.delete.DeleteProp;
import xc.engine.operate.canvas.track.delete.DeleteType;

/* JADX INFO: loaded from: classes6.dex */
public class TouchEventOperator implements ITouchOperator {
    public boolean enableShapeEdit;
    public DeleteProp mDeleteProp;
    public final PenEngine mPenEngine;
    public final PenEngineHandlerWrapper mPenEngineHandlerWrapper;
    public ShapeProp mShapeProp;
    public final TouchPointEstimator mTouchPointEstimator;
    private final String TAG = "TouchEventOperator";
    public IOperator mOperator = null;
    public boolean useAlpha = true;
    public final int TIMEOUT = 30;
    public int mOperatorMode = 2;
    public CurveProp mCurveProp = new CurveProp(PenType.BALL, -16776961, 6.0f, 255);

    public TouchEventOperator(PenEngine penEngine) {
        this.mPenEngine = penEngine;
        this.mPenEngineHandlerWrapper = penEngine.getEngineHandler();
        this.mTouchPointEstimator = new TouchPointEstimator(penEngine);
    }

    @Override // xc.engine.operate.touch.ITouchOperator
    public boolean onHandleEvent(MotionEvent motionEvent) {
        if (this.mPenEngine.isRelease()) {
            return false;
        }
        return this.mTouchPointEstimator.onTouchEvent(motionEvent);
    }

    public boolean isScale() {
        return this.mPenEngine.mCanvasOperator.isScale;
    }

    protected void onDownEvent(final TouchPoint touchPoint, final boolean z, final TouchPoint touchPoint2) {
        if (this.mPenEngine.isRelease()) {
            return;
        }
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        this.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.touch.TouchEventOperator$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4654lambda$onDownEvent$0$xcengineoperatetouchTouchEventOperator(z, countDownLatch, touchPoint, touchPoint2);
            }
        });
        try {
            if (countDownLatch.await(30L, TimeUnit.MILLISECONDS)) {
                return;
            }
            Thread thread = this.mPenEngine.mPenEngineHandlerWrapper.getLooper().getThread();
            EngLog.fd(this.TAG, "onDownEvent >> blocked thread: " + thread.getName() + ", state: " + thread.getState());
            StackTraceElement[] stackTrace = thread.getStackTrace();
            if (stackTrace.length > 0) {
                EngLog.fd(this.TAG, "onDownEvent >> worker stack: " + Arrays.toString(stackTrace));
            }
            ReentrantLock reentrantLock = ((MultiDrawer) this.mPenEngine.mDrawerWrapper.mDrawer).mReentrantLock;
            if (reentrantLock != null) {
                EngLog.fd(this.TAG, "onDownEvent >>> lock info: " + reentrantLock + ", locked: " + reentrantLock.isLocked());
                if (reentrantLock.isLocked() && reentrantLock.isHeldByCurrentThread()) {
                    reentrantLock.unlock();
                }
            }
        } catch (InterruptedException unused) {
        }
    }

    /* JADX INFO: renamed from: lambda$onDownEvent$0$xc-engine-operate-touch-TouchEventOperator, reason: not valid java name */
    /* synthetic */ void m4654lambda$onDownEvent$0$xcengineoperatetouchTouchEventOperator(boolean z, CountDownLatch countDownLatch, TouchPoint touchPoint, TouchPoint touchPoint2) {
        int i = this.mOperatorMode;
        if (i == 2) {
            CurveOperator curveOperator = new CurveOperator(this.mPenEngine, this.mCurveProp, isScale());
            this.mOperator = curveOperator;
            curveOperator.applyAdsorbent(z);
            if (PenEngine.isEmptyCreationFromCreate()) {
                PenEngine.setIsEmptyCreationFromCreate(false);
            }
        } else if (i == 1) {
            DeleteType deleteType = this.mDeleteProp.getDeleteType();
            if (deleteType == DeleteType.LINE) {
                this.mOperator = new DeleteLineOperator(this.mDeleteProp, isScale(), this.mPenEngine, this.mCurveProp);
            } else if (deleteType == DeleteType.POINT) {
                this.mOperator = new DeletePointOperator(this.mPenEngine, this.mDeleteProp, isScale(), this.mCurveProp);
            }
        } else {
            this.mOperator = null;
        }
        IOperator iOperator = this.mOperator;
        if (iOperator == null) {
            countDownLatch.countDown();
        } else {
            iOperator.onDown(touchPoint, touchPoint2);
            countDownLatch.countDown();
        }
    }

    protected void onMoveEvent(final TouchPoint[] touchPointArr, final TouchPoint[] touchPointArr2, final boolean z, final TouchPoint touchPoint) {
        if (this.mPenEngine.isRelease()) {
            return;
        }
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        this.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.touch.TouchEventOperator$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4656lambda$onMoveEvent$1$xcengineoperatetouchTouchEventOperator(countDownLatch, z, touchPointArr, touchPointArr2, touchPoint);
            }
        });
        try {
            countDownLatch.await(this.mOperator instanceof DeletePointOperator ? 200 : 30, TimeUnit.MILLISECONDS);
        } catch (InterruptedException unused) {
        }
    }

    /* JADX INFO: renamed from: lambda$onMoveEvent$1$xc-engine-operate-touch-TouchEventOperator, reason: not valid java name */
    /* synthetic */ void m4656lambda$onMoveEvent$1$xcengineoperatetouchTouchEventOperator(CountDownLatch countDownLatch, boolean z, TouchPoint[] touchPointArr, TouchPoint[] touchPointArr2, TouchPoint touchPoint) {
        IOperator iOperator = this.mOperator;
        if (iOperator == null) {
            countDownLatch.countDown();
            return;
        }
        iOperator.applyAdsorbent(z);
        this.mOperator.onMove(touchPointArr, touchPointArr2, touchPoint);
        countDownLatch.countDown();
    }

    protected void hoverEnterEvent(final boolean z) {
        if (this.mPenEngine.isRelease()) {
            return;
        }
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        this.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.touch.TouchEventOperator$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4653xcb9ddc94(z, countDownLatch);
            }
        });
        try {
            if (countDownLatch.await(30L, TimeUnit.MILLISECONDS)) {
                return;
            }
            Thread thread = this.mPenEngine.mPenEngineHandlerWrapper.getLooper().getThread();
            EngLog.fd(this.TAG, "hoverEnterEvent >> blocked thread: " + thread.getName() + ", state: " + thread.getState());
            StackTraceElement[] stackTrace = thread.getStackTrace();
            if (stackTrace.length > 0) {
                EngLog.fd(this.TAG, "hoverEnterEvent >> worker stack: " + Arrays.toString(stackTrace));
            }
            ReentrantLock reentrantLock = ((MultiDrawer) this.mPenEngine.mDrawerWrapper.mDrawer).mReentrantLock;
            if (reentrantLock != null) {
                EngLog.fd(this.TAG, "hoverEnterEvent >>> lock info: " + reentrantLock + ", locked: " + reentrantLock.isLocked());
                if (reentrantLock.isLocked() && reentrantLock.isHeldByCurrentThread()) {
                    reentrantLock.unlock();
                }
            }
        } catch (ClassCastException | InterruptedException unused) {
        }
    }

    /* JADX INFO: renamed from: lambda$hoverEnterEvent$2$xc-engine-operate-touch-TouchEventOperator, reason: not valid java name */
    /* synthetic */ void m4653xcb9ddc94(boolean z, CountDownLatch countDownLatch) {
        int i = this.mOperatorMode;
        if (i == 2) {
            CurveOperator curveOperator = new CurveOperator(this.mPenEngine, this.mCurveProp, isScale());
            this.mOperator = curveOperator;
            curveOperator.applyAdsorbent(z);
            if (PenEngine.isEmptyCreationFromCreate()) {
                PenEngine.setIsEmptyCreationFromCreate(false);
            }
        } else if (i == 1) {
            DeleteType deleteType = this.mDeleteProp.getDeleteType();
            if (deleteType == DeleteType.LINE) {
                this.mOperator = new DeleteLineOperator(this.mDeleteProp, isScale(), this.mPenEngine, this.mCurveProp);
            } else if (deleteType == DeleteType.POINT) {
                this.mOperator = new DeletePointOperator(this.mPenEngine, this.mDeleteProp, isScale(), this.mCurveProp);
            }
        } else {
            this.mOperator = null;
        }
        if (this.mOperator == null) {
            countDownLatch.countDown();
        } else {
            countDownLatch.countDown();
        }
    }

    protected void hoverMoveEvent(HoverPoint hoverPoint, boolean z, boolean z2, float f, int i) {
        if (this.mPenEngine.isRelease()) {
            return;
        }
        CurveOperator curveOperator = new CurveOperator(this.mPenEngine, this.mCurveProp, isScale());
        this.mOperator = curveOperator;
        curveOperator.applyAdsorbent(z);
        this.mOperator.hoverMove(hoverPoint, z2, f, i);
    }

    protected void hoverExitEvent() {
        this.mOperator.hoverExit();
    }

    public boolean hoverEvent(MotionEvent motionEvent, boolean z, float f) {
        return this.mTouchPointEstimator.onHoverEvent(motionEvent, z, f);
    }

    protected void onUpEvent(final TouchPoint[] touchPointArr) {
        if (this.mPenEngine.isRelease()) {
            return;
        }
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        this.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.touch.TouchEventOperator$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4657lambda$onUpEvent$3$xcengineoperatetouchTouchEventOperator(countDownLatch, touchPointArr);
            }
        });
        try {
            countDownLatch.await(30L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException unused) {
        }
    }

    /* JADX INFO: renamed from: lambda$onUpEvent$3$xc-engine-operate-touch-TouchEventOperator, reason: not valid java name */
    /* synthetic */ void m4657lambda$onUpEvent$3$xcengineoperatetouchTouchEventOperator(CountDownLatch countDownLatch, TouchPoint[] touchPointArr) {
        IOperator iOperator = this.mOperator;
        if (iOperator == null) {
            countDownLatch.countDown();
        } else {
            iOperator.onUp(touchPointArr);
            countDownLatch.countDown();
        }
    }

    protected void onInnerCancelEvent() {
        if (this.mPenEngine.isRelease()) {
            return;
        }
        this.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.touch.TouchEventOperator$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4655xaaf7898d();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$onInnerCancelEvent$4$xc-engine-operate-touch-TouchEventOperator, reason: not valid java name */
    /* synthetic */ void m4655xaaf7898d() {
        IOperator iOperator = this.mOperator;
        if (iOperator == null) {
            return;
        }
        iOperator.onCancelII();
        this.mOperator = null;
    }

    protected void setShape(final RecognizeData recognizeData) {
        if (this.mPenEngine.isRelease()) {
            return;
        }
        this.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.touch.TouchEventOperator$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4659lambda$setShape$5$xcengineoperatetouchTouchEventOperator(recognizeData);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setShape$5$xc-engine-operate-touch-TouchEventOperator, reason: not valid java name */
    /* synthetic */ void m4659lambda$setShape$5$xcengineoperatetouchTouchEventOperator(RecognizeData recognizeData) {
        IOperator iOperator = this.mOperator;
        if (iOperator == null || !(iOperator instanceof CurveOperator)) {
            return;
        }
        ((CurveOperator) iOperator).setRecognizeShapeData(recognizeData);
    }

    @Override // xc.engine.operate.touch.ITouchOperator
    public void onCancelEvent() {
        if (this.mPenEngine.isRelease()) {
            return;
        }
        TouchPointEstimator touchPointEstimator = this.mTouchPointEstimator;
        if (touchPointEstimator.isDrawing) {
            touchPointEstimator.isDrawing = false;
            touchPointEstimator.reset();
        }
        IOperator iOperator = this.mOperator;
        if (iOperator != null) {
            iOperator.cancel();
        }
        EstimatedGenerator estimatedGenerator = this.mPenEngine.mAlgorithm.getEstimatedGenerator();
        if (estimatedGenerator == null || estimatedGenerator.getEstimatedPoints() == null || estimatedGenerator.getEstimatedPoints().length < 1) {
            return;
        }
        estimatedGenerator.getTouchPoint().clear();
    }

    @Override // xc.engine.operate.touch.ITouchOperator
    public void setOperatorMode(final int i) {
        if (this.mPenEngine.isRelease()) {
            return;
        }
        this.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.touch.TouchEventOperator$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4658x72f8df05(i);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setOperatorMode$6$xc-engine-operate-touch-TouchEventOperator, reason: not valid java name */
    /* synthetic */ void m4658x72f8df05(int i) {
        this.mOperatorMode = i;
    }

    @Override // xc.engine.operate.touch.ITouchOperator
    public int getOperatorMode() {
        return this.mOperatorMode;
    }

    @Override // xc.engine.operate.touch.ITouchOperator
    public void setPenProp(CurveProp curveProp) {
        if (this.mPenEngine.isRelease()) {
            return;
        }
        if (curveProp.getTipSize() > curveProp.getPenSize()) {
            curveProp.setTipSize(curveProp.getPenSize());
        }
        curveProp.setPenAlpha(this.useAlpha ? curveProp.getPenAlpha() : 255);
        Log.w(this.TAG, "xqw- setPenProp: " + curveProp);
        this.mCurveProp = curveProp;
    }

    @Override // xc.engine.operate.touch.ITouchOperator
    public void setDeleteProp(DeleteProp deleteProp) {
        if (this.mPenEngine.isRelease()) {
            return;
        }
        this.mDeleteProp = deleteProp;
    }
}
