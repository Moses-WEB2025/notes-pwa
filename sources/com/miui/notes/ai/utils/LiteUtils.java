package com.miui.notes.ai.utils;

import miuix.core.util.SystemProperties;
import miuix.device.DeviceUtils;

/* JADX INFO: loaded from: classes4.dex */
public class LiteUtils {
    private static Boolean IS_LITE = null;
    private static Boolean IS_MIDDLE = null;
    private static Boolean IS_MISHOW_ROM = null;
    private static Boolean IS_NEW_LITE = null;
    private static Boolean IS_SUPER_LITE = null;
    private static final String TAG = "LiteUtils";

    public static boolean isSuperLite() {
        if (IS_SUPER_LITE == null) {
            IS_SUPER_LITE = Boolean.valueOf(DeviceUtils.isMiuiLiteV2());
        }
        return IS_SUPER_LITE.booleanValue();
    }

    public static boolean isAllLiteOrMiddle() {
        return isLite() || isNewLite() || isSuperLite() || isMiddle();
    }

    public static boolean isNewLiteOrMiddle() {
        return isNewLite() || isMiddle();
    }

    public static boolean isLiteOrMiddle() {
        return isLite() || isMiddle();
    }

    public static boolean isNewLite() {
        if (IS_NEW_LITE == null) {
            IS_NEW_LITE = Boolean.valueOf(isLite() && !DeviceUtils.isLiteV1Stock());
        }
        return IS_NEW_LITE.booleanValue();
    }

    public static boolean isMiddle() {
        if (IS_MIDDLE == null) {
            IS_MIDDLE = Boolean.valueOf(DeviceUtils.isMiuiMiddle());
        }
        return IS_MIDDLE.booleanValue();
    }

    public static boolean isLite() {
        if (IS_LITE == null) {
            IS_LITE = Boolean.valueOf(DeviceUtils.isMiuiLiteRom());
        }
        return IS_LITE.booleanValue();
    }

    public static boolean isMishowRom() {
        if (IS_MISHOW_ROM == null) {
            String str = SystemProperties.get("ro.product.mod_device");
            IS_MISHOW_ROM = Boolean.valueOf(str != null && str.contains("demo"));
        }
        return IS_MISHOW_ROM.booleanValue();
    }
}
