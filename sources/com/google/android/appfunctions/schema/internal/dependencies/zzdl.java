package com.google.android.appfunctions.schema.internal.dependencies;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
abstract class zzdl {
    final Unsafe zza;

    zzdl(Unsafe unsafe) {
        this.zza = unsafe;
    }

    public abstract byte zza(Object obj, long j);

    public abstract void zzb(Object obj, long j, byte b);

    public abstract boolean zzc(Object obj, long j);

    public abstract void zzd(Object obj, long j, boolean z);

    public abstract float zze(Object obj, long j);

    public abstract void zzf(Object obj, long j, float f);

    public abstract double zzg(Object obj, long j);

    public abstract void zzh(Object obj, long j, double d);

    public abstract byte zzi(long j);

    public abstract void zzj(long j, byte[] bArr, long j2, long j3);

    public boolean zzk() {
        try {
            Class<?> cls = this.zza.getClass();
            cls.getMethod("objectFieldOffset", Field.class);
            cls.getMethod("arrayBaseOffset", Class.class);
            cls.getMethod("arrayIndexScale", Class.class);
            cls.getMethod("getInt", Object.class, Long.TYPE);
            cls.getMethod("putInt", Object.class, Long.TYPE, Integer.TYPE);
            cls.getMethod("getLong", Object.class, Long.TYPE);
            cls.getMethod("putLong", Object.class, Long.TYPE, Long.TYPE);
            cls.getMethod("getObject", Object.class, Long.TYPE);
            cls.getMethod("putObject", Object.class, Long.TYPE, Object.class);
            return true;
        } catch (Throwable th) {
            Logger.getLogger(zzdm.class.getName()).logp(Level.WARNING, "com.google.protobuf.UnsafeUtil", "logMissingMethod", "platform method missing - proto runtime falling back to safer methods: ".concat(th.toString()));
            return false;
        }
    }

    public boolean zzl() {
        try {
            Class<?> cls = this.zza.getClass();
            cls.getMethod("objectFieldOffset", Field.class);
            cls.getMethod("getLong", Object.class, Long.TYPE);
            return zzdm.zzI() != null;
        } catch (Throwable th) {
            Logger.getLogger(zzdm.class.getName()).logp(Level.WARNING, "com.google.protobuf.UnsafeUtil", "logMissingMethod", "platform method missing - proto runtime falling back to safer methods: ".concat(th.toString()));
            return false;
        }
    }
}
