package com.miui.libhandwritingpreview.operator;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.Surface;
import com.miui.libhandwritingpreview.engine.PenPreEngine;
import com.miui.libhandwritingpreview.engine.PreMiCurveEngine;
import com.miui.libhandwritingpreview.manager.PreCurveDataManager;
import com.miui.libhandwritingpreview.manager.PreDrawerWrapper;
import com.miui.libhandwritingpreview.manager.PrePenEngineHandlerWrapper;
import java.util.Objects;
import xc.engine.core.graphics.pen.PenType;
import xc.engine.data.CurveProp;
import xc.engine.data.IPenPropTransform;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.DataShape;
import xc.engine.operate.canvas.CanvasSizeInfo;
import xc.engine.operate.canvas.IBitmapChangeListener;
import xc.engine.operate.canvas.ICanvasOperateListener;
import xc.engine.operate.canvas.ICanvasOperator;
import xc.engine.operate.canvas.draw.IMultiDrawer;
import xc.engine.operate.canvas.draw.MultiDrawer;
import xc.engine.operate.canvas.draw.scale.ScaleInfo;
import xc.engine.utils.PenEngineUtils;
import xc.engine.utils.SizeConfig;

/* JADX INFO: loaded from: classes3.dex */
public class PreCanvasOperator implements ICanvasOperator {
    public boolean isApplyingInfiniteCanvas;
    public boolean isScale;
    public IBitmapChangeListener mBitmapChangeListener;
    public float mCanvasDrawWidth;
    public ICanvasOperateListener mCanvasOperateListener;
    public final PenPreEngine mPenEngine;
    private final PrePenEngineHandlerWrapper mPenEngineHandlerWrapper;
    public IPenPropTransform mPenPropTransform;
    public float mPreviousFixedWidth;
    public float mVisibleFixedHeight;
    public float mVisibleFixedWidth;
    private final float s;
    private final String TAG = "PreCanvasOperator";
    private final boolean isColorWheelMode = false;
    public final ScaleInfo mScaleInfo = new ScaleInfo();
    public boolean mCanSelectInvisibleData = true;
    private final Runnable mChangeVisibleSizeTask = new ChangeVisibleSizeTask();
    public CanvasSizeInfo mCanvasSizeInfo = new CanvasSizeInfo();
    public float mVisibleWidth = SizeConfig.DEFAULT_VISIBLE_SIZE.getWidth();
    private float mVisibleHeight = SizeConfig.DEFAULT_VISIBLE_SIZE.getHeight();

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public void forceRedraw() {
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public void generateBrushPreviewBitmap(CurveProp curveProp, Bitmap bitmap) {
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public void initPreviewBitmap(CurveProp curveProp, Bitmap bitmap) {
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public void setSurface(Surface surface) {
    }

    public class ChangeVisibleSizeTask implements Runnable {
        public ChangeVisibleSizeTask() {
        }

        @Override // java.lang.Runnable
        public void run() {
            float f = PreCanvasOperator.this.mVisibleWidth;
            float f2 = PreCanvasOperator.this.mVisibleHeight;
            PreCanvasOperator.this.mCanvasSizeInfo.setVisibleWidth((float) Math.ceil(f));
            PreCanvasOperator.this.mCanvasSizeInfo.setVisibleHeight((float) Math.ceil(f2));
            PreCanvasOperator.this.mPenEngine.mDrawerWrapper.onCanvasVisibleSizeChanged(f, f2);
        }
    }

    public PreCanvasOperator(PenPreEngine penPreEngine) {
        this.mPenEngine = penPreEngine;
        this.mPenEngineHandlerWrapper = penPreEngine.getEngineHandler();
        this.s = Math.min(penPreEngine.getScreenInfo().mWidthPixels, penPreEngine.getScreenInfo().mHeightPixels) * 6.0f;
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public void initDrawer() {
        if (this.mPenEngine.isRelease()) {
            return;
        }
        PrePenEngineHandlerWrapper prePenEngineHandlerWrapper = this.mPenEngine.mPenEngineHandlerWrapper;
        final PreDrawerWrapper preDrawerWrapper = this.mPenEngine.mDrawerWrapper;
        Objects.requireNonNull(preDrawerWrapper);
        prePenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: com.miui.libhandwritingpreview.operator.PreCanvasOperator$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                preDrawerWrapper.initDrawer();
            }
        });
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
            dataCurve2.insertPoint(dataCurve2.mSavePoints, 0.08f * f, f3, 1.0f, 0.0f, 1.5707964f, 1.0f, 0L, (short) 1, 0.0f);
            dataCurve2.insertPoint(dataCurve2.mSavePoints, f * 0.4f, f3, 1.0f, 0.0f, 1.5707964f, 1.0f, 0L, (short) 1, 0.0f);
            dataCurve2.insertPoint(dataCurve2.mSavePoints, f * 0.5f, f3, 1.0f, 0.0f, 1.5707964f, 1.0f, 0L, (short) 1, 0.0f);
            dataCurve2.insertPoint(dataCurve2.mSavePoints, f, f3, 1.0f, 0.0f, 1.5707964f, 1.0f, 0L, (short) 1, 0.0f);
            dataCurve2.insertPoint(dataCurve2.mSavePoints, 0.9f * f, f3, 1.0f, 0.0f, 1.5707964f, 1.0f, 0L, (short) 1, 0.0f);
        }
    }

