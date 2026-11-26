package com.market.sdk.utils;

import android.text.TextUtils;

/* JADX INFO: loaded from: classes2.dex */
public class Log {
    private static final int DEBUG = 3;
    private static final int ERROR = 0;
    private static final int INFO = 2;
    private static final int MAX_CHAR_SIZE_PER_LOG = 3000;
    private static final String PREFIX = "MarketSdk-";
    private static final int VERBOSE = 4;
    private static final int WARN = 1;

    public static void d(String str, String str2) {
        log(addPrefix(str), str2, 3);
    }

    public static void d(String str, String str2, Throwable th) {
        log(addPrefix(str), str2, th, 3);
    }

    public static void e(String str, String str2) {
        log(addPrefix(str), str2, 0);
    }

    public static void e(String str, String str2, Throwable th) {
        log(addPrefix(str), str2, th, 0);
    }

    public static void w(String str, String str2) {
        log(addPrefix(str), str2, 1);
    }

    public static void w(String str, String str2, Throwable th) {
        log(addPrefix(str), str2, th, 1);
    }

    public static void i(String str, String str2) {
        log(addPrefix(str), str2, 2);
    }

    public static void i(String str, String str2, Throwable th) {
        log(addPrefix(str), str2, th, 2);
    }

    public static void v(String str, String str2) {
        log(addPrefix(str), str2, 4);
    }

    public static void v(String str, String str2, Throwable th) {
        log(addPrefix(str), str2, th, 4);
    }

    private static void log(String str, String str2, int i) {
        if (TextUtils.isEmpty(str2) || str2.length() <= 3000) {
            logSubMessage(str, str2, i);
            return;
        }
        int i2 = 0;
        while (i2 <= str2.length() / 3000) {
            int i3 = i2 * 3000;
            i2++;
            int iMin = Math.min(str2.length(), i2 * 3000);
            if (i3 < iMin) {
                logSubMessage(str, str2.substring(i3, iMin), i);
            }
        }
    }

    private static void log(String str, String str2, Throwable th, int i) {
        if (TextUtils.isEmpty(str2) || str2.length() <= 3000) {
            logSubMessage(str, str2, th, i);
            return;
        }
        int i2 = 0;
        while (i2 <= str2.length() / 3000) {
            int i3 = i2 * 3000;
            i2++;
            int iMin = Math.min(str2.length(), i2 * 3000);
            if (i3 < iMin) {
                logSubMessage(str, str2.substring(i3, iMin), th, i);
            }
        }
    }

    private static void logSubMessage(String str, String str2, int i) {
        if (str2 == null) {
            str2 = "";
        }
        if (i == 0) {
            android.util.Log.e(str, str2);
            return;
        }
        if (i == 1) {
            android.util.Log.w(str, str2);
            return;
        }
        if (i == 2) {
            android.util.Log.i(str, str2);
        } else if (i == 3) {
            android.util.Log.d(str, str2);
        } else {
            if (i != 4) {
                return;
            }
            android.util.Log.v(str, str2);
        }
    }

    private static void logSubMessage(String str, String str2, Throwable th, int i) {
        if (str2 == null) {
            str2 = "";
        }
        if (i == 0) {
            android.util.Log.e(str, str2, th);
            return;
        }
        if (i == 1) {
            android.util.Log.w(str, str2, th);
            return;
        }
        if (i == 2) {
            android.util.Log.i(str, str2, th);
        } else if (i == 3) {
            android.util.Log.d(str, str2, th);
        } else {
            if (i != 4) {
                return;
            }
            android.util.Log.v(str, str2, th);
        }
    }

    public static String addPrefix(String str) {
        return PREFIX + str;
    }
}
