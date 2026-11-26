package androidx.collection;

import ch.qos.logback.core.CoreConstants;
import com.miui.maml.data.VariableNames;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.JvmInline;
import net.lingala.zip4j.util.InternalZipConstants;

/* JADX INFO: compiled from: IntIntPair.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0087@\u0018\u00002\u00020\u0001B\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0004\b\u0005\u0010\u0006B\u0011\b\u0000\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0004\b\u0005\u0010\tJ\u0010\u0010\r\u001a\u00020\u0003H\u0086\n¢\u0006\u0004\b\u000e\u0010\u000bJ\u0010\u0010\u000f\u001a\u00020\u0003H\u0086\n¢\u0006\u0004\b\u0010\u0010\u000bJ\u001a\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003¢\u0006\u0004\b\u0014\u0010\u0015J\u0010\u0010\u0016\u001a\u00020\u0003HÖ\u0001¢\u0006\u0004\b\u0017\u0010\u000bJ\u000f\u0010\u0018\u001a\u00020\u0019H\u0016¢\u0006\u0004\b\u001a\u0010\u001bR\u0011\u0010\u0002\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0010\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\f\u0010\u000b\u0088\u0001\u0007¨\u0006\u001c"}, d2 = {"Landroidx/collection/IntIntPair;", "", "first", "", VariableNames.VAR_SECOND, "constructor-impl", "(II)J", "packedValue", "", "(J)J", "getFirst-impl", "(J)I", "getSecond-impl", "component1", "component1-impl", "component2", "component2-impl", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "equals-impl", "(JLjava/lang/Object;)Z", "hashCode", "hashCode-impl", "toString", "", "toString-impl", "(J)Ljava/lang/String;", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
@JvmInline
public final class IntIntPair {
    public final long packedValue;

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ IntIntPair m31boximpl(long j) {
        return new IntIntPair(j);
    }

    /* JADX INFO: renamed from: component1-impl, reason: not valid java name */
    public static final int m32component1impl(long j) {
        return (int) (j >> 32);
    }

    /* JADX INFO: renamed from: component2-impl, reason: not valid java name */
    public static final int m33component2impl(long j) {
        return (int) (j & InternalZipConstants.ZIP_64_SIZE_LIMIT);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m35constructorimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m36equalsimpl(long j, Object obj) {
        return (obj instanceof IntIntPair) && j == ((IntIntPair) obj).getPackedValue();
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m37equalsimpl0(long j, long j2) {
        return j == j2;
    }

    /* JADX INFO: renamed from: getFirst-impl, reason: not valid java name */
    public static final int m38getFirstimpl(long j) {
        return (int) (j >> 32);
    }

    /* JADX INFO: renamed from: getSecond-impl, reason: not valid java name */
    public static final int m39getSecondimpl(long j) {
        return (int) (j & InternalZipConstants.ZIP_64_SIZE_LIMIT);
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m40hashCodeimpl(long j) {
        return Long.hashCode(j);
    }

    public boolean equals(Object obj) {
        return m36equalsimpl(this.packedValue, obj);
    }

    public int hashCode() {
        return m40hashCodeimpl(this.packedValue);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name and from getter */
    public final /* synthetic */ long getPackedValue() {
        return this.packedValue;
    }

    private /* synthetic */ IntIntPair(long j) {
        this.packedValue = j;
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m34constructorimpl(int i, int i2) {
        return m35constructorimpl((((long) i2) & InternalZipConstants.ZIP_64_SIZE_LIMIT) | (((long) i) << 32));
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m41toStringimpl(long j) {
        return "(" + m38getFirstimpl(j) + ", " + m39getSecondimpl(j) + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public String toString() {
        return m41toStringimpl(this.packedValue);
    }
}
