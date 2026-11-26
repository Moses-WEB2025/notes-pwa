package kotlin.reflect.jvm.internal.impl.types.checker;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

/* JADX INFO: compiled from: IntersectionType.kt */
/* JADX INFO: loaded from: classes5.dex */
final /* synthetic */ class TypeIntersector$intersectTypesWithoutIntersectionType$filteredSuperAndEqualTypes$1 extends FunctionReferenceImpl implements Function2<KotlinType, KotlinType, Boolean> {
    TypeIntersector$intersectTypesWithoutIntersectionType$filteredSuperAndEqualTypes$1(Object obj) {
        super(2, obj, NewKotlinTypeCheckerImpl.class, "equalTypes", "equalTypes(Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/types/KotlinType;)Z", 0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Boolean invoke(KotlinType p0, KotlinType p1) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        Intrinsics.checkNotNullParameter(p1, "p1");
        return Boolean.valueOf(((NewKotlinTypeCheckerImpl) this.receiver).equalTypes(p0, p1));
    }
}
