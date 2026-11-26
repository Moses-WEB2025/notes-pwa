package com.xiaomi.passport.ui.internal;

import android.os.AsyncTask;
import com.xiaomi.accountsdk.account.data.NotificationAuthResult;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.SimpleRequest;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.accountsdk.utils.AccountLogger;
import java.io.IOException;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class NotificationAuthTask extends AsyncTask<Void, Void, NotificationAuthResult> {
    private static final String TAG = "NotificationAuthTask";
    private final NotificationAuthUICallback mCallback;
    private final String mStsUrl;

    public interface NotificationAuthUICallback {
        void call(NotificationAuthResult notificationAuthResult);
    }

    NotificationAuthTask(String str, NotificationAuthUICallback notificationAuthUICallback) {
        this.mStsUrl = str;
        this.mCallback = notificationAuthUICallback;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public NotificationAuthResult doInBackground(Void... voidArr) {
        return doRequest();
    }

    private NotificationAuthResult doRequest() {
        Map<String, String> headers;
        try {
            SimpleRequest.StringContent asString = SimpleRequestForAccount.getAsString(this.mStsUrl, null, null, false);
            if (asString != null && (headers = asString.getHeaders()) != null) {
                return new NotificationAuthResult.Builder().setUserId(headers.get("userId")).setServiceToken(headers.get("serviceToken")).setPSecurity_ph(headers.get("passportsecurity_ph")).setPSecurity_slh(headers.get("passportsecurity_slh")).build();
            }
        } catch (AccessDeniedException e) {
            AccountLogger.log(TAG, "access denied", e);
        } catch (AuthenticationFailureException e2) {
            AccountLogger.log(TAG, "auth error", e2);
        } catch (IOException e3) {
            AccountLogger.log(TAG, "network error", e3);
        } catch (RuntimeException e4) {
            letCrashAsync(e4);
            AccountLogger.log(TAG, "runtime exception", e4);
            return null;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(NotificationAuthResult notificationAuthResult) {
        super.onPostExecute(notificationAuthResult);
        this.mCallback.call(notificationAuthResult);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.xiaomi.passport.ui.internal.NotificationAuthTask$1] */
    private void letCrashAsync(final RuntimeException runtimeException) {
        new Thread() { // from class: com.xiaomi.passport.ui.internal.NotificationAuthTask.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                throw runtimeException;
            }
        }.start();
    }
}
