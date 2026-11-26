package com.miui.doodle.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.DisplayCutout;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.TextView;
import androidx.appfunctions.internal.AppSearchAppFunctionReader;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.DoodleApplication;
import com.miui.maml.folme.AnimatedProperty;
import com.miui.notes.base.utils.Logger;
import com.miui.richeditor.constant.MiuiFontName;
import com.xiaomi.mirror.synergy.CallMethod;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DisplayUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b\u0012\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010\u001d\u001a\u00020\u001b2\u0006\u0010\u001e\u001a\u00020\u001fJ\u000e\u0010 \u001a\u00020!2\u0006\u0010\u001e\u001a\u00020\u001fJ\u000e\u0010\"\u001a\u00020#2\u0006\u0010\u001e\u001a\u00020\u001fJ\u000e\u0010$\u001a\u00020\u001b2\u0006\u0010\u0018\u001a\u00020\u0019J\u0010\u0010%\u001a\u00020\u001b2\u0006\u0010\u0018\u001a\u00020\u0019H\u0007J\u0010\u0010&\u001a\u00020#2\u0006\u0010\u0018\u001a\u00020\u0019H\u0007J\u0010\u0010'\u001a\u00020#2\u0006\u0010\u0018\u001a\u00020\u0019H\u0007J\u000e\u0010(\u001a\u00020)2\u0006\u0010\u001e\u001a\u00020\u001fJ\u000e\u0010*\u001a\u00020#2\u0006\u0010\u0018\u001a\u00020\u0019J\u0016\u00101\u001a\u00020#2\u0006\u00102\u001a\u00020!2\u0006\u00103\u001a\u00020!J\u0016\u00104\u001a\u00020#2\u0006\u00102\u001a\u0002002\u0006\u00103\u001a\u000200J\u001e\u00105\u001a\u00020#2\u0006\u00102\u001a\u00020!2\u0006\u00103\u001a\u00020!2\u0006\u00106\u001a\u00020!J\u001e\u00107\u001a\u00020#2\u0006\u00102\u001a\u0002002\u0006\u00103\u001a\u0002002\u0006\u00106\u001a\u000200J\u0018\u00108\u001a\u00020#2\b\u00109\u001a\u0004\u0018\u00010\u00012\u0006\u0010:\u001a\u00020\u0001J\u001e\u0010;\u001a\u00020!2\u0006\u0010<\u001a\u00020!2\u0006\u0010=\u001a\u00020!2\u0006\u0010>\u001a\u00020!J\u0010\u0010?\u001a\u00020#2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019J\u0016\u0010@\u001a\u00020\u001b2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010A\u001a\u00020!J\u000e\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020EJ\u000e\u0010F\u001a\u00020C2\u0006\u0010D\u001a\u00020ER\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001c\u0010\n\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u001c\u0010\r\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u0007\"\u0004\b\u000f\u0010\tR\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0007\"\u0004\b\u0012\u0010\tR\u000e\u0010\u0013\u001a\u00020\u0014X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0014X\u0082T¢\u0006\u0002\n\u0000R\u0016\u0010+\u001a\u0004\u0018\u00010\u00148BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b,\u0010-R\u000e\u0010.\u001a\u00020!X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u000200X\u0082T¢\u0006\u0002\n\u0000¨\u0006G"}, d2 = {"Lcom/miui/doodle/utils/DisplayUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "FONT_MIUI_BOLD", "Landroid/graphics/Typeface;", "getFONT_MIUI_BOLD", "()Landroid/graphics/Typeface;", "setFONT_MIUI_BOLD", "(Landroid/graphics/Typeface;)V", "FONT_MIUI_REGULAR", "getFONT_MIUI_REGULAR", "setFONT_MIUI_REGULAR", "FONT_MIPRO_MEDIUM", "getFONT_MIPRO_MEDIUM", "setFONT_MIPRO_MEDIUM", "FONT_MIPRO_REGULAR", "getFONT_MIPRO_REGULAR", "setFONT_MIPRO_REGULAR", "USE_GESTURE_VERSION_THREE", "", "HIDE_GESTURE_LINE", "getScreenSizeDp", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "getRealHeight", "", "getRealWidth", "getMultiHeight", "activity", "Landroid/app/Activity;", "getMultiRatio", "", "isInMultiWindowMode", "", "getStatusBarHeight", "getNavigationBarHeightFromProp", "isSupportGestureLine", "isEnableGestureLine", "getDisplayCutout", "Landroid/graphics/Rect;", "hasNavigationBar", "navBarOverride", "getNavBarOverride", "()Ljava/lang/String;", "EPS", "EPSF", "", "floatEquals", "f1", "f2", "doubleEquals", "floatNear", "eps", "doubleNear", "equals", "o1", "o2", "clamp", AnimatedProperty.PROPERTY_NAME_X, "min", "max", "isNightMode", "dip2px", "dpValue", "setMiuiRegularTypeFace", "", "textView", "Landroid/widget/TextView;", "setMiuiBoldTypeFace", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DisplayUtils {
    private static final float EPS = 1.0E-7f;
    private static final double EPSF = 1.0E-7d;
    private static Typeface FONT_MIPRO_MEDIUM = null;
    private static Typeface FONT_MIPRO_REGULAR = null;
    private static Typeface FONT_MIUI_BOLD = null;
    private static Typeface FONT_MIUI_REGULAR = null;
    private static final String HIDE_GESTURE_LINE = "hide_gesture_line";
    public static final DisplayUtils INSTANCE = new DisplayUtils();
    private static final String USE_GESTURE_VERSION_THREE = "use_gesture_version_three";

    public final float clamp(float x, float min, float max) {
        if (x > max) {
            return max;
        }
        return x < min ? min : x;
    }

    private DisplayUtils() {
    }

    public final Typeface getFONT_MIUI_BOLD() {
        return FONT_MIUI_BOLD;
    }

    public final void setFONT_MIUI_BOLD(Typeface typeface) {
        FONT_MIUI_BOLD = typeface;
    }

    public final Typeface getFONT_MIUI_REGULAR() {
        return FONT_MIUI_REGULAR;
    }

    public final void setFONT_MIUI_REGULAR(Typeface typeface) {
        FONT_MIUI_REGULAR = typeface;
    }

    public final Typeface getFONT_MIPRO_MEDIUM() {
        return FONT_MIPRO_MEDIUM;
    }

    public final void setFONT_MIPRO_MEDIUM(Typeface typeface) {
        FONT_MIPRO_MEDIUM = typeface;
    }

    public final Typeface getFONT_MIPRO_REGULAR() {
        return FONT_MIPRO_REGULAR;
    }

    public final void setFONT_MIPRO_REGULAR(Typeface typeface) {
        FONT_MIPRO_REGULAR = typeface;
    }

    public final int[] getScreenSizeDp(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        int[] iArr = new int[2];
        float f = context.getResources().getDisplayMetrics().density;
        if (Build.VERSION.SDK_INT >= 30) {
            WindowMetrics maximumWindowMetrics = ((WindowManager) context.getSystemService(WindowManager.class)).getMaximumWindowMetrics();
            Intrinsics.checkNotNullExpressionValue(maximumWindowMetrics, "getMaximumWindowMetrics(...)");
            iArr[0] = (int) (maximumWindowMetrics.getBounds().width() / f);
            iArr[1] = (int) (maximumWindowMetrics.getBounds().height() / f);
        } else {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            Object systemService = context.getSystemService("window");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.WindowManager");
            ((WindowManager) systemService).getDefaultDisplay().getRealMetrics(displayMetrics);
            iArr[0] = (int) (displayMetrics.widthPixels / f);
            iArr[1] = (int) (displayMetrics.heightPixels / f);
        }
        Logger.INSTANCE.d("DisplayUtils", "getScreenSizeDp w " + iArr[0] + " h " + iArr[1]);
        return iArr;
    }

    public final int getRealHeight(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Object systemService = context.getSystemService("window");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.WindowManager");
        ((WindowManager) systemService).getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public final int getRealWidth(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Object systemService = context.getSystemService("window");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.WindowManager");
        ((WindowManager) systemService).getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public final int getMultiHeight(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Object systemService = activity.getSystemService("window");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.WindowManager");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) systemService).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public final float getMultiRatio(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        boolean zIsInMultiWindowMode = isInMultiWindowMode(activity);
        Logger.INSTANCE.d("Display", " isInMultiWindowMode = " + zIsInMultiWindowMode);
        if (zIsInMultiWindowMode) {
            Activity activity2 = activity;
            if (getRealHeight(activity2) < getRealWidth(activity2)) {
                return getRealHeight(activity2) / getRealWidth(activity2);
            }
        }
        return getMultiHeight(activity) / getRealHeight(activity);
    }

    public final boolean isInMultiWindowMode(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        return activity.isInMultiWindowMode();
    }

    public final int getStatusBarHeight(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            Class<?> cls = Class.forName("com.android.internal.R$dimen");
            return context.getResources().getDimensionPixelSize(Integer.parseInt(cls.getField("status_bar_height").get(cls.newInstance()).toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @JvmStatic
    public static final int getNavigationBarHeightFromProp(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (!INSTANCE.hasNavigationBar(context)) {
            return 0;
        }
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("navigation_bar_height", "dimen", AppSearchAppFunctionReader.SYSTEM_PACKAGE_NAME);
        int dimensionPixelSize = identifier > 0 ? resources.getDimensionPixelSize(identifier) : 0;
        Logger.INSTANCE.i("DisplayUtils", "getNavigationBarHeightFromProp = " + dimensionPixelSize);
        return dimensionPixelSize;
    }

    @JvmStatic
    public static final boolean isSupportGestureLine(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            Method declaredMethod = Class.forName("android.provider.MiuiSettings$Global").getDeclaredMethod("getBoolean", ContentResolver.class, String.class);
            declaredMethod.setAccessible(true);
            Object objInvoke = declaredMethod.invoke(null, context.getContentResolver(), USE_GESTURE_VERSION_THREE);
            Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type kotlin.Boolean");
            return ((Boolean) objInvoke).booleanValue();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @JvmStatic
    public static final boolean isEnableGestureLine(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return Settings.Global.getInt(context.getContentResolver(), HIDE_GESTURE_LINE, 0) == 0;
    }

    public final Rect getDisplayCutout(Activity activity) {
        DisplayCutout displayCutout;
        Intrinsics.checkNotNullParameter(activity, "activity");
        Rect rect = new Rect();
        if (activity.getWindow().getDecorView().getRootWindowInsets() != null && (displayCutout = activity.getWindow().getDecorView().getRootWindowInsets().getDisplayCutout()) != null && displayCutout.getBoundingRects().size() > 0) {
            rect.set(displayCutout.getBoundingRects().get(0));
        }
        Logger.INSTANCE.d("DisplayUtils", "getDisplayCutout " + rect);
        return rect;
    }

    public final boolean hasNavigationBar(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            Object objInvoke = Class.forName("android.view.WindowManagerGlobal").getMethod("getWindowManagerService", new Class[0]).invoke(null, new Object[0]);
            Object objInvoke2 = objInvoke.getClass().getMethod("hasNavigationBar", Integer.TYPE).invoke(objInvoke, context.getClass().getMethod("getDisplayId", new Class[0]).invoke(context, new Object[0]));
            Intrinsics.checkNotNull(objInvoke2, "null cannot be cast to non-null type kotlin.Boolean");
            return ((Boolean) objInvoke2).booleanValue();
        } catch (Exception e) {
            Logger.INSTANCE.d("DisplayUtils", "hasNavigationBar Q" + e);
            return false;
        }
    }

    private final String getNavBarOverride() {
        try {
            Method declaredMethod = Class.forName("android.os.SystemProperties").getDeclaredMethod(CallMethod.METHOD_GET, String.class);
            declaredMethod.setAccessible(true);
            Object objInvoke = declaredMethod.invoke(null, "qemu.hw.mainkeys");
            Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type kotlin.String");
            return (String) objInvoke;
        } catch (Throwable unused) {
            return null;
        }
    }

    public final boolean floatEquals(float f1, float f2) {
        return floatNear(f1, f2, EPS);
    }

    public final boolean doubleEquals(double f1, double f2) {
        return doubleNear(f1, f2, EPSF);
    }

    public final boolean floatNear(float f1, float f2, float eps) {
        return Math.abs(f1 - f2) < Math.abs(eps);
    }

    public final boolean doubleNear(double f1, double f2, double eps) {
        return Math.abs(f1 - f2) < Math.abs(eps);
    }

    public final boolean equals(Object o1, Object o2) {
        Intrinsics.checkNotNullParameter(o2, "o2");
        if (o1 != o2) {
            return o1 != null && Intrinsics.areEqual(o1, o2);
        }
        return true;
    }

    public final boolean isNightMode(Context context) {
        return (DoodleApplication.INSTANCE.getAppInstance().getResources().getConfiguration().uiMode & 48) == 32;
    }

    public final int dip2px(Context context, float dpValue) {
        Intrinsics.checkNotNullParameter(context, "context");
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public final void setMiuiRegularTypeFace(TextView textView) {
        Intrinsics.checkNotNullParameter(textView, "textView");
        if (RomUtils.INSTANCE.isInternationalBuild()) {
            textView.setTypeface(null, 0);
            return;
        }
        if (UIUtils.INSTANCE.isMiuiXISdkSupported()) {
            if (FONT_MIPRO_REGULAR == null) {
                FONT_MIPRO_REGULAR = Typeface.create(MiuiFontName.REGULAR, 0);
            }
            textView.setTypeface(FONT_MIPRO_REGULAR);
        } else {
            if (FONT_MIUI_REGULAR == null) {
                FONT_MIUI_REGULAR = Typeface.create("miui-regular", 0);
            }
            textView.setTypeface(FONT_MIUI_REGULAR);
        }
    }

    public final void setMiuiBoldTypeFace(TextView textView) {
        Intrinsics.checkNotNullParameter(textView, "textView");
        if (RomUtils.INSTANCE.isInternationalBuild()) {
            textView.setTypeface(null, 1);
            return;
        }
        if (UIUtils.INSTANCE.isMiuiXISdkSupported()) {
            if (FONT_MIPRO_MEDIUM == null) {
                FONT_MIPRO_MEDIUM = Typeface.create(MiuiFontName.NORMAL, 0);
            }
            textView.setTypeface(FONT_MIPRO_MEDIUM);
        } else {
            if (FONT_MIUI_BOLD == null) {
                FONT_MIUI_BOLD = Typeface.create("miui-bold", 0);
            }
            textView.setTypeface(FONT_MIUI_BOLD);
        }
    }
}
