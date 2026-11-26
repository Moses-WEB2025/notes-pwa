package kotlin.reflect.jvm.internal.impl.km;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.KmConstructorExtension;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.MetadataExtensions;

/* JADX INFO: compiled from: Nodes.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class KmConstructor {
    private final List<KmAnnotation> annotations;
    private final List<KmConstructorExtension> extensions;
    private int flags;
    private final List<KmValueParameter> valueParameters;
    private final List<KmVersionRequirement> versionRequirements;

    public KmConstructor(int i) {
        this.flags = i;
        this.valueParameters = new ArrayList();
        this.versionRequirements = new ArrayList(0);
        this.annotations = new ArrayList(0);
        List<MetadataExtensions> iNSTANCES$kotlin_metadata = MetadataExtensions.Companion.getINSTANCES$kotlin_metadata();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iNSTANCES$kotlin_metadata, 10));
        Iterator<T> it = iNSTANCES$kotlin_metadata.iterator();
        while (it.hasNext()) {
            arrayList.add(((MetadataExtensions) it.next()).createConstructorExtension());
        }
        this.extensions = arrayList;
    }

    public final int getFlags$kotlin_metadata() {
        return this.flags;
    }

    public final void setFlags$kotlin_metadata(int i) {
        this.flags = i;
    }

    public KmConstructor() {
        this(0);
    }

    public final List<KmValueParameter> getValueParameters() {
        return this.valueParameters;
    }

    public final List<KmVersionRequirement> getVersionRequirements() {
        return this.versionRequirements;
    }

    public final List<KmAnnotation> getAnnotations() {
        return this.annotations;
    }

    public final List<KmConstructorExtension> getExtensions$kotlin_metadata() {
        return this.extensions;
    }
}
