package okio;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SegmentPool.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\bÀ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0011\u001a\u00020\tH\u0007J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\tH\u0007J\u0010\u0010\u0015\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\rH\u0002R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u000b\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\r0\fX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0007¨\u0006\u0016"}, d2 = {"Lokio/SegmentPool;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "MAX_SIZE", "", "getMAX_SIZE", "()I", "LOCK", "Lokio/Segment;", "HASH_BUCKET_COUNT", "hashBuckets", "", "Ljava/util/concurrent/atomic/AtomicReference;", "[Ljava/util/concurrent/atomic/AtomicReference;", "byteCount", "getByteCount", "take", "recycle", "", "segment", "firstRef", "okio"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class SegmentPool {
    private static final int HASH_BUCKET_COUNT;
    private static final AtomicReference<Segment>[] hashBuckets;
    public static final SegmentPool INSTANCE = new SegmentPool();
    private static final int MAX_SIZE = 65536;
    private static final Segment LOCK = new Segment(new byte[0], 0, 0, false, false);

    private SegmentPool() {
    }

    static {
        int iHighestOneBit = Integer.highestOneBit((Runtime.getRuntime().availableProcessors() * 2) - 1);
        HASH_BUCKET_COUNT = iHighestOneBit;
        AtomicReference<Segment>[] atomicReferenceArr = new AtomicReference[iHighestOneBit];
        for (int i = 0; i < iHighestOneBit; i++) {
            atomicReferenceArr[i] = new AtomicReference<>();
        }
        hashBuckets = atomicReferenceArr;
    }

    public final int getMAX_SIZE() {
        return MAX_SIZE;
    }

    public final int getByteCount() {
        Segment segment = firstRef().get();
        if (segment == null) {
            return 0;
        }
        return segment.limit;
    }

    @JvmStatic
    public static final Segment take() {
        AtomicReference<Segment> atomicReferenceFirstRef = INSTANCE.firstRef();
        Segment segment = LOCK;
        Segment andSet = atomicReferenceFirstRef.getAndSet(segment);
        if (andSet == segment) {
            return new Segment();
        }
        if (andSet == null) {
            atomicReferenceFirstRef.set(null);
            return new Segment();
        }
        atomicReferenceFirstRef.set(andSet.next);
        andSet.next = null;
        andSet.limit = 0;
        return andSet;
    }

    @JvmStatic
    public static final void recycle(Segment segment) {
        AtomicReference<Segment> atomicReferenceFirstRef;
        Segment segment2;
        Segment andSet;
        Intrinsics.checkNotNullParameter(segment, "segment");
        if (segment.next != null || segment.prev != null) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (segment.shared || (andSet = (atomicReferenceFirstRef = INSTANCE.firstRef()).getAndSet((segment2 = LOCK))) == segment2) {
            return;
        }
        int i = andSet != null ? andSet.limit : 0;
        if (i >= MAX_SIZE) {
            atomicReferenceFirstRef.set(andSet);
            return;
        }
        segment.next = andSet;
        segment.pos = 0;
        segment.limit = i + 8192;
        atomicReferenceFirstRef.set(segment);
    }

    private final AtomicReference<Segment> firstRef() {
        return hashBuckets[(int) (Thread.currentThread().getId() & (((long) HASH_BUCKET_COUNT) - 1))];
    }
}
