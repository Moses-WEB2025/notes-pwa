package com.google.android.appfunctions.schema.internal.dependencies;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzaj {
    static final zzaj zza = new zzaj(true);
    private static volatile boolean zzb = false;
    private static volatile zzaj zzc;
    private final Map zzd;

    zzaj() {
        this.zzd = new HashMap();
    }

    public static zzaj zza() {
        zzaj zzajVar;
        int i = zzf.zza;
        zzaj zzajVar2 = zzc;
        if (zzajVar2 != null) {
            return zzajVar2;
        }
        synchronized (zzaj.class) {
            zzajVar = zzc;
            if (zzajVar == null) {
                Class cls = zzah.zza;
                zzaj zzajVar3 = null;
                if (cls != null) {
                    try {
                        zzajVar3 = (zzaj) cls.getDeclaredMethod("getEmptyRegistry", new Class[0]).invoke(null, new Object[0]);
                    } catch (Exception unused) {
                    }
                }
                zzajVar = zzajVar3 == null ? zza : zzajVar3;
                zzc = zzajVar;
            }
        }
        return zzajVar;
    }

    public final zzaw zzb(zzcf zzcfVar, int i) {
        return (zzaw) this.zzd.get(new zzai(zzcfVar, i));
    }

    zzaj(boolean z) {
        this.zzd = Collections.emptyMap();
    }
}
