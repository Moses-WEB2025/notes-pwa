package com.miui.aiengine.common.utils;

import android.os.SystemClock;
import android.text.TextUtils;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SyncRequestParam.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u000b\u001a\u00020\fJ\u0006\u0010\r\u001a\u00020\fJN\u0010\u000e\u001a\u00020\f2\"\u0010\u000f\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0010j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005`\u00112\"\u0010\u0012\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0010j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005`\u0011R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/miui/aiengine/common/utils/SyncRequestParam;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "LOCAL_MAX_MANUAL_SYNC_INHERITANCE_DURATION_IN_MILLIS", "", "sLastManualSyncTimeInMillis", "sManualSyncCounter", "Ljava/util/concurrent/atomic/AtomicInteger;", "incrementManualSyncCountAndRecordCurMillis", "", "decrementManualSyncCount", "addParam", "headerParams", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "params", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class SyncRequestParam {
    private static final long LOCAL_MAX_MANUAL_SYNC_INHERITANCE_DURATION_IN_MILLIS = 300000;
    private static final String TAG = "SyncRequestParam";
    public static final SyncRequestParam INSTANCE = new SyncRequestParam();
    private static long sLastManualSyncTimeInMillis = -1;
    private static final AtomicInteger sManualSyncCounter = new AtomicInteger(0);

    private SyncRequestParam() {
    }

    public final void incrementManualSyncCountAndRecordCurMillis() {
        sManualSyncCounter.incrementAndGet();
        sLastManualSyncTimeInMillis = SystemClock.uptimeMillis();
    }

    public final void decrementManualSyncCount() {
        sManualSyncCounter.decrementAndGet();
    }

    public final void addParam(HashMap<String, String> headerParams, HashMap<String, String> params) {
        long jUptimeMillis;
        Intrinsics.checkNotNullParameter(headerParams, "headerParams");
        Intrinsics.checkNotNullParameter(params, "params");
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
        if (TextUtils.isEmpty(headerParams.get("backend"))) {
            headerParams.put("backend", String.valueOf(z));
        }
        if (TextUtils.isEmpty(params.get("_backend"))) {
            params.put("_backend", String.valueOf(z));
        }
        if (TextUtils.isEmpty(params.get("_delta"))) {
            params.put("_delta", String.valueOf(jUptimeMillis));
        }
        if (TextUtils.isEmpty(params.get("_count"))) {
            params.put("_count", String.valueOf(i));
        }
    }
}
