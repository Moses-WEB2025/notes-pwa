package com.miui.handwriting.common.tools;

/* JADX INFO: loaded from: classes3.dex */
public class LiteUtils {
    private static Boolean IS_LITE = null;
    private static Boolean IS_MIDDLE = null;
    private static Boolean IS_NEW_LITE = null;
    private static Boolean IS_SUPER_LITE = null;
    private static final String TAG = "LiteUtils";

    public static boolean isSuperLite() {
        if (IS_SUPER_LITE == null) {
            IS_SUPER_LITE = Boolean.valueOf(miuix.device.DeviceUtils.isMiuiLiteV2());
        }
        return IS_SUPER_LITE.booleanValue();
    }

    public static boolean isNewLiteOrMiddle() {
        return isNewLite() || isMiddle();
    }

    public static boolean isLiteOrMiddle() {
        return isLite() || isMiddle();
    }

    public static boolean isNewLite() {
        if (IS_NEW_LITE == null) {
            IS_NEW_LITE = Boolean.valueOf(isLite() && !miuix.device.DeviceUtils.isLiteV1Stock());
        }
        return IS_NEW_LITE.booleanValue();
    }

    public static boolean isMiddle() {
        if (IS_MIDDLE == null) {
            IS_MIDDLE = Boolean.valueOf(miuix.device.DeviceUtils.isMiuiMiddle());
        }
        return IS_MIDDLE.booleanValue();
    }

    public static boolean isLite() {
        if (IS_LITE == null) {
            IS_LITE = Boolean.valueOf(miuix.device.DeviceUtils.isMiuiLiteRom());
        }
        return IS_LITE.booleanValue();
    }
}
