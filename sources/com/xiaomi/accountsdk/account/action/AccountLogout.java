package com.xiaomi.accountsdk.account.action;

import android.accounts.Account;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.xiaomi.accountsdk.account.ChildAccount;
import com.xiaomi.accountsdk.account.URLs;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.accountsdk.account.config.SystemAdStatus;
import com.xiaomi.accountsdk.account.config.UserExperienceStatus;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.EasyMap;
import com.xiaomi.accountsdk.utils.UserSpaceIdUtil;
import com.xiaomi.passport.accountmanager.IXiaomiAccountManager;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import com.xiaomi.passport.utils.HttpCookies;
import java.io.IOException;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class AccountLogout {
    public static final int CODE_LOGOUT_NEED_NOTIFICATION = 2;
    private static final String KEY_ACCOUNT_REMOVAL_ALLOWED = "isAccountRemovalAllowed";
    public static final String TAG = "AccountLogout";

    private static String requestLogoutAccountNotificationUrl(Context context, Account account, Boolean bool, String str, Boolean bool2, Boolean bool3) throws AccessDeniedException, IOException, InvalidResponseException, AuthenticationFailureException {
        if (account == null) {
            throw new IllegalStateException("no account when request logout");
        }
        String str2 = URLs.URL_LOGOUT_SYSTEM_DEVICE;
        String str3 = Build.MODEL;
        String userData = XiaomiAccountManager.get(context).getUserData(account, "encrypted_user_id");
        String passToken = XiaomiAccountManager.get(context).getPassToken(account);
        String strSubstring = UUID.randomUUID().toString().substring(0, 15);
        EasyMap easyMapEasyPut = new EasyMap().easyPut("userId", account.name).easyPutOpt("userSpaceId", UserSpaceIdUtil.getNullableUserSpaceIdCookie()).easyPutOpt("isMainSpace", bool2 == null ? null : String.valueOf(bool2)).easyPutOpt("isLastSpace", bool3 == null ? null : String.valueOf(bool3)).easyPut("sid", str).easyPut("deviceModel", str3).easyPutOpt("isFindDeviceSwitchOn", bool == null ? null : String.valueOf(bool)).easyPut("_json", String.valueOf(true)).easyPut("traceId", strSubstring);
        EasyMap easyMapEasyPut2 = new EasyMap().easyPut("passToken", passToken).easyPut("cUserId", userData);
        AccountLogger.log(TAG, "logoutDevice traceId: " + strSubstring);
        try {
            JSONObject jSONObject = new JSONObject(XMPassport.removeSafePrefixAndGetRealBody(SimpleRequestForAccount.getAsString(str2, easyMapEasyPut, easyMapEasyPut2, true)));
            try {
                int i = jSONObject.getInt("code");
                if (i == 0) {
                    return null;
                }
                if (i == 2) {
                    try {
                        return jSONObject.getString("notificationUrl");
                    } catch (JSONException unused) {
                        throw new InvalidResponseException("response no notificationUrl");
                    }
                }
                if (i == 70016) {
                    throw new AuthenticationFailureException("un-authenticated");
                }
                throw new InvalidResponseException("unknown code=" + i);
            } catch (JSONException unused2) {
                throw new InvalidResponseException("response no code");
            }
        } catch (JSONException unused3) {
            throw new InvalidResponseException("response not json");
        }
    }

    public static String requestLogoutSystemAccountNotificationUrl(Context context, Account account, boolean z, String str, boolean z2, boolean z3) throws AccessDeniedException, IOException, InvalidResponseException, AuthenticationFailureException {
        if (!XiaomiAccountManager.isSystemXiaomiAccountApp(context)) {
            throw new IllegalStateException("only can be invoked in system account process");
        }
        return requestLogoutAccountNotificationUrl(context, account, Boolean.valueOf(z), str, Boolean.valueOf(z2), Boolean.valueOf(z3));
    }

    public static String requestLogoutAppAccountNotificationUrl(Context context, Account account, String str) throws AccessDeniedException, IOException, InvalidResponseException, AuthenticationFailureException {
        return requestLogoutAccountNotificationUrl(context, account, null, str, null, null);
    }

    public static boolean syncRemoveSystemAccountDirectly(Context context, Account account) {
        boolean z;
        setSystemAccountRemovalAllowed(context, account, true);
        XiaomiAccountManager xiaomiAccountManager = XiaomiAccountManager.get(context);
        try {
            z = xiaomiAccountManager.removeXiaomiAccount(null, null).getResult().getBoolean("booleanResult");
        } catch (Exception e) {
            AccountLogger.log(TAG, "error when remove account", e);
            z = false;
        }
        if (z) {
            AccountLogger.log(TAG, "Xiaomi account removed successfully!");
            HttpCookies.clear();
            if (context != null) {
                UserExperienceStatus.unsetWhenLogout(context);
                SystemAdStatus.unset(context);
                ChildAccount.unsetSettingsGlobal(context);
            }
            xiaomiAccountManager.sendAccountUpdateBroadcast(account, IXiaomiAccountManager.UpdateType.POST_REMOVE);
        } else {
            setSystemAccountRemovalAllowed(context, account, false);
        }
        return z;
    }

    public static boolean syncRemoveInAppAccountDirectly(Context context) {
        if (XiaomiAccountManager.isSystemAccountInstalled(context)) {
            throw new IllegalStateException("can not remove account directly when has system account");
        }
        XiaomiAccountManager xiaomiAccountManager = XiaomiAccountManager.get(context);
        Account xiaomiAccount = xiaomiAccountManager.getXiaomiAccount();
        boolean z = false;
        if (xiaomiAccount == null) {
            return false;
        }
        try {
            z = xiaomiAccountManager.removeXiaomiAccount(null, null).getResult().getBoolean("booleanResult");
        } catch (Exception e) {
            AccountLogger.log(TAG, "error when remove account", e);
        }
        if (z) {
            AccountLogger.log(TAG, "Xiaomi account removed successfully!");
            HttpCookies.clear();
            xiaomiAccountManager.sendAccountUpdateBroadcast(xiaomiAccount, IXiaomiAccountManager.UpdateType.POST_REMOVE);
        }
        return z;
    }

    public static boolean getSystemAccountRemovalAllowed(Context context, Account account) {
        if (!XiaomiAccountManager.isSystemXiaomiAccountApp(context)) {
            throw new IllegalStateException("only access in system account app");
        }
        String userData = XiaomiAccountManager.get(context).getUserData(account, KEY_ACCOUNT_REMOVAL_ALLOWED);
        return !TextUtils.isEmpty(userData) && userData.equals(String.valueOf(true));
    }

    public static void setSystemAccountRemovalAllowed(Context context, Account account, boolean z) {
        if (!XiaomiAccountManager.isSystemXiaomiAccountApp(context)) {
            throw new IllegalStateException("only access in system account app");
        }
        XiaomiAccountManager.get(context).setUserData(account, KEY_ACCOUNT_REMOVAL_ALLOWED, String.valueOf(z));
    }
}
