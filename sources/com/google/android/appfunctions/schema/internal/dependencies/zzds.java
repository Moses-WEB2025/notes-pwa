package com.google.android.appfunctions.schema.internal.dependencies;

import java.nio.ByteBuffer;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzds {
    private static final zzdo zza;

    static {
        zza = (zzdm.zza() && zzdm.zzb() && !zzf.zza()) ? new zzdr() : new zzdp();
    }

    static boolean zza(byte[] bArr) {
        return zza.zza(bArr, 0, bArr.length);
    }

    static boolean zzb(byte[] bArr, int i, int i2) {
        return zza.zza(bArr, i, i2);
    }

    static int zzc(String str) {
        int length = str.length();
        int i = 0;
        int i2 = 0;
        while (i2 < length && str.charAt(i2) < 128) {
            i2++;
        }
        int i3 = length;
        while (i2 < length) {
            char cCharAt = str.charAt(i2);
            if (cCharAt >= 2048) {
                int length2 = str.length();
                while (i2 < length2) {
                    char cCharAt2 = str.charAt(i2);
                    if (cCharAt2 < 2048) {
                        i += (127 - cCharAt2) >>> 31;
                    } else {
                        i += 2;
                        if (cCharAt2 >= 55296 && cCharAt2 <= 57343) {
                            if (Character.codePointAt(str, i2) < 65536) {
                                throw new zzdq(i2, length2);
                            }
                            i2++;
                        }
                    }
                    i2++;
                }
                i3 += i;
                break;
            }
            i3 += (127 - cCharAt) >>> 31;
            i2++;
        }
        if (i3 >= length) {
            return i3;
        }
        long j = ((long) i3) + 4294967296L;
        StringBuilder sb = new StringBuilder(String.valueOf(j).length() + 34);
        sb.append("UTF-8 length does not fit in int: ");
        sb.append(j);
        throw new IllegalArgumentException(sb.toString());
    }

    static int zzd(String str, byte[] bArr, int i, int i2) {
        return zza.zze(str, bArr, i, i2);
    }

    static String zze(ByteBuffer byteBuffer, int i, int i2) throws zzbl {
        zzdo zzdoVar = zza;
        if (byteBuffer.hasArray()) {
            return zzdoVar.zzc(byteBuffer.array(), byteBuffer.arrayOffset() + i, i2);
        }
        return byteBuffer.isDirect() ? zzdoVar.zzd(byteBuffer, i, i2) : zzdo.zzf(byteBuffer, i, i2);
    }

    static String zzf(byte[] bArr, int i, int i2) throws zzbl {
        return zza.zzc(bArr, i, i2);
    }

    static /* synthetic */ int zzj(byte[] bArr, int i, int i2) {
        int i3 = i2 - i;
        byte b = bArr[i - 1];
        if (i3 == 0) {
            return zzk(b);
        }
        if (i3 == 1) {
            return zzl(b, bArr[i]);
        }
        if (i3 == 2) {
            return zzm(b, bArr[i], bArr[i + 1]);
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int zzk(int i) {
        if (i > -12) {
            return -1;
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int zzl(int i, int i2) {
        if (i > -12 || i2 > -65) {
            return -1;
        }
        return i ^ (i2 << 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int zzm(int i, int i2, int i3) {
        if (i > -12 || i2 > -65 || i3 > -65) {
            return -1;
        }
        return (i ^ (i2 << 8)) ^ (i3 << 16);
    }
}
