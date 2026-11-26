package com.google.android.appfunctions.schema.internal.dependencies;

import com.xiaomi.mirror.RemoteDeviceInfo;
import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import sun.misc.Unsafe;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzdm {
    static final long zza;
    static final boolean zzb;
    private static final Unsafe zzc;
    private static final Class zzd;
    private static final boolean zze;
    private static final zzdl zzf;
    private static final boolean zzg;
    private static final boolean zzh;
    private static final long zzi;

    static {
        Unsafe unsafeZzu = zzu();
        zzc = unsafeZzu;
        zzd = zzf.zzb();
        boolean zZzv = zzv(Long.TYPE);
        zze = zZzv;
        boolean zZzv2 = zzv(Integer.TYPE);
        zzdl zzdkVar = null;
        if (unsafeZzu != null) {
            if (!zzf.zza()) {
                zzdkVar = new zzdk(unsafeZzu);
            } else if (zZzv) {
                zzdkVar = new zzdj(unsafeZzu);
            } else if (zZzv2) {
                zzdkVar = new zzdi(unsafeZzu);
            }
        }
        zzf = zzdkVar;
        zzg = zzdkVar == null ? false : zzdkVar.zzl();
        zzh = zzdkVar == null ? false : zzdkVar.zzk();
        zza = zzG(byte[].class);
        zzG(boolean[].class);
        zzH(boolean[].class);
        zzG(int[].class);
        zzH(int[].class);
        zzG(long[].class);
        zzH(long[].class);
        zzG(float[].class);
        zzH(float[].class);
        zzG(double[].class);
        zzH(double[].class);
        zzG(Object[].class);
        zzH(Object[].class);
        Field fieldZzI = zzI();
        long jObjectFieldOffset = -1;
        if (fieldZzI != null && zzdkVar != null) {
            jObjectFieldOffset = zzdkVar.zza.objectFieldOffset(fieldZzI);
        }
        zzi = jObjectFieldOffset;
        zzb = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
    }

    private zzdm() {
    }

    static /* synthetic */ boolean zzB(Object obj, long j) {
        return zzK(obj, j) != 0;
    }

    static /* synthetic */ boolean zzC(Object obj, long j) {
        return zzL(obj, j) != 0;
    }

    private static int zzG(Class cls) {
        if (zzh) {
            return zzf.zza.arrayBaseOffset(cls);
        }
        return -1;
    }

    private static int zzH(Class cls) {
        if (zzh) {
            return zzf.zza.arrayIndexScale(cls);
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Field zzI() {
        Field fieldZzJ;
        if (zzf.zza() && (fieldZzJ = zzJ(Buffer.class, "effectiveDirectAddress")) != null) {
            return fieldZzJ;
        }
        Field fieldZzJ2 = zzJ(Buffer.class, RemoteDeviceInfo.KEY_ADDRESS);
        if (fieldZzJ2 == null || fieldZzJ2.getType() != Long.TYPE) {
            return null;
        }
        return fieldZzJ2;
    }

    private static Field zzJ(Class cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (Throwable unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte zzK(Object obj, long j) {
        return (byte) ((zzf.zza.getInt(obj, (-4) & j) >>> ((int) (((~j) & 3) << 3))) & 255);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte zzL(Object obj, long j) {
        return (byte) ((zzf.zza.getInt(obj, (-4) & j) >>> ((int) ((j & 3) << 3))) & 255);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void zzM(Object obj, long j, byte b) {
        Unsafe unsafe = zzf.zza;
        long j2 = (-4) & j;
        int i = unsafe.getInt(obj, j2);
        int i2 = ((~((int) j)) & 3) << 3;
        unsafe.putInt(obj, j2, ((255 & b) << i2) | (i & (~(255 << i2))));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void zzN(Object obj, long j, byte b) {
        Unsafe unsafe = zzf.zza;
        long j2 = (-4) & j;
        int i = (((int) j) & 3) << 3;
        unsafe.putInt(obj, j2, ((255 & b) << i) | (unsafe.getInt(obj, j2) & (~(255 << i))));
    }

    static boolean zza() {
        return zzh;
    }

    static boolean zzb() {
        return zzg;
    }

    static Object zzc(Class cls) {
        try {
            return zzc.allocateInstance(cls);
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        }
    }

    static int zzd(Object obj, long j) {
        return zzf.zza.getInt(obj, j);
    }

    static void zze(Object obj, long j, int i) {
        zzf.zza.putInt(obj, j, i);
    }

    static long zzf(Object obj, long j) {
        return zzf.zza.getLong(obj, j);
    }

    static void zzg(Object obj, long j, long j2) {
        zzf.zza.putLong(obj, j, j2);
    }

    static boolean zzh(Object obj, long j) {
        return zzf.zzc(obj, j);
    }

    static void zzi(Object obj, long j, boolean z) {
        zzf.zzd(obj, j, z);
    }

    static float zzj(Object obj, long j) {
        return zzf.zze(obj, j);
    }

    static void zzk(Object obj, long j, float f) {
        zzf.zzf(obj, j, f);
    }

    static double zzl(Object obj, long j) {
        return zzf.zzg(obj, j);
    }

    static void zzm(Object obj, long j, double d) {
        zzf.zzh(obj, j, d);
    }

    static Object zzn(Object obj, long j) {
        return zzf.zza.getObject(obj, j);
    }

    static void zzo(Object obj, long j, Object obj2) {
        zzf.zza.putObject(obj, j, obj2);
    }

    static byte zzp(byte[] bArr, long j) {
        return zzf.zza(bArr, zza + j);
    }

    static void zzq(byte[] bArr, long j, byte b) {
        zzf.zzb(bArr, zza + j, b);
    }

    static void zzr(long j, byte[] bArr, long j2, long j3) {
        zzf.zzj(j, bArr, 0L, j3);
    }

    static byte zzs(long j) {
        return zzf.zzi(j);
    }

    static long zzt(ByteBuffer byteBuffer) {
        zzdl zzdlVar = zzf;
        return zzdlVar.zza.getLong(byteBuffer, zzi);
    }

    static Unsafe zzu() {
        try {
            return (Unsafe) AccessController.doPrivileged(new zzdh());
        } catch (Throwable unused) {
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static boolean zzv(Class cls) {
        if (!zzf.zza()) {
            return false;
        }
        try {
            Class cls2 = zzd;
            cls2.getMethod("peekLong", cls, Boolean.TYPE);
            cls2.getMethod("pokeLong", cls, Long.TYPE, Boolean.TYPE);
            cls2.getMethod("pokeInt", cls, Integer.TYPE, Boolean.TYPE);
            cls2.getMethod("peekInt", cls, Boolean.TYPE);
            cls2.getMethod("pokeByte", cls, Byte.TYPE);
            cls2.getMethod("peekByte", cls);
            cls2.getMethod("pokeByteArray", cls, byte[].class, Integer.TYPE, Integer.TYPE);
            cls2.getMethod("peekByteArray", cls, byte[].class, Integer.TYPE, Integer.TYPE);
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }
}
