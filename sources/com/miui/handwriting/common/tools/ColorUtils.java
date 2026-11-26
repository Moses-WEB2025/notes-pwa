package com.miui.handwriting.common.tools;

import android.graphics.Color;

/* JADX INFO: loaded from: classes3.dex */
public class ColorUtils {
    public static int calculateARGBColor(int i, int i2) {
        return (i << 24) | (i2 & 16777215);
    }

    public static int calculateHueColor(float f) {
        return Color.HSVToColor(new float[]{f, 1.0f, 1.0f});
    }

    public static int calculateHSLColor(float f, float f2, float f3) {
        return Color.HSVToColor(HSLToHSV(f, f2, f3));
    }

    public static float[] HSLToHSV(float f, float f2, float f3) {
        float[] fArr = new float[3];
        fArr[0] = f;
        float fMin = (f2 * Math.min(f3, 1.0f - f3)) + f3;
        fArr[2] = fMin;
        fArr[1] = fMin != 0.0f ? (1.0f - (f3 / fMin)) * 2.0f : 0.0f;
        return fArr;
    }

    public static int calculateRGBColor(int i) {
        return calculateARGBColor(255, i);
    }

    public static int getClosestColor(int i, int[] iArr) {
        int i2 = Integer.MAX_VALUE;
        int i3 = i;
        for (int i4 : iArr) {
            int iAbs = Math.abs(i - i4);
            if (iAbs < i2) {
                i3 = i4;
                i2 = iAbs;
            }
        }
        return i3;
    }

    private static float[] HSVToHSL(float[] fArr) {
        float f = 2.0f - fArr[1];
        float f2 = fArr[2];
        float f3 = (f * f2) / 2.0f;
        float fMin = 0.0f;
        if (f3 != 0.0f && f3 != 1.0f) {
            fMin = (f2 - f3) / Math.min(f3, 1.0f - f3);
        }
        return new float[]{fArr[0], fMin, f3};
    }

    public static float[] caculateOriginalHSL(int i) {
        Color.alpha(i);
        float[] fArr = new float[3];
        Color.RGBToHSV(Color.red(i), Color.green(i), Color.blue(i), fArr);
        return HSVToHSL(fArr);
    }

    public static float caculateOriginalHue(int i) {
        Color.alpha(i);
        float[] fArr = new float[3];
        Color.RGBToHSV(Color.red(i), Color.green(i), Color.blue(i), fArr);
        return fArr[0];
    }
}
