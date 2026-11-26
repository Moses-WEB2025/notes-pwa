package com.xiaomi.passport.accountmanager;

import android.accounts.Account;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.xiaomi.accounts.AbstractAccountAuthenticator;
import com.xiaomi.accounts.AccountAuthenticatorResponse;
import com.xiaomi.accounts.AccountManager;
import com.xiaomi.accountsdk.account.AccountIntent;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.data.PassTokenLoginParams;
import com.xiaomi.accountsdk.account.exception.IllegalDeviceException;
import com.xiaomi.accountsdk.account.exception.InvalidCredentialException;
import com.xiaomi.accountsdk.account.exception.InvalidUserNameException;
import com.xiaomi.accountsdk.account.exception.NeedCaptchaException;
import com.xiaomi.accountsdk.account.exception.NeedNotificationException;
import com.xiaomi.accountsdk.account.exception.NeedVerificationException;
import com.xiaomi.accountsdk.account.exception.PackageNameDeniedException;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.utils.AccountHelper;
import java.io.IOException;

/* JADX INFO: loaded from: classes4.dex */
public class OwnAppXiaomiAccountAuthenticator extends AbstractAccountAuthenticator {
    private static final boolean DEBUG = true;
    private static final String TAG = "OwnAppXiaomiAccountAuthenticator";
    private Context mContext;

    @Override // com.xiaomi.accounts.AbstractAccountAuthenticator
    public Bundle editProperties(AccountAuthenticatorResponse accountAuthenticatorResponse, String str) {
        return null;
    }

    @Override // com.xiaomi.accounts.AbstractAccountAuthenticator
    public String getAuthTokenLabel(String str) {
        return null;
    }

    @Override // com.xiaomi.accounts.AbstractAccountAuthenticator
    public Bundle hasFeatures(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String[] strArr) throws NetworkErrorException {
        return null;
    }

