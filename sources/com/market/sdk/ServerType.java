package com.market.sdk;

/* JADX INFO: loaded from: classes2.dex */
public enum ServerType {
    PRODUCT("https://api.developer.xiaomi.com/autoupdate/", "https://global.developer.xiaomi.com/autoupdate/");

    private String baseUrl;
    private String globalBaseUrl;

    ServerType(String str, String str2) {
        this.baseUrl = str;
        this.globalBaseUrl = str2;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public String getGlobalBaseUrl() {
        return this.globalBaseUrl;
    }
}
