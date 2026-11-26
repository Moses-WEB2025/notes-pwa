package androidx.appfunctions.metadata;

import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AppFunctionDeprecationMetadata.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0081\b\u0018\u00002\u00020\u0001B#\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0004\b\u0006\u0010\u0007J\u0006\u0010\f\u001a\u00020\rJ\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J'\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003HÆ\u0001J\u0014\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0083\u0004J\n\u0010\u0015\u001a\u00020\u0016HÖ\u0081\u0004J\n\u0010\u0017\u001a\u00020\u0003HÖ\u0081\u0004R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0016\u0010\u0005\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\t¨\u0006\u0018"}, d2 = {"Landroidx/appfunctions/metadata/AppFunctionDeprecationMetadataDocument;", "", "namespace", "", "id", "message", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getNamespace", "()Ljava/lang/String;", "getId", "getMessage", "toAppFunctionDeprecationMetadata", "Landroidx/appfunctions/metadata/AppFunctionDeprecationMetadata;", "component1", "component2", "component3", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
public final /* data */ class AppFunctionDeprecationMetadataDocument {
    private final String id;
    private final String message;
    private final String namespace;

    public static /* synthetic */ AppFunctionDeprecationMetadataDocument copy$default(AppFunctionDeprecationMetadataDocument appFunctionDeprecationMetadataDocument, String str, String str2, String str3, int i, Object obj) {
        if ((i & 1) != 0) {
            str = appFunctionDeprecationMetadataDocument.namespace;
        }
        if ((i & 2) != 0) {
            str2 = appFunctionDeprecationMetadataDocument.id;
        }
        if ((i & 4) != 0) {
            str3 = appFunctionDeprecationMetadataDocument.message;
        }
        return appFunctionDeprecationMetadataDocument.copy(str, str2, str3);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getNamespace() {
        return this.namespace;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getId() {
        return this.id;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getMessage() {
        return this.message;
    }

    public final AppFunctionDeprecationMetadataDocument copy(String namespace, String id, String message) {
        Intrinsics.checkNotNullParameter(namespace, "namespace");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(message, "message");
        return new AppFunctionDeprecationMetadataDocument(namespace, id, message);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AppFunctionDeprecationMetadataDocument)) {
            return false;
        }
        AppFunctionDeprecationMetadataDocument appFunctionDeprecationMetadataDocument = (AppFunctionDeprecationMetadataDocument) other;
        return Intrinsics.areEqual(this.namespace, appFunctionDeprecationMetadataDocument.namespace) && Intrinsics.areEqual(this.id, appFunctionDeprecationMetadataDocument.id) && Intrinsics.areEqual(this.message, appFunctionDeprecationMetadataDocument.message);
    }

    public int hashCode() {
        return (((this.namespace.hashCode() * 31) + this.id.hashCode()) * 31) + this.message.hashCode();
    }

    public String toString() {
        return "AppFunctionDeprecationMetadataDocument(namespace=" + this.namespace + ", id=" + this.id + ", message=" + this.message + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public AppFunctionDeprecationMetadataDocument(String namespace, String id, String message) {
        Intrinsics.checkNotNullParameter(namespace, "namespace");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(message, "message");
        this.namespace = namespace;
        this.id = id;
        this.message = message;
    }

    public /* synthetic */ AppFunctionDeprecationMetadataDocument(String str, String str2, String str3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE : str, (i & 2) != 0 ? AppFunctionMetadataKt.APP_FUNCTION_ID_EMPTY : str2, str3);
    }

    public final String getNamespace() {
        return this.namespace;
    }

    public final String getId() {
        return this.id;
    }

    public final String getMessage() {
        return this.message;
    }

    public final AppFunctionDeprecationMetadata toAppFunctionDeprecationMetadata() {
        return new AppFunctionDeprecationMetadata(this.message);
    }
}