    public OwnAppXiaomiAccountAuthenticator(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override // com.xiaomi.accounts.AbstractAccountAuthenticator
    public Bundle addAccount(AccountAuthenticatorResponse accountAuthenticatorResponse, String str, String str2, String[] strArr, Bundle bundle) throws NetworkErrorException {
        Account xiaomiAccount = XiaomiAccountManager.get(this.mContext).getXiaomiAccount();
        Bundle bundle2 = new Bundle();
        if (xiaomiAccount != null) {
            AccountLogger.log(TAG, "a xiaomi account already exists");
            bundle2.putString("authAccount", xiaomiAccount.name);
            bundle2.putString("accountType", xiaomiAccount.type);
            return bundle2;
        }
        if (TextUtils.isEmpty(str2)) {
            AccountLogger.log(TAG, "no service id contained, use passportapi");
            str2 = "passportapi";
        }
        bundle2.putParcelable("intent", XiaomiAccountManager.get(this.mContext).getAccountLoginActivityIntent(str2, bundle, accountAuthenticatorResponse));
        return bundle2;
    }

    /* JADX WARN: Code duplicated, block: B:28:0x007c  */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.xiaomi.accounts.AbstractAccountAuthenticator
    public Bundle confirmCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, Bundle bundle) throws NetworkErrorException {
        Object objBuild;
        String captchaUrl;
        Bundle bundle2 = new Bundle();
        Object obj = null;
        if (bundle == null || !bundle.containsKey("password")) {
            String password = XiaomiAccountManager.get(this.mContext).getPassword(account);
            if ((bundle == null || bundle.getBoolean("verify_only", true)) && TextUtils.isEmpty(password)) {
                if (bundle == null) {
                    bundle = new Bundle();
                }
                bundle.putBoolean("verify_only", false);
            }
            Bundle bundle3 = bundle;
            fillQuickLoginIntent(bundle2, accountAuthenticatorResponse, false, account, bundle3 != null ? bundle3.getString("service_id") : null, bundle3);
            return bundle2;
        }
        String str = account.name;
        try {
            objBuild = AccountHelper.getServiceTokenByPassword(str, bundle.getString("password"), bundle.getString("captcha_code"), bundle.getString("captcha_ick"), null);
        } catch (IllegalDeviceException e) {
            e.printStackTrace();
            captchaUrl = null;
            bundle2.putString("authAccount", str);
            bundle2.putString("accountType", "com.xiaomi");
            bundle2.putBoolean("booleanResult", obj != null);
            bundle2.putString("captcha_url", captchaUrl);
            return bundle2;
        } catch (InvalidCredentialException e2) {
            captchaUrl = e2.getCaptchaUrl();
            e2.printStackTrace();
        } catch (InvalidUserNameException e3) {
            e3.printStackTrace();
            captchaUrl = null;
            bundle2.putString("authAccount", str);
            bundle2.putString("accountType", "com.xiaomi");
            bundle2.putBoolean("booleanResult", obj != null);
            bundle2.putString("captcha_url", captchaUrl);
            return bundle2;
        } catch (NeedCaptchaException e4) {
            captchaUrl = e4.getCaptchaUrl();
            e4.printStackTrace();
        } catch (NeedVerificationException unused) {
            objBuild = new AccountInfo.Builder().userId(str).build();
        } catch (AccessDeniedException e5) {
            e5.printStackTrace();
            captchaUrl = null;
            bundle2.putString("authAccount", str);
            bundle2.putString("accountType", "com.xiaomi");
            bundle2.putBoolean("booleanResult", obj != null);
            bundle2.putString("captcha_url", captchaUrl);
            return bundle2;
        } catch (AuthenticationFailureException e6) {
            e6.printStackTrace();
            captchaUrl = null;
            bundle2.putString("authAccount", str);
            bundle2.putString("accountType", "com.xiaomi");
            bundle2.putBoolean("booleanResult", obj != null);
            bundle2.putString("captcha_url", captchaUrl);
            return bundle2;
        } catch (InvalidResponseException e7) {
            e7.printStackTrace();
            captchaUrl = null;
            bundle2.putString("authAccount", str);
            bundle2.putString("accountType", "com.xiaomi");
            bundle2.putBoolean("booleanResult", obj != null);
            bundle2.putString("captcha_url", captchaUrl);
            return bundle2;
        } catch (IOException e8) {
            throw new NetworkErrorException("IO exception when sending request to passport server", e8);
        }
        captchaUrl = null;
        obj = objBuild;
        bundle2.putString("authAccount", str);
        bundle2.putString("accountType", "com.xiaomi");
        bundle2.putBoolean("booleanResult", obj != null);
        bundle2.putString("captcha_url", captchaUrl);
        return bundle2;
    }

    private String getRealPasstoken(Account account) {
        return XiaomiAccountManager.get(this.mContext).getPassToken(account);
    }

    @Override // com.xiaomi.accounts.AbstractAccountAuthenticator
    public Bundle getAuthToken(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String str, Bundle bundle) {
        return recheckAuthTokenResult(getAuthTokenBundle(accountAuthenticatorResponse, account, str, bundle));
    }

