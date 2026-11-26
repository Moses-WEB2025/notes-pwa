package kotlin.reflect.jvm.internal.impl.km;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.KmTypeAliasExtension;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.MetadataExtensions;

/* JADX INFO: compiled from: Nodes.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class KmTypeAlias {
    private final List<KmAnnotation> annotations;
    public KmType expandedType;
    private final List<KmTypeAliasExtension> extensions;
    private int flags;
    private String name;
    private final List<KmTypeParameter> typeParameters;
    public KmType underlyingType;
    private final List<KmVersionRequirement> versionRequirements;

    public KmTypeAlias(int i, String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        this.flags = i;
        this.name = name;
        this.typeParameters = new ArrayList(0);
        this.annotations = new ArrayList(0);
        this.versionRequirements = new ArrayList(0);
        List<MetadataExtensions> iNSTANCES$kotlin_metadata = MetadataExtensions.Companion.getINSTANCES$kotlin_metadata();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = iNSTANCES$kotlin_metadata.iterator();
        while (it.hasNext()) {
            KmTypeAliasExtension kmTypeAliasExtensionCreateTypeAliasExtension = ((MetadataExtensions) it.next()).createTypeAliasExtension();
            if (kmTypeAliasExtensionCreateTypeAliasExtension != null) {
                arrayList.add(kmTypeAliasExtensionCreateTypeAliasExtension);
            }
        }
        this.extensions = arrayList;
    }

    public final int getFlags$kotlin_metadata() {
        return this.flags;
    }

    public final void setFlags$kotlin_metadata(int i) {
        this.flags = i;
    }

    public final List<KmTypeParameter> getTypeParameters() {
        return this.typeParameters;
    }

    public final void setUnderlyingType(KmType kmType) {
        Intrinsics.checkNotNullParameter(kmType, "<set-?>");
        this.underlyingType = kmType;
    }

    public final void setExpandedType(KmType kmType) {
        Intrinsics.checkNotNullParameter(kmType, "<set-?>");
        this.expandedType = kmType;
    }

    public final List<KmAnnotation> getAnnotations() {
        return this.annotations;
    }

    public final List<KmVersionRequirement> getVersionRequirements() {
        return this.versionRequirements;
    }
}
