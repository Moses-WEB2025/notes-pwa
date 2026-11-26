package com.miui.blur.sdk.backdrop;

import android.content.Context;
import android.graphics.Outline;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.ViewRootImpl;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes2.dex */
public interface ViewBlurDrawInfo extends BlurDrawInfo {
    Context getContext();

    ViewOutlineProvider getOutlineProvider();

    ViewRootImpl getViewRootImpl();

    @Override // com.miui.blur.sdk.backdrop.BlurDrawInfo
    default ViewRootImpl getBlurViewRootImpl() {
        return getViewRootImpl();
    }

    default SurfaceControl getSurfaceControl() {
        Method method;
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null && (method = ReflectUtils.getMethod(viewRootImpl.getClass(), "getSurfaceControl", new Class[0])) != null) {
            method.setAccessible(true);
            Object objInvokeMethod = ReflectUtils.invokeMethod(viewRootImpl, method, new Object[0]);
            if (objInvokeMethod instanceof SurfaceControl) {
                return (SurfaceControl) objInvokeMethod;
            }
        }
        return null;
    }

    @Override // com.miui.blur.sdk.backdrop.BlurDrawInfo
    default BlurStyle getBlurStyle() {
        return (getContext().getResources().getConfiguration().uiMode & 48) == 32 ? getBlurStyleNightMode() : getBlurStyleDayMode();
    }

    default BlurStyle getBlurStyleDayMode() {
        return BlurStyle.DEFAULT_LIGHT;
    }

    default BlurStyle getBlurStyleNightMode() {
        return BlurStyle.DEFAULT_DARK;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.miui.blur.sdk.backdrop.BlurDrawInfo
    default void getBlurOutline(Outline outline) {
        if (getOutlineProvider() != null && (this instanceof View)) {
            getOutlineProvider().getOutline((View) this, outline);
        } else {
            outline.setRect(0, 0, getWidth(), getHeight());
        }
    }
}
