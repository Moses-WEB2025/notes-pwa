package okhttp3.internal.cache;

import com.xiaomi.accountsdk.request.PassportSimpleRequest;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.HttpUrl;
import okhttp3.Request;

/* JADX INFO: compiled from: CacheInterceptor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0001H\u0002¨\u0006\u0002"}, d2 = {"requestForCache", "Lokhttp3/Request;", "okhttp"}, k = 2, mv = {2, 2, 0}, xi = 48)
public final class CacheInterceptorKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final Request requestForCache(Request request) {
        HttpUrl cacheUrlOverride = request.getCacheUrlOverride();
        if (cacheUrlOverride != null) {
            return (Intrinsics.areEqual(request.method(), PassportSimpleRequest.HTTP_METHOD_GET) || Intrinsics.areEqual(request.method(), PassportSimpleRequest.HTTP_METHOD_POST)) ? request.newBuilder().get().url(cacheUrlOverride).cacheUrlOverride(null).build() : request;
        }
        return request;
    }
}
