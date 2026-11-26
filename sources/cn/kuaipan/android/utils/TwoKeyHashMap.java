package cn.kuaipan.android.utils;

import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class TwoKeyHashMap<E, K, V> extends AbstractMap<String, V> {
    static final int DEFAULT_INITIAL_SIZE = 16;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private Entry<E, K, V>[] arr;
    private int arrSize;
    private Set<Map.Entry<String, V>> entrySet;
    private float loadFactor;
    private int modCount;
    private int size;
    int threshold;
    private Collection<V> values;

    static /* synthetic */ int access$010(TwoKeyHashMap twoKeyHashMap) {
        int i = twoKeyHashMap.size;
        twoKeyHashMap.size = i - 1;
        return i;
    }

    static /* synthetic */ int access$308(TwoKeyHashMap twoKeyHashMap) {
        int i = twoKeyHashMap.modCount;
        twoKeyHashMap.modCount = i + 1;
        return i;
    }

    public TwoKeyHashMap() {
        this(16, 0.75f);
    }

    public TwoKeyHashMap(int i) {
        this(i, 0.75f);
    }

    public TwoKeyHashMap(int i, float f) {
        this.threshold = 0;
        if (i < 0) {
            throw new IllegalArgumentException("initialCapacity should be >= 0");
        }
        if (f <= 0.0f) {
            throw new IllegalArgumentException("initialLoadFactor should be > 0");
        }
        this.loadFactor = f;
        i = i == Integer.MAX_VALUE ? i - 1 : i;
        i = i <= 0 ? 1 : i;
        this.arrSize = i;
        this.threshold = (int) (i * f);
        this.arr = new Entry[i + 1];
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Collection<V> values() {
        if (this.values == null) {
            this.values = new ValuesCollectionImpl();
        }
        return this.values;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<String, V>> entrySet() {
        if (this.entrySet == null) {
            this.entrySet = new EntrySetImpl();
        }
        return this.entrySet;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        this.modCount++;
        this.size = 0;
        Entry<E, K, V>[] entryArr = this.arr;
        Arrays.fill(entryArr, 0, entryArr.length, (Object) null);
    }

    public V removeByKey(Object obj, Object obj2) {
        Entry<E, K, V> entryRemoveEntry = removeEntry(obj, obj2);
        if (entryRemoveEntry != null) {
            return entryRemoveEntry.value;
        }
        return null;
    }

    public V put(E e, K k, V v) {
        if (e == null && k == null) {
            int i = this.arrSize;
            Entry<E, K, V>[] entryArr = this.arr;
            Entry<E, K, V> entry = entryArr[i];
            if (entry == null) {
                entryArr[i] = createEntry(0, null, null, v, null);
                this.size++;
                this.modCount++;
                return null;
            }
            V v2 = entry.value;
            this.arr[i].value = v;
            return v2;
        }
        int iHashCode = e.hashCode() + k.hashCode();
        int i2 = (Integer.MAX_VALUE & iHashCode) % this.arrSize;
        for (Entry<E, K, V> entry2 = this.arr[i2]; entry2 != null; entry2 = entry2.next) {
            if (iHashCode == entry2.hash && e.equals(entry2.getKey1()) && k.equals(entry2.getKey2())) {
                V v3 = entry2.value;
                entry2.value = v;
                return v3;
            }
        }
        Entry<E, K, V>[] entryArr2 = this.arr;
        entryArr2[i2] = createEntry(iHashCode, e, k, v, entryArr2[i2]);
        int i3 = this.size + 1;
        this.size = i3;
        this.modCount++;
        if (i3 > this.threshold) {
            rehash();
        }
        return null;
    }

    void rehash() {
        Entry<E, K, V>[] entryArr;
        int i = ((this.arrSize + 1) * 2) + 1;
        if (i < 0) {
            i = 2147483646;
        }
        Entry<E, K, V>[] entryArr2 = new Entry[i + 1];
        int i2 = 0;
        while (true) {
            entryArr = this.arr;
            if (i2 >= entryArr.length - 1) {
                break;
            }
            Entry<E, K, V> entry = entryArr[i2];
            while (entry != null) {
                Entry<E, K, V> entry2 = entry.next;
                int i3 = (entry.hash & Integer.MAX_VALUE) % i;
                entry.next = entryArr2[i3];
                entryArr2[i3] = entry;
                entry = entry2;
            }
            i2++;
        }
        entryArr2[i] = entryArr[this.arrSize];
        this.arrSize = i;
        if (i == Integer.MAX_VALUE) {
            this.loadFactor *= 10.0f;
        }
        this.threshold = (int) (i * this.loadFactor);
        this.arr = entryArr2;
    }

    public boolean containsKey(Object obj, Object obj2) {
        return findEntry(obj, obj2) != null;
    }

    public V get(Object obj, Object obj2) {
        Entry<E, K, V> entryFindEntry = findEntry(obj, obj2);
        if (entryFindEntry != null) {
            return entryFindEntry.value;
        }
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return this.size;
    }

    Entry<E, K, V> createEntry(int i, E e, K k, V v, Entry<E, K, V> entry) {
        return new Entry<>(i, e, k, v, entry);
    }

    Iterator<Map.Entry<String, V>> createEntrySetIterator() {
        return new EntryIteratorImpl();
    }

    Iterator<V> createValueCollectionIterator() {
        return new ValueIteratorImpl();
    }

    public static class Entry<E, K, V> implements Map.Entry<String, V> {
        int hash;
        E key1;
        K key2;
        Entry<E, K, V> next;
        V value;

        public Entry(int i, E e, K k, V v, Entry<E, K, V> entry) {
            this.hash = i;
            this.key1 = e;
            this.key2 = k;
            this.value = v;
            this.next = entry;
        }

        @Override // java.util.Map.Entry
        public String getKey() {
            return this.key1.toString() + this.key2.toString();
        }

        public E getKey1() {
            return this.key1;
        }

        public K getKey2() {
            return this.key2;
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return this.value;
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            V v2 = this.value;
            this.value = v;
            return v2;
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            Object key1 = entry.getKey1();
            Object key2 = entry.getKey2();
            Object value = entry.getValue();
            E e = this.key1;
            return (e != null || key1 == null) && (this.key2 != null || key2 == null) && ((this.value != null || value == null) && e.equals(entry.getKey1()) && this.key2.equals(entry.getKey2()) && this.value.equals(value));
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            E e = this.key1;
            int iHashCode = e == null ? 0 : e.hashCode();
            K k = this.key2;
            int iHashCode2 = iHashCode + (k == null ? 0 : k.hashCode());
            V v = this.value;
            return iHashCode2 ^ (v != null ? v.hashCode() : 0);
        }
    }

    class EntrySetImpl extends AbstractSet<Map.Entry<String, V>> {
        EntrySetImpl() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return TwoKeyHashMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            TwoKeyHashMap.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return TwoKeyHashMap.this.size == 0;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            Entry entryFindEntry = TwoKeyHashMap.this.findEntry(entry.getKey1(), entry.getKey2());
            if (entryFindEntry == null) {
                return false;
            }
            Object value = entry.getValue();
            Object value2 = entryFindEntry.getValue();
            if (value == null) {
                return value2 == null;
            }
            return value.equals(value2);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            return TwoKeyHashMap.this.removeEntry(entry.getKey1(), entry.getKey2()) != null;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<String, V>> iterator() {
            return TwoKeyHashMap.this.createEntrySetIterator();
        }
    }

    class EntryIteratorImpl implements Iterator<Map.Entry<String, V>> {
        private Entry<E, K, V> curr_entry;
        private boolean found;
        private Entry<E, K, V> returned_entry;
        private int startModCount;
        private int curr = -1;
        private int returned_index = -1;

        EntryIteratorImpl() {
            this.startModCount = TwoKeyHashMap.this.modCount;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (this.found) {
                return true;
            }
            Entry<E, K, V> entry = this.curr_entry;
            if (entry != null) {
                this.curr_entry = entry.next;
            }
            if (this.curr_entry == null) {
                this.curr++;
                while (this.curr < TwoKeyHashMap.this.arr.length) {
                    Entry[] entryArr = TwoKeyHashMap.this.arr;
                    int i = this.curr;
                    if (entryArr[i] != null) {
                        break;
                    }
                    this.curr = i + 1;
                }
                if (this.curr < TwoKeyHashMap.this.arr.length) {
                    this.curr_entry = TwoKeyHashMap.this.arr[this.curr];
                }
            }
            boolean z = this.curr_entry != null;
            this.found = z;
            return z;
        }

        @Override // java.util.Iterator
        public Map.Entry<String, V> next() {
            if (TwoKeyHashMap.this.modCount != this.startModCount) {
                throw new ConcurrentModificationException();
            }
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.found = false;
            this.returned_index = this.curr;
            Entry<E, K, V> entry = this.curr_entry;
            this.returned_entry = entry;
            return entry;
        }

        @Override // java.util.Iterator
        public void remove() {
            Entry<E, K, V> entry;
            if (this.returned_index != -1) {
                if (TwoKeyHashMap.this.modCount == this.startModCount) {
                    Entry<E, K, V> entry2 = TwoKeyHashMap.this.arr[this.returned_index];
                    Entry<E, K, V> entry3 = null;
                    while (true) {
                        entry = this.returned_entry;
                        if (entry2 == entry) {
                            break;
                        }
                        entry3 = entry2;
                        entry2 = entry2.next;
                    }
                    if (entry3 == null) {
                        TwoKeyHashMap.this.arr[this.returned_index] = this.returned_entry.next;
                    } else {
                        entry3.next = entry.next;
                    }
                    TwoKeyHashMap.access$010(TwoKeyHashMap.this);
                    TwoKeyHashMap.access$308(TwoKeyHashMap.this);
                    this.startModCount++;
                    this.returned_index = -1;
                    return;
                }
                throw new ConcurrentModificationException();
            }
            throw new IllegalStateException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Entry<E, K, V> findEntry(Object obj, Object obj2) {
        if (obj == null && obj2 == null) {
            return this.arr[this.arrSize];
        }
        int iHashCode = obj.hashCode() + obj2.hashCode();
        for (Entry<E, K, V> entry = this.arr[(Integer.MAX_VALUE & iHashCode) % this.arrSize]; entry != null; entry = entry.next) {
            if (iHashCode == entry.hash && obj.equals(entry.getKey1()) && obj2.equals(entry.getKey2())) {
                return entry;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Entry<E, K, V> removeEntry(Object obj, Object obj2) {
        if (obj == null && obj2 == null) {
            int i = this.arrSize;
            Entry<E, K, V>[] entryArr = this.arr;
            Entry<E, K, V> entry = entryArr[i];
            if (entry == null) {
                return null;
            }
            entryArr[i] = null;
            this.size--;
            this.modCount++;
            return entry;
        }
        int iHashCode = obj.hashCode() + obj2.hashCode();
        int i2 = (Integer.MAX_VALUE & iHashCode) % this.arrSize;
        Entry<E, K, V> entry2 = this.arr[i2];
        Entry<E, K, V> entry3 = entry2;
        while (entry2 != null) {
            if (iHashCode == entry2.hash && obj.equals(entry2.getKey1()) && obj2.equals(entry2.getKey2())) {
                if (entry3 == entry2) {
                    this.arr[i2] = entry2.next;
                } else {
                    entry3.next = entry2.next;
                }
                this.size--;
                this.modCount++;
                return entry2;
            }
            entry3 = entry2;
            entry2 = entry2.next;
        }
        return null;
    }

    class ValuesCollectionImpl extends AbstractCollection<V> {
        ValuesCollectionImpl() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return TwoKeyHashMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            TwoKeyHashMap.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return TwoKeyHashMap.this.size == 0;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            return TwoKeyHashMap.this.createValueCollectionIterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(Object obj) {
            return TwoKeyHashMap.this.containsValue(obj);
        }
    }

    class ValueIteratorImpl implements Iterator<V> {
        private TwoKeyHashMap<E, K, V>.EntryIteratorImpl itr;

        ValueIteratorImpl() {
            this.itr = new EntryIteratorImpl();
        }

        @Override // java.util.Iterator
        public V next() {
            return this.itr.next().getValue();
        }

        @Override // java.util.Iterator
        public void remove() {
            this.itr.remove();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.itr.hasNext();
        }
    }
}
