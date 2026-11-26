package com.google.android.appfunctions.schema.internal.dependencies;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzbx implements zzcu {
    private static final zzcd zzb = new zzbv();
    private final zzcd zza;

    public zzbx() {
        zzcd zzcdVar;
        zzcd[] zzcdVarArr = new zzcd[2];
        zzcdVarArr[0] = zzar.zza();
        int i = zzf.zza;
        try {
            zzcdVar = (zzcd) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception unused) {
            zzcdVar = zzb;
        }
        zzcdVarArr[1] = zzcdVar;
        zzbw zzbwVar = new zzbw(zzcdVarArr);
        byte[] bArr = zzbj.zzb;
        this.zza = zzbwVar;
    }

    private static boolean zzb(zzcc zzccVar) {
        return zzccVar.zzc() + (-1) != 1;
    }

    private static boolean zzc(Class cls) {
        int i = zzf.zza;
        return zzay.class.isAssignableFrom(cls);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcu
    public final zzct zza(Class cls) {
        zzcv.zza(cls);
        zzcc zzccVarZzc = this.zza.zzc(cls);
        if (zzccVarZzc.zza()) {
            return zzc(cls) ? zzcj.zzh(zzcv.zzH(), zzam.zza(), zzccVarZzc.zzb()) : zzcj.zzh(zzcv.zzG(), zzam.zzb(), zzccVarZzc.zzb());
        }
        if (zzc(cls)) {
            return zzci.zzm(cls, zzccVarZzc, zzcm.zzb(), zzbt.zzb(), zzcv.zzH(), zzb(zzccVarZzc) ? zzam.zza() : null, zzcb.zzb());
        }
        zzak zzakVarZzb = null;
        zzcl zzclVarZza = zzcm.zza();
        zzbs zzbsVarZza = zzbt.zza();
        zzde zzdeVarZzG = zzcv.zzG();
        if (zzb(zzccVarZzc)) {
            zzakVarZzb = zzam.zzb();
        }
        return zzci.zzm(cls, zzccVarZzc, zzclVarZza, zzbsVarZza, zzdeVarZzG, zzakVarZzb, zzcb.zza());
    }
}
