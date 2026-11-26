package com.google.android.appfunctions.schema.internal.dependencies;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzcj implements zzct {
    private final zzcf zza;
    private final zzde zzb;
    private final boolean zzc;
    private final zzak zzd;

    private zzcj(zzde zzdeVar, zzak zzakVar, zzcf zzcfVar) {
        this.zzb = zzdeVar;
        this.zzc = zzakVar.zza(zzcfVar);
        this.zzd = zzakVar;
        this.zza = zzcfVar;
    }

    static zzcj zzh(zzde zzdeVar, zzak zzakVar, zzcf zzcfVar) {
        return new zzcj(zzdeVar, zzakVar, zzcfVar);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzct
    public final Object zza() {
        zzcf zzcfVar = this.zza;
        return zzcfVar instanceof zzay ? ((zzay) zzcfVar).newMutableInstance() : zzcfVar.newBuilderForType().buildPartial();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzct
    public final boolean zzb(Object obj, Object obj2) {
        zzde zzdeVar = this.zzb;
        if (!zzdeVar.zzj(obj).equals(zzdeVar.zzj(obj2))) {
            return false;
        }
        if (!this.zzc) {
            return true;
        }
        zzak zzakVar = this.zzd;
        return zzakVar.zzb(obj).equals(zzakVar.zzb(obj2));
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzct
    public final int zzc(Object obj) {
        int iHashCode = this.zzb.zzj(obj).hashCode();
        return this.zzc ? (iHashCode * 53) + this.zzd.zzb(obj).zza.hashCode() : iHashCode;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzct
    public final void zzd(Object obj, Object obj2) {
        zzcv.zzK(this.zzb, obj, obj2);
        if (this.zzc) {
            zzcv.zzJ(this.zzd, obj, obj2);
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzct
    public final int zze(Object obj) {
        zzde zzdeVar = this.zzb;
        int iZzr = zzdeVar.zzr(zzdeVar.zzj(obj));
        return this.zzc ? iZzr + this.zzd.zzb(obj).zzi() : iZzr;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzct
    public final void zzf(Object obj, zzdv zzdvVar) throws IOException {
        Iterator itZzc = this.zzd.zzb(obj).zzc();
        while (itZzc.hasNext()) {
            Map.Entry entry = (Map.Entry) itZzc.next();
            zzan zzanVar = (zzan) entry.getKey();
            if (zzanVar.zzc() != zzdu.MESSAGE || zzanVar.zzd() || zzanVar.zze()) {
                throw new IllegalStateException("Found invalid MessageSet item.");
            }
            if (entry instanceof zzbn) {
                zzdvVar.zzv(zzanVar.zza(), ((zzbn) entry).zza().zzc());
            } else {
                zzdvVar.zzv(zzanVar.zza(), entry.getValue());
            }
        }
        zzde zzdeVar = this.zzb;
        zzdeVar.zzp(zzdeVar.zzj(obj), zzdvVar);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzct
    public final void zzg(Object obj, zzcs zzcsVar, zzaj zzajVar) throws IOException {
        boolean zZzd;
        zzde zzdeVar = this.zzb;
        Object objZzk = zzdeVar.zzk(obj);
        zzak zzakVar = this.zzd;
        zzao zzaoVarZzc = zzakVar.zzc(obj);
        while (zzcsVar.zzb() != Integer.MAX_VALUE) {
            try {
                int iZzc = zzcsVar.zzc();
                int iZzt = 0;
                if (iZzc != 11) {
                    if ((iZzc & 7) == 2) {
                        Object objZzh = zzakVar.zzh(zzajVar, this.zza, iZzc >>> 3);
                        if (objZzh != null) {
                            zzakVar.zzi(zzcsVar, objZzh, zzajVar, zzaoVarZzc);
                        } else {
                            zZzd = zzdeVar.zzn(objZzk, zzcsVar, 0);
                        }
                    } else {
                        zZzd = zzcsVar.zzd();
                    }
                    if (!zZzd) {
                        break;
                    }
                } else {
                    Object objZzh2 = null;
                    zzt zztVarZzs = null;
                    while (zzcsVar.zzb() != Integer.MAX_VALUE) {
                        int iZzc2 = zzcsVar.zzc();
                        if (iZzc2 == 16) {
                            iZzt = zzcsVar.zzt();
                            objZzh2 = zzakVar.zzh(zzajVar, this.zza, iZzt);
                        } else if (iZzc2 == 26) {
                            if (objZzh2 != null) {
                                zzakVar.zzi(zzcsVar, objZzh2, zzajVar, zzaoVarZzc);
                            } else {
                                zztVarZzs = zzcsVar.zzs();
                            }
                        } else if (iZzc2 == 12 || !zzcsVar.zzd()) {
                            break;
                        }
                    }
                    if (zzcsVar.zzc() != 12) {
                        throw new zzbl("Protocol message end-group tag did not match expected tag.");
                    }
                    if (zztVarZzs != null) {
                        if (objZzh2 != null) {
                            zzakVar.zzj(zztVarZzs, objZzh2, zzajVar, zzaoVarZzc);
                        } else {
                            zzdeVar.zze(objZzk, iZzt, zztVarZzs);
                        }
                    }
                }
            } catch (Throwable th) {
                zzdeVar.zzl(obj, objZzk);
                throw th;
            }
        }
        zzdeVar.zzl(obj, objZzk);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzct
    public final void zzj(Object obj, byte[] bArr, int i, int i2, zzg zzgVar) throws IOException {
        zzay zzayVar = (zzay) obj;
        if (zzayVar.unknownFields == zzdf.zza()) {
            zzayVar.unknownFields = zzdf.zzb();
        }
        throw null;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzct
    public final void zzk(Object obj) {
        this.zzb.zzm(obj);
        this.zzd.zzd(obj);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzct
    public final boolean zzl(Object obj) {
        return this.zzd.zzb(obj).zzg();
    }
}
