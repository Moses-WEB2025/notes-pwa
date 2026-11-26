package com.miui.maml.animation.interpolater;

import android.view.animation.Interpolator;

/* JADX INFO: loaded from: classes3.dex */
public class QuintEaseOutInterpolater implements Interpolator {
    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        float f2 = f - 1.0f;
        return (f2 * f2 * f2 * f2 * f2) + 1.0f;
    }
}
