package com.xiaomi.passport.accountmanager;

import android.accounts.Account;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.OnAccountsUpdateListener;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.text.TextUtils;
import com.xiaomi.accountsdk.account.XMPassportSettings;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.data.SecondaryDeviceInfo;
import com.xiaomi.accountsdk.account.data.XiaomiUserCoreInfo;
import com.xiaomi.accountsdk.account.exception.RequestSecondaryDeviceLoginDataException;
import com.xiaomi.accountsdk.account.utils.AppSignatureHash;
import com.xiaomi.accountsdk.account.utils.AppSignatureUtil;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.SystemXiaomiAccountPackageName;
import com.xiaomi.passport.LocalFeatures.LocalFeaturesImpl;
import com.xiaomi.passport.LocalFeatures.MiLocalFeaturesManager;
import com.xiaomi.passport.servicetoken.ServiceTokenFuture;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;
import com.xiaomi.passport.servicetoken.data.XmAccountVisibility;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes4.dex */
public class XiaomiAccountManager implements IXiaomiAccountManager {
    public static final String ACTION_AUTHENTICATOR_INTENT = "android.accounts.AccountAuthenticator";
    public static final String AUTHENTICATOR_ATTRIBUTES_NAME = "account-authenticator";
    public static final String AUTHENTICATOR_META_DATA_NAME = "android.accounts.AccountAuthenticator";
    public static final int ERROR_CODE_BAD_ARGUMENTS = 7;
    public static final int ERROR_CODE_BAD_REQUEST = 8;
    public static final int ERROR_CODE_CANCELED = 4;
    public static final int ERROR_CODE_INVALID_RESPONSE = 5;
    public static final int ERROR_CODE_NETWORK_ERROR = 3;
    public static final int ERROR_CODE_REMOTE_EXCEPTION = 1;
    public static final int ERROR_CODE_UNSUPPORTED_OPERATION = 6;
    public static final String EXTRA_NEED_RETRY_ON_AUTHENTICATOR_RESPONSE_RESULT = "need_retry_on_authenticator_response_result";
    public static final String KEY_ACCOUNTS = "accounts";
    public static final String KEY_ACCOUNT_AUTHENTICATOR_RESPONSE = "accountAuthenticatorResponse";
    public static final String KEY_ACCOUNT_MANAGER_RESPONSE = "accountManagerResponse";
    public static final String KEY_ACCOUNT_NAME = "authAccount";
    public static final String KEY_ACCOUNT_TYPE = "accountType";
    public static final String KEY_AUTHENTICATOR_TYPES = "authenticator_types";
    public static final String KEY_AUTHTOKEN = "authtoken";
    public static final String KEY_AUTH_FAILED_MESSAGE = "authFailedMessage";
    public static final String KEY_AUTH_TOKEN_LABEL = "authTokenLabelKey";
    public static final String KEY_BOOLEAN_RESULT = "booleanResult";
    public static final String KEY_ERROR_CODE = "errorCode";
    public static final String KEY_ERROR_MESSAGE = "errorMessage";
    public static final String KEY_INTENT = "intent";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_STS_URL = "sts_url";
    public static final String KEY_USERDATA = "userdata";
    public static final String LOGIN_ACCOUNTS_CHANGED_ACTION = "account-authenticator";
    public static final String TAG = "XiaomiAccountManager";
    public static final String XIAOMI_ACCOUNT_TYPE = "com.xiaomi";
    private static volatile XiaomiAccountManager sInstance;
    private static volatile Boolean sUseSystemXiaomiAccountFirst;
    private final Context mContext;
    private final IXiaomiAccountManager mXiaomiAccountAdapter;

    public static synchronized XiaomiAccountManager setup(Context context, boolean z) {
        try {
            if (context == null) {
                throw new IllegalArgumentException("context is null");
            }
            AccountLogger.log(TAG, "setup " + z);
            if (sUseSystemXiaomiAccountFirst != null && sUseSystemXiaomiAccountFirst.booleanValue() == z) {
                return get(context);
            }
            sUseSystemXiaomiAccountFirst = Boolean.valueOf(z);
            resetInstance();
            return get(context);
        } catch (Throwable th) {
            throw th;
        }
    }

