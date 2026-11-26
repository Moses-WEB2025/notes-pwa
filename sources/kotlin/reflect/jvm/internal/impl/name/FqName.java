package kotlin.reflect.jvm.internal.impl.name;

import com.xiaomi.verificationsdk.internal.Constants;
import java.util.List;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FqName.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class FqName {
    public static final Companion Companion = new Companion(null);
    public static final FqName ROOT = new FqName("");
    private final FqNameUnsafe fqName;
    private transient FqName parent;

    public FqName(String fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        this.fqName = new FqNameUnsafe(fqName, this);
    }

    public FqName(FqNameUnsafe fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        this.fqName = fqName;
    }

    private FqName(FqNameUnsafe fqNameUnsafe, FqName fqName) {
        this.fqName = fqNameUnsafe;
        this.parent = fqName;
    }

    public final String asString() {
        return this.fqName.asString();
    }

    public final FqNameUnsafe toUnsafe() {
        return this.fqName;
    }

    public final boolean isRoot() {
        return this.fqName.isRoot();
    }

    public final FqName parent() {
        FqName fqName = this.parent;
        if (fqName != null) {
            return fqName;
        }
        if (isRoot()) {
            throw new IllegalStateException(Constants.ROOT.toString());
        }
        FqName fqName2 = new FqName(this.fqName.parent());
        this.parent = fqName2;
        return fqName2;
    }

    public final FqName child(Name name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return new FqName(this.fqName.child(name), this);
    }

    public final Name shortName() {
        return this.fqName.shortName();
    }

    public final Name shortNameOrSpecial() {
        return this.fqName.shortNameOrSpecial();
    }

    public final List<Name> pathSegments() {
        return this.fqName.pathSegments();
    }

    public final boolean startsWith(Name segment) {
        Intrinsics.checkNotNullParameter(segment, "segment");
        return this.fqName.startsWith(segment);
    }

    public String toString() {
        return this.fqName.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof FqName) && Intrinsics.areEqual(this.fqName, ((FqName) obj).fqName);
    }

    public int hashCode() {
        return this.fqName.hashCode();
    }

    /* JADX INFO: compiled from: FqName.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final FqName topLevel(Name shortName) {
            Intrinsics.checkNotNullParameter(shortName, "shortName");
            return new FqName(FqNameUnsafe.Companion.topLevel(shortName));
        }
    }
}
