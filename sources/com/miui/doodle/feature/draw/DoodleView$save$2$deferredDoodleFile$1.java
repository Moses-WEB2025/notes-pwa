package com.miui.doodle.feature.draw;

import java.io.File;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: DoodleView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "Ljava/io/File;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.miui.doodle.feature.draw.DoodleView$save$2$deferredDoodleFile$1", f = "DoodleView.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
final class DoodleView$save$2$deferredDoodleFile$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super File>, Object> {
    final /* synthetic */ boolean $ignoreCache;
    int label;
    final /* synthetic */ DoodleView this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    DoodleView$save$2$deferredDoodleFile$1(DoodleView doodleView, boolean z, Continuation<? super DoodleView$save$2$deferredDoodleFile$1> continuation) {
        super(2, continuation);
        this.this$0 = doodleView;
        this.$ignoreCache = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new DoodleView$save$2$deferredDoodleFile$1(this.this$0, this.$ignoreCache, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super File> continuation) {
        return ((DoodleView$save$2$deferredDoodleFile$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return this.this$0.saveDoodleBitmap(this.$ignoreCache);
    }
}
