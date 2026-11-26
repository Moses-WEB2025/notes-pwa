package xc.engine.operate.edit.select.selectobject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import xc.engine.api.PenEngine;
import xc.engine.core.graphics.curve.CurveDataListManager;
import xc.engine.data.pagedata.BaseData;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.operate.canvas.ICanvasOperateListener;
import xc.engine.operate.canvas.draw.scale.ScaleInfo;
import xc.engine.operate.edit.select.SelectTouchPoints;
import xc.engine.operate.edit.select.selectInfo.AbsSelectInfo;
import xc.engine.utils.ScaleDataUtil;

/* JADX INFO: loaded from: classes6.dex */
public class BaseSelectObject implements ISelectObject {
    protected boolean isCanvasVisible;
    public BaseData mBaseData;
    public PointF mCenterPointF;
    public SelectTouchPoints mCopiedSelectTouchPoints;
    public CurveDataListManager mCurveDataListManager;
    public float mDegrees;
    public volatile boolean mEnableRefreshing;
    public boolean mFinishSelect;
    public int mHashCode;
    public volatile boolean mIsReady;
    public PointF mMovePointF;
    public boolean mNotDeleted;
    public SelectTouchPoints mOriginSelectTouchPoints;
    public final Paint mPaint;
    public PenEngine mPenEngine;
    public final ReentrantLock mReentrantLock;
    public float mScale;
    public int mSelectActionType;
    public AbsSelectInfo mSelectInfo;
    public RectF mSelectObjectRect;
    public boolean p;
    private final String TAG = "BaseSelectObject";
    public PointF e = new PointF();
    public boolean l = false;
    public boolean mIsStart = false;
    public float mTotalScale = 1.0f;
    public float mTotalDegress = 0.0f;
    public boolean isShape = false;

    public void changeVisibleSize(float f) {
    }

    @Override // xc.engine.operate.edit.select.selectobject.ISelectObject
    public boolean checkCurveSelectLimit(float f) {
        return false;
    }

    @Override // xc.engine.operate.edit.select.selectobject.ISelectObject
    public void doCopy() {
    }

    @Override // xc.engine.operate.edit.select.selectobject.ISelectObject
    public void doCopyAndPaste() {
    }

    @Override // xc.engine.operate.edit.select.selectobject.ISelectObject
    public void doDelete(boolean z) {
    }

    @Override // xc.engine.operate.edit.select.selectobject.ISelectObject
    public void doDeleteJustCurve() {
    }

    @Override // xc.engine.operate.edit.select.selectobject.ISelectObject
    public void forceRedraw() {
    }

    @Override // xc.engine.operate.edit.select.selectobject.ISelectObject
    public float getCurScaleValue() {
        return 0.0f;
    }

    @Override // xc.engine.operate.edit.select.selectobject.ISelectObject
    public short getDataType() {
        return (short) 1;
    }

    @Override // xc.engine.operate.edit.select.selectobject.ISelectObject
    public Point getSelectBitmapSize() {
        return null;
    }

    @Override // xc.engine.operate.edit.select.selectobject.ISelectObject
    public List<DataCurve> getSelectData() {
        return null;
    }

    @Override // xc.engine.operate.edit.select.selectobject.ISelectObject
    public void onDraw(Canvas canvas) {
    }

    @Override // xc.engine.operate.edit.select.selectobject.ISelectObject
    public boolean onScreenshots(Bitmap bitmap) {
        return true;
    }

    public void selectTouchComplete(SelectTouchPoints selectTouchPoints) {
    }

    public BaseSelectObject(BaseData baseData, PenEngine penEngine) {
        Paint paint = new Paint();
        this.mPaint = paint;
        this.mMovePointF = new PointF();
        this.mScale = 1.0f;
        this.mCenterPointF = new PointF();
        this.mDegrees = 0.0f;
        this.mSelectActionType = 0;
        this.p = false;
        this.mIsReady = false;
        this.isCanvasVisible = true;
        this.mReentrantLock = new ReentrantLock();
        this.mBaseData = baseData;
        this.mPenEngine = penEngine;
        this.mCurveDataListManager = penEngine.getCurveDataListManager();
        this.mSelectObjectRect = baseData.getSaveRect();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setFilterBitmap(true);
    }

    @Override // xc.engine.operate.edit.select.selectobject.ISelectObject
    public void startAction(int i) {
        this.mSelectActionType = i;
        this.mIsStart = true;
    }

