package kotlinx.coroutines.sync;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.internal.SystemPropsKt__SystemProps_commonKt;

/* JADX INFO: compiled from: Semaphore.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u001a3\u0010\u0005\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u0006*\u00020\u00012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00060\bH\u0086H\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\t\u001a\u001a\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000bH\u0002\"\u000e\u0010\u000f\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0012\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0013\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0014\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0015\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Semaphore", "Lkotlinx/coroutines/sync/Semaphore;", "permits", "", "acquiredPermits", "withPermit", ExifInterface.GPS_DIRECTION_TRUE, "action", "Lkotlin/Function0;", "(Lkotlinx/coroutines/sync/Semaphore;Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createSegment", "Lkotlinx/coroutines/sync/SemaphoreSegment;", "id", "", "prev", "MAX_SPIN_CYCLES", "PERMIT", "Lkotlinx/coroutines/internal/Symbol;", "TAKEN", "BROKEN", "CANCELLED", "SEGMENT_SIZE", "kotlinx-coroutines-core"}, k = 2, mv = {2, 1, 0}, xi = 48)
public final class SemaphoreKt {
    private static final int MAX_SPIN_CYCLES = SystemPropsKt__SystemProps_commonKt.systemProp$default("kotlinx.coroutines.semaphore.maxSpinCycles", 100, 0, 0, 12, (Object) null);
    private static final Symbol PERMIT = new Symbol("PERMIT");
    private static final Symbol TAKEN = new Symbol("TAKEN");
    private static final Symbol BROKEN = new Symbol("BROKEN");
    private static final Symbol CANCELLED = new Symbol("CANCELLED");
    private static final int SEGMENT_SIZE = SystemPropsKt__SystemProps_commonKt.systemProp$default("kotlinx.coroutines.semaphore.segmentSize", 16, 0, 0, 12, (Object) null);

    /* JADX INFO: renamed from: kotlinx.coroutines.sync.SemaphoreKt$withPermit$1, reason: invalid class name */
    /* JADX INFO: compiled from: Semaphore.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 176)
    @DebugMetadata(c = "kotlinx.coroutines.sync.SemaphoreKt", f = "Semaphore.kt", i = {0, 0}, l = {81}, m = "withPermit", n = {"$this$withPermit", "action"}, s = {"L$0", "L$1"})
    static final class AnonymousClass1<T> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return SemaphoreKt.withPermit(null, null, this);
        }
    }

    public static /* synthetic */ Semaphore Semaphore$default(int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i2 = 0;
        }
        return Semaphore(i, i2);
    }

    public static final Semaphore Semaphore(int i, int i2) {
        return new SemaphoreImpl(i, i2);
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    public static final <T> Object withPermit(Semaphore semaphore, Function0<? extends T> function0, Continuation<? super T> continuation) {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            if ((anonymousClass1.label & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label -= Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        } else {
            anonymousClass1 = new AnonymousClass1(continuation);
        }
        Object obj = anonymousClass1.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = anonymousClass1.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            anonymousClass1.L$0 = semaphore;
            anonymousClass1.L$1 = function0;
            anonymousClass1.label = 1;
            if (semaphore.acquire(anonymousClass1) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            function0 = (Function0) anonymousClass1.L$1;
            semaphore = (Semaphore) anonymousClass1.L$0;
            ResultKt.throwOnFailure(obj);
        }
        try {
            return function0.invoke();
        } finally {
            semaphore.release();
        }
    }

    private static final <T> Object withPermit$$forInline(Semaphore semaphore, Function0<? extends T> function0, Continuation<? super T> continuation) {
        semaphore.acquire(continuation);
        try {
            return function0.invoke();
        } finally {
            semaphore.release();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final SemaphoreSegment createSegment(long j, SemaphoreSegment semaphoreSegment) {
        return new SemaphoreSegment(j, semaphoreSegment, 0);
    }
}
