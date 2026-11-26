package com.google.android.appfunctions.schema.internal.dependencies;

import java.util.logging.Level;
import java.util.logging.Logger;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzdk extends zzdl {
    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdl
    public final byte zza(Object obj, long j) {
        return this.zza.getByte(obj, j);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdl
    public final void zzb(Object obj, long j, byte b) {
        this.zza.putByte(obj, j, b);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdl
    public final boolean zzc(Object obj, long j) {
        return this.zza.getBoolean(obj, j);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdl
    public final void zzd(Object obj, long j, boolean z) {
        this.zza.putBoolean(obj, j, z);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdl
    public final float zze(Object obj, long j) {
        return this.zza.getFloat(obj, j);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdl
    public final void zzf(Object obj, long j, float f) {
        this.zza.putFloat(obj, j, f);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdl
    public final double zzg(Object obj, long j) {
        return this.zza.getDouble(obj, j);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdl
    public final void zzh(Object obj, long j, double d) {
        this.zza.putDouble(obj, j, d);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdl
    public final byte zzi(long j) {
        return this.zza.getByte(j);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdl
    public final void zzj(long j, byte[] bArr, long j2, long j3) {
        this.zza.copyMemory((Object) null, j, bArr, zzdm.zza, j3);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdl
    public final boolean zzk() {
        if (!super.zzk()) {
            return false;
        }
        try {
            Class<?> cls = this.zza.getClass();
            cls.getMethod("getByte", Object.class, Long.TYPE);
            cls.getMethod("putByte", Object.class, Long.TYPE, Byte.TYPE);
            cls.getMethod("getBoolean", Object.class, Long.TYPE);
            cls.getMethod("putBoolean", Object.class, Long.TYPE, Boolean.TYPE);
            cls.getMethod("getFloat", Object.class, Long.TYPE);
            cls.getMethod("putFloat", Object.class, Long.TYPE, Float.TYPE);
            cls.getMethod("getDouble", Object.class, Long.TYPE);
            cls.getMethod("putDouble", Object.class, Long.TYPE, Double.TYPE);
            return true;
        } catch (Throwable th) {
            Logger.getLogger(zzdm.class.getName()).logp(Level.WARNING, "com.google.protobuf.UnsafeUtil", "logMissingMethod", "platform method missing - proto runtime falling back to safer methods: ".concat(th.toString()));
            return false;
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdl
    public final boolean zzl() {
        if (!super.zzl()) {
            return false;
        }
        try {
            Class<?> cls = this.zza.getClass();
            cls.getMethod("getByte", Long.TYPE);
            cls.getMethod("putByte", Long.TYPE, Byte.TYPE);
            cls.getMethod("getInt", Long.TYPE);
            cls.getMethod("putInt", Long.TYPE, Integer.TYPE);
            cls.getMethod("getLong", Long.TYPE);
            cls.getMethod("putLong", Long.TYPE, Long.TYPE);
            cls.getMethod("copyMemory", Long.TYPE, Long.TYPE, Long.TYPE);
            cls.getMethod("copyMemory", Object.class, Long.TYPE, Object.class, Long.TYPE, Long.TYPE);
            return true;
        } catch (Throwable th) {
            Logger.getLogger(zzdm.class.getName()).logp(Level.WARNING, "com.google.protobuf.UnsafeUtil", "logMissingMethod", "platform method missing - proto runtime falling back to safer methods: ".concat(th.toString()));
            return false;
        }
    }
}
