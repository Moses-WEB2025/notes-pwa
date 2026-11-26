package xc.engine.data.shape;

/* JADX INFO: loaded from: classes6.dex */
public class ShapeType {
    public static final int ARROW = 2;
    public static final int BIGARROW = 7;
    public static final int CIRCLE = 5;
    private static final float DEFAULT_SCALE_MAX = 1346.3f;
    private static final float DEFAULT_SCALE_MIN = 12.0f;
    public static final int LINE = 1;
    private static final float LINE_SCALE_MIN = 22.0f;
    public static final int RECT = 3;
    public static final int STAR = 6;
    public static final int TRIANGLE = 4;

    public static float getShapeScaleLimitMax() {
        return DEFAULT_SCALE_MAX;
    }

    public static float getShapeScaleLimitMin(boolean z) {
        if (z) {
            return LINE_SCALE_MIN;
        }
        return 12.0f;
    }

    public static int getShapeType(byte b) {
        if (b <= 7) {
            return b;
        }
        return 2;
    }
}
