package com.xiaomi.passport.jsb.method_impl;

import android.accounts.Account;
import android.content.Context;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import com.xiaomi.passport.jsb.PassportJsbMethod;
import com.xiaomi.passport.jsb.PassportJsbMethodException;
import com.xiaomi.passport.jsb.PassportJsbMethodResult;
import com.xiaomi.passport.webview.PassportJsbWebView;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class PassportJsbMethodSetAccountInfo extends PassportJsbMethod {
    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public PassportJsbMethodResult invoke(PassportJsbWebView passportJsbWebView, JSONObject jSONObject) throws PassportJsbMethodException {
        Context context = passportJsbWebView.getContext();
        String paramsStringFieldOrThrow = getParamsStringFieldOrThrow(jSONObject, "userId");
        String strOptString = jSONObject.optString("passToken");
        boolean zOptBoolean = jSONObject.optBoolean("addAccountIfNotExists", false);
        AccountInfo accountInfoBuild = new AccountInfo.Builder().userId(paramsStringFieldOrThrow).passToken(strOptString).build();
        XiaomiAccountManager xiaomiAccountManager = XiaomiAccountManager.get(context);
        Account xiaomiAccount = xiaomiAccountManager.getXiaomiAccount();
        if (xiaomiAccount == null) {
            if (zOptBoolean) {
                boolean zAddAccountOrUpdatePassToken = xiaomiAccountManager.addAccountOrUpdatePassToken(accountInfoBuild);
                xiaomiAccount = xiaomiAccountManager.getXiaomiAccount();
                if (!zAddAccountOrUpdatePassToken) {
                    return new PassportJsbMethodResult(false);
                }
            } else {
                return new PassportJsbMethodResult(false);
            }
        } else if (xiaomiAccount.name.equals(paramsStringFieldOrThrow)) {
            xiaomiAccountManager.addAccountOrUpdatePassToken(accountInfoBuild);
        } else {
            return new PassportJsbMethodResult(false);
        }
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("userData");
        if (jSONObjectOptJSONObject != null) {
            try {
                Iterator<String> itKeys = jSONObjectOptJSONObject.keys();
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    xiaomiAccountManager.setUserData(xiaomiAccount, next, jSONObjectOptJSONObject.getString(next));
                }
            } catch (JSONException e) {
                throw new IllegalStateException("should never happen", e);
            }
        }
        return new PassportJsbMethodResult(true);
    }

    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public String getName() {
        return "setAccountInfo";
    }
}
