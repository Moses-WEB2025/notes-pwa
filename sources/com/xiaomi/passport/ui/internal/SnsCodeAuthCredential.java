package com.xiaomi.passport.ui.internal;

/* JADX INFO: loaded from: classes4.dex */
public class SnsCodeAuthCredential extends SNSAuthCredential {
    public final String code;

    public SnsCodeAuthCredential(String str, String str2, String str3, String str4) {
        super(str, str2, str4);
        this.code = str3;
    }
}
