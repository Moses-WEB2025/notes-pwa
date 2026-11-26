package com.xiaomi.accounts;

import android.accounts.Account;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorDescription;
import android.accounts.AuthenticatorException;
import android.accounts.OnAccountsUpdateListener;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.os.RemoteException;
import android.text.TextUtils;
import androidx.appfunctions.internal.AppSearchAppFunctionReader;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.onetrack.api.ah;
import com.xiaomi.passport.compat.ContextCompat;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes4.dex */
public class AccountManager {
    public static final String ACTION_AUTHENTICATOR_INTENT = "com.xiaomi.accounts.AccountAuthenticator";
    public static final int ERROR_CODE_BAD_ARGUMENTS = 7;
    public static final int ERROR_CODE_BAD_AUTHENTICATION = 9;
    public static final int ERROR_CODE_BAD_REQUEST = 8;
    public static final int ERROR_CODE_CANCELED = 4;
    public static final int ERROR_CODE_INVALID_RESPONSE = 5;
    public static final int ERROR_CODE_NETWORK_ERROR = 3;
    public static final int ERROR_CODE_REMOTE_EXCEPTION = 1;
    public static final int ERROR_CODE_UNSUPPORTED_OPERATION = 6;
    public static final String KEY_ACCOUNTS = "accounts";
    public static final String KEY_ACCOUNT_AUTHENTICATOR_RESPONSE = "accountAuthenticatorResponse";
    public static final String KEY_ACCOUNT_MANAGER_RESPONSE = "accountManagerResponse";
    public static final String KEY_ACCOUNT_NAME = "authAccount";
    public static final String KEY_ACCOUNT_TYPE = "accountType";
    public static final String KEY_ANDROID_PACKAGE_NAME = "androidPackageName";
    public static final String KEY_AUTHENTICATOR_TYPES = "authenticator_types";
    public static final String KEY_AUTHTOKEN = "authtoken";
    public static final String KEY_AUTH_FAILED_MESSAGE = "authFailedMessage";
    public static final String KEY_AUTH_FAILED_TITLE = "authFailedTitle";
    public static final String KEY_AUTH_TOKEN_LABEL = "authTokenLabelKey";
    public static final String KEY_BOOLEAN_RESULT = "booleanResult";
    public static final String KEY_CALLER_PID = "callerPid";
    public static final String KEY_CALLER_UID = "callerUid";
    public static final String KEY_ERROR_CODE = "errorCode";
    public static final String KEY_ERROR_MESSAGE = "errorMessage";
    public static final String KEY_INTENT = "intent";
    public static final String KEY_NOTIFY_ON_FAILURE = "notifyOnAuthFailure";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_USERDATA = "userdata";
    public static final String LOGIN_ACCOUNTS_CHANGED_ACTION = "com.xiaomi.accounts.LOGIN_ACCOUNTS_CHANGED";
    private static final String TAG = "AccountManager";
    private static volatile AccountManager sThis;
    private final Context mContext;
    private final Handler mMainHandler;
    private AccountManagerService mService;
    private final HashMap<OnAccountsUpdateListener, Handler> mAccountsUpdatedListeners = new HashMap<>();
    private final BroadcastReceiver mAccountsChangedBroadcastReceiver = new BroadcastReceiver() { // from class: com.xiaomi.accounts.AccountManager.13
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Account[] accounts = AccountManager.this.getAccounts();
            synchronized (AccountManager.this.mAccountsUpdatedListeners) {
                for (Map.Entry entry : AccountManager.this.mAccountsUpdatedListeners.entrySet()) {
                    AccountManager.this.postToHandler((Handler) entry.getValue(), (OnAccountsUpdateListener) entry.getKey(), accounts);
                }
            }
        }
    };

    private AccountManager(Context context) {
        this.mContext = context;
        this.mMainHandler = new Handler(context.getMainLooper());
        this.mService = new AccountManagerService(context);
    }

    public static Bundle sanitizeResult(Bundle bundle) {
        if (bundle == null || !bundle.containsKey("authtoken") || TextUtils.isEmpty(bundle.getString("authtoken"))) {
            return bundle;
        }
        Bundle bundle2 = new Bundle(bundle);
        bundle2.putString("authtoken", "<omitted for logging purposes>");
        return bundle2;
    }

    public static AccountManager get(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context is null");
        }
        if (sThis == null) {
            synchronized (AccountManager.class) {
                if (sThis == null) {
                    sThis = new AccountManager(context.getApplicationContext());
                }
            }
        }
        return sThis;
    }

    public static AccountManager newInstance(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context is null");
        }
        return new AccountManager(context.getApplicationContext());
    }

    public String getPassword(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        return this.mService.getPassword(account);
    }

    public String getUserData(Account account, String str) {
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        if (str == null) {
            throw new IllegalArgumentException("key is null");
        }
        return this.mService.getUserData(account, str);
    }

    public Map<String, String> getAllUserData(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        return this.mService.getAllUserData(account);
    }

    public AuthenticatorDescription[] getAuthenticatorTypes() {
        return this.mService.getAuthenticatorTypes();
    }

    public Account[] getAccounts() {
        return this.mService.getAccounts((String) null);
    }

    public Account[] getAccountsByType(String str) {
        return this.mService.getAccounts(str);
    }

    public void updateAppPermission(Account account, String str, int i, boolean z) {
        try {
            this.mService.updateAppPermission(account, str, i, z);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public AccountManagerFuture<String> getAuthTokenLabel(final String str, final String str2, AccountManagerCallback<String> accountManagerCallback, Handler handler) {
        if (str == null) {
            throw new IllegalArgumentException("accountType is null");
        }
        if (str2 == null) {
            throw new IllegalArgumentException("authTokenType is null");
        }
        return new Future2Task<String>(handler, accountManagerCallback) { // from class: com.xiaomi.accounts.AccountManager.1
            @Override // com.xiaomi.accounts.AccountManager.BaseFutureTask
            public void doWork() throws Throwable {
                AccountManager.this.mService.getAuthTokenLabel(this.mResponse, str, str2);
            }

            @Override // com.xiaomi.accounts.AccountManager.BaseFutureTask
            public String bundleToResult(Bundle bundle) throws AuthenticatorException {
                if (!bundle.containsKey("authTokenLabelKey")) {
                    throw new AuthenticatorException("no result in response");
                }
                return bundle.getString("authTokenLabelKey");
            }
        }.start();
    }

    public AccountManagerFuture<Boolean> hasFeatures(final Account account, final String[] strArr, AccountManagerCallback<Boolean> accountManagerCallback, Handler handler) {
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        if (strArr == null) {
            throw new IllegalArgumentException("features is null");
        }
        return new Future2Task<Boolean>(handler, accountManagerCallback) { // from class: com.xiaomi.accounts.AccountManager.2
            @Override // com.xiaomi.accounts.AccountManager.BaseFutureTask
            public void doWork() throws Throwable {
                AccountManager.this.mService.hasFeatures(this.mResponse, account, strArr);
            }

            @Override // com.xiaomi.accounts.AccountManager.BaseFutureTask
            public Boolean bundleToResult(Bundle bundle) throws AuthenticatorException {
                if (!bundle.containsKey("booleanResult")) {
                    throw new AuthenticatorException("no result in response");
                }
                return Boolean.valueOf(bundle.getBoolean("booleanResult"));
            }
        }.start();
    }

    public AccountManagerFuture<Account[]> getAccountsByTypeAndFeatures(final String str, final String[] strArr, AccountManagerCallback<Account[]> accountManagerCallback, Handler handler) {
        if (str == null) {
            throw new IllegalArgumentException("type is null");
        }
        return new Future2Task<Account[]>(handler, accountManagerCallback) { // from class: com.xiaomi.accounts.AccountManager.3
            @Override // com.xiaomi.accounts.AccountManager.BaseFutureTask
            public void doWork() throws Throwable {
                AccountManager.this.mService.getAccountsByFeatures(this.mResponse, str, strArr);
            }

            @Override // com.xiaomi.accounts.AccountManager.BaseFutureTask
            public Account[] bundleToResult(Bundle bundle) throws AuthenticatorException {
                if (!bundle.containsKey("accounts")) {
                    throw new AuthenticatorException("no result in response");
                }
                Parcelable[] parcelableArray = bundle.getParcelableArray("accounts");
                Account[] accountArr = new Account[parcelableArray.length];
                for (int i = 0; i < parcelableArray.length; i++) {
                    accountArr[i] = (Account) parcelableArray[i];
                }
                return accountArr;
            }
        }.start();
    }

    public boolean addAccountExplicitly(Account account, String str, Bundle bundle) {
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        return this.mService.addAccount(account, str, bundle);
    }

    public AccountManagerFuture<Boolean> removeAccount(final Account account, AccountManagerCallback<Boolean> accountManagerCallback, Handler handler) {
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        return new Future2Task<Boolean>(handler, accountManagerCallback) { // from class: com.xiaomi.accounts.AccountManager.4
            @Override // com.xiaomi.accounts.AccountManager.BaseFutureTask
            public void doWork() throws RemoteException {
                AccountManager.this.mService.removeAccount(this.mResponse, account);
            }

            @Override // com.xiaomi.accounts.AccountManager.BaseFutureTask
            public Boolean bundleToResult(Bundle bundle) throws AuthenticatorException {
                if (!bundle.containsKey("booleanResult")) {
                    throw new AuthenticatorException("no result in response");
                }
                return Boolean.valueOf(bundle.getBoolean("booleanResult"));
            }
        }.start();
    }

    public void invalidateAuthToken(String str, String str2) {
        if (str == null) {
            throw new IllegalArgumentException("accountType is null");
        }
        if (str2 != null) {
            this.mService.invalidateAuthToken(str, str2);
        }
    }

    public String peekAuthToken(Account account, String str) {
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        if (str == null) {
            throw new IllegalArgumentException("authTokenType is null");
        }
        return this.mService.peekAuthToken(account, str);
    }

    public Map<String, String> peekAllAuthToken(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        return this.mService.peekAllAuthToken(account);
    }

    public void setPassword(Account account, String str) {
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        this.mService.setPassword(account, str);
    }

    public void clearPassword(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        this.mService.clearPassword(account);
    }

    public void setUserData(Account account, String str, String str2) {
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        if (str == null) {
            throw new IllegalArgumentException("key is null");
        }
        this.mService.setUserData(account, str, str2);
    }

    public void setAuthToken(Account account, String str, String str2) {
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        if (str == null) {
            throw new IllegalArgumentException("authTokenType is null");
        }
        this.mService.setAuthToken(account, str, str2);
    }

    public String blockingGetAuthToken(Account account, String str, boolean z) throws OperationCanceledException, IOException, AuthenticatorException {
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        if (str == null) {
            throw new IllegalArgumentException("authTokenType is null");
        }
        Bundle result = getAuthToken(account, str, z, null, null).getResult();
        if (result == null) {
            AccountLogger.log(TAG, "blockingGetAuthToken: null was returned from getResult() for " + account + ", authTokenType " + str);
            return null;
        }
        return result.getString("authtoken");
    }

    public AccountManagerFuture<Bundle> getAuthToken(final Account account, final String str, Bundle bundle, Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        if (str == null) {
            throw new IllegalArgumentException("authTokenType is null");
        }
        final Bundle bundle2 = new Bundle();
        if (bundle != null) {
            bundle2.putAll(bundle);
        }
        bundle2.putString(KEY_ANDROID_PACKAGE_NAME, this.mContext.getPackageName());
        return new AmsTask(activity, handler, accountManagerCallback) { // from class: com.xiaomi.accounts.AccountManager.5
            @Override // com.xiaomi.accounts.AccountManager.AmsTask
            public void doWork() throws RemoteException {
                AccountManager.this.mService.getAuthToken(this.mResponse, account, str, false, true, bundle2);
            }
        }.start();
    }

    @Deprecated
    public AccountManagerFuture<Bundle> getAuthToken(Account account, String str, boolean z, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        return getAuthToken(account, str, (Bundle) null, z, accountManagerCallback, handler);
    }

    public AccountManagerFuture<Bundle> getAuthToken(final Account account, final String str, Bundle bundle, final boolean z, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        if (str == null) {
            throw new IllegalArgumentException("authTokenType is null");
        }
        final Bundle bundle2 = new Bundle();
        if (bundle != null) {
            bundle2.putAll(bundle);
        }
        bundle2.putString(KEY_ANDROID_PACKAGE_NAME, this.mContext.getPackageName());
        return new AmsTask(null, handler, accountManagerCallback) { // from class: com.xiaomi.accounts.AccountManager.6
            @Override // com.xiaomi.accounts.AccountManager.AmsTask
            public void doWork() throws RemoteException {
                AccountManager.this.mService.getAuthToken(this.mResponse, account, str, z, false, bundle2);
            }
        }.start();
    }

    public AccountManagerFuture<Bundle> addAccount(final String str, final String str2, final String[] strArr, Bundle bundle, final Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        if (str == null) {
            throw new IllegalArgumentException("accountType is null");
        }
        final Bundle bundle2 = new Bundle();
        if (bundle != null) {
            bundle2.putAll(bundle);
        }
        bundle2.putString(KEY_ANDROID_PACKAGE_NAME, this.mContext.getPackageName());
        return new AmsTask(activity, handler, accountManagerCallback) { // from class: com.xiaomi.accounts.AccountManager.7
            @Override // com.xiaomi.accounts.AccountManager.AmsTask
            public void doWork() throws RemoteException {
                AccountManager.this.mService.addAcount(this.mResponse, str, str2, strArr, activity != null, bundle2);
            }
        }.start();
    }

    public AccountManagerFuture<Bundle> confirmCredentials(final Account account, final Bundle bundle, final Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        return new AmsTask(activity, handler, accountManagerCallback) { // from class: com.xiaomi.accounts.AccountManager.8
            @Override // com.xiaomi.accounts.AccountManager.AmsTask
            public void doWork() throws RemoteException {
                AccountManager.this.mService.confirmCredentials(this.mResponse, account, bundle, activity != null);
            }
        }.start();
    }

    public AccountManagerFuture<Bundle> updateCredentials(final Account account, final String str, final Bundle bundle, final Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        return new AmsTask(activity, handler, accountManagerCallback) { // from class: com.xiaomi.accounts.AccountManager.9
            @Override // com.xiaomi.accounts.AccountManager.AmsTask
            public void doWork() throws RemoteException {
                AccountManager.this.mService.updateCredentials(this.mResponse, account, str, activity != null, bundle);
            }
        }.start();
    }

    public AccountManagerFuture<Bundle> editProperties(final String str, final Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        if (str == null) {
            throw new IllegalArgumentException("accountType is null");
        }
        return new AmsTask(activity, handler, accountManagerCallback) { // from class: com.xiaomi.accounts.AccountManager.10
            @Override // com.xiaomi.accounts.AccountManager.AmsTask
            public void doWork() throws Throwable {
                AccountManager.this.mService.editProperties(this.mResponse, str, activity != null);
            }
        }.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ensureNotOnMainThread() {
        Looper looperMyLooper = Looper.myLooper();
        if (looperMyLooper == null || looperMyLooper != this.mContext.getMainLooper()) {
            return;
        }
        IllegalStateException illegalStateException = new IllegalStateException("calling this from your main thread can lead to deadlock");
        AccountLogger.log(TAG, "calling this from your main thread can lead to deadlock and/or ANRs", illegalStateException);
        if (this.mContext.getApplicationInfo().targetSdkVersion >= 8) {
            throw illegalStateException;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postToHandler(Handler handler, final AccountManagerCallback<Bundle> accountManagerCallback, final AccountManagerFuture<Bundle> accountManagerFuture) {
        if (handler == null) {
            handler = this.mMainHandler;
        }
        handler.post(new Runnable() { // from class: com.xiaomi.accounts.AccountManager.11
            @Override // java.lang.Runnable
            public void run() {
                accountManagerCallback.run(accountManagerFuture);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postToHandler(Handler handler, final OnAccountsUpdateListener onAccountsUpdateListener, Account[] accountArr) {
        int length = accountArr.length;
        final Account[] accountArr2 = new Account[length];
        System.arraycopy(accountArr, 0, accountArr2, 0, length);
        if (handler == null) {
            handler = this.mMainHandler;
        }
        handler.post(new Runnable() { // from class: com.xiaomi.accounts.AccountManager.12
            @Override // java.lang.Runnable
            public void run() {
                try {
                    onAccountsUpdateListener.onAccountsUpdated(accountArr2);
                } catch (SQLException e) {
                    AccountLogger.log(AccountManager.TAG, "Can't update accounts", e);
                }
            }
        });
    }

    private abstract class AmsTask extends FutureTask<Bundle> implements AccountManagerFuture<Bundle> {
        final WeakReference<Activity> mActivity;
        final AccountManagerCallback<Bundle> mCallback;
        final Handler mHandler;
        final IAccountManagerResponse mResponse;

        public abstract void doWork() throws RemoteException;

        public AmsTask(Activity activity, Handler handler, AccountManagerCallback<Bundle> accountManagerCallback) {
            super(new Callable<Bundle>() { // from class: com.xiaomi.accounts.AccountManager.AmsTask.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // java.util.concurrent.Callable
                public Bundle call() throws Exception {
                    throw new IllegalStateException("this should never be called");
                }
            });
            this.mHandler = handler;
            this.mCallback = accountManagerCallback;
            this.mActivity = new WeakReference<>(activity);
            this.mResponse = new Response();
        }

        public final AccountManagerFuture<Bundle> start() {
            try {
                doWork();
                return this;
            } catch (RemoteException e) {
                setException(e);
                return this;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.util.concurrent.FutureTask
        public void set(Bundle bundle) {
            if (bundle == null) {
                AccountLogger.log(AccountManager.TAG, "the bundle must not be null", new Exception());
            }
            super.set(bundle);
        }

        private Bundle internalGetResult(Long l, TimeUnit timeUnit) throws OperationCanceledException, IOException, AuthenticatorException {
            if (!isDone()) {
                AccountManager.this.ensureNotOnMainThread();
            }
            try {
                try {
                    try {
                        if (l == null) {
                            Bundle bundle = get();
                            cancel(true);
                            return bundle;
                        }
                        Bundle bundle2 = get(l.longValue(), timeUnit);
                        cancel(true);
                        return bundle2;
                    } catch (CancellationException unused) {
                        throw new OperationCanceledException();
                    } catch (ExecutionException e) {
                        Throwable cause = e.getCause();
                        if (cause instanceof IOException) {
                            throw ((IOException) cause);
                        }
                        if (cause instanceof UnsupportedOperationException) {
                            throw new AuthenticatorException(cause);
                        }
                        if (cause instanceof AuthenticatorException) {
                            throw ((AuthenticatorException) cause);
                        }
                        if (cause instanceof RuntimeException) {
                            throw ((RuntimeException) cause);
                        }
                        if (cause instanceof Error) {
                            throw ((Error) cause);
                        }
                        throw new IllegalStateException(cause);
                    }
                } catch (InterruptedException | TimeoutException unused2) {
                    cancel(true);
                    throw new OperationCanceledException();
                }
            } catch (Throwable th) {
                cancel(true);
                throw th;
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.accounts.AccountManagerFuture
        public Bundle getResult() throws OperationCanceledException, IOException, AuthenticatorException {
            return internalGetResult(null, null);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.accounts.AccountManagerFuture
        public Bundle getResult(long j, TimeUnit timeUnit) throws OperationCanceledException, IOException, AuthenticatorException {
            return internalGetResult(Long.valueOf(j), timeUnit);
        }

        @Override // java.util.concurrent.FutureTask
        protected void done() {
            AccountManagerCallback<Bundle> accountManagerCallback = this.mCallback;
            if (accountManagerCallback != null) {
                AccountManager.this.postToHandler(this.mHandler, accountManagerCallback, this);
            }
        }

        private class Response extends IAccountManagerResponse.Stub {
            @Override // com.xiaomi.accounts.IAccountManagerResponse
            public void onRequestContinued() throws RemoteException {
            }

            private Response() {
            }

            @Override // com.xiaomi.accounts.IAccountManagerResponse
            public void onResult(Bundle bundle) {
                Intent intent = (Intent) bundle.getParcelable("intent");
                if (intent != null && AmsTask.this.mActivity.get() != null) {
                    AmsTask.this.mActivity.get().startActivity(intent);
                } else if (bundle.getBoolean(ah.L)) {
                    try {
                        AmsTask.this.doWork();
                    } catch (RemoteException unused) {
                    }
                } else {
                    AmsTask.this.set(bundle);
                }
            }

            @Override // com.xiaomi.accounts.IAccountManagerResponse
            public void onError(int i, String str) {
                if (i == 4) {
                    AmsTask.this.cancel(true);
                } else {
                    AmsTask amsTask = AmsTask.this;
                    amsTask.setException(AccountManager.this.convertErrorToException(i, str));
                }
            }
        }
    }

    private abstract class BaseFutureTask<T> extends FutureTask<T> {
        final Handler mHandler;
        public final IAccountManagerResponse mResponse;

        public abstract T bundleToResult(Bundle bundle) throws AuthenticatorException;

        public abstract void doWork() throws RemoteException;

        public BaseFutureTask(Handler handler) {
            super(new Callable<T>() { // from class: com.xiaomi.accounts.AccountManager.BaseFutureTask.1
                @Override // java.util.concurrent.Callable
                public T call() throws Exception {
                    throw new IllegalStateException("this should never be called");
                }
            });
            this.mHandler = handler;
            this.mResponse = new Response();
        }

        protected void postRunnableToHandler(Runnable runnable) {
            Handler handler = this.mHandler;
            if (handler == null) {
                handler = AccountManager.this.mMainHandler;
            }
            handler.post(runnable);
        }

        protected void startTask() {
            try {
                doWork();
            } catch (RemoteException e) {
                setException(e);
            }
        }

        protected class Response extends IAccountManagerResponse.Stub {
            @Override // com.xiaomi.accounts.IAccountManagerResponse
            public void onRequestContinued() throws RemoteException {
            }

            protected Response() {
            }

            @Override // com.xiaomi.accounts.IAccountManagerResponse
            public void onResult(Bundle bundle) {
                try {
                    Object objBundleToResult = BaseFutureTask.this.bundleToResult(bundle);
                    if (objBundleToResult == null) {
                        return;
                    }
                    BaseFutureTask.this.set(objBundleToResult);
                } catch (AuthenticatorException | ClassCastException unused) {
                    onError(5, "no result in response");
                }
            }

            @Override // com.xiaomi.accounts.IAccountManagerResponse
            public void onError(int i, String str) {
                if (i == 4) {
                    BaseFutureTask.this.cancel(true);
                } else {
                    BaseFutureTask baseFutureTask = BaseFutureTask.this;
                    baseFutureTask.setException(AccountManager.this.convertErrorToException(i, str));
                }
            }
        }
    }

    private abstract class Future2Task<T> extends BaseFutureTask<T> implements AccountManagerFuture<T> {
        final AccountManagerCallback<T> mCallback;

        public Future2Task(Handler handler, AccountManagerCallback<T> accountManagerCallback) {
            super(handler);
            this.mCallback = accountManagerCallback;
        }

        @Override // java.util.concurrent.FutureTask
        protected void done() {
            if (this.mCallback != null) {
                postRunnableToHandler(new Runnable() { // from class: com.xiaomi.accounts.AccountManager.Future2Task.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Future2Task.this.mCallback.run(Future2Task.this);
                    }
                });
            }
        }

        public Future2Task<T> start() {
            startTask();
            return this;
        }

        private T internalGetResult(Long l, TimeUnit timeUnit) throws OperationCanceledException, IOException, AuthenticatorException {
            if (!isDone()) {
                AccountManager.this.ensureNotOnMainThread();
            }
            try {
                try {
                    try {
                        if (l == null) {
                            T t = (T) get();
                            cancel(true);
                            return t;
                        }
                        T t2 = (T) get(l.longValue(), timeUnit);
                        cancel(true);
                        return t2;
                    } catch (ExecutionException e) {
                        Throwable cause = e.getCause();
                        if (cause instanceof IOException) {
                            throw ((IOException) cause);
                        }
                        if (cause instanceof UnsupportedOperationException) {
                            throw new AuthenticatorException(cause);
                        }
                        if (cause instanceof AuthenticatorException) {
                            throw ((AuthenticatorException) cause);
                        }
                        if (cause instanceof RuntimeException) {
                            throw ((RuntimeException) cause);
                        }
                        if (cause instanceof Error) {
                            throw ((Error) cause);
                        }
                        throw new IllegalStateException(cause);
                    }
                } catch (Throwable th) {
                    cancel(true);
                    throw th;
                }
            } catch (InterruptedException | CancellationException | TimeoutException unused) {
                cancel(true);
                throw new OperationCanceledException();
            }
        }

        @Override // android.accounts.AccountManagerFuture
        public T getResult() throws OperationCanceledException, IOException, AuthenticatorException {
            return internalGetResult(null, null);
        }

        @Override // android.accounts.AccountManagerFuture
        public T getResult(long j, TimeUnit timeUnit) throws OperationCanceledException, IOException, AuthenticatorException {
            return internalGetResult(Long.valueOf(j), timeUnit);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Exception convertErrorToException(int i, String str) {
        if (i == 3) {
            return new IOException(str);
        }
        if (i == 6) {
            return new UnsupportedOperationException(str);
        }
        if (i == 5) {
            return new AuthenticatorException(str);
        }
        if (i == 7) {
            return new IllegalArgumentException(str);
        }
        return new AuthenticatorException(str);
    }

    private class GetAuthTokenByTypeAndFeaturesTask extends AmsTask implements AccountManagerCallback<Bundle> {
        final String mAccountType;
        final Bundle mAddAccountOptions;
        final String mAuthTokenType;
        final String[] mFeatures;
        volatile AccountManagerFuture<Bundle> mFuture;
        final Bundle mLoginOptions;
        final AccountManagerCallback<Bundle> mMyCallback;
        private volatile int mNumAccounts;

        GetAuthTokenByTypeAndFeaturesTask(String str, String str2, String[] strArr, Activity activity, Bundle bundle, Bundle bundle2, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
            super(activity, handler, accountManagerCallback);
            this.mFuture = null;
            this.mNumAccounts = 0;
            if (str == null) {
                throw new IllegalArgumentException("account type is null");
            }
            this.mAccountType = str;
            this.mAuthTokenType = str2;
            this.mFeatures = strArr;
            this.mAddAccountOptions = bundle;
            this.mLoginOptions = bundle2;
            this.mMyCallback = this;
        }

        @Override // com.xiaomi.accounts.AccountManager.AmsTask
        public void doWork() throws RemoteException {
            AccountManager.this.getAccountsByTypeAndFeatures(this.mAccountType, this.mFeatures, new AccountManagerCallback<Account[]>() { // from class: com.xiaomi.accounts.AccountManager.GetAuthTokenByTypeAndFeaturesTask.1
                @Override // android.accounts.AccountManagerCallback
                public void run(AccountManagerFuture<Account[]> accountManagerFuture) {
                    try {
                        Account[] result = accountManagerFuture.getResult();
                        GetAuthTokenByTypeAndFeaturesTask.this.mNumAccounts = result.length;
                        try {
                            if (result.length == 0) {
                                if (GetAuthTokenByTypeAndFeaturesTask.this.mActivity.get() != null) {
                                    GetAuthTokenByTypeAndFeaturesTask getAuthTokenByTypeAndFeaturesTask = GetAuthTokenByTypeAndFeaturesTask.this;
                                    getAuthTokenByTypeAndFeaturesTask.mFuture = AccountManager.this.addAccount(GetAuthTokenByTypeAndFeaturesTask.this.mAccountType, GetAuthTokenByTypeAndFeaturesTask.this.mAuthTokenType, GetAuthTokenByTypeAndFeaturesTask.this.mFeatures, GetAuthTokenByTypeAndFeaturesTask.this.mAddAccountOptions, GetAuthTokenByTypeAndFeaturesTask.this.mActivity.get(), GetAuthTokenByTypeAndFeaturesTask.this.mMyCallback, GetAuthTokenByTypeAndFeaturesTask.this.mHandler);
                                } else {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("authAccount", null);
                                    bundle.putString("accountType", null);
                                    bundle.putString("authtoken", null);
                                    GetAuthTokenByTypeAndFeaturesTask.this.mResponse.onResult(bundle);
                                }
                            } else {
                                if (result.length == 1) {
                                    if (GetAuthTokenByTypeAndFeaturesTask.this.mActivity == null) {
                                        GetAuthTokenByTypeAndFeaturesTask getAuthTokenByTypeAndFeaturesTask2 = GetAuthTokenByTypeAndFeaturesTask.this;
                                        getAuthTokenByTypeAndFeaturesTask2.mFuture = AccountManager.this.getAuthToken(result[0], GetAuthTokenByTypeAndFeaturesTask.this.mAuthTokenType, false, GetAuthTokenByTypeAndFeaturesTask.this.mMyCallback, GetAuthTokenByTypeAndFeaturesTask.this.mHandler);
                                        return;
                                    } else {
                                        GetAuthTokenByTypeAndFeaturesTask getAuthTokenByTypeAndFeaturesTask3 = GetAuthTokenByTypeAndFeaturesTask.this;
                                        getAuthTokenByTypeAndFeaturesTask3.mFuture = AccountManager.this.getAuthToken(result[0], GetAuthTokenByTypeAndFeaturesTask.this.mAuthTokenType, GetAuthTokenByTypeAndFeaturesTask.this.mLoginOptions, GetAuthTokenByTypeAndFeaturesTask.this.mActivity.get(), GetAuthTokenByTypeAndFeaturesTask.this.mMyCallback, GetAuthTokenByTypeAndFeaturesTask.this.mHandler);
                                        return;
                                    }
                                }
                                if (GetAuthTokenByTypeAndFeaturesTask.this.mActivity != null) {
                                    IAccountManagerResponse.Stub stub = new IAccountManagerResponse.Stub() { // from class: com.xiaomi.accounts.AccountManager.GetAuthTokenByTypeAndFeaturesTask.1.1
                                        @Override // com.xiaomi.accounts.IAccountManagerResponse
                                        public void onRequestContinued() throws RemoteException {
                                        }

                                        @Override // com.xiaomi.accounts.IAccountManagerResponse
                                        public void onResult(Bundle bundle2) throws RemoteException {
                                            Account account = new Account(bundle2.getString("authAccount"), bundle2.getString("accountType"));
                                            GetAuthTokenByTypeAndFeaturesTask.this.mFuture = AccountManager.this.getAuthToken(account, GetAuthTokenByTypeAndFeaturesTask.this.mAuthTokenType, GetAuthTokenByTypeAndFeaturesTask.this.mLoginOptions, GetAuthTokenByTypeAndFeaturesTask.this.mActivity.get(), GetAuthTokenByTypeAndFeaturesTask.this.mMyCallback, GetAuthTokenByTypeAndFeaturesTask.this.mHandler);
                                        }

                                        @Override // com.xiaomi.accounts.IAccountManagerResponse
                                        public void onError(int i, String str) throws RemoteException {
                                            GetAuthTokenByTypeAndFeaturesTask.this.mResponse.onError(i, str);
                                        }
                                    };
                                    Intent intent = new Intent();
                                    intent.setClassName(AppSearchAppFunctionReader.SYSTEM_PACKAGE_NAME, "android.accounts.ChooseAccountActivity");
                                    intent.putExtra("accounts", result);
                                    intent.putExtra("accountManagerResponse", new AccountManagerResponse(stub));
                                    GetAuthTokenByTypeAndFeaturesTask.this.mActivity.get().startActivity(intent);
                                    return;
                                }
                                Bundle bundle2 = new Bundle();
                                bundle2.putString("accounts", null);
                                GetAuthTokenByTypeAndFeaturesTask.this.mResponse.onResult(bundle2);
                            }
                        } catch (RemoteException unused) {
                        }
                    } catch (AuthenticatorException e) {
                        GetAuthTokenByTypeAndFeaturesTask.this.setException(e);
                    } catch (OperationCanceledException e2) {
                        GetAuthTokenByTypeAndFeaturesTask.this.setException(e2);
                    } catch (IOException e3) {
                        GetAuthTokenByTypeAndFeaturesTask.this.setException(e3);
                    }
                }
            }, this.mHandler);
        }

        @Override // android.accounts.AccountManagerCallback
        public void run(AccountManagerFuture<Bundle> accountManagerFuture) {
            try {
                Bundle result = accountManagerFuture.getResult();
                if (this.mNumAccounts == 0) {
                    String string = result.getString("authAccount");
                    String string2 = result.getString("accountType");
                    if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2)) {
                        Account account = new Account(string, string2);
                        this.mNumAccounts = 1;
                        AccountManager.this.getAuthToken(account, this.mAuthTokenType, (Bundle) null, this.mActivity.get(), this.mMyCallback, this.mHandler);
                        return;
                    }
                    setException(new AuthenticatorException("account not in result"));
                    return;
                }
                set(result);
            } catch (AuthenticatorException e) {
                setException(e);
            } catch (OperationCanceledException unused) {
                cancel(true);
            } catch (IOException e2) {
                setException(e2);
            }
        }
    }

    public AccountManagerFuture<Bundle> getAuthTokenByFeatures(String str, String str2, String[] strArr, Activity activity, Bundle bundle, Bundle bundle2, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        if (str == null) {
            throw new IllegalArgumentException("account type is null");
        }
        if (str2 == null) {
            throw new IllegalArgumentException("authTokenType is null");
        }
        GetAuthTokenByTypeAndFeaturesTask getAuthTokenByTypeAndFeaturesTask = new GetAuthTokenByTypeAndFeaturesTask(str, str2, strArr, activity, bundle, bundle2, accountManagerCallback, handler);
        getAuthTokenByTypeAndFeaturesTask.start();
        return getAuthTokenByTypeAndFeaturesTask;
    }

    public void addOnAccountsUpdatedListener(OnAccountsUpdateListener onAccountsUpdateListener, Handler handler, boolean z) {
        if (onAccountsUpdateListener == null) {
            throw new IllegalArgumentException("the listener is null");
        }
        synchronized (this.mAccountsUpdatedListeners) {
            if (this.mAccountsUpdatedListeners.containsKey(onAccountsUpdateListener)) {
                throw new IllegalStateException("this listener is already added");
            }
            boolean zIsEmpty = this.mAccountsUpdatedListeners.isEmpty();
            this.mAccountsUpdatedListeners.put(onAccountsUpdateListener, handler);
            if (zIsEmpty) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(LOGIN_ACCOUNTS_CHANGED_ACTION);
                intentFilter.addAction("android.intent.action.DEVICE_STORAGE_OK");
                ContextCompat.registerReceiver(this.mContext, this.mAccountsChangedBroadcastReceiver, intentFilter, true);
            }
        }
        if (z) {
            postToHandler(handler, onAccountsUpdateListener, getAccounts());
        }
    }

    public void removeOnAccountsUpdatedListener(OnAccountsUpdateListener onAccountsUpdateListener) {
        if (onAccountsUpdateListener == null) {
            throw new IllegalArgumentException("listener is null");
        }
        synchronized (this.mAccountsUpdatedListeners) {
            if (!this.mAccountsUpdatedListeners.containsKey(onAccountsUpdateListener)) {
                AccountLogger.log(TAG, "Listener was not previously added");
                return;
            }
            this.mAccountsUpdatedListeners.remove(onAccountsUpdateListener);
            if (this.mAccountsUpdatedListeners.isEmpty()) {
                this.mContext.unregisterReceiver(this.mAccountsChangedBroadcastReceiver);
            }
        }
    }

    public void release() {
        AccountManagerService accountManagerService = this.mService;
        if (accountManagerService != null) {
            accountManagerService.release();
        }
    }
}
