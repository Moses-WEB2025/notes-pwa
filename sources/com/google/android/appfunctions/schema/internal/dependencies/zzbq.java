package com.google.android.appfunctions.schema.internal.dependencies;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
public class zzbq {
    protected volatile zzcf zza;
    private volatile zzt zzb;
    private volatile boolean zzc;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzbq)) {
            return false;
        }
        zzbq zzbqVar = (zzbq) obj;
        zzcf zzcfVar = this.zza;
        zzcf zzcfVar2 = zzbqVar.zza;
        if (zzcfVar == null && zzcfVar2 == null) {
            return zzc().equals(zzbqVar.zzc());
        }
        if (zzcfVar != null && zzcfVar2 != null) {
            return zzcfVar.equals(zzcfVar2);
        }
        if (zzcfVar != null) {
            zzbqVar.zzd(zzcfVar.getDefaultInstanceForType());
            return zzcfVar.equals(zzbqVar.zza);
        }
        zzd(zzcfVar2.getDefaultInstanceForType());
        return this.zza.equals(zzcfVar2);
    }

    public int hashCode() {
        return 1;
    }

    public final zzcf zza(zzcf zzcfVar) {
        zzcf zzcfVar2 = this.zza;
        this.zzb = null;
        this.zza = zzcfVar;
        return zzcfVar2;
    }

    public final int zzb() {
        if (this.zzb != null) {
            return this.zzb.zzc();
        }
        if (this.zza != null) {
            return this.zza.getSerializedSize();
        }
        return 0;
    }

    public final zzt zzc() {
        if (this.zzb != null) {
            return this.zzb;
        }
        synchronized (this) {
            if (this.zzb != null) {
                return this.zzb;
            }
            if (this.zza == null) {
                this.zzb = zzt.zza;
            } else {
                this.zzb = this.zza.toByteString();
            }
            return this.zzb;
        }
    }

    protected final void zzd(zzcf zzcfVar) {
        if (this.zza != null) {
            return;
        }
        synchronized (this) {
            if (this.zza != null) {
                return;
            }
            try {
                this.zza = zzcfVar;
                this.zzb = zzt.zza;
            } catch (zzbl unused) {
                this.zzc = true;
                this.zza = zzcfVar;
                this.zzb = zzt.zza;
            }
        }
    }
}
