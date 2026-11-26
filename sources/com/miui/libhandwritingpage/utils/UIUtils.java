package com.miui.libhandwritingpage.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import androidx.appfunctions.internal.AppSearchAppFunctionReader;
import com.miui.floatwindow.feature.root.BaseFloatFullWindow;
import defpackage.R2;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import miuix.core.util.SystemProperties;
import xc.engine.api.CreationPageSDK;
import xc.engine.utils.DisplayUtils;

/* JADX INFO: loaded from: classes3.dex */
public class UIUtils {
    private static String MIUI_DESKTOP_MODE = "miui_dkt_mode";
    private static final String PROP_NAV_BAR_HEIGHT = "navigation_bar_height";
    public static final int RESPONSIVE_LAYOUT_FULL = 1100;
    public static final int RESPONSIVE_LAYOUT_HALF = 1102;
    public static final int RESPONSIVE_LAYOUT_ONE_THIRD = 1101;
    public static final int RESPONSIVE_LAYOUT_TWO_THIRD = 1103;
    public static final float SCREEN_SCALE_RATIO_0525F = 0.525f;
    public static final float SCREEN_SCALE_RATIO_075F = 0.75f;
    public static final float SCREEN_SCALE_RATIO_07F = 0.7f;
    public static final float SCREEN_SCALE_RATIO_1F = 1.0f;
    private static final String TAG = "UIUtils";
    private static final Point sScreenRealSize = new Point();
    private static final Point sWindowSize = new Point();

    private static boolean isInRegion(float f, float f2, float f3) {
        return f >= f2 && f < f3;
    }

    public static float mm2px(Context context, float f) {
        return (f / 25.4f) * context.getResources().getDisplayMetrics().xdpi;
    }

    public static float px2mm(Context context, float f) {
        return (f / context.getResources().getDisplayMetrics().xdpi) * 25.4f;
    }

