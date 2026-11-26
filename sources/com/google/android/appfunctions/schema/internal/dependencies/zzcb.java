package com.google.android.appfunctions.schema.internal.dependencies;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzcb {
    private static final zzca zza;
    private static final zzca zzb;

    static {
        zzca zzcaVar;
        int i = zzf.zza;
        try {
            zzcaVar = (zzca) Class.forName("com.google.protobuf.MapFieldSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            zzcaVar = null;
        }
        zza = zzcaVar;
        zzb = new zzca();
    }

    static zzca zza() {
        return zza;
    }

    static zzca zzb() {
        return zzb;
    }
}
