package com.miui.doodle.document;

import com.miui.doodle.base.core.IDoodle;
import com.miui.doodle.feature.draw.DoodleView;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: Layer.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.miui.doodle.document.Layer$finishRemoveAction$1$1$2$1", f = "Layer.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
final class Layer$finishRemoveAction$1$1$2$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ DoodleView $doodleView;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    Layer$finishRemoveAction$1$1$2$1(DoodleView doodleView, Continuation<? super Layer$finishRemoveAction$1$1$2$1> continuation) {
        super(2, continuation);
        this.$doodleView = doodleView;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new Layer$finishRemoveAction$1$1$2$1(this.$doodleView, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((Layer$finishRemoveAction$1$1$2$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.$doodleView.setEnabled(true);
        IDoodle.refresh$default(this.$doodleView, null, 1, null);
        this.$doodleView.onProcessing(true);
        return Unit.INSTANCE;
    }
}
