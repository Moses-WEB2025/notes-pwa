package com.xiaomi.passport.utils;

import android.accounts.Account;
import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class STSCookies {
    public static void requestAndFill(Context context, String str, Map<String, String> map) {
        XiaomiAccountManager xiaomiAccountManager = XiaomiAccountManager.get(context);
        Account xiaomiAccount = xiaomiAccountManager.getXiaomiAccount();
        ServiceTokenResult serviceTokenResult = xiaomiAccountManager.getServiceToken(xiaomiAccount, str, null).get();
        if (!TextUtils.isEmpty(serviceTokenResult.stsCookies)) {
            map.putAll(HttpCookies.parse(serviceTokenResult.stsCookies));
        } else {
            xiaomiAccountManager.invalidateServiceToken(serviceTokenResult).get();
            map.putAll(HttpCookies.parse(xiaomiAccountManager.getServiceToken(xiaomiAccount, str, null).get().stsCookies));
        }
    }
}
