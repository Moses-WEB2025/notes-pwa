package com.xiaomi.account.utils;

import android.accounts.Account;
import android.os.Build;
import android.text.TextUtils;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class AccountManagerCompat {
    private static final String TAG = "XmAccountManager";

    public static boolean setAccountVisible(XiaomiAccountManager xiaomiAccountManager, Account account, String str) {
        checkNotNull(str, "packageName");
        if (account == null) {
            AccountLogger.log(TAG, "no xiaomi account");
            return false;
        }
        return xiaomiAccountManager.setAccountVisibility(account, str, 1);
    }

    public static boolean getAccountVisible(XiaomiAccountManager xiaomiAccountManager, Account account, String str) {
        checkNotNull(str, "packageName");
        if (account != null) {
            return xiaomiAccountManager.getAccountVisibility(account, str) == 1;
        }
        AccountLogger.log(TAG, "no xiaomi account");
        return false;
    }

    private static void checkNotNull(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException(str2 + "must not be null");
        }
    }

    private static String getBuildVersionMsg() {
        return "Build.VERSION.SDK_INT: " + Build.VERSION.SDK_INT + StringUtils.SPACE;
    }
}
