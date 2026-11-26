package com.miui.note.algorithm.utils;

import com.miui.note.algorithm.common.Point;
import com.miui.note.algorithm.service.AngleService;
import com.miui.note.algorithm.service.GraphService;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class GeometryUtil {
    public static ArrayList<Point> extractKeyPoint(List<Point> list) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(list.get(0));
        ArrayList arrayList2 = new ArrayList();
        for (Point point : list) {
            arrayList2.add(point);
            if (arrayList2.size() > 1 && (distance((Point) arrayList2.get(0), (Point) arrayList2.get(arrayList2.size() - 1)) > 10.0d || !isStraightLine2(arrayList2, 0.99d))) {
                arrayList.add(point);
                arrayList2.clear();
            }
        }
        arrayList.add(list.get(list.size() - 1));
        ArrayList<Point> arrayListKeyPointLengthFilter = keyPointLengthFilter(keyPointLengthFilter(keyPointAngleFilter(keyPointAngleFilter(arrayList))));
        if (arrayListKeyPointLengthFilter.size() > 3) {
            ArrayList arrayList3 = new ArrayList();
            if (GraphService.isSameWise(arrayListKeyPointLengthFilter.get(arrayListKeyPointLengthFilter.size() - 1), arrayListKeyPointLengthFilter.get(arrayListKeyPointLengthFilter.size() - 2), arrayListKeyPointLengthFilter.get(0))) {
                arrayList3.add(arrayListKeyPointLengthFilter.get(arrayListKeyPointLengthFilter.size() - 2));
                arrayList3.add(arrayListKeyPointLengthFilter.get(0));
                arrayList3.add(arrayListKeyPointLengthFilter.get(arrayListKeyPointLengthFilter.size() - 1));
                arrayList3.add(arrayListKeyPointLengthFilter.get(1));
            } else {
                arrayList3.add(arrayListKeyPointLengthFilter.get(arrayListKeyPointLengthFilter.size() - 2));
                arrayList3.add(arrayListKeyPointLengthFilter.get(arrayListKeyPointLengthFilter.size() - 1));
                arrayList3.add(arrayListKeyPointLengthFilter.get(0));
                arrayList3.add(arrayListKeyPointLengthFilter.get(1));
            }
            if (isStraightLine2(arrayList3, 0.95d)) {
                arrayListKeyPointLengthFilter.remove(0);
                arrayListKeyPointLengthFilter.remove(arrayListKeyPointLengthFilter.size() - 1);
                arrayListKeyPointLengthFilter.add(arrayListKeyPointLengthFilter.get(0));
            }
        }
        return arrayListKeyPointLengthFilter;
    }

    public static ArrayList<Point> keyPointAngleFilter(ArrayList<Point> arrayList) {
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
            double dDistance = distance(arrayList2.get(arrayList2.size() - 1), arrayList.get(i3));
            int i4 = i3 + 1;
            double dDistance2 = distance(arrayList.get(i3), arrayList.get(i4));
            double dDistance3 = distance(arrayList.get(i4), arrayList.get(i3 + 2));
            if ((arrayList.size() <= 6 || dDistance / dDistance2 <= 5.0d || dDistance3 / dDistance2 <= 5.0d) && (arrayList.size() > 6 || (dDistance / dDistance2 <= 5.0d && dDistance3 / dDistance2 <= 5.0d))) {
                arrayList2.add(arrayList.get(i3));
            }
            i3 = i4;
        }
        arrayList2.add(arrayList.get(arrayList.size() - 2));
        arrayList2.add(arrayList.get(arrayList.size() - 1));
        if (arrayList2.size() >= 5) {
            double dDistance4 = distance(arrayList2.get(0), arrayList2.get(1));
            double dDistance5 = distance(arrayList2.get(1), arrayList2.get(2));
            double dDistance6 = distance(arrayList2.get(arrayList2.size() - 1), arrayList2.get(arrayList2.size() - 2));
            double dDistance7 = distance(arrayList2.get(arrayList2.size() - 2), arrayList2.get(arrayList2.size() - 3));
            if (dDistance5 / dDistance4 > 5.0d || dDistance6 / dDistance4 > 5.0d) {
                arrayList2.remove(0);
            }
            if (dDistance4 / dDistance6 > 5.0d || dDistance7 / dDistance6 > 5.0d) {
                arrayList2.remove(arrayList2.size() - 1);
            }
        }
        return arrayList2;
    }

    public static double distance(Point point, Point point2) {
        return Math.hypot(point.getX() - point2.getX(), point.getY() - point2.getY());
    }

    public static double curveLength(List<Point> list) {
        double dHypot = 0.0d;
        Point point = null;
        for (Point point2 : list) {
            if (point != null) {
                dHypot += Math.hypot(point2.getX() - point.getX(), point2.getY() - point.getY());
            }
            point = point2;
        }
        return dHypot;
    }

    public static boolean isStraightLine1(ArrayList<Point> arrayList) {
        Point point = arrayList.get(0);
        Point point2 = arrayList.get(arrayList.size() - 1);
        double y = (point2.getY() - point.getY()) / (point2.getX() - point.getX());
        double y2 = ((double) point.getY()) - (((double) point.getX()) * y);
        double dHypot = Math.hypot(point.getX() - point2.getX(), point.getY() - point2.getY()) / ((double) 50);
        for (Point point3 : arrayList) {
            if (Math.abs(((((double) point3.getX()) * y) + (((double) point3.getY()) * (-1.0d))) + y2) / Math.hypot(y, -1.0d) > dHypot) {
                return false;
            }
        }
        return true;
    }

    public static boolean isStraightLine2(List<Point> list, double d) {
        Point point = list.get(0);
        Point point2 = list.get(list.size() - 1);
        return Math.hypot((double) (point.getX() - point2.getX()), (double) (point.getY() - point2.getY())) / curveLength(list) > d;
    }

    public static float[] leastSquare(List<Point> list) {
        int size = list.size();
        float[] fArr = new float[size];
        float[] fArr2 = new float[size];
        for (int i = 0; i < size; i++) {
            fArr[i] = list.get(i).getX();
            fArr2[i] = list.get(i).getY();
        }
        float fDotProduct = dotProduct(fArr);
        float fDotProduct2 = dotProduct(fArr2);
        float fDotProduct3 = dotProduct(fArr, fArr);
        float fDotProduct4 = dotProduct(fArr, fArr2);
        float f = size;
        float f2 = (f * fDotProduct4) - (fDotProduct * fDotProduct2);
        float f3 = (f * fDotProduct3) - (fDotProduct * fDotProduct);
        return new float[]{f2 / f3, ((fDotProduct2 * fDotProduct3) - (fDotProduct * fDotProduct4)) / f3};
    }

    public static float dotProduct(float[]... fArr) {
        int length = fArr.length;
        int length2 = fArr[0].length;
        float f = 0.0f;
        for (int i = 0; i < length2; i++) {
            if (length == 1) {
                f += fArr[0][i];
            } else if (length == 2) {
                f += fArr[0][i] * fArr[1][i];
            }
        }
        return f;
    }

    public static Point getFootOfPerpendicular(float f, float f2, float f3, float f4) {
        float f5 = f3 * f3;
        if ((f4 * f4) + f5 < 1.0E-5f) {
            return new Point(f, 0.0f);
        }
        float f6 = f3 * f;
        if (Math.abs((f6 + f4) - f2) < 1.0E-5f) {
            return new Point(f, f2);
        }
        float f7 = f2 * f3;
        float f8 = f5 + 1.0f;
        return new Point(((f + f7) - (f3 * f4)) / f8, ((f6 + (f7 * f3)) + f4) / f8);
    }

    public static Point getIntersectPoint(Point point, Point point2, Point point3, Point point4) {
        float y = point.getY() - point2.getY();
        float x = point2.getX() - point.getX();
        float x2 = (point.getX() * y) + (point.getY() * x);
        float y2 = point3.getY() - point4.getY();
        float x3 = point4.getX() - point3.getX();
        float x4 = (point3.getX() * y2) + (point3.getY() * x3);
        float f = (y * x3) - (y2 * x);
        if (Math.abs(f) < 1.0E-5d) {
            return null;
        }
        return new Point(((x3 / f) * x2) + (((x * (-1.0f)) / f) * x4), (((y2 * (-1.0f)) / f) * x2) + ((y / f) * x4));
    }

    public static double[] getCentroid2D(double[][] dArr) {
        int length = dArr.length;
        double d = 0.0d;
        double d2 = 0.0d;
        for (double[] dArr2 : dArr) {
            d += dArr2[0];
            d2 += dArr2[1];
        }
        double d3 = length;
        return new double[]{d / d3, d2 / d3};
    }

    public static Point getCenterPoint(List<Point> list) {
        double x = 0.0d;
        double y = 0.0d;
        for (Point point : list) {
            x += (double) point.getX();
            y += (double) point.getY();
        }
        return new Point(((float) x) / list.size(), ((float) y) / list.size());
    }

    public static float getRoateAngle(Point point, Point point2, Point point3) {
        double dAtan2 = Math.atan2(point.getX() - point2.getX(), point.getY() - point2.getY()) - Math.atan2(point3.getX() - point2.getX(), point3.getY() - point2.getY());
        if (dAtan2 > 3.141592653589793d) {
            dAtan2 -= 6.283185307179586d;
        }
        if (dAtan2 < -3.141592653589793d) {
            dAtan2 += 6.283185307179586d;
        }
        return (float) ((dAtan2 / 3.141592653589793d) * 180.0d);
    }

    public static void main(String[] strArr) {
        float[] fArr = {1.0f, 2.0f, 3.0f};
        System.out.println(dotProduct(fArr, fArr));
    }
}
