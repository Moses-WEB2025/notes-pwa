package androidx.appfunctions.metadata;

import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import com.xiaomi.onetrack.api.ah;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AppFunctionMetadata.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b!\n\u0002\u0010\b\n\u0002\b\u0002\b\u0081\b\u0018\u00002\u00020\u0001Bs\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\u000e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\f\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0012¢\u0006\u0004\b\u0013\u0010\u0014J\t\u0010%\u001a\u00020\u0003HÆ\u0003J\t\u0010&\u001a\u00020\u0003HÆ\u0003J\t\u0010'\u001a\u00020\u0006HÆ\u0003J\u000b\u0010(\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010)\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0010\u0010*\u001a\u0004\u0018\u00010\nHÆ\u0003¢\u0006\u0002\u0010\u001cJ\u0011\u0010+\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\fHÆ\u0003J\u000b\u0010,\u001a\u0004\u0018\u00010\u000fHÆ\u0003J\u000b\u0010-\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010.\u001a\u0004\u0018\u00010\u0012HÆ\u0003J\u0086\u0001\u0010/\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\u0010\b\u0002\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\f2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0012HÆ\u0001¢\u0006\u0002\u00100J\u0014\u00101\u001a\u00020\u00062\b\u00102\u001a\u0004\u0018\u00010\u0001HÖ\u0083\u0004J\n\u00103\u001a\u000204HÖ\u0081\u0004J\n\u00105\u001a\u00020\u0003HÖ\u0081\u0004R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0016R\u0016\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0018R\u0018\u0010\u0007\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0016R\u0018\u0010\b\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0016R\u001a\u0010\t\u001a\u0004\u0018\u00010\n8\u0006X\u0087\u0004¢\u0006\n\n\u0002\u0010\u001d\u001a\u0004\b\u001b\u0010\u001cR\u001e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\f8\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0018\u0010\u000e\u001a\u0004\u0018\u00010\u000f8\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0018\u0010\u0010\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0016R\u0018\u0010\u0011\u001a\u0004\u0018\u00010\u00128\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$¨\u00066"}, d2 = {"Landroidx/appfunctions/metadata/AppFunctionMetadataDocument;", "", "namespace", "", "id", "isEnabledByDefault", "", "schemaCategory", "schemaName", "schemaVersion", "", "parameters", "", "Landroidx/appfunctions/metadata/AppFunctionParameterMetadataDocument;", ah.H, "Landroidx/appfunctions/metadata/AppFunctionResponseMetadataDocument;", "description", "deprecation", "Landroidx/appfunctions/metadata/AppFunctionDeprecationMetadataDocument;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;ZLjava/lang/String;Ljava/lang/String;Ljava/lang/Long;Ljava/util/List;Landroidx/appfunctions/metadata/AppFunctionResponseMetadataDocument;Ljava/lang/String;Landroidx/appfunctions/metadata/AppFunctionDeprecationMetadataDocument;)V", "getNamespace", "()Ljava/lang/String;", "getId", "()Z", "getSchemaCategory", "getSchemaName", "getSchemaVersion", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getParameters", "()Ljava/util/List;", "getResponse", "()Landroidx/appfunctions/metadata/AppFunctionResponseMetadataDocument;", "getDescription", "getDeprecation", "()Landroidx/appfunctions/metadata/AppFunctionDeprecationMetadataDocument;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "component10", "copy", "(Ljava/lang/String;Ljava/lang/String;ZLjava/lang/String;Ljava/lang/String;Ljava/lang/Long;Ljava/util/List;Landroidx/appfunctions/metadata/AppFunctionResponseMetadataDocument;Ljava/lang/String;Landroidx/appfunctions/metadata/AppFunctionDeprecationMetadataDocument;)Landroidx/appfunctions/metadata/AppFunctionMetadataDocument;", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
public final /* data */ class AppFunctionMetadataDocument {
    private final AppFunctionDeprecationMetadataDocument deprecation;
    private final String description;
    private final String id;
    private final boolean isEnabledByDefault;
    private final String namespace;
    private final List<AppFunctionParameterMetadataDocument> parameters;
    private final AppFunctionResponseMetadataDocument response;
    private final String schemaCategory;
    private final String schemaName;
    private final Long schemaVersion;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ AppFunctionMetadataDocument copy$default(AppFunctionMetadataDocument appFunctionMetadataDocument, String str, String str2, boolean z, String str3, String str4, Long l, List list, AppFunctionResponseMetadataDocument appFunctionResponseMetadataDocument, String str5, AppFunctionDeprecationMetadataDocument appFunctionDeprecationMetadataDocument, int i, Object obj) {
        if ((i & 1) != 0) {
            str = appFunctionMetadataDocument.namespace;
        }
        if ((i & 2) != 0) {
            str2 = appFunctionMetadataDocument.id;
        }
        if ((i & 4) != 0) {
            z = appFunctionMetadataDocument.isEnabledByDefault;
        }
        if ((i & 8) != 0) {
            str3 = appFunctionMetadataDocument.schemaCategory;
        }
        if ((i & 16) != 0) {
            str4 = appFunctionMetadataDocument.schemaName;
        }
        if ((i & 32) != 0) {
            l = appFunctionMetadataDocument.schemaVersion;
        }
        if ((i & 64) != 0) {
            list = appFunctionMetadataDocument.parameters;
        }
        if ((i & 128) != 0) {
            appFunctionResponseMetadataDocument = appFunctionMetadataDocument.response;
        }
        if ((i & 256) != 0) {
            str5 = appFunctionMetadataDocument.description;
        }
        if ((i & 512) != 0) {
            appFunctionDeprecationMetadataDocument = appFunctionMetadataDocument.deprecation;
        }
        String str6 = str5;
        AppFunctionDeprecationMetadataDocument appFunctionDeprecationMetadataDocument2 = appFunctionDeprecationMetadataDocument;
        List list2 = list;
        AppFunctionResponseMetadataDocument appFunctionResponseMetadataDocument2 = appFunctionResponseMetadataDocument;
        String str7 = str4;
        Long l2 = l;
        return appFunctionMetadataDocument.copy(str, str2, z, str3, str7, l2, list2, appFunctionResponseMetadataDocument2, str6, appFunctionDeprecationMetadataDocument2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getNamespace() {
        return this.namespace;
    }

    /* JADX INFO: renamed from: component10, reason: from getter */
    public final AppFunctionDeprecationMetadataDocument getDeprecation() {
        return this.deprecation;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getId() {
        return this.id;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final boolean getIsEnabledByDefault() {
        return this.isEnabledByDefault;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getSchemaCategory() {
        return this.schemaCategory;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final String getSchemaName() {
        return this.schemaName;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final Long getSchemaVersion() {
        return this.schemaVersion;
    }

    public final List<AppFunctionParameterMetadataDocument> component7() {
        return this.parameters;
    }

    /* JADX INFO: renamed from: component8, reason: from getter */
    public final AppFunctionResponseMetadataDocument getResponse() {
        return this.response;
    }

    /* JADX INFO: renamed from: component9, reason: from getter */
    public final String getDescription() {
        return this.description;
    }

    public final AppFunctionMetadataDocument copy(String namespace, String id, boolean isEnabledByDefault, String schemaCategory, String schemaName, Long schemaVersion, List<AppFunctionParameterMetadataDocument> parameters, AppFunctionResponseMetadataDocument response, String description, AppFunctionDeprecationMetadataDocument deprecation) {
        Intrinsics.checkNotNullParameter(namespace, "namespace");
        Intrinsics.checkNotNullParameter(id, "id");
        return new AppFunctionMetadataDocument(namespace, id, isEnabledByDefault, schemaCategory, schemaName, schemaVersion, parameters, response, description, deprecation);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AppFunctionMetadataDocument)) {
            return false;
        }
        AppFunctionMetadataDocument appFunctionMetadataDocument = (AppFunctionMetadataDocument) other;
        return Intrinsics.areEqual(this.namespace, appFunctionMetadataDocument.namespace) && Intrinsics.areEqual(this.id, appFunctionMetadataDocument.id) && this.isEnabledByDefault == appFunctionMetadataDocument.isEnabledByDefault && Intrinsics.areEqual(this.schemaCategory, appFunctionMetadataDocument.schemaCategory) && Intrinsics.areEqual(this.schemaName, appFunctionMetadataDocument.schemaName) && Intrinsics.areEqual(this.schemaVersion, appFunctionMetadataDocument.schemaVersion) && Intrinsics.areEqual(this.parameters, appFunctionMetadataDocument.parameters) && Intrinsics.areEqual(this.response, appFunctionMetadataDocument.response) && Intrinsics.areEqual(this.description, appFunctionMetadataDocument.description) && Intrinsics.areEqual(this.deprecation, appFunctionMetadataDocument.deprecation);
    }

    public int hashCode() {
        int iHashCode = ((((this.namespace.hashCode() * 31) + this.id.hashCode()) * 31) + Boolean.hashCode(this.isEnabledByDefault)) * 31;
        String str = this.schemaCategory;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.schemaName;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        Long l = this.schemaVersion;
        int iHashCode4 = (iHashCode3 + (l == null ? 0 : l.hashCode())) * 31;
        List<AppFunctionParameterMetadataDocument> list = this.parameters;
        int iHashCode5 = (iHashCode4 + (list == null ? 0 : list.hashCode())) * 31;
        AppFunctionResponseMetadataDocument appFunctionResponseMetadataDocument = this.response;
        int iHashCode6 = (iHashCode5 + (appFunctionResponseMetadataDocument == null ? 0 : appFunctionResponseMetadataDocument.hashCode())) * 31;
        String str3 = this.description;
        int iHashCode7 = (iHashCode6 + (str3 == null ? 0 : str3.hashCode())) * 31;
        AppFunctionDeprecationMetadataDocument appFunctionDeprecationMetadataDocument = this.deprecation;
        return iHashCode7 + (appFunctionDeprecationMetadataDocument != null ? appFunctionDeprecationMetadataDocument.hashCode() : 0);
    }

    public String toString() {
        return "AppFunctionMetadataDocument(namespace=" + this.namespace + ", id=" + this.id + ", isEnabledByDefault=" + this.isEnabledByDefault + ", schemaCategory=" + this.schemaCategory + ", schemaName=" + this.schemaName + ", schemaVersion=" + this.schemaVersion + ", parameters=" + this.parameters + ", response=" + this.response + ", description=" + this.description + ", deprecation=" + this.deprecation + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public AppFunctionMetadataDocument(String namespace, String id, boolean z, String str, String str2, Long l, List<AppFunctionParameterMetadataDocument> list, AppFunctionResponseMetadataDocument appFunctionResponseMetadataDocument, String str3, AppFunctionDeprecationMetadataDocument appFunctionDeprecationMetadataDocument) {
        Intrinsics.checkNotNullParameter(namespace, "namespace");
        Intrinsics.checkNotNullParameter(id, "id");
        this.namespace = namespace;
        this.id = id;
        this.isEnabledByDefault = z;
        this.schemaCategory = str;
        this.schemaName = str2;
        this.schemaVersion = l;
        this.parameters = list;
        this.response = appFunctionResponseMetadataDocument;
        this.description = str3;
        this.deprecation = appFunctionDeprecationMetadataDocument;
    }

    public /* synthetic */ AppFunctionMetadataDocument(String str, String str2, boolean z, String str3, String str4, Long l, List list, AppFunctionResponseMetadataDocument appFunctionResponseMetadataDocument, String str5, AppFunctionDeprecationMetadataDocument appFunctionDeprecationMetadataDocument, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE : str, (i & 2) != 0 ? AppFunctionMetadataKt.APP_FUNCTION_ID_EMPTY : str2, z, str3, str4, l, list, appFunctionResponseMetadataDocument, (i & 256) != 0 ? null : str5, (i & 512) != 0 ? null : appFunctionDeprecationMetadataDocument);
    }

    public final String getNamespace() {
        return this.namespace;
    }

    public final String getId() {
        return this.id;
    }

    public final boolean isEnabledByDefault() {
        return this.isEnabledByDefault;
    }

    public final String getSchemaCategory() {
        return this.schemaCategory;
    }

    public final String getSchemaName() {
        return this.schemaName;
    }

    public final Long getSchemaVersion() {
        return this.schemaVersion;
    }

    public final List<AppFunctionParameterMetadataDocument> getParameters() {
        return this.parameters;
    }

    public final AppFunctionResponseMetadataDocument getResponse() {
        return this.response;
    }

    public final String getDescription() {
        return this.description;
    }

    public final AppFunctionDeprecationMetadataDocument getDeprecation() {
        return this.deprecation;
    }
}
