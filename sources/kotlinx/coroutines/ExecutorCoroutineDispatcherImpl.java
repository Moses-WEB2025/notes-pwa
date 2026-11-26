package kotlinx.coroutines;

import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.internal.ConcurrentKt;
import miuix.navigation.SplitLayout;

/* JADX INFO: compiled from: Executors.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0004\b\u0005\u0010\u0006J\u001c\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\n\u0010\r\u001a\u00060\u000ej\u0002`\u000fH\u0016J\u001e\u0010\u0010\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\n0\u0014H\u0016J$\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0011\u001a\u00020\u00122\n\u0010\r\u001a\u00060\u000ej\u0002`\u000f2\u0006\u0010\u000b\u001a\u00020\fH\u0016J.\u0010\u0017\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0018*\u00020\u00192\n\u0010\r\u001a\u00060\u000ej\u0002`\u000f2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0018\u0010\u001a\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\b\u0010\u001d\u001a\u00020\nH\u0016J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\u0013\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010#H\u0096\u0002J\b\u0010$\u001a\u00020%H\u0016R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006&"}, d2 = {"Lkotlinx/coroutines/ExecutorCoroutineDispatcherImpl;", "Lkotlinx/coroutines/ExecutorCoroutineDispatcher;", "Lkotlinx/coroutines/Delay;", "executor", "Ljava/util/concurrent/Executor;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/util/concurrent/Executor;)V", "getExecutor", "()Ljava/util/concurrent/Executor;", "dispatch", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Lkotlin/coroutines/CoroutineContext;", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "scheduleResumeAfterDelay", "timeMillis", "", "continuation", "Lkotlinx/coroutines/CancellableContinuation;", "invokeOnTimeout", "Lkotlinx/coroutines/DisposableHandle;", "scheduleBlock", "Ljava/util/concurrent/ScheduledFuture;", "Ljava/util/concurrent/ScheduledExecutorService;", "cancelJobOnRejection", "exception", "Ljava/util/concurrent/RejectedExecutionException;", SplitLayout.TAG_CLOSE, "toString", "", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "kotlinx-coroutines-core"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class ExecutorCoroutineDispatcherImpl extends ExecutorCoroutineDispatcher implements Delay {
    private final Executor executor;

    public ExecutorCoroutineDispatcherImpl(Executor executor) {
        this.executor = executor;
        ConcurrentKt.removeFutureOnCancel(getExecutor());
    }

    @Override // kotlinx.coroutines.Delay
    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated without replacement as an internal method never intended for public use")
    public Object delay(long j, Continuation<? super Unit> continuation) {
        return Delay.DefaultImpls.delay(this, j, continuation);
    }

    @Override // kotlinx.coroutines.ExecutorCoroutineDispatcher
    public Executor getExecutor() {
        return this.executor;
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    /* JADX INFO: renamed from: dispatch */
    public void mo4121dispatch(CoroutineContext context, Runnable block) {
        Runnable runnableWrapTask;
        try {
            Executor executor = getExecutor();
            AbstractTimeSource abstractTimeSource = AbstractTimeSourceKt.timeSource;
            if (abstractTimeSource == null || (runnableWrapTask = abstractTimeSource.wrapTask(block)) == null) {
                runnableWrapTask = block;
            }
            executor.execute(runnableWrapTask);
        } catch (RejectedExecutionException e) {
            AbstractTimeSource abstractTimeSource2 = AbstractTimeSourceKt.timeSource;
            if (abstractTimeSource2 != null) {
                abstractTimeSource2.unTrackTask();
            }
            cancelJobOnRejection(context, e);
            Dispatchers.getIO().mo4121dispatch(context, block);
        }
    }

    @Override // kotlinx.coroutines.Delay
    /* JADX INFO: renamed from: scheduleResumeAfterDelay */
    public void mo4122scheduleResumeAfterDelay(long timeMillis, CancellableContinuation<? super Unit> continuation) {
        long j;
        Executor executor = getExecutor();
        ScheduledFuture<?> scheduledFutureScheduleBlock = null;
        ScheduledExecutorService scheduledExecutorService = executor instanceof ScheduledExecutorService ? (ScheduledExecutorService) executor : null;
        if (scheduledExecutorService != null) {
            j = timeMillis;
            scheduledFutureScheduleBlock = scheduleBlock(scheduledExecutorService, new ResumeUndispatchedRunnable(this, continuation), continuation.get$context(), j);
        } else {
            j = timeMillis;
        }
        if (scheduledFutureScheduleBlock != null) {
            CancellableContinuationKt.invokeOnCancellation(continuation, new CancelFutureOnCancel(scheduledFutureScheduleBlock));
        } else {
            DefaultExecutor.INSTANCE.mo4122scheduleResumeAfterDelay(j, continuation);
        }
    }

    @Override // kotlinx.coroutines.Delay
    public DisposableHandle invokeOnTimeout(long timeMillis, Runnable block, CoroutineContext context) {
        long j;
        Runnable runnable;
        CoroutineContext coroutineContext;
        Executor executor = getExecutor();
        ScheduledFuture<?> scheduledFutureScheduleBlock = null;
        ScheduledExecutorService scheduledExecutorService = executor instanceof ScheduledExecutorService ? (ScheduledExecutorService) executor : null;
        if (scheduledExecutorService != null) {
            j = timeMillis;
            runnable = block;
            coroutineContext = context;
            scheduledFutureScheduleBlock = scheduleBlock(scheduledExecutorService, runnable, coroutineContext, j);
        } else {
            j = timeMillis;
            runnable = block;
            coroutineContext = context;
        }
        if (scheduledFutureScheduleBlock != null) {
            return new DisposableFutureHandle(scheduledFutureScheduleBlock);
        }
        return DefaultExecutor.INSTANCE.invokeOnTimeout(j, runnable, coroutineContext);
    }

    private final ScheduledFuture<?> scheduleBlock(ScheduledExecutorService scheduledExecutorService, Runnable runnable, CoroutineContext coroutineContext, long j) {
        try {
            return scheduledExecutorService.schedule(runnable, j, TimeUnit.MILLISECONDS);
        } catch (RejectedExecutionException e) {
            cancelJobOnRejection(coroutineContext, e);
            return null;
        }
    }

    private final void cancelJobOnRejection(CoroutineContext context, RejectedExecutionException exception) {
        JobKt.cancel(context, ExceptionsKt.CancellationException("The task was rejected", exception));
    }

    @Override // kotlinx.coroutines.ExecutorCoroutineDispatcher, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        Executor executor = getExecutor();
        ExecutorService executorService = executor instanceof ExecutorService ? (ExecutorService) executor : null;
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    /* JADX INFO: renamed from: toString */
    public String getName() {
        return getExecutor().toString();
    }

    public boolean equals(Object other) {
        return (other instanceof ExecutorCoroutineDispatcherImpl) && ((ExecutorCoroutineDispatcherImpl) other).getExecutor() == getExecutor();
    }

    public int hashCode() {
        return System.identityHashCode(getExecutor());
    }
}
