package com.miui.pc.frame;

import com.miui.notes.cache.FolderCache;

/* JADX INFO: loaded from: classes4.dex */
public class FolderItem {
    int count;
    FolderCache folderCache;
    long folderId;
    String folderName;
    int iconId;

    public FolderItem(FolderCache folderCache) {
        if (folderCache != null) {
            this.folderId = folderCache.getFolder().getId();
            this.folderName = folderCache.getFolder().getSubject();
            this.count = folderCache.getFolder().getCount();
            this.iconId = folderCache.getFolder().getIconResource();
        }
        this.folderCache = folderCache;
    }

    public long getFolderId() {
        return this.folderId;
    }

    public void setFolderId(long j) {
        this.folderId = j;
    }

    public String getFolderName() {
        return this.folderName;
    }

    public void setFolderName(String str) {
        this.folderName = str;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int i) {
        this.count = i;
    }

    public int getIconId() {
        return this.iconId;
    }

    public void setIconId(int i) {
        this.iconId = i;
    }

    public FolderCache getFolderCache() {
        return this.folderCache;
    }

    public void setFolderCache(FolderCache folderCache) {
        this.folderCache = folderCache;
    }
}
