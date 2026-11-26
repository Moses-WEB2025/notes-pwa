package com.google.android.appfunctions.schema.internal.dependencies;

import defpackage.R2;
import java.io.IOException;
import java.util.Arrays;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzdf {
    private static final zzdf zza = new zzdf(0, new int[0], new Object[0], false);
    private int zzb;
    private int[] zzc;
    private Object[] zzd;
    private int zze;
    private boolean zzf;

    private zzdf() {
        this(0, new int[8], new Object[8], true);
    }

    private zzdf(int i, int[] iArr, Object[] objArr, boolean z) {
        this.zze = -1;
        this.zzb = i;
        this.zzc = iArr;
        this.zzd = objArr;
        this.zzf = z;
    }

    public static zzdf zza() {
        return zza;
    }

    static zzdf zzb() {
        return new zzdf();
    }

    static zzdf zzc(zzdf zzdfVar, zzdf zzdfVar2) {
        int i = zzdfVar.zzb + zzdfVar2.zzb;
        int[] iArrCopyOf = Arrays.copyOf(zzdfVar.zzc, i);
        System.arraycopy(zzdfVar2.zzc, 0, iArrCopyOf, zzdfVar.zzb, zzdfVar2.zzb);
        Object[] objArrCopyOf = Arrays.copyOf(zzdfVar.zzd, i);
        System.arraycopy(zzdfVar2.zzd, 0, objArrCopyOf, zzdfVar.zzb, zzdfVar2.zzb);
        return new zzdf(i, iArrCopyOf, objArrCopyOf, true);
    }

    private final void zzn(int i) {
        int[] iArr = this.zzc;
        if (i > iArr.length) {
            int i2 = this.zzb;
            int i3 = i2 + (i2 / 2);
            if (i3 >= i) {
                i = i3;
            }
            if (i < 8) {
                i = 8;
            }
            this.zzc = Arrays.copyOf(iArr, i);
            this.zzd = Arrays.copyOf(this.zzd, i);
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof zzdf)) {
            return false;
        }
        zzdf zzdfVar = (zzdf) obj;
        int i = this.zzb;
        if (i == zzdfVar.zzb) {
            int[] iArr = this.zzc;
            int[] iArr2 = zzdfVar.zzc;
            for (int i2 = 0; i2 < i; i2++) {
                if (iArr[i2] == iArr2[i2]) {
                }
            }
            Object[] objArr = this.zzd;
            Object[] objArr2 = zzdfVar.zzd;
            int i3 = this.zzb;
            for (int i4 = 0; i4 < i3; i4++) {
                if (objArr[i4].equals(objArr2[i4])) {
                }
            }
            return true;
        }
        return false;
    }

    public final int hashCode() {
        int i = this.zzb;
        int i2 = i + R2.attr.buttonBackground;
        int[] iArr = this.zzc;
        int iHashCode = 17;
        int i3 = 17;
        for (int i4 = 0; i4 < i; i4++) {
            i3 = (i3 * 31) + iArr[i4];
        }
        int i5 = ((i2 * 31) + i3) * 31;
        Object[] objArr = this.zzd;
        int i6 = this.zzb;
        for (int i7 = 0; i7 < i6; i7++) {
            iHashCode = (iHashCode * 31) + objArr[i7].hashCode();
        }
        return i5 + iHashCode;
    }

    public final void zzd() {
        if (this.zzf) {
            this.zzf = false;
        }
    }

    final void zze() {
        if (!this.zzf) {
            throw new UnsupportedOperationException();
        }
    }

    final void zzf(zzdv zzdvVar) throws IOException {
        for (int i = 0; i < this.zzb; i++) {
            zzdvVar.zzv(this.zzc[i] >>> 3, this.zzd[i]);
        }
    }

    public final void zzg(zzdv zzdvVar) throws IOException {
        if (this.zzb != 0) {
            for (int i = 0; i < this.zzb; i++) {
                int i2 = this.zzc[i];
                Object obj = this.zzd[i];
                int i3 = i2 & 7;
                int i4 = i2 >>> 3;
                if (i3 == 0) {
                    zzdvVar.zzc(i4, ((Long) obj).longValue());
                } else if (i3 == 1) {
                    zzdvVar.zzj(i4, ((Long) obj).longValue());
                } else if (i3 == 2) {
                    zzdvVar.zzn(i4, (zzt) obj);
                } else if (i3 == 3) {
                    zzdvVar.zzt(i4);
                    ((zzdf) obj).zzg(zzdvVar);
                    zzdvVar.zzu(i4);
                } else {
                    if (i3 != 5) {
                        throw new RuntimeException(new zzbk("Protocol message tag had invalid wire type."));
                    }
                    zzdvVar.zzk(i4, ((Integer) obj).intValue());
                }
            }
        }
    }

    public final int zzh() {
        int i = this.zze;
        if (i != -1) {
            return i;
        }
        int iZzx = 0;
        for (int i2 = 0; i2 < this.zzb; i2++) {
            int i3 = this.zzc[i2] >>> 3;
            zzt zztVar = (zzt) this.zzd[i2];
            int iZzx2 = zzad.zzx(8);
            int iZzx3 = zzad.zzx(16) + zzad.zzx(i3);
            int iZzx4 = zzad.zzx(24);
            int iZzc = zztVar.zzc();
            iZzx += iZzx2 + iZzx2 + iZzx3 + iZzx4 + zzad.zzx(iZzc) + iZzc;
        }
        this.zze = iZzx;
        return iZzx;
    }

    public final int zzi() {
        int iZzx;
        int iZzy;
        int iZzx2;
        int i = this.zze;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.zzb; i3++) {
            int i4 = this.zzc[i3];
            int i5 = i4 >>> 3;
            int i6 = i4 & 7;
            if (i6 != 0) {
                if (i6 == 1) {
                    ((Long) this.zzd[i3]).longValue();
                    iZzx2 = zzad.zzx(i5 << 3) + 8;
                } else if (i6 == 2) {
                    int i7 = i5 << 3;
                    zzt zztVar = (zzt) this.zzd[i3];
                    int iZzx3 = zzad.zzx(i7);
                    int iZzc = zztVar.zzc();
                    iZzx2 = iZzx3 + zzad.zzx(iZzc) + iZzc;
                } else if (i6 == 3) {
                    int iZzx4 = zzad.zzx(i5 << 3);
                    iZzx = iZzx4 + iZzx4;
                    iZzy = ((zzdf) this.zzd[i3]).zzi();
                } else {
                    if (i6 != 5) {
                        throw new IllegalStateException(new zzbk("Protocol message tag had invalid wire type."));
                    }
                    ((Integer) this.zzd[i3]).intValue();
                    iZzx2 = zzad.zzx(i5 << 3) + 4;
                }
                i2 += iZzx2;
            } else {
                int i8 = i5 << 3;
                long jLongValue = ((Long) this.zzd[i3]).longValue();
                iZzx = zzad.zzx(i8);
                iZzy = zzad.zzy(jLongValue);
            }
            iZzx2 = iZzx + iZzy;
            i2 += iZzx2;
        }
        this.zze = i2;
        return i2;
    }

    final void zzj(StringBuilder sb, int i) {
        for (int i2 = 0; i2 < this.zzb; i2++) {
            zzch.zzb(sb, i, String.valueOf(this.zzc[i2] >>> 3), this.zzd[i2]);
        }
    }

    final void zzk(int i, Object obj) {
        zze();
        zzn(this.zzb + 1);
        int[] iArr = this.zzc;
        int i2 = this.zzb;
        iArr[i2] = i;
        this.zzd[i2] = obj;
        this.zzb = i2 + 1;
    }

    final boolean zzl(int i, zzx zzxVar) throws IOException {
        int iZza;
        zze();
        int i2 = i & 7;
        if (i2 == 0) {
            zzk(i, Long.valueOf(zzxVar.zzg()));
            return true;
        }
        if (i2 == 1) {
            zzk(i, Long.valueOf(zzxVar.zzi()));
            return true;
        }
        if (i2 == 2) {
            zzk(i, zzxVar.zzn());
            return true;
        }
        if (i2 != 3) {
            if (i2 == 4) {
                zzxVar.zzI();
                return false;
            }
            if (i2 != 5) {
                throw new zzbk("Protocol message tag had invalid wire type.");
            }
            zzk(i, Integer.valueOf(zzxVar.zzj()));
            return true;
        }
        zzdf zzdfVar = new zzdf();
        do {
            iZza = zzxVar.zza();
            if (iZza == 0) {
                break;
            }
        } while (zzdfVar.zzl(iZza, zzxVar));
        zzxVar.zzb(4 | ((i >>> 3) << 3));
        zzk(i, zzdfVar);
        return true;
    }

    final zzdf zzm(zzdf zzdfVar) {
        if (zzdfVar.equals(zza)) {
            return this;
        }
        zze();
        int i = this.zzb + zzdfVar.zzb;
        zzn(i);
        System.arraycopy(zzdfVar.zzc, 0, this.zzc, this.zzb, zzdfVar.zzb);
        System.arraycopy(zzdfVar.zzd, 0, this.zzd, this.zzb, zzdfVar.zzb);
        this.zzb = i;
        return this;
    }
}
