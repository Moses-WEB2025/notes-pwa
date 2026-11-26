package com.miui.note.algorithm.utils.mathutils;

/* JADX INFO: loaded from: classes3.dex */
public class Maths {
    public static double hypot(double d, double d2) {
        if (Math.abs(d) > Math.abs(d2)) {
            double d3 = d2 / d;
            return Math.abs(d) * Math.sqrt((d3 * d3) + 1.0d);
        }
        if (d2 == 0.0d) {
            return 0.0d;
        }
        double d4 = d / d2;
        return Math.abs(d2) * Math.sqrt((d4 * d4) + 1.0d);
    }
}
