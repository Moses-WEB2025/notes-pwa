package com.miui.doodle.feature.action;

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

/* JADX INFO: compiled from: EraserLayerBitmapInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "Ljava/lang/Object;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.miui.doodle.feature.action.EraserLayerBitmapInfo$restoreOldLayerBm$1$5$1$1", f = "EraserLayerBitmapInfo.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
final class EraserLayerBitmapInfo$restoreOldLayerBm$1$5$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Object>, Object> {
    final /* synthetic */ ErasedBrushPathInfo $info;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    EraserLayerBitmapInfo$restoreOldLayerBm$1$5$1$1(ErasedBrushPathInfo erasedBrushPathInfo, Continuation<? super EraserLayerBitmapInfo$restoreOldLayerBm$1$5$1$1> continuation) {
        super(2, continuation);
        this.$info = erasedBrushPathInfo;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        EraserLayerBitmapInfo$restoreOldLayerBm$1$5$1$1 eraserLayerBitmapInfo$restoreOldLayerBm$1$5$1$1 = new EraserLayerBitmapInfo$restoreOldLayerBm$1$5$1$1(this.$info, continuation);
        eraserLayerBitmapInfo$restoreOldLayerBm$1$5$1$1.L$0 = obj;
        return eraserLayerBitmapInfo$restoreOldLayerBm$1$5$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Object> continuation) {
        return ((EraserLayerBitmapInfo$restoreOldLayerBm$1$5$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        if (!CoroutineScopeKt.isActive(coroutineScope)) {
            return new Object();
        }
        this.$info.restoreOld();
        return new Object();
    }
}
