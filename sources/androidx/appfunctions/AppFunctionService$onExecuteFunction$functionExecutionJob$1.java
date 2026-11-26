package androidx.appfunctions;

import android.os.OutcomeReceiver;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: AppFunctionService.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 1, 0}, xi = 48)
@DebugMetadata(c = "androidx.appfunctions.AppFunctionService$onExecuteFunction$functionExecutionJob$1", f = "AppFunctionService.kt", i = {}, l = {89, 96}, m = "invokeSuspend", n = {}, s = {}, v = 1)
final class AppFunctionService$onExecuteFunction$functionExecutionJob$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ OutcomeReceiver<android.app.appfunctions.ExecuteAppFunctionResponse, android.app.appfunctions.AppFunctionException> $callback;
    final /* synthetic */ String $callingPackage;
    final /* synthetic */ android.app.appfunctions.ExecuteAppFunctionRequest $request;
    int label;
    final /* synthetic */ AppFunctionService this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    AppFunctionService$onExecuteFunction$functionExecutionJob$1(AppFunctionService appFunctionService, android.app.appfunctions.ExecuteAppFunctionRequest executeAppFunctionRequest, String str, OutcomeReceiver<android.app.appfunctions.ExecuteAppFunctionResponse, android.app.appfunctions.AppFunctionException> outcomeReceiver, Continuation<? super AppFunctionService$onExecuteFunction$functionExecutionJob$1> continuation) {
        super(2, continuation);
        this.this$0 = appFunctionService;
        this.$request = executeAppFunctionRequest;
        this.$callingPackage = str;
        this.$callback = outcomeReceiver;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new AppFunctionService$onExecuteFunction$functionExecutionJob$1(this.this$0, this.$request, this.$callingPackage, this.$callback, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((AppFunctionService$onExecuteFunction$functionExecutionJob$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0063, code lost:
    
        if (r8 == r1) goto L22;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r8) {
        /*
            Method dump skipped, instruction units count: 209
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appfunctions.AppFunctionService$onExecuteFunction$functionExecutionJob$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
