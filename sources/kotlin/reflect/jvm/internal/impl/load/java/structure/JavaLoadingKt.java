package kotlin.reflect.jvm.internal.impl.load.java.structure;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.FqName;

/* JADX INFO: compiled from: javaLoading.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class JavaLoadingKt {
    public static final boolean isObjectMethodInInterface(JavaMember javaMember) {
        Intrinsics.checkNotNullParameter(javaMember, "<this>");
        return javaMember.getContainingClass().isInterface() && (javaMember instanceof JavaMethod) && isObjectMethod((JavaMethod) javaMember);
    }

    private static final boolean isObjectMethod(JavaMethod javaMethod) {
        String strAsString = javaMethod.getName().asString();
        int iHashCode = strAsString.hashCode();
        if (iHashCode != -1776922004) {
            if (iHashCode == -1295482945) {
                if (strAsString.equals("equals")) {
                    return isMethodWithOneObjectParameter(javaMethod);
                }
                return false;
            }
            if (iHashCode != 147696667 || !strAsString.equals("hashCode")) {
                return false;
            }
        } else if (!strAsString.equals("toString")) {
            return false;
        }
        return javaMethod.getValueParameters().isEmpty();
    }

    private static final boolean isMethodWithOneObjectParameter(JavaMethod javaMethod) {
        FqName fqName;
        JavaValueParameter javaValueParameter = (JavaValueParameter) CollectionsKt.singleOrNull((List) javaMethod.getValueParameters());
        JavaType type = javaValueParameter != null ? javaValueParameter.getType() : null;
        JavaClassifierType javaClassifierType = type instanceof JavaClassifierType ? (JavaClassifierType) type : null;
        if (javaClassifierType == null) {
            return false;
        }
        JavaClassifier classifier = javaClassifierType.getClassifier();
        return (classifier instanceof JavaClass) && (fqName = ((JavaClass) classifier).getFqName()) != null && Intrinsics.areEqual(fqName.asString(), "java.lang.Object");
    }
}
