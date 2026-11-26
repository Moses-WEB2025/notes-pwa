package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import ch.qos.logback.core.CoreConstants;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DeserializedContainerSource.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class PreReleaseInfo {
    public static final Companion Companion = new Companion(null);
    private static final PreReleaseInfo DEFAULT_VISIBLE = new PreReleaseInfo(false, 0 == true ? 1 : 0, 2, 0 == true ? 1 : 0);
    private final boolean isInvisible;
    private final List<String> poisoningFeatures;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PreReleaseInfo)) {
            return false;
        }
        PreReleaseInfo preReleaseInfo = (PreReleaseInfo) obj;
        return this.isInvisible == preReleaseInfo.isInvisible && Intrinsics.areEqual(this.poisoningFeatures, preReleaseInfo.poisoningFeatures);
    }

    public int hashCode() {
        return (Boolean.hashCode(this.isInvisible) * 31) + this.poisoningFeatures.hashCode();
    }

    public String toString() {
        return "PreReleaseInfo(isInvisible=" + this.isInvisible + ", poisoningFeatures=" + this.poisoningFeatures + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    /* JADX INFO: compiled from: DeserializedContainerSource.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public PreReleaseInfo(boolean z, List<String> poisoningFeatures) {
        Intrinsics.checkNotNullParameter(poisoningFeatures, "poisoningFeatures");
        this.isInvisible = z;
        this.poisoningFeatures = poisoningFeatures;
    }

    public /* synthetic */ PreReleaseInfo(boolean z, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, (i & 2) != 0 ? CollectionsKt.emptyList() : list);
    }
}
