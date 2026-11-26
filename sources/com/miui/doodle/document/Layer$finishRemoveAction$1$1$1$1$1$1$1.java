package com.miui.doodle.document;

import com.miui.doodle.base.core.IDoodleItem;
import com.miui.doodle.feature.draw.DoodleBrushPath;
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

/* JADX INFO: compiled from: Layer.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "Lcom/miui/doodle/base/core/IDoodleItem;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.miui.doodle.document.Layer$finishRemoveAction$1$1$1$1$1$1$1", f = "Layer.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
final class Layer$finishRemoveAction$1$1$1$1$1$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super IDoodleItem>, Object> {
    final /* synthetic */ IDoodleItem $item;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ Layer this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    Layer$finishRemoveAction$1$1$1$1$1$1$1(IDoodleItem iDoodleItem, Layer layer, Continuation<? super Layer$finishRemoveAction$1$1$1$1$1$1$1> continuation) {
        super(2, continuation);
        this.$item = iDoodleItem;
        this.this$0 = layer;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Layer$finishRemoveAction$1$1$1$1$1$1$1 layer$finishRemoveAction$1$1$1$1$1$1$1 = new Layer$finishRemoveAction$1$1$1$1$1$1$1(this.$item, this.this$0, continuation);
        layer$finishRemoveAction$1$1$1$1$1$1$1.L$0 = obj;
        return layer$finishRemoveAction$1$1$1$1$1$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super IDoodleItem> continuation) {
        return ((Layer$finishRemoveAction$1$1$1$1$1$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
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
            return this.$item;
        }
        IDoodleItem iDoodleItem = this.$item;
        if (iDoodleItem instanceof DoodleBrushPath) {
            ((DoodleBrushPath) iDoodleItem).elementRedrawToBitmap(this.this$0.getWidth(), this.this$0.getHeight());
        }
        return this.$item;
    }
}
