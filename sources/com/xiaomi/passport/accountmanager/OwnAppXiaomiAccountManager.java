package com.xiaomi.passport.accountmanager;

import android.accounts.Account;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorDescription;
import android.accounts.OnAccountsUpdateListener;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.text.TextUtils;
import com.xiaomi.account.passportsdk.account_lib.IAccountExchangeService;
import com.xiaomi.accounts.AccountManager;
import com.xiaomi.accounts.secure.SecureDataManager;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.data.ExtendedAuthToken;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.ActivityExportSafetyGuardian;
import com.xiaomi.accountsdk.utils.SystemXiaomiAccountPackageName;
import com.xiaomi.passport.interfaces.AuthenticatorIntentInterface;
import com.xiaomi.passport.servicetoken.ServiceTokenFuture;
import com.xiaomi.passport.servicetoken.ServiceTokenOp;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;
import com.xiaomi.passport.task.BgTask;
import com.xiaomi.passport.utils.AccountLoginUploader;
import com.xiaomi.passport.utils.ActivityUtils;
import com.xiaomi.passport.utils.HttpCookies;
import com.xiaomi.passport.utils.LogoutInvalidateServiceTokenHelper;
import com.xiaomi.passport.webview.PassportJsbWebPageLifecycleListener;
import com.xiaomi.verificationsdk.internal.CommonUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes4.dex */
class OwnAppXiaomiAccountManager extends AbsXiaomiAccountManager {
    public static final String EXTRA_ACCOUNT = "extra_account";
    public static final String EXTRA_UPDATE_TYPE = "extra_update_type";
    private static final String LOCAL_LOGIN_ACCOUNTS_POST_CHANGED_ACTION = "com.xiaomi.accounts.LOGIN_ACCOUNTS_POST_CHANGED";
    private static final String LOCAL_LOGIN_ACCOUNTS_PRE_CHANGED_ACTION = "com.xiaomi.accounts.LOGIN_ACCOUNTS_PRE_CHANGED";
    public static final String TAG = "OwnAppXiaomiAccountManager";
    public static final int TYPE_ADD = 2;
    public static final int TYPE_REFRESH = 3;
    public static final int TYPE_REMOVE = 1;
    private final AccountManager mAccountManager;

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    public int getAccountVisibility(Account account, String str) {
        return 1;
    }

    @Override // com.xiaomi.passport.accountmanager.AbsXiaomiAccountManager, com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public boolean isSystemAccount() {
        return false;
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    public boolean setAccountVisibility(Account account, String str, int i) {
        return true;
    }

    public OwnAppXiaomiAccountManager(Context context) {
        super(context);
        SecureDataManager.safeGenerateOrInitKey(this.mContext);
        this.mAccountManager = AccountManager.newInstance(context);
    }

