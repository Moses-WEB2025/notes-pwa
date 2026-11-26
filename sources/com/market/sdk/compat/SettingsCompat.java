package com.market.sdk.compat;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import com.market.sdk.utils.ReflectUtils;

/* JADX INFO: loaded from: classes2.dex */
public class SettingsCompat {
    public static final String SYS_STORAGE_THRESHOLD_MAX_BYTES = "sys_storage_threshold_max_bytes";

    public static int getSecureIntForUser(Context context, String str, int i, int i2) {
        Integer num = (Integer) ReflectUtils.invokeObject(Settings.Secure.class, Settings.Secure.class, "getIntForUser", ReflectUtils.getMethodSignature(Integer.TYPE, ContentResolver.class, String.class, Integer.TYPE, Integer.TYPE), context.getContentResolver(), str, Integer.valueOf(i), Integer.valueOf(i2));
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }
}
