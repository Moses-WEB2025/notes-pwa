package xc.engine.operate.canvas;

import android.graphics.Bitmap;
import android.graphics.Path;
import android.util.Log;
import android.view.Surface;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import xc.engine.api.PenEngine;
import xc.engine.api.PenEngineHandlerWrapper;
import xc.engine.core.graphics.curve.CurveDataManager;
import xc.engine.core.graphics.curve.CurveFittingProcessor;
import xc.engine.core.graphics.pen.PenType;
import xc.engine.data.CurveProp;
import xc.engine.data.IPenPropTransform;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.DataShape;
import xc.engine.data.shape.ShapeProp;
import xc.engine.operate.canvas.draw.DrawerWrapper;
import xc.engine.operate.canvas.draw.IMultiDrawer;
import xc.engine.operate.canvas.draw.MultiDrawer;
import xc.engine.operate.canvas.draw.scale.ScaleInfo;
import xc.engine.operate.canvas.track.shape.ShapeCurveInfo;
import xc.engine.operate.edit.EditOperator;
import xc.engine.utils.PenEngineUtils;
import xc.engine.utils.SizeConfig;
import xc.richtext.data.bean.BitmapBean;

/* JADX INFO: loaded from: classes6.dex */
public class CanvasOperator implements ICanvasOperator {
    public boolean isApplyingInfiniteCanvas;
    public boolean isScale;
    public IBitmapChangeListener mBitmapChangeListener;
    private List<BitmapBean> mBitmapList;
    public float mCanvasDrawWidth;
    public ICanvasOperateListener mCanvasOperateListener;
    public final PenEngine mPenEngine;
    private final PenEngineHandlerWrapper mPenEngineHandlerWrapper;
    public IPenPropTransform mPenPropTransform;
    public float mPreviousFixedWidth;
    public float mVisibleFixedHeight;
    public float mVisibleFixedWidth;
    private final float s;
    private final String TAG = "CanvasOperator";
    private final boolean isColorWheelMode = false;
    public final ScaleInfo mScaleInfo = new ScaleInfo();
    public boolean mCanSelectInvisibleData = true;
    private final Runnable mChangeVisibleSizeTask = new ChangeVisibleSizeTask();
    public CanvasSizeInfo mCanvasSizeInfo = new CanvasSizeInfo();
    boolean hasRemain = false;
    private Bitmap mBitmap = null;
    public float mVisibleWidth = SizeConfig.DEFAULT_VISIBLE_SIZE.getWidth();
    private float mVisibleHeight = SizeConfig.DEFAULT_VISIBLE_SIZE.getHeight();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Semaphore semaphore = new Semaphore(1);

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public void forceRedraw() {
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public void setSurface(Surface surface) {
    }

    public class ChangeVisibleSizeTask implements Runnable {
        public ChangeVisibleSizeTask() {
        }

        @Override // java.lang.Runnable
        public void run() {
            float f = CanvasOperator.this.mVisibleWidth;
            float f2 = CanvasOperator.this.mVisibleHeight;
            CanvasOperator.this.mCanvasSizeInfo.setVisibleWidth((float) Math.ceil(f));
            CanvasOperator.this.mCanvasSizeInfo.setVisibleHeight((float) Math.ceil(f2));
            CanvasOperator.this.mPenEngine.mDrawerWrapper.onCanvasVisibleSizeChanged(f, f2);
            EditOperator editOperator = CanvasOperator.this.mPenEngine.mEditOperator;
            if (editOperator.mSelectObject != null) {
                editOperator.mSelectObject.changeVisibleSize(CanvasOperator.this.mVisibleWidth);
            }
        }
    }

    public CanvasOperator(PenEngine penEngine) {
        this.mPenEngine = penEngine;
        this.mPenEngineHandlerWrapper = penEngine.getEngineHandler();
        this.s = Math.min(penEngine.getScreenInfo().mWidthPixels, penEngine.getScreenInfo().mHeightPixels) * 6.0f;
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public void initDrawer() {
        if (this.mPenEngine.isRelease()) {
            return;
        }
        PenEngineHandlerWrapper penEngineHandlerWrapper = this.mPenEngine.mPenEngineHandlerWrapper;
        final DrawerWrapper drawerWrapper = this.mPenEngine.mDrawerWrapper;
        Objects.requireNonNull(drawerWrapper);
        penEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.canvas.CanvasOperator$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                drawerWrapper.initDrawer();
            }
        });
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public void initPreviewBitmap(CurveProp curveProp, Bitmap bitmap) {
        DataCurve dataCurve;
        if (this.mPenEngine.isRelease()) {
            return;
        }
        if (curveProp instanceof ShapeProp) {
            DataShape dataShape = new DataShape((ShapeProp) curveProp, 0L);
            float drawRatio = getDrawRatio();
            dataShape.mDrawRatio = getDrawRatio();
            float width = (bitmap.getWidth() / drawRatio) / 4.0f;
            float height = (bitmap.getHeight() / drawRatio) / 4.0f;
            new ShapeCurveInfo(this.mPenEngine, dataShape, false, true).a(new float[]{width, height, width * 3.0f, 3.0f * height});
            dataShape.calculateBoundingRect();
            dataCurve = dataShape;
        } else {
            if (curveProp.getTipSize() > curveProp.getPenSize()) {
                curveProp.setTipSize(curveProp.getPenSize());
            }
            curveProp.setPenAlpha(this.mPenEngine.mTouchEventOperator.useAlpha ? curveProp.getPenAlpha() : 255);
            DataCurve dataCurve2 = new DataCurve(curveProp, System.currentTimeMillis());
            float drawRatio2 = getDrawRatio();
            dataCurve2.mDrawRatio = getDrawRatio();
            if (dataCurve2.getPenType() == PenType.MARK || dataCurve2.getPenType() == PenType.WATER_COLOR) {
                drawRatio2 *= 0.6301f;
                dataCurve2.mDrawRatio = drawRatio2;
            }
            fillPreviewCurvePoints(dataCurve2, bitmap.getWidth() / drawRatio2, bitmap.getHeight() / drawRatio2);
            dataCurve2.calculateBoundingRect();
            new CurveFittingProcessor(this.mPenEngine, dataCurve2, new Path(), false, true).fillCurvePoints();
            dataCurve = dataCurve2;
        }
        this.mPenEngine.getCurveEngine().getPreviewSystem().drawBrushPreviewImage(dataCurve, bitmap);
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public void generateBrushPreviewBitmap(CurveProp curveProp, Bitmap bitmap) {
        if (curveProp.getTipSize() > curveProp.getPenSize()) {
            curveProp.setTipSize(curveProp.getPenSize());
        }
        curveProp.setPenAlpha(this.mPenEngine.mTouchEventOperator.useAlpha ? curveProp.getPenAlpha() : 255);
        DataCurve dataCurve = new DataCurve(curveProp, System.currentTimeMillis());
        dataCurve.mDrawRatio = 1.0f;
        fillQuickBrushPreviewCurvePoints(dataCurve, bitmap.getWidth(), bitmap.getHeight());
        dataCurve.calculateBoundingRect();
        new CurveFittingProcessor(this.mPenEngine, dataCurve, new Path(), false, true).fillCurvePoints();
        this.mPenEngine.getCurveEngine().getPreviewSystem().drawBrushPreviewImage(dataCurve, bitmap);
        dataCurve.releasePointList();
    }

    private float valueMapping(float f, float f2, float f3, float f4, float f5) {
        float fMax = (Math.max(f2, Math.min(f, f3)) - f2) / (f3 - f2);
        return f4 < f5 ? f4 + (fMax * (f5 - f4)) : f4 - (fMax * Math.abs(f5 - f4));
    }

    private void fillQuickBrushPreviewCurvePoints(DataCurve dataCurve, float f, float f2) {
        DataCurve dataCurve2;
        float f3;
        DataCurve dataCurve3 = dataCurve;
        float f4 = f2 / 2.0f;
        if (dataCurve3.getPenType() == PenType.BALL) {
            float fValueMapping = valueMapping(dataCurve3.getCurvePenWidth(), 1.0f, 22.0f, 0.05f, 0.34f);
            float fValueMapping2 = valueMapping(dataCurve3.getCurvePenWidth(), 1.0f, 22.0f, 1.4f, 0.973f);
            Log.d(this.TAG, "fillQuickBrushPreviewCurvePoints:  endRatio: " + fValueMapping2);
            dataCurve.insertPoint(dataCurve3.mSavePoints, fValueMapping * f, f4, 1.0f, 0.0f, 1.5707964f, 1.0f, 0L, (short) 1, 0.0f);
            dataCurve.insertPoint(dataCurve.mSavePoints, f * 0.4f, f4, 1.0f, 0.0f, 1.5707964f, 1.0f, 0L, (short) 1, 0.0f);
            dataCurve.insertPoint(dataCurve.mSavePoints, f * 0.5f, f4, 1.0f, 0.0f, 1.5707964f, 1.0f, 0L, (short) 1, 0.0f);
            dataCurve.insertPoint(dataCurve.mSavePoints, f * fValueMapping2, f4, 1.0f, 0.0f, 1.5707964f, 1.0f, 0L, (short) 1, 0.0f);
            dataCurve3 = dataCurve;
            f4 = f4;
        }
        if (dataCurve3.getPenType() == PenType.INK) {
            dataCurve3.getPenType();
            PenType penType = PenType.INK;
            dataCurve3.getPenType();
            PenType penType2 = PenType.INK;
            float fValueMapping3 = valueMapping(dataCurve3.getCurvePenWidth(), 1.0f, 30.0f, 0.05f, 0.3f);
            float fValueMapping4 = valueMapping(dataCurve3.getCurvePenWidth(), 1.0f, 30.0f, 1.4f, 0.96f);
            Log.d(this.TAG, "fillQuickBrushPreviewCurvePoints:  endRatio: " + fValueMapping4);
            float f5 = f4;
            dataCurve.insertPoint(dataCurve3.mSavePoints, fValueMapping3 * f, f5, 1.0f, 0.0f, 1.5707964f, 1.0f, 0L, (short) 1, 0.0f);
            dataCurve.insertPoint(dataCurve.mSavePoints, f * 0.4f, f5, 1.0f, 0.0f, 1.5707964f, 1.0f, 0L, (short) 1, 0.0f);
            dataCurve.insertPoint(dataCurve.mSavePoints, f * 0.5f, f5, 1.0f, 0.0f, 1.5707964f, 1.0f, 0L, (short) 1, 0.0f);
            dataCurve.insertPoint(dataCurve.mSavePoints, f * fValueMapping4, f5, 1.0f, 0.0f, 1.5707964f, 1.0f, 0L, (short) 1, 0.0f);
            dataCurve3 = dataCurve;
            f4 = f5;
        }
        if (dataCurve3.getPenType() == PenType.WATER_COLOR) {
            float fValueMapping5 = valueMapping(dataCurve3.getCurvePenWidth(), 5.0f, 65.0f, 0.05f, 0.9f);
            float fValueMapping6 = valueMapping(dataCurve3.getCurvePenWidth(), 5.0f, 65.0f, 1.3f, 0.35f);
            Log.d(this.TAG, "fillQuickBrushPreviewCurvePoints:  endRatio: " + fValueMapping6);
            f3 = f4;
            dataCurve2 = dataCurve;
            dataCurve2.insertPoint(dataCurve3.mSavePoints, f * fValueMapping5, f3, 1.0f, 0.0f, 1.5707964f, 1.0f, 0L, (short) 1, 0.0f);
            dataCurve2.insertPoint(dataCurve2.mSavePoints, f * 0.4f, f3, 1.0f, 0.0f, 1.5707964f, 1.0f, 0L, (short) 1, 0.0f);
            dataCurve2.insertPoint(dataCurve2.mSavePoints, f * 0.5f, f3, 1.0f, 0.0f, 1.5707964f, 1.0f, 0L, (short) 1, 0.0f);
            dataCurve2.insertPoint(dataCurve2.mSavePoints, f * fValueMapping6, f3, 1.0f, 0.0f, 1.5707964f, 1.0f, 0L, (short) 1, 0.0f);
        } else {
            dataCurve2 = dataCurve3;
            f3 = f4;
        }
        if (dataCurve2.getPenType() == PenType.MARK || dataCurve2.getPenType() == PenType.PENCIL) {
            float f6 = dataCurve2.getPenType() == PenType.MARK ? 0.6666f : 0.0f;
            dataCurve2.insertPoint(dataCurve2.mSavePoints, 0.08f * f, f3, 1.0f, f6, 1.5707964f, 1.0f, 0L, (short) 1, 0.0f);
            dataCurve2.insertPoint(dataCurve2.mSavePoints, f * 0.4f, f3, 1.0f, f6, 1.5707964f, 1.0f, 0L, (short) 1, 0.0f);
            dataCurve2.insertPoint(dataCurve2.mSavePoints, f * 0.5f, f3, 1.0f, f6, 1.5707964f, 1.0f, 0L, (short) 1, 0.0f);
            dataCurve2.insertPoint(dataCurve2.mSavePoints, f, f3, 1.0f, f6, 1.5707964f, 1.0f, 0L, (short) 1, 0.0f);
            dataCurve2.insertPoint(dataCurve2.mSavePoints, 0.9f * f, f3, 1.0f, f6, 1.5707964f, 1.0f, 0L, (short) 1, 0.0f);
        }
    }

    private void fillPreviewCurvePoints(DataCurve dataCurve, float f, float f2) {
        float f3 = f2 / 2.0f;
        float f4 = dataCurve.getPenType() == PenType.MARK ? 0.6666f : 0.0f;
        dataCurve.insertPoint(dataCurve.mSavePoints, 0.12f * f, f3, 0.12f, f4, 1.5707964f, 1.0f, 0L, (short) 1, 0.0f);
        dataCurve.insertPoint(dataCurve.mSavePoints, 0.3f * f, 0.6f * f3, 0.5f, f4, 1.5707964f, 1.0f, 0L, (short) 1, 0.0f);
        dataCurve.insertPoint(dataCurve.mSavePoints, 0.5f * f, f3, 1.0f, f4, 1.5707964f, 1.0f, 0L, (short) 1, 0.0f);
        dataCurve.insertPoint(dataCurve.mSavePoints, 0.7f * f, 1.4f * f3, 0.8f, f4, 1.5707964f, 1.0f, 0L, (short) 1, 0.0f);
        dataCurve.insertPoint(dataCurve.mSavePoints, 0.9f * f, f3, 0.5f, f4, 1.5707964f, 1.0f, 0L, (short) 1, 0.0f);
        dataCurve.insertPoint(dataCurve.mSavePoints, 0.94f * f, f3, 0.03f, f4, 1.5707964f, 1.0f, 0L, (short) 1, 0.0f);
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public void setVisibleSize(float f, float f2) {
        if (this.mPenEngine.isRelease()) {
            return;
        }
        this.mVisibleWidth = f;
        this.mVisibleHeight = f2;
        if (this.mPenEngineHandlerWrapper.hasMessages(this.mChangeVisibleSizeTask)) {
            this.mPenEngineHandlerWrapper.mHandler.removeCallbacks(this.mChangeVisibleSizeTask);
        }
        this.mPenEngineHandlerWrapper.sendMessage(this.mChangeVisibleSizeTask);
        ICanvasOperateListener iCanvasOperateListener = this.mCanvasOperateListener;
        if (iCanvasOperateListener != null) {
            iCanvasOperateListener.onCanvasDataChanged(null, 11);
        }
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public void setCanvasSize(final float f, final float f2) {
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.canvas.CanvasOperator$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4626lambda$setCanvasSize$0$xcengineoperatecanvasCanvasOperator(f, f2);
            }
        });
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public void renderingUpdate() {
        this.mPenEngine.getCurveEngine().refreshFrame();
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public void setMaxPage(int i) {
        if (this.mPenEngine.isRelease()) {
            return;
        }
        CurveDataManager curveDataManager = (CurveDataManager) this.mPenEngine.mCurveDataManager;
        curveDataManager.mMaxPage = i;
        curveDataManager.init();
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public void setCanSelectInvisibleData(boolean z) {
        this.mCanSelectInvisibleData = z;
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public float getVisibleWidth() {
        if (this.mPenEngine.isRelease()) {
            return 0.0f;
        }
        return this.mVisibleWidth;
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public float getVisibleHeight() {
        if (this.mPenEngine.isRelease()) {
            return 0.0f;
        }
        return this.mVisibleHeight;
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public float getDrawRatio() {
        if (!this.mPenEngine.isRelease() && this.mPenEngine.mDrawerWrapper.isInitialized) {
            return this.mPenEngine.mDrawerWrapper.mDrawer.getDrawRatio();
        }
        return 1.0f;
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public float getSaveRatio() {
        if (!this.mPenEngine.isRelease() && this.mPenEngine.mDrawerWrapper.isInitialized) {
            return this.mPenEngine.mDrawerWrapper.mDrawer.getSaveRatio();
        }
        return 1.0f;
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public void doScale(ScaleInfo scaleInfo) {
        if (this.mPenEngine.isRelease()) {
            return;
        }
        ScaleInfo scaleInfo2 = new ScaleInfo(scaleInfo.getScaleRate(), PenEngineUtils.getCopyPointF(scaleInfo.getTranslate()), PenEngineUtils.getCopyPointF(scaleInfo.getScaleCenter()));
        float scaleRate = getScaleInfo().getScaleRate();
        if (scaleInfo2.getScaleRate() * scaleRate > b()) {
            scaleInfo2.setScaleRate(b() / scaleRate);
        } else if (scaleInfo2.getScaleRate() * scaleRate < 0.3f) {
            scaleInfo2.setScaleRate(0.3f / scaleRate);
        }
        scaleInfo.setScaleRate(scaleInfo2.getScaleRate());
        scaleInfo2.getTranslate().x = (int) scaleInfo2.getTranslate().x;
        scaleInfo2.getTranslate().y = (int) scaleInfo2.getTranslate().y;
        if (this.mPenEngine.mDrawerWrapper.isInitialized) {
            scaleInfo2.setScaleRate(this.mPenEngine.getCurveEngine().getScaleRatio());
            scaleInfo2.getTranslate().set(this.mPenEngine.getCurveEngine().getViewportOffset());
            this.mPenEngine.mDrawerWrapper.mDrawer.doScale(scaleInfo2);
        }
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public void doScaleUp() {
        if (this.mPenEngine.isRelease() || this.mPenEngine.mDrawerWrapper == null || !this.mPenEngine.mDrawerWrapper.isInitialized) {
            return;
        }
        this.mPenEngine.mDrawerWrapper.mDrawer.doScaleUp();
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public void resetScale() {
        if (this.mPenEngine.isRelease() || this.mPenEngine.mDrawerWrapper == null || !this.mPenEngine.mDrawerWrapper.isInitialized) {
            return;
        }
        this.mPenEngine.mDrawerWrapper.mDrawer.resetScale();
    }

    public float b() {
        return Math.min(this.s / this.mVisibleWidth, 6.0f);
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public ScaleInfo getCanvasScaleInfo() {
        if (this.mPenEngine.isRelease()) {
            return new ScaleInfo();
        }
        this.mScaleInfo.setScaleRate(this.mPenEngine.getCurveEngine().getScaleRatio());
        this.mScaleInfo.getTranslate().set(this.mPenEngine.getCurveEngine().getViewportOffset());
        ScaleInfo scaleInfo = this.mScaleInfo;
        return new ScaleInfo(scaleInfo.getScaleRate(), scaleInfo.getTranslate(), scaleInfo.getScaleCenter());
    }

    public ScaleInfo getScaleInfo() {
        if (this.mPenEngine.isRelease()) {
            return new ScaleInfo();
        }
        this.mScaleInfo.setScaleRate(this.mPenEngine.getCurveEngine().getScaleRatio());
        this.mScaleInfo.getTranslate().set(this.mPenEngine.getCurveEngine().getViewportOffset());
        return this.mScaleInfo;
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public float getScaleVisibleTop() {
        if (this.mPenEngine.isRelease()) {
            return 0.0f;
        }
        return this.mPenEngine.mDrawerWrapper.getScaleVisibleTop();
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public void setCanvasOperateListener(ICanvasOperateListener iCanvasOperateListener) {
        if (this.mPenEngine.isRelease()) {
            return;
        }
        this.mCanvasOperateListener = iCanvasOperateListener;
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public void setPenPropTransform(IPenPropTransform iPenPropTransform) {
        if (this.mPenEngine.isRelease()) {
            return;
        }
        this.mPenPropTransform = iPenPropTransform;
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public void setScaleMode(final boolean z) {
        this.isScale = z;
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.canvas.CanvasOperator$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4630lambda$setScaleMode$1$xcengineoperatecanvasCanvasOperator(z);
            }
        });
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public boolean isColorWheelMode() {
        if (this.mPenEngine.isRelease()) {
            return false;
        }
        Objects.requireNonNull(this);
        return false;
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public int getCurCurveColor(DataCurve dataCurve) {
        if (this.mPenEngine.isRelease()) {
            Objects.requireNonNull(this);
            return 0;
        }
        Objects.requireNonNull(this);
        int curveColor = dataCurve.getCurveColor();
        IPenPropTransform iPenPropTransform = this.mPenPropTransform;
        return iPenPropTransform != null ? iPenPropTransform.transformColor(curveColor) : curveColor;
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public int getShapeFillColor(DataShape dataShape) {
        if (this.mPenEngine.isRelease()) {
            return 0;
        }
        int fillColor = dataShape.mShapeProp.getFillColor();
        IPenPropTransform iPenPropTransform = this.mPenPropTransform;
        return iPenPropTransform != null ? iPenPropTransform.transformColor(fillColor) : fillColor;
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public void setLoadDirection(final int i) {
        if (this.mPenEngine.isRelease()) {
            return;
        }
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.canvas.CanvasOperator$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4629xc8cb0b3d(i);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setLoadDirection$2$xc-engine-operate-canvas-CanvasOperator, reason: not valid java name */
    /* synthetic */ void m4629xc8cb0b3d(int i) {
        this.mPenEngine.mDrawerWrapper.mDrawer.setLoadDirection(i);
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public void setInfiniteCanvasMode(final boolean z) {
        this.isApplyingInfiniteCanvas = z;
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.canvas.CanvasOperator$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4628x839e4e3e(z);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setInfiniteCanvasMode$3$xc-engine-operate-canvas-CanvasOperator, reason: not valid java name */
    /* synthetic */ void m4628x839e4e3e(boolean z) {
        this.mPenEngine.mDrawerWrapper.setInfiniteCanvasMode(z);
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public void setInfiniteCanvasDebuggable(final boolean z) {
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.canvas.CanvasOperator$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4627xdb3d88ea(z);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setInfiniteCanvasDebuggable$4$xc-engine-operate-canvas-CanvasOperator, reason: not valid java name */
    /* synthetic */ void m4627xdb3d88ea(boolean z) {
        this.mPenEngine.mDrawerWrapper.setInfiniteCanvasDebugMode(z);
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public void setVisibleFixedWidth(float f) {
        this.mPreviousFixedWidth = this.mVisibleFixedWidth;
        this.mVisibleFixedWidth = (float) Math.ceil(f);
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public void setVisibleFixedHeight(float f) {
        this.mVisibleFixedHeight = (float) Math.ceil(f);
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public float getVisibleFixedWidth() {
        return this.mVisibleFixedWidth;
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public float getVisibleFixedHeight() {
        return this.mVisibleFixedHeight;
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public float getCanvasDrawWidth() {
        return this.mCanvasDrawWidth;
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public void setCanvasDrawWidth(float f) {
        this.mCanvasDrawWidth = f;
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public void resetInfiniteCanvas() {
        PenEngineHandlerWrapper penEngineHandlerWrapper = this.mPenEngine.mPenEngineHandlerWrapper;
        final DrawerWrapper drawerWrapper = this.mPenEngine.mDrawerWrapper;
        Objects.requireNonNull(drawerWrapper);
        penEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.canvas.CanvasOperator$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                drawerWrapper.resetInfiniteCanvasData();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: handleSetCanvasSize, reason: merged with bridge method [inline-methods] */
    public void m4626lambda$setCanvasSize$0$xcengineoperatecanvasCanvasOperator(float f, float f2) {
        this.mCanvasSizeInfo.setCanvasWidth((float) Math.ceil(f));
        this.mCanvasSizeInfo.setCanvasHeight((float) Math.ceil(f2));
        this.mPenEngine.mDrawerWrapper.mDrawer.onCanvasSizeChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: applyScaleMode, reason: merged with bridge method [inline-methods] */
    public void m4630lambda$setScaleMode$1$xcengineoperatecanvasCanvasOperator(boolean z) {
        this.isScale = z;
        if (this.mPenEngine.mDrawerWrapper.isInitialized) {
            IMultiDrawer iMultiDrawer = this.mPenEngine.mDrawerWrapper.mDrawer;
            if (iMultiDrawer instanceof MultiDrawer) {
                ((MultiDrawer) iMultiDrawer).setScaleMode(z);
            }
        }
    }

    public void shutdown() {
        this.mCanvasOperateListener = null;
        this.mBitmapChangeListener = null;
        this.executorService.shutdown();
    }
}
