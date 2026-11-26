package com.miui.mishare.tapshare.Util;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes3.dex */
public class WindowUtil {
    private static final String TAG = "WindowUtil";

    public static boolean isSpecialWindowMode(Context context) {
        if (context == null) {
            return false;
        }
        return isWindowModeMultiWindow(context) || isSmallWindowMode(context);
    }

    public static boolean isSpecialWindowMode(Context context, Configuration configuration) {
        if (context == null) {
            return false;
        }
        return isWindowModeMultiWindow(context) || isSmallWindowModeByConfig(configuration);
    }

    private static boolean isWindowModeMultiWindow(Context context) {
        boolean z = false;
        try {
            Class<?> cls = Class.forName("android.content.res.Configuration");
            Configuration configuration = context.getResources().getConfiguration();
            Field field = cls.getField("windowConfiguration");
            field.setAccessible(true);
            int iIntValue = ((Integer) Class.forName("android.app.WindowConfiguration").getDeclaredMethod("getWindowingMode", new Class[0]).invoke(field.get(configuration), new Object[0])).intValue();
            z = iIntValue == 6 || iIntValue == 3 || iIntValue == 4;
            Log.i(TAG, "is multi window: " + z);
            return z;
        } catch (ClassNotFoundException | NoSuchFieldException | NoSuchMethodException e) {
            Log.w(TAG, "WindowConfiguration reflect api not found", e);
            return z;
        } catch (IllegalAccessException e2) {
            Log.w(TAG, "WindowConfiguration reflect access denied", e2);
            return z;
        } catch (InvocationTargetException e3) {
            Log.w(TAG, "WindowConfiguration reflect invoke failed, target=" + e3.getTargetException(), e3);
            return z;
        }
    }

    private static boolean isSmallWindowMode(Context context) {
        boolean z = false;
        try {
            Class<?> cls = Class.forName("android.content.res.Configuration");
            Configuration configuration = context.getResources().getConfiguration();
            Field field = cls.getField("windowConfiguration");
            field.setAccessible(true);
            z = ((Integer) Class.forName("android.app.WindowConfiguration").getDeclaredMethod("getWindowingMode", new Class[0]).invoke(field.get(configuration), new Object[0])).intValue() == 5;
            Log.i(TAG, "is small window: " + z);
            return z;
        } catch (ClassNotFoundException | NoSuchFieldException | NoSuchMethodException e) {
            Log.w(TAG, "isSmallWindowMode WindowConfiguration reflect api not found", e);
            return z;
        } catch (IllegalAccessException e2) {
            Log.w(TAG, "isSmallWindowMode WindowConfiguration reflect access denied", e2);
            return z;
        } catch (InvocationTargetException e3) {
            Log.w(TAG, "isSmallWindowMode WindowConfiguration reflect invoke failed, target=" + e3.getTargetException(), e3);
            return z;
        }
    }

    private static boolean isSmallWindowModeByConfig(Configuration configuration) {
        boolean z = false;
        try {
            Field field = Class.forName("android.content.res.Configuration").getField("windowConfiguration");
            field.setAccessible(true);
            z = ((Integer) Class.forName("android.app.WindowConfiguration").getDeclaredMethod("getWindowingMode", new Class[0]).invoke(field.get(configuration), new Object[0])).intValue() == 5;
            Log.i(TAG, "is small window by configuration: " + z);
            return z;
        } catch (ClassNotFoundException | NoSuchFieldException | NoSuchMethodException e) {
            Log.w(TAG, "isSmallWindowModeByConfig WindowConfiguration reflect api not found", e);
            return z;
        } catch (IllegalAccessException e2) {
            Log.w(TAG, "isSmallWindowModeByConfig WindowConfiguration reflect access denied", e2);
            return z;
        } catch (InvocationTargetException e3) {
            Log.w(TAG, "isSmallWindowModeByConfig WindowConfiguration reflect invoke failed, target=" + e3.getTargetException(), e3);
            return z;
        }
    }
}
