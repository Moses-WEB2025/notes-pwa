package miuix.internal.webkit;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes5.dex */
public class DeviceAccountLogin {
    private static final String TAG = "DeviceAccountLogin";
    protected AccountManager mAccountManager;
    protected Activity mActivity;
    private AccountManagerCallback<Bundle> mCallback = new LoginCallback(this);

    public void onLoginCancel() {
    }

    public void onLoginFail() {
    }

    public void onLoginPageFinished() {
    }

    public void onLoginStart() {
    }

    public void onLoginSuccess(String str) {
    }

    public DeviceAccountLogin(Activity activity) {
        this.mActivity = activity;
        this.mAccountManager = AccountManager.get(activity.getApplicationContext());
    }

    /* JADX WARN: Code duplicated, block: B:18:0x002e  */
    /* JADX WARN: Code duplicated, block: B:20:0x004c  */
    public void login(String str, String str2, String str3) {
        Account account;
        Account account2;
        Account[] accountsByType = this.mAccountManager.getAccountsByType(str);
        if (accountsByType.length == 0) {
            onLoginCancel();
            return;
        }
        int i = 0;
        if (TextUtils.isEmpty(str2)) {
            account = accountsByType[0];
        } else {
            int length = accountsByType.length;
            while (true) {
                if (i < length) {
                    Account account3 = accountsByType[i];
                    if (account3.name.equals(str2)) {
                        account2 = account3;
                        break;
                    }
                    i++;
                } else {
                    account = null;
                }
            }
            if (account2 != null) {
                onLoginStart();
                this.mAccountManager.getAuthToken(account2, "weblogin:" + str3, (Bundle) null, (Activity) null, this.mCallback, (Handler) null);
                return;
            }
            onLoginCancel();
        }
        account2 = account;
        if (account2 != null) {
            onLoginStart();
            this.mAccountManager.getAuthToken(account2, "weblogin:" + str3, (Bundle) null, (Activity) null, this.mCallback, (Handler) null);
            return;
        }
        onLoginCancel();
    }

    private static class LoginCallback implements AccountManagerCallback<Bundle> {
        private WeakReference<DeviceAccountLogin> mLoginRef;

        public LoginCallback(DeviceAccountLogin deviceAccountLogin) {
            this.mLoginRef = new WeakReference<>(deviceAccountLogin);
        }

        @Override // android.accounts.AccountManagerCallback
        public void run(AccountManagerFuture<Bundle> accountManagerFuture) {
            DeviceAccountLogin deviceAccountLogin = this.mLoginRef.get();
            if (deviceAccountLogin == null) {
                return;
            }
            try {
                String string = accountManagerFuture.getResult().getString("authtoken");
                if (string == null) {
                    deviceAccountLogin.onLoginFail();
                } else {
                    deviceAccountLogin.onLoginSuccess(string);
                }
            } catch (Exception e) {
                Log.e(DeviceAccountLogin.TAG, "Fail to login", e);
                deviceAccountLogin.onLoginFail();
            }
        }
    }
}
