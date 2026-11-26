package com.xiaomi.phonenum.data;

import android.content.Context;

/* JADX INFO: loaded from: classes4.dex */
public class PhoneTokenCache {
    private static final String SP_FILE_NAME = "activation_phone_token";
    private static volatile SharedPrefsStringCache sCache;

    private static SharedPrefsStringCache getCache(Context context) {
        if (sCache == null) {
            synchronized (PhoneTokenCache.class) {
                if (sCache == null) {
                    sCache = new SharedPrefsStringCache(context, SP_FILE_NAME);
                }
            }
        }
        return sCache;
    }

    public static String get(Context context, int i) {
        return getCache(context).get(String.valueOf(i));
    }

    public static void put(Context context, int i, String str) {
        getCache(context).put(String.valueOf(i), str);
    }
}