    public static synchronized XiaomiAccountManager reget(Context context) {
        AccountLogger.log(TAG, "reget");
        resetInstance();
        return get(context);
    }

    private static void resetInstance() {
        if (sInstance != null) {
            sInstance.mXiaomiAccountAdapter.release();
        }
        sInstance = null;
    }

    public static synchronized XiaomiAccountManager get(Context context) {
        if (sUseSystemXiaomiAccountFirst == null) {
            throw new IllegalStateException("call setup first");
        }
        if (context == null) {
            throw new IllegalArgumentException("context is null");
        }
        if (sInstance == null) {
            sInstance = new XiaomiAccountManager(context, sUseSystemXiaomiAccountFirst.booleanValue());
        }
        return sInstance;
    }

    private XiaomiAccountManager(Context context, boolean z) throws Throwable {
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        XMPassportSettings.ensureApplicationContext((Application) applicationContext);
        XMPassportSettings.setNonNullApplicationContextContract(true);
        if (!z) {
            this.mXiaomiAccountAdapter = new OwnAppXiaomiAccountManager(applicationContext);
            AccountLogger.log(TAG, "not use system xiaomi account first, use app xiaomi account");
            return;
        }
        if (isSystemXiaomiAccountApp(applicationContext)) {
            this.mXiaomiAccountAdapter = new OwnSystemXiaomiAccountManager(applicationContext);
            AccountLogger.log(TAG, "is in system account app");
            return;
        }
        if (isSystemAccountInstalled(applicationContext)) {
            String packageName = applicationContext.getPackageName();
            String valid = SystemXiaomiAccountPackageName.getValid(applicationContext);
            AppSignatureHash validSignatureHash = AppSignatureUtil.getValidSignatureHash(context, packageName);
            AppSignatureHash validSignatureHash2 = AppSignatureUtil.getValidSignatureHash(context, valid);
            AccountLogger.log(TAG, "caller signature = " + validSignatureHash);
            AccountLogger.log(TAG, "sys account signature = " + validSignatureHash2);
            if (validSignatureHash != null && validSignatureHash2 != null && validSignatureHash.equals(validSignatureHash2)) {
                this.mXiaomiAccountAdapter = new ManageSystemXiaomiAccountManager(applicationContext);
                AccountLogger.log(TAG, "has system account app and current " + packageName + " has same signature");
                return;
            } else {
                this.mXiaomiAccountAdapter = new VisibleSystemXiaomiAccountManager(applicationContext);
                AccountLogger.log(TAG, "has system account app and current " + packageName + " has different signature");
                return;
            }
        }
        this.mXiaomiAccountAdapter = new OwnAppXiaomiAccountManager(applicationContext);
        AccountLogger.log(TAG, "has no system account app");
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public void sendAccountUpdateBroadcast(Account account, IXiaomiAccountManager.UpdateType updateType) {
        AccountLogger.log(TAG, "send update broadcast " + updateType.name());
        this.mXiaomiAccountAdapter.sendAccountUpdateBroadcast(account, updateType);
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public void updateAccountInfo(Account account, AccountInfo accountInfo) {
        AccountLogger.log(TAG, "update info " + accountInfo);
        this.mXiaomiAccountAdapter.updateAccountInfo(account, accountInfo);
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public boolean addAccountOrUpdatePassToken(AccountInfo accountInfo) {
        AccountLogger.log(TAG, "add/update info " + accountInfo);
        return this.mXiaomiAccountAdapter.addAccountOrUpdatePassToken(accountInfo);
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public void handleAccountAuthenticatorResponse(Parcelable parcelable, Bundle bundle) {
        this.mXiaomiAccountAdapter.handleAccountAuthenticatorResponse(parcelable, bundle);
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public XiaomiAccountManagerFuture<XiaomiUserCoreInfo> getUserInfo(XiaomiAccountManagerCallback<XiaomiUserCoreInfo> xiaomiAccountManagerCallback, Handler handler) {
        return this.mXiaomiAccountAdapter.getUserInfo(xiaomiAccountManagerCallback, handler);
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public Intent getConfirmCredentialActivityIntent(Bundle bundle, Parcelable parcelable) {
        return this.mXiaomiAccountAdapter.getConfirmCredentialActivityIntent(bundle, parcelable);
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public Intent getAccountLoginActivityIntent(String str, Bundle bundle, Parcelable parcelable) {
        return this.mXiaomiAccountAdapter.getAccountLoginActivityIntent(str, bundle, parcelable);
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public Intent getAccountNotificationActivityIntent(String str, String str2, Bundle bundle, Parcelable parcelable) {
        return this.mXiaomiAccountAdapter.getAccountNotificationActivityIntent(str, str2, bundle, parcelable);
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public Intent getQRCodeAuthActivityIntent(String str, Parcelable parcelable) {
        return this.mXiaomiAccountAdapter.getQRCodeAuthActivityIntent(str, parcelable);
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public Intent getAccountHomeActivityIntent() {
        return this.mXiaomiAccountAdapter.getAccountHomeActivityIntent();
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public String requestSecondaryDeviceLoginData(SecondaryDeviceInfo secondaryDeviceInfo) throws RequestSecondaryDeviceLoginDataException {
        return this.mXiaomiAccountAdapter.requestSecondaryDeviceLoginData(secondaryDeviceInfo);
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public void notifyAccountLoginAbnormal(String str) {
        AccountLogger.log(TAG, "notify account login abnormal");
        this.mXiaomiAccountAdapter.notifyAccountLoginAbnormal(str);
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public void jumpToAccountLoginAbnormalPage(Context context, String str) {
        AccountLogger.log(TAG, "start device details webview");
        this.mXiaomiAccountAdapter.jumpToAccountLoginAbnormalPage(context, str);
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public void jumpToDeviceSettingListPage(Context context) {
        AccountLogger.log(TAG, "start device setting list webview");
        this.mXiaomiAccountAdapter.jumpToDeviceSettingListPage(context);
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public void release() {
        AccountLogger.log(TAG, "release adapter, not supported use");
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    public void clearPassword(Account account) {
        AccountLogger.log(TAG, "clear password");
        this.mXiaomiAccountAdapter.clearPassword(account);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    public AccountManagerFuture<Bundle> confirmCredentials(Account account, Bundle bundle, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        return this.mXiaomiAccountAdapter.confirmCredentials(account, bundle, accountManagerCallback, handler);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    public String getUserData(Account account, String str) {
        return this.mXiaomiAccountAdapter.getUserData(account, str);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    public Map<String, String> getUserData(Account account, Set<String> set) {
        return this.mXiaomiAccountAdapter.getUserData(account, set);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    public void setUserData(Account account, String str, String str2) {
        AccountLogger.log(TAG, "set user data k=" + str + ", v=" + str2);
        this.mXiaomiAccountAdapter.setUserData(account, str, str2);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    public void setUserData(Account account, Map<String, String> map) {
        this.mXiaomiAccountAdapter.setUserData(account, map);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    public int getAccountVisibility(Account account, String str) {
        return this.mXiaomiAccountAdapter.getAccountVisibility(account, str);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    public boolean setAccountVisibility(Account account, String str, int i) {
        AccountLogger.log(TAG, "set visibility " + i + " from " + str);
        return this.mXiaomiAccountAdapter.setAccountVisibility(account, str, i);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    public void addOnAccountsUpdatedListener(OnAccountsUpdateListener onAccountsUpdateListener, Handler handler, boolean z) {
        AccountLogger.log(TAG, "add listener=" + onAccountsUpdateListener + ", immediately=" + z);
        this.mXiaomiAccountAdapter.addOnAccountsUpdatedListener(onAccountsUpdateListener, handler, z);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    public void removeOnAccountsUpdatedListener(OnAccountsUpdateListener onAccountsUpdateListener) {
        AccountLogger.log(TAG, "remove listener=" + onAccountsUpdateListener);
        this.mXiaomiAccountAdapter.removeOnAccountsUpdatedListener(onAccountsUpdateListener);
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public Account getXiaomiAccount() {
        return this.mXiaomiAccountAdapter.getXiaomiAccount();
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public void addXiaomiAccount(String str, Bundle bundle, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        this.mXiaomiAccountAdapter.addXiaomiAccount(str, bundle, accountManagerCallback, handler);
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public XiaomiAccountManagerFuture<Bundle> removeXiaomiAccount(XiaomiAccountManagerCallback<Bundle> xiaomiAccountManagerCallback, Handler handler) {
        return this.mXiaomiAccountAdapter.removeXiaomiAccount(xiaomiAccountManagerCallback, handler);
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public void setServiceToken(Account account, String str, ServiceTokenResult serviceTokenResult) {
        AccountLogger.log(TAG, "set service token for " + str + ", token=" + serviceTokenResult);
        this.mXiaomiAccountAdapter.setServiceToken(account, str, serviceTokenResult);
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public ServiceTokenFuture getServiceToken(Account account, String str, Bundle bundle) {
        AccountLogger.log(TAG, "get service token for " + str);
        return this.mXiaomiAccountAdapter.getServiceToken(account, str, bundle);
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public ServiceTokenFuture invalidateServiceToken(ServiceTokenResult serviceTokenResult) {
        AccountLogger.log(TAG, "invalidate service token " + serviceTokenResult);
        return this.mXiaomiAccountAdapter.invalidateServiceToken(serviceTokenResult);
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public ServiceTokenResult peekServiceToken(Account account, String str, Bundle bundle) {
        ServiceTokenResult serviceTokenResultPeekServiceToken = this.mXiaomiAccountAdapter.peekServiceToken(account, str, bundle);
        AccountLogger.log(TAG, "peek service token " + serviceTokenResultPeekServiceToken);
        return serviceTokenResultPeekServiceToken;
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public ServiceTokenFuture refreshServiceToken(Account account, String str, ServiceTokenResult serviceTokenResult, Bundle bundle) {
        AccountLogger.log(TAG, "refresh service token for " + str + ", token=" + serviceTokenResult);
        return this.mXiaomiAccountAdapter.refreshServiceToken(account, str, serviceTokenResult, bundle);
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public String getPassToken(Account account) {
        return this.mXiaomiAccountAdapter.getPassToken(account);
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public void updatePassToken(Account account, AccountInfo accountInfo) {
        AccountLogger.log(TAG, "update pass token " + accountInfo);
        this.mXiaomiAccountAdapter.updatePassToken(account, accountInfo);
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public boolean hasSetPassword(Account account, String str) {
        return this.mXiaomiAccountAdapter.hasSetPassword(account, str);
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public XiaomiAccountManagerFuture<XmAccountVisibility> makeAccountVisible(XiaomiAccountManagerCallback<XmAccountVisibility> xiaomiAccountManagerCallback, Handler handler) {
        AccountLogger.log(TAG, "make account visible");
        return this.mXiaomiAccountAdapter.makeAccountVisible(xiaomiAccountManagerCallback, handler);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    public String getPassword(Account account) {
        return this.mXiaomiAccountAdapter.getPassword(account);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    public boolean addAccountExplicitly(AccountInfo accountInfo, Bundle bundle) {
        AccountLogger.log(TAG, "add explicit account=" + accountInfo + ", data=" + bundle + ", id=" + accountInfo.userId);
        return this.mXiaomiAccountAdapter.addAccountExplicitly(accountInfo, bundle);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    public void setPassword(Account account, String str) {
        AccountLogger.log(TAG, "set password=" + AccountLogger.getMaskedPrivacyData(str) + " for " + account.name);
        this.mXiaomiAccountAdapter.setPassword(account, str);
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public boolean isSystemAccount() {
        return this.mXiaomiAccountAdapter.isSystemAccount();
    }

    public MiLocalFeaturesManager getLocalFeatures() {
        return LocalFeaturesImpl.get(this.mContext);
    }

    public static boolean isSystemXiaomiAccountApp(Context context) {
        return TextUtils.equals(context.getPackageName(), SystemXiaomiAccountPackageName.getValid(context));
    }

    public static boolean isSystemAccountInstalled(Context context) {
        return !TextUtils.isEmpty(SystemXiaomiAccountPackageName.getValid(context));
    }
}
