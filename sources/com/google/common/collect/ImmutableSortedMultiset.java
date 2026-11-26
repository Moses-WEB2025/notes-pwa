package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
public abstract class ImmutableSortedMultiset<E> extends ImmutableSortedMultisetFauxverideShim<E> implements SortedMultiset<E> {

    @CheckForNull
    @LazyInit
    transient ImmutableSortedMultiset<E> descendingMultiset;

    @Override // com.google.common.collect.ImmutableMultiset, com.google.common.collect.Multiset
    public abstract ImmutableSortedSet<E> elementSet();

    public abstract ImmutableSortedMultiset<E> headMultiset(E upperBound, BoundType boundType);

    public abstract ImmutableSortedMultiset<E> tailMultiset(E lowerBound, BoundType boundType);

    public static <E> ImmutableSortedMultiset<E> of() {
        return (ImmutableSortedMultiset<E>) RegularImmutableSortedMultiset.NATURAL_EMPTY_MULTISET;
    }

    /* JADX WARN: Incorrect types in method signature: <E::Ljava/lang/Comparable<-TE;>;>(TE;)Lcom/google/common/collect/ImmutableSortedMultiset<TE;>; */
    public static ImmutableSortedMultiset of(Comparable element) {
        return new RegularImmutableSortedMultiset((RegularImmutableSortedSet) ImmutableSortedSet.of(element), new long[]{0, 1}, 0, 1);
    }

    /* JADX WARN: Incorrect types in method signature: <E::Ljava/lang/Comparable<-TE;>;>(TE;TE;)Lcom/google/common/collect/ImmutableSortedMultiset<TE;>; */
    public static ImmutableSortedMultiset of(Comparable e1, Comparable e2) {
        return copyOf(Ordering.natural(), Arrays.asList(e1, e2));
    }

    /* JADX WARN: Incorrect types in method signature: <E::Ljava/lang/Comparable<-TE;>;>(TE;TE;TE;)Lcom/google/common/collect/ImmutableSortedMultiset<TE;>; */
    public static ImmutableSortedMultiset of(Comparable e1, Comparable e2, Comparable e3) {
        return copyOf(Ordering.natural(), Arrays.asList(e1, e2, e3));
    }

    /* JADX WARN: Incorrect types in method signature: <E::Ljava/lang/Comparable<-TE;>;>(TE;TE;TE;TE;)Lcom/google/common/collect/ImmutableSortedMultiset<TE;>; */
    public static ImmutableSortedMultiset of(Comparable e1, Comparable e2, Comparable e3, Comparable e4) {
        return copyOf(Ordering.natural(), Arrays.asList(e1, e2, e3, e4));
    }

    /* JADX WARN: Incorrect types in method signature: <E::Ljava/lang/Comparable<-TE;>;>(TE;TE;TE;TE;TE;)Lcom/google/common/collect/ImmutableSortedMultiset<TE;>; */
    public static ImmutableSortedMultiset of(Comparable e1, Comparable e2, Comparable e3, Comparable e4, Comparable e5) {
        return copyOf(Ordering.natural(), Arrays.asList(e1, e2, e3, e4, e5));
    }

    /* JADX WARN: Incorrect types in method signature: <E::Ljava/lang/Comparable<-TE;>;>(TE;TE;TE;TE;TE;TE;[TE;)Lcom/google/common/collect/ImmutableSortedMultiset<TE;>; */
    public static ImmutableSortedMultiset of(Comparable e1, Comparable e2, Comparable e3, Comparable e4, Comparable e5, Comparable e6, Comparable... remaining) {
        ArrayList arrayListNewArrayListWithCapacity = Lists.newArrayListWithCapacity(remaining.length + 6);
        Collections.addAll(arrayListNewArrayListWithCapacity, e1, e2, e3, e4, e5, e6);
        Collections.addAll(arrayListNewArrayListWithCapacity, remaining);
        return copyOf(Ordering.natural(), arrayListNewArrayListWithCapacity);
    }

