package okhttp3;

import androidx.exifinterface.media.ExifInterface;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.ToDoStat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal._UtilJvmKt;
import okhttp3.internal.connection.RealCall;

/* JADX INFO: compiled from: Dispatcher.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003B\u0013\b\u0016\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0002\u0010\u0006J\u0019\u0010\u001e\u001a\u00020\u001f2\n\u0010 \u001a\u00060\u001aR\u00020\u001bH\u0000¢\u0006\u0002\b!J\u0016\u0010\"\u001a\b\u0018\u00010\u001aR\u00020\u001b2\u0006\u0010#\u001a\u00020$H\u0002J\u0006\u0010%\u001a\u00020\u001fJ\b\u0010&\u001a\u00020'H\u0002J\u0015\u0010(\u001a\u00020'2\u0006\u0010 \u001a\u00020\u001bH\u0000¢\u0006\u0002\b)J\u0019\u0010*\u001a\u00020\u001f2\n\u0010 \u001a\u00060\u001aR\u00020\u001bH\u0000¢\u0006\u0002\b+J\u0015\u0010*\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001bH\u0000¢\u0006\u0002\b+J)\u0010*\u001a\u00020\u001f\"\u0004\b\u0000\u0010,2\f\u0010-\u001a\b\u0012\u0004\u0012\u0002H,0.2\u0006\u0010 \u001a\u0002H,H\u0002¢\u0006\u0002\u0010/J\f\u00100\u001a\b\u0012\u0004\u0012\u00020201J\f\u00103\u001a\b\u0012\u0004\u0012\u00020201J\u0006\u00104\u001a\u00020\bJ\u0006\u00105\u001a\u00020\bJ\r\u0010\u0004\u001a\u00020\u0005H\u0007¢\u0006\u0002\b6R$\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR$\u0010\r\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\b@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\n\"\u0004\b\u000f\u0010\fR\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u00058G¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0017R\u0018\u0010\u0018\u001a\f\u0012\b\u0012\u00060\u001aR\u00020\u001b0\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u001c\u001a\f\u0012\b\u0012\u00060\u001aR\u00020\u001b0\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0019X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00067"}, d2 = {"Lokhttp3/Dispatcher;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "executorService", "Ljava/util/concurrent/ExecutorService;", "(Ljava/util/concurrent/ExecutorService;)V", "maxRequests", "", "getMaxRequests", "()I", "setMaxRequests", "(I)V", "maxRequestsPerHost", "getMaxRequestsPerHost", "setMaxRequestsPerHost", "idleCallback", "Ljava/lang/Runnable;", "getIdleCallback", "()Ljava/lang/Runnable;", "setIdleCallback", "(Ljava/lang/Runnable;)V", "executorServiceOrNull", "()Ljava/util/concurrent/ExecutorService;", "readyAsyncCalls", "Ljava/util/ArrayDeque;", "Lokhttp3/internal/connection/RealCall$AsyncCall;", "Lokhttp3/internal/connection/RealCall;", "runningAsyncCalls", "runningSyncCalls", "enqueue", "", "call", "enqueue$okhttp", "findExistingCallWithHost", "host", "", "cancelAll", "promoteAndExecute", "", "executed", "executed$okhttp", ToDoStat.FINISHED, "finished$okhttp", ExifInterface.GPS_DIRECTION_TRUE, "calls", "Ljava/util/Deque;", "(Ljava/util/Deque;Ljava/lang/Object;)V", "queuedCalls", "", "Lokhttp3/Call;", "runningCalls", "queuedCallsCount", "runningCallsCount", "-deprecated_executorService", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class Dispatcher {
    private ExecutorService executorServiceOrNull;
    private Runnable idleCallback;
    private int maxRequests;
    private int maxRequestsPerHost;
    private final ArrayDeque<RealCall.AsyncCall> readyAsyncCalls;
    private final ArrayDeque<RealCall.AsyncCall> runningAsyncCalls;
    private final ArrayDeque<RealCall> runningSyncCalls;

    public Dispatcher() {
        this.maxRequests = 64;
        this.maxRequestsPerHost = 5;
        this.readyAsyncCalls = new ArrayDeque<>();
        this.runningAsyncCalls = new ArrayDeque<>();
        this.runningSyncCalls = new ArrayDeque<>();
    }

    public final synchronized int getMaxRequests() {
        return this.maxRequests;
    }

    public final void setMaxRequests(int i) {
        if (i < 1) {
            throw new IllegalArgumentException(("max < 1: " + i).toString());
        }
        synchronized (this) {
            this.maxRequests = i;
            Unit unit = Unit.INSTANCE;
        }
        promoteAndExecute();
    }

    public final synchronized int getMaxRequestsPerHost() {
        return this.maxRequestsPerHost;
    }

    public final void setMaxRequestsPerHost(int i) {
        if (i < 1) {
            throw new IllegalArgumentException(("max < 1: " + i).toString());
        }
        synchronized (this) {
            this.maxRequestsPerHost = i;
            Unit unit = Unit.INSTANCE;
        }
        promoteAndExecute();
    }

    public final synchronized Runnable getIdleCallback() {
        return this.idleCallback;
    }

    public final synchronized void setIdleCallback(Runnable runnable) {
        this.idleCallback = runnable;
    }

    public final synchronized ExecutorService executorService() {
        ExecutorService executorService;
        if (this.executorServiceOrNull == null) {
            this.executorServiceOrNull = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue(), _UtilJvmKt.threadFactory(_UtilJvmKt.okHttpName + " Dispatcher", false));
        }
        executorService = this.executorServiceOrNull;
        Intrinsics.checkNotNull(executorService);
        return executorService;
    }

    public Dispatcher(ExecutorService executorService) {
        this();
        this.executorServiceOrNull = executorService;
    }

    public final void enqueue$okhttp(RealCall.AsyncCall call) {
        RealCall.AsyncCall asyncCallFindExistingCallWithHost;
        Intrinsics.checkNotNullParameter(call, "call");
        synchronized (this) {
            this.readyAsyncCalls.add(call);
            if (!call.getThis$0().getForWebSocket() && (asyncCallFindExistingCallWithHost = findExistingCallWithHost(call.getHost())) != null) {
                call.reuseCallsPerHostFrom(asyncCallFindExistingCallWithHost);
            }
            Unit unit = Unit.INSTANCE;
        }
        promoteAndExecute();
    }

    private final RealCall.AsyncCall findExistingCallWithHost(String host) {
        Iterator<RealCall.AsyncCall> it = this.runningAsyncCalls.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            RealCall.AsyncCall next = it.next();
            if (Intrinsics.areEqual(next.getHost(), host)) {
                return next;
            }
        }
        Iterator<RealCall.AsyncCall> it2 = this.readyAsyncCalls.iterator();
        Intrinsics.checkNotNullExpressionValue(it2, "iterator(...)");
        while (it2.hasNext()) {
            RealCall.AsyncCall next2 = it2.next();
            if (Intrinsics.areEqual(next2.getHost(), host)) {
                return next2;
            }
        }
        return null;
    }

    public final synchronized void cancelAll() {
        Iterator<RealCall.AsyncCall> it = this.readyAsyncCalls.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            it.next().getThis$0().cancel();
        }
        Iterator<RealCall.AsyncCall> it2 = this.runningAsyncCalls.iterator();
        Intrinsics.checkNotNullExpressionValue(it2, "iterator(...)");
        while (it2.hasNext()) {
            it2.next().getThis$0().cancel();
        }
        Iterator<RealCall> it3 = this.runningSyncCalls.iterator();
        Intrinsics.checkNotNullExpressionValue(it3, "iterator(...)");
        while (it3.hasNext()) {
            it3.next().cancel();
        }
    }

    private final boolean promoteAndExecute() {
        int i;
        boolean z;
        _UtilJvmKt.assertLockNotHeld(this);
        ArrayList arrayList = new ArrayList();
        synchronized (this) {
            Iterator<RealCall.AsyncCall> it = this.readyAsyncCalls.iterator();
            Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
            while (it.hasNext()) {
                RealCall.AsyncCall next = it.next();
                if (this.runningAsyncCalls.size() >= this.maxRequests) {
                    break;
                }
                if (next.getCallsPerHost().get() < this.maxRequestsPerHost) {
                    it.remove();
                    next.getCallsPerHost().incrementAndGet();
                    Intrinsics.checkNotNull(next);
                    arrayList.add(next);
                    this.runningAsyncCalls.add(next);
                }
            }
            i = 0;
            z = runningCallsCount() > 0;
            Unit unit = Unit.INSTANCE;
        }
        if (executorService().isShutdown()) {
            int size = arrayList.size();
            while (i < size) {
                RealCall.AsyncCall asyncCall = (RealCall.AsyncCall) arrayList.get(i);
                asyncCall.getCallsPerHost().decrementAndGet();
                synchronized (this) {
                    this.runningAsyncCalls.remove(asyncCall);
                }
                RealCall.AsyncCall.failRejected$okhttp$default(asyncCall, null, 1, null);
                i++;
            }
            Runnable runnable = this.idleCallback;
            if (runnable != null) {
                runnable.run();
                return z;
            }
        } else {
            int size2 = arrayList.size();
            while (i < size2) {
                ((RealCall.AsyncCall) arrayList.get(i)).executeOn(executorService());
                i++;
            }
        }
        return z;
    }

    public final synchronized boolean executed$okhttp(RealCall call) {
        Intrinsics.checkNotNullParameter(call, "call");
        return this.runningSyncCalls.add(call);
    }

    public final void finished$okhttp(RealCall.AsyncCall call) {
        Intrinsics.checkNotNullParameter(call, "call");
        call.getCallsPerHost().decrementAndGet();
        finished(this.runningAsyncCalls, call);
    }

    public final void finished$okhttp(RealCall call) {
        Intrinsics.checkNotNullParameter(call, "call");
        finished(this.runningSyncCalls, call);
    }

    private final <T> void finished(Deque<T> calls, T call) {
        Runnable runnable;
        synchronized (this) {
            if (!calls.remove(call)) {
                throw new AssertionError("Call wasn't in-flight!");
            }
            runnable = this.idleCallback;
            Unit unit = Unit.INSTANCE;
        }
        if (promoteAndExecute() || runnable == null) {
            return;
        }
        runnable.run();
    }

    public final synchronized List<Call> queuedCalls() {
        List<Call> listUnmodifiableList;
        ArrayDeque<RealCall.AsyncCall> arrayDeque = this.readyAsyncCalls;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayDeque, 10));
        Iterator<T> it = arrayDeque.iterator();
        while (it.hasNext()) {
            arrayList.add(((RealCall.AsyncCall) it.next()).getThis$0());
        }
        listUnmodifiableList = Collections.unmodifiableList(arrayList);
        Intrinsics.checkNotNullExpressionValue(listUnmodifiableList, "unmodifiableList(...)");
        return listUnmodifiableList;
    }

    public final synchronized List<Call> runningCalls() {
        List<Call> listUnmodifiableList;
        ArrayDeque<RealCall> arrayDeque = this.runningSyncCalls;
        ArrayDeque<RealCall.AsyncCall> arrayDeque2 = this.runningAsyncCalls;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayDeque2, 10));
        Iterator<T> it = arrayDeque2.iterator();
        while (it.hasNext()) {
            arrayList.add(((RealCall.AsyncCall) it.next()).getThis$0());
        }
        listUnmodifiableList = Collections.unmodifiableList(CollectionsKt.plus((Collection) arrayDeque, (Iterable) arrayList));
        Intrinsics.checkNotNullExpressionValue(listUnmodifiableList, "unmodifiableList(...)");
        return listUnmodifiableList;
    }

    public final synchronized int queuedCallsCount() {
        return this.readyAsyncCalls.size();
    }

    public final synchronized int runningCallsCount() {
        return this.runningAsyncCalls.size() + this.runningSyncCalls.size();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "executorService", imports = {}))
    /* JADX INFO: renamed from: -deprecated_executorService, reason: not valid java name */
    public final ExecutorService m4408deprecated_executorService() {
        return executorService();
    }
}
