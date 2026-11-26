package okhttp3.internal.ws;

import ch.qos.logback.core.CoreConstants;
import com.google.common.net.HttpHeaders;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.ToDoStat;
import com.xiaomi.accountsdk.account.stat.StatConstants;
import com.xiaomi.accountsdk.request.PassportSimpleRequest;
import com.xiaomi.onetrack.api.ah;
import java.io.Closeable;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.IntRange;
import kotlin.text.StringsKt;
import miui.cloud.CloudPushConstants;
import miuix.navigation.SplitLayout;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.EventListener;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.internal._UtilCommonKt;
import okhttp3.internal._UtilJvmKt;
import okhttp3.internal.concurrent.Lockable;
import okhttp3.internal.concurrent.Task;
import okhttp3.internal.concurrent.TaskQueue;
import okhttp3.internal.concurrent.TaskRunner;
import okhttp3.internal.connection.Exchange;
import okhttp3.internal.connection.RealCall;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;

/* JADX INFO: compiled from: RealWebSocket.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000°\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 h2\u00020\u00012\u00020\u00022\u00020\u0003:\u0005defghBI\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\u0006\u0010\u0010\u001a\u00020\r\u0012\u0006\u0010\u0011\u001a\u00020\r¢\u0006\u0004\b\u0012\u0010\u0013J\b\u00109\u001a\u00020\u0007H\u0016J\b\u0010.\u001a\u00020\rH\u0016J\b\u0010:\u001a\u00020;H\u0016J\u000e\u0010<\u001a\u00020;2\u0006\u0010=\u001a\u00020>J\f\u0010?\u001a\u000200*\u00020\u000fH\u0002J\u001f\u0010@\u001a\u00020;2\u0006\u0010A\u001a\u00020B2\b\u0010C\u001a\u0004\u0018\u00010DH\u0000¢\u0006\u0002\bEJ\u0016\u0010F\u001a\u00020;2\u0006\u0010&\u001a\u00020\u00172\u0006\u0010'\u001a\u00020(J\u000e\u0010G\u001a\u00020;2\u0006\u0010A\u001a\u00020BJ\u0006\u0010H\u001a\u000200J\u0006\u0010I\u001a\u00020;J\u0006\u0010J\u001a\u00020;J\u0006\u00105\u001a\u000202J\u0006\u00106\u001a\u000202J\u0006\u00107\u001a\u000202J\u0010\u0010K\u001a\u00020;2\u0006\u0010L\u001a\u00020\u0017H\u0016J\u0010\u0010K\u001a\u00020;2\u0006\u0010M\u001a\u00020+H\u0016J\u0010\u0010N\u001a\u00020;2\u0006\u0010O\u001a\u00020+H\u0016J\u0010\u0010P\u001a\u00020;2\u0006\u0010O\u001a\u00020+H\u0016J\u0018\u0010Q\u001a\u00020;2\u0006\u0010R\u001a\u0002022\u0006\u0010S\u001a\u00020\u0017H\u0016J\u0010\u0010T\u001a\u0002002\u0006\u0010L\u001a\u00020\u0017H\u0016J\u0010\u0010T\u001a\u0002002\u0006\u0010M\u001a\u00020+H\u0016J\u0018\u0010T\u001a\u0002002\u0006\u0010U\u001a\u00020+2\u0006\u0010V\u001a\u000202H\u0002J\u000e\u0010W\u001a\u0002002\u0006\u0010O\u001a\u00020+J\u001a\u0010X\u001a\u0002002\u0006\u0010R\u001a\u0002022\b\u0010S\u001a\u0004\u0018\u00010\u0017H\u0016J \u0010X\u001a\u0002002\u0006\u0010R\u001a\u0002022\b\u0010S\u001a\u0004\u0018\u00010\u00172\u0006\u0010Y\u001a\u00020\rJ\b\u0010Z\u001a\u00020;H\u0002J\r\u0010[\u001a\u000200H\u0000¢\u0006\u0002\b\\J\r\u0010]\u001a\u00020;H\u0000¢\u0006\u0002\b^J(\u0010_\u001a\u00020;2\n\u0010`\u001a\u00060aj\u0002`b2\n\b\u0002\u0010A\u001a\u0004\u0018\u00010B2\b\b\u0002\u0010c\u001a\u000200R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\tX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010 \u001a\u0004\u0018\u00010!X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\"\u001a\u0004\u0018\u00010#X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020%X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010&\u001a\u0004\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010'\u001a\u0004\u0018\u00010(X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010)\u001a\b\u0012\u0004\u0012\u00020+0*X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010,\u001a\b\u0012\u0004\u0012\u00020-0*X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u000200X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u000202X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00103\u001a\u0004\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u000200X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00105\u001a\u000202X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00106\u001a\u000202X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00107\u001a\u000202X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00108\u001a\u000200X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006i"}, d2 = {"Lokhttp3/internal/ws/RealWebSocket;", "Lokhttp3/WebSocket;", "Lokhttp3/internal/ws/WebSocketReader$FrameCallback;", "Lokhttp3/internal/concurrent/Lockable;", "taskRunner", "Lokhttp3/internal/concurrent/TaskRunner;", "originalRequest", "Lokhttp3/Request;", "listener", "Lokhttp3/WebSocketListener;", "random", "Ljava/util/Random;", "pingIntervalMillis", "", "extensions", "Lokhttp3/internal/ws/WebSocketExtensions;", "minimumDeflateSize", "webSocketCloseTimeout", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokhttp3/internal/concurrent/TaskRunner;Lokhttp3/Request;Lokhttp3/WebSocketListener;Ljava/util/Random;JLokhttp3/internal/ws/WebSocketExtensions;JJ)V", "getListener$okhttp", "()Lokhttp3/WebSocketListener;", "key", "", "call", "Lokhttp3/Call;", "getCall$okhttp", "()Lokhttp3/Call;", "setCall$okhttp", "(Lokhttp3/Call;)V", "writerTask", "Lokhttp3/internal/concurrent/Task;", "reader", "Lokhttp3/internal/ws/WebSocketReader;", "writer", "Lokhttp3/internal/ws/WebSocketWriter;", "taskQueue", "Lokhttp3/internal/concurrent/TaskQueue;", "name", "streams", "Lokhttp3/internal/ws/RealWebSocket$Streams;", "pongQueue", "Ljava/util/ArrayDeque;", "Lokio/ByteString;", "messageAndCloseQueue", "", "queueSize", "enqueuedClose", "", "receivedCloseCode", "", "receivedCloseReason", "failed", "sentPingCount", "receivedPingCount", "receivedPongCount", "awaitingPong", StatConstants.Event.REQUEST, ToDoStat.CANCEL, "", "connect", "client", "Lokhttp3/OkHttpClient;", "isValid", "checkUpgradeSuccess", ah.H, "Lokhttp3/Response;", "exchange", "Lokhttp3/internal/connection/Exchange;", "checkUpgradeSuccess$okhttp", "initReaderAndWriter", "loopReader", "processNextFrame", "finishReader", "tearDown", "onReadMessage", "text", "bytes", "onReadPing", CloudPushConstants.XML_PAYLOAD, "onReadPong", "onReadClose", "code", "reason", "send", "data", "formatOpcode", "pong", SplitLayout.TAG_CLOSE, "cancelAfterCloseMillis", "runWriter", "writeOneFrame", "writeOneFrame$okhttp", "writePingFrame", "writePingFrame$okhttp", "failWebSocket", "e", "Ljava/lang/Exception;", "Lkotlin/Exception;", "isWriter", "Message", "Close", "Streams", "WriterTask", "Companion", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class RealWebSocket implements WebSocket, WebSocketReader.FrameCallback, Lockable {
    public static final long CANCEL_AFTER_CLOSE_MILLIS = 60000;
    public static final long DEFAULT_MINIMUM_DEFLATE_SIZE = 1024;
    private static final long MAX_QUEUE_SIZE = 16777216;
    private boolean awaitingPong;
    private Call call;
    private boolean enqueuedClose;
    private WebSocketExtensions extensions;
    private boolean failed;
    private final String key;
    private final WebSocketListener listener;
    private final ArrayDeque<Object> messageAndCloseQueue;
    private long minimumDeflateSize;
    private String name;
    private final Request originalRequest;
    private final long pingIntervalMillis;
    private final ArrayDeque<ByteString> pongQueue;
    private long queueSize;
    private final Random random;
    private WebSocketReader reader;
    private int receivedCloseCode;
    private String receivedCloseReason;
    private int receivedPingCount;
    private int receivedPongCount;
    private int sentPingCount;
    private Streams streams;
    private TaskQueue taskQueue;
    private final long webSocketCloseTimeout;
    private WebSocketWriter writer;
    private Task writerTask;
    private static final List<Protocol> ONLY_HTTP1 = CollectionsKt.listOf(Protocol.HTTP_1_1);

    public RealWebSocket(TaskRunner taskRunner, Request originalRequest, WebSocketListener listener, Random random, long j, WebSocketExtensions webSocketExtensions, long j2, long j3) {
        Intrinsics.checkNotNullParameter(taskRunner, "taskRunner");
        Intrinsics.checkNotNullParameter(originalRequest, "originalRequest");
        Intrinsics.checkNotNullParameter(listener, "listener");
        Intrinsics.checkNotNullParameter(random, "random");
        this.originalRequest = originalRequest;
        this.listener = listener;
        this.random = random;
        this.pingIntervalMillis = j;
        this.extensions = webSocketExtensions;
        this.minimumDeflateSize = j2;
        this.webSocketCloseTimeout = j3;
        this.taskQueue = taskRunner.newQueue();
        this.pongQueue = new ArrayDeque<>();
        this.messageAndCloseQueue = new ArrayDeque<>();
        this.receivedCloseCode = -1;
        if (!Intrinsics.areEqual(PassportSimpleRequest.HTTP_METHOD_GET, originalRequest.method())) {
            throw new IllegalArgumentException(("Request must be GET: " + originalRequest.method()).toString());
        }
        ByteString.Companion companion = ByteString.INSTANCE;
        byte[] bArr = new byte[16];
        random.nextBytes(bArr);
        Unit unit = Unit.INSTANCE;
        this.key = ByteString.Companion.of$default(companion, bArr, 0, 0, 3, null).base64();
    }

    /* JADX INFO: renamed from: getListener$okhttp, reason: from getter */
    public final WebSocketListener getListener() {
        return this.listener;
    }

    /* JADX INFO: renamed from: getCall$okhttp, reason: from getter */
    public final Call getCall() {
        return this.call;
    }

    public final void setCall$okhttp(Call call) {
        this.call = call;
    }

    @Override // okhttp3.WebSocket
    /* JADX INFO: renamed from: request, reason: from getter */
    public Request getOriginalRequest() {
        return this.originalRequest;
    }

    @Override // okhttp3.WebSocket
    public synchronized long queueSize() {
        return this.queueSize;
    }

    @Override // okhttp3.WebSocket
    public void cancel() {
        Call call = this.call;
        Intrinsics.checkNotNull(call);
        call.cancel();
    }

    public final void connect(OkHttpClient client) {
        Intrinsics.checkNotNullParameter(client, "client");
        if (this.originalRequest.header(HttpHeaders.SEC_WEBSOCKET_EXTENSIONS) != null) {
            failWebSocket$default(this, new ProtocolException("Request header not permitted: 'Sec-WebSocket-Extensions'"), null, false, 6, null);
            return;
        }
        OkHttpClient okHttpClientBuild = client.newBuilder().eventListener(EventListener.NONE).protocols(ONLY_HTTP1).build();
        final Request requestBuild = this.originalRequest.newBuilder().header("Upgrade", "websocket").header("Connection", "Upgrade").header(HttpHeaders.SEC_WEBSOCKET_KEY, this.key).header(HttpHeaders.SEC_WEBSOCKET_VERSION, "13").header(HttpHeaders.SEC_WEBSOCKET_EXTENSIONS, "permessage-deflate").build();
        RealCall realCall = new RealCall(okHttpClientBuild, requestBuild, true);
        this.call = realCall;
        Intrinsics.checkNotNull(realCall);
        realCall.enqueue(new Callback() { // from class: okhttp3.internal.ws.RealWebSocket.connect.1
            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) throws Throwable {
                Intrinsics.checkNotNullParameter(call, "call");
                Intrinsics.checkNotNullParameter(response, "response");
                Exchange exchange = response.getExchange();
                try {
                    RealWebSocket.this.checkUpgradeSuccess$okhttp(response, exchange);
                    Intrinsics.checkNotNull(exchange);
                    Streams streamsNewWebSocketStreams = exchange.newWebSocketStreams();
                    WebSocketExtensions webSocketExtensions = WebSocketExtensions.INSTANCE.parse(response.headers());
                    RealWebSocket.this.extensions = webSocketExtensions;
                    if (!RealWebSocket.this.isValid(webSocketExtensions)) {
                        RealWebSocket realWebSocket = RealWebSocket.this;
                        synchronized (realWebSocket) {
                            realWebSocket.messageAndCloseQueue.clear();
                            realWebSocket.close(1010, "unexpected Sec-WebSocket-Extensions in response header");
                        }
                    }
                    RealWebSocket.this.initReaderAndWriter(_UtilJvmKt.okHttpName + " WebSocket " + requestBuild.url().redact(), streamsNewWebSocketStreams);
                    RealWebSocket.this.loopReader(response);
                } catch (IOException e) {
                    RealWebSocket.failWebSocket$default(RealWebSocket.this, e, response, false, 4, null);
                    _UtilCommonKt.closeQuietly(response);
                    if (exchange != null) {
                        exchange.webSocketUpgradeFailed();
                    }
                }
            }

            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException e) {
                Intrinsics.checkNotNullParameter(call, "call");
                Intrinsics.checkNotNullParameter(e, "e");
                RealWebSocket.failWebSocket$default(RealWebSocket.this, e, null, false, 6, null);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isValid(WebSocketExtensions webSocketExtensions) {
        if (!webSocketExtensions.unknownValues && webSocketExtensions.clientMaxWindowBits == null) {
            return webSocketExtensions.serverMaxWindowBits == null || new IntRange(8, 15).contains(webSocketExtensions.serverMaxWindowBits.intValue());
        }
        return false;
    }

    public final void checkUpgradeSuccess$okhttp(Response response, Exchange exchange) throws IOException {
        Intrinsics.checkNotNullParameter(response, "response");
        if (response.code() != 101) {
            throw new ProtocolException("Expected HTTP 101 response but was '" + response.code() + ' ' + response.message() + CoreConstants.SINGLE_QUOTE_CHAR);
        }
        String strHeader$default = Response.header$default(response, "Connection", null, 2, null);
        if (!StringsKt.equals("Upgrade", strHeader$default, true)) {
            throw new ProtocolException("Expected 'Connection' header value 'Upgrade' but was '" + strHeader$default + CoreConstants.SINGLE_QUOTE_CHAR);
        }
        String strHeader$default2 = Response.header$default(response, "Upgrade", null, 2, null);
        if (!StringsKt.equals("websocket", strHeader$default2, true)) {
            throw new ProtocolException("Expected 'Upgrade' header value 'websocket' but was '" + strHeader$default2 + CoreConstants.SINGLE_QUOTE_CHAR);
        }
        String strHeader$default3 = Response.header$default(response, HttpHeaders.SEC_WEBSOCKET_ACCEPT, null, 2, null);
        String strBase64 = ByteString.INSTANCE.encodeUtf8(this.key + WebSocketProtocol.ACCEPT_MAGIC).sha1().base64();
        if (!Intrinsics.areEqual(strBase64, strHeader$default3)) {
            throw new ProtocolException("Expected 'Sec-WebSocket-Accept' header value '" + strBase64 + "' but was '" + strHeader$default3 + CoreConstants.SINGLE_QUOTE_CHAR);
        }
        if (exchange == null) {
            throw new ProtocolException("Web Socket exchange missing: bad interceptor?");
        }
    }

    public final void initReaderAndWriter(String name, Streams streams) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(streams, "streams");
        WebSocketExtensions webSocketExtensions = this.extensions;
        Intrinsics.checkNotNull(webSocketExtensions);
        synchronized (this) {
            this.name = name;
            this.streams = streams;
            this.writer = new WebSocketWriter(streams.getClient(), streams.getSink(), this.random, webSocketExtensions.perMessageDeflate, webSocketExtensions.noContextTakeover(streams.getClient()), this.minimumDeflateSize);
            this.writerTask = new WriterTask();
            if (this.pingIntervalMillis != 0) {
                final long nanos = TimeUnit.MILLISECONDS.toNanos(this.pingIntervalMillis);
                this.taskQueue.schedule(name + " ping", nanos, new Function0() { // from class: okhttp3.internal.ws.RealWebSocket$$ExternalSyntheticLambda3
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return Long.valueOf(RealWebSocket.initReaderAndWriter$lambda$3$lambda$2(this.f$0, nanos));
                    }
                });
            }
            if (!this.messageAndCloseQueue.isEmpty()) {
                runWriter();
            }
            Unit unit = Unit.INSTANCE;
        }
        this.reader = new WebSocketReader(streams.getClient(), streams.getSource(), this, webSocketExtensions.perMessageDeflate, webSocketExtensions.noContextTakeover(!streams.getClient()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final long initReaderAndWriter$lambda$3$lambda$2(RealWebSocket realWebSocket, long j) {
        realWebSocket.writePingFrame$okhttp();
        return j;
    }

    public final void loopReader(Response response) throws Throwable {
        Throwable th;
        RealWebSocket realWebSocket;
        Intrinsics.checkNotNullParameter(response, "response");
        try {
            this.listener.onOpen(this, response);
            while (this.receivedCloseCode == -1) {
                WebSocketReader webSocketReader = this.reader;
                Intrinsics.checkNotNull(webSocketReader);
                webSocketReader.processNextFrame();
            }
            finishReader();
        } catch (Exception e) {
            realWebSocket = this;
            try {
                failWebSocket$default(realWebSocket, e, null, false, 6, null);
                realWebSocket.finishReader();
            } catch (Throwable th2) {
                th = th2;
                realWebSocket.finishReader();
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            realWebSocket = this;
            realWebSocket.finishReader();
            throw th;
        }
    }

    public final boolean processNextFrame() throws IOException {
        try {
            WebSocketReader webSocketReader = this.reader;
            Intrinsics.checkNotNull(webSocketReader);
            webSocketReader.processNextFrame();
            return this.receivedCloseCode == -1;
        } catch (Exception e) {
            failWebSocket$default(this, e, null, false, 6, null);
            return false;
        }
    }

    public final void finishReader() {
        boolean z;
        int i;
        String str;
        WebSocketReader webSocketReader;
        Streams streams;
        synchronized (this) {
            z = this.failed;
            i = this.receivedCloseCode;
            str = this.receivedCloseReason;
            webSocketReader = this.reader;
            this.reader = null;
            if (this.enqueuedClose && this.messageAndCloseQueue.isEmpty()) {
                final WebSocketWriter webSocketWriter = this.writer;
                if (webSocketWriter != null) {
                    this.writer = null;
                    TaskQueue.execute$default(this.taskQueue, this.name + " writer close", 0L, false, new Function0() { // from class: okhttp3.internal.ws.RealWebSocket$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return RealWebSocket.finishReader$lambda$5$lambda$4(webSocketWriter);
                        }
                    }, 2, null);
                }
                this.taskQueue.shutdown();
            }
            streams = this.writer == null ? this.streams : null;
            Unit unit = Unit.INSTANCE;
        }
        if (!z && streams != null && this.receivedCloseCode != -1) {
            Intrinsics.checkNotNull(str);
            this.listener.onClosed(this, i, str);
        }
        if (webSocketReader != null) {
            _UtilCommonKt.closeQuietly(webSocketReader);
        }
        if (streams != null) {
            _UtilCommonKt.closeQuietly(streams);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit finishReader$lambda$5$lambda$4(WebSocketWriter webSocketWriter) {
        _UtilCommonKt.closeQuietly(webSocketWriter);
        return Unit.INSTANCE;
    }

    public final void tearDown() throws InterruptedException {
        this.taskQueue.shutdown();
        this.taskQueue.idleLatch().await(10L, TimeUnit.SECONDS);
    }

    public final synchronized int sentPingCount() {
        return this.sentPingCount;
    }

    public final synchronized int receivedPingCount() {
        return this.receivedPingCount;
    }

    public final synchronized int receivedPongCount() {
        return this.receivedPongCount;
    }

    @Override // okhttp3.internal.ws.WebSocketReader.FrameCallback
    public void onReadMessage(String text) throws IOException {
        Intrinsics.checkNotNullParameter(text, "text");
        this.listener.onMessage(this, text);
    }

    @Override // okhttp3.internal.ws.WebSocketReader.FrameCallback
    public void onReadMessage(ByteString bytes) throws IOException {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        this.listener.onMessage(this, bytes);
    }

    @Override // okhttp3.internal.ws.WebSocketReader.FrameCallback
    public synchronized void onReadPing(ByteString payload) {
        Intrinsics.checkNotNullParameter(payload, "payload");
        if (!this.failed && (!this.enqueuedClose || !this.messageAndCloseQueue.isEmpty())) {
            this.pongQueue.add(payload);
            runWriter();
            this.receivedPingCount++;
        }
    }

    @Override // okhttp3.internal.ws.WebSocketReader.FrameCallback
    public synchronized void onReadPong(ByteString payload) {
        Intrinsics.checkNotNullParameter(payload, "payload");
        this.receivedPongCount++;
        this.awaitingPong = false;
    }

    @Override // okhttp3.internal.ws.WebSocketReader.FrameCallback
    public void onReadClose(int code, String reason) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        if (code == -1) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        synchronized (this) {
            if (this.receivedCloseCode != -1) {
                throw new IllegalStateException("already closed".toString());
            }
            this.receivedCloseCode = code;
            this.receivedCloseReason = reason;
            Unit unit = Unit.INSTANCE;
        }
        this.listener.onClosing(this, code, reason);
    }

    @Override // okhttp3.WebSocket
    public boolean send(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        return send(ByteString.INSTANCE.encodeUtf8(text), 1);
    }

    @Override // okhttp3.WebSocket
    public boolean send(ByteString bytes) {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        return send(bytes, 2);
    }

    private final synchronized boolean send(ByteString data, int formatOpcode) {
        if (!this.failed && !this.enqueuedClose) {
            if (this.queueSize + ((long) data.size()) > MAX_QUEUE_SIZE) {
                close(1001, null);
                return false;
            }
            this.queueSize += (long) data.size();
            this.messageAndCloseQueue.add(new Message(formatOpcode, data));
            runWriter();
            return true;
        }
        return false;
    }

    public final synchronized boolean pong(ByteString payload) {
        Intrinsics.checkNotNullParameter(payload, "payload");
        if (!this.failed && (!this.enqueuedClose || !this.messageAndCloseQueue.isEmpty())) {
            this.pongQueue.add(payload);
            runWriter();
            return true;
        }
        return false;
    }

    @Override // okhttp3.WebSocket
    public boolean close(int code, String reason) {
        return close(code, reason, this.webSocketCloseTimeout);
    }

    public final synchronized boolean close(int code, String reason, long cancelAfterCloseMillis) {
        ByteString byteStringEncodeUtf8;
        WebSocketProtocol.INSTANCE.validateCloseCode(code);
        if (reason != null) {
            byteStringEncodeUtf8 = ByteString.INSTANCE.encodeUtf8(reason);
            if (byteStringEncodeUtf8.size() > 123) {
                throw new IllegalArgumentException(("reason.size() > 123: " + reason).toString());
            }
        } else {
            byteStringEncodeUtf8 = null;
        }
        if (!this.failed && !this.enqueuedClose) {
            this.enqueuedClose = true;
            this.messageAndCloseQueue.add(new Close(code, byteStringEncodeUtf8, cancelAfterCloseMillis));
            runWriter();
            return true;
        }
        return false;
    }

    private final void runWriter() {
        RealWebSocket realWebSocket = this;
        if (!_UtilJvmKt.assertionsEnabled || Thread.holdsLock(realWebSocket)) {
            Task task = this.writerTask;
            if (task != null) {
                TaskQueue.schedule$default(this.taskQueue, task, 0L, 2, null);
                return;
            }
            return;
        }
        throw new AssertionError("Thread " + Thread.currentThread().getName() + " MUST hold lock on " + realWebSocket);
    }

    /* JADX WARN: Code duplicated, block: B:32:0x0085 A[Catch: all -> 0x00ee, TRY_ENTER, TryCatch #2 {all -> 0x00ee, blocks: (B:32:0x0085, B:33:0x008e, B:35:0x0092, B:36:0x00a2, B:38:0x00b3, B:41:0x00b6, B:42:0x00b7, B:43:0x00b8, B:45:0x00bc, B:47:0x00ce, B:54:0x00e8, B:55:0x00ed, B:37:0x00a3), top: B:71:0x0083, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:33:0x008e A[Catch: all -> 0x00ee, TryCatch #2 {all -> 0x00ee, blocks: (B:32:0x0085, B:33:0x008e, B:35:0x0092, B:36:0x00a2, B:38:0x00b3, B:41:0x00b6, B:42:0x00b7, B:43:0x00b8, B:45:0x00bc, B:47:0x00ce, B:54:0x00e8, B:55:0x00ed, B:37:0x00a3), top: B:71:0x0083, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:35:0x0092 A[Catch: all -> 0x00ee, TryCatch #2 {all -> 0x00ee, blocks: (B:32:0x0085, B:33:0x008e, B:35:0x0092, B:36:0x00a2, B:38:0x00b3, B:41:0x00b6, B:42:0x00b7, B:43:0x00b8, B:45:0x00bc, B:47:0x00ce, B:54:0x00e8, B:55:0x00ed, B:37:0x00a3), top: B:71:0x0083, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:43:0x00b8 A[Catch: all -> 0x00ee, TryCatch #2 {all -> 0x00ee, blocks: (B:32:0x0085, B:33:0x008e, B:35:0x0092, B:36:0x00a2, B:38:0x00b3, B:41:0x00b6, B:42:0x00b7, B:43:0x00b8, B:45:0x00bc, B:47:0x00ce, B:54:0x00e8, B:55:0x00ed, B:37:0x00a3), top: B:71:0x0083, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:45:0x00bc A[Catch: all -> 0x00ee, TryCatch #2 {all -> 0x00ee, blocks: (B:32:0x0085, B:33:0x008e, B:35:0x0092, B:36:0x00a2, B:38:0x00b3, B:41:0x00b6, B:42:0x00b7, B:43:0x00b8, B:45:0x00bc, B:47:0x00ce, B:54:0x00e8, B:55:0x00ed, B:37:0x00a3), top: B:71:0x0083, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:47:0x00ce A[Catch: all -> 0x00ee, TRY_LEAVE, TryCatch #2 {all -> 0x00ee, blocks: (B:32:0x0085, B:33:0x008e, B:35:0x0092, B:36:0x00a2, B:38:0x00b3, B:41:0x00b6, B:42:0x00b7, B:43:0x00b8, B:45:0x00bc, B:47:0x00ce, B:54:0x00e8, B:55:0x00ed, B:37:0x00a3), top: B:71:0x0083, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:49:0x00da  */
    /* JADX WARN: Code duplicated, block: B:52:0x00e2  */
    /* JADX WARN: Code duplicated, block: B:54:0x00e8 A[Catch: all -> 0x00ee, TRY_ENTER, TryCatch #2 {all -> 0x00ee, blocks: (B:32:0x0085, B:33:0x008e, B:35:0x0092, B:36:0x00a2, B:38:0x00b3, B:41:0x00b6, B:42:0x00b7, B:43:0x00b8, B:45:0x00bc, B:47:0x00ce, B:54:0x00e8, B:55:0x00ed, B:37:0x00a3), top: B:71:0x0083, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:69:0x00a3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public final boolean writeOneFrame$okhttp() throws IOException {
        Object obj;
        String str;
        int i;
        Object obj2;
        Message message;
        synchronized (this) {
            if (this.failed) {
                return false;
            }
            WebSocketWriter webSocketWriter = this.writer;
            ByteString byteStringPoll = this.pongQueue.poll();
            Object obj3 = null;
            try {
                if (byteStringPoll == null) {
                    Object objPoll = this.messageAndCloseQueue.poll();
                    if (objPoll instanceof Close) {
                        int i2 = this.receivedCloseCode;
                        str = this.receivedCloseReason;
                        if (i2 != -1) {
                            obj2 = this.writer;
                            this.writer = null;
                            if (obj2 != null && this.reader == null) {
                                obj3 = this.streams;
                            }
                            this.taskQueue.shutdown();
                            i = i2;
                            obj = obj3;
                            obj3 = objPoll;
                        } else {
                            TaskQueue.execute$default(this.taskQueue, this.name + " cancel", TimeUnit.MILLISECONDS.toNanos(((Close) objPoll).getCancelAfterCloseMillis()), false, new Function0() { // from class: okhttp3.internal.ws.RealWebSocket$$ExternalSyntheticLambda0
                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    return RealWebSocket.writeOneFrame$lambda$10$lambda$9(this.f$0);
                                }
                            }, 4, null);
                            obj2 = null;
                            obj3 = objPoll;
                            i = i2;
                            obj = null;
                        }
                    } else {
                        if (objPoll == null) {
                            return false;
                        }
                        obj = null;
                        str = null;
                        obj3 = objPoll;
                    }
                    Unit unit = Unit.INSTANCE;
                    if (byteStringPoll != null) {
                        Intrinsics.checkNotNull(webSocketWriter);
                        webSocketWriter.writePong(byteStringPoll);
                    } else if (obj3 instanceof Message) {
                        message = (Message) obj3;
                        Intrinsics.checkNotNull(webSocketWriter);
                        webSocketWriter.writeMessageFrame(message.getFormatOpcode(), message.getData());
                        synchronized (this) {
                            this.queueSize -= (long) message.getData().size();
                            Unit unit2 = Unit.INSTANCE;
                        }
                    } else if (obj3 instanceof Close) {
                        Close close = (Close) obj3;
                        Intrinsics.checkNotNull(webSocketWriter);
                        webSocketWriter.writeClose(close.getCode(), close.getReason());
                        if (obj != null) {
                            Intrinsics.checkNotNull(str);
                            this.listener.onClosed(this, i, str);
                        }
                    } else {
                        throw new AssertionError();
                    }
                    if (obj2 != null) {
                        _UtilCommonKt.closeQuietly((Closeable) obj2);
                    }
                    if (obj != null) {
                        _UtilCommonKt.closeQuietly((Closeable) obj);
                    }
                    return true;
                }
                obj = null;
                str = null;
                if (byteStringPoll != null) {
                    Intrinsics.checkNotNull(webSocketWriter);
                    webSocketWriter.writePong(byteStringPoll);
                } else if (obj3 instanceof Message) {
                    message = (Message) obj3;
                    Intrinsics.checkNotNull(webSocketWriter);
                    webSocketWriter.writeMessageFrame(message.getFormatOpcode(), message.getData());
                    synchronized (this) {
                        this.queueSize -= (long) message.getData().size();
                        Unit unit3 = Unit.INSTANCE;
                    }
                } else if (obj3 instanceof Close) {
                    Close close2 = (Close) obj3;
                    Intrinsics.checkNotNull(webSocketWriter);
                    webSocketWriter.writeClose(close2.getCode(), close2.getReason());
                    if (obj != null) {
                        Intrinsics.checkNotNull(str);
                        this.listener.onClosed(this, i, str);
                    }
                } else {
                    throw new AssertionError();
                }
                if (obj2 != null) {
                    _UtilCommonKt.closeQuietly((Closeable) obj2);
                }
                if (obj != null) {
                    _UtilCommonKt.closeQuietly((Closeable) obj);
                }
                return true;
            } catch (Throwable th) {
                if (obj2 != null) {
                    _UtilCommonKt.closeQuietly((Closeable) obj2);
                }
                if (obj != null) {
                    _UtilCommonKt.closeQuietly((Closeable) obj);
                }
                throw th;
            }
            i = -1;
            obj2 = str;
            Unit unit4 = Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit writeOneFrame$lambda$10$lambda$9(RealWebSocket realWebSocket) {
        realWebSocket.cancel();
        return Unit.INSTANCE;
    }

    public final void writePingFrame$okhttp() {
        synchronized (this) {
            if (this.failed) {
                return;
            }
            WebSocketWriter webSocketWriter = this.writer;
            if (webSocketWriter == null) {
                return;
            }
            int i = this.awaitingPong ? this.sentPingCount : -1;
            this.sentPingCount++;
            this.awaitingPong = true;
            Unit unit = Unit.INSTANCE;
            if (i != -1) {
                failWebSocket$default(this, new SocketTimeoutException("sent ping but didn't receive pong within " + this.pingIntervalMillis + "ms (after " + (i - 1) + " successful ping/pongs)"), null, true, 2, null);
                return;
            }
            try {
                webSocketWriter.writePing(ByteString.EMPTY);
            } catch (IOException e) {
                failWebSocket$default(this, e, null, true, 2, null);
            }
        }
    }

    public static /* synthetic */ void failWebSocket$default(RealWebSocket realWebSocket, Exception exc, Response response, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            response = null;
        }
        if ((i & 4) != 0) {
            z = false;
        }
        realWebSocket.failWebSocket(exc, response, z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v4, types: [T, okhttp3.internal.ws.WebSocketWriter] */
    public final void failWebSocket(Exception e, Response response, boolean isWriter) {
        Intrinsics.checkNotNullParameter(e, "e");
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
        synchronized (this) {
            if (this.failed) {
                return;
            }
            this.failed = true;
            Streams streams = this.streams;
            objectRef2.element = this.writer;
            T t = 0;
            t = 0;
            this.writer = null;
            if (objectRef2.element != 0 && this.reader == null) {
                t = this.streams;
            }
            objectRef.element = t;
            if (!isWriter && objectRef2.element != 0) {
                TaskQueue.execute$default(this.taskQueue, this.name + " writer close", 0L, false, new Function0() { // from class: okhttp3.internal.ws.RealWebSocket$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return RealWebSocket.failWebSocket$lambda$14$lambda$13(objectRef2, objectRef);
                    }
                }, 2, null);
            }
            this.taskQueue.shutdown();
            Unit unit = Unit.INSTANCE;
            try {
                this.listener.onFailure(this, e, response);
            } finally {
                if (streams != null) {
                    streams.cancel();
                }
                if (isWriter) {
                    WebSocketWriter webSocketWriter = (WebSocketWriter) objectRef2.element;
                    if (webSocketWriter != null) {
                        _UtilCommonKt.closeQuietly(webSocketWriter);
                    }
                    Streams streams2 = (Streams) objectRef.element;
                    if (streams2 != null) {
                        _UtilCommonKt.closeQuietly(streams2);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final Unit failWebSocket$lambda$14$lambda$13(Ref.ObjectRef objectRef, Ref.ObjectRef objectRef2) {
        _UtilCommonKt.closeQuietly((Closeable) objectRef.element);
        Streams streams = (Streams) objectRef2.element;
        if (streams != null) {
            _UtilCommonKt.closeQuietly(streams);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: compiled from: RealWebSocket.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lokhttp3/internal/ws/RealWebSocket$Message;", "", "formatOpcode", "", "data", "Lokio/ByteString;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(ILokio/ByteString;)V", "getFormatOpcode", "()I", "getData", "()Lokio/ByteString;", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Message {
        private final ByteString data;
        private final int formatOpcode;

        public Message(int i, ByteString data) {
            Intrinsics.checkNotNullParameter(data, "data");
            this.formatOpcode = i;
            this.data = data;
        }

        public final int getFormatOpcode() {
            return this.formatOpcode;
        }

        public final ByteString getData() {
            return this.data;
        }
    }

    /* JADX INFO: compiled from: RealWebSocket.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\t\b\u0000\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, d2 = {"Lokhttp3/internal/ws/RealWebSocket$Close;", "", "code", "", "reason", "Lokio/ByteString;", "cancelAfterCloseMillis", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(ILokio/ByteString;J)V", "getCode", "()I", "getReason", "()Lokio/ByteString;", "getCancelAfterCloseMillis", "()J", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Close {
        private final long cancelAfterCloseMillis;
        private final int code;
        private final ByteString reason;

        public Close(int i, ByteString byteString, long j) {
            this.code = i;
            this.reason = byteString;
            this.cancelAfterCloseMillis = j;
        }

        public final int getCode() {
            return this.code;
        }

        public final ByteString getReason() {
            return this.reason;
        }

        public final long getCancelAfterCloseMillis() {
            return this.cancelAfterCloseMillis;
        }
    }

    /* JADX INFO: compiled from: RealWebSocket.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ\b\u0010\u0010\u001a\u00020\u0011H&R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0012"}, d2 = {"Lokhttp3/internal/ws/RealWebSocket$Streams;", "Ljava/io/Closeable;", "client", "", "source", "Lokio/BufferedSource;", "sink", "Lokio/BufferedSink;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(ZLokio/BufferedSource;Lokio/BufferedSink;)V", "getClient", "()Z", "getSource", "()Lokio/BufferedSource;", "getSink", "()Lokio/BufferedSink;", ToDoStat.CANCEL, "", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static abstract class Streams implements Closeable {
        private final boolean client;
        private final BufferedSink sink;
        private final BufferedSource source;

        public abstract void cancel();

        public Streams(boolean z, BufferedSource source, BufferedSink sink) {
            Intrinsics.checkNotNullParameter(source, "source");
            Intrinsics.checkNotNullParameter(sink, "sink");
            this.client = z;
            this.source = source;
            this.sink = sink;
        }

        public final boolean getClient() {
            return this.client;
        }

        public final BufferedSource getSource() {
            return this.source;
        }

        public final BufferedSink getSink() {
            return this.sink;
        }
    }

    /* JADX INFO: compiled from: RealWebSocket.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0016¨\u0006\u0006"}, d2 = {"Lokhttp3/internal/ws/RealWebSocket$WriterTask;", "Lokhttp3/internal/concurrent/Task;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokhttp3/internal/ws/RealWebSocket;)V", "runOnce", "", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private final class WriterTask extends Task {
        public WriterTask() {
            super(RealWebSocket.this.name + " writer", false, 2, null);
        }

        @Override // okhttp3.internal.concurrent.Task
        public long runOnce() {
            try {
                return RealWebSocket.this.writeOneFrame$okhttp() ? 0L : -1L;
            } catch (IOException e) {
                RealWebSocket.failWebSocket$default(RealWebSocket.this, e, null, true, 2, null);
                return -1L;
            }
        }
    }
}
