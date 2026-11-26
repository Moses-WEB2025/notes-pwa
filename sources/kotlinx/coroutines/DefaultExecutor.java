package kotlinx.coroutines;

import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: DefaultExecutor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\bÀ\u0002\u0018\u00002\u00020\u00012\u00060\u0002j\u0002`\u0003B\t\b\u0002¢\u0006\u0004\b\u0004\u0010\u0005J\u0014\u0010\u001c\u001a\u00020\u001d2\n\u0010\u001e\u001a\u00060\u0002j\u0002`\u0003H\u0016J\u0018\u0010\u001f\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020\t2\u0006\u0010!\u001a\u00020\"H\u0014J\b\u0010#\u001a\u00020\u001dH\u0002J\b\u0010$\u001a\u00020\u001dH\u0016J$\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\t2\n\u0010(\u001a\u00060\u0002j\u0002`\u00032\u0006\u0010)\u001a\u00020*H\u0016J\b\u0010+\u001a\u00020\u001dH\u0016J\b\u0010,\u001a\u00020\fH\u0002J\r\u0010-\u001a\u00020\u001dH\u0000¢\u0006\u0002\b.J\b\u0010/\u001a\u00020\u0019H\u0002J\u000e\u00100\u001a\u00020\u001d2\u0006\u00101\u001a\u00020\tJ\b\u00102\u001a\u00020\u001dH\u0002J\b\u00105\u001a\u00020\u0007H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\b\n\u0000\u0012\u0004\b\r\u0010\u0005R\u0014\u0010\u000e\u001a\u00020\f8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0012X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0012X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0012X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0012X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\u00020\u00198BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u001aR\u0014\u0010\u001b\u001a\u00020\u00198BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001aR\u0014\u00103\u001a\u00020\u00198@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b4\u0010\u001a¨\u00066"}, d2 = {"Lkotlinx/coroutines/DefaultExecutor;", "Lkotlinx/coroutines/EventLoopImplBase;", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "THREAD_NAME", "", "DEFAULT_KEEP_ALIVE_MS", "", "KEEP_ALIVE_NANOS", "_thread", "Ljava/lang/Thread;", "get_thread$annotations", "thread", "getThread", "()Ljava/lang/Thread;", "FRESH", "", "ACTIVE", "SHUTDOWN_REQ", "SHUTDOWN_ACK", "SHUTDOWN", "debugStatus", "isShutDown", "", "()Z", "isShutdownRequested", "enqueue", "", "task", "reschedule", "now", "delayedTask", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "shutdownError", "shutdown", "invokeOnTimeout", "Lkotlinx/coroutines/DisposableHandle;", "timeMillis", "block", CoreConstants.CONTEXT_SCOPE_VALUE, "Lkotlin/coroutines/CoroutineContext;", "run", "createThreadSync", "ensureStarted", "ensureStarted$kotlinx_coroutines_core", "notifyStartup", "shutdownForTests", "timeout", "acknowledgeShutdownIfNeeded", "isThreadPresent", "isThreadPresent$kotlinx_coroutines_core", "toString", "kotlinx-coroutines-core"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class DefaultExecutor extends EventLoopImplBase implements Runnable {
    private static final int ACTIVE = 1;
    private static final long DEFAULT_KEEP_ALIVE_MS = 1000;
    private static final int FRESH = 0;
    public static final DefaultExecutor INSTANCE;
    private static final long KEEP_ALIVE_NANOS;
    private static final int SHUTDOWN = 4;
    private static final int SHUTDOWN_ACK = 3;
    private static final int SHUTDOWN_REQ = 2;
    public static final String THREAD_NAME = "kotlinx.coroutines.DefaultExecutor";
    private static volatile Thread _thread;
    private static volatile int debugStatus;

    private static /* synthetic */ void get_thread$annotations() {
    }

    private DefaultExecutor() {
    }

    static {
        Long l;
        DefaultExecutor defaultExecutor = new DefaultExecutor();
        INSTANCE = defaultExecutor;
        EventLoop.incrementUseCount$default(defaultExecutor, false, 1, null);
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        try {
            l = Long.getLong("kotlinx.coroutines.DefaultExecutor.keepAlive", 1000L);
        } catch (SecurityException unused) {
            l = 1000L;
        }
        KEEP_ALIVE_NANOS = timeUnit.toNanos(l.longValue());
    }

    @Override // kotlinx.coroutines.EventLoopImplPlatform
    protected Thread getThread() {
        Thread thread = _thread;
        return thread == null ? createThreadSync() : thread;
    }

    private final boolean isShutDown() {
        return debugStatus == 4;
    }

    private final boolean isShutdownRequested() {
        int i = debugStatus;
        return i == 2 || i == 3;
    }

    @Override // kotlinx.coroutines.EventLoopImplBase
    public void enqueue(Runnable task) {
        if (isShutDown()) {
            shutdownError();
        }
        super.enqueue(task);
    }

    @Override // kotlinx.coroutines.EventLoopImplPlatform
    protected void reschedule(long now, EventLoopImplBase.DelayedTask delayedTask) {
        shutdownError();
    }

    private final void shutdownError() {
        throw new RejectedExecutionException("DefaultExecutor was shut down. This error indicates that Dispatchers.shutdown() was invoked prior to completion of exiting coroutines, leaving coroutines in incomplete state. Please refer to Dispatchers.shutdown documentation for more details");
    }

    @Override // kotlinx.coroutines.EventLoopImplBase, kotlinx.coroutines.EventLoop
    public void shutdown() {
        debugStatus = 4;
        super.shutdown();
    }

    @Override // kotlinx.coroutines.EventLoopImplBase, kotlinx.coroutines.Delay
    public DisposableHandle invokeOnTimeout(long timeMillis, Runnable block, CoroutineContext context) {
        return scheduleInvokeOnTimeout(timeMillis, block);
    }

    @Override // java.lang.Runnable
    public void run() {
        boolean zIsEmpty;
        ThreadLocalEventLoop.INSTANCE.setEventLoop$kotlinx_coroutines_core(this);
        AbstractTimeSource abstractTimeSource = AbstractTimeSourceKt.timeSource;
        if (abstractTimeSource != null) {
            abstractTimeSource.registerTimeLoopThread();
        }
        try {
            if (!notifyStartup()) {
                if (zIsEmpty) {
                    return;
                } else {
                    return;
                }
            }
            long j = Long.MAX_VALUE;
            while (true) {
                Thread.interrupted();
                long jProcessNextEvent = processNextEvent();
                if (jProcessNextEvent == Long.MAX_VALUE) {
                    AbstractTimeSource abstractTimeSource2 = AbstractTimeSourceKt.timeSource;
                    long jNanoTime = abstractTimeSource2 != null ? abstractTimeSource2.nanoTime() : System.nanoTime();
                    if (j == Long.MAX_VALUE) {
                        j = KEEP_ALIVE_NANOS + jNanoTime;
                    }
                    long j2 = j - jNanoTime;
                    if (j2 <= 0) {
                        if (zIsEmpty) {
                            return;
                        } else {
                            return;
                        }
                    }
                    jProcessNextEvent = RangesKt.coerceAtMost(jProcessNextEvent, j2);
                } else {
                    j = Long.MAX_VALUE;
                }
                if (jProcessNextEvent > 0) {
                    if (isShutdownRequested()) {
                        if (zIsEmpty) {
                            return;
                        } else {
                            return;
                        }
                    }
                    AbstractTimeSource abstractTimeSource3 = AbstractTimeSourceKt.timeSource;
                    if (abstractTimeSource3 != null) {
                        abstractTimeSource3.parkNanos(this, jProcessNextEvent);
                    } else {
                        LockSupport.parkNanos(this, jProcessNextEvent);
                    }
                }
            }
        } finally {
            _thread = null;
            acknowledgeShutdownIfNeeded();
            AbstractTimeSource abstractTimeSource4 = AbstractTimeSourceKt.timeSource;
            if (abstractTimeSource4 != null) {
                abstractTimeSource4.unregisterTimeLoopThread();
            }
            if (!isEmpty()) {
                getThread();
            }
        }
    }

    private final synchronized Thread createThreadSync() {
        Thread thread;
        thread = _thread;
        if (thread == null) {
            thread = new Thread(this, THREAD_NAME);
            DefaultExecutor defaultExecutor = INSTANCE;
            _thread = thread;
            thread.setContextClassLoader(defaultExecutor.getClass().getClassLoader());
            thread.setDaemon(true);
            thread.start();
        }
        return thread;
    }

    public final synchronized void ensureStarted$kotlinx_coroutines_core() {
        if (DebugKt.getASSERTIONS_ENABLED() && _thread != null) {
            throw new AssertionError();
        }
        if (DebugKt.getASSERTIONS_ENABLED() && debugStatus != 0 && debugStatus != 3) {
            throw new AssertionError();
        }
        debugStatus = 0;
        createThreadSync();
        while (debugStatus == 0) {
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type java.lang.Object");
            wait();
        }
    }

    private final synchronized boolean notifyStartup() {
        if (isShutdownRequested()) {
            return false;
        }
        debugStatus = 1;
        Intrinsics.checkNotNull(this, "null cannot be cast to non-null type java.lang.Object");
        notifyAll();
        return true;
    }

    public final synchronized void shutdownForTests(long timeout) {
        long jCurrentTimeMillis = System.currentTimeMillis() + timeout;
        if (!isShutdownRequested()) {
            debugStatus = 2;
        }
        while (debugStatus != 3 && _thread != null) {
            Thread thread = _thread;
            if (thread != null) {
                AbstractTimeSource abstractTimeSource = AbstractTimeSourceKt.timeSource;
                if (abstractTimeSource != null) {
                    abstractTimeSource.unpark(thread);
                } else {
                    LockSupport.unpark(thread);
                }
            }
            if (jCurrentTimeMillis - System.currentTimeMillis() <= 0) {
                break;
            }
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type java.lang.Object");
            wait(timeout);
        }
        debugStatus = 0;
    }

    private final synchronized void acknowledgeShutdownIfNeeded() {
        if (isShutdownRequested()) {
            debugStatus = 3;
            resetAll();
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type java.lang.Object");
            notifyAll();
        }
    }

    public final boolean isThreadPresent$kotlinx_coroutines_core() {
        return _thread != null;
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    /* JADX INFO: renamed from: toString */
    public String getName() {
        return "DefaultExecutor";
    }
}
