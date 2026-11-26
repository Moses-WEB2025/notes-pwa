package com.miui.note.algorithm;

import com.miui.note.algorithm.common.Graph;
import com.miui.note.algorithm.common.GraphType;
import com.miui.note.algorithm.common.Point;
import com.miui.note.algorithm.service.AngleService;
import com.miui.note.algorithm.utils.GeometryUtil;
import com.miui.note.algorithm.utils.mathutils.Bezier;
import com.miui.note.algorithm.utils.mathutils.Matrix;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class GraphFitting {

    /* JADX INFO: renamed from: com.miui.note.algorithm.GraphFitting$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$miui$note$algorithm$common$GraphType;

        static {
            int[] iArr = new int[GraphType.values().length];
            $SwitchMap$com$miui$note$algorithm$common$GraphType = iArr;
            try {
                iArr[GraphType.STRAIGHT_LINE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$note$algorithm$common$GraphType[GraphType.BROKEN_LINE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$note$algorithm$common$GraphType[GraphType.ARC_LINE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$note$algorithm$common$GraphType[GraphType.TRIANGLE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$note$algorithm$common$GraphType[GraphType.SQUARE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$miui$note$algorithm$common$GraphType[GraphType.RECTANGLE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$miui$note$algorithm$common$GraphType[GraphType.PENTAGON.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$miui$note$algorithm$common$GraphType[GraphType.PENTAGRAM.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$miui$note$algorithm$common$GraphType[GraphType.CIRCLE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$miui$note$algorithm$common$GraphType[GraphType.ELLIPSE.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
        }
    }

    public static void curveShapeFitting(Graph graph, List<Point> list) {
        switch (AnonymousClass1.$SwitchMap$com$miui$note$algorithm$common$GraphType[graph.getType().ordinal()]) {
            case 1:
                strightLineFitting(graph, list);
                break;
            case 2:
                brokenLineFitting(graph, list);
                break;
            case 3:
                arcLineFitting(graph, list);
                break;
            case 4:
                triangleFitting(graph, list);
                break;
            case 5:
                squareFitting(graph, list);
                break;
            case 6:
                rectangleFitting(graph, list);
                break;
            case 7:
                pentagonFitting(graph, list);
                break;
            case 8:
                pentagramFitting(graph, list);
                break;
            case 9:
                circleFitting(graph, list);
                break;
            case 10:
                ellipseFitting(graph, list);
                break;
        }
    }

    public static void strightLineFitting(Graph graph, List<Point> list) {
        graph.setFittedPoints(graph.getPoints());
    }

    public static void brokenLineFitting(Graph graph, List<Point> list) {
        float y;
        float f;
        ArrayList arrayList = new ArrayList();
        Point point = graph.getPoints().get(1);
        float f2 = 0.0f;
        int i = 1;
        float f3 = 0.0f;
        for (int i2 = 0; i2 < list.size(); i2++) {
            if (point.getX() == list.get(i2).getX() && point.getY() == list.get(i2).getY()) {
                i++;
                if (i == 2) {
                    float[] fArrLeastSquare = GeometryUtil.leastSquare(arrayList);
                    y = fArrLeastSquare[0];
                    f = fArrLeastSquare[1];
                    graph.getFittedPoints().add(GeometryUtil.getFootOfPerpendicular(graph.getPoints().get(0).getX(), graph.getPoints().get(0).getY(), y, f));
                    point.setX(graph.getPoints().get(i).getX());
                    point.setY(graph.getPoints().get(i).getY());
                } else if (arrayList.size() != 0) {
                    float x = (((Point) arrayList.get(arrayList.size() - 1)).getX() + ((Point) arrayList.get(0)).getX()) / 2.0f;
                    float y2 = (((Point) arrayList.get(arrayList.size() - 1)).getY() + ((Point) arrayList.get(0)).getY()) / 2.0f;
                    Point footOfPerpendicular = GeometryUtil.getFootOfPerpendicular(x, y2, f2, f3);
                    graph.getFittedPoints().add(footOfPerpendicular);
                    y = (footOfPerpendicular.getY() - y2) / (footOfPerpendicular.getX() - x);
                    f = y2 - (x * y);
                    if (i < graph.getPoints().size()) {
                        point.setX(graph.getPoints().get(i).getX());
                        point.setY(graph.getPoints().get(i).getY());
                    }
                }
                arrayList.clear();
                float f4 = y;
                f3 = f;
                f2 = f4;
            } else {
                arrayList.add(list.get(i2));
            }
        }
        graph.getFittedPoints().add(GeometryUtil.getFootOfPerpendicular(graph.getPoints().get(graph.getPoints().size() - 1).getX(), graph.getPoints().get(graph.getPoints().size() - 1).getY(), f2, f3));
    }

    public static void arcLineFitting(Graph graph, List<Point> list) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(list.get(0));
        int size = graph.getPoints().size() - 2;
        int i = 1;
        while (i <= size) {
            int i2 = i - 1;
            Point point = new Point((graph.getPoints().get(i2).getX() + graph.getPoints().get(i).getX()) / 2.0f, (graph.getPoints().get(i2).getY() + graph.getPoints().get(i).getY()) / 2.0f);
            int i3 = i + 1;
            Point point2 = new Point((graph.getPoints().get(i).getX() + graph.getPoints().get(i3).getX()) / 2.0f, (graph.getPoints().get(i).getY() + graph.getPoints().get(i3).getY()) / 2.0f);
            float fDistance = (float) (GeometryUtil.distance(graph.getPoints().get(i2), graph.getPoints().get(i)) / (GeometryUtil.distance(graph.getPoints().get(i), graph.getPoints().get(i3)) + GeometryUtil.distance(graph.getPoints().get(i2), graph.getPoints().get(i))));
            GeometryUtil.distance(point, point2);
            Point point3 = new Point(((graph.getPoints().get(i).getX() * 1.6f) - ((point.getX() + point2.getX()) * 0.3f)) - ((point2.getX() - point.getX()) * fDistance), ((graph.getPoints().get(i).getY() * 1.6f) - ((point.getY() + point2.getY()) * 0.3f)) - ((point2.getY() - point.getY()) * fDistance));
            float f = 1.0f - fDistance;
            Point point4 = new Point(((graph.getPoints().get(i).getX() * 1.6f) - ((point.getX() + point2.getX()) * 0.3f)) + ((point2.getX() - point.getX()) * f), ((graph.getPoints().get(i).getY() * 1.6f) - ((point.getY() + point2.getY()) * 0.3f)) + (f * (point2.getY() - point.getY())));
            arrayList.add(point3);
            arrayList.add(point4);
            i = i3;
        }
        arrayList.add(list.get(list.size() - 1));
        Iterator<Point> it = Bezier.direct_bezier(arrayList, 500).iterator();
        while (it.hasNext()) {
            graph.getFittedPoints().add(it.next());
        }
    }

    public static void triangleFitting(Graph graph, List<Point> list) {
        graph.setFittedPoints(graph.getPoints());
    }

    public static void rectangleFitting(Graph graph, List<Point> list) {
        ArrayList<Point> points = graph.getPoints();
        int i = Math.abs((AngleService.angle(points.get(0), points.get(1), points.get(2)) + AngleService.angle(points.get(1), points.get(2), points.get(3))) - 180.0d) < Math.abs((AngleService.angle(points.get(3), points.get(0), points.get(1)) + AngleService.angle(points.get(0), points.get(1), points.get(2))) - 180.0d) ? 0 : 1;
        int i2 = (i + 3) % 4;
        Point point = new Point((points.get(i).getX() + points.get(i2).getX()) / 2.0f, (points.get(i).getY() + points.get(i2).getY()) / 2.0f);
        int i3 = i + 1;
        int i4 = i + 2;
        Point point2 = new Point((points.get(i3).getX() + points.get(i4).getX()) / 2.0f, (points.get(i3).getY() + points.get(i4).getY()) / 2.0f);
        float y = (points.get(i3).getY() - points.get(i).getY()) / (points.get(i3).getX() - points.get(i).getX());
        float y2 = points.get(i).getY() - (points.get(i).getX() * y);
        Point footOfPerpendicular = GeometryUtil.getFootOfPerpendicular(point.getX(), point.getY(), y, y2);
        Point footOfPerpendicular2 = GeometryUtil.getFootOfPerpendicular(point2.getX(), point2.getY(), y, y2);
        graph.getFittedPoints().add(footOfPerpendicular);
        graph.getFittedPoints().add(footOfPerpendicular2);
        Point point3 = points.get(i4);
        Point point4 = points.get(i2);
        ArrayList arrayList = new ArrayList();
        boolean z = false;
        for (int i5 = 0; i5 < list.size(); i5++) {
            if (list.get(i5 % list.size()).getX() == point3.getX() && list.get(i5 % list.size()).getY() == point3.getY()) {
                z = true;
            }
            if (list.get(i5 % list.size()).getX() == point4.getX() && list.get(i5 % list.size()).getY() == point4.getY()) {
                break;
            }
            if (z) {
                arrayList.add(new Point(list.get(i5 % list.size()).getX(), list.get(i5 % list.size()).getY()));
            }
        }
        arrayList.add(new Point(point4.getX(), point4.getY()));
        int i6 = 1000;
        float size = 0.0f;
        while (i6 > 0) {
            i6--;
            float x = 0.0f;
            for (int i7 = 0; i7 < arrayList.size(); i7++) {
                x += ((((Point) arrayList.get(i7)).getX() * y) + size) - ((Point) arrayList.get(i7)).getY();
            }
            size -= (0.01f * x) / arrayList.size();
        }
        Point footOfPerpendicular3 = GeometryUtil.getFootOfPerpendicular(footOfPerpendicular2.getX(), footOfPerpendicular2.getY(), y, size);
        Point footOfPerpendicular4 = GeometryUtil.getFootOfPerpendicular(footOfPerpendicular.getX(), footOfPerpendicular.getY(), y, size);
        graph.getFittedPoints().add(footOfPerpendicular3);
        graph.getFittedPoints().add(footOfPerpendicular4);
        Point centerPoint = GeometryUtil.getCenterPoint(graph.getFittedPoints());
        Point point5 = new Point((footOfPerpendicular3.getX() + footOfPerpendicular4.getX()) / 2.0f, (footOfPerpendicular3.getY() + footOfPerpendicular4.getY()) / 2.0f);
        float roateAngle = GeometryUtil.getRoateAngle(point5, centerPoint, new Point(point5.getX(), centerPoint.getY()));
        graph.setGraphParam("centerX", Float.valueOf(centerPoint.getX()));
        graph.setGraphParam("centerY", Float.valueOf(centerPoint.getY()));
        graph.setGraphParam("rotationAngle", Float.valueOf(roateAngle));
        graph.getFittedPoints().add(footOfPerpendicular);
    }

    public static void squareFitting(Graph graph, List<Point> list) {
        rectangleFitting(graph, list);
    }

    public static void pentagonFitting(Graph graph, List<Point> list) {
        ArrayList<Point> points = graph.getPoints();
        points.remove(graph.getPoints().size() - 1);
        float x = 0.0f;
        float y = 0.0f;
        for (int i = 0; i < points.size(); i++) {
            x += points.get(i).getX();
            y += points.get(i).getY();
        }
        float f = x / 5.0f;
        float f2 = y / 5.0f;
        ArrayList arrayList = new ArrayList();
        double[] dArr = new double[5];
        for (int i2 = 0; i2 < 5; i2++) {
            double dHypot = Math.hypot(((double) points.get(i2).getX()) - ((double) f), ((double) points.get(i2).getY()) - ((double) f2));
            dArr[i2] = dHypot;
            arrayList.add(Double.valueOf(dHypot));
        }
        Arrays.sort(dArr);
        Point point = points.get(arrayList.indexOf(Double.valueOf(dArr[2])));
        for (int i3 = 0; i3 < 5; i3++) {
            double d = 1.2566370614359172d * ((double) i3);
            graph.getFittedPoints().add(new Point((float) (((Math.cos(d) * ((double) (point.getX() - f))) - (Math.sin(d) * ((double) (point.getY() - f2)))) + ((double) f)), (float) ((Math.sin(d) * ((double) (point.getX() - f))) + (Math.cos(d) * ((double) (point.getY() - f2))) + ((double) f2))));
        }
        graph.getFittedPoints().add(point);
        float roateAngle = GeometryUtil.getRoateAngle(new Point((graph.getFittedPoints().get(0).getX() + graph.getFittedPoints().get(1).getX()) / 2.0f, (graph.getFittedPoints().get(0).getY() + graph.getFittedPoints().get(1).getY()) / 2.0f), new Point(f, f2), new Point(f, f2 - 1.0f));
        while (Math.abs(roateAngle) > 72.0d) {
            double d2 = roateAngle;
            roateAngle = (float) (d2 > 72.0d ? d2 - 72.0d : d2 + 72.0d);
        }
        graph.setGraphParam("centerX", Float.valueOf(f));
        graph.setGraphParam("centerY", Float.valueOf(f2));
        graph.setGraphParam("radius", Float.valueOf((float) GeometryUtil.distance(new Point(f, f2), point)));
        graph.setGraphParam("rotationAngle", Float.valueOf(roateAngle));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void pentagramFitting(Graph graph, List<Point> list) {
        ArrayList<Point> arrayList = new ArrayList<>();
        char c = 2;
        if (graph.getPoints().size() == 11) {
            if (AngleService.approximate30(AngleService.angle(graph.getPoints().get(0), graph.getPoints().get(1), graph.getPoints().get(2)))) {
                for (int i = 1; i < 11; i += 2) {
                    arrayList.add(new Point(graph.getPoints().get(i).getX(), graph.getPoints().get(i).getY()));
                }
            } else {
                for (int i2 = 0; i2 < 10; i2 += 2) {
                    arrayList.add(new Point(graph.getPoints().get(i2).getX(), graph.getPoints().get(i2).getY()));
                }
            }
        } else {
            arrayList = graph.getPoints();
            arrayList.remove(arrayList.size() - 1);
        }
        float x = 0.0f;
        float y = 0.0f;
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            x += arrayList.get(i3).getX();
            y += arrayList.get(i3).getY();
        }
        float size = x / arrayList.size();
        float size2 = y / arrayList.size();
        ArrayList arrayList2 = new ArrayList();
        double[] dArr = new double[5];
        int i4 = 0;
        while (i4 < 5) {
            char c2 = c;
            double dHypot = Math.hypot(((double) arrayList.get(i4).getX()) - ((double) size), ((double) arrayList.get(i4).getY()) - ((double) size2));
            dArr[i4] = dHypot;
            arrayList2.add(Double.valueOf(dHypot));
            i4++;
            c = c2;
        }
        Arrays.sort(dArr);
        Point point = arrayList.get(arrayList2.indexOf(Double.valueOf(dArr[c])));
        ArrayList arrayList3 = new ArrayList();
        int i5 = 0;
        for (int i6 = 5; i5 < i6; i6 = i6) {
            double d = 1.2566370614359172d * ((double) i5);
            arrayList3.add(new Point((float) (((Math.cos(d) * ((double) (point.getX() - size))) - (Math.sin(d) * ((double) (point.getY() - size2)))) + ((double) size)), (float) ((Math.sin(d) * ((double) (point.getX() - size))) + (Math.cos(d) * ((double) (point.getY() - size2))) + ((double) size2))));
            i5++;
        }
        float roateAngle = GeometryUtil.getRoateAngle(new Point((((Point) arrayList3.get(0)).getX() + ((Point) arrayList3.get(1)).getX()) / 2.0f, (((Point) arrayList3.get(0)).getY() + ((Point) arrayList3.get(1)).getY()) / 2.0f), new Point(size, size2), new Point(size, size2 - 1.0f));
        while (Math.abs(roateAngle) > 72.0d) {
            double d2 = roateAngle;
            roateAngle = (float) (d2 > 72.0d ? d2 - 72.0d : d2 + 72.0d);
        }
        graph.setGraphParam("centerX", Float.valueOf(size));
        graph.setGraphParam("centerY", Float.valueOf(size2));
        graph.setGraphParam("radius", Float.valueOf((float) GeometryUtil.distance(new Point(size, size2), point)));
        graph.setGraphParam("rotationAngle", Float.valueOf(roateAngle));
        int i7 = 0;
        while (i7 < arrayList3.size()) {
            Point point2 = (Point) arrayList3.get(i7);
            Point point3 = (Point) arrayList3.get((i7 + 2) % 5);
            int i8 = i7 + 1;
            Point point4 = (Point) arrayList3.get(i8 % 5);
            Point point5 = (Point) arrayList3.get((i7 + 4) % 5);
            float x2 = (((point2.getX() - point3.getX()) * ((point4.getX() * point5.getY()) - (point5.getX() * point4.getY()))) - ((point4.getX() - point5.getX()) * ((point2.getX() * point3.getY()) - (point3.getX() * point2.getY())))) / (((point4.getX() - point5.getX()) * (point2.getY() - point3.getY())) - ((point2.getX() - point3.getX()) * (point4.getY() - point5.getY())));
            float y2 = (((point2.getY() - point3.getY()) * ((point4.getX() * point5.getY()) - (point5.getX() * point4.getY()))) - (((point2.getX() * point3.getY()) - (point3.getX() * point2.getY())) * (point4.getY() - point5.getY()))) / (((point2.getY() - point3.getY()) * (point4.getX() - point5.getX())) - ((point2.getX() - point3.getX()) * (point4.getY() - point5.getY())));
            graph.getFittedPoints().add(new Point(((Point) arrayList3.get(i7)).getX(), ((Point) arrayList3.get(i7)).getY()));
            graph.getFittedPoints().add(new Point(x2, y2));
            i7 = i8;
        }
        graph.getFittedPoints().add(arrayList3.get(0));
    }

    public static void circleFitting(Graph graph, List<Point> list) {
        int size = graph.getPoints().size();
        float f = 0.0f;
        float f2 = 0.0f;
        float f3 = 0.0f;
        float f4 = 0.0f;
        float f5 = 0.0f;
        float f6 = 0.0f;
        float f7 = 0.0f;
        float f8 = 0.0f;
        float f9 = 0.0f;
        for (int i = 0; i < size; i++) {
            float x = graph.getPoints().get(i).getX();
            float y = graph.getPoints().get(i).getY();
            float f10 = x * x;
            float f11 = y * y;
            f2 += x;
            f4 += y;
            f += f10;
            f7 += f11;
            f5 += f10 * x;
            f9 += f11 * y;
            f3 += x * y;
            f6 += x * f11;
            f8 += f10 * y;
        }
        float f12 = size;
        float f13 = (f12 * f) - (f2 * f2);
        float f14 = (f3 * f12) - (f2 * f4);
        float f15 = (f5 * f12) + (f6 * f12);
        float f16 = f + f7;
        float f17 = f15 - (f16 * f2);
        float f18 = (f12 * f7) - (f4 * f4);
        float f19 = ((f8 * f12) + (f9 * f12)) - (f16 * f4);
        float f20 = (f19 * f14) - (f17 * f18);
        float f21 = f18 * f13;
        float f22 = f14 * f14;
        float f23 = f20 / (f21 - f22);
        float f24 = ((f19 * f13) - (f17 * f14)) / (f22 - f21);
        float f25 = (-((((f2 * f23) + (f4 * f24)) + f) + f7)) / f12;
        float f26 = f23 / (-2.0f);
        float f27 = f24 / (-2.0f);
        float fSqrt = ((float) Math.sqrt(((f23 * f23) + (f24 * f24)) - (f25 * 4.0f))) / 2.0f;
        graph.setGraphParam("centerX", Float.valueOf(f26));
        graph.setGraphParam("centerY", Float.valueOf(f27));
        graph.setGraphParam("radius", Float.valueOf(fSqrt));
        for (int i2 = 0; i2 < 360; i2++) {
            double d = fSqrt;
            double d2 = (((double) i2) * 3.141592653589793d) / 180.0d;
            graph.getFittedPoints().add(new Point((float) (((double) f26) + (Math.cos(d2) * d)), (float) (((double) f27) + (d * Math.sin(d2)))));
        }
    }

    public static void ellipseFitting(Graph graph, List<Point> list) {
        double[][] dArr = (double[][]) Array.newInstance((Class<?>) Double.TYPE, list.size(), 2);
        for (int i = 0; i < list.size(); i++) {
            dArr[i][0] = list.get(i).getX();
            dArr[i][1] = list.get(i).getY();
        }
        double[] dArrVarToDimensions = varToDimensions(direct(dArr));
        graph.setGraphParam("centerX", Float.valueOf((float) dArrVarToDimensions[0]));
        graph.setGraphParam("centerY", Float.valueOf((float) dArrVarToDimensions[1]));
        graph.setGraphParam("majorAxis", Float.valueOf((float) dArrVarToDimensions[2]));
        graph.setGraphParam("minorAxis", Float.valueOf((float) dArrVarToDimensions[3]));
        graph.setGraphParam("rotationAngle", Float.valueOf((float) dArrVarToDimensions[4]));
        for (double[] dArr2 : generatePoints(dArrVarToDimensions[2], dArrVarToDimensions[3], dArrVarToDimensions[4], dArrVarToDimensions[0], dArrVarToDimensions[1], 1000)) {
            graph.getFittedPoints().add(new Point((float) dArr2[0], (float) dArr2[1]));
        }
    }

    public static double[] direct(double[][] dArr) {
        int length = dArr.length;
        double[] centroid2D = GeometryUtil.getCentroid2D(dArr);
        double d = centroid2D[0];
        double d2 = centroid2D[1];
        double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, length, 3);
        for (int i = 0; i < length; i++) {
            double[] dArr3 = dArr[i];
            double d3 = dArr3[0] - d;
            double d4 = dArr3[1] - d2;
            double[] dArr4 = dArr2[i];
            dArr4[0] = d3 * d3;
            dArr4[1] = d3 * d4;
            dArr4[2] = d4 * d4;
        }
        Matrix matrix = new Matrix(dArr2);
        double[][] dArr5 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, length, 3);
        for (int i2 = 0; i2 < length; i2++) {
            double[] dArr6 = dArr5[i2];
            double[] dArr7 = dArr[i2];
            dArr6[0] = dArr7[0] - d;
            dArr6[1] = dArr7[1] - d2;
            dArr6[2] = 1.0d;
        }
        Matrix matrix2 = new Matrix(dArr5);
        Matrix matrixTimes = matrix.transpose().times(matrix);
        Matrix matrixTimes2 = matrix.transpose().times(matrix2);
        Matrix matrixTimes3 = matrix2.transpose().times(matrix2).inverse().times(-1.0d).times(matrixTimes2.transpose());
        double[][] array = matrixTimes.plus(matrixTimes2.times(matrixTimes3)).getArray();
        double[] dArr8 = array[2];
        double[] dArr9 = {dArr8[0] / 2.0d, dArr8[1] / 2.0d, dArr8[2] / 2.0d};
        double[] dArr10 = array[1];
        double[] dArr11 = {-dArr10[0], -dArr10[1], -dArr10[2]};
        double[] dArr12 = array[0];
        Matrix v = new Matrix(new double[][]{dArr9, dArr11, new double[]{dArr12[0] / 2.0d, dArr12[1] / 2.0d, dArr12[2] / 2.0d}}).eig().getV();
        Matrix matrix3 = v.getMatrix(0, 0, 0, 2);
        Matrix matrix4 = v.getMatrix(1, 1, 0, 2);
        Matrix matrixMinus = matrix3.times(4.0d).arrayTimes(v.getMatrix(2, 2, 0, 2)).minus(matrix4.arrayTimes(matrix4));
        int i3 = 0;
        while (i3 < 3) {
            if (matrixMinus.get(0, i3) > 0.0d) {
                Matrix matrix5 = v.getMatrix(0, 2, i3, i3);
                Matrix matrix6 = new Matrix(6, 1);
                matrix6.setMatrix(0, 2, 0, 0, matrix5);
                matrix6.setMatrix(3, 5, 0, 0, matrixTimes3.times(matrix5));
                double[] columnPackedCopy = matrix6.getColumnPackedCopy();
                double d5 = columnPackedCopy[3];
                double d6 = columnPackedCopy[0];
                double d7 = columnPackedCopy[1];
                double d8 = (d5 - ((d6 * 2.0d) * d)) - (d7 * d2);
                double d9 = columnPackedCopy[4];
                double d10 = columnPackedCopy[2];
                double d11 = ((((columnPackedCopy[5] + ((d6 * d) * d)) + ((d10 * d2) * d2)) + ((d7 * d) * d2)) - (d5 * d)) - (d9 * d2);
                matrix6.set(3, 0, d8);
                matrix6.set(4, 0, (d9 - ((2.0d * d10) * d2)) - (d7 * d));
                matrix6.set(5, 0, d11);
                return matrix6.times(1.0d / matrix6.normF()).getColumnPackedCopy();
            }
            i3++;
        }
        i3 = 0;
        Matrix matrix7 = v.getMatrix(0, 2, i3, i3);
        Matrix matrix8 = new Matrix(6, 1);
        matrix8.setMatrix(0, 2, 0, 0, matrix7);
        matrix8.setMatrix(3, 5, 0, 0, matrixTimes3.times(matrix7));
        double[] columnPackedCopy2 = matrix8.getColumnPackedCopy();
        double d12 = columnPackedCopy2[3];
        double d13 = columnPackedCopy2[0];
        double d14 = columnPackedCopy2[1];
        double d15 = (d12 - ((d13 * 2.0d) * d)) - (d14 * d2);
        double d16 = columnPackedCopy2[4];
        double d17 = columnPackedCopy2[2];
        double d18 = ((((columnPackedCopy2[5] + ((d13 * d) * d)) + ((d17 * d2) * d2)) + ((d14 * d) * d2)) - (d12 * d)) - (d16 * d2);
        matrix8.set(3, 0, d15);
        matrix8.set(4, 0, (d16 - ((2.0d * d17) * d2)) - (d14 * d));
        matrix8.set(5, 0, d18);
        return matrix8.times(1.0d / matrix8.normF()).getColumnPackedCopy();
    }

    public static double[] varToDimensions(double[] dArr) {
        double d = dArr[0];
        double d2 = dArr[1] / 2.0d;
        double d3 = dArr[2];
        double d4 = dArr[3] / 2.0d;
        double d5 = dArr[4] / 2.0d;
        double d6 = dArr[5];
        double d7 = d3 * d4;
        double d8 = d * d3;
        double d9 = (d2 * d2) - d8;
        double d10 = (d7 - (d2 * d5)) / d9;
        double d11 = d * d5;
        double d12 = (d11 - (d2 * d4)) / d9;
        double d13 = (d11 * d5) + (d7 * d4) + (d6 * d2 * d2);
        double d14 = d2 * 2.0d;
        double d15 = ((d13 - ((d4 * d14) * d5)) - (d8 * d6)) * 2.0d;
        double d16 = d - d3;
        double d17 = (d16 * d16) + (4.0d * d2 * d2);
        double d18 = d + d3;
        double dSqrt = Math.sqrt(d15 / ((Math.sqrt(d17) - d18) * d9));
        double dSqrt2 = Math.sqrt(d15 / (d9 * ((-Math.sqrt(d17)) - d18)));
        double dAtan = 0.0d;
        if (d2 == 0.0d) {
            if (d > d3 && d > d3) {
                dAtan = 1.5707963267948966d;
            }
        } else if (d < d3) {
            dAtan = Math.atan(d14 / d16) / 2.0d;
        } else if (d > d3) {
            dAtan = (Math.atan(d14 / d16) / 2.0d) + 1.5707963267948966d;
        }
        if (dSqrt < dSqrt2) {
            dAtan -= 1.5707963267948966d;
            dSqrt = dSqrt2;
            dSqrt2 = dSqrt;
        }
        return new double[]{d10, d12, dSqrt, dSqrt2, dAtan};
    }

    public static double[][] generatePoints(double d, double d2, double d3, double d4, double d5, int i) {
        double[][] dArr = (double[][]) Array.newInstance((Class<?>) Double.TYPE, i, 2);
        double d6 = 6.283185307179586d / ((double) i);
        double d7 = 0.0d;
        for (int i2 = 0; i2 < i; i2++) {
            dArr[i2][0] = Math.cos(d7) * d;
            dArr[i2][1] = Math.sin(d7) * d2;
            d7 += d6;
        }
        double dSin = Math.sin(d3);
        double dCos = Math.cos(d3);
        for (int i3 = 0; i3 < i; i3++) {
            double[] dArr2 = dArr[i3];
            double d8 = dArr2[0];
            double d9 = dArr2[1];
            dArr2[0] = ((d8 * dCos) - (d9 * dSin)) + d4;
            dArr2[1] = (d8 * dSin) + (d9 * dCos) + d5;
        }
        return dArr;
    }
}
