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
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B#\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003¢\u0006\u0004\b\u0007\u0010\bJ\u0013\u0010\u000b\u001a\u00020\u00052\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0096\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0003H\u0016J\r\u0010\u0011\u001a\u00020\u0012H\u0010¢\u0006\u0002\b\u0013R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Landroidx/appfunctions/metadata/AppFunctionReferenceTypeMetadata;", "Landroidx/appfunctions/metadata/AppFunctionDataTypeMetadata;", "referenceDataType", "", "isNullable", "", "description", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;ZLjava/lang/String;)V", "getReferenceDataType", "()Ljava/lang/String;", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "toString", "toAppFunctionDataTypeMetadataDocument", "Landroidx/appfunctions/metadata/AppFunctionDataTypeMetadataDocument;", "toAppFunctionDataTypeMetadataDocument$appfunctions", "Companion", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class AppFunctionReferenceTypeMetadata extends AppFunctionDataTypeMetadata {
    public static final int TYPE = 11;
    private final String referenceDataType;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public AppFunctionReferenceTypeMetadata(String referenceDataType, boolean z) {
        this(referenceDataType, z, null, 4, null);
        Intrinsics.checkNotNullParameter(referenceDataType, "referenceDataType");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppFunctionReferenceTypeMetadata(String referenceDataType, boolean z, String description) {
        super(z, description);
        Intrinsics.checkNotNullParameter(referenceDataType, "referenceDataType");
        Intrinsics.checkNotNullParameter(description, "description");
        this.referenceDataType = referenceDataType;
    }

    public final String getReferenceDataType() {
        return this.referenceDataType;
    }

    public /* synthetic */ AppFunctionReferenceTypeMetadata(String str, boolean z, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, z, (i & 4) != 0 ? "" : str2);
    }

    @Override // androidx.appfunctions.metadata.AppFunctionDataTypeMetadata
    public boolean equals(Object other) {
        if (super.equals(other) && (other instanceof AppFunctionReferenceTypeMetadata)) {
            return Intrinsics.areEqual(this.referenceDataType, ((AppFunctionReferenceTypeMetadata) other).referenceDataType);
        }
        return false;
    }

    @Override // androidx.appfunctions.metadata.AppFunctionDataTypeMetadata
    public int hashCode() {
        return (super.hashCode() * 31) + this.referenceDataType.hashCode();
    }

    @Override // androidx.appfunctions.metadata.AppFunctionDataTypeMetadata
    public String toString() {
        return "AppFunctionReferenceTypeMetadata(referenceDataType=" + this.referenceDataType + ", isNullable=" + getIsNullable() + ",description=" + getDescription() + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    @Override // androidx.appfunctions.metadata.AppFunctionDataTypeMetadata
    public AppFunctionDataTypeMetadataDocument toAppFunctionDataTypeMetadataDocument$appfunctions() {
        String str = this.referenceDataType;
        boolean zIsNullable = getIsNullable();
        String description = getDescription();
        if (description.length() == 0) {
            description = null;
        }
        return new AppFunctionDataTypeMetadataDocument(null, null, 11, null, null, null, null, null, str, zIsNullable, null, description, null, R2.color.miuix_search_simple_clear_all_color_dark, null);
    }
}
