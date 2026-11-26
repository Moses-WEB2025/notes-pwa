package com.xiaomi.accountsdk.account.exception;

/* JADX INFO: loaded from: classes4.dex */
public class InvalidPhoneOrTicketException extends Exception {
    public InvalidPhoneOrTicketException() {
        super("wrong ticket or unactivated phone");
    }

    public InvalidPhoneOrTicketException(String str) {
        super(str);
    }
}
