package com.market.sdk.utils;

import com.market.sdk.MarketManager;
import miuix.core.util.SystemProperties;

/* JADX INFO: loaded from: classes2.dex */
public class Region {
    public static final String ES = "ES";
    public static final String ID = "ID";
    public static final String IN = "IN";
    public static final String RU = "RU";

    public static String getRegion() {
        try {
            return SystemProperties.get("ro.miui.region", "CN");
        } catch (Throwable th) {
            android.util.Log.d(MarketManager.TAG, th.toString());
            return "CN";
        }
    }
}
