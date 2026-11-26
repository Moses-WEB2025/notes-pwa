package com.miui.mishare.tapshare.Util;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

/* JADX INFO: loaded from: classes3.dex */
public class FlipDeviceUtil {
    private static final String TAG = "FlipDeviceUtil";

    public static boolean isFlipDeviceFolded(Context context) {
        return context != null && isFlipDevice() && isInSmallScreen(context);
    }

    private static boolean isInSmallScreen(Context context) {
        int screenType = getScreenType(context.getResources().getConfiguration());
        Log.i(TAG, "screen type: " + screenType);
        return screenType == 1;
    }

    private static int getScreenType(Configuration configuration) {
        String string = configuration.toString();
        if (string.contains("screenType=0")) {
            return 0;
        }
        if (string.contains("screenType=1")) {
            return 1;
        }
        if (string.contains("screenType=2")) {
            return 2;
        }
        if (string.contains("screenType=3")) {
            return 3;
        }
        if (string.contains("screenType=4")) {
            return 4;
        }
        return string.contains("screenType=5") ? 5 : 0;
    }

    private static boolean isFlipDevice() {
        boolean zBooleanValue = false;
        try {
            Class<?> cls = Class.forName("miui.util.MiuiMultiDisplayTypeInfo");
            zBooleanValue = ((Boolean) cls.getMethod("isFlipDevice", new Class[0]).invoke(cls, new Object[0])).booleanValue();
        } catch (Throwable th) {
            th.printStackTrace();
        }
        Log.i(TAG, "is flip device: " + zBooleanValue);
        return zBooleanValue;
    }
}
