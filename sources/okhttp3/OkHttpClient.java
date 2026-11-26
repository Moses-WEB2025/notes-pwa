package okhttp3;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.accountsdk.account.stat.StatConstants;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.net.Proxy;
import java.net.ProxySelector;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal._UtilJvmKt;
import okhttp3.internal.concurrent.TaskRunner;
import okhttp3.internal.connection.RealCall;
import okhttp3.internal.connection.RouteDatabase;
import okhttp3.internal.platform.Platform;
import okhttp3.internal.proxy.NullProxySelector;
import okhttp3.internal.tls.CertificateChainCleaner;
import okhttp3.internal.tls.OkHostnameVerifier;
import okhttp3.internal.ws.RealWebSocket;

/* JADX INFO: compiled from: OkHttpClient.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000ú\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001e\b\u0016\u0018\u0000 \u0083\u00012\u00020\u00012\u00020\u0002:\u0004\u0082\u0001\u0083\u0001B\u0011\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0004\b\u0005\u0010\u0006B\t\b\u0016¢\u0006\u0004\b\u0005\u0010\u0007J\u000e\u0010Y\u001a\u00020Z2\u0006\u0010[\u001a\u00020\\J\b\u0010]\u001a\u00020^H\u0002J\u0010\u0010_\u001a\u00020`2\u0006\u0010a\u001a\u00020bH\u0016J\u0018\u0010c\u001a\u00020d2\u0006\u0010a\u001a\u00020b2\u0006\u0010e\u001a\u00020fH\u0016J\b\u0010g\u001a\u00020\u0004H\u0016J\r\u0010\b\u001a\u00020\tH\u0007¢\u0006\u0002\bhJ\r\u0010V\u001a\u00020WH\u0007¢\u0006\u0002\biJ\u0013\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0007¢\u0006\u0002\bjJ\u0013\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0007¢\u0006\u0002\bkJ\r\u0010\u0010\u001a\u00020\u0011H\u0007¢\u0006\u0002\blJ\r\u0010\u0013\u001a\u00020\u0014H\u0007¢\u0006\u0002\bmJ\r\u0010\u0017\u001a\u00020\u0018H\u0007¢\u0006\u0002\bnJ\r\u0010\u001a\u001a\u00020\u0014H\u0007¢\u0006\u0002\boJ\r\u0010\u001b\u001a\u00020\u0014H\u0007¢\u0006\u0002\bpJ\r\u0010\u001c\u001a\u00020\u001dH\u0007¢\u0006\u0002\bqJ\u000f\u0010\u001f\u001a\u0004\u0018\u00010 H\u0007¢\u0006\u0002\brJ\r\u0010\"\u001a\u00020#H\u0007¢\u0006\u0002\bsJ\u000f\u0010%\u001a\u0004\u0018\u00010&H\u0007¢\u0006\u0002\btJ\r\u0010(\u001a\u00020)H\u0007¢\u0006\u0002\buJ\r\u0010+\u001a\u00020\u0018H\u0007¢\u0006\u0002\bvJ\r\u0010,\u001a\u00020-H\u0007¢\u0006\u0002\bwJ\r\u00101\u001a\u000200H\u0007¢\u0006\u0002\bxJ\u0013\u00106\u001a\b\u0012\u0004\u0012\u0002070\fH\u0007¢\u0006\u0002\byJ\u0013\u00108\u001a\b\u0012\u0004\u0012\u0002090\fH\u0007¢\u0006\u0002\bzJ\r\u0010:\u001a\u00020;H\u0007¢\u0006\u0002\b{J\r\u0010=\u001a\u00020>H\u0007¢\u0006\u0002\b|J\r\u0010C\u001a\u00020DH\u0007¢\u0006\u0002\b}J\r\u0010F\u001a\u00020DH\u0007¢\u0006\u0002\b~J\r\u0010G\u001a\u00020DH\u0007¢\u0006\u0002\b\u007fJ\u000e\u0010H\u001a\u00020DH\u0007¢\u0006\u0003\b\u0080\u0001J\u000e\u0010I\u001a\u00020DH\u0007¢\u0006\u0003\b\u0081\u0001R\u0013\u0010\b\u001a\u00020\t8G¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\nR\u0019\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f8G¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u000eR\u0019\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\r0\f8G¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0013\u0010\u0010\u001a\u00020\u00118G¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0012R\u0013\u0010\u0013\u001a\u00020\u00148G¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0015R\u0013\u0010\u0016\u001a\u00020\u00148G¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0015R\u0013\u0010\u0017\u001a\u00020\u00188G¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0019R\u0013\u0010\u001a\u001a\u00020\u00148G¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0015R\u0013\u0010\u001b\u001a\u00020\u00148G¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0015R\u0013\u0010\u001c\u001a\u00020\u001d8G¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001eR\u0015\u0010\u001f\u001a\u0004\u0018\u00010 8G¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010!R\u0013\u0010\"\u001a\u00020#8G¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010$R\u0015\u0010%\u001a\u0004\u0018\u00010&8G¢\u0006\b\n\u0000\u001a\u0004\b%\u0010'R\u0013\u0010(\u001a\u00020)8G¢\u0006\b\n\u0000\u001a\u0004\b(\u0010*R\u0013\u0010+\u001a\u00020\u00188G¢\u0006\b\n\u0000\u001a\u0004\b+\u0010\u0019R\u0013\u0010,\u001a\u00020-8G¢\u0006\b\n\u0000\u001a\u0004\b,\u0010.R\u0010\u0010/\u001a\u0004\u0018\u000100X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u00101\u001a\u0002008G¢\u0006\u0006\u001a\u0004\b1\u00102R\u0015\u00103\u001a\u0004\u0018\u0001048G¢\u0006\b\n\u0000\u001a\u0004\b3\u00105R\u0019\u00106\u001a\b\u0012\u0004\u0012\u0002070\f8G¢\u0006\b\n\u0000\u001a\u0004\b6\u0010\u000eR\u0019\u00108\u001a\b\u0012\u0004\u0012\u0002090\f8G¢\u0006\b\n\u0000\u001a\u0004\b8\u0010\u000eR\u0013\u0010:\u001a\u00020;8G¢\u0006\b\n\u0000\u001a\u0004\b:\u0010<R\u0013\u0010=\u001a\u00020>8G¢\u0006\b\n\u0000\u001a\u0004\b=\u0010?R\u0015\u0010@\u001a\u0004\u0018\u00010A8G¢\u0006\b\n\u0000\u001a\u0004\b@\u0010BR\u0013\u0010C\u001a\u00020D8G¢\u0006\b\n\u0000\u001a\u0004\bC\u0010ER\u0013\u0010F\u001a\u00020D8G¢\u0006\b\n\u0000\u001a\u0004\bF\u0010ER\u0013\u0010G\u001a\u00020D8G¢\u0006\b\n\u0000\u001a\u0004\bG\u0010ER\u0013\u0010H\u001a\u00020D8G¢\u0006\b\n\u0000\u001a\u0004\bH\u0010ER\u0013\u0010I\u001a\u00020D8G¢\u0006\b\n\u0000\u001a\u0004\bI\u0010ER\u0013\u0010J\u001a\u00020D8G¢\u0006\b\n\u0000\u001a\u0004\bJ\u0010ER\u0013\u0010K\u001a\u00020L8G¢\u0006\b\n\u0000\u001a\u0004\bK\u0010MR\u0014\u0010N\u001a\u00020OX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\bP\u0010QR\u0014\u0010R\u001a\u00020SX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\bT\u0010UR\u0013\u0010V\u001a\u00020W8G¢\u0006\b\n\u0000\u001a\u0004\bV\u0010X¨\u0006\u0084\u0001"}, d2 = {"Lokhttp3/OkHttpClient;", "Lokhttp3/Call$Factory;", "Lokhttp3/WebSocket$Factory;", "builder", "Lokhttp3/OkHttpClient$Builder;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokhttp3/OkHttpClient$Builder;)V", "()V", "dispatcher", "Lokhttp3/Dispatcher;", "()Lokhttp3/Dispatcher;", "interceptors", "", "Lokhttp3/Interceptor;", "()Ljava/util/List;", "networkInterceptors", "eventListenerFactory", "Lokhttp3/EventListener$Factory;", "()Lokhttp3/EventListener$Factory;", "retryOnConnectionFailure", "", "()Z", "fastFallback", "authenticator", "Lokhttp3/Authenticator;", "()Lokhttp3/Authenticator;", "followRedirects", "followSslRedirects", "cookieJar", "Lokhttp3/CookieJar;", "()Lokhttp3/CookieJar;", "cache", "Lokhttp3/Cache;", "()Lokhttp3/Cache;", "dns", "Lokhttp3/Dns;", "()Lokhttp3/Dns;", "proxy", "Ljava/net/Proxy;", "()Ljava/net/Proxy;", "proxySelector", "Ljava/net/ProxySelector;", "()Ljava/net/ProxySelector;", "proxyAuthenticator", "socketFactory", "Ljavax/net/SocketFactory;", "()Ljavax/net/SocketFactory;", "sslSocketFactoryOrNull", "Ljavax/net/ssl/SSLSocketFactory;", "sslSocketFactory", "()Ljavax/net/ssl/SSLSocketFactory;", "x509TrustManager", "Ljavax/net/ssl/X509TrustManager;", "()Ljavax/net/ssl/X509TrustManager;", "connectionSpecs", "Lokhttp3/ConnectionSpec;", "protocols", "Lokhttp3/Protocol;", "hostnameVerifier", "Ljavax/net/ssl/HostnameVerifier;", "()Ljavax/net/ssl/HostnameVerifier;", "certificatePinner", "Lokhttp3/CertificatePinner;", "()Lokhttp3/CertificatePinner;", "certificateChainCleaner", "Lokhttp3/internal/tls/CertificateChainCleaner;", "()Lokhttp3/internal/tls/CertificateChainCleaner;", "callTimeoutMillis", "", "()I", "connectTimeoutMillis", "readTimeoutMillis", "writeTimeoutMillis", "pingIntervalMillis", "webSocketCloseTimeout", "minWebSocketMessageToCompress", "", "()J", "routeDatabase", "Lokhttp3/internal/connection/RouteDatabase;", "getRouteDatabase$okhttp", "()Lokhttp3/internal/connection/RouteDatabase;", "taskRunner", "Lokhttp3/internal/concurrent/TaskRunner;", "getTaskRunner$okhttp", "()Lokhttp3/internal/concurrent/TaskRunner;", "connectionPool", "Lokhttp3/ConnectionPool;", "()Lokhttp3/ConnectionPool;", RemoteDeviceInfo.KEY_ADDRESS, "Lokhttp3/Address;", "url", "Lokhttp3/HttpUrl;", "verifyClientState", "", "newCall", "Lokhttp3/Call;", StatConstants.Event.REQUEST, "Lokhttp3/Request;", "newWebSocket", "Lokhttp3/WebSocket;", "listener", "Lokhttp3/WebSocketListener;", "newBuilder", "-deprecated_dispatcher", "-deprecated_connectionPool", "-deprecated_interceptors", "-deprecated_networkInterceptors", "-deprecated_eventListenerFactory", "-deprecated_retryOnConnectionFailure", "-deprecated_authenticator", "-deprecated_followRedirects", "-deprecated_followSslRedirects", "-deprecated_cookieJar", "-deprecated_cache", "-deprecated_dns", "-deprecated_proxy", "-deprecated_proxySelector", "-deprecated_proxyAuthenticator", "-deprecated_socketFactory", "-deprecated_sslSocketFactory", "-deprecated_connectionSpecs", "-deprecated_protocols", "-deprecated_hostnameVerifier", "-deprecated_certificatePinner", "-deprecated_callTimeoutMillis", "-deprecated_connectTimeoutMillis", "-deprecated_readTimeoutMillis", "-deprecated_writeTimeoutMillis", "-deprecated_pingIntervalMillis", "Builder", "Companion", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class OkHttpClient implements Call.Factory, WebSocket.Factory {
    private final Authenticator authenticator;
    private final Cache cache;
    private final int callTimeoutMillis;
    private final CertificateChainCleaner certificateChainCleaner;
    private final CertificatePinner certificatePinner;
    private final int connectTimeoutMillis;
    private final ConnectionPool connectionPool;
    private final List<ConnectionSpec> connectionSpecs;
    private final CookieJar cookieJar;
    private final Dispatcher dispatcher;
    private final Dns dns;
    private final EventListener.Factory eventListenerFactory;
    private final boolean fastFallback;
    private final boolean followRedirects;
    private final boolean followSslRedirects;
    private final HostnameVerifier hostnameVerifier;
    private final List<Interceptor> interceptors;
    private final long minWebSocketMessageToCompress;
    private final List<Interceptor> networkInterceptors;
    private final int pingIntervalMillis;
    private final List<Protocol> protocols;
    private final Proxy proxy;
    private final Authenticator proxyAuthenticator;
    private final ProxySelector proxySelector;
    private final int readTimeoutMillis;
    private final boolean retryOnConnectionFailure;
    private final RouteDatabase routeDatabase;
    private final SocketFactory socketFactory;
    private final SSLSocketFactory sslSocketFactoryOrNull;
    private final TaskRunner taskRunner;
    private final int webSocketCloseTimeout;
    private final int writeTimeoutMillis;
    private final X509TrustManager x509TrustManager;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final List<Protocol> DEFAULT_PROTOCOLS = _UtilJvmKt.immutableListOf(Protocol.HTTP_2, Protocol.HTTP_1_1);
    private static final List<ConnectionSpec> DEFAULT_CONNECTION_SPECS = _UtilJvmKt.immutableListOf(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT);

    public OkHttpClient(Builder builder) throws NoSuchAlgorithmException, KeyStoreException {
        NullProxySelector proxySelector;
        List<ConnectionSpec> list;
        Intrinsics.checkNotNullParameter(builder, "builder");
        this.dispatcher = builder.getDispatcher();
        this.interceptors = _UtilJvmKt.toImmutableList(builder.getInterceptors$okhttp());
        this.networkInterceptors = _UtilJvmKt.toImmutableList(builder.getNetworkInterceptors$okhttp());
        this.eventListenerFactory = builder.getEventListenerFactory();
        boolean retryOnConnectionFailure = builder.getRetryOnConnectionFailure();
        this.retryOnConnectionFailure = retryOnConnectionFailure;
        boolean fastFallback = builder.getFastFallback();
        this.fastFallback = fastFallback;
        this.authenticator = builder.getAuthenticator();
        this.followRedirects = builder.getFollowRedirects();
        this.followSslRedirects = builder.getFollowSslRedirects();
        this.cookieJar = builder.getCookieJar();
        this.cache = builder.getCache();
        this.dns = builder.getDns();
        this.proxy = builder.getProxy();
        if (builder.getProxy() != null) {
            proxySelector = NullProxySelector.INSTANCE;
        } else {
            proxySelector = builder.getProxySelector();
            if (proxySelector == null && (proxySelector = ProxySelector.getDefault()) == null) {
                proxySelector = NullProxySelector.INSTANCE;
            }
        }
        this.proxySelector = proxySelector;
        this.proxyAuthenticator = builder.getProxyAuthenticator();
        this.socketFactory = builder.getSocketFactory();
        List<ConnectionSpec> connectionSpecs$okhttp = builder.getConnectionSpecs$okhttp();
        this.connectionSpecs = connectionSpecs$okhttp;
        this.protocols = builder.getProtocols$okhttp();
        this.hostnameVerifier = builder.getHostnameVerifier();
        this.callTimeoutMillis = builder.getCallTimeout();
        int connectTimeout = builder.getConnectTimeout();
        this.connectTimeoutMillis = connectTimeout;
        int readTimeout = builder.getReadTimeout();
        this.readTimeoutMillis = readTimeout;
        int writeTimeout = builder.getWriteTimeout();
        this.writeTimeoutMillis = writeTimeout;
        int pingInterval = builder.getPingInterval();
        this.pingIntervalMillis = pingInterval;
        this.webSocketCloseTimeout = builder.getWebSocketCloseTimeout();
        this.minWebSocketMessageToCompress = builder.getMinWebSocketMessageToCompress();
        RouteDatabase routeDatabase = builder.getRouteDatabase();
        routeDatabase = routeDatabase == null ? new RouteDatabase() : routeDatabase;
        this.routeDatabase = routeDatabase;
        TaskRunner taskRunner = builder.getTaskRunner();
        this.taskRunner = taskRunner == null ? TaskRunner.INSTANCE : taskRunner;
        ConnectionPool connectionPool = builder.getConnectionPool();
        if (connectionPool == null) {
            list = connectionSpecs$okhttp;
            ConnectionPool connectionPool2 = new ConnectionPool(0, 0L, null, null, null, readTimeout, writeTimeout, connectTimeout, readTimeout, pingInterval, retryOnConnectionFailure, fastFallback, routeDatabase, 31, null);
            builder.setConnectionPool$okhttp(connectionPool2);
            connectionPool = connectionPool2;
        } else {
            list = connectionSpecs$okhttp;
        }
        this.connectionPool = connectionPool;
        List<ConnectionSpec> list2 = list;
        if ((list2 instanceof Collection) && list2.isEmpty()) {
            this.sslSocketFactoryOrNull = null;
            this.certificateChainCleaner = null;
            this.x509TrustManager = null;
            this.certificatePinner = CertificatePinner.DEFAULT;
        } else {
            Iterator<T> it = list2.iterator();
            while (it.hasNext()) {
                if (((ConnectionSpec) it.next()).getIsTls()) {
                    if (builder.getSslSocketFactoryOrNull() != null) {
                        this.sslSocketFactoryOrNull = builder.getSslSocketFactoryOrNull();
                        CertificateChainCleaner certificateChainCleaner = builder.getCertificateChainCleaner();
                        Intrinsics.checkNotNull(certificateChainCleaner);
                        this.certificateChainCleaner = certificateChainCleaner;
                        X509TrustManager x509TrustManagerOrNull = builder.getX509TrustManagerOrNull();
                        Intrinsics.checkNotNull(x509TrustManagerOrNull);
                        this.x509TrustManager = x509TrustManagerOrNull;
                        CertificatePinner certificatePinner = builder.getCertificatePinner();
                        Intrinsics.checkNotNull(certificateChainCleaner);
                        this.certificatePinner = certificatePinner.withCertificateChainCleaner$okhttp(certificateChainCleaner);
                    } else {
                        X509TrustManager x509TrustManagerPlatformTrustManager = Platform.INSTANCE.get().platformTrustManager();
                        this.x509TrustManager = x509TrustManagerPlatformTrustManager;
                        Platform platform = Platform.INSTANCE.get();
                        Intrinsics.checkNotNull(x509TrustManagerPlatformTrustManager);
                        this.sslSocketFactoryOrNull = platform.newSslSocketFactory(x509TrustManagerPlatformTrustManager);
                        CertificateChainCleaner.Companion companion = CertificateChainCleaner.INSTANCE;
                        Intrinsics.checkNotNull(x509TrustManagerPlatformTrustManager);
                        CertificateChainCleaner certificateChainCleaner2 = companion.get(x509TrustManagerPlatformTrustManager);
                        this.certificateChainCleaner = certificateChainCleaner2;
                        CertificatePinner certificatePinner2 = builder.getCertificatePinner();
                        Intrinsics.checkNotNull(certificateChainCleaner2);
                        this.certificatePinner = certificatePinner2.withCertificateChainCleaner$okhttp(certificateChainCleaner2);
                    }
                }
            }
            this.sslSocketFactoryOrNull = null;
            this.certificateChainCleaner = null;
            this.x509TrustManager = null;
            this.certificatePinner = CertificatePinner.DEFAULT;
        }
        verifyClientState();
    }

    public final Dispatcher dispatcher() {
        return this.dispatcher;
    }

    public final List<Interceptor> interceptors() {
        return this.interceptors;
    }

    public final List<Interceptor> networkInterceptors() {
        return this.networkInterceptors;
    }

    public final EventListener.Factory eventListenerFactory() {
        return this.eventListenerFactory;
    }

    public final boolean retryOnConnectionFailure() {
        return this.retryOnConnectionFailure;
    }

    /* JADX INFO: renamed from: fastFallback, reason: from getter */
    public final boolean getFastFallback() {
        return this.fastFallback;
    }

    public final Authenticator authenticator() {
        return this.authenticator;
    }

    public final boolean followRedirects() {
        return this.followRedirects;
    }

    public final boolean followSslRedirects() {
        return this.followSslRedirects;
    }

    public final CookieJar cookieJar() {
        return this.cookieJar;
    }

    public final Cache cache() {
        return this.cache;
    }

    public final Dns dns() {
        return this.dns;
    }

    public final Proxy proxy() {
        return this.proxy;
    }

    public final ProxySelector proxySelector() {
        return this.proxySelector;
    }

    public final Authenticator proxyAuthenticator() {
        return this.proxyAuthenticator;
    }

    public final SocketFactory socketFactory() {
        return this.socketFactory;
    }

    public final SSLSocketFactory sslSocketFactory() {
        SSLSocketFactory sSLSocketFactory = this.sslSocketFactoryOrNull;
        if (sSLSocketFactory != null) {
            return sSLSocketFactory;
        }
        throw new IllegalStateException("CLEARTEXT-only client");
    }

    /* JADX INFO: renamed from: x509TrustManager, reason: from getter */
    public final X509TrustManager getX509TrustManager() {
        return this.x509TrustManager;
    }

    public final List<ConnectionSpec> connectionSpecs() {
        return this.connectionSpecs;
    }

    public final List<Protocol> protocols() {
        return this.protocols;
    }

    public final HostnameVerifier hostnameVerifier() {
        return this.hostnameVerifier;
    }

    public final CertificatePinner certificatePinner() {
        return this.certificatePinner;
    }

    /* JADX INFO: renamed from: certificateChainCleaner, reason: from getter */
    public final CertificateChainCleaner getCertificateChainCleaner() {
        return this.certificateChainCleaner;
    }

    public final int callTimeoutMillis() {
        return this.callTimeoutMillis;
    }

    public final int connectTimeoutMillis() {
        return this.connectTimeoutMillis;
    }

    public final int readTimeoutMillis() {
        return this.readTimeoutMillis;
    }

    public final int writeTimeoutMillis() {
        return this.writeTimeoutMillis;
    }

    public final int pingIntervalMillis() {
        return this.pingIntervalMillis;
    }

    /* JADX INFO: renamed from: webSocketCloseTimeout, reason: from getter */
    public final int getWebSocketCloseTimeout() {
        return this.webSocketCloseTimeout;
    }

    /* JADX INFO: renamed from: minWebSocketMessageToCompress, reason: from getter */
    public final long getMinWebSocketMessageToCompress() {
        return this.minWebSocketMessageToCompress;
    }

    /* JADX INFO: renamed from: getRouteDatabase$okhttp, reason: from getter */
    public final RouteDatabase getRouteDatabase() {
        return this.routeDatabase;
    }

    /* JADX INFO: renamed from: getTaskRunner$okhttp, reason: from getter */
    public final TaskRunner getTaskRunner() {
        return this.taskRunner;
    }

    public final ConnectionPool connectionPool() {
        return this.connectionPool;
    }

    public OkHttpClient() {
        this(new Builder());
    }

    public final Address address(HttpUrl url) {
        SSLSocketFactory sslSocketFactory;
        HostnameVerifier hostnameVerifier;
        CertificatePinner certificatePinner;
        Intrinsics.checkNotNullParameter(url, "url");
        if (url.isHttps()) {
            sslSocketFactory = sslSocketFactory();
            hostnameVerifier = this.hostnameVerifier;
            certificatePinner = this.certificatePinner;
        } else {
            sslSocketFactory = null;
            hostnameVerifier = null;
            certificatePinner = null;
        }
        return new Address(url.host(), url.port(), this.dns, this.socketFactory, sslSocketFactory, hostnameVerifier, certificatePinner, this.proxyAuthenticator, this.proxy, this.protocols, this.connectionSpecs, this.proxySelector);
    }

    private final void verifyClientState() {
        List<Interceptor> list = this.interceptors;
        Intrinsics.checkNotNull(list, "null cannot be cast to non-null type kotlin.collections.List<okhttp3.Interceptor?>");
        if (list.contains(null)) {
            throw new IllegalStateException(("Null interceptor: " + this.interceptors).toString());
        }
        List<Interceptor> list2 = this.networkInterceptors;
        Intrinsics.checkNotNull(list2, "null cannot be cast to non-null type kotlin.collections.List<okhttp3.Interceptor?>");
        if (list2.contains(null)) {
            throw new IllegalStateException(("Null network interceptor: " + this.networkInterceptors).toString());
        }
        List<ConnectionSpec> list3 = this.connectionSpecs;
        if (!(list3 instanceof Collection) || !list3.isEmpty()) {
            Iterator<T> it = list3.iterator();
            while (it.hasNext()) {
                if (((ConnectionSpec) it.next()).getIsTls()) {
                    if (this.sslSocketFactoryOrNull == null) {
                        throw new IllegalStateException("sslSocketFactory == null".toString());
                    }
                    if (this.certificateChainCleaner == null) {
                        throw new IllegalStateException("certificateChainCleaner == null".toString());
                    }
                    if (this.x509TrustManager == null) {
                        throw new IllegalStateException("x509TrustManager == null".toString());
                    }
                    return;
                }
            }
        }
        if (this.sslSocketFactoryOrNull != null) {
            throw new IllegalStateException("Check failed.");
        }
        if (this.certificateChainCleaner != null) {
            throw new IllegalStateException("Check failed.");
        }
        if (this.x509TrustManager != null) {
            throw new IllegalStateException("Check failed.");
        }
        if (!Intrinsics.areEqual(this.certificatePinner, CertificatePinner.DEFAULT)) {
            throw new IllegalStateException("Check failed.");
        }
        Unit unit = Unit.INSTANCE;
    }

    @Override // okhttp3.Call.Factory
    public Call newCall(Request request) {
        Intrinsics.checkNotNullParameter(request, "request");
        return new RealCall(this, request, false);
    }

    @Override // okhttp3.WebSocket.Factory
    public WebSocket newWebSocket(Request request, WebSocketListener listener) {
        Intrinsics.checkNotNullParameter(request, "request");
        Intrinsics.checkNotNullParameter(listener, "listener");
        RealWebSocket realWebSocket = new RealWebSocket(this.taskRunner, request, listener, new Random(), this.pingIntervalMillis, null, this.minWebSocketMessageToCompress, this.webSocketCloseTimeout);
        realWebSocket.connect(this);
        return realWebSocket;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "dispatcher", imports = {}))
    /* JADX INFO: renamed from: -deprecated_dispatcher, reason: not valid java name and from getter */
    public final Dispatcher getDispatcher() {
        return this.dispatcher;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "connectionPool", imports = {}))
    /* JADX INFO: renamed from: -deprecated_connectionPool, reason: not valid java name and from getter */
    public final ConnectionPool getConnectionPool() {
        return this.connectionPool;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "interceptors", imports = {}))
    /* JADX INFO: renamed from: -deprecated_interceptors, reason: not valid java name */
    public final List<Interceptor> m4468deprecated_interceptors() {
        return this.interceptors;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "networkInterceptors", imports = {}))
    /* JADX INFO: renamed from: -deprecated_networkInterceptors, reason: not valid java name */
    public final List<Interceptor> m4469deprecated_networkInterceptors() {
        return this.networkInterceptors;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "eventListenerFactory", imports = {}))
    /* JADX INFO: renamed from: -deprecated_eventListenerFactory, reason: not valid java name and from getter */
    public final EventListener.Factory getEventListenerFactory() {
        return this.eventListenerFactory;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "retryOnConnectionFailure", imports = {}))
    /* JADX INFO: renamed from: -deprecated_retryOnConnectionFailure, reason: not valid java name and from getter */
    public final boolean getRetryOnConnectionFailure() {
        return this.retryOnConnectionFailure;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "authenticator", imports = {}))
    /* JADX INFO: renamed from: -deprecated_authenticator, reason: not valid java name and from getter */
    public final Authenticator getAuthenticator() {
        return this.authenticator;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "followRedirects", imports = {}))
    /* JADX INFO: renamed from: -deprecated_followRedirects, reason: not valid java name and from getter */
    public final boolean getFollowRedirects() {
        return this.followRedirects;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "followSslRedirects", imports = {}))
    /* JADX INFO: renamed from: -deprecated_followSslRedirects, reason: not valid java name and from getter */
    public final boolean getFollowSslRedirects() {
        return this.followSslRedirects;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "cookieJar", imports = {}))
    /* JADX INFO: renamed from: -deprecated_cookieJar, reason: not valid java name and from getter */
    public final CookieJar getCookieJar() {
        return this.cookieJar;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "cache", imports = {}))
    /* JADX INFO: renamed from: -deprecated_cache, reason: not valid java name and from getter */
    public final Cache getCache() {
        return this.cache;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "dns", imports = {}))
    /* JADX INFO: renamed from: -deprecated_dns, reason: not valid java name and from getter */
    public final Dns getDns() {
        return this.dns;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "proxy", imports = {}))
    /* JADX INFO: renamed from: -deprecated_proxy, reason: not valid java name and from getter */
    public final Proxy getProxy() {
        return this.proxy;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "proxySelector", imports = {}))
    /* JADX INFO: renamed from: -deprecated_proxySelector, reason: not valid java name and from getter */
    public final ProxySelector getProxySelector() {
        return this.proxySelector;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "proxyAuthenticator", imports = {}))
    /* JADX INFO: renamed from: -deprecated_proxyAuthenticator, reason: not valid java name and from getter */
    public final Authenticator getProxyAuthenticator() {
        return this.proxyAuthenticator;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "socketFactory", imports = {}))
    /* JADX INFO: renamed from: -deprecated_socketFactory, reason: not valid java name and from getter */
    public final SocketFactory getSocketFactory() {
        return this.socketFactory;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "sslSocketFactory", imports = {}))
    /* JADX INFO: renamed from: -deprecated_sslSocketFactory, reason: not valid java name */
    public final SSLSocketFactory m4478deprecated_sslSocketFactory() {
        return sslSocketFactory();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "connectionSpecs", imports = {}))
    /* JADX INFO: renamed from: -deprecated_connectionSpecs, reason: not valid java name */
    public final List<ConnectionSpec> m4460deprecated_connectionSpecs() {
        return this.connectionSpecs;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "protocols", imports = {}))
    /* JADX INFO: renamed from: -deprecated_protocols, reason: not valid java name */
    public final List<Protocol> m4471deprecated_protocols() {
        return this.protocols;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "hostnameVerifier", imports = {}))
    /* JADX INFO: renamed from: -deprecated_hostnameVerifier, reason: not valid java name and from getter */
    public final HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "certificatePinner", imports = {}))
    /* JADX INFO: renamed from: -deprecated_certificatePinner, reason: not valid java name and from getter */
    public final CertificatePinner getCertificatePinner() {
        return this.certificatePinner;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "callTimeoutMillis", imports = {}))
    /* JADX INFO: renamed from: -deprecated_callTimeoutMillis, reason: not valid java name and from getter */
    public final int getCallTimeoutMillis() {
        return this.callTimeoutMillis;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "connectTimeoutMillis", imports = {}))
    /* JADX INFO: renamed from: -deprecated_connectTimeoutMillis, reason: not valid java name and from getter */
    public final int getConnectTimeoutMillis() {
        return this.connectTimeoutMillis;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "readTimeoutMillis", imports = {}))
    /* JADX INFO: renamed from: -deprecated_readTimeoutMillis, reason: not valid java name and from getter */
    public final int getReadTimeoutMillis() {
        return this.readTimeoutMillis;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "writeTimeoutMillis", imports = {}))
    /* JADX INFO: renamed from: -deprecated_writeTimeoutMillis, reason: not valid java name and from getter */
    public final int getWriteTimeoutMillis() {
        return this.writeTimeoutMillis;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "pingIntervalMillis", imports = {}))
    /* JADX INFO: renamed from: -deprecated_pingIntervalMillis, reason: not valid java name and from getter */
    public final int getPingIntervalMillis() {
        return this.pingIntervalMillis;
    }

    /* JADX INFO: compiled from: OkHttpClient.kt */
    @Metadata(d1 = {"\u0000\u0086\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0014\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003B\u0011\b\u0010\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0002\u0010\u0006J\u000e\u0010\u0007\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\r\u001a\u00020\u00002\u0006\u0010\r\u001a\u00020\u000eJ\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014J\u0010\u0010«\u0001\u001a\u00020\u00002\u0007\u0010¬\u0001\u001a\u00020\u0015J?\u0010«\u0001\u001a\u00020\u00002*\b\u0004\u0010\u00ad\u0001\u001a#\u0012\u0017\u0012\u00150¯\u0001¢\u0006\u000f\b°\u0001\u0012\n\b±\u0001\u0012\u0005\b\b(²\u0001\u0012\u0005\u0012\u00030³\u00010®\u0001H\u0087\bø\u0001\u0000¢\u0006\u0003\b´\u0001J\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014J\u0010\u0010µ\u0001\u001a\u00020\u00002\u0007\u0010¬\u0001\u001a\u00020\u0015J?\u0010µ\u0001\u001a\u00020\u00002*\b\u0004\u0010\u00ad\u0001\u001a#\u0012\u0017\u0012\u00150¯\u0001¢\u0006\u000f\b°\u0001\u0012\n\b±\u0001\u0012\u0005\b\b(²\u0001\u0012\u0005\u0012\u00030³\u00010®\u0001H\u0087\bø\u0001\u0000¢\u0006\u0003\b¶\u0001J\u0011\u0010·\u0001\u001a\u00020\u00002\b\u0010·\u0001\u001a\u00030¸\u0001J\u000e\u0010\u001a\u001a\u00020\u00002\u0006\u0010\u001a\u001a\u00020\u001bJ\u000e\u0010 \u001a\u00020\u00002\u0006\u0010 \u001a\u00020!J\u000e\u0010&\u001a\u00020\u00002\u0006\u0010&\u001a\u00020!J\u000e\u0010)\u001a\u00020\u00002\u0006\u0010)\u001a\u00020*J\u000e\u0010/\u001a\u00020\u00002\u0006\u0010/\u001a\u00020!J\u000f\u00102\u001a\u00020\u00002\u0007\u0010¹\u0001\u001a\u00020!J\u000e\u00105\u001a\u00020\u00002\u0006\u00105\u001a\u000206J\u0010\u0010;\u001a\u00020\u00002\b\u0010;\u001a\u0004\u0018\u00010<J\u0019\u0010¥\u0001\u001a\u00020\u00002\b\u0010¥\u0001\u001a\u00030¦\u0001H\u0000¢\u0006\u0003\bº\u0001J\u000e\u0010A\u001a\u00020\u00002\u0006\u0010A\u001a\u00020BJ\u0010\u0010G\u001a\u00020\u00002\b\u0010G\u001a\u0004\u0018\u00010HJ\u000e\u0010M\u001a\u00020\u00002\u0006\u0010M\u001a\u00020NJ\u000e\u0010S\u001a\u00020\u00002\u0006\u0010S\u001a\u00020*J\u000e\u0010V\u001a\u00020\u00002\u0006\u0010V\u001a\u00020WJ\u0012\u0010»\u0001\u001a\u00020\u00002\u0007\u0010»\u0001\u001a\u00020]H\u0007J\u0019\u0010»\u0001\u001a\u00020\u00002\u0007\u0010»\u0001\u001a\u00020]2\u0007\u0010¼\u0001\u001a\u00020cJ\u0014\u0010h\u001a\u00020\u00002\f\u0010h\u001a\b\u0012\u0004\u0012\u00020j0iJ\u0014\u0010n\u001a\u00020\u00002\f\u0010n\u001a\b\u0012\u0004\u0012\u00020o0iJ\u000e\u0010r\u001a\u00020\u00002\u0006\u0010r\u001a\u00020sJ\u000e\u0010x\u001a\u00020\u00002\u0006\u0010x\u001a\u00020yJ\u001b\u0010\u0084\u0001\u001a\u00020\u00002\b\u0010½\u0001\u001a\u00030\u009a\u00012\b\u0010¾\u0001\u001a\u00030¿\u0001J\u0013\u0010\u0084\u0001\u001a\u00020\u00002\b\u0010À\u0001\u001a\u00030Á\u0001H\u0007J\u001a\u0010\u0084\u0001\u001a\u00020\u00002\b\u0010À\u0001\u001a\u00030Â\u0001¢\u0006\u0006\bÃ\u0001\u0010Ä\u0001J\u001b\u0010\u008a\u0001\u001a\u00020\u00002\b\u0010½\u0001\u001a\u00030\u009a\u00012\b\u0010¾\u0001\u001a\u00030¿\u0001J\u0013\u0010\u008a\u0001\u001a\u00020\u00002\b\u0010À\u0001\u001a\u00030Á\u0001H\u0007J\u001a\u0010\u008a\u0001\u001a\u00020\u00002\b\u0010À\u0001\u001a\u00030Â\u0001¢\u0006\u0006\bÅ\u0001\u0010Ä\u0001J\u001b\u0010\u008d\u0001\u001a\u00020\u00002\b\u0010½\u0001\u001a\u00030\u009a\u00012\b\u0010¾\u0001\u001a\u00030¿\u0001J\u0013\u0010\u008d\u0001\u001a\u00020\u00002\b\u0010À\u0001\u001a\u00030Á\u0001H\u0007J\u001a\u0010\u008d\u0001\u001a\u00020\u00002\b\u0010À\u0001\u001a\u00030Â\u0001¢\u0006\u0006\bÆ\u0001\u0010Ä\u0001J\u001b\u0010\u0090\u0001\u001a\u00020\u00002\b\u0010½\u0001\u001a\u00030\u009a\u00012\b\u0010¾\u0001\u001a\u00030¿\u0001J\u0013\u0010\u0090\u0001\u001a\u00020\u00002\b\u0010À\u0001\u001a\u00030Á\u0001H\u0007J\u001a\u0010\u0090\u0001\u001a\u00020\u00002\b\u0010À\u0001\u001a\u00030Â\u0001¢\u0006\u0006\bÇ\u0001\u0010Ä\u0001J\u001b\u0010\u0093\u0001\u001a\u00020\u00002\b\u0010È\u0001\u001a\u00030\u009a\u00012\b\u0010¾\u0001\u001a\u00030¿\u0001J\u0013\u0010\u0093\u0001\u001a\u00020\u00002\b\u0010À\u0001\u001a\u00030Á\u0001H\u0007J\u001a\u0010\u0093\u0001\u001a\u00020\u00002\b\u0010À\u0001\u001a\u00030Â\u0001¢\u0006\u0006\bÉ\u0001\u0010Ä\u0001J\u001b\u0010\u0096\u0001\u001a\u00020\u00002\b\u0010½\u0001\u001a\u00030\u009a\u00012\b\u0010¾\u0001\u001a\u00030¿\u0001J\u0013\u0010\u0096\u0001\u001a\u00020\u00002\b\u0010À\u0001\u001a\u00030Á\u0001H\u0007J\u001a\u0010\u0096\u0001\u001a\u00020\u00002\b\u0010À\u0001\u001a\u00030Â\u0001¢\u0006\u0006\bÊ\u0001\u0010Ä\u0001J\u0011\u0010\u0099\u0001\u001a\u00020\u00002\b\u0010Ë\u0001\u001a\u00030\u009a\u0001J\u0007\u0010Ì\u0001\u001a\u00020\u0005R\u001a\u0010\u0007\u001a\u00020\bX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001c\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0017R\u001a\u0010\u001a\u001a\u00020\u001bX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001a\u0010 \u001a\u00020!X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u001a\u0010&\u001a\u00020!X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010#\"\u0004\b(\u0010%R\u001a\u0010)\u001a\u00020*X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\u001a\u0010/\u001a\u00020!X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u0010#\"\u0004\b1\u0010%R\u001a\u00102\u001a\u00020!X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u0010#\"\u0004\b4\u0010%R\u001a\u00105\u001a\u000206X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b7\u00108\"\u0004\b9\u0010:R\u001c\u0010;\u001a\u0004\u0018\u00010<X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b=\u0010>\"\u0004\b?\u0010@R\u001a\u0010A\u001a\u00020BX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bC\u0010D\"\u0004\bE\u0010FR\u001c\u0010G\u001a\u0004\u0018\u00010HX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bI\u0010J\"\u0004\bK\u0010LR\u001c\u0010M\u001a\u0004\u0018\u00010NX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bO\u0010P\"\u0004\bQ\u0010RR\u001a\u0010S\u001a\u00020*X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bT\u0010,\"\u0004\bU\u0010.R\u001a\u0010V\u001a\u00020WX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bX\u0010Y\"\u0004\bZ\u0010[R\u001c\u0010\\\u001a\u0004\u0018\u00010]X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b^\u0010_\"\u0004\b`\u0010aR\u001c\u0010b\u001a\u0004\u0018\u00010cX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bd\u0010e\"\u0004\bf\u0010gR \u0010h\u001a\b\u0012\u0004\u0012\u00020j0iX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bk\u0010\u0017\"\u0004\bl\u0010mR \u0010n\u001a\b\u0012\u0004\u0012\u00020o0iX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bp\u0010\u0017\"\u0004\bq\u0010mR\u001a\u0010r\u001a\u00020sX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bt\u0010u\"\u0004\bv\u0010wR\u001a\u0010x\u001a\u00020yX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bz\u0010{\"\u0004\b|\u0010}R \u0010~\u001a\u0004\u0018\u00010\u007fX\u0080\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u0080\u0001\u0010\u0081\u0001\"\u0006\b\u0082\u0001\u0010\u0083\u0001R \u0010\u0084\u0001\u001a\u00030\u0085\u0001X\u0080\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u0086\u0001\u0010\u0087\u0001\"\u0006\b\u0088\u0001\u0010\u0089\u0001R \u0010\u008a\u0001\u001a\u00030\u0085\u0001X\u0080\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u008b\u0001\u0010\u0087\u0001\"\u0006\b\u008c\u0001\u0010\u0089\u0001R \u0010\u008d\u0001\u001a\u00030\u0085\u0001X\u0080\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u008e\u0001\u0010\u0087\u0001\"\u0006\b\u008f\u0001\u0010\u0089\u0001R \u0010\u0090\u0001\u001a\u00030\u0085\u0001X\u0080\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u0091\u0001\u0010\u0087\u0001\"\u0006\b\u0092\u0001\u0010\u0089\u0001R \u0010\u0093\u0001\u001a\u00030\u0085\u0001X\u0080\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u0094\u0001\u0010\u0087\u0001\"\u0006\b\u0095\u0001\u0010\u0089\u0001R \u0010\u0096\u0001\u001a\u00030\u0085\u0001X\u0080\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u0097\u0001\u0010\u0087\u0001\"\u0006\b\u0098\u0001\u0010\u0089\u0001R \u0010\u0099\u0001\u001a\u00030\u009a\u0001X\u0080\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u009b\u0001\u0010\u009c\u0001\"\u0006\b\u009d\u0001\u0010\u009e\u0001R\"\u0010\u009f\u0001\u001a\u0005\u0018\u00010 \u0001X\u0080\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b¡\u0001\u0010¢\u0001\"\u0006\b£\u0001\u0010¤\u0001R\"\u0010¥\u0001\u001a\u0005\u0018\u00010¦\u0001X\u0080\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b§\u0001\u0010¨\u0001\"\u0006\b©\u0001\u0010ª\u0001\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006Í\u0001"}, d2 = {"Lokhttp3/OkHttpClient$Builder;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "okHttpClient", "Lokhttp3/OkHttpClient;", "(Lokhttp3/OkHttpClient;)V", "dispatcher", "Lokhttp3/Dispatcher;", "getDispatcher$okhttp", "()Lokhttp3/Dispatcher;", "setDispatcher$okhttp", "(Lokhttp3/Dispatcher;)V", "connectionPool", "Lokhttp3/ConnectionPool;", "getConnectionPool$okhttp", "()Lokhttp3/ConnectionPool;", "setConnectionPool$okhttp", "(Lokhttp3/ConnectionPool;)V", "interceptors", "", "Lokhttp3/Interceptor;", "getInterceptors$okhttp", "()Ljava/util/List;", "networkInterceptors", "getNetworkInterceptors$okhttp", "eventListenerFactory", "Lokhttp3/EventListener$Factory;", "getEventListenerFactory$okhttp", "()Lokhttp3/EventListener$Factory;", "setEventListenerFactory$okhttp", "(Lokhttp3/EventListener$Factory;)V", "retryOnConnectionFailure", "", "getRetryOnConnectionFailure$okhttp", "()Z", "setRetryOnConnectionFailure$okhttp", "(Z)V", "fastFallback", "getFastFallback$okhttp", "setFastFallback$okhttp", "authenticator", "Lokhttp3/Authenticator;", "getAuthenticator$okhttp", "()Lokhttp3/Authenticator;", "setAuthenticator$okhttp", "(Lokhttp3/Authenticator;)V", "followRedirects", "getFollowRedirects$okhttp", "setFollowRedirects$okhttp", "followSslRedirects", "getFollowSslRedirects$okhttp", "setFollowSslRedirects$okhttp", "cookieJar", "Lokhttp3/CookieJar;", "getCookieJar$okhttp", "()Lokhttp3/CookieJar;", "setCookieJar$okhttp", "(Lokhttp3/CookieJar;)V", "cache", "Lokhttp3/Cache;", "getCache$okhttp", "()Lokhttp3/Cache;", "setCache$okhttp", "(Lokhttp3/Cache;)V", "dns", "Lokhttp3/Dns;", "getDns$okhttp", "()Lokhttp3/Dns;", "setDns$okhttp", "(Lokhttp3/Dns;)V", "proxy", "Ljava/net/Proxy;", "getProxy$okhttp", "()Ljava/net/Proxy;", "setProxy$okhttp", "(Ljava/net/Proxy;)V", "proxySelector", "Ljava/net/ProxySelector;", "getProxySelector$okhttp", "()Ljava/net/ProxySelector;", "setProxySelector$okhttp", "(Ljava/net/ProxySelector;)V", "proxyAuthenticator", "getProxyAuthenticator$okhttp", "setProxyAuthenticator$okhttp", "socketFactory", "Ljavax/net/SocketFactory;", "getSocketFactory$okhttp", "()Ljavax/net/SocketFactory;", "setSocketFactory$okhttp", "(Ljavax/net/SocketFactory;)V", "sslSocketFactoryOrNull", "Ljavax/net/ssl/SSLSocketFactory;", "getSslSocketFactoryOrNull$okhttp", "()Ljavax/net/ssl/SSLSocketFactory;", "setSslSocketFactoryOrNull$okhttp", "(Ljavax/net/ssl/SSLSocketFactory;)V", "x509TrustManagerOrNull", "Ljavax/net/ssl/X509TrustManager;", "getX509TrustManagerOrNull$okhttp", "()Ljavax/net/ssl/X509TrustManager;", "setX509TrustManagerOrNull$okhttp", "(Ljavax/net/ssl/X509TrustManager;)V", "connectionSpecs", "", "Lokhttp3/ConnectionSpec;", "getConnectionSpecs$okhttp", "setConnectionSpecs$okhttp", "(Ljava/util/List;)V", "protocols", "Lokhttp3/Protocol;", "getProtocols$okhttp", "setProtocols$okhttp", "hostnameVerifier", "Ljavax/net/ssl/HostnameVerifier;", "getHostnameVerifier$okhttp", "()Ljavax/net/ssl/HostnameVerifier;", "setHostnameVerifier$okhttp", "(Ljavax/net/ssl/HostnameVerifier;)V", "certificatePinner", "Lokhttp3/CertificatePinner;", "getCertificatePinner$okhttp", "()Lokhttp3/CertificatePinner;", "setCertificatePinner$okhttp", "(Lokhttp3/CertificatePinner;)V", "certificateChainCleaner", "Lokhttp3/internal/tls/CertificateChainCleaner;", "getCertificateChainCleaner$okhttp", "()Lokhttp3/internal/tls/CertificateChainCleaner;", "setCertificateChainCleaner$okhttp", "(Lokhttp3/internal/tls/CertificateChainCleaner;)V", "callTimeout", "", "getCallTimeout$okhttp", "()I", "setCallTimeout$okhttp", "(I)V", "connectTimeout", "getConnectTimeout$okhttp", "setConnectTimeout$okhttp", "readTimeout", "getReadTimeout$okhttp", "setReadTimeout$okhttp", "writeTimeout", "getWriteTimeout$okhttp", "setWriteTimeout$okhttp", "pingInterval", "getPingInterval$okhttp", "setPingInterval$okhttp", "webSocketCloseTimeout", "getWebSocketCloseTimeout$okhttp", "setWebSocketCloseTimeout$okhttp", "minWebSocketMessageToCompress", "", "getMinWebSocketMessageToCompress$okhttp", "()J", "setMinWebSocketMessageToCompress$okhttp", "(J)V", "routeDatabase", "Lokhttp3/internal/connection/RouteDatabase;", "getRouteDatabase$okhttp", "()Lokhttp3/internal/connection/RouteDatabase;", "setRouteDatabase$okhttp", "(Lokhttp3/internal/connection/RouteDatabase;)V", "taskRunner", "Lokhttp3/internal/concurrent/TaskRunner;", "getTaskRunner$okhttp", "()Lokhttp3/internal/concurrent/TaskRunner;", "setTaskRunner$okhttp", "(Lokhttp3/internal/concurrent/TaskRunner;)V", "addInterceptor", "interceptor", "block", "Lkotlin/Function1;", "Lokhttp3/Interceptor$Chain;", "Lkotlin/ParameterName;", "name", "chain", "Lokhttp3/Response;", "-addInterceptor", "addNetworkInterceptor", "-addNetworkInterceptor", "eventListener", "Lokhttp3/EventListener;", "followProtocolRedirects", "taskRunner$okhttp", "sslSocketFactory", "trustManager", "timeout", "unit", "Ljava/util/concurrent/TimeUnit;", "duration", "Ljava/time/Duration;", "Lkotlin/time/Duration;", "callTimeout-LRDsOJo", "(J)Lokhttp3/OkHttpClient$Builder;", "connectTimeout-LRDsOJo", "readTimeout-LRDsOJo", "writeTimeout-LRDsOJo", "interval", "pingInterval-LRDsOJo", "webSocketCloseTimeout-LRDsOJo", "bytes", "build", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Builder {
        private Authenticator authenticator;
        private Cache cache;
        private int callTimeout;
        private CertificateChainCleaner certificateChainCleaner;
        private CertificatePinner certificatePinner;
        private int connectTimeout;
        private ConnectionPool connectionPool;
        private List<ConnectionSpec> connectionSpecs;
        private CookieJar cookieJar;
        private Dispatcher dispatcher;
        private Dns dns;
        private EventListener.Factory eventListenerFactory;
        private boolean fastFallback;
        private boolean followRedirects;
        private boolean followSslRedirects;
        private HostnameVerifier hostnameVerifier;
        private final List<Interceptor> interceptors;
        private long minWebSocketMessageToCompress;
        private final List<Interceptor> networkInterceptors;
        private int pingInterval;
        private List<? extends Protocol> protocols;
        private Proxy proxy;
        private Authenticator proxyAuthenticator;
        private ProxySelector proxySelector;
        private int readTimeout;
        private boolean retryOnConnectionFailure;
        private RouteDatabase routeDatabase;
        private SocketFactory socketFactory;
        private SSLSocketFactory sslSocketFactoryOrNull;
        private TaskRunner taskRunner;
        private int webSocketCloseTimeout;
        private int writeTimeout;
        private X509TrustManager x509TrustManagerOrNull;

        public Builder() {
            this.dispatcher = new Dispatcher();
            this.interceptors = new ArrayList();
            this.networkInterceptors = new ArrayList();
            this.eventListenerFactory = _UtilJvmKt.asFactory(EventListener.NONE);
            this.retryOnConnectionFailure = true;
            this.fastFallback = true;
            this.authenticator = Authenticator.NONE;
            this.followRedirects = true;
            this.followSslRedirects = true;
            this.cookieJar = CookieJar.NO_COOKIES;
            this.dns = Dns.SYSTEM;
            this.proxyAuthenticator = Authenticator.NONE;
            SocketFactory socketFactory = SocketFactory.getDefault();
            Intrinsics.checkNotNullExpressionValue(socketFactory, "getDefault(...)");
            this.socketFactory = socketFactory;
            this.connectionSpecs = OkHttpClient.INSTANCE.getDEFAULT_CONNECTION_SPECS$okhttp();
            this.protocols = OkHttpClient.INSTANCE.getDEFAULT_PROTOCOLS$okhttp();
            this.hostnameVerifier = OkHostnameVerifier.INSTANCE;
            this.certificatePinner = CertificatePinner.DEFAULT;
            this.connectTimeout = 10000;
            this.readTimeout = 10000;
            this.writeTimeout = 10000;
            this.webSocketCloseTimeout = 60000;
            this.minWebSocketMessageToCompress = 1024L;
        }

        /* JADX INFO: renamed from: getDispatcher$okhttp, reason: from getter */
        public final Dispatcher getDispatcher() {
            return this.dispatcher;
        }

        public final void setDispatcher$okhttp(Dispatcher dispatcher) {
            Intrinsics.checkNotNullParameter(dispatcher, "<set-?>");
            this.dispatcher = dispatcher;
        }

        /* JADX INFO: renamed from: getConnectionPool$okhttp, reason: from getter */
        public final ConnectionPool getConnectionPool() {
            return this.connectionPool;
        }

        public final void setConnectionPool$okhttp(ConnectionPool connectionPool) {
            this.connectionPool = connectionPool;
        }

        public final List<Interceptor> getInterceptors$okhttp() {
            return this.interceptors;
        }

        public final List<Interceptor> getNetworkInterceptors$okhttp() {
            return this.networkInterceptors;
        }

        /* JADX INFO: renamed from: getEventListenerFactory$okhttp, reason: from getter */
        public final EventListener.Factory getEventListenerFactory() {
            return this.eventListenerFactory;
        }

        public final void setEventListenerFactory$okhttp(EventListener.Factory factory) {
            Intrinsics.checkNotNullParameter(factory, "<set-?>");
            this.eventListenerFactory = factory;
        }

        /* JADX INFO: renamed from: getRetryOnConnectionFailure$okhttp, reason: from getter */
        public final boolean getRetryOnConnectionFailure() {
            return this.retryOnConnectionFailure;
        }

        public final void setRetryOnConnectionFailure$okhttp(boolean z) {
            this.retryOnConnectionFailure = z;
        }

        /* JADX INFO: renamed from: getFastFallback$okhttp, reason: from getter */
        public final boolean getFastFallback() {
            return this.fastFallback;
        }

        public final void setFastFallback$okhttp(boolean z) {
            this.fastFallback = z;
        }

        /* JADX INFO: renamed from: getAuthenticator$okhttp, reason: from getter */
        public final Authenticator getAuthenticator() {
            return this.authenticator;
        }

        public final void setAuthenticator$okhttp(Authenticator authenticator) {
            Intrinsics.checkNotNullParameter(authenticator, "<set-?>");
            this.authenticator = authenticator;
        }

        /* JADX INFO: renamed from: getFollowRedirects$okhttp, reason: from getter */
        public final boolean getFollowRedirects() {
            return this.followRedirects;
        }

        public final void setFollowRedirects$okhttp(boolean z) {
            this.followRedirects = z;
        }

        /* JADX INFO: renamed from: getFollowSslRedirects$okhttp, reason: from getter */
        public final boolean getFollowSslRedirects() {
            return this.followSslRedirects;
        }

        public final void setFollowSslRedirects$okhttp(boolean z) {
            this.followSslRedirects = z;
        }

        /* JADX INFO: renamed from: getCookieJar$okhttp, reason: from getter */
        public final CookieJar getCookieJar() {
            return this.cookieJar;
        }

        public final void setCookieJar$okhttp(CookieJar cookieJar) {
            Intrinsics.checkNotNullParameter(cookieJar, "<set-?>");
            this.cookieJar = cookieJar;
        }

        /* JADX INFO: renamed from: getCache$okhttp, reason: from getter */
        public final Cache getCache() {
            return this.cache;
        }

        public final void setCache$okhttp(Cache cache) {
            this.cache = cache;
        }

        /* JADX INFO: renamed from: getDns$okhttp, reason: from getter */
        public final Dns getDns() {
            return this.dns;
        }

        public final void setDns$okhttp(Dns dns) {
            Intrinsics.checkNotNullParameter(dns, "<set-?>");
            this.dns = dns;
        }

        /* JADX INFO: renamed from: getProxy$okhttp, reason: from getter */
        public final Proxy getProxy() {
            return this.proxy;
        }

        public final void setProxy$okhttp(Proxy proxy) {
            this.proxy = proxy;
        }

        /* JADX INFO: renamed from: getProxySelector$okhttp, reason: from getter */
        public final ProxySelector getProxySelector() {
            return this.proxySelector;
        }

        public final void setProxySelector$okhttp(ProxySelector proxySelector) {
            this.proxySelector = proxySelector;
        }

        /* JADX INFO: renamed from: getProxyAuthenticator$okhttp, reason: from getter */
        public final Authenticator getProxyAuthenticator() {
            return this.proxyAuthenticator;
        }

        public final void setProxyAuthenticator$okhttp(Authenticator authenticator) {
            Intrinsics.checkNotNullParameter(authenticator, "<set-?>");
            this.proxyAuthenticator = authenticator;
        }

        /* JADX INFO: renamed from: getSocketFactory$okhttp, reason: from getter */
        public final SocketFactory getSocketFactory() {
            return this.socketFactory;
        }

        public final void setSocketFactory$okhttp(SocketFactory socketFactory) {
            Intrinsics.checkNotNullParameter(socketFactory, "<set-?>");
            this.socketFactory = socketFactory;
        }

        /* JADX INFO: renamed from: getSslSocketFactoryOrNull$okhttp, reason: from getter */
        public final SSLSocketFactory getSslSocketFactoryOrNull() {
            return this.sslSocketFactoryOrNull;
        }

        public final void setSslSocketFactoryOrNull$okhttp(SSLSocketFactory sSLSocketFactory) {
            this.sslSocketFactoryOrNull = sSLSocketFactory;
        }

        /* JADX INFO: renamed from: getX509TrustManagerOrNull$okhttp, reason: from getter */
        public final X509TrustManager getX509TrustManagerOrNull() {
            return this.x509TrustManagerOrNull;
        }

        public final void setX509TrustManagerOrNull$okhttp(X509TrustManager x509TrustManager) {
            this.x509TrustManagerOrNull = x509TrustManager;
        }

        public final List<ConnectionSpec> getConnectionSpecs$okhttp() {
            return this.connectionSpecs;
        }

        public final void setConnectionSpecs$okhttp(List<ConnectionSpec> list) {
            Intrinsics.checkNotNullParameter(list, "<set-?>");
            this.connectionSpecs = list;
        }

        public final List<Protocol> getProtocols$okhttp() {
            return this.protocols;
        }

        public final void setProtocols$okhttp(List<? extends Protocol> list) {
            Intrinsics.checkNotNullParameter(list, "<set-?>");
            this.protocols = list;
        }

        /* JADX INFO: renamed from: getHostnameVerifier$okhttp, reason: from getter */
        public final HostnameVerifier getHostnameVerifier() {
            return this.hostnameVerifier;
        }

        public final void setHostnameVerifier$okhttp(HostnameVerifier hostnameVerifier) {
            Intrinsics.checkNotNullParameter(hostnameVerifier, "<set-?>");
            this.hostnameVerifier = hostnameVerifier;
        }

        /* JADX INFO: renamed from: getCertificatePinner$okhttp, reason: from getter */
        public final CertificatePinner getCertificatePinner() {
            return this.certificatePinner;
        }

        public final void setCertificatePinner$okhttp(CertificatePinner certificatePinner) {
            Intrinsics.checkNotNullParameter(certificatePinner, "<set-?>");
            this.certificatePinner = certificatePinner;
        }

        /* JADX INFO: renamed from: getCertificateChainCleaner$okhttp, reason: from getter */
        public final CertificateChainCleaner getCertificateChainCleaner() {
            return this.certificateChainCleaner;
        }

        public final void setCertificateChainCleaner$okhttp(CertificateChainCleaner certificateChainCleaner) {
            this.certificateChainCleaner = certificateChainCleaner;
        }

        /* JADX INFO: renamed from: getCallTimeout$okhttp, reason: from getter */
        public final int getCallTimeout() {
            return this.callTimeout;
        }

        public final void setCallTimeout$okhttp(int i) {
            this.callTimeout = i;
        }

        /* JADX INFO: renamed from: getConnectTimeout$okhttp, reason: from getter */
        public final int getConnectTimeout() {
            return this.connectTimeout;
        }

        public final void setConnectTimeout$okhttp(int i) {
            this.connectTimeout = i;
        }

        /* JADX INFO: renamed from: getReadTimeout$okhttp, reason: from getter */
        public final int getReadTimeout() {
            return this.readTimeout;
        }

        public final void setReadTimeout$okhttp(int i) {
            this.readTimeout = i;
        }

        /* JADX INFO: renamed from: getWriteTimeout$okhttp, reason: from getter */
        public final int getWriteTimeout() {
            return this.writeTimeout;
        }

        public final void setWriteTimeout$okhttp(int i) {
            this.writeTimeout = i;
        }

        /* JADX INFO: renamed from: getPingInterval$okhttp, reason: from getter */
        public final int getPingInterval() {
            return this.pingInterval;
        }

        public final void setPingInterval$okhttp(int i) {
            this.pingInterval = i;
        }

        /* JADX INFO: renamed from: getWebSocketCloseTimeout$okhttp, reason: from getter */
        public final int getWebSocketCloseTimeout() {
            return this.webSocketCloseTimeout;
        }

        public final void setWebSocketCloseTimeout$okhttp(int i) {
            this.webSocketCloseTimeout = i;
        }

        /* JADX INFO: renamed from: getMinWebSocketMessageToCompress$okhttp, reason: from getter */
        public final long getMinWebSocketMessageToCompress() {
            return this.minWebSocketMessageToCompress;
        }

        public final void setMinWebSocketMessageToCompress$okhttp(long j) {
            this.minWebSocketMessageToCompress = j;
        }

        /* JADX INFO: renamed from: getRouteDatabase$okhttp, reason: from getter */
        public final RouteDatabase getRouteDatabase() {
            return this.routeDatabase;
        }

        public final void setRouteDatabase$okhttp(RouteDatabase routeDatabase) {
            this.routeDatabase = routeDatabase;
        }

        /* JADX INFO: renamed from: getTaskRunner$okhttp, reason: from getter */
        public final TaskRunner getTaskRunner() {
            return this.taskRunner;
        }

        public final void setTaskRunner$okhttp(TaskRunner taskRunner) {
            this.taskRunner = taskRunner;
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public Builder(OkHttpClient okHttpClient) {
            this();
            Intrinsics.checkNotNullParameter(okHttpClient, "okHttpClient");
            this.dispatcher = okHttpClient.dispatcher();
            this.connectionPool = okHttpClient.connectionPool();
            CollectionsKt.addAll(this.interceptors, okHttpClient.interceptors());
            CollectionsKt.addAll(this.networkInterceptors, okHttpClient.networkInterceptors());
            this.eventListenerFactory = okHttpClient.eventListenerFactory();
            this.retryOnConnectionFailure = okHttpClient.retryOnConnectionFailure();
            this.fastFallback = okHttpClient.getFastFallback();
            this.authenticator = okHttpClient.authenticator();
            this.followRedirects = okHttpClient.followRedirects();
            this.followSslRedirects = okHttpClient.followSslRedirects();
            this.cookieJar = okHttpClient.cookieJar();
            this.cache = okHttpClient.cache();
            this.dns = okHttpClient.dns();
            this.proxy = okHttpClient.proxy();
            this.proxySelector = okHttpClient.proxySelector();
            this.proxyAuthenticator = okHttpClient.proxyAuthenticator();
            this.socketFactory = okHttpClient.socketFactory();
            this.sslSocketFactoryOrNull = okHttpClient.sslSocketFactoryOrNull;
            this.x509TrustManagerOrNull = okHttpClient.getX509TrustManager();
            this.connectionSpecs = okHttpClient.connectionSpecs();
            this.protocols = okHttpClient.protocols();
            this.hostnameVerifier = okHttpClient.hostnameVerifier();
            this.certificatePinner = okHttpClient.certificatePinner();
            this.certificateChainCleaner = okHttpClient.getCertificateChainCleaner();
            this.callTimeout = okHttpClient.callTimeoutMillis();
            this.connectTimeout = okHttpClient.connectTimeoutMillis();
            this.readTimeout = okHttpClient.readTimeoutMillis();
            this.writeTimeout = okHttpClient.writeTimeoutMillis();
            this.pingInterval = okHttpClient.pingIntervalMillis();
            this.webSocketCloseTimeout = okHttpClient.getWebSocketCloseTimeout();
            this.minWebSocketMessageToCompress = okHttpClient.getMinWebSocketMessageToCompress();
            this.routeDatabase = okHttpClient.getRouteDatabase();
            this.taskRunner = okHttpClient.getTaskRunner();
        }

        public final Builder dispatcher(Dispatcher dispatcher) {
            Intrinsics.checkNotNullParameter(dispatcher, "dispatcher");
            this.dispatcher = dispatcher;
            return this;
        }

        public final Builder connectionPool(ConnectionPool connectionPool) {
            Intrinsics.checkNotNullParameter(connectionPool, "connectionPool");
            this.connectionPool = connectionPool;
            return this;
        }

        public final List<Interceptor> interceptors() {
            return this.interceptors;
        }

        public final Builder addInterceptor(Interceptor interceptor) {
            Intrinsics.checkNotNullParameter(interceptor, "interceptor");
            this.interceptors.add(interceptor);
            return this;
        }

        /* JADX INFO: renamed from: -addInterceptor, reason: not valid java name */
        public final Builder m4480addInterceptor(final Function1<? super Interceptor.Chain, Response> block) {
            Intrinsics.checkNotNullParameter(block, "block");
            return addInterceptor(new Interceptor() { // from class: okhttp3.OkHttpClient$Builder$addInterceptor$2
                @Override // okhttp3.Interceptor
                public final Response intercept(Interceptor.Chain chain) {
                    Intrinsics.checkNotNullParameter(chain, "chain");
                    return block.invoke(chain);
                }
            });
        }

        public final List<Interceptor> networkInterceptors() {
            return this.networkInterceptors;
        }

        public final Builder addNetworkInterceptor(Interceptor interceptor) {
            Intrinsics.checkNotNullParameter(interceptor, "interceptor");
            this.networkInterceptors.add(interceptor);
            return this;
        }

        /* JADX INFO: renamed from: -addNetworkInterceptor, reason: not valid java name */
        public final Builder m4481addNetworkInterceptor(final Function1<? super Interceptor.Chain, Response> block) {
            Intrinsics.checkNotNullParameter(block, "block");
            return addNetworkInterceptor(new Interceptor() { // from class: okhttp3.OkHttpClient$Builder$addNetworkInterceptor$2
                @Override // okhttp3.Interceptor
                public final Response intercept(Interceptor.Chain chain) {
                    Intrinsics.checkNotNullParameter(chain, "chain");
                    return block.invoke(chain);
                }
            });
        }

        public final Builder eventListener(EventListener eventListener) {
            Intrinsics.checkNotNullParameter(eventListener, "eventListener");
            this.eventListenerFactory = _UtilJvmKt.asFactory(eventListener);
            return this;
        }

        public final Builder eventListenerFactory(EventListener.Factory eventListenerFactory) {
            Intrinsics.checkNotNullParameter(eventListenerFactory, "eventListenerFactory");
            this.eventListenerFactory = eventListenerFactory;
            return this;
        }

        public final Builder retryOnConnectionFailure(boolean retryOnConnectionFailure) {
            this.retryOnConnectionFailure = retryOnConnectionFailure;
            return this;
        }

        public final Builder fastFallback(boolean fastFallback) {
            this.fastFallback = fastFallback;
            return this;
        }

        public final Builder authenticator(Authenticator authenticator) {
            Intrinsics.checkNotNullParameter(authenticator, "authenticator");
            this.authenticator = authenticator;
            return this;
        }

        public final Builder followRedirects(boolean followRedirects) {
            this.followRedirects = followRedirects;
            return this;
        }

        public final Builder followSslRedirects(boolean followProtocolRedirects) {
            this.followSslRedirects = followProtocolRedirects;
            return this;
        }

        public final Builder cookieJar(CookieJar cookieJar) {
            Intrinsics.checkNotNullParameter(cookieJar, "cookieJar");
            this.cookieJar = cookieJar;
            return this;
        }

        public final Builder cache(Cache cache) {
            this.cache = cache;
            return this;
        }

        public final Builder taskRunner$okhttp(TaskRunner taskRunner) {
            Intrinsics.checkNotNullParameter(taskRunner, "taskRunner");
            this.taskRunner = taskRunner;
            return this;
        }

        public final Builder dns(Dns dns) {
            Intrinsics.checkNotNullParameter(dns, "dns");
            if (!Intrinsics.areEqual(dns, this.dns)) {
                this.routeDatabase = null;
            }
            this.dns = dns;
            return this;
        }

        public final Builder proxy(Proxy proxy) {
            if (!Intrinsics.areEqual(proxy, this.proxy)) {
                this.routeDatabase = null;
            }
            this.proxy = proxy;
            return this;
        }

        public final Builder proxySelector(ProxySelector proxySelector) {
            Intrinsics.checkNotNullParameter(proxySelector, "proxySelector");
            if (!Intrinsics.areEqual(proxySelector, this.proxySelector)) {
                this.routeDatabase = null;
            }
            this.proxySelector = proxySelector;
            return this;
        }

        public final Builder proxyAuthenticator(Authenticator proxyAuthenticator) {
            Intrinsics.checkNotNullParameter(proxyAuthenticator, "proxyAuthenticator");
            if (!Intrinsics.areEqual(proxyAuthenticator, this.proxyAuthenticator)) {
                this.routeDatabase = null;
            }
            this.proxyAuthenticator = proxyAuthenticator;
            return this;
        }

        public final Builder socketFactory(SocketFactory socketFactory) {
            Intrinsics.checkNotNullParameter(socketFactory, "socketFactory");
            if (socketFactory instanceof SSLSocketFactory) {
                throw new IllegalArgumentException("socketFactory instanceof SSLSocketFactory".toString());
            }
            if (!Intrinsics.areEqual(socketFactory, this.socketFactory)) {
                this.routeDatabase = null;
            }
            this.socketFactory = socketFactory;
            return this;
        }

        @Deprecated(level = DeprecationLevel.ERROR, message = "Use the sslSocketFactory overload that accepts a X509TrustManager.")
        public final Builder sslSocketFactory(SSLSocketFactory sslSocketFactory) {
            Intrinsics.checkNotNullParameter(sslSocketFactory, "sslSocketFactory");
            if (!Intrinsics.areEqual(sslSocketFactory, this.sslSocketFactoryOrNull)) {
                this.routeDatabase = null;
            }
            this.sslSocketFactoryOrNull = sslSocketFactory;
            X509TrustManager x509TrustManagerTrustManager = Platform.INSTANCE.get().trustManager(sslSocketFactory);
            if (x509TrustManagerTrustManager != null) {
                this.x509TrustManagerOrNull = x509TrustManagerTrustManager;
                Platform platform = Platform.INSTANCE.get();
                X509TrustManager x509TrustManager = this.x509TrustManagerOrNull;
                Intrinsics.checkNotNull(x509TrustManager);
                this.certificateChainCleaner = platform.buildCertificateChainCleaner(x509TrustManager);
                return this;
            }
            throw new IllegalStateException("Unable to extract the trust manager on " + Platform.INSTANCE.get() + ", sslSocketFactory is " + sslSocketFactory.getClass());
        }

        public final Builder sslSocketFactory(SSLSocketFactory sslSocketFactory, X509TrustManager trustManager) {
            Intrinsics.checkNotNullParameter(sslSocketFactory, "sslSocketFactory");
            Intrinsics.checkNotNullParameter(trustManager, "trustManager");
            if (!Intrinsics.areEqual(sslSocketFactory, this.sslSocketFactoryOrNull) || !Intrinsics.areEqual(trustManager, this.x509TrustManagerOrNull)) {
                this.routeDatabase = null;
            }
            this.sslSocketFactoryOrNull = sslSocketFactory;
            this.certificateChainCleaner = CertificateChainCleaner.INSTANCE.get(trustManager);
            this.x509TrustManagerOrNull = trustManager;
            return this;
        }

        public final Builder connectionSpecs(List<ConnectionSpec> connectionSpecs) {
            Intrinsics.checkNotNullParameter(connectionSpecs, "connectionSpecs");
            if (!Intrinsics.areEqual(connectionSpecs, this.connectionSpecs)) {
                this.routeDatabase = null;
            }
            this.connectionSpecs = _UtilJvmKt.toImmutableList(connectionSpecs);
            return this;
        }

        public final Builder protocols(List<? extends Protocol> protocols) {
            Intrinsics.checkNotNullParameter(protocols, "protocols");
            List mutableList = CollectionsKt.toMutableList((Collection) protocols);
            if (!mutableList.contains(Protocol.H2_PRIOR_KNOWLEDGE) && !mutableList.contains(Protocol.HTTP_1_1)) {
                throw new IllegalArgumentException(("protocols must contain h2_prior_knowledge or http/1.1: " + mutableList).toString());
            }
            if (mutableList.contains(Protocol.H2_PRIOR_KNOWLEDGE) && mutableList.size() > 1) {
                throw new IllegalArgumentException(("protocols containing h2_prior_knowledge cannot use other protocols: " + mutableList).toString());
            }
            if (mutableList.contains(Protocol.HTTP_1_0)) {
                throw new IllegalArgumentException(("protocols must not contain http/1.0: " + mutableList).toString());
            }
            Intrinsics.checkNotNull(mutableList, "null cannot be cast to non-null type kotlin.collections.List<okhttp3.Protocol?>");
            if (mutableList.contains(null)) {
                throw new IllegalArgumentException("protocols must not contain null".toString());
            }
            mutableList.remove(Protocol.SPDY_3);
            if (!Intrinsics.areEqual(mutableList, this.protocols)) {
                this.routeDatabase = null;
            }
            List<? extends Protocol> listUnmodifiableList = Collections.unmodifiableList(mutableList);
            Intrinsics.checkNotNullExpressionValue(listUnmodifiableList, "unmodifiableList(...)");
            this.protocols = listUnmodifiableList;
            return this;
        }

        public final Builder hostnameVerifier(HostnameVerifier hostnameVerifier) {
            Intrinsics.checkNotNullParameter(hostnameVerifier, "hostnameVerifier");
            if (!Intrinsics.areEqual(hostnameVerifier, this.hostnameVerifier)) {
                this.routeDatabase = null;
            }
            this.hostnameVerifier = hostnameVerifier;
            return this;
        }

        public final Builder certificatePinner(CertificatePinner certificatePinner) {
            Intrinsics.checkNotNullParameter(certificatePinner, "certificatePinner");
            if (!Intrinsics.areEqual(certificatePinner, this.certificatePinner)) {
                this.routeDatabase = null;
            }
            this.certificatePinner = certificatePinner;
            return this;
        }

        public final Builder callTimeout(long timeout, TimeUnit unit) {
            Intrinsics.checkNotNullParameter(unit, "unit");
            this.callTimeout = _UtilJvmKt.checkDuration("timeout", timeout, unit);
            return this;
        }

        public final Builder callTimeout(Duration duration) {
            Intrinsics.checkNotNullParameter(duration, "duration");
            callTimeout(duration.toMillis(), TimeUnit.MILLISECONDS);
            return this;
        }

        /* JADX INFO: renamed from: callTimeout-LRDsOJo, reason: not valid java name */
        public final Builder m4482callTimeoutLRDsOJo(long duration) {
            this.callTimeout = _UtilJvmKt.m4510checkDurationHG0u8IE("duration", duration);
            return this;
        }

        public final Builder connectTimeout(long timeout, TimeUnit unit) {
            Intrinsics.checkNotNullParameter(unit, "unit");
            this.connectTimeout = _UtilJvmKt.checkDuration("timeout", timeout, unit);
            return this;
        }

        public final Builder connectTimeout(Duration duration) {
            Intrinsics.checkNotNullParameter(duration, "duration");
            connectTimeout(duration.toMillis(), TimeUnit.MILLISECONDS);
            return this;
        }

        /* JADX INFO: renamed from: connectTimeout-LRDsOJo, reason: not valid java name */
        public final Builder m4483connectTimeoutLRDsOJo(long duration) {
            this.connectTimeout = _UtilJvmKt.m4510checkDurationHG0u8IE("duration", duration);
            return this;
        }

        public final Builder readTimeout(long timeout, TimeUnit unit) {
            Intrinsics.checkNotNullParameter(unit, "unit");
            this.readTimeout = _UtilJvmKt.checkDuration("timeout", timeout, unit);
            return this;
        }

        public final Builder readTimeout(Duration duration) {
            Intrinsics.checkNotNullParameter(duration, "duration");
            readTimeout(duration.toMillis(), TimeUnit.MILLISECONDS);
            return this;
        }

        /* JADX INFO: renamed from: readTimeout-LRDsOJo, reason: not valid java name */
        public final Builder m4485readTimeoutLRDsOJo(long duration) {
            this.readTimeout = _UtilJvmKt.m4510checkDurationHG0u8IE("duration", duration);
            return this;
        }

        public final Builder writeTimeout(long timeout, TimeUnit unit) {
            Intrinsics.checkNotNullParameter(unit, "unit");
            this.writeTimeout = _UtilJvmKt.checkDuration("timeout", timeout, unit);
            return this;
        }

        public final Builder writeTimeout(Duration duration) {
            Intrinsics.checkNotNullParameter(duration, "duration");
            writeTimeout(duration.toMillis(), TimeUnit.MILLISECONDS);
            return this;
        }

        /* JADX INFO: renamed from: writeTimeout-LRDsOJo, reason: not valid java name */
        public final Builder m4487writeTimeoutLRDsOJo(long duration) {
            this.writeTimeout = _UtilJvmKt.m4510checkDurationHG0u8IE("duration", duration);
            return this;
        }

        public final Builder pingInterval(long interval, TimeUnit unit) {
            Intrinsics.checkNotNullParameter(unit, "unit");
            this.pingInterval = _UtilJvmKt.checkDuration("interval", interval, unit);
            return this;
        }

        public final Builder pingInterval(Duration duration) {
            Intrinsics.checkNotNullParameter(duration, "duration");
            pingInterval(duration.toMillis(), TimeUnit.MILLISECONDS);
            return this;
        }

        /* JADX INFO: renamed from: pingInterval-LRDsOJo, reason: not valid java name */
        public final Builder m4484pingIntervalLRDsOJo(long duration) {
            this.pingInterval = _UtilJvmKt.m4510checkDurationHG0u8IE("duration", duration);
            return this;
        }

        public final Builder webSocketCloseTimeout(long timeout, TimeUnit unit) {
            Intrinsics.checkNotNullParameter(unit, "unit");
            this.webSocketCloseTimeout = _UtilJvmKt.checkDuration("webSocketCloseTimeout", timeout, unit);
            return this;
        }

        public final Builder webSocketCloseTimeout(Duration duration) {
            Intrinsics.checkNotNullParameter(duration, "duration");
            webSocketCloseTimeout(duration.toMillis(), TimeUnit.MILLISECONDS);
            return this;
        }

        /* JADX INFO: renamed from: webSocketCloseTimeout-LRDsOJo, reason: not valid java name */
        public final Builder m4486webSocketCloseTimeoutLRDsOJo(long duration) {
            this.webSocketCloseTimeout = _UtilJvmKt.m4510checkDurationHG0u8IE("duration", duration);
            return this;
        }

        public final Builder minWebSocketMessageToCompress(long bytes) {
            if (bytes < 0) {
                throw new IllegalArgumentException(("minWebSocketMessageToCompress must be positive: " + bytes).toString());
            }
            this.minWebSocketMessageToCompress = bytes;
            return this;
        }

        public final OkHttpClient build() {
            return new OkHttpClient(this);
        }
    }

    /* JADX INFO: compiled from: OkHttpClient.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\b¨\u0006\f"}, d2 = {"Lokhttp3/OkHttpClient$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "DEFAULT_PROTOCOLS", "", "Lokhttp3/Protocol;", "getDEFAULT_PROTOCOLS$okhttp", "()Ljava/util/List;", "DEFAULT_CONNECTION_SPECS", "Lokhttp3/ConnectionSpec;", "getDEFAULT_CONNECTION_SPECS$okhttp", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final List<Protocol> getDEFAULT_PROTOCOLS$okhttp() {
            return OkHttpClient.DEFAULT_PROTOCOLS;
        }

        public final List<ConnectionSpec> getDEFAULT_CONNECTION_SPECS$okhttp() {
            return OkHttpClient.DEFAULT_CONNECTION_SPECS;
        }
    }
}
