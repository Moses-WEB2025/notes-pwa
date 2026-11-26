package com.miui.common.view;

import android.view.animation.Interpolator;

/* JADX INFO: loaded from: classes2.dex */
public class PhysicBasedInterpolator implements Interpolator {
    private float c;
    private float c1;
    private float c2;
    private float k;
    private float m;
    private float mInitial;
    private float r;
    private float w;

    private PhysicBasedInterpolator(float f, float f2) {
        this.mInitial = -1.0f;
        this.m = 1.0f;
        this.c1 = -1.0f;
        double d = f2;
        double dPow = Math.pow(6.283185307179586d / d, 2.0d);
        float f3 = this.m;
        float f4 = (float) (dPow * ((double) f3));
        this.k = f4;
        float f5 = (float) (((((double) f) * 12.566370614359172d) * ((double) f3)) / d);
        this.c = f5;
        float fSqrt = (float) Math.sqrt(((f3 * 4.0f) * f4) - (f5 * f5));
        float f6 = this.m;
        float f7 = fSqrt / (f6 * 2.0f);
        this.w = f7;
        float f8 = -((this.c / 2.0f) * f6);
        this.r = f8;
        this.c2 = (0.0f - (f8 * this.mInitial)) / f7;
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return (float) ((Math.pow(2.718281828459045d, this.r * f) * ((((double) this.c1) * Math.cos(this.w * f)) + (((double) this.c2) * Math.sin(this.w * f)))) + 1.0d);
    }

    public static final class Builder {
        private float mDamping = 0.95f;
        private float mResponse = 0.6f;

        public Builder setDamping(float f) {
            this.mDamping = f;
            return this;
        }

        public Builder setResponse(float f) {
            this.mResponse = f;
            return this;
        }

        public PhysicBasedInterpolator build() {
            return new PhysicBasedInterpolator(this.mDamping, this.mResponse);
        }
    }
}
