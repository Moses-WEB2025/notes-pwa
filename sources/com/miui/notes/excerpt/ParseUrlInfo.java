package com.miui.notes.excerpt;

/* JADX INFO: loaded from: classes4.dex */
public class ParseUrlInfo extends UrlInfo {
    private boolean isParse;

    public ParseUrlInfo(String str, long j, boolean z) {
        super(str, j);
        this.isParse = z;
    }

    public boolean isParse() {
        return this.isParse;
    }

    public void setParse(boolean z) {
        this.isParse = z;
    }
}
