package androidx.appfunctions.metadata;

import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AppFunctionSchemaMetadata.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\bJ\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0003H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0014"}, d2 = {"Landroidx/appfunctions/metadata/AppFunctionSchemaMetadata;", "", "category", "", "name", "version", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;J)V", "getCategory", "()Ljava/lang/String;", "getName", "getVersion", "()J", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class AppFunctionSchemaMetadata {
    private final String category;
    private final String name;
    private final long version;

    public AppFunctionSchemaMetadata(String category, String name, long j) {
        Intrinsics.checkNotNullParameter(category, "category");
        Intrinsics.checkNotNullParameter(name, "name");
        this.category = category;
        this.name = name;
        this.version = j;
    }

    public final String getCategory() {
        return this.category;
    }

    public final String getName() {
        return this.name;
    }

    public final long getVersion() {
        return this.version;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type androidx.appfunctions.metadata.AppFunctionSchemaMetadata");
        AppFunctionSchemaMetadata appFunctionSchemaMetadata = (AppFunctionSchemaMetadata) other;
        return this.version == appFunctionSchemaMetadata.version && Intrinsics.areEqual(this.category, appFunctionSchemaMetadata.category) && Intrinsics.areEqual(this.name, appFunctionSchemaMetadata.name);
    }

    public int hashCode() {
        return (((Long.hashCode(this.version) * 31) + this.category.hashCode()) * 31) + this.name.hashCode();
    }

    public String toString() {
        return "AppFunctionSchemaMetadata(category='" + this.category + "', name='" + this.name + "', version=" + this.version + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }
}
