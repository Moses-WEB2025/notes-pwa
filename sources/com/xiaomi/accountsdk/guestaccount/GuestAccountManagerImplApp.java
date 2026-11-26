package com.xiaomi.accountsdk.guestaccount;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.text.TextUtils;
import com.xiaomi.accountsdk.guestaccount.data.GuestAccount;
import com.xiaomi.accountsdk.guestaccount.data.GuestAccountFuture;
import com.xiaomi.accountsdk.guestaccount.data.GuestAccountResult;
import com.xiaomi.accountsdk.guestaccount.data.GuestAccountType;
import com.xiaomi.accountsdk.guestaccount.data.ServiceArgument;
import com.xiaomi.accountsdk.utils.AccountLogger;

/* JADX INFO: loaded from: classes4.dex */
class GuestAccountManagerImplApp extends GuestAccountManager {
    private static final String TAG = "GuestAccountManagerApp";
    private static volatile GuestAccountManagerImplApp sInstance;
    private GuestAccountBackupUtil backupUtil;
    private final GuestAccountType guestAccountType;
    private final GuestAccountOnlineFetcher onlineFetcher;
    private final GuestAccountStorage storage;

    @Override // com.xiaomi.accountsdk.guestaccount.IGuestAccountManager
    public void onXiaomiAccountCTAAllowed() {
    }

    static synchronized GuestAccountManagerImplApp getInstance(Context context, GuestAccountType guestAccountType) {
        if (sInstance == null) {
            sInstance = new GuestAccountManagerImplApp(context, guestAccountType);
        }
        return sInstance;
    }

    private GuestAccountManagerImplApp(Context context, GuestAccountType guestAccountType) {
        this(context, guestAccountType, new GuestAccountOnlineFetcher(context.getApplicationContext()), new GuestAccountStorage());
    }

    GuestAccountManagerImplApp(Context context, GuestAccountType guestAccountType, GuestAccountOnlineFetcher guestAccountOnlineFetcher, GuestAccountStorage guestAccountStorage) {
        super(context);
        if (guestAccountOnlineFetcher == null) {
            throw new IllegalArgumentException("onlineFetcher == null");
        }
        if (guestAccountStorage == null) {
            throw new IllegalArgumentException("storage == null");
        }
        if (guestAccountType == null) {
            throw new IllegalArgumentException("type == null");
        }
        this.guestAccountType = guestAccountType;
        this.onlineFetcher = guestAccountOnlineFetcher;
        this.storage = guestAccountStorage;
    }

    @Override // com.xiaomi.accountsdk.guestaccount.IGuestAccountManager
    public void injectHardwareInfoFetcher(GuestAccountHardwareInfoFetcher guestAccountHardwareInfoFetcher) {
        this.onlineFetcher.setHardwareInfoFetcher(guestAccountHardwareInfoFetcher);
    }

    @Override // com.xiaomi.accountsdk.guestaccount.IGuestAccountManager
    public void injectHttpRequester(GuestAccountHttpRequester guestAccountHttpRequester) {
        this.onlineFetcher.setRequest(guestAccountHttpRequester);
    }

    @Override // com.xiaomi.accountsdk.guestaccount.IGuestAccountManager
    public void injectBackupUtil(GuestAccountBackupUtil guestAccountBackupUtil) {
        this.backupUtil = guestAccountBackupUtil;
    }

    @Override // com.xiaomi.accountsdk.guestaccount.GuestAccountManager
    protected GuestAccountFuture getStoredUserIdImpl(ServiceArgument serviceArgument) {
        GuestAccountFuture guestAccountFuture = new GuestAccountFuture();
        guestAccountFuture.setServerData(new GuestAccountResult().setGuestAccount(this.storage.getGuestAccount(this.applicationContext)));
        return guestAccountFuture;
    }

    @Override // com.xiaomi.accountsdk.guestaccount.GuestAccountManager
    protected GuestAccountFuture getGuestAccountImpl(ServiceArgument serviceArgument, IGuestAccountIntentHandler iGuestAccountIntentHandler) {
        return internalGetGuestAccount(false, serviceArgument);
    }

    @Override // com.xiaomi.accountsdk.guestaccount.GuestAccountManager
    protected GuestAccountFuture renewServiceTokenImpl(ServiceArgument serviceArgument, IGuestAccountIntentHandler iGuestAccountIntentHandler) {
        return internalGetGuestAccount(true, serviceArgument);
    }

