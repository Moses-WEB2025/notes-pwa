package com.airbnb.lottie.utils;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.RenderEffect;
import android.graphics.RenderNode;
import android.graphics.Shader;
import android.os.Build;
import androidx.core.graphics.BlendModeCompat;
import androidx.core.graphics.PaintCompat;
import com.airbnb.lottie.animation.LPaint;

/* JADX INFO: loaded from: classes.dex */
public class OffscreenLayer {
    private static final Matrix IDENTITY_MATRIX = new Matrix();
    private Bitmap bitmap;
    private Canvas bitmapCanvas;
    private Rect bitmapSrcRect;
    private LPaint clearPaint;
    private Paint composePaint;
    private RenderStrategy currentStrategy;
    private Rect intRectIncludingShadow;
    private DropShadow lastRenderNodeShadow;
    private float lastShadowBlurRadius = 0.0f;
    private ComposeOp op;
    private Canvas parentCanvas;
    Matrix parentCanvasMatrix;
    float[] preExistingTransform;
    private RectF rectIncludingShadow;
    private RenderNode renderNode;
    private RectF scaledBounds;
    private RectF scaledRectIncludingShadow;
    private Bitmap shadowBitmap;
    private Canvas shadowBitmapCanvas;
    private Rect shadowBitmapSrcRect;
    private BlurMaskFilter shadowBlurFilter;
    private Bitmap shadowMaskBitmap;
    private Canvas shadowMaskBitmapCanvas;
    private LPaint shadowPaint;
    private RenderNode shadowRenderNode;
    private RectF targetRect;
    private RectF tmpRect;

    protected enum RenderStrategy {
        DIRECT,
        SAVE_LAYER,
        BITMAP,
        RENDER_NODE
    }

    public static class ComposeOp {
        public int alpha;
        public BlendModeCompat blendMode;
        public ColorFilter colorFilter;
        public DropShadow shadow;

        public ComposeOp() {
            reset();
        }

        public boolean isTranslucent() {
            return this.alpha < 255;
        }

        public boolean hasBlendMode() {
            BlendModeCompat blendModeCompat = this.blendMode;
            return (blendModeCompat == null || blendModeCompat == BlendModeCompat.SRC_OVER) ? false : true;
        }

        public boolean hasShadow() {
            return this.shadow != null;
        }

        public boolean hasColorFilter() {
            return this.colorFilter != null;
        }

        public boolean isNoop() {
            return (isTranslucent() || hasBlendMode() || hasShadow() || hasColorFilter()) ? false : true;
        }

        public void reset() {
            this.alpha = 255;
            this.blendMode = null;
            this.colorFilter = null;
            this.shadow = null;
        }
    }

    private RenderStrategy chooseRenderStrategy(Canvas canvas, ComposeOp composeOp) {
        if (composeOp.isNoop()) {
            return RenderStrategy.DIRECT;
        }
        if (!composeOp.hasShadow()) {
            return RenderStrategy.SAVE_LAYER;
        }
        if (!canvas.isHardwareAccelerated()) {
            return RenderStrategy.BITMAP;
        }
        if (Build.VERSION.SDK_INT <= 31) {
            return RenderStrategy.BITMAP;
        }
        return RenderStrategy.RENDER_NODE;
    }

    private Bitmap allocateBitmap(RectF rectF, Bitmap.Config config) {
        return Bitmap.createBitmap(Math.max((int) Math.ceil(((double) rectF.width()) * 1.05d), 1), Math.max((int) Math.ceil(((double) rectF.height()) * 1.05d), 1), config);
    }

    private void deallocateBitmap(Bitmap bitmap) {
        bitmap.recycle();
    }

    private boolean needNewBitmap(Bitmap bitmap, RectF rectF) {
        return bitmap == null || rectF.width() >= ((float) bitmap.getWidth()) || rectF.height() >= ((float) bitmap.getHeight()) || rectF.width() < ((float) bitmap.getWidth()) * 0.75f || rectF.height() < ((float) bitmap.getHeight()) * 0.75f;
    }

