package kotlin.reflect.jvm.internal.impl.descriptors;

import androidx.exifinterface.media.ExifInterface;
import ch.qos.logback.core.CoreConstants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ClassDescriptorBase;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.EmptyPackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.TypeParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNotNull;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.ClassTypeConstructorImpl;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;

/* JADX INFO: compiled from: NotFoundClasses.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class NotFoundClasses {
    private final MemoizedFunctionToNotNull<ClassRequest, ClassDescriptor> classes;
    private final ModuleDescriptor module;
    private final MemoizedFunctionToNotNull<FqName, PackageFragmentDescriptor> packageFragments;
    private final StorageManager storageManager;

    public NotFoundClasses(StorageManager storageManager, ModuleDescriptor module) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(module, "module");
        this.storageManager = storageManager;
        this.module = module;
        this.packageFragments = storageManager.createMemoizedFunction(new Function1(this) { // from class: kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses$$Lambda$0
            private final NotFoundClasses arg$0;

            {
                this.arg$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return NotFoundClasses.packageFragments$lambda$0(this.arg$0, (FqName) obj);
            }
        });
        this.classes = storageManager.createMemoizedFunction(new Function1(this) { // from class: kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses$$Lambda$1
            private final NotFoundClasses arg$0;

            {
                this.arg$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return NotFoundClasses.classes$lambda$2(this.arg$0, (NotFoundClasses.ClassRequest) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: NotFoundClasses.kt */
    static final class ClassRequest {
        private final ClassId classId;
        private final List<Integer> typeParametersCount;

        public final ClassId component1() {
            return this.classId;
        }

        public final List<Integer> component2() {
            return this.typeParametersCount;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ClassRequest)) {
                return false;
            }
            ClassRequest classRequest = (ClassRequest) obj;
            return Intrinsics.areEqual(this.classId, classRequest.classId) && Intrinsics.areEqual(this.typeParametersCount, classRequest.typeParametersCount);
        }

        public int hashCode() {
            return (this.classId.hashCode() * 31) + this.typeParametersCount.hashCode();
        }

        public String toString() {
            return "ClassRequest(classId=" + this.classId + ", typeParametersCount=" + this.typeParametersCount + CoreConstants.RIGHT_PARENTHESIS_CHAR;
        }

        public ClassRequest(ClassId classId, List<Integer> typeParametersCount) {
            Intrinsics.checkNotNullParameter(classId, "classId");
            Intrinsics.checkNotNullParameter(typeParametersCount, "typeParametersCount");
            this.classId = classId;
            this.typeParametersCount = typeParametersCount;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final PackageFragmentDescriptor packageFragments$lambda$0(NotFoundClasses notFoundClasses, FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return new EmptyPackageFragmentDescriptor(notFoundClasses.module, fqName);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ClassDescriptor classes$lambda$2(NotFoundClasses notFoundClasses, ClassRequest classRequest) {
        PackageFragmentDescriptor packageFragmentDescriptorInvoke;
        ClassDescriptor classDescriptor;
        Intrinsics.checkNotNullParameter(classRequest, "<destruct>");
        ClassId classIdComponent1 = classRequest.component1();
        List<Integer> listComponent2 = classRequest.component2();
        if (classIdComponent1.isLocal()) {
            throw new UnsupportedOperationException("Unresolved local class: " + classIdComponent1);
        }
        ClassId outerClassId = classIdComponent1.getOuterClassId();
        if (outerClassId != null && (classDescriptor = notFoundClasses.getClass(outerClassId, CollectionsKt.drop(listComponent2, 1))) != null) {
            packageFragmentDescriptorInvoke = classDescriptor;
        } else {
            packageFragmentDescriptorInvoke = notFoundClasses.packageFragments.invoke(classIdComponent1.getPackageFqName());
        }
        boolean zIsNestedClass = classIdComponent1.isNestedClass();
        StorageManager storageManager = notFoundClasses.storageManager;
        DeclarationDescriptor declarationDescriptor = packageFragmentDescriptorInvoke;
        Name shortClassName = classIdComponent1.getShortClassName();
        Integer num = (Integer) CollectionsKt.firstOrNull((List) listComponent2);
        return new MockClassDescriptor(storageManager, declarationDescriptor, shortClassName, zIsNestedClass, num != null ? num.intValue() : 0);
    }

    /* JADX INFO: compiled from: NotFoundClasses.kt */
    public static final class MockClassDescriptor extends ClassDescriptorBase {
        private final List<TypeParameterDescriptor> declaredTypeParameters;
        private final boolean isInner;
        private final ClassTypeConstructorImpl typeConstructor;

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
        /* JADX INFO: renamed from: getCompanionObjectDescriptor */
        public ClassDescriptor mo3812getCompanionObjectDescriptor() {
            return null;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
        /* JADX INFO: renamed from: getUnsubstitutedPrimaryConstructor */
        public ClassConstructorDescriptor mo3813getUnsubstitutedPrimaryConstructor() {
            return null;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
        public ValueClassRepresentation<SimpleType> getValueClassRepresentation() {
            return null;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor
        public boolean isActual() {
            return false;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
        public boolean isCompanionObject() {
            return false;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
        public boolean isData() {
            return false;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor
        public boolean isExpect() {
            return false;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.ClassDescriptorBase, kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor
        public boolean isExternal() {
            return false;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
        public boolean isFun() {
            return false;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
        public boolean isInline() {
            return false;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
        public boolean isValue() {
            return false;
        }

        /* JADX WARN: Illegal instructions before constructor call */
        public MockClassDescriptor(StorageManager storageManager, DeclarationDescriptor container, Name name, boolean z, int i) {
            Intrinsics.checkNotNullParameter(storageManager, "storageManager");
            Intrinsics.checkNotNullParameter(container, "container");
            Intrinsics.checkNotNullParameter(name, "name");
            StorageManager storageManager2 = storageManager;
            super(storageManager2, container, name, SourceElement.NO_SOURCE, false);
            this.isInner = z;
            IntRange intRangeUntil = RangesKt.until(0, i);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(intRangeUntil, 10));
            Iterator<Integer> it = intRangeUntil.iterator();
            while (it.hasNext()) {
                int iNextInt = ((IntIterator) it).nextInt();
                StorageManager storageManager3 = storageManager2;
                storageManager2 = storageManager3;
                arrayList.add(TypeParameterDescriptorImpl.createWithDefaultBound(this, Annotations.Companion.getEMPTY(), false, Variance.INVARIANT, Name.identifier(ExifInterface.GPS_DIRECTION_TRUE + iNextInt), iNextInt, storageManager3));
            }
            this.declaredTypeParameters = arrayList;
            this.typeConstructor = new ClassTypeConstructorImpl(this, TypeParameterUtilsKt.computeConstructorTypeParameters(this), SetsKt.setOf(DescriptorUtilsKt.getModule(this).getBuiltIns().getAnyType()), storageManager2);
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
        public ClassKind getKind() {
            return ClassKind.CLASS;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor, kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor
        public Modality getModality() {
            return Modality.FINAL;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor, kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor, kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorWithVisibility
        public DescriptorVisibility getVisibility() {
            DescriptorVisibility PUBLIC = DescriptorVisibilities.PUBLIC;
            Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
            return PUBLIC;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor
        public ClassTypeConstructorImpl getTypeConstructor() {
            return this.typeConstructor;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor, kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters
        public List<TypeParameterDescriptor> getDeclaredTypeParameters() {
            return this.declaredTypeParameters;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters
        public boolean isInner() {
            return this.isInner;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated
        public Annotations getAnnotations() {
            return Annotations.Companion.getEMPTY();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.ModuleAwareClassDescriptor
        public MemberScope.Empty getUnsubstitutedMemberScope(KotlinTypeRefiner kotlinTypeRefiner) {
            Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
            return MemberScope.Empty.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
        public MemberScope.Empty getStaticScope() {
            return MemberScope.Empty.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
        public Collection<ClassConstructorDescriptor> getConstructors() {
            return SetsKt.emptySet();
        }

        public String toString() {
            return "class " + getName() + " (not found)";
        }
    }

    public final ClassDescriptor getClass(ClassId classId, List<Integer> typeParametersCount) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(typeParametersCount, "typeParametersCount");
        return this.classes.invoke(new ClassRequest(classId, typeParametersCount));
    }
}
