package com.miui.blur.sdk.backdrop;

import android.graphics.Canvas;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes2.dex */
class BackdropBlurViewBinder {
    private boolean mAttachedToWindow;
    private final BlurDrawInfo mBlurDrawInfo;
    private boolean mBlurEnabled = true;
    private final ViewTreeObserver.OnPreDrawListener mCheckSurface = new ViewTreeObserver.OnPreDrawListener() { // from class: com.miui.blur.sdk.backdrop.BackdropBlurViewBinder.1
        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            BackdropBlurViewBinder.this.mView.getViewTreeObserver().removeOnPreDrawListener(this);
            BackdropBlurViewBinder.this.evaluateUserVisibility();
            return true;
        }
    };
    private boolean mUserVisible;
    private final View mView;
    private boolean mVisibilityAggregated;

    BackdropBlurViewBinder(View view, BlurDrawInfo blurDrawInfo) {
        this.mView = view;
        this.mBlurDrawInfo = blurDrawInfo;
    }

    void onDraw(Canvas canvas) {
        if (isBlurEnabledAndSupported()) {
            BlurManager.draw(canvas, this.mBlurDrawInfo);
        }
    }

    void onVisibilityAggregated(boolean z) {
        this.mVisibilityAggregated = z;
        evaluateUserVisibility();
    }

    void onAttachedToWindow() {
        this.mAttachedToWindow = true;
        evaluateUserVisibility();
    }

    void onDetachedFromWindow() {
        this.mAttachedToWindow = false;
        evaluateUserVisibility();
    }

    void setBlurEnabled(boolean z) {
        this.mBlurEnabled = z;
        evaluateUserVisibility();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:9:0x002c  */
    public void evaluateUserVisibility() {
        boolean zIsValid;
        Method method;
        ViewRootImpl viewRootImpl = this.mView.getViewRootImpl();
        if (viewRootImpl == null || (method = ReflectUtils.getMethod(viewRootImpl.getClass(), "getSurfaceControl", new Class[0])) == null) {
            zIsValid = false;
        } else {
            method.setAccessible(true);
            Object objInvokeMethod = ReflectUtils.invokeMethod(viewRootImpl, method, new Object[0]);
            if (objInvokeMethod instanceof SurfaceControl) {
                zIsValid = ((SurfaceControl) objInvokeMethod).isValid();
            } else {
                zIsValid = false;
            }
        }
        boolean z = isBackdropBlurSupported() && viewRootImpl != null && zIsValid;
        boolean z2 = isBackdropBlurSupported() && this.mVisibilityAggregated && this.mAttachedToWindow && this.mBlurEnabled;
        boolean z3 = z2 && z;
        if (z2 && !z) {
            this.mView.getViewTreeObserver().addOnPreDrawListener(this.mCheckSurface);
        }
        if (this.mUserVisible != z3) {
            if (z3) {
                BlurManager.register(this.mView.getContext(), this.mBlurDrawInfo);
            } else {
                BlurManager.unregister(this.mBlurDrawInfo);
            }
        }
        this.mUserVisible = z3;
    }

    public boolean isBackdropBlurSupported() {
        return BlurManager.BACKDROP_SAMPLING_ENABLED;
    }

    public boolean isBlurEnabledAndSupported() {
        return this.mBlurEnabled && isBackdropBlurSupported();
    }

    public boolean isGoogleBlurSupported() {
        return BlurManager.GOOGLE_BACKGROUND_BLUR_SUPPORTED;
    }
}
