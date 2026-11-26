package com.google.android.appfunctions.schema.internal.dependencies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzao {
    private static final zzao zzd = new zzao(true);
    final zzda zza = new zzcw();
    boolean zzb;
    boolean zzc;

    private zzao() {
    }

    public static zzao zza() {
        return zzd;
    }

    static int zzj(zzdt zzdtVar, int i, Object obj) {
        int iZzx = zzad.zzx(i << 3);
        if (zzdtVar == zzdt.GROUP) {
            iZzx += iZzx;
        }
        return iZzx + zzk(zzdtVar, obj);
    }

    static int zzk(zzdt zzdtVar, Object obj) {
        int iZzc;
        int iZzx;
        zzdt zzdtVar2 = zzdt.DOUBLE;
        zzdu zzduVar = zzdu.INT;
        switch (zzdtVar) {
            case DOUBLE:
                ((Double) obj).doubleValue();
                int i = zzad.zzf;
                return 8;
            case FLOAT:
                ((Float) obj).floatValue();
                int i2 = zzad.zzf;
                return 4;
            case INT64:
                return zzad.zzy(((Long) obj).longValue());
            case UINT64:
                return zzad.zzy(((Long) obj).longValue());
            case INT32:
                return zzad.zzy(((Integer) obj).intValue());
            case FIXED64:
                ((Long) obj).longValue();
                int i3 = zzad.zzf;
                return 8;
            case FIXED32:
                ((Integer) obj).intValue();
                int i4 = zzad.zzf;
                return 4;
            case BOOL:
                ((Boolean) obj).booleanValue();
                int i5 = zzad.zzf;
                return 1;
            case STRING:
                if (!(obj instanceof zzt)) {
                    return zzad.zzz((String) obj);
                }
                int i6 = zzad.zzf;
                iZzc = ((zzt) obj).zzc();
                iZzx = zzad.zzx(iZzc);
                break;
                break;
            case GROUP:
                return ((zzcf) obj).getSerializedSize();
            case MESSAGE:
                if (!(obj instanceof zzbp)) {
                    return zzad.zzA((zzcf) obj);
                }
                iZzc = ((zzbp) obj).zzb();
                iZzx = zzad.zzx(iZzc);
                break;
                break;
            case BYTES:
                if (!(obj instanceof zzt)) {
                    int i7 = zzad.zzf;
                    iZzc = ((byte[]) obj).length;
                    iZzx = zzad.zzx(iZzc);
                } else {
                    int i8 = zzad.zzf;
                    iZzc = ((zzt) obj).zzc();
                    iZzx = zzad.zzx(iZzc);
                }
                break;
            case UINT32:
                return zzad.zzx(((Integer) obj).intValue());
            case ENUM:
                return obj instanceof zzbc ? zzad.zzy(((zzbc) obj).getNumber()) : zzad.zzy(((Integer) obj).intValue());
            case SFIXED32:
                ((Integer) obj).intValue();
                int i9 = zzad.zzf;
                return 4;
            case SFIXED64:
                ((Long) obj).longValue();
                int i10 = zzad.zzf;
                return 8;
            case SINT32:
                int iIntValue = ((Integer) obj).intValue();
                return zzad.zzx((iIntValue >> 31) ^ (iIntValue + iIntValue));
            case SINT64:
                long jLongValue = ((Long) obj).longValue();
                return zzad.zzy((jLongValue >> 63) ^ (jLongValue + jLongValue));
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
        return iZzx + iZzc;
    }

    public static int zzl(zzan zzanVar, Object obj) {
        zzdt zzdtVarZzb = zzanVar.zzb();
        int iZza = zzanVar.zza();
        if (!zzanVar.zzd()) {
            return zzj(zzdtVarZzb, iZza, obj);
        }
        List list = (List) obj;
        int size = list.size();
        int i = 0;
        if (!zzanVar.zze()) {
            int iZzj = 0;
            while (i < size) {
                iZzj += zzj(zzdtVarZzb, iZza, list.get(i));
                i++;
            }
            return iZzj;
        }
        if (list.isEmpty()) {
            return 0;
        }
        int iZzk = 0;
        while (i < size) {
            iZzk += zzk(zzdtVarZzb, list.get(i));
            i++;
        }
        return zzad.zzx(iZza << 3) + iZzk + zzad.zzx(iZzk);
    }

    private static boolean zzm(Map.Entry entry) {
        zzan zzanVar = (zzan) entry.getKey();
        if (zzanVar.zzc() != zzdu.MESSAGE) {
            return true;
        }
        if (!zzanVar.zzd()) {
            return zzn(entry.getValue());
        }
        List list = (List) entry.getValue();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (!zzn(list.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean zzn(Object obj) {
        if (obj instanceof zzcg) {
            return ((zzcg) obj).isInitialized();
        }
        if (obj instanceof zzbp) {
            return true;
        }
        throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
    }

    private static Object zzo(Object obj) {
        if (obj instanceof zzck) {
            return ((zzck) obj).zza();
        }
        if (!(obj instanceof byte[])) {
            return obj;
        }
        byte[] bArr = (byte[]) obj;
        int length = bArr.length;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        return bArr2;
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    private final void zzp(Map.Entry entry) {
        zzan zzanVar = (zzan) entry.getKey();
        Object value = entry.getValue();
        boolean z = value instanceof zzbp;
        if (zzanVar.zzd()) {
            if (z) {
                throw new IllegalStateException("Lazy fields can not be repeated");
            }
            Object objZzd = zzd(zzanVar);
            List list = (List) value;
            int size = list.size();
            if (objZzd == null) {
                objZzd = new ArrayList(size);
            }
            List list2 = (List) objZzd;
            for (int i = 0; i < size; i++) {
                list2.add(zzo(list.get(i)));
            }
            this.zza.put(zzanVar, objZzd);
            return;
        }
        if (zzanVar.zzc() != zzdu.MESSAGE) {
            if (z) {
                throw new IllegalStateException("Lazy fields must be message-valued");
            }
            this.zza.put(zzanVar, zzo(value));
            return;
        }
        Object objZzd2 = zzd(zzanVar);
        if (objZzd2 == null) {
            this.zza.put(zzanVar, zzo(value));
            if (z) {
                this.zzc = true;
                return;
            }
            return;
        }
        if (z) {
            throw null;
        }
        if (!zzanVar.zzf(objZzd2)) {
            zzanVar.zzg(objZzd2, value);
            return;
        }
        zzce builder = ((zzcf) objZzd2).toBuilder();
        zzanVar.zzg(builder, value);
        this.zza.put(zzanVar, builder.build());
    }

    private static final int zzq(Map.Entry entry) {
        int i;
        int iZzx;
        int iZzx2;
        zzan zzanVar = (zzan) entry.getKey();
        Object value = entry.getValue();
        if (zzanVar.zzc() != zzdu.MESSAGE || zzanVar.zzd() || zzanVar.zze()) {
            return zzl(zzanVar, value);
        }
        if (value instanceof zzbp) {
            int iZza = ((zzan) entry.getKey()).zza();
            int iZzx3 = zzad.zzx(8);
            i = iZzx3 + iZzx3;
            iZzx = zzad.zzx(16) + zzad.zzx(iZza);
            int iZzx4 = zzad.zzx(24);
            int iZzb = ((zzbp) value).zzb();
            iZzx2 = iZzx4 + zzad.zzx(iZzb) + iZzb;
        } else {
            int iZza2 = ((zzan) entry.getKey()).zza();
            int iZzx5 = zzad.zzx(8);
            i = iZzx5 + iZzx5;
            iZzx = zzad.zzx(16) + zzad.zzx(iZza2);
            iZzx2 = zzad.zzx(24) + zzad.zzA((zzcf) value);
        }
        return i + iZzx + iZzx2;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:32:? A[RETURN, SYNTHETIC] */
    private static final void zzr(zzan zzanVar, Object obj) {
        boolean z;
        zzanVar.zzb();
        byte[] bArr = zzbj.zzb;
        obj.getClass();
        zzdt zzdtVar = zzdt.DOUBLE;
        zzdu zzduVar = zzdu.INT;
        switch (r0.zza()) {
            case INT:
                z = obj instanceof Integer;
                if (z) {
                    return;
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(zzanVar.zza()), zzanVar.zzb().zza(), obj.getClass().getName()));
            case LONG:
                z = obj instanceof Long;
                if (z) {
                    return;
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(zzanVar.zza()), zzanVar.zzb().zza(), obj.getClass().getName()));
            case FLOAT:
                z = obj instanceof Float;
                if (z) {
                    return;
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(zzanVar.zza()), zzanVar.zzb().zza(), obj.getClass().getName()));
            case DOUBLE:
                z = obj instanceof Double;
                if (z) {
                    return;
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(zzanVar.zza()), zzanVar.zzb().zza(), obj.getClass().getName()));
            case BOOLEAN:
                z = obj instanceof Boolean;
                if (z) {
                    return;
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(zzanVar.zza()), zzanVar.zzb().zza(), obj.getClass().getName()));
            case STRING:
                z = obj instanceof String;
                if (z) {
                    return;
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(zzanVar.zza()), zzanVar.zzb().zza(), obj.getClass().getName()));
            case BYTE_STRING:
                if ((obj instanceof zzt) || (obj instanceof byte[])) {
                    return;
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(zzanVar.zza()), zzanVar.zzb().zza(), obj.getClass().getName()));
            case ENUM:
                if ((obj instanceof Integer) || (obj instanceof zzbc)) {
                    return;
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(zzanVar.zza()), zzanVar.zzb().zza(), obj.getClass().getName()));
            case MESSAGE:
                if ((obj instanceof zzcf) || (obj instanceof zzbp)) {
                    return;
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(zzanVar.zza()), zzanVar.zzb().zza(), obj.getClass().getName()));
            default:
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(zzanVar.zza()), zzanVar.zzb().zza(), obj.getClass().getName()));
        }
    }

    public final /* bridge */ /* synthetic */ Object clone() throws CloneNotSupportedException {
        zzao zzaoVar = new zzao();
        zzda zzdaVar = this.zza;
        int iZzc = zzdaVar.zzc();
        for (int i = 0; i < iZzc; i++) {
            Map.Entry entryZzd = zzdaVar.zzd(i);
            zzaoVar.zze((zzan) ((zzcx) entryZzd).zza(), entryZzd.getValue());
        }
        for (Map.Entry entry : zzdaVar.zze()) {
            zzaoVar.zze((zzan) entry.getKey(), entry.getValue());
        }
        zzaoVar.zzc = this.zzc;
        return zzaoVar;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzao) {
            return this.zza.equals(((zzao) obj).zza);
        }
        return false;
    }

    public final int hashCode() {
        return this.zza.hashCode();
    }

    public final void zzb() {
        if (this.zzb) {
            return;
        }
        zzda zzdaVar = this.zza;
        int iZzc = zzdaVar.zzc();
        for (int i = 0; i < iZzc; i++) {
            Object value = zzdaVar.zzd(i).getValue();
            if (value instanceof zzay) {
                ((zzay) value).makeImmutable();
            }
        }
        Iterator it = zzdaVar.zze().iterator();
        while (it.hasNext()) {
            Object value2 = ((Map.Entry) it.next()).getValue();
            if (value2 instanceof zzay) {
                ((zzay) value2).makeImmutable();
            }
        }
        zzdaVar.zza();
        this.zzb = true;
    }

    public final Iterator zzc() {
        zzda zzdaVar = this.zza;
        if (zzdaVar.isEmpty()) {
            return Collections.emptyIterator();
        }
        return this.zzc ? new zzbo(zzdaVar.entrySet().iterator()) : zzdaVar.entrySet().iterator();
    }

    public final Object zzd(zzan zzanVar) {
        Object obj = this.zza.get(zzanVar);
        if (!(obj instanceof zzbp)) {
            return obj;
        }
        throw null;
    }

    public final void zze(zzan zzanVar, Object obj) {
        if (!zzanVar.zzd()) {
            zzr(zzanVar, obj);
        } else {
            if (!(obj instanceof List)) {
                throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
            }
            List list = (List) obj;
            int size = list.size();
            ArrayList arrayList = new ArrayList(size);
            for (int i = 0; i < size; i++) {
                Object obj2 = list.get(i);
                zzr(zzanVar, obj2);
                arrayList.add(obj2);
            }
            obj = arrayList;
        }
        if (obj instanceof zzbp) {
            this.zzc = true;
        }
        this.zza.put(zzanVar, obj);
    }

    public final void zzf(zzan zzanVar, Object obj) {
        List arrayList;
        if (!((zzav) zzanVar).zzd) {
            throw new IllegalArgumentException("addRepeatedField() can only be called on repeated fields.");
        }
        zzr(zzanVar, obj);
        Object objZzd = zzd(zzanVar);
        if (objZzd == null) {
            arrayList = new ArrayList();
            this.zza.put(zzanVar, arrayList);
        } else {
            arrayList = (List) objZzd;
        }
        arrayList.add(obj);
    }

    public final boolean zzg() {
        zzda zzdaVar = this.zza;
        int iZzc = zzdaVar.zzc();
        for (int i = 0; i < iZzc; i++) {
            if (!zzm(zzdaVar.zzd(i))) {
                return false;
            }
        }
        Iterator it = zzdaVar.zze().iterator();
        while (it.hasNext()) {
            if (!zzm((Map.Entry) it.next())) {
                return false;
            }
        }
        return true;
    }

    public final void zzh(zzao zzaoVar) {
        zzda zzdaVar = zzaoVar.zza;
        int iZzc = zzdaVar.zzc();
        for (int i = 0; i < iZzc; i++) {
            zzp(zzdaVar.zzd(i));
        }
        Iterator it = zzdaVar.zze().iterator();
        while (it.hasNext()) {
            zzp((Map.Entry) it.next());
        }
    }

    public final int zzi() {
        zzda zzdaVar = this.zza;
        int iZzc = zzdaVar.zzc();
        int iZzq = 0;
        for (int i = 0; i < iZzc; i++) {
            iZzq += zzq(zzdaVar.zzd(i));
        }
        Iterator it = zzdaVar.zze().iterator();
        while (it.hasNext()) {
            iZzq += zzq((Map.Entry) it.next());
        }
        return iZzq;
    }

    private zzao(boolean z) {
        zzb();
        zzb();
    }
}
