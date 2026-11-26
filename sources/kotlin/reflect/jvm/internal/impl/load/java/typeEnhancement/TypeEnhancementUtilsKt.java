package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.JvmAnnotationNames;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: typeEnhancementUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class TypeEnhancementUtilsKt {
    private static final <T> T select(Set<? extends T> set, T t, T t2, T t3, boolean z) {
        Set<? extends T> set2;
        T t4;
        if (z) {
            if (set.contains(t)) {
                t4 = t;
            } else {
                t4 = set.contains(t2) ? t2 : null;
            }
            if (Intrinsics.areEqual(t4, t) && Intrinsics.areEqual(t3, t2)) {
                return null;
            }
            return t3 == null ? t4 : t3;
        }
        if (t3 != null && (set2 = CollectionsKt.toSet(SetsKt.plus(set, t3))) != null) {
            set = set2;
        }
        return (T) CollectionsKt.singleOrNull(set);
    }

    private static final NullabilityQualifier select(Set<? extends NullabilityQualifier> set, NullabilityQualifier nullabilityQualifier, boolean z) {
        if (nullabilityQualifier == NullabilityQualifier.FORCE_FLEXIBILITY) {
            return NullabilityQualifier.FORCE_FLEXIBILITY;
        }
        return (NullabilityQualifier) select(set, NullabilityQualifier.NOT_NULL, NullabilityQualifier.NULLABLE, nullabilityQualifier, z);
    }

    private static final NullabilityQualifier getNullabilityForErrors(JavaTypeQualifiers javaTypeQualifiers) {
        if (javaTypeQualifiers.isNullabilityQualifierForWarning()) {
            return null;
        }
        return javaTypeQualifiers.getNullability();
    }

    /* JADX WARN: Code duplicated, block: B:49:0x00ea  */
    public static final JavaTypeQualifiers computeQualifiersForOverride(JavaTypeQualifiers javaTypeQualifiers, Collection<JavaTypeQualifiers> superQualifiers, boolean z, boolean z2, boolean z3) {
        NullabilityQualifier nullabilityQualifierSelect;
        Intrinsics.checkNotNullParameter(javaTypeQualifiers, "<this>");
        Intrinsics.checkNotNullParameter(superQualifiers, "superQualifiers");
        Collection<JavaTypeQualifiers> collection = superQualifiers;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            NullabilityQualifier nullabilityForErrors = getNullabilityForErrors((JavaTypeQualifiers) it.next());
            if (nullabilityForErrors != null) {
                arrayList.add(nullabilityForErrors);
            }
        }
        NullabilityQualifier nullabilityQualifierSelect2 = select(CollectionsKt.toSet(arrayList), getNullabilityForErrors(javaTypeQualifiers), z);
        if (nullabilityQualifierSelect2 == null) {
            ArrayList arrayList2 = new ArrayList();
            Iterator<T> it2 = collection.iterator();
            while (it2.hasNext()) {
                NullabilityQualifier nullability = ((JavaTypeQualifiers) it2.next()).getNullability();
                if (nullability != null) {
                    arrayList2.add(nullability);
                }
            }
            nullabilityQualifierSelect = select(CollectionsKt.toSet(arrayList2), javaTypeQualifiers.getNullability(), z);
        } else {
            nullabilityQualifierSelect = nullabilityQualifierSelect2;
        }
        ArrayList arrayList3 = new ArrayList();
        Iterator<T> it3 = collection.iterator();
        while (it3.hasNext()) {
            MutabilityQualifier mutability = ((JavaTypeQualifiers) it3.next()).getMutability();
            if (mutability != null) {
                arrayList3.add(mutability);
            }
        }
        MutabilityQualifier mutabilityQualifier = (MutabilityQualifier) select(CollectionsKt.toSet(arrayList3), MutabilityQualifier.MUTABLE, MutabilityQualifier.READ_ONLY, javaTypeQualifiers.getMutability(), z);
        if (nullabilityQualifierSelect == null || z3 || (z2 && nullabilityQualifierSelect == NullabilityQualifier.NULLABLE)) {
            nullabilityQualifierSelect = null;
        }
        boolean z4 = true;
        boolean z5 = nullabilityQualifierSelect != null && nullabilityQualifierSelect2 == null;
        if (nullabilityQualifierSelect != NullabilityQualifier.NOT_NULL) {
            z4 = false;
        } else if (!isDefinitelyNotNullAndSameSeverity(javaTypeQualifiers, z5)) {
            if (collection.isEmpty()) {
                z4 = false;
            } else {
                Iterator<T> it4 = collection.iterator();
                while (it4.hasNext()) {
                    if (isDefinitelyNotNullAndSameSeverity((JavaTypeQualifiers) it4.next(), z5)) {
                    }
                }
                z4 = false;
            }
        }
        return new JavaTypeQualifiers(nullabilityQualifierSelect, mutabilityQualifier, z4, z5);
    }

    private static final boolean isDefinitelyNotNullAndSameSeverity(JavaTypeQualifiers javaTypeQualifiers, boolean z) {
        return javaTypeQualifiers.isNullabilityQualifierForWarning() == z && javaTypeQualifiers.getDefinitelyNotNull();
    }

    public static final boolean hasEnhancedNullability(TypeSystemCommonBackendContext typeSystemCommonBackendContext, KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(typeSystemCommonBackendContext, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        FqName ENHANCED_NULLABILITY_ANNOTATION = JvmAnnotationNames.ENHANCED_NULLABILITY_ANNOTATION;
        Intrinsics.checkNotNullExpressionValue(ENHANCED_NULLABILITY_ANNOTATION, "ENHANCED_NULLABILITY_ANNOTATION");
        return typeSystemCommonBackendContext.hasAnnotation(type, ENHANCED_NULLABILITY_ANNOTATION);
    }
}
