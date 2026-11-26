package com.miui.aiengine.common.cloud;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.aiengine.common.NetConfig;
import com.miui.aiengine.common.utils.LogUtils;
import com.miui.aiengine.common.utils.Logger;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.OkHttpClient;

/* JADX INFO: compiled from: OkHttpClientAgent.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0016\u0018\u0000 \b2\u00020\u0001:\u0001\bB\u0011\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\t"}, d2 = {"Lcom/miui/aiengine/common/cloud/OkHttpClientAgent;", "Lcom/miui/aiengine/common/utils/Logger;", "mProxy", "Lokhttp3/OkHttpClient$Builder;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokhttp3/OkHttpClient$Builder;)V", "getMProxy", "()Lokhttp3/OkHttpClient$Builder;", "Companion", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class OkHttpClientAgent extends Logger {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final OkHttpClient.Builder mProxy;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected OkHttpClientAgent(OkHttpClient.Builder mProxy) {
        super(NetConfig.TAG);
        Intrinsics.checkNotNullParameter(mProxy, "mProxy");
        this.mProxy = mProxy;
    }

    public final OkHttpClient.Builder getMProxy() {
        return this.mProxy;
    }

    /* JADX INFO: compiled from: OkHttpClientAgent.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u0004\u001a\u00020\u0005J\b\u0010\u0006\u001a\u00020\u0007H\u0004¨\u0006\b"}, d2 = {"Lcom/miui/aiengine/common/cloud/OkHttpClientAgent$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "newInstance", "Lcom/miui/aiengine/common/cloud/OkHttpClientAgent;", "initClient", "Lokhttp3/OkHttpClient$Builder;", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final OkHttpClientAgent newInstance() {
            return new OkHttpClientAgent(initClient());
        }

        protected final OkHttpClient.Builder initClient() {
            LogUtils.INSTANCE.logDebug(NetConfig.TAG, "OkHttpClientAgent", "initClient");
            return new OkHttpClient.Builder();
        }
    }
}
