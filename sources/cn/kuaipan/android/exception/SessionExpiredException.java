package cn.kuaipan.android.exception;

import cn.kuaipan.android.kss.TransferStep;

/* JADX INFO: loaded from: classes.dex */
public class SessionExpiredException extends KscException {
    public final long retryAfterInMillis;

    public SessionExpiredException(TransferStep transferStep) {
        super(ErrorCode.MSG206_SESSION_EXPIRED, transferStep);
        this.retryAfterInMillis = 5000L;
    }

    public SessionExpiredException(String str, TransferStep transferStep) {
        super(ErrorCode.MSG206_SESSION_EXPIRED, str, transferStep);
        this.retryAfterInMillis = 5000L;
    }

    public SessionExpiredException(Throwable th, TransferStep transferStep) {
        super(ErrorCode.MSG206_SESSION_EXPIRED, th, transferStep);
        this.retryAfterInMillis = 5000L;
    }

    public SessionExpiredException(String str, Throwable th, TransferStep transferStep) {
        super(ErrorCode.MSG206_SESSION_EXPIRED, str, th, transferStep);
        this.retryAfterInMillis = 5000L;
    }
}
