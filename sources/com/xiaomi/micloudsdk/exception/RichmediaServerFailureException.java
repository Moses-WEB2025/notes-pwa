package com.xiaomi.micloudsdk.exception;

/* JADX INFO: loaded from: classes4.dex */
public class RichmediaServerFailureException extends Exception {
    private int errorCode;

    public RichmediaServerFailureException() {
    }

    public RichmediaServerFailureException(String str) {
        super(str);
    }

    public RichmediaServerFailureException(Throwable th) {
        super(th);
    }

    public RichmediaServerFailureException(Throwable th, int i) {
        super(th);
        this.errorCode = i;
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
