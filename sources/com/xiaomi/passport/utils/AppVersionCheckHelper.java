package com.xiaomi.passport.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import com.xiaomi.accountsdk.utils.AccountLogger;

/* JADX INFO: loaded from: classes4.dex */
public class AppVersionCheckHelper {
    private static final String SP_KEY_APP_VERSION = "app_version";
    private static final String SP_NAME = "passport_app_version";
    private static final String TAG = "AppVersionCheckHelper";

    public static void checkAppVersion(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NAME, 0);
        int i = sharedPreferences.getInt("app_version", -1);
        int versionCode = getVersionCode(context);
        AccountLogger.log(TAG, "checkAppVersion>>>cacheAppVersion=" + i + "  curAppVersion=" + versionCode);
        if (i != versionCode) {
            sharedPreferences.edit().putInt("app_version", versionCode).apply();
        }
    }

    public static int getVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            AccountLogger.log(TAG, "getVersionCode>>>get err", e);
            return 0;
        }
    }
}
