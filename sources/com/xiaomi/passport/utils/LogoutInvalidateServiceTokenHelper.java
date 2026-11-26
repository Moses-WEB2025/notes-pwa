package com.xiaomi.passport.utils;

import android.accounts.Account;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.xiaomi.accountsdk.account.URLs;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class LogoutInvalidateServiceTokenHelper {
    public static final String SERVICE_IDS = "serviceIds";
    public static final String SP_SERVICE_ID = "sp_service_id";
    public static final String TAG = "LogoutInvalidateServiceTokenHelper";

    public static boolean logoutAccount(Context context, Set<String> set, boolean z) {
        String str;
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        XiaomiAccountManager xiaomiAccountManager = XiaomiAccountManager.get(context);
        Account xiaomiAccount = xiaomiAccountManager.getXiaomiAccount();
        if (xiaomiAccount == null) {
            AccountLogger.log(TAG, "no xiaomi account.");
            return false;
        }
        if (set == null || set.isEmpty()) {
            AccountLogger.log(TAG, "serviceIdSet is empty");
            return false;
        }
        if (z) {
            str = URLs.URL_LOGOUT_SYS_ACCOUNT;
            ServiceTokenResult serviceTokenResult = xiaomiAccountManager.getServiceToken(xiaomiAccount, "passportapi", null).get();
            if (TextUtils.isEmpty(serviceTokenResult.serviceToken)) {
                AccountLogger.log(TAG, "serviceToken is null");
                return false;
            }
            map2.put("serviceToken", serviceTokenResult.serviceToken);
            map2.put("cUserId", serviceTokenResult.cUserId);
            map2.put("userId", xiaomiAccount.name);
            map2.put("passportapi_ph", serviceTokenResult.ph);
            map.put("passportapi_ph", serviceTokenResult.ph);
        } else {
            str = URLs.URL_LOGOUT_LOCAL_ACCOUNT;
            String passToken = xiaomiAccountManager.getPassToken(xiaomiAccount);
            if (TextUtils.isEmpty(passToken)) {
                return true;
            }
            map2.put("passToken", passToken);
        }
        map.put("sids", String.join(",", set));
        map.put("userId", xiaomiAccount.name);
        map.put("deviceId", AccountHelper.getHashedDeviceId());
        try {
            int i = new JSONObject(SimpleRequestForAccount.postAsString(str, map, map2, true).getBody()).getInt("code");
            AccountLogger.log(TAG, "request resultCode: " + i);
            if (i != 0) {
                return false;
            }
            clearServiceTokenSid(context);
            return true;
        } catch (AccessDeniedException | AuthenticationFailureException | IOException | JSONException e) {
            AccountLogger.log(TAG, "logoutApp error: ", e);
            return false;
        }
    }

    public static Set<String> getServiceTokenSid(Context context) {
        return context.getSharedPreferences(SP_SERVICE_ID, 0).getStringSet(SERVICE_IDS, new HashSet());
    }

    public static void setServiceTokenSid(Context context, String str) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(SP_SERVICE_ID, 0).edit();
        Set<String> serviceTokenSid = getServiceTokenSid(context);
        serviceTokenSid.add(str);
        editorEdit.putStringSet(SERVICE_IDS, serviceTokenSid);
        editorEdit.apply();
    }

    private static void clearServiceTokenSid(Context context) {
        context.getSharedPreferences(SP_SERVICE_ID, 0).edit().clear().apply();
    }
}