    /* JADX WARN: Code duplicated, block: B:25:0x0090  */
    /* JADX WARN: Code duplicated, block: B:27:0x009d  */
    /* JADX WARN: Code duplicated, block: B:29:0x00bf  */
    /* JADX WARN: Code duplicated, block: B:32:0x00e8 A[Catch: NeedNotificationException -> 0x0133, InvalidUserNameException -> 0x0177, AuthenticationFailureException -> 0x017f, AccessDeniedException -> 0x0196, PackageNameDeniedException -> 0x01ad, InvalidCredentialException -> 0x01d2, InvalidResponseException -> 0x01f1, IllegalDeviceException -> 0x0207, IOException -> 0x021b, TRY_LEAVE, TryCatch #4 {NeedNotificationException -> 0x0133, blocks: (B:30:0x00c0, B:32:0x00e8), top: B:75:0x00bd }] */
    /* JADX WARN: Code duplicated, block: B:39:0x0115 A[Catch: NeedNotificationException -> 0x0131, InvalidUserNameException -> 0x0177, AuthenticationFailureException -> 0x017f, AccessDeniedException -> 0x0196, PackageNameDeniedException -> 0x01ad, InvalidCredentialException -> 0x01d2, InvalidResponseException -> 0x01f1, IllegalDeviceException -> 0x0207, IOException -> 0x021b, TryCatch #1 {NeedNotificationException -> 0x0131, blocks: (B:34:0x0101, B:36:0x0109, B:38:0x0111, B:40:0x0118, B:42:0x011f, B:43:0x0122, B:39:0x0115), top: B:73:0x0101 }] */
    /* JADX WARN: Code duplicated, block: B:42:0x011f A[Catch: NeedNotificationException -> 0x0131, InvalidUserNameException -> 0x0177, AuthenticationFailureException -> 0x017f, AccessDeniedException -> 0x0196, PackageNameDeniedException -> 0x01ad, InvalidCredentialException -> 0x01d2, InvalidResponseException -> 0x01f1, IllegalDeviceException -> 0x0207, IOException -> 0x021b, TryCatch #1 {NeedNotificationException -> 0x0131, blocks: (B:34:0x0101, B:36:0x0109, B:38:0x0111, B:40:0x0118, B:42:0x011f, B:43:0x0122, B:39:0x0115), top: B:73:0x0101 }] */
    private Bundle getAuthTokenBundle(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String str, Bundle bundle) {
        String str2;
        String strSubstring;
        String realPasstoken;
        PassTokenLoginParams.Builder builderPackageName;
        AccountInfo serviceTokenByPassToken;
        XiaomiAccountManager xiaomiAccountManager;
        Bundle authenticatorResponseBundle;
        String string = bundle.getString(AccountManager.KEY_ANDROID_PACKAGE_NAME, this.mContext.getPackageName());
        AccountLogger.log(TAG, String.format("getting AuthToken, type: %s, package name: %s", str.startsWith("weblogin:") ? "websso" : str, string));
        Bundle bundle2 = new Bundle();
        Account xiaomiAccount = XiaomiAccountManager.get(this.mContext).getXiaomiAccount();
        if (xiaomiAccount == null || !xiaomiAccount.name.equals(account.name)) {
            bundle2.putBoolean("booleanResult", false);
            return bundle2;
        }
        if (TextUtils.isEmpty(str)) {
            AccountLogger.log(TAG, "getting auth token, but no service url contained, use passportapi");
            str2 = "passportapi";
        } else {
            if (str.startsWith("weblogin:")) {
                strSubstring = str.substring("weblogin:".length());
                if (!AccountHelper.isTrustedWebSsoUrl(strSubstring)) {
                    bundle2.putInt("errorCode", 7);
                    bundle2.putString("errorMessage", "untrusted web sso url");
                    return bundle2;
                }
                str2 = "passportapi";
            } else {
                str2 = str;
            }
            realPasstoken = getRealPasstoken(account);
            if (TextUtils.isEmpty(realPasstoken)) {
                fillQuickLoginIntent(bundle2, accountAuthenticatorResponse, true, account, str2, bundle);
                AccountLogger.log(TAG, "passToken is null");
                return bundle2;
            }
            builderPackageName = new PassTokenLoginParams.Builder().userId(account.name).passToken(realPasstoken).packageName(string);
            try {
                try {
                    if (strSubstring != null) {
                        builderPackageName.serviceId(null);
                        builderPackageName.loginRequestUrl(strSubstring);
                        AccountInfo serviceTokenByPassToken2 = AccountHelper.getServiceTokenByPassToken(this.mContext, builderPackageName.build());
                        bundle2.putString("authAccount", serviceTokenByPassToken2.getUserId());
                        bundle2.putString("accountType", "weblogin:com.xiaomi");
                        bundle2.putString("authtoken", serviceTokenByPassToken2.getAutoLoginUrl());
                        AccountLogger.log(TAG, "web sso getAuthToken succeed");
                        return bundle2;
                    }
                    builderPackageName.serviceId(str2);
                    builderPackageName.loginRequestUrl(null);
                    serviceTokenByPassToken = AccountHelper.getServiceTokenByPassToken(this.mContext, builderPackageName.build());
                    xiaomiAccountManager = XiaomiAccountManager.get(this.mContext);
                    try {
                        if (TextUtils.isEmpty(serviceTokenByPassToken.passToken) && !realPasstoken.equals(serviceTokenByPassToken.passToken)) {
                            xiaomiAccountManager.addAccountOrUpdatePassToken(serviceTokenByPassToken);
                        } else {
                            xiaomiAccountManager.updateAccountInfo(account, serviceTokenByPassToken);
                        }
                        authenticatorResponseBundle = AccountHelper.getAuthenticatorResponseBundle(serviceTokenByPassToken, false);
                        if (authenticatorResponseBundle != null) {
                            bundle2.putAll(authenticatorResponseBundle);
                        }
                        AccountLogger.log(TAG, String.format("type: %s, package name: %s, getAuthToken succeed", str, string));
                        return bundle2;
                    } catch (NeedNotificationException e) {
                        e = e;
                        AccountLogger.log(TAG, "need notification ", e);
                        if (strSubstring != null) {
                            AccountLogger.log(TAG, "websso return notification url: " + str2);
                            bundle2.putString("authAccount", account.name);
                            bundle2.putString("accountType", "weblogin:com.xiaomi");
                            bundle2.putString("authtoken", e.getNotificationUrl());
                            return bundle2;
                        }
                        bundle2.putParcelable("intent", XiaomiAccountManager.get(this.mContext).getAccountNotificationActivityIntent(str2, e.getNotificationUrl(), bundle, accountAuthenticatorResponse));
                        return bundle2;
                    }
                } catch (IllegalDeviceException e2) {
                    AccountLogger.log(TAG, "get device id failed when getting service token", e2);
                    bundle2.putInt("errorCode", 3);
                    bundle2.putString("errorMessage", "illegal device exception");
                    return bundle2;
                } catch (InvalidCredentialException e3) {
                    AccountLogger.log(TAG, "invalid credential, passToken is invalid", e3);
                    XiaomiAccountManager.get(this.mContext).clearPassword(account);
                    bundle.putString("captcha_url", e3.getCaptchaUrl());
                    fillQuickLoginIntent(bundle2, accountAuthenticatorResponse, true, account, str2, bundle);
                    return bundle2;
                } catch (InvalidUserNameException e4) {
                    AccountLogger.log(TAG, "no such a user", e4);
                    return bundle2;
                } catch (PackageNameDeniedException e5) {
                    AccountLogger.log(TAG, "package name denied: " + string, e5);
                    bundle2.putInt("errorCode", 9);
                    bundle2.putString("errorMessage", e5.toString());
                    return bundle2;
                } catch (AccessDeniedException e6) {
                    AccountLogger.log(TAG, "access denied", e6);
                    bundle2.putInt("errorCode", 5);
                    bundle2.putString("errorMessage", e6.toString());
                    return bundle2;
                } catch (AuthenticationFailureException e7) {
                    AccountLogger.log(TAG, "auth failure", e7);
                    bundle2.putInt("errorCode", 5);
                    bundle2.putString("errorMessage", e7.toString());
                    return bundle2;
                } catch (InvalidResponseException e8) {
                    AccountLogger.log(TAG, "invalid response received when getting service token", e8);
                    bundle2.putInt("errorCode", 5);
                    bundle2.putString("errorMessage", e8.toString());
                    return bundle2;
                } catch (IOException e9) {
                    AccountLogger.log(TAG, "io exception when getting service token", e9);
                    bundle2.putInt("errorCode", 3);
                    bundle2.putString("errorMessage", e9.toString());
                    return bundle2;
                }
            } catch (NeedNotificationException e10) {
                e = e10;
            }
        }
        strSubstring = null;
        realPasstoken = getRealPasstoken(account);
        if (TextUtils.isEmpty(realPasstoken)) {
            fillQuickLoginIntent(bundle2, accountAuthenticatorResponse, true, account, str2, bundle);
            AccountLogger.log(TAG, "passToken is null");
            return bundle2;
        }
        builderPackageName = new PassTokenLoginParams.Builder().userId(account.name).passToken(realPasstoken).packageName(string);
        if (strSubstring != null) {
            builderPackageName.serviceId(null);
            builderPackageName.loginRequestUrl(strSubstring);
            AccountInfo serviceTokenByPassToken3 = AccountHelper.getServiceTokenByPassToken(this.mContext, builderPackageName.build());
            bundle2.putString("authAccount", serviceTokenByPassToken3.getUserId());
            bundle2.putString("accountType", "weblogin:com.xiaomi");
            bundle2.putString("authtoken", serviceTokenByPassToken3.getAutoLoginUrl());
            AccountLogger.log(TAG, "web sso getAuthToken succeed");
            return bundle2;
        }
        builderPackageName.serviceId(str2);
        builderPackageName.loginRequestUrl(null);
        serviceTokenByPassToken = AccountHelper.getServiceTokenByPassToken(this.mContext, builderPackageName.build());
        xiaomiAccountManager = XiaomiAccountManager.get(this.mContext);
        if (TextUtils.isEmpty(serviceTokenByPassToken.passToken)) {
            xiaomiAccountManager.updateAccountInfo(account, serviceTokenByPassToken);
        } else {
            xiaomiAccountManager.updateAccountInfo(account, serviceTokenByPassToken);
        }
        authenticatorResponseBundle = AccountHelper.getAuthenticatorResponseBundle(serviceTokenByPassToken, false);
        if (authenticatorResponseBundle != null) {
            bundle2.putAll(authenticatorResponseBundle);
        }
        AccountLogger.log(TAG, String.format("type: %s, package name: %s, getAuthToken succeed", str, string));
        return bundle2;
    }