    public Canvas start(Canvas canvas, RectF rectF, ComposeOp composeOp) {
        if (this.parentCanvas != null) {
            throw new IllegalStateException("Cannot nest start() calls on a single OffscreenBitmap - call finish() first");
        }
        if (this.preExistingTransform == null) {
            this.preExistingTransform = new float[9];
        }
        if (this.parentCanvasMatrix == null) {
            this.parentCanvasMatrix = new Matrix();
        }
        canvas.getMatrix(this.parentCanvasMatrix);
        this.parentCanvasMatrix.getValues(this.preExistingTransform);
        float[] fArr = this.preExistingTransform;
        float f = fArr[0];
        float f2 = fArr[4];
        if (this.scaledBounds == null) {
            this.scaledBounds = new RectF();
        }
        this.scaledBounds.set(rectF.left * f, rectF.top * f2, rectF.right * f, rectF.bottom * f2);
        this.parentCanvas = canvas;
        this.op = composeOp;
        this.currentStrategy = chooseRenderStrategy(canvas, composeOp);
        if (this.targetRect == null) {
            this.targetRect = new RectF();
        }
        this.targetRect.set((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
        if (this.composePaint == null) {
            this.composePaint = new LPaint();
        }
        this.composePaint.reset();
        int iOrdinal = this.currentStrategy.ordinal();
        if (iOrdinal == 0) {
            canvas.save();
            return canvas;
        }
        if (iOrdinal == 1) {
            this.composePaint.setAlpha(composeOp.alpha);
            this.composePaint.setColorFilter(composeOp.colorFilter);
            if (composeOp.hasBlendMode()) {
                PaintCompat.setBlendMode(this.composePaint, composeOp.blendMode);
            }
            Utils.saveLayerCompat(canvas, rectF, this.composePaint);
            return canvas;
        }
        if (iOrdinal == 2) {
            if (this.clearPaint == null) {
                LPaint lPaint = new LPaint();
                this.clearPaint = lPaint;
                lPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            }
            if (needNewBitmap(this.bitmap, this.scaledBounds)) {
                Bitmap bitmap = this.bitmap;
                if (bitmap != null) {
                    deallocateBitmap(bitmap);
                }
                this.bitmap = allocateBitmap(this.scaledBounds, Bitmap.Config.ARGB_8888);
                this.bitmapCanvas = new Canvas(this.bitmap);
            } else {
                Canvas canvas2 = this.bitmapCanvas;
                if (canvas2 == null) {
                    throw new IllegalStateException("If needNewBitmap() returns true, we should have a canvas ready");
                }
                canvas2.setMatrix(IDENTITY_MATRIX);
                this.bitmapCanvas.drawRect(-1.0f, -1.0f, this.scaledBounds.width() + 1.0f, this.scaledBounds.height() + 1.0f, this.clearPaint);
            }
            PaintCompat.setBlendMode(this.composePaint, composeOp.blendMode);
            this.composePaint.setColorFilter(composeOp.colorFilter);
            this.composePaint.setAlpha(composeOp.alpha);
            Canvas canvas3 = this.bitmapCanvas;
            canvas3.scale(f, f2);
            canvas3.translate(-rectF.left, -rectF.top);
            return canvas3;
        }
        if (iOrdinal == 3) {
            if (this.renderNode == null) {
                this.renderNode = new RenderNode("OffscreenLayer.main");
            }
            if (composeOp.hasShadow() && this.shadowRenderNode == null) {
                this.shadowRenderNode = new RenderNode("OffscreenLayer.shadow");
                this.lastRenderNodeShadow = null;
            }
            if (composeOp.hasBlendMode() || composeOp.hasColorFilter()) {
                if (this.composePaint == null) {
                    this.composePaint = new LPaint();
                }
                this.composePaint.reset();
                PaintCompat.setBlendMode(this.composePaint, composeOp.blendMode);
                this.composePaint.setColorFilter(composeOp.colorFilter);
                this.renderNode.setUseCompositingLayer(true, this.composePaint);
                if (composeOp.hasShadow()) {
                    RenderNode renderNode = this.shadowRenderNode;
                    if (renderNode == null) {
                        throw new IllegalStateException("Must initialize shadowRenderNode when we have shadow");
                    }
                    renderNode.setUseCompositingLayer(true, this.composePaint);
                }
            }
            this.renderNode.setAlpha(composeOp.alpha / 255.0f);
            if (composeOp.hasShadow()) {
                RenderNode renderNode2 = this.shadowRenderNode;
                if (renderNode2 == null) {
                    throw new IllegalStateException("Must initialize shadowRenderNode when we have shadow");
                }
                renderNode2.setAlpha(composeOp.alpha / 255.0f);
            }
            this.renderNode.setHasOverlappingRendering(true);
            this.renderNode.setPosition((int) this.scaledBounds.left, (int) this.scaledBounds.top, (int) this.scaledBounds.right, (int) this.scaledBounds.bottom);
            RecordingCanvas recordingCanvasBeginRecording = this.renderNode.beginRecording((int) this.scaledBounds.width(), (int) this.scaledBounds.height());
            recordingCanvasBeginRecording.setMatrix(IDENTITY_MATRIX);
            recordingCanvasBeginRecording.scale(f, f2);
            recordingCanvasBeginRecording.translate(-rectF.left, -rectF.top);
            return recordingCanvasBeginRecording;
        }
        throw new RuntimeException("Invalid render strategy for OffscreenLayer");
    }

    public void finish() {
        if (this.parentCanvas == null || this.op == null || this.preExistingTransform == null || this.targetRect == null) {
            throw new IllegalStateException("OffscreenBitmap: finish() call without matching start()");
        }
        int iOrdinal = this.currentStrategy.ordinal();
        if (iOrdinal == 0 || iOrdinal == 1) {
            this.parentCanvas.restore();
        } else if (iOrdinal != 2) {
            if (iOrdinal == 3) {
                if (this.renderNode == null) {
                    throw new IllegalStateException("RenderNode is not ready; should've been initialized at start() time");
                }
                this.parentCanvas.save();
                Canvas canvas = this.parentCanvas;
                float[] fArr = this.preExistingTransform;
                canvas.scale(1.0f / fArr[0], 1.0f / fArr[4]);
                this.renderNode.endRecording();
                if (this.op.hasShadow()) {
                    renderHardwareShadow(this.parentCanvas, this.op.shadow);
                }
                this.parentCanvas.drawRenderNode(this.renderNode);
                this.parentCanvas.restore();
            }
        } else {
            if (this.bitmap == null) {
                throw new IllegalStateException("Bitmap is not ready; should've been initialized at start() time");
            }
            if (this.op.hasShadow()) {
                renderBitmapShadow(this.parentCanvas, this.op.shadow);
            }
            if (this.bitmapSrcRect == null) {
                this.bitmapSrcRect = new Rect();
            }
            this.bitmapSrcRect.set(0, 0, (int) (this.targetRect.width() * this.preExistingTransform[0]), (int) (this.targetRect.height() * this.preExistingTransform[4]));
            this.parentCanvas.drawBitmap(this.bitmap, this.bitmapSrcRect, this.targetRect, this.composePaint);
        }
        this.parentCanvas = null;
    }

    private RectF calculateRectIncludingShadow(RectF rectF, DropShadow dropShadow) {
        if (this.rectIncludingShadow == null) {
            this.rectIncludingShadow = new RectF();
        }
        if (this.tmpRect == null) {
            this.tmpRect = new RectF();
        }
        this.rectIncludingShadow.set(rectF);
        this.rectIncludingShadow.offsetTo(rectF.left + dropShadow.getDx(), rectF.top + dropShadow.getDy());
        this.rectIncludingShadow.inset(-dropShadow.getRadius(), -dropShadow.getRadius());
        this.tmpRect.set(rectF);
        this.rectIncludingShadow.union(this.tmpRect);
        return this.rectIncludingShadow;
    }

    private void renderBitmapShadow(Canvas canvas, DropShadow dropShadow) {
        LPaint lPaint;
        RectF rectF = this.targetRect;
        if (rectF == null || this.bitmap == null) {
            throw new IllegalStateException("Cannot render to bitmap outside a start()/finish() block");
        }
        RectF rectFCalculateRectIncludingShadow = calculateRectIncludingShadow(rectF, dropShadow);
        if (this.intRectIncludingShadow == null) {
            this.intRectIncludingShadow = new Rect();
        }
        this.intRectIncludingShadow.set((int) Math.floor(rectFCalculateRectIncludingShadow.left), (int) Math.floor(rectFCalculateRectIncludingShadow.top), (int) Math.ceil(rectFCalculateRectIncludingShadow.right), (int) Math.ceil(rectFCalculateRectIncludingShadow.bottom));
        float[] fArr = this.preExistingTransform;
        float f = fArr != null ? fArr[0] : 1.0f;
        float f2 = fArr != null ? fArr[4] : 1.0f;
        if (this.scaledRectIncludingShadow == null) {
            this.scaledRectIncludingShadow = new RectF();
        }
        this.scaledRectIncludingShadow.set(rectFCalculateRectIncludingShadow.left * f, rectFCalculateRectIncludingShadow.top * f2, rectFCalculateRectIncludingShadow.right * f, rectFCalculateRectIncludingShadow.bottom * f2);
        if (this.shadowBitmapSrcRect == null) {
            this.shadowBitmapSrcRect = new Rect();
        }
        this.shadowBitmapSrcRect.set(0, 0, Math.round(this.scaledRectIncludingShadow.width()), Math.round(this.scaledRectIncludingShadow.height()));
        if (needNewBitmap(this.shadowBitmap, this.scaledRectIncludingShadow)) {
            Bitmap bitmap = this.shadowBitmap;
            if (bitmap != null) {
                deallocateBitmap(bitmap);
            }
            Bitmap bitmap2 = this.shadowMaskBitmap;
            if (bitmap2 != null) {
                deallocateBitmap(bitmap2);
            }
            this.shadowBitmap = allocateBitmap(this.scaledRectIncludingShadow, Bitmap.Config.ARGB_8888);
            this.shadowMaskBitmap = allocateBitmap(this.scaledRectIncludingShadow, Bitmap.Config.ALPHA_8);
            this.shadowBitmapCanvas = new Canvas(this.shadowBitmap);
            this.shadowMaskBitmapCanvas = new Canvas(this.shadowMaskBitmap);
        } else {
            Canvas canvas2 = this.shadowBitmapCanvas;
            if (canvas2 == null || this.shadowMaskBitmapCanvas == null || (lPaint = this.clearPaint) == null) {
                throw new IllegalStateException("If needNewBitmap() returns true, we should have a canvas and bitmap ready");
            }
            canvas2.drawRect(this.shadowBitmapSrcRect, lPaint);
            this.shadowMaskBitmapCanvas.drawRect(this.shadowBitmapSrcRect, this.clearPaint);
        }
        if (this.shadowMaskBitmap == null) {
            throw new IllegalStateException("Expected to have allocated a shadow mask bitmap");
        }
        if (this.shadowPaint == null) {
            this.shadowPaint = new LPaint(1);
        }
        this.shadowMaskBitmapCanvas.drawBitmap(this.bitmap, Math.round((this.targetRect.left - rectFCalculateRectIncludingShadow.left) * f), Math.round((this.targetRect.top - rectFCalculateRectIncludingShadow.top) * f2), (Paint) null);
        if (this.shadowBlurFilter == null || this.lastShadowBlurRadius != dropShadow.getRadius()) {
            float radius = (dropShadow.getRadius() * (f + f2)) / 2.0f;
            if (radius > 0.0f) {
                this.shadowBlurFilter = new BlurMaskFilter(radius, BlurMaskFilter.Blur.NORMAL);
            } else {
                this.shadowBlurFilter = null;
            }
            this.lastShadowBlurRadius = dropShadow.getRadius();
        }
        this.shadowPaint.setColor(dropShadow.getColor());
        if (dropShadow.getRadius() > 0.0f) {
            this.shadowPaint.setMaskFilter(this.shadowBlurFilter);
        } else {
            this.shadowPaint.setMaskFilter(null);
        }
        this.shadowPaint.setFilterBitmap(true);
        this.shadowBitmapCanvas.drawBitmap(this.shadowMaskBitmap, Math.round(dropShadow.getDx() * f), Math.round(dropShadow.getDy() * f2), this.shadowPaint);
        canvas.drawBitmap(this.shadowBitmap, this.shadowBitmapSrcRect, this.intRectIncludingShadow, this.composePaint);
    }

    private void renderHardwareShadow(Canvas canvas, DropShadow dropShadow) {
        if (this.renderNode == null || this.shadowRenderNode == null) {
            throw new IllegalStateException("Cannot render to render node outside a start()/finish() block");
        }
        if (Build.VERSION.SDK_INT < 31) {
            throw new RuntimeException("RenderEffect is not supported on API level <31");
        }
        float[] fArr = this.preExistingTransform;
        float f = fArr != null ? fArr[0] : 1.0f;
        float f2 = fArr != null ? fArr[4] : 1.0f;
        DropShadow dropShadow2 = this.lastRenderNodeShadow;
        if (dropShadow2 == null || !dropShadow.sameAs(dropShadow2)) {
            RenderEffect renderEffectCreateColorFilterEffect = RenderEffect.createColorFilterEffect(new PorterDuffColorFilter(dropShadow.getColor(), PorterDuff.Mode.SRC_IN));
            if (dropShadow.getRadius() > 0.0f) {
                float radius = (dropShadow.getRadius() * (f + f2)) / 2.0f;
                renderEffectCreateColorFilterEffect = RenderEffect.createBlurEffect(radius, radius, renderEffectCreateColorFilterEffect, Shader.TileMode.CLAMP);
            }
            this.shadowRenderNode.setRenderEffect(renderEffectCreateColorFilterEffect);
            this.lastRenderNodeShadow = dropShadow;
        }
        RectF rectFCalculateRectIncludingShadow = calculateRectIncludingShadow(this.targetRect, dropShadow);
        RectF rectF = new RectF(rectFCalculateRectIncludingShadow.left * f, rectFCalculateRectIncludingShadow.top * f2, rectFCalculateRectIncludingShadow.right * f, rectFCalculateRectIncludingShadow.bottom * f2);
        this.shadowRenderNode.setPosition(0, 0, (int) rectF.width(), (int) rectF.height());
        RecordingCanvas recordingCanvasBeginRecording = this.shadowRenderNode.beginRecording((int) rectF.width(), (int) rectF.height());
        recordingCanvasBeginRecording.translate((-rectF.left) + (dropShadow.getDx() * f), (-rectF.top) + (dropShadow.getDy() * f2));
        recordingCanvasBeginRecording.drawRenderNode(this.renderNode);
        this.shadowRenderNode.endRecording();
        canvas.save();
        canvas.translate(rectF.left, rectF.top);
        canvas.drawRenderNode(this.shadowRenderNode);
        canvas.restore();
    }

    public boolean finishDecrementsCanvasSaveCount() {
        return this.currentStrategy == RenderStrategy.RENDER_NODE;
    }
}
