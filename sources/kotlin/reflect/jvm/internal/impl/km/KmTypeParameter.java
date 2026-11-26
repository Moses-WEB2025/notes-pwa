package kotlin.reflect.jvm.internal.impl.km;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.KmTypeParameterExtension;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.MetadataExtensions;

/* JADX INFO: compiled from: Nodes.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class KmTypeParameter {
    private final List<KmTypeParameterExtension> extensions;
    private int flags;
    private int id;
    private String name;
    private final List<KmType> upperBounds;
    private KmVariance variance;

    public KmTypeParameter(int i, String name, int i2, KmVariance variance) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(variance, "variance");
        this.flags = i;
        this.name = name;
        this.id = i2;
        this.variance = variance;
        this.upperBounds = new ArrayList(1);
        List<MetadataExtensions> iNSTANCES$kotlin_metadata = MetadataExtensions.Companion.getINSTANCES$kotlin_metadata();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iNSTANCES$kotlin_metadata, 10));
        Iterator<T> it = iNSTANCES$kotlin_metadata.iterator();
        while (it.hasNext()) {
            arrayList.add(((MetadataExtensions) it.next()).createTypeParameterExtension());
        }
        this.extensions = arrayList;
    }

    public final int getFlags$kotlin_metadata() {
        return this.flags;
    }

    public final void setFlags$kotlin_metadata(int i) {
        this.flags = i;
    }

    public final String getName() {
        return this.name;
    }

    public final int getId() {
        return this.id;
    }

    public final KmVariance getVariance() {
        return this.variance;
    }

    public final List<KmType> getUpperBounds() {
        return this.upperBounds;
    }

    public final List<KmTypeParameterExtension> getExtensions$kotlin_metadata() {
        return this.extensions;
    }
}
