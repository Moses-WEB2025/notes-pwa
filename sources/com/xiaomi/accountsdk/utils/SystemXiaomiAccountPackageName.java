package com.xiaomi.accountsdk.utils;

import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;
import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.accountsdk.account.AccountIntent;
import java.util.HashSet;

/* JADX INFO: loaded from: classes4.dex */
public class SystemXiaomiAccountPackageName {
    private static final String ACCOUNT_TYPE = "com.xiaomi";

    public static String getValid(Context context) {
        HashSet hashSet = new HashSet();
        for (AuthenticatorDescription authenticatorDescription : AccountManager.get(context).getAuthenticatorTypes()) {
            if (TextUtils.equals(authenticatorDescription.type, "com.xiaomi")) {
                hashSet.add(authenticatorDescription.packageName);
            }
        }
        if (hashSet.contains(AccountIntent.PACKAGE_XIAOMI_ACCOUNT)) {
            return AccountIntent.PACKAGE_XIAOMI_ACCOUNT;
        }
        if (hashSet.contains("com.xiaomi.controlscreen.account")) {
            return "com.xiaomi.controlscreen.account";
        }
        return null;
    }
}
