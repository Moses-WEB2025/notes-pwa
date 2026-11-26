package com.miui.floatwindow;

import com.miui.common.app.BaseNoteApp;
import com.miui.common.tool.Logger;
import com.miui.floatwindow.utils.DockUtils;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SpillingKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* JADX INFO: compiled from: FloatWindowManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0018\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/flow/FlowCollector;", ""}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.miui.floatwindow.FloatWindowManager$mAddViewFlow$2$1", f = "FloatWindowManager.kt", i = {0, 0}, l = {49}, m = "invokeSuspend", n = {"$this$flow", "result"}, s = {"L$0", "L$1"})
final class FloatWindowManager$mAddViewFlow$2$1 extends SuspendLambda implements Function2<FlowCollector<? super boolean[]>, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;

    FloatWindowManager$mAddViewFlow$2$1(Continuation<? super FloatWindowManager$mAddViewFlow$2$1> continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        FloatWindowManager$mAddViewFlow$2$1 floatWindowManager$mAddViewFlow$2$1 = new FloatWindowManager$mAddViewFlow$2$1(continuation);
        floatWindowManager$mAddViewFlow$2$1.L$0 = obj;
        return floatWindowManager$mAddViewFlow$2$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(FlowCollector<? super boolean[]> flowCollector, Continuation<? super Unit> continuation) {
        return ((FloatWindowManager$mAddViewFlow$2$1) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        FlowCollector flowCollector = (FlowCollector) this.L$0;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Logger.INSTANCE.d("FloatWindowManager", "addViewTask ..AsyncTask..doInBackground");
            boolean[] zArr = {DockUtils.getDockMode(BaseNoteApp.INSTANCE.getInstance()), DockUtils.isOldToolboxSupportQuickNote(BaseNoteApp.INSTANCE.getInstance()), DockUtils.isDockSupport(BaseNoteApp.INSTANCE.getInstance())};
            this.L$0 = SpillingKt.nullOutSpilledVariable(flowCollector);
            this.L$1 = SpillingKt.nullOutSpilledVariable(zArr);
            this.label = 1;
            if (flowCollector.emit(zArr, this) == coroutine_suspended) {
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
