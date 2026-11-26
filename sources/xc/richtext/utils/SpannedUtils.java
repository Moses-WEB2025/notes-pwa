package xc.richtext.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Region;
import xc.richtext.api.ScreenRenderProvider;
import xc.richtext.data.SpannedData;

/* JADX INFO: loaded from: classes2.dex */
public class SpannedUtils {
    public static final int MAX_BITMAP_SIZE = 78643200;
    private static final String TAG = "SpannedUtils";

    public static float remapRange(float f, float f2, float f3, float f4, float f5) {
        return f4 + (((f - f2) / (f3 - f2)) * (f5 - f4));
    }

    public static Bitmap getDrawBitmap(String str, float f) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        int i = 1;
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        BitmapFactory.decodeFile(str, options);
        int i2 = options.outWidth;
        if (i2 == 0 || options.outHeight == 0) {
            return null;
        }
        int i3 = (int) (i2 / f);
        if (i3 >= 6) {
            i = 8;
        } else if (i3 > 2) {
            i = 4;
        } else if (i3 > 1) {
            i = 2;
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = i;
        if (((options.outWidth / i) * options.outHeight) / i > 78643200) {
            return BitmapFactory.decodeFile(str, options).copy(Bitmap.Config.ARGB_4444, false);
        }
        return BitmapFactory.decodeFile(str, options);
    }

    public static RectF unTranslateAndScaleRectF(RectF rectF, float f, float f2, float f3) {
        PointF pointF = new PointF(rectF.left, rectF.top);
        PointF pointF2 = new PointF(rectF.right, rectF.bottom);
        unTranslateAndScalePointF(pointF, f, f2, f3);
        unTranslateAndScalePointF(pointF2, f, f2, f3);
        return new RectF(pointF.x, pointF.y, pointF2.x, pointF2.y);
    }

    public static void unTranslateAndScalePointF(PointF pointF, float f, float f2, float f3) {
        pointF.x = (pointF.x - f) / f3;
        pointF.y = (pointF.y - f2) / f3;
    }

    public static RectF translateAndScaleRectF(RectF rectF, float f, float f2, float f3) {
        PointF pointF = new PointF(rectF.left, rectF.top);
        PointF pointF2 = new PointF(rectF.right, rectF.bottom);
        translateAndScalePointF(pointF, f, f2, f3);
        translateAndScalePointF(pointF2, f, f2, f3);
        return new RectF(pointF.x, pointF.y, pointF2.x, pointF2.y);
    }

    public static void translateAndScalePointF(PointF pointF, float f, float f2, float f3) {
        pointF.x = (pointF.x * f3) + f;
        pointF.y = (pointF.y * f3) + f2;
    }

    public static RectF getRotatedBoundingBox(RectF rectF, float f, float f2, float f3) {
        PointF pointF = new PointF(rectF.left, rectF.top);
        PointF pointF2 = new PointF(rectF.right, rectF.top);
        PointF pointF3 = new PointF(rectF.right, rectF.bottom);
        PointF pointF4 = new PointF(rectF.left, rectF.bottom);
        rotatePointAroundCenter(pointF, f, f2, f3);
        rotatePointAroundCenter(pointF2, f, f2, f3);
        rotatePointAroundCenter(pointF3, f, f2, f3);
        rotatePointAroundCenter(pointF4, f, f2, f3);
        float[] fArr = {pointF.x, pointF.y, pointF2.x, pointF2.y, pointF3.x, pointF3.y, pointF4.x, pointF4.y};
        float[] fArr2 = {fArr[0], fArr[2], fArr[4], fArr[6]};
        float[] fArr3 = {fArr[1], fArr[3], fArr[5], fArr[7]};
        float fMin = fArr2[0];
        float fMin2 = fArr3[0];
        float fMax = fMin2;
        float fMax2 = fMin;
        for (int i = 1; i < 4; i++) {
            fMin = Math.min(fArr2[i], fMin);
            fMax2 = Math.max(fArr2[i], fMax2);
            fMin2 = Math.min(fArr3[i], fMin2);
            fMax = Math.max(fArr3[i], fMax);
        }
        return new RectF(fMin, fMin2, fMax2, fMax);
    }

