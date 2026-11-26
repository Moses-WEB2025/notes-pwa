package com.xiaomi.account.data;

import android.accounts.Account;
import android.content.Context;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;

/* JADX INFO: loaded from: classes4.dex */
public class CUserId {
    private static final String AM_USER_DATA_KEY_C_USER_ID = "encrypted_user_id";

    public static String get(Context context, Account account) {
        return XiaomiAccountManager.get(context).getUserData(account, "encrypted_user_id");
    }
}
