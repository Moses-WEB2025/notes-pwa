package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;

/* JADX INFO: compiled from: DeserializedClassDescriptor.kt */
/* JADX INFO: loaded from: classes5.dex */
final /* synthetic */ class DeserializedClassDescriptor$memberScopeHolder$1 extends FunctionReferenceImpl implements Function1<KotlinTypeRefiner, DeserializedClassDescriptor.DeserializedClassMemberScope> {
    DeserializedClassDescriptor$memberScopeHolder$1(Object obj) {
        super(1, obj, DeserializedClassDescriptor.DeserializedClassMemberScope.class, ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "<init>(Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedClassDescriptor;Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final DeserializedClassDescriptor.DeserializedClassMemberScope invoke(KotlinTypeRefiner p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return new DeserializedClassDescriptor.DeserializedClassMemberScope((DeserializedClassDescriptor) this.receiver, p0);
    }
}
