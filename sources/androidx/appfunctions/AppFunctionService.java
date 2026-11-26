package androidx.appfunctions;

import android.content.pm.SigningInfo;
import android.os.CancellationSignal;
import android.os.OutcomeReceiver;
import androidx.appfunctions.internal.Dispatchers;
import androidx.appfunctions.metadata.AppFunctionMetadataKt;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.accountsdk.account.stat.StatConstants;
import com.xiaomi.mirror.synergy.CallMethod;
import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Job;

/* JADX INFO: compiled from: AppFunctionService.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b'\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J:\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00130\u0011J\u0016\u0010\u0014\u001a\u00020\u00152\u0006\u0010\b\u001a\u00020\u0016H§@¢\u0006\u0002\u0010\u0017J\b\u0010\u0018\u001a\u00020\u0007H\u0017R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Landroidx/appfunctions/AppFunctionService;", "Landroid/app/appfunctions/AppFunctionService;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "workerCoroutineScope", "Lkotlinx/coroutines/CoroutineScope;", "onExecuteFunction", "", StatConstants.Event.REQUEST, "Landroid/app/appfunctions/ExecuteAppFunctionRequest;", "callingPackage", "", "signingInfo", "Landroid/content/pm/SigningInfo;", "cancellationSignal", "Landroid/os/CancellationSignal;", CallMethod.ARG_CALLBACK, "Landroid/os/OutcomeReceiver;", "Landroid/app/appfunctions/ExecuteAppFunctionResponse;", "Landroid/app/appfunctions/AppFunctionException;", "executeFunction", "Landroidx/appfunctions/ExecuteAppFunctionResponse;", "Landroidx/appfunctions/ExecuteAppFunctionRequest;", "(Landroidx/appfunctions/ExecuteAppFunctionRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onDestroy", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
public abstract class AppFunctionService extends android.app.appfunctions.AppFunctionService {
    private final CoroutineScope workerCoroutineScope = CoroutineScopeKt.CoroutineScope(Dispatchers.INSTANCE.getWorker());

    public abstract Object executeFunction(ExecuteAppFunctionRequest executeAppFunctionRequest, Continuation<? super ExecuteAppFunctionResponse> continuation);

    public final void onExecuteFunction(android.app.appfunctions.ExecuteAppFunctionRequest request, String callingPackage, SigningInfo signingInfo, CancellationSignal cancellationSignal, OutcomeReceiver<android.app.appfunctions.ExecuteAppFunctionResponse, android.app.appfunctions.AppFunctionException> callback) {
        Intrinsics.checkNotNullParameter(request, "request");
        Intrinsics.checkNotNullParameter(callingPackage, "callingPackage");
        Intrinsics.checkNotNullParameter(signingInfo, "signingInfo");
        Intrinsics.checkNotNullParameter(cancellationSignal, "cancellationSignal");
        Intrinsics.checkNotNullParameter(callback, "callback");
        final Job jobLaunch$default = BuildersKt__Builders_commonKt.launch$default(this.workerCoroutineScope, null, null, new AppFunctionService$onExecuteFunction$functionExecutionJob$1(this, request, callingPackage, callback, null), 3, null);
        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: androidx.appfunctions.AppFunctionService$$ExternalSyntheticLambda0
            @Override // android.os.CancellationSignal.OnCancelListener
            public final void onCancel() {
                Job.DefaultImpls.cancel$default(jobLaunch$default, (CancellationException) null, 1, (Object) null);
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        CoroutineScopeKt.cancel$default(this.workerCoroutineScope, null, 1, null);
    }
}
