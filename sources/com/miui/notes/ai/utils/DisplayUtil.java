package com.miui.notes.ai.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.appfunctions.internal.AppSearchAppFunctionReader;
import com.miui.floatwindow.feature.root.BaseFloatFullWindow;
import com.miui.notes.base.utils.Logger;
import com.miui.richeditor.constant.MiuiFontName;
import miuix.core.util.MiuixUIUtils;

/* JADX INFO: loaded from: classes4.dex */
public class DisplayUtil {
    private static final String TAG = "DisplayUtils";
    private static final TypedValue mTmpValue = new TypedValue();
    public static final Typeface FONT_MIUI_BOLD = Typeface.create("miui-bold", 0);
    public static Typeface FONT_MIUI_REGULAR = null;
    public static final Typeface FONT_MIPRO_MEDIUM = Typeface.create(MiuiFontName.NORMAL, 0);
    public static Typeface FONT_MIPRO_REGULAR = null;
    public static Typeface FONT_MIPRO_BOLD = null;

    public static int getXmlDef(Context context, int i) {
        int iComplexToFloat;
        TypedValue typedValue = mTmpValue;
        synchronized (typedValue) {
            context.getResources().getValue(i, typedValue, true);
            iComplexToFloat = (int) TypedValue.complexToFloat(typedValue.data);
        }
        return iComplexToFloat;
    }

    public static boolean isInFullWindowGestureMode(Context context) {
        boolean z = Settings.Global.getInt(context.getContentResolver(), BaseFloatFullWindow.FORCE_FSG_NAV_BAR, 0) != 0;
        Logger.INSTANCE.i(TAG, "isInFullWindowGestureMode " + z);
        return z;
    }

    public static boolean hasNavigationBar(Context context) {
        if (isInFullWindowGestureMode(context)) {
            Logger.INSTANCE.i(TAG, "hasNavigationBar return false");
            return false;
        }
        try {
            Object objInvoke = Class.forName("android.view.WindowManagerGlobal").getMethod("getWindowManagerService", new Class[0]).invoke(null, new Object[0]);
            return ((Boolean) objInvoke.getClass().getMethod("hasNavigationBar", Integer.TYPE).invoke(objInvoke, context.getClass().getMethod("getDisplayId", new Class[0]).invoke(context, new Object[0]))).booleanValue();
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, "hasNavigationBar Q" + e);
            return false;
        }
    }

    public static int getNavigationBarHeightFromProp(Context context) {
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("navigation_bar_height", "dimen", AppSearchAppFunctionReader.SYSTEM_PACKAGE_NAME);
        int dimensionPixelSize = identifier > 0 ? resources.getDimensionPixelSize(identifier) : 0;
        Logger.INSTANCE.i(TAG, "getNavigationBarHeightFromProp = " + dimensionPixelSize);
        return dimensionPixelSize;
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

    public static DisplayMetrics getScreenMetrics(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics;
    }

    public static int getMaxHeight(Context context) {
        double dMax;
        double d;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
        if (RomUtils.isPadDevice()) {
            dMax = Math.min(displayMetrics.heightPixels, displayMetrics.widthPixels);
            d = 0.76d;
        } else {
            dMax = Math.max(displayMetrics.heightPixels, displayMetrics.widthPixels);
            d = 0.7d;
        }
        return (int) (dMax * d);
    }

    public static int getRealWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int dip2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static boolean isLandScape(Context context) {
        int[] screenSizeDp = MiuixUIUtils.getScreenSizeDp(context);
        return screenSizeDp[0] > screenSizeDp[1];
    }

    public static boolean isNightMode(Context context) {
        return (context.getResources().getConfiguration().uiMode & 48) == 32;
    }

    public static void setMiuiMediumTypeFace(TextView textView) {
        if (RomUtils.isInternationalBuild()) {
            textView.setTypeface(null, 1);
        } else if (isMiuiXISdkSupported()) {
            textView.setTypeface(FONT_MIPRO_MEDIUM);
        } else {
            textView.setTypeface(FONT_MIUI_BOLD);
        }
    }

    public static boolean isMiuiXISdkSupported() {
        return UIUtils.getMiuiVersion() >= 9;
    }
}
