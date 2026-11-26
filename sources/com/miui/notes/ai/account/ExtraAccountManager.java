package com.miui.notes.ai.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import com.miui.notes.base.utils.Logger;

/* JADX INFO: loaded from: classes4.dex */
public class ExtraAccountManager {
    private static final String AI_SID = "hyperos_sec_common";
    public static final String XIAOMI_ACCOUNT_TYPE = "com.xiaomi";

    public static Account getXiaomiAccount(Context context) {
        Account[] accountsByType = AccountManager.get(context).getAccountsByType("com.xiaomi");
        Logger.INSTANCE.d("ExtraAccountManager", "getXiaomiAccount:" + accountsByType.length);
        if (accountsByType.length > 0) {
            return accountsByType[0];
        }
        return null;
    }

    public static boolean isLogin(Context context) {
        return getXiaomiAccount(context) != null;
    }

    public static void requestAccountGrant(Activity activity, int i) {
        activity.startActivityForResult(AccountManager.newChooseAccountIntent(null, null, new String[]{"com.xiaomi"}, true, null, null, null, null), i);
    }

    public static Bundle getAuthToken(Context context) {
        try {
            return AccountManager.get(context).getAuthToken(getXiaomiAccount(context), "hyperos_sec_common", (Bundle) null, (Activity) null, (AccountManagerCallback<Bundle>) null, (Handler) null).getResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
