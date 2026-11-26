package com.google.android.appfunctions.schema.internal.dependencies;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzar implements zzcd {
    private static final zzar zza = new zzar();

    public static zzar zza() {
        return zza;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcd
    public final boolean zzb(Class cls) {
        return zzay.class.isAssignableFrom(cls);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcd
    public final zzcc zzc(Class cls) {
        if (!zzay.class.isAssignableFrom(cls)) {
            String name = cls.getName();
            String.valueOf(name);
            throw new IllegalArgumentException("Unsupported message type: ".concat(String.valueOf(name)));
        }
        try {
            return (zzcc) zzay.getDefaultInstance(cls.asSubclass(zzay.class)).buildMessageInfo();
        } catch (Exception e) {
            String name2 = cls.getName();
            String.valueOf(name2);
            throw new RuntimeException("Unable to get message info for ".concat(String.valueOf(name2)), e);
        }
    }
}
