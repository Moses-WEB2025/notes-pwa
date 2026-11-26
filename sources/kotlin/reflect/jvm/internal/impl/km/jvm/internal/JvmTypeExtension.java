package kotlin.reflect.jvm.internal.impl.km.jvm.internal;

import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.jvm.internal.impl.km.KmAnnotation;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.KmExtensionType;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.KmTypeExtension;

/* JADX INFO: compiled from: JvmExtensionNodes.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class JvmTypeExtension implements KmTypeExtension {
    public static final Companion Companion = new Companion(null);
    public static final KmExtensionType TYPE = new KmExtensionType(Reflection.getOrCreateKotlinClass(JvmTypeExtension.class));
    private final List<KmAnnotation> annotations = new ArrayList();
    private boolean isRaw;

    public final boolean isRaw() {
        return this.isRaw;
    }

    public final void setRaw(boolean z) {
        this.isRaw = z;
    }

    public final List<KmAnnotation> getAnnotations() {
        return this.annotations;
    }

    @Override // kotlin.reflect.jvm.internal.impl.km.internal.extensions.KmExtension
    public KmExtensionType getType() {
        return TYPE;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), obj != null ? obj.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.metadata.jvm.internal.JvmTypeExtension");
        JvmTypeExtension jvmTypeExtension = (JvmTypeExtension) obj;
        return this.isRaw == jvmTypeExtension.isRaw && Intrinsics.areEqual(this.annotations, jvmTypeExtension.annotations);
    }

    public int hashCode() {
        return (Boolean.hashCode(this.isRaw) * 31) + this.annotations.hashCode();
    }

    /* JADX INFO: compiled from: JvmExtensionNodes.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
