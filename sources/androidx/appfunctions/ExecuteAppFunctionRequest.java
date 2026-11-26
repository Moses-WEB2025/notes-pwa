package androidx.appfunctions;

import android.app.appsearch.GenericDocument;
import android.os.Bundle;
import androidx.appfunctions.metadata.AppFunctionMetadata;
import androidx.appfunctions.metadata.AppFunctionMetadataKt;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.accountsdk.account.stat.StatConstants;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ExecuteAppFunctionRequest.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u001a2\u00020\u0001:\u0001\u001aB)\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0004\b\t\u0010\nB!\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0004\b\t\u0010\u000bJ\r\u0010\u0013\u001a\u00020\u0014H\u0000¢\u0006\u0002\b\u0015J\b\u0010\u0016\u001a\u00020\u0017H\u0007J\b\u0010\u0018\u001a\u00020\u0003H\u0016J0\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\u0007\u001a\u00020\b8\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001b"}, d2 = {"Landroidx/appfunctions/ExecuteAppFunctionRequest;", "", "targetPackageName", "", "functionIdentifier", "functionParameters", "Landroidx/appfunctions/AppFunctionData;", "useJetpackSchema", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Landroidx/appfunctions/AppFunctionData;Z)V", "(Ljava/lang/String;Ljava/lang/String;Landroidx/appfunctions/AppFunctionData;)V", "getTargetPackageName", "()Ljava/lang/String;", "getFunctionIdentifier", "getFunctionParameters", "()Landroidx/appfunctions/AppFunctionData;", "getUseJetpackSchema", "()Z", "toPlatformExtensionClass", "Lcom/android/extensions/appfunctions/ExecuteAppFunctionRequest;", "toPlatformExtensionClass$appfunctions", "toPlatformExecuteAppFunctionRequest", "Landroid/app/appfunctions/ExecuteAppFunctionRequest;", "toString", "copy", "Companion", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class ExecuteAppFunctionRequest {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final String EXTRA_PARAMETERS = "androidXAppfunctionsExtraParameters";
    public static final String EXTRA_USE_JETPACK_SCHEMA = "androidXAppfunctionsExtraUseJetpackSchema";
    private final String functionIdentifier;
    private final AppFunctionData functionParameters;
    private final String targetPackageName;
    private final boolean useJetpackSchema;

    @JvmStatic
    public static final ExecuteAppFunctionRequest toCompatExecuteAppFunctionRequest(android.app.appfunctions.ExecuteAppFunctionRequest executeAppFunctionRequest, AppFunctionMetadata appFunctionMetadata) {
        return INSTANCE.toCompatExecuteAppFunctionRequest(executeAppFunctionRequest, appFunctionMetadata);
    }

    public ExecuteAppFunctionRequest(String targetPackageName, String functionIdentifier, AppFunctionData functionParameters, boolean z) {
        Intrinsics.checkNotNullParameter(targetPackageName, "targetPackageName");
        Intrinsics.checkNotNullParameter(functionIdentifier, "functionIdentifier");
        Intrinsics.checkNotNullParameter(functionParameters, "functionParameters");
        this.targetPackageName = targetPackageName;
        this.functionIdentifier = functionIdentifier;
        this.functionParameters = functionParameters;
        this.useJetpackSchema = z;
    }

    public final String getTargetPackageName() {
        return this.targetPackageName;
    }

    public final String getFunctionIdentifier() {
        return this.functionIdentifier;
    }

    public final AppFunctionData getFunctionParameters() {
        return this.functionParameters;
    }

    public final boolean getUseJetpackSchema() {
        return this.useJetpackSchema;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ExecuteAppFunctionRequest(String targetPackageName, String functionIdentifier, AppFunctionData functionParameters) {
        this(targetPackageName, functionIdentifier, functionParameters, true);
        Intrinsics.checkNotNullParameter(targetPackageName, "targetPackageName");
        Intrinsics.checkNotNullParameter(functionIdentifier, "functionIdentifier");
        Intrinsics.checkNotNullParameter(functionParameters, "functionParameters");
    }

    public final com.android.extensions.appfunctions.ExecuteAppFunctionRequest toPlatformExtensionClass$appfunctions() {
        com.android.extensions.appfunctions.ExecuteAppFunctionRequest.Builder parameters = new com.android.extensions.appfunctions.ExecuteAppFunctionRequest.Builder(this.targetPackageName, this.functionIdentifier).setParameters(this.functionParameters.getGenericDocument());
        Bundle bundle = new Bundle();
        bundle.putBundle(EXTRA_PARAMETERS, this.functionParameters.getExtras());
        bundle.putBoolean(EXTRA_USE_JETPACK_SCHEMA, this.useJetpackSchema);
        com.android.extensions.appfunctions.ExecuteAppFunctionRequest executeAppFunctionRequestBuild = parameters.setExtras(bundle).build();
        Intrinsics.checkNotNullExpressionValue(executeAppFunctionRequestBuild, "build(...)");
        return executeAppFunctionRequestBuild;
    }

    public final android.app.appfunctions.ExecuteAppFunctionRequest toPlatformExecuteAppFunctionRequest() {
        android.app.appfunctions.ExecuteAppFunctionRequest.Builder parameters = new android.app.appfunctions.ExecuteAppFunctionRequest.Builder(this.targetPackageName, this.functionIdentifier).setParameters(this.functionParameters.getGenericDocument());
        Bundle bundle = new Bundle();
        bundle.putBundle(EXTRA_PARAMETERS, this.functionParameters.getExtras());
        bundle.putBoolean(EXTRA_USE_JETPACK_SCHEMA, this.useJetpackSchema);
        android.app.appfunctions.ExecuteAppFunctionRequest executeAppFunctionRequestBuild = parameters.setExtras(bundle).build();
        Intrinsics.checkNotNullExpressionValue(executeAppFunctionRequestBuild, "build(...)");
        return executeAppFunctionRequestBuild;
    }

    public String toString() {
        return "ExecuteAppFunctionRequest(functionMetadata.packageName=" + this.targetPackageName + ", functionMetadata.id=" + this.functionIdentifier + ", functionParameters=" + this.functionParameters + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public static /* synthetic */ ExecuteAppFunctionRequest copy$default(ExecuteAppFunctionRequest executeAppFunctionRequest, String str, String str2, AppFunctionData appFunctionData, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            str = executeAppFunctionRequest.targetPackageName;
        }
        if ((i & 2) != 0) {
            str2 = executeAppFunctionRequest.functionIdentifier;
        }
        if ((i & 4) != 0) {
            appFunctionData = executeAppFunctionRequest.functionParameters;
        }
        if ((i & 8) != 0) {
            z = executeAppFunctionRequest.useJetpackSchema;
        }
        return executeAppFunctionRequest.copy(str, str2, appFunctionData, z);
    }

    public final ExecuteAppFunctionRequest copy(String targetPackageName, String functionIdentifier, AppFunctionData functionParameters, boolean useJetpackSchema) {
        Intrinsics.checkNotNullParameter(targetPackageName, "targetPackageName");
        Intrinsics.checkNotNullParameter(functionIdentifier, "functionIdentifier");
        Intrinsics.checkNotNullParameter(functionParameters, "functionParameters");
        return new ExecuteAppFunctionRequest(targetPackageName, functionIdentifier, functionParameters, useJetpackSchema);
    }

    /* JADX INFO: compiled from: ExecuteAppFunctionRequest.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001d\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0001¢\u0006\u0002\b\rJ\u0014\u0010\u000e\u001a\u00020\b*\u00020\u000f2\u0006\u0010\u000b\u001a\u00020\fH\u0007J\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0011H\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0080T¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Landroidx/appfunctions/ExecuteAppFunctionRequest$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "EXTRA_PARAMETERS", "", "EXTRA_USE_JETPACK_SCHEMA", "fromPlatformExtensionClass", "Landroidx/appfunctions/ExecuteAppFunctionRequest;", StatConstants.Event.REQUEST, "Lcom/android/extensions/appfunctions/ExecuteAppFunctionRequest;", "functionMetadata", "Landroidx/appfunctions/metadata/AppFunctionMetadata;", "fromPlatformExtensionClass$appfunctions", "toCompatExecuteAppFunctionRequest", "Landroid/app/appfunctions/ExecuteAppFunctionRequest;", "createAppFunctionDataWithParameterSpec", "Landroidx/appfunctions/AppFunctionData;", "parametersAfd", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ExecuteAppFunctionRequest fromPlatformExtensionClass$appfunctions(com.android.extensions.appfunctions.ExecuteAppFunctionRequest request, AppFunctionMetadata functionMetadata) {
            Intrinsics.checkNotNullParameter(request, "request");
            Intrinsics.checkNotNullParameter(functionMetadata, "functionMetadata");
            String targetPackageName = request.getTargetPackageName();
            Intrinsics.checkNotNullExpressionValue(targetPackageName, "getTargetPackageName(...)");
            String functionIdentifier = request.getFunctionIdentifier();
            Intrinsics.checkNotNullExpressionValue(functionIdentifier, "getFunctionIdentifier(...)");
            GenericDocument parameters = request.getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
            Bundle bundle = request.getExtras().getBundle(ExecuteAppFunctionRequest.EXTRA_PARAMETERS);
            if (bundle == null) {
                bundle = Bundle.EMPTY;
            }
            Intrinsics.checkNotNull(bundle);
            return new ExecuteAppFunctionRequest(targetPackageName, functionIdentifier, createAppFunctionDataWithParameterSpec(functionMetadata, new AppFunctionData(parameters, bundle)), request.getExtras().getBoolean(ExecuteAppFunctionRequest.EXTRA_USE_JETPACK_SCHEMA, false));
        }

        @JvmStatic
        public final ExecuteAppFunctionRequest toCompatExecuteAppFunctionRequest(android.app.appfunctions.ExecuteAppFunctionRequest executeAppFunctionRequest, AppFunctionMetadata functionMetadata) {
            Intrinsics.checkNotNullParameter(executeAppFunctionRequest, "<this>");
            Intrinsics.checkNotNullParameter(functionMetadata, "functionMetadata");
            String targetPackageName = executeAppFunctionRequest.getTargetPackageName();
            Intrinsics.checkNotNullExpressionValue(targetPackageName, "getTargetPackageName(...)");
            String functionIdentifier = executeAppFunctionRequest.getFunctionIdentifier();
            Intrinsics.checkNotNullExpressionValue(functionIdentifier, "getFunctionIdentifier(...)");
            GenericDocument parameters = executeAppFunctionRequest.getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
            Bundle bundle = executeAppFunctionRequest.getExtras().getBundle(ExecuteAppFunctionRequest.EXTRA_PARAMETERS);
            if (bundle == null) {
                bundle = Bundle.EMPTY;
            }
            Intrinsics.checkNotNull(bundle);
            return new ExecuteAppFunctionRequest(targetPackageName, functionIdentifier, createAppFunctionDataWithParameterSpec(functionMetadata, new AppFunctionData(parameters, bundle)), executeAppFunctionRequest.getExtras().getBoolean(ExecuteAppFunctionRequest.EXTRA_USE_JETPACK_SCHEMA, false));
        }

        private final AppFunctionData createAppFunctionDataWithParameterSpec(AppFunctionMetadata functionMetadata, AppFunctionData parametersAfd) {
            return parametersAfd.replaceSpecWith$appfunctions(functionMetadata.getParameters(), functionMetadata.getComponents());
        }
    }
}
