package com.miui.notes.ai.account;

/* JADX INFO: loaded from: classes4.dex */
public class AccountListenerHolder {
    private static AccountResultListener INSTANCE;

    public static void setInstance(AccountResultListener accountResultListener) {
        if (INSTANCE == null) {
            INSTANCE = accountResultListener;
        }
    }

    public static AccountResultListener getInstance() {
        return INSTANCE;
    }
}
