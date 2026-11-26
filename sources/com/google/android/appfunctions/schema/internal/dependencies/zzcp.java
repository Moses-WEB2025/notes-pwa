package com.google.android.appfunctions.schema.internal.dependencies;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzcp {
    private static final zzcp zza = new zzcp();
    private final ConcurrentMap zzc = new ConcurrentHashMap();
    private final zzcu zzb = new zzbx();

    private zzcp() {
    }

    static zzcp zza() {
        return zza;
    }

    final zzct zzb(Class cls) {
        zzbj.zza(cls, "messageType");
        ConcurrentMap concurrentMap = this.zzc;
        zzct zzctVar = (zzct) concurrentMap.get(cls);
        if (zzctVar != null) {
            return zzctVar;
        }
        zzct zzctVarZza = this.zzb.zza(cls);
        zzct zzctVar2 = (zzct) concurrentMap.putIfAbsent(cls, zzctVarZza);
        return zzctVar2 != null ? zzctVar2 : zzctVarZza;
    }
}
