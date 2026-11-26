package com.xiaomi.passport.accountmanager;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.accountsdk.account.XMPassportSettings;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.data.PassTokenLoginParams;
import com.xiaomi.accountsdk.account.data.SecondaryDeviceInfo;
import com.xiaomi.accountsdk.account.exception.InvalidCredentialException;
import com.xiaomi.accountsdk.account.exception.RequestSecondaryDeviceLoginDataException;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.verificationsdk.internal.CommonUtils;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class PrimarySecondaryDeviceAccountHelper {
    private static final String KEY_CODE = "p_s_code";
    private static final String KEY_ERR_MSG = "p_s_err_msg";
    private static final String KEY_INTENT = "p_s_intent";
    private static final String KEY_LOGIN_DATA = "p_s_login_data";
    private static final String KEY_SERVER_CODE = "p_s_server_code";
    public static final String TAG = "PrimarySecondaryDeviceAccountHelper";

    public static String requestSecondaryDeviceLoginData(SecondaryDeviceInfo secondaryDeviceInfo) throws RequestSecondaryDeviceLoginDataException {
        CommonUtils.checkWorkThreadAndThrow();
        secondaryDeviceInfo.paramsCheckAndThrow();
        XiaomiAccountManager xiaomiAccountManager = XiaomiAccountManager.get(XMPassportSettings.getApplicationContext());
        Account xiaomiAccount = xiaomiAccountManager.getXiaomiAccount();
        if (xiaomiAccount == null || !TextUtils.equals(xiaomiAccount.name, secondaryDeviceInfo.bindUid)) {
            AccountLogger.log(TAG, "requestSecondaryDeviceLoginData>>>request account is not same as current account");
            throw new RequestSecondaryDeviceLoginDataException("request account is not same as current account");
        }
        String passToken = xiaomiAccountManager.getPassToken(xiaomiAccount);
        if (TextUtils.isEmpty(passToken)) {
            AccountLogger.log(TAG, "requestSecondaryDeviceLoginData>>>passToken is null");
            throw new RequestSecondaryDeviceLoginDataException("passToken is null");
        }
        try {
            return createSecondaryDeviceLoginData(XMPassport.secondaryDeviceLogin(new PassTokenLoginParams.Builder().userId(xiaomiAccount.name).passToken(passToken).build(), secondaryDeviceInfo));
        } catch (InvalidCredentialException e) {
            AccountLogger.log(TAG, "requestSecondaryDeviceLoginData>>>request login data err2:", e);
            throw new RequestSecondaryDeviceLoginDataException(e.getMessage(), RequestSecondaryDeviceLoginDataException.Code.INVALID_PASS_TOKEN, XiaomiAccountManager.get(XMPassportSettings.getApplicationContext()).getConfirmCredentialActivityIntent(null, null), e.code, null);
        } catch (AccessDeniedException | AuthenticationFailureException | IOException e2) {
            AccountLogger.log(TAG, "requestSecondaryDeviceLoginData>>>request login data err1:", e2);
            throw new RequestSecondaryDeviceLoginDataException(e2);
        }
    }

    public static String parseLoginDataBundleFromSystemAccount(Bundle bundle) throws RequestSecondaryDeviceLoginDataException {
        if (bundle == null) {
            throw new RequestSecondaryDeviceLoginDataException("parse bundle is empty");
        }
        String string = bundle.getString(KEY_LOGIN_DATA);
        if (!TextUtils.isEmpty(string)) {
            return string;
        }
        int i = bundle.getInt(KEY_CODE);
        throw new RequestSecondaryDeviceLoginDataException(bundle.getString(KEY_ERR_MSG), RequestSecondaryDeviceLoginDataException.Code.fromValue(i), (Intent) bundle.getParcelable(KEY_INTENT));
    }

    public static void putSystemAccountRequestSucToResult(Bundle bundle, String str) {
        bundle.putString(KEY_LOGIN_DATA, str);
    }

    public static void putSystemAccountRequestFailToResult(Bundle bundle, int i, String str, Intent intent) {
        bundle.putInt(KEY_CODE, i);
        bundle.putString(KEY_ERR_MSG, str);
        bundle.putParcelable(KEY_INTENT, intent);
    }

    public static String createGetSecondaryDeviceLoginDataFailInfo(int i, int i2, String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(KEY_CODE, i);
            jSONObject.put(KEY_SERVER_CODE, i2);
            jSONObject.put(KEY_ERR_MSG, str);
            return jSONObject.toString();
        } catch (JSONException e) {
            AccountLogger.log(TAG, "createGetSecondaryDeviceLoginDataFailInfo>>>", e);
            return "";
        }
    }

    public static RequestSecondaryDeviceLoginDataException parsePrimaryDeviceLoginDataFailInfo(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has(KEY_CODE) && jSONObject.has(KEY_ERR_MSG)) {
                return new RequestSecondaryDeviceLoginDataException(jSONObject.getString(KEY_ERR_MSG), RequestSecondaryDeviceLoginDataException.Code.fromValue(jSONObject.getInt(KEY_CODE)), jSONObject.optInt(KEY_SERVER_CODE, -1));
            }
            return null;
        } catch (JSONException e) {
            AccountLogger.log(TAG, "parsePrimarySecondaryDeviceLoginDataFailInfo>>>", e);
            return null;
        }
    }

    public static String createSecondaryDeviceLoginData(AccountInfo accountInfo) {
        if (accountInfo == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("userId", accountInfo.userId);
            jSONObject.put("psecurity", accountInfo.psecurity);
            jSONObject.put("encryptedUserId", accountInfo.encryptedUserId);
            jSONObject.put("passToken", accountInfo.passToken);
        } catch (JSONException e) {
            AccountLogger.log(TAG, "toJsonStr error", e);
        }
        return jSONObject.toString();
    }

    public static AccountInfo parseLoginDataFromPrimaryDevice(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            AccountInfo.Builder builder = new AccountInfo.Builder();
            builder.userId(jSONObject.optString("userId"));
            builder.psecurity(jSONObject.optString("psecurity"));
            builder.encryptedUserId(jSONObject.optString("encryptedUserId"));
            builder.passToken(jSONObject.optString("passToken"));
            return builder.build();
        } catch (JSONException e) {
            AccountLogger.log(TAG, "toJsonStr error", e);
            return null;
        }
    }
}
