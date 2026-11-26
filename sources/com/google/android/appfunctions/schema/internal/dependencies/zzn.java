package com.google.android.appfunctions.schema.internal.dependencies;

import java.io.IOException;
import java.nio.charset.Charset;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzn extends zzq {
    private final byte[] zzb;
    private final int zzc;
    private final int zzd;

    zzn(byte[] bArr, int i, int i2) {
        super(null);
        zzq(i, i + i2, bArr.length);
        this.zzb = bArr;
        this.zzc = i;
        this.zzd = i2;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzt
    final byte zzb(int i) {
        return this.zzb[this.zzc + i];
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzt
    public final int zzc() {
        return this.zzd;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzt
    public final zzt zzd(int i, int i2) {
        int iZzq = zzq(i, i2, this.zzd);
        return iZzq == 0 ? zzt.zza : new zzn(this.zzb, this.zzc + i, iZzq);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzt
    final void zze(zzj zzjVar) throws IOException {
        zzjVar.zza(this.zzb, this.zzc, this.zzd);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzt
    protected final String zzf(Charset charset) {
        return new String(this.zzb, this.zzc, this.zzd, charset);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzt
    public final boolean zzg() {
        byte[] bArr = this.zzb;
        int i = this.zzc;
        return zzds.zzb(bArr, i, this.zzd + i);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzt
    protected final boolean zzh(zzt zztVar) {
        boolean z = zztVar instanceof zzr;
        if (!z && !(zztVar instanceof zzn)) {
            return zztVar.zzh(this);
        }
        int i = this.zzd;
        if (i > zztVar.zzc()) {
            StringBuilder sb = new StringBuilder(String.valueOf(i).length() + 18 + String.valueOf(i).length());
            sb.append("Length too large: ");
            sb.append(i);
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        }
        if (i <= zztVar.zzc()) {
            if (z) {
                return zzt.zzr(this.zzb, this.zzc, ((zzr) zztVar).zzk(), 0, i);
            }
            if (zztVar instanceof zzn) {
                zzn zznVar = (zzn) zztVar;
                return zzt.zzr(this.zzb, this.zzc, zznVar.zzb, zznVar.zzc, i);
            }
            zzt zztVarZzd = zztVar.zzd(0, i);
            int i2 = this.zzc;
            return zztVarZzd.equals(zzd(i2, i + i2));
        }
        int iZzc = zztVar.zzc();
        StringBuilder sb2 = new StringBuilder(String.valueOf(i).length() + 27 + String.valueOf(iZzc).length());
        sb2.append("Ran off end of other: 0, ");
        sb2.append(i);
        sb2.append(", ");
        sb2.append(iZzc);
        throw new IllegalArgumentException(sb2.toString());
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzt
    protected final int zzi(int i, int i2, int i3) {
        return zzbj.zzc(i, this.zzb, this.zzc, i3);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzt
    public final zzx zzj() {
        return zzx.zzG(this.zzb, this.zzc, this.zzd, true);
    }

    final /* synthetic */ byte[] zzk() {
        return this.zzb;
    }

    final /* synthetic */ int zzl() {
        return this.zzc;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzt
    public final byte zza(int i) {
        int i2 = this.zzd;
        if (((i2 - (i + 1)) | i) >= 0) {
            return this.zzb[this.zzc + i];
        }
        if (i < 0) {
            StringBuilder sb = new StringBuilder(String.valueOf(i).length() + 11);
            sb.append("Index < 0: ");
            sb.append(i);
            throw new ArrayIndexOutOfBoundsException(sb.toString());
        }
        StringBuilder sb2 = new StringBuilder(String.valueOf(i).length() + 18 + String.valueOf(i2).length());
        sb2.append("Index > length: ");
        sb2.append(i);
        sb2.append(", ");
        sb2.append(i2);
        throw new ArrayIndexOutOfBoundsException(sb2.toString());
    }
}
