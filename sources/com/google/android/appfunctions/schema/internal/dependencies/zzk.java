package com.google.android.appfunctions.schema.internal.dependencies;

import java.util.NoSuchElementException;
import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzk extends zzl {
    final /* synthetic */ zzt zza;
    private int zzb;
    private final int zzc;

    zzk(zzt zztVar) {
        Objects.requireNonNull(zztVar);
        this.zza = zztVar;
        this.zzb = 0;
        this.zzc = zztVar.zzc();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zzb < this.zzc;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzp
    public final byte zza() {
        int i = this.zzb;
        if (i >= this.zzc) {
            throw new NoSuchElementException();
        }
        this.zzb = i + 1;
        return this.zza.zzb(i);
    }
}
