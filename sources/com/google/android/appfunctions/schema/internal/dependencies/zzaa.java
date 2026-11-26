package com.google.android.appfunctions.schema.internal.dependencies;

import java.io.IOException;
import java.util.Locale;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzaa extends zzad {
    private final byte[] zza;
    private final int zzb;
    private int zzc;

    zzaa(byte[] bArr, int i, int i2) {
        super(null);
        int length = bArr.length;
        if (((length - i2) | i2) < 0) {
            throw new IllegalArgumentException(String.format(Locale.US, "Array range is invalid. Buffer.length=%d, offset=%d, length=%d", Integer.valueOf(length), 0, Integer.valueOf(i2)));
        }
        this.zza = bArr;
        this.zzc = 0;
        this.zzb = i2;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzad
    public final void zzG(int i, int i2) throws IOException {
        zzp((i << 3) | i2);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzad
    public final void zzH(int i, int i2) throws IOException {
        zzp(i << 3);
        zzo(i2);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzad
    public final void zzI(int i, int i2) throws IOException {
        zzp(i << 3);
        zzp(i2);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzad
    public final void zzJ(int i, int i2) throws IOException {
        zzp((i << 3) | 5);
        zzq(i2);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzad
    public final void zzK(int i, long j) throws IOException {
        zzp(i << 3);
        zzr(j);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzad, com.google.android.appfunctions.schema.internal.dependencies.zzj
    public final void zza(byte[] bArr, int i, int i2) throws IOException {
        zzt(bArr, i, i2);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzad
    public final int zzb() {
        return this.zzb - this.zzc;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzad
    public final void zzh(int i, long j) throws IOException {
        zzp((i << 3) | 1);
        zzs(j);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzad
    public final void zzi(int i, boolean z) throws IOException {
        zzp(i << 3);
        zzn(z ? (byte) 1 : (byte) 0);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzad
    public final void zzj(int i, String str) throws IOException {
        zzp((i << 3) | 2);
        zzu(str);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzad
    public final void zzk(int i, zzt zztVar) throws IOException {
        zzp((i << 3) | 2);
        zzp(zztVar.zzc());
        zztVar.zze(this);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzad
    public final void zzl(int i, zzcf zzcfVar) throws IOException {
        zzp(11);
        zzI(2, i);
        zzp(26);
        zzp(zzcfVar.getSerializedSize());
        zzcfVar.writeTo(this);
        zzp(12);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzad
    public final void zzm(int i, zzt zztVar) throws IOException {
        zzp(11);
        zzI(2, i);
        zzk(3, zztVar);
        zzp(12);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzad
    public final void zzn(byte b) throws IOException {
        int i = this.zzc;
        try {
            int i2 = i + 1;
            try {
                this.zza[i] = b;
                this.zzc = i2;
            } catch (IndexOutOfBoundsException e) {
                e = e;
                i = i2;
                throw new zzab(i, this.zzb, 1, e);
            }
        } catch (IndexOutOfBoundsException e2) {
            e = e2;
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzad
    public final void zzo(int i) throws IOException {
        if (i >= 0) {
            zzp(i);
        } else {
            zzr(i);
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzad
    public final void zzq(int i) throws IOException {
        int i2 = this.zzc;
        try {
            byte[] bArr = this.zza;
            bArr[i2] = (byte) i;
            bArr[i2 + 1] = (byte) (i >> 8);
            bArr[i2 + 2] = (byte) (i >> 16);
            bArr[i2 + 3] = (byte) (i >> 24);
            this.zzc = i2 + 4;
        } catch (IndexOutOfBoundsException e) {
            throw new zzab(i2, this.zzb, 4, e);
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzad
    public final void zzs(long j) throws IOException {
        int i = this.zzc;
        try {
            byte[] bArr = this.zza;
            bArr[i] = (byte) j;
            bArr[i + 1] = (byte) (j >> 8);
            bArr[i + 2] = (byte) (j >> 16);
            bArr[i + 3] = (byte) (j >> 24);
            bArr[i + 4] = (byte) (j >> 32);
            bArr[i + 5] = (byte) (j >> 40);
            bArr[i + 6] = (byte) (j >> 48);
            bArr[i + 7] = (byte) (j >> 56);
            this.zzc = i + 8;
        } catch (IndexOutOfBoundsException e) {
            throw new zzab(i, this.zzb, 8, e);
        }
    }

    public final void zzt(byte[] bArr, int i, int i2) throws IOException {
        try {
            System.arraycopy(bArr, i, this.zza, this.zzc, i2);
            this.zzc += i2;
        } catch (IndexOutOfBoundsException e) {
            throw new zzab(this.zzc, this.zzb, i2, e);
        }
    }

    public final void zzu(String str) throws IOException {
        int i = this.zzc;
        try {
            int iZzx = zzx(str.length() * 3);
            int iZzx2 = zzx(str.length());
            if (iZzx2 != iZzx) {
                zzp(zzds.zzc(str));
                byte[] bArr = this.zza;
                int i2 = this.zzc;
                this.zzc = zzds.zzd(str, bArr, i2, this.zzb - i2);
                return;
            }
            int i3 = i + iZzx2;
            this.zzc = i3;
            int iZzd = zzds.zzd(str, this.zza, i3, this.zzb - i3);
            this.zzc = i;
            zzp((iZzd - i) - iZzx2);
            this.zzc = iZzd;
        } catch (zzdq e) {
            this.zzc = i;
            zzC(str, e);
        } catch (IndexOutOfBoundsException e2) {
            throw new zzab(e2);
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzad
    public final void zzv() {
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzad
    public final void zzp(int i) throws IOException {
        int i2;
        IndexOutOfBoundsException indexOutOfBoundsException;
        int i3 = this.zzc;
        while ((i & (-128)) != 0) {
            try {
                i2 = i3 + 1;
                try {
                    this.zza[i3] = (byte) (i | 128);
                    i >>>= 7;
                    i3 = i2;
                } catch (IndexOutOfBoundsException e) {
                    indexOutOfBoundsException = e;
                    i3 = i2;
                    throw new zzab(i3, this.zzb, 1, indexOutOfBoundsException);
                }
            } catch (IndexOutOfBoundsException e2) {
                indexOutOfBoundsException = e2;
                throw new zzab(i3, this.zzb, 1, indexOutOfBoundsException);
            }
        }
        i2 = i3 + 1;
        this.zza[i3] = (byte) i;
        this.zzc = i2;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzad
    public final void zzr(long j) throws IOException {
        int i;
        IndexOutOfBoundsException indexOutOfBoundsException;
        boolean z = zzad.zzb;
        int i2 = this.zzc;
        if (!z || this.zzb - i2 < 10) {
            while ((j & (-128)) != 0) {
                try {
                    int i3 = i2 + 1;
                    try {
                        this.zza[i2] = (byte) (((int) j) | 128);
                        j >>>= 7;
                        i2 = i3;
                    } catch (IndexOutOfBoundsException e) {
                        indexOutOfBoundsException = e;
                        i2 = i3;
                        throw new zzab(i2, this.zzb, 1, indexOutOfBoundsException);
                    }
                } catch (IndexOutOfBoundsException e2) {
                    indexOutOfBoundsException = e2;
                }
            }
            i = i2 + 1;
            try {
                this.zza[i2] = (byte) j;
            } catch (IndexOutOfBoundsException e3) {
                indexOutOfBoundsException = e3;
                i2 = i;
                throw new zzab(i2, this.zzb, 1, indexOutOfBoundsException);
            }
        } else {
            while ((j & (-128)) != 0) {
                zzdm.zzq(this.zza, i2, (byte) (((int) j) | 128));
                j >>>= 7;
                i2++;
            }
            i = i2 + 1;
            zzdm.zzq(this.zza, i2, (byte) j);
        }
        this.zzc = i;
    }
}
