package com.miui.blur.sdk.backdrop;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.GraphicBuffer;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Build;
import android.os.Handler;
import android.os.Trace;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.renderscript.Type;
import android.util.Log;
import android.view.MiuiCompositionSamplingListener;
import android.view.SurfaceControl;
import android.view.ViewRootImpl;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;

/* JADX INFO: loaded from: classes2.dex */
class BlurLayerHolder {
    private static final float DEFAULT_SCALE_FACTOR = 16.0f;
    private static final String TAG = "BlurLayerHolder";
    private static final String TRACE_ATTACH_AND_BLUR = "attachAndProcessBuffer";
    private static final String TRACE_PROCESS_BLUR = "processBlur";
    private final Set<BlurDrawInfo> mAttachedDrawInfos;
    private final Paint mBlendPaint;
    private BlurBufferHolder mBlurBuffer;
    private float mBlurRadius;
    private final Context mContext;
    private boolean mDestroyed;
    private final Handler mHandler;
    private final Object mLock;
    private final Outline mOutline;
    private final Paint mPaint;
    private final RenderScript mRenderScript;
    private BlurManager.CompositionSamplingListenerWrapper mSamplingListener;
    private float mScale;
    private final ScriptIntrinsicBlur mScriptIntrinsicBlur;
    private final int[] mTmpLoc;
    private final Rect mTmpRect;
    private final Point mTmpSize;
    private final ViewRootImpl mViewRoot;
    private static final Pools.SimplePool<Matrix> MATRIX_SIMPLE_POOL = new Pools.SimplePool<>(10);
    private static final int DEFAULT_BLUR_RADIUS = BlurStyle.DEFAULT_LIGHT.getBlurRadius();

    BlurLayerHolder(Context context, ViewRootImpl viewRootImpl, RenderScript renderScript, Handler handler) {
        Paint paint = new Paint();
        this.mPaint = paint;
        Paint paint2 = new Paint();
        this.mBlendPaint = paint2;
        this.mOutline = new Outline();
        this.mLock = new Object();
        this.mTmpLoc = new int[2];
        this.mTmpRect = new Rect();
        this.mTmpSize = new Point();
        this.mBlurRadius = DEFAULT_BLUR_RADIUS;
        this.mAttachedDrawInfos = new HashSet();
        this.mContext = context;
        this.mViewRoot = viewRootImpl;
        this.mHandler = handler;
        this.mRenderScript = renderScript;
        paint.setAntiAlias(true);
        paint2.setAntiAlias(true);
        ScriptIntrinsicBlur scriptIntrinsicBlurCreate = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        this.mScriptIntrinsicBlur = scriptIntrinsicBlurCreate;
        scriptIntrinsicBlurCreate.setRadius(this.mBlurRadius);
    }

    public void draw(Canvas canvas, BlurDrawInfo blurDrawInfo) {
        BlurBufferHolder blurBufferHolder = this.mBlurBuffer;
        if (blurBufferHolder == null || !BlurManager.BACKDROP_SAMPLING_ENABLED) {
            return;
        }
        updateLocalMatrixForShader(blurDrawInfo, blurBufferHolder);
        blurDrawInfo.getBlurOutline(this.mOutline);
        int i = this.mOutline.mMode;
        if (i == 2) {
            drawPath(canvas, this.mOutline.mPath, blurDrawInfo);
        } else if (i == 1) {
            this.mOutline.getRect(this.mTmpRect);
            drawRect(canvas, this.mTmpRect, this.mOutline.getRadius(), blurDrawInfo);
        } else {
            drawEmpty(canvas, blurDrawInfo);
        }
    }

    private void updateLocalMatrixForShader(BlurDrawInfo blurDrawInfo, BlurBufferHolder blurBufferHolder) {
        boolean z = this.mContext.getResources().getConfiguration().orientation == 2;
        blurDrawInfo.getLocationOnScreen(this.mTmpLoc);
        int[] iArr = this.mTmpLoc;
        int i = iArr[0];
        int i2 = iArr[1];
        float f = 1.0f / this.mScale;
        Pools.SimplePool<Matrix> simplePool = MATRIX_SIMPLE_POOL;
        Matrix matrixAcquire = simplePool.acquire();
        if (matrixAcquire == null) {
            matrixAcquire = new Matrix();
        }
        matrixAcquire.reset();
        if (z) {
            matrixAcquire.setRotate(180.0f, blurBufferHolder.mBufferWidth / 2.0f, blurBufferHolder.mBufferHeight / 2.0f);
        }
        matrixAcquire.postScale(f, f, 0.0f, 0.0f);
        matrixAcquire.postTranslate(-i, -i2);
        simplePool.release(matrixAcquire);
        blurBufferHolder.mShader.setLocalMatrix(matrixAcquire);
    }

    private void drawPath(Canvas canvas, Path path, BlurDrawInfo blurDrawInfo) {
        canvas.drawPath(path, this.mPaint);
        for (BlurStyle.BlendConfig blendConfig : blurDrawInfo.getBlurStyle().getConfigs()) {
            setupBlendMode(blendConfig.mColor, blendConfig.mBlendMode);
            canvas.drawPath(path, this.mBlendPaint);
        }
    }

