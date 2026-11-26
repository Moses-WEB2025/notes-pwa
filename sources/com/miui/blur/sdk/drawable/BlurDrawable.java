package com.miui.blur.sdk.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes2.dex */
public class BlurDrawable extends Drawable {
    public static final int MIUI_BLUR_DEFAULT_DARK = 6;
    public static final int MIUI_BLUR_DEFAULT_LIGHT = 2;
    public static final int MIUI_BLUR_EXTRA_THIN_DARK = 4;
    public static final int MIUI_BLUR_EXTRA_THIN_LIGHT = 0;
    public static final int MIUI_BLUR_HEAVY_DARK = 7;
    public static final int MIUI_BLUR_HEAVY_LIGHT = 3;
    public static final int MIUI_BLUR_THIN_DARK = 5;
    public static final int MIUI_BLUR_THIN_LIGHT = 1;
    public static final int MIUI_FULL_SCREEN_BLUR_DEFAULT_DARK = 11;
    public static final int MIUI_FULL_SCREEN_BLUR_DEFAULT_LIGHT = 9;
    public static final int MIUI_FULL_SCREEN_BLUR_THIN_DARK = 10;
    public static final int MIUI_FULL_SCREEN_BLUR_THIN_LIGHT = 8;
    private static final String TAG = "BlurDrawable";
    private static final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    private int mAlpha;
    private long mFunctor;
    private Method mMethodCallDrawGLFunction;
    private Paint mPaint;
    private boolean mBlurEnabled = true;
    private int mBlurWidth = getBounds().width();
    private int mBlurHeight = getBounds().height();

    public static boolean isSupportBlurStatic() {
        return true;
    }

    public static native void nAddMixColor(long j, int i, int i2);

    public static native void nClearMixColor(long j);

    public static native long nCreateNativeFunctor(int i, int i2);

    public static native long nDeleteNativeFunctor(long j);

    public static native void nEnableBlur(long j, boolean z);

    public static native void nNeedUpdateBounds(long j, boolean z);

    public static native void nSetAlpha(long j, float f);

    public static native void nSetBlurCornerRadii(long j, float[] fArr);

    public static native void nSetBlurMode(long j, int i);

    public static native void nSetBlurRatio(long j, float f);

