package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import kotlin.DeprecationLevel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;

/* JADX INFO: compiled from: VersionRequirement.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class VersionRequirement {
    public static final Companion Companion = new Companion(null);
    private final Integer errorCode;
    private final ProtoBuf.VersionRequirement.VersionKind kind;
    private final DeprecationLevel level;
    private final String message;
    private final Version version;

    public VersionRequirement(Version version, ProtoBuf.VersionRequirement.VersionKind kind, DeprecationLevel level, Integer num, String str) {
        Intrinsics.checkNotNullParameter(version, "version");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(level, "level");
        this.version = version;
        this.kind = kind;
        this.level = level;
        this.errorCode = num;
        this.message = str;
    }

    public final Version getVersion() {
        return this.version;
    }

    public final ProtoBuf.VersionRequirement.VersionKind getKind() {
        return this.kind;
    }

    public final DeprecationLevel getLevel() {
        return this.level;
    }

    public final Integer getErrorCode() {
        return this.errorCode;
    }

    public final String getMessage() {
        return this.message;
    }

    /* JADX INFO: compiled from: VersionRequirement.kt */
    public static final class Version {
        public static final Companion Companion = new Companion(null);
        public static final Version INFINITY = new Version(256, 256, 256);
        private final int major;
        private final int minor;
        private final int patch;

        public final int component1() {
            return this.major;
        }

        public final int component2() {
            return this.minor;
        }

        public final int component3() {
            return this.patch;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Version)) {
                return false;
            }
            Version version = (Version) obj;
            return this.major == version.major && this.minor == version.minor && this.patch == version.patch;
        }

        public int hashCode() {
            return (((Integer.hashCode(this.major) * 31) + Integer.hashCode(this.minor)) * 31) + Integer.hashCode(this.patch);
        }

        public Version(int i, int i2, int i3) {
            this.major = i;
            this.minor = i2;
            this.patch = i3;
        }

        public final String asString() {
            StringBuilder sbAppend;
            int i;
            if (this.patch == 0) {
                sbAppend = new StringBuilder().append(this.major).append('.');
                i = this.minor;
            } else {
                sbAppend = new StringBuilder().append(this.major).append('.').append(this.minor).append('.');
                i = this.patch;
            }
            return sbAppend.append(i).toString();
        }

        public final void encode(Function1<? super Integer, Unit> writeVersion, Function1<? super Integer, Unit> writeVersionFull) {
            int i;
            int i2;
            Intrinsics.checkNotNullParameter(writeVersion, "writeVersion");
            Intrinsics.checkNotNullParameter(writeVersionFull, "writeVersionFull");
            if (Intrinsics.areEqual(this, INFINITY)) {
                return;
            }
            int i3 = this.major;
            if (i3 > 7 || (i = this.minor) > 15 || (i2 = this.patch) > 127) {
                writeVersionFull.invoke(Integer.valueOf((this.patch << 16) | (this.minor << 8) | i3));
            } else {
                writeVersion.invoke(Integer.valueOf((i << 3) | i3 | (i2 << 7)));
            }
        }

        public String toString() {
            return asString();
        }

        /* JADX INFO: compiled from: VersionRequirement.kt */
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final Version decode(Integer num, Integer num2) {
                if (num2 != null) {
                    return new Version(num2.intValue() & 255, (num2.intValue() >> 8) & 255, (num2.intValue() >> 16) & 255);
                }
                if (num != null) {
                    return new Version(num.intValue() & 7, (num.intValue() >> 3) & 15, (num.intValue() >> 7) & 127);
                }
                return Version.INFINITY;
            }
        }
    }

    public String toString() {
        return "since " + this.version + ' ' + this.level + (this.errorCode != null ? " error " + this.errorCode : "") + (this.message != null ? ": " + this.message : "");
    }

    /* JADX INFO: compiled from: VersionRequirement.kt */
    public static final class Companion {

        /* JADX INFO: compiled from: VersionRequirement.kt */
        public static final /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[ProtoBuf.VersionRequirement.Level.values().length];
                try {
                    iArr[ProtoBuf.VersionRequirement.Level.WARNING.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[ProtoBuf.VersionRequirement.Level.ERROR.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[ProtoBuf.VersionRequirement.Level.HIDDEN.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final VersionRequirement create(int i, NameResolver nameResolver, VersionRequirementTable table) {
            DeprecationLevel deprecationLevel;
            Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
            Intrinsics.checkNotNullParameter(table, "table");
            ProtoBuf.VersionRequirement versionRequirement = table.get(i);
            if (versionRequirement == null) {
                return null;
            }
            Version versionDecode = Version.Companion.decode(versionRequirement.hasVersion() ? Integer.valueOf(versionRequirement.getVersion()) : null, versionRequirement.hasVersionFull() ? Integer.valueOf(versionRequirement.getVersionFull()) : null);
            ProtoBuf.VersionRequirement.Level level = versionRequirement.getLevel();
            Intrinsics.checkNotNull(level);
            int i2 = WhenMappings.$EnumSwitchMapping$0[level.ordinal()];
            if (i2 == 1) {
                deprecationLevel = DeprecationLevel.WARNING;
            } else if (i2 == 2) {
                deprecationLevel = DeprecationLevel.ERROR;
            } else {
                if (i2 != 3) {
                    throw new NoWhenBranchMatchedException();
                }
                deprecationLevel = DeprecationLevel.HIDDEN;
            }
            DeprecationLevel deprecationLevel2 = deprecationLevel;
            Integer numValueOf = versionRequirement.hasErrorCode() ? Integer.valueOf(versionRequirement.getErrorCode()) : null;
            String string = versionRequirement.hasMessage() ? nameResolver.getString(versionRequirement.getMessage()) : null;
            ProtoBuf.VersionRequirement.VersionKind versionKind = versionRequirement.getVersionKind();
            Intrinsics.checkNotNullExpressionValue(versionKind, "getVersionKind(...)");
            return new VersionRequirement(versionDecode, versionKind, deprecationLevel2, numValueOf, string);
        }
    }
}
