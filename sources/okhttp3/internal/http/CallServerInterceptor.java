package okhttp3.internal.http;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.io.IOException;
import java.net.ProtocolException;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import miuix.navigation.SplitLayout;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.TrailersSource;
import okhttp3.internal.UnreadableResponseBodyKt;
import okhttp3.internal._UtilJvmKt;
import okhttp3.internal.connection.Exchange;
import okhttp3.internal.http2.ConnectionShutdownException;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/* JADX INFO: compiled from: CallServerInterceptor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lokhttp3/internal/http/CallServerInterceptor;", "Lokhttp3/Interceptor;", "forWebSocket", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Z)V", "intercept", "Lokhttp3/Response;", "chain", "Lokhttp3/Interceptor$Chain;", "shouldIgnoreAndWaitForRealResponse", "code", "", "exchange", "Lokhttp3/internal/connection/Exchange;", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class CallServerInterceptor implements Interceptor {
    private final boolean forWebSocket;

    private final boolean shouldIgnoreAndWaitForRealResponse(int code, Exchange exchange) {
        if (code == 100) {
            return true;
        }
        return 102 <= code && code < 200;
    }

    public CallServerInterceptor(boolean z) {
        this.forWebSocket = z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v0 */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v18, types: [boolean] */
    /* JADX WARN: Type inference failed for: r9v19 */
    /* JADX WARN: Type inference failed for: r9v2 */
    /* JADX WARN: Type inference failed for: r9v20 */
    /* JADX WARN: Type inference failed for: r9v21 */
    /* JADX WARN: Type inference failed for: r9v24 */
    /* JADX WARN: Type inference failed for: r9v25 */
    /* JADX WARN: Type inference failed for: r9v26, types: [okhttp3.Response$Builder] */
    /* JADX WARN: Type inference failed for: r9v27 */
    /* JADX WARN: Type inference failed for: r9v28 */
    /* JADX WARN: Type inference failed for: r9v29 */
    /* JADX WARN: Type inference failed for: r9v3, types: [java.lang.Object, okhttp3.Response$Builder] */
    /* JADX WARN: Type inference failed for: r9v30 */
    /* JADX WARN: Type inference failed for: r9v31 */
    /* JADX WARN: Type inference failed for: r9v32 */
    /* JADX WARN: Type inference failed for: r9v33 */
    /* JADX WARN: Type inference failed for: r9v4, types: [okhttp3.Response$Builder] */
    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        boolean z;
        ?? PermitsRequestBody;
        ?? responseHeaders;
        Response responseBuild;
        ?? r9;
        Intrinsics.checkNotNullParameter(chain, "chain");
        RealInterceptorChain realInterceptorChain = (RealInterceptorChain) chain;
        final Exchange exchange = realInterceptorChain.getExchange();
        Intrinsics.checkNotNull(exchange);
        Request request = realInterceptorChain.getRequest();
        RequestBody requestBodyBody = request.body();
        long jCurrentTimeMillis = System.currentTimeMillis();
        try {
            exchange.writeRequestHeaders(request);
            PermitsRequestBody = HttpMethod.permitsRequestBody(request.method());
            try {
                if (PermitsRequestBody != 0 && requestBodyBody != null) {
                    if (StringsKt.equals("100-continue", request.header("Expect"), true)) {
                        exchange.flushRequest();
                        PermitsRequestBody = exchange.readResponseHeaders(true);
                        try {
                            exchange.responseHeadersStart();
                            z = false;
                            r9 = PermitsRequestBody;
                        } catch (IOException e) {
                            e = e;
                            z = true;
                            if (e instanceof ConnectionShutdownException) {
                                throw e;
                            }
                            responseHeaders = PermitsRequestBody;
                            if (!exchange.getHasFailure()) {
                                throw e;
                            }
                        }
                    } else {
                        z = true;
                        r9 = 0;
                    }
                    if (r9 == 0) {
                        if (requestBodyBody.isDuplex()) {
                            exchange.flushRequest();
                            requestBodyBody.writeTo(Okio.buffer(exchange.createRequestBody(request, true)));
                            PermitsRequestBody = r9;
                        } else {
                            BufferedSink bufferedSinkBuffer = Okio.buffer(exchange.createRequestBody(request, false));
                            requestBodyBody.writeTo(bufferedSinkBuffer);
                            bufferedSinkBuffer.close();
                            PermitsRequestBody = r9;
                        }
                    } else {
                        exchange.noRequestBody();
                        PermitsRequestBody = r9;
                        if (!exchange.getConnection$okhttp().isMultiplexed$okhttp()) {
                            exchange.noNewExchangesOnConnection();
                            PermitsRequestBody = r9;
                        }
                    }
                } else {
                    exchange.noRequestBody();
                    z = true;
                    PermitsRequestBody = 0;
                }
                if (requestBodyBody == null || !requestBodyBody.isDuplex()) {
                    exchange.finishRequest();
                }
                e = null;
                responseHeaders = PermitsRequestBody;
            } catch (IOException e2) {
                e = e2;
            }
        } catch (IOException e3) {
            e = e3;
            z = true;
            PermitsRequestBody = 0;
        }
        if (responseHeaders == 0) {
            try {
                responseHeaders = exchange.readResponseHeaders(false);
                Intrinsics.checkNotNull(responseHeaders);
                if (z) {
                    exchange.responseHeadersStart();
                    z = false;
                }
            } catch (IOException e4) {
                if (e != null) {
                    ExceptionsKt.addSuppressed(e, e4);
                    throw e;
                }
                throw e4;
            }
        }
        Response responseBuild2 = responseHeaders.request(request).handshake(exchange.getConnection$okhttp().getHandshake()).sentRequestAtMillis(jCurrentTimeMillis).receivedResponseAtMillis(System.currentTimeMillis()).build();
        int iCode = responseBuild2.code();
        while (shouldIgnoreAndWaitForRealResponse(iCode, exchange)) {
            Response.Builder responseHeaders2 = exchange.readResponseHeaders(false);
            Intrinsics.checkNotNull(responseHeaders2);
            if (z) {
                exchange.responseHeadersStart();
            }
            responseBuild2 = responseHeaders2.request(request).handshake(exchange.getConnection$okhttp().getHandshake()).sentRequestAtMillis(jCurrentTimeMillis).receivedResponseAtMillis(System.currentTimeMillis()).build();
            iCode = responseBuild2.code();
        }
        exchange.responseHeadersEnd(responseBuild2);
        if (this.forWebSocket && iCode == 101) {
            responseBuild = UnreadableResponseBodyKt.stripBody(responseBuild2);
        } else {
            final ResponseBody responseBodyOpenResponseBody = exchange.openResponseBody(responseBuild2);
            responseBuild = responseBuild2.newBuilder().body(responseBodyOpenResponseBody).trailers(new TrailersSource() { // from class: okhttp3.internal.http.CallServerInterceptor.intercept.1
                @Override // okhttp3.TrailersSource
                public Headers peek() {
                    return exchange.peekTrailers();
                }

                @Override // okhttp3.TrailersSource
                public Headers get() throws IOException {
                    BufferedSource bodySource = responseBodyOpenResponseBody.getBodySource();
                    if (bodySource.isOpen()) {
                        _UtilJvmKt.skipAll(bodySource);
                    }
                    Headers headersPeek = peek();
                    if (headersPeek != null) {
                        return headersPeek;
                    }
                    throw new IllegalStateException("null trailers after exhausting response body?!".toString());
                }
            }).build();
        }
        if (StringsKt.equals(SplitLayout.TAG_CLOSE, responseBuild.request().header("Connection"), true) || StringsKt.equals(SplitLayout.TAG_CLOSE, Response.header$default(responseBuild, "Connection", null, 2, null), true)) {
            exchange.noNewExchangesOnConnection();
        }
        if ((iCode == 204 || iCode == 205) && responseBuild.body().getContentLength() > 0) {
            throw new ProtocolException("HTTP " + iCode + " had non-zero Content-Length: " + responseBuild.body().getContentLength());
        }
        return responseBuild;
    }
}
