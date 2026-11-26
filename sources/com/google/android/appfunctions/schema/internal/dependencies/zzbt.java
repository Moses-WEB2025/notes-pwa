package com.google.android.appfunctions.schema.internal.dependencies;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzbt {
    private static final zzbs zza;
    private static final zzbs zzb;

    static {
        zzbs zzbsVar;
        int i = zzf.zza;
        try {
            zzbsVar = (zzbs) Class.forName("com.google.protobuf.ListFieldSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            zzbsVar = null;
        }
        zza = zzbsVar;
        zzb = new zzbs();
    }

    static zzbs zza() {
        return zza;
    }

    static zzbs zzb() {
        return zzb;
    }
}
