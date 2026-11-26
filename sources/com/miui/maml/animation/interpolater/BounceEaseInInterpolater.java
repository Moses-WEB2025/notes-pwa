package com.miui.maml.animation.interpolater;

import android.view.animation.Interpolator;

/* JADX INFO: loaded from: classes3.dex */
public class BounceEaseInInterpolater implements Interpolator {
    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return getInterpolationImp(f);
    }

    public static float getInterpolationImp(float f) {
        return 1.0f - BounceEaseOutInterpolater.getInterpolationImp(1.0f - f);
    }
}
