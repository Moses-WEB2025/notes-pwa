package kotlinx.coroutines.internal;

import androidx.concurrent.futures.AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0;
import androidx.lifecycle.AtomicReference$$ExternalSyntheticBackportWithForwarding0;
import com.google.common.util.concurrent.Striped$SmallLazyStriped$$ExternalSyntheticBackportWithForwarding0;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import miuix.navigation.SplitLayout;

/* JADX INFO: compiled from: ConcurrentLinkedList.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000N\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001ag\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003*\u0002H\u00022\u0006\u0010\u0004\u001a\u00020\u000526\u0010\u0006\u001a2\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0004\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0004\u0012\u0002H\u00020\u0007H\u0000¢\u0006\u0002\u0010\u000b\u001a+\u0010\f\u001a\u00020\r\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u000e2\u0006\u0010\u000f\u001a\u0002H\u0002H\u0080\b\u001as\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u0002H\u000228\b\b\u0010\u0006\u001a2\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0004\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0004\u0012\u0002H\u00020\u0007H\u0080\b\u001a!\u0010\u0012\u001a\u0002H\u0013\"\u000e\b\u0000\u0010\u0013*\b\u0012\u0004\u0012\u0002H\u00130\u0014*\u0002H\u0013H\u0000¢\u0006\u0002\u0010\u0015\u001a8\u0010\u0016\u001a\u00020\r*\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192!\u0010\u001a\u001a\u001d\u0012\u0013\u0012\u00110\u0019¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u001c\u0012\u0004\u0012\u00020\r0\u001bH\u0082\b\"\u000e\u0010\u001d\u001a\u00020\u0019X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"findSegmentInternal", "Lkotlinx/coroutines/internal/SegmentOrClosed;", "S", "Lkotlinx/coroutines/internal/Segment;", "id", "", "createNewSegment", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "prev", "(Lkotlinx/coroutines/internal/Segment;JLkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "moveForward", "", "Lkotlinx/atomicfu/AtomicRef;", "to", "findSegmentAndMoveForward", "startFrom", SplitLayout.TAG_CLOSE, "N", "Lkotlinx/coroutines/internal/ConcurrentLinkedListNode;", "(Lkotlinx/coroutines/internal/ConcurrentLinkedListNode;)Lkotlinx/coroutines/internal/ConcurrentLinkedListNode;", "addConditionally", "Lkotlinx/atomicfu/AtomicInt;", "delta", "", "condition", "Lkotlin/Function1;", "cur", "POINTERS_SHIFT", "CLOSED", "Lkotlinx/coroutines/internal/Symbol;", "kotlinx-coroutines-core"}, k = 2, mv = {2, 1, 0}, xi = 48)
public final class ConcurrentLinkedListKt {
    private static final Symbol CLOSED = new Symbol("CLOSED");
    private static final int POINTERS_SHIFT = 16;

    public static final <S extends Segment<S>> Object findSegmentInternal(S s, long j, Function2<? super Long, ? super S, ? extends S> function2) {
        while (true) {
            if (s.id >= j && !s.isRemoved()) {
                return SegmentOrClosed.m4124constructorimpl(s);
            }
            Object nextOrClosed = s.getNextOrClosed();
            if (nextOrClosed == CLOSED) {
                return SegmentOrClosed.m4124constructorimpl(CLOSED);
            }
            S sInvoke = (S) ((ConcurrentLinkedListNode) nextOrClosed);
            if (sInvoke == null) {
                sInvoke = function2.invoke(Long.valueOf(s.id + 1), s);
                if (s.trySetNext(sInvoke)) {
                    if (s.isRemoved()) {
                        s.remove();
                    }
                }
            }
            s = (Object) sInvoke;
        }
    }

    public static final /* synthetic */ <S extends Segment<S>> boolean moveForward$atomicfu$ATOMIC_ARRAY$Any(AtomicReferenceArray atomicReferenceArray, int i, S s) {
        while (true) {
            Segment segment = (Segment) atomicReferenceArray.get(i);
            if (segment.id >= s.id) {
                return true;
            }
            if (!s.tryIncPointers$kotlinx_coroutines_core()) {
                return false;
            }
            if (Striped$SmallLazyStriped$$ExternalSyntheticBackportWithForwarding0.m(atomicReferenceArray, i, segment, s)) {
                if (segment.decPointers$kotlinx_coroutines_core()) {
                    segment.remove();
                }
                return true;
            }
            if (s.decPointers$kotlinx_coroutines_core()) {
                s.remove();
            }
        }
    }

    public static final /* synthetic */ <S extends Segment<S>> boolean moveForward$atomicfu$ATOMIC_FIELD_UPDATER$Any(AtomicReferenceFieldUpdater atomicReferenceFieldUpdater, Object obj, S s) {
        while (true) {
            Segment segment = (Segment) atomicReferenceFieldUpdater.get(obj);
            if (segment.id >= s.id) {
                return true;
            }
            if (!s.tryIncPointers$kotlinx_coroutines_core()) {
                return false;
            }
            if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(atomicReferenceFieldUpdater, obj, segment, s)) {
                if (segment.decPointers$kotlinx_coroutines_core()) {
                    segment.remove();
                }
                return true;
            }
            if (s.decPointers$kotlinx_coroutines_core()) {
                s.remove();
            }
        }
    }

    public static final /* synthetic */ <S extends Segment<S>> boolean moveForward$atomicfu$BOXED_ATOMIC$Any(AtomicReference atomicReference, S s) {
        while (true) {
            Segment segment = (Segment) atomicReference.get();
            if (segment.id >= s.id) {
                return true;
            }
            if (!s.tryIncPointers$kotlinx_coroutines_core()) {
                return false;
            }
            if (AtomicReference$$ExternalSyntheticBackportWithForwarding0.m(atomicReference, segment, s)) {
                if (segment.decPointers$kotlinx_coroutines_core()) {
                    segment.remove();
                }
                return true;
            }
            if (s.decPointers$kotlinx_coroutines_core()) {
                s.remove();
            }
        }
    }

    public static final /* synthetic */ <S extends Segment<S>> Object findSegmentAndMoveForward$atomicfu$ATOMIC_ARRAY$Any(AtomicReferenceArray atomicReferenceArray, int i, long j, S s, Function2<? super Long, ? super S, ? extends S> function2) {
        while (true) {
            Object objFindSegmentInternal = findSegmentInternal(s, j, function2);
            if (SegmentOrClosed.m4129isClosedimpl(objFindSegmentInternal)) {
                return objFindSegmentInternal;
            }
            Segment segmentM4127getSegmentimpl = SegmentOrClosed.m4127getSegmentimpl(objFindSegmentInternal);
            while (true) {
                Segment segment = (Segment) atomicReferenceArray.get(i);
                if (segment.id >= segmentM4127getSegmentimpl.id) {
                    return objFindSegmentInternal;
                }
                if (!segmentM4127getSegmentimpl.tryIncPointers$kotlinx_coroutines_core()) {
                    break;
                }
                if (Striped$SmallLazyStriped$$ExternalSyntheticBackportWithForwarding0.m(atomicReferenceArray, i, segment, segmentM4127getSegmentimpl)) {
                    if (segment.decPointers$kotlinx_coroutines_core()) {
                        segment.remove();
                    }
                    return objFindSegmentInternal;
                }
                if (segmentM4127getSegmentimpl.decPointers$kotlinx_coroutines_core()) {
                    segmentM4127getSegmentimpl.remove();
                }
            }
        }
    }

    public static final /* synthetic */ <S extends Segment<S>> Object findSegmentAndMoveForward$atomicfu$ATOMIC_FIELD_UPDATER$Any(AtomicReferenceFieldUpdater atomicReferenceFieldUpdater, Object obj, long j, S s, Function2<? super Long, ? super S, ? extends S> function2) {
        while (true) {
            Object objFindSegmentInternal = findSegmentInternal(s, j, function2);
            if (SegmentOrClosed.m4129isClosedimpl(objFindSegmentInternal)) {
                return objFindSegmentInternal;
            }
            Segment segmentM4127getSegmentimpl = SegmentOrClosed.m4127getSegmentimpl(objFindSegmentInternal);
            while (true) {
                Segment segment = (Segment) atomicReferenceFieldUpdater.get(obj);
                if (segment.id >= segmentM4127getSegmentimpl.id) {
                    return objFindSegmentInternal;
                }
                if (!segmentM4127getSegmentimpl.tryIncPointers$kotlinx_coroutines_core()) {
                    break;
                }
                if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(atomicReferenceFieldUpdater, obj, segment, segmentM4127getSegmentimpl)) {
                    if (segment.decPointers$kotlinx_coroutines_core()) {
                        segment.remove();
                    }
                    return objFindSegmentInternal;
                }
                if (segmentM4127getSegmentimpl.decPointers$kotlinx_coroutines_core()) {
                    segmentM4127getSegmentimpl.remove();
                }
            }
        }
    }

    public static final /* synthetic */ <S extends Segment<S>> Object findSegmentAndMoveForward$atomicfu$BOXED_ATOMIC$Any(AtomicReference atomicReference, long j, S s, Function2<? super Long, ? super S, ? extends S> function2) {
        while (true) {
            Object objFindSegmentInternal = findSegmentInternal(s, j, function2);
            if (SegmentOrClosed.m4129isClosedimpl(objFindSegmentInternal)) {
                return objFindSegmentInternal;
            }
            Segment segmentM4127getSegmentimpl = SegmentOrClosed.m4127getSegmentimpl(objFindSegmentInternal);
            while (true) {
                Segment segment = (Segment) atomicReference.get();
                if (segment.id >= segmentM4127getSegmentimpl.id) {
                    return objFindSegmentInternal;
                }
                if (!segmentM4127getSegmentimpl.tryIncPointers$kotlinx_coroutines_core()) {
                    break;
                }
                if (AtomicReference$$ExternalSyntheticBackportWithForwarding0.m(atomicReference, segment, segmentM4127getSegmentimpl)) {
                    if (segment.decPointers$kotlinx_coroutines_core()) {
                        segment.remove();
                    }
                    return objFindSegmentInternal;
                }
                if (segmentM4127getSegmentimpl.decPointers$kotlinx_coroutines_core()) {
                    segmentM4127getSegmentimpl.remove();
                }
            }
        }
    }

    private static final /* synthetic */ boolean addConditionally$atomicfu$ATOMIC_ARRAY$Int(AtomicIntegerArray atomicIntegerArray, int i, int i2, Function1<? super Integer, Boolean> function1) {
        int i3;
        do {
            i3 = atomicIntegerArray.get(i);
            if (!function1.invoke(Integer.valueOf(i3)).booleanValue()) {
                return false;
            }
        } while (!atomicIntegerArray.compareAndSet(i, i3, i3 + i2));
        return true;
    }

    private static final /* synthetic */ boolean addConditionally$atomicfu$ATOMIC_FIELD_UPDATER$Int(AtomicIntegerFieldUpdater atomicIntegerFieldUpdater, Object obj, int i, Function1<? super Integer, Boolean> function1) {
        int i2;
        do {
            i2 = atomicIntegerFieldUpdater.get(obj);
            if (!function1.invoke(Integer.valueOf(i2)).booleanValue()) {
                return false;
            }
        } while (!atomicIntegerFieldUpdater.compareAndSet(obj, i2, i2 + i));
        return true;
    }

    private static final /* synthetic */ boolean addConditionally$atomicfu$BOXED_ATOMIC$Int(AtomicInteger atomicInteger, int i, Function1<? super Integer, Boolean> function1) {
        int i2;
        do {
            i2 = atomicInteger.get();
            if (!function1.invoke(Integer.valueOf(i2)).booleanValue()) {
                return false;
            }
        } while (!atomicInteger.compareAndSet(i2, i2 + i));
        return true;
    }

    public static final <N extends ConcurrentLinkedListNode<N>> N close(N n) {
        while (true) {
            Object nextOrClosed = n.getNextOrClosed();
            if (nextOrClosed == CLOSED) {
                return n;
            }
            ConcurrentLinkedListNode concurrentLinkedListNode = (ConcurrentLinkedListNode) nextOrClosed;
            if (concurrentLinkedListNode != null) {
                n = (N) concurrentLinkedListNode;
            } else if (n.markAsClosed()) {
                return n;
            }
        }
    }
}
