package com.miui.notes.tool.util;

import android.graphics.Color;

/* JADX INFO: loaded from: classes4.dex */
public class ColorMatrixHelper {
    public static float[] colorToMatrix(int i) {
        return new float[]{0.0f, 0.0f, 0.0f, 0.0f, Color.red(i), 0.0f, 0.0f, 0.0f, 0.0f, Color.green(i), 0.0f, 0.0f, 0.0f, 0.0f, Color.blue(i), 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    }
}
