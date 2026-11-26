package com.xiaomi.accountsdk.utils;

import com.xiaomi.mirror.synergy.CallMethod;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes4.dex */
public class SystemPropertiesReflection {
    private static final String CLASS_NAME = "android.os.SystemProperties";

    private SystemPropertiesReflection() {
    }

    public static String get(String str, String str2) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, InvocationTargetException {
        return (String) Class.forName(CLASS_NAME).getMethod(CallMethod.METHOD_GET, String.class, String.class).invoke(null, str, str2);
    }

    public static int getInt(String str, int i) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, InvocationTargetException {
        return ((Integer) Class.forName(CLASS_NAME).getMethod("getInt", String.class, Integer.TYPE).invoke(null, str, Integer.valueOf(i))).intValue();
    }
}
