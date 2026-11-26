package androidx.appfunctions;

import android.os.Bundle;
import androidx.appfunctions.metadata.AppFunctionMetadataKt;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.accounts.secure.SecureDatabaseHelper;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AppFunctionRequestExceptions.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u001d\b\u0000\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007B\u0015\b\u0016\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0006\u0010\b¨\u0006\t"}, d2 = {"Landroidx/appfunctions/AppFunctionDisabledException;", "Landroidx/appfunctions/AppFunctionRequestException;", "errorMessage", "", SecureDatabaseHelper.TABLE_EXTRAS, "Landroid/os/Bundle;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Landroid/os/Bundle;)V", "(Ljava/lang/String;)V", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class AppFunctionDisabledException extends AppFunctionRequestException {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppFunctionDisabledException(String str, Bundle extras) {
        super(1002, str, extras);
        Intrinsics.checkNotNullParameter(extras, "extras");
    }

    public /* synthetic */ AppFunctionDisabledException(String str, Bundle bundle, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, bundle);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public AppFunctionDisabledException(String str) {
        Bundle EMPTY = Bundle.EMPTY;
        Intrinsics.checkNotNullExpressionValue(EMPTY, "EMPTY");
        this(str, EMPTY);
    }

    public /* synthetic */ AppFunctionDisabledException(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str);
    }
}
