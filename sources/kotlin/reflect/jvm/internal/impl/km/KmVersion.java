package kotlin.reflect.jvm.internal.impl.km;

/* JADX INFO: compiled from: Nodes.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class KmVersion {
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
        if (!(obj instanceof KmVersion)) {
            return false;
        }
        KmVersion kmVersion = (KmVersion) obj;
        return this.major == kmVersion.major && this.minor == kmVersion.minor && this.patch == kmVersion.patch;
    }

    public int hashCode() {
        return (((Integer.hashCode(this.major) * 31) + Integer.hashCode(this.minor)) * 31) + Integer.hashCode(this.patch);
    }

    public KmVersion(int i, int i2, int i3) {
        this.major = i;
        this.minor = i2;
        this.patch = i3;
    }

    public String toString() {
        return new StringBuilder().append(this.major).append('.').append(this.minor).append('.').append(this.patch).toString();
    }
}
