package com.google.android.appfunctions.schema.internal.dependencies;

import com.google.android.appfunctions.schema.internal.dependencies.zzcf;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzaw<ContainingType extends zzcf, Type> extends zzag<ContainingType, Type> {
    final zzcf zza;
    final zzav zzb;

    zzaw(zzcf zzcfVar, Object obj, zzcf zzcfVar2, zzav zzavVar, Class cls) {
        if (zzcfVar == null) {
            throw new IllegalArgumentException("Null containingTypeDefaultInstance");
        }
        if (zzavVar.zzc == zzdt.MESSAGE && zzcfVar2 == null) {
            throw new IllegalArgumentException("Null messageDefaultInstance");
        }
        this.zza = zzcfVar2;
        this.zzb = zzavVar;
    }
}
