package com.google.android.appfunctions.schema.internal.dependencies;

import com.google.common.base.Ascii;
import defpackage.R2;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzv extends zzx {
    private final InputStream zza;
    private final byte[] zzg;
    private int zzh;
    private int zzi;
    private int zzj;
    private int zzk;
    private int zzl;
    private int zzm;

    /* synthetic */ zzv(InputStream inputStream, int i, byte[] bArr) {
        super(null);
        this.zzm = Integer.MAX_VALUE;
        byte[] bArr2 = zzbj.zzb;
        this.zza = inputStream;
        this.zzg = new byte[4096];
        this.zzh = 0;
        this.zzj = 0;
        this.zzl = 0;
    }

    private final void zzN() {
        int i = this.zzh + this.zzi;
        this.zzh = i;
        int i2 = this.zzl + i;
        int i3 = this.zzm;
        if (i2 <= i3) {
            this.zzi = 0;
            return;
        }
        int i4 = i2 - i3;
        this.zzi = i4;
        this.zzh = i - i4;
    }

    private final void zzO(int i) throws IOException {
        if (zzP(i)) {
            return;
        }
        if (i <= (Integer.MAX_VALUE - this.zzl) - this.zzj) {
            throw new zzbl("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
        }
        throw new zzbl("Protocol message was too large.  May be malicious.  Use CodedInputStream.setSizeLimit() to increase the size limit. If reading multiple messages, consider resetting the counter between each message using CodedInputStream.resetSizeCounter().");
    }

    private final boolean zzP(int i) throws IOException {
        int i2 = this.zzj;
        int i3 = i2 + i;
        int i4 = this.zzh;
        if (i3 <= i4) {
            StringBuilder sb = new StringBuilder(String.valueOf(i).length() + 66);
            sb.append("refillBuffer() called when ");
            sb.append(i);
            sb.append(" bytes were already available in buffer");
            throw new IllegalStateException(sb.toString());
        }
        int i5 = this.zzl;
        if (i > (Integer.MAX_VALUE - i5) - i2 || i5 + i2 + i > this.zzm) {
            return false;
        }
        if (i2 > 0) {
            if (i4 > i2) {
                byte[] bArr = this.zzg;
                System.arraycopy(bArr, i2, bArr, 0, i4 - i2);
            }
            i5 = this.zzl + i2;
            this.zzl = i5;
            i4 = this.zzh - i2;
            this.zzh = i4;
            this.zzj = 0;
        }
        try {
            int i6 = this.zza.read(this.zzg, i4, Math.min(4096 - i4, (Integer.MAX_VALUE - i5) - i4));
            if (i6 != 0 && i6 >= -1 && i6 <= 4096) {
                if (i6 <= 0) {
                    return false;
                }
                this.zzh += i6;
                zzN();
                return this.zzh >= i || zzP(i);
            }
            String strValueOf = String.valueOf(this.zza.getClass());
            StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf).length() + 39 + String.valueOf(i6).length() + 41);
            sb2.append(strValueOf);
            sb2.append("#read(byte[]) returned invalid result: ");
            sb2.append(i6);
            sb2.append("\nThe InputStream implementation is buggy.");
            throw new IllegalStateException(sb2.toString());
        } catch (zzbl e) {
            e.zza();
            throw e;
        }
    }

    private final byte[] zzQ(int i, boolean z) throws IOException {
        byte[] bArrZzR = zzR(i);
        if (bArrZzR != null) {
            return bArrZzR;
        }
        int i2 = this.zzj;
        int i3 = this.zzh;
        int i4 = i3 - i2;
        this.zzl += i3;
        this.zzj = 0;
        this.zzh = 0;
        List<byte[]> listZzS = zzS(i - i4);
        byte[] bArr = new byte[i];
        System.arraycopy(this.zzg, i2, bArr, 0, i4);
        for (byte[] bArr2 : listZzS) {
            int length = bArr2.length;
            System.arraycopy(bArr2, 0, bArr, i4, length);
            i4 += length;
        }
        return bArr;
    }

    private final byte[] zzR(int i) throws IOException {
        if (i == 0) {
            return zzbj.zzb;
        }
        int i2 = this.zzl;
        int i3 = this.zzj;
        int i4 = i2 + i3 + i;
        if ((-2147483647) + i4 > 0) {
            throw new zzbl("Protocol message was too large.  May be malicious.  Use CodedInputStream.setSizeLimit() to increase the size limit. If reading multiple messages, consider resetting the counter between each message using CodedInputStream.resetSizeCounter().");
        }
        int i5 = this.zzm;
        if (i4 > i5) {
            zzE((i5 - i2) - i3);
            throw new zzbl("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
        }
        int i6 = this.zzh - i3;
        int i7 = i - i6;
        if (i7 >= 4096) {
            try {
                if (i7 > this.zza.available()) {
                    return null;
                }
            } catch (zzbl e) {
                e.zza();
                throw e;
            }
        }
        byte[] bArr = new byte[i];
        System.arraycopy(this.zzg, this.zzj, bArr, 0, i6);
        this.zzl += this.zzh;
        this.zzj = 0;
        this.zzh = 0;
        while (i6 < i) {
            try {
                int i8 = this.zza.read(bArr, i6, i - i6);
                if (i8 == -1) {
                    throw new zzbl("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
                }
                this.zzl += i8;
                i6 += i8;
            } catch (zzbl e2) {
                e2.zza();
                throw e2;
            }
        }
        return bArr;
    }

    private final List zzS(int i) throws IOException {
        ArrayList arrayList = new ArrayList();
        while (i > 0) {
            int iMin = Math.min(i, 4096);
            byte[] bArr = new byte[iMin];
            int i2 = 0;
            while (i2 < iMin) {
                int i3 = this.zza.read(bArr, i2, iMin - i2);
                if (i3 == -1) {
                    throw new zzbl("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
                }
                this.zzl += i3;
                i2 += i3;
            }
            i -= iMin;
            arrayList.add(bArr);
        }
        return arrayList;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzx
    public final void zzA(int i) {
        this.zzm = i;
        zzN();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzx
    public final boolean zzB() throws IOException {
        return this.zzj == this.zzh && !zzP(1);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzx
    public final int zzC() {
        return this.zzl + this.zzj;
    }

    public final byte zzD() throws IOException {
        if (this.zzj == this.zzh) {
            zzO(1);
        }
        byte[] bArr = this.zzg;
        int i = this.zzj;
        this.zzj = i + 1;
        return bArr[i];
    }

    public final void zzE(int i) throws IOException {
        int i2 = this.zzh;
        int i3 = this.zzj;
        int i4 = i2 - i3;
        if (i <= i4 && i >= 0) {
            this.zzj = i3 + i;
            return;
        }
        if (i < 0) {
            throw new zzbl("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
        }
        int i5 = this.zzl;
        int i6 = i5 + i3;
        int i7 = this.zzm;
        if (i6 + i > i7) {
            zzE((i7 - i5) - i3);
            throw new zzbl("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
        }
        this.zzl = i6;
        this.zzh = 0;
        this.zzj = 0;
        while (i4 < i) {
            try {
                long j = i - i4;
                try {
                    long jSkip = this.zza.skip(j);
                    if (jSkip < 0 || jSkip > j) {
                        String strValueOf = String.valueOf(this.zza.getClass());
                        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 31 + String.valueOf(jSkip).length() + 41);
                        sb.append(strValueOf);
                        sb.append("#skip returned invalid result: ");
                        sb.append(jSkip);
                        sb.append("\nThe InputStream implementation is buggy.");
                        throw new IllegalStateException(sb.toString());
                    }
                    if (jSkip == 0) {
                        break;
                    } else {
                        i4 += (int) jSkip;
                    }
                } catch (zzbl e) {
                    e.zza();
                    throw e;
                }
            } catch (Throwable th) {
                this.zzl += i4;
                zzN();
                throw th;
            }
        }
        this.zzl += i4;
        zzN();
        if (i4 >= i) {
            return;
        }
        int i8 = this.zzh;
        int i9 = i8 - this.zzj;
        this.zzj = i8;
        zzO(1);
        while (true) {
            int i10 = i - i9;
            int i11 = this.zzh;
            if (i10 <= i11) {
                this.zzj = i10;
                return;
            } else {
                i9 += i11;
                this.zzj = i11;
                zzO(1);
            }
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzx
    public final int zza() throws IOException {
        if (zzB()) {
            this.zzk = 0;
            return 0;
        }
        int iZzu = zzu();
        this.zzk = iZzu;
        if ((iZzu >>> 3) != 0) {
            return iZzu;
        }
        throw new zzbl("Protocol message contained an invalid tag (zero).");
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzx
    public final void zzb(int i) throws zzbl {
        if (this.zzk != i) {
            throw new zzbl("Protocol message end-group tag did not match expected tag.");
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzx
    public final double zzd() throws IOException {
        return Double.longBitsToDouble(zzy());
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzx
    public final float zze() throws IOException {
        return Float.intBitsToFloat(zzx());
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzx
    public final long zzf() throws IOException {
        return zzv();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzx
    public final long zzg() throws IOException {
        return zzv();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzx
    public final int zzh() throws IOException {
        return zzu();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzx
    public final long zzi() throws IOException {
        return zzy();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzx
    public final int zzj() throws IOException {
        return zzx();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzx
    public final boolean zzk() throws IOException {
        return zzv() != 0;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzx
    public final String zzl() throws IOException {
        int iZzu = zzu();
        if (iZzu > 0) {
            int i = this.zzh;
            int i2 = this.zzj;
            if (iZzu <= i - i2) {
                String str = new String(this.zzg, i2, iZzu, zzbj.zza);
                this.zzj += iZzu;
                return str;
            }
        }
        if (iZzu == 0) {
            return "";
        }
        if (iZzu < 0) {
            throw new zzbl("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
        }
        if (iZzu > this.zzh) {
            return new String(zzQ(iZzu, false), zzbj.zza);
        }
        zzO(iZzu);
        String str2 = new String(this.zzg, this.zzj, iZzu, zzbj.zza);
        this.zzj += iZzu;
        return str2;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzx
    public final String zzm() throws IOException {
        byte[] bArrZzQ;
        int iZzu = zzu();
        int i = this.zzj;
        int i2 = this.zzh;
        if (iZzu <= i2 - i && iZzu > 0) {
            bArrZzQ = this.zzg;
            this.zzj = i + iZzu;
        } else {
            if (iZzu == 0) {
                return "";
            }
            if (iZzu < 0) {
                throw new zzbl("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
            }
            i = 0;
            if (iZzu <= i2) {
                zzO(iZzu);
                bArrZzQ = this.zzg;
                this.zzj = iZzu;
            } else {
                bArrZzQ = zzQ(iZzu, false);
            }
        }
        return zzds.zzf(bArrZzQ, i, iZzu);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzx
    public final zzt zzn() throws IOException {
        int iZzu = zzu();
        int i = this.zzh;
        int i2 = this.zzj;
        if (iZzu <= i - i2 && iZzu > 0) {
            zzt zztVarZzn = zzt.zzn(this.zzg, i2, iZzu, false);
            this.zzj += iZzu;
            return zztVarZzn;
        }
        if (iZzu == 0) {
            return zzt.zza;
        }
        if (iZzu < 0) {
            throw new zzbl("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
        }
        byte[] bArrZzR = zzR(iZzu);
        if (bArrZzR != null) {
            return zzt.zzn(bArrZzR, 0, bArrZzR.length, false);
        }
        int i3 = this.zzj;
        int i4 = this.zzh;
        int i5 = i4 - i3;
        this.zzl += i4;
        this.zzj = 0;
        this.zzh = 0;
        List<byte[]> listZzS = zzS(iZzu - i5);
        byte[] bArr = new byte[iZzu];
        System.arraycopy(this.zzg, i3, bArr, 0, i5);
        for (byte[] bArr2 : listZzS) {
            int length = bArr2.length;
            System.arraycopy(bArr2, 0, bArr, i5, length);
            i5 += length;
        }
        zzt zztVar = zzt.zza;
        try {
            return new zzr(bArr);
        } catch (zzbl e) {
            throw new AssertionError("Expected no InvalidProtocolBufferException as data UTF8 validity is not checked.", e);
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzx
    public final int zzo() throws IOException {
        return zzu();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzx
    public final int zzp() throws IOException {
        return zzu();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzx
    public final int zzq() throws IOException {
        return zzx();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzx
    public final long zzr() throws IOException {
        return zzy();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzx
    public final int zzs() throws IOException {
        return zzK(zzu());
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzx
    public final long zzt() throws IOException {
        return zzL(zzv());
    }

    final long zzw() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte bZzD = zzD();
            j |= ((long) (bZzD & 127)) << i;
            if ((bZzD & 128) == 0) {
                return j;
            }
        }
        throw new zzbl("CodedInputStream encountered a malformed varint.");
    }

    public final int zzx() throws IOException {
        int i = this.zzj;
        if (this.zzh - i < 4) {
            zzO(4);
            i = this.zzj;
        }
        byte[] bArr = this.zzg;
        this.zzj = i + 4;
        return (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16) | ((bArr[i + 3] & 255) << 24);
    }

    public final long zzy() throws IOException {
        int i = this.zzj;
        if (this.zzh - i < 8) {
            zzO(8);
            i = this.zzj;
        }
        byte[] bArr = this.zzg;
        this.zzj = i + 8;
        long j = bArr[i];
        long j2 = (((long) bArr[i + 1]) & 255) << 8;
        long j3 = bArr[i + 2];
        long j4 = bArr[i + 3];
        return ((((long) bArr[i + 6]) & 255) << 48) | (j & 255) | j2 | ((j3 & 255) << 16) | ((j4 & 255) << 24) | ((bArr[i + 4] & 255) << 32) | ((bArr[i + 5] & 255) << 40) | ((((long) bArr[i + 7]) & 255) << 56);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzx
    public final int zzz(int i) throws zzbl {
        if (i < 0) {
            throw new zzbl("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
        }
        int i2 = i + this.zzl + this.zzj;
        if (i2 < 0) {
            throw new zzbl("Protocol message was too large.  May be malicious.  Use CodedInputStream.setSizeLimit() to increase the size limit. If reading multiple messages, consider resetting the counter between each message using CodedInputStream.resetSizeCounter().");
        }
        int i3 = this.zzm;
        if (i2 > i3) {
            throw new zzbl("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
        }
        this.zzm = i2;
        zzN();
        return i3;
    }

    public final int zzu() throws IOException {
        int i;
        int i2 = this.zzj;
        int i3 = this.zzh;
        if (i3 != i2) {
            byte[] bArr = this.zzg;
            int i4 = i2 + 1;
            byte b = bArr[i2];
            if (b >= 0) {
                this.zzj = i4;
                return b;
            }
            if (i3 - i4 >= 9) {
                int i5 = i2 + 2;
                int i6 = (bArr[i4] << 7) ^ b;
                if (i6 < 0) {
                    i = i6 ^ (-128);
                } else {
                    int i7 = i2 + 3;
                    int i8 = (bArr[i5] << 14) ^ i6;
                    if (i8 >= 0) {
                        i = i8 ^ R2.string.miuix_font_weight_headline2;
                    } else {
                        int i9 = i2 + 4;
                        int i10 = i8 ^ (bArr[i7] << Ascii.NAK);
                        if (i10 < 0) {
                            i = (-2080896) ^ i10;
                        } else {
                            i7 = i2 + 5;
                            byte b2 = bArr[i9];
                            int i11 = (i10 ^ (b2 << Ascii.FS)) ^ 266354560;
                            if (b2 < 0) {
                                i9 = i2 + 6;
                                if (bArr[i7] < 0) {
                                    i7 = i2 + 7;
                                    if (bArr[i9] < 0) {
                                        i9 = i2 + 8;
                                        if (bArr[i7] < 0) {
                                            i7 = i2 + 9;
                                            if (bArr[i9] < 0) {
                                                int i12 = i2 + 10;
                                                if (bArr[i7] >= 0) {
                                                    i5 = i12;
                                                    i = i11;
                                                }
                                            }
                                        }
                                    }
                                }
                                i = i11;
                            }
                            i = i11;
                        }
                        i5 = i9;
                    }
                    i5 = i7;
                }
                this.zzj = i5;
                return i;
            }
        }
        return (int) zzw();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzx
    public final boolean zzc(int i) throws IOException {
        int i2 = i & 7;
        int i3 = 0;
        if (i2 == 0) {
            if (this.zzh - this.zzj < 10) {
                while (i3 < 10) {
                    if (zzD() < 0) {
                        i3++;
                    }
                }
                throw new zzbl("CodedInputStream encountered a malformed varint.");
            }
            while (i3 < 10) {
                byte[] bArr = this.zzg;
                int i4 = this.zzj;
                this.zzj = i4 + 1;
                if (bArr[i4] < 0) {
                    i3++;
                }
            }
            throw new zzbl("CodedInputStream encountered a malformed varint.");
            return true;
        }
        if (i2 == 1) {
            zzE(8);
            return true;
        }
        if (i2 == 2) {
            zzE(zzu());
            return true;
        }
        if (i2 == 3) {
            zzJ();
            zzb(((i >>> 3) << 3) | 4);
            return true;
        }
        if (i2 == 4) {
            zzI();
            return false;
        }
        if (i2 != 5) {
            throw new zzbk("Protocol message tag had invalid wire type.");
        }
        zzE(4);
        return true;
    }

    public final long zzv() throws IOException {
        long j;
        long j2;
        int i = this.zzj;
        int i2 = this.zzh;
        if (i2 != i) {
            byte[] bArr = this.zzg;
            int i3 = i + 1;
            byte b = bArr[i];
            if (b >= 0) {
                this.zzj = i3;
                return b;
            }
            if (i2 - i3 >= 9) {
                int i4 = i + 2;
                int i5 = (bArr[i3] << 7) ^ b;
                if (i5 < 0) {
                    j = i5 ^ (-128);
                } else {
                    int i6 = i + 3;
                    int i7 = (bArr[i4] << 14) ^ i5;
                    if (i7 >= 0) {
                        j = i7 ^ R2.string.miuix_font_weight_headline2;
                    } else {
                        int i8 = i + 4;
                        int i9 = i7 ^ (bArr[i6] << Ascii.NAK);
                        if (i9 < 0) {
                            long j3 = (-2080896) ^ i9;
                            i4 = i8;
                            j = j3;
                        } else {
                            i6 = i + 5;
                            long j4 = (((long) bArr[i8]) << 28) ^ ((long) i9);
                            if (j4 >= 0) {
                                j = j4 ^ 266354560;
                            } else {
                                i4 = i + 6;
                                long j5 = (((long) bArr[i6]) << 35) ^ j4;
                                if (j5 < 0) {
                                    j2 = -34093383808L;
                                } else {
                                    int i10 = i + 7;
                                    long j6 = j5 ^ (((long) bArr[i4]) << 42);
                                    if (j6 >= 0) {
                                        j = j6 ^ 4363953127296L;
                                    } else {
                                        i4 = i + 8;
                                        j5 = j6 ^ (((long) bArr[i10]) << 49);
                                        if (j5 < 0) {
                                            j2 = -558586000294016L;
                                        } else {
                                            i10 = i + 9;
                                            long j7 = (j5 ^ (((long) bArr[i4]) << 56)) ^ 71499008037633920L;
                                            if (j7 < 0) {
                                                i4 = i + 10;
                                                if (bArr[i10] >= 0) {
                                                    j = j7;
                                                }
                                            } else {
                                                j = j7;
                                            }
                                        }
                                    }
                                    i4 = i10;
                                }
                                j = j5 ^ j2;
                            }
                        }
                    }
                    i4 = i6;
                }
                this.zzj = i4;
                return j;
            }
        }
        return zzw();
    }
}
