package androidx.collection;

import defpackage.R2;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.jvm.internal.markers.KMutableIterator;
import kotlin.jvm.internal.markers.KMutableSet;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: ScatterMap.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0010'\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010)\n\u0002\b\u0004\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00040\u0003B\u0019\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0006¢\u0006\u0002\u0010\u0007J\u001c\u0010\f\u001a\u00020\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0004H\u0016J\"\u0010\u000f\u001a\u00020\r2\u0018\u0010\u0010\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00040\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\u001d\u0010\u0014\u001a\u00020\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0004H\u0096\u0002J\"\u0010\u0015\u001a\u00020\r2\u0018\u0010\u0010\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00040\u0011H\u0016J\b\u0010\u0016\u001a\u00020\rH\u0016J\u001b\u0010\u0017\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00040\u0018H\u0096\u0002J\u001c\u0010\u0019\u001a\u00020\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0004H\u0016J\"\u0010\u001a\u001a\u00020\r2\u0018\u0010\u0010\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00040\u0011H\u0016J\"\u0010\u001b\u001a\u00020\r2\u0018\u0010\u0010\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00040\u0011H\u0016R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\u001c"}, d2 = {"Landroidx/collection/MutableEntries;", "K", "V", "", "", "parent", "Landroidx/collection/MutableScatterMap;", "(Landroidx/collection/MutableScatterMap;)V", "size", "", "getSize", "()I", "add", "", "element", "addAll", "elements", "", "clear", "", "contains", "containsAll", "isEmpty", "iterator", "", "remove", "removeAll", "retainAll", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class MutableEntries<K, V> implements Set<Map.Entry<K, V>>, KMutableSet {
    private final MutableScatterMap<K, V> parent;

    @Override // java.util.Set, java.util.Collection
    public Object[] toArray() {
        return CollectionToArray.toArray(this);
    }

    @Override // java.util.Set, java.util.Collection
    public <T> T[] toArray(T[] array) {
        Intrinsics.checkNotNullParameter(array, "array");
        return (T[]) CollectionToArray.toArray(this, array);
    }

    public MutableEntries(MutableScatterMap<K, V> parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        this.parent = parent;
    }

    @Override // java.util.Set, java.util.Collection
    public final /* bridge */ boolean contains(Object obj) {
        if (TypeIntrinsics.isMutableMapEntry(obj)) {
            return contains((Map.Entry) obj);
        }
        return false;
    }

    @Override // java.util.Set, java.util.Collection
    public final /* bridge */ boolean remove(Object obj) {
        if (TypeIntrinsics.isMutableMapEntry(obj)) {
            return remove((Map.Entry) obj);
        }
        return false;
    }

    @Override // java.util.Set, java.util.Collection
    public final /* bridge */ int size() {
        return getSize();
    }

    public int getSize() {
        return this.parent._size;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean isEmpty() {
        return this.parent.isEmpty();
    }

    /* JADX INFO: renamed from: androidx.collection.MutableEntries$iterator$1, reason: invalid class name */
    /* JADX INFO: compiled from: ScatterMap.kt */
    @Metadata(d1 = {"\u0000-\n\u0000\n\u0002\u0010)\n\u0002\u0010'\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010(\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00020\u0001J\t\u0010\u000f\u001a\u00020\u0010H\u0096\u0002J\u0015\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0002H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR,\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0014"}, d2 = {"androidx/collection/MutableEntries$iterator$1", "", "", "current", "", "getCurrent", "()I", "setCurrent", "(I)V", "iterator", "", "getIterator", "()Ljava/util/Iterator;", "setIterator", "(Ljava/util/Iterator;)V", "hasNext", "", "next", "remove", "", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class AnonymousClass1 implements Iterator<Map.Entry<K, V>>, KMutableIterator {
        private int current = -1;
        private Iterator<? extends Map.Entry<K, V>> iterator;
        final /* synthetic */ MutableEntries<K, V> this$0;

        AnonymousClass1(MutableEntries<K, V> mutableEntries) {
            this.this$0 = mutableEntries;
            this.iterator = SequencesKt.iterator(new C00011(mutableEntries, this, null));
        }

        public final Iterator<Map.Entry<K, V>> getIterator() {
            return this.iterator;
        }

        public final void setIterator(Iterator<? extends Map.Entry<K, V>> it) {
            Intrinsics.checkNotNullParameter(it, "<set-?>");
            this.iterator = it;
        }

        public final int getCurrent() {
            return this.current;
        }

        public final void setCurrent(int i) {
            this.current = i;
        }

        /* JADX INFO: renamed from: androidx.collection.MutableEntries$iterator$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: ScatterMap.kt */
        @Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010'\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00050\u0004H\u008a@"}, d2 = {"<anonymous>", "", "K", "V", "Lkotlin/sequences/SequenceScope;", ""}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "androidx.collection.MutableEntries$iterator$1$1", f = "ScatterMap.kt", i = {0, 0, 0, 0, 0, 0, 0}, l = {R2.attr.lottie_clipTextToBoundingBox}, m = "invokeSuspend", n = {"$this$iterator", "m$iv", "lastIndex$iv", "i$iv", "slot$iv", "bitCount$iv", "j$iv"}, s = {"L$0", "L$3", "I$0", "I$1", "J$0", "I$2", "I$3"})
        static final class C00011 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super Map.Entry<K, V>>, Continuation<? super Unit>, Object> {
            int I$0;
            int I$1;
            int I$2;
            int I$3;
            long J$0;
            private /* synthetic */ Object L$0;
            Object L$1;
            Object L$2;
            Object L$3;
            int label;
            final /* synthetic */ MutableEntries<K, V> this$0;
            final /* synthetic */ AnonymousClass1 this$1;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00011(MutableEntries<K, V> mutableEntries, AnonymousClass1 anonymousClass1, Continuation<? super C00011> continuation) {
                super(2, continuation);
                this.this$0 = mutableEntries;
                this.this$1 = anonymousClass1;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                C00011 c00011 = new C00011(this.this$0, this.this$1, continuation);
                c00011.L$0 = obj;
                return c00011;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(SequenceScope<? super Map.Entry<K, V>> sequenceScope, Continuation<? super Unit> continuation) {
                return ((C00011) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Code duplicated, block: B:13:0x0061  */
            /* JADX WARN: Code duplicated, block: B:25:0x00ce  */
            /* JADX WARN: Code duplicated, block: B:27:0x00d1  */
            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:13:0x0061 -> B:14:0x0075). Please report as a decompilation issue!!! */
            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:16:0x007e -> B:20:0x00b9). Please report as a decompilation issue!!! */
            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:18:0x00b6 -> B:21:0x00bc). Please report as a decompilation issue!!! */
            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:25:0x00ce -> B:26:0x00cf). Please report as a decompilation issue!!! */
            /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
                jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
                	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
                	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
                	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
                */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final java.lang.Object invokeSuspend(java.lang.Object r22) {
                /*
                    Method dump skipped, instruction units count: 217
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.collection.MutableEntries.AnonymousClass1.C00011.invokeSuspend(java.lang.Object):java.lang.Object");
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override // java.util.Iterator
        public Map.Entry<K, V> next() {
            return this.iterator.next();
        }

        @Override // java.util.Iterator
        public void remove() {
            if (this.current != -1) {
                ((MutableEntries) this.this$0).parent.removeValueAt(this.current);
                this.current = -1;
            }
        }
    }

    @Override // java.util.Set, java.util.Collection, java.lang.Iterable
    public Iterator<Map.Entry<K, V>> iterator() {
        return new AnonymousClass1(this);
    }

    @Override // java.util.Set, java.util.Collection
    public void clear() {
        this.parent.clear();
    }

    @Override // java.util.Set, java.util.Collection
    public boolean containsAll(Collection<? extends Object> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        Collection<? extends Object> collection = elements;
        if (collection.isEmpty()) {
            return true;
        }
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (!Intrinsics.areEqual(this.parent.get((K) entry.getKey()), entry.getValue())) {
                return false;
            }
        }
        return true;
    }

    public boolean contains(Map.Entry<K, V> element) {
        Intrinsics.checkNotNullParameter(element, "element");
        return Intrinsics.areEqual(this.parent.get(element.getKey()), element.getValue());
    }

    @Override // java.util.Set, java.util.Collection
    public boolean addAll(Collection<? extends Map.Entry<K, V>> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Set, java.util.Collection
    public boolean add(Map.Entry<K, V> element) {
        Intrinsics.checkNotNullParameter(element, "element");
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Set, java.util.Collection
    public boolean retainAll(Collection<? extends Object> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        long[] jArr = this.parent.metadata;
        int length = jArr.length - 2;
        if (length < 0) {
            return false;
        }
        int i = 0;
        boolean z = false;
        while (true) {
            long j = jArr[i];
            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                int i2 = 8 - ((~(i - length)) >>> 31);
                for (int i3 = 0; i3 < i2; i3++) {
                    if ((255 & j) < 128) {
                        int i4 = (i << 3) + i3;
                        Iterator<? extends Object> it = elements.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                Map.Entry entry = (Map.Entry) it.next();
                                if (Intrinsics.areEqual(entry.getKey(), this.parent.keys[i4]) && Intrinsics.areEqual(entry.getValue(), this.parent.values[i4])) {
                                    break;
                                }
                            } else {
                                this.parent.removeValueAt(i4);
                                z = true;
                                break;
                            }
                        }
                    }
                    j >>= 8;
                }
                if (i2 != 8) {
                    return z;
                }
            }
            if (i == length) {
                return z;
            }
            i++;
        }
    }

    @Override // java.util.Set, java.util.Collection
    public boolean removeAll(Collection<? extends Object> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        long[] jArr = this.parent.metadata;
        int length = jArr.length - 2;
        if (length < 0) {
            return false;
        }
        int i = 0;
        boolean z = false;
        while (true) {
            long j = jArr[i];
            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                int i2 = 8 - ((~(i - length)) >>> 31);
                for (int i3 = 0; i3 < i2; i3++) {
                    if ((255 & j) >= 128) {
                        break;
                        break;
                    }
                    int i4 = (i << 3) + i3;
                    Iterator<? extends Object> it = elements.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        Map.Entry entry = (Map.Entry) it.next();
                        if (Intrinsics.areEqual(entry.getKey(), this.parent.keys[i4]) && Intrinsics.areEqual(entry.getValue(), this.parent.values[i4])) {
                            this.parent.removeValueAt(i4);
                            z = true;
                            break;
                        }
                    }
                    j >>= 8;
                }
                if (i2 != 8) {
                    return z;
                }
            }
            if (i == length) {
                return z;
            }
            i++;
        }
    }

    public boolean remove(Map.Entry<K, V> element) {
        int iNumberOfTrailingZeros;
        Intrinsics.checkNotNullParameter(element, "element");
        MutableScatterMap<K, V> mutableScatterMap = this.parent;
        K key = element.getKey();
        int iHashCode = (key != null ? key.hashCode() : 0) * ScatterMapKt.MurmurHashC1;
        int i = iHashCode ^ (iHashCode << 16);
        int i2 = i & 127;
        int i3 = mutableScatterMap._capacity;
        int i4 = (i >>> 7) & i3;
        int i5 = 0;
        loop0: while (true) {
            long[] jArr = mutableScatterMap.metadata;
            int i6 = i4 >> 3;
            int i7 = (i4 & 7) << 3;
            int i8 = i4;
            long j = (((-i7) >> 63) & (jArr[i6 + 1] << (64 - i7))) | (jArr[i6] >>> i7);
            long j2 = (((long) i2) * ScatterMapKt.BitmaskLsb) ^ j;
            long j3 = -9187201950435737472L;
            long j4 = (~j2) & (j2 - ScatterMapKt.BitmaskLsb) & (-9187201950435737472L);
            while (j4 != 0) {
                iNumberOfTrailingZeros = (i8 + (Long.numberOfTrailingZeros(j4) >> 3)) & i3;
                long j5 = j3;
                if (Intrinsics.areEqual(mutableScatterMap.keys[iNumberOfTrailingZeros], key)) {
                    break loop0;
                }
                j4 &= j4 - 1;
                j3 = j5;
            }
            if ((j & ((~j) << 6) & j3) != 0) {
                iNumberOfTrailingZeros = -1;
                break;
            }
            i5 += 8;
            i4 = (i8 + i5) & i3;
        }
        if (iNumberOfTrailingZeros < 0 || !Intrinsics.areEqual(this.parent.values[iNumberOfTrailingZeros], element.getValue())) {
            return false;
        }
        this.parent.removeValueAt(iNumberOfTrailingZeros);
        return true;
    }
}
