package xc.engine.cbridge;

/* JADX INFO: loaded from: classes6.dex */
public class MiCurveNative {
    public static native void addLine(long j, long j2);

    public static native void clearLine(long j);

    public static native long createLine(long j);

    public static native void duplicatePoints(long j, long j2);

    public static native void getLine(long j, float[] fArr, float[] fArr2, short[] sArr, short[] sArr2, int[] iArr, short[] sArr3, short[] sArr4, float[] fArr3, short[] sArr5);

    public static native int getNativeCurvePoolSize();

    public static native void insertPoint(long j, float f, float f2, short s, short s2, int i, short s3, short s4, float f3, short s5);

    public static native void insertPoints(long j, int i, float[] fArr, float[] fArr2, short[] sArr, short[] sArr2, int[] iArr, short[] sArr3, short[] sArr4, float[] fArr3, short[] sArr5);

    public static native void interpolateCurve(long j, long j2, int i, float f, float f2);

    public static native float nativeCalculateBrushAlpha(float[] fArr, float f);

    public static native float nativeCalculateBrushSize(float[] fArr, float f, float f2);

    public static native void nativeDebugPrintCurvePoints(long j, int i);

    public static native void nativeDrawVertexBuffer(long j);

    public static native void nativeDrawVertexBufferReuse(long j);

    public static native long nativeGenCurveVertexBuffer(long j, float[] fArr, long j2, boolean z);

    public static native long nativeGenCurveVertexBufferRanged(long j, float[] fArr, int i, int i2);

    public static native void nativeReleaseVertexBuffer(long j);

    public static native void releaseLine(long j);

    public static native void resetDataStatus(long j);

    public static native void setDataStatus(long j, short[] sArr, int i, int i2);

    public static native void setPointX(long j, float[] fArr, int i, int i2);

    public static native void setPointY(long j, float[] fArr, int i, int i2);

    public static native void setRotateAngle(long j, float[] fArr, int i, int i2);

    public static native int size(long j);

    static {
        System.loadLibrary("micurve");
    }
}
