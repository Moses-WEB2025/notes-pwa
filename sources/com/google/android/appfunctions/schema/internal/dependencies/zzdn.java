package com.google.android.appfunctions.schema.internal.dependencies;

import com.google.common.base.Ascii;
import defpackage.R2;
import okio.Utf8;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzdn {
    static /* synthetic */ boolean zza(byte b) {
        return b >= 0;
    }

    static /* synthetic */ boolean zzb(byte b) {
        return b < -32;
    }

    static /* synthetic */ boolean zzc(byte b) {
        return b < -16;
    }

    static /* synthetic */ void zzd(byte b, byte b2, char[] cArr, int i) throws zzbl {
        if (b < -62 || zzg(b2)) {
            throw new zzbl("Protocol message had invalid UTF-8.");
        }
        cArr[i] = (char) (((b & Ascii.US) << 6) | (b2 & Utf8.REPLACEMENT_BYTE));
    }

    static /* synthetic */ void zzf(byte b, byte b2, byte b3, byte b4, char[] cArr, int i) throws zzbl {
        if (zzg(b2) || (((b << Ascii.FS) + (b2 + 112)) >> 30) != 0 || zzg(b3) || zzg(b4)) {
            throw new zzbl("Protocol message had invalid UTF-8.");
        }
        int i2 = ((b & 7) << 18) | ((b2 & Utf8.REPLACEMENT_BYTE) << 12) | ((b3 & Utf8.REPLACEMENT_BYTE) << 6) | (b4 & Utf8.REPLACEMENT_BYTE);
        cArr[i] = (char) ((i2 >>> 10) + Utf8.HIGH_SURROGATE_HEADER);
        cArr[i + 1] = (char) ((i2 & R2.attr.endActionMenuEnabled) + Utf8.LOG_SURROGATE_HEADER);
    }

    private static boolean zzg(byte b) {
        return b > -65;
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0013 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:11:0x0015  */
    /* JADX WARN: Code duplicated, block: B:12:0x0016 A[PHI: r2
  0x0016: PHI (r2v3 byte) = (r2v2 byte), (r2v9 byte) binds: [B:9:0x0011, B:11:0x0015] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:14:0x001c  */
    static /* synthetic */ void zze(byte b, byte b2, byte b3, char[] cArr, int i) throws zzbl {
        if (!zzg(b2)) {
            if (b != -32) {
                if (b != -19) {
                    if (!zzg(b3)) {
                        cArr[i] = (char) (((b & 15) << 12) | ((b2 & Utf8.REPLACEMENT_BYTE) << 6) | (b3 & Utf8.REPLACEMENT_BYTE));
                        return;
                    }
                } else if (b2 < -96) {
                    b = -19;
                    if (!zzg(b3)) {
                        cArr[i] = (char) (((b & 15) << 12) | ((b2 & Utf8.REPLACEMENT_BYTE) << 6) | (b3 & Utf8.REPLACEMENT_BYTE));
                        return;
                    }
                }
            } else if (b2 >= -96) {
                b = -32;
                if (b != -19) {
                    if (!zzg(b3)) {
                        cArr[i] = (char) (((b & 15) << 12) | ((b2 & Utf8.REPLACEMENT_BYTE) << 6) | (b3 & Utf8.REPLACEMENT_BYTE));
                        return;
                    }
                } else if (b2 < -96) {
                    b = -19;
                    if (!zzg(b3)) {
                        cArr[i] = (char) (((b & 15) << 12) | ((b2 & Utf8.REPLACEMENT_BYTE) << 6) | (b3 & Utf8.REPLACEMENT_BYTE));
                        return;
                    }
                }
            }
        }
        throw new zzbl("Protocol message had invalid UTF-8.");
    }
}
