package okhttp3;

import cn.kuaipan.kss.KssDef;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.accountsdk.account.stat.StatConstants;
import com.xiaomi.onetrack.api.ah;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: EventListener.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0006\b&\u0018\u0000 H2\u00020\u0001:\u0002GHB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\nH\u0016J+\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\n2\u0011\u0010\f\u001a\r\u0012\t\u0012\u00070\u000e¢\u0006\u0002\b\u000f0\rH\u0016J\u0018\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J+\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u00122\u0011\u0010\u0014\u001a\r\u0012\t\u0012\u00070\u0015¢\u0006\u0002\b\u000f0\rH\u0016J \u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u000eH\u0016J\u0010\u0010\u001a\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u001a\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J*\u0010\u001e\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u000e2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0016J2\u0010!\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u000e2\b\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010\"\u001a\u00020#H\u0016J\u0018\u0010$\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010%\u001a\u00020&H\u0016J\u0018\u0010'\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010%\u001a\u00020&H\u0016J\u0010\u0010(\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010)\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010*\u001a\u00020+H\u0016J\u0010\u0010,\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010-\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010.\u001a\u00020/H\u0016J\u0018\u00100\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\"\u001a\u00020#H\u0016J\u0010\u00101\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u00102\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u00103\u001a\u000204H\u0016J\u0010\u00105\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u00106\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010.\u001a\u00020/H\u0016J\u0018\u00107\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\"\u001a\u00020#H\u0016J\u0010\u00108\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u00109\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\"\u001a\u00020#H\u0016J\u0010\u0010:\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010;\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u00103\u001a\u000204H\u0016J\u0018\u0010<\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u00103\u001a\u000204H\u0016J\u0010\u0010=\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010>\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010?\u001a\u000204H\u0016J \u0010@\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010A\u001a\u00020#2\u0006\u0010B\u001a\u00020CH\u0016J\"\u0010D\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010E\u001a\u0002042\b\u0010F\u001a\u0004\u0018\u00010+H\u0016¨\u0006I"}, d2 = {"Lokhttp3/EventListener;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "callStart", "", "call", "Lokhttp3/Call;", "proxySelectStart", "url", "Lokhttp3/HttpUrl;", "proxySelectEnd", KssDef.KEY_PROXIES, "", "Ljava/net/Proxy;", "Lkotlin/jvm/JvmSuppressWildcards;", "dnsStart", "domainName", "", "dnsEnd", "inetAddressList", "Ljava/net/InetAddress;", "connectStart", "inetSocketAddress", "Ljava/net/InetSocketAddress;", "proxy", "secureConnectStart", "secureConnectEnd", ah.R, "Lokhttp3/Handshake;", "connectEnd", "protocol", "Lokhttp3/Protocol;", "connectFailed", "ioe", "Ljava/io/IOException;", "connectionAcquired", "connection", "Lokhttp3/Connection;", "connectionReleased", "requestHeadersStart", "requestHeadersEnd", StatConstants.Event.REQUEST, "Lokhttp3/Request;", "requestBodyStart", "requestBodyEnd", "byteCount", "", "requestFailed", "responseHeadersStart", "responseHeadersEnd", ah.H, "Lokhttp3/Response;", "responseBodyStart", "responseBodyEnd", "responseFailed", "callEnd", "callFailed", "canceled", "satisfactionFailure", "cacheHit", "cacheMiss", "cacheConditionalHit", "cachedResponse", "retryDecision", "exception", ah.L, "", "followUpDecision", "networkResponse", "nextRequest", "Factory", "Companion", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class EventListener {
    public static final EventListener NONE = new EventListener() { // from class: okhttp3.EventListener$Companion$NONE$1
    };

    /* JADX INFO: compiled from: EventListener.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bæ\u0080\u0001\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006À\u0006\u0003"}, d2 = {"Lokhttp3/EventListener$Factory;", "", "create", "Lokhttp3/EventListener;", "call", "Lokhttp3/Call;", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface Factory {
        EventListener create(Call call);
    }

    public void cacheConditionalHit(Call call, Response cachedResponse) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(cachedResponse, "cachedResponse");
    }

    public void cacheHit(Call call, Response response) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(response, "response");
    }

    public void cacheMiss(Call call) {
        Intrinsics.checkNotNullParameter(call, "call");
    }

    public void callEnd(Call call) {
        Intrinsics.checkNotNullParameter(call, "call");
    }

    public void callFailed(Call call, IOException ioe) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(ioe, "ioe");
    }

    public void callStart(Call call) {
        Intrinsics.checkNotNullParameter(call, "call");
    }

    public void canceled(Call call) {
        Intrinsics.checkNotNullParameter(call, "call");
    }

    public void connectEnd(Call call, InetSocketAddress inetSocketAddress, Proxy proxy, Protocol protocol) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(inetSocketAddress, "inetSocketAddress");
        Intrinsics.checkNotNullParameter(proxy, "proxy");
    }

    public void connectFailed(Call call, InetSocketAddress inetSocketAddress, Proxy proxy, Protocol protocol, IOException ioe) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(inetSocketAddress, "inetSocketAddress");
        Intrinsics.checkNotNullParameter(proxy, "proxy");
        Intrinsics.checkNotNullParameter(ioe, "ioe");
    }

    public void connectStart(Call call, InetSocketAddress inetSocketAddress, Proxy proxy) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(inetSocketAddress, "inetSocketAddress");
        Intrinsics.checkNotNullParameter(proxy, "proxy");
    }

    public void connectionAcquired(Call call, Connection connection) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(connection, "connection");
    }

    public void connectionReleased(Call call, Connection connection) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(connection, "connection");
    }

    public void dnsEnd(Call call, String domainName, List<InetAddress> inetAddressList) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(domainName, "domainName");
        Intrinsics.checkNotNullParameter(inetAddressList, "inetAddressList");
    }

    public void dnsStart(Call call, String domainName) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(domainName, "domainName");
    }

    public void followUpDecision(Call call, Response networkResponse, Request nextRequest) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(networkResponse, "networkResponse");
    }

    public void proxySelectEnd(Call call, HttpUrl url, List<Proxy> proxies) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(url, "url");
        Intrinsics.checkNotNullParameter(proxies, "proxies");
    }

    public void proxySelectStart(Call call, HttpUrl url) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(url, "url");
    }

    public void requestBodyEnd(Call call, long byteCount) {
        Intrinsics.checkNotNullParameter(call, "call");
    }

    public void requestBodyStart(Call call) {
        Intrinsics.checkNotNullParameter(call, "call");
    }

    public void requestFailed(Call call, IOException ioe) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(ioe, "ioe");
    }

    public void requestHeadersEnd(Call call, Request request) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(request, "request");
    }

    public void requestHeadersStart(Call call) {
        Intrinsics.checkNotNullParameter(call, "call");
    }

    public void responseBodyEnd(Call call, long byteCount) {
        Intrinsics.checkNotNullParameter(call, "call");
    }

    public void responseBodyStart(Call call) {
        Intrinsics.checkNotNullParameter(call, "call");
    }

    public void responseFailed(Call call, IOException ioe) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(ioe, "ioe");
    }

    public void responseHeadersEnd(Call call, Response response) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(response, "response");
    }

    public void responseHeadersStart(Call call) {
        Intrinsics.checkNotNullParameter(call, "call");
    }

    public void retryDecision(Call call, IOException exception, boolean retry) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(exception, "exception");
    }

    public void satisfactionFailure(Call call, Response response) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(response, "response");
    }

    public void secureConnectEnd(Call call, Handshake handshake) {
        Intrinsics.checkNotNullParameter(call, "call");
    }

    public void secureConnectStart(Call call) {
        Intrinsics.checkNotNullParameter(call, "call");
    }
}
