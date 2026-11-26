package androidx.appsearch.util;

import androidx.appsearch.app.AppSearchEnvironmentFactory;

/* JADX INFO: loaded from: classes.dex */
public final class LogUtil {
    public static final boolean DEBUG = false;
    public static final boolean INFO = AppSearchEnvironmentFactory.getEnvironmentInstance().isInfoLoggingEnabled();
    private static final int PII_TRACE_LEVEL = 0;

    public static boolean isPiiTraceEnabled() {
        return false;
    }

    public static void piiTrace(String str, String str2, Object obj, Object obj2) {
    }

    private LogUtil() {
    }

    public static void piiTrace(String str, String str2) {
        piiTrace(str, str2, null, null);
    }

    public static void piiTrace(String str, String str2, Object obj) {
        piiTrace(str, str2, obj, null);
    }
}
