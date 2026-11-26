package com.xiaomi.accountsdk.guestaccount;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import com.xiaomi.accountsdk.futureservice.ServerServiceConnector;
import com.xiaomi.accountsdk.futureservice.SimpleClientFuture;
import com.xiaomi.accountsdk.guestaccount.data.GuestAccountFuture;
import com.xiaomi.accountsdk.guestaccount.data.GuestAccountResult;
import com.xiaomi.accountsdk.guestaccount.data.ServiceArgument;

/* JADX INFO: loaded from: classes4.dex */
class GuestAccountManagerImplMiui extends GuestAccountManager {
    private static final String MIUI_GUEST_ACCOUNT_SERVICE_ACTION = "com.xiaomi.account.action.BIND_GUEST_ACCOUNT_SERVICE";
    private static final String MIUI_GUEST_ACCOUNT_SERVICE_PACKAGE_NAME = "com.xiaomi.account";
    private static volatile GuestAccountManagerImplMiui sInstance;

    @Override // com.xiaomi.accountsdk.guestaccount.IGuestAccountManager
    public void injectBackupUtil(GuestAccountBackupUtil guestAccountBackupUtil) {
    }

    @Override // com.xiaomi.accountsdk.guestaccount.IGuestAccountManager
    public void injectHardwareInfoFetcher(GuestAccountHardwareInfoFetcher guestAccountHardwareInfoFetcher) {
    }

    @Override // com.xiaomi.accountsdk.guestaccount.IGuestAccountManager
    public void injectHttpRequester(GuestAccountHttpRequester guestAccountHttpRequester) {
    }

    static synchronized GuestAccountManagerImplMiui getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new GuestAccountManagerImplMiui(context);
        }
        return sInstance;
    }

    private GuestAccountManagerImplMiui(Context context) {
        super(context);
    }

    @Override // com.xiaomi.accountsdk.guestaccount.GuestAccountManager
    protected GuestAccountFuture getStoredUserIdImpl(final ServiceArgument serviceArgument) {
        GuestAccountFuture guestAccountFuture = new GuestAccountFuture();
        new GuestAccountServiceConnector<GuestAccountResult>(guestAccountFuture) { // from class: com.xiaomi.accountsdk.guestaccount.GuestAccountManagerImplMiui.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaomi.accountsdk.futureservice.ServerServiceConnector
            public GuestAccountResult callServiceWork() throws RemoteException {
                return new GuestAccountResult(getIService().getStoredUserId(serviceArgument.toBundle()));
            }
        }.bind();
        return guestAccountFuture;
    }

    @Override // com.xiaomi.accountsdk.guestaccount.GuestAccountManager
    protected GuestAccountFuture getGuestAccountImpl(final ServiceArgument serviceArgument, final IGuestAccountIntentHandler iGuestAccountIntentHandler) {
        GuestAccountFuture guestAccountFuture = new GuestAccountFuture();
        new GuestAccountServiceConnector<GuestAccountResult>(guestAccountFuture) { // from class: com.xiaomi.accountsdk.guestaccount.GuestAccountManagerImplMiui.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaomi.accountsdk.futureservice.ServerServiceConnector
            public GuestAccountResult callServiceWork() throws RemoteException {
                return new GuestAccountResult(getIService().getGuestAccount(serviceArgument.toBundle(), iGuestAccountIntentHandler));
            }
        }.bind();
        return guestAccountFuture;
    }

    @Override // com.xiaomi.accountsdk.guestaccount.GuestAccountManager
    protected GuestAccountFuture renewServiceTokenImpl(final ServiceArgument serviceArgument, final IGuestAccountIntentHandler iGuestAccountIntentHandler) {
        GuestAccountFuture guestAccountFuture = new GuestAccountFuture();
        new GuestAccountServiceConnector<GuestAccountResult>(guestAccountFuture) { // from class: com.xiaomi.accountsdk.guestaccount.GuestAccountManagerImplMiui.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaomi.accountsdk.futureservice.ServerServiceConnector
            public GuestAccountResult callServiceWork() throws RemoteException {
                return new GuestAccountResult(getIService().renewServiceToken(serviceArgument.toBundle(), iGuestAccountIntentHandler));
            }
        }.bind();
        return guestAccountFuture;
    }

    @Override // com.xiaomi.accountsdk.guestaccount.IGuestAccountManager
    public void onXiaomiAccountCTAAllowed() {
        new GuestAccountServiceConnector<Void>(new SimpleClientFuture()) { // from class: com.xiaomi.accountsdk.guestaccount.GuestAccountManagerImplMiui.4
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaomi.accountsdk.futureservice.ServerServiceConnector
            public Void callServiceWork() throws RemoteException {
                getIService().onXiaomiAccountCTAAllowed();
                return null;
            }
        }.bind();
    }

    @Override // com.xiaomi.accountsdk.guestaccount.IGuestAccountManager
    public void saveToBackup() {
        throw new IllegalStateException("don NOT call this for GuestAccountManagerImplMiui");
    }

    @Override // com.xiaomi.accountsdk.guestaccount.IGuestAccountManager
    public void restoreFromBackup() {
        throw new IllegalStateException("don NOT call this for GuestAccountManagerImplMiui");
    }

    static boolean isMiuiGuestAccountAvailable(Context context) {
        Intent intent = new Intent(MIUI_GUEST_ACCOUNT_SERVICE_ACTION);
        intent.setPackage("com.xiaomi.account");
        return context.getPackageManager().resolveService(intent, 0) != null;
    }

    private abstract class GuestAccountServiceConnector<T> extends ServerServiceConnector<IGuestAccountService, T, T> {
        public GuestAccountServiceConnector(SimpleClientFuture<T> simpleClientFuture) {
            super(GuestAccountManagerImplMiui.this.applicationContext, GuestAccountManagerImplMiui.MIUI_GUEST_ACCOUNT_SERVICE_ACTION, "com.xiaomi.account", simpleClientFuture);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.xiaomi.accountsdk.futureservice.ServerServiceConnector
        public IGuestAccountService binderToServiceType(IBinder iBinder) {
            return IGuestAccountService.Stub.asInterface(iBinder);
        }
    }
}
