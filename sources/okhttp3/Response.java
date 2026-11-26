package okhttp3;

import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.accountsdk.account.stat.StatConstants;
import com.xiaomi.onetrack.api.ah;
import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import miuix.navigation.SplitLayout;
import okhttp3.internal.connection.Exchange;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;
import okio.Source;

/* JADX INFO: compiled from: Response.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001MB\u0083\u0001\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0000\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u0000\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0000\u0012\u0006\u0010\u0013\u001a\u00020\u0014\u0012\u0006\u0010\u0015\u001a\u00020\u0014\u0012\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017\u0012\u0006\u0010\u0018\u001a\u00020\u0019¢\u0006\u0004\b\u001a\u0010\u001bJ\r\u0010\u0002\u001a\u00020\u0003H\u0007¢\u0006\u0002\b,J\r\u0010\u0004\u001a\u00020\u0005H\u0007¢\u0006\u0002\b-J\r\u0010\b\u001a\u00020\tH\u0007¢\u0006\u0002\b.J\r\u0010\u0006\u001a\u00020\u0007H\u0007¢\u0006\u0002\b2J\u000f\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0007¢\u0006\u0002\b3J\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u0007042\u0006\u00105\u001a\u00020\u0007J\u001e\u00106\u001a\u0004\u0018\u00010\u00072\u0006\u00105\u001a\u00020\u00072\n\b\u0002\u00107\u001a\u0004\u0018\u00010\u0007H\u0007J\r\u0010\f\u001a\u00020\rH\u0007¢\u0006\u0002\b8J\u0006\u00109\u001a\u00020\rJ\b\u0010:\u001a\u0004\u0018\u00010\rJ\u000e\u0010;\u001a\u00020\u000f2\u0006\u0010<\u001a\u00020\u0014J\r\u0010\u000e\u001a\u00020\u000fH\u0007¢\u0006\u0002\b=J\u0006\u0010>\u001a\u00020?J\u000f\u0010\u0010\u001a\u0004\u0018\u00010\u0000H\u0007¢\u0006\u0002\bAJ\u000f\u0010\u0011\u001a\u0004\u0018\u00010\u0000H\u0007¢\u0006\u0002\bBJ\u000f\u0010\u0012\u001a\u0004\u0018\u00010\u0000H\u0007¢\u0006\u0002\bCJ\f\u0010D\u001a\b\u0012\u0004\u0012\u00020E04J\r\u0010F\u001a\u00020'H\u0007¢\u0006\u0002\bGJ\r\u0010\u0013\u001a\u00020\u0014H\u0007¢\u0006\u0002\bHJ\r\u0010\u0015\u001a\u00020\u0014H\u0007¢\u0006\u0002\bIJ\b\u0010J\u001a\u00020KH\u0016J\b\u0010L\u001a\u00020\u0007H\u0016R\u0013\u0010\u0002\u001a\u00020\u00038G¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u001cR\u0013\u0010\u0004\u001a\u00020\u00058G¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u001dR\u0013\u0010\u0006\u001a\u00020\u00078G¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u001eR\u0013\u0010\b\u001a\u00020\t8G¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u001fR\u0015\u0010\n\u001a\u0004\u0018\u00010\u000b8G¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010 R\u0013\u0010\f\u001a\u00020\r8G¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010!R\u0013\u0010\u000e\u001a\u00020\u000f8G¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\"R\u0015\u0010\u0010\u001a\u0004\u0018\u00010\u00008G¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010#R\u0015\u0010\u0011\u001a\u0004\u0018\u00010\u00008G¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010#R\u0015\u0010\u0012\u001a\u0004\u0018\u00010\u00008G¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010#R\u0013\u0010\u0013\u001a\u00020\u00148G¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010$R\u0013\u0010\u0015\u001a\u00020\u00148G¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010$R\u0018\u0010\u0016\u001a\u0004\u0018\u00010\u00178AX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010%R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010&\u001a\u0004\u0018\u00010'X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u0011\u0010/\u001a\u000200¢\u0006\b\n\u0000\u001a\u0004\b/\u00101R\u0011\u0010@\u001a\u000200¢\u0006\b\n\u0000\u001a\u0004\b@\u00101R\u0011\u0010F\u001a\u00020'8G¢\u0006\u0006\u001a\u0004\bF\u0010)¨\u0006N"}, d2 = {"Lokhttp3/Response;", "Ljava/io/Closeable;", StatConstants.Event.REQUEST, "Lokhttp3/Request;", "protocol", "Lokhttp3/Protocol;", "message", "", "code", "", ah.R, "Lokhttp3/Handshake;", "headers", "Lokhttp3/Headers;", "body", "Lokhttp3/ResponseBody;", "networkResponse", "cacheResponse", "priorResponse", "sentRequestAtMillis", "", "receivedResponseAtMillis", "exchange", "Lokhttp3/internal/connection/Exchange;", "trailersSource", "Lokhttp3/TrailersSource;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokhttp3/Request;Lokhttp3/Protocol;Ljava/lang/String;ILokhttp3/Handshake;Lokhttp3/Headers;Lokhttp3/ResponseBody;Lokhttp3/Response;Lokhttp3/Response;Lokhttp3/Response;JJLokhttp3/internal/connection/Exchange;Lokhttp3/TrailersSource;)V", "()Lokhttp3/Request;", "()Lokhttp3/Protocol;", "()Ljava/lang/String;", "()I", "()Lokhttp3/Handshake;", "()Lokhttp3/Headers;", "()Lokhttp3/ResponseBody;", "()Lokhttp3/Response;", "()J", "()Lokhttp3/internal/connection/Exchange;", "lazyCacheControl", "Lokhttp3/CacheControl;", "getLazyCacheControl$okhttp", "()Lokhttp3/CacheControl;", "setLazyCacheControl$okhttp", "(Lokhttp3/CacheControl;)V", "-deprecated_request", "-deprecated_protocol", "-deprecated_code", "isSuccessful", "", "()Z", "-deprecated_message", "-deprecated_handshake", "", "name", "header", "defaultValue", "-deprecated_headers", "trailers", "peekTrailers", "peekBody", "byteCount", "-deprecated_body", "newBuilder", "Lokhttp3/Response$Builder;", "isRedirect", "-deprecated_networkResponse", "-deprecated_cacheResponse", "-deprecated_priorResponse", "challenges", "Lokhttp3/Challenge;", "cacheControl", "-deprecated_cacheControl", "-deprecated_sentRequestAtMillis", "-deprecated_receivedResponseAtMillis", SplitLayout.TAG_CLOSE, "", "toString", "Builder", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class Response implements Closeable {
    private final ResponseBody body;
    private final Response cacheResponse;
    private final int code;
    private final Exchange exchange;
    private final Handshake handshake;
    private final Headers headers;
    private final boolean isRedirect;
    private final boolean isSuccessful;
    private CacheControl lazyCacheControl;
    private final String message;
    private final Response networkResponse;
    private final Response priorResponse;
    private final Protocol protocol;
    private final long receivedResponseAtMillis;
    private final Request request;
    private final long sentRequestAtMillis;
    private TrailersSource trailersSource;

    public final String header(String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return header$default(this, name, null, 2, null);
    }

    public Response(Request request, Protocol protocol, String message, int i, Handshake handshake, Headers headers, ResponseBody body, Response response, Response response2, Response response3, long j, long j2, Exchange exchange, TrailersSource trailersSource) {
        Intrinsics.checkNotNullParameter(request, "request");
        Intrinsics.checkNotNullParameter(protocol, "protocol");
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(headers, "headers");
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(trailersSource, "trailersSource");
        this.request = request;
        this.protocol = protocol;
        this.message = message;
        this.code = i;
        this.handshake = handshake;
        this.headers = headers;
        this.body = body;
        this.networkResponse = response;
        this.cacheResponse = response2;
        this.priorResponse = response3;
        this.sentRequestAtMillis = j;
        this.receivedResponseAtMillis = j2;
        this.exchange = exchange;
        this.trailersSource = trailersSource;
        boolean z = true;
        this.isSuccessful = 200 <= i && i < 300;
        if (i != 307 && i != 308) {
            switch (i) {
                case 300:
                case 301:
                case 302:
                case 303:
                    break;
                default:
                    z = false;
                    break;
            }
        }
        this.isRedirect = z;
    }

    public final Request request() {
        return this.request;
    }

    public final Protocol protocol() {
        return this.protocol;
    }

    public final String message() {
        return this.message;
    }

    public final int code() {
        return this.code;
    }

    public final Handshake handshake() {
        return this.handshake;
    }

    public final Headers headers() {
        return this.headers;
    }

    public final ResponseBody body() {
        return this.body;
    }

    public final Response networkResponse() {
        return this.networkResponse;
    }

    public final Response cacheResponse() {
        return this.cacheResponse;
    }

    public final Response priorResponse() {
        return this.priorResponse;
    }

    public final long sentRequestAtMillis() {
        return this.sentRequestAtMillis;
    }

    public final long receivedResponseAtMillis() {
        return this.receivedResponseAtMillis;
    }

    /* JADX INFO: renamed from: exchange, reason: from getter */
    public final Exchange getExchange() {
        return this.exchange;
    }

    /* JADX INFO: renamed from: getLazyCacheControl$okhttp, reason: from getter */
    public final CacheControl getLazyCacheControl() {
        return this.lazyCacheControl;
    }

    public final void setLazyCacheControl$okhttp(CacheControl cacheControl) {
        this.lazyCacheControl = cacheControl;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = StatConstants.Event.REQUEST, imports = {}))
    /* JADX INFO: renamed from: -deprecated_request, reason: not valid java name and from getter */
    public final Request getRequest() {
        return this.request;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "protocol", imports = {}))
    /* JADX INFO: renamed from: -deprecated_protocol, reason: not valid java name and from getter */
    public final Protocol getProtocol() {
        return this.protocol;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "code", imports = {}))
    /* JADX INFO: renamed from: -deprecated_code, reason: not valid java name and from getter */
    public final int getCode() {
        return this.code;
    }

    /* JADX INFO: renamed from: isSuccessful, reason: from getter */
    public final boolean getIsSuccessful() {
        return this.isSuccessful;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "message", imports = {}))
    /* JADX INFO: renamed from: -deprecated_message, reason: not valid java name and from getter */
    public final String getMessage() {
        return this.message;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = ah.R, imports = {}))
    /* JADX INFO: renamed from: -deprecated_handshake, reason: not valid java name and from getter */
    public final Handshake getHandshake() {
        return this.handshake;
    }

    public final List<String> headers(String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return this.headers.values(name);
    }

    public static /* synthetic */ String header$default(Response response, String str, String str2, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = null;
        }
        return response.header(str, str2);
    }

    public final String header(String name, String defaultValue) {
        Intrinsics.checkNotNullParameter(name, "name");
        String str = this.headers.get(name);
        return str == null ? defaultValue : str;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "headers", imports = {}))
    /* JADX INFO: renamed from: -deprecated_headers, reason: not valid java name and from getter */
    public final Headers getHeaders() {
        return this.headers;
    }

    public final Headers trailers() throws IOException {
        return this.trailersSource.get();
    }

    public final Headers peekTrailers() throws IOException {
        return this.trailersSource.peek();
    }

    public final ResponseBody peekBody(long byteCount) throws IOException {
        BufferedSource bufferedSourcePeek = this.body.source().peek();
        Buffer buffer = new Buffer();
        bufferedSourcePeek.request(byteCount);
        buffer.write((Source) bufferedSourcePeek, Math.min(byteCount, bufferedSourcePeek.getBuffer().size()));
        return ResponseBody.INSTANCE.create(buffer, this.body.contentType(), buffer.size());
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "body", imports = {}))
    /* JADX INFO: renamed from: -deprecated_body, reason: not valid java name and from getter */
    public final ResponseBody getBody() {
        return this.body;
    }

    public final Builder newBuilder() {
        return new Builder(this);
    }

    /* JADX INFO: renamed from: isRedirect, reason: from getter */
    public final boolean getIsRedirect() {
        return this.isRedirect;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "networkResponse", imports = {}))
    /* JADX INFO: renamed from: -deprecated_networkResponse, reason: not valid java name and from getter */
    public final Response getNetworkResponse() {
        return this.networkResponse;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "cacheResponse", imports = {}))
    /* JADX INFO: renamed from: -deprecated_cacheResponse, reason: not valid java name and from getter */
    public final Response getCacheResponse() {
        return this.cacheResponse;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "priorResponse", imports = {}))
    /* JADX INFO: renamed from: -deprecated_priorResponse, reason: not valid java name and from getter */
    public final Response getPriorResponse() {
        return this.priorResponse;
    }

    public final List<Challenge> challenges() {
        String str;
        Headers headers = this.headers;
        int i = this.code;
        if (i == 401) {
            str = "WWW-Authenticate";
        } else if (i == 407) {
            str = "Proxy-Authenticate";
        } else {
            return CollectionsKt.emptyList();
        }
        return HttpHeaders.parseChallenges(headers, str);
    }

    public final CacheControl cacheControl() {
        CacheControl cacheControl = this.lazyCacheControl;
        if (cacheControl != null) {
            return cacheControl;
        }
        CacheControl cacheControl2 = CacheControl.INSTANCE.parse(this.headers);
        this.lazyCacheControl = cacheControl2;
        return cacheControl2;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "cacheControl", imports = {}))
    /* JADX INFO: renamed from: -deprecated_cacheControl, reason: not valid java name */
    public final CacheControl m4494deprecated_cacheControl() {
        return cacheControl();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "sentRequestAtMillis", imports = {}))
    /* JADX INFO: renamed from: -deprecated_sentRequestAtMillis, reason: not valid java name and from getter */
    public final long getSentRequestAtMillis() {
        return this.sentRequestAtMillis;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "receivedResponseAtMillis", imports = {}))
    /* JADX INFO: renamed from: -deprecated_receivedResponseAtMillis, reason: not valid java name and from getter */
    public final long getReceivedResponseAtMillis() {
        return this.receivedResponseAtMillis;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.body.close();
    }

    public String toString() {
        return "Response{protocol=" + this.protocol + ", code=" + this.code + ", message=" + this.message + ", url=" + this.request.url() + CoreConstants.CURLY_RIGHT;
    }

    /* JADX INFO: compiled from: Response.kt */
    @Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0016\u0018\u00002\u00020\u0001B\t\b\u0016¢\u0006\u0004\b\u0002\u0010\u0003B\u0011\b\u0010\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\r\u001a\u00020\u00002\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u0013\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0019\u001a\u00020\u00002\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\u0012\u0010\u001f\u001a\u00020\u00002\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0016J\u0018\u0010P\u001a\u00020\u00002\u0006\u0010Q\u001a\u00020\u001a2\u0006\u0010R\u001a\u00020\u001aH\u0016J\u0018\u0010S\u001a\u00020\u00002\u0006\u0010Q\u001a\u00020\u001a2\u0006\u0010R\u001a\u00020\u001aH\u0016J\u0010\u0010T\u001a\u00020\u00002\u0006\u0010Q\u001a\u00020\u001aH\u0016J\u0010\u0010%\u001a\u00020\u00002\u0006\u0010%\u001a\u00020UH\u0016J\u0010\u0010+\u001a\u00020\u00002\u0006\u0010+\u001a\u00020,H\u0016J\u0012\u00101\u001a\u00020\u00002\b\u00101\u001a\u0004\u0018\u00010\u0005H\u0016J\u0012\u00105\u001a\u00020\u00002\b\u00105\u001a\u0004\u0018\u00010\u0005H\u0016J\u001a\u0010V\u001a\u00020W2\u0006\u0010Q\u001a\u00020\u001a2\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0002J\u0012\u00108\u001a\u00020\u00002\b\u00108\u001a\u0004\u0018\u00010\u0005H\u0016J\u0010\u0010X\u001a\u00020\u00002\u0006\u0010J\u001a\u00020KH\u0016J\u0010\u0010;\u001a\u00020\u00002\u0006\u0010;\u001a\u00020<H\u0016J\u0010\u0010A\u001a\u00020\u00002\u0006\u0010A\u001a\u00020<H\u0016J\u0015\u0010Y\u001a\u00020W2\u0006\u0010D\u001a\u00020EH\u0000¢\u0006\u0002\bZJ\b\u0010[\u001a\u00020\u0005H\u0016R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001c\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\u00020\u0014X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001c\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001c\u0010\u001f\u001a\u0004\u0018\u00010 X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u001a\u0010%\u001a\u00020&X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\u001a\u0010+\u001a\u00020,X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\u001c\u00101\u001a\u0004\u0018\u00010\u0005X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u00103\"\u0004\b4\u0010\u0006R\u001c\u00105\u001a\u0004\u0018\u00010\u0005X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b6\u00103\"\u0004\b7\u0010\u0006R\u001c\u00108\u001a\u0004\u0018\u00010\u0005X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u00103\"\u0004\b:\u0010\u0006R\u001a\u0010;\u001a\u00020<X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b=\u0010>\"\u0004\b?\u0010@R\u001a\u0010A\u001a\u00020<X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bB\u0010>\"\u0004\bC\u0010@R\u001c\u0010D\u001a\u0004\u0018\u00010EX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bF\u0010G\"\u0004\bH\u0010IR\u001a\u0010J\u001a\u00020KX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bL\u0010M\"\u0004\bN\u0010O¨\u0006\\"}, d2 = {"Lokhttp3/Response$Builder;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", ah.H, "Lokhttp3/Response;", "(Lokhttp3/Response;)V", StatConstants.Event.REQUEST, "Lokhttp3/Request;", "getRequest$okhttp", "()Lokhttp3/Request;", "setRequest$okhttp", "(Lokhttp3/Request;)V", "protocol", "Lokhttp3/Protocol;", "getProtocol$okhttp", "()Lokhttp3/Protocol;", "setProtocol$okhttp", "(Lokhttp3/Protocol;)V", "code", "", "getCode$okhttp", "()I", "setCode$okhttp", "(I)V", "message", "", "getMessage$okhttp", "()Ljava/lang/String;", "setMessage$okhttp", "(Ljava/lang/String;)V", ah.R, "Lokhttp3/Handshake;", "getHandshake$okhttp", "()Lokhttp3/Handshake;", "setHandshake$okhttp", "(Lokhttp3/Handshake;)V", "headers", "Lokhttp3/Headers$Builder;", "getHeaders$okhttp", "()Lokhttp3/Headers$Builder;", "setHeaders$okhttp", "(Lokhttp3/Headers$Builder;)V", "body", "Lokhttp3/ResponseBody;", "getBody$okhttp", "()Lokhttp3/ResponseBody;", "setBody$okhttp", "(Lokhttp3/ResponseBody;)V", "networkResponse", "getNetworkResponse$okhttp", "()Lokhttp3/Response;", "setNetworkResponse$okhttp", "cacheResponse", "getCacheResponse$okhttp", "setCacheResponse$okhttp", "priorResponse", "getPriorResponse$okhttp", "setPriorResponse$okhttp", "sentRequestAtMillis", "", "getSentRequestAtMillis$okhttp", "()J", "setSentRequestAtMillis$okhttp", "(J)V", "receivedResponseAtMillis", "getReceivedResponseAtMillis$okhttp", "setReceivedResponseAtMillis$okhttp", "exchange", "Lokhttp3/internal/connection/Exchange;", "getExchange$okhttp", "()Lokhttp3/internal/connection/Exchange;", "setExchange$okhttp", "(Lokhttp3/internal/connection/Exchange;)V", "trailersSource", "Lokhttp3/TrailersSource;", "getTrailersSource$okhttp", "()Lokhttp3/TrailersSource;", "setTrailersSource$okhttp", "(Lokhttp3/TrailersSource;)V", "header", "name", "value", "addHeader", "removeHeader", "Lokhttp3/Headers;", "checkSupportResponse", "", "trailers", "initExchange", "initExchange$okhttp", "build", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static class Builder {
        private ResponseBody body;
        private Response cacheResponse;
        private int code;
        private Exchange exchange;
        private Handshake handshake;
        private Headers.Builder headers;
        private String message;
        private Response networkResponse;
        private Response priorResponse;
        private Protocol protocol;
        private long receivedResponseAtMillis;
        private Request request;
        private long sentRequestAtMillis;
        private TrailersSource trailersSource;

        /* JADX INFO: renamed from: getRequest$okhttp, reason: from getter */
        public final Request getRequest() {
            return this.request;
        }

        public final void setRequest$okhttp(Request request) {
            this.request = request;
        }

        /* JADX INFO: renamed from: getProtocol$okhttp, reason: from getter */
        public final Protocol getProtocol() {
            return this.protocol;
        }

        public final void setProtocol$okhttp(Protocol protocol) {
            this.protocol = protocol;
        }

        /* JADX INFO: renamed from: getCode$okhttp, reason: from getter */
        public final int getCode() {
            return this.code;
        }

        public final void setCode$okhttp(int i) {
            this.code = i;
        }

        /* JADX INFO: renamed from: getMessage$okhttp, reason: from getter */
        public final String getMessage() {
            return this.message;
        }

        public final void setMessage$okhttp(String str) {
            this.message = str;
        }

        /* JADX INFO: renamed from: getHandshake$okhttp, reason: from getter */
        public final Handshake getHandshake() {
            return this.handshake;
        }

        public final void setHandshake$okhttp(Handshake handshake) {
            this.handshake = handshake;
        }

        /* JADX INFO: renamed from: getHeaders$okhttp, reason: from getter */
        public final Headers.Builder getHeaders() {
            return this.headers;
        }

        public final void setHeaders$okhttp(Headers.Builder builder) {
            Intrinsics.checkNotNullParameter(builder, "<set-?>");
            this.headers = builder;
        }

        /* JADX INFO: renamed from: getBody$okhttp, reason: from getter */
        public final ResponseBody getBody() {
            return this.body;
        }

        public final void setBody$okhttp(ResponseBody responseBody) {
            Intrinsics.checkNotNullParameter(responseBody, "<set-?>");
            this.body = responseBody;
        }

        /* JADX INFO: renamed from: getNetworkResponse$okhttp, reason: from getter */
        public final Response getNetworkResponse() {
            return this.networkResponse;
        }

        public final void setNetworkResponse$okhttp(Response response) {
            this.networkResponse = response;
        }

        /* JADX INFO: renamed from: getCacheResponse$okhttp, reason: from getter */
        public final Response getCacheResponse() {
            return this.cacheResponse;
        }

        public final void setCacheResponse$okhttp(Response response) {
            this.cacheResponse = response;
        }

        /* JADX INFO: renamed from: getPriorResponse$okhttp, reason: from getter */
        public final Response getPriorResponse() {
            return this.priorResponse;
        }

        public final void setPriorResponse$okhttp(Response response) {
            this.priorResponse = response;
        }

        /* JADX INFO: renamed from: getSentRequestAtMillis$okhttp, reason: from getter */
        public final long getSentRequestAtMillis() {
            return this.sentRequestAtMillis;
        }

        public final void setSentRequestAtMillis$okhttp(long j) {
            this.sentRequestAtMillis = j;
        }

        /* JADX INFO: renamed from: getReceivedResponseAtMillis$okhttp, reason: from getter */
        public final long getReceivedResponseAtMillis() {
            return this.receivedResponseAtMillis;
        }

        public final void setReceivedResponseAtMillis$okhttp(long j) {
            this.receivedResponseAtMillis = j;
        }

        /* JADX INFO: renamed from: getExchange$okhttp, reason: from getter */
        public final Exchange getExchange() {
            return this.exchange;
        }

        public final void setExchange$okhttp(Exchange exchange) {
            this.exchange = exchange;
        }

        /* JADX INFO: renamed from: getTrailersSource$okhttp, reason: from getter */
        public final TrailersSource getTrailersSource() {
            return this.trailersSource;
        }

        public final void setTrailersSource$okhttp(TrailersSource trailersSource) {
            Intrinsics.checkNotNullParameter(trailersSource, "<set-?>");
            this.trailersSource = trailersSource;
        }

        public Builder() {
            this.code = -1;
            this.body = ResponseBody.EMPTY;
            this.trailersSource = TrailersSource.EMPTY;
            this.headers = new Headers.Builder();
        }

        public Builder(Response response) {
            Intrinsics.checkNotNullParameter(response, "response");
            this.code = -1;
            this.body = ResponseBody.EMPTY;
            this.trailersSource = TrailersSource.EMPTY;
            this.request = response.request();
            this.protocol = response.protocol();
            this.code = response.code();
            this.message = response.message();
            this.handshake = response.handshake();
            this.headers = response.headers().newBuilder();
            this.body = response.body();
            this.networkResponse = response.networkResponse();
            this.cacheResponse = response.cacheResponse();
            this.priorResponse = response.priorResponse();
            this.sentRequestAtMillis = response.sentRequestAtMillis();
            this.receivedResponseAtMillis = response.receivedResponseAtMillis();
            this.exchange = response.getExchange();
            this.trailersSource = response.trailersSource;
        }

        public Builder request(Request request) {
            Intrinsics.checkNotNullParameter(request, "request");
            this.request = request;
            return this;
        }

        public Builder protocol(Protocol protocol) {
            Intrinsics.checkNotNullParameter(protocol, "protocol");
            this.protocol = protocol;
            return this;
        }

        public Builder code(int code) {
            this.code = code;
            return this;
        }

        public Builder message(String message) {
            Intrinsics.checkNotNullParameter(message, "message");
            this.message = message;
            return this;
        }

        public Builder handshake(Handshake handshake) {
            this.handshake = handshake;
            return this;
        }

        public Builder header(String name, String value) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(value, "value");
            this.headers.set(name, value);
            return this;
        }

        public Builder addHeader(String name, String value) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(value, "value");
            this.headers.add(name, value);
            return this;
        }

        public Builder removeHeader(String name) {
            Intrinsics.checkNotNullParameter(name, "name");
            this.headers.removeAll(name);
            return this;
        }

        public Builder headers(Headers headers) {
            Intrinsics.checkNotNullParameter(headers, "headers");
            this.headers = headers.newBuilder();
            return this;
        }

        public Builder body(ResponseBody body) {
            Intrinsics.checkNotNullParameter(body, "body");
            this.body = body;
            return this;
        }

        public Builder networkResponse(Response networkResponse) {
            checkSupportResponse("networkResponse", networkResponse);
            this.networkResponse = networkResponse;
            return this;
        }

        public Builder cacheResponse(Response cacheResponse) {
            checkSupportResponse("cacheResponse", cacheResponse);
            this.cacheResponse = cacheResponse;
            return this;
        }

        private final void checkSupportResponse(String name, Response response) {
            if (response != null) {
                if (response.networkResponse() != null) {
                    throw new IllegalArgumentException((name + ".networkResponse != null").toString());
                }
                if (response.cacheResponse() != null) {
                    throw new IllegalArgumentException((name + ".cacheResponse != null").toString());
                }
                if (response.priorResponse() != null) {
                    throw new IllegalArgumentException((name + ".priorResponse != null").toString());
                }
            }
        }

        public Builder priorResponse(Response priorResponse) {
            this.priorResponse = priorResponse;
            return this;
        }

        public Builder trailers(TrailersSource trailersSource) {
            Intrinsics.checkNotNullParameter(trailersSource, "trailersSource");
            this.trailersSource = trailersSource;
            return this;
        }

        public Builder sentRequestAtMillis(long sentRequestAtMillis) {
            this.sentRequestAtMillis = sentRequestAtMillis;
            return this;
        }

        public Builder receivedResponseAtMillis(long receivedResponseAtMillis) {
            this.receivedResponseAtMillis = receivedResponseAtMillis;
            return this;
        }

        public final void initExchange$okhttp(Exchange exchange) {
            Intrinsics.checkNotNullParameter(exchange, "exchange");
            this.exchange = exchange;
        }

        public Response build() {
            int i = this.code;
            if (i < 0) {
                throw new IllegalStateException(("code < 0: " + this.code).toString());
            }
            Request request = this.request;
            if (request == null) {
                throw new IllegalStateException("request == null".toString());
            }
            Protocol protocol = this.protocol;
            if (protocol == null) {
                throw new IllegalStateException("protocol == null".toString());
            }
            String str = this.message;
            if (str != null) {
                return new Response(request, protocol, str, i, this.handshake, this.headers.build(), this.body, this.networkResponse, this.cacheResponse, this.priorResponse, this.sentRequestAtMillis, this.receivedResponseAtMillis, this.exchange, this.trailersSource);
            }
            throw new IllegalStateException("message == null".toString());
        }
    }
}
