package com.xiaomi.accountsdk.utils;

import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes4.dex */
public class SystemPropertiesHelper {
    public static final boolean IS_SYSTEM_DEBUGGABLE;
    private static final String TAG = "SystemPropertiesHelper";

    static {
        boolean z = false;
        try {
            if (SystemPropertiesReflection.getInt("ro.debuggable", 0) == 1) {
                z = true;
            }
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            AccountLogger.log(TAG, e);
        }
        IS_SYSTEM_DEBUGGABLE = z;
    }

    private SystemPropertiesHelper() {
    }
}
