package kotlin.reflect.jvm.internal.impl.km;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.km.internal.FlagImpl;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.Flags;

/* JADX INFO: compiled from: Modifiers.kt */
/* JADX INFO: loaded from: classes5.dex */
public enum ClassKind {
    CLASS(0),
    INTERFACE(1),
    ENUM_CLASS(2),
    ENUM_ENTRY(3),
    ANNOTATION_CLASS(4),
    OBJECT(5),
    COMPANION_OBJECT(6);

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final FlagImpl flag;

    public static EnumEntries<ClassKind> getEntries() {
        return $ENTRIES;
    }

    ClassKind(int i) {
        Flags.FlagField<ProtoBuf.Class.Kind> CLASS_KIND = Flags.CLASS_KIND;
        Intrinsics.checkNotNullExpressionValue(CLASS_KIND, "CLASS_KIND");
        this.flag = new FlagImpl(CLASS_KIND, i);
    }

    public final FlagImpl getFlag$kotlin_metadata() {
        return this.flag;
    }
}
