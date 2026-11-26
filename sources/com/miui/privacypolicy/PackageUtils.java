package com.miui.privacypolicy;

import android.content.Context;
import android.util.Log;

/* JADX INFO: loaded from: classes4.dex */
public class PackageUtils {
    private static final String TAG = "Privacy_PackageUtils";

    private PackageUtils() {
    }

    protected static String getAppVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            Log.e(TAG, "Exception", e);
            return "";
        }
    }
}
