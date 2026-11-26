package androidx.appfunctions;

import android.app.appsearch.GenericDocument;
import android.content.Context;
import android.os.Bundle;
import androidx.appfunctions.metadata.AppFunctionMetadata;
import androidx.appfunctions.metadata.AppFunctionMetadataKt;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.onetrack.api.ah;
import com.xiaomi.passport.ui.internal.SNSAuthProvider;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ExecuteAppFunctionResponse.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0002\u0002\u0003\u0082\u0001\u0002\u0004\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0006À\u0006\u0001"}, d2 = {"Landroidx/appfunctions/ExecuteAppFunctionResponse;", "", "Success", "Error", "Landroidx/appfunctions/ExecuteAppFunctionResponse$Error;", "Landroidx/appfunctions/ExecuteAppFunctionResponse$Success;", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
public interface ExecuteAppFunctionResponse {

    /* JADX INFO: compiled from: ExecuteAppFunctionResponse.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\r\u0010\b\u001a\u00020\tH\u0000¢\u0006\u0002\b\nJ\b\u0010\u000b\u001a\u00020\fH\u0007J\u001d\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0001¢\u0006\u0002\b\u0013J\u0012\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0016"}, d2 = {"Landroidx/appfunctions/ExecuteAppFunctionResponse$Success;", "Landroidx/appfunctions/ExecuteAppFunctionResponse;", "returnValue", "Landroidx/appfunctions/AppFunctionData;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroidx/appfunctions/AppFunctionData;)V", "getReturnValue", "()Landroidx/appfunctions/AppFunctionData;", "toPlatformExtensionClass", "Lcom/android/extensions/appfunctions/ExecuteAppFunctionResponse;", "toPlatformExtensionClass$appfunctions", "toPlatformExecuteAppFunctionResponse", "Landroid/app/appfunctions/ExecuteAppFunctionResponse;", "grantUriAccess", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "callingPackageName", "", "grantUriAccess$appfunctions", "copy", "Companion", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class Success implements ExecuteAppFunctionResponse {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        public static final String PROPERTY_RETURN_VALUE = "androidAppfunctionsReturnValue";
        private final AppFunctionData returnValue;

        @JvmStatic
        public static final Success toCompatExecuteAppFunctionResponse(android.app.appfunctions.ExecuteAppFunctionResponse executeAppFunctionResponse, AppFunctionMetadata appFunctionMetadata) {
            return INSTANCE.toCompatExecuteAppFunctionResponse(executeAppFunctionResponse, appFunctionMetadata);
        }

        public Success(AppFunctionData returnValue) {
            Intrinsics.checkNotNullParameter(returnValue, "returnValue");
            this.returnValue = returnValue;
        }

        public final AppFunctionData getReturnValue() {
            return this.returnValue;
        }

        public final com.android.extensions.appfunctions.ExecuteAppFunctionResponse toPlatformExtensionClass$appfunctions() {
            return new com.android.extensions.appfunctions.ExecuteAppFunctionResponse(this.returnValue.getGenericDocument(), this.returnValue.getExtras());
        }

        public final android.app.appfunctions.ExecuteAppFunctionResponse toPlatformExecuteAppFunctionResponse() {
            return new android.app.appfunctions.ExecuteAppFunctionResponse(this.returnValue.getGenericDocument(), this.returnValue.getExtras());
        }

        public final void grantUriAccess$appfunctions(final Context context, final String callingPackageName) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(callingPackageName, "callingPackageName");
            this.returnValue.visitAppFunctionUriGrants(new Function1() { // from class: androidx.appfunctions.ExecuteAppFunctionResponse$Success$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return ExecuteAppFunctionResponse.Success.grantUriAccess$lambda$0(context, callingPackageName, (AppFunctionUriGrant) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit grantUriAccess$lambda$0(Context context, String str, AppFunctionUriGrant uriGrant) {
            Intrinsics.checkNotNullParameter(uriGrant, "uriGrant");
            context.grantUriPermission(str, uriGrant.getUri(), uriGrant.getModeFlags());
            return Unit.INSTANCE;
        }

        public static /* synthetic */ Success copy$default(Success success, AppFunctionData appFunctionData, int i, Object obj) {
            if ((i & 1) != 0) {
                appFunctionData = success.returnValue;
            }
            return success.copy(appFunctionData);
        }

