package com.miui.maml.util;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import com.miui.maml.RendererCore;
import com.miui.maml.ResourceLoader;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.ScreenElementRootFactory;
import java.util.HashMap;
import java.util.Iterator;

/* JADX INFO: loaded from: classes3.dex */
public class RendererCoreCache implements RendererCore.OnReleaseListener {
    private static final String LOG_TAG = "RendererCoreCache";
    public static final int TIME_DAY = 86400000;
    public static final int TIME_HOUR = 3600000;
    public static final int TIME_MIN = 60000;
    private HashMap<Object, RendererCoreInfo> mCaches;
    private Handler mHandler;

    public interface OnCreateRootCallback {
        void onCreateRoot(ScreenElementRoot screenElementRoot);
    }

    protected class CheckCacheRunnable implements Runnable {
        private Object mKey;

        public CheckCacheRunnable(Object obj) {
            this.mKey = obj;
        }

        @Override // java.lang.Runnable
        public void run() {
            RendererCoreCache.this.checkCache(this.mKey);
        }
    }

    public static class RendererCoreInfo {
        public long accessTime = Long.MAX_VALUE;
        public long cacheTime;
        public CheckCacheRunnable checkCache;
        public RendererCore r;

        public RendererCoreInfo(RendererCore rendererCore) {
            this.r = rendererCore;
        }
    }

    public RendererCoreCache() {
        this.mCaches = new HashMap<>();
        this.mHandler = new Handler();
    }

    public RendererCoreCache(Handler handler) {
        this.mCaches = new HashMap<>();
        this.mHandler = handler;
    }

    public synchronized RendererCoreInfo get(Object obj, long j) {
        RendererCoreInfo rendererCoreInfo = this.mCaches.get(obj);
        if (rendererCoreInfo == null) {
            return null;
        }
        rendererCoreInfo.accessTime = Long.MAX_VALUE;
        rendererCoreInfo.cacheTime = j;
        this.mHandler.removeCallbacks(rendererCoreInfo.checkCache);
        return rendererCoreInfo;
    }

    public synchronized RendererCoreInfo get(Object obj, Context context, long j, String str, OnCreateRootCallback onCreateRootCallback) {
        return get(obj, context, j, null, str, onCreateRootCallback);
    }

    public synchronized RendererCoreInfo get(Object obj, Context context, long j, ResourceLoader resourceLoader, OnCreateRootCallback onCreateRootCallback) {
        return get(obj, context, j, resourceLoader, null, onCreateRootCallback);
    }

    private RendererCoreInfo get(Object obj, Context context, long j, ResourceLoader resourceLoader, String str, OnCreateRootCallback onCreateRootCallback) {
        ScreenElementRoot screenElementRootCreate;
        RendererCoreInfo rendererCoreInfo = get(obj, j);
        if (rendererCoreInfo != null) {
            return rendererCoreInfo;
        }
        if (resourceLoader != null) {
            screenElementRootCreate = ScreenElementRootFactory.create(new ScreenElementRootFactory.Parameter(context, resourceLoader));
        } else {
            screenElementRootCreate = ScreenElementRootFactory.create(new ScreenElementRootFactory.Parameter(context, str));
        }
        if (screenElementRootCreate == null) {
            Log.e(LOG_TAG, "fail to get RendererCoreInfo" + obj);
            return null;
        }
        if (onCreateRootCallback != null) {
            onCreateRootCallback.onCreateRoot(screenElementRootCreate);
        }
        screenElementRootCreate.setDefaultFramerate(0.0f);
        RendererCore rendererCore = screenElementRootCreate.load() ? new RendererCore(screenElementRootCreate) : null;
        RendererCoreInfo rendererCoreInfo2 = new RendererCoreInfo(rendererCore);
        rendererCoreInfo2.accessTime = Long.MAX_VALUE;
        rendererCoreInfo2.cacheTime = j;
        if (rendererCore != null) {
            rendererCore.setOnReleaseListener(this);
            rendererCoreInfo2.checkCache = new CheckCacheRunnable(obj);
        }
        this.mCaches.put(obj, rendererCoreInfo2);
        return rendererCoreInfo2;
    }

    public synchronized void release(Object obj) {
        Log.d(LOG_TAG, "release: " + obj);
        RendererCoreInfo rendererCoreInfo = this.mCaches.get(obj);
        if (rendererCoreInfo != null) {
            rendererCoreInfo.accessTime = System.currentTimeMillis();
            if (rendererCoreInfo.cacheTime == 0) {
                this.mCaches.remove(obj);
                Log.d(LOG_TAG, "removed: " + obj);
            } else {
                Log.d(LOG_TAG, "scheduled release: " + obj + " after " + rendererCoreInfo.cacheTime);
                this.mHandler.removeCallbacks(rendererCoreInfo.checkCache);
                this.mHandler.postDelayed(rendererCoreInfo.checkCache, rendererCoreInfo.cacheTime);
            }
        }
    }

    public synchronized void clear() {
        this.mCaches.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void checkCache(Object obj) {
        Log.d(LOG_TAG, "checkCache: " + obj);
        RendererCoreInfo rendererCoreInfo = this.mCaches.get(obj);
        if (rendererCoreInfo == null) {
            Log.d(LOG_TAG, "checkCache: the key does not exist, " + obj);
            return;
        }
        if (rendererCoreInfo.accessTime == Long.MAX_VALUE) {
            return;
        }
        long jCurrentTimeMillis = System.currentTimeMillis() - rendererCoreInfo.accessTime;
        if (jCurrentTimeMillis >= rendererCoreInfo.cacheTime) {
            this.mCaches.remove(obj);
            Log.d(LOG_TAG, "checkCache removed: " + obj);
        } else {
            if (jCurrentTimeMillis < 0) {
                rendererCoreInfo.accessTime = System.currentTimeMillis();
                jCurrentTimeMillis = 0;
            }
            this.mHandler.postDelayed(rendererCoreInfo.checkCache, rendererCoreInfo.cacheTime - jCurrentTimeMillis);
            Log.d(LOG_TAG, "checkCache resheduled: " + obj + " after " + (rendererCoreInfo.cacheTime - jCurrentTimeMillis));
        }
    }

    @Override // com.miui.maml.RendererCore.OnReleaseListener
    public synchronized boolean OnRendererCoreReleased(RendererCore rendererCore) {
        Object next;
        RendererCoreInfo rendererCoreInfo;
        Log.d(LOG_TAG, "OnRendererCoreReleased: " + rendererCore);
        Iterator<Object> it = this.mCaches.keySet().iterator();
        do {
            if (!it.hasNext()) {
                return false;
            }
            next = it.next();
            rendererCoreInfo = this.mCaches.get(next);
        } while (rendererCoreInfo.r != rendererCore);
        release(next);
        return rendererCoreInfo.cacheTime == 0;
    }
}
