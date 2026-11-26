package com.miui.note.algorithm.utils;

import com.miui.note.algorithm.common.Point;
import com.miui.note.algorithm.service.AngleService;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class UnClosedGraphUtil {
    public static ArrayList<Point> extractKeyPoint(List<Point> list) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(list.get(0));
        ArrayList arrayList2 = new ArrayList();
        for (Point point : list) {
            arrayList2.add(point);
            if (arrayList2.size() > 1 && !GeometryUtil.isStraightLine2(arrayList2, 0.99d)) {
                arrayList.add(point);
                arrayList2.clear();
            }
        }
        arrayList.add(list.get(list.size() - 1));
        return keyPointLengthFilter(keyPointAngleFilter(arrayList));
    }

    public static ArrayList<Point> keyPointAngleFilter(ArrayList<Point> arrayList) {
        if (arrayList.size() < 3) {
            return arrayList;
        }
        ArrayList<Point> arrayList2 = new ArrayList<>();
        arrayList2.add(arrayList.get(0));
        Point point = null;
        Point point2 = null;
        Point point3 = null;
        for (Point point4 : arrayList) {
            if (point == null) {
                point = point4;
            } else if (point2 == null && point3 == null) {
                point2 = point4;
            } else {
                if (point3 != null) {
                    if (point2 != null) {
                        point = point2;
                    }
                    point2 = point3;
                }
                double dAngle = AngleService.angle(point, point2, point4);
                double dHypot = Math.hypot(point2.getX() - point4.getX(), point2.getY() - point4.getY());
                if (dAngle > 160.0d || dHypot < 15.0d) {
                    point2 = null;
                } else {
                    arrayList2.add(point2);
                }
                point3 = point4;
            }
        }
        arrayList2.add(arrayList.get(arrayList.size() - 1));
        return arrayList2;
    }

    public static ArrayList<Point> keyPointLengthFilter(ArrayList<Point> arrayList) {
        char c = 3;
        if (arrayList.size() < 3) {
            return arrayList;
        }
        ArrayList<Point> arrayList2 = new ArrayList<>();
        arrayList2.add(arrayList.get(0));
        boolean z = false;
        int i = 1;
        while (i < arrayList.size() - 1) {
            int i2 = i + 1;
            if (!AngleService.isRightAngle(AngleService.angle(arrayList2.get(arrayList2.size() - 1), arrayList.get(i), arrayList.get(i2)))) {
                arrayList2.clear();
                z = false;
                break;
            }
            arrayList2.add(arrayList.get(i));
            z = true;
            i = i2;
        }
        if (z) {
            arrayList2.add(arrayList.get(arrayList.size() - 1));
            return arrayList2;
        }
        arrayList2.add(arrayList.get(0));
        int i3 = 1;
        while (i3 < arrayList.size() - 2) {
            double dDistance = GeometryUtil.distance(arrayList2.get(arrayList2.size() - 1), arrayList.get(i3));
            int i4 = i3 + 1;
            double dDistance2 = GeometryUtil.distance(arrayList.get(i3), arrayList.get(i4));
            double dDistance3 = GeometryUtil.distance(arrayList.get(i4), arrayList.get(i3 + 2));
            char c2 = c;
            if ((arrayList.size() <= 6 || dDistance / dDistance2 <= 5.0d || dDistance3 / dDistance2 <= 5.0d) && (arrayList.size() > 6 || (dDistance / dDistance2 <= 5.0d && dDistance3 / dDistance2 <= 5.0d))) {
                arrayList2.add(arrayList.get(i3));
            }
            i3 = i4;
            c = c2;
        }
        arrayList2.add(arrayList.get(arrayList.size() - 2));
        arrayList2.add(arrayList.get(arrayList.size() - 1));
        if (arrayList2.size() >= 5) {
            double dDistance4 = GeometryUtil.distance(arrayList2.get(0), arrayList2.get(1));
            double dDistance5 = GeometryUtil.distance(arrayList2.get(1), arrayList2.get(2));
            double dDistance6 = GeometryUtil.distance(arrayList2.get(arrayList2.size() - 1), arrayList2.get(arrayList2.size() - 2));
            double dDistance7 = GeometryUtil.distance(arrayList2.get(arrayList2.size() - 2), arrayList2.get(arrayList2.size() - 3));
            if (dDistance5 / dDistance4 > 5.0d || dDistance6 / dDistance4 > 5.0d) {
                arrayList2.remove(0);
            }
            if (dDistance4 / dDistance6 > 5.0d || dDistance7 / dDistance6 > 5.0d) {
                arrayList2.remove(arrayList2.size() - 1);
            }
        }
        return arrayList2;
    }
}
