package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.resolve.InlineClassesUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import kotlin.reflect.jvm.internal.impl.util.OperatorNameConventions;

/* JADX INFO: compiled from: descriptorUtil.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class DescriptorUtilKt {
    public static final ClassDescriptor resolveClassByFqName(ModuleDescriptor moduleDescriptor, FqName fqName, LookupLocation lookupLocation) {
        MemberScope unsubstitutedInnerClassesScope;
        Intrinsics.checkNotNullParameter(moduleDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(lookupLocation, "lookupLocation");
        if (fqName.isRoot()) {
            return null;
        }
        ClassifierDescriptor contributedClassifier = moduleDescriptor.getPackage(fqName.parent()).getMemberScope().mo3825getContributedClassifier(fqName.shortName(), lookupLocation);
        ClassDescriptor classDescriptor = contributedClassifier instanceof ClassDescriptor ? (ClassDescriptor) contributedClassifier : null;
        if (classDescriptor != null) {
            return classDescriptor;
        }
        ClassDescriptor classDescriptorResolveClassByFqName = resolveClassByFqName(moduleDescriptor, fqName.parent(), lookupLocation);
        ClassifierDescriptor contributedClassifier2 = (classDescriptorResolveClassByFqName == null || (unsubstitutedInnerClassesScope = classDescriptorResolveClassByFqName.getUnsubstitutedInnerClassesScope()) == null) ? null : unsubstitutedInnerClassesScope.mo3825getContributedClassifier(fqName.shortName(), lookupLocation);
        if (contributedClassifier2 instanceof ClassDescriptor) {
            return (ClassDescriptor) contributedClassifier2;
        }
        return null;
    }

    public static final boolean isTopLevelInPackage(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return declarationDescriptor.getContainingDeclaration() instanceof PackageFragmentDescriptor;
    }

    public static final ClassifierDescriptor getTopLevelContainingClassifier(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        DeclarationDescriptor containingDeclaration = declarationDescriptor.getContainingDeclaration();
        if (containingDeclaration != null && !(declarationDescriptor instanceof PackageFragmentDescriptor)) {
            if (!isTopLevelInPackage(containingDeclaration)) {
                return getTopLevelContainingClassifier(containingDeclaration);
            }
            if (containingDeclaration instanceof ClassifierDescriptor) {
                return (ClassifierDescriptor) containingDeclaration;
            }
        }
        return null;
    }

    public static final boolean isTypedEqualsInValueClass(FunctionDescriptor functionDescriptor) {
        SimpleType defaultType;
        KotlinType kotlinTypeReplaceArgumentsWithStarProjections;
        KotlinType returnType;
        Intrinsics.checkNotNullParameter(functionDescriptor, "<this>");
        DeclarationDescriptor containingDeclaration = functionDescriptor.getContainingDeclaration();
        ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
        if (classDescriptor != null) {
            ClassDescriptor classDescriptor2 = InlineClassesUtilsKt.isValueClass(classDescriptor) ? classDescriptor : null;
            if (classDescriptor2 != null && (defaultType = classDescriptor2.getDefaultType()) != null && (kotlinTypeReplaceArgumentsWithStarProjections = TypeUtilsKt.replaceArgumentsWithStarProjections(defaultType)) != null && (returnType = functionDescriptor.getReturnType()) != null && Intrinsics.areEqual(functionDescriptor.getName(), OperatorNameConventions.EQUALS) && ((TypeUtilsKt.isBoolean(returnType) || TypeUtilsKt.isNothing(returnType)) && functionDescriptor.getValueParameters().size() == 1)) {
                KotlinType type = functionDescriptor.getValueParameters().get(0).getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                if (Intrinsics.areEqual(TypeUtilsKt.replaceArgumentsWithStarProjections(type), kotlinTypeReplaceArgumentsWithStarProjections) && functionDescriptor.getContextReceiverParameters().isEmpty() && functionDescriptor.getExtensionReceiverParameter() == null) {
                    return true;
                }
            }
        }
        return false;
    }
}
