package com.google.android.appfunctions.schema.internal.dependencies;

import com.google.common.base.Ascii;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import okio.Utf8;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzdr extends zzdo {
    private static int zzg(byte[] bArr, int i, long j, int i2) {
        if (i2 == 0) {
            return zzds.zzk(i);
        }
        if (i2 == 1) {
            return zzds.zzl(i, zzdm.zzp(bArr, j));
        }
        if (i2 == 2) {
            return zzds.zzm(i, zzdm.zzp(bArr, j), zzdm.zzp(bArr, j + 1));
        }
        throw new AssertionError();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdo
    final int zzb(int i, byte[] bArr, int i2, int i3) {
        int i4;
        long j;
        int length = bArr.length;
        if (((length - i3) | i2 | i3) < 0) {
            throw new ArrayIndexOutOfBoundsException(String.format("Array length=%d, index=%d, limit=%d", Integer.valueOf(length), Integer.valueOf(i2), Integer.valueOf(i3)));
        }
        long j2 = i2;
        int i5 = (int) (((long) i3) - j2);
        byte b = 0;
        long j3 = 1;
        if (i5 >= 16) {
            int i6 = (int) j2;
            long j4 = j2;
            i4 = 0;
            while (true) {
                if (i4 >= 8 - (i6 & 7)) {
                    while (true) {
                        int i7 = i4 + 8;
                        if (i7 > i5 || (zzdm.zzf(bArr, zzdm.zza + j4) & (-9187201950435737472L)) != 0) {
                            break;
                        }
                        j4 += 8;
                        i4 = i7;
                    }
                    while (true) {
                        if (i4 >= i5) {
                            i4 = i5;
                            break;
                        }
                        long j5 = j4 + 1;
                        if (zzdm.zzp(bArr, j4) < 0) {
                            break;
                        }
                        i4++;
                        j4 = j5;
                    }
                } else {
                    long j6 = j4 + 1;
                    if (zzdm.zzp(bArr, j4) < 0) {
                        break;
                    }
                    i4++;
                    j4 = j6;
                }
            }
        } else {
            i4 = 0;
        }
        long j7 = j2 + ((long) i4);
        int i8 = i5 - i4;
        while (true) {
            byte bZzp = b;
            while (i8 > 0) {
                long j8 = j7 + j3;
                bZzp = zzdm.zzp(bArr, j7);
                if (bZzp < 0) {
                    j7 = j8;
                    break;
                }
                i8--;
                j7 = j8;
            }
            if (i8 == 0) {
                return b;
            }
            int i9 = i8 - 1;
            if (bZzp < -32) {
                if (i9 == 0) {
                    return bZzp;
                }
                i8 -= 2;
                if (bZzp < -62) {
                    return -1;
                }
                long j9 = j7 + j3;
                if (zzdm.zzp(bArr, j7) > -65) {
                    return -1;
                }
                j = j3;
                j7 = j9;
            } else {
                if (bZzp >= -16) {
                    j = j3;
                    if (i9 < 3) {
                        return zzg(bArr, bZzp, j7, i9);
                    }
                    long j10 = j7 + j;
                    i8 -= 4;
                    byte bZzp2 = zzdm.zzp(bArr, j7);
                    if (bZzp2 <= -65 && (((bZzp << Ascii.FS) + (bZzp2 + 112)) >> 30) == 0) {
                        long j11 = 2 + j7;
                        if (zzdm.zzp(bArr, j10) <= -65) {
                            j7 += 3;
                            if (zzdm.zzp(bArr, j11) > -65) {
                                return -1;
                            }
                        }
                    }
                    return -1;
                }
                if (i9 < 2) {
                    return zzg(bArr, bZzp, j7, i9);
                }
                j = j3;
                long j12 = j7 + j;
                i8 -= 3;
                byte bZzp3 = zzdm.zzp(bArr, j7);
                if (bZzp3 > -65 || (bZzp == -32 && bZzp3 < -96)) {
                    return -1;
                }
                if (bZzp == -19 && bZzp3 >= -96) {
                    return -1;
                }
                j7 += 2;
                if (zzdm.zzp(bArr, j12) > -65) {
                    return -1;
                }
            }
            j3 = j;
            b = 0;
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdo
    final String zzc(byte[] bArr, int i, int i2) throws zzbl {
        Charset charset = zzbj.zza;
        String str = new String(bArr, i, i2, charset);
        if (str.indexOf(Utf8.REPLACEMENT_CODE_POINT) >= 0 && !Arrays.equals(str.getBytes(charset), Arrays.copyOfRange(bArr, i, i2 + i))) {
            throw new zzbl("Protocol message had invalid UTF-8.");
        }
        return str;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdo
    final String zzd(ByteBuffer byteBuffer, int i, int i2) throws zzbl {
        long j;
        int i3;
        long j2;
        if ((((byteBuffer.limit() - i) - i2) | i | i2) < 0) {
            throw new ArrayIndexOutOfBoundsException(String.format("buffer limit=%d, index=%d, limit=%d", Integer.valueOf(byteBuffer.limit()), Integer.valueOf(i), Integer.valueOf(i2)));
        }
        long jZzt = zzdm.zzt(byteBuffer) + ((long) i);
        long j3 = i2;
        char[] cArr = new char[i2];
        int i4 = 0;
        long j4 = jZzt;
        while (true) {
            j = jZzt + j3;
            if (j4 >= j) {
                break;
            }
            byte bZzs = zzdm.zzs(j4);
            if (!zzdn.zza(bZzs)) {
                break;
            }
            j4++;
            cArr[i4] = (char) bZzs;
            i4++;
        }
        int i5 = i4;
        while (j4 < j) {
            long j5 = j4 + 1;
            long j6 = j4;
            byte bZzs2 = zzdm.zzs(j6);
            if (zzdn.zza(bZzs2)) {
                int i6 = i5 + 1;
                cArr[i5] = (char) bZzs2;
                while (j5 < j) {
                    byte bZzs3 = zzdm.zzs(j5);
                    if (!zzdn.zza(bZzs3)) {
                        break;
                    }
                    j5++;
                    cArr[i6] = (char) bZzs3;
                    i6++;
                }
                j4 = j5;
                i5 = i6;
            } else {
                if (zzdn.zzb(bZzs2)) {
                    if (j5 >= j) {
                        throw new zzbl("Protocol message had invalid UTF-8.");
                    }
                    i3 = i5 + 1;
                    j2 = j6 + 2;
                    zzdn.zzd(bZzs2, zzdm.zzs(j5), cArr, i5);
                } else if (zzdn.zzc(bZzs2)) {
                    if (j5 >= j - 1) {
                        throw new zzbl("Protocol message had invalid UTF-8.");
                    }
                    i3 = i5 + 1;
                    long j7 = j6 + 2;
                    j2 = j6 + 3;
                    zzdn.zze(bZzs2, zzdm.zzs(j5), zzdm.zzs(j7), cArr, i5);
                } else {
                    if (j5 >= j - 2) {
                        throw new zzbl("Protocol message had invalid UTF-8.");
                    }
                    zzdn.zzf(bZzs2, zzdm.zzs(j5), zzdm.zzs(j6 + 2), zzdm.zzs(j6 + 3), cArr, i5);
                    i5 += 2;
                    j4 = j6 + 4;
                }
                j4 = j2;
                i5 = i3;
            }
        }
        return new String(cArr, 0, i5);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdo
    final int zze(String str, byte[] bArr, int i, int i2) {
        byte[] bytes = str.getBytes(zzbj.zza);
        int length = bytes.length;
        if (length - i > i2) {
            throw new ArrayIndexOutOfBoundsException("Not enough space in output buffer to encode UTF-8 string");
        }
        System.arraycopy(bytes, 0, bArr, i, length);
        return i + length;
    }
}
