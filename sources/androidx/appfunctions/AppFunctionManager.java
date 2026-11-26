package androidx.appfunctions;

import android.content.Context;
import android.os.Build;
import androidx.appfunctions.internal.AppFunctionManagerApi;
import androidx.appfunctions.internal.AppFunctionReader;
import androidx.appfunctions.internal.AppSearchAppFunctionReader;
import androidx.appfunctions.internal.Dependencies;
import androidx.appfunctions.internal.ExtensionAppFunctionManagerApi;
import androidx.appfunctions.internal.NullTranslatorSelector;
import androidx.appfunctions.internal.PlatformAppFunctionManagerApi;
import androidx.appfunctions.internal.Translator;
import androidx.appfunctions.internal.TranslatorSelector;
import androidx.appfunctions.metadata.AppFunctionMetadata;
import androidx.appfunctions.metadata.AppFunctionMetadataKt;
import androidx.appfunctions.metadata.AppFunctionPackageMetadata;
import androidx.appfunctions.metadata.AppFunctionSchemaMetadata;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.accountsdk.account.stat.StatConstants;
import com.xiaomi.onetrack.api.ah;
import defpackage.R2;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.annotation.AnnotationRetention;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;

/* JADX INFO: compiled from: AppFunctionManager.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 *2\u00020\u0001:\u0002)*B+\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0004\b\n\u0010\u000bJ\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0086@¢\u0006\u0002\u0010\u0010J\u001e\u0010\f\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0087@¢\u0006\u0002\u0010\u0012J\u001e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0086@¢\u0006\u0002\u0010\u0017J\u0016\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0087@¢\u0006\u0002\u0010\u001cJ$\u0010\u001d\u001a\u00020\u00192\b\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\b\u0010 \u001a\u0004\u0018\u00010!2\u0006\u0010\"\u001a\u00020\u0019H\u0002J\u001c\u0010#\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020&0%0$2\u0006\u0010'\u001a\u00020(H\u0007R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006+"}, d2 = {"Landroidx/appfunctions/AppFunctionManager;", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "appFunctionReader", "Landroidx/appfunctions/internal/AppFunctionReader;", "appFunctionManagerApi", "Landroidx/appfunctions/internal/AppFunctionManagerApi;", "translatorSelector", "Landroidx/appfunctions/internal/TranslatorSelector;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Landroidx/appfunctions/internal/AppFunctionReader;Landroidx/appfunctions/internal/AppFunctionManagerApi;Landroidx/appfunctions/internal/TranslatorSelector;)V", "isAppFunctionEnabled", "", "functionId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "packageName", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setAppFunctionEnabled", "", "newEnabledState", "", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "executeAppFunction", "Landroidx/appfunctions/ExecuteAppFunctionResponse;", StatConstants.Event.REQUEST, "Landroidx/appfunctions/ExecuteAppFunctionRequest;", "(Landroidx/appfunctions/ExecuteAppFunctionRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "processResponse", "translator", "Landroidx/appfunctions/internal/Translator;", "functionMetadata", "Landroidx/appfunctions/metadata/AppFunctionMetadata;", ah.H, "observeAppFunctions", "Lkotlinx/coroutines/flow/Flow;", "", "Landroidx/appfunctions/metadata/AppFunctionPackageMetadata;", "searchSpec", "Landroidx/appfunctions/AppFunctionSearchSpec;", "EnabledState", "Companion", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class AppFunctionManager {
    public static final int APP_FUNCTION_STATE_DEFAULT = 0;
    public static final int APP_FUNCTION_STATE_DISABLED = 2;
    public static final int APP_FUNCTION_STATE_ENABLED = 1;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final long LEGACY_SDK_GLOBAL_SCHEMA_VERSION = 1;
    private final AppFunctionManagerApi appFunctionManagerApi;
    private final AppFunctionReader appFunctionReader;
    private final Context context;
    private final TranslatorSelector translatorSelector;

    /* JADX INFO: compiled from: AppFunctionManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"}, d2 = {"Landroidx/appfunctions/AppFunctionManager$EnabledState;", "", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
    @Retention(RetentionPolicy.SOURCE)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    public @interface EnabledState {
    }

    /* JADX INFO: renamed from: androidx.appfunctions.AppFunctionManager$executeAppFunction$1, reason: invalid class name */
    /* JADX INFO: compiled from: AppFunctionManager.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "androidx.appfunctions.AppFunctionManager", f = "AppFunctionManager.kt", i = {0, 1, 1}, l = {114, R2.array.ai_material_view_bg_blend_modes}, m = "executeAppFunction", n = {StatConstants.Event.REQUEST, "functionMetadata", "translator"}, s = {"L$0", "L$0", "L$1"}, v = 1)
    static final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AppFunctionManager.this.executeAppFunction(null, this);
        }
    }

    @JvmStatic
    public static final AppFunctionManager getInstance(Context context) {
        return INSTANCE.getInstance(context);
    }

    public AppFunctionManager(Context context, AppFunctionReader appFunctionReader, AppFunctionManagerApi appFunctionManagerApi, TranslatorSelector translatorSelector) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(appFunctionReader, "appFunctionReader");
        Intrinsics.checkNotNullParameter(appFunctionManagerApi, "appFunctionManagerApi");
        Intrinsics.checkNotNullParameter(translatorSelector, "translatorSelector");
        this.context = context;
        this.appFunctionReader = appFunctionReader;
        this.appFunctionManagerApi = appFunctionManagerApi;
        this.translatorSelector = translatorSelector;
    }

    public /* synthetic */ AppFunctionManager(Context context, AppFunctionReader appFunctionReader, AppFunctionManagerApi appFunctionManagerApi, NullTranslatorSelector nullTranslatorSelector, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, appFunctionReader, appFunctionManagerApi, (i & 8) != 0 ? new NullTranslatorSelector() : nullTranslatorSelector);
    }

    public final Object isAppFunctionEnabled(String str, Continuation<? super Boolean> continuation) {
        String packageName = this.context.getPackageName();
        Intrinsics.checkNotNullExpressionValue(packageName, "getPackageName(...)");
        return isAppFunctionEnabled(packageName, str, continuation);
    }

    public final Object isAppFunctionEnabled(String str, String str2, Continuation<? super Boolean> continuation) {
        return this.appFunctionManagerApi.isAppFunctionEnabled(str, str2, continuation);
    }

    public final Object setAppFunctionEnabled(String str, int i, Continuation<? super Unit> continuation) {
        Object appFunctionEnabled = this.appFunctionManagerApi.setAppFunctionEnabled(str, i, continuation);
        return appFunctionEnabled == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? appFunctionEnabled : Unit.INSTANCE;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0016  */
    public final Object executeAppFunction(ExecuteAppFunctionRequest executeAppFunctionRequest, Continuation<? super ExecuteAppFunctionResponse> continuation) {
        AnonymousClass1 anonymousClass1;
        AppFunctionMetadata appFunctionMetadata;
        Translator translator;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            if ((anonymousClass1.label & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label -= Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        } else {
            anonymousClass1 = new AnonymousClass1(continuation);
        }
        Object appFunctionMetadata2 = anonymousClass1.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = anonymousClass1.label;
        try {
            if (i == 0) {
                ResultKt.throwOnFailure(appFunctionMetadata2);
                AppFunctionReader appFunctionReader = this.appFunctionReader;
                String functionIdentifier = executeAppFunctionRequest.getFunctionIdentifier();
                String targetPackageName = executeAppFunctionRequest.getTargetPackageName();
                anonymousClass1.L$0 = executeAppFunctionRequest;
                anonymousClass1.label = 1;
                appFunctionMetadata2 = appFunctionReader.getAppFunctionMetadata(functionIdentifier, targetPackageName, anonymousClass1);
                if (appFunctionMetadata2 == coroutine_suspended) {
                }
                return coroutine_suspended;
            }
            if (i == 1) {
                executeAppFunctionRequest = (ExecuteAppFunctionRequest) anonymousClass1.L$0;
                ResultKt.throwOnFailure(appFunctionMetadata2);
            } else {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                translator = (Translator) anonymousClass1.L$1;
                appFunctionMetadata = (AppFunctionMetadata) anonymousClass1.L$0;
                ResultKt.throwOnFailure(appFunctionMetadata2);
            }
            return processResponse(translator, appFunctionMetadata, (ExecuteAppFunctionResponse) appFunctionMetadata2);
            ExecuteAppFunctionRequest executeAppFunctionRequestCopy$default = executeAppFunctionRequest;
            AppFunctionMetadata appFunctionMetadata3 = (AppFunctionMetadata) appFunctionMetadata2;
            if (appFunctionMetadata3 == null) {
                throw new AppFunctionFunctionNotFoundException("App function not found with identifier: " + executeAppFunctionRequestCopy$default.getFunctionIdentifier() + " under package: " + executeAppFunctionRequestCopy$default.getTargetPackageName());
            }
            AppFunctionSchemaMetadata schema = appFunctionMetadata3.getSchema();
            Translator translator2 = (schema == null || schema.getVersion() != 1) ? null : this.translatorSelector.getTranslator(appFunctionMetadata3.getSchema());
            if (translator2 != null) {
                executeAppFunctionRequestCopy$default = ExecuteAppFunctionRequest.copy$default(executeAppFunctionRequestCopy$default, null, null, translator2.downgradeRequest(executeAppFunctionRequestCopy$default.getFunctionParameters()), false, 11, null);
            }
            AppFunctionManagerApi appFunctionManagerApi = this.appFunctionManagerApi;
            anonymousClass1.L$0 = appFunctionMetadata3;
            anonymousClass1.L$1 = translator2;
            anonymousClass1.label = 2;
            Object objExecuteAppFunction = appFunctionManagerApi.executeAppFunction(executeAppFunctionRequestCopy$default, appFunctionMetadata3, anonymousClass1);
            if (objExecuteAppFunction != coroutine_suspended) {
                appFunctionMetadata = appFunctionMetadata3;
                translator = translator2;
                appFunctionMetadata2 = objExecuteAppFunction;
                return processResponse(translator, appFunctionMetadata, (ExecuteAppFunctionResponse) appFunctionMetadata2);
            }
            return coroutine_suspended;
        } catch (AppFunctionFunctionNotFoundException e) {
            return new ExecuteAppFunctionResponse.Error(e);
        } catch (Exception e2) {
            return new ExecuteAppFunctionResponse.Error(new AppFunctionSystemUnknownException("Something went wrong when querying the app function from AppSearch: " + e2.getMessage()));
        }
    }

    private final ExecuteAppFunctionResponse processResponse(Translator translator, AppFunctionMetadata functionMetadata, ExecuteAppFunctionResponse response) {
        AppFunctionData returnValue;
        if (!(response instanceof ExecuteAppFunctionResponse.Success)) {
            return response;
        }
        if (translator == null || (returnValue = translator.upgradeResponse(((ExecuteAppFunctionResponse.Success) response).getReturnValue())) == null) {
            returnValue = ((ExecuteAppFunctionResponse.Success) response).getReturnValue();
        }
        if (functionMetadata == null) {
            return new ExecuteAppFunctionResponse.Success(returnValue);
        }
        return new ExecuteAppFunctionResponse.Success(returnValue.replaceSpecWith$appfunctions(functionMetadata.getResponse(), functionMetadata.getComponents()));
    }

    public final Flow<List<AppFunctionPackageMetadata>> observeAppFunctions(AppFunctionSearchSpec searchSpec) {
        Intrinsics.checkNotNullParameter(searchSpec, "searchSpec");
        return this.appFunctionReader.searchAppFunctions(searchSpec);
    }

    /* JADX INFO: compiled from: AppFunctionManager.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\n\u001a\u00020\u000bH\u0002J\u0012\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Landroidx/appfunctions/AppFunctionManager$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "APP_FUNCTION_STATE_DEFAULT", "", "APP_FUNCTION_STATE_ENABLED", "APP_FUNCTION_STATE_DISABLED", "LEGACY_SDK_GLOBAL_SCHEMA_VERSION", "", "isExtensionLibraryAvailable", "", "getInstance", "Landroidx/appfunctions/AppFunctionManager;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        private final boolean isExtensionLibraryAvailable() {
            try {
                Class.forName("com.android.extensions.appfunctions.AppFunctionManager");
                return true;
            } catch (ClassNotFoundException unused) {
                return false;
            }
        }

        @JvmStatic
        public final AppFunctionManager getInstance(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            if (Build.VERSION.SDK_INT >= 36) {
                return new AppFunctionManager(context, new AppSearchAppFunctionReader(context, Dependencies.INSTANCE.getSchemaAppFunctionInventory$appfunctions()), new PlatformAppFunctionManagerApi(context), Dependencies.INSTANCE.getTranslatorSelector());
            }
            if (Build.VERSION.SDK_INT < 34 || !isExtensionLibraryAvailable()) {
                return null;
            }
            return new AppFunctionManager(context, new AppSearchAppFunctionReader(context, Dependencies.INSTANCE.getSchemaAppFunctionInventory$appfunctions()), new ExtensionAppFunctionManagerApi(context), Dependencies.INSTANCE.getTranslatorSelector());
        }
    }
}
