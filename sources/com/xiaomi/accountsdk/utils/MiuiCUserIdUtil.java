package com.xiaomi.accountsdk.utils;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import com.xiaomi.accountsdk.account.IXiaomiAccountService;
import com.xiaomi.accountsdk.futureservice.ServerServiceConnector;
import com.xiaomi.accountsdk.futureservice.SimpleClientFuture;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;
import java.util.concurrent.ExecutionException;

/* JADX INFO: loaded from: classes4.dex */
public final class MiuiCUserIdUtil {
    private static final String ACTION_NAME = "android.intent.action.BIND_XIAOMI_ACCOUNT_SERVICE";
    private static final String ACTION_NEW_NAME = "com.xiaomi.account.action.BIND_XIAOMI_ACCOUNT_SERVICE";
    private static final String PACKAGE_NAME = "com.xiaomi.account";
    private static final String TAG = "MiuiCUserIdUtil";
    private final Account mAccount;
    private final Context mContext;

    public MiuiCUserIdUtil(Context context, Account account) {
        if (context == null) {
            throw new IllegalArgumentException("context == null");
        }
        this.mContext = context.getApplicationContext();
        this.mAccount = account;
    }

    public final String getCUserId() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("MiuiCUserIdUtil#getCUserId() should NOT be called on main thread!");
        }
        try {
            return getFromXiaomiAccountService();
        } catch (SecurityException unused) {
            return getFromServiceTokenUtil();
        }
    }

    private String getFromServiceTokenUtil() {
        ServiceTokenResult serviceTokenResult = XiaomiAccountManager.get(this.mContext).getServiceToken(this.mAccount, "passportapi", null).get();
        if (serviceTokenResult != null) {
            return serviceTokenResult.cUserId;
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private String getFromXiaomiAccountService() {
        String str = ACTION_NEW_NAME;
        if (this.mContext.getPackageManager().resolveService(new Intent(ACTION_NEW_NAME), 0) == null) {
            str = ACTION_NAME;
        }
        SimpleClientFuture simpleClientFuture = new SimpleClientFuture();
        new ServerServiceConnector<IXiaomiAccountService, String, String>(this.mContext, str, "com.xiaomi.account", simpleClientFuture) { // from class: com.xiaomi.accountsdk.utils.MiuiCUserIdUtil.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaomi.accountsdk.futureservice.ServerServiceConnector
            public String callServiceWork() throws RemoteException {
                return getIService().getEncryptedUserId(MiuiCUserIdUtil.this.mAccount);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.xiaomi.accountsdk.futureservice.ServerServiceConnector
            public IXiaomiAccountService binderToServiceType(IBinder iBinder) {
                return IXiaomiAccountService.Stub.asInterface(iBinder);
            }
        }.bind();
        try {
            return (String) simpleClientFuture.get();
        } catch (InterruptedException e) {
            AccountLogger.log(TAG, "getCUserId", e);
            return null;
        } catch (ExecutionException e2) {
            AccountLogger.log(TAG, "getCUserId", e2);
            return null;
        }
    }
}
