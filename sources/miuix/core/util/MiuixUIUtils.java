package miuix.core.util;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowMetrics;
import androidx.appfunctions.internal.AppSearchAppFunctionReader;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.content.ContextCompat;
import com.miui.floatwindow.feature.root.BaseFloatFullWindow;
import java.util.Locale;
import miuix.internal.util.AttributeResolver;
import miuix.reflect.Reflects;

/* JADX INFO: loaded from: classes5.dex */
public class MiuixUIUtils {
    public static final int FONT_LEVEL_LARGE = 2;
    public static final int FONT_LEVEL_NORMAL = 1;
    private static final String HIDE_GESTURE_LINE = "hide_gesture_line";
    private static final String TAG = "MiuixUtils";
    private static final String USE_GESTURE_VERSION_THREE = "use_gesture_version_three";
    private static TypedValue mTmpValue = new TypedValue();

    public static int dp2px(float f, float f2) {
        return (int) ((f2 * f) + 0.5f);
    }

    public static boolean isLightColor(int i) {
        if (i == 0) {
            return true;
        }
        return ((((double) ((i >> 16) & 255)) * 0.299d) + (((double) ((i >> 8) & 255)) * 0.587d)) + (((double) (i & 255)) * 0.114d) > 128.0d;
    }

    public static int px2dp(float f, float f2) {
        return (int) ((f2 / f) + 0.5f);
    }

    public static int dp2px(Context context, float f) {
        return dp2px(context.getResources().getConfiguration().densityDpi / 160.0f, f);
    }

    public static int px2dp(Context context, float f) {
        return px2dp(context.getResources().getConfiguration().densityDpi / 160.0f, f);
    }

    public static int getDefDimen(Context context, int i) {
        TypedValue typedValue = new TypedValue();
        context.getResources().getValue(i, typedValue, true);
        return (int) TypedValue.complexToFloat(typedValue.data);
    }

