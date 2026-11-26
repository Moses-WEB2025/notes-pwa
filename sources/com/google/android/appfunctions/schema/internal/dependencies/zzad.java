package com.google.android.appfunctions.schema.internal.dependencies;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
public abstract class zzad extends zzj {
    private static final Logger zza = Logger.getLogger(zzad.class.getName());
    private static final boolean zzb = zzdm.zza();
    public static final /* synthetic */ int zzf = 0;
    Object zze;

    private zzad() {
        throw null;
    }

    /* synthetic */ zzad(byte[] bArr) {
    }

    public static int zzA(zzcf zzcfVar) {
        int serializedSize = zzcfVar.getSerializedSize();
        return zzx(serializedSize) + serializedSize;
    }

    static int zzw(int i) {
        if (i > 4096) {
            return 4096;
        }
        return i;
    }

    public static int zzx(int i) {
        return (352 - (Integer.numberOfLeadingZeros(i) * 9)) >>> 6;
    }

    public static int zzy(long j) {
        return (640 - (Long.numberOfLeadingZeros(j) * 9)) >>> 6;
    }

    public static int zzz(String str) {
        int length;
        try {
            length = zzds.zzc(str);
        } catch (zzdq unused) {
            length = str.getBytes(zzbj.zza).length;
        }
        return zzx(length) + length;
    }

    public final void zzB() {
        if (zzb() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }

    final void zzC(String str, zzdq zzdqVar) throws IOException {
        zza.logp(Level.WARNING, "com.google.protobuf.CodedOutputStream", "inefficientWriteStringNoTag", "Converting ill-formed UTF-16. Your Protocol Buffer will not round trip correctly!", (Throwable) zzdqVar);
        byte[] bytes = str.getBytes(zzbj.zza);
        try {
            int length = bytes.length;
            zzp(length);
            zza(bytes, 0, length);
        } catch (IndexOutOfBoundsException e) {
            throw new zzab(e);
        }
    }

    public abstract void zzG(int i, int i2) throws IOException;

    public abstract void zzH(int i, int i2) throws IOException;

    public abstract void zzI(int i, int i2) throws IOException;

    public abstract void zzJ(int i, int i2) throws IOException;

    public abstract void zzK(int i, long j) throws IOException;

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzj
    public abstract void zza(byte[] bArr, int i, int i2) throws IOException;

    public abstract int zzb();

    public abstract void zzh(int i, long j) throws IOException;

    public abstract void zzi(int i, boolean z) throws IOException;

    public abstract void zzj(int i, String str) throws IOException;

    public abstract void zzk(int i, zzt zztVar) throws IOException;

    public abstract void zzl(int i, zzcf zzcfVar) throws IOException;

    public abstract void zzm(int i, zzt zztVar) throws IOException;

    public abstract void zzn(byte b) throws IOException;

    public abstract void zzo(int i) throws IOException;

    public abstract void zzp(int i) throws IOException;

    public abstract void zzq(int i) throws IOException;

    public abstract void zzr(long j) throws IOException;

    public abstract void zzs(long j) throws IOException;

    public abstract void zzv() throws IOException;
}
