package com.google.common.collect;

import com.google.errorprone.annotations.DoNotMock;
import java.lang.Comparable;
import java.util.Map;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@DoNotMock("Use ImmutableRangeMap or TreeRangeMap")
@ElementTypesAreNonnullByDefault
public interface RangeMap<K extends Comparable, V> {
    Map<Range<K>, V> asDescendingMapOfRanges();

    Map<Range<K>, V> asMapOfRanges();

    void clear();

    boolean equals(@CheckForNull Object o);

    @CheckForNull
    V get(K key);

    @CheckForNull
    Map.Entry<Range<K>, V> getEntry(K key);

    int hashCode();

    void put(Range<K> range, V value);

    void putAll(RangeMap<K, ? extends V> rangeMap);

    void putCoalescing(Range<K> range, V value);

    void remove(Range<K> range);

    Range<K> span();

    RangeMap<K, V> subRangeMap(Range<K> range);

    String toString();
}
