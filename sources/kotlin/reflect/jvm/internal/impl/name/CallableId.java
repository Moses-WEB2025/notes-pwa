package kotlin.reflect.jvm.internal.impl.name;

import defpackage.R2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.lingala.zip4j.util.InternalZipConstants;

/* JADX INFO: compiled from: CallableId.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class CallableId {
    public static final Companion Companion = new Companion(null);
    private static final Name LOCAL_NAME;
    private static final FqName PACKAGE_FQ_NAME_FOR_LOCAL;
    private final Name callableName;
    private final ClassId classId;
    private final FqName className;
    private final FqName packageName;
    private final FqName pathToLocal;

    private CallableId(FqName fqName, FqName fqName2, Name name, ClassId classId, FqName fqName3) {
        this.packageName = fqName;
        this.className = fqName2;
        this.callableName = name;
        this.classId = classId;
        this.pathToLocal = fqName3;
    }

    /* JADX INFO: compiled from: CallableId.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        Name name = SpecialNames.LOCAL;
        LOCAL_NAME = name;
        PACKAGE_FQ_NAME_FOR_LOCAL = FqName.Companion.topLevel(name);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CallableId(FqName packageName, Name callableName) {
        this(packageName, null, callableName, null, null);
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(callableName, "callableName");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CallableId)) {
            return false;
        }
        CallableId callableId = (CallableId) obj;
        return Intrinsics.areEqual(this.packageName, callableId.packageName) && Intrinsics.areEqual(this.className, callableId.className) && Intrinsics.areEqual(this.callableName, callableId.callableName);
    }

    public int hashCode() {
        int iHashCode = (R2.attr.buttonBackground + this.packageName.hashCode()) * 31;
        FqName fqName = this.className;
        return ((iHashCode + (fqName != null ? fqName.hashCode() : 0)) * 31) + this.callableName.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(StringsKt.replace$default(this.packageName.asString(), '.', '/', false, 4, (Object) null));
        sb.append(InternalZipConstants.ZIP_FILE_SEPARATOR);
        FqName fqName = this.className;
        if (fqName != null) {
            sb.append(fqName);
            sb.append(".");
        }
        sb.append(this.callableName);
        return sb.toString();
    }
}
