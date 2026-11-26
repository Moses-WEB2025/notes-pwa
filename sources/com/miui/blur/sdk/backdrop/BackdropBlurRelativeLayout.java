package com.miui.blur.sdk.backdrop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewRootImpl;
import android.widget.RelativeLayout;
import com.android.internal.graphics.drawable.BackgroundBlurDrawable;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes2.dex */
public class BackdropBlurRelativeLayout extends RelativeLayout implements ViewBlurDrawInfo {
    private Method createBackgroundBlurDrawable;
    private final Paint mBlendPaint;
    private Object mBlurBackground;
    private final BlurLayerHolderForGoogle mBlurLayerHolder;
    private int mBlurRadius;
    private final BackdropBlurViewBinder mBlurViewBinder;
    private float mCornerRadiusBL;
    private float mCornerRadiusBR;
    private float mCornerRadiusTL;
    private float mCornerRadiusTR;
    private Method setAlpha;
    private Method setBlurRadius;
    private Method setCornerRadius;

    public BackdropBlurRelativeLayout(Context context) {
        this(context, null);
    }

    public BackdropBlurRelativeLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BackdropBlurRelativeLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Paint paint = new Paint();
        this.mBlendPaint = paint;
        this.mBlurViewBinder = new BackdropBlurViewBinder(this, this);
        setWillNotDraw(false);
        paint.setAntiAlias(true);
        this.mBlurLayerHolder = new BlurLayerHolderForGoogle();
        if (isGoogleBlurSupported()) {
            this.setBlurRadius = ReflectUtils.getMethod(BackgroundBlurDrawable.class, "setBlurRadius", Integer.TYPE);
            this.setCornerRadius = ReflectUtils.getMethod(BackgroundBlurDrawable.class, "setCornerRadius", Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE);
            this.setAlpha = ReflectUtils.getMethod(BackgroundBlurDrawable.class, "setAlpha", Integer.TYPE);
            this.createBackgroundBlurDrawable = ReflectUtils.getMethod(ViewRootImpl.class, "createBackgroundBlurDrawable", new Class[0]);
        }
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        this.mBlurViewBinder.onDraw(canvas);
        if (!isGoogleBlurSupported() || canvas.isHardwareAccelerated()) {
            super.draw(canvas);
        }
        if (isGoogleBlurSupported()) {
            this.mBlurLayerHolder.draw(canvas, this, this.mCornerRadiusTL);
        }
    }

    public void dispatchAlphaChanged(float f) {
        if (getBackground() != null) {
            for (ViewParent parent = getParent(); parent instanceof ViewGroup; parent = parent.getParent()) {
                f *= ((ViewGroup) parent).getAlpha();
            }
            getBackground().setAlpha((int) (f * 255.0f));
        }
    }

    public float getParentAlpha() {
        float alpha = getAlpha();
        for (ViewParent parent = getParent(); parent instanceof ViewGroup; parent = parent.getParent()) {
            alpha *= ((ViewGroup) parent).getAlpha();
        }
        return alpha;
    }

    @Override // android.view.View
    public void onVisibilityAggregated(boolean z) {
        super.onVisibilityAggregated(z);
        this.mBlurViewBinder.onVisibilityAggregated(z);
        postInvalidateOnAnimation();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mBlurViewBinder.onAttachedToWindow();
        if (isGoogleBlurSupported()) {
            if (getBackground() == null || (getBackground() instanceof BackgroundBlurDrawable)) {
                Object objInvokeMethod = ReflectUtils.invokeMethod(getBlurViewRootImpl(), this.createBackgroundBlurDrawable, new Object[0]);
                this.mBlurBackground = objInvokeMethod;
                if (objInvokeMethod instanceof BackgroundBlurDrawable) {
                    ReflectUtils.invokeMethod(objInvokeMethod, this.setBlurRadius, Integer.valueOf(this.mBlurRadius));
                    ReflectUtils.invokeMethod(this.mBlurBackground, this.setCornerRadius, Float.valueOf(this.mCornerRadiusTL), Float.valueOf(this.mCornerRadiusTR), Float.valueOf(this.mCornerRadiusBL), Float.valueOf(this.mCornerRadiusBR));
                    if (getParentAlpha() == 0.0f) {
                        ReflectUtils.invokeMethod(this.mBlurBackground, this.setAlpha, 0);
                    }
                    setBackground((Drawable) this.mBlurBackground);
                }
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mBlurViewBinder.onDetachedFromWindow();
    }

    public void setBlurEnabled(boolean z) {
        this.mBlurViewBinder.setBlurEnabled(z);
        if (isGoogleBlurSupported()) {
            this.mBlurLayerHolder.setEnable(z);
            if (z) {
                if (this.mBlurBackground instanceof BackgroundBlurDrawable) {
                    setBlurRadius(this.mBlurRadius);
                    setBackground((Drawable) this.mBlurBackground);
                }
            } else if (getBackground() instanceof BackgroundBlurDrawable) {
                setBlurRadius(0);
                setBackground(null);
            }
        }
        postInvalidateOnAnimation();
    }

    public void damageInParent() {
        super.damageInParent();
        invalidate();
    }

    public void setBlurRadius(int i) {
        if (this.mBlurRadius != i) {
            this.mBlurRadius = i;
            if (isGoogleBlurSupported()) {
                Object obj = this.mBlurBackground;
                if (obj instanceof BackgroundBlurDrawable) {
                    ReflectUtils.invokeMethod(obj, this.setBlurRadius, Integer.valueOf(i));
                }
            }
        }
    }

    public void setCornerRadius(float f) {
        setCornerRadius(f, f, f, f);
    }

    public void setCornerRadius(float f, float f2, float f3, float f4) {
        if (this.mCornerRadiusTL == f && this.mCornerRadiusTR == f2 && this.mCornerRadiusBL == f3 && this.mCornerRadiusBR == f4) {
            return;
        }
        this.mCornerRadiusTL = f;
        this.mCornerRadiusTR = f2;
        this.mCornerRadiusBL = f3;
        this.mCornerRadiusBR = f4;
        if (isGoogleBlurSupported()) {
            Object obj = this.mBlurBackground;
            if (obj instanceof BackgroundBlurDrawable) {
                ReflectUtils.invokeMethod(obj, this.setCornerRadius, Float.valueOf(f), Float.valueOf(f2), Float.valueOf(f3), Float.valueOf(f4));
            }
        }
    }

    public boolean isBackdropBlurSupported() {
        return this.mBlurViewBinder.isBackdropBlurSupported();
    }

    public boolean isBlurEnabledAndSupported() {
        return this.mBlurViewBinder.isBlurEnabledAndSupported();
    }

    public boolean isGoogleBlurSupported() {
        return BlurManager.GOOGLE_BACKGROUND_BLUR_SUPPORTED;
    }
}
