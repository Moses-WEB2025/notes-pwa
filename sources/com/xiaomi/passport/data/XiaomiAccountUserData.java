package com.xiaomi.passport.data;

import android.content.Context;
import com.xiaomi.accountsdk.account.AccountIntent;
import com.xiaomi.accountsdk.account.data.Constants;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes4.dex */
public class XiaomiAccountUserData {
    public static Set<String> getKeys(Context context) {
        return new HashSet<String>() { // from class: com.xiaomi.passport.data.XiaomiAccountUserData.1
            {
                add(Constants.ACCOUNT_USER_NAME);
                add(Constants.ACCOUNT_NICK_NAME);
                add(Constants.ACCOUNT_USER_SNS_LIST);
                add(Constants.ACCOUNT_USER_EMAIL);
                add(Constants.ACCOUNT_USER_PHONE);
                add(Constants.ACCOUNT_USER_REGION);
                add(Constants.ACCOUNT_USER_PHONE_LIST);
                add("acc_udevid");
                add("is_set_safe_questions");
                add(Constants.ACCOUNT_USER_GENDER);
                add(AccountIntent.EXTRA_HAS_PASSWORD);
                add("is_child_account");
            }
        };
    }
}
