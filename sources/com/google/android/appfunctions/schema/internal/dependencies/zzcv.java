package com.google.android.appfunctions.schema.internal.dependencies;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzcv {
    public static final /* synthetic */ int zza = 0;
    private static final Class zzb;
    private static final zzde zzc;
    private static final zzde zzd;

    static {
        Class<?> cls;
        Class<?> cls2;
        int i = zzf.zza;
        zzde zzdeVar = null;
        try {
            cls = Class.forName("com.google.protobuf.GeneratedMessage");
        } catch (Throwable unused) {
            cls = null;
        }
        zzb = cls;
        try {
            cls2 = Class.forName("com.google.protobuf.UnknownFieldSetSchema");
        } catch (Throwable unused2) {
            cls2 = null;
        }
        if (cls2 != null) {
            try {
                zzdeVar = (zzde) cls2.getConstructor(new Class[0]).newInstance(new Object[0]);
            } catch (Throwable unused3) {
            }
        }
        zzc = zzdeVar;
        zzd = new zzdg();
    }

    static int zzA(List list) {
        return list.size() * 4;
    }

    static int zzB(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * (zzad.zzx(i << 3) + 4);
    }

    static int zzC(List list) {
        return list.size() * 8;
    }

    static int zzD(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * (zzad.zzx(i << 3) + 8);
    }

    static int zzE(int i, Object obj, zzct zzctVar) {
        int iZzx;
        int serializedSize;
        int iZzx2;
        int i2 = i << 3;
        if (obj instanceof zzbq) {
            iZzx = zzad.zzx(i2);
            serializedSize = ((zzbq) obj).zzb();
            iZzx2 = zzad.zzx(serializedSize);
        } else {
            iZzx = zzad.zzx(i2);
            serializedSize = ((zzc) obj).getSerializedSize(zzctVar);
            iZzx2 = zzad.zzx(serializedSize);
        }
        return iZzx + iZzx2 + serializedSize;
    }

    @Deprecated
    static int zzF(int i, zzcf zzcfVar, zzct zzctVar) {
        int iZzx = zzad.zzx(i << 3);
        return iZzx + iZzx + ((zzc) zzcfVar).getSerializedSize(zzctVar);
    }

    public static zzde zzG() {
        return zzc;
    }

    public static zzde zzH() {
        return zzd;
    }

    static boolean zzI(Object obj, Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }

    static void zzJ(zzak zzakVar, Object obj, Object obj2) {
        zzao zzaoVarZzb = zzakVar.zzb(obj2);
        if (zzaoVarZzb.zza.isEmpty()) {
            return;
        }
        zzakVar.zzc(obj).zzh(zzaoVarZzb);
    }

    static void zzK(zzde zzdeVar, Object obj, Object obj2) {
        zzdeVar.zzi(obj, zzdeVar.zzq(zzdeVar.zzj(obj), zzdeVar.zzj(obj2)));
    }

    static Object zzL(Object obj, int i, List list, zzbe zzbeVar, Object obj2, zzde zzdeVar) {
        if (zzbeVar == null) {
            return obj2;
        }
        if (!(list instanceof RandomAccess)) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                int iIntValue = ((Integer) it.next()).intValue();
                if (!zzbeVar.isInRange(iIntValue)) {
                    obj2 = zzM(obj, i, iIntValue, obj2, zzdeVar);
                    it.remove();
                }
            }
            return obj2;
        }
        int size = list.size();
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            int iIntValue2 = ((Integer) list.get(i3)).intValue();
            if (zzbeVar.isInRange(iIntValue2)) {
                if (i3 != i2) {
                    list.set(i2, Integer.valueOf(iIntValue2));
                }
                i2++;
            } else {
                obj2 = zzM(obj, i, iIntValue2, obj2, zzdeVar);
            }
        }
        if (i2 != size) {
            list.subList(i2, size).clear();
        }
        return obj2;
    }

    static Object zzM(Object obj, int i, int i2, Object obj2, zzde zzdeVar) {
        if (obj2 == null) {
            obj2 = zzdeVar.zzk(obj);
        }
        zzdeVar.zzb(obj2, i, i2);
        return obj2;
    }

    public static void zza(Class cls) {
        if (zzay.class.isAssignableFrom(cls)) {
            return;
        }
        int i = zzf.zza;
        Class cls2 = zzb;
        if (cls2 != null && !cls2.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
        }
    }

    public static void zzb(int i, List list, zzdv zzdvVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzdvVar.zzC(i, list, z);
    }

    public static void zzc(int i, List list, zzdv zzdvVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzdvVar.zzB(i, list, z);
    }

    public static void zzd(int i, List list, zzdv zzdvVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzdvVar.zzy(i, list, z);
    }

    public static void zze(int i, List list, zzdv zzdvVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzdvVar.zzz(i, list, z);
    }

    public static void zzf(int i, List list, zzdv zzdvVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzdvVar.zzL(i, list, z);
    }

    public static void zzg(int i, List list, zzdv zzdvVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzdvVar.zzA(i, list, z);
    }

    public static void zzh(int i, List list, zzdv zzdvVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzdvVar.zzJ(i, list, z);
    }

    public static void zzi(int i, List list, zzdv zzdvVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzdvVar.zzw(i, list, z);
    }

    public static void zzj(int i, List list, zzdv zzdvVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzdvVar.zzH(i, list, z);
    }

    public static void zzk(int i, List list, zzdv zzdvVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzdvVar.zzK(i, list, z);
    }

    public static void zzl(int i, List list, zzdv zzdvVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzdvVar.zzx(i, list, z);
    }

    public static void zzm(int i, List list, zzdv zzdvVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzdvVar.zzI(i, list, z);
    }

    public static void zzn(int i, List list, zzdv zzdvVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzdvVar.zzD(i, list, z);
    }

    public static void zzo(int i, List list, zzdv zzdvVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzdvVar.zzE(i, list, z);
    }

    public static void zzp(int i, List list, zzdv zzdvVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzdvVar.zzF(i, list);
    }

    public static void zzq(int i, List list, zzdv zzdvVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzdvVar.zzG(i, list);
    }

    public static void zzr(int i, List list, zzdv zzdvVar, zzct zzctVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            ((zzae) zzdvVar).zzr(i, list.get(i2), zzctVar);
        }
    }

    public static void zzs(int i, List list, zzdv zzdvVar, zzct zzctVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            ((zzae) zzdvVar).zzs(i, list.get(i2), zzctVar);
        }
    }

    static int zzt(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof zzbu)) {
            int iZzy = 0;
            while (i < size) {
                iZzy += zzad.zzy(((Long) list.get(i)).longValue());
                i++;
            }
            return iZzy;
        }
        zzbu zzbuVar = (zzbu) list;
        int iZzy2 = 0;
        while (i < size) {
            iZzy2 += zzad.zzy(zzbuVar.zze(i));
            i++;
        }
        return iZzy2;
    }

    static int zzu(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof zzbu)) {
            int iZzy = 0;
            while (i < size) {
                iZzy += zzad.zzy(((Long) list.get(i)).longValue());
                i++;
            }
            return iZzy;
        }
        zzbu zzbuVar = (zzbu) list;
        int iZzy2 = 0;
        while (i < size) {
            iZzy2 += zzad.zzy(zzbuVar.zze(i));
            i++;
        }
        return iZzy2;
    }

    static int zzv(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof zzbu)) {
            int iZzy = 0;
            while (i < size) {
                long jLongValue = ((Long) list.get(i)).longValue();
                iZzy += zzad.zzy((jLongValue >> 63) ^ (jLongValue + jLongValue));
                i++;
            }
            return iZzy;
        }
        zzbu zzbuVar = (zzbu) list;
        int iZzy2 = 0;
        while (i < size) {
            long jZze = zzbuVar.zze(i);
            iZzy2 += zzad.zzy((jZze >> 63) ^ (jZze + jZze));
            i++;
        }
        return iZzy2;
    }

    static int zzw(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof zzaz)) {
            int iZzy = 0;
            while (i < size) {
                iZzy += zzad.zzy(((Integer) list.get(i)).intValue());
                i++;
            }
            return iZzy;
        }
        zzaz zzazVar = (zzaz) list;
        int iZzy2 = 0;
        while (i < size) {
            iZzy2 += zzad.zzy(zzazVar.zzf(i));
            i++;
        }
        return iZzy2;
    }

    static int zzx(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof zzaz)) {
            int iZzy = 0;
            while (i < size) {
                iZzy += zzad.zzy(((Integer) list.get(i)).intValue());
                i++;
            }
            return iZzy;
        }
        zzaz zzazVar = (zzaz) list;
        int iZzy2 = 0;
        while (i < size) {
            iZzy2 += zzad.zzy(zzazVar.zzf(i));
            i++;
        }
        return iZzy2;
    }

    static int zzy(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof zzaz)) {
            int iZzx = 0;
            while (i < size) {
                iZzx += zzad.zzx(((Integer) list.get(i)).intValue());
                i++;
            }
            return iZzx;
        }
        zzaz zzazVar = (zzaz) list;
        int iZzx2 = 0;
        while (i < size) {
            iZzx2 += zzad.zzx(zzazVar.zzf(i));
            i++;
        }
        return iZzx2;
    }

    static int zzz(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof zzaz)) {
            int iZzx = 0;
            while (i < size) {
                int iIntValue = ((Integer) list.get(i)).intValue();
                iZzx += zzad.zzx((iIntValue >> 31) ^ (iIntValue + iIntValue));
                i++;
            }
            return iZzx;
        }
        zzaz zzazVar = (zzaz) list;
        int iZzx2 = 0;
        while (i < size) {
            int iZzf = zzazVar.zzf(i);
            iZzx2 += zzad.zzx((iZzf >> 31) ^ (iZzf + iZzf));
            i++;
        }
        return iZzx2;
    }
}
