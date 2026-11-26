package kotlin.reflect.jvm.internal.impl.km.jvm;

import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: JvmMemberSignature.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class JvmMethodSignature extends JvmMemberSignature {
    private final String descriptor;
    private final String name;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof JvmMethodSignature)) {
            return false;
        }
        JvmMethodSignature jvmMethodSignature = (JvmMethodSignature) obj;
        return Intrinsics.areEqual(this.name, jvmMethodSignature.name) && Intrinsics.areEqual(this.descriptor, jvmMethodSignature.descriptor);
    }

    public int hashCode() {
        return (this.name.hashCode() * 31) + this.descriptor.hashCode();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JvmMethodSignature(String name, String descriptor) {
        super(null);
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        this.name = name;
        this.descriptor = descriptor;
    }

    @Override // kotlin.reflect.jvm.internal.impl.km.jvm.JvmMemberSignature
    public String getDescriptor() {
        return this.descriptor;
    }

    @Override // kotlin.reflect.jvm.internal.impl.km.jvm.JvmMemberSignature
    public String getName() {
        return this.name;
    }

    @Override // kotlin.reflect.jvm.internal.impl.km.jvm.JvmMemberSignature
    public String toString() {
        return getName() + getDescriptor();
    }
}
