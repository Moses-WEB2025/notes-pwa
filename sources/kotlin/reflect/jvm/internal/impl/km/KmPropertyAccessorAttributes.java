package kotlin.reflect.jvm.internal.impl.km;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: Nodes.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class KmPropertyAccessorAttributes {
    private final List<KmAnnotation> annotations;
    private int flags;

    public KmPropertyAccessorAttributes(int i) {
        this.flags = i;
        this.annotations = new ArrayList(0);
    }

    public final int getFlags$kotlin_metadata() {
        return this.flags;
    }

    public final void setFlags$kotlin_metadata(int i) {
        this.flags = i;
    }

    public KmPropertyAccessorAttributes() {
        this(0);
    }

    public final List<KmAnnotation> getAnnotations() {
        return this.annotations;
    }
}
