package com.google.android.appfunctions.schema.internal.dependencies;

import com.google.common.base.Ascii;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzh {
    public static final /* synthetic */ int zza = 0;
    private static volatile int zzb = 100;

    static int zza(byte[] bArr, int i, zzg zzgVar) {
        int i2 = i + 1;
        byte b = bArr[i];
        if (b < 0) {
            return zzb(b, bArr, i2, zzgVar);
        }
        zzgVar.zza = b;
        return i2;
    }

    static int zzb(int i, byte[] bArr, int i2, zzg zzgVar) {
        byte b = bArr[i2];
        int i3 = i2 + 1;
        int i4 = i & 127;
        if (b >= 0) {
            zzgVar.zza = i4 | (b << 7);
            return i3;
        }
        int i5 = i4 | ((b & 127) << 7);
        int i6 = i2 + 2;
        byte b2 = bArr[i3];
        if (b2 >= 0) {
            zzgVar.zza = i5 | (b2 << 14);
            return i6;
        }
        int i7 = i5 | ((b2 & 127) << 14);
        int i8 = i2 + 3;
        byte b3 = bArr[i6];
        if (b3 >= 0) {
            zzgVar.zza = i7 | (b3 << Ascii.NAK);
            return i8;
        }
        int i9 = i7 | ((b3 & 127) << 21);
        int i10 = i2 + 4;
        byte b4 = bArr[i8];
        if (b4 >= 0) {
            zzgVar.zza = i9 | (b4 << Ascii.FS);
            return i10;
        }
        int i11 = i9 | ((b4 & 127) << 28);
        while (true) {
            int i12 = i10 + 1;
            if (bArr[i10] >= 0) {
                zzgVar.zza = i11;
                return i12;
            }
            i10 = i12;
        }
    }

    static int zzc(byte[] bArr, int i, zzg zzgVar) {
        long j = bArr[i];
        int i2 = i + 1;
        if (j >= 0) {
            zzgVar.zzb = j;
            return i2;
        }
        int i3 = i + 2;
        byte b = bArr[i2];
        long j2 = (j & 127) | (((long) (b & 127)) << 7);
        int i4 = 7;
        while (b < 0) {
            int i5 = i3 + 1;
            byte b2 = bArr[i3];
            i4 += 7;
            j2 |= ((long) (b2 & 127)) << i4;
            b = b2;
            i3 = i5;
        }
        zzgVar.zzb = j2;
        return i3;
    }

    static int zzd(byte[] bArr, int i) {
        int i2 = bArr[i] & 255;
        int i3 = bArr[i + 1] & 255;
        int i4 = bArr[i + 2] & 255;
        return ((bArr[i + 3] & 255) << 24) | (i3 << 8) | i2 | (i4 << 16);
    }

    static long zze(byte[] bArr, int i) {
        return (((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8) | ((((long) bArr[i + 2]) & 255) << 16) | ((((long) bArr[i + 3]) & 255) << 24) | ((((long) bArr[i + 4]) & 255) << 32) | ((((long) bArr[i + 5]) & 255) << 40) | ((((long) bArr[i + 6]) & 255) << 48) | ((((long) bArr[i + 7]) & 255) << 56);
    }

    static int zzf(byte[] bArr, int i, zzg zzgVar) throws zzbl {
        int iZza = zza(bArr, i, zzgVar);
        int i2 = zzgVar.zza;
        if (i2 < 0) {
            throw new zzbl("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
        }
        if (i2 > bArr.length - iZza) {
            throw new zzbl("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
        }
        if (i2 == 0) {
            zzgVar.zzc = zzt.zza;
            return iZza;
        }
        zzgVar.zzc = zzt.zzm(bArr, iZza, i2);
        return iZza + i2;
    }

    static int zzg(zzct zzctVar, byte[] bArr, int i, int i2, zzg zzgVar) throws IOException {
        Object objZza = zzctVar.zza();
        int iZzi = zzi(objZza, zzctVar, bArr, i, i2, zzgVar);
        zzctVar.zzk(objZza);
        zzgVar.zzc = objZza;
        return iZzi;
    }

    static int zzh(zzct zzctVar, byte[] bArr, int i, int i2, int i3, zzg zzgVar) throws IOException {
        Object objZza = zzctVar.zza();
        int iZzj = zzj(objZza, zzctVar, bArr, i, i2, i3, zzgVar);
        zzctVar.zzk(objZza);
        zzgVar.zzc = objZza;
        return iZzj;
    }

    static int zzi(Object obj, zzct zzctVar, byte[] bArr, int i, int i2, zzg zzgVar) throws IOException {
        int iZzb = i + 1;
        int i3 = bArr[i];
        if (i3 < 0) {
            iZzb = zzb(i3, bArr, iZzb, zzgVar);
            i3 = zzgVar.zza;
        }
        int i4 = iZzb;
        if (i3 < 0 || i3 > i2 - i4) {
            throw new zzbl("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
        }
        int i5 = zzgVar.zze + 1;
        zzgVar.zze = i5;
        zzo(i5);
        int i6 = i4 + i3;
        zzctVar.zzj(obj, bArr, i4, i6, zzgVar);
        zzgVar.zze--;
        zzgVar.zzc = obj;
        return i6;
    }

    static int zzj(Object obj, zzct zzctVar, byte[] bArr, int i, int i2, int i3, zzg zzgVar) throws IOException {
        int i4 = zzgVar.zze + 1;
        zzgVar.zze = i4;
        zzo(i4);
        int iZzi = ((zzci) zzctVar).zzi(obj, bArr, i, i2, i3, zzgVar);
        zzgVar.zze--;
        zzgVar.zzc = obj;
        return iZzi;
    }

    static int zzk(int i, byte[] bArr, int i2, int i3, zzbi zzbiVar, zzg zzgVar) {
        zzaz zzazVar = (zzaz) zzbiVar;
        int iZza = zza(bArr, i2, zzgVar);
        zzazVar.zzg(zzgVar.zza);
        while (iZza < i3) {
            int iZza2 = zza(bArr, iZza, zzgVar);
            if (i != zzgVar.zza) {
                break;
            }
            iZza = zza(bArr, iZza2, zzgVar);
            zzazVar.zzg(zzgVar.zza);
        }
        return iZza;
    }

    static int zzl(byte[] bArr, int i, zzbi zzbiVar, zzg zzgVar) throws IOException {
        zzaz zzazVar = (zzaz) zzbiVar;
        int iZza = zza(bArr, i, zzgVar);
        int i2 = zzgVar.zza + iZza;
        while (iZza < i2) {
            iZza = zza(bArr, iZza, zzgVar);
            zzazVar.zzg(zzgVar.zza);
        }
        if (iZza == i2) {
            return iZza;
        }
        throw new zzbl("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
    }

    static int zzm(zzct zzctVar, int i, byte[] bArr, int i2, int i3, zzbi zzbiVar, zzg zzgVar) throws IOException {
        int iZzg = zzg(zzctVar, bArr, i2, i3, zzgVar);
        zzbiVar.add(zzgVar.zzc);
        while (iZzg < i3) {
            int iZza = zza(bArr, iZzg, zzgVar);
            if (i != zzgVar.zza) {
                break;
            }
            iZzg = zzg(zzctVar, bArr, iZza, i3, zzgVar);
            zzbiVar.add(zzgVar.zzc);
        }
        return iZzg;
    }

    static int zzn(int i, byte[] bArr, int i2, int i3, zzdf zzdfVar, zzg zzgVar) throws zzbl {
        if ((i >>> 3) == 0) {
            throw new zzbl("Protocol message contained an invalid tag (zero).");
        }
        int i4 = i & 7;
        if (i4 == 0) {
            int iZzc = zzc(bArr, i2, zzgVar);
            zzdfVar.zzk(i, Long.valueOf(zzgVar.zzb));
            return iZzc;
        }
        if (i4 == 1) {
            zzdfVar.zzk(i, Long.valueOf(zze(bArr, i2)));
            return i2 + 8;
        }
        if (i4 == 2) {
            int iZza = zza(bArr, i2, zzgVar);
            int i5 = zzgVar.zza;
            if (i5 < 0) {
                throw new zzbl("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
            }
            if (i5 > bArr.length - iZza) {
                throw new zzbl("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
            }
            if (i5 == 0) {
                zzdfVar.zzk(i, zzt.zza);
            } else {
                zzdfVar.zzk(i, zzt.zzm(bArr, iZza, i5));
            }
            return iZza + i5;
        }
        if (i4 != 3) {
            if (i4 != 5) {
                throw new zzbl("Protocol message contained an invalid tag (zero).");
            }
            zzdfVar.zzk(i, Integer.valueOf(zzd(bArr, i2)));
            return i2 + 4;
        }
        int i6 = (i & (-8)) | 4;
        zzdf zzdfVarZzb = zzdf.zzb();
        int i7 = zzgVar.zze + 1;
        zzgVar.zze = i7;
        zzo(i7);
        int i8 = 0;
        while (i2 < i3) {
            int iZza2 = zza(bArr, i2, zzgVar);
            int i9 = zzgVar.zza;
            if (i9 == i6) {
                i8 = i9;
                i2 = iZza2;
                break;
            }
            i2 = zzn(i9, bArr, iZza2, i3, zzdfVarZzb, zzgVar);
            i8 = i9;
        }
        zzgVar.zze--;
        if (i2 > i3 || i8 != i6) {
            throw new zzbl("Failed to parse the message.");
        }
        zzdfVar.zzk(i, zzdfVarZzb);
        return i2;
    }

    private static void zzo(int i) throws zzbl {
        if (i >= zzb) {
            throw new zzbl("Protocol message had too many levels of nesting.  May be malicious.  Use setRecursionLimit() to increase the recursion depth limit.");
        }
    }
}
