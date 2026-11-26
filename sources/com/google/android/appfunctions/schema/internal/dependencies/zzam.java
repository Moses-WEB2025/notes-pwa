package com.google.android.appfunctions.schema.internal.dependencies;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzam {
    private static final zzak zza = new zzal();
    private static final zzak zzb;

    static {
        zzak zzakVar;
        int i = zzf.zza;
        try {
            zzakVar = (zzak) Class.forName("com.google.protobuf.ExtensionSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            zzakVar = null;
        }
        zzb = zzakVar;
    }

    static zzak zza() {
        return zza;
    }

    static zzak zzb() {
        zzak zzakVar = zzb;
        if (zzakVar != null) {
            return zzakVar;
        }
        throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
    }
}
