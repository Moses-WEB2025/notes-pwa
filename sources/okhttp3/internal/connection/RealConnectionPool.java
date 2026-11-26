package okhttp3.internal.connection;

import androidx.concurrent.futures.AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import com.xiaomi.mirror.synergy.CallMethod;
import java.io.IOException;
import java.lang.ref.Reference;
import java.net.Socket;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Address;
import okhttp3.ConnectionPool;
import okhttp3.Route;
import okhttp3.internal._UtilJvmKt;
import okhttp3.internal.concurrent.Task;
import okhttp3.internal.concurrent.TaskQueue;
import okhttp3.internal.concurrent.TaskRunner;
import okhttp3.internal.platform.Platform;

/* JADX INFO: compiled from: RealConnectionPool.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0081\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\b*\u0001\u001e\u0018\u0000 A2\u00020\u0001:\u0002@ABQ\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u001e\u0010\f\u001a\u001a\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\r¢\u0006\u0004\b\u0011\u0010\u0012J\f\u0010 \u001a\u00020!*\u00020\u001aH\u0002J\u0006\u0010%\u001a\u00020\u0005J\u0006\u0010&\u001a\u00020\u0005J8\u0010'\u001a\u0004\u0018\u00010$2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u000e2\u0006\u0010+\u001a\u00020\u000f2\u000e\u0010,\u001a\n\u0012\u0004\u0012\u00020.\u0018\u00010-2\u0006\u0010/\u001a\u00020)J\u000e\u00100\u001a\u00020!2\u0006\u00101\u001a\u00020$J\u000e\u00102\u001a\u00020)2\u0006\u00101\u001a\u00020$J\u0006\u00103\u001a\u00020!J\u000e\u00104\u001a\u00020\u00072\u0006\u00105\u001a\u00020\u0007J$\u00106\u001a\u00020)2\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u001a0\u00192\u0006\u00101\u001a\u00020$H\u0002J\u0018\u00107\u001a\u00020\u00052\u0006\u00101\u001a\u00020$2\u0006\u00105\u001a\u00020\u0007H\u0002J\u0016\u00108\u001a\u00020!2\u0006\u0010*\u001a\u00020\u000e2\u0006\u00109\u001a\u00020:J\u000e\u0010 \u001a\u00020!2\u0006\u0010*\u001a\u00020\u000eJ\u0006\u0010;\u001a\u00020!J\u0010\u0010<\u001a\u00020\u00072\u0006\u0010=\u001a\u00020\u001aH\u0002J\u0014\u0010>\u001a\u00020\u0007*\u00020\u00072\u0006\u0010?\u001a\u00020\u0005H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00020\u000bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R&\u0010\f\u001a\u001a\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\u00020\u0007X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u001a0\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u001fR\u0014\u0010\"\u001a\b\u0012\u0004\u0012\u00020$0#X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006B"}, d2 = {"Lokhttp3/internal/connection/RealConnectionPool;", "", "taskRunner", "Lokhttp3/internal/concurrent/TaskRunner;", "maxIdleConnections", "", "keepAliveDuration", "", "timeUnit", "Ljava/util/concurrent/TimeUnit;", "connectionListener", "Lokhttp3/internal/connection/ConnectionListener;", "exchangeFinderFactory", "Lkotlin/Function3;", "Lokhttp3/Address;", "Lokhttp3/internal/connection/ConnectionUser;", "Lokhttp3/internal/connection/ExchangeFinder;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokhttp3/internal/concurrent/TaskRunner;IJLjava/util/concurrent/TimeUnit;Lokhttp3/internal/connection/ConnectionListener;Lkotlin/jvm/functions/Function3;)V", "getConnectionListener$okhttp", "()Lokhttp3/internal/connection/ConnectionListener;", "keepAliveDurationNs", "getKeepAliveDurationNs$okhttp", "()J", "addressStates", "", "Lokhttp3/internal/connection/RealConnectionPool$AddressState;", "cleanupQueue", "Lokhttp3/internal/concurrent/TaskQueue;", "cleanupTask", "okhttp3/internal/connection/RealConnectionPool$cleanupTask$1", "Lokhttp3/internal/connection/RealConnectionPool$cleanupTask$1;", "scheduleOpener", "", "connections", "Ljava/util/concurrent/ConcurrentLinkedQueue;", "Lokhttp3/internal/connection/RealConnection;", "idleConnectionCount", "connectionCount", "callAcquirePooledConnection", "doExtensiveHealthChecks", "", RemoteDeviceInfo.KEY_ADDRESS, "connectionUser", "routes", "", "Lokhttp3/Route;", "requireMultiplexed", "put", "connection", "connectionBecameIdle", "evictAll", "closeConnections", "now", "isEvictable", "pruneAndGetAllocationCount", "setPolicy", "policy", "Lokhttp3/internal/connection/AddressPolicy;", "scheduleCloser", "openConnections", "state", "jitterBy", "amount", "AddressState", "Companion", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class RealConnectionPool {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static AtomicReferenceFieldUpdater<RealConnectionPool, Map<?, ?>> addressStatesUpdater = AtomicReferenceFieldUpdater.newUpdater(RealConnectionPool.class, Map.class, "addressStates");
    private volatile Map<Address, AddressState> addressStates;
    private final TaskQueue cleanupQueue;
    private final RealConnectionPool$cleanupTask$1 cleanupTask;
    private final ConnectionListener connectionListener;
    private final ConcurrentLinkedQueue<RealConnection> connections;
    private final Function3<RealConnectionPool, Address, ConnectionUser, ExchangeFinder> exchangeFinderFactory;
    private final long keepAliveDurationNs;
    private final int maxIdleConnections;
    private final TaskRunner taskRunner;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v4, types: [okhttp3.internal.connection.RealConnectionPool$cleanupTask$1] */
    public RealConnectionPool(TaskRunner taskRunner, int i, long j, TimeUnit timeUnit, ConnectionListener connectionListener, Function3<? super RealConnectionPool, ? super Address, ? super ConnectionUser, ? extends ExchangeFinder> exchangeFinderFactory) {
        Intrinsics.checkNotNullParameter(taskRunner, "taskRunner");
        Intrinsics.checkNotNullParameter(timeUnit, "timeUnit");
        Intrinsics.checkNotNullParameter(connectionListener, "connectionListener");
        Intrinsics.checkNotNullParameter(exchangeFinderFactory, "exchangeFinderFactory");
        this.taskRunner = taskRunner;
        this.maxIdleConnections = i;
        this.connectionListener = connectionListener;
        this.exchangeFinderFactory = exchangeFinderFactory;
        this.keepAliveDurationNs = timeUnit.toNanos(j);
        this.addressStates = MapsKt.emptyMap();
        this.cleanupQueue = taskRunner.newQueue();
        final String str = _UtilJvmKt.okHttpName + " ConnectionPool connection closer";
        this.cleanupTask = new Task(str) { // from class: okhttp3.internal.connection.RealConnectionPool$cleanupTask$1
            @Override // okhttp3.internal.concurrent.Task
            public long runOnce() {
                return this.this$0.closeConnections(System.nanoTime());
            }
        };
        this.connections = new ConcurrentLinkedQueue<>();
        if (j <= 0) {
            throw new IllegalArgumentException(("keepAliveDuration <= 0: " + j).toString());
        }
    }

    /* JADX INFO: renamed from: getConnectionListener$okhttp, reason: from getter */
    public final ConnectionListener getConnectionListener() {
        return this.connectionListener;
    }

    /* JADX INFO: renamed from: getKeepAliveDurationNs$okhttp, reason: from getter */
    public final long getKeepAliveDurationNs() {
        return this.keepAliveDurationNs;
    }

    private final void scheduleOpener(final AddressState addressState) {
        TaskQueue.schedule$default(addressState.getQueue(), new Task(_UtilJvmKt.okHttpName + " ConnectionPool connection opener") { // from class: okhttp3.internal.connection.RealConnectionPool.scheduleOpener.1
            @Override // okhttp3.internal.concurrent.Task
            public long runOnce() {
                return RealConnectionPool.this.openConnections(addressState);
            }
        }, 0L, 2, null);
    }

    public final int idleConnectionCount() {
        boolean zIsEmpty;
        ConcurrentLinkedQueue<RealConnection> concurrentLinkedQueue = this.connections;
        int i = 0;
        if ((concurrentLinkedQueue instanceof Collection) && concurrentLinkedQueue.isEmpty()) {
            return 0;
        }
        for (RealConnection realConnection : concurrentLinkedQueue) {
            Intrinsics.checkNotNull(realConnection);
            synchronized (realConnection) {
                zIsEmpty = realConnection.getCalls().isEmpty();
            }
            if (zIsEmpty && (i = i + 1) < 0) {
                CollectionsKt.throwCountOverflow();
            }
        }
        return i;
    }

    public final int connectionCount() {
        return this.connections.size();
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0033 A[Catch: all -> 0x006f, TryCatch #1 {, blocks: (B:9:0x002c, B:12:0x0033, B:15:0x003a), top: B:39:0x002c }] */
    /* JADX WARN: Code duplicated, block: B:14:0x0039  */
    /* JADX WARN: Code duplicated, block: B:15:0x003a A[Catch: all -> 0x006f, TRY_LEAVE, TryCatch #1 {, blocks: (B:9:0x002c, B:12:0x0033, B:15:0x003a), top: B:39:0x002c }] */
    public final RealConnection callAcquirePooledConnection(boolean doExtensiveHealthChecks, Address address, ConnectionUser connectionUser, List<Route> routes, boolean requireMultiplexed) {
        boolean z;
        boolean noNewExchanges;
        Socket socketReleaseConnectionNoEvents;
        Intrinsics.checkNotNullParameter(address, "address");
        Intrinsics.checkNotNullParameter(connectionUser, "connectionUser");
        Iterator<RealConnection> it = this.connections.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            RealConnection next = it.next();
            Intrinsics.checkNotNull(next);
            RealConnection realConnection = next;
            synchronized (realConnection) {
                z = false;
                if (requireMultiplexed) {
                    if (next.isMultiplexed$okhttp()) {
                        if (!next.isEligible$okhttp(address, routes)) {
                            connectionUser.acquireConnectionNoEvents(next);
                            z = true;
                        }
                    }
                } else if (!next.isEligible$okhttp(address, routes)) {
                    connectionUser.acquireConnectionNoEvents(next);
                    z = true;
                }
            }
            if (z) {
                if (next.isHealthy(doExtensiveHealthChecks)) {
                    return next;
                }
                synchronized (realConnection) {
                    noNewExchanges = next.getNoNewExchanges();
                    next.setNoNewExchanges(true);
                    socketReleaseConnectionNoEvents = connectionUser.releaseConnectionNoEvents();
                }
                if (socketReleaseConnectionNoEvents != null) {
                    _UtilJvmKt.closeQuietly(socketReleaseConnectionNoEvents);
                    this.connectionListener.connectionClosed(next);
                } else if (!noNewExchanges) {
                    this.connectionListener.noNewExchanges(next);
                }
            }
        }
        return null;
    }

    public final void put(RealConnection connection) {
        Intrinsics.checkNotNullParameter(connection, "connection");
        RealConnection realConnection = connection;
        if (!_UtilJvmKt.assertionsEnabled || Thread.holdsLock(realConnection)) {
            this.connections.add(connection);
            scheduleCloser();
            return;
        }
        throw new AssertionError("Thread " + Thread.currentThread().getName() + " MUST hold lock on " + realConnection);
    }

    public final boolean connectionBecameIdle(RealConnection connection) {
        Intrinsics.checkNotNullParameter(connection, "connection");
        RealConnection realConnection = connection;
        if (!_UtilJvmKt.assertionsEnabled || Thread.holdsLock(realConnection)) {
            if (connection.getNoNewExchanges() || this.maxIdleConnections == 0) {
                connection.setNoNewExchanges(true);
                this.connections.remove(connection);
                if (this.connections.isEmpty()) {
                    this.cleanupQueue.cancelAll();
                }
                scheduleOpener(connection.getRoute().address());
                return true;
            }
            scheduleCloser();
            return false;
        }
        throw new AssertionError("Thread " + Thread.currentThread().getName() + " MUST hold lock on " + realConnection);
    }

    public final void evictAll() {
        Socket socket;
        Iterator<RealConnection> it = this.connections.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            RealConnection next = it.next();
            Intrinsics.checkNotNull(next);
            synchronized (next) {
                if (next.getCalls().isEmpty()) {
                    it.remove();
                    next.setNoNewExchanges(true);
                    socket = next.getSocket();
                } else {
                    socket = null;
                }
            }
            if (socket != null) {
                _UtilJvmKt.closeQuietly(socket);
                this.connectionListener.connectionClosed(next);
            }
        }
        if (this.connections.isEmpty()) {
            this.cleanupQueue.cancelAll();
        }
        Iterator<AddressState> it2 = this.addressStates.values().iterator();
        while (it2.hasNext()) {
            scheduleOpener(it2.next());
        }
    }

    public final long closeConnections(long now) {
        int i;
        Map<Address, AddressState> map = this.addressStates;
        Iterator<AddressState> it = map.values().iterator();
        while (true) {
            i = 0;
            if (!it.hasNext()) {
                break;
            }
            it.next().setConcurrentCallCapacity(0);
        }
        Iterator<RealConnection> it2 = this.connections.iterator();
        Intrinsics.checkNotNullExpressionValue(it2, "iterator(...)");
        while (it2.hasNext()) {
            RealConnection next = it2.next();
            AddressState addressState = map.get(next.getRoute().address());
            if (addressState != null) {
                Intrinsics.checkNotNull(next);
                synchronized (next) {
                    addressState.setConcurrentCallCapacity(addressState.getConcurrentCallCapacity() + next.getAllocationLimit());
                    Unit unit = Unit.INSTANCE;
                }
            }
        }
        long j = (now - this.keepAliveDurationNs) + 1;
        Iterator<RealConnection> it3 = this.connections.iterator();
        Intrinsics.checkNotNullExpressionValue(it3, "iterator(...)");
        RealConnection realConnection = null;
        RealConnection realConnection2 = null;
        RealConnection realConnection3 = null;
        long j2 = Long.MAX_VALUE;
        int i2 = 0;
        while (it3.hasNext()) {
            RealConnection next2 = it3.next();
            Intrinsics.checkNotNull(next2);
            synchronized (next2) {
                if (pruneAndGetAllocationCount(next2, now) > 0) {
                    i2++;
                } else {
                    long idleAtNs = next2.getIdleAtNs();
                    if (idleAtNs < j) {
                        realConnection2 = next2;
                        j = idleAtNs;
                    }
                    if (isEvictable(map, next2)) {
                        i++;
                        if (idleAtNs < j2) {
                            realConnection3 = next2;
                            j2 = idleAtNs;
                        }
                    }
                }
                Unit unit2 = Unit.INSTANCE;
            }
        }
        if (realConnection2 != null) {
            realConnection = realConnection2;
        } else if (i > this.maxIdleConnections) {
            j = j2;
            realConnection = realConnection3;
        } else {
            j = -1;
        }
        if (realConnection == null) {
            if (realConnection3 != null) {
                return (j2 + this.keepAliveDurationNs) - now;
            }
            if (i2 > 0) {
                return this.keepAliveDurationNs;
            }
            return -1L;
        }
        synchronized (realConnection) {
            if (!realConnection.getCalls().isEmpty()) {
                return 0L;
            }
            if (realConnection.getIdleAtNs() != j) {
                return 0L;
            }
            realConnection.setNoNewExchanges(true);
            this.connections.remove(realConnection);
            AddressState addressState2 = map.get(realConnection.getRoute().address());
            if (addressState2 != null) {
                scheduleOpener(addressState2);
            }
            _UtilJvmKt.closeQuietly(realConnection.getSocket());
            this.connectionListener.connectionClosed(realConnection);
            if (this.connections.isEmpty()) {
                this.cleanupQueue.cancelAll();
            }
            return 0L;
        }
    }

    private final boolean isEvictable(Map<Address, AddressState> addressStates, RealConnection connection) {
        AddressState addressState = addressStates.get(connection.getRoute().address());
        return addressState == null || addressState.getConcurrentCallCapacity() - connection.getAllocationLimit() >= addressState.getPolicy().minimumConcurrentCalls;
    }

    private final int pruneAndGetAllocationCount(RealConnection connection, long now) {
        RealConnection realConnection = connection;
        if (!_UtilJvmKt.assertionsEnabled || Thread.holdsLock(realConnection)) {
            List<Reference<RealCall>> calls = connection.getCalls();
            int i = 0;
            while (i < calls.size()) {
                Reference<RealCall> reference = calls.get(i);
                if (reference.get() != null) {
                    i++;
                } else {
                    Intrinsics.checkNotNull(reference, "null cannot be cast to non-null type okhttp3.internal.connection.RealCall.CallReference");
                    Platform.INSTANCE.get().logCloseableLeak("A connection to " + connection.route().address().url() + " was leaked. Did you forget to close a response body?", ((RealCall.CallReference) reference).getCallStackTrace());
                    calls.remove(i);
                    if (calls.isEmpty()) {
                        connection.setIdleAtNs(now - this.keepAliveDurationNs);
                        return 0;
                    }
                }
            }
            return calls.size();
        }
        throw new AssertionError("Thread " + Thread.currentThread().getName() + " MUST hold lock on " + realConnection);
    }

    public final void setPolicy(Address address, AddressPolicy policy) {
        Map<Address, AddressState> map;
        AddressPolicy policy2;
        Intrinsics.checkNotNullParameter(address, "address");
        Intrinsics.checkNotNullParameter(policy, "policy");
        AddressState addressState = new AddressState(address, this.taskRunner.newQueue(), policy);
        do {
            map = this.addressStates;
        } while (!AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(addressStatesUpdater, this, map, MapsKt.plus(map, TuplesKt.to(address, addressState))));
        AddressState addressState2 = map.get(address);
        int i = policy.minimumConcurrentCalls - ((addressState2 == null || (policy2 = addressState2.getPolicy()) == null) ? 0 : policy2.minimumConcurrentCalls);
        if (i > 0) {
            scheduleOpener(addressState);
        } else if (i < 0) {
            scheduleCloser();
        }
    }

    public final void scheduleOpener(Address address) {
        Intrinsics.checkNotNullParameter(address, "address");
        AddressState addressState = this.addressStates.get(address);
        if (addressState != null) {
            scheduleOpener(addressState);
        }
    }

    public final void scheduleCloser() {
        TaskQueue.schedule$default(this.cleanupQueue, this.cleanupTask, 0L, 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final long openConnections(AddressState state) {
        if (state.getPolicy().minimumConcurrentCalls == 0) {
            return -1L;
        }
        Iterator<RealConnection> it = this.connections.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        int allocationLimit = 0;
        while (it.hasNext()) {
            RealConnection next = it.next();
            if (Intrinsics.areEqual(state.getAddress(), next.getRoute().address())) {
                Intrinsics.checkNotNull(next);
                synchronized (next) {
                    allocationLimit += next.getAllocationLimit();
                    Unit unit = Unit.INSTANCE;
                }
                if (allocationLimit >= state.getPolicy().minimumConcurrentCalls) {
                    return -1L;
                }
            }
        }
        try {
            RealConnection realConnectionFind = this.exchangeFinderFactory.invoke(this, state.getAddress(), PoolConnectionUser.INSTANCE).find();
            if (this.connections.contains(realConnectionFind)) {
                return 0L;
            }
            synchronized (realConnectionFind) {
                put(realConnectionFind);
                Unit unit2 = Unit.INSTANCE;
            }
            return 0L;
        } catch (IOException unused) {
            return jitterBy(state.getPolicy().backoffDelayMillis, state.getPolicy().backoffJitterMillis) * ((long) 1000000);
        }
    }

    private final long jitterBy(long j, int i) {
        return j + ((long) ThreadLocalRandom.current().nextInt(i * (-1), i));
    }

    /* JADX INFO: compiled from: RealConnectionPool.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017¨\u0006\u0018"}, d2 = {"Lokhttp3/internal/connection/RealConnectionPool$AddressState;", "", RemoteDeviceInfo.KEY_ADDRESS, "Lokhttp3/Address;", "queue", "Lokhttp3/internal/concurrent/TaskQueue;", "policy", "Lokhttp3/internal/connection/AddressPolicy;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokhttp3/Address;Lokhttp3/internal/concurrent/TaskQueue;Lokhttp3/internal/connection/AddressPolicy;)V", "getAddress", "()Lokhttp3/Address;", "getQueue", "()Lokhttp3/internal/concurrent/TaskQueue;", "getPolicy", "()Lokhttp3/internal/connection/AddressPolicy;", "setPolicy", "(Lokhttp3/internal/connection/AddressPolicy;)V", "concurrentCallCapacity", "", "getConcurrentCallCapacity", "()I", "setConcurrentCallCapacity", "(I)V", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class AddressState {
        private final Address address;
        private int concurrentCallCapacity;
        private AddressPolicy policy;
        private final TaskQueue queue;

        public AddressState(Address address, TaskQueue queue, AddressPolicy policy) {
            Intrinsics.checkNotNullParameter(address, "address");
            Intrinsics.checkNotNullParameter(queue, "queue");
            Intrinsics.checkNotNullParameter(policy, "policy");
            this.address = address;
            this.queue = queue;
            this.policy = policy;
        }

        public final Address getAddress() {
            return this.address;
        }

        public final TaskQueue getQueue() {
            return this.queue;
        }

        public final AddressPolicy getPolicy() {
            return this.policy;
        }

        public final void setPolicy(AddressPolicy addressPolicy) {
            Intrinsics.checkNotNullParameter(addressPolicy, "<set-?>");
            this.policy = addressPolicy;
        }

        public final int getConcurrentCallCapacity() {
            return this.concurrentCallCapacity;
        }

        public final void setConcurrentCallCapacity(int i) {
            this.concurrentCallCapacity = i;
        }
    }

    /* JADX INFO: compiled from: RealConnectionPool.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007Rn\u0010\b\u001ab\u0012\f\u0012\n \n*\u0004\u0018\u00010\u00050\u0005\u0012\u001c\u0012\u001a\u0012\u0002\b\u0003\u0012\u0002\b\u0003 \n*\f\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0018\u00010\u000b0\u000b \n*0\u0012\f\u0012\n \n*\u0004\u0018\u00010\u00050\u0005\u0012\u001c\u0012\u001a\u0012\u0002\b\u0003\u0012\u0002\b\u0003 \n*\f\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0018\u00010\u000b0\u000b\u0018\u00010\t0\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lokhttp3/internal/connection/RealConnectionPool$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", CallMethod.METHOD_GET, "Lokhttp3/internal/connection/RealConnectionPool;", "connectionPool", "Lokhttp3/ConnectionPool;", "addressStatesUpdater", "Ljava/util/concurrent/atomic/AtomicReferenceFieldUpdater;", "kotlin.jvm.PlatformType", "", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final RealConnectionPool get(ConnectionPool connectionPool) {
            Intrinsics.checkNotNullParameter(connectionPool, "connectionPool");
            return connectionPool.getDelegate();
        }
    }
}
