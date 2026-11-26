package com.google.android.appfunctions.schema.internal.dependencies;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Locale;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
public abstract class zzt implements Iterable<Byte>, Serializable {
    public static final zzt zza = new zzr(zzbj.zzb);
    private static final zzo zzb;
    private int zzc = 0;

    static {
        zzb = zzf.zza() ? new zzs(null) : new zzm(null);
    }

    zzt() {
    }

    public static zzt zzm(byte[] bArr, int i, int i2) {
        try {
            return zzn(bArr, i, i2, false);
        } catch (zzbl e) {
            throw new AssertionError("Expected no InvalidProtocolBufferException as data UTF8 validity is not checked.", e);
        }
    }

    static zzt zzn(byte[] bArr, int i, int i2, boolean z) throws zzbl {
        zzq(i, i + i2, bArr.length);
        return new zzr(zzb.zza(bArr, i, i2));
    }

    public static zzt zzo(String str) {
        return new zzr(str.getBytes(zzbj.zza));
    }

    static /* synthetic */ boolean zzr(byte[] bArr, int i, byte[] bArr2, int i2, int i3) {
        int i4 = i + i3;
        zzq(i, i4, bArr.length);
        zzq(i2, i3 + i2, bArr2.length);
        while (i < i4) {
            if (bArr[i] != bArr2[i2]) {
                return false;
            }
            i++;
            i2++;
        }
        return true;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzt)) {
            return false;
        }
        zzt zztVar = (zzt) obj;
        int iZzc = zzc();
        if (iZzc != zztVar.zzc()) {
            return false;
        }
        if (iZzc == 0) {
            return true;
        }
        int i = this.zzc;
        int i2 = zztVar.zzc;
        if (i == 0 || i2 == 0 || i == i2) {
            return zzh(zztVar);
        }
        return false;
    }

    public final int hashCode() {
        int iZzi = this.zzc;
        if (iZzi == 0) {
            int iZzc = zzc();
            iZzi = zzi(iZzc, 0, iZzc);
            if (iZzi == 0) {
                iZzi = 1;
            }
            this.zzc = iZzi;
        }
        return iZzi;
    }

    @Override // java.lang.Iterable
    public final /* synthetic */ Iterator<Byte> iterator() {
        return new zzk(this);
    }

    public final String toString() {
        return String.format(Locale.ROOT, "<ByteString@%s size=%d contents=\"%s\">", Integer.toHexString(System.identityHashCode(this)), Integer.valueOf(zzc()), zzc() <= 50 ? zzdc.zza(this) : zzdc.zza(zzd(0, 47)).concat("..."));
    }

    public abstract byte zza(int i);

    abstract byte zzb(int i);

    public abstract int zzc();

    public abstract zzt zzd(int i, int i2);

    abstract void zze(zzj zzjVar) throws IOException;

    protected abstract String zzf(Charset charset);

    public abstract boolean zzg();

    protected abstract boolean zzh(zzt zztVar);

    protected abstract int zzi(int i, int i2, int i3);

    public abstract zzx zzj();

    public final String zzp() {
        return zzc() == 0 ? "" : zzf(zzbj.zza);
    }

    static int zzq(int i, int i2, int i3) {
        int i4 = i2 - i;
        if ((i | i2 | i4 | (i3 - i2)) >= 0) {
            return i4;
        }
        if (i < 0) {
            StringBuilder sb = new StringBuilder(String.valueOf(i).length() + 21);
            sb.append("Beginning index: ");
            sb.append(i);
            sb.append(" < 0");
            throw new IndexOutOfBoundsException(sb.toString());
        }
        if (i2 < i) {
            StringBuilder sb2 = new StringBuilder(String.valueOf(i).length() + 44 + String.valueOf(i2).length());
            sb2.append("Beginning index larger than ending index: ");
            sb2.append(i);
            sb2.append(", ");
            sb2.append(i2);
            throw new IndexOutOfBoundsException(sb2.toString());
        }
        StringBuilder sb3 = new StringBuilder(String.valueOf(i2).length() + 15 + String.valueOf(i3).length());
        sb3.append("End index: ");
        sb3.append(i2);
        sb3.append(" >= ");
        sb3.append(i3);
        throw new IndexOutOfBoundsException(sb3.toString());
    }
}
