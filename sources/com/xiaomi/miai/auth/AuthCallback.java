package com.xiaomi.miai.auth;

/* JADX INFO: loaded from: classes4.dex */
public interface AuthCallback extends AuthTokenStorage {
    void scheduleRefresh(AuthProvider authProvider, UserInfo userInfo, TokenRecord tokenRecord);
}
