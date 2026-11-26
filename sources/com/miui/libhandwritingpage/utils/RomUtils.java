package com.miui.libhandwritingpage.utils;

import miui.os.Build;
import miui.util.FeatureParser;
import miuix.core.util.SystemProperties;
import miuix.os.DeviceHelper;
import xc.engine.api.CreationPageSDK;

/* JADX INFO: loaded from: classes3.dex */
public class RomUtils {
    private static Boolean isUseNewCreationAI;

    public static boolean isUseNewCreationAI() {
        return false;
    }

    public static boolean isPadDevice() {
        return Build.IS_TABLET;
    }

    public static boolean isPhoneDevice() {
        return (isFoldDevice() || isPadDevice()) ? false : true;
    }

    public static boolean isFoldDevice() {
        return DeviceHelper.detectType(CreationPageSDK.sApplication.getApplicationContext()) == 3 || android.os.Build.DEVICE.equals("cetus") || android.os.Build.DEVICE.equals("zizhan");
    }

    public static boolean isN81A() {
        return android.os.Build.DEVICE.equals("sheng");
    }

    public static boolean isN83() {
        return android.os.Build.DEVICE.equals("dizi");
    }

    public static boolean isO80() {
        return android.os.Build.DEVICE.equals("jinghu");
    }

    public static boolean isO81() {
        return android.os.Build.DEVICE.equals("muyu");
    }

    public static boolean isO82() {
        return android.os.Build.DEVICE.equals("uke");
    }

    public static boolean isP81() {
        return android.os.Build.DEVICE.equals("piano");
    }

    public static boolean isP82() {
        return android.os.Build.DEVICE.equals("yupei");
    }

    public static boolean isInternationalBuild() {
        return miuix.os.Build.IS_INTERNATIONAL_BUILD;
    }

    public static boolean isLargePad() {
        return android.os.Build.DEVICE.equals("yudi");
    }

    public static boolean isSupportStylus() {
        return FeatureParser.getBoolean("support_stylus_gesture", false);
    }

    public static String getRegion() {
        return SystemProperties.get("ro.miui.build.region");
    }
}
