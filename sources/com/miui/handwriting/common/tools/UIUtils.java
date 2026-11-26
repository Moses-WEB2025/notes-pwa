package com.miui.handwriting.common.tools;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import androidx.appfunctions.internal.AppSearchAppFunctionReader;
import androidx.hardware.DataSpace;
import com.miui.floatwindow.feature.root.BaseFloatFullWindow;
import com.miui.handwriting.common.HandWritingViewApplication;
import com.miui.handwriting.common.view.BaseActivity;
import com.miui.libhandwritingpage.utils.MetaReflectUtil;
import com.miui.libhandwritingpage.utils.RomUtils;
import defpackage.R2;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import miuix.animation.Folme;
import miuix.animation.IHoverStyle;
import miuix.animation.base.AnimConfig;
import miuix.core.util.MiuixUIUtils;
import miuix.core.util.SystemProperties;
import miuix.responsive.map.ScreenSpec;
import xc.engine.utils.DisplayUtils;

/* JADX INFO: loaded from: classes3.dex */
public class UIUtils {
    private static final String PROP_NAV_BAR_HEIGHT = "navigation_bar_height";
    private static final String TAG = "UIUtils";
    private static Handler sHandler = new Handler(HandWritingViewApplication.INSTANCE.getApplication().getMainLooper());
    private static String MIUI_DESKTOP_MODE = "miui_dkt_mode";

    public static void runOnUIThread(Runnable runnable) {
        sHandler.post(runnable);
    }

    public static void runOnUIThread(Runnable runnable, long j) {
        sHandler.postDelayed(runnable, j);
    }

    public static void removeRunnable(Runnable runnable) {
        sHandler.removeCallbacks(runnable);
    }

