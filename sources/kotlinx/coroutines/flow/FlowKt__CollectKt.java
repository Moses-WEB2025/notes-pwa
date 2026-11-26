package kotlinx.coroutines.flow;

import androidx.exifinterface.media.ExifInterface;
import ch.qos.logback.core.joran.action.Action;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.internal.NopCollector;

/* JADX INFO: compiled from: Collect.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000J\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0016\u0010\u0000\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u0002H\u0086@¢\u0006\u0002\u0010\u0003\u001a\u001e\u0010\u0004\u001a\u00020\u0005\"\u0004\b\u0000\u0010\u0006*\b\u0012\u0004\u0012\u0002H\u00060\u00022\u0006\u0010\u0007\u001a\u00020\b\u001ah\u0010\t\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0006*\b\u0012\u0004\u0012\u0002H\u00060\u00022H\b\u0004\u0010\n\u001aB\b\u0001\u0012\u0013\u0012\u00110\f¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u000f\u0012\u0013\u0012\u0011H\u0006¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u0011\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u000bH\u0086H¢\u0006\u0002\u0010\u0013\u001aQ\u0010\u0014\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0006*\b\u0012\u0004\u0012\u0002H\u00060\u000221\u0010\n\u001a-\b\u0001\u0012\u0013\u0012\u0011H\u0006¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u0011\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0015H\u0086@¢\u0006\u0002\u0010\u0016\u001a,\u0010\u0017\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0006*\b\u0012\u0004\u0012\u0002H\u00060\u00182\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0002H\u0086@¢\u0006\u0002\u0010\u001a\u001aS\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0006*\b\u0012\u0004\u0012\u0002H\u00060\u000223\b\u0004\u0010\n\u001a-\b\u0001\u0012\u0013\u0012\u0011H\u0006¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u0011\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0015H\u0087H¢\u0006\u0002\u0010\u0016¨\u0006\u001b"}, d2 = {"collect", "", "Lkotlinx/coroutines/flow/Flow;", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "launchIn", "Lkotlinx/coroutines/Job;", ExifInterface.GPS_DIRECTION_TRUE, Action.SCOPE_ATTRIBUTE, "Lkotlinx/coroutines/CoroutineScope;", "collectIndexed", "action", "Lkotlin/Function3;", "", "Lkotlin/ParameterName;", "name", "index", "value", "Lkotlin/coroutines/Continuation;", "", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function3;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "collectLatest", "Lkotlin/Function2;", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "emitAll", "Lkotlinx/coroutines/flow/FlowCollector;", "flow", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlinx/coroutines/flow/Flow;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 5, mv = {2, 1, 0}, xi = 48, xs = "kotlinx/coroutines/flow/FlowKt")
final /* synthetic */ class FlowKt__CollectKt {
    public static final Object collect(Flow<?> flow, Continuation<? super Unit> continuation) {
        Object objCollect = flow.collect(NopCollector.INSTANCE, continuation);
        return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__CollectKt$launchIn$1, reason: invalid class name */
    /* JADX INFO: compiled from: Collect.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__CollectKt$launchIn$1", f = "Collect.kt", i = {}, l = {46}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Flow<T> $this_launchIn;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        AnonymousClass1(Flow<? extends T> flow, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$this_launchIn = flow;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$this_launchIn, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (FlowKt.collect(this.$this_launchIn, this) == coroutine_suspended) {
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

    public static final <T> Job launchIn(Flow<? extends T> flow, CoroutineScope coroutineScope) {
        return BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new AnonymousClass1(flow, null), 3, null);
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__CollectKt$collectIndexed$2, reason: invalid class name */
    /* JADX INFO: compiled from: Collect.kt */
    @Metadata(d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0016\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00028\u0000H\u0096@¢\u0006\u0002\u0010\u0007R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"kotlinx/coroutines/flow/FlowKt__CollectKt$collectIndexed$2", "Lkotlinx/coroutines/flow/FlowCollector;", "index", "", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 1, mv = {2, 1, 0}, xi = 176)
    public static final class AnonymousClass2<T> implements FlowCollector<T> {
        final /* synthetic */ Function3<Integer, T, Continuation<? super Unit>, Object> $action;
        private int index;

        /* JADX WARN: Multi-variable type inference failed */
        public AnonymousClass2(Function3<? super Integer, ? super T, ? super Continuation<? super Unit>, ? extends Object> function3) {
            this.$action = function3;
        }

        @Override // kotlinx.coroutines.flow.FlowCollector
        public Object emit(T t, Continuation<? super Unit> continuation) {
            Function3<Integer, T, Continuation<? super Unit>, Object> function3 = this.$action;
            int i = this.index;
            this.index = i + 1;
            if (i >= 0) {
                Object objInvoke = function3.invoke(Boxing.boxInt(i), t, continuation);
                return objInvoke == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objInvoke : Unit.INSTANCE;
            }
            throw new ArithmeticException("Index overflow has happened");
        }

        public Object emit$$forInline(T t, final Continuation<? super Unit> continuation) {
            new ContinuationImpl(continuation) { // from class: kotlinx.coroutines.flow.FlowKt__CollectKt$collectIndexed$2$emit$1
                int label;
                /* synthetic */ Object result;

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    this.result = obj;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.emit(null, this);
                }
            };
            Function3<Integer, T, Continuation<? super Unit>, Object> function3 = this.$action;
            int i = this.index;
            this.index = i + 1;
            if (i >= 0) {
                function3.invoke(Integer.valueOf(i), t, continuation);
                return Unit.INSTANCE;
            }
            throw new ArithmeticException("Index overflow has happened");
        }
    }

    public static final <T> Object collectIndexed(Flow<? extends T> flow, Function3<? super Integer, ? super T, ? super Continuation<? super Unit>, ? extends Object> function3, Continuation<? super Unit> continuation) {
        Object objCollect = flow.collect(new AnonymousClass2(function3), continuation);
        return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
    }

    private static final <T> Object collectIndexed$$forInline(Flow<? extends T> flow, Function3<? super Integer, ? super T, ? super Continuation<? super Unit>, ? extends Object> function3, Continuation<? super Unit> continuation) {
        flow.collect(new AnonymousClass2(function3), continuation);
        return Unit.INSTANCE;
    }

    public static final <T> Object collectLatest(Flow<? extends T> flow, Function2<? super T, ? super Continuation<? super Unit>, ? extends Object> function2, Continuation<? super Unit> continuation) {
        Object objCollect = FlowKt.collect(FlowKt__ContextKt.buffer$default(FlowKt.mapLatest(flow, function2), 0, null, 2, null), continuation);
        return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
    }

    public static final <T> Object emitAll(FlowCollector<? super T> flowCollector, Flow<? extends T> flow, Continuation<? super Unit> continuation) {
        FlowKt.ensureActive(flowCollector);
        Object objCollect = flow.collect(flowCollector, continuation);
        return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__CollectKt$collect$3, reason: invalid class name */
    /* JADX INFO: compiled from: Collect.kt */
    @Metadata(d1 = {"\u0000\u0013\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00028\u0000H\u0096@¢\u0006\u0002\u0010\u0005¨\u0006\u0006"}, d2 = {"kotlinx/coroutines/flow/FlowKt__CollectKt$collect$3", "Lkotlinx/coroutines/flow/FlowCollector;", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 1, mv = {2, 1, 0}, xi = 176)
    public static final class AnonymousClass3<T> implements FlowCollector<T> {
        final /* synthetic */ Function2<T, Continuation<? super Unit>, Object> $action;

        /* JADX WARN: Multi-variable type inference failed */
        public AnonymousClass3(Function2<? super T, ? super Continuation<? super Unit>, ? extends Object> function2) {
            this.$action = function2;
        }

        @Override // kotlinx.coroutines.flow.FlowCollector
        public Object emit(T t, Continuation<? super Unit> continuation) {
            Object objInvoke = this.$action.invoke(t, continuation);
            return objInvoke == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objInvoke : Unit.INSTANCE;
        }

        public Object emit$$forInline(T t, final Continuation<? super Unit> continuation) {
            new ContinuationImpl(continuation) { // from class: kotlinx.coroutines.flow.FlowKt__CollectKt$collect$3$emit$1
                int label;
                /* synthetic */ Object result;

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    this.result = obj;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.emit(null, this);
                }
            };
            this.$action.invoke(t, continuation);
            return Unit.INSTANCE;
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Backwards compatibility with JS and K/N")
    public static final /* synthetic */ <T> Object collect(Flow<? extends T> flow, Function2<? super T, ? super Continuation<? super Unit>, ? extends Object> function2, Continuation<? super Unit> continuation) {
        Object objCollect = flow.collect(new AnonymousClass3(function2), continuation);
        return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Backwards compatibility with JS and K/N")
    private static final /* synthetic */ <T> Object collect$$forInline(Flow<? extends T> flow, Function2<? super T, ? super Continuation<? super Unit>, ? extends Object> function2, Continuation<? super Unit> continuation) {
        flow.collect(new AnonymousClass3(function2), continuation);
        return Unit.INSTANCE;
    }
}
