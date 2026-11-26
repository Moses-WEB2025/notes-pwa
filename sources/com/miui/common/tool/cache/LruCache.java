package com.miui.common.tool.cache;

import android.app.ActivityManager;
import com.miui.notes.NoteApp;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes2.dex */
public class LruCache<K, V> implements Cache<K, V> {
    private static final int BASE_MEMORY_SIZE = ((ActivityManager) NoteApp.getInstance().getSystemService("activity")).getMemoryClass() * 1048576;
    private final LinkedHashMap<K, CacheItem<K, V>> mCacheItems;
    private final ReentrantLock mLock;
    private long mMaxSize;
    private long mTotalSize;

    public LruCache(int i) {
        i = i < 0 ? BASE_MEMORY_SIZE / 8 : i;
        this.mLock = new ReentrantLock();
        this.mMaxSize = i;
        this.mTotalSize = 0L;
        this.mCacheItems = new LinkedHashMap<>(0, 0.75f, true);
    }

    @Override // com.miui.common.tool.cache.Cache
    public void setMaxSize(int i) {
        if (i < 0) {
            i = BASE_MEMORY_SIZE / 8;
        }
        this.mMaxSize = i;
        try {
            this.mLock.lock();
            if (this.mTotalSize < this.mMaxSize) {
                trim();
            }
        } finally {
            this.mLock.unlock();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.miui.common.tool.cache.Cache
    public void put(K k, V v, int i) {
        if (k == null || v == 0) {
            return;
        }
        CacheItem<K, V> cacheItem = new CacheItem<>();
        cacheItem.key = k;
        cacheItem.cacheItem = v;
        if (i < 0) {
            i = 0;
        }
        cacheItem.size = i;
        this.mLock.lock();
        try {
            this.mCacheItems.put(k, cacheItem);
            this.mTotalSize += (long) cacheItem.size;
            trim();
        } finally {
            this.mLock.unlock();
        }
    }

    @Override // com.miui.common.tool.cache.Cache
    public V get(K k) {
        V v;
        this.mLock.lock();
        try {
            trim();
            CacheItem<K, V> cacheItem = this.mCacheItems.get(k);
            if (cacheItem != null) {
                if (cacheItem.cacheItem != null) {
                    v = cacheItem.cacheItem;
                } else {
                    if (cacheItem.softCacheItem != null) {
                        cacheItem.cacheItem = cacheItem.softCacheItem.get();
                        if (cacheItem.cacheItem != null) {
                            long j = this.mTotalSize + ((long) cacheItem.size);
                            this.mTotalSize = j;
                            if (j > this.mMaxSize) {
                                trim();
                            }
                            v = cacheItem.cacheItem;
                        }
                    }
                    this.mCacheItems.remove(k);
                }
                return v;
            }
            return null;
        } finally {
            this.mLock.unlock();
        }
    }

    @Override // com.miui.common.tool.cache.Cache
    public void clear() {
        this.mLock.lock();
        try {
            Iterator<Map.Entry<K, CacheItem<K, V>>> it = this.mCacheItems.entrySet().iterator();
            while (it.hasNext()) {
                CacheItem<K, V> value = it.next().getValue();
                if (value.softCacheItem != null) {
                    value.softCacheItem.clear();
                }
            }
            this.mCacheItems.clear();
        } finally {
            this.mLock.unlock();
        }
    }

    @Override // com.miui.common.tool.cache.Cache
    public int getCount() {
        this.mLock.lock();
        try {
            trim();
            return this.mCacheItems.size();
        } finally {
            this.mLock.unlock();
        }
    }

    private void trim() {
        Iterator<Map.Entry<K, CacheItem<K, V>>> it = this.mCacheItems.entrySet().iterator();
        while (it.hasNext()) {
            CacheItem<K, V> value = it.next().getValue();
            if (value.cacheItem == null && (value.softCacheItem == null || value.softCacheItem.get() == null)) {
                it.remove();
            }
        }
        Iterator<Map.Entry<K, CacheItem<K, V>>> it2 = this.mCacheItems.entrySet().iterator();
        while (this.mTotalSize > this.mMaxSize && it2.hasNext()) {
            CacheItem<K, V> value2 = it2.next().getValue();
            if (value2 != null) {
                if (value2.softCacheItem == null) {
                    value2.softCacheItem = new SoftReference<>(value2.cacheItem);
                }
                this.mTotalSize -= (long) value2.size;
                value2.cacheItem = null;
            }
        }
    }

    private static class CacheItem<K, T> {
        public T cacheItem;
        public K key;
        public int size;
        public SoftReference<T> softCacheItem;

        private CacheItem() {
        }
    }
}
