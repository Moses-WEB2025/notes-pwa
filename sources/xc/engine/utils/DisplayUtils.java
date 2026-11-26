package xc.engine.utils;

import android.R;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import androidx.appfunctions.internal.AppSearchAppFunctionReader;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Objects;

/* JADX INFO: loaded from: classes6.dex */
public class DisplayUtils {
    public static float DEFAULT_FONT_SIZE = 1.0f;
    private static final String HIDE_GESTURE_LINE = "hide_gesture_line";
    private static final String USE_GESTURE_VERSION_THREE = "use_gesture_version_three";

    public static int getMultiHeight(Activity activity) {
        WindowManager windowManager = (WindowManager) activity.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static boolean isInMultiWindowMode(Activity activity) {
        return activity.isInMultiWindowMode();
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

    public static int getActionBarHeight(Context context) {
        if (context == null) {
            return 0;
        }
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{R.attr.actionBarSize});
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, 0);
        typedArrayObtainStyledAttributes.recycle();
        return dimensionPixelSize;
    }

    public static int getNavigationBarHeightFromProp(Context context) {
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("navigation_bar_height", "dimen", AppSearchAppFunctionReader.SYSTEM_PACKAGE_NAME);
        if (identifier > 0) {
            return resources.getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public static boolean isSupportGestureLine(Context context) {
        try {
            Method declaredMethod = Class.forName("android.provider.MiuiSettings$Global").getDeclaredMethod("getBoolean", ContentResolver.class, String.class);
            declaredMethod.setAccessible(true);
            return ((Boolean) declaredMethod.invoke(null, context.getContentResolver(), USE_GESTURE_VERSION_THREE)).booleanValue();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isEnableGestureLine(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), HIDE_GESTURE_LINE, 0) == 0;
    }

    public static boolean equals(Object obj, Object obj2) {
        return Objects.equals(obj, obj2);
    }

    public static int dip2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int getWindowWidth(Activity activity) {
        WindowManager windowManager = (WindowManager) activity.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static boolean isRTL() {
        return TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
    }
}
