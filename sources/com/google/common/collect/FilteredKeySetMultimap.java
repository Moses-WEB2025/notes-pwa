package com.google.common.collect;

import com.google.common.base.Predicate;
import java.util.Map;
import java.util.Set;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
final class FilteredKeySetMultimap<K, V> extends FilteredKeyMultimap<K, V> implements FilteredSetMultimap<K, V> {
    FilteredKeySetMultimap(SetMultimap<K, V> unfiltered, Predicate<? super K> keyPredicate) {
        super(unfiltered, keyPredicate);
    }

    @Override // com.google.common.collect.FilteredKeyMultimap, com.google.common.collect.FilteredMultimap
    public SetMultimap<K, V> unfiltered() {
        return (SetMultimap) this.unfiltered;
    }

    @Override // com.google.common.collect.FilteredKeyMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public Set<V> get(@ParametricNullness K key) {
        return (Set) super.get((Object) key);
    }

    @Override // com.google.common.collect.FilteredKeyMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public Set<V> removeAll(@CheckForNull Object key) {
        return (Set) super.removeAll(key);
    }

    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public Set<V> replaceValues(@ParametricNullness K key, Iterable<? extends V> values) {
        return (Set) super.replaceValues((Object) key, (Iterable) values);
    }

    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    public Set<Map.Entry<K, V>> entries() {
        return (Set) super.entries();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.FilteredKeyMultimap, com.google.common.collect.AbstractMultimap
    public Set<Map.Entry<K, V>> createEntries() {
        return new EntrySet(this);
    }

    class EntrySet extends FilteredKeyMultimap<K, V>.Entries implements Set<Map.Entry<K, V>> {
        EntrySet(final FilteredKeySetMultimap this$0) {
            super();
        }

        @Override // java.util.Collection, java.util.Set
        public int hashCode() {
            return Sets.hashCodeImpl(this);
        }

        @Override // java.util.Collection, java.util.Set
        public boolean equals(@CheckForNull Object o) {
            return Sets.equalsImpl(this, o);
        }
    }
}
