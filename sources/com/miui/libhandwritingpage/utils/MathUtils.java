package com.miui.libhandwritingpage.utils;

import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import java.math.BigDecimal;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class MathUtils {
    public static float arcToDegree(float f) {
        return (float) (((double) f) * 57.29577951308232d);
    }

    public static int calculateIndex(int i) {
        if (i >= 8) {
            return i - 8;
        }
        return i < 0 ? i + 8 : i;
    }

    public static int getOrientation(double d, double d2, double d3, double d4, double d5, double d6) {
        return ((d3 - d) * (d6 - d2)) - ((d5 - d) * (d4 - d2)) >= 0.0d ? 0 : 1;
    }

    public static double getSlope(double d, double d2, double d3, double d4) {
        if (d == d3 || d2 == d4) {
            return 0.0d;
        }
        return (d4 - d2) / (d3 - d);
    }

    public static double div(double d, double d2, int i) {
        if (i < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        return new BigDecimal(String.valueOf(d)).divide(new BigDecimal(String.valueOf(d2)), i, 4).doubleValue();
    }

    public static double getRotate(float f, float f2, float f3, float f4, float f5, float f6) {
        double dHypot = Math.hypot(f - f3, f2 - f4);
        double dHypot2 = Math.hypot(f - f5, f2 - f6);
        double dHypot3 = Math.hypot(f5 - f3, f6 - f4);
        double d = (((dHypot * dHypot) + (dHypot2 * dHypot2)) - (dHypot3 * dHypot3)) / ((dHypot2 * 2.0d) * dHypot);
        if (d >= 1.0d) {
            d = 1.0d;
        }
        double degrees = Math.toDegrees(Math.acos(d));
        if (getOrientation(f, f2, f3, f4, f5, f6) == 0) {
            degrees = -degrees;
        }
        return -degrees;
    }

    public static float distanceTwoPoint(float f, float f2, float f3, float f4) {
        float fAbs = Math.abs(f3 - f);
        float fAbs2 = Math.abs(f4 - f2);
        return (float) Math.sqrt((fAbs * fAbs) + (fAbs2 * fAbs2));
    }

    public static Path getRectAntPath(float[] fArr, float[] fArr2) {
        Path path = new Path();
        path.moveTo(fArr[2], fArr[3]);
        path.lineTo(fArr[4], fArr[5]);
        path.lineTo(fArr[6], fArr[7]);
        path.lineTo(fArr[8], fArr[9]);
        path.close();
        float[] centerPoint = getCenterPoint(fArr[4], fArr[5], fArr[6], fArr[7]);
        path.moveTo(centerPoint[0], centerPoint[1]);
        float[] distancePoint = getDistancePoint(fArr[2], fArr[3], fArr[4], fArr[5]);
        fArr2[0] = distancePoint[0];
        fArr2[1] = distancePoint[1];
        return path;
    }

    public static Path getRectAntPaths(float[] fArr) {
        Path path = new Path();
        if (fArr.length > 8) {
            path.moveTo(fArr[0], fArr[1]);
            path.lineTo(fArr[4], fArr[5]);
            path.lineTo(fArr[8], fArr[9]);
            path.lineTo(fArr[12], fArr[13]);
        } else {
            path.moveTo(fArr[0], fArr[1]);
            path.lineTo(fArr[2], fArr[3]);
            path.lineTo(fArr[4], fArr[5]);
            path.lineTo(fArr[6], fArr[7]);
        }
        path.close();
        return path;
    }

    private double distanceTwoPoint(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() != 2) {
            return 0.0d;
        }
        float x = motionEvent.getX(0) - motionEvent.getX(1);
        float y = motionEvent.getY(0) - motionEvent.getY(1);
        return Math.sqrt((x * x) + (y * y));
    }

    public static Path getTreeBeziersPath(float[] fArr, int i, int i2, float[] fArr2, float[] fArr3) {
        Path path = new Path();
        int i3 = i2 + 1;
        float f = fArr[i2];
        float f2 = fArr[i3];
        path.moveTo(f, f2);
        fArr2[0] = f;
        fArr2[1] = f2;
        fArr3[0] = f;
        fArr3[1] = f2;
        int i4 = ((i - 2) - i3) / 6;
        for (int i5 = 0; i5 < i4; i5++) {
            int i6 = i5 * 6;
            float f3 = fArr[i6 + 2 + i2];
            float f4 = fArr[i6 + 3 + i2];
            if (f4 < fArr2[1]) {
                fArr2[0] = f3;
                fArr2[1] = f4;
            }
            if (f4 > fArr3[1]) {
                fArr3[0] = f3;
                fArr3[1] = f4;
            }
            float f5 = fArr[i6 + 4 + i2];
            float f6 = fArr[i6 + 5 + i2];
            if (f6 < fArr2[1]) {
                fArr2[0] = f5;
                fArr2[1] = f6;
            }
            if (f6 > fArr3[1]) {
                fArr3[0] = f5;
                fArr3[1] = f6;
            }
            float f7 = fArr[i6 + 6 + i2];
            float f8 = fArr[i6 + 7 + i2];
            if (f8 < fArr2[1]) {
                fArr2[0] = f7;
                fArr2[1] = f8;
            }
            if (f8 > fArr3[1]) {
                fArr3[0] = f7;
                fArr3[1] = f8;
            }
            path.cubicTo(f3, f4, f5, f6, f7, f8);
        }
        return path;
    }

    public static Path getTreeBeziersPath(float[] fArr, int i, int i2) {
        return getTreeBeziersPath(fArr, i, i2, new float[2], new float[2]);
    }

    public static float calcZoom(float f, float f2, float f3, float f4, float f5, float f6) {
        return distanceTwoPoint(f, f2, f5, f6) / distanceTwoPoint(f, f2, f3, f4);
    }

    public static float calcZoomWithMiss(float f, float f2, float f3, float f4, float f5, float f6, float f7) {
        return (distanceTwoPoint(f, f2, f5, f6) - f7) / distanceTwoPoint(f, f2, f3, f4);
    }

    public static float[] getCenterPoint(float f, float f2, float f3, float f4) {
        return new float[]{(f + f3) / 2.0f, (f2 + f4) / 2.0f};
    }

    public static float[] getVerticalLinePoint(float f, float f2, float f3, float f4, float f5) {
        float[] fArr = new float[2];
        float f6 = f - f3;
        float f7 = f2 - f4;
        double dSqrt = ((double) f5) / Math.sqrt(Math.pow(Math.abs(f6), 2.0d) + Math.pow(Math.abs(f7), 2.0d));
        if (f3 > f && f4 > f2) {
            fArr[0] = (float) (((double) f3) + (((double) (f3 - f)) * dSqrt));
            fArr[1] = (float) (((double) f4) + (((double) (f4 - f2)) * dSqrt));
            return fArr;
        }
        if (f3 > f && f4 < f2) {
            fArr[0] = (float) (((double) f3) + (((double) (f3 - f)) * dSqrt));
            fArr[1] = (float) (((double) f4) - (((double) f7) * dSqrt));
            return fArr;
        }
        if (f3 < f && f4 > f2) {
            fArr[0] = (float) (((double) f3) - (((double) f6) * dSqrt));
            fArr[1] = (float) (((double) f4) + (((double) (f4 - f2)) * dSqrt));
            return fArr;
        }
        if (f3 < f && f4 < f2) {
            fArr[0] = (float) (((double) f3) - (((double) f6) * dSqrt));
            fArr[1] = (float) (((double) f4) - (((double) f7) * dSqrt));
            return fArr;
        }
        if (f3 == f && f4 > f2) {
            fArr[0] = f3;
            fArr[1] = f4 + f5;
            return fArr;
        }
        if (f3 == f && f4 < f2) {
            fArr[0] = f3;
            fArr[1] = f4 - f5;
            return fArr;
        }
        if (f3 > f && f4 == f2) {
            fArr[0] = f3 + f5;
            fArr[1] = f4;
            return fArr;
        }
        if (f3 < f && f4 == f2) {
            fArr[0] = f3 - f5;
            fArr[1] = f4;
        }
        return fArr;
    }

    public static float getSlopeDegree(float f) {
        return arcToDegree((float) Math.atan(f));
    }

    public static float[] getDistancePoint(float f, float f2, float f3, float f4) {
        float[] fArr = {(f3 + f) / 2.0f, (f4 + f2) / 2.0f};
        Log.d("Main", "onClickNewCirclePoint: dstX=" + fArr[0] + " dstY =" + fArr[1]);
        return fArr;
    }

    public static PointF getProjectivePoints2(PointF pointF, double d, PointF pointF2) {
        PointF pointF3 = new PointF();
        float f = (float) (((double) pointF.y) - (((double) pointF.x) * d));
        pointF3.x = (float) (((((double) (pointF2.y - f)) * d) + ((double) pointF2.x)) / ((d * d) + 1.0d));
        pointF3.y = (float) ((d * ((double) pointF3.x)) + ((double) f));
        return pointF3;
    }

    public static PointF getProjectivePoints(PointF pointF, PointF pointF2, PointF pointF3) {
        PointF pointF4 = new PointF();
        if (pointF.x == pointF2.x) {
            pointF4.x = pointF.x;
            pointF4.y = pointF3.y;
            return pointF4;
        }
        pointF4.x = pointF3.x;
        pointF4.y = pointF.y;
        return pointF4;
    }

    public static PointF getProjectivePoint(PointF pointF, PointF pointF2, PointF pointF3) {
        double slope = getSlope(pointF.x, pointF.y, pointF2.x, pointF2.y);
        if (slope == 0.0d) {
            return getProjectivePoints(pointF, pointF2, pointF3);
        }
        return getProjectivePoints2(pointF, slope, pointF3);
    }

    public static PointF getMiddlePoint(float f, float f2, float f3, float f4) {
        return new PointF((f + f3) / 2.0f, (f2 + f4) / 2.0f);
    }

    public static float computeDegree(Point point, Point point2) {
        float f = point.x - point2.x;
        float f2 = point.y - point2.y;
        float fAsin = (float) ((Math.asin(((double) f) / Math.sqrt((f * f) + (f2 * f2))) * 180.0d) / 3.141592653589793d);
        if (Float.isNaN(fAsin)) {
            return 0.0f;
        }
        if ((f < 0.0f || f2 > 0.0f) && (f > 0.0f || f2 > 0.0f)) {
            return (f <= 0.0f ? -180.0f : 180.0f) - fAsin;
        }
        return fAsin;
    }

    public static float getDistanceOfTwoPoints(float f, float f2, float f3, float f4) {
        float f5 = f - f3;
        float f6 = f2 - f4;
        return (float) Math.sqrt((f5 * f5) + (f6 * f6));
    }

    public static float[] getEndPointByTrigonometric(double d, float f, float f2, double d2, int i) {
        float[] fArr = new float[2];
        if (i == 1) {
            fArr[0] = f;
            fArr[1] = (float) (((double) f2) - d2);
            return fArr;
        }
        fArr[0] = (float) (((double) f) + d2);
        fArr[1] = f2;
        return fArr;
    }

    private float[] afterRotation(float f, float f2, float f3, float f4, float f5) {
        float[] fArr = new float[2];
        double d = ((double) f5) * 0.017453292519943295d;
        if (f5 % 360.0f == 0.0f) {
            fArr[0] = f3;
            fArr[1] = f4;
            return fArr;
        }
        double d2 = f3 - f;
        double d3 = f4 - f2;
        fArr[0] = (float) ((((double) f) + (Math.cos(d) * d2)) - (Math.sin(d) * d3));
        fArr[1] = (float) (((double) f2) + (d2 * Math.sin(d)) + (d3 * Math.cos(d)));
        return fArr;
    }

    public static float[] getCenterOfGravityPoint(List<float[]> list) {
        float f = 0.0f;
        Float fValueOf = Float.valueOf(0.0f);
        float f2 = 0.0f;
        for (int i = 1; i <= list.size(); i++) {
            float f3 = list.get(i % list.size())[0];
            float f4 = list.get(i % list.size())[1];
            int i2 = i - 1;
            float f5 = list.get(i2)[0];
            float f6 = list.get(i2)[1];
            float f7 = ((f3 * f6) - (f4 * f5)) / 2.0f;
            fValueOf = Float.valueOf(fValueOf.floatValue() + f7);
            f += ((f3 + f5) * f7) / 3.0f;
            f2 += (f7 * (f4 + f6)) / 3.0f;
        }
        return new float[]{f / fValueOf.floatValue(), f2 / fValueOf.floatValue()};
    }

    public static double getAngle(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8) {
        double degrees = Math.toDegrees(Math.atan2(d4 - d2, d3 - d) - Math.atan2(d8 - d6, d7 - d5));
        return degrees < 0.0d ? degrees + 360.0d : degrees;
    }

    public static float[] calculateSquareVertices(float f, float f2, float f3, float f4) {
        float f5 = f + f3;
        float f6 = f4 - f2;
        float f7 = f2 + f4;
        float f8 = f - f3;
        return new float[]{f, f2, (f5 + f6) / 2.0f, (f7 + f8) / 2.0f, f3, f4, (f5 - f6) / 2.0f, (f7 - f8) / 2.0f};
    }

    public static float[] calculateLinearInterpolation(float f, float f2, float f3, float f4, float f5, float f6, float f7) {
        float f8 = f3 - f;
        float f9 = f4 - f2;
        float fSqrt = (float) Math.sqrt(Math.pow(f8, 2.0d) + Math.pow(f9, 2.0d));
        float fSqrt2 = (float) Math.sqrt(Math.pow(f5 - f, 2.0d) + Math.pow(f6 - f2, 2.0d));
        float f10 = (((1.0f - (fSqrt2 / fSqrt)) * f7) + fSqrt2) / fSqrt;
        return new float[]{f + (f8 * f10), f2 + (f9 * f10)};
    }

    public static RectF destFormMatrix(RectF rectF, float f, float f2, float f3) {
        PointF pointF = new PointF(rectF.left, rectF.top);
        PointF pointF2 = new PointF(rectF.right, rectF.bottom);
        toTranAndScale(pointF, f, f2, f3);
        toTranAndScale(pointF2, f, f2, f3);
        return new RectF(pointF.x, pointF.y, pointF2.x, pointF2.y);
    }

    public static RectF toTranMatrix(RectF rectF, float f, float f2) {
        PointF pointF = new PointF(rectF.left, rectF.top);
        PointF pointF2 = new PointF(rectF.right, rectF.bottom);
        toTran(pointF, f, f2);
        toTran(pointF2, f, f2);
        return new RectF(pointF.x, pointF.y, pointF2.x, pointF2.y);
    }

    public static void toTranAndScale(PointF pointF, float f, float f2, float f3) {
        pointF.x = (pointF.x - f) / f3;
        pointF.y = (pointF.y - f2) / f3;
    }

    public static void toTran(PointF pointF, float f, float f2) {
        pointF.x += f;
        pointF.y += f2;
    }

    public static RectF inverseRectF(RectF rectF, float f, float f2, float f3) {
        PointF pointF = new PointF(rectF.left, rectF.top);
        PointF pointF2 = new PointF(rectF.right, rectF.bottom);
        inversePoint(pointF, f, f2, f3);
        inversePoint(pointF2, f, f2, f3);
        return new RectF(pointF.x, pointF.y, pointF2.x, pointF2.y);
    }

    public static void inversePoint(PointF pointF, float f, float f2, float f3) {
        pointF.x = (pointF.x * f3) + f;
        pointF.y = (pointF.y * f3) + f2;
    }
}
