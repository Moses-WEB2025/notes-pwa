package com.google.common.collect;

import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
final class DescendingImmutableSortedSet<E> extends ImmutableSortedSet<E> {
    private final ImmutableSortedSet<E> forward;

    DescendingImmutableSortedSet(ImmutableSortedSet<E> forward) {
        super(Ordering.from(forward.comparator()).reverse());
        this.forward = forward;
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(@CheckForNull Object object) {
        return this.forward.contains(object);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this.forward.size();
    }

    @Override // com.google.common.collect.ImmutableSortedSet, com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set, java.util.NavigableSet, com.google.common.collect.SortedIterable
    public UnmodifiableIterator<E> iterator() {
        return this.forward.descendingIterator();
    }

    @Override // com.google.common.collect.ImmutableSortedSet
    ImmutableSortedSet<E> headSetImpl(E toElement, boolean inclusive) {
        return this.forward.tailSet((Object) toElement, inclusive).descendingSet();
    }

    @Override // com.google.common.collect.ImmutableSortedSet
    ImmutableSortedSet<E> subSetImpl(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        return this.forward.subSet((Object) toElement, toInclusive, (Object) fromElement, fromInclusive).descendingSet();
    }

    @Override // com.google.common.collect.ImmutableSortedSet
    ImmutableSortedSet<E> tailSetImpl(E fromElement, boolean inclusive) {
        return this.forward.headSet((Object) fromElement, inclusive).descendingSet();
    }

    @Override // com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    public ImmutableSortedSet<E> descendingSet() {
        return this.forward;
    }

    @Override // com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    public UnmodifiableIterator<E> descendingIterator() {
        return this.forward.iterator();
    }

    @Override // com.google.common.collect.ImmutableSortedSet
    ImmutableSortedSet<E> createDescendingSet() {
        throw new AssertionError("should never be called");
    }

    @Override // com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    @CheckForNull
    public E lower(E element) {
        return this.forward.higher(element);
    }

    @Override // com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    @CheckForNull
    public E floor(E element) {
        return this.forward.ceiling(element);
    }

    @Override // com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    @CheckForNull
    public E ceiling(E element) {
        return this.forward.floor(element);
    }

    @Override // com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    @CheckForNull
    public E higher(E element) {
        return this.forward.lower(element);
    }

    @Override // com.google.common.collect.ImmutableSortedSet
    int indexOf(@CheckForNull Object target) {
        int iIndexOf = this.forward.indexOf(target);
        return iIndexOf == -1 ? iIndexOf : (size() - 1) - iIndexOf;
    }

    @Override // com.google.common.collect.ImmutableCollection
    boolean isPartialView() {
        return this.forward.isPartialView();
    }
}
