package xc.engine.math.iink.ui;

import android.util.Pair;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import xc.engine.math.iink.api.IInkEngine;

/* JADX INFO: compiled from: RecognitionModel.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "xc.engine.math.iink.ui.RecognitionModel$cleanupRefresh$1$1", f = "RecognitionModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
final class RecognitionModel$cleanupRefresh$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ Pair<IInkEngine, String[]> $pending;
    int label;
    final /* synthetic */ RecognitionModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    RecognitionModel$cleanupRefresh$1$1(RecognitionModel recognitionModel, Pair<IInkEngine, String[]> pair, Continuation<? super RecognitionModel$cleanupRefresh$1$1> continuation) {
        super(2, continuation);
        this.this$0 = recognitionModel;
        this.$pending = pair;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new RecognitionModel$cleanupRefresh$1$1(this.this$0, this.$pending, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((RecognitionModel$cleanupRefresh$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        RecognitionModel recognitionModel = this.this$0;
        Object first = this.$pending.first;
        Intrinsics.checkNotNullExpressionValue(first, "first");
        Object second = this.$pending.second;
        Intrinsics.checkNotNullExpressionValue(second, "second");
        recognitionModel.contentHaveChange((IInkEngine) first, (String[]) second);
        return Unit.INSTANCE;
    }
}