    private void fillPreviewCurvePoints(DataCurve dataCurve, float f, float f2) {
        float f3 = f2 / 2.0f;
        dataCurve.insertPoint(dataCurve.mSavePoints, 0.12f * f, f3, 0.12f, 0.0f, 1.5707964f, 1.0f, 0L, (short) 1, 0.0f);
        dataCurve.insertPoint(dataCurve.mSavePoints, 0.3f * f, 0.6f * f3, 0.5f, 0.0f, 1.5707964f, 1.0f, 0L, (short) 1, 0.0f);
        dataCurve.insertPoint(dataCurve.mSavePoints, 0.5f * f, f3, 1.0f, 0.0f, 1.5707964f, 1.0f, 0L, (short) 1, 0.0f);
        dataCurve.insertPoint(dataCurve.mSavePoints, 0.7f * f, 1.4f * f3, 0.8f, 0.0f, 1.5707964f, 1.0f, 0L, (short) 1, 0.0f);
        dataCurve.insertPoint(dataCurve.mSavePoints, 0.9f * f, f3, 0.5f, 0.0f, 1.5707964f, 1.0f, 0L, (short) 1, 0.0f);
        dataCurve.insertPoint(dataCurve.mSavePoints, 0.94f * f, f3, 0.03f, 0.0f, 1.5707964f, 1.0f, 0L, (short) 1, 0.0f);
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public void setVisibleSize(float f, float f2) {
        if (this.mPenEngine.isRelease()) {
            return;
        }
        this.mVisibleWidth = f;
        this.mVisibleHeight = f2;
        if (this.mPenEngineHandlerWrapper.hasMessages(this.mChangeVisibleSizeTask)) {
            this.mPenEngineHandlerWrapper.getMHandler().removeCallbacks(this.mChangeVisibleSizeTask);
        }
        this.mPenEngineHandlerWrapper.sendMessage(this.mChangeVisibleSizeTask);
        ICanvasOperateListener iCanvasOperateListener = this.mCanvasOperateListener;
        if (iCanvasOperateListener != null) {
            iCanvasOperateListener.onCanvasDataChanged(null, 11);
        }
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public void setCanvasSize(final float f, final float f2) {
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: com.miui.libhandwritingpreview.operator.PreCanvasOperator$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m882x8fbc859a(f, f2);
            }
        });
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public void renderingUpdate() {
        this.mPenEngine.getCurveEngine().requestMultiBufferRender(PreMiCurveEngine.ERefreshReason.Force);
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public void setMaxPage(int i) {
        if (this.mPenEngine.isRelease()) {
            return;
        }
        PreCurveDataManager preCurveDataManager = this.mPenEngine.mCurveDataManager;
        preCurveDataManager.mMaxPage = i;
        preCurveDataManager.init();
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
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: com.miui.libhandwritingpreview.operator.PreCanvasOperator$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m886x3325a385(z);
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
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: com.miui.libhandwritingpreview.operator.PreCanvasOperator$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m885x4baf13f2(i);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setLoadDirection$2$com-miui-libhandwritingpreview-operator-PreCanvasOperator, reason: not valid java name */
    /* synthetic */ void m885x4baf13f2(int i) {
        this.mPenEngine.mDrawerWrapper.mDrawer.setLoadDirection(i);
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public void setInfiniteCanvasMode(final boolean z) {
        this.isApplyingInfiniteCanvas = z;
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: com.miui.libhandwritingpreview.operator.PreCanvasOperator$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m884x7d01f8b3(z);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setInfiniteCanvasMode$3$com-miui-libhandwritingpreview-operator-PreCanvasOperator, reason: not valid java name */
    /* synthetic */ void m884x7d01f8b3(boolean z) {
        this.mPenEngine.mDrawerWrapper.setInfiniteCanvasMode(z);
    }

    @Override // xc.engine.operate.canvas.ICanvasOperator
    public void setInfiniteCanvasDebuggable(final boolean z) {
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: com.miui.libhandwritingpreview.operator.PreCanvasOperator$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m883x9380605f(z);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setInfiniteCanvasDebuggable$4$com-miui-libhandwritingpreview-operator-PreCanvasOperator, reason: not valid java name */
    /* synthetic */ void m883x9380605f(boolean z) {
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
        PrePenEngineHandlerWrapper prePenEngineHandlerWrapper = this.mPenEngine.mPenEngineHandlerWrapper;
        final PreDrawerWrapper preDrawerWrapper = this.mPenEngine.mDrawerWrapper;
        Objects.requireNonNull(preDrawerWrapper);
        prePenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: com.miui.libhandwritingpreview.operator.PreCanvasOperator$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                preDrawerWrapper.resetInfiniteCanvasData();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: handleSetCanvasSize, reason: merged with bridge method [inline-methods] */
    public void m882x8fbc859a(float f, float f2) {
        this.mCanvasSizeInfo.setCanvasWidth((float) Math.ceil(f));
        this.mCanvasSizeInfo.setCanvasHeight((float) Math.ceil(f2));
        this.mPenEngine.mDrawerWrapper.mDrawer.onCanvasSizeChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: applyScaleMode, reason: merged with bridge method [inline-methods] */
    public void m886x3325a385(boolean z) {
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
        this.mPenPropTransform = null;
    }
}
