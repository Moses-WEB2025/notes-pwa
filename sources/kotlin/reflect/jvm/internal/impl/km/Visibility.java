package kotlin.reflect.jvm.internal.impl.km;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.km.internal.FlagImpl;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.Flags;

/* JADX INFO: compiled from: Modifiers.kt */
/* JADX INFO: loaded from: classes5.dex */
public enum Visibility {
    INTERNAL(0),
    PRIVATE(1),
    PROTECTED(2),
    PUBLIC(3),
    PRIVATE_TO_THIS(4),
    LOCAL(5);

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final FlagImpl flag;

    public static EnumEntries<Visibility> getEntries() {
        return $ENTRIES;
    }

    Visibility(int i) {
        Flags.FlagField<ProtoBuf.Visibility> VISIBILITY = Flags.VISIBILITY;
        Intrinsics.checkNotNullExpressionValue(VISIBILITY, "VISIBILITY");
        this.flag = new FlagImpl(VISIBILITY, i);
    }

    public final FlagImpl getFlag$kotlin_metadata() {
        return this.flag;
    }
}
