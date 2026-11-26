package com.miui.libhandwritingpage.utils;

import android.graphics.Bitmap;
import androidx.collection.LruCache;

/* JADX INFO: loaded from: classes3.dex */
public class LruCacheUtil {
    private static LruCache<String, Bitmap> mMemoryCache;
    private static int mTotalSize = (int) Runtime.getRuntime().totalMemory();

    public static LruCache<String, Bitmap> initCache() {
        LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(mTotalSize / 5) { // from class: com.miui.libhandwritingpage.utils.LruCacheUtil.1
            @Override // androidx.collection.LruCache
            public int sizeOf(String str, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
        mMemoryCache = lruCache;
        return lruCache;
    }

    public static synchronized void addBitmapToMemoryCache(String str, Bitmap bitmap) {
        LruCache<String, Bitmap> lruCache;
        if (getBitmapFromMemCache(str) == null && str != null && bitmap != null && (lruCache = mMemoryCache) != null) {
            lruCache.put(str, bitmap);
        }
    }

    public static synchronized Bitmap getBitmapFromMemCache(String str) {
        if (str != null) {
            LruCache<String, Bitmap> lruCache = mMemoryCache;
            if (lruCache != null) {
                return lruCache.get(str);
            }
        }
        return null;
    }

    public static void removeBitmapFromMemCache(String str) {
        LruCache<String, Bitmap> lruCache;
        if (str == null || (lruCache = mMemoryCache) == null) {
            return;
        }
        lruCache.remove(str);
    }

    public static void clearCache() {
        LruCache<String, Bitmap> lruCache = mMemoryCache;
        if (lruCache != null) {
            if (lruCache.size() > 0) {
                mMemoryCache.evictAll();
            }
            mMemoryCache = null;
        }
    }
}
