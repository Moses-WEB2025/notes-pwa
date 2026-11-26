package kotlin.reflect.jvm.internal.impl.km.internal.extensions;

import java.util.Collection;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.km.KmClass;
import kotlin.reflect.jvm.internal.impl.km.KmConstructor;
import kotlin.reflect.jvm.internal.impl.km.KmFunction;
import kotlin.reflect.jvm.internal.impl.km.KmPackage;
import kotlin.reflect.jvm.internal.impl.km.KmProperty;
import kotlin.reflect.jvm.internal.impl.km.KmType;
import kotlin.reflect.jvm.internal.impl.km.KmTypeParameter;

/* JADX INFO: compiled from: ExtensionNodes.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class ExtensionNodesKt {
    public static final KmClassExtension getExtension(KmClass kmClass, KmExtensionType type) {
        Intrinsics.checkNotNullParameter(kmClass, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        return (KmClassExtension) singleOfType(kmClass.getExtensions$kotlin_metadata(), type);
    }

    public static final KmPackageExtension getExtension(KmPackage kmPackage, KmExtensionType type) {
        Intrinsics.checkNotNullParameter(kmPackage, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        return (KmPackageExtension) singleOfType(kmPackage.getExtensions$kotlin_metadata(), type);
    }

    public static final KmFunctionExtension getExtension(KmFunction kmFunction, KmExtensionType type) {
        Intrinsics.checkNotNullParameter(kmFunction, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        return (KmFunctionExtension) singleOfType(kmFunction.getExtensions$kotlin_metadata(), type);
    }

    public static final KmPropertyExtension getExtension(KmProperty kmProperty, KmExtensionType type) {
        Intrinsics.checkNotNullParameter(kmProperty, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        return (KmPropertyExtension) singleOfType(kmProperty.getExtensions$kotlin_metadata(), type);
    }

    public static final KmConstructorExtension getExtension(KmConstructor kmConstructor, KmExtensionType type) {
        Intrinsics.checkNotNullParameter(kmConstructor, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        return (KmConstructorExtension) singleOfType(kmConstructor.getExtensions$kotlin_metadata(), type);
    }

    public static final KmTypeParameterExtension getExtension(KmTypeParameter kmTypeParameter, KmExtensionType type) {
        Intrinsics.checkNotNullParameter(kmTypeParameter, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        return (KmTypeParameterExtension) singleOfType(kmTypeParameter.getExtensions$kotlin_metadata(), type);
    }

    public static final KmTypeExtension getExtension(KmType kmType, KmExtensionType type) {
        Intrinsics.checkNotNullParameter(kmType, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        return (KmTypeExtension) singleOfType(kmType.getExtensions$kotlin_metadata(), type);
    }

    private static final <N extends KmExtension> N singleOfType(Collection<? extends N> collection, KmExtensionType kmExtensionType) {
        N n = null;
        for (N n2 : collection) {
            if (Intrinsics.areEqual(n2.getType(), kmExtensionType)) {
                if (n != null) {
                    throw new IllegalStateException("Multiple extensions handle the same extension type: " + kmExtensionType);
                }
                n = n2;
            }
        }
        if (n != null) {
            return n;
        }
        throw new IllegalStateException("No extensions handle the extension type: " + kmExtensionType);
    }
}
