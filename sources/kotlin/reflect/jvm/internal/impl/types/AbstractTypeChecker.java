package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.model.ArgumentList;
import kotlin.reflect.jvm.internal.impl.types.model.CaptureStatus;
import kotlin.reflect.jvm.internal.impl.types.model.CapturedTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.FlexibleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.IntersectionTypeConstructorMarker;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.RigidTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentListMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeConstructorMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeParameterMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext;
import kotlin.reflect.jvm.internal.impl.types.model.TypeSystemInferenceExtensionContext;
import kotlin.reflect.jvm.internal.impl.types.model.TypeVariableTypeConstructorMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeVariance;
import kotlin.reflect.jvm.internal.impl.utils.SmartList;

/* JADX INFO: compiled from: AbstractTypeChecker.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class AbstractTypeChecker {
    public static final AbstractTypeChecker INSTANCE = new AbstractTypeChecker();
    public static boolean RUN_SLOW_ASSERTIONS;

    /* JADX INFO: compiled from: AbstractTypeChecker.kt */
    public static final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[TypeVariance.values().length];
            try {
                iArr[TypeVariance.INV.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TypeVariance.OUT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TypeVariance.IN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[TypeCheckerState.LowerCapturedTypePolicy.values().length];
            try {
                iArr2[TypeCheckerState.LowerCapturedTypePolicy.CHECK_ONLY_LOWER.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[TypeCheckerState.LowerCapturedTypePolicy.CHECK_SUBTYPE_AND_LOWER.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[TypeCheckerState.LowerCapturedTypePolicy.SKIP_LOWER.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public final boolean isSubtypeOf(TypeCheckerState state, KotlinTypeMarker subType, KotlinTypeMarker superType) {
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(subType, "subType");
        Intrinsics.checkNotNullParameter(superType, "superType");
        return isSubtypeOf$default(this, state, subType, superType, false, 8, null);
    }

    private AbstractTypeChecker() {
    }

    public static /* synthetic */ boolean isSubtypeOf$default(AbstractTypeChecker abstractTypeChecker, TypeCheckerState typeCheckerState, KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker kotlinTypeMarker2, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = false;
        }
        return abstractTypeChecker.isSubtypeOf(typeCheckerState, kotlinTypeMarker, kotlinTypeMarker2, z);
    }

    public final boolean isSubtypeOf(TypeCheckerState state, KotlinTypeMarker subType, KotlinTypeMarker superType, boolean z) {
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(subType, "subType");
        Intrinsics.checkNotNullParameter(superType, "superType");
        if (subType == superType) {
            return true;
        }
        if (state.customIsSubtypeOf(subType, superType)) {
            return completeIsSubTypeOf(state, subType, superType, z);
        }
        return false;
    }

    public final boolean equalTypes(TypeCheckerState state, KotlinTypeMarker a, KotlinTypeMarker b) {
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        TypeSystemContext typeSystemContext = state.getTypeSystemContext();
        if (a == b) {
            return true;
        }
        AbstractTypeChecker abstractTypeChecker = INSTANCE;
        if (abstractTypeChecker.isCommonDenotableType(typeSystemContext, a) && abstractTypeChecker.isCommonDenotableType(typeSystemContext, b)) {
            KotlinTypeMarker kotlinTypeMarkerPrepareType = state.prepareType(state.refineType(a));
            KotlinTypeMarker kotlinTypeMarkerPrepareType2 = state.prepareType(state.refineType(b));
            RigidTypeMarker rigidTypeMarkerLowerBoundIfFlexible = typeSystemContext.lowerBoundIfFlexible(kotlinTypeMarkerPrepareType);
            if (!typeSystemContext.areEqualTypeConstructors(typeSystemContext.typeConstructor(kotlinTypeMarkerPrepareType), typeSystemContext.typeConstructor(kotlinTypeMarkerPrepareType2))) {
                return false;
            }
            RigidTypeMarker rigidTypeMarker = rigidTypeMarkerLowerBoundIfFlexible;
            if (typeSystemContext.argumentsCount(rigidTypeMarker) == 0) {
                return typeSystemContext.hasFlexibleNullability(kotlinTypeMarkerPrepareType) || typeSystemContext.hasFlexibleNullability(kotlinTypeMarkerPrepareType2) || typeSystemContext.isMarkedNullable(rigidTypeMarker) == typeSystemContext.isMarkedNullable(typeSystemContext.lowerBoundIfFlexible(kotlinTypeMarkerPrepareType2));
            }
        }
        return isSubtypeOf$default(abstractTypeChecker, state, a, b, false, 8, null) && isSubtypeOf$default(abstractTypeChecker, state, b, a, false, 8, null);
    }

    private final boolean completeIsSubTypeOf(TypeCheckerState typeCheckerState, KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker kotlinTypeMarker2, boolean z) {
        TypeSystemContext typeSystemContext = typeCheckerState.getTypeSystemContext();
        KotlinTypeMarker kotlinTypeMarkerPrepareType = typeCheckerState.prepareType(typeCheckerState.refineType(kotlinTypeMarker));
        KotlinTypeMarker kotlinTypeMarkerPrepareType2 = typeCheckerState.prepareType(typeCheckerState.refineType(kotlinTypeMarker2));
        if (typeCheckerState.isDnnTypesEqualToFlexible() && typeSystemContext.isFlexible(kotlinTypeMarkerPrepareType) && typeSystemContext.isDefinitelyNotNullType(kotlinTypeMarkerPrepareType2)) {
            AbstractTypeChecker abstractTypeChecker = INSTANCE;
            FlexibleTypeMarker flexibleTypeMarkerAsFlexibleType = typeSystemContext.asFlexibleType(kotlinTypeMarkerPrepareType);
            Intrinsics.checkNotNull(flexibleTypeMarkerAsFlexibleType);
            RigidTypeMarker rigidTypeMarkerLowerBound = typeSystemContext.lowerBound(flexibleTypeMarkerAsFlexibleType);
            RigidTypeMarker rigidTypeMarkerAsRigidType = typeSystemContext.asRigidType(kotlinTypeMarkerPrepareType2);
            Intrinsics.checkNotNull(rigidTypeMarkerAsRigidType);
            return abstractTypeChecker.completeIsSubTypeOf(typeCheckerState, rigidTypeMarkerLowerBound, typeSystemContext.originalIfDefinitelyNotNullable(rigidTypeMarkerAsRigidType), z);
        }
        AbstractTypeChecker abstractTypeChecker2 = INSTANCE;
        Boolean boolCheckSubtypeForSpecialCases = abstractTypeChecker2.checkSubtypeForSpecialCases(typeCheckerState, typeSystemContext.lowerBoundIfFlexible(kotlinTypeMarkerPrepareType), typeSystemContext.upperBoundIfFlexible(kotlinTypeMarkerPrepareType2));
        if (boolCheckSubtypeForSpecialCases != null) {
            boolean zBooleanValue = boolCheckSubtypeForSpecialCases.booleanValue();
            typeCheckerState.addSubtypeConstraint(kotlinTypeMarkerPrepareType, kotlinTypeMarkerPrepareType2, z);
            return zBooleanValue;
        }
        Boolean boolAddSubtypeConstraint = typeCheckerState.addSubtypeConstraint(kotlinTypeMarkerPrepareType, kotlinTypeMarkerPrepareType2, z);
        return boolAddSubtypeConstraint != null ? boolAddSubtypeConstraint.booleanValue() : abstractTypeChecker2.isSubtypeOfForSingleClassifierType(typeCheckerState, typeSystemContext.lowerBoundIfFlexible(kotlinTypeMarkerPrepareType), typeSystemContext.upperBoundIfFlexible(kotlinTypeMarkerPrepareType2));
    }

    private final Boolean checkSubtypeForIntegerLiteralType(TypeCheckerState typeCheckerState, RigidTypeMarker rigidTypeMarker, RigidTypeMarker rigidTypeMarker2) {
        TypeSystemContext typeSystemContext = typeCheckerState.getTypeSystemContext();
        if (!typeSystemContext.isIntegerLiteralType(rigidTypeMarker) && !typeSystemContext.isIntegerLiteralType(rigidTypeMarker2)) {
            return null;
        }
        if (checkSubtypeForIntegerLiteralType$lambda$7$isIntegerLiteralTypeOrCapturedOne(typeSystemContext, rigidTypeMarker) && checkSubtypeForIntegerLiteralType$lambda$7$isIntegerLiteralTypeOrCapturedOne(typeSystemContext, rigidTypeMarker2)) {
            return true;
        }
        if (typeSystemContext.isIntegerLiteralType(rigidTypeMarker)) {
            if (checkSubtypeForIntegerLiteralType$lambda$7$isTypeInIntegerLiteralType(typeSystemContext, typeCheckerState, rigidTypeMarker, rigidTypeMarker2, false)) {
                return true;
            }
        } else if (typeSystemContext.isIntegerLiteralType(rigidTypeMarker2) && (checkSubtypeForIntegerLiteralType$lambda$7$isIntegerLiteralTypeInIntersectionComponents(typeSystemContext, rigidTypeMarker) || checkSubtypeForIntegerLiteralType$lambda$7$isTypeInIntegerLiteralType(typeSystemContext, typeCheckerState, rigidTypeMarker2, rigidTypeMarker, true))) {
            return true;
        }
        return null;
    }

    private static final boolean checkSubtypeForIntegerLiteralType$lambda$7$isTypeInIntegerLiteralType(TypeSystemContext typeSystemContext, TypeCheckerState typeCheckerState, RigidTypeMarker rigidTypeMarker, RigidTypeMarker rigidTypeMarker2, boolean z) {
        TypeCheckerState typeCheckerState2;
        Collection<KotlinTypeMarker> collectionPossibleIntegerTypes = typeSystemContext.possibleIntegerTypes(rigidTypeMarker);
        if ((collectionPossibleIntegerTypes instanceof Collection) && collectionPossibleIntegerTypes.isEmpty()) {
            return false;
        }
        for (KotlinTypeMarker kotlinTypeMarker : collectionPossibleIntegerTypes) {
            if (Intrinsics.areEqual(typeSystemContext.typeConstructor(kotlinTypeMarker), typeSystemContext.typeConstructor(rigidTypeMarker2))) {
                return true;
            }
            if (z) {
                typeCheckerState2 = typeCheckerState;
                if (isSubtypeOf$default(INSTANCE, typeCheckerState2, rigidTypeMarker2, kotlinTypeMarker, false, 8, null)) {
                    return true;
                }
            } else {
                typeCheckerState2 = typeCheckerState;
            }
            typeCheckerState = typeCheckerState2;
        }
        return false;
    }

    private static final boolean checkSubtypeForIntegerLiteralType$lambda$7$isIntegerLiteralTypeInIntersectionComponents(TypeSystemContext typeSystemContext, RigidTypeMarker rigidTypeMarker) {
        TypeConstructorMarker typeConstructorMarkerTypeConstructor = typeSystemContext.typeConstructor(rigidTypeMarker);
        if (!(typeConstructorMarkerTypeConstructor instanceof IntersectionTypeConstructorMarker)) {
            return false;
        }
        Collection<KotlinTypeMarker> collectionSupertypes = typeSystemContext.supertypes(typeConstructorMarkerTypeConstructor);
        if ((collectionSupertypes instanceof Collection) && collectionSupertypes.isEmpty()) {
            return false;
        }
        Iterator<T> it = collectionSupertypes.iterator();
        while (it.hasNext()) {
            RigidTypeMarker rigidTypeMarkerAsRigidType = typeSystemContext.asRigidType((KotlinTypeMarker) it.next());
            if (rigidTypeMarkerAsRigidType != null && typeSystemContext.isIntegerLiteralType(rigidTypeMarkerAsRigidType)) {
                return true;
            }
        }
        return false;
    }

    private static final boolean checkSubtypeForIntegerLiteralType$lambda$7$isCapturedIntegerLiteralType(TypeSystemContext typeSystemContext, RigidTypeMarker rigidTypeMarker) {
        KotlinTypeMarker type;
        RigidTypeMarker rigidTypeMarkerUpperBoundIfFlexible;
        return (rigidTypeMarker instanceof CapturedTypeMarker) && (type = typeSystemContext.getType(typeSystemContext.projection(typeSystemContext.typeConstructor((CapturedTypeMarker) rigidTypeMarker)))) != null && (rigidTypeMarkerUpperBoundIfFlexible = typeSystemContext.upperBoundIfFlexible(type)) != null && typeSystemContext.isIntegerLiteralType(rigidTypeMarkerUpperBoundIfFlexible);
    }

    private static final boolean checkSubtypeForIntegerLiteralType$lambda$7$isIntegerLiteralTypeOrCapturedOne(TypeSystemContext typeSystemContext, RigidTypeMarker rigidTypeMarker) {
        return typeSystemContext.isIntegerLiteralType(rigidTypeMarker) || checkSubtypeForIntegerLiteralType$lambda$7$isCapturedIntegerLiteralType(typeSystemContext, rigidTypeMarker);
    }

    private final boolean hasNothingSupertype(TypeCheckerState typeCheckerState, RigidTypeMarker rigidTypeMarker) {
        TypeCheckerState.SupertypesPolicy.LowerIfFlexible lowerIfFlexible;
        TypeSystemContext typeSystemContext = typeCheckerState.getTypeSystemContext();
        TypeConstructorMarker typeConstructorMarkerTypeConstructor = typeSystemContext.typeConstructor(rigidTypeMarker);
        if (typeSystemContext.isClassTypeConstructor(typeConstructorMarkerTypeConstructor)) {
            return typeSystemContext.isNothingConstructor(typeConstructorMarkerTypeConstructor);
        }
        if (typeSystemContext.isNothingConstructor(typeSystemContext.typeConstructor(rigidTypeMarker))) {
            return true;
        }
        typeCheckerState.initialize();
        ArrayDeque<RigidTypeMarker> supertypesDeque = typeCheckerState.getSupertypesDeque();
        Intrinsics.checkNotNull(supertypesDeque);
        Set<RigidTypeMarker> supertypesSet = typeCheckerState.getSupertypesSet();
        Intrinsics.checkNotNull(supertypesSet);
        supertypesDeque.push(rigidTypeMarker);
        while (!supertypesDeque.isEmpty()) {
            RigidTypeMarker rigidTypeMarkerPop = supertypesDeque.pop();
            Intrinsics.checkNotNull(rigidTypeMarkerPop);
            if (supertypesSet.add(rigidTypeMarkerPop)) {
                if (typeSystemContext.isClassType(rigidTypeMarkerPop)) {
                    lowerIfFlexible = TypeCheckerState.SupertypesPolicy.None.INSTANCE;
                } else {
                    lowerIfFlexible = TypeCheckerState.SupertypesPolicy.LowerIfFlexible.INSTANCE;
                }
                if (Intrinsics.areEqual(lowerIfFlexible, TypeCheckerState.SupertypesPolicy.None.INSTANCE)) {
                    lowerIfFlexible = null;
                }
                if (lowerIfFlexible == null) {
                    continue;
                } else {
                    TypeSystemContext typeSystemContext2 = typeCheckerState.getTypeSystemContext();
                    Iterator<KotlinTypeMarker> it = typeSystemContext2.supertypes(typeSystemContext2.typeConstructor(rigidTypeMarkerPop)).iterator();
                    while (it.hasNext()) {
                        RigidTypeMarker rigidTypeMarkerMo3827transformType = lowerIfFlexible.mo3827transformType(typeCheckerState, it.next());
                        if (typeSystemContext.isNothingConstructor(typeSystemContext.typeConstructor(rigidTypeMarkerMo3827transformType))) {
                            typeCheckerState.clear();
                            return true;
                        }
                        supertypesDeque.add(rigidTypeMarkerMo3827transformType);
                    }
                }
            }
        }
        typeCheckerState.clear();
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:106:0x00ee A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:44:0x00c2  */
    /* JADX WARN: Code duplicated, block: B:47:0x00d9  */
    /* JADX WARN: Code duplicated, block: B:50:0x00ed  */
    private final boolean isSubtypeOfForSingleClassifierType(final TypeCheckerState typeCheckerState, RigidTypeMarker rigidTypeMarker, final RigidTypeMarker rigidTypeMarker2) {
        ArrayList arrayList;
        final ArrayList linkedHashSet;
        RigidTypeMarker rigidTypeMarkerAsRigidType;
        boolean z;
        KotlinTypeMarker type;
        final TypeSystemContext typeSystemContext = typeCheckerState.getTypeSystemContext();
        if (RUN_SLOW_ASSERTIONS) {
            if (!typeSystemContext.isSingleClassifierType(rigidTypeMarker) && !typeSystemContext.isIntersection(typeSystemContext.typeConstructor(rigidTypeMarker))) {
                typeCheckerState.isAllowedTypeVariable(rigidTypeMarker);
            }
            if (!typeSystemContext.isSingleClassifierType(rigidTypeMarker2)) {
                typeCheckerState.isAllowedTypeVariable(rigidTypeMarker2);
            }
        }
        boolean z2 = false;
        if (!AbstractNullabilityChecker.INSTANCE.isPossibleSubtype(typeCheckerState, rigidTypeMarker, rigidTypeMarker2)) {
            return false;
        }
        AbstractTypeChecker abstractTypeChecker = INSTANCE;
        Boolean boolCheckSubtypeForIntegerLiteralType = abstractTypeChecker.checkSubtypeForIntegerLiteralType(typeCheckerState, rigidTypeMarker, rigidTypeMarker2);
        if (boolCheckSubtypeForIntegerLiteralType != null) {
            boolean zBooleanValue = boolCheckSubtypeForIntegerLiteralType.booleanValue();
            TypeCheckerState.addSubtypeConstraint$default(typeCheckerState, rigidTypeMarker, rigidTypeMarker2, false, 4, null);
            return zBooleanValue;
        }
        TypeConstructorMarker typeConstructorMarkerTypeConstructor = typeSystemContext.typeConstructor(rigidTypeMarker2);
        boolean z3 = true;
        if ((typeSystemContext.areEqualTypeConstructors(typeSystemContext.typeConstructor(rigidTypeMarker), typeConstructorMarkerTypeConstructor) && typeSystemContext.parametersCount(typeConstructorMarkerTypeConstructor) == 0) || typeSystemContext.isAnyConstructor(typeSystemContext.typeConstructor(rigidTypeMarker2))) {
            return true;
        }
        List<RigidTypeMarker> listFindCorrespondingSupertypes = abstractTypeChecker.findCorrespondingSupertypes(typeCheckerState, rigidTypeMarker, typeConstructorMarkerTypeConstructor);
        int i = 10;
        if (listFindCorrespondingSupertypes.size() > 1) {
            TypeSystemContext typeSystemContext2 = typeCheckerState.getTypeSystemContext();
            TypeSystemInferenceExtensionContext typeSystemInferenceExtensionContext = typeSystemContext2 instanceof TypeSystemInferenceExtensionContext ? (TypeSystemInferenceExtensionContext) typeSystemContext2 : null;
            if (typeSystemInferenceExtensionContext == null || !typeSystemInferenceExtensionContext.isK2()) {
                List<RigidTypeMarker> list = listFindCorrespondingSupertypes;
                arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                for (RigidTypeMarker rigidTypeMarker3 : list) {
                    rigidTypeMarkerAsRigidType = typeSystemContext.asRigidType(typeCheckerState.prepareType(rigidTypeMarker3));
                    if (rigidTypeMarkerAsRigidType == null) {
                        rigidTypeMarker3 = rigidTypeMarkerAsRigidType;
                    }
                    arrayList.add(rigidTypeMarker3);
                }
                linkedHashSet = arrayList;
            } else {
                linkedHashSet = new LinkedHashSet();
                for (RigidTypeMarker rigidTypeMarker4 : listFindCorrespondingSupertypes) {
                    RigidTypeMarker rigidTypeMarkerAsRigidType2 = typeSystemContext.asRigidType(typeCheckerState.prepareType(rigidTypeMarker4));
                    if (rigidTypeMarkerAsRigidType2 != null) {
                        rigidTypeMarker4 = rigidTypeMarkerAsRigidType2;
                    }
                    linkedHashSet.add(rigidTypeMarker4);
                }
            }
        } else {
            List<RigidTypeMarker> list2 = listFindCorrespondingSupertypes;
            arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            while (r4.hasNext()) {
                rigidTypeMarkerAsRigidType = typeSystemContext.asRigidType(typeCheckerState.prepareType(rigidTypeMarker3));
                if (rigidTypeMarkerAsRigidType == null) {
                    rigidTypeMarker3 = rigidTypeMarkerAsRigidType;
                }
                arrayList.add(rigidTypeMarker3);
            }
            linkedHashSet = arrayList;
        }
        int size = linkedHashSet.size();
        if (size == 0) {
            return INSTANCE.hasNothingSupertype(typeCheckerState, rigidTypeMarker);
        }
        if (size == 1) {
            return INSTANCE.isSubtypeForSameConstructor(typeCheckerState, typeSystemContext.asArgumentList((RigidTypeMarker) CollectionsKt.first(linkedHashSet)), rigidTypeMarker2);
        }
        ArgumentList argumentList = new ArgumentList(typeSystemContext.parametersCount(typeConstructorMarkerTypeConstructor));
        int iParametersCount = typeSystemContext.parametersCount(typeConstructorMarkerTypeConstructor);
        int i2 = 0;
        boolean z4 = false;
        while (i2 < iParametersCount) {
            z4 = (z4 || typeSystemContext.getVariance(typeSystemContext.getParameter(typeConstructorMarkerTypeConstructor, i2)) != TypeVariance.OUT) ? z3 : z2;
            if (z4) {
                z = z3;
            } else {
                Collection<RigidTypeMarker> collection = linkedHashSet;
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, i));
                for (RigidTypeMarker rigidTypeMarker5 : collection) {
                    boolean z5 = z3;
                    TypeArgumentMarker argumentOrNull = typeSystemContext.getArgumentOrNull(rigidTypeMarker5, i2);
                    if (argumentOrNull != null) {
                        if (typeSystemContext.getVariance(argumentOrNull) != TypeVariance.INV) {
                            argumentOrNull = null;
                        }
                        if (argumentOrNull != null && (type = typeSystemContext.getType(argumentOrNull)) != null) {
                            arrayList2.add(type);
                            z3 = z5;
                        }
                    }
                    throw new IllegalStateException(("Incorrect type: " + rigidTypeMarker5 + ", subType: " + rigidTypeMarker + ", superType: " + rigidTypeMarker2).toString());
                }
                z = z3;
                argumentList.add(typeSystemContext.asTypeArgument(typeSystemContext.intersectTypes(arrayList2)));
            }
            i2++;
            z3 = z;
            z2 = false;
            i = 10;
        }
        return (z4 || !INSTANCE.isSubtypeForSameConstructor(typeCheckerState, argumentList, rigidTypeMarker2)) ? typeCheckerState.runForkingPoint(new Function1(linkedHashSet, typeCheckerState, typeSystemContext, rigidTypeMarker2) { // from class: kotlin.reflect.jvm.internal.impl.types.AbstractTypeChecker$$Lambda$0
            private final Collection arg$0;
            private final TypeCheckerState arg$1;
            private final TypeSystemContext arg$2;
            private final RigidTypeMarker arg$3;

            {
                this.arg$0 = linkedHashSet;
                this.arg$1 = typeCheckerState;
                this.arg$2 = typeSystemContext;
                this.arg$3 = rigidTypeMarker2;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return AbstractTypeChecker.isSubtypeOfForSingleClassifierType$lambda$21$lambda$20(this.arg$0, this.arg$1, this.arg$2, this.arg$3, (TypeCheckerState.ForkPointContext) obj);
            }
        }) : z3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit isSubtypeOfForSingleClassifierType$lambda$21$lambda$20(Collection collection, final TypeCheckerState typeCheckerState, final TypeSystemContext typeSystemContext, final RigidTypeMarker rigidTypeMarker, TypeCheckerState.ForkPointContext runForkingPoint) {
        Intrinsics.checkNotNullParameter(runForkingPoint, "$this$runForkingPoint");
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            final RigidTypeMarker rigidTypeMarker2 = (RigidTypeMarker) it.next();
            runForkingPoint.fork(new Function0(typeCheckerState, typeSystemContext, rigidTypeMarker2, rigidTypeMarker) { // from class: kotlin.reflect.jvm.internal.impl.types.AbstractTypeChecker$$Lambda$1
                private final TypeCheckerState arg$0;
                private final TypeSystemContext arg$1;
                private final RigidTypeMarker arg$2;
                private final RigidTypeMarker arg$3;

                {
                    this.arg$0 = typeCheckerState;
                    this.arg$1 = typeSystemContext;
                    this.arg$2 = rigidTypeMarker2;
                    this.arg$3 = rigidTypeMarker;
                }

                @Override // kotlin.jvm.functions.Function0
                public Object invoke() {
                    return Boolean.valueOf(AbstractTypeChecker.isSubtypeOfForSingleClassifierType$lambda$21$lambda$20$lambda$19(this.arg$0, this.arg$1, this.arg$2, this.arg$3));
                }
            });
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isSubtypeOfForSingleClassifierType$lambda$21$lambda$20$lambda$19(TypeCheckerState typeCheckerState, TypeSystemContext typeSystemContext, RigidTypeMarker rigidTypeMarker, RigidTypeMarker rigidTypeMarker2) {
        return INSTANCE.isSubtypeForSameConstructor(typeCheckerState, typeSystemContext.asArgumentList(rigidTypeMarker), rigidTypeMarker2);
    }

    private final boolean isTypeVariableAgainstStarProjectionForSelfType(TypeSystemContext typeSystemContext, KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker kotlinTypeMarker2, TypeConstructorMarker typeConstructorMarker) {
        TypeParameterMarker typeParameter;
        RigidTypeMarker rigidTypeMarkerAsRigidType = typeSystemContext.asRigidType(kotlinTypeMarker);
        if (rigidTypeMarkerAsRigidType instanceof CapturedTypeMarker) {
            CapturedTypeMarker capturedTypeMarker = (CapturedTypeMarker) rigidTypeMarkerAsRigidType;
            if (typeSystemContext.isOldCapturedType(capturedTypeMarker) || !typeSystemContext.isStarProjection(typeSystemContext.projection(typeSystemContext.typeConstructor(capturedTypeMarker))) || typeSystemContext.captureStatus(capturedTypeMarker) != CaptureStatus.FOR_SUBTYPING) {
                return false;
            }
            TypeConstructorMarker typeConstructorMarkerTypeConstructor = typeSystemContext.typeConstructor(kotlinTypeMarker2);
            TypeVariableTypeConstructorMarker typeVariableTypeConstructorMarker = typeConstructorMarkerTypeConstructor instanceof TypeVariableTypeConstructorMarker ? (TypeVariableTypeConstructorMarker) typeConstructorMarkerTypeConstructor : null;
            if (typeVariableTypeConstructorMarker != null && (typeParameter = typeSystemContext.getTypeParameter(typeVariableTypeConstructorMarker)) != null && typeSystemContext.hasRecursiveBounds(typeParameter, typeConstructorMarker)) {
                return true;
            }
        }
        return false;
    }

    public final boolean isSubtypeForSameConstructor(TypeCheckerState typeCheckerState, TypeArgumentListMarker capturedSubArguments, RigidTypeMarker superType) {
        boolean zEqualTypes;
        TypeCheckerState typeCheckerState2 = typeCheckerState;
        Intrinsics.checkNotNullParameter(typeCheckerState2, "<this>");
        Intrinsics.checkNotNullParameter(capturedSubArguments, "capturedSubArguments");
        Intrinsics.checkNotNullParameter(superType, "superType");
        TypeSystemContext typeSystemContext = typeCheckerState2.getTypeSystemContext();
        TypeConstructorMarker typeConstructorMarkerTypeConstructor = typeSystemContext.typeConstructor(superType);
        int size = typeSystemContext.size(capturedSubArguments);
        int iParametersCount = typeSystemContext.parametersCount(typeConstructorMarkerTypeConstructor);
        if (size == iParametersCount) {
            RigidTypeMarker rigidTypeMarker = superType;
            if (size == typeSystemContext.argumentsCount(rigidTypeMarker)) {
                for (int i = 0; i < iParametersCount; i++) {
                    TypeArgumentMarker argument = typeSystemContext.getArgument(rigidTypeMarker, i);
                    KotlinTypeMarker type = typeSystemContext.getType(argument);
                    if (type != null) {
                        TypeArgumentMarker typeArgumentMarker = typeSystemContext.get(capturedSubArguments, i);
                        typeSystemContext.getVariance(typeArgumentMarker);
                        TypeVariance typeVariance = TypeVariance.INV;
                        KotlinTypeMarker type2 = typeSystemContext.getType(typeArgumentMarker);
                        Intrinsics.checkNotNull(type2);
                        AbstractTypeChecker abstractTypeChecker = INSTANCE;
                        TypeVariance typeVarianceEffectiveVariance = abstractTypeChecker.effectiveVariance(typeSystemContext.getVariance(typeSystemContext.getParameter(typeConstructorMarkerTypeConstructor, i)), typeSystemContext.getVariance(argument));
                        if (typeVarianceEffectiveVariance == null) {
                            return typeCheckerState2.isErrorTypeEqualsToAnything();
                        }
                        if (typeVarianceEffectiveVariance != TypeVariance.INV || (!abstractTypeChecker.isTypeVariableAgainstStarProjectionForSelfType(typeSystemContext, type2, type, typeConstructorMarkerTypeConstructor) && !abstractTypeChecker.isTypeVariableAgainstStarProjectionForSelfType(typeSystemContext, type, type2, typeConstructorMarkerTypeConstructor))) {
                            if (typeCheckerState2.argumentsDepth <= 100) {
                                typeCheckerState2.argumentsDepth++;
                                int i2 = WhenMappings.$EnumSwitchMapping$0[typeVarianceEffectiveVariance.ordinal()];
                                if (i2 == 1) {
                                    zEqualTypes = abstractTypeChecker.equalTypes(typeCheckerState2, type2, type);
                                } else if (i2 == 2) {
                                    typeCheckerState2 = typeCheckerState;
                                    zEqualTypes = isSubtypeOf$default(abstractTypeChecker, typeCheckerState2, type2, type, false, 8, null);
                                } else {
                                    if (i2 != 3) {
                                        throw new NoWhenBranchMatchedException();
                                    }
                                    zEqualTypes = isSubtypeOf$default(abstractTypeChecker, typeCheckerState2, type, type2, false, 8, null);
                                    typeCheckerState2 = typeCheckerState;
                                }
                                typeCheckerState2.argumentsDepth--;
                                if (!zEqualTypes) {
                                    return false;
                                }
                            } else {
                                throw new IllegalStateException(("Arguments depth is too high. Some related argument: " + type2).toString());
                            }
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    private final boolean isCommonDenotableType(TypeSystemContext typeSystemContext, KotlinTypeMarker kotlinTypeMarker) {
        return (!typeSystemContext.isDenotable(typeSystemContext.typeConstructor(kotlinTypeMarker)) || typeSystemContext.isDynamic(kotlinTypeMarker) || typeSystemContext.isDefinitelyNotNullType(kotlinTypeMarker) || typeSystemContext.isNotNullTypeParameter(kotlinTypeMarker) || typeSystemContext.isFlexibleWithDifferentTypeConstructors(kotlinTypeMarker)) ? false : true;
    }

    public final TypeVariance effectiveVariance(TypeVariance declared, TypeVariance useSite) {
        Intrinsics.checkNotNullParameter(declared, "declared");
        Intrinsics.checkNotNullParameter(useSite, "useSite");
        if (declared == TypeVariance.INV) {
            return useSite;
        }
        if (useSite == TypeVariance.INV || declared == useSite) {
            return declared;
        }
        return null;
    }

    private final boolean isStubTypeSubtypeOfAnother(TypeSystemContext typeSystemContext, RigidTypeMarker rigidTypeMarker, RigidTypeMarker rigidTypeMarker2) {
        if (typeSystemContext.typeConstructor(rigidTypeMarker) != typeSystemContext.typeConstructor(rigidTypeMarker2)) {
            return false;
        }
        if (typeSystemContext.isDefinitelyNotNullType(rigidTypeMarker) || !typeSystemContext.isDefinitelyNotNullType(rigidTypeMarker2)) {
            return !typeSystemContext.isMarkedNullable(rigidTypeMarker) || typeSystemContext.isMarkedNullable(rigidTypeMarker2);
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:80:0x0129  */
    /* JADX WARN: Code duplicated, block: B:82:0x0135  */
    private final Boolean checkSubtypeForSpecialCases(TypeCheckerState typeCheckerState, RigidTypeMarker rigidTypeMarker, RigidTypeMarker rigidTypeMarker2) {
        TypeCheckerState typeCheckerState2;
        TypeParameterMarker typeParameterForArgumentInBaseIfItEqualToTarget;
        TypeSystemContext typeSystemContext = typeCheckerState.getTypeSystemContext();
        RigidTypeMarker rigidTypeMarker3 = rigidTypeMarker;
        boolean z = false;
        if (!typeSystemContext.isError(rigidTypeMarker3)) {
            RigidTypeMarker rigidTypeMarker4 = rigidTypeMarker2;
            if (!typeSystemContext.isError(rigidTypeMarker4)) {
                if (typeSystemContext.isStubTypeForBuilderInference(rigidTypeMarker) && typeSystemContext.isStubTypeForBuilderInference(rigidTypeMarker2)) {
                    return Boolean.valueOf(INSTANCE.isStubTypeSubtypeOfAnother(typeSystemContext, rigidTypeMarker, rigidTypeMarker2) || typeCheckerState.isStubTypeEqualsToAnything());
                }
                if (typeSystemContext.isStubType(rigidTypeMarker) || typeSystemContext.isStubType(rigidTypeMarker2)) {
                    return Boolean.valueOf(typeCheckerState.isStubTypeEqualsToAnything());
                }
                CapturedTypeMarker capturedTypeMarkerAsCapturedTypeUnwrappingDnn = typeSystemContext.asCapturedTypeUnwrappingDnn(rigidTypeMarker2);
                KotlinTypeMarker kotlinTypeMarkerLowerType = capturedTypeMarkerAsCapturedTypeUnwrappingDnn != null ? typeSystemContext.lowerType(capturedTypeMarkerAsCapturedTypeUnwrappingDnn) : null;
                if (capturedTypeMarkerAsCapturedTypeUnwrappingDnn == null || kotlinTypeMarkerLowerType == null) {
                    typeCheckerState2 = typeCheckerState;
                } else {
                    if (typeSystemContext.isMarkedNullable(rigidTypeMarker4)) {
                        kotlinTypeMarkerLowerType = typeSystemContext.withNullability(kotlinTypeMarkerLowerType, true);
                    } else if (typeSystemContext.isDefinitelyNotNullType(rigidTypeMarker2)) {
                        kotlinTypeMarkerLowerType = typeSystemContext.makeDefinitelyNotNullOrNotNull(kotlinTypeMarkerLowerType);
                    }
                    KotlinTypeMarker kotlinTypeMarker = kotlinTypeMarkerLowerType;
                    int i = WhenMappings.$EnumSwitchMapping$1[typeCheckerState.getLowerCapturedTypePolicy(rigidTypeMarker, capturedTypeMarkerAsCapturedTypeUnwrappingDnn).ordinal()];
                    if (i == 1) {
                        return Boolean.valueOf(isSubtypeOf$default(INSTANCE, typeCheckerState, rigidTypeMarker3, kotlinTypeMarker, false, 8, null));
                    }
                    if (i != 2) {
                        if (i != 3) {
                            throw new NoWhenBranchMatchedException();
                        }
                        typeCheckerState2 = typeCheckerState;
                    } else {
                        typeCheckerState2 = typeCheckerState;
                        if (isSubtypeOf$default(INSTANCE, typeCheckerState2, rigidTypeMarker3, kotlinTypeMarker, false, 8, null)) {
                            return true;
                        }
                    }
                }
                TypeConstructorMarker typeConstructorMarkerTypeConstructor = typeSystemContext.typeConstructor(rigidTypeMarker2);
                if (typeSystemContext.isIntersection(typeConstructorMarkerTypeConstructor)) {
                    typeSystemContext.isMarkedNullable(rigidTypeMarker4);
                    Collection<KotlinTypeMarker> collectionSupertypes = typeSystemContext.supertypes(typeConstructorMarkerTypeConstructor);
                    if ((collectionSupertypes instanceof Collection) && collectionSupertypes.isEmpty()) {
                        z = true;
                    } else {
                        Iterator<T> it = collectionSupertypes.iterator();
                        while (it.hasNext()) {
                            if (!isSubtypeOf$default(INSTANCE, typeCheckerState2, rigidTypeMarker3, (KotlinTypeMarker) it.next(), false, 8, null)) {
                            }
                        }
                        z = true;
                    }
                    return Boolean.valueOf(z);
                }
                TypeConstructorMarker typeConstructorMarkerTypeConstructor2 = typeSystemContext.typeConstructor(rigidTypeMarker);
                if (!(rigidTypeMarker instanceof CapturedTypeMarker)) {
                    if (typeSystemContext.isIntersection(typeConstructorMarkerTypeConstructor2)) {
                        Collection<KotlinTypeMarker> collectionSupertypes2 = typeSystemContext.supertypes(typeConstructorMarkerTypeConstructor2);
                        if ((collectionSupertypes2 instanceof Collection) && collectionSupertypes2.isEmpty()) {
                            typeParameterForArgumentInBaseIfItEqualToTarget = INSTANCE.getTypeParameterForArgumentInBaseIfItEqualToTarget(typeCheckerState2.getTypeSystemContext(), rigidTypeMarker4, rigidTypeMarker3);
                            if (typeParameterForArgumentInBaseIfItEqualToTarget == null) {
                            }
                        } else {
                            Iterator<T> it2 = collectionSupertypes2.iterator();
                            while (it2.hasNext()) {
                                if (!(((KotlinTypeMarker) it2.next()) instanceof CapturedTypeMarker)) {
                                }
                            }
                            typeParameterForArgumentInBaseIfItEqualToTarget = INSTANCE.getTypeParameterForArgumentInBaseIfItEqualToTarget(typeCheckerState2.getTypeSystemContext(), rigidTypeMarker4, rigidTypeMarker3);
                            if (typeParameterForArgumentInBaseIfItEqualToTarget == null) {
                            }
                        }
                    }
                } else {
                    typeParameterForArgumentInBaseIfItEqualToTarget = INSTANCE.getTypeParameterForArgumentInBaseIfItEqualToTarget(typeCheckerState2.getTypeSystemContext(), rigidTypeMarker4, rigidTypeMarker3);
                    if (typeParameterForArgumentInBaseIfItEqualToTarget == null && typeSystemContext.hasRecursiveBounds(typeParameterForArgumentInBaseIfItEqualToTarget, typeSystemContext.typeConstructor(rigidTypeMarker2))) {
                        return true;
                    }
                }
                return null;
            }
        }
        if (typeCheckerState.isErrorTypeEqualsToAnything()) {
            return true;
        }
        if (!typeSystemContext.isMarkedNullable(rigidTypeMarker3) || typeSystemContext.isMarkedNullable(rigidTypeMarker2)) {
            return Boolean.valueOf(AbstractStrictEqualityTypeChecker.INSTANCE.strictEqualTypes(typeSystemContext, typeSystemContext.withNullability(rigidTypeMarker, false), typeSystemContext.withNullability(rigidTypeMarker2, false)));
        }
        return false;
    }

    private final TypeParameterMarker getTypeParameterForArgumentInBaseIfItEqualToTarget(TypeSystemContext typeSystemContext, KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker kotlinTypeMarker2) {
        KotlinTypeMarker type;
        int iArgumentsCount = typeSystemContext.argumentsCount(kotlinTypeMarker);
        int i = 0;
        while (true) {
            if (i >= iArgumentsCount) {
                return null;
            }
            TypeArgumentMarker argument = typeSystemContext.getArgument(kotlinTypeMarker, i);
            TypeArgumentMarker typeArgumentMarker = typeSystemContext.isStarProjection(argument) ? null : argument;
            if (typeArgumentMarker != null && (type = typeSystemContext.getType(typeArgumentMarker)) != null) {
                boolean z = typeSystemContext.isCapturedType(typeSystemContext.lowerBoundIfFlexible(type)) && typeSystemContext.isCapturedType(typeSystemContext.lowerBoundIfFlexible(kotlinTypeMarker2));
                if (Intrinsics.areEqual(type, kotlinTypeMarker2) || (z && Intrinsics.areEqual(typeSystemContext.typeConstructor(type), typeSystemContext.typeConstructor(kotlinTypeMarker2)))) {
                    return typeSystemContext.getParameter(typeSystemContext.typeConstructor(kotlinTypeMarker), i);
                }
                TypeParameterMarker typeParameterForArgumentInBaseIfItEqualToTarget = getTypeParameterForArgumentInBaseIfItEqualToTarget(typeSystemContext, type, kotlinTypeMarker2);
                if (typeParameterForArgumentInBaseIfItEqualToTarget != null) {
                    return typeParameterForArgumentInBaseIfItEqualToTarget;
                }
            }
            i++;
        }
    }

    private final List<RigidTypeMarker> collectAllSupertypesWithGivenTypeConstructor(TypeCheckerState typeCheckerState, RigidTypeMarker rigidTypeMarker, TypeConstructorMarker typeConstructorMarker) {
        TypeCheckerState.SupertypesPolicy.LowerIfFlexible lowerIfFlexibleSubstitutionSupertypePolicy;
        TypeSystemContext typeSystemContext = typeCheckerState.getTypeSystemContext();
        List<SimpleTypeMarker> listFastCorrespondingSupertypes = typeSystemContext.fastCorrespondingSupertypes(rigidTypeMarker, typeConstructorMarker);
        if (listFastCorrespondingSupertypes != null) {
            return listFastCorrespondingSupertypes;
        }
        if (!typeSystemContext.isClassTypeConstructor(typeConstructorMarker) && typeSystemContext.isClassType(rigidTypeMarker)) {
            return CollectionsKt.emptyList();
        }
        if (typeSystemContext.isCommonFinalClassConstructor(typeConstructorMarker)) {
            if (typeSystemContext.areEqualTypeConstructors(typeSystemContext.typeConstructor(rigidTypeMarker), typeConstructorMarker)) {
                RigidTypeMarker rigidTypeMarkerCaptureFromArguments = typeSystemContext.captureFromArguments(rigidTypeMarker, CaptureStatus.FOR_SUBTYPING);
                if (rigidTypeMarkerCaptureFromArguments != null) {
                    rigidTypeMarker = rigidTypeMarkerCaptureFromArguments;
                }
                return CollectionsKt.listOf(rigidTypeMarker);
            }
            return CollectionsKt.emptyList();
        }
        SmartList smartList = new SmartList();
        typeCheckerState.initialize();
        ArrayDeque<RigidTypeMarker> supertypesDeque = typeCheckerState.getSupertypesDeque();
        Intrinsics.checkNotNull(supertypesDeque);
        Set<RigidTypeMarker> supertypesSet = typeCheckerState.getSupertypesSet();
        Intrinsics.checkNotNull(supertypesSet);
        supertypesDeque.push(rigidTypeMarker);
        while (!supertypesDeque.isEmpty()) {
            RigidTypeMarker rigidTypeMarkerPop = supertypesDeque.pop();
            Intrinsics.checkNotNull(rigidTypeMarkerPop);
            if (supertypesSet.add(rigidTypeMarkerPop)) {
                RigidTypeMarker rigidTypeMarkerCaptureFromArguments2 = typeSystemContext.captureFromArguments(rigidTypeMarkerPop, CaptureStatus.FOR_SUBTYPING);
                if (rigidTypeMarkerCaptureFromArguments2 == null) {
                    rigidTypeMarkerCaptureFromArguments2 = rigidTypeMarkerPop;
                }
                if (typeSystemContext.areEqualTypeConstructors(typeSystemContext.typeConstructor(rigidTypeMarkerCaptureFromArguments2), typeConstructorMarker)) {
                    smartList.add(rigidTypeMarkerCaptureFromArguments2);
                    lowerIfFlexibleSubstitutionSupertypePolicy = TypeCheckerState.SupertypesPolicy.None.INSTANCE;
                } else if (typeSystemContext.argumentsCount(rigidTypeMarkerCaptureFromArguments2) == 0) {
                    lowerIfFlexibleSubstitutionSupertypePolicy = TypeCheckerState.SupertypesPolicy.LowerIfFlexible.INSTANCE;
                } else {
                    lowerIfFlexibleSubstitutionSupertypePolicy = typeCheckerState.getTypeSystemContext().substitutionSupertypePolicy(rigidTypeMarkerCaptureFromArguments2);
                }
                if (Intrinsics.areEqual(lowerIfFlexibleSubstitutionSupertypePolicy, TypeCheckerState.SupertypesPolicy.None.INSTANCE)) {
                    lowerIfFlexibleSubstitutionSupertypePolicy = null;
                }
                if (lowerIfFlexibleSubstitutionSupertypePolicy != null) {
                    TypeSystemContext typeSystemContext2 = typeCheckerState.getTypeSystemContext();
                    Iterator<KotlinTypeMarker> it = typeSystemContext2.supertypes(typeSystemContext2.typeConstructor(rigidTypeMarkerPop)).iterator();
                    while (it.hasNext()) {
                        supertypesDeque.add(lowerIfFlexibleSubstitutionSupertypePolicy.mo3827transformType(typeCheckerState, it.next()));
                    }
                }
            }
        }
        typeCheckerState.clear();
        return smartList;
    }

    private final List<RigidTypeMarker> collectAndFilter(TypeCheckerState typeCheckerState, RigidTypeMarker rigidTypeMarker, TypeConstructorMarker typeConstructorMarker) {
        return selectOnlyPureKotlinSupertypes(typeCheckerState, collectAllSupertypesWithGivenTypeConstructor(typeCheckerState, rigidTypeMarker, typeConstructorMarker));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final List<RigidTypeMarker> selectOnlyPureKotlinSupertypes(TypeCheckerState typeCheckerState, List<? extends RigidTypeMarker> list) {
        TypeSystemContext typeSystemContext = typeCheckerState.getTypeSystemContext();
        if (list.size() >= 2) {
            ArrayList arrayList = new ArrayList();
            for (Object obj : list) {
                TypeArgumentListMarker typeArgumentListMarkerAsArgumentList = typeSystemContext.asArgumentList((RigidTypeMarker) obj);
                int size = typeSystemContext.size(typeArgumentListMarkerAsArgumentList);
                int i = 0;
                while (true) {
                    if (i < size) {
                        KotlinTypeMarker type = typeSystemContext.getType(typeSystemContext.get(typeArgumentListMarkerAsArgumentList, i));
                        if ((type != null ? typeSystemContext.asFlexibleType(type) : null) != null) {
                            break;
                        }
                        i++;
                    } else {
                        arrayList.add(obj);
                        break;
                    }
                }
            }
            ArrayList arrayList2 = arrayList;
            if (!arrayList2.isEmpty()) {
                return arrayList2;
            }
        }
        return list;
    }

    public final List<RigidTypeMarker> findCorrespondingSupertypes(TypeCheckerState state, RigidTypeMarker subType, TypeConstructorMarker superConstructor) {
        TypeCheckerState.SupertypesPolicy.LowerIfFlexible lowerIfFlexible;
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(subType, "subType");
        Intrinsics.checkNotNullParameter(superConstructor, "superConstructor");
        TypeSystemContext typeSystemContext = state.getTypeSystemContext();
        if (typeSystemContext.isClassType(subType)) {
            return INSTANCE.collectAndFilter(state, subType, superConstructor);
        }
        if (!typeSystemContext.isClassTypeConstructor(superConstructor) && !typeSystemContext.isIntegerLiteralTypeConstructor(superConstructor)) {
            return INSTANCE.collectAllSupertypesWithGivenTypeConstructor(state, subType, superConstructor);
        }
        SmartList<RigidTypeMarker> smartList = new SmartList();
        state.initialize();
        ArrayDeque<RigidTypeMarker> supertypesDeque = state.getSupertypesDeque();
        Intrinsics.checkNotNull(supertypesDeque);
        Set<RigidTypeMarker> supertypesSet = state.getSupertypesSet();
        Intrinsics.checkNotNull(supertypesSet);
        supertypesDeque.push(subType);
        while (!supertypesDeque.isEmpty()) {
            RigidTypeMarker rigidTypeMarkerPop = supertypesDeque.pop();
            Intrinsics.checkNotNull(rigidTypeMarkerPop);
            if (supertypesSet.add(rigidTypeMarkerPop)) {
                if (typeSystemContext.isClassType(rigidTypeMarkerPop)) {
                    smartList.add(rigidTypeMarkerPop);
                    lowerIfFlexible = TypeCheckerState.SupertypesPolicy.None.INSTANCE;
                } else {
                    lowerIfFlexible = TypeCheckerState.SupertypesPolicy.LowerIfFlexible.INSTANCE;
                }
                if (Intrinsics.areEqual(lowerIfFlexible, TypeCheckerState.SupertypesPolicy.None.INSTANCE)) {
                    lowerIfFlexible = null;
                }
                if (lowerIfFlexible != null) {
                    TypeSystemContext typeSystemContext2 = state.getTypeSystemContext();
                    Iterator<KotlinTypeMarker> it = typeSystemContext2.supertypes(typeSystemContext2.typeConstructor(rigidTypeMarkerPop)).iterator();
                    while (it.hasNext()) {
                        supertypesDeque.add(lowerIfFlexible.mo3827transformType(state, it.next()));
                    }
                }
            }
        }
        state.clear();
        ArrayList arrayList = new ArrayList();
        for (RigidTypeMarker rigidTypeMarker : smartList) {
            AbstractTypeChecker abstractTypeChecker = INSTANCE;
            Intrinsics.checkNotNull(rigidTypeMarker);
            CollectionsKt.addAll(arrayList, abstractTypeChecker.collectAndFilter(state, rigidTypeMarker, superConstructor));
        }
        return arrayList;
    }
}
