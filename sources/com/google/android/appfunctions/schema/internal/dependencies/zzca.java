package com.google.android.appfunctions.schema.internal.dependencies;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzca {
    zzca() {
    }

    public static final boolean zza(Object obj) {
        return !((zzbz) obj).zze();
    }

    public static final Object zzb(Object obj, Object obj2) {
        zzbz zzbzVarZzc = (zzbz) obj;
        zzbz zzbzVar = (zzbz) obj2;
        if (!zzbzVar.isEmpty()) {
            if (!zzbzVarZzc.zze()) {
                zzbzVarZzc = zzbzVarZzc.zzc();
            }
            zzbzVarZzc.zzb(zzbzVar);
        }
        return zzbzVarZzc;
    }
}