    public static native void nSetMixColor(long j, int i, int i2);

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return 0;
    }

    public boolean isSupportBlur() {
        return true;
    }

    static {
        try {
            if (isSupportBlurStatic()) {
                System.loadLibrary("miuiblursdk");
            }
        } catch (Throwable th) {
            Log.e(TAG, "Failed to load miuiblursdk library", th);
            try {
                System.loadLibrary("miuiblur");
            } catch (Throwable th2) {
                Log.e(TAG, "Failed to load miuiblur library", th2);
            }
        }
    }

    public BlurDrawable() {
        this.mFunctor = 0L;
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setColor(0);
        if (isSupportBlur()) {
            this.mFunctor = nCreateNativeFunctor(this.mBlurWidth, this.mBlurHeight);
            initMethod();
        }
    }

    public void setMiuiBlurType(int i) {
        switch (i) {
            case 0:
                clearMixColor();
                addMixColor(18, Color.parseColor("#7f4d4d4d"));
                addMixColor(29, Color.parseColor("#26d9d9d9"));
                setBlurRatio(0.4f);
                break;
            case 1:
                clearMixColor();
                addMixColor(18, Color.parseColor("#84585858"));
                addMixColor(29, Color.parseColor("#40e3e3e3"));
                setBlurRatio(0.7f);
                break;
            case 2:
                clearMixColor();
                addMixColor(18, Color.parseColor("#8f606060"));
                addMixColor(29, Color.parseColor("#a3f2f2f2"));
                setBlurRatio(0.9f);
                break;
            case 3:
                clearMixColor();
                addMixColor(18, Color.parseColor("#a66b6b6b"));
                addMixColor(29, Color.parseColor("#ccf5f5f5"));
                setBlurRatio(1.0f);
                break;
            case 4:
                clearMixColor();
                addMixColor(19, Color.parseColor("#4dadadad"));
                addMixColor(29, Color.parseColor("#33616161"));
                setBlurRatio(0.4f);
                break;
            case 5:
                clearMixColor();
                addMixColor(19, Color.parseColor("#618a8a8a"));
                addMixColor(29, Color.parseColor("#4d424242"));
                setBlurRatio(0.7f);
                break;
            case 6:
                clearMixColor();
                addMixColor(19, Color.parseColor("#75737373"));
                addMixColor(29, Color.parseColor("#8a262626"));
                setBlurRatio(0.9f);
                break;
            case 7:
                clearMixColor();
                addMixColor(19, Color.parseColor("#7f5c5c5c"));
                addMixColor(29, Color.parseColor("#bf1f1f1f"));
                setBlurRatio(1.0f);
                break;
            case 8:
                clearMixColor();
                addMixColor(18, Color.parseColor("#61424242"));
                addMixColor(29, Color.parseColor("#1effffff"));
                setBlurRatio(1.0f);
                break;
            case 9:
                clearMixColor();
                addMixColor(18, Color.parseColor("#85666666"));
                addMixColor(29, Color.parseColor("#66ffffff"));
                setBlurRatio(1.0f);
                break;
            case 10:
                clearMixColor();
                addMixColor(19, Color.parseColor("#52b4b4b4"));
                addMixColor(29, Color.parseColor("#26000000"));
                setBlurRatio(1.0f);
                break;
            case 11:
                clearMixColor();
                addMixColor(19, Color.parseColor("#80a3a3a3"));
                addMixColor(29, Color.parseColor("#66000000"));
                setBlurRatio(1.0f);
                break;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Log.e(TAG, "draw");
        if (canvas.isHardwareAccelerated() && this.mBlurEnabled && isSupportBlur()) {
            drawBlurBack(canvas);
        } else {
            canvas.drawRect(getBounds(), this.mPaint);
        }
    }

    private void drawBlurBack(Canvas canvas) {
        try {
            this.mMethodCallDrawGLFunction.setAccessible(true);
            this.mMethodCallDrawGLFunction.invoke(canvas, Long.valueOf(this.mFunctor));
        } catch (Throwable th) {
            Log.e(TAG, "canvas function [callDrawGLFunction()] error", th);
        }
    }

    private void initMethod() {
        try {
            this.mMethodCallDrawGLFunction = (Method) Class.class.getDeclaredMethod("getDeclaredMethod", String.class, Class[].class).invoke((Class) Class.class.getDeclaredMethod("forName", String.class).invoke(null, "android.graphics.RecordingCanvas"), "callDrawGLFunction2", new Class[]{Long.TYPE});
        } catch (Exception e) {
            Log.e(TAG, "canvas function [callDrawGLFunction()] error", e);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mAlpha = i;
        nSetAlpha(this.mFunctor, i / 255.0f);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        Log.d(TAG, "nothing in setColorFilter");
    }

    protected void finalize() throws Throwable {
        if (isSupportBlur()) {
            nDeleteNativeFunctor(this.mFunctor);
        }
        Log.e(TAG, "finalize");
        super.finalize();
    }

    public void setBlurMode(int i) {
        if (isSupportBlur()) {
            nSetBlurMode(this.mFunctor, i);
            invalidateOnMainThread();
        }
    }

    public void setMixColor(int i, int i2) {
        if (isSupportBlur()) {
            nSetMixColor(this.mFunctor, i2, i);
            invalidateOnMainThread();
        }
    }

    public void addMixColor(int i) {
        if (isSupportBlur()) {
            nAddMixColor(this.mFunctor, i, 4);
            invalidateOnMainThread();
        }
    }

    public void addMixColor(int i, int i2) {
        if (isSupportBlur()) {
            nAddMixColor(this.mFunctor, i2, i);
            invalidateOnMainThread();
        }
    }

    public void clearMixColor() {
        if (isSupportBlur()) {
            nClearMixColor(this.mFunctor);
            invalidateOnMainThread();
        }
    }

    public void setBlurRatio(float f) {
        if (isSupportBlur()) {
            nSetBlurRatio(this.mFunctor, f);
            invalidateOnMainThread();
        }
    }

    public void setBlurCornerRadii(float[] fArr) {
        if (isSupportBlur()) {
            nSetBlurCornerRadii(this.mFunctor, fArr);
            invalidateOnMainThread();
        }
    }

    public void enableBlur(boolean z) {
        if (isSupportBlur()) {
            this.mBlurEnabled = z;
            nEnableBlur(this.mFunctor, z);
        }
    }

    public void needUpdateBounds(boolean z) {
        if (isSupportBlur()) {
            nNeedUpdateBounds(this.mFunctor, z);
        }
    }

    private void invalidateOnMainThread() {
        Looper looperMyLooper = Looper.myLooper();
        if (looperMyLooper == null || !looperMyLooper.equals(Looper.getMainLooper())) {
            mainThreadHandler.post(new Runnable() { // from class: com.miui.blur.sdk.drawable.BlurDrawable.1
                @Override // java.lang.Runnable
                public void run() {
                    BlurDrawable.this.invalidateSelf();
                }
            });
        } else {
            invalidateSelf();
        }
    }
}
