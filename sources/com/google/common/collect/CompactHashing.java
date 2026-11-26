package com.google.common.collect;

import com.google.common.base.Objects;
import java.util.Arrays;
import javax.annotation.CheckForNull;
import kotlin.UShort;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
final class CompactHashing {
    private static final int BYTE_MASK = 255;
    private static final int BYTE_MAX_SIZE = 256;
    static final int DEFAULT_SIZE = 3;
    static final int HASH_TABLE_BITS_MASK = 31;
    private static final int HASH_TABLE_BITS_MAX_BITS = 5;
    static final int MAX_SIZE = 1073741823;
    private static final int MIN_HASH_TABLE_SIZE = 4;
    static final int MODIFICATION_COUNT_INCREMENT = 32;
    private static final int SHORT_MASK = 65535;
    private static final int SHORT_MAX_SIZE = 65536;
    static final byte UNSET = 0;

    static int getHashPrefix(int value, int mask) {
        return value & (~mask);
    }

    static int getNext(int entry, int mask) {
        return entry & mask;
    }

    static int maskCombine(int prefix, int suffix, int mask) {
        return (prefix & (~mask)) | (suffix & mask);
    }

    static int newCapacity(int mask) {
        return (mask < 32 ? 4 : 2) * (mask + 1);
    }

    private CompactHashing() {
    }

    static int tableSize(int expectedSize) {
        return Math.max(4, Hashing.closedTableSize(expectedSize + 1, 1.0d));
    }

    static Object createTable(int buckets) {
        if (buckets < 2 || buckets > 1073741824 || Integer.highestOneBit(buckets) != buckets) {
            throw new IllegalArgumentException("must be power of 2 between 2^1 and 2^30: " + buckets);
        }
        if (buckets <= 256) {
            return new byte[buckets];
        }
        if (buckets <= 65536) {
            return new short[buckets];
        }
        return new int[buckets];
    }

    static void tableClear(Object table) {
        if (table instanceof byte[]) {
            Arrays.fill((byte[]) table, (byte) 0);
        } else if (table instanceof short[]) {
            Arrays.fill((short[]) table, (short) 0);
        } else {
            Arrays.fill((int[]) table, 0);
        }
    }

    static int tableGet(Object table, int index) {
        if (table instanceof byte[]) {
            return ((byte[]) table)[index] & 255;
        }
        if (table instanceof short[]) {
            return ((short[]) table)[index] & UShort.MAX_VALUE;
        }
        return ((int[]) table)[index];
    }

    static void tableSet(Object table, int index, int entry) {
        if (table instanceof byte[]) {
            ((byte[]) table)[index] = (byte) entry;
        } else if (table instanceof short[]) {
            ((short[]) table)[index] = (short) entry;
        } else {
            ((int[]) table)[index] = entry;
        }
    }

    static int remove(@CheckForNull Object key, @CheckForNull Object value, int mask, Object table, int[] entries, Object[] keys, @CheckForNull Object[] values) {
        int iSmearedHash = Hashing.smearedHash(key);
        int i = iSmearedHash & mask;
        int iTableGet = tableGet(table, i);
        if (iTableGet == 0) {
            return -1;
        }
        int hashPrefix = getHashPrefix(iSmearedHash, mask);
        int i2 = -1;
        while (true) {
            int i3 = iTableGet - 1;
            int i4 = entries[i3];
            if (getHashPrefix(i4, mask) == hashPrefix && Objects.equal(key, keys[i3]) && (values == null || Objects.equal(value, values[i3]))) {
                int next = getNext(i4, mask);
                if (i2 == -1) {
                    tableSet(table, i, next);
                    return i3;
                }
                entries[i2] = maskCombine(entries[i2], next, mask);
                return i3;
            }
            int next2 = getNext(i4, mask);
            if (next2 == 0) {
                return -1;
            }
            i2 = i3;
            iTableGet = next2;
        }
    }
}