    @Override // xc.engine.operate.edit.select.selectobject.ISelectObject
    public void doMove(final float f, final float f2) {
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.edit.select.selectobject.BaseSelectObject$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4640xeb84f77f(f, f2);
            }
        });
    }

    @Override // xc.engine.operate.edit.select.selectobject.ISelectObject
    public void doScale(final float f, final float f2, final float f3) {
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.edit.select.selectobject.BaseSelectObject$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4642xc0711b69(f, f2, f3);
            }
        });
    }

    @Override // xc.engine.operate.edit.select.selectobject.ISelectObject
    public void doRotate(final float f, final float f2, final float f3) {
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.edit.select.selectobject.BaseSelectObject$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4641x119fda8b(f, f2, f3);
            }
        });
    }

    @Override // xc.engine.operate.edit.select.selectobject.ISelectObject
    public void stopAction() {
        this.mIsStart = false;
        this.mIsReady = false;
    }

    @Override // xc.engine.operate.edit.select.selectobject.ISelectObject
    public void cancelAction() {
        this.mIsStart = false;
        this.mIsReady = false;
    }

    @Override // xc.engine.operate.edit.select.selectobject.ISelectObject
    public void finishSelect() {
        finishSelect(false);
    }

    @Override // xc.engine.operate.edit.select.selectobject.ISelectObject
    public void finishSelect(boolean z) {
        this.mIsReady = false;
        this.p = true;
        this.mTotalScale = 1.0f;
        this.mTotalDegress = 0.0f;
    }

    public void setSelectTouchPoint(SelectTouchPoints selectTouchPoints) {
        this.mOriginSelectTouchPoints = selectTouchPoints;
        SelectTouchPoints selectTouchPoints2 = new SelectTouchPoints();
        this.mCopiedSelectTouchPoints = selectTouchPoints2;
        selectTouchPoints2.copyTouchPoints(selectTouchPoints);
    }

    @Override // xc.engine.operate.edit.select.selectobject.ISelectObject
    public boolean isReady() {
        return this.mIsReady;
    }

    @Override // xc.engine.operate.edit.select.selectobject.ISelectObject
    public int getSelectedHashCode() {
        return this.mHashCode;
    }

    public void canvasDataChange() {
        ICanvasOperateListener iCanvasOperateListener = this.mPenEngine.mCanvasOperator.mCanvasOperateListener;
    }

    public RectF getRatioRectF() {
        RectF rectF = new RectF(this.mSelectObjectRect);
        float drawRatio = this.mPenEngine.mCanvasOperator.getDrawRatio();
        rectF.left *= drawRatio;
        rectF.top *= drawRatio;
        rectF.right *= drawRatio;
        rectF.bottom *= drawRatio;
        return rectF;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: handleDoMove, reason: merged with bridge method [inline-methods] */
    public void m4640xeb84f77f(float f, float f2) {
        ScaleInfo scaleInfo = this.mPenEngine.mCanvasOperator.getScaleInfo();
        this.mMovePointF.set(f / scaleInfo.getScaleRate(), f2 / scaleInfo.getScaleRate());
        AbsSelectInfo absSelectInfo = this.mSelectInfo;
        if (absSelectInfo != null) {
            absSelectInfo.doMove(f, f2);
        }
        canvasDataChange();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: handleDoScale, reason: merged with bridge method [inline-methods] */
    public void m4642xc0711b69(float f, float f2, float f3) {
        this.mScale = f3;
        PointF pointFScaleInverseOperation = ScaleDataUtil.scaleInverseOperation(f, f2, this.mPenEngine.mCanvasOperator.getScaleInfo());
        this.mCenterPointF.set(pointFScaleInverseOperation.x, pointFScaleInverseOperation.y);
        AbsSelectInfo absSelectInfo = this.mSelectInfo;
        if (absSelectInfo != null) {
            absSelectInfo.doScale(f, f2, this.mScale);
        }
        canvasDataChange();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: handleDoRotate, reason: merged with bridge method [inline-methods] */
    public void m4641x119fda8b(float f, float f2, float f3) {
        this.mDegrees = f3;
        PointF pointFScaleInverseOperation = ScaleDataUtil.scaleInverseOperation(f, f2, this.mPenEngine.mCanvasOperator.getScaleInfo());
        this.mCenterPointF.set(pointFScaleInverseOperation.x, pointFScaleInverseOperation.y);
        AbsSelectInfo absSelectInfo = this.mSelectInfo;
        if (absSelectInfo != null) {
            absSelectInfo.doRotate(f, f2, this.mDegrees);
        }
        canvasDataChange();
    }

    public PointF getRatioPointF(PointF pointF) {
        float saveRatio = this.mPenEngine.mCanvasOperator.getSaveRatio();
        PointF pointF2 = new PointF();
        pointF2.x = pointF.x * saveRatio;
        pointF2.y = pointF.y * saveRatio;
        return pointF2;
    }

    public RectF getRatioRectF(RectF rectF) {
        float saveRatio = this.mPenEngine.mCanvasOperator.getSaveRatio();
        RectF rectF2 = new RectF();
        rectF2.left = rectF.left * saveRatio;
        rectF2.top = rectF.top * saveRatio;
        rectF2.right = rectF.right * saveRatio;
        rectF2.bottom = rectF.bottom * saveRatio;
        return rectF2;
    }

    protected void deleteSelectOperate(RectF rectF, AbsSelectInfo absSelectInfo) {
        if (this.mPenEngine.mDrawerWrapper.isInitialized && this.isCanvasVisible) {
            this.mPenEngine.mDrawerWrapper.refreshForSelect(rectF);
            this.mEnableRefreshing = false;
            this.mNotDeleted = false;
        }
    }
}
