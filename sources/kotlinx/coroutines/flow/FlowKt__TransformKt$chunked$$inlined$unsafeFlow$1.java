package kotlinx.coroutines.flow;

import java.util.List;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* JADX INFO: Add missing generic type declarations: [T] */
/* JADX INFO: compiled from: SafeCollector.common.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001c\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H\u0096@¢\u0006\u0002\u0010\u0006¨\u0006\u0007¸\u0006\u0000"}, d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class FlowKt__TransformKt$chunked$$inlined$unsafeFlow$1<T> implements Flow<List<? extends T>> {
    final /* synthetic */ int $size$inlined;
    final /* synthetic */ Flow $this_chunked$inlined;

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__TransformKt$chunked$$inlined$unsafeFlow$1$1, reason: invalid class name */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__TransformKt$chunked$$inlined$unsafeFlow$1", f = "Transform.kt", i = {0, 0}, l = {110, 120}, m = "collect", n = {"$this$chunked_u24lambda_u2413", "result"}, s = {"L$0", "L$1"})
    public static final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt__TransformKt$chunked$$inlined$unsafeFlow$1.this.collect(null, this);
        }
    }

    public FlowKt__TransformKt$chunked$$inlined$unsafeFlow$1(Flow flow, int i) {
        this.$this_chunked$inlined = flow;
        this.$size$inlined = i;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0076, code lost:
    
        if (r7.emit(r6, r0) == r1) goto L24;
     */
    @Override // kotlinx.coroutines.flow.Flow
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object collect(kotlinx.coroutines.flow.FlowCollector<? super java.util.List<? extends T>> r7, kotlin.coroutines.Continuation<? super kotlin.Unit> r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof kotlinx.coroutines.flow.FlowKt__TransformKt$chunked$$inlined$unsafeFlow$1.AnonymousClass1
            if (r0 == 0) goto L14
            r0 = r8
            kotlinx.coroutines.flow.FlowKt__TransformKt$chunked$$inlined$unsafeFlow$1$1 r0 = (kotlinx.coroutines.flow.FlowKt__TransformKt$chunked$$inlined$unsafeFlow$1.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r8 = r0.label
            int r8 = r8 - r2
            r0.label = r8
            goto L19
        L14:
            kotlinx.coroutines.flow.FlowKt__TransformKt$chunked$$inlined$unsafeFlow$1$1 r0 = new kotlinx.coroutines.flow.FlowKt__TransformKt$chunked$$inlined$unsafeFlow$1$1
            r0.<init>(r8)
        L19:
            java.lang.Object r8 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L41
            if (r2 == r4) goto L35
            if (r2 != r3) goto L2d
            kotlin.ResultKt.throwOnFailure(r8)
            goto L79
        L2d:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L35:
            java.lang.Object r6 = r0.L$1
            kotlin.jvm.internal.Ref$ObjectRef r6 = (kotlin.jvm.internal.Ref.ObjectRef) r6
            java.lang.Object r7 = r0.L$0
            kotlinx.coroutines.flow.FlowCollector r7 = (kotlinx.coroutines.flow.FlowCollector) r7
            kotlin.ResultKt.throwOnFailure(r8)
            goto L65
        L41:
            kotlin.ResultKt.throwOnFailure(r8)
            r8 = r0
            kotlin.coroutines.Continuation r8 = (kotlin.coroutines.Continuation) r8
            kotlin.jvm.internal.Ref$ObjectRef r8 = new kotlin.jvm.internal.Ref$ObjectRef
            r8.<init>()
            kotlinx.coroutines.flow.Flow r2 = r6.$this_chunked$inlined
            kotlinx.coroutines.flow.FlowKt__TransformKt$chunked$2$1 r5 = new kotlinx.coroutines.flow.FlowKt__TransformKt$chunked$2$1
            int r6 = r6.$size$inlined
            r5.<init>(r8, r6, r7)
            kotlinx.coroutines.flow.FlowCollector r5 = (kotlinx.coroutines.flow.FlowCollector) r5
            r0.L$0 = r7
            r0.L$1 = r8
            r0.label = r4
            java.lang.Object r6 = r2.collect(r5, r0)
            if (r6 != r1) goto L64
            goto L78
        L64:
            r6 = r8
        L65:
            T r6 = r6.element
            java.util.ArrayList r6 = (java.util.ArrayList) r6
            if (r6 == 0) goto L79
            r8 = 0
            r0.L$0 = r8
            r0.L$1 = r8
            r0.label = r3
            java.lang.Object r6 = r7.emit(r6, r0)
            if (r6 != r1) goto L79
        L78:
            return r1
        L79:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__TransformKt$chunked$$inlined$unsafeFlow$1.collect(kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
