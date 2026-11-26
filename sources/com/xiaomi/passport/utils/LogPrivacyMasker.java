package com.xiaomi.passport.utils;

import android.text.TextUtils;

/* JADX INFO: loaded from: classes4.dex */
public class LogPrivacyMasker {
    public static String mask(String str) {
        if (TextUtils.isEmpty(str)) {
            return "[empty]";
        }
        return String.format("[%d]@%s", Integer.valueOf(str.length()), Integer.toHexString(str.hashCode()));
    }
}
