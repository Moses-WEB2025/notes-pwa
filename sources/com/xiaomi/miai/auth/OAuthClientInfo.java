package com.xiaomi.miai.auth;

/* JADX INFO: loaded from: classes4.dex */
public class OAuthClientInfo {
    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;

    public OAuthClientInfo(String str, String str2, String str3) {
        this.clientId = str;
        this.redirectUri = str3;
        this.clientSecret = str2;
    }

    public String getClientId() {
        return this.clientId;
    }

    public String getRedirectUri() {
        return this.redirectUri;
    }

    public String getClientSecret() {
        return this.clientSecret;
    }
}
