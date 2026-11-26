package kotlinx.coroutines;

import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;

/* JADX INFO: compiled from: Delay.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a\u000e\u0010\u0000\u001a\u00020\u0001H\u0086@¢\u0006\u0002\u0010\u0002\u001a\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0086@¢\u0006\u0002\u0010\u0007\u001a\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0086@¢\u0006\u0004\b\n\u0010\u0007\u001a\u0013\u0010\u000f\u001a\u00020\u0006*\u00020\tH\u0000¢\u0006\u0004\b\u0010\u0010\u0011\"\u0018\u0010\u0003\u001a\u00020\u000b*\u00020\f8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006\u0012"}, d2 = {"awaitCancellation", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "delay", "", "timeMillis", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "duration", "Lkotlin/time/Duration;", "delay-VtjQ1oo", "Lkotlinx/coroutines/Delay;", "Lkotlin/coroutines/CoroutineContext;", "getDelay", "(Lkotlin/coroutines/CoroutineContext;)Lkotlinx/coroutines/Delay;", "toDelayMillis", "toDelayMillis-LRDsOJo", "(J)J", "kotlinx-coroutines-core"}, k = 2, mv = {2, 1, 0}, xi = 48)
public final class DelayKt {

    /* JADX INFO: renamed from: kotlinx.coroutines.DelayKt$awaitCancellation$1, reason: invalid class name */
    /* JADX INFO: compiled from: Delay.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.DelayKt", f = "Delay.kt", i = {}, l = {160}, m = "awaitCancellation", n = {}, s = {})
    static final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return DelayKt.awaitCancellation(this);
        }
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    public static final Object awaitCancellation(Continuation<?> continuation) {
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
            anonymousClass1.label = 1;
            AnonymousClass1 anonymousClass2 = anonymousClass1;
            CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(anonymousClass2), 1);
            cancellableContinuationImpl.initCancellability();
            Object result = cancellableContinuationImpl.getResult();
            if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended(anonymousClass2);
            }
            if (result == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }

    public static final Object delay(long j, Continuation<? super Unit> continuation) {
        if (j <= 0) {
            return Unit.INSTANCE;
        }
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        CancellableContinuationImpl cancellableContinuationImpl2 = cancellableContinuationImpl;
        if (j < Long.MAX_VALUE) {
            getDelay(cancellableContinuationImpl2.getContext()).mo4122scheduleResumeAfterDelay(j, cancellableContinuationImpl2);
        }
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: delay-VtjQ1oo, reason: not valid java name */
    public static final Object m4041delayVtjQ1oo(long j, Continuation<? super Unit> continuation) {
        Object objDelay = delay(m4042toDelayMillisLRDsOJo(j), continuation);
        return objDelay == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objDelay : Unit.INSTANCE;
    }

    public static final Delay getDelay(CoroutineContext coroutineContext) {
        CoroutineContext.Element element = coroutineContext.get(ContinuationInterceptor.INSTANCE);
        Delay delay = element instanceof Delay ? (Delay) element : null;
        return delay == null ? DefaultExecutorKt.getDefaultDelay() : delay;
    }

    /* JADX INFO: renamed from: toDelayMillis-LRDsOJo, reason: not valid java name */
    public static final long m4042toDelayMillisLRDsOJo(long j) {
        boolean zM3925isPositiveimpl = Duration.m3925isPositiveimpl(j);
        if (zM3925isPositiveimpl) {
            Duration.Companion companion = Duration.INSTANCE;
            return Duration.m3909getInWholeMillisecondsimpl(Duration.m3927plusLRDsOJo(j, DurationKt.toDuration(999999L, DurationUnit.NANOSECONDS)));
        }
        if (zM3925isPositiveimpl) {
            throw new NoWhenBranchMatchedException();
        }
        return 0L;
    }
}
