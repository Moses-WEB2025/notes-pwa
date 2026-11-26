package com.google.android.appfunctions.schema.internal.dependencies;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzcm {
    private static final zzcl zza;
    private static final zzcl zzb;

    static {
        zzcl zzclVar;
        int i = zzf.zza;
        try {
            zzclVar = (zzcl) Class.forName("com.google.protobuf.NewInstanceSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            zzclVar = null;
        }
        zza = zzclVar;
        zzb = new zzcl();
    }

    static zzcl zza() {
        return zza;
    }

    static zzcl zzb() {
        return zzb;
    }
}
