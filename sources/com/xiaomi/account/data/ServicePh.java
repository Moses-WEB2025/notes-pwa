package com.xiaomi.account.data;

import android.accounts.Account;
import android.content.Context;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;

/* JADX INFO: loaded from: classes4.dex */
public class ServicePh {
    private static final String AM_USER_DATA_KEY_PH_POSTFIX = "_ph";

    public static String get(Context context, Account account, String str) {
        return XiaomiAccountManager.get(context).getUserData(account, str + AM_USER_DATA_KEY_PH_POSTFIX);
    }
}
