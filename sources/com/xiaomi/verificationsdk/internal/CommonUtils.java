package com.xiaomi.verificationsdk.internal;

import android.os.Build;
import android.os.Looper;
import android.text.TextUtils;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
public class CommonUtils {
    public static String getLocaleString(Locale locale) {
        if (locale == null) {
            return null;
        }
        String language = locale.getLanguage();
        String country = locale.getCountry();
        return TextUtils.isEmpty(country) ? language : String.format("%s_%s", language, country);
    }

    public static void checkWorkThreadAndThrow() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalThreadStateException("cannot be called with main thread");
        }
    }

    public static boolean isXiaomiDevice() {
        return Build.MANUFACTURER.equalsIgnoreCase("XIAOMI") || Build.BRAND.equalsIgnoreCase("XIAOMI") || Build.BRAND.equalsIgnoreCase("REDMI");
    }

    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
