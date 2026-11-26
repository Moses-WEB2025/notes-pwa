package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.model.FlexibleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.RigidTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext;

/* JADX INFO: compiled from: AbstractStrictEqualityTypeChecker.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class AbstractStrictEqualityTypeChecker {
    public static final AbstractStrictEqualityTypeChecker INSTANCE = new AbstractStrictEqualityTypeChecker();

    private AbstractStrictEqualityTypeChecker() {
    }

    public final boolean strictEqualTypes(TypeSystemContext context, KotlinTypeMarker a, KotlinTypeMarker b) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        return strictEqualTypesInternal(context, a, b);
    }

    private final boolean strictEqualTypesInternal(TypeSystemContext typeSystemContext, KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker kotlinTypeMarker2) {
        if (kotlinTypeMarker == kotlinTypeMarker2) {
            return true;
        }
        RigidTypeMarker rigidTypeMarkerAsRigidType = typeSystemContext.asRigidType(kotlinTypeMarker);
        RigidTypeMarker rigidTypeMarkerAsRigidType2 = typeSystemContext.asRigidType(kotlinTypeMarker2);
        if (rigidTypeMarkerAsRigidType != null && rigidTypeMarkerAsRigidType2 != null) {
            return strictEqualRigidTypes(typeSystemContext, rigidTypeMarkerAsRigidType, rigidTypeMarkerAsRigidType2);
        }
        FlexibleTypeMarker flexibleTypeMarkerAsFlexibleType = typeSystemContext.asFlexibleType(kotlinTypeMarker);
        FlexibleTypeMarker flexibleTypeMarkerAsFlexibleType2 = typeSystemContext.asFlexibleType(kotlinTypeMarker2);
        return flexibleTypeMarkerAsFlexibleType != null && flexibleTypeMarkerAsFlexibleType2 != null && strictEqualRigidTypes(typeSystemContext, typeSystemContext.lowerBound(flexibleTypeMarkerAsFlexibleType), typeSystemContext.lowerBound(flexibleTypeMarkerAsFlexibleType2)) && strictEqualRigidTypes(typeSystemContext, typeSystemContext.upperBound(flexibleTypeMarkerAsFlexibleType), typeSystemContext.upperBound(flexibleTypeMarkerAsFlexibleType2));
    }

    private final boolean strictEqualRigidTypes(TypeSystemContext typeSystemContext, RigidTypeMarker rigidTypeMarker, RigidTypeMarker rigidTypeMarker2) {
        RigidTypeMarker rigidTypeMarker3 = rigidTypeMarker;
        RigidTypeMarker rigidTypeMarker4 = rigidTypeMarker2;
        if (typeSystemContext.argumentsCount(rigidTypeMarker3) != typeSystemContext.argumentsCount(rigidTypeMarker4) || typeSystemContext.isMarkedNullable(rigidTypeMarker3) != typeSystemContext.isMarkedNullable(rigidTypeMarker4) || typeSystemContext.isDefinitelyNotNullType(rigidTypeMarker) != typeSystemContext.isDefinitelyNotNullType(rigidTypeMarker2) || !typeSystemContext.areEqualTypeConstructors(typeSystemContext.typeConstructor(rigidTypeMarker), typeSystemContext.typeConstructor(rigidTypeMarker2))) {
            return false;
        }
        if (typeSystemContext.identicalArguments(rigidTypeMarker, rigidTypeMarker2)) {
            return true;
        }
        int iArgumentsCount = typeSystemContext.argumentsCount(rigidTypeMarker3);
        for (int i = 0; i < iArgumentsCount; i++) {
            TypeArgumentMarker argument = typeSystemContext.getArgument(rigidTypeMarker3, i);
            TypeArgumentMarker argument2 = typeSystemContext.getArgument(rigidTypeMarker4, i);
            if (typeSystemContext.isStarProjection(argument) != typeSystemContext.isStarProjection(argument2)) {
                return false;
            }
            if (!typeSystemContext.isStarProjection(argument)) {
                if (typeSystemContext.getVariance(argument) != typeSystemContext.getVariance(argument2)) {
                    return false;
                }
                KotlinTypeMarker type = typeSystemContext.getType(argument);
                Intrinsics.checkNotNull(type);
                KotlinTypeMarker type2 = typeSystemContext.getType(argument2);
                Intrinsics.checkNotNull(type2);
                if (!strictEqualTypesInternal(typeSystemContext, type, type2)) {
                    return false;
                }
            }
        }
        return true;
    }
}
