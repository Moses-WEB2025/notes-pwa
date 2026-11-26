package com.miui.todo.view.swipemenulayout;

import android.view.animation.Interpolator;

/* JADX INFO: loaded from: classes4.dex */
public class FolmeScrollInterpolator implements Interpolator {
    private float c;
    private float c2;
    private float k;
    private float r;
    private float w;
    private float damping = 0.95f;
    private float response = 0.6f;
    private float initial = -1.0f;
    private float c1 = -1.0f;
    private float m = 1.0f;

    public FolmeScrollInterpolator() {
        updateParameters();
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return (float) ((Math.pow(2.718281828459045d, this.r * f) * ((((double) this.c1) * Math.cos(this.w * f)) + (((double) this.c2) * Math.sin(this.w * f)))) + 1.0d);
    }

    public float getDamping() {
        return this.damping;
    }

    public float getResponse() {
        return this.response;
    }

    public FolmeScrollInterpolator setDamping(float f) {
        this.damping = f;
        updateParameters();
        return this;
    }

    public FolmeScrollInterpolator setResponse(float f) {
        this.response = f;
        updateParameters();
        return this;
    }

    private void updateParameters() {
        double dPow = Math.pow(6.283185307179586d / ((double) this.response), 2.0d);
        float f = this.m;
        float f2 = (float) (dPow * ((double) f));
        this.k = f2;
        float f3 = (float) (((((double) this.damping) * 12.566370614359172d) * ((double) f)) / ((double) this.response));
        this.c = f3;
        float fSqrt = (float) Math.sqrt(((f * 4.0f) * f2) - (f3 * f3));
        float f4 = this.m;
        float f5 = fSqrt / (f4 * 2.0f);
        this.w = f5;
        float f6 = -((this.c / 2.0f) * f4);
        this.r = f6;
        this.c2 = (0.0f - (f6 * this.initial)) / f5;
    }
}
