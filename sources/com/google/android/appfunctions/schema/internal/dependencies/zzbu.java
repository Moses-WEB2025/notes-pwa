package com.google.android.appfunctions.schema.internal.dependencies;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzbu extends zze implements RandomAccess, zzbh, zzco {
    private static final long[] zza;
    private static final zzbu zzb;
    private long[] zzc;
    private int zzd;

    static {
        long[] jArr = new long[0];
        zza = jArr;
        zzb = new zzbu(jArr, 0, false);
    }

    zzbu() {
        this(zza, 0, true);
    }

    public static zzbu zzd() {
        return zzb;
    }

    private static int zzi(int i) {
        return Math.max(((i * 3) / 2) + 1, 10);
    }

    private final void zzj(int i) {
        if (i < 0 || i >= this.zzd) {
            throw new IndexOutOfBoundsException(zzk(i));
        }
    }

    private final String zzk(int i) {
        return zzg.zza(this.zzd, i, (byte) 13, "Index:", ", Size:");
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zze, java.util.AbstractList, java.util.List
    public final /* synthetic */ void add(int i, Object obj) {
        int i2;
        long jLongValue = ((Long) obj).longValue();
        zzs();
        if (i < 0 || i > (i2 = this.zzd)) {
            throw new IndexOutOfBoundsException(zzk(i));
        }
        int i3 = i + 1;
        long[] jArr = this.zzc;
        int length = jArr.length;
        if (i2 < length) {
            System.arraycopy(jArr, i, jArr, i3, i2 - i);
        } else {
            long[] jArr2 = new long[zzi(length)];
            System.arraycopy(this.zzc, 0, jArr2, 0, i);
            System.arraycopy(this.zzc, i, jArr2, i3, this.zzd - i);
            this.zzc = jArr2;
        }
        this.zzc[i] = jLongValue;
        this.zzd++;
        this.modCount++;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zze, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection collection) {
        zzs();
        byte[] bArr = zzbj.zzb;
        collection.getClass();
        if (!(collection instanceof zzbu)) {
            return super.addAll(collection);
        }
        zzbu zzbuVar = (zzbu) collection;
        int i = zzbuVar.zzd;
        if (i == 0) {
            return false;
        }
        int i2 = this.zzd;
        if (Integer.MAX_VALUE - i2 < i) {
            throw new OutOfMemoryError();
        }
        int i3 = i2 + i;
        long[] jArr = this.zzc;
        if (i3 > jArr.length) {
            this.zzc = Arrays.copyOf(jArr, i3);
        }
        System.arraycopy(zzbuVar.zzc, 0, this.zzc, this.zzd, zzbuVar.zzd);
        this.zzd = i3;
        this.modCount++;
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zze, java.util.AbstractList, java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzbu)) {
            return super.equals(obj);
        }
        zzbu zzbuVar = (zzbu) obj;
        if (this.zzd != zzbuVar.zzd) {
            return false;
        }
        long[] jArr = zzbuVar.zzc;
        for (int i = 0; i < this.zzd; i++) {
            if (this.zzc[i] != jArr[i]) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i) {
        zzj(i);
        return Long.valueOf(this.zzc[i]);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zze, java.util.AbstractList, java.util.Collection, java.util.List
    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.zzd; i2++) {
            long j = this.zzc[i2];
            byte[] bArr = zzbj.zzb;
            i = (i * 31) + ((int) (j ^ (j >>> 32)));
        }
        return i;
    }

    @Override // java.util.AbstractList, java.util.List
    public final int indexOf(Object obj) {
        if (!(obj instanceof Long)) {
            return -1;
        }
        long jLongValue = ((Long) obj).longValue();
        int i = this.zzd;
        for (int i2 = 0; i2 < i; i2++) {
            if (this.zzc[i2] == jLongValue) {
                return i2;
            }
        }
        return -1;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zze, java.util.AbstractList, java.util.List
    public final /* bridge */ /* synthetic */ Object remove(int i) {
        zzs();
        zzj(i);
        long[] jArr = this.zzc;
        long j = jArr[i];
        int i2 = this.zzd;
        if (i < i2 - 1) {
            System.arraycopy(jArr, i + 1, jArr, i, (i2 - i) - 1);
        }
        this.zzd--;
        this.modCount++;
        return Long.valueOf(j);
    }

    @Override // java.util.AbstractList
    protected final void removeRange(int i, int i2) {
        zzs();
        if (i2 < i) {
            throw new IndexOutOfBoundsException("toIndex < fromIndex");
        }
        long[] jArr = this.zzc;
        System.arraycopy(jArr, i2, jArr, i, this.zzd - i2);
        this.zzd -= i2 - i;
        this.modCount++;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zze, java.util.AbstractList, java.util.List
    public final /* bridge */ /* synthetic */ Object set(int i, Object obj) {
        long jLongValue = ((Long) obj).longValue();
        zzs();
        zzj(i);
        long[] jArr = this.zzc;
        long j = jArr[i];
        jArr[i] = jLongValue;
        return Long.valueOf(j);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzd;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzbi, com.google.android.appfunctions.schema.internal.dependencies.zzbb
    /* JADX INFO: renamed from: zzc */
    public final zzbh zzh(int i) {
        if (i >= this.zzd) {
            return new zzbu(i == 0 ? zza : Arrays.copyOf(this.zzc, i), this.zzd, true);
        }
        throw new IllegalArgumentException();
    }

    public final long zze(int i) {
        zzj(i);
        return this.zzc[i];
    }

    public final void zzf(long j) {
        zzs();
        int i = this.zzd;
        int length = this.zzc.length;
        if (i == length) {
            long[] jArr = new long[zzi(length)];
            System.arraycopy(this.zzc, 0, jArr, 0, this.zzd);
            this.zzc = jArr;
        }
        long[] jArr2 = this.zzc;
        int i2 = this.zzd;
        this.zzd = i2 + 1;
        jArr2[i2] = j;
    }

    final void zzg(int i) {
        int length = this.zzc.length;
        if (i <= length) {
            return;
        }
        if (length == 0) {
            this.zzc = new long[Math.max(i, 10)];
            return;
        }
        while (length < i) {
            length = zzi(length);
        }
        this.zzc = Arrays.copyOf(this.zzc, length);
    }

    private zzbu(long[] jArr, int i, boolean z) {
        super(z);
        this.zzc = jArr;
        this.zzd = i;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zze, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ boolean add(Object obj) {
        zzf(((Long) obj).longValue());
        return true;
    }
}
