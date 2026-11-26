package kotlin.reflect.jvm.internal.impl.km.internal;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.MetadataExtensions;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirementTable;

/* JADX INFO: compiled from: Readers.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class ReadContext {
    private final List<Object> contextExtensions;
    private final List<MetadataExtensions> extensions;
    private final boolean ignoreUnknownVersionRequirements;
    private final ReadContext parent;
    private final NameResolver strings;
    private final Map<Integer, Integer> typeParameterNameToId;
    private final TypeTable types;
    private final VersionRequirementTable versionRequirements;

    public ReadContext(NameResolver strings, TypeTable types, VersionRequirementTable versionRequirements, boolean z, ReadContext readContext, List<? extends Object> contextExtensions) {
        Intrinsics.checkNotNullParameter(strings, "strings");
        Intrinsics.checkNotNullParameter(types, "types");
        Intrinsics.checkNotNullParameter(versionRequirements, "versionRequirements");
        Intrinsics.checkNotNullParameter(contextExtensions, "contextExtensions");
        this.strings = strings;
        this.types = types;
        this.versionRequirements = versionRequirements;
        this.ignoreUnknownVersionRequirements = z;
        this.parent = readContext;
        this.contextExtensions = contextExtensions;
        this.typeParameterNameToId = new LinkedHashMap();
        this.extensions = MetadataExtensions.Companion.getINSTANCES$kotlin_metadata();
    }

    public final NameResolver getStrings() {
        return this.strings;
    }

    public final TypeTable getTypes() {
        return this.types;
    }

    public final VersionRequirementTable getVersionRequirements$kotlin_metadata() {
        return this.versionRequirements;
    }

    public final boolean getIgnoreUnknownVersionRequirements$kotlin_metadata() {
        return this.ignoreUnknownVersionRequirements;
    }

    public /* synthetic */ ReadContext(NameResolver nameResolver, TypeTable typeTable, VersionRequirementTable versionRequirementTable, boolean z, ReadContext readContext, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(nameResolver, typeTable, versionRequirementTable, z, (i & 16) != 0 ? null : readContext, (i & 32) != 0 ? CollectionsKt.emptyList() : list);
    }

    public final List<MetadataExtensions> getExtensions$kotlin_metadata() {
        return this.extensions;
    }

    public final String get(int i) {
        return this.strings.getString(i);
    }

    public final String className$kotlin_metadata(int i) {
        return ReadUtilsKt.getClassName(this.strings, i);
    }

    public final Integer getTypeParameterId$kotlin_metadata(int i) {
        Integer num = this.typeParameterNameToId.get(Integer.valueOf(i));
        if (num != null) {
            return num;
        }
        ReadContext readContext = this.parent;
        if (readContext != null) {
            return readContext.getTypeParameterId$kotlin_metadata(i);
        }
        return null;
    }

    public final ReadContext withTypeParameters$kotlin_metadata(List<ProtoBuf.TypeParameter> typeParameters) {
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        ReadContext readContext = new ReadContext(this.strings, this.types, this.versionRequirements, this.ignoreUnknownVersionRequirements, this, this.contextExtensions);
        for (ProtoBuf.TypeParameter typeParameter : typeParameters) {
            readContext.typeParameterNameToId.put(Integer.valueOf(typeParameter.getName()), Integer.valueOf(typeParameter.getId()));
        }
        return readContext;
    }
}