    public static void rotatePointAroundCenter(PointF pointF, float f, float f2, float f3) {
        double radians = Math.toRadians(f);
        float f4 = pointF.x - f2;
        float f5 = pointF.y - f3;
        double dCos = Math.cos(radians);
        double dSin = Math.sin(radians);
        double d = f4;
        double d2 = f5;
        pointF.x = (float) (((d * dCos) - (d2 * dSin)) + ((double) f2));
        pointF.y = (float) ((d * dSin) + (d2 * dCos) + ((double) f3));
    }

    public static RectF getPerpendicularFoot(RectF rectF, float f, PointF pointF) {
        float f2 = 1.0f - f;
        return new RectF((rectF.left * f) + (pointF.x * f2), (rectF.top * f) + (pointF.y * f2), (rectF.right * f) + (pointF.x * f2), (f * rectF.bottom) + (pointF.y * f2));
    }

    public static float[] calculateRotatedRectPoints(RectF rectF, float f, float f2, float f3) {
        float[] fArr = new float[12];
        float[] fArr2 = {rectF.left, rectF.top, rectF.right, rectF.top, rectF.right, rectF.bottom, rectF.left, rectF.bottom, rectF.centerX(), rectF.centerY(), 0.0f, 0.0f};
        Matrix matrix = new Matrix();
        matrix.setRotate(f, f2, f3);
        matrix.mapPoints(fArr, fArr2);
        return fArr;
    }

    public static Path getRotatedRectPath(RectF rectF, float f, float f2, float f3) {
        float[] fArrCalculateRotatedRectPoints = calculateRotatedRectPoints(rectF, f, f2, f3);
        Path path = new Path();
        path.reset();
        path.moveTo(fArrCalculateRotatedRectPoints[0], fArrCalculateRotatedRectPoints[1]);
        path.lineTo(fArrCalculateRotatedRectPoints[2], fArrCalculateRotatedRectPoints[3]);
        path.lineTo(fArrCalculateRotatedRectPoints[4], fArrCalculateRotatedRectPoints[5]);
        path.lineTo(fArrCalculateRotatedRectPoints[6], fArrCalculateRotatedRectPoints[7]);
        path.lineTo(fArrCalculateRotatedRectPoints[0], fArrCalculateRotatedRectPoints[1]);
        return path;
    }

