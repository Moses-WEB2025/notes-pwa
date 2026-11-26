package kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.InlineClassRepresentation;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.MultiFieldValueClassRepresentation;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyAccessorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueClassRepresentation;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefinerKt;
import kotlin.reflect.jvm.internal.impl.types.checker.TypeRefinementSupport;
import kotlin.reflect.jvm.internal.impl.utils.DFS;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: DescriptorUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class DescriptorUtilsKt {
    private static final Name RETENTION_PARAMETER_NAME;

    static {
        Name nameIdentifier = Name.identifier("value");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        RETENTION_PARAMETER_NAME = nameIdentifier;
    }

    public static final FqNameUnsafe getFqNameUnsafe(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        FqNameUnsafe fqName = DescriptorUtils.getFqName(declarationDescriptor);
        Intrinsics.checkNotNullExpressionValue(fqName, "getFqName(...)");
        return fqName;
    }

    public static final FqName getFqNameSafe(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        FqName fqNameSafe = DescriptorUtils.getFqNameSafe(declarationDescriptor);
        Intrinsics.checkNotNullExpressionValue(fqNameSafe, "getFqNameSafe(...)");
        return fqNameSafe;
    }

    public static final ModuleDescriptor getModule(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        ModuleDescriptor containingModule = DescriptorUtils.getContainingModule(declarationDescriptor);
        Intrinsics.checkNotNullExpressionValue(containingModule, "getContainingModule(...)");
        return containingModule;
    }

    public static final ClassDescriptor resolveTopLevelClass(ModuleDescriptor moduleDescriptor, FqName topLevelClassFqName, LookupLocation location) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(topLevelClassFqName, "topLevelClassFqName");
        Intrinsics.checkNotNullParameter(location, "location");
        topLevelClassFqName.isRoot();
        ClassifierDescriptor contributedClassifier = moduleDescriptor.getPackage(topLevelClassFqName.parent()).getMemberScope().mo3825getContributedClassifier(topLevelClassFqName.shortName(), location);
        if (contributedClassifier instanceof ClassDescriptor) {
            return (ClassDescriptor) contributedClassifier;
        }
        return null;
    }

    public static final ClassId getClassId(ClassifierDescriptor classifierDescriptor) {
        DeclarationDescriptor containingDeclaration;
        ClassId classId;
        if (classifierDescriptor != null && (containingDeclaration = classifierDescriptor.getContainingDeclaration()) != null) {
            if (containingDeclaration instanceof PackageFragmentDescriptor) {
                FqName fqName = ((PackageFragmentDescriptor) containingDeclaration).getFqName();
                Name name = classifierDescriptor.getName();
                Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
                return new ClassId(fqName, name);
            }
            if ((containingDeclaration instanceof ClassifierDescriptorWithTypeParameters) && (classId = getClassId((ClassifierDescriptor) containingDeclaration)) != null) {
                Name name2 = classifierDescriptor.getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                return classId.createNestedClassId(name2);
            }
        }
        return null;
    }

    public static final ClassDescriptor getSuperClassNotAny(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "<this>");
        for (KotlinType kotlinType : classDescriptor.getDefaultType().getConstructor().mo3824getSupertypes()) {
            if (!KotlinBuiltIns.isAnyOrNullableAny(kotlinType)) {
                ClassifierDescriptor classifierDescriptorMo3823getDeclarationDescriptor = kotlinType.getConstructor().mo3823getDeclarationDescriptor();
                if (DescriptorUtils.isClassOrEnumClass(classifierDescriptorMo3823getDeclarationDescriptor)) {
                    Intrinsics.checkNotNull(classifierDescriptorMo3823getDeclarationDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                    return (ClassDescriptor) classifierDescriptorMo3823getDeclarationDescriptor;
                }
            }
        }
        return null;
    }

    public static final KotlinBuiltIns getBuiltIns(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return getModule(declarationDescriptor).getBuiltIns();
    }

    public static final boolean declaresOrInheritsDefaultValue(ValueParameterDescriptor valueParameterDescriptor) {
        Intrinsics.checkNotNullParameter(valueParameterDescriptor, "<this>");
        Boolean boolIfAny = DFS.ifAny(CollectionsKt.listOf(valueParameterDescriptor), new DFS.Neighbors() { // from class: kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt$$Lambda$0
            @Override // kotlin.reflect.jvm.internal.impl.utils.DFS.Neighbors
            public Iterable getNeighbors(Object obj) {
                return DescriptorUtilsKt.declaresOrInheritsDefaultValue$lambda$5((ValueParameterDescriptor) obj);
            }
        }, AnonymousClass2.INSTANCE);
        Intrinsics.checkNotNullExpressionValue(boolIfAny, "ifAny(...)");
        return boolIfAny.booleanValue();
    }

    /* JADX INFO: renamed from: kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt$declaresOrInheritsDefaultValue$2, reason: invalid class name */
    /* JADX INFO: compiled from: DescriptorUtils.kt */
    static final /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function1<ValueParameterDescriptor, Boolean> {
        public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

        AnonymousClass2() {
            super(1, ValueParameterDescriptor.class, "declaresDefaultValue", "declaresDefaultValue()Z", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Boolean invoke(ValueParameterDescriptor p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return Boolean.valueOf(p0.declaresDefaultValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Iterable declaresOrInheritsDefaultValue$lambda$5(ValueParameterDescriptor valueParameterDescriptor) {
        Collection<ValueParameterDescriptor> overriddenDescriptors = valueParameterDescriptor.getOverriddenDescriptors();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(overriddenDescriptors, 10));
        Iterator<T> it = overriddenDescriptors.iterator();
        while (it.hasNext()) {
            arrayList.add(((ValueParameterDescriptor) it.next()).getOriginal());
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final DeclarationDescriptor _get_parentsWithSelf_$lambda$8(DeclarationDescriptor it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return it.getContainingDeclaration();
    }

    public static final Sequence<DeclarationDescriptor> getParentsWithSelf(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return SequencesKt.generateSequence(declarationDescriptor, new Function1() { // from class: kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt$$Lambda$1
            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return DescriptorUtilsKt._get_parentsWithSelf_$lambda$8((DeclarationDescriptor) obj);
            }
        });
    }

    public static final Sequence<DeclarationDescriptor> getParents(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return SequencesKt.drop(getParentsWithSelf(declarationDescriptor), 1);
    }

    public static final CallableMemberDescriptor getPropertyIfAccessor(CallableMemberDescriptor callableMemberDescriptor) {
        Intrinsics.checkNotNullParameter(callableMemberDescriptor, "<this>");
        if (!(callableMemberDescriptor instanceof PropertyAccessorDescriptor)) {
            return callableMemberDescriptor;
        }
        PropertyDescriptor correspondingProperty = ((PropertyAccessorDescriptor) callableMemberDescriptor).getCorrespondingProperty();
        Intrinsics.checkNotNullExpressionValue(correspondingProperty, "getCorrespondingProperty(...)");
        return correspondingProperty;
    }

    public static final FqName fqNameOrNull(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        FqNameUnsafe fqNameUnsafe = getFqNameUnsafe(declarationDescriptor);
        if (!fqNameUnsafe.isSafe()) {
            fqNameUnsafe = null;
        }
        if (fqNameUnsafe != null) {
            return fqNameUnsafe.toSafe();
        }
        return null;
    }

    public static /* synthetic */ CallableMemberDescriptor firstOverridden$default(CallableMemberDescriptor callableMemberDescriptor, boolean z, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return firstOverridden(callableMemberDescriptor, z, function1);
    }

    public static final CallableMemberDescriptor firstOverridden(CallableMemberDescriptor callableMemberDescriptor, final boolean z, final Function1<? super CallableMemberDescriptor, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(callableMemberDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        return (CallableMemberDescriptor) DFS.dfs(CollectionsKt.listOf(callableMemberDescriptor), new DFS.Neighbors(z) { // from class: kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt$$Lambda$2
            private final boolean arg$0;

            {
                this.arg$0 = z;
            }

            @Override // kotlin.reflect.jvm.internal.impl.utils.DFS.Neighbors
            public Iterable getNeighbors(Object obj) {
                return DescriptorUtilsKt.firstOverridden$lambda$10(this.arg$0, (CallableMemberDescriptor) obj);
            }
        }, new DFS.AbstractNodeHandler<CallableMemberDescriptor, CallableMemberDescriptor>() { // from class: kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt.firstOverridden.2
            @Override // kotlin.reflect.jvm.internal.impl.utils.DFS.AbstractNodeHandler, kotlin.reflect.jvm.internal.impl.utils.DFS.NodeHandler
            public boolean beforeChildren(CallableMemberDescriptor current) {
                Intrinsics.checkNotNullParameter(current, "current");
                return objectRef.element == null;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.reflect.jvm.internal.impl.utils.DFS.AbstractNodeHandler, kotlin.reflect.jvm.internal.impl.utils.DFS.NodeHandler
            public void afterChildren(CallableMemberDescriptor current) {
                Intrinsics.checkNotNullParameter(current, "current");
                if (objectRef.element == null && predicate.invoke(current).booleanValue()) {
                    objectRef.element = current;
                }
            }

            @Override // kotlin.reflect.jvm.internal.impl.utils.DFS.NodeHandler
            public CallableMemberDescriptor result() {
                return objectRef.element;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Iterable firstOverridden$lambda$10(boolean z, CallableMemberDescriptor callableMemberDescriptor) {
        Collection<? extends CallableMemberDescriptor> overriddenDescriptors;
        if (z) {
            callableMemberDescriptor = callableMemberDescriptor != null ? callableMemberDescriptor.getOriginal() : null;
        }
        return (callableMemberDescriptor == null || (overriddenDescriptors = callableMemberDescriptor.getOverriddenDescriptors()) == null) ? CollectionsKt.emptyList() : overriddenDescriptors;
    }

    public static final Sequence<CallableMemberDescriptor> overriddenTreeAsSequence(CallableMemberDescriptor callableMemberDescriptor, final boolean z) {
        Intrinsics.checkNotNullParameter(callableMemberDescriptor, "<this>");
        if (z) {
            callableMemberDescriptor = callableMemberDescriptor.getOriginal();
        }
        Sequence sequenceSequenceOf = SequencesKt.sequenceOf((Object[]) new CallableMemberDescriptor[]{callableMemberDescriptor});
        Collection<? extends CallableMemberDescriptor> overriddenDescriptors = callableMemberDescriptor.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
        return SequencesKt.plus(sequenceSequenceOf, SequencesKt.flatMap(CollectionsKt.asSequence(overriddenDescriptors), new Function1(z) { // from class: kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt$$Lambda$3
            private final boolean arg$0;

            {
                this.arg$0 = z;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return DescriptorUtilsKt.overriddenTreeAsSequence$lambda$12$lambda$11(this.arg$0, (CallableMemberDescriptor) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Sequence overriddenTreeAsSequence$lambda$12$lambda$11(boolean z, CallableMemberDescriptor callableMemberDescriptor) {
        Intrinsics.checkNotNull(callableMemberDescriptor);
        return overriddenTreeAsSequence(callableMemberDescriptor, z);
    }

    public static final ClassDescriptor getAnnotationClass(AnnotationDescriptor annotationDescriptor) {
        Intrinsics.checkNotNullParameter(annotationDescriptor, "<this>");
        ClassifierDescriptor classifierDescriptorMo3823getDeclarationDescriptor = annotationDescriptor.getType().getConstructor().mo3823getDeclarationDescriptor();
        if (classifierDescriptorMo3823getDeclarationDescriptor instanceof ClassDescriptor) {
            return (ClassDescriptor) classifierDescriptorMo3823getDeclarationDescriptor;
        }
        return null;
    }

    public static final KotlinTypeRefiner getKotlinTypeRefiner(ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "<this>");
        kotlin.reflect.jvm.internal.impl.types.checker.Ref ref = (kotlin.reflect.jvm.internal.impl.types.checker.Ref) moduleDescriptor.getCapability(KotlinTypeRefinerKt.getREFINER_CAPABILITY());
        TypeRefinementSupport typeRefinementSupport = ref != null ? (TypeRefinementSupport) ref.getValue() : null;
        return typeRefinementSupport instanceof TypeRefinementSupport.Enabled ? ((TypeRefinementSupport.Enabled) typeRefinementSupport).getTypeRefiner() : KotlinTypeRefiner.Default.INSTANCE;
    }

    public static final boolean isTypeRefinementEnabled(ModuleDescriptor moduleDescriptor) {
        TypeRefinementSupport typeRefinementSupport;
        Intrinsics.checkNotNullParameter(moduleDescriptor, "<this>");
        kotlin.reflect.jvm.internal.impl.types.checker.Ref ref = (kotlin.reflect.jvm.internal.impl.types.checker.Ref) moduleDescriptor.getCapability(KotlinTypeRefinerKt.getREFINER_CAPABILITY());
        return (ref == null || (typeRefinementSupport = (TypeRefinementSupport) ref.getValue()) == null || !typeRefinementSupport.isEnabled()) ? false : true;
    }

    public static final InlineClassRepresentation<SimpleType> getInlineClassRepresentation(ClassDescriptor classDescriptor) {
        ValueClassRepresentation<SimpleType> valueClassRepresentation = classDescriptor != null ? classDescriptor.getValueClassRepresentation() : null;
        if (valueClassRepresentation instanceof InlineClassRepresentation) {
            return (InlineClassRepresentation) valueClassRepresentation;
        }
        return null;
    }

    public static final MultiFieldValueClassRepresentation<SimpleType> getMultiFieldValueClassRepresentation(ClassDescriptor classDescriptor) {
        ValueClassRepresentation<SimpleType> valueClassRepresentation = classDescriptor != null ? classDescriptor.getValueClassRepresentation() : null;
        if (valueClassRepresentation instanceof MultiFieldValueClassRepresentation) {
            return (MultiFieldValueClassRepresentation) valueClassRepresentation;
        }
        return null;
    }
}
