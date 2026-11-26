package com.miui.pad.feature.notes.calc;

import android.util.Log;
import com.miui.handwriting.common.MiuiHandWritingView;
import com.miui.handwriting.common.listener.CommonPageSaveListener;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: CalcEditFragment.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.miui.pad.feature.notes.calc.CalcEditFragment$onPause$1$1$1", f = "CalcEditFragment.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
final class CalcEditFragment$onPause$1$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Boolean>, Object> {
    int label;
    final /* synthetic */ CalcEditFragment this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    CalcEditFragment$onPause$1$1$1(CalcEditFragment calcEditFragment, Continuation<? super CalcEditFragment$onPause$1$1$1> continuation) {
        super(2, continuation);
        this.this$0 = calcEditFragment;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new CalcEditFragment$onPause$1$1$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Boolean> continuation) {
        return ((CalcEditFragment$onPause$1$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            MiuiHandWritingView miuiHandWritingView = this.this$0.multiHandWriteView;
            if (miuiHandWritingView == null) {
                return null;
            }
            final CalcEditFragment calcEditFragment = this.this$0;
            return Boxing.boxBoolean(miuiHandWritingView.save(new CommonPageSaveListener() { // from class: com.miui.pad.feature.notes.calc.CalcEditFragment$onPause$1$1$1$$ExternalSyntheticLambda0
                @Override // com.miui.handwriting.common.listener.CommonPageSaveListener
                public final void onDataSaved(boolean z, String str, boolean z2) {
                    CalcEditFragment$onPause$1$1$1.invokeSuspend$lambda$0(calcEditFragment, z, str, z2);
                }
            }));
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invokeSuspend$lambda$0(CalcEditFragment calcEditFragment, boolean z, String str, boolean z2) {
        Log.d(calcEditFragment.getTAG(), "onPause: SavePath:" + str);
        if (z) {
            calcEditFragment.saveToDB(false);
        }
    }
}
