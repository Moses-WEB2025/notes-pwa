package com.miui.aiengine.common.cloud;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.synergy.CallMethod;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CloudStatManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\fJ2\u0010\u0010\u001a\u00020\u000e2\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0012R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lcom/miui/aiengine/common/cloud/CloudStatManager;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "enable", "", "getEnable", "()Z", "setEnable", "(Z)V", "mIsInitialized", "mCldStatCallback", "Lcom/miui/aiengine/common/cloud/CloudStatCallback;", "init", "", CallMethod.ARG_CALLBACK, "addHttpEvent", "url", "", "timeCost", "", "netFlow", "responseCode", "", "exceptionName", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class CloudStatManager {
    public static final CloudStatManager INSTANCE = new CloudStatManager();
    private static boolean enable;
    private static CloudStatCallback mCldStatCallback;
    private static boolean mIsInitialized;

    private CloudStatManager() {
    }

    public final boolean getEnable() {
        return enable;
    }

    public final void setEnable(boolean z) {
        enable = z;
    }

    public final void init(CloudStatCallback callback) {
        mCldStatCallback = callback;
        enable = true;
        Intrinsics.checkNotNull(callback);
        callback.onInitialize();
        mIsInitialized = true;
        CloudStatCallback cloudStatCallback = mCldStatCallback;
        Intrinsics.checkNotNull(cloudStatCallback);
        cloudStatCallback.onSetUploadPolicy();
        CloudStatCallback cloudStatCallback2 = mCldStatCallback;
        Intrinsics.checkNotNull(cloudStatCallback2);
        cloudStatCallback2.onSetEventFilter();
        CloudStatCallback cloudStatCallback3 = mCldStatCallback;
        Intrinsics.checkNotNull(cloudStatCallback3);
        cloudStatCallback3.onEnableAutoRecord();
    }

    public final void addHttpEvent(String url, long timeCost, long netFlow, int responseCode, String exceptionName) {
        CloudStatCallback cloudStatCallback;
        if (enable && mIsInitialized && (cloudStatCallback = mCldStatCallback) != null) {
            Intrinsics.checkNotNull(cloudStatCallback);
            cloudStatCallback.onAddHttpEvent(url, timeCost, netFlow, responseCode, exceptionName);
        }
    }
}
