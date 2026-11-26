package com.xiaomi.accountsdk.account.config;

import android.content.Context;
import android.provider.Settings;

/* JADX INFO: loaded from: classes4.dex */
public class SystemAdStatus {
    public static final String SYSTEM_AD_OFF = "OFF";
    public static final String SYSTEM_AD_ON = "ON";
    public static final String SYSTEM_AD_STATUS = "passport_ad_status";

    public static void set(Context context, String str) {
        Settings.Global.putString(context.getContentResolver(), SYSTEM_AD_STATUS, str);
    }

    public static void set(Context context, boolean z) {
        set(context, z ? SYSTEM_AD_ON : SYSTEM_AD_OFF);
    }

    public static void unset(Context context) {
        Settings.Global.putString(context.getContentResolver(), SYSTEM_AD_STATUS, null);
    }
}
