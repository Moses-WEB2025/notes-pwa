package com.airbnb.lottie.configurations.reducemotion;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public class IgnoreDisabledSystemAnimationsOption implements ReducedMotionOption {
    @Override // com.airbnb.lottie.configurations.reducemotion.ReducedMotionOption
    public ReducedMotionMode getCurrentReducedMotionMode(Context context) {
        return ReducedMotionMode.STANDARD_MOTION;
    }
}
