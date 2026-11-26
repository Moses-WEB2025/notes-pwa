package com.miui.notes.cache;

import android.util.LruCache;
import com.miui.notes.model.FolderModel;
import com.miui.notes.model.NoteModel;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class CacheManager {
    private static final CacheManager INSTANCE = new CacheManager();
    private static final int MAX_CACHE_SIZE = 100;
    private LruCache<Long, NoteCache> mNoteCacheMap = new LruCache<>(100);
    private LruCache<Long, FolderCache> mFolderCacheMap = new LruCache<>(100);
    private LruCache<Long, ThumbnailCache> mThumbnailCacheMap = new LruCache<>(100);

    public static CacheManager getDefault() {
        return INSTANCE;
    }

    private CacheManager() {
    }

    public void invalidate() {
        Iterator<Map.Entry<Long, NoteCache>> it = this.mNoteCacheMap.snapshot().entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().setExpired(true);
        }
        Iterator<Map.Entry<Long, FolderCache>> it2 = this.mFolderCacheMap.snapshot().entrySet().iterator();
        while (it2.hasNext()) {
            it2.next().getValue().setExpired(true);
        }
    }

    public NoteCache getNote(long j) {
        NoteCache noteCache = this.mNoteCacheMap.get(Long.valueOf(j));
        if (noteCache == null || noteCache.isExpired()) {
            return null;
        }
        return noteCache;
    }

    public NoteCache getNote(NoteModel noteModel) {
        NoteCache noteCache = this.mNoteCacheMap.get(Long.valueOf(noteModel.getId()));
        if (noteCache == null) {
            NoteCache noteCache2 = new NoteCache(noteModel);
            this.mNoteCacheMap.put(Long.valueOf(noteModel.getId()), noteCache2);
            return noteCache2;
        }
        if (noteCache.isExpired()) {
            noteCache.update(noteModel);
        }
        return noteCache;
    }

    public FolderCache getFolder(long j) {
        FolderCache folderCache = this.mFolderCacheMap.get(Long.valueOf(j));
        if (folderCache == null || folderCache.isExpired()) {
            return null;
        }
        return folderCache;
    }

    public FolderCache getFolder(FolderModel folderModel) {
        FolderCache folderCache = this.mFolderCacheMap.get(Long.valueOf(folderModel.getId()));
        if (folderCache == null) {
            return new FolderCache(folderModel);
        }
        if (folderCache.isExpired()) {
            folderCache.update(folderModel);
        }
        return folderCache;
    }

    public ThumbnailCache getThumbnail(long j) {
        ThumbnailCache thumbnailCache = this.mThumbnailCacheMap.get(Long.valueOf(j));
        if (thumbnailCache == null || thumbnailCache.isExpired()) {
            return null;
        }
        return thumbnailCache;
    }

    public void putThumbnail(long j, ThumbnailCache thumbnailCache) {
        this.mThumbnailCacheMap.put(Long.valueOf(j), thumbnailCache);
    }

    public void deleteNote(long j) {
        this.mNoteCacheMap.remove(Long.valueOf(j));
    }
}
