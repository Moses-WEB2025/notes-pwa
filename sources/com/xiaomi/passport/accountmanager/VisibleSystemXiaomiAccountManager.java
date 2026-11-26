package com.xiaomi.passport.accountmanager;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import com.xiaomi.accountsdk.account.AccountIntent;
import com.xiaomi.accountsdk.account.XMPassportSettings;
import com.xiaomi.accountsdk.account.data.SecondaryDeviceInfo;
import com.xiaomi.accountsdk.account.exception.RequestSecondaryDeviceLoginDataException;
import com.xiaomi.accountsdk.futureservice.ClientFuture;
import com.xiaomi.accountsdk.futureservice.ServerServiceConnector;
import com.xiaomi.accountsdk.futureservice.SimpleClientFuture;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.SystemXiaomiAccountPackageName;
import com.xiaomi.passport.IPassportCommonService;
import com.xiaomi.passport.IPassportServiceTokenService;
import com.xiaomi.passport.servicetoken.ServiceTokenFuture;
import com.xiaomi.passport.servicetoken.ServiceTokenOp;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;
import com.xiaomi.passport.servicetoken.ServiceTokenUIErrorHandler;
import com.xiaomi.passport.servicetoken.data.XmAccountVisibility;
import com.xiaomi.passport.utils.LogoutInvalidateServiceTokenHelper;
import com.xiaomi.verificationsdk.internal.CommonUtils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/* JADX INFO: loaded from: classes4.dex */
class VisibleSystemXiaomiAccountManager extends ManageSystemXiaomiAccountManager {
    public static final int SUPPORT_SECONDARY_DEVICE_SYSTEM_ACCOUNT_VERSION = 325030300;
    public static final String TAG = "VisibleSystemXiaomiAccountManager";

