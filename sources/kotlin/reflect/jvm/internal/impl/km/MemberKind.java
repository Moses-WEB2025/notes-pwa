package kotlin.reflect.jvm.internal.impl.km;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.km.internal.FlagImpl;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.Flags;

/* JADX INFO: compiled from: Modifiers.kt */
/* JADX INFO: loaded from: classes5.dex */
public enum MemberKind {
    DECLARATION(0),
    FAKE_OVERRIDE(1),
    DELEGATION(2),
    SYNTHESIZED(3);

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final FlagImpl flag;

    public static EnumEntries<MemberKind> getEntries() {
        return $ENTRIES;
    }

    MemberKind(int i) {
        Flags.FlagField<ProtoBuf.MemberKind> MEMBER_KIND = Flags.MEMBER_KIND;
        Intrinsics.checkNotNullExpressionValue(MEMBER_KIND, "MEMBER_KIND");
        this.flag = new FlagImpl(MEMBER_KIND, i);
    }

    public final FlagImpl getFlag$kotlin_metadata() {
        return this.flag;
    }
}
