package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import ch.qos.logback.core.CoreConstants;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: IncompatibleVersionErrorData.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class IncompatibleVersionErrorData<T> {
    private final T actualVersion;
    private final T compilerVersion;
    private final T expectedVersion;
    private final String filePath;
    private final T languageVersion;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IncompatibleVersionErrorData)) {
            return false;
        }
        IncompatibleVersionErrorData incompatibleVersionErrorData = (IncompatibleVersionErrorData) obj;
        return Intrinsics.areEqual(this.actualVersion, incompatibleVersionErrorData.actualVersion) && Intrinsics.areEqual(this.compilerVersion, incompatibleVersionErrorData.compilerVersion) && Intrinsics.areEqual(this.languageVersion, incompatibleVersionErrorData.languageVersion) && Intrinsics.areEqual(this.expectedVersion, incompatibleVersionErrorData.expectedVersion) && Intrinsics.areEqual(this.filePath, incompatibleVersionErrorData.filePath);
    }

    public int hashCode() {
        T t = this.actualVersion;
        int iHashCode = (t == null ? 0 : t.hashCode()) * 31;
        T t2 = this.compilerVersion;
        int iHashCode2 = (iHashCode + (t2 == null ? 0 : t2.hashCode())) * 31;
        T t3 = this.languageVersion;
        int iHashCode3 = (iHashCode2 + (t3 == null ? 0 : t3.hashCode())) * 31;
        T t4 = this.expectedVersion;
        return ((iHashCode3 + (t4 != null ? t4.hashCode() : 0)) * 31) + this.filePath.hashCode();
    }

    public String toString() {
        return "IncompatibleVersionErrorData(actualVersion=" + this.actualVersion + ", compilerVersion=" + this.compilerVersion + ", languageVersion=" + this.languageVersion + ", expectedVersion=" + this.expectedVersion + ", filePath=" + this.filePath + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public IncompatibleVersionErrorData(T t, T t2, T t3, T t4, String filePath) {
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        this.actualVersion = t;
        this.compilerVersion = t2;
        this.languageVersion = t3;
        this.expectedVersion = t4;
        this.filePath = filePath;
    }
}
