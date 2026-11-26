package com.xiaomi.accountsdk.request;

import com.xiaomi.accountsdk.account.exception.AccountException;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.PassThroughErrorInfo;

/* JADX INFO: loaded from: classes4.dex */
public class InvalidResponseException extends AccountException {
    private static final long serialVersionUID = 5544530065307643635L;
    public boolean isHtmlOr302;
    private PassThroughErrorInfo serverError;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public InvalidResponseException(String str) {
        this(str, (Throwable) null);
    }

    public InvalidResponseException(int i, String str) {
        super(i, str);
        this.isHtmlOr302 = false;
        this.serverError = null;
    }

    public InvalidResponseException(String str, Throwable th) {
        this(str, th, false);
    }

    public InvalidResponseException(String str, Throwable th, boolean z) {
        super(-1, str, th);
        this.serverError = null;
        this.isHtmlOr302 = z;
    }

    public InvalidResponseException(PassThroughErrorInfo passThroughErrorInfo) {
        super(-1, null);
        this.isHtmlOr302 = false;
        this.serverError = passThroughErrorInfo;
    }

    public InvalidResponseException(int i, String str, PassThroughErrorInfo passThroughErrorInfo) {
        super(i, str);
        this.isHtmlOr302 = false;
        this.serverError = passThroughErrorInfo;
    }

    public InvalidResponseException(String str, PassThroughErrorInfo passThroughErrorInfo) {
        this(str);
        this.serverError = passThroughErrorInfo;
    }

    public PassThroughErrorInfo getServerError() {
        return this.serverError;
    }
}