    @Deprecated
    public static boolean isNavigationBarFullScreen(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), BaseFloatFullWindow.FORCE_FSG_NAV_BAR, 0) != 0;
    }

    public static boolean isFullScreenGestureMode(Context context) {
        return getNaviBarInteractionMode(context) == 2;
    }

    public static int getNaviBarInteractionMode(Context context) {
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("config_navBarInteractionMode", TypedValues.Custom.S_INT, AppSearchAppFunctionReader.SYSTEM_PACKAGE_NAME);
        if (identifier > 0) {
            return resources.getInteger(identifier);
        }
        return 0;
    }

    @Deprecated
    public static int getNaviBarIntercationMode(Context context) {
        return getNaviBarInteractionMode(context);
    }

    public static boolean checkDeviceHasNavigationBar(Context context) {
        String str = SystemProperties.get("qemu.hw.mainkeys");
        if ("1".equals(str)) {
            return false;
        }
        if ("0".equals(str)) {
            return true;
        }
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("config_showNavigationBar", "bool", AppSearchAppFunctionReader.SYSTEM_PACKAGE_NAME);
        if (identifier > 0) {
            return resources.getBoolean(identifier);
        }
        return false;
    }

    public static int getRealNavigationBarHeight(Context context) {
        Resources resources;
        int identifier;
        if (checkDeviceHasNavigationBar(context) && (identifier = (resources = context.getResources()).getIdentifier("navigation_bar_height", "dimen", AppSearchAppFunctionReader.SYSTEM_PACKAGE_NAME)) > 0) {
            return resources.getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public static boolean isSupportGestureLine(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), USE_GESTURE_VERSION_THREE, 0) != 0;
    }

    public static boolean isEnableGestureLine(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), HIDE_GESTURE_LINE, 0) == 0;
    }

    public static boolean isShowNavigationHandle(Context context) {
        return isEnableGestureLine(context) && isFullScreenGestureMode(context) && isSupportGestureLine(context);
    }

    public static int getNavigationBarHeight(Context context) {
        int realNavigationBarHeight = (isShowNavigationHandle(context) || !isFullScreenGestureMode(context)) ? getRealNavigationBarHeight(context) : 0;
        if (realNavigationBarHeight < 0) {
            return 0;
        }
        return realNavigationBarHeight;
    }

    public static boolean isInMultiWindowMode(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return checkMultiWindow((Activity) context);
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return false;
    }

    private static boolean checkMultiWindow(Activity activity) {
        return activity.isInMultiWindowMode();
    }

    public static int getStatusBarHeight(Context context) {
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", AppSearchAppFunctionReader.SYSTEM_PACKAGE_NAME);
        if (identifier > 0) {
            return context.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    @Deprecated
    public static boolean isFreeformMode(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        Point physicalSize = getPhysicalSize(context);
        float f = (point.x + 0.0f) / physicalSize.x;
        float f2 = (point.y + 0.0f) / physicalSize.y;
        if (context.getResources().getConfiguration().toString().contains("mWindowingMode=freeform")) {
            return f <= 0.9f || f2 <= 0.9f;
        }
        return false;
    }

    private static Point getPhysicalSize(Context context) {
        Point point = new Point();
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        Display defaultDisplay = windowManager.getDefaultDisplay();
        try {
            Object obj = Reflects.get(defaultDisplay, Reflects.getDeclaredField(defaultDisplay.getClass(), "mDisplayInfo"));
            point.x = ((Integer) Reflects.get(obj, Reflects.getField(obj.getClass(), "logicalWidth"))).intValue();
            point.y = ((Integer) Reflects.get(obj, Reflects.getField(obj.getClass(), "logicalHeight"))).intValue();
            return point;
        } catch (Exception e) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
            point.x = displayMetrics.widthPixels;
            point.y = displayMetrics.heightPixels;
            Log.w(TAG, "catch error! failed to get physical size", e);
            return point;
        }
    }

    public static int[] getScreenSizeDp(Context context) {
        int[] iArr = new int[2];
        float f = context.getResources().getDisplayMetrics().density;
        if (Build.VERSION.SDK_INT >= 30) {
            WindowMetrics maximumWindowMetrics = ((WindowManager) context.getSystemService(WindowManager.class)).getMaximumWindowMetrics();
            iArr[0] = (int) (maximumWindowMetrics.getBounds().width() / f);
            iArr[1] = (int) (maximumWindowMetrics.getBounds().height() / f);
            return iArr;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
        iArr[0] = (int) (displayMetrics.widthPixels / f);
        iArr[1] = (int) (displayMetrics.heightPixels / f);
        return iArr;
    }

    public static boolean isLayoutHideNavigation(View view) {
        return isTargetSdkVersionAboveV(view.getContext()) || (view.getWindowSystemUiVisibility() & 512) != 0;
    }

    public static boolean renderContentInCutoutArea(Context context) {
        if (isTargetSdkVersionAboveV(context)) {
            return true;
        }
        Activity activity = getActivity(context);
        if (activity != null) {
            Window window = activity.getWindow();
            if (window == null) {
                return false;
            }
            WindowManager.LayoutParams attributes = window.getAttributes();
            boolean z = attributes.layoutInDisplayCutoutMode == 1;
            if (Build.VERSION.SDK_INT >= 30) {
                return z || attributes.layoutInDisplayCutoutMode == 3;
            }
            return z;
        }
        int iResolveInt = AttributeResolver.resolveInt(context, R.attr.windowLayoutInDisplayCutoutMode, 0);
        boolean z2 = iResolveInt == 1;
        if (Build.VERSION.SDK_INT >= 30) {
            return z2 || iResolveInt == 3;
        }
        return z2;
    }

    public static boolean isTargetSdkVersionAboveV(Context context) {
        return context != null && Build.VERSION.SDK_INT >= 35 && context.getApplicationContext().getApplicationInfo().targetSdkVersion >= 35;
    }

    public static boolean isDisplayCutoutModeAlways(Context context) {
        if (context != null && Build.VERSION.SDK_INT >= 30) {
            Activity activity = getActivity(context);
            if (activity != null) {
                Window window = activity.getWindow();
                return window != null && window.getAttributes().layoutInDisplayCutoutMode == 3;
            }
            if (AttributeResolver.resolveInt(context, R.attr.windowLayoutInDisplayCutoutMode, 0) == 3) {
                return true;
            }
        }
        return false;
    }

    public static Activity getActivity(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    public static int getFontLevel(Context context) {
        return context.getResources().getConfiguration().fontScale < 1.55f ? 1 : 2;
    }

    public static boolean isTallFontLang(Context context) {
        Resources resources;
        Configuration configuration;
        if (context == null || (resources = context.getResources()) == null || (configuration = resources.getConfiguration()) == null) {
            return false;
        }
        Locale locale = configuration.locale;
        if (locale == null && configuration.getLocales() != null && !configuration.getLocales().isEmpty()) {
            locale = configuration.getLocales().get(0);
        }
        if (locale == null) {
            return false;
        }
        String language = locale.getLanguage();
        return "bo".equals(language) || "ta".equals(language);
    }

    public static Integer getColorFromDrawable(Drawable drawable) {
        ColorStateList color;
        if (drawable instanceof ColorDrawable) {
            return Integer.valueOf(((ColorDrawable) drawable).getColor());
        }
        if (!(drawable instanceof GradientDrawable) || (color = ((GradientDrawable) drawable).getColor()) == null) {
            return null;
        }
        return Integer.valueOf(color.getColorForState(drawable.getState(), color.getDefaultColor()));
    }

    public static boolean isDarkThemeOverlay(Context context, int i) {
        return isLightColor(ContextCompat.getColor(context, i));
    }
}
