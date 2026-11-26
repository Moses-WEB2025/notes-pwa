package kotlin.reflect.jvm.internal.impl.km.jvm;

import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: JvmMemberSignature.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class JvmMemberSignatureKt {
    public static final JvmMethodSignature wrapAsPublic(kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMemberSignature.Method method) {
        Intrinsics.checkNotNullParameter(method, "<this>");
        return new JvmMethodSignature(method.getName(), method.getDesc());
    }

    public static final JvmFieldSignature wrapAsPublic(kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMemberSignature.Field field) {
        Intrinsics.checkNotNullParameter(field, "<this>");
        return new JvmFieldSignature(field.getName(), field.getDesc());
    }
}
