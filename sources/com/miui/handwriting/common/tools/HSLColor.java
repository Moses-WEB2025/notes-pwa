package com.miui.handwriting.common.tools;

import android.graphics.Color;
import ch.qos.logback.core.CoreConstants;

/* JADX INFO: loaded from: classes3.dex */
public class HSLColor {
    public int alpha;
    public int color;
    public float hue;
    public float lightness;
    public float saturation;

    public HSLColor(float f, float f2, float f3) {
        this.hue = f;
        this.saturation = f2;
        this.lightness = f3;
        this.alpha = 255;
        this.color = ColorUtils.calculateARGBColor(255, ColorUtils.calculateHSLColor(f, f2, f3));
    }

    public HSLColor(int i) {
        this.alpha = 255;
        this.color = i;
        float[] fArrCaculateOriginalHSL = ColorUtils.caculateOriginalHSL(i);
        this.hue = fArrCaculateOriginalHSL[0];
        this.saturation = fArrCaculateOriginalHSL[1];
        this.lightness = fArrCaculateOriginalHSL[2];
        this.alpha = Color.alpha(i);
    }

    public float getHue() {
        return this.hue;
    }

    public void setHue(float f) {
        this.hue = f;
    }

    public float getSaturation() {
        return this.saturation;
    }

    public void setSaturation(float f) {
        this.saturation = f;
    }

    public float getLightness() {
        return this.lightness;
    }

    public void setLightness(float f) {
        this.lightness = f;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int i) {
        this.color = i;
    }

    public int getAlpha() {
        return this.alpha;
    }

    public void setAlpha(int i) {
        this.alpha = i;
        this.color = ColorUtils.calculateARGBColor(i, this.color);
    }

    public String toString() {
        return "HSLColor{hue=" + this.hue + ", saturation=" + this.saturation + ", lightness=" + this.lightness + ", alpha=" + this.alpha + ", color=" + this.color + CoreConstants.CURLY_RIGHT;
    }
}
