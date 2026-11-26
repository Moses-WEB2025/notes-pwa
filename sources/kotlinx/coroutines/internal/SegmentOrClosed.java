package kotlinx.coroutines.internal;

import ch.qos.logback.core.CoreConstants;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.Segment;

/* JADX INFO: compiled from: ConcurrentLinkedList.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0081@\u0018\u0000*\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00020\u0003B\u0011\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0005\u0010\u0006J\u0013\u0010\u0010\u001a\u00020\b2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0003HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0017\u0010\u000b\u001a\u00028\u00008F¢\u0006\f\u0012\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000f\u0088\u0001\u0004\u0092\u0001\u0004\u0018\u00010\u0003¨\u0006\u0016"}, d2 = {"Lkotlinx/coroutines/internal/SegmentOrClosed;", "S", "Lkotlinx/coroutines/internal/Segment;", "", "value", "constructor-impl", "(Ljava/lang/Object;)Ljava/lang/Object;", "isClosed", "", "isClosed-impl", "(Ljava/lang/Object;)Z", "segment", "getSegment$annotations", "()V", "getSegment-impl", "(Ljava/lang/Object;)Lkotlinx/coroutines/internal/Segment;", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {2, 1, 0}, xi = 48)
@JvmInline
public final class SegmentOrClosed<S extends Segment<S>> {
    private final Object value;

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ SegmentOrClosed m4123boximpl(Object obj) {
        return new SegmentOrClosed(obj);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static <S extends Segment<S>> Object m4124constructorimpl(Object obj) {
        return obj;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m4125equalsimpl(Object obj, Object obj2) {
        return (obj2 instanceof SegmentOrClosed) && Intrinsics.areEqual(obj, ((SegmentOrClosed) obj2).getValue());
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m4126equalsimpl0(Object obj, Object obj2) {
        return Intrinsics.areEqual(obj, obj2);
    }

    public static /* synthetic */ void getSegment$annotations() {
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m4128hashCodeimpl(Object obj) {
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m4130toStringimpl(Object obj) {
        return "SegmentOrClosed(value=" + obj + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public boolean equals(Object other) {
        return m4125equalsimpl(this.value, other);
    }

    public int hashCode() {
        return m4128hashCodeimpl(this.value);
    }

    public String toString() {
        return m4130toStringimpl(this.value);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name and from getter */
    public final /* synthetic */ Object getValue() {
        return this.value;
    }

    private /* synthetic */ SegmentOrClosed(Object obj) {
        this.value = obj;
    }

    /* JADX INFO: renamed from: isClosed-impl, reason: not valid java name */
    public static final boolean m4129isClosedimpl(Object obj) {
        return obj == ConcurrentLinkedListKt.CLOSED;
    }

    /* JADX INFO: renamed from: getSegment-impl, reason: not valid java name */
    public static final S m4127getSegmentimpl(Object obj) {
        if (obj == ConcurrentLinkedListKt.CLOSED) {
            throw new IllegalStateException("Does not contain segment".toString());
        }
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type S of kotlinx.coroutines.internal.SegmentOrClosed");
        return (S) obj;
    }
}