    Bundle recheckAuthTokenResult(Bundle bundle) {
        if (XiaomiAccountManager.get(this.mContext).getXiaomiAccount() != null) {
            return bundle;
        }
        Bundle bundle2 = new Bundle();
        bundle2.putBoolean("booleanResult", false);
        return bundle2;
    }

    @Override // com.xiaomi.accounts.AbstractAccountAuthenticator
    public Bundle updateCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String str, Bundle bundle) throws NetworkErrorException {
        throw new UnsupportedOperationException("updateCredentials not supported");
    }

    @Override // com.xiaomi.accounts.AbstractAccountAuthenticator
    public Bundle getAccountRemovalAllowed(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account) throws NetworkErrorException {
        Bundle bundle = new Bundle();
        bundle.putBoolean("booleanResult", true);
        return bundle;
    }

    private void fillQuickLoginIntent(Bundle bundle, AccountAuthenticatorResponse accountAuthenticatorResponse, boolean z, Account account, String str, Bundle bundle2) {
        XiaomiAccountManager xiaomiAccountManager = XiaomiAccountManager.get(this.mContext);
        if (!TextUtils.equals(xiaomiAccountManager.getUserData(account, AccountIntent.EXTRA_HAS_PASSWORD), String.valueOf(true)) && !TextUtils.isEmpty(xiaomiAccountManager.getPassToken(account))) {
            xiaomiAccountManager.hasSetPassword(account, null);
        }
        String userData = XiaomiAccountManager.get(this.mContext).getUserData(account, AccountIntent.EXTRA_HAS_PASSWORD);
        if (bundle2 == null) {
            bundle2 = new Bundle();
        }
        bundle2.putString("userId", account.name);
        bundle2.putBoolean("need_retry_on_authenticator_response_result", z);
        bundle2.putBoolean(AccountIntent.EXTRA_HAS_PASSWORD, TextUtils.isEmpty(userData) ? true : TextUtils.equals(userData, String.valueOf(true)));
        Intent confirmCredentialActivityIntent = xiaomiAccountManager.getConfirmCredentialActivityIntent(bundle2, accountAuthenticatorResponse);
        confirmCredentialActivityIntent.putExtra("service_id", str);
        bundle.putParcelable("intent", confirmCredentialActivityIntent);
    }
}
