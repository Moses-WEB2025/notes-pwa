package com.xiaomi.onetrack.util;

import android.util.Log;
import com.xiaomi.mirror.synergy.CallMethod;

/* JADX INFO: loaded from: classes4.dex */
public class ad {
    private static final String a = "SystemProperties";

    public static String a(String str, String str2) {
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod(CallMethod.METHOD_GET, String.class, String.class).invoke(null, str, str2);
        } catch (Throwable th) {
            Log.e(r.a(a), "get e" + th.getMessage());
            return str2;
        }
    }

    public static String a(String str) {
        return a(str, "");
    }

    public static long a(String str, Long l) {
        try {
            return ((Long) Class.forName("android.os.SystemProperties").getMethod("getLong", String.class, Long.TYPE).invoke(null, str, l)).longValue();
        } catch (Throwable th) {
            Log.e(r.a(a), "getLong e" + th.getMessage());
            return l.longValue();
        }
    }
}
