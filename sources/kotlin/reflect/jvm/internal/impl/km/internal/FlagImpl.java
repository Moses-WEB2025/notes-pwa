package kotlin.reflect.jvm.internal.impl.km.internal;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.Flags;

/* JADX INFO: compiled from: FlagImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class FlagImpl {
    private final int bitWidth;
    private final int offset;
    private final int value;

    public FlagImpl(int i, int i2, int i3) {
        this.offset = i;
        this.bitWidth = i2;
        this.value = i3;
    }

    public final int getBitWidth$kotlin_metadata() {
        return this.bitWidth;
    }

    public final int getOffset$kotlin_metadata() {
        return this.offset;
    }

    public final int getValue$kotlin_metadata() {
        return this.value;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public FlagImpl(Flags.FlagField<?> field, int i) {
        this(field.offset, field.bitWidth, i);
        Intrinsics.checkNotNullParameter(field, "field");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public FlagImpl(Flags.BooleanFlagField field) {
        this(field, 1);
        Intrinsics.checkNotNullParameter(field, "field");
    }

    public final boolean invoke(int i) {
        return ((i >>> this.offset) & ((1 << this.bitWidth) - 1)) == this.value;
    }
}
