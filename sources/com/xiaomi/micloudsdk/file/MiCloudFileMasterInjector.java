package com.xiaomi.micloudsdk.file;

import android.content.Context;
import micloud.compat.independent.sync.GdprUtilsCompat;

/* JADX INFO: loaded from: classes4.dex */
public class MiCloudFileMasterInjector {
    public static boolean checkUploadConditions(Context context) {
        return GdprUtilsCompat.isGdprPermissionGranted(context);
    }

    public static boolean checkDownloadConditions(Context context) {
        return GdprUtilsCompat.isGdprPermissionGranted(context);
    }
}
