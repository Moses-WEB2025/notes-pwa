package com.google.android.appfunctions.schema.internal.dependencies;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzax {
    public static final zzax zza;
    public static final zzax zzb;
    public static final zzax zzc;
    public static final zzax zzd;
    public static final zzax zze;
    public static final zzax zzf;
    public static final zzax zzg;
    private static final /* synthetic */ zzax[] zzh;

    static {
        zzax zzaxVar = new zzax("GET_MEMOIZED_IS_INITIALIZED", 0);
        zza = zzaxVar;
        zzax zzaxVar2 = new zzax("SET_MEMOIZED_IS_INITIALIZED", 1);
        zzb = zzaxVar2;
        zzax zzaxVar3 = new zzax("BUILD_MESSAGE_INFO", 2);
        zzc = zzaxVar3;
        zzax zzaxVar4 = new zzax("NEW_MUTABLE_INSTANCE", 3);
        zzd = zzaxVar4;
        zzax zzaxVar5 = new zzax("NEW_BUILDER", 4);
        zze = zzaxVar5;
        zzax zzaxVar6 = new zzax("GET_DEFAULT_INSTANCE", 5);
        zzf = zzaxVar6;
        zzax zzaxVar7 = new zzax("GET_PARSER", 6);
        zzg = zzaxVar7;
        zzh = new zzax[]{zzaxVar, zzaxVar2, zzaxVar3, zzaxVar4, zzaxVar5, zzaxVar6, zzaxVar7};
    }

    public static zzax[] values() {
        return (zzax[]) zzh.clone();
    }
}
