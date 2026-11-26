package com.google.android.appfunctions.schema.internal.dependencies;

import java.util.List;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzbs {
    zzbs() {
    }

    public static final List zza(Object obj, long j) {
        zzbi zzbiVar = (zzbi) zzdm.zzn(obj, j);
        if (zzbiVar.zza()) {
            return zzbiVar;
        }
        int size = zzbiVar.size();
        zzbi zzbiVarZzh = zzbiVar.zzh(size == 0 ? 10 : size + size);
        zzdm.zzo(obj, j, zzbiVarZzh);
        return zzbiVarZzh;
    }
}
