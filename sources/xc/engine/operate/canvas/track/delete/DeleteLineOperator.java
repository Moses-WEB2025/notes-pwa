package xc.engine.operate.canvas.track.delete;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import xc.engine.api.PenEngine;
import xc.engine.core.fast.GLFrontBufferedWrapperRenderer;
import xc.engine.core.fast.data.CurveSegment;
import xc.engine.core.graphics.curve.CurveDataManager;
import xc.engine.core.graphics.curve.info.CurveInfo;
import xc.engine.data.CsPoint;
import xc.engine.data.CurveProp;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.math.iink.manager.EraserOperationManager;
import xc.engine.operate.canvas.CanvasOperator;
import xc.engine.operate.canvas.draw.scale.ScaleInfo;
import xc.engine.operate.canvas.track.IOperator;
import xc.engine.operate.step.type.StepDeleteLine;
import xc.engine.operate.touch.HoverPoint;
import xc.engine.operate.touch.TouchPoint;
import xc.engine.utils.FeatureManager;
import xc.engine.utils.PenEngineUtils;

/* JADX INFO: loaded from: classes6.dex */
public class DeleteLineOperator extends CurveInfo implements IOperator {
    private static final int MAX_PEN_SIZE = 145;
    private static final String TAG = "DeleteLineOperator";
    private long lastDeleteTime;
    private CanvasOperator mCanvasOperator;
    private CurveProp mCurveProp;
    private DataCurve mDataCurve;
    private DeleteProp mDeleteProp;
    private long mEraseTimeThreshold;
    private GLFrontBufferedWrapperRenderer mGlFastRenderer;
    private final PenEngine mPenEngine;
    private final PointF mStartTouchPoint = new PointF();
    private final Region mTouchPointDelAreaBound = new Region();
    private final Path mTouchPointDelAreaPath = new Path();
    private final List<DataCurve> mTempDeleteDataCurveList = new ArrayList();
    private final Region mTouchPointDelAreaIntersect = new Region();
    private final RectF tempDelBound = new RectF();
    private RectF tileRendererUpdateRect = new RectF();
    private final List<DataCurve> mDeleteDataCurveList = new ArrayList();
    private final EraserOperationManager eraserOperationManager = EraserOperationManager.getInstance();

    @Override // xc.engine.operate.canvas.track.IOperator
    public void applyAdsorbent(boolean z) {
    }

    @Override // xc.engine.operate.canvas.track.IOperator
    public void hoverExit() {
    }

    @Override // xc.engine.operate.canvas.track.IOperator
    public void onDraw(Canvas canvas) {
    }

    public DeleteLineOperator(DeleteProp deleteProp, boolean z, PenEngine penEngine, CurveProp curveProp) {
        this.mEraseTimeThreshold = 0L;
        this.mPenEngine = penEngine;
        this.mDeleteProp = deleteProp;
        if (PenEngineUtils.isSmallPad()) {
            this.mEraseTimeThreshold = 100L;
        }
        enableScale(z);
        init(curveProp);
    }

    private void init(CurveProp curveProp) {
        this.mCurveProp = curveProp;
        this.mCanvasOperator = this.mPenEngine.getCanvasOperator();
        this.mDataCurve = new DataCurve(this.mCurveProp, System.currentTimeMillis());
        ScaleInfo scaleInfo = this.mCanvasOperator.getScaleInfo();
        this.mDataCurve.setValue(scaleInfo.getScaleRate(), scaleInfo.getTranslate(), this.mCanvasOperator.getDrawRatio());
        if (FeatureManager.getInstance().frontBufferEnable()) {
            this.mGlFastRenderer = this.mPenEngine.mFrontBufferedRenderProcessor.getMFrontBufferRenderer();
        }
    }

    @Override // xc.engine.operate.canvas.track.IOperator
    public void onCancelII() {
        if (this.mTempDeleteDataCurveList.size() > 0) {
            this.mDeleteDataCurveList.addAll(this.mTempDeleteDataCurveList);
            this.mTempDeleteDataCurveList.clear();
        }
        if (this.mDeleteDataCurveList.size() > 0) {
            f();
            this.mDeleteDataCurveList.clear();
        }
        this.eraserOperationManager.forceEndAllEraserOperations();
    }

    @Override // xc.engine.operate.canvas.track.IOperator
    public void setEraseTime(long j) {
        this.mEraseTimeThreshold = j;
    }

    private void processLineErasing(boolean z, TouchPoint touchPoint) {
        DeleteUtils.calDeleteRegion(this.tempDelBound, this.mStartTouchPoint, touchPoint, this.mDeleteProp.getDeleteRadius(), this.mTouchPointDelAreaIntersect, true, this.mPenEngine.mCanvasOperator.getDrawRatio() * 0.5f);
        this.mStartTouchPoint.set(touchPoint.getX(), touchPoint.getY());
        deleteIntersectionData(touchPoint.getX(), touchPoint.getY(), this.tempDelBound);
        if (!z || this.mTempDeleteDataCurveList.isEmpty()) {
            this.mPenEngine.mCanvasOperator.mCanvasOperateListener.onCanvasDataChanged(null, -1);
            return;
        }
        this.mDeleteDataCurveList.addAll(this.mTempDeleteDataCurveList);
        e();
        this.mTempDeleteDataCurveList.clear();
    }

    private void e() {
        this.tileRendererUpdateRect = this.mPenEngine.mCurveDataManager.removeDataAndGetRectF(this.mTempDeleteDataCurveList);
    }

