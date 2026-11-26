package okhttp3;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import defpackage.R2;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.concurrent.TaskRunner;
import okhttp3.internal.connection.ConnectionListener;
import okhttp3.internal.connection.ConnectionUser;
import okhttp3.internal.connection.ExchangeFinder;
import okhttp3.internal.connection.FastFallbackExchangeFinder;
import okhttp3.internal.connection.ForceConnectRoutePlanner;
import okhttp3.internal.connection.RealConnectionPool;
import okhttp3.internal.connection.RealRoutePlanner;
import okhttp3.internal.connection.RouteDatabase;

/* JADX INFO: compiled from: ConnectionPool.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0011\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005B\u008b\u0001\b\u0010\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0016\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0016\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0019¢\u0006\u0004\b\u0004\u0010\u001aB1\b\u0010\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f¢\u0006\u0004\b\u0004\u0010\u001bB!\b\u0016\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0004\b\u0004\u0010\u001cB\t\b\u0016¢\u0006\u0004\b\u0004\u0010\u001dJ\u0006\u0010 \u001a\u00020\u0007J\u0006\u0010!\u001a\u00020\u0007J\u0006\u0010$\u001a\u00020%R\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0014\u0010\u000e\u001a\u00020\u000f8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#¨\u0006&"}, d2 = {"Lokhttp3/ConnectionPool;", "", "delegate", "Lokhttp3/internal/connection/RealConnectionPool;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokhttp3/internal/connection/RealConnectionPool;)V", "maxIdleConnections", "", "keepAliveDuration", "", "timeUnit", "Ljava/util/concurrent/TimeUnit;", "taskRunner", "Lokhttp3/internal/concurrent/TaskRunner;", "connectionListener", "Lokhttp3/internal/connection/ConnectionListener;", "readTimeoutMillis", "writeTimeoutMillis", "socketConnectTimeoutMillis", "socketReadTimeoutMillis", "pingIntervalMillis", "retryOnConnectionFailure", "", "fastFallback", "routeDatabase", "Lokhttp3/internal/connection/RouteDatabase;", "(IJLjava/util/concurrent/TimeUnit;Lokhttp3/internal/concurrent/TaskRunner;Lokhttp3/internal/connection/ConnectionListener;IIIIIZZLokhttp3/internal/connection/RouteDatabase;)V", "(IJLjava/util/concurrent/TimeUnit;Lokhttp3/internal/connection/ConnectionListener;)V", "(IJLjava/util/concurrent/TimeUnit;)V", "()V", "getDelegate$okhttp", "()Lokhttp3/internal/connection/RealConnectionPool;", "idleConnectionCount", "connectionCount", "getConnectionListener$okhttp", "()Lokhttp3/internal/connection/ConnectionListener;", "evictAll", "", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ConnectionPool {
    private final RealConnectionPool delegate;

    public ConnectionPool(RealConnectionPool delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.delegate = delegate;
    }

    /* JADX INFO: renamed from: getDelegate$okhttp, reason: from getter */
    public final RealConnectionPool getDelegate() {
        return this.delegate;
    }

    public /* synthetic */ ConnectionPool(int i, long j, TimeUnit timeUnit, TaskRunner taskRunner, ConnectionListener connectionListener, int i2, int i3, int i4, int i5, int i6, boolean z, boolean z2, RouteDatabase routeDatabase, int i7, DefaultConstructorMarker defaultConstructorMarker) {
        this((i7 & 1) != 0 ? 5 : i, (i7 & 2) != 0 ? 5L : j, (i7 & 4) != 0 ? TimeUnit.MINUTES : timeUnit, (i7 & 8) != 0 ? TaskRunner.INSTANCE : taskRunner, (i7 & 16) != 0 ? ConnectionListener.INSTANCE.getNONE() : connectionListener, (i7 & 32) != 0 ? 10000 : i2, (i7 & 64) != 0 ? 10000 : i3, (i7 & 128) != 0 ? 10000 : i4, (i7 & 256) != 0 ? 10000 : i5, (i7 & 512) == 0 ? i6 : 10000, (i7 & 1024) != 0 ? true : z, (i7 & 2048) == 0 ? z2 : true, (i7 & 4096) != 0 ? new RouteDatabase() : routeDatabase);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ConnectionPool(int i, long j, TimeUnit timeUnit, final TaskRunner taskRunner, ConnectionListener connectionListener, final int i2, final int i3, final int i4, final int i5, final int i6, final boolean z, final boolean z2, final RouteDatabase routeDatabase) {
        this(new RealConnectionPool(taskRunner, i, j, timeUnit, connectionListener, new Function3() { // from class: okhttp3.ConnectionPool$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                return ConnectionPool._init_$lambda$0(taskRunner, i2, i3, i4, i5, i6, z, z2, routeDatabase, (RealConnectionPool) obj, (Address) obj2, (ConnectionUser) obj3);
            }
        }));
        Intrinsics.checkNotNullParameter(timeUnit, "timeUnit");
        Intrinsics.checkNotNullParameter(taskRunner, "taskRunner");
        Intrinsics.checkNotNullParameter(connectionListener, "connectionListener");
        Intrinsics.checkNotNullParameter(routeDatabase, "routeDatabase");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ExchangeFinder _init_$lambda$0(TaskRunner taskRunner, int i, int i2, int i3, int i4, int i5, boolean z, boolean z2, RouteDatabase routeDatabase, RealConnectionPool pool, Address address, ConnectionUser user) {
        Intrinsics.checkNotNullParameter(pool, "pool");
        Intrinsics.checkNotNullParameter(address, "address");
        Intrinsics.checkNotNullParameter(user, "user");
        return new FastFallbackExchangeFinder(new ForceConnectRoutePlanner(new RealRoutePlanner(taskRunner, pool, i, i2, i3, i4, i5, z, z2, address, routeDatabase, user)), taskRunner);
    }

    public /* synthetic */ ConnectionPool(int i, long j, TimeUnit timeUnit, ConnectionListener connectionListener, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 5 : i, (i2 & 2) != 0 ? 5L : j, (i2 & 4) != 0 ? TimeUnit.MINUTES : timeUnit, (i2 & 8) != 0 ? ConnectionListener.INSTANCE.getNONE() : connectionListener);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ConnectionPool(int i, long j, TimeUnit timeUnit, ConnectionListener connectionListener) {
        this(i, j, timeUnit, TaskRunner.INSTANCE, connectionListener, 0, 0, 0, 0, 0, false, false, null, R2.dimen.miuix_appcompat_capsule_button_bg_corner_radius, null);
        Intrinsics.checkNotNullParameter(timeUnit, "timeUnit");
        Intrinsics.checkNotNullParameter(connectionListener, "connectionListener");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ConnectionPool(int i, long j, TimeUnit timeUnit) {
        this(i, j, timeUnit, TaskRunner.INSTANCE, ConnectionListener.INSTANCE.getNONE(), 0, 0, 0, 0, 0, false, false, null, R2.dimen.miuix_appcompat_capsule_button_bg_corner_radius, null);
        Intrinsics.checkNotNullParameter(timeUnit, "timeUnit");
    }

    public ConnectionPool() {
        this(5, 5L, TimeUnit.MINUTES);
    }

    public final int idleConnectionCount() {
        return this.delegate.idleConnectionCount();
    }

    public final int connectionCount() {
        return this.delegate.connectionCount();
    }

    public final ConnectionListener getConnectionListener$okhttp() {
        return this.delegate.getConnectionListener();
    }

    public final void evictAll() {
        this.delegate.evictAll();
    }
}
