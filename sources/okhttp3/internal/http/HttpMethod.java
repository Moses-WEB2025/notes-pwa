package okhttp3.internal.http;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.accountsdk.request.PassportSimpleRequest;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.ccil.cowan.tagsoup.XMLWriter;

/* JADX INFO: compiled from: HttpMethod.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0007J\u0010\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0007J\u0010\u0010\t\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0007J\u000e\u0010\n\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007J\u000e\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\f"}, d2 = {"Lokhttp3/internal/http/HttpMethod;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "invalidatesCache", "", XMLWriter.METHOD, "", "requiresRequestBody", "permitsRequestBody", "redirectsWithBody", "redirectsToGet", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class HttpMethod {
    public static final HttpMethod INSTANCE = new HttpMethod();

    private HttpMethod() {
    }

    @JvmStatic
    public static final boolean invalidatesCache(String method) {
        Intrinsics.checkNotNullParameter(method, "method");
        return Intrinsics.areEqual(method, PassportSimpleRequest.HTTP_METHOD_POST) || Intrinsics.areEqual(method, "PATCH") || Intrinsics.areEqual(method, "PUT") || Intrinsics.areEqual(method, "DELETE") || Intrinsics.areEqual(method, "MOVE");
    }

    @JvmStatic
    public static final boolean requiresRequestBody(String method) {
        Intrinsics.checkNotNullParameter(method, "method");
        return Intrinsics.areEqual(method, PassportSimpleRequest.HTTP_METHOD_POST) || Intrinsics.areEqual(method, "PUT") || Intrinsics.areEqual(method, "PATCH") || Intrinsics.areEqual(method, "PROPPATCH") || Intrinsics.areEqual(method, "REPORT");
    }

    @JvmStatic
    public static final boolean permitsRequestBody(String method) {
        Intrinsics.checkNotNullParameter(method, "method");
        return (Intrinsics.areEqual(method, PassportSimpleRequest.HTTP_METHOD_GET) || Intrinsics.areEqual(method, "HEAD")) ? false : true;
    }

    public final boolean redirectsWithBody(String method) {
        Intrinsics.checkNotNullParameter(method, "method");
        return Intrinsics.areEqual(method, "PROPFIND");
    }

    public final boolean redirectsToGet(String method) {
        Intrinsics.checkNotNullParameter(method, "method");
        return !Intrinsics.areEqual(method, "PROPFIND");
    }
}
