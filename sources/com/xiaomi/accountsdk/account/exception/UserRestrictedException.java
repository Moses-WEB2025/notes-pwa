package com.xiaomi.accountsdk.account.exception;

/* JADX INFO: loaded from: classes4.dex */
public class UserRestrictedException extends Exception {
    public UserRestrictedException() {
        super("User has been restricted by server");
    }

    public UserRestrictedException(String str) {
        super(str);
    }
}
