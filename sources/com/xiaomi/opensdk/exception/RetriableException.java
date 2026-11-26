package com.xiaomi.opensdk.exception;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import javax.net.ssl.SSLException;
import org.apache.http.conn.ConnectTimeoutException;

/* JADX INFO: loaded from: classes4.dex */
public class RetriableException extends Exception {
    public static final int NETWORK_DEFAULT_RETRY_TIME = 300000;
    private static final long serialVersionUID = 1;
    private final int ERRORCODE_NOT_DEFINE;
    private final String mDescription;
    private int mErrorCode;
    private final long mRetrytime;
    private String mStep;

    public RetriableException(String str, long j) {
        this.ERRORCODE_NOT_DEFINE = -1;
        this.mErrorCode = -1;
        this.mDescription = str;
        this.mRetrytime = j;
    }

    public RetriableException(String str, long j, int i) {
        this.ERRORCODE_NOT_DEFINE = -1;
        this.mDescription = str;
        this.mRetrytime = j;
        this.mErrorCode = i;
    }

    public RetriableException(Throwable th, String str, long j) {
        super(th);
        this.ERRORCODE_NOT_DEFINE = -1;
        this.mErrorCode = -1;
        this.mDescription = str;
        this.mRetrytime = j;
    }

    public long getRetryTime() {
        return this.mRetrytime;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    @Override // java.lang.Throwable
    public String toString() {
        String simpleName = getClass().getSimpleName();
        if (this.mStep != null) {
            simpleName = simpleName + ", " + this.mStep;
        }
        return this.mDescription != null ? simpleName + ": " + this.mDescription + ", retry after " + (this.mRetrytime / 1000) + " seconds" : simpleName;
    }

    public static boolean isRetriableException(Throwable th) {
        return (th instanceof ConnectException) || (th instanceof ConnectTimeoutException) || (th instanceof SocketException) || (th instanceof SocketTimeoutException) || (th instanceof UnknownHostException) || (th instanceof SSLException);
    }

    public void setStep(String str) {
        this.mStep = str;
    }

    public String getStep() {
        return this.mStep;
    }
}
