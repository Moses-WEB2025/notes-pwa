package com.google.android.appfunctions.schema.internal.dependencies;

import libcore.io.Memory;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzdi extends zzdl {
    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdl
    public final byte zza(Object obj, long j) {
        return zzdm.zzb ? zzdm.zzK(obj, j) : zzdm.zzL(obj, j);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdl
    public final void zzb(Object obj, long j, byte b) {
        if (zzdm.zzb) {
            zzdm.zzM(obj, j, b);
        } else {
            zzdm.zzN(obj, j, b);
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdl
    public final boolean zzc(Object obj, long j) {
        return zzdm.zzb ? zzdm.zzB(obj, j) : zzdm.zzC(obj, j);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdl
    public final void zzd(Object obj, long j, boolean z) {
        if (zzdm.zzb) {
            zzdm.zzM(obj, j, z ? (byte) 1 : (byte) 0);
        } else {
            zzdm.zzN(obj, j, z ? (byte) 1 : (byte) 0);
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdl
    public final float zze(Object obj, long j) {
        return Float.intBitsToFloat(this.zza.getInt(obj, j));
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdl
    public final void zzf(Object obj, long j, float f) {
        this.zza.putInt(obj, j, Float.floatToIntBits(f));
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdl
    public final double zzg(Object obj, long j) {
        return Double.longBitsToDouble(this.zza.getLong(obj, j));
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdl
    public final void zzh(Object obj, long j, double d) {
        this.zza.putLong(obj, j, Double.doubleToLongBits(d));
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdl
    public final byte zzi(long j) {
        return Memory.peekByte((int) j);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdl
    public final void zzj(long j, byte[] bArr, long j2, long j3) {
        Memory.peekByteArray((int) j, bArr, 0, (int) j3);
    }
}
