package kotlin.reflect.jvm.internal.impl.km.jvm;

import ch.qos.logback.core.CoreConstants;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: JvmMemberSignature.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class JvmFieldSignature extends JvmMemberSignature {
    private final String descriptor;
    private final String name;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof JvmFieldSignature)) {
            return false;
        }
        JvmFieldSignature jvmFieldSignature = (JvmFieldSignature) obj;
        return Intrinsics.areEqual(this.name, jvmFieldSignature.name) && Intrinsics.areEqual(this.descriptor, jvmFieldSignature.descriptor);
    }

    public int hashCode() {
        return (this.name.hashCode() * 31) + this.descriptor.hashCode();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JvmFieldSignature(String name, String descriptor) {
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
        return getName() + CoreConstants.COLON_CHAR + getDescriptor();
    }
}
