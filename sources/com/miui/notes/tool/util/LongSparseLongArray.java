package com.miui.notes.tool.util;

import ch.qos.logback.core.CoreConstants;

/* JADX INFO: loaded from: classes4.dex */
public class LongSparseLongArray implements Cloneable {
    private long[] mKeys;
    private int mSize;
    private long[] mValues;

    public LongSparseLongArray() {
        this(10);
    }

    public LongSparseLongArray(int i) {
        if (i == 0) {
            this.mKeys = ContainerHelpers.EMPTY_LONGS;
            this.mValues = ContainerHelpers.EMPTY_LONGS;
        } else {
            int iIdealLongArraySize = ArrayUtils.idealLongArraySize(i);
            this.mKeys = new long[iIdealLongArraySize];
            this.mValues = new long[iIdealLongArraySize];
        }
        this.mSize = 0;
    }

    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public LongSparseLongArray m1744clone() {
        try {
            LongSparseLongArray longSparseLongArray = (LongSparseLongArray) super.clone();
            try {
                longSparseLongArray.mKeys = (long[]) this.mKeys.clone();
                longSparseLongArray.mValues = (long[]) this.mValues.clone();
                return longSparseLongArray;
            } catch (CloneNotSupportedException unused) {
                return longSparseLongArray;
            }
        } catch (CloneNotSupportedException unused2) {
            return null;
        }
    }

    public long get(long j) {
        return get(j, 0L);
    }

    public long get(long j, long j2) {
        int iBinarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, j);
        return iBinarySearch < 0 ? j2 : this.mValues[iBinarySearch];
    }

    public void delete(long j) {
        int iBinarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, j);
        if (iBinarySearch >= 0) {
            removeAt(iBinarySearch);
        }
    }

    public void removeAt(int i) {
        long[] jArr = this.mKeys;
        int i2 = i + 1;
        System.arraycopy(jArr, i2, jArr, i, this.mSize - i2);
        long[] jArr2 = this.mValues;
        System.arraycopy(jArr2, i2, jArr2, i, this.mSize - i2);
        this.mSize--;
    }

    public void put(long j, long j2) {
        int iBinarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, j);
        if (iBinarySearch >= 0) {
            this.mValues[iBinarySearch] = j2;
            return;
        }
        int i = ~iBinarySearch;
        int i2 = this.mSize;
        if (i2 >= this.mKeys.length) {
            growKeyAndValueArrays(i2 + 1);
        }
        int i3 = this.mSize;
        if (i3 - i != 0) {
            long[] jArr = this.mKeys;
            int i4 = i + 1;
            System.arraycopy(jArr, i, jArr, i4, i3 - i);
            long[] jArr2 = this.mValues;
            System.arraycopy(jArr2, i, jArr2, i4, this.mSize - i);
        }
        this.mKeys[i] = j;
        this.mValues[i] = j2;
        this.mSize++;
    }

    public int size() {
        return this.mSize;
    }

    public long keyAt(int i) {
        return this.mKeys[i];
    }

    public long valueAt(int i) {
        return this.mValues[i];
    }

    public int indexOfKey(long j) {
        return ContainerHelpers.binarySearch(this.mKeys, this.mSize, j);
    }

    public int indexOfValue(long j) {
        for (int i = 0; i < this.mSize; i++) {
            if (this.mValues[i] == j) {
                return i;
            }
        }
        return -1;
    }

    public void clear() {
        this.mSize = 0;
    }

    public void append(long j, long j2) {
        int i = this.mSize;
        if (i != 0 && j <= this.mKeys[i - 1]) {
            put(j, j2);
            return;
        }
        if (i >= this.mKeys.length) {
            growKeyAndValueArrays(i + 1);
        }
        this.mKeys[i] = j;
        this.mValues[i] = j2;
        this.mSize = i + 1;
    }

    private void growKeyAndValueArrays(int i) {
        int iIdealLongArraySize = ArrayUtils.idealLongArraySize(i);
        long[] jArr = new long[iIdealLongArraySize];
        long[] jArr2 = new long[iIdealLongArraySize];
        long[] jArr3 = this.mKeys;
        System.arraycopy(jArr3, 0, jArr, 0, jArr3.length);
        long[] jArr4 = this.mValues;
        System.arraycopy(jArr4, 0, jArr2, 0, jArr4.length);
        this.mKeys = jArr;
        this.mValues = jArr2;
    }

    public String toString() {
        if (size() <= 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.mSize * 28);
        sb.append(CoreConstants.CURLY_LEFT);
        for (int i = 0; i < this.mSize; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(keyAt(i));
            sb.append('=');
            sb.append(valueAt(i));
        }
        sb.append(CoreConstants.CURLY_RIGHT);
        return sb.toString();
    }
}
