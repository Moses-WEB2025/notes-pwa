package kotlin.reflect.jvm.internal.impl.name;

import com.xiaomi.onetrack.util.ab;
import com.xiaomi.verificationsdk.internal.Constants;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlin.text.Typography;

/* JADX INFO: compiled from: FqNameUnsafe.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class FqNameUnsafe {
    public static final Companion Companion = new Companion(null);
    private static final Name ROOT_NAME;
    private static final Pattern SPLIT_BY_DOTS;
    private final String fqName;
    private transient FqNameUnsafe parent;
    private transient FqName safe;
    private transient Name shortName;

    public /* synthetic */ FqNameUnsafe(String str, FqNameUnsafe fqNameUnsafe, Name name, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, fqNameUnsafe, name);
    }

    public FqNameUnsafe(String fqName, FqName safe) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(safe, "safe");
        this.fqName = fqName;
        this.safe = safe;
    }

    public FqNameUnsafe(String fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        this.fqName = fqName;
    }

    private FqNameUnsafe(String str, FqNameUnsafe fqNameUnsafe, Name name) {
        this.fqName = str;
        this.parent = fqNameUnsafe;
        this.shortName = name;
    }

    private final void compute() {
        int iIndexOfLastDotWithBackticksSupport = indexOfLastDotWithBackticksSupport(this.fqName);
        if (iIndexOfLastDotWithBackticksSupport >= 0) {
            String strSubstring = this.fqName.substring(iIndexOfLastDotWithBackticksSupport + 1);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            this.shortName = Name.guessByFirstCharacter(strSubstring);
            String strSubstring2 = this.fqName.substring(0, iIndexOfLastDotWithBackticksSupport);
            Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
            this.parent = new FqNameUnsafe(strSubstring2);
            return;
        }
        this.shortName = Name.guessByFirstCharacter(this.fqName);
        this.parent = FqName.ROOT.toUnsafe();
    }

    private final int indexOfLastDotWithBackticksSupport(String str) {
        int length = str.length() - 1;
        boolean z = false;
        while (length >= 0) {
            char cCharAt = str.charAt(length);
            if (cCharAt == '.' && !z) {
                return length;
            }
            if (cCharAt == '`') {
                z = !z;
            } else if (cCharAt == '\\') {
                length--;
            }
            length--;
        }
        return -1;
    }

    public final String asString() {
        return this.fqName;
    }

    public final boolean isSafe() {
        return this.safe != null || StringsKt.indexOf$default((CharSequence) asString(), Typography.less, 0, false, 6, (Object) null) < 0;
    }

    public final FqName toSafe() {
        FqName fqName = this.safe;
        if (fqName != null) {
            return fqName;
        }
        FqName fqName2 = new FqName(this);
        this.safe = fqName2;
        return fqName2;
    }

    public final boolean isRoot() {
        return this.fqName.length() == 0;
    }

    public final FqNameUnsafe parent() {
        FqNameUnsafe fqNameUnsafe = this.parent;
        if (fqNameUnsafe != null) {
            return fqNameUnsafe;
        }
        if (isRoot()) {
            throw new IllegalStateException(Constants.ROOT.toString());
        }
        compute();
        FqNameUnsafe fqNameUnsafe2 = this.parent;
        Intrinsics.checkNotNull(fqNameUnsafe2);
        return fqNameUnsafe2;
    }

    public final FqNameUnsafe child(Name name) {
        String strAsString;
        Intrinsics.checkNotNullParameter(name, "name");
        if (isRoot()) {
            strAsString = name.asString();
        } else {
            strAsString = this.fqName + '.' + name.asString();
        }
        Intrinsics.checkNotNull(strAsString);
        return new FqNameUnsafe(strAsString, this, name);
    }

    public final Name shortName() {
        Name name = this.shortName;
        if (name != null) {
            return name;
        }
        if (isRoot()) {
            throw new IllegalStateException(Constants.ROOT.toString());
        }
        compute();
        Name name2 = this.shortName;
        Intrinsics.checkNotNull(name2);
        return name2;
    }

    public final Name shortNameOrSpecial() {
        if (isRoot()) {
            return ROOT_NAME;
        }
        return shortName();
    }

    private static final List<Name> pathSegments$collectSegmentsOf(FqNameUnsafe fqNameUnsafe) {
        if (fqNameUnsafe.isRoot()) {
            return new ArrayList();
        }
        List<Name> listPathSegments$collectSegmentsOf = pathSegments$collectSegmentsOf(fqNameUnsafe.parent());
        listPathSegments$collectSegmentsOf.add(fqNameUnsafe.shortName());
        return listPathSegments$collectSegmentsOf;
    }

    public final List<Name> pathSegments() {
        return pathSegments$collectSegmentsOf(this);
    }

    public final boolean startsWith(Name segment) {
        Intrinsics.checkNotNullParameter(segment, "segment");
        if (isRoot()) {
            return false;
        }
        int iIndexOf$default = StringsKt.indexOf$default((CharSequence) this.fqName, '.', 0, false, 6, (Object) null);
        if (iIndexOf$default == -1) {
            iIndexOf$default = this.fqName.length();
        }
        int i = iIndexOf$default;
        String strAsString = segment.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return i == strAsString.length() && StringsKt.regionMatches$default(this.fqName, 0, strAsString, 0, i, false, 16, (Object) null);
    }

    public String toString() {
        if (!isRoot()) {
            return this.fqName;
        }
        String strAsString = ROOT_NAME.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return strAsString;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof FqNameUnsafe) && Intrinsics.areEqual(this.fqName, ((FqNameUnsafe) obj).fqName);
    }

    public int hashCode() {
        return this.fqName.hashCode();
    }

    /* JADX INFO: compiled from: FqNameUnsafe.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final FqNameUnsafe topLevel(Name shortName) {
            Intrinsics.checkNotNullParameter(shortName, "shortName");
            String strAsString = shortName.asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            return new FqNameUnsafe(strAsString, FqName.ROOT.toUnsafe(), shortName, null);
        }
    }

    static {
        Name nameSpecial = Name.special("<root>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        ROOT_NAME = nameSpecial;
        Pattern patternCompile = Pattern.compile(ab.a);
        Intrinsics.checkNotNullExpressionValue(patternCompile, "compile(...)");
        SPLIT_BY_DOTS = patternCompile;
    }
}