    /* JADX WARN: Incorrect types in method signature: <E::Ljava/lang/Comparable<-TE;>;>([TE;)Lcom/google/common/collect/ImmutableSortedMultiset<TE;>; */
    public static ImmutableSortedMultiset copyOf(Comparable[] elements) {
        return copyOf(Ordering.natural(), Arrays.asList(elements));
    }

    public static <E> ImmutableSortedMultiset<E> copyOf(Iterable<? extends E> elements) {
        return copyOf(Ordering.natural(), elements);
    }

    public static <E> ImmutableSortedMultiset<E> copyOf(Iterator<? extends E> elements) {
        return copyOf(Ordering.natural(), elements);
    }

    public static <E> ImmutableSortedMultiset<E> copyOf(Comparator<? super E> comparator, Iterator<? extends E> elements) {
        Preconditions.checkNotNull(comparator);
        return new Builder(comparator).addAll((Iterator) elements).build();
    }

    public static <E> ImmutableSortedMultiset<E> copyOf(Comparator<? super E> comparator, Iterable<? extends E> elements) {
        if (elements instanceof ImmutableSortedMultiset) {
            ImmutableSortedMultiset<E> immutableSortedMultiset = (ImmutableSortedMultiset) elements;
            if (comparator.equals(immutableSortedMultiset.comparator())) {
                return immutableSortedMultiset.isPartialView() ? copyOfSortedEntries(comparator, immutableSortedMultiset.entrySet().asList()) : immutableSortedMultiset;
            }
        }
        return new Builder(comparator).addAll((Iterable) elements).build();
    }

    public static <E> ImmutableSortedMultiset<E> copyOfSorted(SortedMultiset<E> sortedMultiset) {
        return copyOfSortedEntries(sortedMultiset.comparator(), Lists.newArrayList(sortedMultiset.entrySet()));
    }

    private static <E> ImmutableSortedMultiset<E> copyOfSortedEntries(Comparator<? super E> comparator, Collection<Multiset.Entry<E>> entries) {
        if (entries.isEmpty()) {
            return emptyMultiset(comparator);
        }
        ImmutableList.Builder builder = new ImmutableList.Builder(entries.size());
        long[] jArr = new long[entries.size() + 1];
        int i = 0;
        for (Multiset.Entry<E> entry : entries) {
            builder.add((Object) entry.getElement());
            int i2 = i + 1;
            jArr[i2] = jArr[i] + ((long) entry.getCount());
            i = i2;
        }
        return new RegularImmutableSortedMultiset(new RegularImmutableSortedSet(builder.build(), comparator), jArr, 0, entries.size());
    }

    static <E> ImmutableSortedMultiset<E> emptyMultiset(Comparator<? super E> comparator) {
        if (Ordering.natural().equals(comparator)) {
            return (ImmutableSortedMultiset<E>) RegularImmutableSortedMultiset.NATURAL_EMPTY_MULTISET;
        }
        return new RegularImmutableSortedMultiset(comparator);
    }

    ImmutableSortedMultiset() {
    }

    @Override // com.google.common.collect.SortedMultiset, com.google.common.collect.SortedIterable
    public final Comparator<? super E> comparator() {
        return elementSet().comparator();
    }

    @Override // com.google.common.collect.SortedMultiset
    public ImmutableSortedMultiset<E> descendingMultiset() {
        ImmutableSortedMultiset<E> descendingImmutableSortedMultiset = this.descendingMultiset;
        if (descendingImmutableSortedMultiset == null) {
            if (isEmpty()) {
                descendingImmutableSortedMultiset = emptyMultiset(Ordering.from(comparator()).reverse());
            } else {
                descendingImmutableSortedMultiset = new DescendingImmutableSortedMultiset<>(this);
            }
            this.descendingMultiset = descendingImmutableSortedMultiset;
        }
        return descendingImmutableSortedMultiset;
    }

