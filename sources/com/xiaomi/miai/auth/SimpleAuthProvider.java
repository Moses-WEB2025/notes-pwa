package com.xiaomi.miai.auth;

/* JADX INFO: loaded from: classes4.dex */
public class SimpleAuthProvider extends AuthProvider {
    private final String authValue;

    @Override // com.xiaomi.miai.auth.AuthProvider
    public String buildAuthValue(UserInfo userInfo, TokenRecord tokenRecord) {
        return null;
    }

    protected SimpleAuthProvider(String str, String str2) {
        super(str);
        this.authValue = str2;
    }

    @Override // com.xiaomi.miai.auth.AuthProvider
    public String getAuthValue(UserInfo userInfo) {
        return this.authValue;
    }

    @Override // com.xiaomi.miai.auth.AuthProvider
    public TokenRecord getOrRefreshToken(UserInfo userInfo, boolean z, TokenRecord tokenRecord) {
        throw new UnsupportedOperationException();
    }
}
