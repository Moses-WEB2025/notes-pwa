package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import java.util.Map;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaTypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaTypeParameter;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaTypeParameterListOwner;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNullable;
import kotlin.reflect.jvm.internal.impl.utils.CollectionsKt;

/* JADX INFO: compiled from: resolvers.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class LazyJavaTypeParameterResolver implements TypeParameterResolver {
    private final LazyJavaResolverContext c;
    private final DeclarationDescriptor containingDeclaration;
    private final MemoizedFunctionToNullable<JavaTypeParameter, LazyJavaTypeParameterDescriptor> resolve;
    private final Map<JavaTypeParameter, Integer> typeParameters;
    private final int typeParametersIndexOffset;

    public LazyJavaTypeParameterResolver(LazyJavaResolverContext c, DeclarationDescriptor containingDeclaration, JavaTypeParameterListOwner typeParameterOwner, int i) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(typeParameterOwner, "typeParameterOwner");
        this.c = c;
        this.containingDeclaration = containingDeclaration;
        this.typeParametersIndexOffset = i;
        this.typeParameters = CollectionsKt.mapToIndex(typeParameterOwner.getTypeParameters());
        this.resolve = c.getStorageManager().createMemoizedFunctionWithNullableValues(new Function1(this) { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaTypeParameterResolver$$Lambda$0
            private final LazyJavaTypeParameterResolver arg$0;

            {
                this.arg$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return LazyJavaTypeParameterResolver.resolve$lambda$1(this.arg$0, (JavaTypeParameter) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LazyJavaTypeParameterDescriptor resolve$lambda$1(LazyJavaTypeParameterResolver lazyJavaTypeParameterResolver, JavaTypeParameter typeParameter) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        Integer num = lazyJavaTypeParameterResolver.typeParameters.get(typeParameter);
        if (num == null) {
            return null;
        }
        return new LazyJavaTypeParameterDescriptor(ContextKt.copyWithNewDefaultTypeQualifiers(ContextKt.child(lazyJavaTypeParameterResolver.c, lazyJavaTypeParameterResolver), lazyJavaTypeParameterResolver.containingDeclaration.getAnnotations()), typeParameter, lazyJavaTypeParameterResolver.typeParametersIndexOffset + num.intValue(), lazyJavaTypeParameterResolver.containingDeclaration);
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.lazy.TypeParameterResolver
    public TypeParameterDescriptor resolveTypeParameter(JavaTypeParameter javaTypeParameter) {
        Intrinsics.checkNotNullParameter(javaTypeParameter, "javaTypeParameter");
        LazyJavaTypeParameterDescriptor lazyJavaTypeParameterDescriptorInvoke = this.resolve.invoke(javaTypeParameter);
        return lazyJavaTypeParameterDescriptorInvoke != null ? lazyJavaTypeParameterDescriptorInvoke : this.c.getTypeParameterResolver().resolveTypeParameter(javaTypeParameter);
    }
}
