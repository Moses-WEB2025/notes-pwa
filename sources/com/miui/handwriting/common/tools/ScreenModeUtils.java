package com.miui.handwriting.common.tools;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import com.miui.handwriting.common.HandWritingViewApplication;

/* JADX INFO: loaded from: classes3.dex */
public class ScreenModeUtils {
    public static final int RESPONSIVE_LAYOUT_FULL = 1100;
    public static final int RESPONSIVE_LAYOUT_HALF = 1102;
    public static final int RESPONSIVE_LAYOUT_ONE_THIRD = 1101;
    public static final int RESPONSIVE_LAYOUT_TWO_THIRD = 1103;
    public static final float SCREEN_SCALE_RATIO_0525F = 0.525f;
    public static final float SCREEN_SCALE_RATIO_075F = 0.75f;
    public static final float SCREEN_SCALE_RATIO_07F = 0.7f;
    public static final float SCREEN_SCALE_RATIO_1F = 1.0f;
    public static final String TAG = "ScreenModeUtils";
    private static final Point sScreenRealSize = new Point();
    private static final Point sWindowSize = new Point();

    private static boolean isInRegion(float f, float f2, float f3) {
        return f >= f2 && f < f3;
    }

    private static boolean isLandscape() {
        Point point = sScreenRealSize;
        return point.x > point.y;
    }

    public static int detectScreenMode(Context context) {
        try {
            Display display = context.getDisplay();
            Point point = sScreenRealSize;
            display.getRealSize(point);
            Display display2 = context.getDisplay();
            Point point2 = sWindowSize;
            display2.getSize(point2);
            Log.d(TAG, "sWindowSize is " + point2 + " sScreenRealSize is " + point);
            float fMin = Math.min(point2.x / (point.x + 0.0f), point2.y / (point.y + 0.0f));
            if (isInRegion(fMin, 0.0f, 0.4f)) {
                Log.d(TAG, "screenMode is 1/3 " + fMin);
                return 1101;
            }
            if (isInRegion(fMin, 0.4f, 0.6f)) {
                Log.d(TAG, "screenMode is 1/2 " + fMin);
                return 1102;
            }
            if (isInRegion(fMin, 0.6f, 0.8f)) {
                Log.d(TAG, "screenMode is 2/3 " + fMin);
                return 1103;
            }
            Log.d(TAG, "screenMode is full " + fMin);
            return 1100;
        } catch (Exception e) {
            Log.e(TAG, "detectScreenMode Error :" + e);
            return 1100;
        }
    }

    public static boolean isOneThird(Context context) {
        return detectScreenMode(context) == 1101;
    }

    public static boolean isHalf(Context context) {
        return detectScreenMode(context) == 1102;
    }

    public static boolean isTwoThird(Context context) {
        return detectScreenMode(context) == 1103;
    }

    public static boolean isFull(Context context) {
        return detectScreenMode(context) == 1100;
    }

    public static boolean isWidthFull(Context context) {
        Point point = new Point();
        context.getDisplay().getRealSize(point);
        Point point2 = new Point();
        context.getDisplay().getSize(point2);
        Log.d(TAG, "windowSize is " + point2 + " screenSize is " + point);
        if (point2.x != point.x) {
            return false;
        }
        Log.d(TAG, "window width is equal to screen width");
        return true;
    }

    public static boolean isLandScape(Context context) {
        if (context == null) {
            context = HandWritingViewApplication.INSTANCE.getApplication();
        }
        return context.getResources().getConfiguration().orientation == 2;
    }

    public static boolean isPortHalf(Context context) {
        return isHalf(context) && !isLandscape();
    }

    public static Boolean isLandscapeOrientation(Context context) {
        return Boolean.valueOf(context.getResources().getConfiguration().orientation == 2);
    }

    public static float getScreenScale(Context context) {
        if (isFull(context)) {
            return com.miui.libhandwritingpage.utils.UIUtils.getRealScreenOrientation(context) == 2 ? 1.0f : 0.75f;
        }
        if (!com.miui.libhandwritingpage.utils.UIUtils.isActiveDekstopMode(context) && com.miui.libhandwritingpage.utils.UIUtils.isSmallWindowMode(context)) {
            return 0.525f;
        }
        if (isTwoThird(context)) {
            if (isBetweenRegion(context, 0.6f, 0.75f)) {
                return (!(com.miui.libhandwritingpage.utils.UIUtils.isActiveDekstopMode(context) && isBetweenRegion(context, 0.71f, 0.75f)) && com.miui.libhandwritingpage.utils.UIUtils.isActiveDekstopMode(context)) ? 0.75f : 1.0f;
            }
            return 1.0f;
        }
        if (isOneThird(context)) {
            if (!isLandscape()) {
                Point point = sWindowSize;
                float f = point.x;
                Point point2 = sScreenRealSize;
                if (f / (point2.x + 0.0f) > 0.96f || (point.x / (point2.x + 0.0f) > 0.93f && point.y / (point2.y + 0.0f) > 0.35f)) {
                    return 0.75f;
                }
            }
            return 0.525f;
        }
        if (com.miui.libhandwritingpage.utils.UIUtils.getRealScreenOrientation(context) == 2 && isHalf(context) && !com.miui.libhandwritingpage.utils.UIUtils.isSmallWindowMode(context)) {
            return 0.75f;
        }
        if (!isLandscape()) {
            Point point3 = sWindowSize;
            float f2 = point3.x;
            Point point4 = sScreenRealSize;
            if (f2 / (point4.x + 0.0f) > 0.96f || (point3.x / (point4.x + 0.0f) > 0.9f && point3.y / (point4.y + 0.0f) > 0.35f)) {
                return 0.75f;
            }
        }
        return 0.525f;
    }

    private static boolean isBetweenRegion(Context context, float f, float f2) {
        Display display = context.getDisplay();
        Point point = sScreenRealSize;
        display.getRealSize(point);
        Display display2 = context.getDisplay();
        Point point2 = sWindowSize;
        display2.getSize(point2);
        float fMin = Math.min(point2.x / (point.x + 0.0f), point2.y / (point.y + 0.0f));
        Log.d(TAG, "isBetweenRegion sWindowSize is " + point2 + " sScreenRealSize is " + point + ",majorScale=" + fMin + ",start=" + f + ",end=" + f2);
        return isInRegion(fMin, f, f2);
    }
}
