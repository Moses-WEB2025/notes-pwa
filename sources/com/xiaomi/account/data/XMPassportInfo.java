package com.xiaomi.account.data;

import android.accounts.Account;
import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.accountsdk.account.data.ExtendedAuthToken;
import com.xiaomi.accountsdk.account.data.PassportInfo;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;

/* JADX INFO: loaded from: classes4.dex */
public class XMPassportInfo extends PassportInfo {
    private static final String TAG = "XMPassportInfo";

    private XMPassportInfo(String str, String str2, String str3, String str4, String str5) {
        super(str, str2, str3, str4, str5);
    }

    public static XMPassportInfo build(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            str = "passportapi";
        }
        String str2 = str;
        XiaomiAccountManager xiaomiAccountManager = XiaomiAccountManager.get(context.getApplicationContext());
        Account xiaomiAccount = xiaomiAccountManager.getXiaomiAccount();
        if (xiaomiAccount == null) {
            AccountLogger.log(TAG, "no xiaomi account");
            return null;
        }
        ExtendedAuthToken extendedAuthToken = ExtendedAuthToken.parse(xiaomiAccountManager.getServiceToken(xiaomiAccount, str2, null).get().toAuthToken());
        if (extendedAuthToken == null) {
            AccountLogger.log(TAG, "auth token is null");
            return null;
        }
        String userData = xiaomiAccountManager.getUserData(xiaomiAccount, "encrypted_user_id");
        if (TextUtils.isEmpty(userData)) {
            AccountLogger.log(TAG, "cUserId is null");
            return null;
        }
        return new XMPassportInfo(xiaomiAccount.name, userData, str2, extendedAuthToken.authToken, extendedAuthToken.security);
    }

    public void refreshAuthToken(Context context) {
        XiaomiAccountManager xiaomiAccountManager = XiaomiAccountManager.get(context.getApplicationContext());
        Account xiaomiAccount = xiaomiAccountManager.getXiaomiAccount();
        if (xiaomiAccount == null) {
            AccountLogger.log(TAG, "no xiaomi account");
            return;
        }
        xiaomiAccountManager.invalidateServiceToken(new ServiceTokenResult.Builder(getServiceId()).serviceToken(getServiceToken()).security(getSecurity()).build()).get();
        ExtendedAuthToken extendedAuthToken = ExtendedAuthToken.parse(xiaomiAccountManager.getServiceToken(xiaomiAccount, getServiceId(), null).get().toAuthToken());
        if (extendedAuthToken == null) {
            AccountLogger.log(TAG, "auth token is null");
        } else {
            setServiceToken(extendedAuthToken.authToken);
            setSecurity(extendedAuthToken.security);
        }
    }
}
