package androidx.concurrent.futures;

import androidx.exifinterface.media.ExifInterface;
import ch.qos.logback.core.CoreConstants;
import com.google.common.util.concurrent.ListenableFuture;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.ToDoStat;
import com.xiaomi.mirror.synergy.CallMethod;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationKt;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;

/* JADX INFO: compiled from: SuspendToFutureAdapter.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000A\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003*\u0001\u0005\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u0016B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003JT\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u000b0\n\"\u0004\b\u0000\u0010\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2'\u0010\u0010\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000b0\u0013\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0011¢\u0006\u0002\b\u0014¢\u0006\u0002\u0010\u0015R\u0010\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Landroidx/concurrent/futures/SuspendToFutureAdapter;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "GlobalListenableFutureScope", "androidx/concurrent/futures/SuspendToFutureAdapter$GlobalListenableFutureScope$1", "Landroidx/concurrent/futures/SuspendToFutureAdapter$GlobalListenableFutureScope$1;", "GlobalListenableFutureAwaitContext", "Lkotlinx/coroutines/CoroutineDispatcher;", "launchFuture", "Lcom/google/common/util/concurrent/ListenableFuture;", ExifInterface.GPS_DIRECTION_TRUE, CoreConstants.CONTEXT_SCOPE_VALUE, "Lkotlin/coroutines/CoroutineContext;", "launchUndispatched", "", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/CoroutineScope;", "Lkotlin/coroutines/Continuation;", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/coroutines/CoroutineContext;ZLkotlin/jvm/functions/Function2;)Lcom/google/common/util/concurrent/ListenableFuture;", "DeferredFuture", "concurrent-futures-ktx"}, k = 1, mv = {2, 0, 0}, xi = 48)
public final class SuspendToFutureAdapter {
    public static final SuspendToFutureAdapter INSTANCE = new SuspendToFutureAdapter();
    private static final SuspendToFutureAdapter$GlobalListenableFutureScope$1 GlobalListenableFutureScope = new CoroutineScope() { // from class: androidx.concurrent.futures.SuspendToFutureAdapter$GlobalListenableFutureScope$1
        private final CoroutineContext coroutineContext = Dispatchers.getMain();

        @Override // kotlinx.coroutines.CoroutineScope
        public CoroutineContext getCoroutineContext() {
            return this.coroutineContext;
        }
    };
    private static final CoroutineDispatcher GlobalListenableFutureAwaitContext = Dispatchers.getUnconfined();

    private SuspendToFutureAdapter() {
    }

    public static /* synthetic */ ListenableFuture launchFuture$default(SuspendToFutureAdapter suspendToFutureAdapter, CoroutineContext coroutineContext, boolean z, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = EmptyCoroutineContext.INSTANCE;
        }
        if ((i & 2) != 0) {
            z = true;
        }
        return suspendToFutureAdapter.launchFuture(coroutineContext, z, function2);
    }

    public final <T> ListenableFuture<T> launchFuture(CoroutineContext context, boolean launchUndispatched, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> block) {
        Deferred deferredAsync = BuildersKt.async(GlobalListenableFutureScope, context, launchUndispatched ? CoroutineStart.UNDISPATCHED : CoroutineStart.DEFAULT, block);
        DeferredFuture deferredFuture = new DeferredFuture(deferredAsync);
        Continuation<Unit> continuationCreateCoroutine = ContinuationKt.createCoroutine(new SuspendToFutureAdapter$launchFuture$1$1(deferredAsync), deferredFuture);
        Result.Companion companion = Result.INSTANCE;
        continuationCreateCoroutine.resumeWith(Result.m2532constructorimpl(Unit.INSTANCE));
        return deferredFuture;
    }

    /* JADX INFO: compiled from: SuspendToFutureAdapter.kt */
    @Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0015\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rH\u0016J\b\u0010\u000f\u001a\u00020\rH\u0016J\b\u0010\u0010\u001a\u00020\rH\u0016J\r\u0010\u0011\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0012J\u001e\u0010\u0011\u001a\u00028\u00002\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0096\u0002¢\u0006\u0002\u0010\u0017J\u0018\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u001b\u0010\"\u001a\u00020\u00192\f\u0010#\u001a\b\u0012\u0004\u0012\u00028\u00000$H\u0016¢\u0006\u0002\u0010%R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010\b\u001a\u0015\u0012\f\u0012\n \n*\u0004\u0018\u00018\u00008\u00000\t¢\u0006\u0002\b\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001e\u001a\u00020\u001f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b \u0010!¨\u0006&"}, d2 = {"Landroidx/concurrent/futures/SuspendToFutureAdapter$DeferredFuture;", ExifInterface.GPS_DIRECTION_TRUE, "Lcom/google/common/util/concurrent/ListenableFuture;", "Lkotlin/coroutines/Continuation;", "resultDeferred", "Lkotlinx/coroutines/Deferred;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lkotlinx/coroutines/Deferred;)V", "delegateFuture", "Landroidx/concurrent/futures/ResolvableFuture;", "kotlin.jvm.PlatformType", "Lorg/jspecify/annotations/NonNull;", ToDoStat.CANCEL, "", "shouldInterrupt", "isCancelled", "isDone", CallMethod.METHOD_GET, "()Ljava/lang/Object;", "timeout", "", "unit", "Ljava/util/concurrent/TimeUnit;", "(JLjava/util/concurrent/TimeUnit;)Ljava/lang/Object;", "addListener", "", "listener", "Ljava/lang/Runnable;", "executor", "Ljava/util/concurrent/Executor;", CoreConstants.CONTEXT_SCOPE_VALUE, "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "resumeWith", "result", "Lkotlin/Result;", "(Ljava/lang/Object;)V", "concurrent-futures-ktx"}, k = 1, mv = {2, 0, 0}, xi = 48)
    private static final class DeferredFuture<T> implements ListenableFuture<T>, Continuation<T> {
        private final ResolvableFuture<T> delegateFuture = ResolvableFuture.create();
        private final Deferred<T> resultDeferred;

        /* JADX WARN: Multi-variable type inference failed */
        public DeferredFuture(Deferred<? extends T> deferred) {
            this.resultDeferred = deferred;
        }

        @Override // java.util.concurrent.Future
        public boolean cancel(boolean shouldInterrupt) {
            boolean zCancel = this.delegateFuture.cancel(shouldInterrupt);
            if (zCancel) {
                Job.DefaultImpls.cancel$default((Job) this.resultDeferred, (CancellationException) null, 1, (Object) null);
            }
            return zCancel;
        }

        @Override // java.util.concurrent.Future
        public boolean isCancelled() {
            return this.delegateFuture.isCancelled();
        }

        @Override // java.util.concurrent.Future
        public boolean isDone() {
            return this.delegateFuture.isDone();
        }

        @Override // java.util.concurrent.Future
        public T get() {
            return this.delegateFuture.get();
        }

        @Override // java.util.concurrent.Future
        public T get(long timeout, TimeUnit unit) {
            return this.delegateFuture.get(timeout, unit);
        }

        @Override // com.google.common.util.concurrent.ListenableFuture
        public void addListener(Runnable listener, Executor executor) {
            this.delegateFuture.addListener(listener, executor);
        }

        @Override // kotlin.coroutines.Continuation
        /* JADX INFO: renamed from: getContext */
        public CoroutineContext get$context() {
            return SuspendToFutureAdapter.GlobalListenableFutureAwaitContext;
        }

        @Override // kotlin.coroutines.Continuation
        public void resumeWith(Object result) {
            Throwable thM2535exceptionOrNullimpl = Result.m2535exceptionOrNullimpl(result);
            if (thM2535exceptionOrNullimpl == null) {
                this.delegateFuture.set(result);
            } else if (thM2535exceptionOrNullimpl instanceof CancellationException) {
                this.delegateFuture.cancel(false);
            } else {
                this.delegateFuture.setException(thM2535exceptionOrNullimpl);
            }
        }
    }
}
