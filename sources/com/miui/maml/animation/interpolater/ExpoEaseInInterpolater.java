package com.miui.maml.animation.interpolater;

import android.view.animation.Interpolator;

/* JADX INFO: loaded from: classes3.dex */
public class ExpoEaseInInterpolater implements Interpolator {
    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        if (f == 0.0f) {
            return 0.0f;
        }
        return (float) Math.pow(2.0d, (f - 1.0f) * 10.0f);
    }
}
