package com.xiaomi.micloudsdk.utils;

import android.text.TextUtils;
import miui.cloud.os.SystemProperties;

/* JADX INFO: loaded from: classes4.dex */
public class SystemUtils {
    private static final int MIUI12_VERSION_CODE = 10;
    private static final String MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String MI_OS_VERSION_CODE = "ro.mi.os.version.code";
    private static final String MI_OS_VERSION_NAME = "ro.mi.os.version.name";
    private static final int MI_SYSTEM_DEFAULT_VERSION_CODE = 0;

    public static boolean isMiui12() {
        return isMiOS() || getMiuiVersionCode() >= 10;
    }

    private static boolean isMiuiSystem() {
        return !TextUtils.isEmpty(SystemProperties.get(MIUI_VERSION_CODE, ""));
    }

    private static int getMiuiVersionCode() {
        return SystemProperties.getInt(MIUI_VERSION_CODE, 0);
    }

    private static String getMiuiVersionName() {
        return SystemProperties.get(MIUI_VERSION_NAME);
    }

    public static boolean isMiOS() {
        return !TextUtils.isEmpty(SystemProperties.get(MI_OS_VERSION_CODE, ""));
    }

    private static int getMiOSVersionCode() {
        return SystemProperties.getInt(MI_OS_VERSION_CODE, 0);
    }

    private static String getMiOSVersionName() {
        return SystemProperties.get(MI_OS_VERSION_NAME);
    }

    public static boolean isMiSystem() {
        return isMiOS() || isMiuiSystem();
    }

    public static int getMiSystemVersionCode() {
        int miOSVersionCode = getMiOSVersionCode();
        return miOSVersionCode == 0 ? getMiuiVersionCode() : miOSVersionCode;
    }

    public static String getMiSystemVersionName() {
        String miOSVersionName = getMiOSVersionName();
        return TextUtils.isEmpty(miOSVersionName) ? getMiuiVersionName() : miOSVersionName;
    }
}
