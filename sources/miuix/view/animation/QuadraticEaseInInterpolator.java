package miuix.view.animation;

import android.view.animation.Interpolator;

/* JADX INFO: loaded from: classes6.dex */
public class QuadraticEaseInInterpolator implements Interpolator {
    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return f * f;
    }
}
