package okhttp3.internal.connection;

import androidx.exifinterface.media.ExifInterface;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.ToDoStat;
import com.xiaomi.accountsdk.account.stat.StatConstants;
import com.xiaomi.onetrack.api.ah;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.SocketException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.navigation.SplitLayout;
import okhttp3.EventListener;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.ExchangeCodec;
import okhttp3.internal.http.RealResponseBody;
import okhttp3.internal.ws.RealWebSocket;
import okio.Buffer;
import okio.ForwardingSink;
import okio.ForwardingSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

/* JADX INFO: compiled from: Exchange.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0002DEB'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0004\b\n\u0010\u000bJ\u000e\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"J\u0016\u0010#\u001a\u00020$2\u0006\u0010!\u001a\u00020\"2\u0006\u0010%\u001a\u00020\u0013J\u0006\u0010&\u001a\u00020 J\u0006\u0010'\u001a\u00020 J\u0006\u0010(\u001a\u00020 J\u0010\u0010)\u001a\u0004\u0018\u00010*2\u0006\u0010+\u001a\u00020\u0013J\u000e\u0010,\u001a\u00020 2\u0006\u0010-\u001a\u00020.J\u000e\u0010/\u001a\u0002002\u0006\u0010-\u001a\u00020.J\b\u00101\u001a\u0004\u0018\u000102J\u0006\u00103\u001a\u000204J\u0006\u00105\u001a\u00020 J\u0006\u00106\u001a\u00020 J\u0006\u00107\u001a\u00020 J\u0006\u00108\u001a\u00020 J\u0010\u00109\u001a\u00020 2\u0006\u0010:\u001a\u00020;H\u0002J=\u0010<\u001a\u0002H=\"\n\b\u0000\u0010=*\u0004\u0018\u00010;2\b\b\u0002\u0010>\u001a\u00020?2\b\b\u0002\u0010@\u001a\u00020\u00132\b\b\u0002\u0010A\u001a\u00020\u00132\u0006\u0010:\u001a\u0002H=¢\u0006\u0002\u0010BJ\u0006\u0010C\u001a\u00020 R\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0004\u001a\u00020\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0006\u001a\u00020\u0007X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0012\u001a\u00020\u0013@BX\u0080\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u001e\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0012\u001a\u00020\u0013@BX\u0080\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0016R\u0014\u0010\u0019\u001a\u00020\u001a8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0014\u0010\u001d\u001a\u00020\u00138@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u0016¨\u0006F"}, d2 = {"Lokhttp3/internal/connection/Exchange;", "", "call", "Lokhttp3/internal/connection/RealCall;", "eventListener", "Lokhttp3/EventListener;", "finder", "Lokhttp3/internal/connection/ExchangeFinder;", "codec", "Lokhttp3/internal/http/ExchangeCodec;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokhttp3/internal/connection/RealCall;Lokhttp3/EventListener;Lokhttp3/internal/connection/ExchangeFinder;Lokhttp3/internal/http/ExchangeCodec;)V", "getCall$okhttp", "()Lokhttp3/internal/connection/RealCall;", "getEventListener$okhttp", "()Lokhttp3/EventListener;", "getFinder$okhttp", "()Lokhttp3/internal/connection/ExchangeFinder;", "value", "", "isDuplex", "isDuplex$okhttp", "()Z", "hasFailure", "getHasFailure$okhttp", "connection", "Lokhttp3/internal/connection/RealConnection;", "getConnection$okhttp", "()Lokhttp3/internal/connection/RealConnection;", "isCoalescedConnection", "isCoalescedConnection$okhttp", "writeRequestHeaders", "", StatConstants.Event.REQUEST, "Lokhttp3/Request;", "createRequestBody", "Lokio/Sink;", "duplex", "flushRequest", "finishRequest", "responseHeadersStart", "readResponseHeaders", "Lokhttp3/Response$Builder;", "expectContinue", "responseHeadersEnd", ah.H, "Lokhttp3/Response;", "openResponseBody", "Lokhttp3/ResponseBody;", "peekTrailers", "Lokhttp3/Headers;", "newWebSocketStreams", "Lokhttp3/internal/ws/RealWebSocket$Streams;", "webSocketUpgradeFailed", "noNewExchangesOnConnection", ToDoStat.CANCEL, "detachWithViolence", "trackFailure", "e", "Ljava/io/IOException;", "bodyComplete", ExifInterface.LONGITUDE_EAST, "bytesRead", "", "responseDone", "requestDone", "(JZZLjava/io/IOException;)Ljava/io/IOException;", "noRequestBody", "RequestBodySink", "ResponseBodySource", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class Exchange {
    private final RealCall call;
    private final ExchangeCodec codec;
    private final EventListener eventListener;
    private final ExchangeFinder finder;
    private boolean hasFailure;
    private boolean isDuplex;

    public Exchange(RealCall call, EventListener eventListener, ExchangeFinder finder, ExchangeCodec codec) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(eventListener, "eventListener");
        Intrinsics.checkNotNullParameter(finder, "finder");
        Intrinsics.checkNotNullParameter(codec, "codec");
        this.call = call;
        this.eventListener = eventListener;
        this.finder = finder;
        this.codec = codec;
    }

    /* JADX INFO: renamed from: getCall$okhttp, reason: from getter */
    public final RealCall getCall() {
        return this.call;
    }

    /* JADX INFO: renamed from: getEventListener$okhttp, reason: from getter */
    public final EventListener getEventListener() {
        return this.eventListener;
    }

    /* JADX INFO: renamed from: getFinder$okhttp, reason: from getter */
    public final ExchangeFinder getFinder() {
        return this.finder;
    }

    /* JADX INFO: renamed from: isDuplex$okhttp, reason: from getter */
    public final boolean getIsDuplex() {
        return this.isDuplex;
    }

    /* JADX INFO: renamed from: getHasFailure$okhttp, reason: from getter */
    public final boolean getHasFailure() {
        return this.hasFailure;
    }

    public final RealConnection getConnection$okhttp() {
        ExchangeCodec.Carrier carrier = this.codec.getCarrier();
        RealConnection realConnection = carrier instanceof RealConnection ? (RealConnection) carrier : null;
        if (realConnection != null) {
            return realConnection;
        }
        throw new IllegalStateException("no connection for CONNECT tunnels".toString());
    }

    public final boolean isCoalescedConnection$okhttp() {
        return !Intrinsics.areEqual(this.finder.getRoutePlanner().getAddress().url().host(), this.codec.getCarrier().getRoute().address().url().host());
    }

    public final void writeRequestHeaders(Request request) throws IOException {
        Intrinsics.checkNotNullParameter(request, "request");
        try {
            this.eventListener.requestHeadersStart(this.call);
            this.codec.writeRequestHeaders(request);
            this.eventListener.requestHeadersEnd(this.call, request);
        } catch (IOException e) {
            this.eventListener.requestFailed(this.call, e);
            trackFailure(e);
            throw e;
        }
    }

    public final Sink createRequestBody(Request request, boolean duplex) throws IOException {
        Intrinsics.checkNotNullParameter(request, "request");
        this.isDuplex = duplex;
        RequestBody requestBodyBody = request.body();
        Intrinsics.checkNotNull(requestBodyBody);
        long jContentLength = requestBodyBody.contentLength();
        this.eventListener.requestBodyStart(this.call);
        return new RequestBodySink(this, this.codec.createRequestBody(request, jContentLength), jContentLength);
    }

    public final void flushRequest() throws IOException {
        try {
            this.codec.flushRequest();
        } catch (IOException e) {
            this.eventListener.requestFailed(this.call, e);
            trackFailure(e);
            throw e;
        }
    }

    public final void finishRequest() throws IOException {
        try {
            this.codec.finishRequest();
        } catch (IOException e) {
            this.eventListener.requestFailed(this.call, e);
            trackFailure(e);
            throw e;
        }
    }

    public final void responseHeadersStart() {
        this.eventListener.responseHeadersStart(this.call);
    }

    public final Response.Builder readResponseHeaders(boolean expectContinue) throws IOException {
        try {
            Response.Builder responseHeaders = this.codec.readResponseHeaders(expectContinue);
            if (responseHeaders != null) {
                responseHeaders.initExchange$okhttp(this);
            }
            return responseHeaders;
        } catch (IOException e) {
            this.eventListener.responseFailed(this.call, e);
            trackFailure(e);
            throw e;
        }
    }

    public final void responseHeadersEnd(Response response) {
        Intrinsics.checkNotNullParameter(response, "response");
        this.eventListener.responseHeadersEnd(this.call, response);
    }

    public final ResponseBody openResponseBody(Response response) throws IOException {
        Intrinsics.checkNotNullParameter(response, "response");
        try {
            String strHeader$default = Response.header$default(response, "Content-Type", null, 2, null);
            long jReportedContentLength = this.codec.reportedContentLength(response);
            return new RealResponseBody(strHeader$default, jReportedContentLength, Okio.buffer(new ResponseBodySource(this, this.codec.openResponseBodySource(response), jReportedContentLength)));
        } catch (IOException e) {
            this.eventListener.responseFailed(this.call, e);
            trackFailure(e);
            throw e;
        }
    }

    public final Headers peekTrailers() throws IOException {
        return this.codec.peekTrailers();
    }

    public final RealWebSocket.Streams newWebSocketStreams() throws SocketException {
        this.call.timeoutEarlyExit();
        ExchangeCodec.Carrier carrier = this.codec.getCarrier();
        Intrinsics.checkNotNull(carrier, "null cannot be cast to non-null type okhttp3.internal.connection.RealConnection");
        return ((RealConnection) carrier).newWebSocketStreams$okhttp(this);
    }

    public final void webSocketUpgradeFailed() {
        bodyComplete$default(this, 0L, true, true, null, 1, null);
    }

    public final void noNewExchangesOnConnection() {
        this.codec.getCarrier().noNewExchanges();
    }

    public final void cancel() {
        this.codec.cancel();
    }

    public final void detachWithViolence() {
        this.codec.cancel();
        this.call.messageDone$okhttp(this, true, true, null);
    }

    private final void trackFailure(IOException e) {
        this.hasFailure = true;
        this.codec.getCarrier().trackFailure(this.call, e);
    }

    public static /* synthetic */ IOException bodyComplete$default(Exchange exchange, long j, boolean z, boolean z2, IOException iOException, int i, Object obj) {
        if ((i & 1) != 0) {
            j = -1;
        }
        return exchange.bodyComplete(j, (i & 2) != 0 ? false : z, (i & 4) != 0 ? false : z2, iOException);
    }

    public final <E extends IOException> E bodyComplete(long bytesRead, boolean responseDone, boolean requestDone, E e) {
        if (e != null) {
            trackFailure(e);
        }
        if (requestDone) {
            if (e != null) {
                this.eventListener.requestFailed(this.call, e);
            } else {
                this.eventListener.requestBodyEnd(this.call, bytesRead);
            }
        }
        if (responseDone) {
            if (e != null) {
                this.eventListener.responseFailed(this.call, e);
            } else {
                this.eventListener.responseBodyEnd(this.call, bytesRead);
            }
        }
        return (E) this.call.messageDone$okhttp(this, requestDone, responseDone, e);
    }

    public final void noRequestBody() {
        RealCall.messageDone$okhttp$default(this.call, this, true, false, null, 4, null);
    }

    /* JADX INFO: compiled from: Exchange.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0005H\u0016J\b\u0010\u0011\u001a\u00020\rH\u0016J\b\u0010\u0012\u001a\u00020\rH\u0016J!\u0010\u0013\u001a\u0002H\u0014\"\n\b\u0000\u0010\u0014*\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u0002H\u0014H\u0002¢\u0006\u0002\u0010\u0017R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lokhttp3/internal/connection/Exchange$RequestBodySink;", "Lokio/ForwardingSink;", "delegate", "Lokio/Sink;", "contentLength", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokhttp3/internal/connection/Exchange;Lokio/Sink;J)V", "completed", "", "bytesReceived", "closed", "write", "", "source", "Lokio/Buffer;", "byteCount", "flush", SplitLayout.TAG_CLOSE, "complete", ExifInterface.LONGITUDE_EAST, "Ljava/io/IOException;", "e", "(Ljava/io/IOException;)Ljava/io/IOException;", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private final class RequestBodySink extends ForwardingSink {
        private long bytesReceived;
        private boolean closed;
        private boolean completed;
        private final long contentLength;
        final /* synthetic */ Exchange this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public RequestBodySink(Exchange exchange, Sink delegate, long j) {
            super(delegate);
            Intrinsics.checkNotNullParameter(delegate, "delegate");
            this.this$0 = exchange;
            this.contentLength = j;
        }

        @Override // okio.ForwardingSink, okio.Sink
        public void write(Buffer source, long byteCount) throws IOException {
            Intrinsics.checkNotNullParameter(source, "source");
            if (this.closed) {
                throw new IllegalStateException("closed".toString());
            }
            long j = this.contentLength;
            if (j != -1 && this.bytesReceived + byteCount > j) {
                throw new ProtocolException("expected " + this.contentLength + " bytes but received " + (this.bytesReceived + byteCount));
            }
            try {
                super.write(source, byteCount);
                this.bytesReceived += byteCount;
            } catch (IOException e) {
                throw complete(e);
            }
        }

        @Override // okio.ForwardingSink, okio.Sink, java.io.Flushable
        public void flush() throws IOException {
            try {
                super.flush();
            } catch (IOException e) {
                throw complete(e);
            }
        }

        @Override // okio.ForwardingSink, okio.Sink, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (this.closed) {
                return;
            }
            this.closed = true;
            long j = this.contentLength;
            if (j != -1 && this.bytesReceived != j) {
                throw new ProtocolException("unexpected end of stream");
            }
            try {
                super.close();
            } catch (IOException e) {
                throw complete(e);
            }
        }

        private final <E extends IOException> E complete(E e) {
            if (this.completed) {
                return e;
            }
            this.completed = true;
            return (E) Exchange.bodyComplete$default(this.this$0, this.bytesReceived, false, true, e, 2, null);
        }
    }

    /* JADX INFO: compiled from: Exchange.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0080\u0004\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0018\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0005H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u001f\u0010\u0013\u001a\u0002H\u0014\"\n\b\u0000\u0010\u0014*\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u0002H\u0014¢\u0006\u0002\u0010\u0017R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lokhttp3/internal/connection/Exchange$ResponseBodySource;", "Lokio/ForwardingSource;", "delegate", "Lokio/Source;", "contentLength", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokhttp3/internal/connection/Exchange;Lokio/Source;J)V", "bytesReceived", "invokeStartEvent", "", "completed", "closed", "read", "sink", "Lokio/Buffer;", "byteCount", SplitLayout.TAG_CLOSE, "", "complete", ExifInterface.LONGITUDE_EAST, "Ljava/io/IOException;", "e", "(Ljava/io/IOException;)Ljava/io/IOException;", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public final class ResponseBodySource extends ForwardingSource {
        private long bytesReceived;
        private boolean closed;
        private boolean completed;
        private final long contentLength;
        private boolean invokeStartEvent;
        final /* synthetic */ Exchange this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ResponseBodySource(Exchange exchange, Source delegate, long j) {
            super(delegate);
            Intrinsics.checkNotNullParameter(delegate, "delegate");
            this.this$0 = exchange;
            this.contentLength = j;
            this.invokeStartEvent = true;
            if (j == 0) {
                complete(null);
            }
        }

        @Override // okio.ForwardingSource, okio.Source
        public long read(Buffer sink, long byteCount) throws IOException {
            Intrinsics.checkNotNullParameter(sink, "sink");
            if (this.closed) {
                throw new IllegalStateException("closed".toString());
            }
            try {
                long j = delegate().read(sink, byteCount);
                if (this.invokeStartEvent) {
                    this.invokeStartEvent = false;
                    this.this$0.getEventListener().responseBodyStart(this.this$0.getCall());
                }
                if (j == -1) {
                    complete(null);
                    return -1L;
                }
                long j2 = this.bytesReceived + j;
                long j3 = this.contentLength;
                if (j3 != -1 && j2 > j3) {
                    throw new ProtocolException("expected " + this.contentLength + " bytes but received " + j2);
                }
                this.bytesReceived = j2;
                if (this.this$0.codec.isResponseComplete()) {
                    complete(null);
                }
                return j;
            } catch (IOException e) {
                throw complete(e);
            }
        }

        @Override // okio.ForwardingSource, okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (this.closed) {
                return;
            }
            this.closed = true;
            try {
                super.close();
            } catch (IOException e) {
                throw complete(e);
            }
        }

        public final <E extends IOException> E complete(E e) {
            if (this.completed) {
                return e;
            }
            this.completed = true;
            if (e == null && this.invokeStartEvent) {
                this.invokeStartEvent = false;
                this.this$0.getEventListener().responseBodyStart(this.this$0.getCall());
            }
            return (E) Exchange.bodyComplete$default(this.this$0, this.bytesReceived, true, false, e, 4, null);
        }
    }
}
