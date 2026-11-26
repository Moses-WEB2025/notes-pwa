package androidx.collection;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMutableCollection;
import kotlin.jvm.internal.markers.KMutableIterator;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: ScatterMap.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001f\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010)\n\u0002\b\u0004\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\b\u0012\u0004\u0012\u0002H\u00020\u0003B\u0019\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0015\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u000eJ\u0016\u0010\u000f\u001a\u00020\f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00010\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\u0016\u0010\u0014\u001a\u00020\f2\u0006\u0010\r\u001a\u00028\u0001H\u0096\u0002¢\u0006\u0002\u0010\u000eJ\u0016\u0010\u0015\u001a\u00020\f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00010\u0011H\u0016J\b\u0010\u0016\u001a\u00020\fH\u0016J\u000f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00028\u00010\u0018H\u0096\u0002J\u0015\u0010\u0019\u001a\u00020\f2\u0006\u0010\r\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u000eJ\u0016\u0010\u001a\u001a\u00020\f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00010\u0011H\u0016J\u0016\u0010\u001b\u001a\u00020\f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00010\u0011H\u0016R\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u001c"}, d2 = {"Landroidx/collection/MutableValues;", "K", "V", "", "parent", "Landroidx/collection/MutableScatterMap;", "(Landroidx/collection/MutableScatterMap;)V", "size", "", "getSize", "()I", "add", "", "element", "(Ljava/lang/Object;)Z", "addAll", "elements", "", "clear", "", "contains", "containsAll", "isEmpty", "iterator", "", "remove", "removeAll", "retainAll", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class MutableValues<K, V> implements Collection<V>, KMutableCollection {
    private final MutableScatterMap<K, V> parent;

    @Override // java.util.Collection
    public Object[] toArray() {
        return CollectionToArray.toArray(this);
    }

    @Override // java.util.Collection
    public <T> T[] toArray(T[] array) {
        Intrinsics.checkNotNullParameter(array, "array");
        return (T[]) CollectionToArray.toArray(this, array);
    }

    public MutableValues(MutableScatterMap<K, V> parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        this.parent = parent;
    }

    @Override // java.util.Collection
    public final /* bridge */ int size() {
        return getSize();
    }

    public int getSize() {
        return this.parent._size;
    }

    @Override // java.util.Collection
    public boolean isEmpty() {
        return this.parent.isEmpty();
    }

    /* JADX INFO: renamed from: androidx.collection.MutableValues$iterator$1, reason: invalid class name */
    /* JADX INFO: compiled from: ScatterMap.kt */
    @Metadata(d1 = {"\u0000)\n\u0000\n\u0002\u0010)\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010(\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\t\u0010\f\u001a\u00020\rH\u0096\u0002J\u000e\u0010\u000e\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u000fJ\b\u0010\u0010\u001a\u00020\u0011H\u0016R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0012"}, d2 = {"androidx/collection/MutableValues$iterator$1", "", "current", "", "getCurrent", "()I", "setCurrent", "(I)V", "iterator", "", "getIterator", "()Ljava/util/Iterator;", "hasNext", "", "next", "()Ljava/lang/Object;", "remove", "", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class AnonymousClass1 implements Iterator<V>, KMutableIterator {
        private int current = -1;
        private final Iterator<Integer> iterator;
        final /* synthetic */ MutableValues<K, V> this$0;

        AnonymousClass1(MutableValues<K, V> mutableValues) {
            this.this$0 = mutableValues;
            this.iterator = SequencesKt.iterator(new MutableValues$iterator$1$iterator$1(mutableValues, null));
        }

        public final Iterator<Integer> getIterator() {
            return this.iterator;
        }

        public final int getCurrent() {
            return this.current;
        }

        public final void setCurrent(int i) {
            this.current = i;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override // java.util.Iterator
        public V next() {
            this.current = this.iterator.next().intValue();
            return (V) ((MutableValues) this.this$0).parent.values[this.current];
        }

        @Override // java.util.Iterator
        public void remove() {
            if (this.current >= 0) {
                ((MutableValues) this.this$0).parent.removeValueAt(this.current);
                this.current = -1;
            }
        }
    }

    @Override // java.util.Collection, java.lang.Iterable
    public Iterator<V> iterator() {
        return new AnonymousClass1(this);
    }

    @Override // java.util.Collection
    public void clear() {
        this.parent.clear();
    }

    @Override // java.util.Collection
    public boolean addAll(Collection<? extends V> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
    public boolean add(V element) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
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
                        if (!CollectionsKt.contains(elements, this.parent.values[i4])) {
                            this.parent.removeValueAt(i4);
                            z = true;
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

    @Override // java.util.Collection
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
                    if ((255 & j) < 128) {
                        int i4 = (i << 3) + i3;
                        if (CollectionsKt.contains(elements, this.parent.values[i4])) {
                            this.parent.removeValueAt(i4);
                            z = true;
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

    /* JADX WARN: Code duplicated, block: B:17:0x004e A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:18:0x0050 A[LOOP:0: B:5:0x000d->B:18:0x0050, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:21:0x0053 A[SYNTHETIC] */
    @Override // java.util.Collection
    public boolean remove(Object element) {
        long[] jArr = this.parent.metadata;
        int length = jArr.length - 2;
        if (length >= 0) {
            int i = 0;
            while (true) {
                long j = jArr[i];
                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i2 = 8 - ((~(i - length)) >>> 31);
                    for (int i3 = 0; i3 < i2; i3++) {
                        if ((255 & j) < 128) {
                            int i4 = (i << 3) + i3;
                            if (Intrinsics.areEqual(this.parent.values[i4], element)) {
                                this.parent.removeValueAt(i4);
                                return true;
                            }
                        }
                        j >>= 8;
                    }
                    if (i2 == 8) {
                        if (i != length) {
                            i++;
                        }
                    }
                } else if (i != length) {
                    i++;
                }
            }
        }
        return false;
    }

    @Override // java.util.Collection
    public boolean containsAll(Collection<? extends Object> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        Collection<? extends Object> collection = elements;
        if (collection.isEmpty()) {
            return true;
        }
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            if (!this.parent.containsValue((V) it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Collection
    public boolean contains(Object element) {
        return this.parent.containsValue(element);
    }
}
