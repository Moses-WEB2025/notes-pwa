package com.market.sdk;

/* JADX INFO: loaded from: classes2.dex */
public class LocalAppInfo {
    public String packageName = "";
    public String displayName = "";
    public int versionCode = 0;
    public String versionName = "";
    public String signature = "";
    public String sourceDir = "";

    private LocalAppInfo() {
    }

    public static LocalAppInfo get(String str) {
        LocalAppInfo localAppInfo = new LocalAppInfo();
        localAppInfo.packageName = str;
        return localAppInfo;
    }
}
