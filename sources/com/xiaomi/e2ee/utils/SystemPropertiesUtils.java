package com.xiaomi.e2ee.utils;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

/* JADX INFO: loaded from: classes4.dex */
public class SystemPropertiesUtils {
    private static final String CLASS_NAME = "android.os.SystemProperties";
    private static final String E2EE_STATUS_STR = "hyper_cloud_e2ee_status_micloud";

    public static int getIntProperty(String str, int i) {
        try {
            Class<?> cls = Class.forName(CLASS_NAME);
            return ((Integer) cls.getMethod("getInt", String.class, Integer.TYPE).invoke(cls, str, Integer.valueOf(i))).intValue();
        } catch (Exception unused) {
            Log.e("SystemPropertiesUtils_Log", "read " + str + " failed");
            return i;
        }
    }

    public static int getE2EEStatus(Context context) {
        try {
            return Settings.Secure.getInt(context.getContentResolver(), E2EE_STATUS_STR);
        } catch (Settings.SettingNotFoundException unused) {
            Log.e("SystemPropertiesUtils_Log", "not found hyper_cloud_e2ee_status_micloud");
            return -1;
        }
    }
}
