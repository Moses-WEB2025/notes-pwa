package androidx.appfunctions.metadata;

import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: AppFunctionComponentsMetadata.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0081\b\u0018\u00002\u00020\u0001B)\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0004\b\b\u0010\tJ\u0006\u0010\u000f\u001a\u00020\u0010J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006HÆ\u0003J-\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006HÆ\u0001J\u0014\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001HÖ\u0083\u0004J\n\u0010\u0018\u001a\u00020\u0019HÖ\u0081\u0004J\n\u0010\u001a\u001a\u00020\u0003HÖ\u0081\u0004R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u001c\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001b"}, d2 = {"Landroidx/appfunctions/metadata/AppFunctionComponentsMetadataDocument;", "", "namespace", "", "id", "dataTypes", "", "Landroidx/appfunctions/metadata/AppFunctionNamedDataTypeMetadataDocument;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;)V", "getNamespace", "()Ljava/lang/String;", "getId", "getDataTypes", "()Ljava/util/List;", "toAppFunctionComponentsMetadata", "Landroidx/appfunctions/metadata/AppFunctionComponentsMetadata;", "component1", "component2", "component3", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
public final /* data */ class AppFunctionComponentsMetadataDocument {
    private final List<AppFunctionNamedDataTypeMetadataDocument> dataTypes;
    private final String id;
    private final String namespace;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ AppFunctionComponentsMetadataDocument copy$default(AppFunctionComponentsMetadataDocument appFunctionComponentsMetadataDocument, String str, String str2, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            str = appFunctionComponentsMetadataDocument.namespace;
        }
        if ((i & 2) != 0) {
            str2 = appFunctionComponentsMetadataDocument.id;
        }
        if ((i & 4) != 0) {
            list = appFunctionComponentsMetadataDocument.dataTypes;
        }
        return appFunctionComponentsMetadataDocument.copy(str, str2, list);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getNamespace() {
        return this.namespace;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getId() {
        return this.id;
    }

    public final List<AppFunctionNamedDataTypeMetadataDocument> component3() {
        return this.dataTypes;
    }

    public final AppFunctionComponentsMetadataDocument copy(String namespace, String id, List<AppFunctionNamedDataTypeMetadataDocument> dataTypes) {
        Intrinsics.checkNotNullParameter(namespace, "namespace");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(dataTypes, "dataTypes");
        return new AppFunctionComponentsMetadataDocument(namespace, id, dataTypes);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AppFunctionComponentsMetadataDocument)) {
            return false;
        }
        AppFunctionComponentsMetadataDocument appFunctionComponentsMetadataDocument = (AppFunctionComponentsMetadataDocument) other;
        return Intrinsics.areEqual(this.namespace, appFunctionComponentsMetadataDocument.namespace) && Intrinsics.areEqual(this.id, appFunctionComponentsMetadataDocument.id) && Intrinsics.areEqual(this.dataTypes, appFunctionComponentsMetadataDocument.dataTypes);
    }

    public int hashCode() {
        return (((this.namespace.hashCode() * 31) + this.id.hashCode()) * 31) + this.dataTypes.hashCode();
    }

    public String toString() {
        return "AppFunctionComponentsMetadataDocument(namespace=" + this.namespace + ", id=" + this.id + ", dataTypes=" + this.dataTypes + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public AppFunctionComponentsMetadataDocument(String namespace, String id, List<AppFunctionNamedDataTypeMetadataDocument> dataTypes) {
        Intrinsics.checkNotNullParameter(namespace, "namespace");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(dataTypes, "dataTypes");
        this.namespace = namespace;
        this.id = id;
        this.dataTypes = dataTypes;
    }

    public /* synthetic */ AppFunctionComponentsMetadataDocument(String str, String str2, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE : str, (i & 2) != 0 ? AppFunctionMetadataKt.APP_FUNCTION_ID_EMPTY : str2, list);
    }

    public final String getNamespace() {
        return this.namespace;
    }

    public final String getId() {
        return this.id;
    }

    public final List<AppFunctionNamedDataTypeMetadataDocument> getDataTypes() {
        return this.dataTypes;
    }

    public final AppFunctionComponentsMetadata toAppFunctionComponentsMetadata() {
        List<AppFunctionNamedDataTypeMetadataDocument> list = this.dataTypes;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
        for (AppFunctionNamedDataTypeMetadataDocument appFunctionNamedDataTypeMetadataDocument : list) {
            Pair pair = TuplesKt.to(appFunctionNamedDataTypeMetadataDocument.getName(), appFunctionNamedDataTypeMetadataDocument.getDataTypeMetadata().toAppFunctionDataTypeMetadata());
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        return new AppFunctionComponentsMetadata(linkedHashMap);
    }
}
