package com.market.sdk.utils;

import com.market.sdk.MarketManager;

/* JADX INFO: loaded from: classes2.dex */
public class Build {
    public static boolean isInternational() {
        try {
            return miuix.os.Build.IS_INTERNATIONAL_BUILD;
        } catch (Throwable th) {
            android.util.Log.d(MarketManager.TAG, th.toString());
            return false;
        }
    }
}
