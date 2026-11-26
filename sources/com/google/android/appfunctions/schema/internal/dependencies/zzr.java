package com.google.android.appfunctions.schema.internal.dependencies;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzr extends zzq {
    private final byte[] zzb;

    zzr(byte[] bArr) {
        super(null);
        bArr.getClass();
        this.zzb = bArr;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzt
    public final byte zza(int i) {
        return this.zzb[i];
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzt
    final byte zzb(int i) {
        return this.zzb[i];
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzt
    public final int zzc() {
        return this.zzb.length;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzt
    public final zzt zzd(int i, int i2) {
        byte[] bArr = this.zzb;
        int iZzq = zzq(0, i2, bArr.length);
        return iZzq == 0 ? zzt.zza : new zzn(bArr, 0, iZzq);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzt
    final void zze(zzj zzjVar) throws IOException {
        byte[] bArr = this.zzb;
        zzjVar.zza(bArr, 0, bArr.length);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzt
    protected final String zzf(Charset charset) {
        return new String(this.zzb, charset);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzt
    public final boolean zzg() {
        return zzds.zza(this.zzb);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzt
    protected final boolean zzh(zzt zztVar) {
        if (zztVar instanceof zzr) {
            return Arrays.equals(this.zzb, ((zzr) zztVar).zzb);
        }
        if (!(zztVar instanceof zzn)) {
            return zztVar.zzh(this);
        }
        byte[] bArr = this.zzb;
        int iZzc = zztVar.zzc();
        int length = bArr.length;
        if (length > iZzc) {
            StringBuilder sb = new StringBuilder(String.valueOf(length).length() + 18 + String.valueOf(length).length());
            sb.append("Length too large: ");
            sb.append(length);
            sb.append(length);
            throw new IllegalArgumentException(sb.toString());
        }
        if (length <= zztVar.zzc()) {
            zzn zznVar = (zzn) zztVar;
            return zzt.zzr(bArr, 0, zznVar.zzk(), zznVar.zzl(), length);
        }
        int iZzc2 = zztVar.zzc();
        StringBuilder sb2 = new StringBuilder(String.valueOf(length).length() + 27 + String.valueOf(iZzc2).length());
        sb2.append("Ran off end of other: 0, ");
        sb2.append(length);
        sb2.append(", ");
        sb2.append(iZzc2);
        throw new IllegalArgumentException(sb2.toString());
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzt
    protected final int zzi(int i, int i2, int i3) {
        return zzbj.zzc(i, this.zzb, 0, i3);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzt
    public final zzx zzj() {
        byte[] bArr = this.zzb;
        return zzx.zzG(bArr, 0, bArr.length, true);
    }

    final /* synthetic */ byte[] zzk() {
        return this.zzb;
    }
}
