package xc.engine.utils;

import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.text.TextUtils;
import java.io.File;
import java.util.List;
import xc.engine.core.graphics.pen.BasePenPropConfig;
import xc.engine.core.graphics.pen.PenType;
import xc.engine.core.graphics.pen.ball.BallPropConfig;
import xc.engine.core.graphics.pen.ink.InkPropConfig;
import xc.engine.core.graphics.pen.mark.MarkPropConfig;
import xc.engine.core.graphics.pen.pencil.PencilPropConfig;
import xc.engine.core.graphics.pen.watercolor.WaterColorPropConfig;
import xc.engine.data.CsPoint;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.DataShape;

/* JADX INFO: loaded from: classes6.dex */
public class PenEngineUtils {
    public static final String TAG = "PenEngineUtils";

    public static int g(int i) {
        float[] fArr = new float[3];
        Color.colorToHSV(i, fArr);
        float f = fArr[1];
        if (f > 0.5f) {
            return Color.HSVToColor(new float[]{fArr[0], f / 2.0f, fArr[2]});
        }
        float f2 = fArr[2];
        return ((double) f2) > 0.5d ? Color.HSVToColor(new float[]{fArr[0], f + 0.5f, f2}) : Color.HSVToColor(new float[]{fArr[0], f, f2 * 2.0f});
    }

    public static RectF toIntRectF(RectF rectF) {
        rectF.top = (int) Math.floor(rectF.top);
        rectF.bottom = (int) Math.ceil(rectF.bottom);
        rectF.left = (int) Math.floor(rectF.left);
        rectF.right = (int) Math.ceil(rectF.right);
        return rectF;
    }

    public static Rect toIntRect(RectF rectF) {
        Rect rect = new Rect();
        rect.top = (int) Math.floor(rectF.top);
        rect.bottom = (int) Math.ceil(rectF.bottom);
        rect.left = (int) Math.floor(rectF.left);
        rect.right = (int) Math.ceil(rectF.right);
        return rect;
    }

    public static void calculateRoundedCornerPoint(PointF pointF, PointF pointF2, PointF pointF3, PointF pointF4) {
        float f = pointF.x - pointF2.x;
        float f2 = pointF.y - pointF2.y;
        if (Math.abs(f) < 0.1f) {
            pointF4.x = pointF.x;
            pointF4.y = pointF3.y;
        } else {
            if (Math.abs(f2) < 0.1f) {
                pointF4.x = pointF3.x;
                pointF4.y = pointF.y;
                return;
            }
            float f3 = f2 / f;
            float f4 = pointF.y - (pointF.x * f3);
            float f5 = (((pointF3.y - f4) * f3) + pointF3.x) / ((f3 * f3) + 1.0f);
            pointF4.x = f5;
            pointF4.y = (f3 * f5) + f4;
        }
    }

    public static RectF calculateUnionRectF(RectF rectF, RectF rectF2) {
        if (rectF == null || rectF2 == null) {
            return new RectF();
        }
        RectF rectF3 = new RectF();
        rectF3.left = Math.min(rectF.left, rectF2.left);
        rectF3.top = Math.min(rectF.top, rectF2.top);
        rectF3.right = Math.max(rectF.right, rectF2.right);
        rectF3.bottom = Math.max(rectF.bottom, rectF2.bottom);
        return rectF3;
    }

    public static String getFileNameFromPath(String str) {
        if (isFilePathEmpty(str)) {
            return "";
        }
        int iLastIndexOf = str.lastIndexOf(File.separator);
        return iLastIndexOf != -1 ? str.substring(iLastIndexOf + 1) : str;
    }

    public static String getFileNameWithoutExtension(String str) {
        if (isFilePathEmpty(str)) {
            return "";
        }
        int iLastIndexOf = str.lastIndexOf(46);
        int iLastIndexOf2 = str.lastIndexOf(File.separator);
        if (iLastIndexOf2 != -1) {
            return (iLastIndexOf == -1 || iLastIndexOf2 > iLastIndexOf) ? str.substring(iLastIndexOf2 + 1) : str.substring(iLastIndexOf2 + 1, iLastIndexOf);
        }
        return iLastIndexOf != -1 ? str.substring(0, iLastIndexOf) : str;
    }

    public static String getFileExtension(String str) {
        int iLastIndexOf;
        return (isFilePathEmpty(str) || (iLastIndexOf = str.lastIndexOf(46)) == -1 || str.lastIndexOf(File.separator) >= iLastIndexOf) ? "" : str.substring(iLastIndexOf + 1);
    }

