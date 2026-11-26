package com.miui.maml.animation.interpolater;

import android.view.animation.Interpolator;

/* JADX INFO: loaded from: classes3.dex */
public class QuintEaseInInterpolater implements Interpolator {
    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return f * f * f * f * f;
    }
}
