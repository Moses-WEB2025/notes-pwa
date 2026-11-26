package kotlin.reflect.jvm.internal.impl.km;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.KmValueParameterExtension;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.MetadataExtensions;

/* JADX INFO: compiled from: Nodes.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class KmValueParameter {
    private KmAnnotationArgument annotationParameterDefaultValue;
    private final List<KmAnnotation> annotations;
    private final List<KmValueParameterExtension> extensions;
    private int flags;
    private String name;
    public KmType type;
    private KmType varargElementType;

    public KmValueParameter(int i, String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        this.flags = i;
        this.name = name;
        this.annotations = new ArrayList(0);
        List<MetadataExtensions> iNSTANCES$kotlin_metadata = MetadataExtensions.Companion.getINSTANCES$kotlin_metadata();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = iNSTANCES$kotlin_metadata.iterator();
        while (it.hasNext()) {
            KmValueParameterExtension kmValueParameterExtensionCreateValueParameterExtension = ((MetadataExtensions) it.next()).createValueParameterExtension();
            if (kmValueParameterExtensionCreateValueParameterExtension != null) {
                arrayList.add(kmValueParameterExtensionCreateValueParameterExtension);
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

    public final String getName() {
        return this.name;
    }

    public final KmType getType() {
        KmType kmType = this.type;
        if (kmType != null) {
            return kmType;
        }
        Intrinsics.throwUninitializedPropertyAccessException("type");
        return null;
    }

    public final void setType(KmType kmType) {
        Intrinsics.checkNotNullParameter(kmType, "<set-?>");
        this.type = kmType;
    }

    public final KmType getVarargElementType() {
        return this.varargElementType;
    }

    public final void setVarargElementType(KmType kmType) {
        this.varargElementType = kmType;
    }

    public final KmAnnotationArgument getAnnotationParameterDefaultValue() {
        return this.annotationParameterDefaultValue;
    }

    public final void setAnnotationParameterDefaultValue(KmAnnotationArgument kmAnnotationArgument) {
        this.annotationParameterDefaultValue = kmAnnotationArgument;
    }

    public final List<KmAnnotation> getAnnotations() {
        return this.annotations;
    }
}
