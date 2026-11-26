package cn.kuaipan.android.kss.transferclient.exception;

/* JADX INFO: loaded from: classes.dex */
public class SFSRequestServiceTemporaryNotAvailableException extends Exception {
    public final long retryDelayHintMillis;

    public SFSRequestServiceTemporaryNotAvailableException(long j) {
        this.retryDelayHintMillis = j;
    }

    public SFSRequestServiceTemporaryNotAvailableException(String str, long j) {
        super(str);
        this.retryDelayHintMillis = j;
    }

    public SFSRequestServiceTemporaryNotAvailableException(String str, Throwable th, long j) {
        super(str, th);
        this.retryDelayHintMillis = j;
    }

    public SFSRequestServiceTemporaryNotAvailableException(Throwable th, long j) {
        super(th);
        this.retryDelayHintMillis = j;
    }
}
