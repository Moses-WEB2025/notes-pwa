package androidx.appfunctions.internal;

import androidx.appfunctions.AppFunctionSearchSpec;
import androidx.appfunctions.metadata.AppFunctionPackageMetadata;
import androidx.appsearch.app.GlobalSearchSession;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* JADX INFO: compiled from: AppSearchAppFunctionReader.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 1, 0}, xi = 48)
@DebugMetadata(c = "androidx.appfunctions.internal.AppSearchAppFunctionReader$searchAppFunctions$2$observerJob$1", f = "AppSearchAppFunctionReader.kt", i = {}, l = {104}, m = "invokeSuspend", n = {}, s = {}, v = 1)
final class AppSearchAppFunctionReader$searchAppFunctions$2$observerJob$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ ProducerScope<List<AppFunctionPackageMetadata>> $$this$callbackFlow;
    final /* synthetic */ AppSearchAppFunctionReader.AppSearchChannelObserver $appSearchChannelObserver;
    final /* synthetic */ AppFunctionSearchSpec $searchFunctionSpec;
    final /* synthetic */ GlobalSearchSession $session;
    int label;
    final /* synthetic */ AppSearchAppFunctionReader this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    AppSearchAppFunctionReader$searchAppFunctions$2$observerJob$1(AppSearchAppFunctionReader.AppSearchChannelObserver appSearchChannelObserver, ProducerScope<? super List<AppFunctionPackageMetadata>> producerScope, AppSearchAppFunctionReader appSearchAppFunctionReader, GlobalSearchSession globalSearchSession, AppFunctionSearchSpec appFunctionSearchSpec, Continuation<? super AppSearchAppFunctionReader$searchAppFunctions$2$observerJob$1> continuation) {
        super(2, continuation);
        this.$appSearchChannelObserver = appSearchChannelObserver;
        this.$$this$callbackFlow = producerScope;
        this.this$0 = appSearchAppFunctionReader;
        this.$session = globalSearchSession;
        this.$searchFunctionSpec = appFunctionSearchSpec;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new AppSearchAppFunctionReader$searchAppFunctions$2$observerJob$1(this.$appSearchChannelObserver, this.$$this$callbackFlow, this.this$0, this.$session, this.$searchFunctionSpec, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((AppSearchAppFunctionReader$searchAppFunctions$2$observerJob$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX INFO: renamed from: androidx.appfunctions.internal.AppSearchAppFunctionReader$searchAppFunctions$2$observerJob$1$1, reason: invalid class name */
    /* JADX INFO: compiled from: AppSearchAppFunctionReader.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    static final class AnonymousClass1<T> implements FlowCollector {
        final /* synthetic */ ProducerScope<List<AppFunctionPackageMetadata>> $$this$callbackFlow;
        final /* synthetic */ AppFunctionSearchSpec $searchFunctionSpec;
        final /* synthetic */ GlobalSearchSession $session;
        final /* synthetic */ AppSearchAppFunctionReader this$0;

        /* JADX WARN: Multi-variable type inference failed */
        AnonymousClass1(ProducerScope<? super List<AppFunctionPackageMetadata>> producerScope, AppSearchAppFunctionReader appSearchAppFunctionReader, GlobalSearchSession globalSearchSession, AppFunctionSearchSpec appFunctionSearchSpec) {
            this.$$this$callbackFlow = producerScope;
            this.this$0 = appSearchAppFunctionReader;
            this.$session = globalSearchSession;
            this.$searchFunctionSpec = appFunctionSearchSpec;
        }

        @Override // kotlinx.coroutines.flow.FlowCollector
        public /* bridge */ /* synthetic */ Object emit(Object obj, Continuation continuation) {
            return emit((Unit) obj, (Continuation<? super Unit>) continuation);
        }

        /* JADX WARN: Code duplicated, block: B:7:0x0014  */
        /* JADX WARN: Code restructure failed: missing block: B:21:0x005f, code lost:
        
            if (r6.send(r8, r7) == r0) goto L22;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(kotlin.Unit r7, kotlin.coroutines.Continuation<? super kotlin.Unit> r8) {
            /*
                r6 = this;
                boolean r7 = r8 instanceof androidx.appfunctions.internal.AppSearchAppFunctionReader$searchAppFunctions$2$observerJob$1$1$emit$1
                if (r7 == 0) goto L14
                r7 = r8
                androidx.appfunctions.internal.AppSearchAppFunctionReader$searchAppFunctions$2$observerJob$1$1$emit$1 r7 = (androidx.appfunctions.internal.AppSearchAppFunctionReader$searchAppFunctions$2$observerJob$1$1$emit$1) r7
                int r0 = r7.label
                r1 = -2147483648(0xffffffff80000000, float:-0.0)
                r0 = r0 & r1
                if (r0 == 0) goto L14
                int r8 = r7.label
                int r8 = r8 - r1
                r7.label = r8
                goto L19
            L14:
                androidx.appfunctions.internal.AppSearchAppFunctionReader$searchAppFunctions$2$observerJob$1$1$emit$1 r7 = new androidx.appfunctions.internal.AppSearchAppFunctionReader$searchAppFunctions$2$observerJob$1$1$emit$1
                r7.<init>(r6, r8)
            L19:
                java.lang.Object r8 = r7.result
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r7.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L3d
                if (r1 == r3) goto L35
                if (r1 != r2) goto L2d
                kotlin.ResultKt.throwOnFailure(r8)
                goto L62
            L2d:
                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                r6.<init>(r7)
                throw r6
            L35:
                java.lang.Object r6 = r7.L$0
                kotlinx.coroutines.channels.ProducerScope r6 = (kotlinx.coroutines.channels.ProducerScope) r6
                kotlin.ResultKt.throwOnFailure(r8)
                goto L56
            L3d:
                kotlin.ResultKt.throwOnFailure(r8)
                kotlinx.coroutines.channels.ProducerScope<java.util.List<androidx.appfunctions.metadata.AppFunctionPackageMetadata>> r8 = r6.$$this$callbackFlow
                androidx.appfunctions.internal.AppSearchAppFunctionReader r1 = r6.this$0
                androidx.appsearch.app.GlobalSearchSession r4 = r6.$session
                androidx.appfunctions.AppFunctionSearchSpec r6 = r6.$searchFunctionSpec
                r7.L$0 = r8
                r7.label = r3
                java.lang.Object r6 = androidx.appfunctions.internal.AppSearchAppFunctionReader.access$performSearch(r1, r4, r6, r7)
                if (r6 != r0) goto L53
                goto L61
            L53:
                r5 = r8
                r8 = r6
                r6 = r5
            L56:
                r1 = 0
                r7.L$0 = r1
                r7.label = r2
                java.lang.Object r6 = r6.send(r8, r7)
                if (r6 != r0) goto L62
            L61:
                return r0
            L62:
                kotlin.Unit r6 = kotlin.Unit.INSTANCE
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.appfunctions.internal.AppSearchAppFunctionReader$searchAppFunctions$2$observerJob$1.AnonymousClass1.emit(kotlin.Unit, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            this.label = 1;
            if (FlowKt.m4092debounceHG0u8IE(this.$appSearchChannelObserver.observe(), AppSearchAppFunctionReader.Companion.m11getOBSERVER_DEBOUNCE_MILLISUwyO8pc()).collect(new AnonymousClass1(this.$$this$callbackFlow, this.this$0, this.$session, this.$searchFunctionSpec), this) == coroutine_suspended) {
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
