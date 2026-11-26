package com.google.android.appfunctions.schema.internal.dependencies;

import com.google.common.base.Ascii;
import defpackage.R2;
import java.io.IOException;
import java.nio.ByteBuffer;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzw extends zzx {
    public static final /* synthetic */ int zza = 0;
    private final ByteBuffer zzg;
    private final long zzh;
    private long zzi;
    private long zzj;
    private final long zzk;
    private int zzl;
    private int zzm;
    private int zzn;

    /* synthetic */ zzw(ByteBuffer byteBuffer, boolean z, byte[] bArr) {
        super(null);
        this.zzn = Integer.MAX_VALUE;
        this.zzg = byteBuffer.duplicate();
        long jZzt = zzdm.zzt(byteBuffer);
        this.zzh = jZzt;
        this.zzi = ((long) byteBuffer.limit()) + jZzt;
        long jPosition = jZzt + ((long) byteBuffer.position());
        this.zzj = jPosition;
        this.zzk = jPosition;
    }

    private final void zzN() {
        long j = this.zzi + ((long) this.zzl);
        this.zzi = j;
        int i = (int) (j - this.zzk);
        int i2 = this.zzn;
        if (i <= i2) {
            this.zzl = 0;
            return;
        }
        int i3 = i - i2;
        this.zzl = i3;
        this.zzi = j - ((long) i3);
    }

    private final int zzO() {
        return (int) (this.zzi - this.zzj);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzx
    public final void zzA(int i) {
        this.zzn = i;
        zzN();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzx
    public final boolean zzB() throws IOException {
        return this.zzj == this.zzi;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzx
    public final int zzC() {
        return (int) (this.zzj - this.zzk);
    }

    public final byte zzD() throws IOException {
        long j = this.zzj;
        if (j == this.zzi) {
            throw new zzbl("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
        }
        this.zzj = 1 + j;
        return zzdm.zzs(j);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzx
    public final int zza() throws IOException {
        if (zzB()) {
            this.zzm = 0;
            return 0;
        }
        int iZzu = zzu();
        this.zzm = iZzu;
        if ((iZzu >>> 3) != 0) {
            return iZzu;
        }
        throw new zzbl("Protocol message contained an invalid tag (zero).");
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzx
    public final void zzb(int i) throws zzbl {
        if (this.zzm != i) {
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
        if (iZzu <= 0 || iZzu > zzO()) {
            if (iZzu == 0) {
                return "";
            }
            if (iZzu < 0) {
                throw new zzbl("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
            }
            throw new zzbl("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
        }
        byte[] bArr = new byte[iZzu];
        long j = iZzu;
        zzdm.zzr(this.zzj, bArr, 0L, j);
        String str = new String(bArr, zzbj.zza);
        this.zzj += j;
        return str;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzx
    public final String zzm() throws IOException {
        int iZzu = zzu();
        if (iZzu > 0 && iZzu <= zzO()) {
            String strZze = zzds.zze(this.zzg, (int) (this.zzj - this.zzh), iZzu);
            this.zzj += (long) iZzu;
            return strZze;
        }
        if (iZzu == 0) {
            return "";
        }
        if (iZzu <= 0) {
            throw new zzbl("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
        }
        throw new zzbl("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzx
    public final zzt zzn() throws IOException {
        int iZzu = zzu();
        if (iZzu <= 0 || iZzu > zzO()) {
            if (iZzu == 0) {
                return zzt.zza;
            }
            if (iZzu < 0) {
                throw new zzbl("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
            }
            throw new zzbl("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
        }
        byte[] bArr = new byte[iZzu];
        long j = iZzu;
        zzdm.zzr(this.zzj, bArr, 0L, j);
        this.zzj += j;
        zzt zztVar = zzt.zza;
        return new zzr(bArr);
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
        long j = this.zzj;
        if (this.zzi - j < 4) {
            throw new zzbl("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
        }
        this.zzj = 4 + j;
        return (zzdm.zzs(j) & 255) | ((zzdm.zzs(1 + j) & 255) << 8) | ((zzdm.zzs(2 + j) & 255) << 16) | ((zzdm.zzs(j + 3) & 255) << 24);
    }

    public final long zzy() throws IOException {
        long j = this.zzj;
        if (this.zzi - j < 8) {
            throw new zzbl("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
        }
        this.zzj = 8 + j;
        long jZzs = zzdm.zzs(j);
        long jZzs2 = zzdm.zzs(1 + j);
        long jZzs3 = zzdm.zzs(2 + j);
        long jZzs4 = zzdm.zzs(3 + j);
        long jZzs5 = zzdm.zzs(4 + j);
        return ((((long) zzdm.zzs(j + 7)) & 255) << 56) | (jZzs & 255) | ((jZzs2 & 255) << 8) | ((jZzs3 & 255) << 16) | ((jZzs4 & 255) << 24) | ((jZzs5 & 255) << 32) | ((zzdm.zzs(5 + j) & 255) << 40) | ((zzdm.zzs(6 + j) & 255) << 48);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzx
    public final int zzz(int i) throws zzbl {
        if (i < 0) {
            throw new zzbl("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
        }
        int iZzC = i + zzC();
        int i2 = this.zzn;
        if (iZzC > i2) {
            throw new zzbl("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
        }
        this.zzn = iZzC;
        zzN();
        return i2;
    }

    public final void zzE(int i) throws IOException {
        if (i >= 0 && i <= zzO()) {
            this.zzj += (long) i;
        } else {
            if (i >= 0) {
                throw new zzbl("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
            }
            throw new zzbl("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzx
    public final boolean zzc(int i) throws IOException {
        int i2 = i & 7;
        int i3 = 0;
        if (i2 == 0) {
            if (zzO() < 10) {
                while (i3 < 10) {
                    if (zzD() < 0) {
                        i3++;
                    }
                }
                throw new zzbl("CodedInputStream encountered a malformed varint.");
            }
            while (i3 < 10) {
                long j = this.zzj;
                this.zzj = 1 + j;
                if (zzdm.zzs(j) < 0) {
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

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0089, code lost:
    
        if (com.google.android.appfunctions.schema.internal.dependencies.zzdm.zzs(r3) >= 0) goto L33;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int zzu() throws java.io.IOException {
        /*
            r9 = this;
            long r0 = r9.zzj
            long r2 = r9.zzi
            int r2 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r2 != 0) goto La
            goto L92
        La:
            r2 = 1
            long r2 = r2 + r0
            byte r4 = com.google.android.appfunctions.schema.internal.dependencies.zzdm.zzs(r0)
            if (r4 < 0) goto L16
            r9.zzj = r2
            return r4
        L16:
            long r5 = r9.zzi
            long r5 = r5 - r2
            r7 = 9
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 < 0) goto L92
            r5 = 2
            long r5 = r5 + r0
            byte r2 = com.google.android.appfunctions.schema.internal.dependencies.zzdm.zzs(r2)
            int r2 = r2 << 7
            r2 = r2 ^ r4
            if (r2 >= 0) goto L2e
            r0 = r2 ^ (-128(0xffffffffffffff80, float:NaN))
            goto L8f
        L2e:
            r3 = 3
            long r3 = r3 + r0
            byte r5 = com.google.android.appfunctions.schema.internal.dependencies.zzdm.zzs(r5)
            int r5 = r5 << 14
            r2 = r2 ^ r5
            if (r2 < 0) goto L3e
            r0 = r2 ^ 16256(0x3f80, float:2.278E-41)
        L3c:
            r5 = r3
            goto L8f
        L3e:
            r5 = 4
            long r5 = r5 + r0
            byte r3 = com.google.android.appfunctions.schema.internal.dependencies.zzdm.zzs(r3)
            int r3 = r3 << 21
            r2 = r2 ^ r3
            if (r2 >= 0) goto L4f
            r0 = -2080896(0xffffffffffe03f80, float:NaN)
            r0 = r0 ^ r2
            goto L8f
        L4f:
            r3 = 5
            long r3 = r3 + r0
            byte r5 = com.google.android.appfunctions.schema.internal.dependencies.zzdm.zzs(r5)
            int r6 = r5 << 28
            r2 = r2 ^ r6
            r6 = 266354560(0xfe03f80, float:2.2112565E-29)
            r2 = r2 ^ r6
            if (r5 >= 0) goto L8d
            r5 = 6
            long r5 = r5 + r0
            byte r3 = com.google.android.appfunctions.schema.internal.dependencies.zzdm.zzs(r3)
            if (r3 >= 0) goto L8b
            r3 = 7
            long r3 = r3 + r0
            byte r5 = com.google.android.appfunctions.schema.internal.dependencies.zzdm.zzs(r5)
            if (r5 >= 0) goto L8d
            r5 = 8
            long r5 = r5 + r0
            byte r3 = com.google.android.appfunctions.schema.internal.dependencies.zzdm.zzs(r3)
            if (r3 >= 0) goto L8b
            long r3 = r0 + r7
            byte r5 = com.google.android.appfunctions.schema.internal.dependencies.zzdm.zzs(r5)
            if (r5 >= 0) goto L8d
            r5 = 10
            long r5 = r5 + r0
            byte r0 = com.google.android.appfunctions.schema.internal.dependencies.zzdm.zzs(r3)
            if (r0 < 0) goto L92
        L8b:
            r0 = r2
            goto L8f
        L8d:
            r0 = r2
            goto L3c
        L8f:
            r9.zzj = r5
            return r0
        L92:
            long r0 = r9.zzw()
            int r9 = (int) r0
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.appfunctions.schema.internal.dependencies.zzw.zzu():int");
    }

    public final long zzv() throws IOException {
        long j;
        long j2;
        int i;
        long j3 = this.zzj;
        if (this.zzi != j3) {
            long j4 = 1 + j3;
            byte bZzs = zzdm.zzs(j3);
            if (bZzs >= 0) {
                this.zzj = j4;
                return bZzs;
            }
            if (this.zzi - j4 >= 9) {
                long j5 = 2 + j3;
                int iZzs = (zzdm.zzs(j4) << 7) ^ bZzs;
                if (iZzs >= 0) {
                    long j6 = 3 + j3;
                    int iZzs2 = iZzs ^ (zzdm.zzs(j5) << 14);
                    if (iZzs2 < 0) {
                        j5 = 4 + j3;
                        int iZzs3 = iZzs2 ^ (zzdm.zzs(j6) << Ascii.NAK);
                        if (iZzs3 < 0) {
                            i = (-2080896) ^ iZzs3;
                        } else {
                            j6 = 5 + j3;
                            long jZzs = (((long) zzdm.zzs(j5)) << 28) ^ ((long) iZzs3);
                            if (jZzs >= 0) {
                                j = 266354560 ^ jZzs;
                            } else {
                                long j7 = 6 + j3;
                                long jZzs2 = (((long) zzdm.zzs(j6)) << 35) ^ jZzs;
                                if (jZzs2 < 0) {
                                    j2 = -34093383808L;
                                } else {
                                    j5 = j3 + 7;
                                    long jZzs3 = jZzs2 ^ (((long) zzdm.zzs(j7)) << 42);
                                    if (jZzs3 >= 0) {
                                        j = 4363953127296L ^ jZzs3;
                                    } else {
                                        j7 = 8 + j3;
                                        jZzs2 = jZzs3 ^ (((long) zzdm.zzs(j5)) << 49);
                                        if (jZzs2 < 0) {
                                            j2 = -558586000294016L;
                                        } else {
                                            j5 = j3 + 9;
                                            long jZzs4 = (jZzs2 ^ (((long) zzdm.zzs(j7)) << 56)) ^ 71499008037633920L;
                                            if (jZzs4 < 0) {
                                                long j8 = j3 + 10;
                                                if (zzdm.zzs(j5) >= 0) {
                                                    j5 = j8;
                                                }
                                            }
                                            j = jZzs4;
                                        }
                                    }
                                }
                                j = j2 ^ jZzs2;
                                j5 = j7;
                            }
                        }
                        this.zzj = j5;
                        return j;
                    }
                    j = iZzs2 ^ R2.string.miuix_font_weight_headline2;
                    j5 = j6;
                    this.zzj = j5;
                    return j;
                }
                i = iZzs ^ (-128);
                j = i;
                this.zzj = j5;
                return j;
            }
        }
        return zzw();
    }
}
