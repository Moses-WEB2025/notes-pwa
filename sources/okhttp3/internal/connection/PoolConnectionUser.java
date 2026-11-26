package okhttp3.internal.connection;

import cn.kuaipan.kss.KssDef;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.onetrack.api.ah;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Proxy;
import java.net.Socket;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Connection;
import okhttp3.Handshake;
import okhttp3.HttpUrl;
import okhttp3.Protocol;
import okhttp3.Route;

/* JADX INFO: compiled from: PoolConnectionUser.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\r\u001a\u00020\u0005H\u0016J\u0012\u0010\u000e\u001a\u00020\u00052\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016J\u001a\u0010\u0011\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0016J\u0018\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\n\u001a\u00020\u000bH\u0016J\"\u0010\u0017\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u001cH\u0016J\n\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\u0010\u0010\u001f\u001a\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010 \u001a\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u001cH\u0016J\u0010\u0010!\u001a\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u001cH\u0016J\u0010\u0010\"\u001a\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u001cH\u0016J\u0010\u0010#\u001a\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u001cH\u0016J\b\u0010$\u001a\u00020%H\u0016J\b\u0010&\u001a\u00020%H\u0016J\n\u0010'\u001a\u0004\u0018\u00010\u001cH\u0016J\u0010\u0010(\u001a\u00020\u00052\u0006\u0010)\u001a\u00020*H\u0016J\u001e\u0010+\u001a\u00020\u00052\u0006\u0010)\u001a\u00020*2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020.0-H\u0016J\u0010\u0010/\u001a\u00020\u00052\u0006\u00100\u001a\u000201H\u0016J\u001e\u00102\u001a\u00020\u00052\u0006\u00100\u001a\u0002012\f\u00103\u001a\b\u0012\u0004\u0012\u0002040-H\u0016¨\u00065"}, d2 = {"Lokhttp3/internal/connection/PoolConnectionUser;", "Lokhttp3/internal/connection/ConnectionUser;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "addPlanToCancel", "", "connectPlan", "Lokhttp3/internal/connection/ConnectPlan;", "removePlanToCancel", "updateRouteDatabaseAfterSuccess", "route", "Lokhttp3/Route;", "connectStart", "secureConnectStart", "secureConnectEnd", ah.R, "Lokhttp3/Handshake;", "callConnectEnd", "protocol", "Lokhttp3/Protocol;", "connectionConnectEnd", "connection", "Lokhttp3/Connection;", "connectFailed", "e", "Ljava/io/IOException;", "connectionAcquired", "acquireConnectionNoEvents", "Lokhttp3/internal/connection/RealConnection;", "releaseConnectionNoEvents", "Ljava/net/Socket;", "connectionReleased", "connectionConnectionAcquired", "connectionConnectionReleased", "connectionConnectionClosed", "noNewExchanges", "doExtensiveHealthChecks", "", "isCanceled", "candidateConnection", "proxySelectStart", "url", "Lokhttp3/HttpUrl;", "proxySelectEnd", KssDef.KEY_PROXIES, "", "Ljava/net/Proxy;", "dnsStart", "socketHost", "", "dnsEnd", "result", "Ljava/net/InetAddress;", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PoolConnectionUser implements ConnectionUser {
    public static final PoolConnectionUser INSTANCE = new PoolConnectionUser();

    @Override // okhttp3.internal.connection.ConnectionUser
    public void acquireConnectionNoEvents(RealConnection connection) {
        Intrinsics.checkNotNullParameter(connection, "connection");
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void addPlanToCancel(ConnectPlan connectPlan) {
        Intrinsics.checkNotNullParameter(connectPlan, "connectPlan");
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void callConnectEnd(Route route, Protocol protocol) {
        Intrinsics.checkNotNullParameter(route, "route");
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public RealConnection candidateConnection() {
        return null;
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void connectFailed(Route route, Protocol protocol, IOException e) {
        Intrinsics.checkNotNullParameter(route, "route");
        Intrinsics.checkNotNullParameter(e, "e");
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void connectStart(Route route) {
        Intrinsics.checkNotNullParameter(route, "route");
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void connectionAcquired(Connection connection) {
        Intrinsics.checkNotNullParameter(connection, "connection");
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void connectionConnectEnd(Connection connection, Route route) {
        Intrinsics.checkNotNullParameter(connection, "connection");
        Intrinsics.checkNotNullParameter(route, "route");
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void connectionConnectionAcquired(RealConnection connection) {
        Intrinsics.checkNotNullParameter(connection, "connection");
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void connectionConnectionClosed(RealConnection connection) {
        Intrinsics.checkNotNullParameter(connection, "connection");
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void connectionConnectionReleased(RealConnection connection) {
        Intrinsics.checkNotNullParameter(connection, "connection");
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void connectionReleased(Connection connection) {
        Intrinsics.checkNotNullParameter(connection, "connection");
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void dnsEnd(String socketHost, List<? extends InetAddress> result) {
        Intrinsics.checkNotNullParameter(socketHost, "socketHost");
        Intrinsics.checkNotNullParameter(result, "result");
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void dnsStart(String socketHost) {
        Intrinsics.checkNotNullParameter(socketHost, "socketHost");
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public boolean doExtensiveHealthChecks() {
        return false;
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public boolean isCanceled() {
        return false;
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void noNewExchanges(RealConnection connection) {
        Intrinsics.checkNotNullParameter(connection, "connection");
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void proxySelectEnd(HttpUrl url, List<? extends Proxy> proxies) {
        Intrinsics.checkNotNullParameter(url, "url");
        Intrinsics.checkNotNullParameter(proxies, "proxies");
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void proxySelectStart(HttpUrl url) {
        Intrinsics.checkNotNullParameter(url, "url");
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public Socket releaseConnectionNoEvents() {
        return null;
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void removePlanToCancel(ConnectPlan connectPlan) {
        Intrinsics.checkNotNullParameter(connectPlan, "connectPlan");
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void secureConnectEnd(Handshake handshake) {
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void secureConnectStart() {
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void updateRouteDatabaseAfterSuccess(Route route) {
        Intrinsics.checkNotNullParameter(route, "route");
    }

    private PoolConnectionUser() {
    }
}
