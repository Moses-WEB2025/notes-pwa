package com.xiaomi.accountsdk.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import java.util.Arrays;

/* JADX INFO: loaded from: classes4.dex */
public class PackageUtils {
    public static final String TAG = "PackageUtils";

    private PackageUtils() {
    }

    public static boolean isAppDebuggable(Context context) {
        if (context != null) {
            return (context.getApplicationInfo().flags & 2) != 0;
        }
        throw new NullPointerException("context cannot be null");
    }

    private static String[] getPackagesByUid(Context context, int i) {
        return context.getPackageManager().getPackagesForUid(i);
    }

    public static String getMinimumPackageNameByCallingUid(Context context, int i) {
        String[] packagesByUid = getPackagesByUid(context, i);
        if (packagesByUid == null || packagesByUid.length <= 0) {
            return null;
        }
        Arrays.sort(packagesByUid);
        return packagesByUid[0];
    }

    public static boolean isPkgExist(Context context, String str) {
        try {
            context.getPackageManager().getApplicationInfo(str, 0);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public static int getPkgVersionCode(Context context, String str) {
        try {
            return context.getPackageManager().getPackageInfo(str, 0).versionCode;
        } catch (PackageManager.NameNotFoundException unused) {
            return -1;
        }
    }

    public static String getWeiXinAppIDFromMetaData(Context context) {
        try {
            return context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getString("WEIXIN_APP_ID");
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }
}