    public AuthenticatorDescription[] getAuthenticatorTypes() {
        return this.mAccountManager.getAuthenticatorTypes();
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    public String getPassword(Account account) {
        return this.mAccountManager.getPassword(account);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    public boolean addAccountExplicitly(final AccountInfo accountInfo, Bundle bundle) {
        boolean zAddAccountExplicitly = this.mAccountManager.addAccountExplicitly(new Account(accountInfo.getUserId(), "com.xiaomi"), ExtendedAuthToken.build(accountInfo.getPassToken(), accountInfo.getPsecurity()).toPlain(), bundle);
        if (zAddAccountExplicitly && CommonUtils.isXiaomiDevice()) {
            new BgTask(new BgTask.BgTaskRunnable<Void>() { // from class: com.xiaomi.passport.accountmanager.OwnAppXiaomiAccountManager.1
                @Override // com.xiaomi.passport.task.BgTask.BgTaskRunnable
                public Void run() throws Throwable {
                    Intent intent = new Intent("com.xiaomi.account.action.BIND_ACCOUNT_EXCHANGE_SERVICE_V2");
                    intent.setPackage(SystemXiaomiAccountPackageName.getValid(OwnAppXiaomiAccountManager.this.mContext));
                    AccountLogger.log(OwnAppXiaomiAccountManager.TAG, "bind " + intent + ", ret=" + OwnAppXiaomiAccountManager.this.mContext.bindService(intent, new ServiceConnection() { // from class: com.xiaomi.passport.accountmanager.OwnAppXiaomiAccountManager.1.1
                        @Override // android.content.ServiceConnection
                        public void onServiceDisconnected(ComponentName componentName) {
                        }

                        @Override // android.content.ServiceConnection
                        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                            AccountLogger.log(OwnAppXiaomiAccountManager.TAG, "bind success tryAddAccount");
                            try {
                                IAccountExchangeService.Stub.asInterface(iBinder).tryAddAccount(accountInfo, OwnAppXiaomiAccountManager.this.mContext.getPackageName());
                            } catch (RemoteException e) {
                                AccountLogger.log(OwnAppXiaomiAccountManager.TAG, "tryAddAccount failed", e);
                            }
                            OwnAppXiaomiAccountManager.this.mContext.unbindService(this);
                        }
                    }, 1));
                    return null;
                }
            }, null, null).execute();
        }
        return zAddAccountExplicitly;
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    public void setPassword(Account account, String str) {
        this.mAccountManager.setPassword(account, str);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    public void clearPassword(Account account) {
        this.mAccountManager.clearPassword(account);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    public AccountManagerFuture<Bundle> confirmCredentials(Account account, Bundle bundle, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        return this.mAccountManager.confirmCredentials(account, bundle, null, accountManagerCallback, handler);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    public String getUserData(Account account, String str) {
        return this.mAccountManager.getUserData(account, str);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    public Map<String, String> getUserData(Account account, Set<String> set) {
        HashMap map = new HashMap();
        for (String str : set) {
            if (!TextUtils.isEmpty(str)) {
                map.put(str, getUserData(account, str));
            }
        }
        return map;
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    public void setUserData(Account account, String str, String str2) {
        this.mAccountManager.setUserData(account, str, str2);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    public void setUserData(Account account, Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (!TextUtils.isEmpty(entry.getKey()) && !TextUtils.isEmpty(entry.getValue())) {
                setUserData(account, entry.getKey(), entry.getValue());
            }
        }
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    public void addOnAccountsUpdatedListener(OnAccountsUpdateListener onAccountsUpdateListener, Handler handler, boolean z) {
        this.mAccountManager.addOnAccountsUpdatedListener(onAccountsUpdateListener, handler, z);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    public void removeOnAccountsUpdatedListener(OnAccountsUpdateListener onAccountsUpdateListener) {
        this.mAccountManager.removeOnAccountsUpdatedListener(onAccountsUpdateListener);
    }

    private AccountManagerCallback<Boolean> wrapRemoveAccountCallback(final AccountManagerCallback<Boolean> accountManagerCallback) {
        return new AccountManagerCallback<Boolean>() { // from class: com.xiaomi.passport.accountmanager.OwnAppXiaomiAccountManager.2
            @Override // android.accounts.AccountManagerCallback
            public void run(AccountManagerFuture<Boolean> accountManagerFuture) {
                AccountManagerCallback accountManagerCallback2 = accountManagerCallback;
                if (accountManagerCallback2 != null) {
                    accountManagerCallback2.run(accountManagerFuture);
                }
                HttpCookies.clear();
            }
        };
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public Account getXiaomiAccount() {
        Account[] accountsByType = this.mAccountManager.getAccountsByType("com.xiaomi");
        if (accountsByType.length > 0) {
            return accountsByType[0];
        }
        return null;
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public void addXiaomiAccount(String str, Bundle bundle, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        this.mAccountManager.addAccount("com.xiaomi", str, null, bundle, null, accountManagerCallback, handler);
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public XiaomiAccountManagerFuture<Bundle> removeXiaomiAccount(XiaomiAccountManagerCallback<Bundle> xiaomiAccountManagerCallback, Handler handler) {
        final Account xiaomiAccount = getXiaomiAccount();
        return new XiaomiAccountManagerFuture<Bundle>(xiaomiAccountManagerCallback, handler) { // from class: com.xiaomi.passport.accountmanager.OwnAppXiaomiAccountManager.3
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.xiaomi.passport.accountmanager.XiaomiAccountManagerFuture
            public Bundle doWork() throws Exception {
                Bundle bundle = new Bundle();
                Account account = xiaomiAccount;
                if (account == null) {
                    bundle.putBoolean("booleanResult", false);
                    bundle.putString("errorMessage", "no account");
                    return bundle;
                }
                OwnAppXiaomiAccountManager.this.sendAccountUpdateBroadcast(account, IXiaomiAccountManager.UpdateType.PRE_REMOVE);
                LogoutInvalidateServiceTokenHelper.logoutAccount(OwnAppXiaomiAccountManager.this.mContext, LogoutInvalidateServiceTokenHelper.getServiceTokenSid(OwnAppXiaomiAccountManager.this.mContext), false);
                Boolean result = OwnAppXiaomiAccountManager.this.mAccountManager.removeAccount(xiaomiAccount, null, null).getResult();
                bundle.putBoolean("booleanResult", result.booleanValue());
                if (result.booleanValue()) {
                    OwnAppXiaomiAccountManager.this.sendAccountUpdateBroadcast(xiaomiAccount, IXiaomiAccountManager.UpdateType.POST_REMOVE);
                    AccountLoginUploader.clearAccountLoginUploadData();
                }
                return bundle;
            }
        }.start();
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public void setServiceToken(Account account, String str, ServiceTokenResult serviceTokenResult) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(serviceTokenResult.security) || TextUtils.isEmpty(serviceTokenResult.serviceToken)) {
            return;
        }
        this.mAccountManager.setAuthToken(account, str, serviceTokenResult.toAuthToken());
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public ServiceTokenResult peekServiceToken(Account account, String str, Bundle bundle) {
        ServiceTokenResult serviceTokenResultCreate;
        String strPeekAuthToken = this.mAccountManager.peekAuthToken(account, str);
        if (TextUtils.isEmpty(strPeekAuthToken) || (serviceTokenResultCreate = ServiceTokenResult.create(null, str, strPeekAuthToken, true)) == null) {
            return null;
        }
        return serviceTokenResultCreate.addAccountInfo(this.mContext, account);
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public ServiceTokenFuture refreshServiceToken(final Account account, final String str, final ServiceTokenResult serviceTokenResult, final Bundle bundle) {
        return new ServiceTokenOp.OpWorker() { // from class: com.xiaomi.passport.accountmanager.OwnAppXiaomiAccountManager.4
            @Override // com.xiaomi.passport.servicetoken.ServiceTokenOp.OpWorker
            protected ServiceTokenResult doWork() {
                OwnAppXiaomiAccountManager.this.invalidateServiceToken(serviceTokenResult).get();
                return OwnAppXiaomiAccountManager.this.getServiceToken(account, str, bundle).get();
            }
        }.work();
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public ServiceTokenFuture getServiceToken(final Account account, final String str, final Bundle bundle) {
        return new ServiceTokenOp.OpWorker() { // from class: com.xiaomi.passport.accountmanager.OwnAppXiaomiAccountManager.5
            @Override // com.xiaomi.passport.servicetoken.ServiceTokenOp.OpWorker
            protected ServiceTokenResult doWork() {
                Account xiaomiAccount = account;
                if (xiaomiAccount == null) {
                    xiaomiAccount = OwnAppXiaomiAccountManager.this.getXiaomiAccount();
                }
                Account account2 = xiaomiAccount;
                if (account2 == null) {
                    return new ServiceTokenResult.Builder(str).errorCode(ServiceTokenResult.ErrorCode.ERROR_NO_ACCOUNT).build();
                }
                ServiceTokenResult serviceTokenResultPeekServiceToken = OwnAppXiaomiAccountManager.this.peekServiceToken(account2, str, bundle);
                if (serviceTokenResultPeekServiceToken == null) {
                    try {
                        ServiceTokenResult serviceTokenResultCreate = ServiceTokenResult.create(OwnAppXiaomiAccountManager.this.mAccountManager.getAuthToken(account2, str, bundle, (Activity) null, (AccountManagerCallback<Bundle>) null, (Handler) null).getResult(), str);
                        if (serviceTokenResultCreate.errorCode == ServiceTokenResult.ErrorCode.ERROR_NONE) {
                            LogoutInvalidateServiceTokenHelper.setServiceTokenSid(OwnAppXiaomiAccountManager.this.mContext, str);
                        }
                        if (serviceTokenResultCreate == null) {
                            return null;
                        }
                        return serviceTokenResultCreate.addAccountInfo(OwnAppXiaomiAccountManager.this.mContext, account2);
                    } catch (Exception e) {
                        return ServiceTokenResult.create(str, e);
                    }
                }
                return serviceTokenResultPeekServiceToken.addAccountInfo(OwnAppXiaomiAccountManager.this.mContext, account2);
            }
        }.work();
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public ServiceTokenFuture invalidateServiceToken(final ServiceTokenResult serviceTokenResult) {
        return new ServiceTokenOp.OpWorker() { // from class: com.xiaomi.passport.accountmanager.OwnAppXiaomiAccountManager.6
            @Override // com.xiaomi.passport.servicetoken.ServiceTokenOp.OpWorker
            protected ServiceTokenResult doWork() {
                ServiceTokenResult serviceTokenResult2 = serviceTokenResult;
                OwnAppXiaomiAccountManager.this.mAccountManager.invalidateAuthToken("com.xiaomi", serviceTokenResult2 == null ? null : serviceTokenResult2.toAuthToken());
                return new ServiceTokenResult.Builder(serviceTokenResult.sid).build();
            }
        }.work();
    }

    /* JADX INFO: renamed from: com.xiaomi.passport.accountmanager.OwnAppXiaomiAccountManager$7, reason: invalid class name */
    static /* synthetic */ class AnonymousClass7 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaomi$passport$accountmanager$IXiaomiAccountManager$UpdateType;

        static {
            int[] iArr = new int[IXiaomiAccountManager.UpdateType.values().length];
            $SwitchMap$com$xiaomi$passport$accountmanager$IXiaomiAccountManager$UpdateType = iArr;
            try {
                iArr[IXiaomiAccountManager.UpdateType.PRE_ADD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaomi$passport$accountmanager$IXiaomiAccountManager$UpdateType[IXiaomiAccountManager.UpdateType.POST_ADD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaomi$passport$accountmanager$IXiaomiAccountManager$UpdateType[IXiaomiAccountManager.UpdateType.POST_REFRESH.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaomi$passport$accountmanager$IXiaomiAccountManager$UpdateType[IXiaomiAccountManager.UpdateType.PRE_REMOVE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaomi$passport$accountmanager$IXiaomiAccountManager$UpdateType[IXiaomiAccountManager.UpdateType.POST_REMOVE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:17:0x002a A[PHI: r1
  0x002a: PHI (r1v2 int) = (r1v0 int), (r1v3 int), (r1v6 int) binds: [B:6:0x0012, B:8:0x0015, B:13:0x001d] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public void sendAccountUpdateBroadcast(Account account, IXiaomiAccountManager.UpdateType updateType) {
        if (updateType == null) {
            throw new IllegalArgumentException();
        }
        int i = AnonymousClass7.$SwitchMap$com$xiaomi$passport$accountmanager$IXiaomiAccountManager$UpdateType[updateType.ordinal()];
        String str = LOCAL_LOGIN_ACCOUNTS_PRE_CHANGED_ACTION;
        int i2 = 2;
        if (i != 1) {
            if (i != 2) {
                i2 = 3;
                if (i == 3) {
                    str = LOCAL_LOGIN_ACCOUNTS_POST_CHANGED_ACTION;
                } else if (i == 4) {
                    i2 = 1;
                } else {
                    if (i != 5) {
                        throw new IllegalStateException("this should not be happen");
                    }
                    i2 = 1;
                    str = LOCAL_LOGIN_ACCOUNTS_POST_CHANGED_ACTION;
                }
            } else {
                str = LOCAL_LOGIN_ACCOUNTS_POST_CHANGED_ACTION;
            }
        }
        Intent intent = new Intent(str);
        intent.putExtra("extra_account", account);
        intent.putExtra("extra_update_type", i2);
        intent.setPackage(this.mContext.getPackageName());
        this.mContext.sendBroadcast(intent);
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public Intent getConfirmCredentialActivityIntent(Bundle bundle, Parcelable parcelable) {
        Intent confirmCredentialActivityIntent = AuthenticatorIntentInterface.getConfirmCredentialActivityIntent(this.mContext);
        confirmCredentialActivityIntent.putExtra("accountAuthenticatorResponse", parcelable);
        if (bundle != null) {
            confirmCredentialActivityIntent.putExtras(bundle);
        }
        confirmCredentialActivityIntent.addFlags(67108864);
        return confirmCredentialActivityIntent;
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public Intent getAccountLoginActivityIntent(String str, Bundle bundle, Parcelable parcelable) {
        Intent accountLoginActivityIntent = AuthenticatorIntentInterface.getAccountLoginActivityIntent(this.mContext);
        accountLoginActivityIntent.putExtra("service_id", str);
        accountLoginActivityIntent.putExtras(bundle);
        accountLoginActivityIntent.addFlags(67108864);
        accountLoginActivityIntent.putExtra("accountAuthenticatorResponse", parcelable);
        return accountLoginActivityIntent;
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public Intent getAccountNotificationActivityIntent(String str, String str2, Bundle bundle, Parcelable parcelable) {
        Intent accountNotificationActivityIntent = AuthenticatorIntentInterface.getAccountNotificationActivityIntent(this.mContext, parcelable, str2, str, bundle);
        ActivityExportSafetyGuardian.getInstance().sign(this.mContext, accountNotificationActivityIntent);
        return accountNotificationActivityIntent;
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public Intent getQRCodeAuthActivityIntent(String str, Parcelable parcelable) {
        return AuthenticatorIntentInterface.getQRCodeAuthActivityIntent(this.mContext, str, parcelable);
    }

    @Override // com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public Intent getAccountHomeActivityIntent() {
        return AuthenticatorIntentInterface.getAccountHomActivityIntent(this.mContext);
    }

    private static class _RemoveAccountPJWPLL implements PassportJsbWebPageLifecycleListener {
        public static final Parcelable.Creator<_RemoveAccountPJWPLL> CREATOR = new Parcelable.Creator<_RemoveAccountPJWPLL>() { // from class: com.xiaomi.passport.accountmanager.OwnAppXiaomiAccountManager._RemoveAccountPJWPLL.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public _RemoveAccountPJWPLL createFromParcel(Parcel parcel) {
                return new _RemoveAccountPJWPLL(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public _RemoveAccountPJWPLL[] newArray(int i) {
                return new _RemoveAccountPJWPLL[i];
            }
        };
        private final Account mAccount;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // com.xiaomi.passport.webview.PassportJsbWebPageLifecycleListener
        public void onPageEntered(Activity activity) {
        }

        @Override // com.xiaomi.passport.webview.PassportJsbWebPageLifecycleListener
        public void onPageHidden(Activity activity) {
        }

        @Override // com.xiaomi.passport.webview.PassportJsbWebPageLifecycleListener
        public void onPageShown(Activity activity) {
        }

        public _RemoveAccountPJWPLL(Account account) {
            this.mAccount = account;
        }

        @Override // com.xiaomi.passport.webview.PassportJsbWebPageLifecycleListener
        public void onPageExited(Activity activity) {
            int settedResultCode = ActivityUtils.getSettedResultCode(activity);
            AccountLogger.log(OwnAppXiaomiAccountManager.TAG, "retCode=" + settedResultCode);
            if (settedResultCode != -1) {
                return;
            }
            final Context applicationContext = activity.getApplicationContext();
            new BgTask(new BgTask.BgTaskRunnable<Void>() { // from class: com.xiaomi.passport.accountmanager.OwnAppXiaomiAccountManager._RemoveAccountPJWPLL.1
                @Override // com.xiaomi.passport.task.BgTask.BgTaskRunnable
                public Void run() throws Throwable {
                    OwnAppXiaomiAccountManager ownAppXiaomiAccountManager = new OwnAppXiaomiAccountManager(applicationContext);
                    ownAppXiaomiAccountManager.sendAccountUpdateBroadcast(_RemoveAccountPJWPLL.this.mAccount, IXiaomiAccountManager.UpdateType.PRE_REMOVE);
                    if (ownAppXiaomiAccountManager.mAccountManager.removeAccount(_RemoveAccountPJWPLL.this.mAccount, null, null).getResult().booleanValue()) {
                        ownAppXiaomiAccountManager.sendAccountUpdateBroadcast(_RemoveAccountPJWPLL.this.mAccount, IXiaomiAccountManager.UpdateType.POST_REMOVE);
                    }
                    return null;
                }
            }, null, null).execute();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.mAccount, i);
        }

        protected _RemoveAccountPJWPLL(Parcel parcel) {
            this.mAccount = (Account) parcel.readParcelable(Account.class.getClassLoader());
        }
    }

    @Override // com.xiaomi.passport.accountmanager.AbsXiaomiAccountManager, com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public void release() {
        this.mAccountManager.release();
    }
}
