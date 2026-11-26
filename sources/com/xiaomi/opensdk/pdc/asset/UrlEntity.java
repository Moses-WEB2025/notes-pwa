package com.xiaomi.opensdk.pdc.asset;

/* JADX INFO: loaded from: classes4.dex */
public class UrlEntity {
    public final String downloadUrl;
    public final long expireTime;

    public UrlEntity(long j, String str) {
        this.expireTime = j;
        this.downloadUrl = str;
    }

    public String toString() {
        return "UrlEntity [expireTime=" + this.expireTime + ", downloadUrl=" + this.downloadUrl + "]";
    }
}
