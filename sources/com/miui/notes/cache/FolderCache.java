package com.miui.notes.cache;

import com.miui.notes.model.FolderModel;

/* JADX INFO: loaded from: classes4.dex */
public class FolderCache {
    private boolean mExpired;
    private FolderModel mFolder;

    public FolderCache(FolderModel folderModel) {
        this.mFolder = folderModel;
    }

    public FolderModel getFolder() {
        return this.mFolder;
    }

    public boolean isExpired() {
        return this.mExpired;
    }

    public void setExpired(boolean z) {
        this.mExpired = z;
    }

    public void update(FolderModel folderModel) {
        this.mFolder = folderModel;
        this.mExpired = false;
    }
}
