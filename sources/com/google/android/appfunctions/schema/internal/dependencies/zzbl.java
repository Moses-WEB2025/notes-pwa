package com.google.android.appfunctions.schema.internal.dependencies;

import java.io.IOException;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
public class zzbl extends IOException {
    private boolean zza;

    public zzbl(IOException iOException) {
        super(iOException.getMessage(), iOException);
    }

    final void zza() {
        this.zza = true;
    }

    final boolean zzb() {
        return this.zza;
    }

    public zzbl(String str) {
        super(str);
    }
}
