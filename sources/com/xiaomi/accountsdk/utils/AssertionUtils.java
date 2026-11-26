package com.xiaomi.accountsdk.utils;

import android.content.Context;

/* JADX INFO: loaded from: classes4.dex */
public class AssertionUtils {
    private static final String TAG = "AssertionUtils";

    private AssertionUtils() {
    }

    public static class PassportAssertionException extends RuntimeException {
        PassportAssertionException(String str) {
            super(str);
        }
    }

    public static void checkCondition(Context context, boolean z, String str, boolean z2) {
        if (context == null) {
            throw new NullPointerException("context cannot be null");
        }
        if (z) {
            return;
        }
        PassportAssertionException passportAssertionException = new PassportAssertionException(str);
        if (!z2 || isDebuggable(context)) {
            throw passportAssertionException;
        }
        AccountLogger.log(TAG, "Assertion error in non-debuggable app. Special notice is needed, maybe program logic error, please fix it.", passportAssertionException);
    }

    private static boolean isDebuggable(Context context) {
        return SystemPropertiesHelper.IS_SYSTEM_DEBUGGABLE || PackageUtils.isAppDebuggable(context);
    }
}
