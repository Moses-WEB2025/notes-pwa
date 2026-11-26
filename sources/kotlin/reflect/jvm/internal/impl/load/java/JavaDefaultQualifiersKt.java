package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.List;
import java.util.Map;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifierWithMigrationStatus;
import kotlin.reflect.jvm.internal.impl.name.FqName;

/* JADX INFO: compiled from: JavaDefaultQualifiers.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class JavaDefaultQualifiersKt {
    private static final List<AnnotationQualifierApplicabilityType> APPLICABILITY_OF_JAVAX_DEFAULTS;
    private static final List<AnnotationQualifierApplicabilityType> APPLICABILITY_OF_JSPECIFY_DEFAULTS;
    private static final Map<FqName, JavaDefaultQualifiers> BUILT_IN_TYPE_QUALIFIER_DEFAULT_ANNOTATIONS;
    private static final Map<FqName, JavaDefaultQualifiers> JAVAX_DEFAULT_ANNOTATIONS;
    private static final Map<FqName, JavaDefaultQualifiers> JSPECIFY_DEFAULT_ANNOTATIONS;

    static {
        List<AnnotationQualifierApplicabilityType> listListOf = CollectionsKt.listOf((Object[]) new AnnotationQualifierApplicabilityType[]{AnnotationQualifierApplicabilityType.FIELD, AnnotationQualifierApplicabilityType.METHOD_RETURN_TYPE, AnnotationQualifierApplicabilityType.VALUE_PARAMETER, AnnotationQualifierApplicabilityType.TYPE_PARAMETER_BOUNDS, AnnotationQualifierApplicabilityType.TYPE_USE});
        APPLICABILITY_OF_JSPECIFY_DEFAULTS = listListOf;
        List<AnnotationQualifierApplicabilityType> listListOf2 = CollectionsKt.listOf(AnnotationQualifierApplicabilityType.VALUE_PARAMETER);
        APPLICABILITY_OF_JAVAX_DEFAULTS = listListOf2;
        Map<FqName, JavaDefaultQualifiers> mapMapOf = MapsKt.mapOf(TuplesKt.to(JvmAnnotationNamesKt.getJSPECIFY_OLD_NULL_MARKED_ANNOTATION_FQ_NAME(), new JavaDefaultQualifiers(new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, false, 2, null), listListOf, false)), TuplesKt.to(JvmAnnotationNamesKt.getJSPECIFY_NULL_MARKED_ANNOTATION_FQ_NAME(), new JavaDefaultQualifiers(new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, false, 2, null), listListOf, false)), TuplesKt.to(JvmAnnotationNamesKt.getJSPECIFY_NULL_UNMARKED_ANNOTATION_FQ_NAME(), new JavaDefaultQualifiers(new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.FORCE_FLEXIBILITY, false, 2, null), listListOf, false, 4, null)));
        JSPECIFY_DEFAULT_ANNOTATIONS = mapMapOf;
        Map<FqName, JavaDefaultQualifiers> mapMapOf2 = MapsKt.mapOf(TuplesKt.to(JvmAnnotationNamesKt.getJAVAX_PARAMETERS_ARE_NONNULL_BY_DEFAULT_ANNOTATION_FQ_NAME(), new JavaDefaultQualifiers(new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, false, 2, null), listListOf2, false, 4, null)), TuplesKt.to(JvmAnnotationNamesKt.getJAVAX_PARAMETERS_ARE_NULLABLE_BY_DEFAULT_ANNOTATION_FQ_NAME(), new JavaDefaultQualifiers(new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, false, 2, null), listListOf2, false, 4, null)));
        JAVAX_DEFAULT_ANNOTATIONS = mapMapOf2;
        BUILT_IN_TYPE_QUALIFIER_DEFAULT_ANNOTATIONS = MapsKt.plus(mapMapOf, mapMapOf2);
    }

    public static final Map<FqName, JavaDefaultQualifiers> getJSPECIFY_DEFAULT_ANNOTATIONS() {
        return JSPECIFY_DEFAULT_ANNOTATIONS;
    }

    public static final Map<FqName, JavaDefaultQualifiers> getBUILT_IN_TYPE_QUALIFIER_DEFAULT_ANNOTATIONS() {
        return BUILT_IN_TYPE_QUALIFIER_DEFAULT_ANNOTATIONS;
    }
}
