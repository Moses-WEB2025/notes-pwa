package kotlinx.coroutines.flow;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import defpackage.R2;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref;

/* JADX INFO: compiled from: SharingStarted.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u001c\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lkotlinx/coroutines/flow/StartedLazily;", "Lkotlinx/coroutines/flow/SharingStarted;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "command", "Lkotlinx/coroutines/flow/Flow;", "Lkotlinx/coroutines/flow/SharingCommand;", "subscriptionCount", "Lkotlinx/coroutines/flow/StateFlow;", "", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {2, 1, 0}, xi = 48)
final class StartedLazily implements SharingStarted {

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.StartedLazily$command$1, reason: invalid class name */
    /* JADX INFO: compiled from: SharingStarted.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/flow/FlowCollector;", "Lkotlinx/coroutines/flow/SharingCommand;"}, k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.StartedLazily$command$1", f = "SharingStarted.kt", i = {}, l = {R2.array.ai_panel_close_blend_colors}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<FlowCollector<? super SharingCommand>, Continuation<? super Unit>, Object> {
        final /* synthetic */ StateFlow<Integer> $subscriptionCount;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(StateFlow<Integer> stateFlow, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$subscriptionCount = stateFlow;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$subscriptionCount, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(FlowCollector<? super SharingCommand> flowCollector, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                Ref.BooleanRef booleanRef = new Ref.BooleanRef();
                this.label = 1;
                if (this.$subscriptionCount.collect(new C01421(booleanRef, flowCollector), this) == coroutine_suspended) {
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

        /* JADX INFO: renamed from: kotlinx.coroutines.flow.StartedLazily$command$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: SharingStarted.kt */
        @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
        static final class C01421<T> implements FlowCollector {
            final /* synthetic */ FlowCollector<SharingCommand> $$this$flow;
            final /* synthetic */ Ref.BooleanRef $started;

            /* JADX WARN: Multi-variable type inference failed */
            C01421(Ref.BooleanRef booleanRef, FlowCollector<? super SharingCommand> flowCollector) {
                this.$started = booleanRef;
                this.$$this$flow = flowCollector;
            }

            /* JADX WARN: Code duplicated, block: B:7:0x0014  */
            public final Object emit(int i, Continuation<? super Unit> continuation) {
                StartedLazily$command$1$1$emit$1 startedLazily$command$1$1$emit$1;
                if (continuation instanceof StartedLazily$command$1$1$emit$1) {
                    startedLazily$command$1$1$emit$1 = (StartedLazily$command$1$1$emit$1) continuation;
                    if ((startedLazily$command$1$1$emit$1.label & Integer.MIN_VALUE) != 0) {
                        startedLazily$command$1$1$emit$1.label -= Integer.MIN_VALUE;
                    } else {
                        startedLazily$command$1$1$emit$1 = new StartedLazily$command$1$1$emit$1(this, continuation);
                    }
                } else {
                    startedLazily$command$1$1$emit$1 = new StartedLazily$command$1$1$emit$1(this, continuation);
                }
                Object obj = startedLazily$command$1$1$emit$1.result;
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i2 = startedLazily$command$1$1$emit$1.label;
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    if (i <= 0 || this.$started.element) {
                        return Unit.INSTANCE;
                    }
                    this.$started.element = true;
                    FlowCollector<SharingCommand> flowCollector = this.$$this$flow;
                    SharingCommand sharingCommand = SharingCommand.START;
                    startedLazily$command$1$1$emit$1.label = 1;
                    if (flowCollector.emit(sharingCommand, startedLazily$command$1$1$emit$1) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                return Unit.INSTANCE;
            }

            @Override // kotlinx.coroutines.flow.FlowCollector
            public /* bridge */ /* synthetic */ Object emit(Object obj, Continuation continuation) {
                return emit(((Number) obj).intValue(), (Continuation<? super Unit>) continuation);
            }
        }
    }

    @Override // kotlinx.coroutines.flow.SharingStarted
    public Flow<SharingCommand> command(StateFlow<Integer> subscriptionCount) {
        return FlowKt.flow(new AnonymousClass1(subscriptionCount, null));
    }

    public String toString() {
        return "SharingStarted.Lazily";
    }
}
