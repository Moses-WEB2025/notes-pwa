package xc.engine.math.iink.ui;

import defpackage.R2;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.sync.Mutex;
import xc.engine.math.iink.api.IInkEngine;

/* JADX INFO: compiled from: RecognitionModel.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "xc.engine.math.iink.ui.RecognitionModel$offScreenEditorListener$1$contentChanged$1", f = "RecognitionModel.kt", i = {0, 0}, l = {R2.attr.initialExpandedChildrenCount}, m = "invokeSuspend", n = {"$this$withLock_u24default$iv", "$i$f$withLock"}, s = {"L$0", "I$0"})
final class RecognitionModel$offScreenEditorListener$1$contentChanged$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ String[] $blockIds;
    final /* synthetic */ IInkEngine $editor;
    int I$0;
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;
    final /* synthetic */ RecognitionModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    RecognitionModel$offScreenEditorListener$1$contentChanged$1(RecognitionModel recognitionModel, IInkEngine iInkEngine, String[] strArr, Continuation<? super RecognitionModel$offScreenEditorListener$1$contentChanged$1> continuation) {
        super(2, continuation);
        this.this$0 = recognitionModel;
        this.$editor = iInkEngine;
        this.$blockIds = strArr;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new RecognitionModel$offScreenEditorListener$1$contentChanged$1(this.this$0, this.$editor, this.$blockIds, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((RecognitionModel$offScreenEditorListener$1$contentChanged$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Mutex mutex;
        RecognitionModel recognitionModel;
        IInkEngine iInkEngine;
        String[] strArr;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Mutex mutex2 = this.this$0.mutex;
            RecognitionModel recognitionModel2 = this.this$0;
            IInkEngine iInkEngine2 = this.$editor;
            String[] strArr2 = this.$blockIds;
            this.L$0 = mutex2;
            this.L$1 = recognitionModel2;
            this.L$2 = iInkEngine2;
            this.L$3 = strArr2;
            this.I$0 = 0;
            this.label = 1;
            if (mutex2.lock(null, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            mutex = mutex2;
            recognitionModel = recognitionModel2;
            iInkEngine = iInkEngine2;
            strArr = strArr2;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            strArr = (String[]) this.L$3;
            iInkEngine = (IInkEngine) this.L$2;
            recognitionModel = (RecognitionModel) this.L$1;
            mutex = (Mutex) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        try {
            recognitionModel.contentHaveChange(iInkEngine, strArr);
            Unit unit = Unit.INSTANCE;
            return Unit.INSTANCE;
        } finally {
            mutex.unlock(null);
        }
    }
}
