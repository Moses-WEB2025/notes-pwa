package com.miui.maml;

import android.util.Log;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes3.dex */
public class LifecycleResourceManager extends ResourceManager {
    private static final String LOG_TAG = "LifecycleResourceManager";
    public static final int TIME_DAY = 86400000;
    public static final int TIME_HOUR = 3600000;
    private static long mLastCheckCacheTime;
    private long mCheckTime;
    private long mInactiveTime;

    public LifecycleResourceManager(ResourceLoader resourceLoader, long j, long j2) {
        super(resourceLoader);
        this.mInactiveTime = j;
        this.mCheckTime = j2;
    }

    public void checkCache() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - mLastCheckCacheTime < this.mCheckTime) {
            return;
        }
        Log.d(LOG_TAG, "begin check cache... ");
        ArrayList<String> arrayList = new ArrayList();
        synchronized (this.mBitmapKeysLock) {
            for (String str : this.mBitmapKeys) {
                ResourceManager.BitmapInfo bitmapInfo = sBitmapsCache.get(str);
                if (bitmapInfo != null && jCurrentTimeMillis - bitmapInfo.mLastVisitTime > this.mInactiveTime) {
                    arrayList.add(str);
                }
            }
            for (String str2 : arrayList) {
                Log.d(LOG_TAG, "remove cache: " + str2);
                sBitmapsCache.remove(str2);
                this.mBitmapKeys.remove(str2);
            }
        }
        mLastCheckCacheTime = jCurrentTimeMillis;
    }

    @Override // com.miui.maml.ResourceManager
    public void pause() {
        checkCache();
    }

    @Override // com.miui.maml.ResourceManager
    public void finish(boolean z) {
        if (z) {
            checkCache();
        }
        super.finish(z);
    }
}
