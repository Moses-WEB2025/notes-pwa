package kotlinx.coroutines.flow;

import androidx.exifinterface.media.ExifInterface;
import defpackage.R2;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.flow.internal.AbortFlowException;
import kotlinx.coroutines.flow.internal.FlowExceptions_commonKt;
import kotlinx.coroutines.flow.internal.NullSurrogateKt;

/* JADX INFO: compiled from: Reduce.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\u001ap\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001\"\b\b\u0001\u0010\u0002*\u0002H\u0001*\b\u0012\u0004\u0012\u0002H\u00020\u00032F\u0010\u0004\u001aB\b\u0001\u0012\u0013\u0012\u0011H\u0001¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\t\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u0005H\u0086@¢\u0006\u0002\u0010\f\u001av\u0010\r\u001a\u0002H\u000e\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u000f\u001a\u0002H\u000e2H\b\u0004\u0010\u0004\u001aB\b\u0001\u0012\u0013\u0012\u0011H\u000e¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\u0010\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\t\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u0005H\u0086H¢\u0006\u0002\u0010\u0011\u001a\u001e\u0010\u0012\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0086@¢\u0006\u0002\u0010\u0013\u001a \u0010\u0014\u001a\u0004\u0018\u0001H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0086@¢\u0006\u0002\u0010\u0013\u001a\u001e\u0010\u0015\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0086@¢\u0006\u0002\u0010\u0013\u001aB\u0010\u0015\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\"\u0010\u0016\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u0017H\u0086@¢\u0006\u0002\u0010\u0019\u001a \u0010\u001a\u001a\u0004\u0018\u0001H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0086@¢\u0006\u0002\u0010\u0013\u001aD\u0010\u001a\u001a\u0004\u0018\u0001H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\"\u0010\u0016\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u0017H\u0086@¢\u0006\u0002\u0010\u0019\u001a\u001e\u0010\u001b\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0086@¢\u0006\u0002\u0010\u0013\u001a \u0010\u001c\u001a\u0004\u0018\u0001H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0086@¢\u0006\u0002\u0010\u0013¨\u0006\u001d"}, d2 = {"reduce", "S", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/flow/Flow;", "operation", "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", "accumulator", "value", "Lkotlin/coroutines/Continuation;", "", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function3;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "fold", "R", "initial", "acc", "(Lkotlinx/coroutines/flow/Flow;Ljava/lang/Object;Lkotlin/jvm/functions/Function3;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "single", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "singleOrNull", "first", "predicate", "Lkotlin/Function2;", "", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "firstOrNull", "last", "lastOrNull", "kotlinx-coroutines-core"}, k = 5, mv = {2, 1, 0}, xi = 48, xs = "kotlinx/coroutines/flow/FlowKt")
final /* synthetic */ class FlowKt__ReduceKt {

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$first$1, reason: invalid class name */
    /* JADX INFO: compiled from: Reduce.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__ReduceKt", f = "Reduce.kt", i = {0, 0}, l = {R2.array.ai_tag_input_send_blend_colors}, m = "first", n = {"result", "collector$iv"}, s = {"L$0", "L$1"})
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
            return FlowKt.first(null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$first$3, reason: invalid class name */
    /* JADX INFO: compiled from: Reduce.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__ReduceKt", f = "Reduce.kt", i = {0, 0}, l = {R2.array.ai_tag_input_send_blend_colors}, m = "first", n = {"result", "collector$iv"}, s = {"L$0", "L$1"})
    static final class AnonymousClass3<T> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        AnonymousClass3(Continuation<? super AnonymousClass3> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt.first(null, null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$firstOrNull$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Reduce.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__ReduceKt", f = "Reduce.kt", i = {0, 0}, l = {R2.array.ai_tag_input_send_blend_colors}, m = "firstOrNull", n = {"result", "collector$iv"}, s = {"L$0", "L$1"})
    static final class C04501<T> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        C04501(Continuation<? super C04501> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt.firstOrNull(null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$firstOrNull$3, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Reduce.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__ReduceKt", f = "Reduce.kt", i = {0, 0}, l = {R2.array.ai_tag_input_send_blend_colors}, m = "firstOrNull", n = {"result", "collector$iv"}, s = {"L$0", "L$1"})
    static final class C04513<T> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        C04513(Continuation<? super C04513> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt.firstOrNull(null, null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$fold$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Reduce.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 176)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__ReduceKt", f = "Reduce.kt", i = {0}, l = {40}, m = "fold", n = {"accumulator"}, s = {"L$0"})
    static final class C04521<T, R> extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C04521(Continuation<? super C04521> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt__ReduceKt.fold(null, null, null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$last$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Reduce.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__ReduceKt", f = "Reduce.kt", i = {0}, l = {R2.array.ai_panel_close_blend_colors}, m = "last", n = {"result"}, s = {"L$0"})
    static final class C04531<T> extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C04531(Continuation<? super C04531> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt.last(null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$lastOrNull$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Reduce.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__ReduceKt", f = "Reduce.kt", i = {0}, l = {R2.array.ai_select_close_partial_button_colors}, m = "lastOrNull", n = {"result"}, s = {"L$0"})
    static final class C04551<T> extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C04551(Continuation<? super C04551> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt.lastOrNull(null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$reduce$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Reduce.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__ReduceKt", f = "Reduce.kt", i = {0}, l = {18}, m = "reduce", n = {"accumulator"}, s = {"L$0"})
    static final class C04571<S, T extends S> extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C04571(Continuation<? super C04571> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt.reduce(null, null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$single$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Reduce.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__ReduceKt", f = "Reduce.kt", i = {0}, l = {53}, m = "single", n = {"result"}, s = {"L$0"})
    static final class C04591<T> extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C04591(Continuation<? super C04591> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt.single(null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$singleOrNull$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Reduce.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__ReduceKt", f = "Reduce.kt", i = {0, 0}, l = {R2.array.ai_tag_input_send_blend_colors}, m = "singleOrNull", n = {"result", "collector$iv"}, s = {"L$0", "L$1"})
    static final class C04611<T> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        C04611(Continuation<? super C04611> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt.singleOrNull(null, this);
        }
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    /* JADX WARN: Type inference failed for: r2v1, types: [T, kotlinx.coroutines.internal.Symbol] */
    public static final <S, T extends S> Object reduce(Flow<? extends T> flow, Function3<? super S, ? super T, ? super Continuation<? super S>, ? extends Object> function3, Continuation<? super S> continuation) {
        C04571 c04571;
        Ref.ObjectRef objectRef;
        if (continuation instanceof C04571) {
            c04571 = (C04571) continuation;
            if ((c04571.label & Integer.MIN_VALUE) != 0) {
                c04571.label -= Integer.MIN_VALUE;
            } else {
                c04571 = new C04571(continuation);
            }
        } else {
            c04571 = new C04571(continuation);
        }
        Object obj = c04571.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = c04571.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            objectRef2.element = NullSurrogateKt.NULL;
            FlowCollector<? super Object> c04582 = new C04582<>(objectRef2, function3);
            c04571.L$0 = objectRef2;
            c04571.label = 1;
            if (flow.collect(c04582, c04571) == coroutine_suspended) {
                return coroutine_suspended;
            }
            objectRef = objectRef2;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            objectRef = (Ref.ObjectRef) c04571.L$0;
            ResultKt.throwOnFailure(obj);
        }
        if (objectRef.element == NullSurrogateKt.NULL) {
            throw new NoSuchElementException("Empty flow can't be reduced");
        }
        return objectRef.element;
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$reduce$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Reduce.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    static final class C04582<T> implements FlowCollector {
        final /* synthetic */ Ref.ObjectRef<Object> $accumulator;
        final /* synthetic */ Function3<S, T, Continuation<? super S>, Object> $operation;

        /* JADX WARN: Multi-variable type inference failed */
        C04582(Ref.ObjectRef<Object> objectRef, Function3<? super S, ? super T, ? super Continuation<? super S>, ? extends Object> function3) {
            this.$accumulator = objectRef;
            this.$operation = function3;
        }

        /* JADX WARN: Code duplicated, block: B:7:0x0014  */
        /* JADX WARN: Type inference fix 'apply assigned field type' failed
        java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
        	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
        	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
        	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
         */
        @Override // kotlinx.coroutines.flow.FlowCollector
        public final Object emit(T t, Continuation<? super Unit> continuation) {
            FlowKt__ReduceKt$reduce$2$emit$1 flowKt__ReduceKt$reduce$2$emit$1;
            Ref.ObjectRef<Object> objectRef;
            Object obj;
            Ref.ObjectRef<Object> objectRef2;
            Object obj2;
            if (continuation instanceof FlowKt__ReduceKt$reduce$2$emit$1) {
                flowKt__ReduceKt$reduce$2$emit$1 = (FlowKt__ReduceKt$reduce$2$emit$1) continuation;
                if ((flowKt__ReduceKt$reduce$2$emit$1.label & Integer.MIN_VALUE) != 0) {
                    flowKt__ReduceKt$reduce$2$emit$1.label -= Integer.MIN_VALUE;
                } else {
                    flowKt__ReduceKt$reduce$2$emit$1 = new FlowKt__ReduceKt$reduce$2$emit$1(this, continuation);
                }
            } else {
                flowKt__ReduceKt$reduce$2$emit$1 = new FlowKt__ReduceKt$reduce$2$emit$1(this, continuation);
            }
            Object obj3 = flowKt__ReduceKt$reduce$2$emit$1.result;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = flowKt__ReduceKt$reduce$2$emit$1.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj3);
                objectRef = this.$accumulator;
                obj2 = t;
                if (objectRef.element != NullSurrogateKt.NULL) {
                    Function3<S, T, Continuation<? super S>, Object> function3 = this.$operation;
                    Object obj4 = this.$accumulator.element;
                    flowKt__ReduceKt$reduce$2$emit$1.L$0 = objectRef;
                    flowKt__ReduceKt$reduce$2$emit$1.label = 1;
                    Object objInvoke = function3.invoke((S) obj4, t, flowKt__ReduceKt$reduce$2$emit$1);
                    if (objInvoke == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    obj = objInvoke;
                    objectRef2 = objectRef;
                }
                objectRef.element = (T) obj2;
                return Unit.INSTANCE;
            }
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            objectRef2 = (Ref.ObjectRef) flowKt__ReduceKt$reduce$2$emit$1.L$0;
            ResultKt.throwOnFailure(obj3);
            obj = (T) obj3;
            objectRef = objectRef2;
            obj2 = obj;
            objectRef.element = (T) obj2;
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    /* JADX WARN: Multi-variable type inference failed */
    public static final <T, R> Object fold(Flow<? extends T> flow, R r, Function3<? super R, ? super T, ? super Continuation<? super R>, ? extends Object> function3, Continuation<? super R> continuation) {
        C04521 c04521;
        Ref.ObjectRef objectRef;
        if (continuation instanceof C04521) {
            c04521 = (C04521) continuation;
            if ((c04521.label & Integer.MIN_VALUE) != 0) {
                c04521.label -= Integer.MIN_VALUE;
            } else {
                c04521 = new C04521(continuation);
            }
        } else {
            c04521 = new C04521(continuation);
        }
        Object obj = c04521.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = c04521.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            objectRef2.element = r;
            FlowCollector<? super Object> anonymousClass2 = new AnonymousClass2<>(objectRef2, function3);
            c04521.L$0 = objectRef2;
            c04521.label = 1;
            if (flow.collect(anonymousClass2, c04521) == coroutine_suspended) {
                return coroutine_suspended;
            }
            objectRef = objectRef2;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            objectRef = (Ref.ObjectRef) c04521.L$0;
            ResultKt.throwOnFailure(obj);
        }
        return objectRef.element;
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$fold$2, reason: invalid class name */
    /* JADX INFO: compiled from: Reduce.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 176)
    public static final class AnonymousClass2<T> implements FlowCollector {
        final /* synthetic */ Ref.ObjectRef<R> $accumulator;
        final /* synthetic */ Function3<R, T, Continuation<? super R>, Object> $operation;

        /* JADX WARN: Multi-variable type inference failed */
        public AnonymousClass2(Ref.ObjectRef<R> objectRef, Function3<? super R, ? super T, ? super Continuation<? super R>, ? extends Object> function3) {
            this.$accumulator = objectRef;
            this.$operation = function3;
        }

        /* JADX WARN: Code duplicated, block: B:7:0x0014  */
        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // kotlinx.coroutines.flow.FlowCollector
        public final Object emit(T t, Continuation<? super Unit> continuation) {
            FlowKt__ReduceKt$fold$2$emit$1 flowKt__ReduceKt$fold$2$emit$1;
            Ref.ObjectRef objectRef;
            if (continuation instanceof FlowKt__ReduceKt$fold$2$emit$1) {
                flowKt__ReduceKt$fold$2$emit$1 = (FlowKt__ReduceKt$fold$2$emit$1) continuation;
                if ((flowKt__ReduceKt$fold$2$emit$1.label & Integer.MIN_VALUE) != 0) {
                    flowKt__ReduceKt$fold$2$emit$1.label -= Integer.MIN_VALUE;
                } else {
                    flowKt__ReduceKt$fold$2$emit$1 = new FlowKt__ReduceKt$fold$2$emit$1(this, continuation);
                }
            } else {
                flowKt__ReduceKt$fold$2$emit$1 = new FlowKt__ReduceKt$fold$2$emit$1(this, continuation);
            }
            Object obj = flowKt__ReduceKt$fold$2$emit$1.result;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = flowKt__ReduceKt$fold$2$emit$1.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Ref.ObjectRef objectRef2 = this.$accumulator;
                Function3<R, T, Continuation<? super R>, Object> function3 = this.$operation;
                T t2 = objectRef2.element;
                flowKt__ReduceKt$fold$2$emit$1.L$0 = objectRef2;
                flowKt__ReduceKt$fold$2$emit$1.label = 1;
                Object objInvoke = function3.invoke((R) t2, t, flowKt__ReduceKt$fold$2$emit$1);
                if (objInvoke == coroutine_suspended) {
                    return coroutine_suspended;
                }
                obj = (T) objInvoke;
                objectRef = objectRef2;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                objectRef = (Ref.ObjectRef) flowKt__ReduceKt$fold$2$emit$1.L$0;
                ResultKt.throwOnFailure(obj);
            }
            objectRef.element = (T) obj;
            return Unit.INSTANCE;
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        public final Object emit$$forInline(T t, Continuation<? super Unit> continuation) {
            new FlowKt__ReduceKt$fold$2$emit$1(this, continuation);
            Ref.ObjectRef<R> objectRef = this.$accumulator;
            objectRef.element = (T) this.$operation.invoke((R) objectRef.element, t, (Continuation<? super R>) continuation);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final <T, R> Object fold$$forInline(Flow<? extends T> flow, R r, Function3<? super R, ? super T, ? super Continuation<? super R>, ? extends Object> function3, Continuation<? super R> continuation) {
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = r;
        flow.collect(new AnonymousClass2(objectRef, function3), continuation);
        return objectRef.element;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    public static final <T> Object single(Flow<? extends T> flow, Continuation<? super T> continuation) {
        C04591 c04591;
        Ref.ObjectRef objectRef;
        if (continuation instanceof C04591) {
            c04591 = (C04591) continuation;
            if ((c04591.label & Integer.MIN_VALUE) != 0) {
                c04591.label -= Integer.MIN_VALUE;
            } else {
                c04591 = new C04591(continuation);
            }
        } else {
            c04591 = new C04591(continuation);
        }
        Object obj = c04591.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = c04591.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            objectRef2.element = (T) NullSurrogateKt.NULL;
            FlowCollector<? super Object> flowCollector = new FlowCollector() { // from class: kotlinx.coroutines.flow.FlowKt__ReduceKt.single.2
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(T t, Continuation<? super Unit> continuation2) {
                    if (objectRef2.element != NullSurrogateKt.NULL) {
                        throw new IllegalArgumentException("Flow has more than one element".toString());
                    }
                    objectRef2.element = t;
                    return Unit.INSTANCE;
                }
            };
            c04591.L$0 = objectRef2;
            c04591.label = 1;
            if (flow.collect(flowCollector, c04591) == coroutine_suspended) {
                return coroutine_suspended;
            }
            objectRef = objectRef2;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            objectRef = (Ref.ObjectRef) c04591.L$0;
            ResultKt.throwOnFailure(obj);
        }
        if (objectRef.element == NullSurrogateKt.NULL) {
            throw new NoSuchElementException("Flow is empty");
        }
        return objectRef.element;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    public static final <T> Object singleOrNull(Flow<? extends T> flow, Continuation<? super T> continuation) {
        C04611 c04611;
        Ref.ObjectRef objectRef;
        AbortFlowException e;
        FlowCollector<T> flowCollector;
        if (continuation instanceof C04611) {
            c04611 = (C04611) continuation;
            if ((c04611.label & Integer.MIN_VALUE) != 0) {
                c04611.label -= Integer.MIN_VALUE;
            } else {
                c04611 = new C04611(continuation);
            }
        } else {
            c04611 = new C04611(continuation);
        }
        Object obj = c04611.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = c04611.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            objectRef2.element = (T) NullSurrogateKt.NULL;
            FlowCollector<T> flowCollector2 = new FlowCollector<T>() { // from class: kotlinx.coroutines.flow.FlowKt__ReduceKt$singleOrNull$$inlined$collectWhile$1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public Object emit(T t, Continuation<? super Unit> continuation2) {
                    if (objectRef2.element == NullSurrogateKt.NULL) {
                        objectRef2.element = t;
                        return Unit.INSTANCE;
                    }
                    objectRef2.element = (T) NullSurrogateKt.NULL;
                    throw new AbortFlowException(this);
                }
            };
            try {
                c04611.L$0 = objectRef2;
                c04611.L$1 = flowCollector2;
                c04611.label = 1;
                if (flow.collect(flowCollector2, c04611) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                objectRef = objectRef2;
            } catch (AbortFlowException e2) {
                objectRef = objectRef2;
                e = e2;
                flowCollector = flowCollector2;
                FlowExceptions_commonKt.checkOwnership(e, flowCollector);
                JobKt.ensureActive(c04611.getContext());
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            flowCollector = (FlowKt__ReduceKt$singleOrNull$$inlined$collectWhile$1) c04611.L$1;
            objectRef = (Ref.ObjectRef) c04611.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (AbortFlowException e3) {
                e = e3;
                FlowExceptions_commonKt.checkOwnership(e, flowCollector);
                JobKt.ensureActive(c04611.getContext());
            }
        }
        if (objectRef.element == NullSurrogateKt.NULL) {
            return null;
        }
        return objectRef.element;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    public static final <T> Object first(Flow<? extends T> flow, Continuation<? super T> continuation) {
        AnonymousClass1 anonymousClass1;
        Ref.ObjectRef objectRef;
        AbortFlowException e;
        FlowCollector<T> flowCollector;
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
            final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            objectRef2.element = (T) NullSurrogateKt.NULL;
            FlowCollector<T> flowCollector2 = new FlowCollector<T>() { // from class: kotlinx.coroutines.flow.FlowKt__ReduceKt$first$$inlined$collectWhile$1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public Object emit(T t, Continuation<? super Unit> continuation2) {
                    objectRef2.element = t;
                    throw new AbortFlowException(this);
                }
            };
            try {
                anonymousClass1.L$0 = objectRef2;
                anonymousClass1.L$1 = flowCollector2;
                anonymousClass1.label = 1;
                if (flow.collect(flowCollector2, anonymousClass1) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                objectRef = objectRef2;
            } catch (AbortFlowException e2) {
                objectRef = objectRef2;
                e = e2;
                flowCollector = flowCollector2;
                FlowExceptions_commonKt.checkOwnership(e, flowCollector);
                JobKt.ensureActive(anonymousClass1.getContext());
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            flowCollector = (FlowKt__ReduceKt$first$$inlined$collectWhile$1) anonymousClass1.L$1;
            objectRef = (Ref.ObjectRef) anonymousClass1.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (AbortFlowException e3) {
                e = e3;
                FlowExceptions_commonKt.checkOwnership(e, flowCollector);
                JobKt.ensureActive(anonymousClass1.getContext());
            }
        }
        if (objectRef.element == NullSurrogateKt.NULL) {
            throw new NoSuchElementException("Expected at least one element");
        }
        return objectRef.element;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    public static final <T> Object first(Flow<? extends T> flow, Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> function2, Continuation<? super T> continuation) {
        AnonymousClass3 anonymousClass3;
        Ref.ObjectRef objectRef;
        AbortFlowException e;
        FlowCollector<? super Object> flowCollector;
        if (continuation instanceof AnonymousClass3) {
            anonymousClass3 = (AnonymousClass3) continuation;
            if ((anonymousClass3.label & Integer.MIN_VALUE) != 0) {
                anonymousClass3.label -= Integer.MIN_VALUE;
            } else {
                anonymousClass3 = new AnonymousClass3(continuation);
            }
        } else {
            anonymousClass3 = new AnonymousClass3(continuation);
        }
        Object obj = anonymousClass3.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = anonymousClass3.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            objectRef2.element = (T) NullSurrogateKt.NULL;
            FlowCollector<? super Object> flowKt__ReduceKt$first$$inlined$collectWhile$2 = new FlowKt__ReduceKt$first$$inlined$collectWhile$2<>(function2, objectRef2);
            try {
                anonymousClass3.L$0 = objectRef2;
                anonymousClass3.L$1 = flowKt__ReduceKt$first$$inlined$collectWhile$2;
                anonymousClass3.label = 1;
                if (flow.collect(flowKt__ReduceKt$first$$inlined$collectWhile$2, anonymousClass3) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                objectRef = objectRef2;
            } catch (AbortFlowException e2) {
                objectRef = objectRef2;
                e = e2;
                flowCollector = flowKt__ReduceKt$first$$inlined$collectWhile$2;
                FlowExceptions_commonKt.checkOwnership(e, flowCollector);
                JobKt.ensureActive(anonymousClass3.getContext());
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            flowCollector = (FlowKt__ReduceKt$first$$inlined$collectWhile$2) anonymousClass3.L$1;
            objectRef = (Ref.ObjectRef) anonymousClass3.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (AbortFlowException e3) {
                e = e3;
                FlowExceptions_commonKt.checkOwnership(e, flowCollector);
                JobKt.ensureActive(anonymousClass3.getContext());
            }
        }
        if (objectRef.element == NullSurrogateKt.NULL) {
            throw new NoSuchElementException("Expected at least one element matching the predicate");
        }
        return objectRef.element;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    public static final <T> Object firstOrNull(Flow<? extends T> flow, Continuation<? super T> continuation) {
        C04501 c04501;
        Ref.ObjectRef objectRef;
        AbortFlowException e;
        FlowCollector<T> flowCollector;
        if (continuation instanceof C04501) {
            c04501 = (C04501) continuation;
            if ((c04501.label & Integer.MIN_VALUE) != 0) {
                c04501.label -= Integer.MIN_VALUE;
            } else {
                c04501 = new C04501(continuation);
            }
        } else {
            c04501 = new C04501(continuation);
        }
        Object obj = c04501.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = c04501.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            FlowCollector<T> flowCollector2 = new FlowCollector<T>() { // from class: kotlinx.coroutines.flow.FlowKt__ReduceKt$firstOrNull$$inlined$collectWhile$1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public Object emit(T t, Continuation<? super Unit> continuation2) {
                    objectRef2.element = t;
                    throw new AbortFlowException(this);
                }
            };
            try {
                c04501.L$0 = objectRef2;
                c04501.L$1 = flowCollector2;
                c04501.label = 1;
                if (flow.collect(flowCollector2, c04501) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                objectRef = objectRef2;
            } catch (AbortFlowException e2) {
                objectRef = objectRef2;
                e = e2;
                flowCollector = flowCollector2;
                FlowExceptions_commonKt.checkOwnership(e, flowCollector);
                JobKt.ensureActive(c04501.getContext());
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            flowCollector = (FlowKt__ReduceKt$firstOrNull$$inlined$collectWhile$1) c04501.L$1;
            objectRef = (Ref.ObjectRef) c04501.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (AbortFlowException e3) {
                e = e3;
                FlowExceptions_commonKt.checkOwnership(e, flowCollector);
                JobKt.ensureActive(c04501.getContext());
            }
        }
        return objectRef.element;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    public static final <T> Object firstOrNull(Flow<? extends T> flow, Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> function2, Continuation<? super T> continuation) {
        C04513 c04513;
        Ref.ObjectRef objectRef;
        AbortFlowException e;
        FlowCollector<? super Object> flowCollector;
        if (continuation instanceof C04513) {
            c04513 = (C04513) continuation;
            if ((c04513.label & Integer.MIN_VALUE) != 0) {
                c04513.label -= Integer.MIN_VALUE;
            } else {
                c04513 = new C04513(continuation);
            }
        } else {
            c04513 = new C04513(continuation);
        }
        Object obj = c04513.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = c04513.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            FlowCollector<? super Object> flowKt__ReduceKt$firstOrNull$$inlined$collectWhile$2 = new FlowKt__ReduceKt$firstOrNull$$inlined$collectWhile$2<>(function2, objectRef2);
            try {
                c04513.L$0 = objectRef2;
                c04513.L$1 = flowKt__ReduceKt$firstOrNull$$inlined$collectWhile$2;
                c04513.label = 1;
                if (flow.collect(flowKt__ReduceKt$firstOrNull$$inlined$collectWhile$2, c04513) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                objectRef = objectRef2;
            } catch (AbortFlowException e2) {
                objectRef = objectRef2;
                e = e2;
                flowCollector = flowKt__ReduceKt$firstOrNull$$inlined$collectWhile$2;
                FlowExceptions_commonKt.checkOwnership(e, flowCollector);
                JobKt.ensureActive(c04513.getContext());
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            flowCollector = (FlowKt__ReduceKt$firstOrNull$$inlined$collectWhile$2) c04513.L$1;
            objectRef = (Ref.ObjectRef) c04513.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (AbortFlowException e3) {
                e = e3;
                FlowExceptions_commonKt.checkOwnership(e, flowCollector);
                JobKt.ensureActive(c04513.getContext());
            }
        }
        return objectRef.element;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    public static final <T> Object last(Flow<? extends T> flow, Continuation<? super T> continuation) {
        C04531 c04531;
        Ref.ObjectRef objectRef;
        if (continuation instanceof C04531) {
            c04531 = (C04531) continuation;
            if ((c04531.label & Integer.MIN_VALUE) != 0) {
                c04531.label -= Integer.MIN_VALUE;
            } else {
                c04531 = new C04531(continuation);
            }
        } else {
            c04531 = new C04531(continuation);
        }
        Object obj = c04531.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = c04531.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            objectRef2.element = (T) NullSurrogateKt.NULL;
            FlowCollector<? super Object> flowCollector = new FlowCollector() { // from class: kotlinx.coroutines.flow.FlowKt__ReduceKt.last.2
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(T t, Continuation<? super Unit> continuation2) {
                    objectRef2.element = t;
                    return Unit.INSTANCE;
                }
            };
            c04531.L$0 = objectRef2;
            c04531.label = 1;
            if (flow.collect(flowCollector, c04531) == coroutine_suspended) {
                return coroutine_suspended;
            }
            objectRef = objectRef2;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            objectRef = (Ref.ObjectRef) c04531.L$0;
            ResultKt.throwOnFailure(obj);
        }
        if (objectRef.element == NullSurrogateKt.NULL) {
            throw new NoSuchElementException("Expected at least one element");
        }
        return objectRef.element;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    public static final <T> Object lastOrNull(Flow<? extends T> flow, Continuation<? super T> continuation) {
        C04551 c04551;
        Ref.ObjectRef objectRef;
        if (continuation instanceof C04551) {
            c04551 = (C04551) continuation;
            if ((c04551.label & Integer.MIN_VALUE) != 0) {
                c04551.label -= Integer.MIN_VALUE;
            } else {
                c04551 = new C04551(continuation);
            }
        } else {
            c04551 = new C04551(continuation);
        }
        Object obj = c04551.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = c04551.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            FlowCollector<? super Object> flowCollector = new FlowCollector() { // from class: kotlinx.coroutines.flow.FlowKt__ReduceKt.lastOrNull.2
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(T t, Continuation<? super Unit> continuation2) {
                    objectRef2.element = t;
                    return Unit.INSTANCE;
                }
            };
            c04551.L$0 = objectRef2;
            c04551.label = 1;
            if (flow.collect(flowCollector, c04551) == coroutine_suspended) {
                return coroutine_suspended;
            }
            objectRef = objectRef2;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            objectRef = (Ref.ObjectRef) c04551.L$0;
            ResultKt.throwOnFailure(obj);
        }
        return objectRef.element;
    }
}