    @Override // com.xiaomi.passport.accountmanager.OwnSystemXiaomiAccountManager, com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public ServiceTokenResult peekServiceToken(Account account, String str, Bundle bundle) {
        return null;
    }

    @Override // com.xiaomi.passport.accountmanager.OwnSystemXiaomiAccountManager, com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public void setServiceToken(Account account, String str, ServiceTokenResult serviceTokenResult) {
    }

    public VisibleSystemXiaomiAccountManager(Context context) {
        super(context);
    }

    @Override // com.xiaomi.passport.accountmanager.OwnSystemXiaomiAccountManager, com.xiaomi.passport.accountmanager.IAccountManager
    public String getUserData(Account account, String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        HashSet hashSet = new HashSet();
        hashSet.add(str);
        return getUserData(account, hashSet).get(str);
    }

    @Override // com.xiaomi.passport.accountmanager.OwnSystemXiaomiAccountManager, com.xiaomi.passport.accountmanager.IAccountManager
    public Map<String, String> getUserData(final Account account, final Set<String> set) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("can not be invoked in main thread");
        }
        SimpleClientFuture simpleClientFuture = new SimpleClientFuture();
        if (new AccountUserDataServiceConnector<Map<String, String>>(this.mContext, simpleClientFuture) { // from class: com.xiaomi.passport.accountmanager.VisibleSystemXiaomiAccountManager.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaomi.accountsdk.futureservice.ServerServiceConnector
            public Map<String, String> callServiceWork() throws RemoteException {
                int size = set.size();
                String[] strArr = new String[size];
                Iterator it = set.iterator();
                int i = 0;
                while (it.hasNext()) {
                    strArr[i] = (String) it.next();
                    i++;
                }
                String[] userData = getIService().getUserData(account, "passportapi", strArr);
                if (size != userData.length) {
                    return new HashMap();
                }
                HashMap map = new HashMap();
                for (int i2 = 0; i2 < size; i2++) {
                    map.put(strArr[i2], userData[i2]);
                }
                return map;
            }
        }.bind()) {
            try {
                return (Map) simpleClientFuture.get();
            } catch (InterruptedException e) {
                AccountLogger.log(TAG, "getUserData", e);
            } catch (ExecutionException e2) {
                AccountLogger.log(TAG, "getUserData", e2);
            }
        }
        return new HashMap();
    }

    @Override // com.xiaomi.passport.accountmanager.OwnSystemXiaomiAccountManager, com.xiaomi.passport.accountmanager.IAccountManager
    public void setUserData(Account account, String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return;
        }
        HashMap map = new HashMap();
        map.put(str, str2);
        setUserData(account, map);
    }

    @Override // com.xiaomi.passport.accountmanager.OwnSystemXiaomiAccountManager, com.xiaomi.passport.accountmanager.IAccountManager
    public void setUserData(final Account account, final Map<String, String> map) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("can not be invoked in main thread");
        }
        SimpleClientFuture simpleClientFuture = new SimpleClientFuture();
        if (new AccountUserDataServiceConnector<Void>(this.mContext, simpleClientFuture) { // from class: com.xiaomi.passport.accountmanager.VisibleSystemXiaomiAccountManager.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaomi.accountsdk.futureservice.ServerServiceConnector
            public Void callServiceWork() throws RemoteException {
                String[] strArr = new String[map.size()];
                String[] strArr2 = new String[map.size()];
                int i = 0;
                for (Map.Entry entry : map.entrySet()) {
                    strArr[i] = (String) entry.getKey();
                    strArr2[i] = (String) entry.getValue();
                    i++;
                }
                getIService().setUserData(account, "passportapi", strArr, strArr2);
                return null;
            }
        }.bind()) {
            try {
                simpleClientFuture.get();
            } catch (InterruptedException e) {
                AccountLogger.log(TAG, "setUserData", e);
            } catch (ExecutionException e2) {
                AccountLogger.log(TAG, "setUserData", e2);
            }
        }
    }

    @Override // com.xiaomi.passport.accountmanager.OwnSystemXiaomiAccountManager, com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public ServiceTokenFuture getServiceToken(Account account, final String str, Bundle bundle) {
        return new ServiceTokenOp.OpWorker() { // from class: com.xiaomi.passport.accountmanager.VisibleSystemXiaomiAccountManager.3
            @Override // com.xiaomi.passport.servicetoken.ServiceTokenOp.OpWorker
            protected ServiceTokenResult doWork() {
                ServiceTokenFuture serviceTokenFuture = new ServiceTokenFuture(null);
                if (new ServiceTokenServiceConnector<ServiceTokenResult>(VisibleSystemXiaomiAccountManager.this.mContext, serviceTokenFuture) { // from class: com.xiaomi.passport.accountmanager.VisibleSystemXiaomiAccountManager.3.1
                    /* JADX INFO: Access modifiers changed from: protected */
                    @Override // com.xiaomi.accountsdk.futureservice.ServerServiceConnector
                    public ServiceTokenResult callServiceWork() throws RemoteException {
                        return ServiceTokenUIErrorHandler.blockingHandleIntentError(VisibleSystemXiaomiAccountManager.this.mContext, getIService().getServiceToken(str));
                    }
                }.bind()) {
                    ServiceTokenResult serviceTokenResult = serviceTokenFuture.get();
                    if (serviceTokenResult.errorCode == ServiceTokenResult.ErrorCode.ERROR_NONE) {
                        LogoutInvalidateServiceTokenHelper.setServiceTokenSid(VisibleSystemXiaomiAccountManager.this.mContext, str);
                    }
                    return serviceTokenResult;
                }
                throw new IllegalStateException("bind service failed");
            }
        }.work();
    }

    @Override // com.xiaomi.passport.accountmanager.OwnSystemXiaomiAccountManager, com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public ServiceTokenFuture invalidateServiceToken(final ServiceTokenResult serviceTokenResult) {
        return new ServiceTokenOp.OpWorker() { // from class: com.xiaomi.passport.accountmanager.VisibleSystemXiaomiAccountManager.4
            @Override // com.xiaomi.passport.servicetoken.ServiceTokenOp.OpWorker
            protected ServiceTokenResult doWork() {
                ServiceTokenFuture serviceTokenFuture = new ServiceTokenFuture(null);
                if (new ServiceTokenServiceConnector<ServiceTokenResult>(VisibleSystemXiaomiAccountManager.this.mContext, serviceTokenFuture) { // from class: com.xiaomi.passport.accountmanager.VisibleSystemXiaomiAccountManager.4.1
                    /* JADX INFO: Access modifiers changed from: protected */
                    @Override // com.xiaomi.accountsdk.futureservice.ServerServiceConnector
                    public ServiceTokenResult callServiceWork() throws RemoteException {
                        return getIService().invalidateServiceToken(serviceTokenResult);
                    }
                }.bind()) {
                    return serviceTokenFuture.get();
                }
                throw new IllegalStateException("bind service failed");
            }
        }.work();
    }

    @Override // com.xiaomi.passport.accountmanager.OwnSystemXiaomiAccountManager, com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public ServiceTokenFuture refreshServiceToken(Account account, final String str, final ServiceTokenResult serviceTokenResult, Bundle bundle) {
        return new ServiceTokenOp.OpWorker() { // from class: com.xiaomi.passport.accountmanager.VisibleSystemXiaomiAccountManager.5
            @Override // com.xiaomi.passport.servicetoken.ServiceTokenOp.OpWorker
            protected ServiceTokenResult doWork() {
                ServiceTokenFuture serviceTokenFuture = new ServiceTokenFuture(null);
                if (new ServiceTokenServiceConnector<ServiceTokenResult>(VisibleSystemXiaomiAccountManager.this.mContext, serviceTokenFuture) { // from class: com.xiaomi.passport.accountmanager.VisibleSystemXiaomiAccountManager.5.1
                    /* JADX INFO: Access modifiers changed from: protected */
                    @Override // com.xiaomi.accountsdk.futureservice.ServerServiceConnector
                    public ServiceTokenResult callServiceWork() throws RemoteException {
                        getIService().invalidateServiceToken(serviceTokenResult);
                        return getIService().getServiceToken(str);
                    }
                }.bind()) {
                    return serviceTokenFuture.get();
                }
                throw new IllegalStateException("bind service failed");
            }
        }.work();
    }

    @Override // com.xiaomi.passport.accountmanager.AbsXiaomiAccountManager, com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public XiaomiAccountManagerFuture<XmAccountVisibility> makeAccountVisible(XiaomiAccountManagerCallback<XmAccountVisibility> xiaomiAccountManagerCallback, Handler handler) {
        return new XiaomiAccountManagerFuture<XmAccountVisibility>(xiaomiAccountManagerCallback, handler) { // from class: com.xiaomi.passport.accountmanager.VisibleSystemXiaomiAccountManager.6
            /* JADX WARN: Can't rename method to resolve collision */
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.xiaomi.passport.accountmanager.XiaomiAccountManagerFuture
            public XmAccountVisibility doWork() {
                Account xiaomiAccount = VisibleSystemXiaomiAccountManager.this.getXiaomiAccount();
                if (xiaomiAccount != null) {
                    return new XmAccountVisibility.Builder(XmAccountVisibility.ErrorCode.ERROR_NONE, null).accountVisible(true, xiaomiAccount).build();
                }
                SimpleClientFuture simpleClientFuture = new SimpleClientFuture();
                if (new ServiceTokenServiceConnector<XmAccountVisibility>(VisibleSystemXiaomiAccountManager.this.mContext, simpleClientFuture) { // from class: com.xiaomi.passport.accountmanager.VisibleSystemXiaomiAccountManager.6.1
                    /* JADX INFO: Access modifiers changed from: protected */
                    @Override // com.xiaomi.accountsdk.futureservice.ServerServiceConnector
                    public XmAccountVisibility callServiceWork() throws RemoteException {
                        if (getIService().supportAccessAccount()) {
                            return getIService().setAccountVisible(VisibleSystemXiaomiAccountManager.this.mContext.getPackageName());
                        }
                        return new XmAccountVisibility.Builder(XmAccountVisibility.ErrorCode.ERROR_NOT_SUPPORT, null).newChooseAccountIntent(AccountManager.newChooseAccountIntent(null, null, new String[]{"com.xiaomi"}, null, null, null, null)).build();
                    }
                }.bind()) {
                    try {
                        return (XmAccountVisibility) simpleClientFuture.get();
                    } catch (InterruptedException e) {
                        AccountLogger.log(XiaomiAccountManagerFuture.TAG, "makeAccountVisible", e);
                        return new XmAccountVisibility.Builder(XmAccountVisibility.ErrorCode.ERROR_CANCELLED, null).build();
                    } catch (ExecutionException e2) {
                        AccountLogger.log(XiaomiAccountManagerFuture.TAG, "makeAccountVisible", e2);
                        return new XmAccountVisibility.Builder(XmAccountVisibility.ErrorCode.ERROR_EXECUTION, e2.getMessage()).build();
                    }
                }
                throw new IllegalStateException("bind service failed");
            }
        }.start();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.xiaomi.passport.accountmanager.AbsXiaomiAccountManager, com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public String requestSecondaryDeviceLoginData(final SecondaryDeviceInfo secondaryDeviceInfo) throws RequestSecondaryDeviceLoginDataException {
        CommonUtils.checkWorkThreadAndThrow();
        try {
            int i = XMPassportSettings.getApplicationContext().getPackageManager().getPackageInfo(AccountIntent.PACKAGE_XIAOMI_ACCOUNT, 0).versionCode;
            AccountLogger.log(TAG, "requestSecondaryDeviceLoginData>>>versionCode=" + i);
            if (i < 325030300) {
                throw new RequestSecondaryDeviceLoginDataException("cur system account version not support, curVersion is" + i + ", need after version 325030300", RequestSecondaryDeviceLoginDataException.Code.UN_SUPPORT);
            }
            SimpleClientFuture simpleClientFuture = new SimpleClientFuture();
            if (!new ServiceTokenServiceConnector<Bundle>(this.mContext, simpleClientFuture) { // from class: com.xiaomi.passport.accountmanager.VisibleSystemXiaomiAccountManager.7
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.xiaomi.accountsdk.futureservice.ServerServiceConnector
                public Bundle callServiceWork() throws RemoteException {
                    return getIService().requestSecondaryDeviceLoginData(secondaryDeviceInfo.toBundle());
                }
            }.bind()) {
                throw new RequestSecondaryDeviceLoginDataException("bind service failed");
            }
            try {
                return PrimarySecondaryDeviceAccountHelper.parseLoginDataBundleFromSystemAccount((Bundle) simpleClientFuture.get());
            } catch (InterruptedException e) {
                throw new RequestSecondaryDeviceLoginDataException(e);
            } catch (ExecutionException e2) {
                if (!TextUtils.isEmpty(e2.getMessage()) && e2.getMessage().contains("requestSecondaryDeviceLoginData")) {
                    throw new RequestSecondaryDeviceLoginDataException("get secondary device login data is unsupported, please upgrade Xiaomi account version", RequestSecondaryDeviceLoginDataException.Code.UN_SUPPORT);
                }
                throw new RequestSecondaryDeviceLoginDataException(e2.getCause());
            }
        } catch (PackageManager.NameNotFoundException unused) {
            throw new RequestSecondaryDeviceLoginDataException("not exist system account", RequestSecondaryDeviceLoginDataException.Code.UN_SUPPORT);
        }
    }

    private static abstract class AccountUserDataServiceConnector<T> extends ServerServiceConnector<IPassportCommonService, T, T> {
        private static final String ACTION_PASSPORT_COMMON_SERVICE = "com.xiaomi.account.action.COMMON_SERVICE";

        protected AccountUserDataServiceConnector(Context context, ClientFuture<T, T> clientFuture) {
            super(context, "com.xiaomi.account.action.COMMON_SERVICE", SystemXiaomiAccountPackageName.getValid(context), clientFuture);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.xiaomi.accountsdk.futureservice.ServerServiceConnector
        public final IPassportCommonService binderToServiceType(IBinder iBinder) {
            return IPassportCommonService.Stub.asInterface(iBinder);
        }
    }

    private static abstract class ServiceTokenServiceConnector<T> extends ServerServiceConnector<IPassportServiceTokenService, T, T> {
        private static final String ACTION_SERVICE_TOKEN_OP = "com.xiaomi.account.action.SERVICE_TOKEN_OP";

        protected ServiceTokenServiceConnector(Context context, ClientFuture<T, T> clientFuture) {
            super(context, "com.xiaomi.account.action.SERVICE_TOKEN_OP", SystemXiaomiAccountPackageName.getValid(context), clientFuture);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.xiaomi.accountsdk.futureservice.ServerServiceConnector
        public final IPassportServiceTokenService binderToServiceType(IBinder iBinder) {
            return IPassportServiceTokenService.Stub.asInterface(iBinder);
        }
    }
}
