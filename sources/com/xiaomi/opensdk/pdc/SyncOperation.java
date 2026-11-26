package com.xiaomi.opensdk.pdc;

import android.text.TextUtils;

/* JADX INFO: loaded from: classes4.dex */
public interface SyncOperation {
    Result execute() throws SyncException;

    public static class Result {
        private String mDescription;
        private int mErrorCode;
        private Constants.ErrorType mErrorType;
        private boolean mIsSuccess;
        private String mResult;
        private long mRetryTime;

        public boolean isSuccess() {
            return this.mIsSuccess;
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }

        public String getResult() {
            return this.mResult;
        }

        public String getDescription() {
            if (!TextUtils.isEmpty(this.mResult)) {
                return this.mDescription + " [reason : " + this.mResult + "]";
            }
            return this.mDescription;
        }

        public long getRetryTime() {
            return this.mRetryTime;
        }

        public Constants.ErrorType getErrorType() {
            return this.mErrorType;
        }

        public boolean isRetriable() {
            return this.mErrorType == Constants.ErrorType.RETRIABLE_ERROR;
        }

        public Result(boolean z, String str, Constants.ErrorType errorType, int i, long j, String str2) {
            this.mIsSuccess = z;
            this.mDescription = str;
            this.mErrorType = errorType;
            this.mErrorCode = i;
            this.mRetryTime = j;
            this.mResult = str2;
        }
    }
}