    public static boolean isFilePathEmpty(String str) {
        if (str == null) {
            return true;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String getTempFilePath(String str) {
        File file = new File(str);
        String fileNameWithoutExtension = getFileNameWithoutExtension(str);
        String fileExtension = getFileExtension(str);
        String str2 = fileNameWithoutExtension + "tmp";
        return !TextUtils.isEmpty(fileExtension) ? file.getParent() + File.separator + str2 + "." + fileExtension : file.getParent() + File.separator + str2;
    }

    public static PointF getCopyPointF(PointF pointF) {
        return new PointF(pointF.x, pointF.y);
    }

    public static RectF getCopyRectF(RectF rectF) {
        return new RectF(rectF);
    }

    public static void processTempPointsAndUpdateBounds(DataCurve dataCurve, RectF rectF, List<CsPoint> list, float f) {
        for (CsPoint csPoint : list) {
            float x = csPoint.getX() * f;
            float y = csPoint.getY() * f;
            if (rectF.left >= rectF.right || rectF.top >= rectF.bottom || x < rectF.left || x >= rectF.right || y < rectF.top || y > rectF.bottom) {
                CsPoint csPointClonePointWithScale = clonePointWithScale(csPoint, f);
                dataCurve.insertPoint(dataCurve.mSavePoints, csPointClonePointWithScale.getX(), csPointClonePointWithScale.getY(), csPointClonePointWithScale.getPressure(), csPointClonePointWithScale.getTilt(), csPointClonePointWithScale.getOrientation(), csPointClonePointWithScale.getVelocity(), csPointClonePointWithScale.getTime(), (short) csPointClonePointWithScale.getDataState(), csPointClonePointWithScale.getRotateAngle());
                rectF.union(csPointClonePointWithScale.getX(), csPointClonePointWithScale.getY());
            }
        }
        list.clear();
    }

    /* JADX INFO: renamed from: xc.engine.utils.PenEngineUtils$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$xc$engine$core$graphics$pen$PenType;

        static {
            int[] iArr = new int[PenType.values().length];
            $SwitchMap$xc$engine$core$graphics$pen$PenType = iArr;
            try {
                iArr[PenType.MARK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$xc$engine$core$graphics$pen$PenType[PenType.WATER_COLOR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$xc$engine$core$graphics$pen$PenType[PenType.PENCIL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$xc$engine$core$graphics$pen$PenType[PenType.BALL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$xc$engine$core$graphics$pen$PenType[PenType.INK.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public static BasePenPropConfig getPenPropConfig(float f, PenType penType, float f2, float f3, float f4) {
        int i = AnonymousClass1.$SwitchMap$xc$engine$core$graphics$pen$PenType[penType.ordinal()];
        if (i == 1) {
            return new MarkPropConfig(f, f2);
        }
        if (i == 2) {
            return new WaterColorPropConfig(f, f2);
        }
        if (i == 3) {
            PencilPropConfig pencilPropConfig = new PencilPropConfig(f, f2);
            pencilPropConfig.mSensitivity = f3;
            pencilPropConfig.mTipSize = f4;
            return pencilPropConfig;
        }
        if (i == 4) {
            return new BallPropConfig(f, f2);
        }
        if (i == 5) {
            InkPropConfig inkPropConfig = new InkPropConfig(f, f2);
            inkPropConfig.mSensitivity = f3;
            inkPropConfig.mTipSize = f4;
            return inkPropConfig;
        }
        return new InkPropConfig(f, f2);
    }

    public static float getPenSpaceFactor(PenType penType) {
        int i = AnonymousClass1.$SwitchMap$xc$engine$core$graphics$pen$PenType[penType.ordinal()];
        if (i == 1) {
            return 0.02232f;
        }
        if (i == 2) {
            return 0.0818401f;
        }
        if (i == 3) {
            return 0.2862357f;
        }
        if (i != 4) {
            return i != 5 ? 0.2f : 0.22f;
        }
        return 0.194353f;
    }

    public static CsPoint clonePointWithScale(CsPoint csPoint, float f) {
        CsPoint csPoint2 = new CsPoint(csPoint);
        csPoint2.setX(csPoint2.getX() * f);
        csPoint2.setY(csPoint2.getY() * f);
        return csPoint2;
    }

    public static boolean renameFile(String str, String str2) {
        File file = !isFilePathEmpty(str) ? new File(str) : null;
        if (file == null || !file.exists() || isFilePathEmpty(str2) || str2.equals(file.getName())) {
            return false;
        }
        File file2 = new File(file.getParent() + File.separator + str2);
        return !file2.exists() && file.renameTo(file2);
    }

    public static void calculateShapeSavePoints(DataShape dataShape) {
        dataShape.clearPoints(dataShape.mSavePoints);
        switch (dataShape.mShapeProp.getShapeType()) {
            case 1:
            case 2:
                calculatePathSavePoints(dataShape.mShapePath, dataShape.mDrawRatio, dataShape, false);
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                break;
            default:
                return;
        }
        calculatePathSavePoints(dataShape.mShapePath, dataShape.mDrawRatio, dataShape, true);
    }

    public static void calculatePathSavePoints(Path path, float f, DataCurve dataCurve, boolean z, long j, float f2, float f3, float f4, float f5, float f6) {
        DataCurve dataCurve2 = dataCurve;
        Matrix matrix = new Matrix();
        Path path2 = new Path();
        float f7 = 1.0f / f;
        matrix.setScale(f7, f7);
        path.transform(matrix, path2);
        PathMeasure pathMeasure = new PathMeasure();
        pathMeasure.setPath(path2, false);
        float[] fArr = new float[2];
        float[] fArr2 = new float[2];
        float length = pathMeasure.getLength();
        if (z) {
            pathMeasure.getPosTan(0.0f, fArr, fArr2);
            PathMeasure pathMeasure2 = pathMeasure;
            float f8 = length;
            dataCurve2.insertPoint(dataCurve2.mSavePoints, fArr[0], fArr[1], f3, f4, f2, f5, j, (short) 1, f6);
            double dAtan2 = Math.atan2(fArr2[1], fArr2[0]);
            float f9 = fArr[0];
            float f10 = fArr[1];
            int i = 1;
            while (true) {
                float f11 = i;
                if (f11 >= f8) {
                    pathMeasure2.getPosTan(f8, fArr, fArr2);
                    dataCurve2.insertPoint(dataCurve2.mSavePoints, fArr[0], fArr[1], f3, f4, f2, f5, j, (short) 1, f6);
                    return;
                }
                PathMeasure pathMeasure3 = pathMeasure2;
                pathMeasure3.getPosTan(f11, fArr, fArr2);
                double dAtan3 = Math.atan2(fArr2[1], fArr2[0]);
                float f12 = fArr[0];
                float f13 = fArr[1];
                if (Math.abs(dAtan3 - dAtan2) > Math.toRadians(1.0d)) {
                    double d = dAtan2;
                    float fTan = (float) (((((double) (f13 - f10)) - (Math.tan(dAtan3) * ((double) f12))) + (Math.tan(d) * ((double) f9))) / (Math.tan(d) - Math.tan(dAtan3)));
                    float fTan2 = (float) (((double) ((float) ((((Math.tan(dAtan3) * Math.tan(d)) * ((double) (f12 - f9))) - (((double) f13) * Math.tan(d))) + (((double) f10) * Math.tan(dAtan3))))) / (Math.tan(dAtan3) - Math.tan(d)));
                    dataCurve2.insertPoint(dataCurve2.mSavePoints, fTan, fTan2, f3, f4, f2, f5, j, (short) 1, f6);
                    dataCurve2.insertPoint(dataCurve2.mSavePoints, fTan, fTan2, f3, f4, f2, f5, j, (short) 1, f6);
                } else {
                    dataCurve2.insertPoint(dataCurve2.mSavePoints, fArr[0], fArr[1], f3, f4, f2, f5, j, (short) 1, f6);
                }
                i += 4;
                f8 = f8;
                pathMeasure2 = pathMeasure3;
                dAtan2 = dAtan3;
                f9 = f12;
                f10 = f13;
            }
        } else {
            float f14 = length;
            pathMeasure.getPosTan(0.0f, fArr, fArr2);
            dataCurve2.insertPoint(dataCurve2.mSavePoints, fArr[0], fArr[1], f3, f4, f2, f5, j, (short) 1, f6);
            int i2 = 1;
            while (true) {
                float f15 = i2;
                if (f15 >= f14) {
                    pathMeasure.getPosTan(f14, fArr, fArr2);
                    dataCurve2.insertPoint(dataCurve2.mSavePoints, fArr[0], fArr[1], f3, f4, f2, f5, j, (short) 1, f6);
                    return;
                }
                pathMeasure.getPosTan(f15, fArr, fArr2);
                dataCurve2.insertPoint(dataCurve2.mSavePoints, fArr[0], fArr[1], f3, f4, f2, f5, j, (short) 1, f6);
                i2 += 4;
                dataCurve2 = dataCurve;
                f14 = f14;
            }
        }
    }

    public static void calculatePathSavePoints(Path path, float f, DataCurve dataCurve, boolean z) {
        calculatePathSavePoints(path, f, dataCurve, z, 0L, 0.0f, 0.5f, 0.0f, 0.0f, 0.0f);
    }

    public static boolean isSmallPad() {
        return Build.DEVICE.equals("spark") || Build.DEVICE.equals("flare");
    }
}
