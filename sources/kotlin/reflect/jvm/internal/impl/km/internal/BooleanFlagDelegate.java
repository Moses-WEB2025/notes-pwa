package kotlin.reflect.jvm.internal.impl.km.internal;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KMutableProperty1;
import kotlin.reflect.KProperty;

/* JADX INFO: compiled from: FlagDelegatesImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class BooleanFlagDelegate<Node> {
    private final FlagImpl flag;
    private final KMutableProperty1<Node, Integer> flags;
    private final int mask;

    public BooleanFlagDelegate(KMutableProperty1<Node, Integer> flags, FlagImpl flag) {
        Intrinsics.checkNotNullParameter(flags, "flags");
        Intrinsics.checkNotNullParameter(flag, "flag");
        this.flags = flags;
        this.flag = flag;
        if (flag.getBitWidth$kotlin_metadata() != 1 || flag.getValue$kotlin_metadata() != 1) {
            throw new IllegalArgumentException(("BooleanFlagDelegate can work only with boolean flags (bitWidth = 1 and value = 1), but " + flag + " was passed").toString());
        }
        this.mask = 1 << flag.getOffset$kotlin_metadata();
    }

    public final boolean getValue(Node node, KProperty<?> property) {
        Intrinsics.checkNotNullParameter(property, "property");
        return this.flag.invoke(this.flags.get(node).intValue());
    }

    public final void setValue(Node node, KProperty<?> property, boolean z) {
        Intrinsics.checkNotNullParameter(property, "property");
        int iIntValue = this.flags.get(node).intValue();
        this.flags.set(node, Integer.valueOf(z ? iIntValue | this.mask : iIntValue & (~this.mask)));
    }
}
