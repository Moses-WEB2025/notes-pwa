package com.miui.aiengine.common.cloud;

import android.os.Looper;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NetEventStatInjector.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u001e2\u00020\u0001:\u0002\u001d\u001eB\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u0007J\u0010\u0010\u000e\u001a\u00020\f2\b\u0010\u000f\u001a\u0004\u0018\u00010\nJ\u0010\u0010\u0010\u001a\u00020\f2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012J\u0010\u0010\u0013\u001a\u00020\f2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0014J\u001a\u0010\u0015\u001a\u00020\u00052\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0017J\u000e\u0010\u0019\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u001aJ\u000e\u0010\u001b\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u001cR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lcom/miui/aiengine/common/cloud/NetEventStatInjector;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mIsInitialized", "", "mIMiCloudStatCallback", "Lcom/miui/aiengine/common/cloud/NetEventStatCallback;", "mIsDownloadCallbackInitialized", "mIMiCloudDownloadFileNetEventStatCallback", "Lcom/miui/aiengine/common/cloud/ICloudDownloadFileNetEventStatCallback;", "init", "", "iMiCloudStatCallback", "initDownloadFile", "iMiCloudDownloadFileNetEventStatCallback", "addNetSuccessEvent", "param", "Lcom/miui/aiengine/common/cloud/NetSuccessStatParam;", "addNetFailedEvent", "Lcom/miui/aiengine/common/cloud/NetFailedStatParam;", "isGetDownloadFileRequestUrl", "url", "", "httpMethod", "addGetDownloadFileUrlsFailedEvent", "Lcom/miui/aiengine/common/cloud/GetDownloadFileUrlFailedStatParam;", "addAddDownloadFileFailedEvent", "Lcom/miui/aiengine/common/cloud/DownloadFileFailedStatParam;", "MiCloudNetEventStatInjectorHolder", "Companion", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class NetEventStatInjector {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private ICloudDownloadFileNetEventStatCallback mIMiCloudDownloadFileNetEventStatCallback;
    private NetEventStatCallback mIMiCloudStatCallback;
    private boolean mIsDownloadCallbackInitialized;
    private boolean mIsInitialized;

    public /* synthetic */ NetEventStatInjector(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private NetEventStatInjector() {
    }

    public final void init(NetEventStatCallback iMiCloudStatCallback) {
        if (!Intrinsics.areEqual(Looper.getMainLooper().getThread(), Thread.currentThread())) {
            throw new IllegalStateException("init() must be invoked in main thread".toString());
        }
        if (this.mIsInitialized) {
            return;
        }
        this.mIMiCloudStatCallback = iMiCloudStatCallback;
        this.mIsInitialized = true;
    }

    public final void initDownloadFile(ICloudDownloadFileNetEventStatCallback iMiCloudDownloadFileNetEventStatCallback) {
        if (!Intrinsics.areEqual(Looper.getMainLooper().getThread(), Thread.currentThread())) {
            throw new IllegalStateException("initDownloadFile() must be invoked in main thread".toString());
        }
        if (this.mIsDownloadCallbackInitialized) {
            return;
        }
        this.mIMiCloudDownloadFileNetEventStatCallback = iMiCloudDownloadFileNetEventStatCallback;
        this.mIsDownloadCallbackInitialized = true;
    }

    public final void addNetSuccessEvent(NetSuccessStatParam param) {
        NetEventStatCallback netEventStatCallback = this.mIMiCloudStatCallback;
        if (netEventStatCallback != null) {
            Intrinsics.checkNotNull(netEventStatCallback);
            netEventStatCallback.onAddNetSuccessEvent(param);
        }
    }

    public final void addNetFailedEvent(NetFailedStatParam param) {
        NetEventStatCallback netEventStatCallback = this.mIMiCloudStatCallback;
        if (netEventStatCallback != null) {
            Intrinsics.checkNotNull(netEventStatCallback);
            netEventStatCallback.onAddNetFailedEvent(param);
        }
    }

    public final boolean isGetDownloadFileRequestUrl(String url, String httpMethod) {
        ICloudDownloadFileNetEventStatCallback iCloudDownloadFileNetEventStatCallback = this.mIMiCloudDownloadFileNetEventStatCallback;
        if (iCloudDownloadFileNetEventStatCallback == null) {
            return false;
        }
        Intrinsics.checkNotNull(iCloudDownloadFileNetEventStatCallback);
        return iCloudDownloadFileNetEventStatCallback.isGetDownloadFileRequestUrl(url, httpMethod);
    }

    public final void addGetDownloadFileUrlsFailedEvent(GetDownloadFileUrlFailedStatParam param) {
        Intrinsics.checkNotNullParameter(param, "param");
        ICloudDownloadFileNetEventStatCallback iCloudDownloadFileNetEventStatCallback = this.mIMiCloudDownloadFileNetEventStatCallback;
        if (iCloudDownloadFileNetEventStatCallback != null) {
            Intrinsics.checkNotNull(iCloudDownloadFileNetEventStatCallback);
            iCloudDownloadFileNetEventStatCallback.onAddGetDownloadFileUrlsFailedEvent(param);
        }
    }

    public final void addAddDownloadFileFailedEvent(DownloadFileFailedStatParam param) {
        Intrinsics.checkNotNullParameter(param, "param");
        ICloudDownloadFileNetEventStatCallback iCloudDownloadFileNetEventStatCallback = this.mIMiCloudDownloadFileNetEventStatCallback;
        if (iCloudDownloadFileNetEventStatCallback != null) {
            Intrinsics.checkNotNull(iCloudDownloadFileNetEventStatCallback);
            iCloudDownloadFileNetEventStatCallback.onAddDownloadFileFailedEvent(param);
        }
    }

    /* JADX INFO: compiled from: NetEventStatInjector.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bÂ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lcom/miui/aiengine/common/cloud/NetEventStatInjector$MiCloudNetEventStatInjectorHolder;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "instance", "Lcom/miui/aiengine/common/cloud/NetEventStatInjector;", "getInstance", "()Lcom/miui/aiengine/common/cloud/NetEventStatInjector;", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private static final class MiCloudNetEventStatInjectorHolder {
        public static final MiCloudNetEventStatInjectorHolder INSTANCE = new MiCloudNetEventStatInjectorHolder();
        private static final NetEventStatInjector instance = new NetEventStatInjector(null);

        private MiCloudNetEventStatInjectorHolder() {
        }

        public final NetEventStatInjector getInstance() {
            return instance;
        }
    }

    /* JADX INFO: compiled from: NetEventStatInjector.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0011\u0010\u0004\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lcom/miui/aiengine/common/cloud/NetEventStatInjector$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "instance", "Lcom/miui/aiengine/common/cloud/NetEventStatInjector;", "getInstance", "()Lcom/miui/aiengine/common/cloud/NetEventStatInjector;", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final NetEventStatInjector getInstance() {
            return MiCloudNetEventStatInjectorHolder.INSTANCE.getInstance();
        }
    }
}
