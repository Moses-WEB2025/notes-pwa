package com.xiaomi.e2ee;

/* JADX INFO: loaded from: classes4.dex */
public class E2EEException extends Exception {
    public static final int ANDROID_KEY_STORE_ERROR = -500;
    public static final int DECODE_DATA_ERROR = -400;
    public static final int E2EE_STATUS_ERROR = -700;
    public static final int GET_CIPHER_ERROR = -600;
    public static final int KEY_ERROR = -300;
    public static final int SERVER_ERROR = -200;
    public static final int UNKNOWN_ERROR = -100;
    public int errorCode;

    public E2EEException(int i, String str) {
        super(str);
        this.errorCode = i;
    }

    public E2EEException(Throwable th) {
        super(th);
        this.errorCode = -100;
    }

    public E2EEException(int i, Throwable th) {
        super(th);
        this.errorCode = i;
    }

    public E2EEException(String str, Throwable th) {
        super(str, th);
    }

    @Override // java.lang.Throwable
    public String toString() {
        Throwable cause = getCause();
        if (cause != null) {
            return cause.toString();
        }
        return super.toString();
    }
}