    public static boolean isPointInPath(Path path, float f, float f2) {
        RectF rectF = new RectF();
        path.computeBounds(rectF, false);
        Region region = new Region();
        region.setPath(path, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));
        return region.contains((int) f, (int) f2);
    }

    public static void getPerpendicularFoot(PointF pointF, PointF pointF2, PointF pointF3, PointF pointF4) {
        try {
            if (pointF.x != pointF2.x) {
                double d = (pointF2.y - pointF.y) / (pointF2.x - pointF.x);
                if (d == 0.0d) {
                    pointF4.x = pointF3.x;
                    pointF4.y = pointF.y;
                    return;
                } else {
                    float f = (float) (((double) pointF.y) - (((double) pointF.x) * d));
                    float f2 = (float) (((((double) (pointF3.y - f)) * d) + ((double) pointF3.x)) / ((d * d) + 1.0d));
                    pointF4.x = f2;
                    pointF4.y = (float) ((d * ((double) f2)) + ((double) f));
                    return;
                }
            }
            throw new Exception("Not get slope because divisor is zero");
        } catch (Exception unused) {
        }
    }

    public static RectF transformRect(SpannedData spannedData, float f, float f2, float f3, float f4, float f5, int i, ScreenRenderProvider screenRenderProvider) {
        char c;
        char c2;
        char c3;
        RectF rectF = new RectF(spannedData.getDrawRectF(screenRenderProvider));
        float[] fArrCalculateRotatedRectPoints = calculateRotatedRectPoints(rectF, spannedData.mDegrees, rectF.centerX(), rectF.centerY());
        RectF rectF2 = new RectF(spannedData.getDrawRectF(screenRenderProvider));
        rectF2.offset(f, f2);
        float[] fArrCalculateRotatedRectPoints2 = calculateRotatedRectPoints(rectF2, spannedData.mDegrees, rectF2.centerX(), rectF2.centerY());
        fArrCalculateRotatedRectPoints2[4] = fArrCalculateRotatedRectPoints2[4] + f3;
        fArrCalculateRotatedRectPoints2[5] = fArrCalculateRotatedRectPoints2[5] + f4;
        if (fArrCalculateRotatedRectPoints2[0] == fArrCalculateRotatedRectPoints2[2]) {
            fArrCalculateRotatedRectPoints2[3] = fArrCalculateRotatedRectPoints2[3] + f4;
            c = 4;
            c2 = 5;
        } else {
            PointF pointF = new PointF();
            c = 4;
            c2 = 5;
            getPerpendicularFoot(new PointF(fArrCalculateRotatedRectPoints2[0], fArrCalculateRotatedRectPoints2[1]), new PointF(fArrCalculateRotatedRectPoints2[2], fArrCalculateRotatedRectPoints2[3]), new PointF(fArrCalculateRotatedRectPoints2[4], fArrCalculateRotatedRectPoints2[5]), pointF);
            fArrCalculateRotatedRectPoints2[2] = pointF.x;
            fArrCalculateRotatedRectPoints2[3] = pointF.y;
        }
        if (fArrCalculateRotatedRectPoints2[0] == fArrCalculateRotatedRectPoints2[6]) {
            fArrCalculateRotatedRectPoints2[7] = fArrCalculateRotatedRectPoints2[7] + f4;
            c3 = 6;
        } else {
            PointF pointF2 = new PointF();
            c3 = 6;
            getPerpendicularFoot(new PointF(fArrCalculateRotatedRectPoints2[0], fArrCalculateRotatedRectPoints2[1]), new PointF(fArrCalculateRotatedRectPoints2[6], fArrCalculateRotatedRectPoints2[7]), new PointF(fArrCalculateRotatedRectPoints2[c], fArrCalculateRotatedRectPoints2[c2]), pointF2);
            fArrCalculateRotatedRectPoints2[6] = pointF2.x;
            fArrCalculateRotatedRectPoints2[7] = pointF2.y;
        }
        float f6 = fArrCalculateRotatedRectPoints2[r8] - fArrCalculateRotatedRectPoints2[2];
        float f7 = fArrCalculateRotatedRectPoints2[1] - fArrCalculateRotatedRectPoints2[3];
        float fSqrt = (float) Math.sqrt((f6 * f6) + (f7 * f7));
        float f8 = fArrCalculateRotatedRectPoints2[r8] - fArrCalculateRotatedRectPoints2[c3];
        float f9 = fArrCalculateRotatedRectPoints2[1] - fArrCalculateRotatedRectPoints2[7];
        float fSqrt2 = (float) Math.sqrt((f8 * f8) + (f9 * f9));
        RectF rectF3 = new RectF(spannedData.getDrawRectF(screenRenderProvider));
        float f10 = f5 + 18.0f;
        if (fSqrt < f10) {
            fSqrt = f10;
        }
        if (fSqrt2 < f10) {
            fSqrt2 = f10;
        }
        float fMax = Math.max(fSqrt2, i + 18.0f);
        rectF3.right = rectF3.left + fSqrt;
        rectF3.bottom = rectF3.top + fMax;
        float[] fArrCalculateRotatedRectPoints3 = calculateRotatedRectPoints(rectF3, spannedData.mDegrees, rectF3.centerX(), rectF3.centerY());
        Matrix matrix = new Matrix();
        matrix.setTranslate((f + fArrCalculateRotatedRectPoints[r8]) - fArrCalculateRotatedRectPoints3[0], (f2 + fArrCalculateRotatedRectPoints[1]) - fArrCalculateRotatedRectPoints3[1]);
        matrix.mapRect(rectF3);
        return rectF3;
    }

    public static float[] changeFloatsByRotate(RectF rectF, float f, float f2, float f3) {
        float[] fArr = new float[12];
        float[] fArr2 = {rectF.left, rectF.top, rectF.right, rectF.top, rectF.right, rectF.bottom, rectF.left, rectF.bottom, rectF.centerX(), rectF.centerY(), 0.0f, 0.0f};
        Matrix matrix = new Matrix();
        matrix.setRotate(f, f2, f3);
        matrix.mapPoints(fArr, fArr2);
        return fArr;
    }

    public static float[] getRotatePointF(float[] fArr, float f, float f2, float f3) {
        float[] fArr2 = new float[2];
        Matrix matrix = new Matrix();
        matrix.setRotate(f, f2, f3);
        matrix.mapPoints(fArr2, fArr);
        return fArr2;
    }
}
