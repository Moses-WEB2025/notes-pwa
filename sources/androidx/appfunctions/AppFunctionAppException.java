package androidx.appfunctions;

import android.os.Bundle;
import androidx.appfunctions.metadata.AppFunctionMetadataKt;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.accounts.secure.SecureDatabaseHelper;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AppFunctionAppExceptions.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B%\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Landroidx/appfunctions/AppFunctionAppException;", "Landroidx/appfunctions/AppFunctionException;", "errorCode", "", "errorMessage", "", SecureDatabaseHelper.TABLE_EXTRAS, "Landroid/os/Bundle;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(ILjava/lang/String;Landroid/os/Bundle;)V", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
public abstract class AppFunctionAppException extends AppFunctionException {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppFunctionAppException(int i, String str, Bundle extras) {
        super(i, str, extras);
        Intrinsics.checkNotNullParameter(extras, "extras");
    }

    public /* synthetic */ AppFunctionAppException(int i, String str, Bundle bundle, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i2 & 2) != 0 ? null : str, bundle);
    }
}
