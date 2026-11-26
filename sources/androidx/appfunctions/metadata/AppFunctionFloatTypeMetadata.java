package androidx.appfunctions.metadata;

import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import defpackage.R2;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AppFunctionDataTypeMetadata.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001b\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\r\u0010\b\u001a\u00020\tH\u0010¢\u0006\u0002\b\nJ\u0013\u0010\u000b\u001a\u00020\u00032\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0096\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0005H\u0016¨\u0006\u0011"}, d2 = {"Landroidx/appfunctions/metadata/AppFunctionFloatTypeMetadata;", "Landroidx/appfunctions/metadata/AppFunctionDataTypeMetadata;", "isNullable", "", "description", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(ZLjava/lang/String;)V", "toAppFunctionDataTypeMetadataDocument", "Landroidx/appfunctions/metadata/AppFunctionDataTypeMetadataDocument;", "toAppFunctionDataTypeMetadataDocument$appfunctions", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "toString", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class AppFunctionFloatTypeMetadata extends AppFunctionDataTypeMetadata {
    public AppFunctionFloatTypeMetadata(boolean z) {
        this(z, null, 2, 0 == true ? 1 : 0);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppFunctionFloatTypeMetadata(boolean z, String description) {
        super(z, description);
        Intrinsics.checkNotNullParameter(description, "description");
    }

    public /* synthetic */ AppFunctionFloatTypeMetadata(boolean z, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, (i & 2) != 0 ? "" : str);
    }

    @Override // androidx.appfunctions.metadata.AppFunctionDataTypeMetadata
    public AppFunctionDataTypeMetadataDocument toAppFunctionDataTypeMetadataDocument$appfunctions() {
        boolean zIsNullable = getIsNullable();
        String description = getDescription();
        if (description.length() == 0) {
            description = null;
        }
        return new AppFunctionDataTypeMetadataDocument(null, null, 5, null, null, null, null, null, null, zIsNullable, null, description, null, R2.color.passport_black_20_percent, null);
    }

    @Override // androidx.appfunctions.metadata.AppFunctionDataTypeMetadata
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof AppFunctionFloatTypeMetadata) {
            return super.equals(other);
        }
        return false;
    }

    @Override // androidx.appfunctions.metadata.AppFunctionDataTypeMetadata
    public int hashCode() {
        return super.hashCode();
    }

    @Override // androidx.appfunctions.metadata.AppFunctionDataTypeMetadata
    public String toString() {
        return "AppFunctionFloatTypeMetadata(isNullable=" + getIsNullable() + ", description=" + getDescription() + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }
}
