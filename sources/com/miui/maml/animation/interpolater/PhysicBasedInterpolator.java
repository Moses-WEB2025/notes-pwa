package com.miui.maml.animation.interpolater;

import android.view.animation.Interpolator;
import com.miui.maml.data.Expression;

/* JADX INFO: loaded from: classes3.dex */
public class PhysicBasedInterpolator implements Interpolator {
    private float c;
    private float c1;
    private float c2;
    private float k;
    private float m;
    private float mDamping;
    private Expression mDampingExp;
    private float mInitial;
    private boolean mNeedEvaluate;
    private float mResponse;
    private Expression mResponseExp;
    private float r;
    private float w;

    public PhysicBasedInterpolator() {
        this.mDamping = 0.9f;
        this.mResponse = 0.3f;
        this.mInitial = -1.0f;
        this.m = 1.0f;
        this.c1 = -1.0f;
        this.mNeedEvaluate = true;
    }

    public PhysicBasedInterpolator(Expression[] expressionArr) {
        this.mDamping = 0.9f;
        this.mResponse = 0.3f;
        this.mInitial = -1.0f;
        this.m = 1.0f;
        this.c1 = -1.0f;
        this.mNeedEvaluate = true;
        if (expressionArr != null) {
            if (expressionArr.length > 0) {
                this.mDampingExp = expressionArr[0];
            }
            if (expressionArr.length > 1) {
                this.mResponseExp = expressionArr[1];
            }
        }
    }

    public PhysicBasedInterpolator(float f, float f2) {
        this.mInitial = -1.0f;
        this.m = 1.0f;
        this.c1 = -1.0f;
        this.mNeedEvaluate = true;
        this.mDamping = f;
        this.mResponse = f2;
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        Expression expression = this.mDampingExp;
        if (expression != null) {
            float fEvaluate = (float) expression.evaluate();
            if (this.mDamping != fEvaluate) {
                this.mDamping = fEvaluate;
                this.mNeedEvaluate = true;
            }
        }
        Expression expression2 = this.mResponseExp;
        if (expression2 != null) {
            float fEvaluate2 = (float) expression2.evaluate();
            if (this.mResponse != fEvaluate2) {
                this.mResponse = fEvaluate2;
                this.mNeedEvaluate = true;
            }
        }
        evaluate();
        return (float) ((Math.pow(2.718281828459045d, this.r * f) * ((((double) this.c1) * Math.cos(this.w * f)) + (((double) this.c2) * Math.sin(this.w * f)))) + 1.0d);
    }

    private void evaluate() {
        if (this.mNeedEvaluate) {
            double dPow = Math.pow(6.283185307179586d / ((double) this.mResponse), 2.0d);
            float f = this.m;
            float f2 = (float) (dPow * ((double) f));
            this.k = f2;
            float f3 = (float) (((((double) this.mDamping) * 12.566370614359172d) * ((double) f)) / ((double) this.mResponse));
            this.c = f3;
            float fSqrt = (float) Math.sqrt(((f * 4.0f) * f2) - (f3 * f3));
            float f4 = this.m;
            float f5 = fSqrt / (f4 * 2.0f);
            this.w = f5;
            float f6 = -((this.c / 2.0f) * f4);
            this.r = f6;
            this.c2 = (0.0f - (f6 * this.mInitial)) / f5;
            this.mNeedEvaluate = false;
        }
    }
}
