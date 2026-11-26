package com.xiaomi.phonenum.data;

import android.content.Context;

/* JADX INFO: loaded from: classes4.dex */
public class PlainPhoneNumberCache {
    private static final String SP_FILE_NAME = "phone_number";
    private static volatile SharedPrefsStringCache sCache;

    private static SharedPrefsStringCache getCache(Context context) {
        if (sCache == null) {
            synchronized (PlainPhoneNumberCache.class) {
                if (sCache == null) {
                    sCache = new SharedPrefsStringCache(context, SP_FILE_NAME);
                }
            }
        }
        return sCache;
    }

    public static PlainPhoneNumber get(Context context, int i) {
        String str = getCache(context).get(String.valueOf(i));
        if (str == null) {
            return null;
        }
        return new PlainPhoneNumber(i, str);
    }

    public static void put(Context context, PlainPhoneNumber plainPhoneNumber) {
        getCache(context).put(String.valueOf(plainPhoneNumber.subId), plainPhoneNumber.phoneNumber);
    }
}
