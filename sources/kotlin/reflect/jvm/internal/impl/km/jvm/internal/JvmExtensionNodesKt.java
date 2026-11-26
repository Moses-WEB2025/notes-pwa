package kotlin.reflect.jvm.internal.impl.km.jvm.internal;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.km.KmClass;
import kotlin.reflect.jvm.internal.impl.km.KmConstructor;
import kotlin.reflect.jvm.internal.impl.km.KmFunction;
import kotlin.reflect.jvm.internal.impl.km.KmPackage;
import kotlin.reflect.jvm.internal.impl.km.KmProperty;
import kotlin.reflect.jvm.internal.impl.km.KmType;
import kotlin.reflect.jvm.internal.impl.km.KmTypeParameter;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.ExtensionNodesKt;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.KmClassExtension;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.KmConstructorExtension;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.KmFunctionExtension;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.KmPackageExtension;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.KmPropertyExtension;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.KmTypeExtension;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.KmTypeParameterExtension;

/* JADX INFO: compiled from: JvmExtensionNodes.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class JvmExtensionNodesKt {
    public static final JvmClassExtension getJvm(KmClass kmClass) {
        Intrinsics.checkNotNullParameter(kmClass, "<this>");
        KmClassExtension extension = ExtensionNodesKt.getExtension(kmClass, JvmClassExtension.Companion.getTYPE());
        Intrinsics.checkNotNull(extension, "null cannot be cast to non-null type kotlin.metadata.jvm.internal.JvmClassExtension");
        return (JvmClassExtension) extension;
    }

    public static final JvmPackageExtension getJvm(KmPackage kmPackage) {
        Intrinsics.checkNotNullParameter(kmPackage, "<this>");
        KmPackageExtension extension = ExtensionNodesKt.getExtension(kmPackage, JvmPackageExtension.TYPE);
        Intrinsics.checkNotNull(extension, "null cannot be cast to non-null type kotlin.metadata.jvm.internal.JvmPackageExtension");
        return (JvmPackageExtension) extension;
    }

    public static final JvmFunctionExtension getJvm(KmFunction kmFunction) {
        Intrinsics.checkNotNullParameter(kmFunction, "<this>");
        KmFunctionExtension extension = ExtensionNodesKt.getExtension(kmFunction, JvmFunctionExtension.TYPE);
        Intrinsics.checkNotNull(extension, "null cannot be cast to non-null type kotlin.metadata.jvm.internal.JvmFunctionExtension");
        return (JvmFunctionExtension) extension;
    }

    public static final JvmPropertyExtension getJvm(KmProperty kmProperty) {
        Intrinsics.checkNotNullParameter(kmProperty, "<this>");
        KmPropertyExtension extension = ExtensionNodesKt.getExtension(kmProperty, JvmPropertyExtension.TYPE);
        Intrinsics.checkNotNull(extension, "null cannot be cast to non-null type kotlin.metadata.jvm.internal.JvmPropertyExtension");
        return (JvmPropertyExtension) extension;
    }

    public static final JvmConstructorExtension getJvm(KmConstructor kmConstructor) {
        Intrinsics.checkNotNullParameter(kmConstructor, "<this>");
        KmConstructorExtension extension = ExtensionNodesKt.getExtension(kmConstructor, JvmConstructorExtension.TYPE);
        Intrinsics.checkNotNull(extension, "null cannot be cast to non-null type kotlin.metadata.jvm.internal.JvmConstructorExtension");
        return (JvmConstructorExtension) extension;
    }

    public static final JvmTypeParameterExtension getJvm(KmTypeParameter kmTypeParameter) {
        Intrinsics.checkNotNullParameter(kmTypeParameter, "<this>");
        KmTypeParameterExtension extension = ExtensionNodesKt.getExtension(kmTypeParameter, JvmTypeParameterExtension.TYPE);
        Intrinsics.checkNotNull(extension, "null cannot be cast to non-null type kotlin.metadata.jvm.internal.JvmTypeParameterExtension");
        return (JvmTypeParameterExtension) extension;
    }

    public static final JvmTypeExtension getJvm(KmType kmType) {
        Intrinsics.checkNotNullParameter(kmType, "<this>");
        KmTypeExtension extension = ExtensionNodesKt.getExtension(kmType, JvmTypeExtension.TYPE);
        Intrinsics.checkNotNull(extension, "null cannot be cast to non-null type kotlin.metadata.jvm.internal.JvmTypeExtension");
        return (JvmTypeExtension) extension;
    }
}
