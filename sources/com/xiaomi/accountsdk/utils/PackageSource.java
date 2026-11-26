package com.xiaomi.accountsdk.utils;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.xiaomi.accounts.AccountManager;

/* JADX INFO: loaded from: classes4.dex */
public class PackageSource {
    public static final String EXTRA_CALLING_UID = "calling_uid";
    public static final String EXTRA_ORIGINAL_CALLING_PACKAGE_NAME = "original_calling_package_name";

    private PackageSource() {
    }

    public static String getOriginalPackageNameFromOptions(Context context, Bundle bundle, String str) {
        if (context == null) {
            throw new IllegalArgumentException("context cannot be null");
        }
        if (bundle != null) {
            String string = bundle.getString(AccountManager.KEY_ANDROID_PACKAGE_NAME, null);
            String string2 = TextUtils.equals(string, context.getPackageName()) ? bundle.getString(EXTRA_ORIGINAL_CALLING_PACKAGE_NAME, null) : null;
            if (!TextUtils.isEmpty(string2)) {
                return string2;
            }
            if (!TextUtils.isEmpty(string)) {
                return string;
            }
        }
        return str;
    }
}
