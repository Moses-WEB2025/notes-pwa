package com.xiaomi.miai.auth;

/* JADX INFO: loaded from: classes4.dex */
public interface AuthTokenStorage {
    TokenRecord read(String str, String str2);

    void write(TokenRecord tokenRecord);
}