    private void drawRect(Canvas canvas, Rect rect, float f, BlurDrawInfo blurDrawInfo) {
        canvas.drawRoundRect(rect.left, rect.top, rect.right, rect.bottom, f, f, this.mPaint);
        for (BlurStyle.BlendConfig blendConfig : blurDrawInfo.getBlurStyle().getConfigs()) {
            setupBlendMode(blendConfig.mColor, blendConfig.mBlendMode);
            canvas.drawRoundRect(rect.left, rect.top, rect.right, rect.bottom, f, f, this.mBlendPaint);
        }
    }

    private void drawEmpty(Canvas canvas, BlurDrawInfo blurDrawInfo) {
        canvas.drawRect(0.0f, 0.0f, blurDrawInfo.getWidth(), blurDrawInfo.getHeight(), this.mPaint);
        for (BlurStyle.BlendConfig blendConfig : blurDrawInfo.getBlurStyle().getConfigs()) {
            setupBlendMode(blendConfig.mColor, blendConfig.mBlendMode);
            canvas.drawRect(0.0f, 0.0f, blurDrawInfo.getWidth(), blurDrawInfo.getHeight(), this.mBlendPaint);
        }
    }

    private void setupBlendMode(int i, BlendMode blendMode) {
        this.mBlendPaint.setColor(i);
        this.mBlendPaint.setBlendMode(blendMode);
    }

