package okhttp3.internal.connection;

import cn.kuaipan.kss.KssDef;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.accountsdk.request.PassportSimpleRequest;
import com.xiaomi.onetrack.api.ah;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Proxy;
import java.net.Socket;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Connection;
import okhttp3.EventListener;
import okhttp3.Handshake;
import okhttp3.HttpUrl;
import okhttp3.Protocol;
import okhttp3.Route;
import okhttp3.internal.http.RealInterceptorChain;

/* JADX INFO: compiled from: CallConnectionUser.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0092\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\"\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u000fH\u0016J\u0012\u0010\u001d\u001a\u00020\u000f2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\u001a\u0010 \u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J\u0018\u0010!\u001a\u00020\u000f2\u0006\u0010\"\u001a\u00020#2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010$\u001a\u00020\u000f2\u0006\u0010\"\u001a\u00020#H\u0016J\u0010\u0010%\u001a\u00020\u000f2\u0006\u0010\"\u001a\u00020&H\u0016J\n\u0010'\u001a\u0004\u0018\u00010(H\u0016J\u0010\u0010)\u001a\u00020\u000f2\u0006\u0010\"\u001a\u00020#H\u0016J\u0010\u0010*\u001a\u00020\u000f2\u0006\u0010\"\u001a\u00020&H\u0016J\u0010\u0010+\u001a\u00020\u000f2\u0006\u0010\"\u001a\u00020&H\u0016J\u0010\u0010,\u001a\u00020\u000f2\u0006\u0010\"\u001a\u00020&H\u0016J\u0010\u0010-\u001a\u00020\u000f2\u0006\u0010\"\u001a\u00020&H\u0016J\b\u0010.\u001a\u00020/H\u0016J\b\u00100\u001a\u00020/H\u0016J\n\u00101\u001a\u0004\u0018\u00010&H\u0016J\u0010\u00102\u001a\u00020\u000f2\u0006\u00103\u001a\u000204H\u0016J\u001e\u00105\u001a\u00020\u000f2\u0006\u00103\u001a\u0002042\f\u00106\u001a\b\u0012\u0004\u0012\u00020807H\u0016J\u0010\u00109\u001a\u00020\u000f2\u0006\u0010:\u001a\u00020;H\u0016J\u001e\u0010<\u001a\u00020\u000f2\u0006\u0010:\u001a\u00020;2\f\u0010=\u001a\b\u0012\u0004\u0012\u00020>07H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006?"}, d2 = {"Lokhttp3/internal/connection/CallConnectionUser;", "Lokhttp3/internal/connection/ConnectionUser;", "call", "Lokhttp3/internal/connection/RealCall;", "poolConnectionListener", "Lokhttp3/internal/connection/ConnectionListener;", "chain", "Lokhttp3/internal/http/RealInterceptorChain;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokhttp3/internal/connection/RealCall;Lokhttp3/internal/connection/ConnectionListener;Lokhttp3/internal/http/RealInterceptorChain;)V", "eventListener", "Lokhttp3/EventListener;", "getEventListener", "()Lokhttp3/EventListener;", "addPlanToCancel", "", "connectPlan", "Lokhttp3/internal/connection/ConnectPlan;", "removePlanToCancel", "updateRouteDatabaseAfterSuccess", "route", "Lokhttp3/Route;", "connectStart", "connectFailed", "protocol", "Lokhttp3/Protocol;", "e", "Ljava/io/IOException;", "secureConnectStart", "secureConnectEnd", ah.R, "Lokhttp3/Handshake;", "callConnectEnd", "connectionConnectEnd", "connection", "Lokhttp3/Connection;", "connectionAcquired", "acquireConnectionNoEvents", "Lokhttp3/internal/connection/RealConnection;", "releaseConnectionNoEvents", "Ljava/net/Socket;", "connectionReleased", "connectionConnectionAcquired", "connectionConnectionReleased", "connectionConnectionClosed", "noNewExchanges", "doExtensiveHealthChecks", "", "isCanceled", "candidateConnection", "proxySelectStart", "url", "Lokhttp3/HttpUrl;", "proxySelectEnd", KssDef.KEY_PROXIES, "", "Ljava/net/Proxy;", "dnsStart", "socketHost", "", "dnsEnd", "result", "Ljava/net/InetAddress;", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class CallConnectionUser implements ConnectionUser {
    private final RealCall call;
    private final RealInterceptorChain chain;
    private final ConnectionListener poolConnectionListener;

    public CallConnectionUser(RealCall call, ConnectionListener poolConnectionListener, RealInterceptorChain chain) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(poolConnectionListener, "poolConnectionListener");
        Intrinsics.checkNotNullParameter(chain, "chain");
        this.call = call;
        this.poolConnectionListener = poolConnectionListener;
        this.chain = chain;
    }

    private final EventListener getEventListener() {
        return this.call.getEventListener();
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void addPlanToCancel(ConnectPlan connectPlan) {
        Intrinsics.checkNotNullParameter(connectPlan, "connectPlan");
        this.call.getPlansToCancel$okhttp().add(connectPlan);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void removePlanToCancel(ConnectPlan connectPlan) {
        Intrinsics.checkNotNullParameter(connectPlan, "connectPlan");
        this.call.getPlansToCancel$okhttp().remove(connectPlan);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void updateRouteDatabaseAfterSuccess(Route route) {
        Intrinsics.checkNotNullParameter(route, "route");
        this.call.getClient().getRouteDatabase().connected(route);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void connectStart(Route route) {
        Intrinsics.checkNotNullParameter(route, "route");
        getEventListener().connectStart(this.call, route.socketAddress(), route.proxy());
        this.poolConnectionListener.connectStart(route, this.call);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void connectFailed(Route route, Protocol protocol, IOException e) {
        Intrinsics.checkNotNullParameter(route, "route");
        Intrinsics.checkNotNullParameter(e, "e");
        getEventListener().connectFailed(this.call, route.socketAddress(), route.proxy(), null, e);
        this.poolConnectionListener.connectFailed(route, this.call, e);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void secureConnectStart() {
        getEventListener().secureConnectStart(this.call);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void secureConnectEnd(Handshake handshake) {
        getEventListener().secureConnectEnd(this.call, handshake);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void callConnectEnd(Route route, Protocol protocol) {
        Intrinsics.checkNotNullParameter(route, "route");
        getEventListener().connectEnd(this.call, route.socketAddress(), route.proxy(), protocol);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void connectionConnectEnd(Connection connection, Route route) {
        Intrinsics.checkNotNullParameter(connection, "connection");
        Intrinsics.checkNotNullParameter(route, "route");
        this.poolConnectionListener.connectEnd(connection, route, this.call);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void connectionAcquired(Connection connection) {
        Intrinsics.checkNotNullParameter(connection, "connection");
        getEventListener().connectionAcquired(this.call, connection);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void acquireConnectionNoEvents(RealConnection connection) {
        Intrinsics.checkNotNullParameter(connection, "connection");
        this.call.acquireConnectionNoEvents(connection);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public Socket releaseConnectionNoEvents() {
        return this.call.releaseConnectionNoEvents$okhttp();
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void connectionReleased(Connection connection) {
        Intrinsics.checkNotNullParameter(connection, "connection");
        getEventListener().connectionReleased(this.call, connection);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void connectionConnectionAcquired(RealConnection connection) {
        Intrinsics.checkNotNullParameter(connection, "connection");
        connection.getConnectionListener().connectionAcquired(connection, this.call);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void connectionConnectionReleased(RealConnection connection) {
        Intrinsics.checkNotNullParameter(connection, "connection");
        connection.getConnectionListener().connectionReleased(connection, this.call);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void connectionConnectionClosed(RealConnection connection) {
        Intrinsics.checkNotNullParameter(connection, "connection");
        connection.getConnectionListener().connectionClosed(connection);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void noNewExchanges(RealConnection connection) {
        Intrinsics.checkNotNullParameter(connection, "connection");
        connection.getConnectionListener().noNewExchanges(connection);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public boolean doExtensiveHealthChecks() {
        return !Intrinsics.areEqual(this.chain.getRequest().method(), PassportSimpleRequest.HTTP_METHOD_GET);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public boolean isCanceled() {
        return this.call.getCanceled();
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public RealConnection candidateConnection() {
        return this.call.getConnection();
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void proxySelectStart(HttpUrl url) {
        Intrinsics.checkNotNullParameter(url, "url");
        getEventListener().proxySelectStart(this.call, url);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void proxySelectEnd(HttpUrl url, List<? extends Proxy> proxies) {
        Intrinsics.checkNotNullParameter(url, "url");
        Intrinsics.checkNotNullParameter(proxies, "proxies");
        getEventListener().proxySelectEnd(this.call, url, proxies);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void dnsStart(String socketHost) {
        Intrinsics.checkNotNullParameter(socketHost, "socketHost");
        getEventListener().dnsStart(this.call, socketHost);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void dnsEnd(String socketHost, List<? extends InetAddress> result) {
        Intrinsics.checkNotNullParameter(socketHost, "socketHost");
        Intrinsics.checkNotNullParameter(result, "result");
        getEventListener().dnsEnd(this.call, socketHost, result);
    }
}
