package xc.engine.data.curve;

import xc.engine.cbridge.MiCurveNative;

/* JADX INFO: loaded from: classes6.dex */
public class CurvePointListOperator {
    public static boolean isIndependent = true;

    public static void setPointState(long j, int i, short s) {
    }

    public static long createLine() {
        return MiCurveNative.createLine(0L);
    }

    public static long createLine(long j) {
        return MiCurveNative.createLine(j);
    }

    public static void releaseLine(long j) {
        MiCurveNative.releaseLine(j);
    }

    public static ICurvePointList newCurvePointList(long j) {
        return new CurvePointList(j);
    }

    public static ICurvePointPosition createCurvePointsPosition(long j) {
        return new CurvePointPosition(j);
    }

    public static void clearLine(long j) {
        MiCurveNative.clearLine(j);
    }

    public static int size(long j) {
        return MiCurveNative.size(j);
    }

    public static void getLine(long j, float[] fArr, float[] fArr2, short[] sArr, short[] sArr2, int[] iArr, short[] sArr3, short[] sArr4, float[] fArr3, short[] sArr5) {
        MiCurveNative.getLine(j, fArr, fArr2, sArr, sArr2, iArr, sArr3, sArr4, fArr3, sArr5);
    }

    public static void addLine(long j, long j2) {
        MiCurveNative.addLine(j, j2);
    }

    public static void insertPoints(long j, int i, float[] fArr, float[] fArr2, float[] fArr3, float[] fArr4, long[] jArr, float[] fArr5, float[] fArr6, float[] fArr7, short[] sArr) {
        if (sArr != null && sArr.length > 0) {
            insert(j, fArr, fArr2, fArr3, fArr4, jArr, fArr5, fArr6, fArr7, sArr, i);
        } else {
            insert(j, fArr, fArr2, fArr3, i);
        }
    }

    public static void insertPoint(long j, float f, float f2, float f3, float f4, long j2, float f5, float f6, short s, float f7) {
        MiCurveNative.insertPoint(j, f, f2, (short) (f3 * 100.0f), (short) (f4 * 100.0f), (int) j2, (short) (f5 * 100.0f), (short) (100.0f * f6), f7, s);
    }

    private static void insert(long j, float[] fArr, float[] fArr2, float[] fArr3, float[] fArr4, long[] jArr, float[] fArr5, float[] fArr6, float[] fArr7, short[] sArr, int i) {
        short[] sArr2 = new short[i];
        short[] sArr3 = new short[i];
        short[] sArr4 = new short[i];
        short[] sArr5 = new short[i];
        int[] iArr = new int[i];
        for (int i2 = 0; i2 < i; i2++) {
            sArr2[i2] = (short) (fArr3[i2] * 100.0f);
            sArr3[i2] = (short) (fArr4[i2] * 100.0f);
            sArr4[i2] = (short) (fArr5[i2] * 100.0f);
            sArr5[i2] = (short) (fArr6[i2] * 100.0f);
            iArr[i2] = (int) jArr[i2];
        }
        MiCurveNative.insertPoints(j, i, fArr, fArr2, sArr2, sArr3, iArr, sArr4, sArr5, fArr7, sArr);
    }

    public static void insertPoints(long j, int i, float[] fArr, float[] fArr2, short[] sArr, short[] sArr2, int[] iArr, short[] sArr3, short[] sArr4, short[] sArr5, float[] fArr3) {
        MiCurveNative.insertPoints(j, i, fArr, fArr2, sArr, sArr2, iArr, sArr3, sArr4, fArr3, sArr5);
    }

    private static void insert(long j, float[] fArr, float[] fArr2, float[] fArr3, int i) {
        short[] sArr = new short[i];
        for (int i2 = 0; i2 < i; i2++) {
            sArr[i2] = (short) (fArr3[i2] * 100.0f);
        }
        MiCurveNative.insertPoints(j, i, fArr, fArr2, sArr, new short[i], new int[i], new short[i], new short[i], new float[i], new short[i]);
    }

    public static void setPointsX(long j, float[] fArr, int i, int i2, int i3) {
        MiCurveNative.setPointX(j, fArr, i2, i3);
    }

    public static void setPointsY(long j, float[] fArr, int i, int i2, int i3) {
        MiCurveNative.setPointY(j, fArr, i2, i3);
    }

    public static void setPointsState(long j, short[] sArr, int i, int i2) {
        if (isIndependent) {
            MiCurveNative.setDataStatus(j, sArr, i, i2);
        }
    }

    public static void resetPointsState(long j) {
        if (isIndependent) {
            MiCurveNative.resetDataStatus(j);
        }
    }

    public static void setPointsRotateAngle(long j, float[] fArr, int i, int i2) {
        if (isIndependent) {
            MiCurveNative.setRotateAngle(j, fArr, i, i2);
        }
    }
}
