package com.google.android.appfunctions.schema.internal.dependencies;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzav implements zzan {
    final zzbd zza;
    final int zzb;
    final zzdt zzc;
    final boolean zzd;
    final boolean zze;

    zzav(zzbd zzbdVar, int i, zzdt zzdtVar, boolean z, boolean z2) {
        this.zza = zzbdVar;
        this.zzb = i;
        this.zzc = zzdtVar;
        this.zzd = z;
        this.zze = z2;
    }

    @Override // java.lang.Comparable
    public final /* synthetic */ int compareTo(Object obj) {
        return this.zzb - ((zzav) obj).zzb;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzan
    public final int zza() {
        return this.zzb;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzan
    public final zzdt zzb() {
        return this.zzc;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzan
    public final zzdu zzc() {
        return this.zzc.zza();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzan
    public final boolean zzd() {
        return this.zzd;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzan
    public final boolean zze() {
        return this.zze;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzan
    public final boolean zzf(Object obj) {
        return obj instanceof zzcf;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzan
    public final void zzg(Object obj, Object obj2) {
        ((zzas) obj).mergeFrom((zzay) obj2);
    }
}
