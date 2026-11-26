package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MetadataVersion.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class MetadataVersion extends BinaryVersion {
    public static final Companion Companion = new Companion(null);
    public static final MetadataVersion INSTANCE;
    public static final MetadataVersion INSTANCE_NEXT;
    public static final MetadataVersion INVALID_VERSION;
    private final boolean isStrictSemantics;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MetadataVersion(int[] versionArray, boolean z) {
        super(Arrays.copyOf(versionArray, versionArray.length));
        Intrinsics.checkNotNullParameter(versionArray, "versionArray");
        this.isStrictSemantics = z;
    }

    public final boolean isStrictSemantics() {
        return this.isStrictSemantics;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public MetadataVersion(int... numbers) {
        this(numbers, false);
        Intrinsics.checkNotNullParameter(numbers, "numbers");
    }

    public final MetadataVersion lastSupportedVersionWithThisLanguageVersion(boolean z) {
        MetadataVersion metadataVersion = z ? INSTANCE : INSTANCE_NEXT;
        return metadataVersion.newerThan(this) ? metadataVersion : this;
    }

    public final boolean isCompatible(MetadataVersion metadataVersionFromLanguageVersion) {
        Intrinsics.checkNotNullParameter(metadataVersionFromLanguageVersion, "metadataVersionFromLanguageVersion");
        return isCompatibleInternal(metadataVersionFromLanguageVersion.lastSupportedVersionWithThisLanguageVersion(this.isStrictSemantics));
    }

    private final boolean isCompatibleInternal(MetadataVersion metadataVersion) {
        if ((getMajor() == 1 && getMinor() == 0) || getMajor() == 0) {
            return false;
        }
        return !newerThan(metadataVersion);
    }

    public final MetadataVersion next() {
        return (getMajor() == 1 && getMinor() == 9) ? new MetadataVersion(2, 0, 0) : new MetadataVersion(getMajor(), getMinor() + 1, 0);
    }

    private final boolean newerThan(MetadataVersion metadataVersion) {
        if (getMajor() > metadataVersion.getMajor()) {
            return true;
        }
        return getMajor() >= metadataVersion.getMajor() && getMinor() > metadataVersion.getMinor();
    }

    /* JADX INFO: compiled from: MetadataVersion.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        MetadataVersion metadataVersion = new MetadataVersion(2, 2, 0);
        INSTANCE = metadataVersion;
        INSTANCE_NEXT = metadataVersion.next();
        INVALID_VERSION = new MetadataVersion(new int[0]);
    }
}
