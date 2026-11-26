package com.xiaomi.accountsdk.account.exception;

/* JADX INFO: loaded from: classes4.dex */
public class InvalidUserNameException extends Exception {
    private static final long serialVersionUID = 1;

    public InvalidUserNameException() {
        super("No such a user");
    }

    public InvalidUserNameException(String str) {
        super(str);
    }
}
