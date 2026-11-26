package okhttp3.internal.http1;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.ToDoStat;
import com.miui.maml.elements.MusicLyricParser;
import com.xiaomi.accountsdk.account.stat.StatConstants;
import com.xiaomi.onetrack.api.ah;
import java.io.EOFException;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import miuix.navigation.SplitLayout;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal._UtilCommonKt;
import okhttp3.internal._UtilJvmKt;
import okhttp3.internal.http.ExchangeCodec;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http.RequestLine;
import okhttp3.internal.http.StatusLine;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ForwardingTimeout;
import okio.Sink;
import okio.Source;
import okio.Timeout;

/* JADX INFO: compiled from: Http1ExchangeCodec.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 D2\u00020\u0001:\u0007>?@ABCDB)\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0004\b\n\u0010\u000bJ\u0018\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u00162\u0006\u0010\u001f\u001a\u00020 H\u0016J\b\u0010!\u001a\u00020\"H\u0016J\u0010\u0010#\u001a\u00020\"2\u0006\u0010\u001e\u001a\u00020\u0016H\u0016J\u0010\u0010$\u001a\u00020 2\u0006\u0010%\u001a\u00020\u0014H\u0016J\u0010\u0010&\u001a\u00020'2\u0006\u0010%\u001a\u00020\u0014H\u0016J\n\u0010(\u001a\u0004\u0018\u00010\u0019H\u0016J\b\u0010)\u001a\u00020\"H\u0016J\b\u0010*\u001a\u00020\"H\u0016J\u0016\u0010+\u001a\u00020\"2\u0006\u0010,\u001a\u00020\u00192\u0006\u0010-\u001a\u00020.J\u0012\u0010/\u001a\u0004\u0018\u0001002\u0006\u00101\u001a\u00020\u0013H\u0016J\b\u00102\u001a\u00020\u001dH\u0002J\b\u00103\u001a\u00020\u001dH\u0002J\u0018\u00104\u001a\u00020'2\u0006\u00105\u001a\u0002062\u0006\u00107\u001a\u00020 H\u0002J\u0010\u00108\u001a\u00020'2\u0006\u00105\u001a\u000206H\u0002J\u0010\u00109\u001a\u00020'2\u0006\u00105\u001a\u000206H\u0002J\u0010\u0010:\u001a\u00020\"2\u0006\u0010;\u001a\u00020<H\u0002J\u000e\u0010=\u001a\u00020\"2\u0006\u0010%\u001a\u00020\u0014R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0012\u001a\u00020\u0013*\u00020\u00148BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0015R\u0018\u0010\u0012\u001a\u00020\u0013*\u00020\u00168BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0017R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001b¨\u0006E"}, d2 = {"Lokhttp3/internal/http1/Http1ExchangeCodec;", "Lokhttp3/internal/http/ExchangeCodec;", "client", "Lokhttp3/OkHttpClient;", "carrier", "Lokhttp3/internal/http/ExchangeCodec$Carrier;", "source", "Lokio/BufferedSource;", "sink", "Lokio/BufferedSink;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokhttp3/OkHttpClient;Lokhttp3/internal/http/ExchangeCodec$Carrier;Lokio/BufferedSource;Lokio/BufferedSink;)V", "getCarrier", "()Lokhttp3/internal/http/ExchangeCodec$Carrier;", "state", "", "headersReader", "Lokhttp3/internal/http1/HeadersReader;", "isChunked", "", "Lokhttp3/Response;", "(Lokhttp3/Response;)Z", "Lokhttp3/Request;", "(Lokhttp3/Request;)Z", "trailers", "Lokhttp3/Headers;", "isResponseComplete", "()Z", "createRequestBody", "Lokio/Sink;", StatConstants.Event.REQUEST, "contentLength", "", ToDoStat.CANCEL, "", "writeRequestHeaders", "reportedContentLength", ah.H, "openResponseBodySource", "Lokio/Source;", "peekTrailers", "flushRequest", "finishRequest", "writeRequest", "headers", "requestLine", "", "readResponseHeaders", "Lokhttp3/Response$Builder;", "expectContinue", "newChunkedSink", "newKnownLengthSink", "newFixedLengthSource", "url", "Lokhttp3/HttpUrl;", "length", "newChunkedSource", "newUnknownLengthSource", "detachTimeout", "timeout", "Lokio/ForwardingTimeout;", "skipConnectBody", "KnownLengthSink", "ChunkedSink", "AbstractSource", "FixedLengthSource", "ChunkedSource", "UnknownLengthSource", "Companion", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class Http1ExchangeCodec implements ExchangeCodec {
    private static final long NO_CHUNK_YET = -1;
    private static final int STATE_CLOSED = 6;
    private static final int STATE_IDLE = 0;
    private static final int STATE_OPEN_REQUEST_BODY = 1;
    private static final int STATE_OPEN_RESPONSE_BODY = 4;
    private static final int STATE_READING_RESPONSE_BODY = 5;
    private static final int STATE_READ_RESPONSE_HEADERS = 3;
    private static final int STATE_WRITING_REQUEST_BODY = 2;
    private final ExchangeCodec.Carrier carrier;
    private final OkHttpClient client;
    private final HeadersReader headersReader;
    private final BufferedSink sink;
    private final BufferedSource source;
    private int state;
    private Headers trailers;
    private static final Headers TRAILERS_RESPONSE_BODY_TRUNCATED = Headers.INSTANCE.of("OkHttp-Response-Body", "Truncated");

    public Http1ExchangeCodec(OkHttpClient okHttpClient, ExchangeCodec.Carrier carrier, BufferedSource source, BufferedSink sink) {
        Intrinsics.checkNotNullParameter(carrier, "carrier");
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(sink, "sink");
        this.client = okHttpClient;
        this.carrier = carrier;
        this.source = source;
        this.sink = sink;
        this.headersReader = new HeadersReader(source);
    }

    @Override // okhttp3.internal.http.ExchangeCodec
    public ExchangeCodec.Carrier getCarrier() {
        return this.carrier;
    }

    private final boolean isChunked(Response response) {
        return StringsKt.equals("chunked", Response.header$default(response, "Transfer-Encoding", null, 2, null), true);
    }

    private final boolean isChunked(Request request) {
        return StringsKt.equals("chunked", request.header("Transfer-Encoding"), true);
    }

    @Override // okhttp3.internal.http.ExchangeCodec
    public boolean isResponseComplete() {
        return this.state == 6;
    }

    @Override // okhttp3.internal.http.ExchangeCodec
    public Sink createRequestBody(Request request, long contentLength) throws ProtocolException {
        Intrinsics.checkNotNullParameter(request, "request");
        RequestBody requestBodyBody = request.body();
        if (requestBodyBody != null && requestBodyBody.isDuplex()) {
            throw new ProtocolException("Duplex connections are not supported for HTTP/1");
        }
        if (isChunked(request)) {
            return newChunkedSink();
        }
        if (contentLength != -1) {
            return newKnownLengthSink();
        }
        throw new IllegalStateException("Cannot stream a request body without chunked encoding or a known content length!");
    }

    @Override // okhttp3.internal.http.ExchangeCodec
    public void cancel() {
        getCarrier().mo4513cancel();
    }

    @Override // okhttp3.internal.http.ExchangeCodec
    public void writeRequestHeaders(Request request) throws IOException {
        Intrinsics.checkNotNullParameter(request, "request");
        RequestLine requestLine = RequestLine.INSTANCE;
        Proxy.Type type = getCarrier().getRoute().proxy().type();
        Intrinsics.checkNotNullExpressionValue(type, "type(...)");
        writeRequest(request.headers(), requestLine.get(request, type));
    }

    @Override // okhttp3.internal.http.ExchangeCodec
    public long reportedContentLength(Response response) {
        Intrinsics.checkNotNullParameter(response, "response");
        if (!HttpHeaders.promisesBody(response)) {
            return 0L;
        }
        if (isChunked(response)) {
            return -1L;
        }
        return _UtilJvmKt.headersContentLength(response);
    }

    @Override // okhttp3.internal.http.ExchangeCodec
    public Source openResponseBodySource(Response response) {
        Intrinsics.checkNotNullParameter(response, "response");
        if (!HttpHeaders.promisesBody(response)) {
            return newFixedLengthSource(response.request().url(), 0L);
        }
        if (isChunked(response)) {
            return newChunkedSource(response.request().url());
        }
        long jHeadersContentLength = _UtilJvmKt.headersContentLength(response);
        if (jHeadersContentLength != -1) {
            return newFixedLengthSource(response.request().url(), jHeadersContentLength);
        }
        return newUnknownLengthSource(response.request().url());
    }

    @Override // okhttp3.internal.http.ExchangeCodec
    public Headers peekTrailers() throws IOException {
        Headers headers = this.trailers;
        if (headers == TRAILERS_RESPONSE_BODY_TRUNCATED) {
            throw new IOException("Trailers cannot be read because the response body was truncated");
        }
        int i = this.state;
        if (i == 5 || i == 6) {
            return headers;
        }
        throw new IllegalStateException(("Trailers cannot be read because the state is " + this.state).toString());
    }

    @Override // okhttp3.internal.http.ExchangeCodec
    public void flushRequest() throws IOException {
        this.sink.flush();
    }

    @Override // okhttp3.internal.http.ExchangeCodec
    public void finishRequest() throws IOException {
        this.sink.flush();
    }

    public final void writeRequest(Headers headers, String requestLine) throws IOException {
        Intrinsics.checkNotNullParameter(headers, "headers");
        Intrinsics.checkNotNullParameter(requestLine, "requestLine");
        if (this.state != 0) {
            throw new IllegalStateException(("state: " + this.state).toString());
        }
        this.sink.writeUtf8(requestLine).writeUtf8(MusicLyricParser.CRLF);
        int size = headers.size();
        for (int i = 0; i < size; i++) {
            this.sink.writeUtf8(headers.name(i)).writeUtf8(": ").writeUtf8(headers.value(i)).writeUtf8(MusicLyricParser.CRLF);
        }
        this.sink.writeUtf8(MusicLyricParser.CRLF);
        this.state = 1;
    }

    @Override // okhttp3.internal.http.ExchangeCodec
    public Response.Builder readResponseHeaders(boolean expectContinue) throws IOException {
        int i = this.state;
        if (i != 0 && i != 1 && i != 2 && i != 3) {
            throw new IllegalStateException(("state: " + this.state).toString());
        }
        try {
            StatusLine statusLine = StatusLine.INSTANCE.parse(this.headersReader.readLine());
            Response.Builder builderHeaders = new Response.Builder().protocol(statusLine.protocol).code(statusLine.code).message(statusLine.message).headers(this.headersReader.readHeaders());
            if (expectContinue && statusLine.code == 100) {
                return null;
            }
            if (statusLine.code == 100) {
                this.state = 3;
                return builderHeaders;
            }
            int i2 = statusLine.code;
            if (102 <= i2 && i2 < 200) {
                this.state = 3;
                return builderHeaders;
            }
            this.state = 4;
            return builderHeaders;
        } catch (EOFException e) {
            throw new IOException("unexpected end of stream on " + getCarrier().getRoute().address().url().redact(), e);
        }
    }

    private final Sink newChunkedSink() {
        if (this.state != 1) {
            throw new IllegalStateException(("state: " + this.state).toString());
        }
        this.state = 2;
        return new ChunkedSink();
    }

    private final Sink newKnownLengthSink() {
        if (this.state != 1) {
            throw new IllegalStateException(("state: " + this.state).toString());
        }
        this.state = 2;
        return new KnownLengthSink();
    }

    private final Source newFixedLengthSource(HttpUrl url, long length) {
        if (this.state != 4) {
            throw new IllegalStateException(("state: " + this.state).toString());
        }
        this.state = 5;
        return new FixedLengthSource(this, url, length);
    }

    private final Source newChunkedSource(HttpUrl url) {
        if (this.state != 4) {
            throw new IllegalStateException(("state: " + this.state).toString());
        }
        this.state = 5;
        return new ChunkedSource(this, url);
    }

    private final Source newUnknownLengthSource(HttpUrl url) {
        if (this.state != 4) {
            throw new IllegalStateException(("state: " + this.state).toString());
        }
        this.state = 5;
        getCarrier().noNewExchanges();
        return new UnknownLengthSource(this, url);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void detachTimeout(ForwardingTimeout timeout) {
        Timeout delegate = timeout.getDelegate();
        timeout.setDelegate(Timeout.NONE);
        delegate.clearDeadline();
        delegate.clearTimeout();
    }

    public final void skipConnectBody(Response response) throws IOException {
        Intrinsics.checkNotNullParameter(response, "response");
        long jHeadersContentLength = _UtilJvmKt.headersContentLength(response);
        if (jHeadersContentLength == -1) {
            return;
        }
        Source sourceNewFixedLengthSource = newFixedLengthSource(response.request().url(), jHeadersContentLength);
        _UtilJvmKt.skipAll(sourceNewFixedLengthSource, Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
        sourceNewFixedLengthSource.close();
    }

    /* JADX INFO: compiled from: Http1ExchangeCodec.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\bH\u0016J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\nH\u0016J\b\u0010\u0010\u001a\u00020\nH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lokhttp3/internal/http1/Http1ExchangeCodec$KnownLengthSink;", "Lokio/Sink;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokhttp3/internal/http1/Http1ExchangeCodec;)V", "timeout", "Lokio/ForwardingTimeout;", "closed", "", "Lokio/Timeout;", "write", "", "source", "Lokio/Buffer;", "byteCount", "", "flush", SplitLayout.TAG_CLOSE, "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private final class KnownLengthSink implements Sink {
        private boolean closed;
        private final ForwardingTimeout timeout;

        public KnownLengthSink() {
            this.timeout = new ForwardingTimeout(Http1ExchangeCodec.this.sink.getTimeout());
        }

        @Override // okio.Sink
        /* JADX INFO: renamed from: timeout */
        public Timeout getTimeout() {
            return this.timeout;
        }

        @Override // okio.Sink
        public void write(Buffer source, long byteCount) {
            Intrinsics.checkNotNullParameter(source, "source");
            if (this.closed) {
                throw new IllegalStateException("closed".toString());
            }
            _UtilCommonKt.checkOffsetAndCount(source.size(), 0L, byteCount);
            Http1ExchangeCodec.this.sink.write(source, byteCount);
        }

        @Override // okio.Sink, java.io.Flushable
        public void flush() throws IOException {
            if (this.closed) {
                return;
            }
            Http1ExchangeCodec.this.sink.flush();
        }

        @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            if (this.closed) {
                return;
            }
            this.closed = true;
            Http1ExchangeCodec.this.detachTimeout(this.timeout);
            Http1ExchangeCodec.this.state = 3;
        }
    }

    /* JADX INFO: compiled from: Http1ExchangeCodec.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\bH\u0016J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\nH\u0016J\b\u0010\u0010\u001a\u00020\nH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lokhttp3/internal/http1/Http1ExchangeCodec$ChunkedSink;", "Lokio/Sink;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokhttp3/internal/http1/Http1ExchangeCodec;)V", "timeout", "Lokio/ForwardingTimeout;", "closed", "", "Lokio/Timeout;", "write", "", "source", "Lokio/Buffer;", "byteCount", "", "flush", SplitLayout.TAG_CLOSE, "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private final class ChunkedSink implements Sink {
        private boolean closed;
        private final ForwardingTimeout timeout;

        public ChunkedSink() {
            this.timeout = new ForwardingTimeout(Http1ExchangeCodec.this.sink.getTimeout());
        }

        @Override // okio.Sink
        /* JADX INFO: renamed from: timeout */
        public Timeout getTimeout() {
            return this.timeout;
        }

        @Override // okio.Sink
        public void write(Buffer source, long byteCount) throws IOException {
            Intrinsics.checkNotNullParameter(source, "source");
            if (this.closed) {
                throw new IllegalStateException("closed".toString());
            }
            if (byteCount == 0) {
                return;
            }
            Http1ExchangeCodec.this.sink.writeHexadecimalUnsignedLong(byteCount);
            Http1ExchangeCodec.this.sink.writeUtf8(MusicLyricParser.CRLF);
            Http1ExchangeCodec.this.sink.write(source, byteCount);
            Http1ExchangeCodec.this.sink.writeUtf8(MusicLyricParser.CRLF);
        }

        @Override // okio.Sink, java.io.Flushable
        public synchronized void flush() {
            if (this.closed) {
                return;
            }
            Http1ExchangeCodec.this.sink.flush();
        }

        @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
        public synchronized void close() {
            if (this.closed) {
                return;
            }
            this.closed = true;
            Http1ExchangeCodec.this.sink.writeUtf8("0\r\n\r\n");
            Http1ExchangeCodec.this.detachTimeout(this.timeout);
            Http1ExchangeCodec.this.state = 3;
        }
    }

    /* JADX INFO: compiled from: Http1ExchangeCodec.kt */
    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b¢\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\b\u001a\u00020\u0012H\u0016J\u0018\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0014H\u0016J\u000e\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\tX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\rX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006\u001c"}, d2 = {"Lokhttp3/internal/http1/Http1ExchangeCodec$AbstractSource;", "Lokio/Source;", "url", "Lokhttp3/HttpUrl;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokhttp3/internal/http1/Http1ExchangeCodec;Lokhttp3/HttpUrl;)V", "getUrl", "()Lokhttp3/HttpUrl;", "timeout", "Lokio/ForwardingTimeout;", "getTimeout", "()Lokio/ForwardingTimeout;", "closed", "", "getClosed", "()Z", "setClosed", "(Z)V", "Lokio/Timeout;", "read", "", "sink", "Lokio/Buffer;", "byteCount", "responseBodyComplete", "", "trailers", "Lokhttp3/Headers;", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private abstract class AbstractSource implements Source {
        private boolean closed;
        final /* synthetic */ Http1ExchangeCodec this$0;
        private final ForwardingTimeout timeout;
        private final HttpUrl url;

        public AbstractSource(Http1ExchangeCodec http1ExchangeCodec, HttpUrl url) {
            Intrinsics.checkNotNullParameter(url, "url");
            this.this$0 = http1ExchangeCodec;
            this.url = url;
            this.timeout = new ForwardingTimeout(http1ExchangeCodec.source.getTimeout());
        }

        public final HttpUrl getUrl() {
            return this.url;
        }

        protected final ForwardingTimeout getTimeout() {
            return this.timeout;
        }

        protected final boolean getClosed() {
            return this.closed;
        }

        protected final void setClosed(boolean z) {
            this.closed = z;
        }

        @Override // okio.Source
        /* JADX INFO: renamed from: timeout */
        public Timeout getTimeout() {
            return this.timeout;
        }

        @Override // okio.Source
        public long read(Buffer sink, long byteCount) throws IOException {
            Intrinsics.checkNotNullParameter(sink, "sink");
            try {
                return this.this$0.source.read(sink, byteCount);
            } catch (IOException e) {
                this.this$0.getCarrier().noNewExchanges();
                responseBodyComplete(Http1ExchangeCodec.TRAILERS_RESPONSE_BODY_TRUNCATED);
                throw e;
            }
        }

        public final void responseBodyComplete(Headers trailers) {
            OkHttpClient okHttpClient;
            CookieJar cookieJar;
            Intrinsics.checkNotNullParameter(trailers, "trailers");
            if (this.this$0.state == 6) {
                return;
            }
            if (this.this$0.state == 5) {
                this.this$0.detachTimeout(this.timeout);
                this.this$0.trailers = trailers;
                this.this$0.state = 6;
                if (trailers.size() <= 0 || (okHttpClient = this.this$0.client) == null || (cookieJar = okHttpClient.cookieJar()) == null) {
                    return;
                }
                HttpHeaders.receiveHeaders(cookieJar, this.url, trailers);
                return;
            }
            throw new IllegalStateException("state: " + this.this$0.state);
        }
    }

    /* JADX INFO: compiled from: Http1ExchangeCodec.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00060\u0001R\u00020\u0002B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\bJ\u0018\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0006H\u0016J\b\u0010\r\u001a\u00020\u000eH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lokhttp3/internal/http1/Http1ExchangeCodec$FixedLengthSource;", "Lokhttp3/internal/http1/Http1ExchangeCodec$AbstractSource;", "Lokhttp3/internal/http1/Http1ExchangeCodec;", "url", "Lokhttp3/HttpUrl;", "bytesRemaining", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokhttp3/internal/http1/Http1ExchangeCodec;Lokhttp3/HttpUrl;J)V", "read", "sink", "Lokio/Buffer;", "byteCount", SplitLayout.TAG_CLOSE, "", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private final class FixedLengthSource extends AbstractSource {
        private long bytesRemaining;
        final /* synthetic */ Http1ExchangeCodec this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public FixedLengthSource(Http1ExchangeCodec http1ExchangeCodec, HttpUrl url, long j) {
            super(http1ExchangeCodec, url);
            Intrinsics.checkNotNullParameter(url, "url");
            this.this$0 = http1ExchangeCodec;
            this.bytesRemaining = j;
            if (j == 0) {
                responseBodyComplete(Headers.EMPTY);
            }
        }

        @Override // okhttp3.internal.http1.Http1ExchangeCodec.AbstractSource, okio.Source
        public long read(Buffer sink, long byteCount) throws IOException {
            Intrinsics.checkNotNullParameter(sink, "sink");
            if (byteCount < 0) {
                throw new IllegalArgumentException(("byteCount < 0: " + byteCount).toString());
            }
            if (getClosed()) {
                throw new IllegalStateException("closed".toString());
            }
            long j = this.bytesRemaining;
            if (j == 0) {
                return -1L;
            }
            long j2 = super.read(sink, Math.min(j, byteCount));
            if (j2 == -1) {
                this.this$0.getCarrier().noNewExchanges();
                ProtocolException protocolException = new ProtocolException("unexpected end of stream");
                responseBodyComplete(Http1ExchangeCodec.TRAILERS_RESPONSE_BODY_TRUNCATED);
                throw protocolException;
            }
            long j3 = this.bytesRemaining - j2;
            this.bytesRemaining = j3;
            if (j3 == 0) {
                responseBodyComplete(Headers.EMPTY);
            }
            return j2;
        }

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            if (getClosed()) {
                return;
            }
            if (this.bytesRemaining != 0 && !_UtilJvmKt.discard(this, 100, TimeUnit.MILLISECONDS)) {
                this.this$0.getCarrier().noNewExchanges();
                responseBodyComplete(Http1ExchangeCodec.TRAILERS_RESPONSE_BODY_TRUNCATED);
            }
            setClosed(true);
        }
    }

    /* JADX INFO: compiled from: Http1ExchangeCodec.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00060\u0001R\u00020\u0002B\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0004\b\u0005\u0010\u0006J\u0018\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\bH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0002J\b\u0010\u0011\u001a\u00020\u0010H\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lokhttp3/internal/http1/Http1ExchangeCodec$ChunkedSource;", "Lokhttp3/internal/http1/Http1ExchangeCodec$AbstractSource;", "Lokhttp3/internal/http1/Http1ExchangeCodec;", "url", "Lokhttp3/HttpUrl;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokhttp3/internal/http1/Http1ExchangeCodec;Lokhttp3/HttpUrl;)V", "bytesRemainingInChunk", "", "hasMoreChunks", "", "read", "sink", "Lokio/Buffer;", "byteCount", "readChunkSize", "", SplitLayout.TAG_CLOSE, "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private final class ChunkedSource extends AbstractSource {
        private long bytesRemainingInChunk;
        private boolean hasMoreChunks;
        final /* synthetic */ Http1ExchangeCodec this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ChunkedSource(Http1ExchangeCodec http1ExchangeCodec, HttpUrl url) {
            super(http1ExchangeCodec, url);
            Intrinsics.checkNotNullParameter(url, "url");
            this.this$0 = http1ExchangeCodec;
            this.bytesRemainingInChunk = -1L;
            this.hasMoreChunks = true;
        }

        @Override // okhttp3.internal.http1.Http1ExchangeCodec.AbstractSource, okio.Source
        public long read(Buffer sink, long byteCount) throws IOException {
            Intrinsics.checkNotNullParameter(sink, "sink");
            if (byteCount < 0) {
                throw new IllegalArgumentException(("byteCount < 0: " + byteCount).toString());
            }
            if (getClosed()) {
                throw new IllegalStateException("closed".toString());
            }
            if (!this.hasMoreChunks) {
                return -1L;
            }
            long j = this.bytesRemainingInChunk;
            if (j == 0 || j == -1) {
                readChunkSize();
                if (!this.hasMoreChunks) {
                    return -1L;
                }
            }
            long j2 = super.read(sink, Math.min(byteCount, this.bytesRemainingInChunk));
            if (j2 == -1) {
                this.this$0.getCarrier().noNewExchanges();
                ProtocolException protocolException = new ProtocolException("unexpected end of stream");
                responseBodyComplete(Http1ExchangeCodec.TRAILERS_RESPONSE_BODY_TRUNCATED);
                throw protocolException;
            }
            this.bytesRemainingInChunk -= j2;
            return j2;
        }

        private final void readChunkSize() throws IOException {
            if (this.bytesRemainingInChunk != -1) {
                this.this$0.source.readUtf8LineStrict();
            }
            try {
                this.bytesRemainingInChunk = this.this$0.source.readHexadecimalUnsignedLong();
                String string = StringsKt.trim((CharSequence) this.this$0.source.readUtf8LineStrict()).toString();
                if (this.bytesRemainingInChunk < 0 || (string.length() > 0 && !StringsKt.startsWith$default(string, ";", false, 2, (Object) null))) {
                    throw new ProtocolException("expected chunk size and optional extensions but was \"" + this.bytesRemainingInChunk + string + '\"');
                }
                if (this.bytesRemainingInChunk == 0) {
                    this.hasMoreChunks = false;
                    responseBodyComplete(this.this$0.headersReader.readHeaders());
                }
            } catch (NumberFormatException e) {
                throw new ProtocolException(e.getMessage());
            }
        }

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            if (getClosed()) {
                return;
            }
            if (this.hasMoreChunks && !_UtilJvmKt.discard(this, 100, TimeUnit.MILLISECONDS)) {
                this.this$0.getCarrier().noNewExchanges();
                responseBodyComplete(Http1ExchangeCodec.TRAILERS_RESPONSE_BODY_TRUNCATED);
            }
            setClosed(true);
        }
    }

    /* JADX INFO: compiled from: Http1ExchangeCodec.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00060\u0001R\u00020\u0002B\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0004\b\u0005\u0010\u0006J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\nH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lokhttp3/internal/http1/Http1ExchangeCodec$UnknownLengthSource;", "Lokhttp3/internal/http1/Http1ExchangeCodec$AbstractSource;", "Lokhttp3/internal/http1/Http1ExchangeCodec;", "url", "Lokhttp3/HttpUrl;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokhttp3/internal/http1/Http1ExchangeCodec;Lokhttp3/HttpUrl;)V", "inputExhausted", "", "read", "", "sink", "Lokio/Buffer;", "byteCount", SplitLayout.TAG_CLOSE, "", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private final class UnknownLengthSource extends AbstractSource {
        private boolean inputExhausted;
        final /* synthetic */ Http1ExchangeCodec this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public UnknownLengthSource(Http1ExchangeCodec http1ExchangeCodec, HttpUrl url) {
            super(http1ExchangeCodec, url);
            Intrinsics.checkNotNullParameter(url, "url");
            this.this$0 = http1ExchangeCodec;
        }

        @Override // okhttp3.internal.http1.Http1ExchangeCodec.AbstractSource, okio.Source
        public long read(Buffer sink, long byteCount) throws IOException {
            Intrinsics.checkNotNullParameter(sink, "sink");
            if (byteCount < 0) {
                throw new IllegalArgumentException(("byteCount < 0: " + byteCount).toString());
            }
            if (getClosed()) {
                throw new IllegalStateException("closed".toString());
            }
            if (this.inputExhausted) {
                return -1L;
            }
            long j = super.read(sink, byteCount);
            if (j != -1) {
                return j;
            }
            this.inputExhausted = true;
            responseBodyComplete(Headers.EMPTY);
            return -1L;
        }

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            if (getClosed()) {
                return;
            }
            if (!this.inputExhausted) {
                responseBodyComplete(Http1ExchangeCodec.TRAILERS_RESPONSE_BODY_TRUNCATED);
            }
            setClosed(true);
        }
    }
}
