package com.miui.note.algorithm.utils.mathutils;

import com.miui.note.algorithm.common.Point;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class Bezier {
    public static float one_bezier_curve(float f, float f2, float f3) {
        return ((1.0f - f3) * f) + (f3 * f2);
    }

    public static ArrayList<Point> direct_bezier(List<Point> list, int i) {
        int size = list.size();
        int i2 = size - 1;
        int[] iArr = new int[size];
        long j = 1;
        int i3 = 0;
        while (i3 <= i2) {
            iArr[i3] = (int) j;
            long j2 = j * ((long) (i2 - i3));
            i3++;
            j = j2 / ((long) i3);
        }
        ArrayList<Point> arrayList = new ArrayList<>();
        for (int i4 = 0; i4 < i; i4++) {
            float f = (1.0f / (i - 1)) * i4;
            float x = 0.0f;
            float y = 0.0f;
            int i5 = 0;
            while (i5 <= i2) {
                float fPow = (float) Math.pow(1.0f - f, i2 - i5);
                float fPow2 = (float) Math.pow(f, i5);
                x += list.get(i5).getX() * fPow * fPow2 * iArr[i5];
                y += fPow * list.get(i5).getY() * fPow2 * iArr[i5];
                i5++;
                f = f;
            }
            arrayList.add(new Point(x, y));
        }
        return arrayList;
    }

    public static ArrayList<Point> recursion_bezier(float[] fArr, float[] fArr2, int i) {
        int length = fArr.length - 1;
        double d = 1.0d / ((double) (i - 1));
        int i2 = (int) ((1.0d + d) / d);
        float[] fArr3 = new float[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            fArr3[i3] = (((float) d) * i3) + 0.0f;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i4 = 0; i4 < i2; i4++) {
            float f = fArr3[i4];
            arrayList.add(Float.valueOf(n_bezier_curve(fArr, length, 0, f)));
            arrayList2.add(Float.valueOf(n_bezier_curve(fArr2, length, 0, f)));
        }
        ArrayList<Point> arrayList3 = new ArrayList<>();
        for (int i5 = 0; i5 < arrayList.size(); i5++) {
            arrayList3.add(new Point(((Float) arrayList.get(i5)).floatValue(), ((Float) arrayList2.get(i5)).floatValue()));
        }
        return arrayList3;
    }

    public static float n_bezier_curve(float[] fArr, int i, int i2, float f) {
        if (i == 1) {
            return one_bezier_curve(fArr[i2], fArr[i2 + 1], f);
        }
        int i3 = i - 1;
        return ((1.0f - f) * n_bezier_curve(fArr, i3, i2, f)) + (f * n_bezier_curve(fArr, i3, i2 + 1, f));
    }

    public static void main(String[] strArr) {
        recursion_bezier(new float[]{0.0f, 2.0f, 5.0f, 10.0f, 15.0f, 20.0f, 18.0f, 14.0f}, new float[]{0.0f, 6.0f, 10.0f, 15.0f, 12.0f, 8.0f, 6.0f, 3.0f}, 100);
    }
}
