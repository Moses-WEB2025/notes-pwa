package androidx.appfunctions.service;

import android.content.Context;
import android.util.Log;
import androidx.appfunctions.AppFunctionAppUnknownException;
import androidx.appfunctions.AppFunctionCancelledException;
import androidx.appfunctions.AppFunctionContext;
import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.AppFunctionException;
import androidx.appfunctions.AppFunctionFunctionNotFoundException;
import androidx.appfunctions.ExecuteAppFunctionRequest;
import androidx.appfunctions.ExecuteAppFunctionResponse;
import androidx.appfunctions.internal.AggregatedAppFunctionInventory;
import androidx.appfunctions.internal.Constants;
import androidx.appfunctions.internal.Translator;
import androidx.appfunctions.internal.TranslatorSelector;
import androidx.appfunctions.metadata.AppFunctionComponentsMetadata;
import androidx.appfunctions.metadata.AppFunctionParameterMetadata;
import androidx.appfunctions.metadata.AppFunctionSchemaMetadata;
import androidx.appfunctions.metadata.CompileTimeAppFunctionMetadata;
import androidx.appfunctions.service.internal.AggregatedAppFunctionInvoker;
import androidx.appfunctions.service.internal.AppFunctionDataParameterExtractorKt;
import androidx.appfunctions.service.internal.AppFunctionResponseMetadataReturnValueBuilderKt;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.accountsdk.account.stat.StatConstants;
import defpackage.R2;
import java.util.Map;
import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.collections.MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;

