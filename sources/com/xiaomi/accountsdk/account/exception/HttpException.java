package com.xiaomi.accountsdk.account.exception;

import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class HttpException extends Exception {
    public boolean isStsUrlRequestError;
    public final int responseCode;
    public String serviceId;

    public HttpException(int i, String str) {
        super(str);
        this.isStsUrlRequestError = false;
        this.responseCode = i;
    }

    public void stsUrlRequestError(String str) {
        this.serviceId = str;
        this.isStsUrlRequestError = true;
    }

    @Override // java.lang.Throwable
    public String toString() {
        return ("response code: " + this.responseCode + StringUtils.LF + (this.isStsUrlRequestError ? this.serviceId + " sts url request error \n" : "")) + super.toString();
    }
}
