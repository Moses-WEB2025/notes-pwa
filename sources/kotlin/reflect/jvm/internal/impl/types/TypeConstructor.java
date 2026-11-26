package kotlin.reflect.jvm.internal.impl.types;

import java.util.Collection;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.model.TypeConstructorMarker;

/* JADX INFO: loaded from: classes5.dex */
public interface TypeConstructor extends TypeConstructorMarker {
    KotlinBuiltIns getBuiltIns();

    /* JADX INFO: renamed from: getDeclarationDescriptor */
    ClassifierDescriptor mo3823getDeclarationDescriptor();

    List<TypeParameterDescriptor> getParameters();

    /* JADX INFO: renamed from: getSupertypes */
    Collection<KotlinType> mo3824getSupertypes();

    boolean isDenotable();

    TypeConstructor refine(KotlinTypeRefiner kotlinTypeRefiner);
}
