package kotlinx.coroutines.guava;

import androidx.exifinterface.media.ExifInterface;
import ch.qos.logback.core.CoreConstants;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.Uninterruptibles;
import com.google.common.util.concurrent.internal.InternalFutureFailureAccess;
import com.google.common.util.concurrent.internal.InternalFutures;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.ChildHandle;
import kotlinx.coroutines.ChildJob;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredKt;
import kotlinx.coroutines.CoroutineContextKt;
import kotlinx.coroutines.CoroutineExceptionHandlerKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.selects.SelectClause0;
import kotlinx.coroutines.selects.SelectClause1;

/* JADX INFO: compiled from: ListenableFuture.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000@\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\u001aX\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072'\u0010\b\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0003\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\t¢\u0006\u0002\b\f¢\u0006\u0002\u0010\r\u001a\u001c\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00020\u000f\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001\u001a\f\u0010\u0010\u001a\u00020\u0011*\u00020\u0012H\u0002\u001a\u001c\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000f\u001a\u001e\u0010\u0014\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0086@¢\u0006\u0002\u0010\u0015¨\u0006\u0016"}, d2 = {"future", "Lcom/google/common/util/concurrent/ListenableFuture;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/CoroutineScope;", CoreConstants.CONTEXT_SCOPE_VALUE, "Lkotlin/coroutines/CoroutineContext;", "start", "Lkotlinx/coroutines/CoroutineStart;", "block", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/CoroutineStart;Lkotlin/jvm/functions/Function2;)Lcom/google/common/util/concurrent/ListenableFuture;", "asDeferred", "Lkotlinx/coroutines/Deferred;", "nonNullCause", "", "Ljava/util/concurrent/ExecutionException;", "asListenableFuture", "await", "(Lcom/google/common/util/concurrent/ListenableFuture;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-guava"}, k = 2, mv = {2, 1, 0}, xi = 48)
public final class ListenableFutureKt {
    public static /* synthetic */ ListenableFuture future$default(CoroutineScope coroutineScope, CoroutineContext coroutineContext, CoroutineStart coroutineStart, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = EmptyCoroutineContext.INSTANCE;
        }
        if ((i & 2) != 0) {
            coroutineStart = CoroutineStart.DEFAULT;
        }
        return future(coroutineScope, coroutineContext, coroutineStart, function2);
    }

    public static final <T> ListenableFuture<T> future(CoroutineScope coroutineScope, CoroutineContext coroutineContext, CoroutineStart coroutineStart, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> function2) {
        if (coroutineStart.isLazy()) {
            throw new IllegalArgumentException((coroutineStart + " start is not supported").toString());
        }
        ListenableFutureCoroutine listenableFutureCoroutine = new ListenableFutureCoroutine(CoroutineContextKt.newCoroutineContext(coroutineScope, coroutineContext));
        listenableFutureCoroutine.start(coroutineStart, listenableFutureCoroutine, function2);
        return listenableFutureCoroutine.future;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <T> Deferred<T> asDeferred(final ListenableFuture<T> listenableFuture) {
        Throwable thTryInternalFastPathGetFailure;
        if ((listenableFuture instanceof InternalFutureFailureAccess) && (thTryInternalFastPathGetFailure = InternalFutures.tryInternalFastPathGetFailure((InternalFutureFailureAccess) listenableFuture)) != null) {
            CompletableDeferred completableDeferredCompletableDeferred$default = CompletableDeferredKt.CompletableDeferred$default(null, 1, null);
            completableDeferredCompletableDeferred$default.completeExceptionally(thTryInternalFastPathGetFailure);
            return completableDeferredCompletableDeferred$default;
        }
        if (listenableFuture.isDone()) {
            try {
                return CompletableDeferredKt.CompletableDeferred(Uninterruptibles.getUninterruptibly(listenableFuture));
            } catch (CancellationException e) {
                CompletableDeferred completableDeferredCompletableDeferred$default2 = CompletableDeferredKt.CompletableDeferred$default(null, 1, null);
                completableDeferredCompletableDeferred$default2.cancel(e);
                return completableDeferredCompletableDeferred$default2;
            } catch (ExecutionException e2) {
                CompletableDeferred completableDeferredCompletableDeferred$default3 = CompletableDeferredKt.CompletableDeferred$default(null, 1, null);
                completableDeferredCompletableDeferred$default3.completeExceptionally(nonNullCause(e2));
                return completableDeferredCompletableDeferred$default3;
            }
        }
        final CompletableDeferred completableDeferredCompletableDeferred$default4 = CompletableDeferredKt.CompletableDeferred$default(null, 1, null);
        Futures.addCallback(listenableFuture, new FutureCallback<T>() { // from class: kotlinx.coroutines.guava.ListenableFutureKt.asDeferred.4
            @Override // com.google.common.util.concurrent.FutureCallback
            public void onSuccess(T result) {
                Object objM2532constructorimpl;
                CompletableDeferred<T> completableDeferred = completableDeferredCompletableDeferred$default4;
                try {
                    Result.Companion companion = Result.INSTANCE;
                    AnonymousClass4<T> anonymousClass4 = this;
                    objM2532constructorimpl = Result.m2532constructorimpl(Boolean.valueOf(completableDeferred.complete(result)));
                } catch (Throwable th) {
                    Result.Companion companion2 = Result.INSTANCE;
                    objM2532constructorimpl = Result.m2532constructorimpl(ResultKt.createFailure(th));
                }
                Throwable thM2535exceptionOrNullimpl = Result.m2535exceptionOrNullimpl(objM2532constructorimpl);
                if (thM2535exceptionOrNullimpl != null) {
                    CoroutineExceptionHandlerKt.handleCoroutineException(EmptyCoroutineContext.INSTANCE, thM2535exceptionOrNullimpl);
                }
            }

            @Override // com.google.common.util.concurrent.FutureCallback
            public void onFailure(Throwable t) {
                Object objM2532constructorimpl;
                CompletableDeferred<T> completableDeferred = completableDeferredCompletableDeferred$default4;
                try {
                    Result.Companion companion = Result.INSTANCE;
                    AnonymousClass4<T> anonymousClass4 = this;
                    objM2532constructorimpl = Result.m2532constructorimpl(Boolean.valueOf(completableDeferred.completeExceptionally(t)));
                } catch (Throwable th) {
                    Result.Companion companion2 = Result.INSTANCE;
                    objM2532constructorimpl = Result.m2532constructorimpl(ResultKt.createFailure(th));
                }
                Throwable thM2535exceptionOrNullimpl = Result.m2535exceptionOrNullimpl(objM2532constructorimpl);
                if (thM2535exceptionOrNullimpl != null) {
                    CoroutineExceptionHandlerKt.handleCoroutineException(EmptyCoroutineContext.INSTANCE, thM2535exceptionOrNullimpl);
                }
            }
        }, MoreExecutors.directExecutor());
        completableDeferredCompletableDeferred$default4.invokeOnCompletion(new Function1() { // from class: kotlinx.coroutines.guava.ListenableFutureKt$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ListenableFutureKt.asDeferred$lambda$4(listenableFuture, (Throwable) obj);
            }
        });
        return new Deferred<T>() { // from class: kotlinx.coroutines.guava.ListenableFutureKt.asDeferred.6
            @Override // kotlinx.coroutines.Job
            public ChildHandle attachChild(ChildJob child) {
                return completableDeferredCompletableDeferred$default4.attachChild(child);
            }

            @Override // kotlinx.coroutines.Deferred
            public Object await(Continuation<? super T> continuation) {
                return completableDeferredCompletableDeferred$default4.await(continuation);
            }

            @Override // kotlinx.coroutines.Job
            @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.2.0, binary compatibility with versions <= 1.1.x")
            public /* synthetic */ void cancel() {
                completableDeferredCompletableDeferred$default4.cancel();
            }

            @Override // kotlinx.coroutines.Job
            public void cancel(CancellationException cause) {
                completableDeferredCompletableDeferred$default4.cancel(cause);
            }

            @Override // kotlinx.coroutines.Job
            @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.2.0, binary compatibility with versions <= 1.1.x")
            public /* synthetic */ boolean cancel(Throwable cause) {
                return completableDeferredCompletableDeferred$default4.cancel(cause);
            }

            @Override // kotlin.coroutines.CoroutineContext.Element, kotlin.coroutines.CoroutineContext
            public <R> R fold(R initial, Function2<? super R, ? super CoroutineContext.Element, ? extends R> operation) {
                return (R) completableDeferredCompletableDeferred$default4.fold(initial, operation);
            }

            @Override // kotlin.coroutines.CoroutineContext.Element, kotlin.coroutines.CoroutineContext
            public <E extends CoroutineContext.Element> E get(CoroutineContext.Key<E> key) {
                return (E) completableDeferredCompletableDeferred$default4.get(key);
            }

            @Override // kotlinx.coroutines.Job
            public CancellationException getCancellationException() {
                return completableDeferredCompletableDeferred$default4.getCancellationException();
            }

            @Override // kotlinx.coroutines.Job
            public Sequence<Job> getChildren() {
                return completableDeferredCompletableDeferred$default4.getChildren();
            }

            @Override // kotlinx.coroutines.Deferred
            public T getCompleted() {
                return completableDeferredCompletableDeferred$default4.getCompleted();
            }

            @Override // kotlinx.coroutines.Deferred
            public Throwable getCompletionExceptionOrNull() {
                return completableDeferredCompletableDeferred$default4.getCompletionExceptionOrNull();
            }

            @Override // kotlin.coroutines.CoroutineContext.Element
            public CoroutineContext.Key<?> getKey() {
                return completableDeferredCompletableDeferred$default4.getKey();
            }

            @Override // kotlinx.coroutines.Deferred
            public SelectClause1<T> getOnAwait() {
                return completableDeferredCompletableDeferred$default4.getOnAwait();
            }

            @Override // kotlinx.coroutines.Job
            public SelectClause0 getOnJoin() {
                return completableDeferredCompletableDeferred$default4.getOnJoin();
            }

            @Override // kotlinx.coroutines.Job
            public Job getParent() {
                return completableDeferredCompletableDeferred$default4.getParent();
            }

            @Override // kotlinx.coroutines.Job
            public DisposableHandle invokeOnCompletion(Function1<? super Throwable, Unit> handler) {
                return completableDeferredCompletableDeferred$default4.invokeOnCompletion(handler);
            }

            @Override // kotlinx.coroutines.Job
            public DisposableHandle invokeOnCompletion(boolean onCancelling, boolean invokeImmediately, Function1<? super Throwable, Unit> handler) {
                return completableDeferredCompletableDeferred$default4.invokeOnCompletion(onCancelling, invokeImmediately, handler);
            }

            @Override // kotlinx.coroutines.Job
            public boolean isActive() {
                return completableDeferredCompletableDeferred$default4.isActive();
            }

            @Override // kotlinx.coroutines.Job
            public boolean isCancelled() {
                return completableDeferredCompletableDeferred$default4.isCancelled();
            }

            @Override // kotlinx.coroutines.Job
            public boolean isCompleted() {
                return completableDeferredCompletableDeferred$default4.isCompleted();
            }

            @Override // kotlinx.coroutines.Job
            public Object join(Continuation<? super Unit> continuation) {
                return completableDeferredCompletableDeferred$default4.join(continuation);
            }

            @Override // kotlin.coroutines.CoroutineContext.Element, kotlin.coroutines.CoroutineContext
            public CoroutineContext minusKey(CoroutineContext.Key<?> key) {
                return completableDeferredCompletableDeferred$default4.minusKey(key);
            }

            @Override // kotlin.coroutines.CoroutineContext
            public CoroutineContext plus(CoroutineContext context) {
                return completableDeferredCompletableDeferred$default4.plus(context);
            }

            @Override // kotlinx.coroutines.Job
            @Deprecated(level = DeprecationLevel.ERROR, message = "Operator '+' on two Job objects is meaningless. Job is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The job to the right of `+` just replaces the job the left of `+`.")
            public Job plus(Job other) {
                return completableDeferredCompletableDeferred$default4.plus(other);
            }

            @Override // kotlinx.coroutines.Job
            public boolean start() {
                return completableDeferredCompletableDeferred$default4.start();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit asDeferred$lambda$4(ListenableFuture listenableFuture, Throwable th) {
        listenableFuture.cancel(false);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Throwable nonNullCause(ExecutionException executionException) {
        Throwable cause = executionException.getCause();
        Intrinsics.checkNotNull(cause);
        return cause;
    }

    public static final <T> ListenableFuture<T> asListenableFuture(final Deferred<? extends T> deferred) {
        final JobListenableFuture jobListenableFuture = new JobListenableFuture(deferred);
        deferred.invokeOnCompletion(new Function1() { // from class: kotlinx.coroutines.guava.ListenableFutureKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ListenableFutureKt.asListenableFuture$lambda$5(jobListenableFuture, deferred, (Throwable) obj);
            }
        });
        return jobListenableFuture;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit asListenableFuture$lambda$5(JobListenableFuture jobListenableFuture, Deferred deferred, Throwable th) {
        if (th == null) {
            jobListenableFuture.complete(deferred.getCompleted());
        } else {
            jobListenableFuture.completeExceptionallyOrCancel(th);
        }
        return Unit.INSTANCE;
    }

    public static final <T> Object await(final ListenableFuture<T> listenableFuture, Continuation<? super T> continuation) {
        try {
            if (listenableFuture.isDone()) {
                return Uninterruptibles.getUninterruptibly(listenableFuture);
            }
            CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
            cancellableContinuationImpl.initCancellability();
            CancellableContinuationImpl cancellableContinuationImpl2 = cancellableContinuationImpl;
            listenableFuture.addListener(new ToContinuation(listenableFuture, cancellableContinuationImpl2), MoreExecutors.directExecutor());
            cancellableContinuationImpl2.invokeOnCancellation(new Function1<Throwable, Unit>() { // from class: kotlinx.coroutines.guava.ListenableFutureKt$await$2$1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                    invoke2(th);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Throwable th) {
                    listenableFuture.cancel(false);
                }
            });
            Object result = cancellableContinuationImpl.getResult();
            if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended(continuation);
            }
            return result;
        } catch (ExecutionException e) {
            throw nonNullCause(e);
        }
    }
}
