package com.google.android.appfunctions.schema.internal.dependencies;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzdd extends RuntimeException {
    public zzdd(zzcf zzcfVar) {
        super("Message was missing required fields.  (Lite runtime could not determine which fields were missing).");
    }

    public final zzbl zza() {
        return new zzbl(getMessage());
    }
}
