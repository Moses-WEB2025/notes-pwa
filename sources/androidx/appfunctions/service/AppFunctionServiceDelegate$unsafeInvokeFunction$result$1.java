package androidx.appfunctions.service;

import androidx.appfunctions.AppFunctionFunctionNotFoundException;
import androidx.appfunctions.ExecuteAppFunctionRequest;
import defpackage.R2;
import java.util.Map;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: AppFunctionServiceDelegate.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 1, 0}, xi = 48)
@DebugMetadata(c = "androidx.appfunctions.service.AppFunctionServiceDelegate$unsafeInvokeFunction$result$1", f = "AppFunctionServiceDelegate.kt", i = {}, l = {R2.array.ai_input_gradient_border_colors}, m = "invokeSuspend", n = {}, s = {}, v = 1)
final class AppFunctionServiceDelegate$unsafeInvokeFunction$result$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Object>, Object> {
    final /* synthetic */ Map<String, Object> $parameters;
    final /* synthetic */ ExecuteAppFunctionRequest $request;
    int label;
    final /* synthetic */ AppFunctionServiceDelegate this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    AppFunctionServiceDelegate$unsafeInvokeFunction$result$1(AppFunctionServiceDelegate appFunctionServiceDelegate, ExecuteAppFunctionRequest executeAppFunctionRequest, Map<String, ? extends Object> map, Continuation<? super AppFunctionServiceDelegate$unsafeInvokeFunction$result$1> continuation) {
        super(2, continuation);
        this.this$0 = appFunctionServiceDelegate;
        this.$request = executeAppFunctionRequest;
        this.$parameters = map;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new AppFunctionServiceDelegate$unsafeInvokeFunction$result$1(this.this$0, this.$request, this.$parameters, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Object invoke(CoroutineScope coroutineScope, Continuation<? super Object> continuation) {
        return invoke2(coroutineScope, (Continuation<Object>) continuation);
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final Object invoke2(CoroutineScope coroutineScope, Continuation<Object> continuation) {
        return ((AppFunctionServiceDelegate$unsafeInvokeFunction$result$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws AppFunctionFunctionNotFoundException {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i != 0) {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return obj;
        }
        ResultKt.throwOnFailure(obj);
        this.label = 1;
        Object objUnsafeInvoke = this.this$0.aggregatedInvoker.unsafeInvoke(this.this$0.buildAppFunctionContext(), this.$request.getFunctionIdentifier(), this.$parameters, this);
        return objUnsafeInvoke == coroutine_suspended ? coroutine_suspended : objUnsafeInvoke;
    }
}
