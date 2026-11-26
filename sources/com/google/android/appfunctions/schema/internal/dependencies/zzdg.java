package com.google.android.appfunctions.schema.internal.dependencies;

import java.io.IOException;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzdg extends zzde {
    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzde
    final boolean zza(zzcs zzcsVar) {
        return false;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzde
    final /* bridge */ /* synthetic */ void zzb(Object obj, int i, long j) {
        ((zzdf) obj).zzk(i << 3, Long.valueOf(j));
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzde
    final /* bridge */ /* synthetic */ void zzc(Object obj, int i, int i2) {
        ((zzdf) obj).zzk((i << 3) | 5, Integer.valueOf(i2));
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzde
    final /* bridge */ /* synthetic */ void zzd(Object obj, int i, long j) {
        ((zzdf) obj).zzk((i << 3) | 1, Long.valueOf(j));
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzde
    final /* bridge */ /* synthetic */ void zze(Object obj, int i, zzt zztVar) {
        ((zzdf) obj).zzk((i << 3) | 2, zztVar);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzde
    final /* bridge */ /* synthetic */ void zzf(Object obj, int i, Object obj2) {
        ((zzdf) obj).zzk((i << 3) | 3, (zzdf) obj2);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzde
    final /* synthetic */ Object zzg() {
        return zzdf.zzb();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzde
    final /* synthetic */ Object zzh(Object obj) {
        zzdf zzdfVar = (zzdf) obj;
        zzdfVar.zzd();
        return zzdfVar;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzde
    final /* synthetic */ void zzi(Object obj, Object obj2) {
        ((zzay) obj).unknownFields = (zzdf) obj2;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzde
    final /* synthetic */ Object zzj(Object obj) {
        return ((zzay) obj).unknownFields;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzde
    final /* bridge */ /* synthetic */ Object zzk(Object obj) {
        zzay zzayVar = (zzay) obj;
        zzdf zzdfVar = zzayVar.unknownFields;
        if (zzdfVar != zzdf.zza()) {
            return zzdfVar;
        }
        zzdf zzdfVarZzb = zzdf.zzb();
        zzayVar.unknownFields = zzdfVarZzb;
        return zzdfVarZzb;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzde
    final /* synthetic */ void zzl(Object obj, Object obj2) {
        ((zzay) obj).unknownFields = (zzdf) obj2;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzde
    final void zzm(Object obj) {
        ((zzay) obj).unknownFields.zzd();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzde
    final /* synthetic */ void zzo(Object obj, zzdv zzdvVar) throws IOException {
        ((zzdf) obj).zzg(zzdvVar);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzde
    final /* synthetic */ void zzp(Object obj, zzdv zzdvVar) throws IOException {
        ((zzdf) obj).zzf(zzdvVar);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzde
    final /* bridge */ /* synthetic */ Object zzq(Object obj, Object obj2) {
        zzdf zzdfVar = (zzdf) obj;
        zzdf zzdfVar2 = (zzdf) obj2;
        if (!zzdf.zza().equals(zzdfVar2)) {
            if (zzdf.zza().equals(zzdfVar)) {
                return zzdf.zzc(zzdfVar, zzdfVar2);
            }
            zzdfVar.zzm(zzdfVar2);
        }
        return zzdfVar;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzde
    final /* synthetic */ int zzr(Object obj) {
        return ((zzdf) obj).zzh();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzde
    final /* synthetic */ int zzs(Object obj) {
        return ((zzdf) obj).zzi();
    }
}
