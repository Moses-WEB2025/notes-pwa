package com.xiaomi.ai.utils;

import android.content.Context;
import com.xiaomi.ai.mibrain.Mibrainsdk;

/* JADX INFO: loaded from: classes4.dex */
public final class Log {
    public static final int MAX_LOG_FILE_COUNT = 5;
    public static final int MAX_LOG_FILE_SIZE_IN_BYTE = 1048576;
    private static volatile boolean sInitialized = false;
    private static boolean sLogToFile;
    private static Mibrainsdk.MibrainsdkLogHook sLogHook = new d();
    private static ExternalLogHook sExternalLogHook = null;

    public interface ExternalLogHook {
        void onLog(int i, String str, String str2, Throwable th);
    }

    public static void MiLogD(String str, String str2) {
        if (Mibrainsdk.MIBRAIN_LOG_CURRENT_LEVEL <= Mibrainsdk.MIBRAIN_DEBUG_LEVEL_DEBUG) {
            d(str, str2);
        }
    }

    public static void MiLogD(String str, String str2, Throwable th) {
        if (Mibrainsdk.MIBRAIN_LOG_CURRENT_LEVEL <= Mibrainsdk.MIBRAIN_DEBUG_LEVEL_DEBUG) {
            d(str, str2, th);
        }
    }

    public static void MiLogE(String str, String str2) {
        if (Mibrainsdk.MIBRAIN_LOG_CURRENT_LEVEL <= Mibrainsdk.MIBRAIN_DEBUG_LEVEL_ERROR) {
            e(str, str2);
        }
    }

    public static void MiLogE(String str, String str2, Throwable th) {
        if (Mibrainsdk.MIBRAIN_LOG_CURRENT_LEVEL <= Mibrainsdk.MIBRAIN_DEBUG_LEVEL_ERROR) {
            e(str, str2, th);
        }
    }

    public static void MiLogW(String str, String str2) {
        if (Mibrainsdk.MIBRAIN_LOG_CURRENT_LEVEL <= Mibrainsdk.MIBRAIN_DEBUG_LEVEL_WARNING) {
            w(str, str2);
        }
    }

    public static void MiLogW(String str, String str2, Throwable th) {
        if (Mibrainsdk.MIBRAIN_LOG_CURRENT_LEVEL <= Mibrainsdk.MIBRAIN_DEBUG_LEVEL_WARNING) {
            w(str, str2, th);
        }
    }

    public static void d(String str, String str2) {
        if (injectLog(3, str, str2, null)) {
            return;
        }
        android.util.Log.d(str, str2);
        if (sLogToFile) {
            o.a(3, str, str2);
        }
    }

    public static void d(String str, String str2, Throwable th) {
        if (injectLog(3, str, str2, th)) {
            return;
        }
        android.util.Log.d(str, str2, th);
        if (sLogToFile) {
            o.a(3, str, str2, th);
        }
    }

    public static void e(String str, String str2) {
        if (injectLog(6, str, str2, null)) {
            return;
        }
        android.util.Log.e(str, str2);
        if (sLogToFile) {
            o.a(6, str, str2);
        }
    }

    public static void e(String str, String str2, Throwable th) {
        if (injectLog(6, str, str2, th)) {
            return;
        }
        android.util.Log.e(str, str2, th);
        if (sLogToFile) {
            o.a(6, str, str2, th);
        }
    }

    public static void i(String str, String str2) {
        if (injectLog(4, str, str2, null)) {
            return;
        }
        android.util.Log.i(str, str2);
        if (sLogToFile) {
            o.a(4, str, str2);
        }
    }

    public static void i(String str, String str2, Throwable th) {
        if (injectLog(4, str, str2, th)) {
            return;
        }
        android.util.Log.i(str, str2, th);
        if (sLogToFile) {
            o.a(4, str, str2, th);
        }
    }

    public static void init(Context context) {
        synchronized (Log.class) {
            if (!sInitialized) {
                o.a((o.a) new i(context.getApplicationContext(), context.getPackageName(), 1048576, 5, o.b()));
                sInitialized = true;
            }
        }
    }

    private static boolean injectLog(int i, String str, String str2, Throwable th) {
        ExternalLogHook externalLogHook = sExternalLogHook;
        if (externalLogHook == null) {
            return false;
        }
        externalLogHook.onLog(i, str, str2, th);
        return true;
    }

    public static void setExternalLogHook(ExternalLogHook externalLogHook) {
        sExternalLogHook = externalLogHook;
    }

    public static void setJNILogHook(boolean z) {
        Mibrainsdk.setMibrainsdkLogHook(z ? sLogHook : null);
    }

    public static void setLogToFile(boolean z) {
        sLogToFile = z;
    }

    public static void v(String str, String str2) {
        if (injectLog(2, str, str2, null)) {
            return;
        }
        android.util.Log.v(str, str2);
        if (sLogToFile) {
            o.a(2, str, str2);
        }
    }

    public static void v(String str, String str2, Throwable th) {
        if (injectLog(2, str, str2, th)) {
            return;
        }
        android.util.Log.v(str, str2, th);
        if (sLogToFile) {
            o.a(2, str, str2, th);
        }
    }

    public static void w(String str, String str2) {
        if (injectLog(5, str, str2, null)) {
            return;
        }
        android.util.Log.w(str, str2);
        if (sLogToFile) {
            o.a(5, str, str2);
        }
    }

    public static void w(String str, String str2, Throwable th) {
        if (injectLog(5, str, str2, th)) {
            return;
        }
        android.util.Log.w(str, str2, th);
        if (sLogToFile) {
            o.a(5, str, str2, th);
        }
    }
}
