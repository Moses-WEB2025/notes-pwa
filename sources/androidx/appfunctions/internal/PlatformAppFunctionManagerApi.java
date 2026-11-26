package androidx.appfunctions.internal;

import android.app.appfunctions.AppFunctionException;
import android.app.appfunctions.AppFunctionManager;
import android.content.Context;
import android.os.CancellationSignal;
import android.os.OutcomeReceiver;
import androidx.appfunctions.ExecuteAppFunctionRequest;
import androidx.appfunctions.ExecuteAppFunctionResponse;
import androidx.appfunctions.metadata.AppFunctionMetadata;
import androidx.appfunctions.metadata.AppFunctionMetadataKt;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.accountsdk.account.stat.StatConstants;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;

/* JADX INFO: compiled from: PlatformAppFunctionManagerApi.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0001\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u001e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0096@¢\u0006\u0002\u0010\u0011J\u001e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0015H\u0096@¢\u0006\u0002\u0010\u0016J\u001e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0096@¢\u0006\u0002\u0010\u001dJ\u0010\u0010\u001e\u001a\u00020\u00152\u0006\u0010\u001f\u001a\u00020\u0015H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0006\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\t¨\u0006 "}, d2 = {"Landroidx/appfunctions/internal/PlatformAppFunctionManagerApi;", "Landroidx/appfunctions/internal/AppFunctionManagerApi;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;)V", "appFunctionManager", "Landroid/app/appfunctions/AppFunctionManager;", "getAppFunctionManager", "()Landroid/app/appfunctions/AppFunctionManager;", "appFunctionManager$delegate", "Lkotlin/Lazy;", "isAppFunctionEnabled", "", "packageName", "", "functionId", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setAppFunctionEnabled", "", "newEnabledState", "", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "executeAppFunction", "Landroidx/appfunctions/ExecuteAppFunctionResponse;", StatConstants.Event.REQUEST, "Landroidx/appfunctions/ExecuteAppFunctionRequest;", "functionMetadata", "Landroidx/appfunctions/metadata/AppFunctionMetadata;", "(Landroidx/appfunctions/ExecuteAppFunctionRequest;Landroidx/appfunctions/metadata/AppFunctionMetadata;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "convertToPlatformEnabledState", "enabledState", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class PlatformAppFunctionManagerApi implements AppFunctionManagerApi {

    /* JADX INFO: renamed from: appFunctionManager$delegate, reason: from kotlin metadata */
    private final Lazy appFunctionManager;
    private final Context context;

    public PlatformAppFunctionManagerApi(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.appFunctionManager = LazyKt.lazy(new Function0() { // from class: androidx.appfunctions.internal.PlatformAppFunctionManagerApi$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return PlatformAppFunctionManagerApi.appFunctionManager_delegate$lambda$0(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final AppFunctionManager getAppFunctionManager() {
        Object value = this.appFunctionManager.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (AppFunctionManager) value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final AppFunctionManager appFunctionManager_delegate$lambda$0(PlatformAppFunctionManagerApi platformAppFunctionManagerApi) {
        return (AppFunctionManager) platformAppFunctionManagerApi.context.getSystemService(AppFunctionManager.class);
    }

    @Override // androidx.appfunctions.internal.AppFunctionManagerApi
    public Object setAppFunctionEnabled(String str, int i, Continuation<? super Unit> continuation) {
        int iConvertToPlatformEnabledState = convertToPlatformEnabledState(i);
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        final CancellableContinuationImpl cancellableContinuationImpl2 = cancellableContinuationImpl;
        getAppFunctionManager().setAppFunctionEnabled(str, iConvertToPlatformEnabledState, new AppSearchUtilsKt$$ExternalSyntheticLambda0(), new OutcomeReceiver<Void, Exception>() { // from class: androidx.appfunctions.internal.PlatformAppFunctionManagerApi$setAppFunctionEnabled$2$2
            @Override // android.os.OutcomeReceiver
            public void onResult(Void result) {
                CancellableContinuation<Unit> cancellableContinuation = cancellableContinuationImpl2;
                Result.Companion companion = Result.INSTANCE;
                cancellableContinuation.resumeWith(Result.m2532constructorimpl(Unit.INSTANCE));
            }

            @Override // android.os.OutcomeReceiver
            public void onError(Exception error) {
                Intrinsics.checkNotNullParameter(error, "error");
                CancellableContinuation<Unit> cancellableContinuation = cancellableContinuationImpl2;
                Result.Companion companion = Result.INSTANCE;
                cancellableContinuation.resumeWith(Result.m2532constructorimpl(ResultKt.createFailure(error)));
            }
        });
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
    }

    private final int convertToPlatformEnabledState(int enabledState) {
        if (enabledState == 0) {
            return 0;
        }
        if (enabledState == 1) {
            return 1;
        }
        if (enabledState == 2) {
            return 2;
        }
        throw new IllegalArgumentException("Unknown enabled state " + enabledState);
    }

    @Override // androidx.appfunctions.internal.AppFunctionManagerApi
    public Object isAppFunctionEnabled(String str, String str2, Continuation<? super Boolean> continuation) {
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        final CancellableContinuationImpl cancellableContinuationImpl2 = cancellableContinuationImpl;
        getAppFunctionManager().isAppFunctionEnabled(str2, str, new AppSearchUtilsKt$$ExternalSyntheticLambda0(), new OutcomeReceiver<Boolean, Exception>() { // from class: androidx.appfunctions.internal.PlatformAppFunctionManagerApi$isAppFunctionEnabled$2$2
            @Override // android.os.OutcomeReceiver
            public void onResult(Boolean result) {
                if (result == null) {
                    CancellableContinuation<Boolean> cancellableContinuation = cancellableContinuationImpl2;
                    Result.Companion companion = Result.INSTANCE;
                    cancellableContinuation.resumeWith(Result.m2532constructorimpl(ResultKt.createFailure(new IllegalStateException("Something went wrong"))));
                } else {
                    CancellableContinuation<Boolean> cancellableContinuation2 = cancellableContinuationImpl2;
                    Result.Companion companion2 = Result.INSTANCE;
                    cancellableContinuation2.resumeWith(Result.m2532constructorimpl(result));
                }
            }

            @Override // android.os.OutcomeReceiver
            public void onError(Exception error) {
                Intrinsics.checkNotNullParameter(error, "error");
                CancellableContinuation<Boolean> cancellableContinuation = cancellableContinuationImpl2;
                Result.Companion companion = Result.INSTANCE;
                cancellableContinuation.resumeWith(Result.m2532constructorimpl(ResultKt.createFailure(error)));
            }
        });
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result;
    }

    @Override // androidx.appfunctions.internal.AppFunctionManagerApi
    public Object executeAppFunction(ExecuteAppFunctionRequest executeAppFunctionRequest, final AppFunctionMetadata appFunctionMetadata, Continuation<? super ExecuteAppFunctionResponse> continuation) {
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        final CancellableContinuationImpl cancellableContinuationImpl2 = cancellableContinuationImpl;
        final CancellationSignal cancellationSignal = new CancellationSignal();
        cancellableContinuationImpl2.invokeOnCancellation(new Function1<Throwable, Unit>() { // from class: androidx.appfunctions.internal.PlatformAppFunctionManagerApi$executeAppFunction$2$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable th) {
                cancellationSignal.cancel();
            }
        });
        getAppFunctionManager().executeAppFunction(executeAppFunctionRequest.toPlatformExecuteAppFunctionRequest(), new AppSearchUtilsKt$$ExternalSyntheticLambda0(), cancellationSignal, new OutcomeReceiver<android.app.appfunctions.ExecuteAppFunctionResponse, AppFunctionException>() { // from class: androidx.appfunctions.internal.PlatformAppFunctionManagerApi$executeAppFunction$2$3
            @Override // android.os.OutcomeReceiver
            public void onResult(android.app.appfunctions.ExecuteAppFunctionResponse result) {
                Intrinsics.checkNotNullParameter(result, "result");
                CancellableContinuation<ExecuteAppFunctionResponse> cancellableContinuation = cancellableContinuationImpl2;
                Result.Companion companion = Result.INSTANCE;
                cancellableContinuation.resumeWith(Result.m2532constructorimpl(ExecuteAppFunctionResponse.Success.INSTANCE.toCompatExecuteAppFunctionResponse(result, appFunctionMetadata)));
            }

            @Override // android.os.OutcomeReceiver
            public void onError(AppFunctionException error) {
                Intrinsics.checkNotNullParameter(error, "error");
                CancellableContinuation<ExecuteAppFunctionResponse> cancellableContinuation = cancellableContinuationImpl2;
                Result.Companion companion = Result.INSTANCE;
                cancellableContinuation.resumeWith(Result.m2532constructorimpl(new ExecuteAppFunctionResponse.Error(androidx.appfunctions.AppFunctionException.INSTANCE.fromPlatformClass(error))));
            }
        });
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result;
    }
}
