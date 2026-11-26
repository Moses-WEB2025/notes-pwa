package com.xiaomi.passport.ui.internal;

import android.accounts.AccountsException;

/* JADX INFO: loaded from: classes4.dex */
public class PassportUIException extends AccountsException {
    public PassportUIException() {
    }

    public PassportUIException(String str) {
        super(str);
    }

    public PassportUIException(String str, Throwable th) {
        super(str, th);
    }
}
