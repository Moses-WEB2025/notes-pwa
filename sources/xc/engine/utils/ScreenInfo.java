package xc.engine.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import java.math.BigDecimal;
import java.math.RoundingMode;

/* JADX INFO: loaded from: classes6.dex */
public class ScreenInfo {
    public final float mDensity;
    public final float mDiagonalLength;
    public final int mHeightPixels;
    public final float mPhysicalSize;
    public final int mWidthPixels;
    public final float INCH_TO_MM = 25.4f;
    public final float DEFAULT_SCAL = 1.5f;

    public ScreenInfo(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        displayMetrics.setTo(context.getResources().getDisplayMetrics());
        this.mDensity = Math.min(displayMetrics.xdpi, displayMetrics.ydpi) / 254.0f;
        this.mPhysicalSize = (25.4f / displayMetrics.xdpi) * 10.0f;
        int i = displayMetrics.widthPixels;
        this.mWidthPixels = i;
        int i2 = displayMetrics.heightPixels;
        this.mHeightPixels = i2;
        float f = i / displayMetrics.xdpi;
        float f2 = i2 / displayMetrics.ydpi;
        this.mDiagonalLength = roundToDecimalPlaces((Math.sqrt((i * i) + (i2 * i2)) / Math.sqrt((f * f) + (f2 * f2))) / 25.399999618530273d, 2);
    }

    public float a(float f, float f2) {
        return (f / this.mDensity) * f2;
    }

    public float getScaleInterval(int i) {
        return i * this.mDiagonalLength;
    }

    public final float roundToDecimalPlaces(double d, int i) {
        return new BigDecimal(d).setScale(i, RoundingMode.HALF_UP).floatValue();
    }
}
