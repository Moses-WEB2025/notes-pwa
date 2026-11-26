package com.miui.doodle.feature.draw;

import com.miui.doodle.document.Layer;
import com.miui.notes.base.utils.Logger;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;

/* JADX INFO: compiled from: DoodleView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.miui.doodle.feature.draw.DoodleView$cacheDoodleSnapshot$1$1$2$1$1", f = "DoodleView.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
final class DoodleView$cacheDoodleSnapshot$1$1$2$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ Layer $layer;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    DoodleView$cacheDoodleSnapshot$1$1$2$1$1(Layer layer, Continuation<? super DoodleView$cacheDoodleSnapshot$1$1$2$1$1> continuation) {
        super(2, continuation);
        this.$layer = layer;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        DoodleView$cacheDoodleSnapshot$1$1$2$1$1 doodleView$cacheDoodleSnapshot$1$1$2$1$1 = new DoodleView$cacheDoodleSnapshot$1$1$2$1$1(this.$layer, continuation);
        doodleView$cacheDoodleSnapshot$1$1$2$1$1.L$0 = obj;
        return doodleView$cacheDoodleSnapshot$1$1$2$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((DoodleView$cacheDoodleSnapshot$1$1$2$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Logger.INSTANCE.d(DoodleView.TAG, "doodle do cache 2 cacheLayerJson start");
        if (!CoroutineScopeKt.isActive(coroutineScope)) {
            Logger.INSTANCE.d(DoodleView.TAG, "doodle do cache 2 cacheLayerJson , won't cache");
            return Unit.INSTANCE;
        }
        this.$layer.cacheLayerJson();
        this.$layer.cacheBgImageToFile();
        Logger.INSTANCE.d(DoodleView.TAG, "doodle do cache 2 cacheLayerJson end");
        return Unit.INSTANCE;
    }
}
