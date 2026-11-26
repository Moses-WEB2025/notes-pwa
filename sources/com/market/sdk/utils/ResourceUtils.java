package com.market.sdk.utils;

import android.content.res.MiuiConfiguration;
import com.market.sdk.MarketManager;

/* JADX INFO: loaded from: classes2.dex */
public class ResourceUtils {
    public static int getScreenWidth() {
        return MarketManager.getContext().getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return MarketManager.getContext().getResources().getDisplayMetrics().heightPixels;
    }

    public static boolean isBigFontMode() {
        try {
            int scaleMode = MiuiConfiguration.getScaleMode();
            return scaleMode == 11 || scaleMode == 14 || scaleMode == 15;
        } catch (Throwable th) {
            android.util.Log.d(MarketManager.TAG, th.toString());
            return false;
        }
    }
}
