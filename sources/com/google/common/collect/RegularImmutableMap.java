package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import javax.annotation.CheckForNull;
import kotlin.UShort;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
final class RegularImmutableMap<K, V> extends ImmutableMap<K, V> {
    private static final byte ABSENT = -1;
    private static final int BYTE_MASK = 255;
    private static final int BYTE_MAX_SIZE = 128;
    static final ImmutableMap<Object, Object> EMPTY = new RegularImmutableMap(null, new Object[0], 0);
    private static final int SHORT_MASK = 65535;
    private static final int SHORT_MAX_SIZE = 32768;
    private static final long serialVersionUID = 0;
    final transient Object[] alternatingKeysAndValues;

    @CheckForNull
    private final transient Object hashTable;
    private final transient int size;

    @Override // com.google.common.collect.ImmutableMap
    boolean isPartialView() {
        return false;
    }

    static <K, V> RegularImmutableMap<K, V> create(int n, Object[] alternatingKeysAndValues) {
        return create(n, alternatingKeysAndValues, null);
    }

    static <K, V> RegularImmutableMap<K, V> create(int n, Object[] alternatingKeysAndValues, ImmutableMap.Builder<K, V> builder) {
        if (n == 0) {
            return (RegularImmutableMap) EMPTY;
        }
        if (n == 1) {
            CollectPreconditions.checkEntryNotNull(Objects.requireNonNull(alternatingKeysAndValues[0]), Objects.requireNonNull(alternatingKeysAndValues[1]));
            return new RegularImmutableMap<>(null, alternatingKeysAndValues, 1);
        }
        Preconditions.checkPositionIndex(n, alternatingKeysAndValues.length >> 1);
        Object objCreateHashTable = createHashTable(alternatingKeysAndValues, n, ImmutableSet.chooseTableSize(n), 0);
        if (objCreateHashTable instanceof Object[]) {
            Object[] objArr = (Object[]) objCreateHashTable;
            ImmutableMap.Builder.DuplicateKey duplicateKey = (ImmutableMap.Builder.DuplicateKey) objArr[2];
            if (builder == null) {
                throw duplicateKey.exception();
            }
            builder.duplicateKey = duplicateKey;
            Object obj = objArr[0];
            int iIntValue = ((Integer) objArr[1]).intValue();
            alternatingKeysAndValues = Arrays.copyOf(alternatingKeysAndValues, iIntValue * 2);
            objCreateHashTable = obj;
            n = iIntValue;
        }
        return new RegularImmutableMap<>(objCreateHashTable, alternatingKeysAndValues, n);
    }

