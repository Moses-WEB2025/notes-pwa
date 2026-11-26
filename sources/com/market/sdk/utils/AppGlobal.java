package com.market.sdk.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;

/* JADX INFO: loaded from: classes2.dex */
public class AppGlobal {
    private static Context sContext;

    public static Context getContext() {
        Context context = sContext;
        if (context != null) {
            return context;
        }
        throw new IllegalArgumentException("sContext is null,should call setContext first!!");
    }

    public static void setContext(Context context) {
        sContext = context.getApplicationContext();
        init();
    }

    private static void init() {
        if (PrefUtils.getLong(com.market.sdk.Constants.SDK_BEGIN_TIME, new PrefUtils.PrefFile[0]) == 0) {
            PrefUtils.setLong(com.market.sdk.Constants.SDK_BEGIN_TIME, System.currentTimeMillis(), new PrefUtils.PrefFile[0]);
        }
    }

    @Deprecated
    public static void setContext(Activity activity) {
        sContext = activity;
        init();
    }

    public static PackageManager getPackageManager() {
        return sContext.getPackageManager();
    }

    public static ContentResolver getContentResolver() {
        return sContext.getContentResolver();
    }
}
