package com.google.common.collect;

import com.google.common.base.Predicate;
import java.util.List;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
final class FilteredKeyListMultimap<K, V> extends FilteredKeyMultimap<K, V> implements ListMultimap<K, V> {
    FilteredKeyListMultimap(ListMultimap<K, V> unfiltered, Predicate<? super K> keyPredicate) {
        super(unfiltered, keyPredicate);
    }

    @Override // com.google.common.collect.FilteredKeyMultimap, com.google.common.collect.FilteredMultimap
    public ListMultimap<K, V> unfiltered() {
        return (ListMultimap) super.unfiltered();
    }

    @Override // com.google.common.collect.FilteredKeyMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public List<V> get(@ParametricNullness K key) {
        return (List) super.get((Object) key);
    }

    @Override // com.google.common.collect.FilteredKeyMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public List<V> removeAll(@CheckForNull Object key) {
        return (List) super.removeAll(key);
    }

    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public List<V> replaceValues(@ParametricNullness K key, Iterable<? extends V> values) {
        return (List) super.replaceValues((Object) key, (Iterable) values);
    }
}