    private void asyncInvalidateAll() {
        HashSet hashSet;
        synchronized (this.mLock) {
            hashSet = new HashSet(this.mAttachedDrawInfos);
        }
        hashSet.forEach(new Consumer() { // from class: com.miui.blur.sdk.backdrop.BlurLayerHolder$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((BlurDrawInfo) obj).postInvalidateOnAnimation();
            }
        });
    }

    void register(BlurDrawInfo blurDrawInfo) {
        synchronized (this.mLock) {
            this.mAttachedDrawInfos.add(blurDrawInfo);
            evaluateBlurRadius();
        }
    }

    void unregister(BlurDrawInfo blurDrawInfo) {
        synchronized (this.mLock) {
            this.mAttachedDrawInfos.remove(blurDrawInfo);
            evaluateBlurRadius();
        }
    }

    private void evaluateBlurRadius() {
        float fMin = Math.min(24, this.mAttachedDrawInfos.stream().mapToInt(new ToIntFunction() { // from class: com.miui.blur.sdk.backdrop.BlurLayerHolder$$ExternalSyntheticLambda5
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((BlurDrawInfo) obj).getBlurStyle().getBlurRadius();
            }
        }).min().orElse(DEFAULT_BLUR_RADIUS));
        if (fMin != this.mBlurRadius) {
            this.mBlurRadius = fMin;
            this.mScriptIntrinsicBlur.setRadius(fMin);
        }
    }

    boolean isEmpty() {
        return this.mAttachedDrawInfos.isEmpty();
    }

    void start() {
        BlurManager.CompositionSamplingListenerWrapper compositionSamplingListenerWrapperAcquire = BlurManager.CompositionSamplingListenerWrapper.acquire();
        this.mSamplingListener = compositionSamplingListenerWrapperAcquire;
        compositionSamplingListenerWrapperAcquire.setCallback(new Consumer() { // from class: com.miui.blur.sdk.backdrop.BlurLayerHolder$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.m498lambda$start$1$commiuiblursdkbackdropBlurLayerHolder((GraphicBuffer) obj);
            }
        });
        int iOrElse = this.mAttachedDrawInfos.stream().mapToInt(new ToIntFunction() { // from class: com.miui.blur.sdk.backdrop.BlurLayerHolder$$ExternalSyntheticLambda2
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((BlurDrawInfo) obj).getRequestedSamplingPeriodNs();
            }
        }).min().orElse(20000000);
        Log.d(TAG, "register " + this.mViewRoot);
        Method method = ReflectUtils.getMethod(this.mViewRoot.getClass(), "getSurfaceControl", new Class[0]);
        method.setAccessible(true);
        Object objInvokeMethod = ReflectUtils.invokeMethod(this.mViewRoot, method, new Object[0]);
        if (objInvokeMethod instanceof SurfaceControl) {
            MiuiCompositionSamplingListener.register(this.mSamplingListener, 0, (SurfaceControl) objInvokeMethod, DEFAULT_SCALE_FACTOR, iOrElse);
        }
    }

    /* JADX INFO: renamed from: lambda$start$1$com-miui-blur-sdk-backdrop-BlurLayerHolder, reason: not valid java name */
    /* synthetic */ void m498lambda$start$1$commiuiblursdkbackdropBlurLayerHolder(GraphicBuffer graphicBuffer) {
        if (this.mDestroyed) {
            return;
        }
        onSampleBufferCollected(graphicBuffer);
    }

    void destroy() {
        this.mDestroyed = true;
        Log.d(TAG, "unregister " + this.mViewRoot);
        BlurManager.CompositionSamplingListenerWrapper compositionSamplingListenerWrapper = this.mSamplingListener;
        if (compositionSamplingListenerWrapper != null) {
            MiuiCompositionSamplingListener.unregister(compositionSamplingListenerWrapper);
            BlurManager.CompositionSamplingListenerWrapper.release(this.mSamplingListener);
            this.mSamplingListener = null;
        }
        this.mHandler.post(new Runnable() { // from class: com.miui.blur.sdk.backdrop.BlurLayerHolder$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m497lambda$destroy$2$commiuiblursdkbackdropBlurLayerHolder();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$destroy$2$com-miui-blur-sdk-backdrop-BlurLayerHolder, reason: not valid java name */
    /* synthetic */ void m497lambda$destroy$2$commiuiblursdkbackdropBlurLayerHolder() {
        BlurBufferHolder blurBufferHolder = this.mBlurBuffer;
        if (blurBufferHolder != null) {
            blurBufferHolder.recycle();
            this.mBlurBuffer = null;
        }
        this.mScriptIntrinsicBlur.destroy();
    }

    private void invalidateBufferHolder(GraphicBuffer graphicBuffer) {
        BlurBufferHolder blurBufferHolder = this.mBlurBuffer;
        if (blurBufferHolder != null) {
            if (blurBufferHolder.mBufferWidth == graphicBuffer.getWidth() && this.mBlurBuffer.mBufferHeight == graphicBuffer.getHeight()) {
                return;
            }
            final BlurBufferHolder blurBufferHolder2 = this.mBlurBuffer;
            Handler handler = this.mHandler;
            blurBufferHolder2.getClass();
            handler.post(new Runnable() { // from class: com.miui.blur.sdk.backdrop.BlurLayerHolder$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    blurBufferHolder2.recycle();
                }
            });
            this.mBlurBuffer = null;
        }
    }

    private void ensureBufferHolder(GraphicBuffer graphicBuffer) {
        if (this.mBlurBuffer != null || Build.VERSION.SDK_INT <= 29) {
            return;
        }
        BlurBufferHolder blurBufferHolder = new BlurBufferHolder(graphicBuffer.getWidth(), graphicBuffer.getHeight(), this.mRenderScript, this.mScriptIntrinsicBlur);
        this.mBlurBuffer = blurBufferHolder;
        this.mPaint.setShader(blurBufferHolder.mShader);
        Context context = this.mContext;
        if (context instanceof Application) {
            this.mScale = this.mBlurBuffer.mBufferWidth / context.getResources().getDisplayMetrics().widthPixels;
        } else {
            context.getDisplay().getRealSize(this.mTmpSize);
            this.mScale = this.mBlurBuffer.mBufferWidth / this.mTmpSize.x;
        }
    }

    private void onSampleBufferCollected(GraphicBuffer graphicBuffer) {
        invalidateBufferHolder(graphicBuffer);
        ensureBufferHolder(graphicBuffer);
        Trace.beginSection(TRACE_ATTACH_AND_BLUR);
        this.mBlurBuffer.attachAndQueueBuffer(graphicBuffer);
        this.mBlurBuffer.processBlur();
        asyncInvalidateAll();
        Trace.endSection();
    }

    static class BlurBufferHolder {
        private final Bitmap mBlurred;
        private final int mBufferHeight;
        private final int mBufferWidth;
        private final Allocation mInput;
        private final Allocation mOutput;
        private final ScriptIntrinsicBlur mScriptIntrinsicBlur;
        private final BitmapShader mShader;

        BlurBufferHolder(int i, int i2, RenderScript renderScript, ScriptIntrinsicBlur scriptIntrinsicBlur) {
            this.mBufferWidth = i;
            this.mBufferHeight = i2;
            this.mScriptIntrinsicBlur = scriptIntrinsicBlur;
            Type typeCreate = new Type.Builder(renderScript, Element.RGBA_8888(renderScript)).setX(i).setY(i2).create();
            this.mInput = Allocation.createTyped(renderScript, typeCreate, 35);
            this.mOutput = Allocation.createTyped(renderScript, typeCreate, 1);
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
            this.mBlurred = bitmapCreateBitmap;
            this.mShader = new BitmapShader(bitmapCreateBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        }

        void attachAndQueueBuffer(GraphicBuffer graphicBuffer) {
            this.mInput.getSurface().attachAndQueueBuffer(graphicBuffer);
            this.mInput.ioReceive();
        }

        void processBlur() {
            Trace.beginSection(BlurLayerHolder.TRACE_PROCESS_BLUR);
            this.mScriptIntrinsicBlur.setInput(this.mInput);
            this.mScriptIntrinsicBlur.forEach(this.mOutput);
            this.mOutput.copyTo(this.mBlurred);
            Trace.endSection();
        }

        void recycle() {
            this.mInput.destroy();
            this.mOutput.destroy();
            this.mBlurred.recycle();
        }
    }
}