    @CheckForNull
    private static Object createHashTable(Object[] alternatingKeysAndValues, int n, int tableSize, int keyOffset) {
        int i;
        ImmutableMap.Builder.DuplicateKey duplicateKey = null;
        int i2 = 1;
        if (n == 1) {
            CollectPreconditions.checkEntryNotNull(Objects.requireNonNull(alternatingKeysAndValues[keyOffset]), Objects.requireNonNull(alternatingKeysAndValues[keyOffset ^ 1]));
            return null;
        }
        int i3 = tableSize - 1;
        if (tableSize <= 128) {
            byte[] bArr = new byte[tableSize];
            Arrays.fill(bArr, (byte) -1);
            int i4 = 0;
            for (int i5 = 0; i5 < n; i5++) {
                int i6 = (i5 * 2) + keyOffset;
                int i7 = (i4 * 2) + keyOffset;
                Object objRequireNonNull = Objects.requireNonNull(alternatingKeysAndValues[i6]);
                Object objRequireNonNull2 = Objects.requireNonNull(alternatingKeysAndValues[i6 ^ 1]);
                CollectPreconditions.checkEntryNotNull(objRequireNonNull, objRequireNonNull2);
                int iSmear = Hashing.smear(objRequireNonNull.hashCode());
                while (true) {
                    int i8 = iSmear & i3;
                    int i9 = bArr[i8] & 255;
                    if (i9 == 255) {
                        bArr[i8] = (byte) i7;
                        if (i4 < i5) {
                            alternatingKeysAndValues[i7] = objRequireNonNull;
                            alternatingKeysAndValues[i7 ^ 1] = objRequireNonNull2;
                        }
                        i4++;
                        break;
                    }
                    if (objRequireNonNull.equals(alternatingKeysAndValues[i9])) {
                        int i10 = i9 ^ 1;
                        duplicateKey = new ImmutableMap.Builder.DuplicateKey(objRequireNonNull, objRequireNonNull2, Objects.requireNonNull(alternatingKeysAndValues[i10]));
                        alternatingKeysAndValues[i10] = objRequireNonNull2;
                        break;
                    }
                    iSmear = i8 + 1;
                }
            }
            return i4 == n ? bArr : new Object[]{bArr, Integer.valueOf(i4), duplicateKey};
        }
        if (tableSize <= 32768) {
            short[] sArr = new short[tableSize];
            Arrays.fill(sArr, (short) -1);
            int i11 = 0;
            for (int i12 = 0; i12 < n; i12++) {
                int i13 = (i12 * 2) + keyOffset;
                int i14 = (i11 * 2) + keyOffset;
                Object objRequireNonNull3 = Objects.requireNonNull(alternatingKeysAndValues[i13]);
                Object objRequireNonNull4 = Objects.requireNonNull(alternatingKeysAndValues[i13 ^ 1]);
                CollectPreconditions.checkEntryNotNull(objRequireNonNull3, objRequireNonNull4);
                int iSmear2 = Hashing.smear(objRequireNonNull3.hashCode());
                while (true) {
                    int i15 = iSmear2 & i3;
                    int i16 = sArr[i15] & UShort.MAX_VALUE;
                    if (i16 == 65535) {
                        sArr[i15] = (short) i14;
                        if (i11 < i12) {
                            alternatingKeysAndValues[i14] = objRequireNonNull3;
                            alternatingKeysAndValues[i14 ^ 1] = objRequireNonNull4;
                        }
                        i11++;
                        break;
                    }
                    if (objRequireNonNull3.equals(alternatingKeysAndValues[i16])) {
                        int i17 = i16 ^ 1;
                        duplicateKey = new ImmutableMap.Builder.DuplicateKey(objRequireNonNull3, objRequireNonNull4, Objects.requireNonNull(alternatingKeysAndValues[i17]));
                        alternatingKeysAndValues[i17] = objRequireNonNull4;
                        break;
                    }
                    iSmear2 = i15 + 1;
                }
            }
            return i11 == n ? sArr : new Object[]{sArr, Integer.valueOf(i11), duplicateKey};
        }
        int[] iArr = new int[tableSize];
        Arrays.fill(iArr, -1);
        int i18 = 0;
        int i19 = 0;
        while (i18 < n) {
            int i20 = (i18 * 2) + keyOffset;
            int i21 = (i19 * 2) + keyOffset;
            Object objRequireNonNull5 = Objects.requireNonNull(alternatingKeysAndValues[i20]);
            Object objRequireNonNull6 = Objects.requireNonNull(alternatingKeysAndValues[i20 ^ i2]);
            CollectPreconditions.checkEntryNotNull(objRequireNonNull5, objRequireNonNull6);
            int iSmear3 = Hashing.smear(objRequireNonNull5.hashCode());
            while (true) {
                int i22 = iSmear3 & i3;
                int i23 = iArr[i22];
                if (i23 == -1) {
                    iArr[i22] = i21;
                    if (i19 < i18) {
                        alternatingKeysAndValues[i21] = objRequireNonNull5;
                        alternatingKeysAndValues[i21 ^ 1] = objRequireNonNull6;
                    }
                    i19++;
                    i = i2;
                    break;
                }
                i = i2;
                if (objRequireNonNull5.equals(alternatingKeysAndValues[i23])) {
                    int i24 = i23 ^ 1;
                    duplicateKey = new ImmutableMap.Builder.DuplicateKey(objRequireNonNull5, objRequireNonNull6, Objects.requireNonNull(alternatingKeysAndValues[i24]));
                    alternatingKeysAndValues[i24] = objRequireNonNull6;
                    break;
                }
                iSmear3 = i22 + 1;
                i2 = i;
            }
            i18++;
            i2 = i;
        }
        int i25 = i2;
        if (i19 == n) {
            return iArr;
        }
        Object[] objArr = new Object[3];
        objArr[0] = iArr;
        objArr[i25] = Integer.valueOf(i19);
        objArr[2] = duplicateKey;
        return objArr;
    }

