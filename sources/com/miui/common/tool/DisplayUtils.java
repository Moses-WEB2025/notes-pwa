package com.miui.common.tool;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.appfunctions.internal.AppSearchAppFunctionReader;
import com.miui.notes.NoteApp;
import com.miui.richeditor.constant.MiuiFontName;
import com.miui.todo.utils.MetaReflectUtil;
import com.xiaomi.mirror.synergy.CallMethod;
import defpackage.R2;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import miuix.core.util.MiuixUIUtils;
import miuix.core.util.SystemProperties;

/* JADX INFO: loaded from: classes2.dex */
public class DisplayUtils {
    private static final int DEF_KEY_MIUI_FONT_WEIGHT_SCALE = 50;
    private static final float EPS = 1.0E-7f;
    private static final double EPSF = 1.0E-7d;
    private static final String HIDE_GESTURE_LINE = "hide_gesture_line";
    private static final String TAG = "DisplayUtils";
    private static final String USE_GESTURE_VERSION_THREE = "use_gesture_version_three";
    public static final Typeface FONT_MIUI_BOLD = Typeface.create("miui-bold", 0);
    public static final Typeface FONT_MIUI_REGULAR = Typeface.create("miui-regular", 0);
    public static final Typeface FONT_MIPRO_MEDIUM = Typeface.create(MiuiFontName.NORMAL, 0);
    public static final Typeface FONT_MIPRO_REGULAR = Typeface.create(MiuiFontName.REGULAR, 0);
    public static final Typeface FONT_MIPRO_BOLD = Typeface.create(MiuiFontName.SEMIBOLD, 0);
    public static final Typeface FONT_MIPRO_DEMIBOLD = Typeface.create("mipro-demibold", 1);
    private static final TypedValue mTmpValue = new TypedValue();

    public static float clamp(float f, float f2, float f3) {
        if (f > f3) {
            return f3;
        }
        return f < f2 ? f2 : f;
    }

    public static int[] getScreenSizeDp(Context context) {
        return MiuixUIUtils.getScreenSizeDp(context);
    }

