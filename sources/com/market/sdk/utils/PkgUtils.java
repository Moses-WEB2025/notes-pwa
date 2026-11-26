package com.market.sdk.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class PkgUtils {
    private static final String TAG = "PkgUtils";

    public static String getSignatureWithPkgName(Context context, String str) {
        try {
            return loadPkgSignature(context.getPackageManager().getPackageInfo(str, 64));
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    public static String loadPkgSignature(PackageInfo packageInfo) {
        if (packageInfo.signatures != null && packageInfo.signatures.length > 0) {
            return Coder.encodeMD5(packageInfo.signatures[0].toCharsString());
        }
        return "";
    }

    public static List<ResolveInfo> queryIntentServices(Intent intent, int i) {
        List<ResolveInfo> listQueryIntentServices;
        try {
            listQueryIntentServices = AppGlobal.getPackageManager().queryIntentServices(intent, i);
        } catch (Exception e) {
            Log.e(TAG, e.toString(), e);
            listQueryIntentServices = null;
        }
        return listQueryIntentServices != null ? listQueryIntentServices : new ArrayList();
    }

    public static boolean isPackageEnabled(String str) {
        try {
            PackageManager packageManager = AppGlobal.getPackageManager();
            int applicationEnabledSetting = packageManager.getApplicationEnabledSetting(str);
            Log.d(TAG, "state: " + applicationEnabledSetting);
            if (applicationEnabledSetting != 0) {
                return applicationEnabledSetting == 1;
            }
            return packageManager.getApplicationInfo(str, 0).enabled;
        } catch (IllegalArgumentException unused) {
            return false;
        } catch (Exception e) {
            Log.e(TAG, e.toString(), e);
            return false;
        }
    }

    public static String queryDefaultPackageForIntent(Intent intent) {
        Iterator<ResolveInfo> it = queryIntentActivities(intent, 0).iterator();
        while (it.hasNext()) {
            ActivityInfo activityInfo = it.next().activityInfo;
            if (activityInfo.enabled && activityInfo.exported) {
                return activityInfo.packageName;
            }
        }
        return null;
    }

    public static List<ResolveInfo> queryIntentActivities(Intent intent, int i) {
        List<ResolveInfo> listQueryIntentActivities;
        try {
            listQueryIntentActivities = AppGlobal.getPackageManager().queryIntentActivities(intent, i);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            listQueryIntentActivities = null;
        }
        return listQueryIntentActivities != null ? listQueryIntentActivities : new ArrayList();
    }
}
