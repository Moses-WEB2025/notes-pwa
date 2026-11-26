package com.google.android.appfunctions.schema.internal.dependencies;

import java.io.IOException;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
public interface zzcf extends zzcg {
    int getSerializedSize();

    zzce newBuilderForType();

    zzce toBuilder();

    zzt toByteString();

    void writeTo(zzad zzadVar) throws IOException;
}
