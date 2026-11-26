package com.google.android.appfunctions.schema.internal.dependencies;

import com.google.android.appfunctions.schema.internal.dependencies.zzdx;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.UnaryOperator;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzdw<E, P extends zzdx> implements List<E>, KMappedMarker {
    private final List zza;

    public zzdw(List delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.zza = delegate;
    }

    @Override // java.util.List
    public final void add(int i, Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List, java.util.Collection
    public final boolean add(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List
    public final boolean addAll(int i, Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List, java.util.Collection
    public final boolean addAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List, java.util.Collection
    public final void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List, java.util.Collection
    public final boolean contains(Object obj) {
        return this.zza.contains(obj);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean containsAll(Collection elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        return this.zza.containsAll(elements);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean equals(Object obj) {
        return Intrinsics.areEqual(this.zza, obj);
    }

    @Override // java.util.List
    public final Object get(int i) {
        return this.zza.get(i);
    }

    @Override // java.util.List, java.util.Collection
    public final int hashCode() {
        return this.zza.hashCode();
    }

    @Override // java.util.List
    public final int indexOf(Object obj) {
        return this.zza.indexOf(obj);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean isEmpty() {
        return this.zza.isEmpty();
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        return new zzdy(this.zza.iterator());
    }

    @Override // java.util.List
    public final int lastIndexOf(Object obj) {
        return this.zza.lastIndexOf(obj);
    }

    @Override // java.util.List
    public final ListIterator listIterator() {
        return new zzdz(this.zza.listIterator());
    }

    @Override // java.util.List
    public final Object remove(int i) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List, java.util.Collection
    public final boolean remove(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List, java.util.Collection
    public final boolean removeAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List
    public final void replaceAll(UnaryOperator unaryOperator) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List, java.util.Collection
    public final boolean retainAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List
    public final Object set(int i, Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List, java.util.Collection
    public final /* bridge */ int size() {
        return this.zza.size();
    }

    @Override // java.util.List
    public final void sort(Comparator comparator) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List
    public final List subList(int i, int i2) {
        return this.zza.subList(i, i2);
    }

    @Override // java.util.List, java.util.Collection
    public final Object[] toArray() {
        return CollectionToArray.toArray(this);
    }

    @Override // java.util.List, java.util.Collection
    public final Object[] toArray(Object[] array) {
        Intrinsics.checkNotNullParameter(array, "array");
        return CollectionToArray.toArray(this, array);
    }

    public final String toString() {
        return this.zza.toString();
    }

    @Override // java.util.List
    public final ListIterator listIterator(int i) {
        return new zzdz(this.zza.listIterator(i));
    }
}
