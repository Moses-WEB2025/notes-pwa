package com.xiaomi.accounts;

import android.accounts.Account;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.xiaomi.accountsdk.utils.AccountLogger;
import java.util.Arrays;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
public abstract class AbstractAccountAuthenticator {
    private static final String TAG = "AccountAuthenticator";
    private final Context mContext;
    private Transport mTransport = new Transport();

    public abstract Bundle addAccount(AccountAuthenticatorResponse accountAuthenticatorResponse, String str, String str2, String[] strArr, Bundle bundle) throws NetworkErrorException;

    public abstract Bundle confirmCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, Bundle bundle) throws NetworkErrorException;

    public abstract Bundle editProperties(AccountAuthenticatorResponse accountAuthenticatorResponse, String str);

    public abstract Bundle getAuthToken(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String str, Bundle bundle) throws NetworkErrorException;

    public abstract String getAuthTokenLabel(String str);

    public abstract Bundle hasFeatures(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String[] strArr) throws NetworkErrorException;

    public abstract Bundle updateCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String str, Bundle bundle) throws NetworkErrorException;

    public AbstractAccountAuthenticator(Context context) {
        this.mContext = context;
    }

    private class Transport extends IAccountAuthenticator.Stub {
        private Transport() {
        }

        @Override // com.xiaomi.accounts.IAccountAuthenticator
        public void addAccount(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str, String str2, String[] strArr, Bundle bundle) throws RemoteException {
            String str3;
            AccountLogger.log(AbstractAccountAuthenticator.TAG, "addAccount: accountType " + str + ", authTokenType " + str2 + ", features " + (strArr == null ? _UrlKt.PATH_SEGMENT_ENCODE_SET_URI : Arrays.toString(strArr)));
            AbstractAccountAuthenticator.this.checkBinderPermission();
            try {
                str3 = str;
                try {
                    Bundle bundleAddAccount = AbstractAccountAuthenticator.this.addAccount(new AccountAuthenticatorResponse(iAccountAuthenticatorResponse), str3, str2, strArr, bundle);
                    bundleAddAccount.keySet();
                    AccountLogger.log(AbstractAccountAuthenticator.TAG, "addAccount: result " + AccountManager.sanitizeResult(bundleAddAccount));
                    if (bundleAddAccount != null) {
                        iAccountAuthenticatorResponse.onResult(bundleAddAccount);
                    }
                } catch (Exception e) {
                    e = e;
                    AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "addAccount", str3, e);
                }
            } catch (Exception e2) {
                e = e2;
                str3 = str;
            }
        }

        @Override // com.xiaomi.accounts.IAccountAuthenticator
        public void confirmCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, Bundle bundle) throws RemoteException {
            AccountLogger.log(AbstractAccountAuthenticator.TAG, "confirmCredentials: " + account);
            AbstractAccountAuthenticator.this.checkBinderPermission();
            try {
                Bundle bundleConfirmCredentials = AbstractAccountAuthenticator.this.confirmCredentials(new AccountAuthenticatorResponse(iAccountAuthenticatorResponse), account, bundle);
                bundleConfirmCredentials.keySet();
                AccountLogger.log(AbstractAccountAuthenticator.TAG, "confirmCredentials: result " + AccountManager.sanitizeResult(bundleConfirmCredentials));
                if (bundleConfirmCredentials != null) {
                    iAccountAuthenticatorResponse.onResult(bundleConfirmCredentials);
                }
            } catch (Exception e) {
                AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "confirmCredentials", account.toString(), e);
            }
        }

        @Override // com.xiaomi.accounts.IAccountAuthenticator
        public void getAuthTokenLabel(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str) throws RemoteException {
            AccountLogger.log(AbstractAccountAuthenticator.TAG, "getAuthTokenLabel: authTokenType " + str);
            AbstractAccountAuthenticator.this.checkBinderPermission();
            try {
                Bundle bundle = new Bundle();
                bundle.putString("authTokenLabelKey", AbstractAccountAuthenticator.this.getAuthTokenLabel(str));
                bundle.keySet();
                AccountLogger.log(AbstractAccountAuthenticator.TAG, "getAuthTokenLabel: result " + AccountManager.sanitizeResult(bundle));
                iAccountAuthenticatorResponse.onResult(bundle);
            } catch (Exception e) {
                AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "getAuthTokenLabel", str, e);
            }
        }

        @Override // com.xiaomi.accounts.IAccountAuthenticator
        public void getAuthToken(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String str, Bundle bundle) throws RemoteException {
            AccountLogger.log(AbstractAccountAuthenticator.TAG, "getAuthToken: " + account + ", authTokenType " + str);
            AbstractAccountAuthenticator.this.checkBinderPermission();
            try {
                Bundle authToken = AbstractAccountAuthenticator.this.getAuthToken(new AccountAuthenticatorResponse(iAccountAuthenticatorResponse), account, str, bundle);
                authToken.keySet();
                AccountLogger.log(AbstractAccountAuthenticator.TAG, "getAuthToken: result " + AccountManager.sanitizeResult(authToken));
                if (authToken != null) {
                    iAccountAuthenticatorResponse.onResult(authToken);
                }
            } catch (Exception e) {
                AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "getAuthToken", account.toString() + "," + str, e);
            }
        }

        @Override // com.xiaomi.accounts.IAccountAuthenticator
        public void updateCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String str, Bundle bundle) throws RemoteException {
            AccountLogger.log(AbstractAccountAuthenticator.TAG, "updateCredentials: " + account + ", authTokenType " + str);
            AbstractAccountAuthenticator.this.checkBinderPermission();
            try {
                Bundle bundleUpdateCredentials = AbstractAccountAuthenticator.this.updateCredentials(new AccountAuthenticatorResponse(iAccountAuthenticatorResponse), account, str, bundle);
                bundleUpdateCredentials.keySet();
                AccountLogger.log(AbstractAccountAuthenticator.TAG, "updateCredentials: result " + AccountManager.sanitizeResult(bundleUpdateCredentials));
                if (bundleUpdateCredentials != null) {
                    iAccountAuthenticatorResponse.onResult(bundleUpdateCredentials);
                }
            } catch (Exception e) {
                AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "updateCredentials", account.toString() + "," + str, e);
            }
        }

        @Override // com.xiaomi.accounts.IAccountAuthenticator
        public void editProperties(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str) throws RemoteException {
            AbstractAccountAuthenticator.this.checkBinderPermission();
            try {
                Bundle bundleEditProperties = AbstractAccountAuthenticator.this.editProperties(new AccountAuthenticatorResponse(iAccountAuthenticatorResponse), str);
                if (bundleEditProperties != null) {
                    iAccountAuthenticatorResponse.onResult(bundleEditProperties);
                }
            } catch (Exception e) {
                AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "editProperties", str, e);
            }
        }

        @Override // com.xiaomi.accounts.IAccountAuthenticator
        public void hasFeatures(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String[] strArr) throws RemoteException {
            AbstractAccountAuthenticator.this.checkBinderPermission();
            try {
                Bundle bundleHasFeatures = AbstractAccountAuthenticator.this.hasFeatures(new AccountAuthenticatorResponse(iAccountAuthenticatorResponse), account, strArr);
                if (bundleHasFeatures != null) {
                    iAccountAuthenticatorResponse.onResult(bundleHasFeatures);
                }
            } catch (Exception e) {
                AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "hasFeatures", account.toString(), e);
            }
        }

        @Override // com.xiaomi.accounts.IAccountAuthenticator
        public void getAccountRemovalAllowed(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account) throws RemoteException {
            AbstractAccountAuthenticator.this.checkBinderPermission();
            try {
                Bundle accountRemovalAllowed = AbstractAccountAuthenticator.this.getAccountRemovalAllowed(new AccountAuthenticatorResponse(iAccountAuthenticatorResponse), account);
                if (accountRemovalAllowed != null) {
                    iAccountAuthenticatorResponse.onResult(accountRemovalAllowed);
                }
            } catch (Exception e) {
                AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "getAccountRemovalAllowed", account.toString(), e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleException(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str, String str2, Exception exc) throws RemoteException {
        if (exc instanceof NetworkErrorException) {
            AccountLogger.log(TAG, str + "(" + str2 + ")", exc);
            iAccountAuthenticatorResponse.onError(3, exc.getMessage());
        } else if (exc instanceof UnsupportedOperationException) {
            AccountLogger.log(TAG, str + "(" + str2 + ")", exc);
            iAccountAuthenticatorResponse.onError(6, str + " not supported");
        } else if (exc instanceof IllegalArgumentException) {
            AccountLogger.log(TAG, str + "(" + str2 + ")", exc);
            iAccountAuthenticatorResponse.onError(7, str + " not supported");
        } else {
            AccountLogger.log(TAG, str + "(" + str2 + ")", exc);
            iAccountAuthenticatorResponse.onError(1, str + " failed");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkBinderPermission() {
        int callingUid = Binder.getCallingUid();
        if (this.mContext.getApplicationInfo().uid != callingUid && this.mContext.checkCallingOrSelfPermission("android.permission.ACCOUNT_MANAGER") != 0) {
            throw new SecurityException("caller uid " + callingUid + " lacks android.permission.ACCOUNT_MANAGER");
        }
    }

    public final IBinder getIBinder() {
        return this.mTransport.asBinder();
    }

    public Bundle getAccountRemovalAllowed(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account) throws NetworkErrorException {
        Bundle bundle = new Bundle();
        bundle.putBoolean("booleanResult", true);
        return bundle;
    }
}
