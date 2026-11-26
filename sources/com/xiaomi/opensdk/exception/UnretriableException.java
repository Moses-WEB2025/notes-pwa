package com.xiaomi.opensdk.exception;

import android.text.TextUtils;

/* JADX INFO: loaded from: classes4.dex */
public class UnretriableException extends Exception {
    private static final long serialVersionUID = 1;
    private String mDescription;
    private int mErrorCode;
    private String mStep;

    public UnretriableException(String str) {
        this.mDescription = str;
    }

    public UnretriableException(String str, int i) {
        this.mDescription = str;
        this.mErrorCode = i;
    }

    public UnretriableException(Throwable th) {
        super(th);
    }

    public UnretriableException(Throwable th, String str) {
        super(th);
        this.mDescription = str;
    }

    public UnretriableException(Throwable th, int i) {
        super(th);
        this.mErrorCode = i;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public String getDescription() {
        if (!TextUtils.isEmpty(this.mDescription)) {
            return this.mDescription;
        }
        return toString();
    }

    @Override // java.lang.Throwable
    public String toString() {
        String simpleName = getClass().getSimpleName();
        Throwable cause = getCause();
        if (cause != null) {
            String str = simpleName + "[" + cause.getClass().getSimpleName();
            if (cause.getMessage() != null) {
                str = str + ":" + cause.getMessage();
            }
            simpleName = str + "]";
        }
        if (this.mErrorCode > 0) {
            simpleName = simpleName + "[" + this.mErrorCode + "]";
        }
        if (this.mDescription != null) {
            simpleName = simpleName + ": " + this.mDescription;
        }
        return this.mStep != null ? simpleName + ", " + this.mStep : simpleName;
    }

    public void setStep(String str) {
        this.mStep = str;
    }

    public String getStep() {
        return this.mStep;
    }
}
