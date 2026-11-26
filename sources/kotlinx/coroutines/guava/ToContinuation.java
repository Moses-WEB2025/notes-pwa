package kotlinx.coroutines.guava;

import androidx.exifinterface.media.ExifInterface;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.Uninterruptibles;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.concurrent.ExecutionException;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlinx.coroutines.CancellableContinuation;

/* JADX INFO: compiled from: ListenableFuture.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00060\u0002j\u0002`\u0003B#\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007¢\u0006\u0004\b\b\u0010\tJ\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0010"}, d2 = {"Lkotlinx/coroutines/guava/ToContinuation;", ExifInterface.GPS_DIRECTION_TRUE, "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "futureToObserve", "Lcom/google/common/util/concurrent/ListenableFuture;", "continuation", "Lkotlinx/coroutines/CancellableContinuation;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/google/common/util/concurrent/ListenableFuture;Lkotlinx/coroutines/CancellableContinuation;)V", "getFutureToObserve", "()Lcom/google/common/util/concurrent/ListenableFuture;", "getContinuation", "()Lkotlinx/coroutines/CancellableContinuation;", "run", "", "kotlinx-coroutines-guava"}, k = 1, mv = {2, 1, 0}, xi = 48)
final class ToContinuation<T> implements Runnable {
    private final CancellableContinuation<T> continuation;
    private final ListenableFuture<T> futureToObserve;

    /* JADX WARN: Multi-variable type inference failed */
    public ToContinuation(ListenableFuture<T> listenableFuture, CancellableContinuation<? super T> cancellableContinuation) {
        this.futureToObserve = listenableFuture;
        this.continuation = cancellableContinuation;
    }

    public final ListenableFuture<T> getFutureToObserve() {
        return this.futureToObserve;
    }

    public final CancellableContinuation<T> getContinuation() {
        return this.continuation;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.futureToObserve.isCancelled()) {
            CancellableContinuation.DefaultImpls.cancel$default(this.continuation, null, 1, null);
            return;
        }
        try {
            CancellableContinuation<T> cancellableContinuation = this.continuation;
            Result.Companion companion = Result.INSTANCE;
            cancellableContinuation.resumeWith(Result.m2532constructorimpl(Uninterruptibles.getUninterruptibly(this.futureToObserve)));
        } catch (ExecutionException e) {
            CancellableContinuation<T> cancellableContinuation2 = this.continuation;
            Result.Companion companion2 = Result.INSTANCE;
            cancellableContinuation2.resumeWith(Result.m2532constructorimpl(ResultKt.createFailure(ListenableFutureKt.nonNullCause(e))));
        }
    }
}
