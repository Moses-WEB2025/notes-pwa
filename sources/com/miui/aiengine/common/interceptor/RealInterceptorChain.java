package com.miui.aiengine.common.interceptor;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.aiengine.common.NetConfig;
import com.miui.aiengine.common.Result;
import com.miui.aiengine.common.request.HttpRequest;
import com.miui.aiengine.common.utils.Logger;
import com.xiaomi.accountsdk.account.stat.StatConstants;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Interceptor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B%\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0004\b\n\u0010\u000bJ\b\u0010\u0006\u001a\u00020\u0007H\u0016J\u0012\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0006\u001a\u00020\u0007H\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/miui/aiengine/common/interceptor/RealInterceptorChain;", "Lcom/miui/aiengine/common/interceptor/Interceptor$Chain;", "Lcom/miui/aiengine/common/utils/Logger;", "interceptors", "", "Lcom/miui/aiengine/common/interceptor/Interceptor;", StatConstants.Event.REQUEST, "Lcom/miui/aiengine/common/request/HttpRequest;", "index", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/util/List;Lcom/miui/aiengine/common/request/HttpRequest;I)V", "proceed", "Lcom/miui/aiengine/common/Result;", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class RealInterceptorChain extends Logger implements Interceptor.Chain {
    private final int index;
    private final List<Interceptor> interceptors;
    private final HttpRequest request;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public RealInterceptorChain(List<? extends Interceptor> interceptors, HttpRequest request, int i) {
        super(NetConfig.TAG);
        Intrinsics.checkNotNullParameter(interceptors, "interceptors");
        Intrinsics.checkNotNullParameter(request, "request");
        this.interceptors = interceptors;
        this.request = request;
        this.index = i;
    }

    @Override // com.miui.aiengine.common.interceptor.Interceptor.Chain
    /* JADX INFO: renamed from: request, reason: from getter */
    public HttpRequest getRequest() {
        return this.request;
    }

    @Override // com.miui.aiengine.common.interceptor.Interceptor.Chain
    public Result proceed(HttpRequest request) {
        Intrinsics.checkNotNullParameter(request, "request");
        try {
            if (this.index >= this.interceptors.size()) {
                throw new IllegalStateException("no more interceptors".toString());
            }
            Interceptor interceptor = this.interceptors.get(this.index);
            logDebug(interceptor.getName() + "/handle-> " + this.index + " / " + this.interceptors.size());
            Result resultIntercept = interceptor.intercept(new RealInterceptorChain(this.interceptors, request, this.index + 1));
            logDebug(interceptor.getName() + "->resp:" + resultIntercept);
            return resultIntercept;
        } catch (IllegalStateException unused) {
            return null;
        }
    }
}
