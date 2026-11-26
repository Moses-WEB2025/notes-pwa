package com.google.android.appfunctions.schema.internal.dependencies;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzg {
    public int zza;
    public long zzb;
    public Object zzc;
    public final zzaj zzd;
    public int zze;

    zzg(zzaj zzajVar) {
        zzajVar.getClass();
        this.zzd = zzajVar;
    }

    static /* synthetic */ String zza(int i, int i2, byte b, String str, String str2) {
        StringBuilder sb = new StringBuilder(String.valueOf(i2).length() + b + String.valueOf(i).length());
        sb.append(str);
        sb.append(i2);
        sb.append(str2);
        sb.append(i);
        return sb.toString();
    }
}
