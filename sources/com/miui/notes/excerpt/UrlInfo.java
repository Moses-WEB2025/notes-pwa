package com.miui.notes.excerpt;

/* JADX INFO: loaded from: classes4.dex */
public class UrlInfo {
    private long excerptTime;
    private String url;

    public UrlInfo(String str, long j) {
        this.url = str;
        this.excerptTime = j;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public long getExcerptTime() {
        return this.excerptTime;
    }

    public void setExcerptTime(long j) {
        this.excerptTime = j;
    }
}