/* JADX INFO: compiled from: AppFunctionServiceDelegate.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0004\b\f\u0010\rJ\u0016\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0086@¢\u0006\u0002\u0010\u0014J\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u00132\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0002J0\u0010\u001a\u001a\u0010\u0012\u0004\u0012\u00020\u001c\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u001b2\u0006\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0016H\u0002JF\u0010 \u001a\u00020\u00112\u0006\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\"2\u0014\u0010#\u001a\u0010\u0012\u0004\u0012\u00020\u001c\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u001b2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0016H\u0082@¢\u0006\u0002\u0010$J\b\u0010%\u001a\u00020&H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\n \u000f*\u0004\u0018\u00010\u00030\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Landroidx/appfunctions/service/AppFunctionServiceDelegate;", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "mainCoroutineContext", "Lkotlin/coroutines/CoroutineContext;", "aggregatedInventory", "Landroidx/appfunctions/internal/AggregatedAppFunctionInventory;", "aggregatedInvoker", "Landroidx/appfunctions/service/internal/AggregatedAppFunctionInvoker;", "translatorSelector", "Landroidx/appfunctions/internal/TranslatorSelector;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Lkotlin/coroutines/CoroutineContext;Landroidx/appfunctions/internal/AggregatedAppFunctionInventory;Landroidx/appfunctions/service/internal/AggregatedAppFunctionInvoker;Landroidx/appfunctions/internal/TranslatorSelector;)V", "appContext", "kotlin.jvm.PlatformType", "executeFunction", "Landroidx/appfunctions/ExecuteAppFunctionResponse;", "executeAppFunctionRequest", "Landroidx/appfunctions/ExecuteAppFunctionRequest;", "(Landroidx/appfunctions/ExecuteAppFunctionRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getTranslator", "Landroidx/appfunctions/internal/Translator;", StatConstants.Event.REQUEST, "schemaMetadata", "Landroidx/appfunctions/metadata/AppFunctionSchemaMetadata;", "extractParameters", "", "", "appFunctionMetadata", "Landroidx/appfunctions/metadata/CompileTimeAppFunctionMetadata;", "translator", "unsafeInvokeFunction", "componentsMetadata", "Landroidx/appfunctions/metadata/AppFunctionComponentsMetadata;", "parameters", "(Landroidx/appfunctions/ExecuteAppFunctionRequest;Landroidx/appfunctions/metadata/CompileTimeAppFunctionMetadata;Landroidx/appfunctions/metadata/AppFunctionComponentsMetadata;Ljava/util/Map;Landroidx/appfunctions/internal/Translator;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "buildAppFunctionContext", "Landroidx/appfunctions/AppFunctionContext;", "appfunctions-service"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class AppFunctionServiceDelegate {
    private final AggregatedAppFunctionInventory aggregatedInventory;
    private final AggregatedAppFunctionInvoker aggregatedInvoker;
    private final Context appContext;
    private final CoroutineContext mainCoroutineContext;
    private final TranslatorSelector translatorSelector;

    /* JADX INFO: renamed from: androidx.appfunctions.service.AppFunctionServiceDelegate$executeFunction$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AppFunctionServiceDelegate.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "androidx.appfunctions.service.AppFunctionServiceDelegate", f = "AppFunctionServiceDelegate.kt", i = {0}, l = {76}, m = "executeFunction", n = {"executeAppFunctionRequest"}, s = {"L$0"}, v = 1)
    static final class C01641 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C01641(Continuation<? super C01641> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AppFunctionServiceDelegate.this.executeFunction(null, this);
        }
    }

    /* JADX INFO: renamed from: androidx.appfunctions.service.AppFunctionServiceDelegate$unsafeInvokeFunction$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AppFunctionServiceDelegate.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "androidx.appfunctions.service.AppFunctionServiceDelegate", f = "AppFunctionServiceDelegate.kt", i = {0, 0, 0}, l = {R2.array.ai_edt_txt_blend_colors}, m = "unsafeInvokeFunction", n = {"appFunctionMetadata", "componentsMetadata", "translator"}, s = {"L$0", "L$1", "L$2"}, v = 1)
    static final class C01651 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        C01651(Continuation<? super C01651> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AppFunctionServiceDelegate.this.unsafeInvokeFunction(null, null, null, null, null, this);
        }
    }

    public AppFunctionServiceDelegate(Context context, CoroutineContext mainCoroutineContext, AggregatedAppFunctionInventory aggregatedInventory, AggregatedAppFunctionInvoker aggregatedInvoker, TranslatorSelector translatorSelector) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(mainCoroutineContext, "mainCoroutineContext");
        Intrinsics.checkNotNullParameter(aggregatedInventory, "aggregatedInventory");
        Intrinsics.checkNotNullParameter(aggregatedInvoker, "aggregatedInvoker");
        Intrinsics.checkNotNullParameter(translatorSelector, "translatorSelector");
        this.mainCoroutineContext = mainCoroutineContext;
        this.aggregatedInventory = aggregatedInventory;
        this.aggregatedInvoker = aggregatedInvoker;
        this.translatorSelector = translatorSelector;
        this.appContext = context.getApplicationContext();
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0016  */
    /* JADX WARN: Multi-variable type inference failed */
    public final Object executeFunction(ExecuteAppFunctionRequest executeAppFunctionRequest, Continuation<? super ExecuteAppFunctionResponse> continuation) throws AppFunctionException {
        C01641 c01641;
        Exception exc;
        ExecuteAppFunctionRequest executeAppFunctionRequest2;
        CancellationException cancellationException;
        ExecuteAppFunctionRequest executeAppFunctionRequest3;
        AppFunctionException appFunctionException;
        ExecuteAppFunctionRequest executeAppFunctionRequest4;
        if (continuation instanceof C01641) {
            c01641 = (C01641) continuation;
            if ((c01641.label & Integer.MIN_VALUE) != 0) {
                c01641.label -= Integer.MIN_VALUE;
            } else {
                c01641 = new C01641(continuation);
            }
        } else {
            c01641 = new C01641(continuation);
        }
        C01641 c01642 = c01641;
        Object objUnsafeInvokeFunction = c01642.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = c01642.label;
        if (i == 0) {
            ResultKt.throwOnFailure(objUnsafeInvokeFunction);
            try {
                CompileTimeAppFunctionMetadata compileTimeAppFunctionMetadata = this.aggregatedInventory.getFunctionIdToMetadataMap().get(executeAppFunctionRequest.getFunctionIdentifier());
                try {
                    if (compileTimeAppFunctionMetadata == null) {
                        Log.d(Constants.APP_FUNCTIONS_TAG, executeAppFunctionRequest.getFunctionIdentifier() + " is not available");
                        throw new AppFunctionFunctionNotFoundException(executeAppFunctionRequest.getFunctionIdentifier() + " is not available");
                    }
                    Translator translator = getTranslator(executeAppFunctionRequest, compileTimeAppFunctionMetadata.getSchema());
                    Map<String, Object> mapExtractParameters = extractParameters(executeAppFunctionRequest, compileTimeAppFunctionMetadata, translator);
                    AppFunctionComponentsMetadata componentsMetadata = this.aggregatedInventory.getComponentsMetadata();
                    c01642.L$0 = executeAppFunctionRequest;
                    c01642.label = 1;
                    objUnsafeInvokeFunction = unsafeInvokeFunction(executeAppFunctionRequest, compileTimeAppFunctionMetadata, componentsMetadata, mapExtractParameters, translator, c01642);
                    if (objUnsafeInvokeFunction == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    executeAppFunctionRequest = executeAppFunctionRequest;
                } catch (AppFunctionException e) {
                    appFunctionException = e;
                    executeAppFunctionRequest4 = 1;
                } catch (CancellationException e2) {
                    cancellationException = e2;
                    executeAppFunctionRequest3 = 1;
                    Log.d(Constants.APP_FUNCTIONS_TAG, "Invocation of " + executeAppFunctionRequest3.getFunctionIdentifier() + " was cancelled", cancellationException);
                    throw new AppFunctionCancelledException(cancellationException.getMessage());
                } catch (Exception e3) {
                    exc = e3;
                    executeAppFunctionRequest2 = 1;
                    Log.d(Constants.APP_FUNCTIONS_TAG, "Failed to invoke " + executeAppFunctionRequest2.getFunctionIdentifier(), exc);
                    throw new AppFunctionAppUnknownException(exc.getMessage());
                }
            } catch (AppFunctionException e4) {
                e = e4;
                appFunctionException = e;
                executeAppFunctionRequest4 = executeAppFunctionRequest;
            } catch (CancellationException e5) {
                e = e5;
                cancellationException = e;
                executeAppFunctionRequest3 = executeAppFunctionRequest;
                Log.d(Constants.APP_FUNCTIONS_TAG, "Invocation of " + executeAppFunctionRequest3.getFunctionIdentifier() + " was cancelled", cancellationException);
                throw new AppFunctionCancelledException(cancellationException.getMessage());
            } catch (Exception e6) {
                e = e6;
                exc = e;
                executeAppFunctionRequest2 = executeAppFunctionRequest;
                Log.d(Constants.APP_FUNCTIONS_TAG, "Failed to invoke " + executeAppFunctionRequest2.getFunctionIdentifier(), exc);
                throw new AppFunctionAppUnknownException(exc.getMessage());
            }
            Log.d(Constants.APP_FUNCTIONS_TAG, "Failed to invoke " + executeAppFunctionRequest4.getFunctionIdentifier(), appFunctionException);
            throw appFunctionException;
        }
        if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        executeAppFunctionRequest = (ExecuteAppFunctionRequest) c01642.L$0;
        try {
            ResultKt.throwOnFailure(objUnsafeInvokeFunction);
            executeAppFunctionRequest = executeAppFunctionRequest;
        } catch (AppFunctionException e7) {
            e = e7;
            appFunctionException = e;
            executeAppFunctionRequest4 = executeAppFunctionRequest;
            Log.d(Constants.APP_FUNCTIONS_TAG, "Failed to invoke " + executeAppFunctionRequest4.getFunctionIdentifier(), appFunctionException);
            throw appFunctionException;
        } catch (CancellationException e8) {
            e = e8;
            cancellationException = e;
            executeAppFunctionRequest3 = executeAppFunctionRequest;
            Log.d(Constants.APP_FUNCTIONS_TAG, "Invocation of " + executeAppFunctionRequest3.getFunctionIdentifier() + " was cancelled", cancellationException);
            throw new AppFunctionCancelledException(cancellationException.getMessage());
        } catch (Exception e9) {
            e = e9;
            exc = e;
            executeAppFunctionRequest2 = executeAppFunctionRequest;
            Log.d(Constants.APP_FUNCTIONS_TAG, "Failed to invoke " + executeAppFunctionRequest2.getFunctionIdentifier(), exc);
            throw new AppFunctionAppUnknownException(exc.getMessage());
        }
        return (ExecuteAppFunctionResponse) objUnsafeInvokeFunction;
    }

    private final Translator getTranslator(ExecuteAppFunctionRequest request, AppFunctionSchemaMetadata schemaMetadata) {
        if (request.getUseJetpackSchema() || schemaMetadata == null) {
            return null;
        }
        return this.translatorSelector.getTranslator(schemaMetadata);
    }

    private final Map<String, Object> extractParameters(ExecuteAppFunctionRequest request, CompileTimeAppFunctionMetadata appFunctionMetadata, Translator translator) {
        AppFunctionData functionParameters;
        if (translator == null || (functionParameters = translator.upgradeRequest(request.getFunctionParameters())) == null) {
            functionParameters = request.getFunctionParameters();
        }
        Map mapCreateMapBuilder = MapsKt.createMapBuilder();
        for (AppFunctionParameterMetadata appFunctionParameterMetadata : appFunctionMetadata.getParameters()) {
            mapCreateMapBuilder.put(appFunctionParameterMetadata.getName(), AppFunctionDataParameterExtractorKt.unsafeGetParameterValue(functionParameters, appFunctionParameterMetadata));
        }
        return MapsKt.build(mapCreateMapBuilder);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    public final Object unsafeInvokeFunction(ExecuteAppFunctionRequest executeAppFunctionRequest, CompileTimeAppFunctionMetadata compileTimeAppFunctionMetadata, AppFunctionComponentsMetadata appFunctionComponentsMetadata, Map<String, ? extends Object> map, Translator translator, Continuation<? super ExecuteAppFunctionResponse> continuation) throws AppFunctionAppUnknownException {
        C01651 c01651;
        AppFunctionData appFunctionDataDowngradeResponse;
        if (continuation instanceof C01651) {
            c01651 = (C01651) continuation;
            if ((c01651.label & Integer.MIN_VALUE) != 0) {
                c01651.label -= Integer.MIN_VALUE;
            } else {
                c01651 = new C01651(continuation);
            }
        } else {
            c01651 = new C01651(continuation);
        }
        Object objWithContext = c01651.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = c01651.label;
        if (i == 0) {
            ResultKt.throwOnFailure(objWithContext);
            CoroutineContext coroutineContext = this.mainCoroutineContext;
            AppFunctionServiceDelegate$unsafeInvokeFunction$result$1 appFunctionServiceDelegate$unsafeInvokeFunction$result$1 = new AppFunctionServiceDelegate$unsafeInvokeFunction$result$1(this, executeAppFunctionRequest, map, null);
            c01651.L$0 = compileTimeAppFunctionMetadata;
            c01651.L$1 = appFunctionComponentsMetadata;
            c01651.L$2 = translator;
            c01651.label = 1;
            objWithContext = BuildersKt.withContext(coroutineContext, appFunctionServiceDelegate$unsafeInvokeFunction$result$1, c01651);
            if (objWithContext == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            translator = (Translator) c01651.L$2;
            appFunctionComponentsMetadata = (AppFunctionComponentsMetadata) c01651.L$1;
            compileTimeAppFunctionMetadata = (CompileTimeAppFunctionMetadata) c01651.L$0;
            ResultKt.throwOnFailure(objWithContext);
        }
        AppFunctionData appFunctionDataUnsafeBuildReturnValue = AppFunctionResponseMetadataReturnValueBuilderKt.unsafeBuildReturnValue(compileTimeAppFunctionMetadata.getResponse(), objWithContext, appFunctionComponentsMetadata);
        if (translator != null && (appFunctionDataDowngradeResponse = translator.downgradeResponse(appFunctionDataUnsafeBuildReturnValue)) != null) {
            appFunctionDataUnsafeBuildReturnValue = appFunctionDataDowngradeResponse;
        }
        return new ExecuteAppFunctionResponse.Success(appFunctionDataUnsafeBuildReturnValue);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final AppFunctionContext buildAppFunctionContext() {
        return new AppFunctionContext() { // from class: androidx.appfunctions.service.AppFunctionServiceDelegate.buildAppFunctionContext.1
            @Override // androidx.appfunctions.AppFunctionContext
            public Context getContext() {
                Context context = AppFunctionServiceDelegate.this.appContext;
                Intrinsics.checkNotNullExpressionValue(context, "access$getAppContext$p(...)");
                return context;
            }
        };
    }
}
