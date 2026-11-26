package com.miui.notes.ai.utils;

import android.content.Context;
import com.miui.notes.base.utils.Logger;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class SplitScreenModelUtils {
    private static final String TAG = "SplitScreenModelUtils";

    public enum SplitResult {
        TRUE,
        FALSE,
        NOT_FOUND
    }

    public static List<String> getTopSplitPackageNames(Context context) {
        try {
            return (List) Class.forName("android.util.MiuiMultiWindowUtils").getMethod("getTopSplitPackageNames", Context.class).invoke(null, context);
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static SplitResult isSplitScreenMode(int i) {
        try {
            Method method = Class.forName("android.app.ActivityManager").getMethod("isInSplitScreenWindowingMode", Integer.TYPE);
            method.setAccessible(true);
            if (((Boolean) method.invoke(null, Integer.valueOf(i))).booleanValue()) {
                Logger.INSTANCE.i(TAG, "isSplitScreenMode true");
                return SplitResult.TRUE;
            }
            Logger.INSTANCE.i(TAG, "isSplitScreenMode false");
            return SplitResult.FALSE;
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            Logger.INSTANCE.i(TAG, "isSplitScreenMode not found");
            return SplitResult.NOT_FOUND;
        }
    }
}