    private GuestAccountFuture internalGetGuestAccount(boolean z, final ServiceArgument serviceArgument) {
        if (z) {
            this.storage.removeServiceToken(this.applicationContext, serviceArgument.getSid());
        }
        final GuestAccount guestAccount = this.storage.getGuestAccount(this.applicationContext, serviceArgument.getSid());
        if (checkServiceToken(guestAccount)) {
            GuestAccountFuture guestAccountFuture = new GuestAccountFuture();
            guestAccountFuture.setServerData(new GuestAccountResult().setGuestAccount(guestAccount));
            AccountLogger.log(TAG, "getGuestAccount from cache");
            return guestAccountFuture;
        }
        if (checkPassToken(guestAccount)) {
            AccountLogger.log(TAG, "getGuestAccount from login");
            return new OnlineRunnable() { // from class: com.xiaomi.accountsdk.guestaccount.GuestAccountManagerImplApp.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super();
                }

                @Override // com.xiaomi.accountsdk.guestaccount.GuestAccountManagerImplApp.OnlineRunnable
                protected GuestAccountResult registerOrLogin() {
                    GuestAccountResult guestAccountResultLoginByPassToken = GuestAccountManagerImplApp.this.onlineFetcher.loginByPassToken(guestAccount, null);
                    GuestAccount guestAccount2 = guestAccountResultLoginByPassToken.getGuestAccount();
                    if (guestAccount2 != null) {
                        guestAccountResultLoginByPassToken.setGuestAccount(guestAccount2.copyWithNewType(guestAccount.type));
                    }
                    return guestAccountResultLoginByPassToken;
                }
            }.executeOnNonUiThread();
        }
        AccountLogger.log(TAG, "getGuestAccount from register");
        return new OnlineRunnable() { // from class: com.xiaomi.accountsdk.guestaccount.GuestAccountManagerImplApp.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.xiaomi.accountsdk.guestaccount.GuestAccountManagerImplApp.OnlineRunnable
            protected GuestAccountResult registerOrLogin() {
                return GuestAccountManagerImplApp.this.onlineFetcher.registerGuestAccount(GuestAccountManagerImplApp.this.guestAccountType, serviceArgument.getSid(), serviceArgument.getCallback(), serviceArgument.getPSid());
            }
        }.executeOnNonUiThread();
    }

    @Override // com.xiaomi.accountsdk.guestaccount.IGuestAccountManager
    public void saveToBackup() {
        if (this.backupUtil == null) {
            AccountLogger.log(TAG, "null backup util. Bail.");
            return;
        }
        GuestAccount guestAccount = this.storage.getGuestAccount(this.applicationContext);
        if (!checkPassToken(guestAccount)) {
            AccountLogger.log(TAG, "fail to checkPassToken. Can not save to backup. Bail.");
        } else {
            this.backupUtil.saveGuestAccount(guestAccount);
        }
    }

    @Override // com.xiaomi.accountsdk.guestaccount.IGuestAccountManager
    public void restoreFromBackup() {
        AccountLogger.log(TAG, "restoreFromBackup called.");
        if (this.backupUtil == null) {
            AccountLogger.log(TAG, "null backup util. Bail.");
            return;
        }
        try {
            if (getStoredUserId().get().getGuestAccount() != null) {
                AccountLogger.log(TAG, "already has account. Can not restore. Bail.");
                return;
            }
            GuestAccount guestAccountLoadGuestAccount = this.backupUtil.loadGuestAccount();
            if (!checkPassToken(guestAccountLoadGuestAccount)) {
                AccountLogger.log(TAG, "fail to checkPassToken. Can not restore from backup. Bail.");
            } else {
                this.storage.savePassToken(this.applicationContext, guestAccountLoadGuestAccount);
            }
        } catch (InterruptedException e) {
            AccountLogger.log(TAG, e);
        }
    }

    static boolean checkPassToken(GuestAccount guestAccount) {
        return (guestAccount == null || TextUtils.isEmpty(guestAccount.userId) || TextUtils.isEmpty(guestAccount.cUserId) || TextUtils.isEmpty(guestAccount.passToken)) ? false : true;
    }

    static boolean checkServiceToken(GuestAccount guestAccount) {
        return (guestAccount == null || TextUtils.isEmpty(guestAccount.userId) || TextUtils.isEmpty(guestAccount.cUserId) || TextUtils.isEmpty(guestAccount.serviceToken) || TextUtils.isEmpty(guestAccount.security) || TextUtils.isEmpty(guestAccount.slh) || TextUtils.isEmpty(guestAccount.ph)) ? false : true;
    }

    private abstract class OnlineRunnable implements Runnable {
        private final GuestAccountFuture future = new GuestAccountFuture();

        protected abstract GuestAccountResult registerOrLogin();

        public OnlineRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            GuestAccountResult guestAccountResultRegisterOrLogin = registerOrLogin();
            GuestAccount guestAccount = guestAccountResultRegisterOrLogin.getGuestAccount();
            if (guestAccount == null) {
                this.future.setServerData(guestAccountResultRegisterOrLogin);
                return;
            }
            if (GuestAccountManagerImplApp.checkPassToken(guestAccount)) {
                GuestAccountManagerImplApp.this.storage.savePassToken(GuestAccountManagerImplApp.this.applicationContext, guestAccount);
            }
            if (GuestAccountManagerImplApp.checkServiceToken(guestAccount)) {
                GuestAccountManagerImplApp.this.storage.saveServiceToken(GuestAccountManagerImplApp.this.applicationContext, guestAccount);
                this.future.setServerData(guestAccountResultRegisterOrLogin);
            } else {
                if (!TextUtils.isEmpty(guestAccount.callback)) {
                    GuestAccountResult guestAccountResultVisitSts = GuestAccountManagerImplApp.this.onlineFetcher.visitSts(guestAccount);
                    GuestAccount guestAccount2 = guestAccountResultVisitSts.getGuestAccount();
                    if (GuestAccountManagerImplApp.checkServiceToken(guestAccount2)) {
                        GuestAccountManagerImplApp.this.storage.saveServiceToken(GuestAccountManagerImplApp.this.applicationContext, guestAccount2);
                    }
                    this.future.setServerData(guestAccountResultVisitSts);
                    return;
                }
                this.future.setServerData(guestAccountResultRegisterOrLogin);
            }
        }

        public GuestAccountFuture executeOnNonUiThread() {
            if (Looper.getMainLooper() == Looper.myLooper()) {
                AsyncTask.THREAD_POOL_EXECUTOR.execute(this);
            } else {
                run();
            }
            return this.future;
        }
    }
}
