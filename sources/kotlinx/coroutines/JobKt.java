package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import java.util.concurrent.Future;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;

/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"kotlinx/coroutines/JobKt__FutureKt", "kotlinx/coroutines/JobKt__JobKt"}, k = 4, mv = {2, 1, 0}, xi = 48)
public final class JobKt {
    public static final CompletableJob Job(Job job) {
        return JobKt__JobKt.Job(job);
    }

    public static final void cancel(CoroutineContext coroutineContext, CancellationException cancellationException) {
        JobKt__JobKt.cancel(coroutineContext, cancellationException);
    }

    public static final void cancel(Job job, String str, Throwable th) {
        JobKt__JobKt.cancel(job, str, th);
    }

    public static final Object cancelAndJoin(Job job, Continuation<? super Unit> continuation) {
        return JobKt__JobKt.cancelAndJoin(job, continuation);
    }

    public static final void cancelChildren(CoroutineContext coroutineContext, CancellationException cancellationException) {
        JobKt__JobKt.cancelChildren(coroutineContext, cancellationException);
    }

    public static final void cancelChildren(Job job, CancellationException cancellationException) {
        JobKt__JobKt.cancelChildren(job, cancellationException);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "This function does not do what its name implies: it will not cancel the future if just cancel() was called.", replaceWith = @ReplaceWith(expression = "this.invokeOnCancellation { future.cancel(false) }", imports = {}))
    public static final void cancelFutureOnCancellation(CancellableContinuation<?> cancellableContinuation, Future<?> future) {
        JobKt__FutureKt.cancelFutureOnCancellation(cancellableContinuation, future);
    }

    public static final DisposableHandle disposeOnCompletion(Job job, DisposableHandle disposableHandle) {
        return JobKt__JobKt.disposeOnCompletion(job, disposableHandle);
    }

    public static final void ensureActive(CoroutineContext coroutineContext) {
        JobKt__JobKt.ensureActive(coroutineContext);
    }

    public static final void ensureActive(Job job) {
        JobKt__JobKt.ensureActive(job);
    }

    public static final Job getJob(CoroutineContext coroutineContext) {
        return JobKt__JobKt.getJob(coroutineContext);
    }

    public static final DisposableHandle invokeOnCompletion(Job job, boolean z, JobNode jobNode) {
        return JobKt__JobKt.invokeOnCompletion(job, z, jobNode);
    }

    public static final boolean isActive(CoroutineContext coroutineContext) {
        return JobKt__JobKt.isActive(coroutineContext);
    }
}
