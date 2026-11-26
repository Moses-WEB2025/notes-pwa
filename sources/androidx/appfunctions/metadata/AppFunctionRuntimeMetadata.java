package androidx.appfunctions.metadata;

import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AppFunctionRuntimeMetadata.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u0081\b\u0018\u0000 !2\u00020\u0001:\u0001!B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\u0003¢\u0006\u0004\b\n\u0010\u000bJ\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0018\u001a\u00020\bHÆ\u0003J\t\u0010\u0019\u001a\u00020\u0003HÆ\u0003JE\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\u0003HÆ\u0001J\u0014\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001HÖ\u0083\u0004J\n\u0010\u001e\u001a\u00020\u001fHÖ\u0081\u0004J\n\u0010 \u001a\u00020\u0003HÖ\u0081\u0004R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0016\u0010\u0005\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u0016\u0010\u0006\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u0016\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\t\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\r¨\u0006\""}, d2 = {"Landroidx/appfunctions/metadata/AppFunctionRuntimeMetadata;", "", "id", "", "namespace", "functionId", "packageName", "enabled", "", AppFunctionRuntimeMetadata.STATIC_METADATA_JOIN_PROPERTY, ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JLjava/lang/String;)V", "getId", "()Ljava/lang/String;", "getNamespace", "getFunctionId", "getPackageName", "getEnabled", "()J", "getAppFunctionStaticMetadataQualifiedId", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "Companion", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
public final /* data */ class AppFunctionRuntimeMetadata {
    public static final String STATIC_METADATA_JOIN_PROPERTY = "appFunctionStaticMetadataQualifiedId";
    private final String appFunctionStaticMetadataQualifiedId;
    private final long enabled;
    private final String functionId;
    private final String id;
    private final String namespace;
    private final String packageName;

    public static /* synthetic */ AppFunctionRuntimeMetadata copy$default(AppFunctionRuntimeMetadata appFunctionRuntimeMetadata, String str, String str2, String str3, String str4, long j, String str5, int i, Object obj) {
        if ((i & 1) != 0) {
            str = appFunctionRuntimeMetadata.id;
        }
        if ((i & 2) != 0) {
            str2 = appFunctionRuntimeMetadata.namespace;
        }
        if ((i & 4) != 0) {
            str3 = appFunctionRuntimeMetadata.functionId;
        }
        if ((i & 8) != 0) {
            str4 = appFunctionRuntimeMetadata.packageName;
        }
        if ((i & 16) != 0) {
            j = appFunctionRuntimeMetadata.enabled;
        }
        if ((i & 32) != 0) {
            str5 = appFunctionRuntimeMetadata.appFunctionStaticMetadataQualifiedId;
        }
        String str6 = str5;
        long j2 = j;
        return appFunctionRuntimeMetadata.copy(str, str2, str3, str4, j2, str6);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getNamespace() {
        return this.namespace;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getFunctionId() {
        return this.functionId;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getPackageName() {
        return this.packageName;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final long getEnabled() {
        return this.enabled;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final String getAppFunctionStaticMetadataQualifiedId() {
        return this.appFunctionStaticMetadataQualifiedId;
    }

    public final AppFunctionRuntimeMetadata copy(String id, String namespace, String functionId, String packageName, long enabled, String appFunctionStaticMetadataQualifiedId) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(namespace, "namespace");
        Intrinsics.checkNotNullParameter(functionId, "functionId");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(appFunctionStaticMetadataQualifiedId, "appFunctionStaticMetadataQualifiedId");
        return new AppFunctionRuntimeMetadata(id, namespace, functionId, packageName, enabled, appFunctionStaticMetadataQualifiedId);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AppFunctionRuntimeMetadata)) {
            return false;
        }
        AppFunctionRuntimeMetadata appFunctionRuntimeMetadata = (AppFunctionRuntimeMetadata) other;
        return Intrinsics.areEqual(this.id, appFunctionRuntimeMetadata.id) && Intrinsics.areEqual(this.namespace, appFunctionRuntimeMetadata.namespace) && Intrinsics.areEqual(this.functionId, appFunctionRuntimeMetadata.functionId) && Intrinsics.areEqual(this.packageName, appFunctionRuntimeMetadata.packageName) && this.enabled == appFunctionRuntimeMetadata.enabled && Intrinsics.areEqual(this.appFunctionStaticMetadataQualifiedId, appFunctionRuntimeMetadata.appFunctionStaticMetadataQualifiedId);
    }

    public int hashCode() {
        return (((((((((this.id.hashCode() * 31) + this.namespace.hashCode()) * 31) + this.functionId.hashCode()) * 31) + this.packageName.hashCode()) * 31) + Long.hashCode(this.enabled)) * 31) + this.appFunctionStaticMetadataQualifiedId.hashCode();
    }

    public String toString() {
        return "AppFunctionRuntimeMetadata(id=" + this.id + ", namespace=" + this.namespace + ", functionId=" + this.functionId + ", packageName=" + this.packageName + ", enabled=" + this.enabled + ", appFunctionStaticMetadataQualifiedId=" + this.appFunctionStaticMetadataQualifiedId + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public AppFunctionRuntimeMetadata(String id, String namespace, String functionId, String packageName, long j, String appFunctionStaticMetadataQualifiedId) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(namespace, "namespace");
        Intrinsics.checkNotNullParameter(functionId, "functionId");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(appFunctionStaticMetadataQualifiedId, "appFunctionStaticMetadataQualifiedId");
        this.id = id;
        this.namespace = namespace;
        this.functionId = functionId;
        this.packageName = packageName;
        this.enabled = j;
        this.appFunctionStaticMetadataQualifiedId = appFunctionStaticMetadataQualifiedId;
    }

    public final String getId() {
        return this.id;
    }

    public final String getNamespace() {
        return this.namespace;
    }

    public final String getFunctionId() {
        return this.functionId;
    }

    public final String getPackageName() {
        return this.packageName;
    }

    public final long getEnabled() {
        return this.enabled;
    }

    public final String getAppFunctionStaticMetadataQualifiedId() {
        return this.appFunctionStaticMetadataQualifiedId;
    }
}