    @CheckForNull
    static Object createHashTableOrThrow(Object[] alternatingKeysAndValues, int n, int tableSize, int keyOffset) {
        Object objCreateHashTable = createHashTable(alternatingKeysAndValues, n, tableSize, keyOffset);
        if (objCreateHashTable instanceof Object[]) {
            throw ((ImmutableMap.Builder.DuplicateKey) ((Object[]) objCreateHashTable)[2]).exception();
        }
        return objCreateHashTable;
    }

    private RegularImmutableMap(@CheckForNull Object hashTable, Object[] alternatingKeysAndValues, int size) {
        this.hashTable = hashTable;
        this.alternatingKeysAndValues = alternatingKeysAndValues;
        this.size = size;
    }

    @Override // java.util.Map
    public int size() {
        return this.size;
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map
    @CheckForNull
    public V get(@CheckForNull Object obj) {
        V v = (V) get(this.hashTable, this.alternatingKeysAndValues, this.size, 0, obj);
        if (v == null) {
            return null;
        }
        return v;
    }

    @CheckForNull
    static Object get(@CheckForNull Object hashTableObject, Object[] alternatingKeysAndValues, int size, int keyOffset, @CheckForNull Object key) {
        if (key == null) {
            return null;
        }
        if (size == 1) {
            if (Objects.requireNonNull(alternatingKeysAndValues[keyOffset]).equals(key)) {
                return Objects.requireNonNull(alternatingKeysAndValues[keyOffset ^ 1]);
            }
            return null;
        }
        if (hashTableObject == null) {
            return null;
        }
        if (hashTableObject instanceof byte[]) {
            byte[] bArr = (byte[]) hashTableObject;
            int length = bArr.length - 1;
            int iSmear = Hashing.smear(key.hashCode());
            while (true) {
                int i = iSmear & length;
                int i2 = bArr[i] & 255;
                if (i2 == 255) {
                    return null;
                }
                if (key.equals(alternatingKeysAndValues[i2])) {
                    return alternatingKeysAndValues[i2 ^ 1];
                }
                iSmear = i + 1;
            }
        } else if (hashTableObject instanceof short[]) {
            short[] sArr = (short[]) hashTableObject;
            int length2 = sArr.length - 1;
            int iSmear2 = Hashing.smear(key.hashCode());
            while (true) {
                int i3 = iSmear2 & length2;
                int i4 = sArr[i3] & UShort.MAX_VALUE;
                if (i4 == 65535) {
                    return null;
                }
                if (key.equals(alternatingKeysAndValues[i4])) {
                    return alternatingKeysAndValues[i4 ^ 1];
                }
                iSmear2 = i3 + 1;
            }
        } else {
            int[] iArr = (int[]) hashTableObject;
            int length3 = iArr.length - 1;
            int iSmear3 = Hashing.smear(key.hashCode());
            while (true) {
                int i5 = iSmear3 & length3;
                int i6 = iArr[i5];
                if (i6 == -1) {
                    return null;
                }
                if (key.equals(alternatingKeysAndValues[i6])) {
                    return alternatingKeysAndValues[i6 ^ 1];
                }
                iSmear3 = i5 + 1;
            }
        }
    }

    @Override // com.google.common.collect.ImmutableMap
    ImmutableSet<Map.Entry<K, V>> createEntrySet() {
        return new EntrySet(this, this.alternatingKeysAndValues, 0, this.size);
    }

    static class EntrySet<K, V> extends ImmutableSet<Map.Entry<K, V>> {
        private final transient Object[] alternatingKeysAndValues;
        private final transient int keyOffset;
        private final transient ImmutableMap<K, V> map;
        private final transient int size;

        @Override // com.google.common.collect.ImmutableCollection
        boolean isPartialView() {
            return true;
        }

        EntrySet(ImmutableMap<K, V> map, Object[] alternatingKeysAndValues, int keyOffset, int size) {
            this.map = map;
            this.alternatingKeysAndValues = alternatingKeysAndValues;
            this.keyOffset = keyOffset;
            this.size = size;
        }

        @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set, java.util.NavigableSet, com.google.common.collect.SortedIterable
        public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
            return asList().iterator();
        }

        @Override // com.google.common.collect.ImmutableCollection
        int copyIntoArray(Object[] dst, int offset) {
            return asList().copyIntoArray(dst, offset);
        }

        @Override // com.google.common.collect.ImmutableSet
        ImmutableList<Map.Entry<K, V>> createAsList() {
            return new ImmutableList<Map.Entry<K, V>>() { // from class: com.google.common.collect.RegularImmutableMap.EntrySet.1
                @Override // com.google.common.collect.ImmutableCollection
                public boolean isPartialView() {
                    return true;
                }

                @Override // java.util.List
                public Map.Entry<K, V> get(int index) {
                    Preconditions.checkElementIndex(index, EntrySet.this.size);
                    int i = index * 2;
                    return new AbstractMap.SimpleImmutableEntry(Objects.requireNonNull(EntrySet.this.alternatingKeysAndValues[EntrySet.this.keyOffset + i]), Objects.requireNonNull(EntrySet.this.alternatingKeysAndValues[i + (EntrySet.this.keyOffset ^ 1)]));
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                public int size() {
                    return EntrySet.this.size;
                }
            };
        }

        @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@CheckForNull Object object) {
            if (object instanceof Map.Entry) {
                Map.Entry entry = (Map.Entry) object;
                Object key = entry.getKey();
                Object value = entry.getValue();
                if (value != null && value.equals(this.map.get(key))) {
                    return true;
                }
            }
            return false;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return this.size;
        }
    }

