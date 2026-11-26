package com.xiaomi.accountsdk.account.exception;

import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class AccountException extends Exception {
    public final int code;
    public final String codeDesc;
    public boolean isStsUrlRequestError;
    public String serviceId;

    public AccountException(int i, String str) {
        this(i, str, null);
    }

    public AccountException(int i, String str, Throwable th) {
        super(th);
        this.isStsUrlRequestError = false;
        this.code = i;
        this.codeDesc = str;
    }

    public void stsUrlRequestError(String str) {
        this.serviceId = str;
        this.isStsUrlRequestError = true;
    }

    @Override // java.lang.Throwable
    public String toString() {
        return ("server code: " + this.code + "; desc: " + this.codeDesc + StringUtils.LF + (this.isStsUrlRequestError ? this.serviceId + " sts url request error \n" : "")) + super.toString();
    }
}
