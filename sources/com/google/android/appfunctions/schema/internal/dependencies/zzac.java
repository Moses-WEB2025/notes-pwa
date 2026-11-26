package com.google.android.appfunctions.schema.internal.dependencies;

import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzac extends zzz {
    private final OutputStream zzg;

    zzac(OutputStream outputStream, int i) {
        super(i);
        if (outputStream == null) {
            throw new NullPointerException("out");
        }
        this.zzg = outputStream;
    }

    private final void zzE(int i) throws IOException {
        if (this.zzb - this.zzc < i) {
            zzF();
        }
    }

    private final void zzF() throws IOException {
        this.zzg.write(this.zza, 0, this.zzc);
        this.zzc = 0;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzad
    public final void zzG(int i, int i2) throws IOException {
        zzp((i << 3) | i2);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzad
    public final void zzH(int i, int i2) throws IOException {
        zzE(20);
        zzd(i << 3);
        if (i2 >= 0) {
            zzd(i2);
        } else {
            zze(i2);
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzad
    public final void zzI(int i, int i2) throws IOException {
        zzE(20);
        zzd(i << 3);
        zzd(i2);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzad
    public final void zzJ(int i, int i2) throws IOException {
        zzE(14);
        zzd((i << 3) | 5);
        zzf(i2);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzad
    public final void zzK(int i, long j) throws IOException {
        zzE(20);
        zzd(i << 3);
        zze(j);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzad, com.google.android.appfunctions.schema.internal.dependencies.zzj
    public final void zza(byte[] bArr, int i, int i2) throws IOException {
        zzu(bArr, i, i2);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzad
    public final void zzh(int i, long j) throws IOException {
        zzE(18);
        zzd((i << 3) | 1);
        zzg(j);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzad
    public final void zzi(int i, boolean z) throws IOException {
        zzE(11);
        zzd(i << 3);
        zzc(z ? (byte) 1 : (byte) 0);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzad
    public final void zzj(int i, String str) throws IOException {
        zzp((i << 3) | 2);
        zzt(str);
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
        if (this.zzc == this.zzb) {
            zzF();
        }
        zzc(b);
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
    public final void zzp(int i) throws IOException {
        zzE(5);
        zzd(i);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzad
    public final void zzq(int i) throws IOException {
        zzE(4);
        zzf(i);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzad
    public final void zzr(long j) throws IOException {
        zzE(10);
        zze(j);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzad
    public final void zzs(long j) throws IOException {
        zzE(8);
        zzg(j);
    }

    public final void zzt(String str) throws IOException {
        int iZzc;
        try {
            int length = str.length() * 3;
            int iZzx = zzx(length);
            int i = iZzx + length;
            int i2 = this.zzb;
            if (i > i2) {
                byte[] bArr = new byte[length];
                int iZzd = zzds.zzd(str, bArr, 0, length);
                zzp(iZzd);
                zzu(bArr, 0, iZzd);
                return;
            }
            if (i > i2 - this.zzc) {
                zzF();
            }
            int iZzx2 = zzx(str.length());
            int i3 = this.zzc;
            try {
                if (iZzx2 == iZzx) {
                    int i4 = i3 + iZzx2;
                    this.zzc = i4;
                    int iZzd2 = zzds.zzd(str, this.zza, i4, i2 - i4);
                    this.zzc = i3;
                    iZzc = (iZzd2 - i3) - iZzx2;
                    zzd(iZzc);
                    this.zzc = iZzd2;
                } else {
                    iZzc = zzds.zzc(str);
                    zzd(iZzc);
                    this.zzc = zzds.zzd(str, this.zza, this.zzc, iZzc);
                }
                this.zzd += iZzc;
            } catch (zzdq e) {
                this.zzd -= this.zzc - i3;
                this.zzc = i3;
                throw e;
            } catch (ArrayIndexOutOfBoundsException e2) {
                throw new zzab(e2);
            }
        } catch (zzdq e3) {
            zzC(str, e3);
        }
    }

    public final void zzu(byte[] bArr, int i, int i2) throws IOException {
        int i3 = this.zzb;
        int i4 = this.zzc;
        int i5 = i3 - i4;
        if (i5 >= i2) {
            System.arraycopy(bArr, i, this.zza, i4, i2);
            this.zzc += i2;
            this.zzd += i2;
            return;
        }
        byte[] bArr2 = this.zza;
        System.arraycopy(bArr, i, bArr2, i4, i5);
        int i6 = i + i5;
        this.zzc = i3;
        this.zzd += i5;
        zzF();
        int i7 = i2 - i5;
        if (i7 <= i3) {
            System.arraycopy(bArr, i6, bArr2, 0, i7);
            this.zzc = i7;
        } else {
            this.zzg.write(bArr, i6, i7);
        }
        this.zzd += i7;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzad
    public final void zzv() throws IOException {
        if (this.zzc > 0) {
            zzF();
        }
    }
}
