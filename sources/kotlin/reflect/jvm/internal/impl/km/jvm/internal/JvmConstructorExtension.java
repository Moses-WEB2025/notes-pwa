package kotlin.reflect.jvm.internal.impl.km.jvm.internal;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.KmConstructorExtension;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.KmExtensionType;
import kotlin.reflect.jvm.internal.impl.km.jvm.JvmMethodSignature;

/* JADX INFO: compiled from: JvmExtensionNodes.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class JvmConstructorExtension implements KmConstructorExtension {
    public static final Companion Companion = new Companion(null);
    public static final KmExtensionType TYPE = new KmExtensionType(Reflection.getOrCreateKotlinClass(JvmConstructorExtension.class));
    private JvmMethodSignature signature;

    public final JvmMethodSignature getSignature() {
        return this.signature;
    }

    public final void setSignature(JvmMethodSignature jvmMethodSignature) {
        this.signature = jvmMethodSignature;
    }

    @Override // kotlin.reflect.jvm.internal.impl.km.internal.extensions.KmExtension
    public KmExtensionType getType() {
        return TYPE;
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
