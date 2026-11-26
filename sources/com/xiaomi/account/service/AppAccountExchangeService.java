package com.xiaomi.account.service;

import android.accounts.Account;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import com.xiaomi.account.passportsdk.account_lib.IAccountExchangeService;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.data.ExtendedAuthToken;
import com.xiaomi.accountsdk.account.utils.AppSignatureUtil;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.SystemXiaomiAccountPackageName;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import com.xiaomi.verificationsdk.internal.CommonUtils;

/* JADX INFO: loaded from: classes4.dex */
public class AppAccountExchangeService extends Service {
    private static final String TAG = "AppAccountExchangeService";
    private IAccountExchangeService.Stub mBinder;

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        if (this.mBinder == null) {
            this.mBinder = new _AccountExchangeServiceStub();
        }
        return this.mBinder;
    }

    private class _AccountExchangeServiceStub extends IAccountExchangeService.Stub {
        private XiaomiAccountManager mAccountManager;

        public _AccountExchangeServiceStub() {
            try {
                this.mAccountManager = XiaomiAccountManager.get(AppAccountExchangeService.this);
            } catch (Throwable unused) {
                AccountLogger.log(AppAccountExchangeService.TAG, "XiaomiAccountManager get error, ignore");
            }
        }

        @Override // com.xiaomi.account.passportsdk.account_lib.IAccountExchangeService
        public void tryAddAccount(AccountInfo accountInfo, String str) throws RemoteException {
            AccountLogger.log(AppAccountExchangeService.TAG, "not support add account from other apps");
        }

        @Override // com.xiaomi.account.passportsdk.account_lib.IAccountExchangeService
        public AccountInfo getNullableAccount(String str) throws RemoteException {
            if (this.mAccountManager == null) {
                AccountLogger.log(AppAccountExchangeService.TAG, "XiaomiAccountManager not setup, skip");
                return null;
            }
            if (TextUtils.isEmpty(str)) {
                AccountLogger.log(AppAccountExchangeService.TAG, "caller pkg name is empty");
                return null;
            }
            String[] packagesForUid = AppAccountExchangeService.this.getPackageManager().getPackagesForUid(Binder.getCallingUid());
            if (packagesForUid != null && packagesForUid.length > 0) {
                boolean z = false;
                for (String str2 : packagesForUid) {
                    if (TextUtils.equals(str2, str)) {
                        AppAccountExchangeService appAccountExchangeService = AppAccountExchangeService.this;
                        String packageName = appAccountExchangeService.getPackageName();
                        if (CommonUtils.isXiaomiDevice() && TextUtils.equals(str, SystemXiaomiAccountPackageName.getValid(appAccountExchangeService))) {
                            z = true;
                        }
                        boolean zEquals = AppSignatureUtil.getValidSignatureHash(appAccountExchangeService, packageName).equals(AppSignatureUtil.getValidSignatureHash(appAccountExchangeService, str));
                        if (!z && !zEquals) {
                            AccountLogger.log(AppAccountExchangeService.TAG, "not called from sys account and caller signature is not same with mine");
                            return null;
                        }
                        Account xiaomiAccount = this.mAccountManager.getXiaomiAccount();
                        if (xiaomiAccount == null) {
                            AccountLogger.log(AppAccountExchangeService.TAG, "no account, skip");
                            return null;
                        }
                        String password = this.mAccountManager.getPassword(xiaomiAccount);
                        if (TextUtils.isEmpty(password)) {
                            AccountLogger.log(AppAccountExchangeService.TAG, "password is empty, skip");
                            return null;
                        }
                        AccountLogger.log(AppAccountExchangeService.TAG, "get account info success from " + str);
                        ExtendedAuthToken extendedAuthToken = ExtendedAuthToken.parse(password);
                        return new AccountInfo.Builder().userId(xiaomiAccount.name).passToken(extendedAuthToken.authToken).psecurity(extendedAuthToken.security).build();
                    }
                }
            }
            AccountLogger.log(AppAccountExchangeService.TAG, "caller uid and pkg name mismatch");
            return null;
        }
    }
}
