package com.miui.floatwindow;

import com.miui.common.tool.Logger;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* JADX INFO: compiled from: FloatWindowManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n"}, d2 = {"<anonymous>", "", "it", ""}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.miui.floatwindow.FloatWindowManager$mAddViewFlow$2$3", f = "FloatWindowManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
final class FloatWindowManager$mAddViewFlow$2$3 extends SuspendLambda implements Function2<boolean[], Continuation<? super Unit>, Object> {
    /* synthetic */ Object L$0;
    int label;

    FloatWindowManager$mAddViewFlow$2$3(Continuation<? super FloatWindowManager$mAddViewFlow$2$3> continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        FloatWindowManager$mAddViewFlow$2$3 floatWindowManager$mAddViewFlow$2$3 = new FloatWindowManager$mAddViewFlow$2$3(continuation);
        floatWindowManager$mAddViewFlow$2$3.L$0 = obj;
        return floatWindowManager$mAddViewFlow$2$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(boolean[] zArr, Continuation<? super Unit> continuation) {
        return ((FloatWindowManager$mAddViewFlow$2$3) create(zArr, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        boolean[] zArr = (boolean[]) this.L$0;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Logger.INSTANCE.d("FloatWindowManager", "addViewTask ..onEach " + zArr);
        return Unit.INSTANCE;
    }
}
