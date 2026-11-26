package kotlinx.coroutines.flow;

import androidx.exifinterface.media.ExifInterface;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function2;

/* JADX INFO: compiled from: Share.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\t\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002BD\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012-\u0010\u0004\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0005¢\u0006\u0002\b\t¢\u0006\u0004\b\n\u0010\u000bJ\u000e\u0010\r\u001a\u00020\u0007H\u0086@¢\u0006\u0002\u0010\u000eJ\u0016\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00028\u0000H\u0096A¢\u0006\u0002\u0010\u0011R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002X\u0082\u0004¢\u0006\u0002\n\u0000R7\u0010\u0004\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0005¢\u0006\u0002\b\tX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\f¨\u0006\u0012"}, d2 = {"Lkotlinx/coroutines/flow/SubscribedFlowCollector;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/flow/FlowCollector;", "collector", "action", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "", "Lkotlin/ExtensionFunctionType;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/jvm/functions/Function2;)V", "Lkotlin/jvm/functions/Function2;", "onSubscription", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "emit", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class SubscribedFlowCollector<T> implements FlowCollector<T> {
    private final Function2<FlowCollector<? super T>, Continuation<? super Unit>, Object> action;
    private final FlowCollector<T> collector;

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.SubscribedFlowCollector$onSubscription$1, reason: invalid class name */
    /* JADX INFO: compiled from: Share.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.SubscribedFlowCollector", f = "Share.kt", i = {0, 0}, l = {422, 426}, m = "onSubscription", n = {"this", "safeCollector"}, s = {"L$0", "L$1"})
    static final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ SubscribedFlowCollector<T> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(SubscribedFlowCollector<T> subscribedFlowCollector, Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
            this.this$0 = subscribedFlowCollector;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.onSubscription(this);
        }
    }

    @Override // kotlinx.coroutines.flow.FlowCollector
    public Object emit(T t, Continuation<? super Unit> continuation) {
        return this.collector.emit(t, continuation);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SubscribedFlowCollector(FlowCollector<? super T> flowCollector, Function2<? super FlowCollector<? super T>, ? super Continuation<? super Unit>, ? extends Object> function2) {
        this.collector = flowCollector;
        this.action = function2;
    }

    /* JADX WARN: Code duplicated, block: B:27:0x006b  */
    /* JADX WARN: Code duplicated, block: B:32:0x007e  */
    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0078, code lost:
    
        if (((kotlinx.coroutines.flow.SubscribedFlowCollector) r7).onSubscription(r0) == r1) goto L29;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onSubscription(kotlin.coroutines.Continuation<? super kotlin.Unit> r8) throws java.lang.Throwable {
        /*
            r7 = this;
            boolean r0 = r8 instanceof kotlinx.coroutines.flow.SubscribedFlowCollector.AnonymousClass1
            if (r0 == 0) goto L14
            r0 = r8
            kotlinx.coroutines.flow.SubscribedFlowCollector$onSubscription$1 r0 = (kotlinx.coroutines.flow.SubscribedFlowCollector.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r8 = r0.label
            int r8 = r8 - r2
            r0.label = r8
            goto L19
        L14:
            kotlinx.coroutines.flow.SubscribedFlowCollector$onSubscription$1 r0 = new kotlinx.coroutines.flow.SubscribedFlowCollector$onSubscription$1
            r0.<init>(r7, r8)
        L19:
            java.lang.Object r8 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L43
            if (r2 == r4) goto L35
            if (r2 != r3) goto L2d
            kotlin.ResultKt.throwOnFailure(r8)
            goto L7b
        L2d:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L35:
            java.lang.Object r7 = r0.L$1
            kotlinx.coroutines.flow.internal.SafeCollector r7 = (kotlinx.coroutines.flow.internal.SafeCollector) r7
            java.lang.Object r2 = r0.L$0
            kotlinx.coroutines.flow.SubscribedFlowCollector r2 = (kotlinx.coroutines.flow.SubscribedFlowCollector) r2
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.lang.Throwable -> L41
            goto L62
        L41:
            r8 = move-exception
            goto L85
        L43:
            kotlin.ResultKt.throwOnFailure(r8)
            kotlinx.coroutines.flow.internal.SafeCollector r8 = new kotlinx.coroutines.flow.internal.SafeCollector
            kotlinx.coroutines.flow.FlowCollector<T> r2 = r7.collector
            kotlin.coroutines.CoroutineContext r5 = r0.getContext()
            r8.<init>(r2, r5)
            kotlin.jvm.functions.Function2<kotlinx.coroutines.flow.FlowCollector<? super T>, kotlin.coroutines.Continuation<? super kotlin.Unit>, java.lang.Object> r2 = r7.action     // Catch: java.lang.Throwable -> L81
            r0.L$0 = r7     // Catch: java.lang.Throwable -> L81
            r0.L$1 = r8     // Catch: java.lang.Throwable -> L81
            r0.label = r4     // Catch: java.lang.Throwable -> L81
            java.lang.Object r2 = r2.invoke(r8, r0)     // Catch: java.lang.Throwable -> L81
            if (r2 != r1) goto L60
            goto L7a
        L60:
            r2 = r7
            r7 = r8
        L62:
            r7.releaseIntercepted()
            kotlinx.coroutines.flow.FlowCollector<T> r7 = r2.collector
            boolean r8 = r7 instanceof kotlinx.coroutines.flow.SubscribedFlowCollector
            if (r8 == 0) goto L7e
            kotlinx.coroutines.flow.SubscribedFlowCollector r7 = (kotlinx.coroutines.flow.SubscribedFlowCollector) r7
            r8 = 0
            r0.L$0 = r8
            r0.L$1 = r8
            r0.label = r3
            java.lang.Object r7 = r7.onSubscription(r0)
            if (r7 != r1) goto L7b
        L7a:
            return r1
        L7b:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        L7e:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        L81:
            r7 = move-exception
            r6 = r8
            r8 = r7
            r7 = r6
        L85:
            r7.releaseIntercepted()
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.SubscribedFlowCollector.onSubscription(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
