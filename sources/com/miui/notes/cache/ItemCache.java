package com.miui.notes.cache;

/* JADX INFO: loaded from: classes4.dex */
public class ItemCache {
    private Object cacheObject;
    private int cacheType;

    public ItemCache(int i, Object obj) {
        this.cacheType = i;
        this.cacheObject = obj;
    }

    public int getCacheType() {
        return this.cacheType;
    }

    public void setCacheType(int i) {
        this.cacheType = i;
    }

    public Object getCacheObject() {
        return this.cacheObject;
    }

    public void setCacheObject(Object obj) {
        this.cacheObject = obj;
    }

    public long getCacheId() {
        int i = this.cacheType;
        if (i == 0) {
            return ((NoteCache) this.cacheObject).getNote().getId();
        }
        if (i == 1) {
            return ((FolderCache) this.cacheObject).getFolder().getId();
        }
        if (i != 2) {
            return -1L;
        }
        return ((FolderCache) this.cacheObject).getFolder().getId();
    }

    public long getModifiedTime() {
        int i = this.cacheType;
        if (i == 0) {
            return ((NoteCache) this.cacheObject).getNote().getUpdatedAt();
        }
        if (i == 1) {
            return ((FolderCache) this.cacheObject).getFolder().getUpdatedAt();
        }
        if (i != 2) {
            return -1L;
        }
        return ((FolderCache) this.cacheObject).getFolder().getUpdatedAt();
    }

    public long getCreateTime() {
        int i = this.cacheType;
        if (i == 0) {
            return ((NoteCache) this.cacheObject).getNote().getCreatedAt();
        }
        if (i == 1) {
            return ((FolderCache) this.cacheObject).getFolder().getCreatedAt();
        }
        if (i != 2) {
            return -1L;
        }
        return ((FolderCache) this.cacheObject).getFolder().getCreatedAt();
    }

    public long getStickTime() {
        int i = this.cacheType;
        if (i == 0) {
            return ((NoteCache) this.cacheObject).getNote().getStickAt();
        }
        if (i == 1) {
            return ((FolderCache) this.cacheObject).getFolder().getStickAt();
        }
        if (i != 2) {
            return -1L;
        }
        return ((FolderCache) this.cacheObject).getFolder().getStickAt();
    }

    public boolean isStick() {
        int i = this.cacheType;
        if (i == 0) {
            return ((NoteCache) this.cacheObject).getNote().getStickAt() > 0;
        }
        if (i != 1) {
            return i == 2 && ((FolderCache) this.cacheObject).getFolder().getStickAt() > 0;
        }
        return ((FolderCache) this.cacheObject).getFolder().getStickAt() > 0;
    }
}
