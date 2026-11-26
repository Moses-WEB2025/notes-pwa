package androidx.appfunctions.internal;

import android.content.Context;
import androidx.appfunctions.metadata.AppFunctionMetadata;
import androidx.appfunctions.metadata.AppFunctionMetadataKt;
import androidx.appfunctions.metadata.CompileTimeAppFunctionMetadata;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AppFunctionMetadataUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bÁ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J \u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0086@¢\u0006\u0002\u0010\n¨\u0006\u000b"}, d2 = {"Landroidx/appfunctions/internal/AppFunctionMetadataUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "getAppFunctionMetadata", "Landroidx/appfunctions/metadata/AppFunctionMetadata;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "functionIdentifier", "", "(Landroid/content/Context;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class AppFunctionMetadataUtils {
    public static final AppFunctionMetadataUtils INSTANCE = new AppFunctionMetadataUtils();

    private AppFunctionMetadataUtils() {
    }

    public final Object getAppFunctionMetadata(Context context, String str, Continuation<? super AppFunctionMetadata> continuation) {
        AppFunctionInventory appFunctionInventory$appfunctions = Dependencies.INSTANCE.getAppFunctionInventory$appfunctions();
        if (appFunctionInventory$appfunctions != null) {
            CompileTimeAppFunctionMetadata compileTimeAppFunctionMetadata = appFunctionInventory$appfunctions.getFunctionIdToMetadataMap().get(str);
            if (compileTimeAppFunctionMetadata == null) {
                return null;
            }
            String id = compileTimeAppFunctionMetadata.getId();
            String packageName = context.getPackageName();
            Intrinsics.checkNotNullExpressionValue(packageName, "getPackageName(...)");
            return new AppFunctionMetadata(id, packageName, compileTimeAppFunctionMetadata.isEnabledByDefault(), compileTimeAppFunctionMetadata.getSchema(), compileTimeAppFunctionMetadata.getParameters(), compileTimeAppFunctionMetadata.getResponse(), appFunctionInventory$appfunctions.getComponentsMetadata(), compileTimeAppFunctionMetadata.getDescription(), null, 256, null);
        }
        AppSearchAppFunctionReader appSearchAppFunctionReader = new AppSearchAppFunctionReader(context, Dependencies.INSTANCE.getSchemaAppFunctionInventory$appfunctions());
        String packageName2 = context.getPackageName();
        Intrinsics.checkNotNullExpressionValue(packageName2, "getPackageName(...)");
        return appSearchAppFunctionReader.getAppFunctionMetadata(str, packageName2, continuation);
    }
}
