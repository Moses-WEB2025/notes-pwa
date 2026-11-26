package com.xiaomi.passport.ui.settings;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.accountsdk.account.data.IdentityAuthReason;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.PassThroughErrorInfo;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.CipherException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.data.XMPassportInfo;
import java.io.IOException;

/* JADX INFO: loaded from: classes4.dex */
public class GetIdentityAuthUrlTask extends AsyncTask<Void, Void, TaskResult> {
    private final String TAG = "GetIdentityAuthUrlTask";
    private String mAuthST;
    private GetIdentityAuthUrlCallback mCallback;
    private Context mContext;
    private IdentityAuthReason mIdentityAuthType;

    public interface GetIdentityAuthUrlCallback {
        void onFail(int i);

        void onFail(PassThroughErrorInfo passThroughErrorInfo);

        void onNeedNotification(String str);

        void onSuccess();
    }

    public GetIdentityAuthUrlTask(Context context, String str, IdentityAuthReason identityAuthReason, GetIdentityAuthUrlCallback getIdentityAuthUrlCallback) {
        this.mContext = context != null ? context.getApplicationContext() : null;
        this.mAuthST = str;
        this.mIdentityAuthType = identityAuthReason;
        this.mCallback = getIdentityAuthUrlCallback;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public TaskResult doInBackground(Void... voidArr) {
        Context context;
        if (this.mCallback == null || (context = this.mContext) == null) {
            AccountLogger.log("GetIdentityAuthUrlTask", "null callback");
            return null;
        }
        XMPassportInfo xMPassportInfoBuild = XMPassportInfo.build(context, "passportapi");
        if (xMPassportInfoBuild == null) {
            AccountLogger.log("GetIdentityAuthUrlTask", "null passportInfo");
            return null;
        }
        int i = 5;
        int i2 = 0;
        while (i2 < 2) {
            i = 3;
            try {
                return new TaskResult(XMPassport.getIdentityAuthUrl(xMPassportInfoBuild, this.mAuthST, this.mIdentityAuthType), 0, null);
            } catch (AccessDeniedException e) {
                AccountLogger.log("GetIdentityAuthUrlTask", "AccessDeniedException", e);
                i = 4;
            } catch (AuthenticationFailureException e2) {
                AccountLogger.log("GetIdentityAuthUrlTask", "AuthenticationFailureException", e2);
                xMPassportInfoBuild.refreshAuthToken(this.mContext);
                i2++;
                i = 1;
            } catch (CipherException e3) {
                AccountLogger.log("GetIdentityAuthUrlTask", "CipherException", e3);
            } catch (InvalidResponseException e4) {
                AccountLogger.log("GetIdentityAuthUrlTask", "InvalidResponseException", e4);
                PassThroughErrorInfo serverError = e4.getServerError();
                if (serverError != null) {
                    return new TaskResult(null, 3, serverError);
                }
            } catch (IOException e5) {
                AccountLogger.log("GetIdentityAuthUrlTask", "IOException", e5);
                i = 2;
            }
        }
        return new TaskResult(null, i, null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(TaskResult taskResult) {
        super.onPostExecute(taskResult);
        if (taskResult == null) {
            AccountLogger.log("GetIdentityAuthUrlTask", "null result");
            return;
        }
        if (taskResult.hasException()) {
            if (taskResult.getServerError() != null) {
                this.mCallback.onFail(taskResult.getServerError());
                return;
            } else {
                this.mCallback.onFail(taskResult.getExceptionReason());
                return;
            }
        }
        if (TextUtils.isEmpty(taskResult.getNotificationUrl())) {
            this.mCallback.onSuccess();
        } else {
            this.mCallback.onNeedNotification(taskResult.getNotificationUrl());
        }
    }

    public class TaskResult {
        private AsyncTaskResult asyncTaskResult;
        private String notificationUrl;
        private PassThroughErrorInfo serverError;

        public TaskResult(String str, int i, PassThroughErrorInfo passThroughErrorInfo) {
            this.asyncTaskResult = new AsyncTaskResult(i);
            this.notificationUrl = str;
            this.serverError = passThroughErrorInfo;
        }

        public boolean hasException() {
            AsyncTaskResult asyncTaskResult = this.asyncTaskResult;
            return asyncTaskResult != null && asyncTaskResult.hasException();
        }

        public String getNotificationUrl() {
            return this.notificationUrl;
        }

        public int getExceptionReason() {
            return this.asyncTaskResult.getExceptionReason();
        }

        public PassThroughErrorInfo getServerError() {
            return this.serverError;
        }
    }
}
