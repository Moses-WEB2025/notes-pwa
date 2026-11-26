package com.google.common.collect;

import java.util.Collection;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
class EmptyImmutableSetMultimap extends ImmutableSetMultimap<Object, Object> {
    static final EmptyImmutableSetMultimap INSTANCE = new EmptyImmutableSetMultimap();
    private static final long serialVersionUID = 0;

    private EmptyImmutableSetMultimap() {
        super(ImmutableMap.of(), 0, null);
    }

    @Override // com.google.common.collect.ImmutableMultimap, com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public ImmutableMap<Object, Collection<Object>> asMap() {
        return super.asMap();
    }

    private Object readResolve() {
        return INSTANCE;
    }
}