    public static int dip2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int getRealHeight(Context context) {
        if (isSmallWindowMode(context)) {
            return getDisplayMetricsInSmallWindow(context).heightPixels;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static int getRealWidth(Context context) {
        if (isSmallWindowMode(context)) {
            return getDisplayMetricsInSmallWindow(context).widthPixels;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static boolean isSmallWindowMode(Context context) {
        if (getWindowMode(context) == 5) {
            Log.d(TAG, "isSmallWindowMode is true");
            return true;
        }
        Log.d(TAG, "isSmallWindowMode is false");
        return false;
    }

    public static int getWindowMode(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        int iIntValue = 0;
        try {
            Field declaredField = configuration.getClass().getDeclaredField("windowConfiguration");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(configuration);
            iIntValue = ((Integer) obj.getClass().getDeclaredMethod("getWindowingMode", new Class[0]).invoke(obj, new Object[0])).intValue();
        } catch (Exception e) {
            Log.e(TAG, "getWindowMode error" + e);
        }
        Log.d(TAG, "getWindowMode " + iIntValue);
        return iIntValue;
    }

    public static void setFullScreenMode(Activity activity, boolean z) {
        if (activity == null) {
            return;
        }
        int systemUiVisibility = activity.getWindow().getDecorView().getSystemUiVisibility();
        activity.getWindow().getDecorView().setSystemUiVisibility(z ? systemUiVisibility | R2.color.theme_default_cursor_color : systemUiVisibility & (-5893));
    }

    private static DisplayMetrics getDisplayMetricsInSmallWindow(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        try {
            Display defaultDisplay = windowManager.getDefaultDisplay();
            Class<?> cls = defaultDisplay.getClass();
            Class<?> cls2 = Class.forName("android.view.DisplayInfo");
            Method declaredMethod = cls.getDeclaredMethod("getDisplayInfo", cls2);
            Object objNewInstance = cls2.newInstance();
            declaredMethod.invoke(defaultDisplay, objNewInstance);
            Class<?> cls3 = displayMetrics.getClass();
            Class<?> cls4 = Class.forName("android.content.res.CompatibilityInfo");
            Field declaredField = cls4.getDeclaredField("DEFAULT_COMPATIBILITY_INFO");
            declaredField.setAccessible(true);
            MetaReflectUtil.callObjectMethod(objNewInstance, "getLogicalMetrics", new Class[]{cls3, cls4, Configuration.class}, displayMetrics, declaredField.get(null), null);
            return displayMetrics;
        } catch (Exception e) {
            Log.e(TAG, "error in getDisplayMetricsInSmallWindow " + e);
            windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
            return displayMetrics;
        }
    }

    public static boolean isInMultiWindowMode(Activity activity) {
        if (activity == null) {
            return false;
        }
        try {
            Method declaredMethod = Activity.class.getDeclaredMethod("isInMultiWindowMode", new Class[0]);
            declaredMethod.setAccessible(true);
            return ((Boolean) declaredMethod.invoke(activity, new Object[0])).booleanValue();
        } catch (IllegalAccessException e) {
            Log.d(TAG, e.getMessage());
            return false;
        } catch (NoSuchMethodException e2) {
            Log.d(TAG, e2.getMessage());
            return false;
        } catch (InvocationTargetException e3) {
            Log.d(TAG, e3.getMessage());
            return false;
        }
    }

    public static boolean isInSplitScreenMode(Context context) {
        int windowMode = getWindowMode(context);
        return (windowMode == 1 || windowMode == 5) ? false : true;
    }

    public static int getAppHeight(Window window) {
        Rect rect = new Rect();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.height();
    }

    public static int getNavigationBarHeight(Context context) {
        int navigationBarHeightFromProp;
        if (isInFullWindowGestureMode(context)) {
            navigationBarHeightFromProp = (DisplayUtils.isSupportGestureLine(context) && DisplayUtils.isEnableGestureLine(context)) ? DisplayUtils.getNavigationBarHeightFromProp(context) : 0;
        } else {
            navigationBarHeightFromProp = DisplayUtils.getNavigationBarHeightFromProp(context);
        }
        int i = navigationBarHeightFromProp >= 0 ? navigationBarHeightFromProp : 0;
        Log.i(TAG, "getNavigationBarHeight = " + i);
        return i;
    }

    public static boolean isInFullWindowGestureMode(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), BaseFloatFullWindow.FORCE_FSG_NAV_BAR, 0) != 0;
    }

    public static boolean isActiveDekstopMode(Context context) {
        if (!isSupportMiuiDekstopModeByFK()) {
            return false;
        }
        try {
            return Settings.System.getInt(context.getContentResolver(), MIUI_DESKTOP_MODE) != 0;
        } catch (Settings.SettingNotFoundException unused) {
        }
    }

    public static boolean isSupportMiuiDekstopModeByFK() {
        return SystemProperties.getBoolean("ro.config.miui_desktop_mode_enabled", false);
    }

    public static boolean isFull(Context context) {
        return detectScreenMode(context) == 1100;
    }

    /* JADX WARN: Code duplicated, block: B:17:0x006b  */
    /* JADX WARN: Code duplicated, block: B:19:0x0073  */
    /* JADX WARN: Code duplicated, block: B:21:0x007c  */
    /* JADX WARN: Code duplicated, block: B:23:0x0084  */
    /* JADX WARN: Code duplicated, block: B:25:0x008d  */
    /* JADX WARN: Code duplicated, block: B:27:0x0095  */
    public static int detectScreenMode(Context context) {
        float f;
        float f2;
        int i;
        if (Build.VERSION.SDK_INT >= 30) {
            context.getDisplay().getRealSize(sScreenRealSize);
        }
        if (Build.VERSION.SDK_INT >= 30) {
            context.getDisplay().getSize(sWindowSize);
        }
        StringBuilder sb = new StringBuilder("sWindowSize is ");
        Point point = sWindowSize;
        StringBuilder sbAppend = sb.append(point).append(" sScreenRealSize is ");
        Point point2 = sScreenRealSize;
        Log.d(TAG, sbAppend.append(point2).toString());
        if (isLandscape()) {
            f2 = point.x;
            i = point2.x;
        } else {
            f = point.x / (point2.x + 0.0f);
            if (f == 1.0f) {
                f2 = point.y;
                i = point2.y;
            }
            if (isInRegion(f, 0.0f, 0.4f)) {
                Log.d(TAG, "screenMode is 1/3");
                return 1101;
            }
            if (isInRegion(f, 0.4f, 0.6f)) {
                Log.d(TAG, "screenMode is 1/2");
                return 1102;
            }
            if (isInRegion(f, 0.6f, 0.8f)) {
                Log.d(TAG, "screenMode is 2/3");
                return 1103;
            }
            Log.d(TAG, "screenMode is full");
            return 1100;
        }
        f = f2 / (i + 0.0f);
        if (isInRegion(f, 0.0f, 0.4f)) {
            Log.d(TAG, "screenMode is 1/3");
            return 1101;
        }
        if (isInRegion(f, 0.4f, 0.6f)) {
            Log.d(TAG, "screenMode is 1/2");
            return 1102;
        }
        if (isInRegion(f, 0.6f, 0.8f)) {
            Log.d(TAG, "screenMode is 2/3");
            return 1103;
        }
        Log.d(TAG, "screenMode is full");
        return 1100;
    }

    private static boolean isLandscape() {
        Point point = sScreenRealSize;
        return point.x > point.y;
    }

    public static boolean isNightMode() {
        return (CreationPageSDK.sApplication.getResources().getConfiguration().uiMode & 48) == 32;
    }

    public static String getLocale(Context context) {
        if (context == null) {
            return Locale.getDefault().toString();
        }
        return context.getResources().getConfiguration().getLocales().toString();
    }

    public static int getWindowWidth(Context context) {
        return ((WindowManager) context.getSystemService("window")).getCurrentWindowMetrics().getBounds().width();
    }

    public static int getWindowHeight(Context context) {
        return ((WindowManager) context.getSystemService("window")).getCurrentWindowMetrics().getBounds().height();
    }

    public static boolean isFreeFormScreen(Configuration configuration) {
        return configuration != null && configuration.toString().contains("mWindowingMode=freeform");
    }

    public static int getWindowScreenOrientation(Context context) {
        return getWindowWidth(context) <= getWindowHeight(context) ? 1 : 2;
    }

    public static int getRealScreenOrientation(Context context) {
        return getRealWidth(context) <= getRealHeight(context) ? 1 : 2;
    }

    public static int px2dip(Context context, float f) {
        return (int) ((f / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static String bitmapToPNG(Bitmap bitmap, String str, String str2) {
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }
        File file = new File(str);
        if (file.mkdirs()) {
            Log.d("BitmapUtil", str + " dir is already exist");
        }
        File file2 = new File(file, str2);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            bitmap.compress(Bitmap.CompressFormat.PNG, 70, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return file2.getAbsolutePath();
    }

    public static float getToolDimen(float f) {
        return RomUtils.isLargePad() ? (float) (((double) f) * 1.18d) : f;
    }

    public static int getStatusBarHeight(Context context) {
        try {
            Class<?> cls = Class.forName("com.android.internal.R$dimen");
            return context.getResources().getDimensionPixelSize(Integer.parseInt(cls.getField("status_bar_height").get(cls.newInstance()).toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int getNavigationBarHeightFromProp(Context context) {
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier(PROP_NAV_BAR_HEIGHT, "dimen", AppSearchAppFunctionReader.SYSTEM_PACKAGE_NAME);
        if (identifier > 0) {
            return resources.getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public static boolean isRTL() {
        return TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
    }

    public static int getScreenOrientation(Activity activity) {
        if (activity == null) {
            return 1;
        }
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        if (((rotation == 0 || rotation == 2) && i2 > i) || ((rotation == 1 || rotation == 3) && i > i2)) {
            if (rotation == 0) {
                return 1;
            }
            if (rotation == 1) {
                return 0;
            }
            if (rotation == 2) {
                return 9;
            }
            if (rotation == 3) {
                return 8;
            }
            Log.e(TAG, "Unknown screen orientation. Defaulting toportrait.");
            return 1;
        }
        if (rotation == 0) {
            return 0;
        }
        if (rotation == 1) {
            return 1;
        }
        if (rotation == 2) {
            return 8;
        }
        if (rotation == 3) {
            return 9;
        }
        Log.e(TAG, "Unknown screen orientation. Defaulting tolandscape.");
        return 0;
    }

    public static boolean isAboveAndroidS() {
        return Build.VERSION.SDK_INT >= 31;
    }

    public static DisplayMetrics getRealScreenSize(Context context) {
        if (isSmallWindowMode(context)) {
            return getDisplayMetricsInSmallWindow(context);
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics;
    }

    public static int getSmallModeHeight(Window window) {
        Rect rect = new Rect();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        int i = rect.bottom;
        Log.i(TAG, "getSmallModeHeight=" + i);
        return i;
    }

    public static float getSystemScaleText(Context context) {
        float f = context.getResources().getConfiguration().fontScale;
        Log.i(TAG, "getSystemScaleText setting,fontScale=" + f);
        return f;
    }

    public static DisplayMetrics getWorkbenchesScreenSize(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        if (isSmallWindowMode(context)) {
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            return displayMetrics;
        }
        windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics;
    }

    public static boolean isFullWindowMode(Context context) {
        if (getWindowMode(context) == 1) {
            Log.d(TAG, "isFullWindow is true");
            return true;
        }
        Log.d(TAG, "isFullWindow is false");
        return false;
    }
}
