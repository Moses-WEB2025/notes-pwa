package androidx.appfunctions;

import android.os.Parcelable;
import androidx.appfunctions.metadata.AppFunctionAllOfTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionArrayTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionBooleanTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionBytesTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionComponentsMetadata;
import androidx.appfunctions.metadata.AppFunctionDataTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionDoubleTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionFloatTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionIntTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionLongTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionMetadataKt;
import androidx.appfunctions.metadata.AppFunctionObjectTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionOneOfTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionParameterMetadata;
import androidx.appfunctions.metadata.AppFunctionParcelableTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionReferenceTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionResponseMetadata;
import androidx.appfunctions.metadata.AppFunctionStringTypeMetadata;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AppFunctionDataSpec.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b \u0018\u0000 22\u00020\u0001:\u0003012B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0017\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u0005H ¢\u0006\u0002\b\u000fJ\u0015\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u0005H ¢\u0006\u0002\b\u0012J\u0013\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00050\u0014H ¢\u0006\u0002\b\u0015J\u000e\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u0005J\u0016\u0010\u0017\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u0005J\u0018\u0010\u0017\u001a\u00020\u00002\u0006\u0010\u0019\u001a\u00020\r2\u0006\u0010\u0018\u001a\u00020\u0005H\u0002J\u000e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dJ*\u0010\u001e\u001a\u00020\u001b2\u0006\u0010\u001f\u001a\u00020\u00052\n\u0010 \u001a\u0006\u0012\u0002\b\u00030!2\u0006\u0010\"\u001a\u00020\u00112\u0006\u0010#\u001a\u00020\u0001J.\u0010$\u001a\u00020\u001b2\u0006\u0010\u001f\u001a\u00020\u00052\n\u0010 \u001a\u0006\u0012\u0002\b\u00030!2\u0006\u0010\"\u001a\u00020\u00112\n\b\u0002\u0010#\u001a\u0004\u0018\u00010\u0001J\u001e\u0010%\u001a\u00020\u001b*\u00020\r2\u0006\u0010\u001f\u001a\u00020\u00052\b\u0010#\u001a\u0004\u0018\u00010\u0001H\u0002J\u001e\u0010&\u001a\u00020\u001b*\u00020'2\u0006\u0010\u001f\u001a\u00020\u00052\b\u0010#\u001a\u0004\u0018\u00010\u0001H\u0002J\u001e\u0010(\u001a\u00020\u0011*\u00020\r2\n\u0010)\u001a\u0006\u0012\u0002\b\u00030!2\u0006\u0010\"\u001a\u00020\u0011J\u0018\u0010(\u001a\u00020\u0011*\u00020'2\n\u0010*\u001a\u0006\u0012\u0002\b\u00030!H\u0002J\u0018\u0010(\u001a\u00020\u0011*\u00020+2\n\u0010,\u001a\u0006\u0012\u0002\b\u00030!H\u0002J\u0018\u0010(\u001a\u00020\u0011*\u00020-2\n\u0010,\u001a\u0006\u0012\u0002\b\u00030!H\u0002J\u0018\u0010(\u001a\u00020\u0011*\u00020.2\n\u0010,\u001a\u0006\u0012\u0002\b\u00030!H\u0002J\u0018\u0010(\u001a\u00020\u0011*\u00020/2\n\u0010,\u001a\u0006\u0012\u0002\b\u00030!H\u0002R\u0012\u0010\u0004\u001a\u00020\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0012\u0010\b\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u00063"}, d2 = {"Landroidx/appfunctions/AppFunctionDataSpec;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "objectQualifiedName", "", "getObjectQualifiedName", "()Ljava/lang/String;", "componentMetadata", "Landroidx/appfunctions/metadata/AppFunctionComponentsMetadata;", "getComponentMetadata", "()Landroidx/appfunctions/metadata/AppFunctionComponentsMetadata;", "getDataType", "Landroidx/appfunctions/metadata/AppFunctionDataTypeMetadata;", "key", "getDataType$appfunctions", "isRequired", "", "isRequired$appfunctions", "getAllPropertyKeys", "", "getAllPropertyKeys$appfunctions", "containsMetadata", "getPropertyObjectSpec", "qualifiedName", "type", "validateDataSpecMatches", "", "data", "Landroidx/appfunctions/AppFunctionData;", "validateWriteRequest", "targetKey", "targetClass", "Ljava/lang/Class;", "isCollection", "targetValue", "validateReadRequest", "requireConstraintsConformance", "requireItemTypeConstraintsConformance", "Landroidx/appfunctions/metadata/AppFunctionArrayTypeMetadata;", "conform", "typeClazz", "itemTypeClass", "Landroidx/appfunctions/metadata/AppFunctionObjectTypeMetadata;", "typeClass", "Landroidx/appfunctions/metadata/AppFunctionAllOfTypeMetadata;", "Landroidx/appfunctions/metadata/AppFunctionOneOfTypeMetadata;", "Landroidx/appfunctions/metadata/AppFunctionReferenceTypeMetadata;", "ObjectSpec", "ParametersSpec", "Companion", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
public abstract class AppFunctionDataSpec {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public abstract Set<String> getAllPropertyKeys$appfunctions();

    public abstract AppFunctionComponentsMetadata getComponentMetadata();

    public abstract AppFunctionDataTypeMetadata getDataType$appfunctions(String key);

    public abstract String getObjectQualifiedName();

    public abstract boolean isRequired$appfunctions(String key);

    public final boolean containsMetadata(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return getDataType$appfunctions(key) != null;
    }

    public final AppFunctionDataSpec getPropertyObjectSpec(String key, String qualifiedName) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(qualifiedName, "qualifiedName");
        AppFunctionDataTypeMetadata dataType$appfunctions = getDataType$appfunctions(key);
        if (dataType$appfunctions == null) {
            throw new IllegalArgumentException("Value associated with " + key + " is not an object");
        }
        return getPropertyObjectSpec(dataType$appfunctions, qualifiedName);
    }

    private final AppFunctionDataSpec getPropertyObjectSpec(AppFunctionDataTypeMetadata type, String qualifiedName) {
        if (type instanceof AppFunctionArrayTypeMetadata) {
            return getPropertyObjectSpec(((AppFunctionArrayTypeMetadata) type).getItemType(), qualifiedName);
        }
        if (type instanceof AppFunctionObjectTypeMetadata) {
            return new ObjectSpec((AppFunctionObjectTypeMetadata) type, getComponentMetadata());
        }
        if (type instanceof AppFunctionReferenceTypeMetadata) {
            AppFunctionReferenceTypeMetadata appFunctionReferenceTypeMetadata = (AppFunctionReferenceTypeMetadata) type;
            AppFunctionDataTypeMetadata appFunctionDataTypeMetadata = getComponentMetadata().getDataTypes().get(appFunctionReferenceTypeMetadata.getReferenceDataType());
            if (appFunctionDataTypeMetadata == null) {
                throw new IllegalStateException("Unable to resolve data type for " + appFunctionReferenceTypeMetadata.getReferenceDataType());
            }
            return getPropertyObjectSpec(appFunctionDataTypeMetadata, qualifiedName);
        }
        if (type instanceof AppFunctionAllOfTypeMetadata) {
            return new ObjectSpec(((AppFunctionAllOfTypeMetadata) type).getPseudoObjectTypeMetadata$appfunctions(getComponentMetadata()), getComponentMetadata());
        }
        if (type instanceof AppFunctionOneOfTypeMetadata) {
            return getPropertyObjectSpec(((AppFunctionOneOfTypeMetadata) type).getObjectMetadataForOneOfType$appfunctions(qualifiedName), qualifiedName);
        }
        throw new IllegalStateException("Unexpected data type " + type);
    }

    public final void validateDataSpecMatches(AppFunctionData data) {
        Intrinsics.checkNotNullParameter(data, "data");
        data.getSpec();
    }

    public final void validateWriteRequest(String targetKey, Class<?> targetClass, boolean isCollection, Object targetValue) {
        Intrinsics.checkNotNullParameter(targetKey, "targetKey");
        Intrinsics.checkNotNullParameter(targetClass, "targetClass");
        Intrinsics.checkNotNullParameter(targetValue, "targetValue");
        AppFunctionDataTypeMetadata dataType$appfunctions = getDataType$appfunctions(targetKey);
        if (dataType$appfunctions == null) {
            throw new IllegalArgumentException("No value should be set at " + targetKey);
        }
        if (!conform(dataType$appfunctions, targetClass, isCollection)) {
            throw new IllegalArgumentException((isCollection ? "Invalid value for " + targetKey + ": got collection of " + targetClass + ", expecting a value matching " + dataType$appfunctions : "Invalid value for " + targetKey + ": got " + targetClass + ", expecting a value matching " + dataType$appfunctions).toString());
        }
        requireConstraintsConformance(dataType$appfunctions, targetKey, targetValue);
    }

    public static /* synthetic */ void validateReadRequest$default(AppFunctionDataSpec appFunctionDataSpec, String str, Class cls, boolean z, Object obj, int i, Object obj2) {
        if (obj2 != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: validateReadRequest");
        }
        if ((i & 8) != 0) {
            obj = null;
        }
        appFunctionDataSpec.validateReadRequest(str, cls, z, obj);
    }

    public final void validateReadRequest(String targetKey, Class<?> targetClass, boolean isCollection, Object targetValue) {
        Intrinsics.checkNotNullParameter(targetKey, "targetKey");
        Intrinsics.checkNotNullParameter(targetClass, "targetClass");
        AppFunctionDataTypeMetadata dataType$appfunctions = getDataType$appfunctions(targetKey);
        if (dataType$appfunctions == null) {
            throw new IllegalArgumentException("No value should be set at " + targetKey);
        }
        if (!conform(dataType$appfunctions, targetClass, isCollection)) {
            throw new IllegalArgumentException((isCollection ? "Unexpected read for " + targetKey + ": expecting collection of " + targetClass + ", the actual value should be " + dataType$appfunctions : "Unexpected read for " + targetKey + ": expecting " + targetClass + ", the actual value should be " + dataType$appfunctions).toString());
        }
        requireConstraintsConformance(dataType$appfunctions, targetKey, targetValue);
    }

    private final void requireConstraintsConformance(AppFunctionDataTypeMetadata appFunctionDataTypeMetadata, String str, Object obj) {
        if (obj == null && isRequired$appfunctions(str)) {
            throw new IllegalArgumentException(("\"" + str + "\" cannot be set to a null value.").toString());
        }
        if (obj == null) {
            return;
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionIntTypeMetadata) {
            AppFunctionIntTypeMetadata appFunctionIntTypeMetadata = (AppFunctionIntTypeMetadata) appFunctionDataTypeMetadata;
            if (appFunctionIntTypeMetadata.getEnumValues() != null && !CollectionsKt.contains(appFunctionIntTypeMetadata.getEnumValues(), obj)) {
                throw new IllegalArgumentException(("Invalid value for \"" + str + "\" got \"" + obj + "\", expecting one of " + appFunctionIntTypeMetadata.getEnumValues()).toString());
            }
            return;
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionStringTypeMetadata) {
            AppFunctionStringTypeMetadata appFunctionStringTypeMetadata = (AppFunctionStringTypeMetadata) appFunctionDataTypeMetadata;
            if (appFunctionStringTypeMetadata.getEnumValues() != null && !CollectionsKt.contains(appFunctionStringTypeMetadata.getEnumValues(), obj)) {
                throw new IllegalArgumentException(("Invalid value for \"" + str + "\" got \"" + obj + "\", expecting one of " + appFunctionStringTypeMetadata.getEnumValues()).toString());
            }
            return;
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionArrayTypeMetadata) {
            requireItemTypeConstraintsConformance((AppFunctionArrayTypeMetadata) appFunctionDataTypeMetadata, str, obj);
        }
    }

    private final void requireItemTypeConstraintsConformance(AppFunctionArrayTypeMetadata appFunctionArrayTypeMetadata, String str, Object obj) {
        AppFunctionDataTypeMetadata itemType = appFunctionArrayTypeMetadata.getItemType();
        if (itemType instanceof AppFunctionIntTypeMetadata) {
            int[] iArr = obj instanceof int[] ? (int[]) obj : null;
            if (iArr == null) {
                iArr = new int[0];
            }
            for (int i : iArr) {
                requireConstraintsConformance(appFunctionArrayTypeMetadata.getItemType(), str, Integer.valueOf(i));
            }
            return;
        }
        if (itemType instanceof AppFunctionStringTypeMetadata) {
            List listEmptyList = obj instanceof List ? (List) obj : null;
            if (listEmptyList == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            Iterator it = listEmptyList.iterator();
            while (it.hasNext()) {
                requireConstraintsConformance(appFunctionArrayTypeMetadata.getItemType(), str, (String) it.next());
            }
        }
    }

    /* JADX INFO: compiled from: AppFunctionDataSpec.kt */
    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\b\u0006\b\u0082\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0096\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u0017\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u0011H\u0010¢\u0006\u0002\b\u0017J\u0015\u0010\u0018\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u0011H\u0010¢\u0006\u0002\b\u0019J\u0013\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00110\u001bH\u0010¢\u0006\u0002\b\u001cJ\t\u0010\u001d\u001a\u00020\u0003HÂ\u0003J\t\u0010\u001e\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\u001f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\n\u0010 \u001a\u00020\u0011HÖ\u0081\u0004R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0010\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013¨\u0006!"}, d2 = {"Landroidx/appfunctions/AppFunctionDataSpec$ObjectSpec;", "Landroidx/appfunctions/AppFunctionDataSpec;", "objectTypeMetadata", "Landroidx/appfunctions/metadata/AppFunctionObjectTypeMetadata;", "componentMetadata", "Landroidx/appfunctions/metadata/AppFunctionComponentsMetadata;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroidx/appfunctions/metadata/AppFunctionObjectTypeMetadata;Landroidx/appfunctions/metadata/AppFunctionComponentsMetadata;)V", "getComponentMetadata", "()Landroidx/appfunctions/metadata/AppFunctionComponentsMetadata;", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "objectQualifiedName", "", "getObjectQualifiedName", "()Ljava/lang/String;", "getDataType", "Landroidx/appfunctions/metadata/AppFunctionDataTypeMetadata;", "key", "getDataType$appfunctions", "isRequired", "isRequired$appfunctions", "getAllPropertyKeys", "", "getAllPropertyKeys$appfunctions", "component1", "component2", "copy", "toString", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
    private static final /* data */ class ObjectSpec extends AppFunctionDataSpec {
        private final AppFunctionComponentsMetadata componentMetadata;
        private final AppFunctionObjectTypeMetadata objectTypeMetadata;

        /* JADX INFO: renamed from: component1, reason: from getter */
        private final AppFunctionObjectTypeMetadata getObjectTypeMetadata() {
            return this.objectTypeMetadata;
        }

        public static /* synthetic */ ObjectSpec copy$default(ObjectSpec objectSpec, AppFunctionObjectTypeMetadata appFunctionObjectTypeMetadata, AppFunctionComponentsMetadata appFunctionComponentsMetadata, int i, Object obj) {
            if ((i & 1) != 0) {
                appFunctionObjectTypeMetadata = objectSpec.objectTypeMetadata;
            }
            if ((i & 2) != 0) {
                appFunctionComponentsMetadata = objectSpec.componentMetadata;
            }
            return objectSpec.copy(appFunctionObjectTypeMetadata, appFunctionComponentsMetadata);
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final AppFunctionComponentsMetadata getComponentMetadata() {
            return this.componentMetadata;
        }

        public final ObjectSpec copy(AppFunctionObjectTypeMetadata objectTypeMetadata, AppFunctionComponentsMetadata componentMetadata) {
            Intrinsics.checkNotNullParameter(objectTypeMetadata, "objectTypeMetadata");
            Intrinsics.checkNotNullParameter(componentMetadata, "componentMetadata");
            return new ObjectSpec(objectTypeMetadata, componentMetadata);
        }

        public String toString() {
            return "ObjectSpec(objectTypeMetadata=" + this.objectTypeMetadata + ", componentMetadata=" + this.componentMetadata + CoreConstants.RIGHT_PARENTHESIS_CHAR;
        }

        public ObjectSpec(AppFunctionObjectTypeMetadata objectTypeMetadata, AppFunctionComponentsMetadata componentMetadata) {
            Intrinsics.checkNotNullParameter(objectTypeMetadata, "objectTypeMetadata");
            Intrinsics.checkNotNullParameter(componentMetadata, "componentMetadata");
            this.objectTypeMetadata = objectTypeMetadata;
            this.componentMetadata = componentMetadata;
        }

        @Override // androidx.appfunctions.AppFunctionDataSpec
        public AppFunctionComponentsMetadata getComponentMetadata() {
            return this.componentMetadata;
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other instanceof ObjectSpec) {
                return Intrinsics.areEqual(this.objectTypeMetadata, ((ObjectSpec) other).objectTypeMetadata);
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.objectTypeMetadata);
        }

        @Override // androidx.appfunctions.AppFunctionDataSpec
        public String getObjectQualifiedName() {
            String qualifiedName = this.objectTypeMetadata.getQualifiedName();
            return qualifiedName == null ? "" : qualifiedName;
        }

        @Override // androidx.appfunctions.AppFunctionDataSpec
        public AppFunctionDataTypeMetadata getDataType$appfunctions(String key) {
            Intrinsics.checkNotNullParameter(key, "key");
            return this.objectTypeMetadata.getProperties().get(key);
        }

        @Override // androidx.appfunctions.AppFunctionDataSpec
        public boolean isRequired$appfunctions(String key) {
            Intrinsics.checkNotNullParameter(key, "key");
            boolean zContains = this.objectTypeMetadata.getRequired().contains(key);
            AppFunctionDataTypeMetadata appFunctionDataTypeMetadata = this.objectTypeMetadata.getProperties().get(key);
            return zContains && !(appFunctionDataTypeMetadata != null ? appFunctionDataTypeMetadata.getIsNullable() : true);
        }

        @Override // androidx.appfunctions.AppFunctionDataSpec
        public Set<String> getAllPropertyKeys$appfunctions() {
            return this.objectTypeMetadata.getProperties().keySet();
        }
    }

    /* JADX INFO: compiled from: AppFunctionDataSpec.kt */
    @Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\b\u0006\b\u0082\b\u0018\u00002\u00020\u0001B\u001d\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\bJ\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0096\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u0017\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u0012H\u0010¢\u0006\u0002\b\u0018J\u0015\u0010\u0019\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u0012H\u0010¢\u0006\u0002\b\u001aJ\u0013\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00120\u001cH\u0010¢\u0006\u0002\b\u001dJ\u000f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÂ\u0003J\t\u0010\u001f\u001a\u00020\u0006HÆ\u0003J#\u0010 \u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\n\u0010!\u001a\u00020\u0012HÖ\u0081\u0004R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u0011\u001a\u00020\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014¨\u0006\""}, d2 = {"Landroidx/appfunctions/AppFunctionDataSpec$ParametersSpec;", "Landroidx/appfunctions/AppFunctionDataSpec;", "parameterMetadataList", "", "Landroidx/appfunctions/metadata/AppFunctionParameterMetadata;", "componentMetadata", "Landroidx/appfunctions/metadata/AppFunctionComponentsMetadata;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/util/List;Landroidx/appfunctions/metadata/AppFunctionComponentsMetadata;)V", "getComponentMetadata", "()Landroidx/appfunctions/metadata/AppFunctionComponentsMetadata;", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "objectQualifiedName", "", "getObjectQualifiedName", "()Ljava/lang/String;", "getDataType", "Landroidx/appfunctions/metadata/AppFunctionDataTypeMetadata;", "key", "getDataType$appfunctions", "isRequired", "isRequired$appfunctions", "getAllPropertyKeys", "", "getAllPropertyKeys$appfunctions", "component1", "component2", "copy", "toString", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
    private static final /* data */ class ParametersSpec extends AppFunctionDataSpec {
        private final AppFunctionComponentsMetadata componentMetadata;
        private final List<AppFunctionParameterMetadata> parameterMetadataList;

        private final List<AppFunctionParameterMetadata> component1() {
            return this.parameterMetadataList;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ ParametersSpec copy$default(ParametersSpec parametersSpec, List list, AppFunctionComponentsMetadata appFunctionComponentsMetadata, int i, Object obj) {
            if ((i & 1) != 0) {
                list = parametersSpec.parameterMetadataList;
            }
            if ((i & 2) != 0) {
                appFunctionComponentsMetadata = parametersSpec.componentMetadata;
            }
            return parametersSpec.copy(list, appFunctionComponentsMetadata);
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final AppFunctionComponentsMetadata getComponentMetadata() {
            return this.componentMetadata;
        }

        public final ParametersSpec copy(List<AppFunctionParameterMetadata> parameterMetadataList, AppFunctionComponentsMetadata componentMetadata) {
            Intrinsics.checkNotNullParameter(parameterMetadataList, "parameterMetadataList");
            Intrinsics.checkNotNullParameter(componentMetadata, "componentMetadata");
            return new ParametersSpec(parameterMetadataList, componentMetadata);
        }

        public String toString() {
            return "ParametersSpec(parameterMetadataList=" + this.parameterMetadataList + ", componentMetadata=" + this.componentMetadata + CoreConstants.RIGHT_PARENTHESIS_CHAR;
        }

        public ParametersSpec(List<AppFunctionParameterMetadata> parameterMetadataList, AppFunctionComponentsMetadata componentMetadata) {
            Intrinsics.checkNotNullParameter(parameterMetadataList, "parameterMetadataList");
            Intrinsics.checkNotNullParameter(componentMetadata, "componentMetadata");
            this.parameterMetadataList = parameterMetadataList;
            this.componentMetadata = componentMetadata;
        }

        @Override // androidx.appfunctions.AppFunctionDataSpec
        public AppFunctionComponentsMetadata getComponentMetadata() {
            return this.componentMetadata;
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other instanceof ParametersSpec) {
                return Intrinsics.areEqual(this.parameterMetadataList, ((ParametersSpec) other).parameterMetadataList);
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.parameterMetadataList);
        }

        @Override // androidx.appfunctions.AppFunctionDataSpec
        public String getObjectQualifiedName() {
            return "";
        }

        @Override // androidx.appfunctions.AppFunctionDataSpec
        public AppFunctionDataTypeMetadata getDataType$appfunctions(String key) {
            Object next;
            Intrinsics.checkNotNullParameter(key, "key");
            Iterator<T> it = this.parameterMetadataList.iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!Intrinsics.areEqual(((AppFunctionParameterMetadata) next).getName(), key));
            AppFunctionParameterMetadata appFunctionParameterMetadata = (AppFunctionParameterMetadata) next;
            if (appFunctionParameterMetadata != null) {
                return appFunctionParameterMetadata.getDataType();
            }
            return null;
        }

        @Override // androidx.appfunctions.AppFunctionDataSpec
        public boolean isRequired$appfunctions(String key) {
            Object obj;
            Object next;
            AppFunctionDataTypeMetadata dataType;
            Intrinsics.checkNotNullParameter(key, "key");
            Iterator<T> it = this.parameterMetadataList.iterator();
            do {
                obj = null;
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!Intrinsics.areEqual(((AppFunctionParameterMetadata) next).getName(), key));
            AppFunctionParameterMetadata appFunctionParameterMetadata = (AppFunctionParameterMetadata) next;
            boolean isRequired = appFunctionParameterMetadata != null ? appFunctionParameterMetadata.getIsRequired() : false;
            for (Object obj2 : this.parameterMetadataList) {
                if (Intrinsics.areEqual(((AppFunctionParameterMetadata) obj2).getName(), key)) {
                    obj = obj2;
                    break;
                }
            }
            AppFunctionParameterMetadata appFunctionParameterMetadata2 = (AppFunctionParameterMetadata) obj;
            return isRequired && !((appFunctionParameterMetadata2 == null || (dataType = appFunctionParameterMetadata2.getDataType()) == null) ? true : dataType.getIsNullable());
        }

        @Override // androidx.appfunctions.AppFunctionDataSpec
        public Set<String> getAllPropertyKeys$appfunctions() {
            List<AppFunctionParameterMetadata> list = this.parameterMetadataList;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((AppFunctionParameterMetadata) it.next()).getName());
            }
            return CollectionsKt.toSet(arrayList);
        }
    }

    public final boolean conform(AppFunctionDataTypeMetadata appFunctionDataTypeMetadata, Class<?> typeClazz, boolean z) {
        Intrinsics.checkNotNullParameter(appFunctionDataTypeMetadata, "<this>");
        Intrinsics.checkNotNullParameter(typeClazz, "typeClazz");
        if (appFunctionDataTypeMetadata instanceof AppFunctionIntTypeMetadata) {
            return !z && Intrinsics.areEqual(typeClazz, Integer.TYPE);
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionLongTypeMetadata) {
            return !z && Intrinsics.areEqual(typeClazz, Long.TYPE);
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionFloatTypeMetadata) {
            return !z && Intrinsics.areEqual(typeClazz, Float.TYPE);
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionDoubleTypeMetadata) {
            return !z && Intrinsics.areEqual(typeClazz, Double.TYPE);
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionBooleanTypeMetadata) {
            return !z && Intrinsics.areEqual(typeClazz, Boolean.TYPE);
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionStringTypeMetadata) {
            return !z && Intrinsics.areEqual(typeClazz, String.class);
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionBytesTypeMetadata) {
            return z && Intrinsics.areEqual(typeClazz, Byte.TYPE);
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionParcelableTypeMetadata) {
            return !z && Parcelable.class.isAssignableFrom(typeClazz) && Intrinsics.areEqual(typeClazz.getName(), ((AppFunctionParcelableTypeMetadata) appFunctionDataTypeMetadata).getQualifiedName());
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionArrayTypeMetadata) {
            return z && conform((AppFunctionArrayTypeMetadata) appFunctionDataTypeMetadata, typeClazz);
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionObjectTypeMetadata) {
            return !z && conform((AppFunctionObjectTypeMetadata) appFunctionDataTypeMetadata, typeClazz);
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionAllOfTypeMetadata) {
            return !z && conform((AppFunctionAllOfTypeMetadata) appFunctionDataTypeMetadata, typeClazz);
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionReferenceTypeMetadata) {
            return !z && conform((AppFunctionReferenceTypeMetadata) appFunctionDataTypeMetadata, typeClazz);
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionOneOfTypeMetadata) {
            return !z && conform((AppFunctionOneOfTypeMetadata) appFunctionDataTypeMetadata, typeClazz);
        }
        throw new IllegalStateException("Unexpected data type " + appFunctionDataTypeMetadata.getClass());
    }

    private final boolean conform(AppFunctionArrayTypeMetadata appFunctionArrayTypeMetadata, Class<?> cls) {
        return conform(appFunctionArrayTypeMetadata.getItemType(), cls, false);
    }

    private final boolean conform(AppFunctionObjectTypeMetadata appFunctionObjectTypeMetadata, Class<?> cls) {
        return Intrinsics.areEqual(cls, AppFunctionData.class);
    }

    private final boolean conform(AppFunctionAllOfTypeMetadata appFunctionAllOfTypeMetadata, Class<?> cls) {
        return Intrinsics.areEqual(cls, AppFunctionData.class);
    }

    private final boolean conform(AppFunctionOneOfTypeMetadata appFunctionOneOfTypeMetadata, Class<?> cls) {
        return Intrinsics.areEqual(cls, AppFunctionData.class);
    }

    private final boolean conform(AppFunctionReferenceTypeMetadata appFunctionReferenceTypeMetadata, Class<?> cls) {
        return Intrinsics.areEqual(cls, AppFunctionData.class);
    }

    /* JADX INFO: compiled from: AppFunctionDataSpec.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u001c\u0010\u0004\u001a\u00020\u00052\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\b\u001a\u00020\tJ\u0016\u0010\u0004\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\b\u001a\u00020\t¨\u0006\u000f"}, d2 = {"Landroidx/appfunctions/AppFunctionDataSpec$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "create", "Landroidx/appfunctions/AppFunctionDataSpec;", "objectType", "Landroidx/appfunctions/metadata/AppFunctionObjectTypeMetadata;", "componentMetadata", "Landroidx/appfunctions/metadata/AppFunctionComponentsMetadata;", "parameterMetadataList", "", "Landroidx/appfunctions/metadata/AppFunctionParameterMetadata;", "responseMetadata", "Landroidx/appfunctions/metadata/AppFunctionResponseMetadata;", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final AppFunctionDataSpec create(AppFunctionObjectTypeMetadata objectType, AppFunctionComponentsMetadata componentMetadata) {
            Intrinsics.checkNotNullParameter(objectType, "objectType");
            Intrinsics.checkNotNullParameter(componentMetadata, "componentMetadata");
            return new ObjectSpec(objectType, componentMetadata);
        }

        public final AppFunctionDataSpec create(List<AppFunctionParameterMetadata> parameterMetadataList, AppFunctionComponentsMetadata componentMetadata) {
            Intrinsics.checkNotNullParameter(parameterMetadataList, "parameterMetadataList");
            Intrinsics.checkNotNullParameter(componentMetadata, "componentMetadata");
            return new ParametersSpec(parameterMetadataList, componentMetadata);
        }

        public final AppFunctionDataSpec create(AppFunctionResponseMetadata responseMetadata, AppFunctionComponentsMetadata componentMetadata) {
            Intrinsics.checkNotNullParameter(responseMetadata, "responseMetadata");
            Intrinsics.checkNotNullParameter(componentMetadata, "componentMetadata");
            return new ObjectSpec(new AppFunctionObjectTypeMetadata(MapsKt.mapOf(TuplesKt.to(ExecuteAppFunctionResponse.Success.PROPERTY_RETURN_VALUE, responseMetadata.getValueType())), CollectionsKt.listOf(ExecuteAppFunctionResponse.Success.PROPERTY_RETURN_VALUE), null, false, null, 16, null), componentMetadata);
        }
    }
}
