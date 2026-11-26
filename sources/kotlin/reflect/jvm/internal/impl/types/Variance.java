package kotlin.reflect.jvm.internal.impl.types;

import com.market.sdk.utils.Language;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: Variance.kt */
/* JADX INFO: loaded from: classes5.dex */
public enum Variance {
    INVARIANT("", true, true, 0),
    IN_VARIANCE(Language.LA_IN, true, false, -1),
    OUT_VARIANCE("out", false, true, 1);

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final boolean allowsInPosition;
    private final boolean allowsOutPosition;
    private final String label;
    private final int superpositionFactor;

    Variance(String str, boolean z, boolean z2, int i) {
        this.label = str;
        this.allowsInPosition = z;
        this.allowsOutPosition = z2;
        this.superpositionFactor = i;
    }

    public final String getLabel() {
        return this.label;
    }

    public final boolean getAllowsOutPosition() {
        return this.allowsOutPosition;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.label;
    }
}
