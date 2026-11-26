package com.xiaomi.passport.utils;

import android.app.Activity;
import com.xiaomi.account.privacy_data.lib.ReflectionCalls;

/* JADX INFO: loaded from: classes4.dex */
public class ActivityUtils {
    public static int getSettedResultCode(Activity activity) {
        Object field = ReflectionCalls.getField(activity, "mResultCode");
        if (field instanceof Integer) {
            return ((Integer) field).intValue();
        }
        return 0;
    }
}