    private void f() {
        DataCurve dataCurve = this.mDeleteDataCurveList.get(0);
        dataCurve.mDataState = 1;
        RectF saveRect = dataCurve.getSaveRect();
        dataCurve.translateRectByPenPoint(saveRect, false);
        for (int i = 1; i < this.mDeleteDataCurveList.size(); i++) {
            DataCurve dataCurve2 = this.mDeleteDataCurveList.get(i);
            dataCurve2.mDataState = 1;
            RectF saveRect2 = dataCurve2.getSaveRect();
            dataCurve2.translateRectByPenPoint(saveRect2, false);
            saveRect.union(saveRect2);
        }
        this.mPenEngine.mCurveDataManager.addDataAndGetRectF(this.mDeleteDataCurveList);
        float drawRatio = this.mPenEngine.mCanvasOperator.getDrawRatio();
        this.mPenEngine.mDrawerWrapper.refreshCurveData(new RectF(saveRect.left * drawRatio, saveRect.top * drawRatio, saveRect.right * drawRatio, saveRect.bottom * drawRatio));
    }

    private void deleteIntersectionData(float f, float f2, RectF rectF) {
        float saveRatio = this.mPenEngine.mCanvasOperator.getSaveRatio();
        for (DataCurve dataCurve : ((CurveDataManager) this.mPenEngine.mCurveDataManager).scanIntersectDataCurve(new RectF(rectF.left * saveRatio, rectF.top * saveRatio, rectF.right * saveRatio, rectF.bottom * saveRatio), true)) {
            if (dataCurve.mDataState != 3 && dataCurve.mTableFlag < 0 && dataCurve.hasIntersectionWithRegionLineDelete(this.mTouchPointDelAreaIntersect, f, f2)) {
                dataCurve.mDataState = 3;
                this.mTempDeleteDataCurveList.add(dataCurve);
            }
        }
    }

    @Override // xc.engine.operate.canvas.track.IOperator
    public void onDown(TouchPoint touchPoint, TouchPoint touchPoint2) {
        this.mStartTouchPoint.set(touchPoint.getX(), touchPoint.getY());
        this.lastDeleteTime = touchPoint.getTime();
        pointEraserRender(touchPoint2);
        this.eraserOperationManager.startLineEraserOperation();
    }

    @Override // xc.engine.operate.canvas.track.IOperator
    public void onMove(TouchPoint[] touchPointArr, TouchPoint[] touchPointArr2, TouchPoint touchPoint) {
        boolean z;
        if (touchPointArr.length == 0) {
            return;
        }
        TouchPoint touchPoint2 = touchPointArr[touchPointArr.length - 1];
        long time = touchPoint2.getTime();
        if (time - this.lastDeleteTime > this.mEraseTimeThreshold) {
            this.lastDeleteTime = time;
            z = true;
        } else {
            z = false;
        }
        pointEraserRender(touchPoint);
        processLineErasing(z, touchPoint2);
        if (z) {
            this.mPenEngine.getCurveEngine().getCurveSystem().updateArea(new RectF(this.tileRendererUpdateRect.left - 145.0f, this.tileRendererUpdateRect.top - 145.0f, this.tileRendererUpdateRect.right + 145.0f, this.tileRendererUpdateRect.bottom + 145.0f), true, true, true);
        }
    }

    @Override // xc.engine.operate.canvas.track.IOperator
    public void onUp(TouchPoint[] touchPointArr) {
        processLineErasing(true, touchPointArr[touchPointArr.length - 1]);
        if (this.mDeleteDataCurveList.size() > 0) {
            this.mPenEngine.mStepManager.addStep(new StepDeleteLine(this.mPenEngine, this.mDeleteDataCurveList));
            this.mDeleteDataCurveList.clear();
            this.mPenEngine.getCurveEngine().getCurveSystem().updateArea(this.tileRendererUpdateRect, true, true, true);
        }
        this.eraserOperationManager.endLineEraserOperation();
        try {
            if (this.mPenEngine.getCurveEngine().getMathDataManager().getHasUserStartedDrawing()) {
                return;
            }
            this.mPenEngine.getCurveEngine().getMathDataManager().setUserStartedDrawing(true);
            Log.d(TAG, "Enable auto-selection after line eraser operation");
        } catch (Exception unused) {
        }
    }

    @Override // xc.engine.operate.canvas.track.IOperator
    public void cancel() {
        onCancelII();
    }

    @Override // xc.engine.operate.canvas.track.IOperator
    public void hoverMove(HoverPoint hoverPoint, boolean z, float f, int i) {
        postFastRendering(new CsPoint(hoverPoint.getX(), hoverPoint.getY(), hoverPoint.getPressure()), z, Float.valueOf(f));
    }

    private void postFastRendering(CsPoint csPoint, boolean z, Float f) {
        if (FeatureManager.getInstance().frontBufferEnable()) {
            this.mGlFastRenderer.renderOnFrontBuffer(CurveSegment.INSTANCE.constructForEraserHover(csPoint));
        }
    }

    public void pointEraserRender(TouchPoint touchPoint) {
        postFastRendering(new CsPoint(touchPoint.getX(), touchPoint.getY(), touchPoint.getPressure()));
    }

    private void postFastRendering(CsPoint csPoint) {
        if (FeatureManager.getInstance().frontBufferEnable()) {
            this.mGlFastRenderer.renderOnFrontBuffer(CurveSegment.INSTANCE.constructForEraserTouch(csPoint));
        }
    }
}
