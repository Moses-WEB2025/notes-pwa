package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.internal.Ref;

/* JADX INFO: compiled from: Limit.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
final class FlowKt__LimitKt$drop$2$1<T> implements FlowCollector {
    final /* synthetic */ int $count;
    final /* synthetic */ Ref.IntRef $skipped;
    final /* synthetic */ FlowCollector<T> $this_flow;

    /* JADX WARN: Multi-variable type inference failed */
    FlowKt__LimitKt$drop$2$1(Ref.IntRef intRef, int i, FlowCollector<? super T> flowCollector) {
        this.$skipped = intRef;
        this.$count = i;
        this.$this_flow = flowCollector;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    @Override // kotlinx.coroutines.flow.FlowCollector
    public final Object emit(T t, Continuation<? super Unit> continuation) {
        FlowKt__LimitKt$drop$2$1$emit$1 flowKt__LimitKt$drop$2$1$emit$1;
        if (continuation instanceof FlowKt__LimitKt$drop$2$1$emit$1) {
            flowKt__LimitKt$drop$2$1$emit$1 = (FlowKt__LimitKt$drop$2$1$emit$1) continuation;
            if ((flowKt__LimitKt$drop$2$1$emit$1.label & Integer.MIN_VALUE) != 0) {
                flowKt__LimitKt$drop$2$1$emit$1.label -= Integer.MIN_VALUE;
            } else {
                flowKt__LimitKt$drop$2$1$emit$1 = new FlowKt__LimitKt$drop$2$1$emit$1(this, continuation);
            }
        } else {
            flowKt__LimitKt$drop$2$1$emit$1 = new FlowKt__LimitKt$drop$2$1$emit$1(this, continuation);
        }
        Object obj = flowKt__LimitKt$drop$2$1$emit$1.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = flowKt__LimitKt$drop$2$1$emit$1.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            if (this.$skipped.element < this.$count) {
                this.$skipped.element++;
                int i2 = this.$skipped.element;
                return Unit.INSTANCE;
            }
            FlowCollector<T> flowCollector = this.$this_flow;
            flowKt__LimitKt$drop$2$1$emit$1.label = 1;
            if (flowCollector.emit(t, flowKt__LimitKt$drop$2$1$emit$1) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
