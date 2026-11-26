package com.xiaomi.accountsdk.utils;

import android.content.Context;
import android.content.pm.PackageManager;

/* JADX INFO: loaded from: classes4.dex */
public class Debuggable {
    public static boolean is(Context context) {
        try {
            return (context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).flags & 2) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalStateException("should never happen", e);
        }
    }
}
