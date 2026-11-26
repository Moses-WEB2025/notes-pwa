package androidx.collection;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMutableIterator;
import kotlin.jvm.internal.markers.KMutableSet;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: ScatterSet.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010#\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010)\n\u0002\b\u0004\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\u0002\u0010\u0006J\u0015\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\nJ\u0016\u0010\u000b\u001a\u00020\b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\rH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u000f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00000\u0011H\u0096\u0002J\u0015\u0010\u0012\u001a\u00020\b2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\nJ\u0016\u0010\u0013\u001a\u00020\b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\rH\u0016J\u0016\u0010\u0014\u001a\u00020\b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\rH\u0016R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Landroidx/collection/MutableSetWrapper;", ExifInterface.LONGITUDE_EAST, "Landroidx/collection/SetWrapper;", "", "parent", "Landroidx/collection/MutableScatterSet;", "(Landroidx/collection/MutableScatterSet;)V", "add", "", "element", "(Ljava/lang/Object;)Z", "addAll", "elements", "", "clear", "", "iterator", "", "remove", "removeAll", "retainAll", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class MutableSetWrapper<E> extends SetWrapper<E> implements Set<E>, KMutableSet {
    private final MutableScatterSet<E> parent;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MutableSetWrapper(MutableScatterSet<E> parent) {
        super(parent);
        Intrinsics.checkNotNullParameter(parent, "parent");
        this.parent = parent;
    }

    @Override // androidx.collection.SetWrapper, java.util.Set, java.util.Collection
    public boolean add(E element) {
        return this.parent.add(element);
    }

    @Override // androidx.collection.SetWrapper, java.util.Set, java.util.Collection
    public boolean addAll(Collection<? extends E> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        return this.parent.addAll(elements);
    }

    @Override // androidx.collection.SetWrapper, java.util.Set, java.util.Collection
    public void clear() {
        this.parent.clear();
    }

    /* JADX INFO: renamed from: androidx.collection.MutableSetWrapper$iterator$1, reason: invalid class name */
    /* JADX INFO: compiled from: ScatterSet.kt */
    @Metadata(d1 = {"\u0000)\n\u0000\n\u0002\u0010)\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010(\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\t\u0010\f\u001a\u00020\rH\u0096\u0002J\u000e\u0010\u000e\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u000fJ\b\u0010\u0010\u001a\u00020\u0011H\u0016R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0012"}, d2 = {"androidx/collection/MutableSetWrapper$iterator$1", "", "current", "", "getCurrent", "()I", "setCurrent", "(I)V", "iterator", "", "getIterator", "()Ljava/util/Iterator;", "hasNext", "", "next", "()Ljava/lang/Object;", "remove", "", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class AnonymousClass1 implements Iterator<E>, KMutableIterator {
        private int current = -1;
        private final Iterator<E> iterator;
        final /* synthetic */ MutableSetWrapper<E> this$0;

        AnonymousClass1(MutableSetWrapper<E> mutableSetWrapper) {
            this.this$0 = mutableSetWrapper;
            this.iterator = SequencesKt.iterator(new MutableSetWrapper$iterator$1$iterator$1(mutableSetWrapper, this, null));
        }

        public final int getCurrent() {
            return this.current;
        }

        public final void setCurrent(int i) {
            this.current = i;
        }

        public final Iterator<E> getIterator() {
            return this.iterator;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override // java.util.Iterator
        public E next() {
            return this.iterator.next();
        }

        @Override // java.util.Iterator
        public void remove() {
            if (this.current != -1) {
                ((MutableSetWrapper) this.this$0).parent.removeElementAt(this.current);
                this.current = -1;
            }
        }
    }

    @Override // androidx.collection.SetWrapper, java.util.Set, java.util.Collection, java.lang.Iterable
    public Iterator<E> iterator() {
        return new AnonymousClass1(this);
    }

    @Override // androidx.collection.SetWrapper, java.util.Set, java.util.Collection
    public boolean remove(Object element) {
        return this.parent.remove(element);
    }

    @Override // androidx.collection.SetWrapper, java.util.Set, java.util.Collection
    public boolean retainAll(Collection<? extends Object> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        return this.parent.retainAll(elements);
    }

    @Override // androidx.collection.SetWrapper, java.util.Set, java.util.Collection
    public boolean removeAll(Collection<? extends Object> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        return this.parent.removeAll(elements);
    }
}
