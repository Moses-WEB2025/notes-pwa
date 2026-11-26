package com.google.android.appfunctions.schema.internal.dependencies;

import java.io.IOException;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
abstract class zzde {
    private static volatile int zza = 100;

    zzde() {
    }

    abstract boolean zza(zzcs zzcsVar);

    abstract void zzb(Object obj, int i, long j);

    abstract void zzc(Object obj, int i, int i2);

    abstract void zzd(Object obj, int i, long j);

    abstract void zze(Object obj, int i, zzt zztVar);

    abstract void zzf(Object obj, int i, Object obj2);

    abstract Object zzg();

    abstract Object zzh(Object obj);

    abstract void zzi(Object obj, Object obj2);

    abstract Object zzj(Object obj);

    abstract Object zzk(Object obj);

    abstract void zzl(Object obj, Object obj2);

    abstract void zzm(Object obj);

    final boolean zzn(Object obj, zzcs zzcsVar, int i) throws IOException {
        int iZzc = zzcsVar.zzc();
        int i2 = iZzc >>> 3;
        int i3 = iZzc & 7;
        if (i3 == 0) {
            zzb(obj, i2, zzcsVar.zzh());
            return true;
        }
        if (i3 == 1) {
            zzd(obj, i2, zzcsVar.zzj());
            return true;
        }
        if (i3 == 2) {
            zze(obj, i2, zzcsVar.zzs());
            return true;
        }
        if (i3 != 3) {
            if (i3 == 4) {
                if (i != 0) {
                    return false;
                }
                throw new zzbl("Protocol message end-group tag did not match expected tag.");
            }
            if (i3 != 5) {
                throw new zzbk("Protocol message tag had invalid wire type.");
            }
            zzc(obj, i2, zzcsVar.zzk());
            return true;
        }
        Object objZzg = zzg();
        int i4 = i2 << 3;
        int i5 = i + 1;
        if (i5 >= zza) {
            throw new zzbl("Protocol message had too many levels of nesting.  May be malicious.  Use setRecursionLimit() to increase the recursion depth limit.");
        }
        while (zzcsVar.zzb() != Integer.MAX_VALUE && zzn(objZzg, zzcsVar, i5)) {
        }
        if ((i4 | 4) != zzcsVar.zzc()) {
            throw new zzbl("Protocol message end-group tag did not match expected tag.");
        }
        zzf(obj, i2, zzh(objZzg));
        return true;
    }

    abstract void zzo(Object obj, zzdv zzdvVar) throws IOException;

    abstract void zzp(Object obj, zzdv zzdvVar) throws IOException;

    abstract Object zzq(Object obj, Object obj2);

    abstract int zzr(Object obj);

    abstract int zzs(Object obj);
}