    @Override // com.google.common.collect.ImmutableMap
    ImmutableSet<K> createKeySet() {
        return new KeySet(this, new KeysOrValuesAsList(this.alternatingKeysAndValues, 0, this.size));
    }

    static final class KeysOrValuesAsList extends ImmutableList<Object> {
        private final transient Object[] alternatingKeysAndValues;
        private final transient int offset;
        private final transient int size;

        @Override // com.google.common.collect.ImmutableCollection
        boolean isPartialView() {
            return true;
        }

        KeysOrValuesAsList(Object[] alternatingKeysAndValues, int offset, int size) {
            this.alternatingKeysAndValues = alternatingKeysAndValues;
            this.offset = offset;
            this.size = size;
        }

        @Override // java.util.List
        public Object get(int index) {
            Preconditions.checkElementIndex(index, this.size);
            return Objects.requireNonNull(this.alternatingKeysAndValues[(index * 2) + this.offset]);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.size;
        }
    }

    static final class KeySet<K> extends ImmutableSet<K> {
        private final transient ImmutableList<K> list;
        private final transient ImmutableMap<K, ?> map;

        @Override // com.google.common.collect.ImmutableCollection
        boolean isPartialView() {
            return true;
        }

        KeySet(ImmutableMap<K, ?> map, ImmutableList<K> list) {
            this.map = map;
            this.list = list;
        }

        @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set, java.util.NavigableSet, com.google.common.collect.SortedIterable
        public UnmodifiableIterator<K> iterator() {
            return asList().iterator();
        }

        @Override // com.google.common.collect.ImmutableCollection
        int copyIntoArray(Object[] dst, int offset) {
            return asList().copyIntoArray(dst, offset);
        }

        @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection
        public ImmutableList<K> asList() {
            return this.list;
        }

        @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@CheckForNull Object object) {
            return this.map.get(object) != null;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return this.map.size();
        }
    }

    @Override // com.google.common.collect.ImmutableMap
    ImmutableCollection<V> createValues() {
        return new KeysOrValuesAsList(this.alternatingKeysAndValues, 1, this.size);
    }
}
