package com.xiaomi.micloudsdk.utils;

import android.content.Context;
import micloud.compat.independent.sync.GdprUtilsCompat;

/* JADX INFO: loaded from: classes4.dex */
public class PermissionUtils {
    private PermissionUtils() {
    }

    public static boolean isGdprPermissionGranted(Context context) {
        return GdprUtilsCompat.isGdprPermissionGranted(context);
    }
}
