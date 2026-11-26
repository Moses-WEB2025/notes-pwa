package com.xiaomi.passport.data;

import android.accounts.Account;
import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.accountsdk.account.data.PassportInfo;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;
import com.xiaomi.passport.utils.CUserIdUtil;

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
        Context applicationContext = context.getApplicationContext();
        XiaomiAccountManager xiaomiAccountManager = XiaomiAccountManager.get(applicationContext);
        Account xiaomiAccount = xiaomiAccountManager.getXiaomiAccount();
        if (xiaomiAccount == null) {
            AccountLogger.log(TAG, "no xiaomi account");
            return null;
        }
        ServiceTokenResult serviceTokenResult = xiaomiAccountManager.getServiceToken(xiaomiAccount, str2, null).get();
        if (serviceTokenResult == null) {
            AccountLogger.log(TAG, "service token result is null");
            return null;
        }
        if (serviceTokenResult.errorCode != ServiceTokenResult.ErrorCode.ERROR_NONE) {
            AccountLogger.log(TAG, "service token result error code = " + serviceTokenResult.errorCode + " error msg: " + serviceTokenResult.errorMessage);
            return null;
        }
        String cUserId = serviceTokenResult.cUserId;
        if (TextUtils.isEmpty(cUserId)) {
            cUserId = new CUserIdUtil(applicationContext).getCUserId();
        }
        return new XMPassportInfo(xiaomiAccount.name, cUserId, str2, serviceTokenResult.serviceToken, serviceTokenResult.security);
    }

    public void refreshAuthToken(Context context) {
        XiaomiAccountManager xiaomiAccountManager = XiaomiAccountManager.get(context.getApplicationContext());
        Account xiaomiAccount = xiaomiAccountManager.getXiaomiAccount();
        if (xiaomiAccount == null) {
            AccountLogger.log(TAG, "no xiaomi account");
            return;
        }
        xiaomiAccountManager.invalidateServiceToken(new ServiceTokenResult.Builder(getServiceId()).serviceToken(getServiceToken()).security(getSecurity()).build()).get();
        ServiceTokenResult serviceTokenResult = xiaomiAccountManager.getServiceToken(xiaomiAccount, getServiceId(), null).get();
        if (serviceTokenResult == null) {
            AccountLogger.log(TAG, "service token result is null");
        } else if (serviceTokenResult.errorCode != ServiceTokenResult.ErrorCode.ERROR_NONE) {
            AccountLogger.log(TAG, "service token result error code = " + serviceTokenResult.errorCode);
        } else {
            setServiceToken(serviceTokenResult.serviceToken);
            setSecurity(serviceTokenResult.security);
        }
    }
}
