package androidx.appfunctions.metadata;

import android.app.PendingIntent;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.apache.commons.text.lookup.StringLookupFactory;

/* JADX INFO: compiled from: AppFunctionDataTypeMetadata.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\b\u0013\b\u0081\b\u0018\u00002\u00020\u0001B\u00ad\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0000\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00000\t\u0012\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00000\t\u0012\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00030\t\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u0012\u000e\b\u0002\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00030\t¢\u0006\u0004\b\u0014\u0010\u0015J\b\u0010'\u001a\u00020(H\u0007J\t\u0010)\u001a\u00020\u0003HÆ\u0003J\t\u0010*\u001a\u00020\u0003HÆ\u0003J\t\u0010+\u001a\u00020\u0006HÆ\u0003J\u000b\u0010,\u001a\u0004\u0018\u00010\u0000HÆ\u0003J\u000f\u0010-\u001a\b\u0012\u0004\u0012\u00020\n0\tHÆ\u0003J\u000f\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00000\tHÆ\u0003J\u000f\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00000\tHÆ\u0003J\u000f\u00100\u001a\b\u0012\u0004\u0012\u00020\u00030\tHÆ\u0003J\u000b\u00101\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u00102\u001a\u00020\u0010HÆ\u0003J\u000b\u00103\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u00104\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000f\u00105\u001a\b\u0012\u0004\u0012\u00020\u00030\tHÆ\u0003J±\u0001\u00106\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00002\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00000\t2\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00000\t2\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00030\t2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00102\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00032\u000e\b\u0002\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00030\tHÆ\u0001J\u0014\u00107\u001a\u00020\u00102\b\u00108\u001a\u0004\u0018\u00010\u0001HÖ\u0083\u0004J\n\u00109\u001a\u00020\u0006HÖ\u0081\u0004J\n\u0010:\u001a\u00020\u0003HÖ\u0081\u0004R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0017R\u0016\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0018\u0010\u0007\u001a\u0004\u0018\u00010\u00008\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u001c\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t8\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u001c\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00000\t8\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001eR\u001c\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00000\t8\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u001eR\u001c\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00030\t8\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001eR\u0018\u0010\u000e\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0017R\u0016\u0010\u000f\u001a\u00020\u00108\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010#R\u0018\u0010\u0011\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0017R\u0018\u0010\u0012\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0017R\u001c\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00030\t8\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001e¨\u0006;"}, d2 = {"Landroidx/appfunctions/metadata/AppFunctionDataTypeMetadataDocument;", "", "namespace", "", "id", "type", "", "itemType", StringLookupFactory.KEY_PROPERTIES, "", "Landroidx/appfunctions/metadata/AppFunctionNamedDataTypeMetadataDocument;", "allOf", "oneOf", "required", "dataTypeReference", "isNullable", "", "objectQualifiedName", "description", "enumValues", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;ILandroidx/appfunctions/metadata/AppFunctionDataTypeMetadataDocument;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/lang/String;ZLjava/lang/String;Ljava/lang/String;Ljava/util/List;)V", "getNamespace", "()Ljava/lang/String;", "getId", "getType", "()I", "getItemType", "()Landroidx/appfunctions/metadata/AppFunctionDataTypeMetadataDocument;", "getProperties", "()Ljava/util/List;", "getAllOf", "getOneOf", "getRequired", "getDataTypeReference", "()Z", "getObjectQualifiedName", "getDescription", "getEnumValues", "toAppFunctionDataTypeMetadata", "Landroidx/appfunctions/metadata/AppFunctionDataTypeMetadata;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "component10", "component11", "component12", "component13", "copy", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "toString", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
public final /* data */ class AppFunctionDataTypeMetadataDocument {
    private final List<AppFunctionDataTypeMetadataDocument> allOf;
    private final String dataTypeReference;
    private final String description;
    private final List<String> enumValues;
    private final String id;
    private final boolean isNullable;
    private final AppFunctionDataTypeMetadataDocument itemType;
    private final String namespace;
    private final String objectQualifiedName;
    private final List<AppFunctionDataTypeMetadataDocument> oneOf;
    private final List<AppFunctionNamedDataTypeMetadataDocument> properties;
    private final List<String> required;
    private final int type;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ AppFunctionDataTypeMetadataDocument copy$default(AppFunctionDataTypeMetadataDocument appFunctionDataTypeMetadataDocument, String str, String str2, int i, AppFunctionDataTypeMetadataDocument appFunctionDataTypeMetadataDocument2, List list, List list2, List list3, List list4, String str3, boolean z, String str4, String str5, List list5, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = appFunctionDataTypeMetadataDocument.namespace;
        }
        return appFunctionDataTypeMetadataDocument.copy(str, (i2 & 2) != 0 ? appFunctionDataTypeMetadataDocument.id : str2, (i2 & 4) != 0 ? appFunctionDataTypeMetadataDocument.type : i, (i2 & 8) != 0 ? appFunctionDataTypeMetadataDocument.itemType : appFunctionDataTypeMetadataDocument2, (i2 & 16) != 0 ? appFunctionDataTypeMetadataDocument.properties : list, (i2 & 32) != 0 ? appFunctionDataTypeMetadataDocument.allOf : list2, (i2 & 64) != 0 ? appFunctionDataTypeMetadataDocument.oneOf : list3, (i2 & 128) != 0 ? appFunctionDataTypeMetadataDocument.required : list4, (i2 & 256) != 0 ? appFunctionDataTypeMetadataDocument.dataTypeReference : str3, (i2 & 512) != 0 ? appFunctionDataTypeMetadataDocument.isNullable : z, (i2 & 1024) != 0 ? appFunctionDataTypeMetadataDocument.objectQualifiedName : str4, (i2 & 2048) != 0 ? appFunctionDataTypeMetadataDocument.description : str5, (i2 & 4096) != 0 ? appFunctionDataTypeMetadataDocument.enumValues : list5);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getNamespace() {
        return this.namespace;
    }

    /* JADX INFO: renamed from: component10, reason: from getter */
    public final boolean getIsNullable() {
        return this.isNullable;
    }

    /* JADX INFO: renamed from: component11, reason: from getter */
    public final String getObjectQualifiedName() {
        return this.objectQualifiedName;
    }

    /* JADX INFO: renamed from: component12, reason: from getter */
    public final String getDescription() {
        return this.description;
    }

    public final List<String> component13() {
        return this.enumValues;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getId() {
        return this.id;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final int getType() {
        return this.type;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final AppFunctionDataTypeMetadataDocument getItemType() {
        return this.itemType;
    }

    public final List<AppFunctionNamedDataTypeMetadataDocument> component5() {
        return this.properties;
    }

    public final List<AppFunctionDataTypeMetadataDocument> component6() {
        return this.allOf;
    }

    public final List<AppFunctionDataTypeMetadataDocument> component7() {
        return this.oneOf;
    }

    public final List<String> component8() {
        return this.required;
    }

    /* JADX INFO: renamed from: component9, reason: from getter */
    public final String getDataTypeReference() {
        return this.dataTypeReference;
    }

    public final AppFunctionDataTypeMetadataDocument copy(String namespace, String id, int type, AppFunctionDataTypeMetadataDocument itemType, List<AppFunctionNamedDataTypeMetadataDocument> properties, List<AppFunctionDataTypeMetadataDocument> allOf, List<AppFunctionDataTypeMetadataDocument> oneOf, List<String> required, String dataTypeReference, boolean isNullable, String objectQualifiedName, String description, List<String> enumValues) {
        Intrinsics.checkNotNullParameter(namespace, "namespace");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(properties, "properties");
        Intrinsics.checkNotNullParameter(allOf, "allOf");
        Intrinsics.checkNotNullParameter(oneOf, "oneOf");
        Intrinsics.checkNotNullParameter(required, "required");
        Intrinsics.checkNotNullParameter(enumValues, "enumValues");
        return new AppFunctionDataTypeMetadataDocument(namespace, id, type, itemType, properties, allOf, oneOf, required, dataTypeReference, isNullable, objectQualifiedName, description, enumValues);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AppFunctionDataTypeMetadataDocument)) {
            return false;
        }
        AppFunctionDataTypeMetadataDocument appFunctionDataTypeMetadataDocument = (AppFunctionDataTypeMetadataDocument) other;
        return Intrinsics.areEqual(this.namespace, appFunctionDataTypeMetadataDocument.namespace) && Intrinsics.areEqual(this.id, appFunctionDataTypeMetadataDocument.id) && this.type == appFunctionDataTypeMetadataDocument.type && Intrinsics.areEqual(this.itemType, appFunctionDataTypeMetadataDocument.itemType) && Intrinsics.areEqual(this.properties, appFunctionDataTypeMetadataDocument.properties) && Intrinsics.areEqual(this.allOf, appFunctionDataTypeMetadataDocument.allOf) && Intrinsics.areEqual(this.oneOf, appFunctionDataTypeMetadataDocument.oneOf) && Intrinsics.areEqual(this.required, appFunctionDataTypeMetadataDocument.required) && Intrinsics.areEqual(this.dataTypeReference, appFunctionDataTypeMetadataDocument.dataTypeReference) && this.isNullable == appFunctionDataTypeMetadataDocument.isNullable && Intrinsics.areEqual(this.objectQualifiedName, appFunctionDataTypeMetadataDocument.objectQualifiedName) && Intrinsics.areEqual(this.description, appFunctionDataTypeMetadataDocument.description) && Intrinsics.areEqual(this.enumValues, appFunctionDataTypeMetadataDocument.enumValues);
    }

    public int hashCode() {
        int iHashCode = ((((this.namespace.hashCode() * 31) + this.id.hashCode()) * 31) + Integer.hashCode(this.type)) * 31;
        AppFunctionDataTypeMetadataDocument appFunctionDataTypeMetadataDocument = this.itemType;
        int iHashCode2 = (((((((((iHashCode + (appFunctionDataTypeMetadataDocument == null ? 0 : appFunctionDataTypeMetadataDocument.hashCode())) * 31) + this.properties.hashCode()) * 31) + this.allOf.hashCode()) * 31) + this.oneOf.hashCode()) * 31) + this.required.hashCode()) * 31;
        String str = this.dataTypeReference;
        int iHashCode3 = (((iHashCode2 + (str == null ? 0 : str.hashCode())) * 31) + Boolean.hashCode(this.isNullable)) * 31;
        String str2 = this.objectQualifiedName;
        int iHashCode4 = (iHashCode3 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.description;
        return ((iHashCode4 + (str3 != null ? str3.hashCode() : 0)) * 31) + this.enumValues.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("AppFunctionDataTypeMetadataDocument(namespace=");
        sb.append(this.namespace).append(", id=").append(this.id).append(", type=").append(this.type).append(", itemType=").append(this.itemType).append(", properties=").append(this.properties).append(", allOf=").append(this.allOf).append(", oneOf=").append(this.oneOf).append(", required=").append(this.required).append(", dataTypeReference=").append(this.dataTypeReference).append(", isNullable=").append(this.isNullable).append(", objectQualifiedName=").append(this.objectQualifiedName).append(", description=");
        sb.append(this.description).append(", enumValues=").append(this.enumValues).append(CoreConstants.RIGHT_PARENTHESIS_CHAR);
        return sb.toString();
    }

    public AppFunctionDataTypeMetadataDocument(String namespace, String id, int i, AppFunctionDataTypeMetadataDocument appFunctionDataTypeMetadataDocument, List<AppFunctionNamedDataTypeMetadataDocument> properties, List<AppFunctionDataTypeMetadataDocument> allOf, List<AppFunctionDataTypeMetadataDocument> oneOf, List<String> required, String str, boolean z, String str2, String str3, List<String> enumValues) {
        Intrinsics.checkNotNullParameter(namespace, "namespace");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(properties, "properties");
        Intrinsics.checkNotNullParameter(allOf, "allOf");
        Intrinsics.checkNotNullParameter(oneOf, "oneOf");
        Intrinsics.checkNotNullParameter(required, "required");
        Intrinsics.checkNotNullParameter(enumValues, "enumValues");
        this.namespace = namespace;
        this.id = id;
        this.type = i;
        this.itemType = appFunctionDataTypeMetadataDocument;
        this.properties = properties;
        this.allOf = allOf;
        this.oneOf = oneOf;
        this.required = required;
        this.dataTypeReference = str;
        this.isNullable = z;
        this.objectQualifiedName = str2;
        this.description = str3;
        this.enumValues = enumValues;
    }

    public /* synthetic */ AppFunctionDataTypeMetadataDocument(String str, String str2, int i, AppFunctionDataTypeMetadataDocument appFunctionDataTypeMetadataDocument, List list, List list2, List list3, List list4, String str3, boolean z, String str4, String str5, List list5, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE : str, (i2 & 2) != 0 ? AppFunctionMetadataKt.APP_FUNCTION_ID_EMPTY : str2, i, (i2 & 8) != 0 ? null : appFunctionDataTypeMetadataDocument, (i2 & 16) != 0 ? CollectionsKt.emptyList() : list, (i2 & 32) != 0 ? CollectionsKt.emptyList() : list2, (i2 & 64) != 0 ? CollectionsKt.emptyList() : list3, (i2 & 128) != 0 ? CollectionsKt.emptyList() : list4, (i2 & 256) != 0 ? null : str3, (i2 & 512) != 0 ? false : z, (i2 & 1024) != 0 ? null : str4, (i2 & 2048) != 0 ? null : str5, (i2 & 4096) != 0 ? CollectionsKt.emptyList() : list5);
    }

    public final String getNamespace() {
        return this.namespace;
    }

    public final String getId() {
        return this.id;
    }

    public final int getType() {
        return this.type;
    }

    public final AppFunctionDataTypeMetadataDocument getItemType() {
        return this.itemType;
    }

    public final List<AppFunctionNamedDataTypeMetadataDocument> getProperties() {
        return this.properties;
    }

    public final List<AppFunctionDataTypeMetadataDocument> getAllOf() {
        return this.allOf;
    }

    public final List<AppFunctionDataTypeMetadataDocument> getOneOf() {
        return this.oneOf;
    }

    public final List<String> getRequired() {
        return this.required;
    }

    public final String getDataTypeReference() {
        return this.dataTypeReference;
    }

    public final boolean isNullable() {
        return this.isNullable;
    }

    public final String getObjectQualifiedName() {
        return this.objectQualifiedName;
    }

    public final String getDescription() {
        return this.description;
    }

    public final List<String> getEnumValues() {
        return this.enumValues;
    }

    public final AppFunctionDataTypeMetadata toAppFunctionDataTypeMetadata() {
        String str;
        switch (this.type) {
            case 0:
                boolean z = this.isNullable;
                String str2 = this.description;
                return new AppFunctionUnitTypeMetadata(z, str2 != null ? str2 : "");
            case 1:
                boolean z2 = this.isNullable;
                String str3 = this.description;
                return new AppFunctionBooleanTypeMetadata(z2, str3 != null ? str3 : "");
            case 2:
                boolean z3 = this.isNullable;
                String str4 = this.description;
                return new AppFunctionBytesTypeMetadata(z3, str4 != null ? str4 : "");
            case 3:
                if (this.properties.isEmpty()) {
                    throw new IllegalStateException("Properties must be present for object type can't be empty".toString());
                }
                List<AppFunctionNamedDataTypeMetadataDocument> list = this.properties;
                LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
                for (AppFunctionNamedDataTypeMetadataDocument appFunctionNamedDataTypeMetadataDocument : list) {
                    Pair pair = TuplesKt.to(appFunctionNamedDataTypeMetadataDocument.getName(), appFunctionNamedDataTypeMetadataDocument.getDataTypeMetadata().toAppFunctionDataTypeMetadata());
                    linkedHashMap.put(pair.getFirst(), pair.getSecond());
                }
                List<String> list2 = this.required;
                String str5 = this.objectQualifiedName;
                boolean z4 = this.isNullable;
                String str6 = this.description;
                return new AppFunctionObjectTypeMetadata(linkedHashMap, list2, str5, z4, str6 == null ? "" : str6);
            case 4:
                boolean z5 = this.isNullable;
                String str7 = this.description;
                return new AppFunctionDoubleTypeMetadata(z5, str7 != null ? str7 : "");
            case 5:
                boolean z6 = this.isNullable;
                String str8 = this.description;
                return new AppFunctionFloatTypeMetadata(z6, str8 != null ? str8 : "");
            case 6:
                boolean z7 = this.isNullable;
                String str9 = this.description;
                return new AppFunctionLongTypeMetadata(z7, str9 != null ? str9 : "");
            case 7:
                boolean z8 = this.isNullable;
                String str10 = this.description;
                str = str10 != null ? str10 : "";
                List<String> list3 = this.enumValues;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
                Iterator<T> it = list3.iterator();
                while (it.hasNext()) {
                    arrayList.add(Integer.valueOf(Integer.parseInt((String) it.next())));
                }
                Set set = CollectionsKt.toSet(arrayList);
                return new AppFunctionIntTypeMetadata(z8, str, set.isEmpty() ? null : set);
            case 8:
                boolean z9 = this.isNullable;
                String str11 = this.description;
                str = str11 != null ? str11 : "";
                Set set2 = CollectionsKt.toSet(this.enumValues);
                return new AppFunctionStringTypeMetadata(z9, str, set2.isEmpty() ? null : set2);
            case 9:
            default:
                throw new IllegalArgumentException("Unknown type: " + this.type);
            case 10:
                AppFunctionDataTypeMetadataDocument appFunctionDataTypeMetadataDocument = this.itemType;
                if (appFunctionDataTypeMetadataDocument == null) {
                    throw new IllegalStateException("Item type must be present for array type".toString());
                }
                AppFunctionDataTypeMetadata appFunctionDataTypeMetadata = appFunctionDataTypeMetadataDocument.toAppFunctionDataTypeMetadata();
                boolean z10 = this.isNullable;
                String str12 = this.description;
                return new AppFunctionArrayTypeMetadata(appFunctionDataTypeMetadata, z10, str12 != null ? str12 : "");
            case 11:
                String str13 = this.dataTypeReference;
                if (str13 == null) {
                    throw new IllegalStateException("Data type reference must be present for reference type".toString());
                }
                boolean z11 = this.isNullable;
                String str14 = this.description;
                return new AppFunctionReferenceTypeMetadata(str13, z11, str14 != null ? str14 : "");
            case 12:
                List<AppFunctionDataTypeMetadataDocument> list4 = this.allOf;
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list4, 10));
                Iterator<T> it2 = list4.iterator();
                while (it2.hasNext()) {
                    arrayList2.add(((AppFunctionDataTypeMetadataDocument) it2.next()).toAppFunctionDataTypeMetadata());
                }
                ArrayList arrayList3 = arrayList2;
                String str15 = this.objectQualifiedName;
                boolean z12 = this.isNullable;
                String str16 = this.description;
                return new AppFunctionAllOfTypeMetadata(arrayList3, str15, z12, str16 != null ? str16 : "");
            case 13:
                String name = this.objectQualifiedName;
                if (name == null) {
                    name = PendingIntent.class.getName();
                }
                Intrinsics.checkNotNull(name);
                boolean z13 = this.isNullable;
                String str17 = this.description;
                return new AppFunctionParcelableTypeMetadata(name, z13, str17 != null ? str17 : "");
            case 14:
                List<AppFunctionDataTypeMetadataDocument> list5 = this.oneOf;
                ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list5, 10));
                Iterator<T> it3 = list5.iterator();
                while (it3.hasNext()) {
                    arrayList4.add(((AppFunctionDataTypeMetadataDocument) it3.next()).toAppFunctionDataTypeMetadata());
                }
                ArrayList arrayList5 = arrayList4;
                String str18 = this.objectQualifiedName;
                if (str18 == null) {
                    throw new IllegalStateException("Required value was null.".toString());
                }
                boolean z14 = this.isNullable;
                String str19 = this.description;
                return new AppFunctionOneOfTypeMetadata(arrayList5, str18, z14, str19 != null ? str19 : "");
        }
    }
}
