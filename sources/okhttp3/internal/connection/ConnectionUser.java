package okhttp3.internal.connection;

import cn.kuaipan.kss.KssDef;
import com.xiaomi.onetrack.api.ah;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Proxy;
import java.net.Socket;
import java.util.List;
import kotlin.Metadata;
import okhttp3.Connection;
import okhttp3.Handshake;
import okhttp3.HttpUrl;
import okhttp3.Protocol;
import okhttp3.Route;

/* JADX INFO: compiled from: ConnectionUser.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH&J\u0010\u0010\n\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH&J\b\u0010\u000b\u001a\u00020\u0003H&J\u0012\u0010\f\u001a\u00020\u00032\b\u0010\r\u001a\u0004\u0018\u00010\u000eH&J\u001a\u0010\u000f\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H&J\u0018\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\b\u001a\u00020\tH&J\"\u0010\u0015\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0016\u001a\u00020\u0017H&J\u0010\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u0014H&J\u0010\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u001aH&J\n\u0010\u001b\u001a\u0004\u0018\u00010\u001cH&J\u0010\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u0014H&J\u0010\u0010\u001e\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u001aH&J\u0010\u0010\u001f\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u001aH&J\u0010\u0010 \u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u001aH&J\u0010\u0010!\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u001aH&J\b\u0010\"\u001a\u00020#H&J\b\u0010$\u001a\u00020#H&J\n\u0010%\u001a\u0004\u0018\u00010\u001aH&J\u0010\u0010&\u001a\u00020\u00032\u0006\u0010'\u001a\u00020(H&J\u001e\u0010)\u001a\u00020\u00032\u0006\u0010'\u001a\u00020(2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020,0+H&J\u0010\u0010-\u001a\u00020\u00032\u0006\u0010.\u001a\u00020/H&J\u001e\u00100\u001a\u00020\u00032\u0006\u0010.\u001a\u00020/2\f\u00101\u001a\b\u0012\u0004\u0012\u0002020+H&¨\u00063À\u0006\u0003"}, d2 = {"Lokhttp3/internal/connection/ConnectionUser;", "", "addPlanToCancel", "", "connectPlan", "Lokhttp3/internal/connection/ConnectPlan;", "removePlanToCancel", "updateRouteDatabaseAfterSuccess", "route", "Lokhttp3/Route;", "connectStart", "secureConnectStart", "secureConnectEnd", ah.R, "Lokhttp3/Handshake;", "callConnectEnd", "protocol", "Lokhttp3/Protocol;", "connectionConnectEnd", "connection", "Lokhttp3/Connection;", "connectFailed", "e", "Ljava/io/IOException;", "connectionAcquired", "acquireConnectionNoEvents", "Lokhttp3/internal/connection/RealConnection;", "releaseConnectionNoEvents", "Ljava/net/Socket;", "connectionReleased", "connectionConnectionAcquired", "connectionConnectionReleased", "connectionConnectionClosed", "noNewExchanges", "doExtensiveHealthChecks", "", "isCanceled", "candidateConnection", "proxySelectStart", "url", "Lokhttp3/HttpUrl;", "proxySelectEnd", KssDef.KEY_PROXIES, "", "Ljava/net/Proxy;", "dnsStart", "socketHost", "", "dnsEnd", "result", "Ljava/net/InetAddress;", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface ConnectionUser {
    void acquireConnectionNoEvents(RealConnection connection);

    void addPlanToCancel(ConnectPlan connectPlan);

    void callConnectEnd(Route route, Protocol protocol);

    RealConnection candidateConnection();

    void connectFailed(Route route, Protocol protocol, IOException e);

    void connectStart(Route route);

    void connectionAcquired(Connection connection);

    void connectionConnectEnd(Connection connection, Route route);

    void connectionConnectionAcquired(RealConnection connection);

    void connectionConnectionClosed(RealConnection connection);

    void connectionConnectionReleased(RealConnection connection);

    void connectionReleased(Connection connection);

    void dnsEnd(String socketHost, List<? extends InetAddress> result);

    void dnsStart(String socketHost);

    boolean doExtensiveHealthChecks();

    boolean isCanceled();

    void noNewExchanges(RealConnection connection);

    void proxySelectEnd(HttpUrl url, List<? extends Proxy> proxies);

    void proxySelectStart(HttpUrl url);

    Socket releaseConnectionNoEvents();

    void removePlanToCancel(ConnectPlan connectPlan);

    void secureConnectEnd(Handshake handshake);

    void secureConnectStart();

    void updateRouteDatabaseAfterSuccess(Route route);
}
