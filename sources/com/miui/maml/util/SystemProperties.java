package com.miui.maml.util;

import com.xiaomi.mirror.synergy.CallMethod;

/* JADX INFO: loaded from: classes3.dex */
public class SystemProperties {
    public static final int PROP_NAME_MAX = 31;
    public static final int PROP_VALUE_MAX = 91;
    private static Class<?> classSystemProperties = null;
    private static boolean isSupportGet = false;
    private static boolean isSupportGetBoolean = false;
    private static boolean isSupportGetInt = false;
    private static boolean isSupportGetLong = false;
    private static boolean isSupportSet = false;

    public static String get(String str, String str2) {
        return isSupportGet ? android.os.SystemProperties.get(str, str2) : str2;
    }

    public static String get(String str) {
        return get(str, "");
    }

    public static int getInt(String str, int i) {
        return isSupportGetInt ? android.os.SystemProperties.getInt(str, i) : i;
    }

    public static long getLong(String str, long j) {
        return isSupportGetLong ? android.os.SystemProperties.getLong(str, j) : j;
    }

    public static boolean getBoolean(String str, boolean z) {
        return isSupportGetBoolean ? android.os.SystemProperties.getBoolean(str, z) : z;
    }

    static {
        try {
            classSystemProperties = Class.forName("android.os.SystemProperties");
        } catch (Exception unused) {
            classSystemProperties = null;
        }
        Class<?> cls = classSystemProperties;
        if (cls != null) {
            boolean z = true;
            try {
                isSupportGet = cls.getMethod(CallMethod.METHOD_GET, String.class, String.class) != null;
            } catch (Exception unused2) {
                isSupportGet = false;
            }
            try {
                isSupportGetInt = classSystemProperties.getMethod("getInt", String.class, Integer.TYPE) != null;
            } catch (Exception unused3) {
                isSupportGetInt = false;
            }
            try {
                isSupportGetLong = classSystemProperties.getMethod("getLong", String.class, Long.TYPE) != null;
            } catch (Exception unused4) {
                isSupportGetLong = false;
            }
            try {
                isSupportGetBoolean = classSystemProperties.getMethod("getBoolean", String.class, Boolean.TYPE) != null;
            } catch (Exception unused5) {
                isSupportGetBoolean = false;
            }
            try {
                if (classSystemProperties.getMethod("set", String.class, String.class) == null) {
                    z = false;
                }
                isSupportSet = z;
            } catch (Exception unused6) {
                isSupportSet = false;
            }
        }
    }
}
