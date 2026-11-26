package com.google.android.appfunctions.schema.internal.dependencies;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzf {
    public static final /* synthetic */ int zza = 0;
    private static final Class zzb;
    private static final boolean zzc;

    static {
        Class<?> cls;
        Class<?> cls2 = null;
        try {
            cls = Class.forName("libcore.io.Memory");
        } catch (Throwable unused) {
            cls = null;
        }
        zzb = cls;
        try {
            cls2 = Class.forName("org.robolectric.Robolectric");
        } catch (Throwable unused2) {
        }
        zzc = cls2 != null;
    }

    static boolean zza() {
        return (zzb == null || zzc) ? false : true;
    }

    static Class zzb() {
        return zzb;
    }
}