    public static DisplayMetrics getRealScreenSize(Context context) {
        if (UIUtils.isSmallWindowMode()) {
            return getDisplayMetricsInSmallWindow(context);
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics;
    }

    public static int getRealHeight(Context context) {
        if (UIUtils.isSmallWindowMode()) {
            return getDisplayMetricsInSmallWindow(context).heightPixels;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static int getRealWidth(Context context) {
        if (UIUtils.isSmallWindowMode()) {
            return getDisplayMetricsInSmallWindow(context).widthPixels;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int getMultiHeight(Activity activity) {
        WindowManager windowManager = (WindowManager) activity.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static int getMultiWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int getRealScreenOrientation(Context context) {
        return getRealWidth(context) <= getRealHeight(context) ? 1 : 2;
    }

    public static float getMultiRatio(Activity activity) {
        float multiHeight;
        int realHeight;
        boolean zIsInMultiWindowMode = isInMultiWindowMode(activity);
        Logger.INSTANCE.d("Display", " isInMultiWindowMode = " + zIsInMultiWindowMode);
        if (zIsInMultiWindowMode && getRealHeight(activity) < getRealWidth(activity)) {
            multiHeight = getRealHeight(activity);
            realHeight = getRealWidth(activity);
        } else {
            multiHeight = getMultiHeight(activity);
            realHeight = getRealHeight(activity);
        }
        return multiHeight / realHeight;
    }

    public static boolean isInMultiWindowMode(Activity activity) {
        return activity.isInMultiWindowMode();
    }

    public static DisplayMetrics getDisplayMetricsInSmallWindow(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        try {
            Display defaultDisplay = ((WindowManager) NoteApp.getInstance().getSystemService("window")).getDefaultDisplay();
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
            Logger.INSTANCE.e(TAG, "error in getDisplayMetricsInSmallWindow " + e);
            ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
            return displayMetrics;
        }
    }

    public static int getStatusBarHeight(Context context) {
        if (Build.VERSION.SDK_INT >= 30) {
            return ((WindowManager) context.getSystemService("window")).getCurrentWindowMetrics().getWindowInsets().getInsets(WindowInsets.Type.systemBars()).top;
        }
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
        int identifier = resources.getIdentifier("navigation_bar_height", "dimen", AppSearchAppFunctionReader.SYSTEM_PACKAGE_NAME);
        int dimensionPixelSize = identifier > 0 ? resources.getDimensionPixelSize(identifier) : 0;
        Logger.INSTANCE.i(TAG, "getNavigationBarHeightFromProp = " + dimensionPixelSize);
        return dimensionPixelSize;
    }

    public static boolean isSupportGestureLine(Context context) {
        try {
            Method declaredMethod = Class.forName("android.provider.MiuiSettings$Global").getDeclaredMethod("getBoolean", ContentResolver.class, String.class);
            declaredMethod.setAccessible(true);
            boolean zBooleanValue = ((Boolean) declaredMethod.invoke(null, context.getContentResolver(), USE_GESTURE_VERSION_THREE)).booleanValue();
            Logger.INSTANCE.d(TAG, "isSupportGestureLine = " + zBooleanValue);
            return zBooleanValue;
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "isSupportGestureLine error " + e.getLocalizedMessage());
            return false;
        }
    }

    public static boolean isEnableGestureLine(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), HIDE_GESTURE_LINE, 0) == 0;
    }

    public static boolean hasNavigationBar(Context context) {
        try {
            Object objInvoke = Class.forName("android.view.WindowManagerGlobal").getMethod("getWindowManagerService", new Class[0]).invoke(null, new Object[0]);
            return ((Boolean) objInvoke.getClass().getMethod("hasNavigationBar", Integer.TYPE).invoke(objInvoke, context.getClass().getMethod("getDisplayId", new Class[0]).invoke(context, new Object[0]))).booleanValue();
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, "hasNavigationBar Q,exception:" + e);
            return false;
        }
    }

    private static String getNavBarOverride() {
        try {
            Method declaredMethod = Class.forName("android.os.SystemProperties").getDeclaredMethod(CallMethod.METHOD_GET, String.class);
            declaredMethod.setAccessible(true);
            return (String) declaredMethod.invoke(null, "qemu.hw.mainkeys");
        } catch (Throwable th) {
            Logger.INSTANCE.d(TAG, "error in " + th);
            return null;
        }
    }

    public static int getFontWeight(Context context) {
        if (context == null) {
            return 50;
        }
        return Settings.System.getInt(context.getContentResolver(), "key_miui_font_weight_scale", 50);
    }

    public static String getSystemFontName(Context context) {
        String str = SystemProperties.get("ro.miui.ui.fonttype", "/system/fonts/MiSansVF1.ttf");
        String str2 = SystemProperties.get("ro.miui.ui.font.theme_apply", "false");
        Logger.INSTANCE.d("Note:MiFontStyle", "fontName = " + str);
        Logger.INSTANCE.d("Note:MiFontStyle", "applyTheme = " + str2);
        return str;
    }

    public static boolean floatEquals(float f, float f2) {
        return floatNear(f, f2, EPS);
    }

    public static boolean doubleEquals(double d, double d2) {
        return doubleNear(d, d2, EPSF);
    }

    public static boolean floatNear(float f, float f2, float f3) {
        return Math.abs(f - f2) < Math.abs(f3);
    }

    public static boolean doubleNear(double d, double d2, double d3) {
        return Math.abs(d - d2) < Math.abs(d3);
    }

    public static boolean equals(Object obj, Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }

    public static boolean isNightMode() {
        return (NoteApp.getInstance().getResources().getConfiguration().uiMode & 48) == 32;
    }

    public static void setFullScreenMode(Activity activity, boolean z) {
        int systemUiVisibility = activity.getWindow().getDecorView().getSystemUiVisibility();
        if (z) {
            activity.getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility | R2.color.theme_default_high_light_color);
        } else {
            activity.getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility | (-5895));
        }
    }

    public static int dip2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2dip(Context context, float f) {
        return (int) ((f / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int sp2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density * context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static int getXmlDef(Context context, int i) {
        int iComplexToFloat;
        TypedValue typedValue = mTmpValue;
        synchronized (typedValue) {
            context.getResources().getValue(i, typedValue, true);
            iComplexToFloat = (int) TypedValue.complexToFloat(typedValue.data);
        }
        return iComplexToFloat;
    }

    public static void setMiuiRegularTypeFace(TextView textView) {
        if (RomUtils.isInternationalBuild()) {
            textView.setTypeface(null, 0);
        } else if (UIUtils.isMiuiXISdkSupported()) {
            textView.setTypeface(FONT_MIPRO_REGULAR);
        } else {
            textView.setTypeface(FONT_MIUI_REGULAR);
        }
    }

    public static void setMiuiMediumTypeFace(TextView textView) {
        if (RomUtils.isInternationalBuild()) {
            textView.setTypeface(null, 1);
        } else if (UIUtils.isMiuiXISdkSupported()) {
            textView.setTypeface(FONT_MIPRO_MEDIUM);
        } else {
            textView.setTypeface(FONT_MIUI_BOLD);
        }
    }

    public static void setMiuiBoldTypeFace(TextView textView) {
        if (RomUtils.isInternationalBuild()) {
            textView.setTypeface(null, 1);
        } else if (UIUtils.isMiuiXISdkSupported()) {
            textView.setTypeface(FONT_MIPRO_BOLD);
        } else {
            textView.setTypeface(FONT_MIUI_BOLD);
        }
    }

    public static void setMiuiDemiboldTypeFace(TextView textView) {
        if (RomUtils.isInternationalBuild()) {
            textView.setTypeface(null, 1);
        } else if (UIUtils.isMiuiXISdkSupported()) {
            textView.setTypeface(FONT_MIPRO_DEMIBOLD);
        } else {
            textView.setTypeface(FONT_MIUI_BOLD);
        }
    }
}
