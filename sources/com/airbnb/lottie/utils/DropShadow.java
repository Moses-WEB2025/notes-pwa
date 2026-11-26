package com.airbnb.lottie.utils;

import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

/* JADX INFO: loaded from: classes.dex */
public class DropShadow {
    private int color;
    private float dx;
    private float dy;
    private float radius;
    private float[] vecs;

    public DropShadow() {
        this.radius = 0.0f;
        this.dx = 0.0f;
        this.dy = 0.0f;
        this.color = 0;
    }

    public DropShadow(float f, float f2, float f3, int i) {
        this.radius = f;
        this.dx = f2;
        this.dy = f3;
        this.color = i;
        this.vecs = null;
    }

    public DropShadow(DropShadow dropShadow) {
        this.radius = 0.0f;
        this.dx = 0.0f;
        this.dy = 0.0f;
        this.color = 0;
        this.radius = dropShadow.radius;
        this.dx = dropShadow.dx;
        this.dy = dropShadow.dy;
        this.color = dropShadow.color;
        this.vecs = null;
    }

    public float getRadius() {
        return this.radius;
    }

    public void setRadius(float f) {
        this.radius = f;
    }

    public float getDx() {
        return this.dx;
    }

    public void setDx(float f) {
        this.dx = f;
    }

    public float getDy() {
        return this.dy;
    }

    public void setDy(float f) {
        this.dy = f;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int i) {
        this.color = i;
    }

    public boolean sameAs(DropShadow dropShadow) {
        return this.radius == dropShadow.radius && this.dx == dropShadow.dx && this.dy == dropShadow.dy && this.color == dropShadow.color;
    }

    public void transformBy(Matrix matrix) {
        if (this.vecs == null) {
            this.vecs = new float[2];
        }
        float[] fArr = this.vecs;
        fArr[0] = this.dx;
        fArr[1] = this.dy;
        matrix.mapVectors(fArr);
        float[] fArr2 = this.vecs;
        this.dx = fArr2[0];
        this.dy = fArr2[1];
        this.radius = matrix.mapRadius(this.radius);
    }

    public void multiplyOpacity(int i) {
        this.color = Color.argb(Math.round((Color.alpha(this.color) * MiscUtils.clamp(i, 0, 255)) / 255.0f), Color.red(this.color), Color.green(this.color), Color.blue(this.color));
    }

    public void applyTo(Paint paint) {
        if (Color.alpha(this.color) > 0) {
            paint.setShadowLayer(Math.max(this.radius, Float.MIN_VALUE), this.dx, this.dy, this.color);
        } else {
            paint.clearShadowLayer();
        }
    }

    public void applyWithAlpha(int i, Paint paint) {
        int iMixOpacities = Utils.mixOpacities(Color.alpha(this.color), MiscUtils.clamp(i, 0, 255));
        if (iMixOpacities > 0) {
            paint.setShadowLayer(Math.max(this.radius, Float.MIN_VALUE), this.dx, this.dy, Color.argb(iMixOpacities, Color.red(this.color), Color.green(this.color), Color.blue(this.color)));
        } else {
            paint.clearShadowLayer();
        }
    }

    public void applyWithAlpha(int i, OffscreenLayer.ComposeOp composeOp) {
        composeOp.shadow = new DropShadow(this);
        composeOp.shadow.multiplyOpacity(i);
    }

    public void applyTo(OffscreenLayer.ComposeOp composeOp) {
        if (Color.alpha(this.color) > 0) {
            composeOp.shadow = this;
        } else {
            composeOp.shadow = null;
        }
    }
}
