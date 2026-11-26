package com.xiaomi.passport.snscorelib.internal.exception;

import com.xiaomi.accountsdk.account.serverpassthrougherror.data.PassThroughErrorInfo;

/* JADX INFO: loaded from: classes4.dex */
public class SNSLoginException extends Exception {
    private int code;
    private PassThroughErrorInfo serverError;

    public SNSLoginException(int i, String str) {
        super(str);
        this.serverError = null;
        this.code = i;
    }

    public SNSLoginException(int i, String str, PassThroughErrorInfo passThroughErrorInfo) {
        super(str);
        this.code = i;
        this.serverError = passThroughErrorInfo;
    }

    public int getCode() {
        return this.code;
    }

    public PassThroughErrorInfo getServerError() {
        return this.serverError;
    }
}
