package com.miui.blur.sdk.backdrop;

import android.graphics.Outline;
import android.view.ViewRootImpl;

/* JADX INFO: loaded from: classes2.dex */
public interface BlurDrawInfo {
    public static final int DEFAULT_SAMPLING_PERIOD_NS = 20000000;

    void getBlurOutline(Outline outline);

    ViewRootImpl getBlurViewRootImpl();

    int getHeight();

    void getLocationOnScreen(int[] iArr);

    default int getRequestedSamplingPeriodNs() {
        return 20000000;
    }

    int getWidth();

    void postInvalidateOnAnimation();

    default BlurStyle getBlurStyle() {
        return BlurStyle.DEFAULT_LIGHT;
    }
}
