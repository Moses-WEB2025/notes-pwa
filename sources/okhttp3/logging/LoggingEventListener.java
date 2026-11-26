package okhttp3.logging;

import cn.kuaipan.kss.KssDef;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.accountsdk.account.stat.StatConstants;
import com.xiaomi.onetrack.api.ah;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.EventListener;
import okhttp3.Handshake;
import okhttp3.HttpUrl;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

/* JADX INFO: compiled from: LoggingEventListener.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0010\u0018\u0000 E2\u00020\u0001:\u0002DEB\u0011\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u0010\f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0016J&\u0010\u000f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0016J\u0018\u0010\u0013\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J&\u0010\u0016\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u00152\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u0011H\u0016J \u0010\u0019\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0012H\u0016J\u0010\u0010\u001d\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u001a\u0010\u001e\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0016J*\u0010!\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u00122\b\u0010\"\u001a\u0004\u0018\u00010#H\u0016J2\u0010$\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u00122\b\u0010\"\u001a\u0004\u0018\u00010#2\u0006\u0010%\u001a\u00020&H\u0016J\u0018\u0010'\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010(\u001a\u00020)H\u0016J\u0018\u0010*\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010(\u001a\u00020)H\u0016J\u0010\u0010+\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u0010,\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010-\u001a\u00020.H\u0016J\u0010\u0010/\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u00100\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u00101\u001a\u00020\u0007H\u0016J\u0018\u00102\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010%\u001a\u00020&H\u0016J\u0010\u00103\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u00104\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u00105\u001a\u000206H\u0016J\u0010\u00107\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u00108\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u00101\u001a\u00020\u0007H\u0016J\u0018\u00109\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010%\u001a\u00020&H\u0016J\u0010\u0010:\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u0010;\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010%\u001a\u00020&H\u0016J\u0010\u0010<\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u0010=\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u00105\u001a\u000206H\u0016J\u0018\u0010>\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u00105\u001a\u000206H\u0016J\u0010\u0010?\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u0010@\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010A\u001a\u000206H\u0016J\u0010\u0010B\u001a\u00020\t2\u0006\u0010C\u001a\u00020\u0015H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006F"}, d2 = {"Lokhttp3/logging/LoggingEventListener;", "Lokhttp3/EventListener;", "logger", "Lokhttp3/logging/HttpLoggingInterceptor$Logger;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokhttp3/logging/HttpLoggingInterceptor$Logger;)V", "startNs", "", "callStart", "", "call", "Lokhttp3/Call;", "proxySelectStart", "url", "Lokhttp3/HttpUrl;", "proxySelectEnd", KssDef.KEY_PROXIES, "", "Ljava/net/Proxy;", "dnsStart", "domainName", "", "dnsEnd", "inetAddressList", "Ljava/net/InetAddress;", "connectStart", "inetSocketAddress", "Ljava/net/InetSocketAddress;", "proxy", "secureConnectStart", "secureConnectEnd", ah.R, "Lokhttp3/Handshake;", "connectEnd", "protocol", "Lokhttp3/Protocol;", "connectFailed", "ioe", "Ljava/io/IOException;", "connectionAcquired", "connection", "Lokhttp3/Connection;", "connectionReleased", "requestHeadersStart", "requestHeadersEnd", StatConstants.Event.REQUEST, "Lokhttp3/Request;", "requestBodyStart", "requestBodyEnd", "byteCount", "requestFailed", "responseHeadersStart", "responseHeadersEnd", ah.H, "Lokhttp3/Response;", "responseBodyStart", "responseBodyEnd", "responseFailed", "callEnd", "callFailed", "canceled", "satisfactionFailure", "cacheHit", "cacheMiss", "cacheConditionalHit", "cachedResponse", "logWithTime", "message", "Factory", "Companion", "logging-interceptor"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class LoggingEventListener extends EventListener {
    private final HttpLoggingInterceptor.Logger logger;
    private long startNs;

    public /* synthetic */ LoggingEventListener(HttpLoggingInterceptor.Logger logger, DefaultConstructorMarker defaultConstructorMarker) {
        this(logger);
    }

    private LoggingEventListener(HttpLoggingInterceptor.Logger logger) {
        this.logger = logger;
    }

    @Override // okhttp3.EventListener
    public void callStart(Call call) {
        Intrinsics.checkNotNullParameter(call, "call");
        this.startNs = System.nanoTime();
        logWithTime("callStart: " + call.request());
    }

    @Override // okhttp3.EventListener
    public void proxySelectStart(Call call, HttpUrl url) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(url, "url");
        logWithTime("proxySelectStart: " + url);
    }

    @Override // okhttp3.EventListener
    public void proxySelectEnd(Call call, HttpUrl url, List<? extends Proxy> proxies) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(url, "url");
        Intrinsics.checkNotNullParameter(proxies, "proxies");
        logWithTime("proxySelectEnd: " + proxies);
    }

    @Override // okhttp3.EventListener
    public void dnsStart(Call call, String domainName) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(domainName, "domainName");
        logWithTime("dnsStart: " + domainName);
    }

    @Override // okhttp3.EventListener
    public void dnsEnd(Call call, String domainName, List<? extends InetAddress> inetAddressList) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(domainName, "domainName");
        Intrinsics.checkNotNullParameter(inetAddressList, "inetAddressList");
        logWithTime("dnsEnd: " + inetAddressList);
    }

    @Override // okhttp3.EventListener
    public void connectStart(Call call, InetSocketAddress inetSocketAddress, Proxy proxy) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(inetSocketAddress, "inetSocketAddress");
        Intrinsics.checkNotNullParameter(proxy, "proxy");
        logWithTime("connectStart: " + inetSocketAddress + ' ' + proxy);
    }

    @Override // okhttp3.EventListener
    public void secureConnectStart(Call call) {
        Intrinsics.checkNotNullParameter(call, "call");
        logWithTime("secureConnectStart");
    }

    @Override // okhttp3.EventListener
    public void secureConnectEnd(Call call, Handshake handshake) {
        Intrinsics.checkNotNullParameter(call, "call");
        logWithTime("secureConnectEnd: " + handshake);
    }

    @Override // okhttp3.EventListener
    public void connectEnd(Call call, InetSocketAddress inetSocketAddress, Proxy proxy, Protocol protocol) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(inetSocketAddress, "inetSocketAddress");
        Intrinsics.checkNotNullParameter(proxy, "proxy");
        logWithTime("connectEnd: " + protocol);
    }

    @Override // okhttp3.EventListener
    public void connectFailed(Call call, InetSocketAddress inetSocketAddress, Proxy proxy, Protocol protocol, IOException ioe) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(inetSocketAddress, "inetSocketAddress");
        Intrinsics.checkNotNullParameter(proxy, "proxy");
        Intrinsics.checkNotNullParameter(ioe, "ioe");
        logWithTime("connectFailed: " + protocol + ' ' + ioe);
    }

    @Override // okhttp3.EventListener
    public void connectionAcquired(Call call, Connection connection) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(connection, "connection");
        logWithTime("connectionAcquired: " + connection);
    }

    @Override // okhttp3.EventListener
    public void connectionReleased(Call call, Connection connection) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(connection, "connection");
        logWithTime("connectionReleased");
    }

    @Override // okhttp3.EventListener
    public void requestHeadersStart(Call call) {
        Intrinsics.checkNotNullParameter(call, "call");
        logWithTime("requestHeadersStart");
    }

    @Override // okhttp3.EventListener
    public void requestHeadersEnd(Call call, Request request) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(request, "request");
        logWithTime("requestHeadersEnd");
    }

    @Override // okhttp3.EventListener
    public void requestBodyStart(Call call) {
        Intrinsics.checkNotNullParameter(call, "call");
        logWithTime("requestBodyStart");
    }

    @Override // okhttp3.EventListener
    public void requestBodyEnd(Call call, long byteCount) {
        Intrinsics.checkNotNullParameter(call, "call");
        logWithTime("requestBodyEnd: byteCount=" + byteCount);
    }

    @Override // okhttp3.EventListener
    public void requestFailed(Call call, IOException ioe) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(ioe, "ioe");
        logWithTime("requestFailed: " + ioe);
    }

    @Override // okhttp3.EventListener
    public void responseHeadersStart(Call call) {
        Intrinsics.checkNotNullParameter(call, "call");
        logWithTime("responseHeadersStart");
    }

    @Override // okhttp3.EventListener
    public void responseHeadersEnd(Call call, Response response) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(response, "response");
        logWithTime("responseHeadersEnd: " + response);
    }

    @Override // okhttp3.EventListener
    public void responseBodyStart(Call call) {
        Intrinsics.checkNotNullParameter(call, "call");
        logWithTime("responseBodyStart");
    }

    @Override // okhttp3.EventListener
    public void responseBodyEnd(Call call, long byteCount) {
        Intrinsics.checkNotNullParameter(call, "call");
        logWithTime("responseBodyEnd: byteCount=" + byteCount);
    }

    @Override // okhttp3.EventListener
    public void responseFailed(Call call, IOException ioe) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(ioe, "ioe");
        logWithTime("responseFailed: " + ioe);
    }

    @Override // okhttp3.EventListener
    public void callEnd(Call call) {
        Intrinsics.checkNotNullParameter(call, "call");
        logWithTime("callEnd");
    }

    @Override // okhttp3.EventListener
    public void callFailed(Call call, IOException ioe) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(ioe, "ioe");
        logWithTime("callFailed: " + ioe);
    }

    @Override // okhttp3.EventListener
    public void canceled(Call call) {
        Intrinsics.checkNotNullParameter(call, "call");
        logWithTime("canceled");
    }

    @Override // okhttp3.EventListener
    public void satisfactionFailure(Call call, Response response) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(response, "response");
        logWithTime("satisfactionFailure: " + response);
    }

    @Override // okhttp3.EventListener
    public void cacheHit(Call call, Response response) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(response, "response");
        logWithTime("cacheHit: " + response);
    }

    @Override // okhttp3.EventListener
    public void cacheMiss(Call call) {
        Intrinsics.checkNotNullParameter(call, "call");
        logWithTime("cacheMiss");
    }

    @Override // okhttp3.EventListener
    public void cacheConditionalHit(Call call, Response cachedResponse) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(cachedResponse, "cachedResponse");
        logWithTime("cacheConditionalHit: " + cachedResponse);
    }

    private final void logWithTime(String message) {
        this.logger.log("[" + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - this.startNs) + " ms] " + message);
    }

    /* JADX INFO: compiled from: LoggingEventListener.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0013\b\u0007\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lokhttp3/logging/LoggingEventListener$Factory;", "Lokhttp3/EventListener$Factory;", "logger", "Lokhttp3/logging/HttpLoggingInterceptor$Logger;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokhttp3/logging/HttpLoggingInterceptor$Logger;)V", "create", "Lokhttp3/EventListener;", "call", "Lokhttp3/Call;", "logging-interceptor"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static class Factory implements EventListener.Factory {
        private final HttpLoggingInterceptor.Logger logger;

        public Factory() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public Factory(HttpLoggingInterceptor.Logger logger) {
            Intrinsics.checkNotNullParameter(logger, "logger");
            this.logger = logger;
        }

        public /* synthetic */ Factory(HttpLoggingInterceptor.Logger logger, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? HttpLoggingInterceptor.Logger.DEFAULT : logger);
        }

        @Override // okhttp3.EventListener.Factory
        public EventListener create(Call call) {
            Intrinsics.checkNotNullParameter(call, "call");
            return new LoggingEventListener(this.logger, null);
        }
    }
}
