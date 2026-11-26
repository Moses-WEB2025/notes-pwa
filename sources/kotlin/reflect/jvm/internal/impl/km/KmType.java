package kotlin.reflect.jvm.internal.impl.km;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.KmTypeExtension;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.MetadataExtensions;

/* JADX INFO: compiled from: Nodes.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class KmType {
    private KmType abbreviatedType;
    private final List<KmTypeProjection> arguments;
    public KmClassifier classifier;
    private final List<KmTypeExtension> extensions;
    private int flags;
    private KmFlexibleTypeUpperBound flexibleTypeUpperBound;
    private KmType outerType;

    public KmType(int i) {
        this.flags = i;
        this.arguments = new ArrayList(0);
        List<MetadataExtensions> iNSTANCES$kotlin_metadata = MetadataExtensions.Companion.getINSTANCES$kotlin_metadata();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iNSTANCES$kotlin_metadata, 10));
        Iterator<T> it = iNSTANCES$kotlin_metadata.iterator();
        while (it.hasNext()) {
            arrayList.add(((MetadataExtensions) it.next()).createTypeExtension());
        }
        this.extensions = arrayList;
    }

    public final int getFlags$kotlin_metadata() {
        return this.flags;
    }

    public final void setFlags$kotlin_metadata(int i) {
        this.flags = i;
    }

    public KmType() {
        this(0);
    }

    public final KmClassifier getClassifier() {
        KmClassifier kmClassifier = this.classifier;
        if (kmClassifier != null) {
            return kmClassifier;
        }
        Intrinsics.throwUninitializedPropertyAccessException("classifier");
        return null;
    }

    public final void setClassifier(KmClassifier kmClassifier) {
        Intrinsics.checkNotNullParameter(kmClassifier, "<set-?>");
        this.classifier = kmClassifier;
    }

    public final List<KmTypeProjection> getArguments() {
        return this.arguments;
    }

    public final KmType getAbbreviatedType() {
        return this.abbreviatedType;
    }

    public final void setAbbreviatedType(KmType kmType) {
        this.abbreviatedType = kmType;
    }

    public final KmType getOuterType() {
        return this.outerType;
    }

    public final void setOuterType(KmType kmType) {
        this.outerType = kmType;
    }

    public final KmFlexibleTypeUpperBound getFlexibleTypeUpperBound() {
        return this.flexibleTypeUpperBound;
    }

    public final void setFlexibleTypeUpperBound(KmFlexibleTypeUpperBound kmFlexibleTypeUpperBound) {
        this.flexibleTypeUpperBound = kmFlexibleTypeUpperBound;
    }

    public final List<KmTypeExtension> getExtensions$kotlin_metadata() {
        return this.extensions;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), obj != null ? obj.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.metadata.KmType");
        KmType kmType = (KmType) obj;
        return this.flags == kmType.flags && Intrinsics.areEqual(getClassifier(), kmType.getClassifier()) && Intrinsics.areEqual(this.arguments, kmType.arguments) && Intrinsics.areEqual(this.outerType, kmType.outerType) && Intrinsics.areEqual(this.abbreviatedType, kmType.abbreviatedType) && Intrinsics.areEqual(this.flexibleTypeUpperBound, kmType.flexibleTypeUpperBound) && Intrinsics.areEqual(this.extensions, kmType.extensions);
    }

    public int hashCode() {
        return (((this.flags * 31) + getClassifier().hashCode()) * 31) + this.arguments.hashCode();
    }
}
