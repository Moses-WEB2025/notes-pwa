package kotlin.reflect.jvm.internal.impl.km;

import ch.qos.logback.core.CoreConstants;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Contracts.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class KmConstantValue {
    private final Object value;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof KmConstantValue) && Intrinsics.areEqual(this.value, ((KmConstantValue) obj).value);
    }

    public int hashCode() {
        Object obj = this.value;
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }

    public String toString() {
        return "KmConstantValue(value=" + this.value + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public KmConstantValue(Object obj) {
        this.value = obj;
    }
}
