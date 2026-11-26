package com.miui.notes.cache;

/* JADX INFO: loaded from: classes4.dex */
public class ThumbnailCache {
    private boolean mExpired;
    private String mPath;

    public boolean isExpired() {
        return this.mExpired;
    }

    public void setExpired(boolean z) {
        this.mExpired = z;
    }

    public String getPath() {
        return this.mPath;
    }

    public void setPath(String str) {
        this.mPath = str;
    }
}
