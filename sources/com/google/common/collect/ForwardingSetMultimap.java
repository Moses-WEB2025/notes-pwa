package com.google.common.collect;

import java.util.Map;
import java.util.Set;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
public abstract class ForwardingSetMultimap<K, V> extends ForwardingMultimap<K, V> implements SetMultimap<K, V> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.common.collect.ForwardingMultimap, com.google.common.collect.ForwardingObject
    public abstract SetMultimap<K, V> delegate();

    @Override // com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap
    public Set<Map.Entry<K, V>> entries() {
        return delegate().entries();
    }

    @Override // com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public Set<V> get(@ParametricNullness K key) {
        return delegate().get((Object) key);
    }

    @Override // com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public Set<V> removeAll(@CheckForNull Object key) {
        return delegate().removeAll(key);
    }

    @Override // com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public Set<V> replaceValues(@ParametricNullness K key, Iterable<? extends V> values) {
        return delegate().replaceValues((Object) key, (Iterable) values);
    }
}
