package com.xiaomi.passport.utils;

import android.content.Context;

/* JADX INFO: loaded from: classes4.dex */
public class PackageUtils {
    public static boolean isDebuggable(Context context) {
        return (context.getApplicationInfo().flags & 2) != 0;
    }
}
