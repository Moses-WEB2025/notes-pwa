package com.miui.note.algorithm.service;

import com.miui.note.algorithm.common.Graph;
import com.miui.note.algorithm.common.GraphType;
import com.miui.note.algorithm.common.Point;
import com.miui.note.algorithm.utils.ClosedGraphUtil;
import com.miui.note.algorithm.utils.GeometryUtil;
import com.miui.note.algorithm.utils.UnClosedGraphUtil;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class GraphService {
    public static ArrayList<Point> isConic(List<Point> list) {
        ArrayList<Point> arrayListExtractKeyPoint = UnClosedGraphUtil.extractKeyPoint(list);
        if (arrayListExtractKeyPoint.size() >= 4) {
            int i = 0;
            Point point = list.get(0);
            Point point2 = list.get(list.size() - 1);
            double y = (point2.getY() - point.getY()) / (point2.getX() - point.getX());
            double y2 = ((double) point.getY()) - (((double) point.getX()) * y);
            for (Point point3 : arrayListExtractKeyPoint) {
                double x = (((double) point3.getX()) * y) + ((-1.0d) * ((double) point3.getY())) + y2;
                if (Math.abs(x) >= 1.0d) {
                    if (i == 0) {
                        i = (int) x;
                    } else if (((double) i) * x < 0.0d) {
                        return null;
                    }
                }
            }
        }
        return arrayListExtractKeyPoint;
    }

    public static ArrayList<Point> is90brokenLine(List<Point> list) {
        ArrayList<Point> arrayListKeyPointAngleFilter = UnClosedGraphUtil.keyPointAngleFilter(UnClosedGraphUtil.extractKeyPoint(list));
        if (arrayListKeyPointAngleFilter.size() < 3) {
            return null;
        }
        Point point = null;
        Point point2 = null;
        Point point3 = null;
        for (Point point4 : arrayListKeyPointAngleFilter) {
            if (point == null) {
                point = point4;
            } else if (point2 == null) {
                point2 = point4;
            } else {
                if (point3 != null) {
                    point = point2;
                    point2 = point3;
                }
                if (!AngleService.isRightAngle(AngleService.angle(point, point2, point4))) {
                    return null;
                }
                point3 = point4;
            }
        }
        return arrayListKeyPointAngleFilter;
    }

    public static boolean isCloseCurve(List<Point> list) {
        Point point = list.get(0);
        Point point2 = list.get(list.size() - 1);
        return GeometryUtil.curveLength(list) / Math.hypot((double) (point.getX() - point2.getX()), (double) (point.getY() - point2.getY())) > 5.0d;
    }

    public static boolean isSameWise(Point point, Point point2, Point point3) {
        return ((point2.getX() - point.getX()) * (point3.getX() - point.getX())) + ((point2.getY() - point.getY()) * (point3.getY() - point.getY())) > 0.0f;
    }

    public static boolean nearlySatisfy(ArrayList<Point> arrayList, double d, double d2, int i) {
        int i2 = 0;
        int i3 = 0;
        while (i2 < arrayList.size() - 2) {
            int i4 = i2 + 1;
            if (AngleService.approximate(AngleService.angle(arrayList.get(i2), arrayList.get(i4), arrayList.get(i2 + 2)), d, d2)) {
                i3++;
            }
            i2 = i4;
        }
        return i3 >= i;
    }

    public static Graph isPolygon(List<Point> list) {
        ArrayList<Point> arrayListExtractKeyPoint = ClosedGraphUtil.extractKeyPoint(list);
        if (arrayListExtractKeyPoint.size() > 999999) {
            return null;
        }
        arrayListExtractKeyPoint.remove(arrayListExtractKeyPoint.size() - 1);
        int i = 0;
        arrayListExtractKeyPoint.add(arrayListExtractKeyPoint.get(0));
        if (arrayListExtractKeyPoint.size() > 6) {
            if (arrayListExtractKeyPoint.size() == 11 && ((AngleService.approximate30(AngleService.angle(arrayListExtractKeyPoint.get(1), arrayListExtractKeyPoint.get(2), arrayListExtractKeyPoint.get(3))) && AngleService.approximate30(AngleService.angle(arrayListExtractKeyPoint.get(3), arrayListExtractKeyPoint.get(4), arrayListExtractKeyPoint.get(5))) && AngleService.approximate30(AngleService.angle(arrayListExtractKeyPoint.get(5), arrayListExtractKeyPoint.get(6), arrayListExtractKeyPoint.get(7))) && AngleService.approximate30(AngleService.angle(arrayListExtractKeyPoint.get(7), arrayListExtractKeyPoint.get(8), arrayListExtractKeyPoint.get(9))) && AngleService.approximate30(AngleService.angle(arrayListExtractKeyPoint.get(9), arrayListExtractKeyPoint.get(0), arrayListExtractKeyPoint.get(1)))) || (AngleService.approximate30(AngleService.angle(arrayListExtractKeyPoint.get(0), arrayListExtractKeyPoint.get(1), arrayListExtractKeyPoint.get(2))) && AngleService.approximate30(AngleService.angle(arrayListExtractKeyPoint.get(2), arrayListExtractKeyPoint.get(3), arrayListExtractKeyPoint.get(4))) && AngleService.approximate30(AngleService.angle(arrayListExtractKeyPoint.get(4), arrayListExtractKeyPoint.get(5), arrayListExtractKeyPoint.get(6))) && AngleService.approximate30(AngleService.angle(arrayListExtractKeyPoint.get(6), arrayListExtractKeyPoint.get(7), arrayListExtractKeyPoint.get(8))) && AngleService.approximate30(AngleService.angle(arrayListExtractKeyPoint.get(8), arrayListExtractKeyPoint.get(9), arrayListExtractKeyPoint.get(0)))))) {
                return new Graph(GraphType.PENTAGRAM, arrayListExtractKeyPoint);
            }
            double x = 0.0d;
            double y = 0.0d;
            while (i < arrayListExtractKeyPoint.size() - 4) {
                Point point = arrayListExtractKeyPoint.get(i);
                x += (double) point.getX();
                y += (double) point.getY();
                int i2 = i + 1;
                if (AngleService.angle(point, arrayListExtractKeyPoint.get(i2), arrayListExtractKeyPoint.get(i + 2)) < 60.0d) {
                    return new Graph(GraphType.OTHER, arrayListExtractKeyPoint);
                }
                i = i2;
            }
            for (int size = arrayListExtractKeyPoint.size() - 4; size < arrayListExtractKeyPoint.size() - 1; size++) {
                Point point2 = arrayListExtractKeyPoint.get(size);
                x += (double) point2.getX();
                y += (double) point2.getY();
            }
            double size2 = x / ((double) (arrayListExtractKeyPoint.size() - 1));
            double size3 = y / ((double) (arrayListExtractKeyPoint.size() - 1));
            float f = 0.0f;
            float f2 = 2.1474836E9f;
            for (Point point3 : arrayListExtractKeyPoint) {
                double dHypot = Math.hypot(((double) point3.getX()) - size2, ((double) point3.getY()) - size3);
                if (dHypot > f) {
                    f = (float) dHypot;
                }
                if (dHypot < f2) {
                    f2 = (float) dHypot;
                }
            }
            if (f2 / f < 0.75d) {
                return new Graph(GraphType.ELLIPSE, arrayListExtractKeyPoint);
            }
            return new Graph(GraphType.CIRCLE, arrayListExtractKeyPoint);
        }
        if (arrayListExtractKeyPoint.size() == 4) {
            return new Graph(GraphType.TRIANGLE, arrayListExtractKeyPoint);
        }
        if (arrayListExtractKeyPoint.size() == 5 && nearlySatisfy(arrayListExtractKeyPoint, 90.0d, 30.0d, 3)) {
            double dHypot2 = Math.hypot(arrayListExtractKeyPoint.get(0).getX() - arrayListExtractKeyPoint.get(1).getX(), arrayListExtractKeyPoint.get(0).getY() - arrayListExtractKeyPoint.get(1).getY());
            double dHypot3 = Math.hypot(arrayListExtractKeyPoint.get(1).getX() - arrayListExtractKeyPoint.get(2).getX(), arrayListExtractKeyPoint.get(1).getY() - arrayListExtractKeyPoint.get(2).getY());
            if (dHypot2 > dHypot3) {
                dHypot3 = dHypot2;
                dHypot2 = dHypot3;
            }
            if (dHypot2 / dHypot3 < 0.8d) {
                return new Graph(GraphType.RECTANGLE, arrayListExtractKeyPoint);
            }
            return new Graph(GraphType.SQUARE, arrayListExtractKeyPoint);
        }
        if (arrayListExtractKeyPoint.size() == 6 && nearlySatisfy(arrayListExtractKeyPoint, 108.0d, 50.0d, 4)) {
            return new Graph(GraphType.PENTAGON, arrayListExtractKeyPoint);
        }
        if (arrayListExtractKeyPoint.size() == 6 && nearlySatisfy(arrayListExtractKeyPoint, 30.0d, 20.0d, 2)) {
            return new Graph(GraphType.PENTAGRAM, arrayListExtractKeyPoint);
        }
        return new Graph(GraphType.OTHER, arrayListExtractKeyPoint);
    }
}
