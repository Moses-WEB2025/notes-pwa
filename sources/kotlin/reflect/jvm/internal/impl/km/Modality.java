package kotlin.reflect.jvm.internal.impl.km;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.km.internal.FlagImpl;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.Flags;

/* JADX INFO: compiled from: Modifiers.kt */
/* JADX INFO: loaded from: classes5.dex */
public enum Modality {
    FINAL(0),
    OPEN(1),
    ABSTRACT(2),
    SEALED(3);

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final FlagImpl flag;

    public static EnumEntries<Modality> getEntries() {
        return $ENTRIES;
    }

    Modality(int i) {
        Flags.FlagField<ProtoBuf.Modality> MODALITY = Flags.MODALITY;
        Intrinsics.checkNotNullExpressionValue(MODALITY, "MODALITY");
        this.flag = new FlagImpl(MODALITY, i);
    }

    public final FlagImpl getFlag$kotlin_metadata() {
        return this.flag;
    }
}
