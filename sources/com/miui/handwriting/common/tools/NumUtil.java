package com.miui.handwriting.common.tools;

import android.text.TextPaint;

/* JADX INFO: loaded from: classes3.dex */
public class NumUtil {
    public static int parseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static float parseFloat(String str) {
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0.0f;
        }
    }

    public static float parseTextSize(String str, float f) {
        return checkTextWidth(str, f);
    }

    private static float checkTextWidth(String str, float f) {
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(34.0f);
        float fMeasureText = (f * 34.0f) / textPaint.measureText(str);
        textPaint.setTextSize(fMeasureText);
        return fMeasureText;
    }
}
