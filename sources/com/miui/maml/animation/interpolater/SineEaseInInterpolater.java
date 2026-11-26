package com.miui.maml.animation.interpolater;

import android.view.animation.Interpolator;

/* JADX INFO: loaded from: classes3.dex */
public class SineEaseInInterpolater implements Interpolator {
    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return (-((float) Math.cos(((double) f) * 1.5707963267948966d))) + 1.0f;
    }
}
