package androidx.collection;

import androidx.collection.internal.RuntimeHelpersKt;
import com.miui.notes.preference.PreferenceProviderHelper;
import kotlin.Metadata;
import kotlin.ULong;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: IntObjectMap.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0015\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u000f\u0012\b\b\u0002\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\r\u0010\u0007\u001a\u00020\bH\u0000¢\u0006\u0002\b\tJ\u0006\u0010\n\u001a\u00020\bJ\r\u0010\u000b\u001a\u00020\bH\u0000¢\u0006\u0002\b\fJ\u0010\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u0004H\u0002J\u0010\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0004H\u0002J'\u0010\u0011\u001a\u00028\u00002\u0006\u0010\u000e\u001a\u00020\u00042\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00000\u0013H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u0014J\b\u0010\u0015\u001a\u00020\bH\u0002J\u0010\u0010\u0016\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\u0004H\u0002J\u0010\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u0004H\u0002J\u0011\u0010\u0019\u001a\u00020\b2\u0006\u0010\u001a\u001a\u00020\u001bH\u0086\nJ\u0011\u0010\u0019\u001a\u00020\b2\u0006\u0010\u001a\u001a\u00020\u001cH\u0086\nJ\u0011\u0010\u0019\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u0004H\u0086\nJ\u0011\u0010\u0019\u001a\u00020\b2\u0006\u0010\u001a\u001a\u00020\u001dH\u0086\nJ\u0017\u0010\u001e\u001a\u00020\b2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002H\u0086\nJ\u001d\u0010 \u001a\u0004\u0018\u00018\u00002\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010!\u001a\u00028\u0000¢\u0006\u0002\u0010\"J\u0014\u0010#\u001a\u00020\b2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002J\u0015\u0010$\u001a\u0004\u0018\u00018\u00002\u0006\u0010\u000e\u001a\u00020\u0004¢\u0006\u0002\u0010%J\u001b\u0010$\u001a\u00020&2\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010!\u001a\u00028\u0000¢\u0006\u0002\u0010'J&\u0010(\u001a\u00020\b2\u0018\u0010)\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020&0*H\u0086\bø\u0001\u0000J\u0017\u0010+\u001a\u0004\u0018\u00018\u00002\u0006\u0010,\u001a\u00020\u0004H\u0001¢\u0006\u0002\u0010%J\u0015\u0010-\u001a\u00020\b2\u0006\u0010.\u001a\u00020\u0004H\u0000¢\u0006\u0002\b/J\u001e\u00100\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010!\u001a\u00028\u0000H\u0086\u0002¢\u0006\u0002\u00101J\u0006\u00102\u001a\u00020\u0004R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u00063"}, d2 = {"Landroidx/collection/MutableIntObjectMap;", "V", "Landroidx/collection/IntObjectMap;", "initialCapacity", "", "(I)V", "growthLimit", "adjustStorage", "", "adjustStorage$collection", "clear", "dropDeletes", "dropDeletes$collection", "findAbsoluteInsertIndex", "key", "findFirstAvailableSlot", "hash1", "getOrPut", "defaultValue", "Lkotlin/Function0;", "(ILkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "initializeGrowth", "initializeMetadata", "capacity", "initializeStorage", "minusAssign", PreferenceProviderHelper.URI_PARAM_KEYS, "Landroidx/collection/IntList;", "Landroidx/collection/IntSet;", "", "plusAssign", "from", "put", "value", "(ILjava/lang/Object;)Ljava/lang/Object;", "putAll", "remove", "(I)Ljava/lang/Object;", "", "(ILjava/lang/Object;)Z", "removeIf", "predicate", "Lkotlin/Function2;", "removeValueAt", "index", "resizeStorage", "newCapacity", "resizeStorage$collection", "set", "(ILjava/lang/Object;)V", "trim", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MutableIntObjectMap<V> extends IntObjectMap<V> {
    private int growthLimit;

    public MutableIntObjectMap() {
        this(0, 1, null);
    }

    public /* synthetic */ MutableIntObjectMap(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 6 : i);
    }

    public MutableIntObjectMap(int i) {
        super(null);
        if (!(i >= 0)) {
            RuntimeHelpersKt.throwIllegalArgumentException("Capacity must be a positive value.");
        }
        initializeStorage(ScatterMapKt.unloadedCapacity(i));
    }

    private final void initializeStorage(int initialCapacity) {
        int iMax = initialCapacity > 0 ? Math.max(7, ScatterMapKt.normalizeCapacity(initialCapacity)) : 0;
        this._capacity = iMax;
        initializeMetadata(iMax);
        this.keys = new int[iMax];
        this.values = new Object[iMax];
    }

    private final void initializeMetadata(int capacity) {
        long[] jArr;
        if (capacity == 0) {
            jArr = ScatterMapKt.EmptyGroup;
        } else {
            long[] jArr2 = new long[((capacity + 15) & (-8)) >> 3];
            ArraysKt.fill$default(jArr2, -9187201950435737472L, 0, 0, 6, (Object) null);
            jArr = jArr2;
        }
        this.metadata = jArr;
        long[] jArr3 = this.metadata;
        int i = capacity >> 3;
        long j = 255 << ((capacity & 7) << 3);
        jArr3[i] = (jArr3[i] & (~j)) | j;
        initializeGrowth();
    }

    private final void initializeGrowth() {
        this.growthLimit = ScatterMapKt.loadedCapacity(get_capacity()) - this._size;
    }

    public final V getOrPut(int key, Function0<? extends V> defaultValue) {
        Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
        V v = get(key);
        if (v != null) {
            return v;
        }
        V vInvoke = defaultValue.invoke();
        set(key, vInvoke);
        return vInvoke;
    }

    public final void set(int key, V value) {
        int iFindAbsoluteInsertIndex = findAbsoluteInsertIndex(key);
        this.keys[iFindAbsoluteInsertIndex] = key;
        this.values[iFindAbsoluteInsertIndex] = value;
    }

    public final V put(int key, V value) {
        int iFindAbsoluteInsertIndex = findAbsoluteInsertIndex(key);
        V v = (V) this.values[iFindAbsoluteInsertIndex];
        this.keys[iFindAbsoluteInsertIndex] = key;
        this.values[iFindAbsoluteInsertIndex] = value;
        return v;
    }

    public final void plusAssign(IntObjectMap<V> from) {
        Intrinsics.checkNotNullParameter(from, "from");
        putAll(from);
    }

    public final V remove(int key) {
        int iNumberOfTrailingZeros;
        MutableIntObjectMap<V> mutableIntObjectMap = this;
        int iHashCode = Integer.hashCode(key) * ScatterMapKt.MurmurHashC1;
        int i = iHashCode ^ (iHashCode << 16);
        int i2 = i & 127;
        int i3 = mutableIntObjectMap._capacity;
        int i4 = (i >>> 7) & i3;
        int i5 = 0;
        loop0: while (true) {
            long[] jArr = mutableIntObjectMap.metadata;
            int i6 = i4 >> 3;
            int i7 = (i4 & 7) << 3;
            long j = ((jArr[i6 + 1] << (64 - i7)) & ((-i7) >> 63)) | (jArr[i6] >>> i7);
            long j2 = (((long) i2) * ScatterMapKt.BitmaskLsb) ^ j;
            for (long j3 = (~j2) & (j2 - ScatterMapKt.BitmaskLsb) & (-9187201950435737472L); j3 != 0; j3 &= j3 - 1) {
                iNumberOfTrailingZeros = ((Long.numberOfTrailingZeros(j3) >> 3) + i4) & i3;
                if (mutableIntObjectMap.keys[iNumberOfTrailingZeros] == key) {
                    break loop0;
                }
            }
            if ((j & ((~j) << 6) & (-9187201950435737472L)) != 0) {
                iNumberOfTrailingZeros = -1;
                break;
            }
            i5 += 8;
            i4 = (i4 + i5) & i3;
        }
        if (iNumberOfTrailingZeros >= 0) {
            return removeValueAt(iNumberOfTrailingZeros);
        }
        return null;
    }

    public final boolean remove(int key, V value) {
        boolean z;
        int iNumberOfTrailingZeros;
        MutableIntObjectMap<V> mutableIntObjectMap = this;
        int iHashCode = Integer.hashCode(key) * ScatterMapKt.MurmurHashC1;
        int i = iHashCode ^ (iHashCode << 16);
        int i2 = i & 127;
        int i3 = mutableIntObjectMap._capacity;
        int i4 = (i >>> 7) & i3;
        int i5 = 0;
        loop0: while (true) {
            long[] jArr = mutableIntObjectMap.metadata;
            int i6 = i4 >> 3;
            int i7 = (i4 & 7) << 3;
            long j = ((jArr[i6 + 1] << (64 - i7)) & ((-i7) >> 63)) | (jArr[i6] >>> i7);
            long j2 = (((long) i2) * ScatterMapKt.BitmaskLsb) ^ j;
            for (long j3 = (~j2) & (j2 - ScatterMapKt.BitmaskLsb) & (-9187201950435737472L); j3 != 0; j3 &= j3 - 1) {
                iNumberOfTrailingZeros = ((Long.numberOfTrailingZeros(j3) >> 3) + i4) & i3;
                z = false;
                if (mutableIntObjectMap.keys[iNumberOfTrailingZeros] == key) {
                    break loop0;
                }
            }
            z = false;
            if ((j & ((~j) << 6) & (-9187201950435737472L)) != 0) {
                iNumberOfTrailingZeros = -1;
                break;
            }
            i5 += 8;
            i4 = (i4 + i5) & i3;
        }
        if (iNumberOfTrailingZeros < 0 || !Intrinsics.areEqual(this.values[iNumberOfTrailingZeros], value)) {
            return z;
        }
        removeValueAt(iNumberOfTrailingZeros);
        return true;
    }

    public final void removeIf(Function2<? super Integer, ? super V, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        long[] jArr = this.metadata;
        int length = jArr.length - 2;
        if (length < 0) {
            return;
        }
        int i = 0;
        while (true) {
            long j = jArr[i];
            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                int i2 = 8 - ((~(i - length)) >>> 31);
                for (int i3 = 0; i3 < i2; i3++) {
                    if ((255 & j) < 128) {
                        int i4 = (i << 3) + i3;
                        if (predicate.invoke(Integer.valueOf(this.keys[i4]), this.values[i4]).booleanValue()) {
                            removeValueAt(i4);
                        }
                    }
                    j >>= 8;
                }
                if (i2 != 8) {
                    return;
                }
            }
            if (i == length) {
                return;
            } else {
                i++;
            }
        }
    }

    public final void minusAssign(int key) {
        remove(key);
    }

    public final void minusAssign(int[] keys) {
        Intrinsics.checkNotNullParameter(keys, "keys");
        for (int i : keys) {
            remove(i);
        }
    }

    public final V removeValueAt(int index) {
        this._size--;
        long[] jArr = this.metadata;
        int i = this._capacity;
        int i2 = index >> 3;
        int i3 = (index & 7) << 3;
        long j = (jArr[i2] & (~(255 << i3))) | (254 << i3);
        jArr[i2] = j;
        jArr[(((index - 7) & i) + (i & 7)) >> 3] = j;
        V v = (V) this.values[index];
        this.values[index] = null;
        return v;
    }

    public final void clear() {
        this._size = 0;
        if (this.metadata != ScatterMapKt.EmptyGroup) {
            ArraysKt.fill$default(this.metadata, -9187201950435737472L, 0, 0, 6, (Object) null);
            long[] jArr = this.metadata;
            int i = this._capacity;
            int i2 = i >> 3;
            long j = 255 << ((i & 7) << 3);
            jArr[i2] = (jArr[i2] & (~j)) | j;
        }
        ArraysKt.fill(this.values, (Object) null, 0, this._capacity);
        initializeGrowth();
    }

    private final int findFirstAvailableSlot(int hash1) {
        int i = this._capacity;
        int i2 = hash1 & i;
        int i3 = 0;
        while (true) {
            long[] jArr = this.metadata;
            int i4 = i2 >> 3;
            int i5 = (i2 & 7) << 3;
            long j = ((jArr[i4 + 1] << (64 - i5)) & ((-i5) >> 63)) | (jArr[i4] >>> i5);
            long j2 = j & ((~j) << 7) & (-9187201950435737472L);
            if (j2 != 0) {
                return (i2 + (Long.numberOfTrailingZeros(j2) >> 3)) & i;
            }
            i3 += 8;
            i2 = (i2 + i3) & i;
        }
    }

    public final int trim() {
        int i = this._capacity;
        int iNormalizeCapacity = ScatterMapKt.normalizeCapacity(ScatterMapKt.unloadedCapacity(this._size));
        if (iNormalizeCapacity >= i) {
            return 0;
        }
        resizeStorage$collection(iNormalizeCapacity);
        return i - this._capacity;
    }

    public final void adjustStorage$collection() {
        if (this._capacity > 8 && Long.compareUnsigned(ULong.m2706constructorimpl(ULong.m2706constructorimpl(this._size) * 32), ULong.m2706constructorimpl(ULong.m2706constructorimpl(this._capacity) * 25)) <= 0) {
            dropDeletes$collection();
        } else {
            resizeStorage$collection(ScatterMapKt.nextCapacity(this._capacity));
        }
    }

    public final void dropDeletes$collection() {
        long[] jArr = this.metadata;
        int i = this._capacity;
        int[] iArr = this.keys;
        Object[] objArr = this.values;
        int i2 = (i + 7) >> 3;
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            long j = jArr[i4] & (-9187201950435737472L);
            jArr[i4] = (-72340172838076674L) & ((~j) + (j >>> 7));
        }
        int lastIndex = ArraysKt.getLastIndex(jArr);
        int i5 = lastIndex - 1;
        long j2 = 72057594037927935L;
        jArr[i5] = (jArr[i5] & 72057594037927935L) | (-72057594037927936L);
        jArr[lastIndex] = jArr[0];
        int i6 = 0;
        while (i6 != i) {
            int i7 = i6 >> 3;
            int i8 = (i6 & 7) << 3;
            long j3 = (jArr[i7] >> i8) & 255;
            if (j3 != 128 && j3 == 254) {
                int iHashCode = Integer.hashCode(iArr[i6]) * ScatterMapKt.MurmurHashC1;
                int i9 = iHashCode ^ (iHashCode << 16);
                int i10 = i9 >>> 7;
                int iFindFirstAvailableSlot = findFirstAvailableSlot(i10);
                int i11 = i10 & i;
                int i12 = i3;
                if (((iFindFirstAvailableSlot - i11) & i) / 8 == ((i6 - i11) & i) / 8) {
                    jArr[i7] = (((long) (i9 & 127)) << i8) | ((~(255 << i8)) & jArr[i7]);
                    jArr[ArraysKt.getLastIndex(jArr)] = (jArr[i12] & j2) | Long.MIN_VALUE;
                    i6++;
                    i3 = i12;
                } else {
                    int i13 = iFindFirstAvailableSlot >> 3;
                    long j4 = jArr[i13];
                    int i14 = (iFindFirstAvailableSlot & 7) << 3;
                    if (((j4 >> i14) & 255) == 128) {
                        jArr[i13] = (((long) (i9 & 127)) << i14) | (j4 & (~(255 << i14)));
                        jArr[i7] = (jArr[i7] & (~(255 << i8))) | (128 << i8);
                        iArr[iFindFirstAvailableSlot] = iArr[i6];
                        iArr[i6] = i12;
                        objArr[iFindFirstAvailableSlot] = objArr[i6];
                        objArr[i6] = null;
                    } else {
                        jArr[i13] = (((long) (i9 & 127)) << i14) | (j4 & (~(255 << i14)));
                        int i15 = iArr[iFindFirstAvailableSlot];
                        iArr[iFindFirstAvailableSlot] = iArr[i6];
                        iArr[i6] = i15;
                        Object obj = objArr[iFindFirstAvailableSlot];
                        objArr[iFindFirstAvailableSlot] = objArr[i6];
                        objArr[i6] = obj;
                        i6--;
                    }
                    jArr[ArraysKt.getLastIndex(jArr)] = (jArr[i12] & j2) | Long.MIN_VALUE;
                    i6++;
                    i3 = i12;
                    j2 = j2;
                }
            } else {
                i6++;
            }
        }
        initializeGrowth();
    }

    public final void resizeStorage$collection(int newCapacity) {
        MutableIntObjectMap<V> mutableIntObjectMap = this;
        long[] jArr = mutableIntObjectMap.metadata;
        int[] iArr = mutableIntObjectMap.keys;
        Object[] objArr = mutableIntObjectMap.values;
        int i = mutableIntObjectMap._capacity;
        initializeStorage(newCapacity);
        long[] jArr2 = mutableIntObjectMap.metadata;
        int[] iArr2 = mutableIntObjectMap.keys;
        Object[] objArr2 = mutableIntObjectMap.values;
        int i2 = mutableIntObjectMap._capacity;
        int i3 = 0;
        while (i3 < i) {
            if (((jArr[i3 >> 3] >> ((i3 & 7) << 3)) & 255) < 128) {
                int i4 = iArr[i3];
                int iHashCode = Integer.hashCode(i4) * ScatterMapKt.MurmurHashC1;
                int i5 = iHashCode ^ (iHashCode << 16);
                int iFindFirstAvailableSlot = mutableIntObjectMap.findFirstAvailableSlot(i5 >>> 7);
                long j = i5 & 127;
                int i6 = iFindFirstAvailableSlot >> 3;
                int i7 = (iFindFirstAvailableSlot & 7) << 3;
                long j2 = (jArr2[i6] & (~(255 << i7))) | (j << i7);
                jArr2[i6] = j2;
                jArr2[(((iFindFirstAvailableSlot - 7) & i2) + (i2 & 7)) >> 3] = j2;
                iArr2[iFindFirstAvailableSlot] = i4;
                objArr2[iFindFirstAvailableSlot] = objArr[i3];
            }
            i3++;
            mutableIntObjectMap = this;
            jArr = jArr;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void putAll(IntObjectMap<V> from) {
        Intrinsics.checkNotNullParameter(from, "from");
        int[] iArr = from.keys;
        Object[] objArr = from.values;
        long[] jArr = from.metadata;
        int length = jArr.length - 2;
        if (length < 0) {
            return;
        }
        int i = 0;
        while (true) {
            long j = jArr[i];
            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                int i2 = 8 - ((~(i - length)) >>> 31);
                for (int i3 = 0; i3 < i2; i3++) {
                    if ((255 & j) < 128) {
                        int i4 = (i << 3) + i3;
                        set(iArr[i4], objArr[i4]);
                    }
                    j >>= 8;
                }
                if (i2 != 8) {
                    return;
                }
            }
            if (i == length) {
                return;
            } else {
                i++;
            }
        }
    }

    public final void minusAssign(IntSet keys) {
        Intrinsics.checkNotNullParameter(keys, "keys");
        int[] iArr = keys.elements;
        long[] jArr = keys.metadata;
        int length = jArr.length - 2;
        if (length < 0) {
            return;
        }
        int i = 0;
        while (true) {
            long j = jArr[i];
            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                int i2 = 8 - ((~(i - length)) >>> 31);
                for (int i3 = 0; i3 < i2; i3++) {
                    if ((255 & j) < 128) {
                        remove(iArr[(i << 3) + i3]);
                    }
                    j >>= 8;
                }
                if (i2 != 8) {
                    return;
                }
            }
            if (i == length) {
                return;
            } else {
                i++;
            }
        }
    }

    public final void minusAssign(IntList keys) {
        Intrinsics.checkNotNullParameter(keys, "keys");
        int[] iArr = keys.content;
        int i = keys._size;
        for (int i2 = 0; i2 < i; i2++) {
            remove(iArr[i2]);
        }
    }

    private final int findAbsoluteInsertIndex(int key) {
        int iHashCode = Integer.hashCode(key) * ScatterMapKt.MurmurHashC1;
        int i = iHashCode ^ (iHashCode << 16);
        int i2 = i >>> 7;
        int i3 = i & 127;
        int i4 = this._capacity;
        int i5 = i2 & i4;
        int i6 = 0;
        while (true) {
            long[] jArr = this.metadata;
            int i7 = i5 >> 3;
            int i8 = (i5 & 7) << 3;
            int i9 = 1;
            long j = ((jArr[i7 + 1] << (64 - i8)) & ((-i8) >> 63)) | (jArr[i7] >>> i8);
            long j2 = i3;
            int i10 = i6;
            long j3 = j ^ (j2 * ScatterMapKt.BitmaskLsb);
            long j4 = (~j3) & (j3 - ScatterMapKt.BitmaskLsb) & (-9187201950435737472L);
            while (j4 != 0) {
                int iNumberOfTrailingZeros = ((Long.numberOfTrailingZeros(j4) >> 3) + i5) & i4;
                int i11 = i9;
                if (this.keys[iNumberOfTrailingZeros] == key) {
                    return iNumberOfTrailingZeros;
                }
                j4 &= j4 - 1;
                i9 = i11;
            }
            int i12 = i9;
            if ((((~j) << 6) & j & (-9187201950435737472L)) != 0) {
                int iFindFirstAvailableSlot = findFirstAvailableSlot(i2);
                if (this.growthLimit == 0 && ((this.metadata[iFindFirstAvailableSlot >> 3] >> ((iFindFirstAvailableSlot & 7) << 3)) & 255) != 254) {
                    adjustStorage$collection();
                    iFindFirstAvailableSlot = findFirstAvailableSlot(i2);
                }
                this._size++;
                int i13 = iFindFirstAvailableSlot >> 3;
                int i14 = (iFindFirstAvailableSlot & 7) << 3;
                this.growthLimit -= ((this.metadata[i13] >> i14) & 255) == 128 ? i12 : 0;
                long[] jArr2 = this.metadata;
                int i15 = this._capacity;
                long j5 = ((~(255 << i14)) & jArr2[i13]) | (j2 << i14);
                jArr2[i13] = j5;
                jArr2[(((iFindFirstAvailableSlot - 7) & i15) + (i15 & 7)) >> 3] = j5;
                return iFindFirstAvailableSlot;
            }
            i6 = i10 + 8;
            i5 = (i5 + i6) & i4;
        }
    }
}
