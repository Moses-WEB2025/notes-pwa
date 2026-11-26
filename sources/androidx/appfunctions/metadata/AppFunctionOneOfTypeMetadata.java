package androidx.appfunctions.metadata;

import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import defpackage.R2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AppFunctionDataTypeMetadata.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u0000 \u001a2\u00020\u0001:\u0001\u001aB1\b\u0007\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005¢\u0006\u0004\b\t\u0010\nJ\r\u0010\u000f\u001a\u00020\u0010H\u0010¢\u0006\u0002\b\u0011J\u0013\u0010\u0012\u001a\u00020\u00072\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0096\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0005H\u0016J\u0015\u0010\u0018\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0005H\u0000¢\u0006\u0002\b\u0019R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001b"}, d2 = {"Landroidx/appfunctions/metadata/AppFunctionOneOfTypeMetadata;", "Landroidx/appfunctions/metadata/AppFunctionDataTypeMetadata;", "matchOneOf", "", "qualifiedName", "", "isNullable", "", "description", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/util/List;Ljava/lang/String;ZLjava/lang/String;)V", "getMatchOneOf", "()Ljava/util/List;", "getQualifiedName", "()Ljava/lang/String;", "toAppFunctionDataTypeMetadataDocument", "Landroidx/appfunctions/metadata/AppFunctionDataTypeMetadataDocument;", "toAppFunctionDataTypeMetadataDocument$appfunctions", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "toString", "getObjectMetadataForOneOfType", "getObjectMetadataForOneOfType$appfunctions", "Companion", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class AppFunctionOneOfTypeMetadata extends AppFunctionDataTypeMetadata {
    public static final int TYPE = 14;
    private final List<AppFunctionDataTypeMetadata> matchOneOf;
    private final String qualifiedName;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public AppFunctionOneOfTypeMetadata(List<? extends AppFunctionDataTypeMetadata> matchOneOf, String qualifiedName, boolean z) {
        this(matchOneOf, qualifiedName, z, null, 8, null);
        Intrinsics.checkNotNullParameter(matchOneOf, "matchOneOf");
        Intrinsics.checkNotNullParameter(qualifiedName, "qualifiedName");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public AppFunctionOneOfTypeMetadata(List<? extends AppFunctionDataTypeMetadata> matchOneOf, String qualifiedName, boolean z, String description) {
        super(z, description);
        Intrinsics.checkNotNullParameter(matchOneOf, "matchOneOf");
        Intrinsics.checkNotNullParameter(qualifiedName, "qualifiedName");
        Intrinsics.checkNotNullParameter(description, "description");
        this.matchOneOf = matchOneOf;
        this.qualifiedName = qualifiedName;
    }

    public final List<AppFunctionDataTypeMetadata> getMatchOneOf() {
        return this.matchOneOf;
    }

    public final String getQualifiedName() {
        return this.qualifiedName;
    }

    public /* synthetic */ AppFunctionOneOfTypeMetadata(List list, String str, boolean z, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, str, z, (i & 8) != 0 ? "" : str2);
    }

    @Override // androidx.appfunctions.metadata.AppFunctionDataTypeMetadata
    public AppFunctionDataTypeMetadataDocument toAppFunctionDataTypeMetadataDocument$appfunctions() {
        List<AppFunctionDataTypeMetadata> list = this.matchOneOf;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((AppFunctionDataTypeMetadata) it.next()).toAppFunctionDataTypeMetadataDocument$appfunctions());
        }
        ArrayList arrayList2 = arrayList;
        boolean zIsNullable = getIsNullable();
        String str = this.qualifiedName;
        String description = getDescription();
        if (description.length() == 0) {
            description = null;
        }
        return new AppFunctionDataTypeMetadataDocument(null, null, 14, null, null, null, arrayList2, null, null, zIsNullable, str, description, null, R2.color.miuix_appcompat_primary_color_dark, null);
    }

    @Override // androidx.appfunctions.metadata.AppFunctionDataTypeMetadata
    public boolean equals(Object other) {
        if (!super.equals(other) || !(other instanceof AppFunctionOneOfTypeMetadata)) {
            return false;
        }
        AppFunctionOneOfTypeMetadata appFunctionOneOfTypeMetadata = (AppFunctionOneOfTypeMetadata) other;
        if (Intrinsics.areEqual(this.qualifiedName, appFunctionOneOfTypeMetadata.qualifiedName)) {
            return Intrinsics.areEqual(this.matchOneOf, appFunctionOneOfTypeMetadata.matchOneOf);
        }
        return false;
    }

    @Override // androidx.appfunctions.metadata.AppFunctionDataTypeMetadata
    public int hashCode() {
        return (((super.hashCode() * 31) + this.matchOneOf.hashCode()) * 31) + this.qualifiedName.hashCode();
    }

    @Override // androidx.appfunctions.metadata.AppFunctionDataTypeMetadata
    public String toString() {
        return "AppFunctionOneOfTypeMetadata(matchOneOf=" + this.matchOneOf + ", isNullable=" + getIsNullable() + ", description=" + getDescription() + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public final AppFunctionDataTypeMetadata getObjectMetadataForOneOfType$appfunctions(String qualifiedName) {
        boolean zAreEqual;
        Intrinsics.checkNotNullParameter(qualifiedName, "qualifiedName");
        Iterator<T> it = this.matchOneOf.iterator();
        Object obj = null;
        boolean z = false;
        Object obj2 = null;
        while (true) {
            if (!it.hasNext()) {
                if (!z) {
                    break;
                }
                obj = obj2;
                break;
            }
            Object next = it.next();
            AppFunctionDataTypeMetadata appFunctionDataTypeMetadata = (AppFunctionDataTypeMetadata) next;
            if (appFunctionDataTypeMetadata instanceof AppFunctionObjectTypeMetadata) {
                zAreEqual = Intrinsics.areEqual(((AppFunctionObjectTypeMetadata) appFunctionDataTypeMetadata).getQualifiedName(), qualifiedName);
            } else if (appFunctionDataTypeMetadata instanceof AppFunctionReferenceTypeMetadata) {
                zAreEqual = Intrinsics.areEqual(((AppFunctionReferenceTypeMetadata) appFunctionDataTypeMetadata).getReferenceDataType(), qualifiedName);
            } else {
                if (!(appFunctionDataTypeMetadata instanceof AppFunctionAllOfTypeMetadata)) {
                    throw new IllegalArgumentException("Unexpected data type " + appFunctionDataTypeMetadata + " for one of type");
                }
                zAreEqual = Intrinsics.areEqual(((AppFunctionAllOfTypeMetadata) appFunctionDataTypeMetadata).getQualifiedName(), qualifiedName);
            }
            if (zAreEqual) {
                if (z) {
                    break;
                }
                z = true;
                obj2 = next;
            }
        }
        AppFunctionDataTypeMetadata appFunctionDataTypeMetadata2 = (AppFunctionDataTypeMetadata) obj;
        if (appFunctionDataTypeMetadata2 != null) {
            return appFunctionDataTypeMetadata2;
        }
        throw new IllegalArgumentException(qualifiedName + " does not match any of the oneOf types");
    }
}
