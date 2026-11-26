package kotlin.reflect.jvm.internal.impl.km.jvm;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: JvmMemberSignature.kt */
/* JADX INFO: loaded from: classes5.dex */
public abstract class JvmMemberSignature {
    public /* synthetic */ JvmMemberSignature(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract String getDescriptor();

    public abstract String getName();

    public abstract String toString();

    private JvmMemberSignature() {
    }
}
