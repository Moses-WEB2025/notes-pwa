package com.google.android.appfunctions.schema.internal.dependencies;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzbw implements zzcd {
    private final zzcd[] zza;

    zzbw(zzcd... zzcdVarArr) {
        this.zza = zzcdVarArr;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcd
    public final boolean zzb(Class cls) {
        for (int i = 0; i < 2; i++) {
            if (this.zza[i].zzb(cls)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcd
    public final zzcc zzc(Class cls) {
        for (int i = 0; i < 2; i++) {
            zzcd zzcdVar = this.zza[i];
            if (zzcdVar.zzb(cls)) {
                return zzcdVar.zzc(cls);
            }
        }
        String name = cls.getName();
        String.valueOf(name);
        throw new UnsupportedOperationException("No factory is available for message type: ".concat(String.valueOf(name)));
    }
}
