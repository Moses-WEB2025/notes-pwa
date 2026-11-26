package com.google.android.appfunctions.schema.internal.dependencies;

import java.io.IOException;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzae implements zzdv {
    private final zzad zza;

    private zzae(zzad zzadVar) {
        zzbj.zza(zzadVar, "output");
        this.zza = zzadVar;
        zzadVar.zze = this;
    }

    public static zzae zza(zzad zzadVar) {
        Object obj = zzadVar.zze;
        return obj != null ? (zzae) obj : new zzae(zzadVar);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    public final void zzG(int i, List list) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            this.zza.zzk(i, (zzt) list.get(i2));
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    public final void zzb(int i, int i2) throws IOException {
        this.zza.zzJ(i, i2);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    public final void zzc(int i, long j) throws IOException {
        this.zza.zzK(i, j);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    public final void zzd(int i, long j) throws IOException {
        this.zza.zzh(i, j);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    public final void zze(int i, float f) throws IOException {
        this.zza.zzJ(i, Float.floatToRawIntBits(f));
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    public final void zzf(int i, double d) throws IOException {
        this.zza.zzh(i, Double.doubleToRawLongBits(d));
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    public final void zzg(int i, int i2) throws IOException {
        this.zza.zzH(i, i2);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    public final void zzh(int i, long j) throws IOException {
        this.zza.zzK(i, j);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    public final void zzi(int i, int i2) throws IOException {
        this.zza.zzH(i, i2);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    public final void zzj(int i, long j) throws IOException {
        this.zza.zzh(i, j);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    public final void zzk(int i, int i2) throws IOException {
        this.zza.zzJ(i, i2);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    public final void zzl(int i, boolean z) throws IOException {
        this.zza.zzi(i, z);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    public final void zzm(int i, String str) throws IOException {
        this.zza.zzj(i, str);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    public final void zzn(int i, zzt zztVar) throws IOException {
        this.zza.zzk(i, zztVar);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    public final void zzo(int i, int i2) throws IOException {
        this.zza.zzI(i, i2);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    public final void zzp(int i, int i2) throws IOException {
        zzad zzadVar = this.zza;
        zzadVar.zzI(i, (i2 >> 31) ^ (i2 + i2));
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    public final void zzq(int i, long j) throws IOException {
        zzad zzadVar = this.zza;
        zzadVar.zzK(i, (j >> 63) ^ (j + j));
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    public final void zzr(int i, Object obj, zzct zzctVar) throws IOException {
        zzad zzadVar = this.zza;
        zzc zzcVar = (zzc) obj;
        zzadVar.zzG(i, 2);
        zzadVar.zzp(zzcVar.getSerializedSize(zzctVar));
        zzctVar.zzf(zzcVar, this);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    public final void zzs(int i, Object obj, zzct zzctVar) throws IOException {
        zzad zzadVar = this.zza;
        zzadVar.zzG(i, 3);
        zzctVar.zzf((zzc) obj, this);
        zzadVar.zzG(i, 4);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    @Deprecated
    public final void zzt(int i) throws IOException {
        this.zza.zzG(i, 3);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    @Deprecated
    public final void zzu(int i) throws IOException {
        this.zza.zzG(i, 4);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    public final void zzv(int i, Object obj) throws IOException {
        boolean z = obj instanceof zzt;
        zzad zzadVar = this.zza;
        if (z) {
            zzadVar.zzm(i, (zzt) obj);
        } else {
            zzadVar.zzl(i, (zzcf) obj);
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    public final void zzF(int i, List list) throws IOException {
        int i2 = 0;
        if (!(list instanceof zzbr)) {
            while (i2 < list.size()) {
                this.zza.zzj(i, (String) list.get(i2));
                i2++;
            }
            return;
        }
        zzbr zzbrVar = (zzbr) list;
        while (i2 < list.size()) {
            Object objZzc = zzbrVar.zzc();
            if (objZzc instanceof String) {
                this.zza.zzj(i, (String) objZzc);
            } else {
                this.zza.zzk(i, (zzt) objZzc);
            }
            i2++;
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    public final void zzA(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!(list instanceof zzbu)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zzh(i, ((Long) list.get(i2)).longValue());
                    i2++;
                }
                return;
            }
            zzad zzadVar = this.zza;
            zzadVar.zzG(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                ((Long) list.get(i4)).longValue();
                i3 += 8;
            }
            zzadVar.zzp(i3);
            while (i2 < list.size()) {
                zzadVar.zzs(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        zzbu zzbuVar = (zzbu) list;
        if (!z) {
            while (i2 < zzbuVar.size()) {
                this.zza.zzh(i, zzbuVar.zze(i2));
                i2++;
            }
            return;
        }
        zzad zzadVar2 = this.zza;
        zzadVar2.zzG(i, 2);
        int i5 = 0;
        for (int i6 = 0; i6 < zzbuVar.size(); i6++) {
            zzbuVar.zze(i6);
            i5 += 8;
        }
        zzadVar2.zzp(i5);
        while (i2 < zzbuVar.size()) {
            zzadVar2.zzs(zzbuVar.zze(i2));
            i2++;
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    public final void zzH(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!(list instanceof zzaz)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zzI(i, ((Integer) list.get(i2)).intValue());
                    i2++;
                }
                return;
            }
            zzad zzadVar = this.zza;
            zzadVar.zzG(i, 2);
            int iZzx = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzx += zzad.zzx(((Integer) list.get(i3)).intValue());
            }
            zzadVar.zzp(iZzx);
            while (i2 < list.size()) {
                zzadVar.zzp(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        zzaz zzazVar = (zzaz) list;
        if (!z) {
            while (i2 < zzazVar.size()) {
                this.zza.zzI(i, zzazVar.zzf(i2));
                i2++;
            }
            return;
        }
        zzad zzadVar2 = this.zza;
        zzadVar2.zzG(i, 2);
        int iZzx2 = 0;
        for (int i4 = 0; i4 < zzazVar.size(); i4++) {
            iZzx2 += zzad.zzx(zzazVar.zzf(i4));
        }
        zzadVar2.zzp(iZzx2);
        while (i2 < zzazVar.size()) {
            zzadVar2.zzp(zzazVar.zzf(i2));
            i2++;
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    public final void zzx(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!(list instanceof zzaz)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zzJ(i, ((Integer) list.get(i2)).intValue());
                    i2++;
                }
                return;
            }
            zzad zzadVar = this.zza;
            zzadVar.zzG(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                ((Integer) list.get(i4)).intValue();
                i3 += 4;
            }
            zzadVar.zzp(i3);
            while (i2 < list.size()) {
                zzadVar.zzq(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        zzaz zzazVar = (zzaz) list;
        if (!z) {
            while (i2 < zzazVar.size()) {
                this.zza.zzJ(i, zzazVar.zzf(i2));
                i2++;
            }
            return;
        }
        zzad zzadVar2 = this.zza;
        zzadVar2.zzG(i, 2);
        int i5 = 0;
        for (int i6 = 0; i6 < zzazVar.size(); i6++) {
            zzazVar.zzf(i6);
            i5 += 4;
        }
        zzadVar2.zzp(i5);
        while (i2 < zzazVar.size()) {
            zzadVar2.zzq(zzazVar.zzf(i2));
            i2++;
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    public final void zzz(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!(list instanceof zzbu)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zzK(i, ((Long) list.get(i2)).longValue());
                    i2++;
                }
                return;
            }
            zzad zzadVar = this.zza;
            zzadVar.zzG(i, 2);
            int iZzy = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzy += zzad.zzy(((Long) list.get(i3)).longValue());
            }
            zzadVar.zzp(iZzy);
            while (i2 < list.size()) {
                zzadVar.zzr(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        zzbu zzbuVar = (zzbu) list;
        if (!z) {
            while (i2 < zzbuVar.size()) {
                this.zza.zzK(i, zzbuVar.zze(i2));
                i2++;
            }
            return;
        }
        zzad zzadVar2 = this.zza;
        zzadVar2.zzG(i, 2);
        int iZzy2 = 0;
        for (int i4 = 0; i4 < zzbuVar.size(); i4++) {
            iZzy2 += zzad.zzy(zzbuVar.zze(i4));
        }
        zzadVar2.zzp(iZzy2);
        while (i2 < zzbuVar.size()) {
            zzadVar2.zzr(zzbuVar.zze(i2));
            i2++;
        }
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    public final void zzE(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!(list instanceof zzi)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zzi(i, ((Boolean) list.get(i2)).booleanValue());
                    i2++;
                }
                return;
            }
            zzad zzadVar = this.zza;
            zzadVar.zzG(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                ((Boolean) list.get(i4)).booleanValue();
                i3++;
            }
            zzadVar.zzp(i3);
            while (i2 < list.size()) {
                zzadVar.zzn(((Boolean) list.get(i2)).booleanValue() ? (byte) 1 : (byte) 0);
                i2++;
            }
            return;
        }
        zzi zziVar = (zzi) list;
        if (!z) {
            while (i2 < zziVar.size()) {
                this.zza.zzi(i, zziVar.zzf(i2));
                i2++;
            }
            return;
        }
        zzad zzadVar2 = this.zza;
        zzadVar2.zzG(i, 2);
        int i5 = 0;
        for (int i6 = 0; i6 < zziVar.size(); i6++) {
            zziVar.zzf(i6);
            i5++;
        }
        zzadVar2.zzp(i5);
        while (i2 < zziVar.size()) {
            zzadVar2.zzn(zziVar.zzf(i2) ? (byte) 1 : (byte) 0);
            i2++;
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    public final void zzw(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!(list instanceof zzaz)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zzH(i, ((Integer) list.get(i2)).intValue());
                    i2++;
                }
                return;
            }
            zzad zzadVar = this.zza;
            zzadVar.zzG(i, 2);
            int iZzy = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzy += zzad.zzy(((Integer) list.get(i3)).intValue());
            }
            zzadVar.zzp(iZzy);
            while (i2 < list.size()) {
                zzadVar.zzo(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        zzaz zzazVar = (zzaz) list;
        if (!z) {
            while (i2 < zzazVar.size()) {
                this.zza.zzH(i, zzazVar.zzf(i2));
                i2++;
            }
            return;
        }
        zzad zzadVar2 = this.zza;
        zzadVar2.zzG(i, 2);
        int iZzy2 = 0;
        for (int i4 = 0; i4 < zzazVar.size(); i4++) {
            iZzy2 += zzad.zzy(zzazVar.zzf(i4));
        }
        zzadVar2.zzp(iZzy2);
        while (i2 < zzazVar.size()) {
            zzadVar2.zzo(zzazVar.zzf(i2));
            i2++;
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    public final void zzB(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!(list instanceof zzaq)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zzJ(i, Float.floatToRawIntBits(((Float) list.get(i2)).floatValue()));
                    i2++;
                }
                return;
            }
            zzad zzadVar = this.zza;
            zzadVar.zzG(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                ((Float) list.get(i4)).floatValue();
                i3 += 4;
            }
            zzadVar.zzp(i3);
            while (i2 < list.size()) {
                zzadVar.zzq(Float.floatToRawIntBits(((Float) list.get(i2)).floatValue()));
                i2++;
            }
            return;
        }
        zzaq zzaqVar = (zzaq) list;
        if (!z) {
            while (i2 < zzaqVar.size()) {
                this.zza.zzJ(i, Float.floatToRawIntBits(zzaqVar.zzf(i2)));
                i2++;
            }
            return;
        }
        zzad zzadVar2 = this.zza;
        zzadVar2.zzG(i, 2);
        int i5 = 0;
        for (int i6 = 0; i6 < zzaqVar.size(); i6++) {
            zzaqVar.zzf(i6);
            i5 += 4;
        }
        zzadVar2.zzp(i5);
        while (i2 < zzaqVar.size()) {
            zzadVar2.zzq(Float.floatToRawIntBits(zzaqVar.zzf(i2)));
            i2++;
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    public final void zzC(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!(list instanceof zzaf)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zzh(i, Double.doubleToRawLongBits(((Double) list.get(i2)).doubleValue()));
                    i2++;
                }
                return;
            }
            zzad zzadVar = this.zza;
            zzadVar.zzG(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                ((Double) list.get(i4)).doubleValue();
                i3 += 8;
            }
            zzadVar.zzp(i3);
            while (i2 < list.size()) {
                zzadVar.zzs(Double.doubleToRawLongBits(((Double) list.get(i2)).doubleValue()));
                i2++;
            }
            return;
        }
        zzaf zzafVar = (zzaf) list;
        if (!z) {
            while (i2 < zzafVar.size()) {
                this.zza.zzh(i, Double.doubleToRawLongBits(zzafVar.zzf(i2)));
                i2++;
            }
            return;
        }
        zzad zzadVar2 = this.zza;
        zzadVar2.zzG(i, 2);
        int i5 = 0;
        for (int i6 = 0; i6 < zzafVar.size(); i6++) {
            zzafVar.zzf(i6);
            i5 += 8;
        }
        zzadVar2.zzp(i5);
        while (i2 < zzafVar.size()) {
            zzadVar2.zzs(Double.doubleToRawLongBits(zzafVar.zzf(i2)));
            i2++;
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    public final void zzI(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!(list instanceof zzaz)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zzJ(i, ((Integer) list.get(i2)).intValue());
                    i2++;
                }
                return;
            }
            zzad zzadVar = this.zza;
            zzadVar.zzG(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                ((Integer) list.get(i4)).intValue();
                i3 += 4;
            }
            zzadVar.zzp(i3);
            while (i2 < list.size()) {
                zzadVar.zzq(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        zzaz zzazVar = (zzaz) list;
        if (!z) {
            while (i2 < zzazVar.size()) {
                this.zza.zzJ(i, zzazVar.zzf(i2));
                i2++;
            }
            return;
        }
        zzad zzadVar2 = this.zza;
        zzadVar2.zzG(i, 2);
        int i5 = 0;
        for (int i6 = 0; i6 < zzazVar.size(); i6++) {
            zzazVar.zzf(i6);
            i5 += 4;
        }
        zzadVar2.zzp(i5);
        while (i2 < zzazVar.size()) {
            zzadVar2.zzq(zzazVar.zzf(i2));
            i2++;
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    public final void zzJ(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!(list instanceof zzbu)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zzh(i, ((Long) list.get(i2)).longValue());
                    i2++;
                }
                return;
            }
            zzad zzadVar = this.zza;
            zzadVar.zzG(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                ((Long) list.get(i4)).longValue();
                i3 += 8;
            }
            zzadVar.zzp(i3);
            while (i2 < list.size()) {
                zzadVar.zzs(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        zzbu zzbuVar = (zzbu) list;
        if (!z) {
            while (i2 < zzbuVar.size()) {
                this.zza.zzh(i, zzbuVar.zze(i2));
                i2++;
            }
            return;
        }
        zzad zzadVar2 = this.zza;
        zzadVar2.zzG(i, 2);
        int i5 = 0;
        for (int i6 = 0; i6 < zzbuVar.size(); i6++) {
            zzbuVar.zze(i6);
            i5 += 8;
        }
        zzadVar2.zzp(i5);
        while (i2 < zzbuVar.size()) {
            zzadVar2.zzs(zzbuVar.zze(i2));
            i2++;
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    public final void zzD(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!(list instanceof zzaz)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zzH(i, ((Integer) list.get(i2)).intValue());
                    i2++;
                }
                return;
            }
            zzad zzadVar = this.zza;
            zzadVar.zzG(i, 2);
            int iZzy = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzy += zzad.zzy(((Integer) list.get(i3)).intValue());
            }
            zzadVar.zzp(iZzy);
            while (i2 < list.size()) {
                zzadVar.zzo(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        zzaz zzazVar = (zzaz) list;
        if (!z) {
            while (i2 < zzazVar.size()) {
                this.zza.zzH(i, zzazVar.zzf(i2));
                i2++;
            }
            return;
        }
        zzad zzadVar2 = this.zza;
        zzadVar2.zzG(i, 2);
        int iZzy2 = 0;
        for (int i4 = 0; i4 < zzazVar.size(); i4++) {
            iZzy2 += zzad.zzy(zzazVar.zzf(i4));
        }
        zzadVar2.zzp(iZzy2);
        while (i2 < zzazVar.size()) {
            zzadVar2.zzo(zzazVar.zzf(i2));
            i2++;
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    public final void zzK(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!(list instanceof zzaz)) {
            if (!z) {
                while (i2 < list.size()) {
                    zzad zzadVar = this.zza;
                    int iIntValue = ((Integer) list.get(i2)).intValue();
                    zzadVar.zzI(i, (iIntValue >> 31) ^ (iIntValue + iIntValue));
                    i2++;
                }
                return;
            }
            zzad zzadVar2 = this.zza;
            zzadVar2.zzG(i, 2);
            int iZzx = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                int iIntValue2 = ((Integer) list.get(i3)).intValue();
                iZzx += zzad.zzx((iIntValue2 >> 31) ^ (iIntValue2 + iIntValue2));
            }
            zzadVar2.zzp(iZzx);
            while (i2 < list.size()) {
                int iIntValue3 = ((Integer) list.get(i2)).intValue();
                zzadVar2.zzp((iIntValue3 >> 31) ^ (iIntValue3 + iIntValue3));
                i2++;
            }
            return;
        }
        zzaz zzazVar = (zzaz) list;
        if (!z) {
            while (i2 < zzazVar.size()) {
                zzad zzadVar3 = this.zza;
                int iZzf = zzazVar.zzf(i2);
                zzadVar3.zzI(i, (iZzf >> 31) ^ (iZzf + iZzf));
                i2++;
            }
            return;
        }
        zzad zzadVar4 = this.zza;
        zzadVar4.zzG(i, 2);
        int iZzx2 = 0;
        for (int i4 = 0; i4 < zzazVar.size(); i4++) {
            int iZzf2 = zzazVar.zzf(i4);
            iZzx2 += zzad.zzx((iZzf2 >> 31) ^ (iZzf2 + iZzf2));
        }
        zzadVar4.zzp(iZzx2);
        while (i2 < zzazVar.size()) {
            int iZzf3 = zzazVar.zzf(i2);
            zzadVar4.zzp((iZzf3 >> 31) ^ (iZzf3 + iZzf3));
            i2++;
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    public final void zzL(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!(list instanceof zzbu)) {
            if (!z) {
                while (i2 < list.size()) {
                    zzad zzadVar = this.zza;
                    long jLongValue = ((Long) list.get(i2)).longValue();
                    zzadVar.zzK(i, (jLongValue >> 63) ^ (jLongValue + jLongValue));
                    i2++;
                }
                return;
            }
            zzad zzadVar2 = this.zza;
            zzadVar2.zzG(i, 2);
            int iZzy = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                long jLongValue2 = ((Long) list.get(i3)).longValue();
                iZzy += zzad.zzy((jLongValue2 >> 63) ^ (jLongValue2 + jLongValue2));
            }
            zzadVar2.zzp(iZzy);
            while (i2 < list.size()) {
                long jLongValue3 = ((Long) list.get(i2)).longValue();
                zzadVar2.zzr((jLongValue3 >> 63) ^ (jLongValue3 + jLongValue3));
                i2++;
            }
            return;
        }
        zzbu zzbuVar = (zzbu) list;
        if (!z) {
            while (i2 < zzbuVar.size()) {
                zzad zzadVar3 = this.zza;
                long jZze = zzbuVar.zze(i2);
                zzadVar3.zzK(i, (jZze >> 63) ^ (jZze + jZze));
                i2++;
            }
            return;
        }
        zzad zzadVar4 = this.zza;
        zzadVar4.zzG(i, 2);
        int iZzy2 = 0;
        for (int i4 = 0; i4 < zzbuVar.size(); i4++) {
            long jZze2 = zzbuVar.zze(i4);
            iZzy2 += zzad.zzy((jZze2 >> 63) ^ (jZze2 + jZze2));
        }
        zzadVar4.zzp(iZzy2);
        while (i2 < zzbuVar.size()) {
            long jZze3 = zzbuVar.zze(i2);
            zzadVar4.zzr((jZze3 >> 63) ^ (jZze3 + jZze3));
            i2++;
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzdv
    public final void zzy(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!(list instanceof zzbu)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zzK(i, ((Long) list.get(i2)).longValue());
                    i2++;
                }
                return;
            }
            zzad zzadVar = this.zza;
            zzadVar.zzG(i, 2);
            int iZzy = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzy += zzad.zzy(((Long) list.get(i3)).longValue());
            }
            zzadVar.zzp(iZzy);
            while (i2 < list.size()) {
                zzadVar.zzr(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        zzbu zzbuVar = (zzbu) list;
        if (!z) {
            while (i2 < zzbuVar.size()) {
                this.zza.zzK(i, zzbuVar.zze(i2));
                i2++;
            }
            return;
        }
        zzad zzadVar2 = this.zza;
        zzadVar2.zzG(i, 2);
        int iZzy2 = 0;
        for (int i4 = 0; i4 < zzbuVar.size(); i4++) {
            iZzy2 += zzad.zzy(zzbuVar.zze(i4));
        }
        zzadVar2.zzp(iZzy2);
        while (i2 < zzbuVar.size()) {
            zzadVar2.zzr(zzbuVar.zze(i2));
            i2++;
        }
    }
}