    @Override // com.google.common.collect.SortedMultiset
    @CheckForNull
    @Deprecated
    public final Multiset.Entry<E> pollFirstEntry() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.SortedMultiset
    @CheckForNull
    @Deprecated
    public final Multiset.Entry<E> pollLastEntry() {
        throw new UnsupportedOperationException();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.SortedMultiset
    public ImmutableSortedMultiset<E> subMultiset(E lowerBound, BoundType lowerBoundType, E upperBound, BoundType upperBoundType) {
        Preconditions.checkArgument(comparator().compare(lowerBound, upperBound) <= 0, "Expected lowerBound <= upperBound but %s > %s", lowerBound, upperBound);
        return tailMultiset((Object) lowerBound, lowerBoundType).headMultiset((Object) upperBound, upperBoundType);
    }

    public static <E> Builder<E> orderedBy(Comparator<E> comparator) {
        return new Builder<>(comparator);
    }

    public static <E extends Comparable<?>> Builder<E> reverseOrder() {
        return new Builder<>(Ordering.natural().reverse());
    }

    public static <E extends Comparable<?>> Builder<E> naturalOrder() {
        return new Builder<>(Ordering.natural());
    }

    public static class Builder<E> extends ImmutableMultiset.Builder<E> {
        private final Comparator<? super E> comparator;
        private int[] counts;
        E[] elements;
        private boolean forceCopyElements;
        private int length;

        public Builder(Comparator<? super E> comparator) {
            super(true);
            this.comparator = (Comparator) Preconditions.checkNotNull(comparator);
            this.elements = (E[]) new Object[4];
            this.counts = new int[4];
        }

        private void maintenance() {
            int i = this.length;
            E[] eArr = this.elements;
            if (i == eArr.length) {
                dedupAndCoalesce(true);
            } else if (this.forceCopyElements) {
                this.elements = (E[]) Arrays.copyOf(eArr, eArr.length);
            }
            this.forceCopyElements = false;
        }

        private void dedupAndCoalesce(boolean z) {
            int i = this.length;
            if (i == 0) {
                return;
            }
            Object[] objArr = (E[]) Arrays.copyOf(this.elements, i);
            Arrays.sort(objArr, this.comparator);
            int i2 = 1;
            for (int i3 = 1; i3 < objArr.length; i3++) {
                if (this.comparator.compare((Object) objArr[i2 - 1], (Object) objArr[i3]) < 0) {
                    objArr[i2] = objArr[i3];
                    i2++;
                }
            }
            Arrays.fill(objArr, i2, this.length, (Object) null);
            if (z) {
                int i4 = i2 * 4;
                int i5 = this.length;
                if (i4 > i5 * 3) {
                    objArr = (E[]) Arrays.copyOf(objArr, IntMath.saturatedAdd(i5, (i5 / 2) + 1));
                }
            }
            int[] iArr = new int[objArr.length];
            for (int i6 = 0; i6 < this.length; i6++) {
                int iBinarySearch = Arrays.binarySearch(objArr, 0, i2, this.elements[i6], this.comparator);
                int i7 = this.counts[i6];
                if (i7 >= 0) {
                    iArr[iBinarySearch] = iArr[iBinarySearch] + i7;
                } else {
                    iArr[iBinarySearch] = ~i7;
                }
            }
            this.elements = (E[]) objArr;
            this.counts = iArr;
            this.length = i2;
        }

        @Override // com.google.common.collect.ImmutableMultiset.Builder, com.google.common.collect.ImmutableCollection.Builder
        public Builder<E> add(E element) {
            return addCopies((Object) element, 1);
        }

        @Override // com.google.common.collect.ImmutableMultiset.Builder, com.google.common.collect.ImmutableCollection.Builder
        public Builder<E> add(E... elements) {
            for (E e : elements) {
                add((Object) e);
            }
            return this;
        }

        @Override // com.google.common.collect.ImmutableMultiset.Builder
        public Builder<E> addCopies(E element, int occurrences) {
            Preconditions.checkNotNull(element);
            CollectPreconditions.checkNonnegative(occurrences, "occurrences");
            if (occurrences == 0) {
                return this;
            }
            maintenance();
            E[] eArr = this.elements;
            int i = this.length;
            eArr[i] = element;
            this.counts[i] = occurrences;
            this.length = i + 1;
            return this;
        }

        @Override // com.google.common.collect.ImmutableMultiset.Builder
        public Builder<E> setCount(E element, int count) {
            Preconditions.checkNotNull(element);
            CollectPreconditions.checkNonnegative(count, "count");
            maintenance();
            E[] eArr = this.elements;
            int i = this.length;
            eArr[i] = element;
            this.counts[i] = ~count;
            this.length = i + 1;
            return this;
        }

        @Override // com.google.common.collect.ImmutableMultiset.Builder, com.google.common.collect.ImmutableCollection.Builder
        public Builder<E> addAll(Iterable<? extends E> elements) {
            if (elements instanceof Multiset) {
                for (Multiset.Entry<E> entry : ((Multiset) elements).entrySet()) {
                    addCopies((Object) entry.getElement(), entry.getCount());
                }
            } else {
                Iterator<? extends E> it = elements.iterator();
                while (it.hasNext()) {
                    add((Object) it.next());
                }
            }
            return this;
        }

        @Override // com.google.common.collect.ImmutableMultiset.Builder, com.google.common.collect.ImmutableCollection.Builder
        public Builder<E> addAll(Iterator<? extends E> elements) {
            while (elements.hasNext()) {
                add((Object) elements.next());
            }
            return this;
        }

        private void dedupAndCoalesceAndDeleteEmpty() {
            dedupAndCoalesce(false);
            int i = 0;
            int i2 = 0;
            while (true) {
                int i3 = this.length;
                if (i < i3) {
                    int[] iArr = this.counts;
                    int i4 = iArr[i];
                    if (i4 > 0) {
                        E[] eArr = this.elements;
                        eArr[i2] = eArr[i];
                        iArr[i2] = i4;
                        i2++;
                    }
                    i++;
                } else {
                    Arrays.fill(this.elements, i2, i3, (Object) null);
                    Arrays.fill(this.counts, i2, this.length, 0);
                    this.length = i2;
                    return;
                }
            }
        }

        @Override // com.google.common.collect.ImmutableMultiset.Builder, com.google.common.collect.ImmutableCollection.Builder
        public ImmutableSortedMultiset<E> build() {
            dedupAndCoalesceAndDeleteEmpty();
            int i = this.length;
            if (i == 0) {
                return ImmutableSortedMultiset.emptyMultiset(this.comparator);
            }
            RegularImmutableSortedSet regularImmutableSortedSet = (RegularImmutableSortedSet) ImmutableSortedSet.construct(this.comparator, i, this.elements);
            long[] jArr = new long[this.length + 1];
            int i2 = 0;
            while (i2 < this.length) {
                int i3 = i2 + 1;
                jArr[i3] = jArr[i2] + ((long) this.counts[i2]);
                i2 = i3;
            }
            this.forceCopyElements = true;
            return new RegularImmutableSortedMultiset(regularImmutableSortedSet, jArr, 0, this.length);
        }
    }

    private static final class SerializedForm<E> implements Serializable {
        final Comparator<? super E> comparator;
        final int[] counts;
        final E[] elements;

        SerializedForm(SortedMultiset<E> sortedMultiset) {
            this.comparator = sortedMultiset.comparator();
            int size = sortedMultiset.entrySet().size();
            this.elements = (E[]) new Object[size];
            this.counts = new int[size];
            int i = 0;
            for (Multiset.Entry<E> entry : sortedMultiset.entrySet()) {
                this.elements[i] = entry.getElement();
                this.counts[i] = entry.getCount();
                i++;
            }
        }

        Object readResolve() {
            int length = this.elements.length;
            Builder builder = new Builder(this.comparator);
            for (int i = 0; i < length; i++) {
                builder.addCopies((Object) this.elements[i], this.counts[i]);
            }
            return builder.build();
        }
    }

    @Override // com.google.common.collect.ImmutableMultiset, com.google.common.collect.ImmutableCollection
    Object writeReplace() {
        return new SerializedForm(this);
    }

    private void readObject(ObjectInputStream stream) throws InvalidObjectException {
        throw new InvalidObjectException("Use SerializedForm");
    }
}
