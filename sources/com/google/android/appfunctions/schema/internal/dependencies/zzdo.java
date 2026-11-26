package com.google.android.appfunctions.schema.internal.dependencies;

import java.nio.ByteBuffer;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
abstract class zzdo {
    static final String zzf(ByteBuffer byteBuffer, int i, int i2) throws zzbl {
        int i3;
        if ((((byteBuffer.limit() - i) - i2) | i | i2) < 0) {
            throw new ArrayIndexOutOfBoundsException(String.format("buffer limit=%d, index=%d, limit=%d", Integer.valueOf(byteBuffer.limit()), Integer.valueOf(i), Integer.valueOf(i2)));
        }
        int i4 = i + i2;
        char[] cArr = new char[i2];
        int i5 = 0;
        while (i < i4) {
            byte b = byteBuffer.get(i);
            if (!zzdn.zza(b)) {
                break;
            }
            i++;
            cArr[i5] = (char) b;
            i5++;
        }
        int i6 = i5;
        while (i < i4) {
            int i7 = i + 1;
            byte b2 = byteBuffer.get(i);
            if (zzdn.zza(b2)) {
                cArr[i6] = (char) b2;
                i6++;
                i = i7;
                while (i < i4) {
                    byte b3 = byteBuffer.get(i);
                    if (!zzdn.zza(b3)) {
                        break;
                    }
                    i++;
                    cArr[i6] = (char) b3;
                    i6++;
                }
            } else {
                if (zzdn.zzb(b2)) {
                    if (i7 >= i4) {
                        throw new zzbl("Protocol message had invalid UTF-8.");
                    }
                    i3 = i6 + 1;
                    i += 2;
                    zzdn.zzd(b2, byteBuffer.get(i7), cArr, i6);
                } else if (zzdn.zzc(b2)) {
                    if (i7 >= i4 - 1) {
                        throw new zzbl("Protocol message had invalid UTF-8.");
                    }
                    i3 = i6 + 1;
                    int i8 = i + 2;
                    i += 3;
                    zzdn.zze(b2, byteBuffer.get(i7), byteBuffer.get(i8), cArr, i6);
                } else {
                    if (i7 >= i4 - 2) {
                        throw new zzbl("Protocol message had invalid UTF-8.");
                    }
                    byte b4 = byteBuffer.get(i7);
                    int i9 = i + 3;
                    byte b5 = byteBuffer.get(i + 2);
                    i += 4;
                    zzdn.zzf(b2, b4, b5, byteBuffer.get(i9), cArr, i6);
                    i6 += 2;
                }
                i6 = i3;
            }
        }
        return new String(cArr, 0, i6);
    }

    final boolean zza(byte[] bArr, int i, int i2) {
        return zzb(0, bArr, i, i2) == 0;
    }

    abstract int zzb(int i, byte[] bArr, int i2, int i3);

    abstract String zzc(byte[] bArr, int i, int i2) throws zzbl;

    abstract String zzd(ByteBuffer byteBuffer, int i, int i2) throws zzbl;

    abstract int zze(String str, byte[] bArr, int i, int i2);
}
