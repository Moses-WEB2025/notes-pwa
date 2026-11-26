package com.google.android.appfunctions.schema.internal.dependencies;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzcy implements Iterator {
    final /* synthetic */ zzda zza;
    private int zzb;
    private boolean zzc;
    private Iterator zzd;

    /* synthetic */ zzcy(zzda zzdaVar, byte[] bArr) {
        Objects.requireNonNull(zzdaVar);
        this.zza = zzdaVar;
        this.zzb = -1;
    }

    private final Iterator zza() {
        if (this.zzd == null) {
            this.zzd = this.zza.zzk().entrySet().iterator();
        }
        return this.zzd;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        int i = this.zzb + 1;
        zzda zzdaVar = this.zza;
        if (i >= zzdaVar.zzj()) {
            return !zzdaVar.zzk().isEmpty() && zza().hasNext();
        }
        return true;
    }

    @Override // java.util.Iterator
    public final /* bridge */ /* synthetic */ Object next() {
        this.zzc = true;
        int i = this.zzb + 1;
        this.zzb = i;
        zzda zzdaVar = this.zza;
        return i < zzdaVar.zzj() ? (zzcx) zzdaVar.zzi()[i] : (Map.Entry) zza().next();
    }

    @Override // java.util.Iterator
    public final void remove() {
        if (!this.zzc) {
            throw new IllegalStateException("remove() was called before next()");
        }
        this.zzc = false;
        zzda zzdaVar = this.zza;
        zzdaVar.zzh();
        int i = this.zzb;
        if (i >= zzdaVar.zzj()) {
            zza().remove();
        } else {
            this.zzb = i - 1;
            zzdaVar.zzg(i);
        }
    }
}
