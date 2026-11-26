package micloud.compat.independent.request;

import android.content.Intent;

/* JADX INFO: loaded from: classes5.dex */
public class QueryAuthTokenException extends Exception {
    public static final int DEFAULT_ERROR_CODE = -1;
    public final int errorCode;
    public final Intent intent;
    public final boolean needReAuth;

    public QueryAuthTokenException(String str) {
        this(-1, str, null, null, false);
    }

    public QueryAuthTokenException(String str, Throwable th) {
        this(-1, str, th, null, false);
    }

    public QueryAuthTokenException(int i, String str, Intent intent) {
        this(i, str, null, intent, true);
    }

    public QueryAuthTokenException(int i, String str, boolean z) {
        this(i, str, null, null, z);
    }

    public QueryAuthTokenException(String str, Throwable th, boolean z) {
        this(-1, str, th, null, z);
    }

    public QueryAuthTokenException(int i, String str, Throwable th, Intent intent, boolean z) {
        super(str, th);
        this.errorCode = i;
        this.intent = intent;
        this.needReAuth = z;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        Throwable cause = super.getCause();
        String message = super.getMessage();
        StringBuilder sb = new StringBuilder("needReAuth=" + this.needReAuth);
        if (this.errorCode != -1) {
            sb.append(", errorCode=").append(this.errorCode);
        }
        if (message != null) {
            sb.append(", msg=").append(message);
        }
        if (this.intent != null) {
            sb.append(", intent=").append(this.intent);
        }
        if (cause != null) {
            sb.append(", cause=").append(cause);
        }
        return sb.toString();
    }
}
