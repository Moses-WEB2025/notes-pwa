package com.miui.notes.ai.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Build;
import android.view.DisplayCutout;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import com.miui.notes.base.utils.Logger;
import java.lang.reflect.Field;
import miuix.core.util.MiuixUIUtils;
import miuix.core.util.SystemProperties;
import miuix.core.util.WindowUtils;

/* JADX INFO: loaded from: classes4.dex */
public class UIUtils {
    private static int lastButtonId;
    public static long lastClickTime;

    public static boolean isMiuiXIISdkSupported() {
        return getMiuiVersion() >= 10;
    }

    public static int getMiuiVersion() {
        return SystemProperties.getInt("ro.miui.ui.version.code", 0);
    }

    public static boolean isSmallWindowMode(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        try {
            Field declaredField = configuration.getClass().getDeclaredField("windowConfiguration");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(configuration);
            return ((Integer) obj.getClass().getDeclaredMethod("getWindowingMode", new Class[0]).invoke(obj, new Object[0])).intValue() == 5;
        } catch (Exception e) {
            Logger.INSTANCE.d("SmallWindowUtils", "error in isSmallWindowMode " + e.toString());
        }
    }

    public static boolean isInNarrowScreen(Context context) {
        int[] screenSizeDp = getScreenSizeDp(context);
        int iMin = Math.min(screenSizeDp[0], screenSizeDp[1]);
        Logger.INSTANCE.d("isInNarrowScreen", "realWidth:" + iMin);
        return iMin <= 394;
    }

    public static int[] getScreenSizeDp(Context context) {
        return MiuixUIUtils.getScreenSizeDp(context);
    }

    public static void adaptationFoldDevice(Activity activity) {
        WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
        attributes.layoutInDisplayCutoutMode = 1;
        activity.getWindow().setAttributes(attributes);
    }

    public static boolean isFastClick(int i, long j) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j2 = lastClickTime;
        long j3 = jCurrentTimeMillis - j2;
        if (i == -1 && j2 > 0 && Math.abs(j3) < j) {
            return true;
        }
        if (lastButtonId == i && lastClickTime > 0 && Math.abs(j3) < j) {
            return true;
        }
        lastClickTime = jCurrentTimeMillis;
        lastButtonId = i;
        return false;
    }

    public static int getWindowWidth(Context context) {
        WindowMetrics currentWindowMetrics;
        Rect bounds = (Build.VERSION.SDK_INT < 30 || (currentWindowMetrics = ((WindowManager) context.getSystemService("window")).getCurrentWindowMetrics()) == null) ? null : currentWindowMetrics.getBounds();
        if (bounds != null) {
            return bounds.width();
        }
        return 0;
    }

    public static int getNavigationBarHeightByWindow(Context context, View view) {
        int navigationBarHeight = 0;
        if (view != null && Build.VERSION.SDK_INT >= 30) {
            WindowInsets rootWindowInsets = view.getRootWindowInsets();
            navigationBarHeight = rootWindowInsets != null ? rootWindowInsets.getInsets(WindowInsets.Type.navigationBars()).bottom : 0;
            DisplayCutout cutout = WindowUtils.getDisplay(context).getCutout();
            if (cutout != null) {
                if (cutout.getBoundingRectBottom().height() > 0) {
                    navigationBarHeight += cutout.getBoundingRectBottom().height();
                } else if (cutout.getBoundingRectRight().width() > 0 && navigationBarHeight - cutout.getBoundingRectRight().width() > 0) {
                    navigationBarHeight -= cutout.getBoundingRectRight().width();
                }
            }
        }
        if (navigationBarHeight == 0) {
            navigationBarHeight = MiuixUIUtils.getNavigationBarHeight(context);
        }
        Logger.INSTANCE.i("systemNavigationBar", "getNavigationBarHeightByWindow = " + navigationBarHeight);
        return navigationBarHeight;
    }
}
