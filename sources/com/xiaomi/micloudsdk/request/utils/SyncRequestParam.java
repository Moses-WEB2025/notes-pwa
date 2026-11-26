package com.xiaomi.micloudsdk.request.utils;

import android.os.SystemClock;
import android.text.TextUtils;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes4.dex */
public class SyncRequestParam {
    private static final long LOCAL_MAX_MANUAL_SYNC_INHERITANCE_DURATION_IN_MILLIS = 300000;
    private static final String TAG = "SyncRequestParam";
    private static volatile long sLastManualSyncTimeInMillis = -1;
    private static AtomicInteger sManualSyncCounter = new AtomicInteger(0);

    public static void incrementManualSyncCountAndRecordCurMillis() {
        sManualSyncCounter.incrementAndGet();
        sLastManualSyncTimeInMillis = SystemClock.uptimeMillis();
    }

    public static void decrementManualSyncCount() {
        sManualSyncCounter.decrementAndGet();
    }

    static void addParam(Map<String, String> map, Map<String, String> map2) {
        long jUptimeMillis;
        long j = sLastManualSyncTimeInMillis;
        boolean z = true;
        if (j != -1) {
            jUptimeMillis = SystemClock.uptimeMillis() - j;
            if (jUptimeMillis <= 300000) {
                z = false;
            }
        } else {
            jUptimeMillis = 0;
        }
        int i = sManualSyncCounter.get();
        if (TextUtils.isEmpty(map.get("backend"))) {
            map.put("backend", Boolean.toString(z));
        }
        if (TextUtils.isEmpty(map2.get("_backend"))) {
            map2.put("_backend", Boolean.toString(z));
        }
        if (TextUtils.isEmpty(map2.get("_delta"))) {
            map2.put("_delta", Long.toString(jUptimeMillis));
        }
        if (TextUtils.isEmpty(map2.get("_count"))) {
            map2.put("_count", Integer.toString(i));
        }
    }
}
