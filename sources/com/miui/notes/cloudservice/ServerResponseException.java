package com.miui.notes.cloudservice;

/* JADX INFO: loaded from: classes4.dex */
public class ServerResponseException extends Exception {
    private int mCode;
    private String mDescription;
    private String mReason;
    private String mResult;
    private boolean mRetriable;

    public ServerResponseException(String str, String str2, String str3, int i, boolean z) {
        this.mResult = str;
        this.mReason = str2;
        this.mDescription = str3;
        this.mCode = i;
        this.mRetriable = z;
    }

    public int getCode() {
        return this.mCode;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return "Server response error: result=" + this.mResult + ", reason=" + this.mReason + ", description=" + this.mDescription + ", code=" + this.mCode;
    }
}
