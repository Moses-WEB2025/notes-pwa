package com.xiaomi.accountsdk.account;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.SystemXiaomiAccountPackageName;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;

/* JADX INFO: loaded from: classes4.dex */
public class ChildAccount {
    private static final String KEY_ACCOUNT_IS_CHILD = "is_child_account";
    private static final String KEY_SYSTEM_ACCOUNT_IS_CHILD = "xiaomi_account_is_child";
    private static final String TAG = "ChildAccount";

    public static void saveAsUserData(Context context, Account account, AccountInfo accountInfo) {
        AccountLogger.log(TAG, "system: user data, child=" + accountInfo.isChild);
        if (accountInfo.isChild == null) {
            return;
        }
        XiaomiAccountManager.get(context).setUserData(account, KEY_ACCOUNT_IS_CHILD, accountInfo.isChild.booleanValue() ? "1" : "0");
        if (XiaomiAccountManager.isSystemXiaomiAccountApp(context)) {
            Settings.Global.putInt(context.getContentResolver(), KEY_SYSTEM_ACCOUNT_IS_CHILD, accountInfo.isChild.booleanValue() ? 1 : 0);
        }
    }

    public static void fill(Context context, Bundle bundle, AccountInfo accountInfo) {
        if (accountInfo.isChild == null) {
            return;
        }
        bundle.putString(KEY_ACCOUNT_IS_CHILD, accountInfo.isChild.booleanValue() ? "1" : "0");
        if (XiaomiAccountManager.isSystemXiaomiAccountApp(context)) {
            AccountLogger.log(TAG, "isChild=" + accountInfo.isChild + ", in system");
            Settings.Global.putInt(context.getContentResolver(), KEY_SYSTEM_ACCOUNT_IS_CHILD, accountInfo.isChild.booleanValue() ? 1 : 0);
        }
    }

    public static boolean is(Context context) {
        XiaomiAccountManager xiaomiAccountManager = XiaomiAccountManager.get(context);
        String userData = xiaomiAccountManager.getUserData(xiaomiAccountManager.getXiaomiAccount(), KEY_ACCOUNT_IS_CHILD);
        AccountLogger.log(TAG, "get isChild=" + userData);
        return "1".equals(userData);
    }

    public static boolean systemAccountIs(Context context) {
        if (!TextUtils.isEmpty(SystemXiaomiAccountPackageName.getValid(context))) {
            int i = Settings.Global.getInt(context.getContentResolver(), KEY_SYSTEM_ACCOUNT_IS_CHILD, -1);
            AccountLogger.log(TAG, "get isChild=" + i + ", system");
            if (i == 1) {
                return true;
            }
        }
        return false;
    }

    public static void unsetSettingsGlobal(Context context) {
        Settings.Global.putInt(context.getContentResolver(), KEY_SYSTEM_ACCOUNT_IS_CHILD, -1);
    }
}
