package com.miui.richeditor.util;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.miui.notes.NoteApp;
import com.miui.todo.utils.MetaReflectUtil;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes4.dex */
public class CommonUtils {
    public static boolean isAboveAndroidS() {
        return Build.VERSION.SDK_INT >= 31;
    }

    public static int getWindowHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static int getWindowWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.widthPixels;
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
        Configuration configuration = context.getResources().getConfiguration();
        try {
            Field declaredField = configuration.getClass().getDeclaredField("windowConfiguration");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(configuration);
            return ((Integer) obj.getClass().getDeclaredMethod("getWindowingMode", new Class[0]).invoke(obj, new Object[0])).intValue() == 5;
        } catch (Exception unused) {
        }
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
        } catch (Exception unused) {
            ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
            return displayMetrics;
        }
    }
}
