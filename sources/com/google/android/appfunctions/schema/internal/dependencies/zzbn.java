package com.google.android.appfunctions.schema.internal.dependencies;

import java.util.Map;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzbn implements Map.Entry {
    private final Map.Entry zza;

    @Override // java.util.Map.Entry
    public final Object getKey() {
        return this.zza.getKey();
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        if (((zzbp) this.zza.getValue()) == null) {
            return null;
        }
        throw null;
    }

    @Override // java.util.Map.Entry
    public final Object setValue(Object obj) {
        if (obj instanceof zzcf) {
            return ((zzbp) this.zza.getValue()).zza((zzcf) obj);
        }
        throw new IllegalArgumentException("LazyField now only used for MessageSet, and the value of MessageSet must be an instance of MessageLite");
    }

    public final zzbp zza() {
        return (zzbp) this.zza.getValue();
    }
}
