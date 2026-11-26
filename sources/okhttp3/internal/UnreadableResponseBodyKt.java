package okhttp3.internal;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Response;

/* JADX INFO: compiled from: UnreadableResponseBody.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0001¨\u0006\u0002"}, d2 = {"stripBody", "Lokhttp3/Response;", "okhttp"}, k = 2, mv = {2, 2, 0}, xi = 48)
public final class UnreadableResponseBodyKt {
    public static final Response stripBody(Response response) {
        Intrinsics.checkNotNullParameter(response, "<this>");
        return response.newBuilder().body(new UnreadableResponseBody(response.body().getMediaType(), response.body().getContentLength())).build();
    }
}
