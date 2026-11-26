package kotlin.reflect.jvm.internal.impl.resolve;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeCheckerState;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.checker.ClassicTypeCheckerStateKt;
import kotlin.reflect.jvm.internal.impl.types.checker.ClassicTypeSystemContext;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypePreparator;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.model.ArgumentList;
import kotlin.reflect.jvm.internal.impl.types.model.CaptureStatus;
import kotlin.reflect.jvm.internal.impl.types.model.CapturedTypeConstructorMarker;
import kotlin.reflect.jvm.internal.impl.types.model.CapturedTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.DefinitelyNotNullTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.DynamicTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.FlexibleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.RigidTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentListMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeConstructorMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeParameterMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeVariableTypeConstructorMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeVariance;

/* JADX INFO: compiled from: OverridingUtilTypeSystemContext.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class OverridingUtilTypeSystemContext implements ClassicTypeSystemContext {
    private final Function2<KotlinType, KotlinType, Boolean> customSubtype;
    private final KotlinTypeChecker.TypeConstructorEquality equalityAxioms;
    private final KotlinTypePreparator kotlinTypePreparator;
    private final KotlinTypeRefiner kotlinTypeRefiner;
    private final Map<TypeConstructor, TypeConstructor> matchingTypeConstructors;

    public List<SimpleTypeMarker> default$fastCorrespondingSupertypes(RigidTypeMarker rigidTypeMarker, TypeConstructorMarker constructor) {
        Intrinsics.checkNotNullParameter(rigidTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public OverridingUtilTypeSystemContext(Map<TypeConstructor, ? extends TypeConstructor> map, KotlinTypeChecker.TypeConstructorEquality equalityAxioms, KotlinTypeRefiner kotlinTypeRefiner, KotlinTypePreparator kotlinTypePreparator, Function2<? super KotlinType, ? super KotlinType, Boolean> function2) {
        Intrinsics.checkNotNullParameter(equalityAxioms, "equalityAxioms");
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        Intrinsics.checkNotNullParameter(kotlinTypePreparator, "kotlinTypePreparator");
        this.matchingTypeConstructors = map;
        this.equalityAxioms = equalityAxioms;
        this.kotlinTypeRefiner = kotlinTypeRefiner;
        this.kotlinTypePreparator = kotlinTypePreparator;
        this.customSubtype = function2;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public int argumentsCount(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.argumentsCount(this, kotlinTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public TypeArgumentListMarker asArgumentList(RigidTypeMarker rigidTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.asArgumentList(this, rigidTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.checker.ClassicTypeSystemContext, kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public CapturedTypeMarker asCapturedType(SimpleTypeMarker simpleTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.asCapturedType(this, simpleTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public CapturedTypeMarker asCapturedTypeUnwrappingDnn(RigidTypeMarker rigidTypeMarker) {
        return default$asCapturedTypeUnwrappingDnn(rigidTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public DefinitelyNotNullTypeMarker asDefinitelyNotNullType(RigidTypeMarker rigidTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.asDefinitelyNotNullType(this, rigidTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public DynamicTypeMarker asDynamicType(FlexibleTypeMarker flexibleTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.asDynamicType(this, flexibleTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public FlexibleTypeMarker asFlexibleType(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.asFlexibleType(this, kotlinTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public SimpleTypeMarker asRigidType(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.asRigidType(this, kotlinTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public TypeArgumentMarker asTypeArgument(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.asTypeArgument(this, kotlinTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public SimpleType captureFromArguments(RigidTypeMarker rigidTypeMarker, CaptureStatus captureStatus) {
        return ClassicTypeSystemContext.DefaultImpls.captureFromArguments(this, rigidTypeMarker, captureStatus);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public CaptureStatus captureStatus(CapturedTypeMarker capturedTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.captureStatus(this, capturedTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.checker.ClassicTypeSystemContext
    public KotlinTypeMarker createFlexibleType(RigidTypeMarker rigidTypeMarker, RigidTypeMarker rigidTypeMarker2) {
        return ClassicTypeSystemContext.DefaultImpls.createFlexibleType(this, rigidTypeMarker, rigidTypeMarker2);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public List<SimpleTypeMarker> fastCorrespondingSupertypes(RigidTypeMarker rigidTypeMarker, TypeConstructorMarker typeConstructorMarker) {
        return default$fastCorrespondingSupertypes(rigidTypeMarker, typeConstructorMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public TypeArgumentMarker get(TypeArgumentListMarker typeArgumentListMarker, int i) {
        return default$get(typeArgumentListMarker, i);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public TypeArgumentMarker getArgument(KotlinTypeMarker kotlinTypeMarker, int i) {
        return ClassicTypeSystemContext.DefaultImpls.getArgument(this, kotlinTypeMarker, i);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public TypeArgumentMarker getArgumentOrNull(RigidTypeMarker rigidTypeMarker, int i) {
        return default$getArgumentOrNull(rigidTypeMarker, i);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public List<TypeArgumentMarker> getArguments(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.getArguments(this, kotlinTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext
    public FqNameUnsafe getClassFqNameUnsafe(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.getClassFqNameUnsafe(this, typeConstructorMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public TypeParameterMarker getParameter(TypeConstructorMarker typeConstructorMarker, int i) {
        return ClassicTypeSystemContext.DefaultImpls.getParameter(this, typeConstructorMarker, i);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public List<TypeParameterMarker> getParameters(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.getParameters(this, typeConstructorMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext
    public PrimitiveType getPrimitiveArrayType(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.getPrimitiveArrayType(this, typeConstructorMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext
    public PrimitiveType getPrimitiveType(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.getPrimitiveType(this, typeConstructorMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext
    public KotlinTypeMarker getRepresentativeUpperBound(TypeParameterMarker typeParameterMarker) {
        return ClassicTypeSystemContext.DefaultImpls.getRepresentativeUpperBound(this, typeParameterMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public KotlinTypeMarker getType(TypeArgumentMarker typeArgumentMarker) {
        return ClassicTypeSystemContext.DefaultImpls.getType(this, typeArgumentMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public TypeParameterMarker getTypeParameter(TypeVariableTypeConstructorMarker typeVariableTypeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.getTypeParameter(this, typeVariableTypeConstructorMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public TypeParameterMarker getTypeParameterClassifier(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.getTypeParameterClassifier(this, typeConstructorMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext
    public KotlinTypeMarker getUnsubstitutedUnderlyingType(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.getUnsubstitutedUnderlyingType(this, kotlinTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public List<KotlinTypeMarker> getUpperBounds(TypeParameterMarker typeParameterMarker) {
        return ClassicTypeSystemContext.DefaultImpls.getUpperBounds(this, typeParameterMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public TypeVariance getVariance(TypeArgumentMarker typeArgumentMarker) {
        return ClassicTypeSystemContext.DefaultImpls.getVariance(this, typeArgumentMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public TypeVariance getVariance(TypeParameterMarker typeParameterMarker) {
        return ClassicTypeSystemContext.DefaultImpls.getVariance(this, typeParameterMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext
    public boolean hasAnnotation(KotlinTypeMarker kotlinTypeMarker, FqName fqName) {
        return ClassicTypeSystemContext.DefaultImpls.hasAnnotation(this, kotlinTypeMarker, fqName);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean hasFlexibleNullability(KotlinTypeMarker kotlinTypeMarker) {
        return default$hasFlexibleNullability(kotlinTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean hasRecursiveBounds(TypeParameterMarker typeParameterMarker, TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.hasRecursiveBounds(this, typeParameterMarker, typeConstructorMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemOptimizationContext
    public boolean identicalArguments(RigidTypeMarker rigidTypeMarker, RigidTypeMarker rigidTypeMarker2) {
        return ClassicTypeSystemContext.DefaultImpls.identicalArguments(this, rigidTypeMarker, rigidTypeMarker2);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public KotlinTypeMarker intersectTypes(Collection<? extends KotlinTypeMarker> collection) {
        return ClassicTypeSystemContext.DefaultImpls.intersectTypes(this, collection);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isAnyConstructor(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isAnyConstructor(this, typeConstructorMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isCapturedType(KotlinTypeMarker kotlinTypeMarker) {
        return default$isCapturedType(kotlinTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isClassType(RigidTypeMarker rigidTypeMarker) {
        return default$isClassType(rigidTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isClassTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isClassTypeConstructor(this, typeConstructorMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isCommonFinalClassConstructor(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isCommonFinalClassConstructor(this, typeConstructorMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isDefinitelyNotNullType(KotlinTypeMarker kotlinTypeMarker) {
        return default$isDefinitelyNotNullType(kotlinTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isDefinitelyNotNullType(RigidTypeMarker rigidTypeMarker) {
        return default$isDefinitelyNotNullType(rigidTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isDenotable(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isDenotable(this, typeConstructorMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isDynamic(KotlinTypeMarker kotlinTypeMarker) {
        return default$isDynamic(kotlinTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isError(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isError(this, kotlinTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isFlexible(KotlinTypeMarker kotlinTypeMarker) {
        return default$isFlexible(kotlinTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isFlexibleWithDifferentTypeConstructors(KotlinTypeMarker kotlinTypeMarker) {
        return default$isFlexibleWithDifferentTypeConstructors(kotlinTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext
    public boolean isInlineClass(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isInlineClass(this, typeConstructorMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isIntegerLiteralType(RigidTypeMarker rigidTypeMarker) {
        return default$isIntegerLiteralType(rigidTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isIntegerLiteralTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isIntegerLiteralTypeConstructor(this, typeConstructorMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isIntersection(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isIntersection(this, typeConstructorMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemInferenceExtensionContext
    public boolean isK2() {
        return ClassicTypeSystemContext.DefaultImpls.isK2(this);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isMarkedNullable(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isMarkedNullable(this, kotlinTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isNotNullTypeParameter(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isNotNullTypeParameter(this, kotlinTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isNothing(KotlinTypeMarker kotlinTypeMarker) {
        return default$isNothing(kotlinTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isNothingConstructor(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isNothingConstructor(this, typeConstructorMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isNullableType(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isNullableType(this, kotlinTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isOldCapturedType(CapturedTypeMarker capturedTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isOldCapturedType(this, capturedTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isPrimitiveType(SimpleTypeMarker simpleTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isPrimitiveType(this, simpleTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isProjectionNotNull(CapturedTypeMarker capturedTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isProjectionNotNull(this, capturedTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isRawType(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isRawType(this, kotlinTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.checker.ClassicTypeSystemContext, kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isSingleClassifierType(RigidTypeMarker rigidTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isSingleClassifierType(this, rigidTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.checker.ClassicTypeSystemContext, kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isStarProjection(TypeArgumentMarker typeArgumentMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isStarProjection(this, typeArgumentMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isStubType(RigidTypeMarker rigidTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isStubType(this, rigidTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isStubTypeForBuilderInference(RigidTypeMarker rigidTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isStubTypeForBuilderInference(this, rigidTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean isTypeVariableType(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isTypeVariableType(this, kotlinTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext
    public boolean isUnderKotlinPackage(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isUnderKotlinPackage(this, typeConstructorMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public SimpleTypeMarker lowerBound(FlexibleTypeMarker flexibleTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.lowerBound(this, flexibleTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public RigidTypeMarker lowerBoundIfFlexible(KotlinTypeMarker kotlinTypeMarker) {
        return default$lowerBoundIfFlexible(kotlinTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public KotlinTypeMarker lowerType(CapturedTypeMarker capturedTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.lowerType(this, capturedTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public KotlinTypeMarker makeDefinitelyNotNullOrNotNull(KotlinTypeMarker kotlinTypeMarker) {
        return default$makeDefinitelyNotNullOrNotNull(kotlinTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public KotlinTypeMarker makeDefinitelyNotNullOrNotNull(KotlinTypeMarker kotlinTypeMarker, boolean z) {
        return ClassicTypeSystemContext.DefaultImpls.makeDefinitelyNotNullOrNotNull(this, kotlinTypeMarker, z);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext
    public KotlinTypeMarker makeNullable(KotlinTypeMarker kotlinTypeMarker) {
        return default$makeNullable(kotlinTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public SimpleTypeMarker original(DefinitelyNotNullTypeMarker definitelyNotNullTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.original(this, definitelyNotNullTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public SimpleTypeMarker originalIfDefinitelyNotNullable(RigidTypeMarker rigidTypeMarker) {
        return default$originalIfDefinitelyNotNullable(rigidTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public int parametersCount(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.parametersCount(this, typeConstructorMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public Collection<KotlinTypeMarker> possibleIntegerTypes(RigidTypeMarker rigidTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.possibleIntegerTypes(this, rigidTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public TypeArgumentMarker projection(CapturedTypeConstructorMarker capturedTypeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.projection(this, capturedTypeConstructorMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public int size(TypeArgumentListMarker typeArgumentListMarker) {
        return default$size(typeArgumentListMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public TypeCheckerState.SupertypesPolicy substitutionSupertypePolicy(RigidTypeMarker rigidTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.substitutionSupertypePolicy(this, rigidTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public Collection<KotlinTypeMarker> supertypes(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.supertypes(this, typeConstructorMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public CapturedTypeConstructorMarker typeConstructor(CapturedTypeMarker capturedTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.typeConstructor((ClassicTypeSystemContext) this, capturedTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public TypeConstructorMarker typeConstructor(KotlinTypeMarker kotlinTypeMarker) {
        return default$typeConstructor(kotlinTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.checker.ClassicTypeSystemContext, kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public TypeConstructorMarker typeConstructor(RigidTypeMarker rigidTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.typeConstructor(this, rigidTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public SimpleTypeMarker upperBound(FlexibleTypeMarker flexibleTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.upperBound(this, flexibleTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public RigidTypeMarker upperBoundIfFlexible(KotlinTypeMarker kotlinTypeMarker) {
        return default$upperBoundIfFlexible(kotlinTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public KotlinTypeMarker withNullability(KotlinTypeMarker kotlinTypeMarker, boolean z) {
        return ClassicTypeSystemContext.DefaultImpls.withNullability(this, kotlinTypeMarker, z);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public SimpleTypeMarker withNullability(RigidTypeMarker rigidTypeMarker, boolean z) {
        return ClassicTypeSystemContext.DefaultImpls.withNullability((ClassicTypeSystemContext) this, rigidTypeMarker, z);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext
    public boolean areEqualTypeConstructors(TypeConstructorMarker c1, TypeConstructorMarker c2) {
        Intrinsics.checkNotNullParameter(c1, "c1");
        Intrinsics.checkNotNullParameter(c2, "c2");
        if (!(c1 instanceof TypeConstructor)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (c2 instanceof TypeConstructor) {
            return ClassicTypeSystemContext.DefaultImpls.areEqualTypeConstructors(this, c1, c2) || areEqualTypeConstructorsByAxioms((TypeConstructor) c1, (TypeConstructor) c2);
        }
        throw new IllegalArgumentException("Failed requirement.".toString());
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.model.TypeCheckerProviderContext
    public TypeCheckerState newTypeCheckerState(boolean z, boolean z2, boolean z3) {
        if (this.customSubtype == null) {
            KotlinTypeRefiner kotlinTypeRefiner = this.kotlinTypeRefiner;
            return ClassicTypeCheckerStateKt.createClassicTypeCheckerState(z, z2, this, this.kotlinTypePreparator, kotlinTypeRefiner);
        }
        return new TypeCheckerState(z, z2, z3, this, this.kotlinTypePreparator, this.kotlinTypeRefiner) { // from class: kotlin.reflect.jvm.internal.impl.resolve.OverridingUtilTypeSystemContext.newTypeCheckerState.1
            final /* synthetic */ OverridingUtilTypeSystemContext this$0;

            {
                this.this$0 = this;
                OverridingUtilTypeSystemContext overridingUtilTypeSystemContext = this;
                KotlinTypePreparator kotlinTypePreparator = kotlinTypePreparator;
                KotlinTypeRefiner kotlinTypeRefiner2 = kotlinTypeRefiner;
            }

            @Override // kotlin.reflect.jvm.internal.impl.types.TypeCheckerState
            public boolean customIsSubtypeOf(KotlinTypeMarker subType, KotlinTypeMarker superType) {
                Intrinsics.checkNotNullParameter(subType, "subType");
                Intrinsics.checkNotNullParameter(superType, "superType");
                if (!(subType instanceof KotlinType)) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                if (superType instanceof KotlinType) {
                    return ((Boolean) this.this$0.customSubtype.invoke(subType, superType)).booleanValue();
                }
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
        };
    }

    public KotlinTypeMarker default$makeNullable(KotlinTypeMarker kotlinTypeMarker) {
        RigidTypeMarker rigidTypeMarkerWithNullability;
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        RigidTypeMarker rigidTypeMarkerAsRigidType = asRigidType(kotlinTypeMarker);
        return (rigidTypeMarkerAsRigidType == null || (rigidTypeMarkerWithNullability = withNullability(rigidTypeMarkerAsRigidType, true)) == null) ? kotlinTypeMarker : rigidTypeMarkerWithNullability;
    }

    private final boolean areEqualTypeConstructorsByAxioms(TypeConstructor typeConstructor, TypeConstructor typeConstructor2) {
        if (this.equalityAxioms.equals(typeConstructor, typeConstructor2)) {
            return true;
        }
        Map<TypeConstructor, TypeConstructor> map = this.matchingTypeConstructors;
        if (map == null) {
            return false;
        }
        TypeConstructor typeConstructor3 = map.get(typeConstructor);
        TypeConstructor typeConstructor4 = this.matchingTypeConstructors.get(typeConstructor2);
        return (typeConstructor3 != null && Intrinsics.areEqual(typeConstructor3, typeConstructor2)) || (typeConstructor4 != null && Intrinsics.areEqual(typeConstructor4, typeConstructor));
    }

    public CapturedTypeMarker default$asCapturedTypeUnwrappingDnn(RigidTypeMarker rigidTypeMarker) {
        Intrinsics.checkNotNullParameter(rigidTypeMarker, "<this>");
        return asCapturedType(originalIfDefinitelyNotNullable(rigidTypeMarker));
    }

    public boolean default$isCapturedType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        RigidTypeMarker rigidTypeMarkerAsRigidType = asRigidType(kotlinTypeMarker);
        return (rigidTypeMarkerAsRigidType != null ? asCapturedTypeUnwrappingDnn(rigidTypeMarkerAsRigidType) : null) != null;
    }

    public SimpleTypeMarker default$originalIfDefinitelyNotNullable(RigidTypeMarker rigidTypeMarker) {
        SimpleTypeMarker simpleTypeMarkerOriginal;
        Intrinsics.checkNotNullParameter(rigidTypeMarker, "<this>");
        DefinitelyNotNullTypeMarker definitelyNotNullTypeMarkerAsDefinitelyNotNullType = asDefinitelyNotNullType(rigidTypeMarker);
        return (definitelyNotNullTypeMarkerAsDefinitelyNotNullType == null || (simpleTypeMarkerOriginal = original(definitelyNotNullTypeMarkerAsDefinitelyNotNullType)) == null) ? (SimpleTypeMarker) rigidTypeMarker : simpleTypeMarkerOriginal;
    }

    public KotlinTypeMarker default$makeDefinitelyNotNullOrNotNull(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return makeDefinitelyNotNullOrNotNull(kotlinTypeMarker, false);
    }

    public TypeArgumentMarker default$getArgumentOrNull(RigidTypeMarker rigidTypeMarker, int i) {
        Intrinsics.checkNotNullParameter(rigidTypeMarker, "<this>");
        if (i < 0 || i >= argumentsCount(rigidTypeMarker)) {
            return null;
        }
        return getArgument(rigidTypeMarker, i);
    }

    public RigidTypeMarker default$lowerBoundIfFlexible(KotlinTypeMarker kotlinTypeMarker) {
        RigidTypeMarker rigidTypeMarkerLowerBound;
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        FlexibleTypeMarker flexibleTypeMarkerAsFlexibleType = asFlexibleType(kotlinTypeMarker);
        if (flexibleTypeMarkerAsFlexibleType != null && (rigidTypeMarkerLowerBound = lowerBound(flexibleTypeMarkerAsFlexibleType)) != null) {
            return rigidTypeMarkerLowerBound;
        }
        RigidTypeMarker rigidTypeMarkerAsRigidType = asRigidType(kotlinTypeMarker);
        Intrinsics.checkNotNull(rigidTypeMarkerAsRigidType);
        return rigidTypeMarkerAsRigidType;
    }

    public RigidTypeMarker default$upperBoundIfFlexible(KotlinTypeMarker kotlinTypeMarker) {
        RigidTypeMarker rigidTypeMarkerUpperBound;
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        FlexibleTypeMarker flexibleTypeMarkerAsFlexibleType = asFlexibleType(kotlinTypeMarker);
        if (flexibleTypeMarkerAsFlexibleType != null && (rigidTypeMarkerUpperBound = upperBound(flexibleTypeMarkerAsFlexibleType)) != null) {
            return rigidTypeMarkerUpperBound;
        }
        RigidTypeMarker rigidTypeMarkerAsRigidType = asRigidType(kotlinTypeMarker);
        Intrinsics.checkNotNull(rigidTypeMarkerAsRigidType);
        return rigidTypeMarkerAsRigidType;
    }

    public boolean default$isFlexibleWithDifferentTypeConstructors(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return !Intrinsics.areEqual(typeConstructor(lowerBoundIfFlexible(kotlinTypeMarker)), typeConstructor(upperBoundIfFlexible(kotlinTypeMarker)));
    }

    public boolean default$isFlexible(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return asFlexibleType(kotlinTypeMarker) != null;
    }

    public boolean default$isDynamic(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        FlexibleTypeMarker flexibleTypeMarkerAsFlexibleType = asFlexibleType(kotlinTypeMarker);
        return (flexibleTypeMarkerAsFlexibleType != null ? asDynamicType(flexibleTypeMarkerAsFlexibleType) : null) != null;
    }

    public boolean default$isDefinitelyNotNullType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        RigidTypeMarker rigidTypeMarkerAsRigidType = asRigidType(kotlinTypeMarker);
        return (rigidTypeMarkerAsRigidType != null ? asDefinitelyNotNullType(rigidTypeMarkerAsRigidType) : null) != null;
    }

    public boolean default$isDefinitelyNotNullType(RigidTypeMarker rigidTypeMarker) {
        Intrinsics.checkNotNullParameter(rigidTypeMarker, "<this>");
        return asDefinitelyNotNullType(rigidTypeMarker) != null;
    }

    public boolean default$hasFlexibleNullability(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return isMarkedNullable(lowerBoundIfFlexible(kotlinTypeMarker)) != isMarkedNullable(upperBoundIfFlexible(kotlinTypeMarker));
    }

    public TypeConstructorMarker default$typeConstructor(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        RigidTypeMarker rigidTypeMarkerAsRigidType = asRigidType(kotlinTypeMarker);
        if (rigidTypeMarkerAsRigidType == null) {
            rigidTypeMarkerAsRigidType = lowerBoundIfFlexible(kotlinTypeMarker);
        }
        return typeConstructor(rigidTypeMarkerAsRigidType);
    }

    public boolean default$isNothing(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return isNothingConstructor(typeConstructor(kotlinTypeMarker)) && !isNullableType(kotlinTypeMarker);
    }

    public boolean default$isClassType(RigidTypeMarker rigidTypeMarker) {
        Intrinsics.checkNotNullParameter(rigidTypeMarker, "<this>");
        return isClassTypeConstructor(typeConstructor(rigidTypeMarker));
    }

    public boolean default$isIntegerLiteralType(RigidTypeMarker rigidTypeMarker) {
        Intrinsics.checkNotNullParameter(rigidTypeMarker, "<this>");
        return isIntegerLiteralTypeConstructor(typeConstructor(rigidTypeMarker));
    }

    public TypeArgumentMarker default$get(TypeArgumentListMarker typeArgumentListMarker, int i) {
        Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
        if (typeArgumentListMarker instanceof SimpleTypeMarker) {
            return getArgument((KotlinTypeMarker) typeArgumentListMarker, i);
        }
        if (!(typeArgumentListMarker instanceof ArgumentList)) {
            throw new IllegalStateException(("unknown type argument list type: " + typeArgumentListMarker + ", " + Reflection.getOrCreateKotlinClass(typeArgumentListMarker.getClass())).toString());
        }
        TypeArgumentMarker typeArgumentMarker = ((ArgumentList) typeArgumentListMarker).get(i);
        Intrinsics.checkNotNullExpressionValue(typeArgumentMarker, "get(...)");
        return typeArgumentMarker;
    }

    public int default$size(TypeArgumentListMarker typeArgumentListMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
        if (typeArgumentListMarker instanceof RigidTypeMarker) {
            return argumentsCount((KotlinTypeMarker) typeArgumentListMarker);
        }
        if (typeArgumentListMarker instanceof ArgumentList) {
            return ((ArgumentList) typeArgumentListMarker).size();
        }
        throw new IllegalStateException(("unknown type argument list type: " + typeArgumentListMarker + ", " + Reflection.getOrCreateKotlinClass(typeArgumentListMarker.getClass())).toString());
    }
}
