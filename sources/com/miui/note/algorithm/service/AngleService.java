package com.miui.note.algorithm.service;

import com.miui.note.algorithm.common.Point;

/* JADX INFO: loaded from: classes3.dex */
public class AngleService {
    public static double angle(Point point, Point point2, Point point3) {
        double dAtan2 = Math.atan2(point.getX() - point2.getX(), point.getY() - point2.getY()) - Math.atan2(point3.getX() - point2.getX(), point3.getY() - point2.getY());
        if (dAtan2 > 3.141592653589793d) {
            dAtan2 -= 6.283185307179586d;
        }
        if (dAtan2 < -3.141592653589793d) {
            dAtan2 += 6.283185307179586d;
        }
        return Math.abs((dAtan2 * 180.0d) / 3.141592653589793d);
    }

    public static boolean isRightAngle(double d) {
        return Math.abs(d - 90.0d) < 30.0d;
    }

    public static boolean approximate108(double d) {
        return Math.abs(d - 108.0d) < 50.0d;
    }

    public static boolean approximate30(double d) {
        return Math.abs(d - 30.0d) < 20.0d;
    }

    public static boolean approximate120(double d) {
        return Math.abs(d - 120.0d) < 30.0d;
    }

    public static boolean approximate(double d, double d2, double d3) {
        return Math.abs(d - d2) < d3;
    }
}
