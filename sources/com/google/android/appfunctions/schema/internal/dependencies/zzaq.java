package com.google.android.appfunctions.schema.internal.dependencies;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzaq extends zze implements RandomAccess, zzbf, zzco {
    private static final float[] zza;
    private static final zzaq zzb;
    private float[] zzc;
    private int zzd;

    static {
        float[] fArr = new float[0];
        zza = fArr;
        zzb = new zzaq(fArr, 0, false);
    }

    zzaq() {
        this(zza, 0, true);
    }

    public static zzaq zzd() {
        return zzb;
    }

    private static int zzj(int i) {
        return Math.max(((i * 3) / 2) + 1, 10);
    }

    private final void zzk(int i) {
        if (i < 0 || i >= this.zzd) {
            throw new IndexOutOfBoundsException(zzl(i));
        }
    }

    private final String zzl(int i) {
        return zzg.zza(this.zzd, i, (byte) 13, "Index:", ", Size:");
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zze, java.util.AbstractList, java.util.List
    public final /* synthetic */ void add(int i, Object obj) {
        int i2;
        float fFloatValue = ((Float) obj).floatValue();
        zzs();
        if (i < 0 || i > (i2 = this.zzd)) {
            throw new IndexOutOfBoundsException(zzl(i));
        }
        int i3 = i + 1;
        float[] fArr = this.zzc;
        int length = fArr.length;
        if (i2 < length) {
            System.arraycopy(fArr, i, fArr, i3, i2 - i);
        } else {
            float[] fArr2 = new float[zzj(length)];
            System.arraycopy(this.zzc, 0, fArr2, 0, i);
            System.arraycopy(this.zzc, i, fArr2, i3, this.zzd - i);
            this.zzc = fArr2;
        }
        this.zzc[i] = fFloatValue;
        this.zzd++;
        this.modCount++;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zze, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection collection) {
        zzs();
        byte[] bArr = zzbj.zzb;
        collection.getClass();
        if (!(collection instanceof zzaq)) {
            return super.addAll(collection);
        }
        zzaq zzaqVar = (zzaq) collection;
        int i = zzaqVar.zzd;
        if (i == 0) {
            return false;
        }
        int i2 = this.zzd;
        if (Integer.MAX_VALUE - i2 < i) {
            throw new OutOfMemoryError();
        }
        int i3 = i2 + i;
        float[] fArr = this.zzc;
        if (i3 > fArr.length) {
            this.zzc = Arrays.copyOf(fArr, i3);
        }
        System.arraycopy(zzaqVar.zzc, 0, this.zzc, this.zzd, zzaqVar.zzd);
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
        if (!(obj instanceof zzaq)) {
            return super.equals(obj);
        }
        zzaq zzaqVar = (zzaq) obj;
        if (this.zzd != zzaqVar.zzd) {
            return false;
        }
        float[] fArr = zzaqVar.zzc;
        for (int i = 0; i < this.zzd; i++) {
            if (Float.floatToIntBits(this.zzc[i]) != Float.floatToIntBits(fArr[i])) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i) {
        zzk(i);
        return Float.valueOf(this.zzc[i]);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zze, java.util.AbstractList, java.util.Collection, java.util.List
    public final int hashCode() {
        int iFloatToIntBits = 1;
        for (int i = 0; i < this.zzd; i++) {
            iFloatToIntBits = (iFloatToIntBits * 31) + Float.floatToIntBits(this.zzc[i]);
        }
        return iFloatToIntBits;
    }

    @Override // java.util.AbstractList, java.util.List
    public final int indexOf(Object obj) {
        if (!(obj instanceof Float)) {
            return -1;
        }
        float fFloatValue = ((Float) obj).floatValue();
        int i = this.zzd;
        for (int i2 = 0; i2 < i; i2++) {
            if (this.zzc[i2] == fFloatValue) {
                return i2;
            }
        }
        return -1;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zze, java.util.AbstractList, java.util.List
    public final /* bridge */ /* synthetic */ Object remove(int i) {
        zzs();
        zzk(i);
        float[] fArr = this.zzc;
        float f = fArr[i];
        int i2 = this.zzd;
        if (i < i2 - 1) {
            System.arraycopy(fArr, i + 1, fArr, i, (i2 - i) - 1);
        }
        this.zzd--;
        this.modCount++;
        return Float.valueOf(f);
    }

    @Override // java.util.AbstractList
    protected final void removeRange(int i, int i2) {
        zzs();
        if (i2 < i) {
            throw new IndexOutOfBoundsException("toIndex < fromIndex");
        }
        float[] fArr = this.zzc;
        System.arraycopy(fArr, i2, fArr, i, this.zzd - i2);
        this.zzd -= i2 - i;
        this.modCount++;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zze, java.util.AbstractList, java.util.List
    public final /* bridge */ /* synthetic */ Object set(int i, Object obj) {
        float fFloatValue = ((Float) obj).floatValue();
        zzs();
        zzk(i);
        float[] fArr = this.zzc;
        float f = fArr[i];
        fArr[i] = fFloatValue;
        return Float.valueOf(f);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzd;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzbi, com.google.android.appfunctions.schema.internal.dependencies.zzbb
    /* JADX INFO: renamed from: zze */
    public final zzbf zzh(int i) {
        if (i >= this.zzd) {
            return new zzaq(i == 0 ? zza : Arrays.copyOf(this.zzc, i), this.zzd, true);
        }
        throw new IllegalArgumentException();
    }

    public final float zzf(int i) {
        zzk(i);
        return this.zzc[i];
    }

    public final void zzg(float f) {
        zzs();
        int i = this.zzd;
        int length = this.zzc.length;
        if (i == length) {
            float[] fArr = new float[zzj(length)];
            System.arraycopy(this.zzc, 0, fArr, 0, this.zzd);
            this.zzc = fArr;
        }
        float[] fArr2 = this.zzc;
        int i2 = this.zzd;
        this.zzd = i2 + 1;
        fArr2[i2] = f;
    }

    final void zzi(int i) {
        int length = this.zzc.length;
        if (i <= length) {
            return;
        }
        if (length == 0) {
            this.zzc = new float[Math.max(i, 10)];
            return;
        }
        while (length < i) {
            length = zzj(length);
        }
        this.zzc = Arrays.copyOf(this.zzc, length);
    }

    private zzaq(float[] fArr, int i, boolean z) {
        super(z);
        this.zzc = fArr;
        this.zzd = i;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zze, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ boolean add(Object obj) {
        zzg(((Float) obj).floatValue());
        return true;
    }
}
