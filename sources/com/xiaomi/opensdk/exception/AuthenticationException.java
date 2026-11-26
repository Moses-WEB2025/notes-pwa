package com.xiaomi.opensdk.exception;

import android.text.TextUtils;

/* JADX INFO: loaded from: classes4.dex */
public class AuthenticationException extends Exception {
    private static final long serialVersionUID = 1;

    public AuthenticationException() {
    }

    public AuthenticationException(String str) {
        super(str);
    }

    @Override // java.lang.Throwable
    public String toString() {
        if (!TextUtils.isEmpty(getMessage())) {
            return getClass().getSimpleName() + ": 鉴权失败, " + getMessage();
        }
        return getClass().getSimpleName() + ": 鉴权失败";
    }
}
