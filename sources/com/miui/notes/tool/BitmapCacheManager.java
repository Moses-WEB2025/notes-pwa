package com.miui.notes.tool;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import androidx.collection.LruCache;
import com.miui.notes.base.utils.Logger;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import miuix.core.util.SoftReferenceSingleton;

/* JADX INFO: loaded from: classes4.dex */
public class BitmapCacheManager {
    private static final int DEFAULT_CACHE_SIZE = 16777216;
    private static final SoftReferenceSingleton<BitmapCacheManager> INSTANCE = new SoftReferenceSingleton<BitmapCacheManager>() { // from class: com.miui.notes.tool.BitmapCacheManager.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // miuix.core.util.SoftReferenceSingleton
        public BitmapCacheManager createInstance() {
            return new BitmapCacheManager();
        }
    };
    private static final String TAG = "BitmapCacheManager";
    private Map<String, WeakReference<Bitmap>> mImageCache;
    private LruCache<String, Bitmap> mLruCache;

    public static BitmapCacheManager getInstance() {
        return INSTANCE.get();
    }

    public static void resize(Context context) {
        int memoryClass = (((ActivityManager) context.getSystemService("activity")).getMemoryClass() * 1048576) / 8;
        if (16777216 > memoryClass) {
            getInstance().mLruCache.resize(memoryClass);
        }
    }

    private BitmapCacheManager() {
        this.mImageCache = new HashMap();
        this.mLruCache = new LruCache<String, Bitmap>(16777216) { // from class: com.miui.notes.tool.BitmapCacheManager.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // androidx.collection.LruCache
            public int sizeOf(String str, Bitmap bitmap) {
                return bitmap.getByteCount();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // androidx.collection.LruCache
            public void entryRemoved(boolean z, String str, Bitmap bitmap, Bitmap bitmap2) {
                super.entryRemoved(z, str, bitmap, bitmap2);
                if (bitmap == null || bitmap.isRecycled()) {
                    return;
                }
                BitmapCacheManager.this.mImageCache.put(str, new WeakReference(bitmap));
            }
        };
    }

    public void put(String str, Bitmap bitmap) {
        this.mLruCache.put(str, bitmap);
        Logger.INSTANCE.i(TAG, this.mLruCache.toString());
    }

    public Bitmap get(String str) {
        Bitmap bitmap;
        WeakReference<Bitmap> weakReference = this.mImageCache.get(str);
        if (weakReference != null && (bitmap = weakReference.get()) != null && !bitmap.isRecycled()) {
            put(str, bitmap);
            Logger.INSTANCE.i(TAG, "WeakRef hit!");
            return bitmap;
        }
        Bitmap bitmap2 = this.mLruCache.get(str);
        if (bitmap2 != null && !bitmap2.isRecycled()) {
            Logger.INSTANCE.i(TAG, "Cache hit!");
        }
        return bitmap2;
    }

    public void clear() {
        this.mLruCache.evictAll();
    }
}
