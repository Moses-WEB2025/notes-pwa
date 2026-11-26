package xc.engine.utils;

/* JADX INFO: loaded from: classes6.dex */
public class PressureUtil {
    public static float getEraserSizeByPressure(float f, float f2) {
        return f + (f2 * f);
    }

    public static float getEraserSizeByPressureForView(float f, float f2) {
        float f3 = f * 1.15f;
        return f3 + (f2 * f3);
    }
}
