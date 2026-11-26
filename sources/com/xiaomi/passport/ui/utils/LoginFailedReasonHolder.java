package com.xiaomi.passport.ui.utils;

import android.text.TextUtils;

/* JADX INFO: loaded from: classes4.dex */
public class LoginFailedReasonHolder {
    private static volatile String sReason;

    public static String get() {
        return sReason;
    }

    public static void set(String... strArr) {
        for (String str : strArr) {
            if (!TextUtils.isEmpty(str)) {
                sReason = str;
                return;
            }
        }
    }
}
