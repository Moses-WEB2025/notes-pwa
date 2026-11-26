package com.xiaomi.onetrack.util;

import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes4.dex */
public class l {
    private static Method a;
    private static Method b;
    private static Method c;

    static {
        try {
            Class<?> cls = Class.forName("miui.util.FeatureParser");
            a = cls.getMethod("getString", String.class);
            b = cls.getMethod("getBoolean", String.class);
            c = cls.getMethod("getInteger", String.class);
        } catch (Throwable unused) {
        }
    }

    public static String a(String str) {
        Method method = a;
        if (method == null) {
            return "";
        }
        try {
            return String.valueOf(method.invoke(null, str));
        } catch (Throwable unused) {
            return "";
        }
    }

    public static boolean a(String str, boolean z) {
        Method method = b;
        if (method != null) {
            try {
                return ((Boolean) method.invoke(null, str, Boolean.valueOf(z))).booleanValue();
            } catch (Throwable unused) {
            }
        }
        return z;
    }

    public static int a(String str, int i) {
        Method method = c;
        if (method != null) {
            try {
                return ((Integer) method.invoke(null, str, Integer.valueOf(i))).intValue();
            } catch (Throwable unused) {
            }
        }
        return i;
    }
}
