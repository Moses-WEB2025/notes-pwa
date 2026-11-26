package androidx.concurrent.futures;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendFunction;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlinx.coroutines.Deferred;

/* JADX INFO: Add missing generic type declarations: [T] */
/* JADX INFO: compiled from: SuspendToFutureAdapter.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(k = 3, mv = {2, 0, 0}, xi = 48)
/* synthetic */ class SuspendToFutureAdapter$launchFuture$1$1<T> extends FunctionReferenceImpl implements Function1<Continuation<? super T>, Object>, SuspendFunction {
    SuspendToFutureAdapter$launchFuture$1$1(Object obj) {
        super(1, obj, Deferred.class, "await", "await(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Continuation<? super T> continuation) {
        return ((Deferred) this.receiver).await(continuation);
    }
}