        public final Success copy(AppFunctionData returnValue) {
            Intrinsics.checkNotNullParameter(returnValue, "returnValue");
            return new Success(returnValue);
        }

        /* JADX INFO: compiled from: ExecuteAppFunctionResponse.kt */
        @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001d\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0001¢\u0006\u0002\b\fJ\u0014\u0010\r\u001a\u00020\u0007*\u00020\u000e2\u0006\u0010\n\u001a\u00020\u000bH\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Landroidx/appfunctions/ExecuteAppFunctionResponse$Success$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "PROPERTY_RETURN_VALUE", "", "fromPlatformExtensionClass", "Landroidx/appfunctions/ExecuteAppFunctionResponse$Success;", ah.H, "Lcom/android/extensions/appfunctions/ExecuteAppFunctionResponse;", "functionMetadata", "Landroidx/appfunctions/metadata/AppFunctionMetadata;", "fromPlatformExtensionClass$appfunctions", "toCompatExecuteAppFunctionResponse", "Landroid/app/appfunctions/ExecuteAppFunctionResponse;", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final Success fromPlatformExtensionClass$appfunctions(com.android.extensions.appfunctions.ExecuteAppFunctionResponse response, AppFunctionMetadata functionMetadata) {
                Intrinsics.checkNotNullParameter(response, "response");
                Intrinsics.checkNotNullParameter(functionMetadata, "functionMetadata");
                GenericDocument resultDocument = response.getResultDocument();
                Intrinsics.checkNotNullExpressionValue(resultDocument, "getResultDocument(...)");
                Bundle extras = response.getExtras();
                Intrinsics.checkNotNullExpressionValue(extras, "getExtras(...)");
                return new Success(new AppFunctionData(resultDocument, extras).replaceSpecWith$appfunctions(functionMetadata.getResponse(), functionMetadata.getComponents()));
            }

            @JvmStatic
            public final Success toCompatExecuteAppFunctionResponse(android.app.appfunctions.ExecuteAppFunctionResponse executeAppFunctionResponse, AppFunctionMetadata functionMetadata) {
                Intrinsics.checkNotNullParameter(executeAppFunctionResponse, "<this>");
                Intrinsics.checkNotNullParameter(functionMetadata, "functionMetadata");
                GenericDocument resultDocument = executeAppFunctionResponse.getResultDocument();
                Intrinsics.checkNotNullExpressionValue(resultDocument, "getResultDocument(...)");
                Bundle extras = executeAppFunctionResponse.getExtras();
                Intrinsics.checkNotNullExpressionValue(extras, "getExtras(...)");
                return new Success(new AppFunctionData(resultDocument, extras).replaceSpecWith$appfunctions(functionMetadata.getResponse(), functionMetadata.getComponents()));
            }
        }
    }

    /* JADX INFO: compiled from: ExecuteAppFunctionResponse.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\b\u001a\u00020\tH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\n"}, d2 = {"Landroidx/appfunctions/ExecuteAppFunctionResponse$Error;", "Landroidx/appfunctions/ExecuteAppFunctionResponse;", SNSAuthProvider.VALUE_SNS_ERROR, "Landroidx/appfunctions/AppFunctionException;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroidx/appfunctions/AppFunctionException;)V", "getError", "()Landroidx/appfunctions/AppFunctionException;", "toString", "", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class Error implements ExecuteAppFunctionResponse {
        private final AppFunctionException error;

        public Error(AppFunctionException error) {
            Intrinsics.checkNotNullParameter(error, "error");
            this.error = error;
        }

        public final AppFunctionException getError() {
            return this.error;
        }

        public String toString() {
            return "AppFunctionResponse.Error(error=" + this.error + CoreConstants.RIGHT_PARENTHESIS_CHAR;
        }
    }
}
