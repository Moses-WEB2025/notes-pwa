package com.google.android.appfunctions.schema.internal.dependencies;

import java.io.IOException;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
public interface zzce extends Cloneable, zzcg {
    zzcf build();

    zzcf buildPartial();

    zzce mergeFrom(zzcf zzcfVar);

    zzce mergeFrom(zzx zzxVar, zzaj zzajVar) throws IOException;
}
