package micloud.compat.independent.request;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.micloudsdk.utils.IXiaomiAccountServiceProxy;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import miui.accounts.ExtraAccountManager;
import miui.cloud.os.SystemProperties;

/* JADX INFO: loaded from: classes5.dex */
public class RequestEnvBuilderCompatInternal implements IRequestEnvBuilderCompat {
    private static final String AUTHENTICATOR_EXCEPTION_INVALID_TOKEN_MSG = "invalid intent in bundle returned";
    private static final long CHECK_AUTH_TOKEN_RESULT_MAX_INTERVAL = 300;
    private static final long CHECK_AUTH_TOKEN_RESULT_MIN_INTERVAL = 1;
    private static final long GET_AUTH_TOKEN_TIMEOUT = 30000;
    private static final int MAX_RETRY_TIME = 3;
    private static final int[] RETRY_INTERVALS = {5000, 10000};
    private static final String SERVICE_MICLOUD = "micloud";
    private static final String TAG = "RequestEvnCompat_V18";
    private ThreadLocal<String> mExtendedAuthToken = new ThreadLocal<>();
    private String mUserAgent;

    @Override // micloud.compat.independent.request.IRequestEnvBuilderCompat
    public IRequestEnvBuilderCompat.RequestEnv build() {
        return new IRequestEnvBuilderCompat.RequestEnv() { // from class: micloud.compat.independent.request.RequestEnvBuilderCompatInternal.1
            @Override // micloud.compat.independent.request.IRequestEnvBuilderCompat.RequestEnv
            public long getAutoRetryInterval() {
                return 0L;
            }

            @Override // micloud.compat.independent.request.IRequestEnvBuilderCompat.RequestEnv
            public int getMaxRetryCount() {
                return 0;
            }

            @Override // micloud.compat.independent.request.IRequestEnvBuilderCompat.RequestEnv
            public boolean shouldUpdateHost() {
                return true;
            }

            @Override // micloud.compat.independent.request.IRequestEnvBuilderCompat.RequestEnv
            public String queryAuthToken(Context context) throws QueryAuthTokenException {
                QueryAuthTokenException queryAuthTokenException;
                QueryAuthTokenException queryAuthTokenException2;
                if (RequestEnvBuilderCompatInternal.this.mExtendedAuthToken.get() != null) {
                    return (String) RequestEnvBuilderCompatInternal.this.mExtendedAuthToken.get();
                }
                String str = null;
                QueryAuthTokenException queryAuthTokenException3 = null;
                boolean z = false;
                for (int i = 0; i < 3; i++) {
                    try {
                        Account systemAccount = getSystemAccount(context);
                        if (systemAccount != null) {
                            Bundle bundleWaitGetAuthToken = RequestEnvBuilderCompatInternal.waitGetAuthToken(AccountManager.get(context).getAuthToken(systemAccount, "micloud", true, null, null));
                            String string = bundleWaitGetAuthToken.getString("authtoken");
                            if (!TextUtils.isEmpty(string)) {
                                str = string;
                                break;
                            }
                            int i2 = bundleWaitGetAuthToken.getInt("errorCode", -1);
                            String string2 = bundleWaitGetAuthToken.getString("errorMessage");
                            Intent intent = (Intent) bundleWaitGetAuthToken.getParcelable("intent");
                            if (intent == null) {
                                queryAuthTokenException2 = new QueryAuthTokenException(i2, string2, false);
                            } else {
                                queryAuthTokenException2 = new QueryAuthTokenException(i2, string2, intent);
                            }
                            Log.e(RequestEnvBuilderCompatInternal.TAG, "empty token: " + queryAuthTokenException2);
                            queryAuthTokenException3 = queryAuthTokenException2;
                            break;
                        }
                        queryAuthTokenException3 = new QueryAuthTokenException("Account is null when getting service token");
                        Log.e(RequestEnvBuilderCompatInternal.TAG, queryAuthTokenException3.toString());
                        break;
                    } catch (AuthenticatorException e) {
                        String message = e.getMessage();
                        if (message != null && message.contains(RequestEnvBuilderCompatInternal.AUTHENTICATOR_EXCEPTION_INVALID_TOKEN_MSG)) {
                            queryAuthTokenException2 = new QueryAuthTokenException("AuthenticatorException when getting service token(isInvalidateAuthToken=" + z + ")", (Throwable) e, true);
                        } else {
                            queryAuthTokenException2 = new QueryAuthTokenException("AuthenticatorException when getting service token(isInvalidateAuthToken=" + z + ")", e);
                        }
                        Log.e(RequestEnvBuilderCompatInternal.TAG, queryAuthTokenException2.toString(), e);
                        if (!z) {
                            invalidateAuthToken(context);
                            queryAuthTokenException3 = queryAuthTokenException2;
                            z = true;
                        }
                    } catch (OperationCanceledException e2) {
                        queryAuthTokenException3 = new QueryAuthTokenException("OperationCanceledException when getting service token", e2);
                        Log.e(RequestEnvBuilderCompatInternal.TAG, queryAuthTokenException3.toString(), e2);
                    } catch (IOException e3) {
                        queryAuthTokenException = new QueryAuthTokenException("IOException when getting service token", e3);
                        Log.e(RequestEnvBuilderCompatInternal.TAG, queryAuthTokenException.toString(), e3);
                        if (i < 2) {
                            try {
                                Thread.sleep(RequestEnvBuilderCompatInternal.RETRY_INTERVALS[i]);
                            } catch (InterruptedException e4) {
                                Thread.currentThread().interrupt();
                                queryAuthTokenException = new QueryAuthTokenException("InterruptedException when sleep", e4);
                                Log.e(RequestEnvBuilderCompatInternal.TAG, queryAuthTokenException.toString(), e4);
                            }
                        }
                        queryAuthTokenException3 = queryAuthTokenException;
                    } catch (InterruptedException e5) {
                        Thread.currentThread().interrupt();
                        queryAuthTokenException3 = new QueryAuthTokenException("InterruptedException when getting service token", e5);
                        Log.e(RequestEnvBuilderCompatInternal.TAG, queryAuthTokenException3.toString(), e5);
                    } catch (TimeoutException e6) {
                        queryAuthTokenException = new QueryAuthTokenException("TimeoutException when getting service token, retry if needed", e6);
                        Log.e(RequestEnvBuilderCompatInternal.TAG, queryAuthTokenException.toString(), e6);
                        queryAuthTokenException3 = queryAuthTokenException;
                    }
                }
                if (str != null) {
                    RequestEnvBuilderCompatInternal.this.mExtendedAuthToken.set(str);
                    return (String) RequestEnvBuilderCompatInternal.this.mExtendedAuthToken.get();
                }
                if (queryAuthTokenException3 == null) {
                    QueryAuthTokenException queryAuthTokenException4 = new QueryAuthTokenException("AccountManager getAuthToken returns null");
                    Log.e(RequestEnvBuilderCompatInternal.TAG, queryAuthTokenException4.toString());
                    throw queryAuthTokenException4;
                }
                throw queryAuthTokenException3;
            }

            @Override // micloud.compat.independent.request.IRequestEnvBuilderCompat.RequestEnv
            public void invalidateAuthToken(Context context) {
                if (RequestEnvBuilderCompatInternal.this.mExtendedAuthToken.get() != null) {
                    Log.d(RequestEnvBuilderCompatInternal.TAG, "invalidateAutoToken");
                    AccountManager.get(context).invalidateAuthToken("com.xiaomi", (String) RequestEnvBuilderCompatInternal.this.mExtendedAuthToken.get());
                    RequestEnvBuilderCompatInternal.this.mExtendedAuthToken.set(null);
                }
            }

            @Override // micloud.compat.independent.request.IRequestEnvBuilderCompat.RequestEnv
            public Account getSystemAccount(Context context) {
                Account xiaomiAccount = ExtraAccountManager.getXiaomiAccount(context);
                if (xiaomiAccount != null) {
                    return xiaomiAccount;
                }
                Log.e(RequestEnvBuilderCompatInternal.TAG, "no account in system");
                return null;
            }

            @Override // micloud.compat.independent.request.IRequestEnvBuilderCompat.RequestEnv
            public String getEncryptedUserId(Context context, IBinder iBinder, Account account) throws RemoteException {
                return IXiaomiAccountServiceProxy.getEncryptedUserId(iBinder, account);
            }

            @Override // micloud.compat.independent.request.IRequestEnvBuilderCompat.RequestEnv
            public synchronized String getUserAgent() {
                if (RequestEnvBuilderCompatInternal.this.mUserAgent == null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(Build.MODEL).append("; MIUI/").append(Build.VERSION.INCREMENTAL);
                    String str = SystemProperties.get("ro.product.marketname");
                    if (!TextUtils.isEmpty(str)) {
                        sb.append("; ").append(str);
                    }
                    try {
                        try {
                            try {
                                if (((Boolean) Class.forName("miui.os.Build").getField("IS_ALPHA_BUILD").get(null)).booleanValue()) {
                                    sb.append(' ').append("ALPHA");
                                }
                            } catch (IllegalAccessException unused) {
                                Log.d(RequestEnvBuilderCompatInternal.TAG, "Not in MIUI in getUserAgent");
                            } catch (NoSuchFieldException unused2) {
                                Log.d(RequestEnvBuilderCompatInternal.TAG, "Not in MIUI in getUserAgent");
                            }
                        } catch (IllegalArgumentException unused3) {
                            Log.d(RequestEnvBuilderCompatInternal.TAG, "Not in MIUI in getUserAgent");
                        }
                    } catch (ClassNotFoundException unused4) {
                        Log.d(RequestEnvBuilderCompatInternal.TAG, "Not in MIUI in getUserAgent");
                    }
                    RequestEnvBuilderCompatInternal.this.mUserAgent = sb.toString();
                }
                return RequestEnvBuilderCompatInternal.this.mUserAgent;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Bundle waitGetAuthToken(AccountManagerFuture<Bundle> accountManagerFuture) throws InterruptedException, TimeoutException, OperationCanceledException, IOException, AuthenticatorException {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        long jMin = 1;
        while (SystemClock.elapsedRealtime() - jElapsedRealtime < 30000) {
            if (accountManagerFuture.isDone()) {
                return accountManagerFuture.getResult();
            }
            Thread.sleep(jMin);
            jMin = Math.min(jMin * 2, 300L);
        }
        throw new TimeoutException();
    }
}