    public static View inflateView(ViewGroup viewGroup, int i) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(i, viewGroup, false);
    }

    public static int getMiuiVersion() {
        return SystemProperties.getInt("ro.miui.ui.version.code", 0);
    }

    public static boolean isMiuiXSdkSupported() {
        return getMiuiVersion() >= 8;
    }

    public static boolean isMiuiXISdkSupported() {
        return getMiuiVersion() >= 9;
    }

    public static boolean isMiuiXIISdkSupported() {
        return getMiuiVersion() >= 10;
    }

    public static boolean isMiuiXII_H_SdkSupported() {
        return getMiuiVersion() >= 11;
    }

    public static boolean isMiuiXIIISdkSupported() {
        return getMiuiVersion() >= 12;
    }

    public static boolean isMIUI14AndAbove() {
        return getMiuiVersion() >= 14;
    }

    public static boolean isMiproFontSupported() {
        if (RomUtils.isInternationalBuild()) {
            return false;
        }
        return isMiuiXISdkSupported();
    }

    public static boolean isNightMode(Context context) {
        return (context.getResources().getConfiguration().uiMode & 48) == 32;
    }

    public static int getNavigationBarHeight(Context context) {
        int navigationBarHeightFromProp;
        if (isInFullWindowGestureMode(context)) {
            navigationBarHeightFromProp = (isMiuiXIISdkSupported() && DisplayUtils.isSupportGestureLine(context) && DisplayUtils.isEnableGestureLine(context)) ? DisplayUtils.getNavigationBarHeightFromProp(context) : 0;
        } else {
            navigationBarHeightFromProp = DisplayUtils.getNavigationBarHeightFromProp(context);
        }
        int i = navigationBarHeightFromProp >= 0 ? navigationBarHeightFromProp : 0;
        Log.i(TAG, "getNavigationBarHeight = " + i);
        return i;
    }

    public static int getNavigationBarHeightFromProp(Context context) {
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier(PROP_NAV_BAR_HEIGHT, "dimen", AppSearchAppFunctionReader.SYSTEM_PACKAGE_NAME);
        if (identifier > 0) {
            return resources.getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public static int getRealNavigationBarHeight(Activity activity) {
        if (isInMultiWindowMode(activity)) {
            return 0;
        }
        return getNavigationBarHeight(activity);
    }

    public static int getRealNavigationBarHeight(Window window) {
        return getNavigationBarHeight(window.getContext());
    }

    public static int getAppHeight(Window window) {
        Rect rect = new Rect();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.height();
    }

    public static boolean hasNotchScreen() {
        int i;
        try {
            i = SystemProperties.getInt("ro.miui.notch", 0);
        } catch (Exception e) {
            e.printStackTrace();
            i = 0;
        }
        return i > 0;
    }

    public static boolean hasHideNotchScreenMode2() {
        return Settings.Global.getInt(HandWritingViewApplication.INSTANCE.getApplication().getContentResolver(), "force_black_v2", 0) == 1;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable.getIntrinsicWidth() == 0 || drawable.getIntrinsicHeight() == 0) {
            Log.d("AudioSpanDebug", "drawableToBitmap drawable size zero " + Log.getStackTraceString(new Throwable()));
        }
        int iMax = Math.max(drawable.getIntrinsicWidth(), 1);
        int iMax2 = Math.max(drawable.getIntrinsicHeight(), 1);
        drawable.setBounds(0, 0, iMax, iMax2);
        if (drawable.getOpacity() != -1) {
            Bitmap.Config config = Bitmap.Config.ARGB_8888;
        } else {
            Bitmap.Config config2 = Bitmap.Config.RGB_565;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iMax, iMax2, Bitmap.Config.ARGB_8888);
        drawable.draw(new Canvas(bitmapCreateBitmap));
        return bitmapCreateBitmap;
    }

    public static Bitmap drawableToBitmap(Drawable drawable, int i, int i2) {
        drawable.getIntrinsicWidth();
        drawable.getIntrinsicHeight();
        drawable.setBounds(0, 0, i, i2);
        if (drawable.getOpacity() != -1) {
            Bitmap.Config config = Bitmap.Config.ARGB_8888;
        } else {
            Bitmap.Config config2 = Bitmap.Config.RGB_565;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        drawable.draw(new Canvas(bitmapCreateBitmap));
        return bitmapCreateBitmap;
    }

    public static Bitmap drawableToBitmap(Drawable drawable, int i, int i2, Bitmap.Config config) {
        drawable.setBounds(0, 0, i, i2);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, config);
        drawable.draw(new Canvas(bitmapCreateBitmap));
        return bitmapCreateBitmap;
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, int i, int i2) {
        return scaleBitmap(bitmap, i, i2, true);
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, int i, int i2, boolean z) {
        if (bitmap == null) {
            return null;
        }
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        Matrix matrix = new Matrix();
        matrix.postScale(i / width, i2 / height);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        if (!bitmap.isRecycled() && z) {
            bitmap.recycle();
        }
        return bitmapCreateBitmap;
    }

    public static Bitmap cropBitmap(Bitmap bitmap, Rect rect) {
        return Bitmap.createBitmap(bitmap, rect.left, rect.top, rect.width(), rect.height(), (Matrix) null, false);
    }

    public static int dip2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
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

    public static boolean isInFullWindowGestureMode(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), BaseFloatFullWindow.FORCE_FSG_NAV_BAR, 0) != 0;
    }

    public static boolean hasNavigationBar(Context context) {
        if (isInFullWindowGestureMode(context)) {
            return false;
        }
        try {
            Object objInvoke = Class.forName("android.view.WindowManagerGlobal").getMethod("getWindowManagerService", new Class[0]).invoke(null, new Object[0]);
            return ((Boolean) objInvoke.getClass().getMethod("hasNavigationBar", Integer.TYPE).invoke(objInvoke, context.getClass().getMethod("getDisplayId", new Class[0]).invoke(context, new Object[0]))).booleanValue();
        } catch (Exception unused) {
            return false;
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

    public static boolean setStatusBarLightMode(Window window, boolean z) {
        if (window != null) {
            Class<?> cls = window.getClass();
            try {
                Class<?> cls2 = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                int i = cls2.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE").getInt(cls2);
                Method method = cls.getMethod("setExtraFlags", Integer.TYPE, Integer.TYPE);
                if (z) {
                    method.invoke(window, Integer.valueOf(i), Integer.valueOf(i));
                } else {
                    method.invoke(window, 0, Integer.valueOf(i));
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean isNightMode() {
        return (HandWritingViewApplication.INSTANCE.getApplication().getResources().getConfiguration().uiMode & 48) == 32;
    }

    public static boolean isNightMode(Configuration configuration) {
        return (configuration.uiMode & 48) == 32;
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

    public static String getLocale(Context context) {
        if (context == null) {
            return Locale.getDefault().toString();
        }
        return context.getResources().getConfiguration().getLocales().toString();
    }

    public static boolean isAboveAndroidS() {
        return Build.VERSION.SDK_INT >= 31;
    }

    public static boolean isPcDensity(Context context) {
        return context.getResources().getDisplayMetrics().density == 1.0f || context.getResources().getDisplayMetrics().density <= 1.31f;
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

    public static boolean isSmallWindowMode() {
        return isSmallWindowMode(HandWritingViewApplication.INSTANCE.getApplication());
    }

    public static boolean isInMultiScreenTop(Context context, Rect rect) {
        DisplayMetrics realScreenSize = getRealScreenSize(context);
        if (rect.left == 0 && rect.right == realScreenSize.widthPixels) {
            int i = (int) (realScreenSize.heightPixels * 0.75f);
            if (rect.top >= 0 && rect.bottom <= i) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInMultiScreenBottom(Context context, Rect rect) {
        DisplayMetrics realScreenSize = getRealScreenSize(context);
        if (rect.left == 0 && rect.right == realScreenSize.widthPixels) {
            if (rect.top >= ((int) (realScreenSize.heightPixels * 0.2f))) {
                return true;
            }
        }
        return false;
    }

    public static void setFolmeHoverEffect(View view) {
        Folme.useAt(view).hover().setEffect(IHoverStyle.HoverEffect.FLOATED_WRAPPED).handleHoverOf(view, new AnimConfig[0]);
    }

    public static void setFolmeCommonTouchEffect(View view) {
        Folme.useAt(view).touch().handleTouchOf(view, new AnimConfig[0]);
    }

    public static View getActionBarOverlayLayout(View view) {
        return getActionBarOverlayLayoutByDecorView(view.getRootView());
    }

    public static View getActionBarOverlayLayoutByDecorView(View view) {
        ViewGroup viewGroup = (ViewGroup) view.findViewById(MetaReflectUtil.getMiuixAppcompatViewId("action_bar_overlay_layout"));
        return viewGroup == null ? (ViewGroup) view : viewGroup;
    }

    public static boolean isInNarrowScreen(Context context) {
        int[] screenSizeDp = getScreenSizeDp(context);
        int iMin = Math.min(screenSizeDp[0], screenSizeDp[1]);
        Log.d("isInNarrowScreen", "realWidth:" + iMin);
        return iMin <= 394;
    }

    public static boolean isInJ18NarrowScreen(Context context) {
        int[] screenSizeDp = getScreenSizeDp(context);
        int iMin = Math.min(screenSizeDp[0], screenSizeDp[1]);
        Log.d("isInNarrowScreen", "realWidth:" + iMin);
        return iMin <= 350;
    }

    private static int[] getScreenSizeDp(Context context) {
        return MiuixUIUtils.getScreenSizeDp(context);
    }

    public static int getScreenStatus(Context context, ScreenSpec screenSpec) {
        if (RomUtils.isFoldDevice() && isInNarrowScreen(context) && screenSpec.screenMode == 4103) {
            return 4097;
        }
        return screenSpec.screenMode;
    }

    public static boolean isInSplitScreenMode(Context context) {
        int windowMode = getWindowMode(context);
        return (windowMode == 1 || windowMode == 5) ? false : true;
    }

    public static boolean isSmallScreen(Context context) {
        int i = context.getResources().getConfiguration().smallestScreenWidthDp;
        boolean z = true;
        boolean z2 = (context.getResources().getConfiguration().uiMode & 8192) != 0;
        if (!z2 ? i > 640 : i > 530) {
            z = false;
        }
        Log.i(TAG, "smallestScreenWidthDp=" + context.getResources().getConfiguration().smallestScreenWidthDp);
        Log.i(TAG, "mOnPcMode=" + z2 + ",isSmallScreen=" + z);
        return z;
    }

    public static boolean isSmallScreenForBrush(Context context) {
        if (ScreenModeUtils.isWidthFull(context)) {
            return false;
        }
        int i = context.getResources().getConfiguration().smallestScreenWidthDp;
        Log.i(TAG, "isOneBrush: smallestScreenWidthDp == " + i);
        boolean z = true;
        if (i < 480) {
            return true;
        }
        boolean zIsOneThird = ScreenModeUtils.isOneThird(context);
        boolean zIsHalf = ScreenModeUtils.isHalf(context);
        boolean zIsTwoThird = ScreenModeUtils.isTwoThird(context);
        boolean zIsSmallWindowMode = isSmallWindowMode();
        boolean zIsLandScape = ScreenModeUtils.isLandScape(context);
        Log.d(TAG, "isOneBrush: oneThirdMode:" + zIsOneThird + ", halfMode: " + zIsHalf + ", twoThirdMode: " + zIsTwoThird + ", smallWindowMode: " + zIsSmallWindowMode + ", landScapeMode: " + zIsLandScape);
        if (!zIsOneThird && !zIsHalf && (!zIsTwoThird || (!zIsSmallWindowMode && zIsLandScape))) {
            z = false;
        }
        boolean z2 = (zIsSmallWindowMode && zIsLandScape) ? false : z;
        Log.i(TAG, "isOneBrush == " + z2);
        return z2;
    }

    public static boolean isSmallScreenForNoPageChange(Context context) {
        int i = context.getResources().getConfiguration().smallestScreenWidthDp;
        Log.i(TAG, "isSmallScreenForNoPageChange: smallestScreenWidthDp == " + i);
        boolean z = true;
        if (i < 480) {
            return true;
        }
        boolean zIsOneThird = ScreenModeUtils.isOneThird(context);
        boolean zIsHalf = ScreenModeUtils.isHalf(context);
        boolean zIsTwoThird = ScreenModeUtils.isTwoThird(context);
        boolean zIsSmallWindowMode = isSmallWindowMode();
        Log.d(TAG, "isSmallScreenForNoPageChange: oneThirdMode:" + zIsOneThird + ", halfMode: " + zIsHalf + ", twoThirdMode: " + zIsTwoThird + ", smallWindowMode: " + zIsSmallWindowMode);
        if (!zIsOneThird && !zIsHalf && !zIsTwoThird && !zIsSmallWindowMode) {
            z = false;
        }
        Log.i(TAG, "isSmallScreenForNoPageChange == " + z);
        return z;
    }

    public static boolean isSmallScreenNoShare(Context context) {
        if (ScreenModeUtils.isWidthFull(context)) {
            return false;
        }
        if (context.getResources().getConfiguration().smallestScreenWidthDp <= 426) {
            return true;
        }
        boolean z = ScreenModeUtils.isPortHalf(context) || ScreenModeUtils.isOneThird(context);
        Log.i(TAG, "isSmallScreenNoShare =" + z);
        return z;
    }

    public static boolean isScreenLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == 2;
    }

    public static boolean isFoldInternalScreen(Context context) {
        DisplayMetrics realScreenSize = getRealScreenSize(context);
        return realScreenSize.widthPixels > 1500 && realScreenSize.heightPixels > 1500;
    }

    public static void checkOrientationInFoldDevice(Activity activity) {
        if (RomUtils.isPadDevice()) {
            return;
        }
        if (RomUtils.isFoldDevice() && !Build.DEVICE.equals("cetus")) {
            if (!isFoldInternalScreen(activity)) {
                activity.setRequestedOrientation(1);
                return;
            } else {
                activity.setRequestedOrientation(-1);
                return;
            }
        }
        activity.setRequestedOrientation(1);
    }

    public static DisplayMetrics getRealScreenSize(Context context) {
        if (isSmallWindowMode()) {
            return getDisplayMetricsInSmallWindow(context);
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics;
    }

    public static DisplayMetrics getWorkbenchesScreenSize(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        if (isSmallWindowMode()) {
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            return displayMetrics;
        }
        windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics;
    }

    public static int getRealHeight(Context context) {
        if (isSmallWindowMode()) {
            return getDisplayMetricsInSmallWindow(context).heightPixels;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static int getRealWidth(Context context) {
        if (isSmallWindowMode()) {
            return getDisplayMetricsInSmallWindow(context).widthPixels;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static float mm2px(Context context, float f) {
        return (f / 25.4f) * context.getResources().getDisplayMetrics().xdpi;
    }

    public static float px2mm(Context context, float f) {
        return (f / context.getResources().getDisplayMetrics().xdpi) * 25.4f;
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

    public static float getToolDimen(float f) {
        return RomUtils.isLargePad() ? (float) (((double) f) * 1.18d) : f;
    }

    public static boolean isSupportMiuiDekstopModeByFK() {
        return SystemProperties.getBoolean("ro.config.miui_desktop_mode_enabled", false);
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

    public static float getSystemScaleText(Context context) {
        float f = context.getResources().getConfiguration().fontScale;
        Log.i(TAG, "getSystemScaleText setting,fontScale=" + f);
        return f;
    }

    public static void setFullScreenMode(Activity activity, boolean z) {
        if (activity == null) {
            return;
        }
        int systemUiVisibility = activity.getWindow().getDecorView().getSystemUiVisibility();
        activity.getWindow().getDecorView().setSystemUiVisibility(z ? systemUiVisibility | R2.color.theme_default_cursor_color : systemUiVisibility & (-5893));
    }

    public static boolean isFullWindowMode(Context context) {
        if (getWindowMode(context) == 1) {
            Log.d(TAG, "isFullWindow is true");
            return true;
        }
        Log.d(TAG, "isFullWindow is false");
        return false;
    }

    public static int getWindowWidth(Context context) {
        return ((WindowManager) context.getSystemService("window")).getCurrentWindowMetrics().getBounds().width();
    }

    public static int getWindowHeight(Context context) {
        return ((WindowManager) context.getSystemService("window")).getCurrentWindowMetrics().getBounds().height();
    }

    public static int getSmallModeHeight(Window window) {
        Rect rect = new Rect();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        int i = rect.bottom;
        Log.i(TAG, "getSmallModeHeight=" + i);
        return i;
    }

    public static int getRealScreenOrientation(Context context) {
        return getRealWidth(context) <= getRealHeight(context) ? 1 : 2;
    }

    public static int getWindowScreenOrientation(Context context) {
        return getWindowWidth(context) <= getWindowHeight(context) ? 1 : 2;
    }

    public static int px2dip(Context context, float f) {
        return (int) ((f / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static boolean isFreeFormScreen(Configuration configuration) {
        return configuration != null && configuration.toString().contains("mWindowingMode=freeform");
    }

    public static boolean isRTL() {
        return TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
    }

    public static void setNavigationTrans(BaseActivity baseActivity) {
        if (!isInFullWindowGestureMode(baseActivity)) {
            baseActivity.getWindow().clearFlags(DataSpace.RANGE_FULL);
        } else {
            baseActivity.getWindow().addFlags(DataSpace.RANGE_FULL);
        }
        baseActivity.getWindow().addFlags(67108864);
    }

    public static void setNavigationColor(Window window, int i) {
        window.setNavigationBarColor(i);
    }
}
