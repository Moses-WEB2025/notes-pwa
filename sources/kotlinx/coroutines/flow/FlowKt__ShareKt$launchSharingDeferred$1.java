package kotlinx.coroutines.flow;

import defpackage.R2;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.JobKt;

/* JADX INFO: compiled from: Share.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 1, 0}, xi = 48)
@DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharingDeferred$1", f = "Share.kt", i = {0}, l = {R2.attr.actionModeCloseContentDescription}, m = "invokeSuspend", n = {"state"}, s = {"L$0"})
final class FlowKt__ShareKt$launchSharingDeferred$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ CompletableDeferred<Result<StateFlow<T>>> $result;
    final /* synthetic */ Flow<T> $upstream;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    FlowKt__ShareKt$launchSharingDeferred$1(Flow<? extends T> flow, CompletableDeferred<Result<StateFlow<T>>> completableDeferred, Continuation<? super FlowKt__ShareKt$launchSharingDeferred$1> continuation) {
        super(2, continuation);
        this.$upstream = flow;
        this.$result = completableDeferred;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        FlowKt__ShareKt$launchSharingDeferred$1 flowKt__ShareKt$launchSharingDeferred$1 = new FlowKt__ShareKt$launchSharingDeferred$1(this.$upstream, this.$result, continuation);
        flowKt__ShareKt$launchSharingDeferred$1.L$0 = obj;
        return flowKt__ShareKt$launchSharingDeferred$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((FlowKt__ShareKt$launchSharingDeferred$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Ref.ObjectRef objectRef;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        try {
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
                Flow<T> flow = this.$upstream;
                final CompletableDeferred<Result<StateFlow<T>>> completableDeferred = this.$result;
                FlowCollector flowCollector = new FlowCollector() { // from class: kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharingDeferred$1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(T t, Continuation<? super Unit> continuation) {
                        MutableStateFlow<T> mutableStateFlow = objectRef2.element;
                        if (mutableStateFlow != null) {
                            mutableStateFlow.setValue(t);
                        } else {
                            CoroutineScope coroutineScope2 = coroutineScope;
                            Ref.ObjectRef<MutableStateFlow<T>> objectRef3 = objectRef2;
                            CompletableDeferred<Result<StateFlow<T>>> completableDeferred2 = completableDeferred;
                            T t2 = (T) StateFlowKt.MutableStateFlow(t);
                            Result.Companion companion = Result.INSTANCE;
                            completableDeferred2.complete(Result.m2531boximpl(Result.m2532constructorimpl(new ReadonlyStateFlow((StateFlow) t2, JobKt.getJob(coroutineScope2.getCoroutineContext())))));
                            objectRef3.element = t2;
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.L$0 = objectRef2;
                this.label = 1;
                if (flow.collect((FlowCollector<? super T>) flowCollector, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                objectRef = objectRef2;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                objectRef = (Ref.ObjectRef) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            if (objectRef.element == 0) {
                CompletableDeferred<Result<StateFlow<T>>> completableDeferred2 = this.$result;
                Result.Companion companion = Result.INSTANCE;
                completableDeferred2.complete((Result<StateFlow<T>>) Result.m2531boximpl(Result.m2532constructorimpl(ResultKt.createFailure(new NoSuchElementException("Flow is empty")))));
            }
            return Unit.INSTANCE;
        } catch (Throwable th) {
            this.$result.completeExceptionally(th);
            throw th;
        }
    }
}
