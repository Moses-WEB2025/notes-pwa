package com.google.android.appfunctions.schema.internal.dependencies;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzal extends zzak {
    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzak
    final boolean zza(zzcf zzcfVar) {
        return zzcfVar instanceof zzau;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzak
    final zzao zzb(Object obj) {
        return ((zzau) obj).zza;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzak
    final zzao zzc(Object obj) {
        throw null;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzak
    final void zzd(Object obj) {
        ((zzau) obj).zza.zzb();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzak
    final Object zze(Object obj, zzcs zzcsVar, Object obj2, zzaj zzajVar, zzao zzaoVar, Object obj3, zzde zzdeVar) throws IOException {
        Object objValueOf;
        Object objZzd;
        ArrayList arrayList;
        zzaw zzawVar = (zzaw) obj2;
        zzav zzavVar = zzawVar.zzb;
        boolean z = zzavVar.zzd;
        int i = zzavVar.zzb;
        if (z && zzavVar.zze) {
            zzdt zzdtVar = zzdt.DOUBLE;
            zzdt zzdtVar2 = zzavVar.zzc;
            switch (zzdtVar2) {
                case DOUBLE:
                    arrayList = new ArrayList();
                    zzcsVar.zzz(arrayList);
                    break;
                case FLOAT:
                    arrayList = new ArrayList();
                    zzcsVar.zzA(arrayList);
                    break;
                case INT64:
                    arrayList = new ArrayList();
                    zzcsVar.zzC(arrayList);
                    break;
                case UINT64:
                    arrayList = new ArrayList();
                    zzcsVar.zzB(arrayList);
                    break;
                case INT32:
                    arrayList = new ArrayList();
                    zzcsVar.zzD(arrayList);
                    break;
                case FIXED64:
                    arrayList = new ArrayList();
                    zzcsVar.zzE(arrayList);
                    break;
                case FIXED32:
                    arrayList = new ArrayList();
                    zzcsVar.zzF(arrayList);
                    break;
                case BOOL:
                    arrayList = new ArrayList();
                    zzcsVar.zzG(arrayList);
                    break;
                case STRING:
                case GROUP:
                case MESSAGE:
                case BYTES:
                default:
                    String strValueOf = String.valueOf(zzdtVar2);
                    String.valueOf(strValueOf);
                    throw new IllegalStateException("Type cannot be packed: ".concat(String.valueOf(strValueOf)));
                case UINT32:
                    arrayList = new ArrayList();
                    zzcsVar.zzL(arrayList);
                    break;
                case ENUM:
                    ArrayList arrayList2 = new ArrayList();
                    zzcsVar.zzM(arrayList2);
                    zzbd zzbdVar = zzavVar.zza;
                    int i2 = zzcv.zza;
                    if (zzbdVar != null) {
                        int size = arrayList2.size();
                        int i3 = 0;
                        for (int i4 = 0; i4 < size; i4++) {
                            int iIntValue = ((Integer) arrayList2.get(i4)).intValue();
                            if (zzbdVar.findValueByNumber(iIntValue) != null) {
                                if (i4 != i3) {
                                    arrayList2.set(i3, Integer.valueOf(iIntValue));
                                }
                                i3++;
                            } else {
                                obj3 = zzcv.zzM(obj, i, iIntValue, obj3, zzdeVar);
                            }
                        }
                        if (i3 != size) {
                            arrayList2.subList(i3, size).clear();
                        }
                    }
                    arrayList = arrayList2;
                    break;
                case SFIXED32:
                    arrayList = new ArrayList();
                    zzcsVar.zzN(arrayList);
                    break;
                case SFIXED64:
                    arrayList = new ArrayList();
                    zzcsVar.zzO(arrayList);
                    break;
                case SINT32:
                    arrayList = new ArrayList();
                    zzcsVar.zzP(arrayList);
                    break;
                case SINT64:
                    arrayList = new ArrayList();
                    zzcsVar.zzQ(arrayList);
                    break;
            }
            zzaoVar.zze(zzavVar, arrayList);
            return obj3;
        }
        zzdt zzdtVar3 = zzavVar.zzc;
        if (zzdtVar3 != zzdt.ENUM) {
            switch (zzdtVar3) {
                case DOUBLE:
                    objValueOf = Double.valueOf(zzcsVar.zze());
                    break;
                case FLOAT:
                    objValueOf = Float.valueOf(zzcsVar.zzf());
                    break;
                case INT64:
                    objValueOf = Long.valueOf(zzcsVar.zzh());
                    break;
                case UINT64:
                    objValueOf = Long.valueOf(zzcsVar.zzg());
                    break;
                case INT32:
                    objValueOf = Integer.valueOf(zzcsVar.zzi());
                    break;
                case FIXED64:
                    objValueOf = Long.valueOf(zzcsVar.zzj());
                    break;
                case FIXED32:
                    objValueOf = Integer.valueOf(zzcsVar.zzk());
                    break;
                case BOOL:
                    objValueOf = Boolean.valueOf(zzcsVar.zzl());
                    break;
                case STRING:
                    objValueOf = zzcsVar.zzm();
                    break;
                case GROUP:
                    if (!z) {
                        Object objZzd2 = zzaoVar.zzd(zzavVar);
                        if (objZzd2 instanceof zzay) {
                            zzct zzctVarZzb = zzcp.zza().zzb(objZzd2.getClass());
                            if (!((zzay) objZzd2).isMutable()) {
                                Object objZza = zzctVarZzb.zza();
                                zzctVarZzb.zzd(objZza, objZzd2);
                                zzaoVar.zze(zzavVar, objZza);
                                objZzd2 = objZza;
                            }
                            zzcsVar.zzr(objZzd2, zzctVarZzb, zzajVar);
                            return obj3;
                        }
                    }
                    objValueOf = zzcsVar.zzp(zzawVar.zza.getClass(), zzajVar);
                    break;
                case MESSAGE:
                    if (!z) {
                        Object objZzd3 = zzaoVar.zzd(zzavVar);
                        if (objZzd3 instanceof zzay) {
                            zzct zzctVarZzb2 = zzcp.zza().zzb(objZzd3.getClass());
                            if (!((zzay) objZzd3).isMutable()) {
                                Object objZza2 = zzctVarZzb2.zza();
                                zzctVarZzb2.zzd(objZza2, objZzd3);
                                zzaoVar.zze(zzavVar, objZza2);
                                objZzd3 = objZza2;
                            }
                            zzcsVar.zzq(objZzd3, zzctVarZzb2, zzajVar);
                            return obj3;
                        }
                    }
                    objValueOf = zzcsVar.zzo(zzawVar.zza.getClass(), zzajVar);
                    break;
                case BYTES:
                    objValueOf = zzcsVar.zzs();
                    break;
                case UINT32:
                    objValueOf = Integer.valueOf(zzcsVar.zzt());
                    break;
                case ENUM:
                    throw new IllegalStateException("Shouldn't reach here.");
                case SFIXED32:
                    objValueOf = Integer.valueOf(zzcsVar.zzv());
                    break;
                case SFIXED64:
                    objValueOf = Long.valueOf(zzcsVar.zzw());
                    break;
                case SINT32:
                    objValueOf = Integer.valueOf(zzcsVar.zzx());
                    break;
                case SINT64:
                    objValueOf = Long.valueOf(zzcsVar.zzy());
                    break;
                default:
                    objValueOf = null;
                    break;
            }
        } else {
            int iZzi = zzcsVar.zzi();
            if (zzavVar.zza.findValueByNumber(iZzi) == null) {
                return zzcv.zzM(obj, i, iZzi, obj3, zzdeVar);
            }
            objValueOf = Integer.valueOf(iZzi);
        }
        if (z) {
            zzaoVar.zzf(zzavVar, objValueOf);
            return obj3;
        }
        int iOrdinal = zzdtVar3.ordinal();
        if ((iOrdinal == 9 || iOrdinal == 10) && (objZzd = zzaoVar.zzd(zzavVar)) != null) {
            byte[] bArr = zzbj.zzb;
            objValueOf = ((zzcf) objZzd).toBuilder().mergeFrom((zzcf) objValueOf).buildPartial();
        }
        zzaoVar.zze(zzavVar, objValueOf);
        return obj3;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzak
    final int zzf(Map.Entry entry) {
        return ((zzav) entry.getKey()).zzb;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzak
    final void zzg(zzdv zzdvVar, Map.Entry entry) throws IOException {
        zzav zzavVar = (zzav) entry.getKey();
        if (!zzavVar.zzd) {
            zzdt zzdtVar = zzdt.DOUBLE;
            switch (zzavVar.zzc) {
                case DOUBLE:
                    zzdvVar.zzf(zzavVar.zzb, ((Double) entry.getValue()).doubleValue());
                    break;
                case FLOAT:
                    zzdvVar.zze(zzavVar.zzb, ((Float) entry.getValue()).floatValue());
                    break;
                case INT64:
                    zzdvVar.zzc(zzavVar.zzb, ((Long) entry.getValue()).longValue());
                    break;
                case UINT64:
                    zzdvVar.zzh(zzavVar.zzb, ((Long) entry.getValue()).longValue());
                    break;
                case INT32:
                    zzdvVar.zzi(zzavVar.zzb, ((Integer) entry.getValue()).intValue());
                    break;
                case FIXED64:
                    zzdvVar.zzj(zzavVar.zzb, ((Long) entry.getValue()).longValue());
                    break;
                case FIXED32:
                    zzdvVar.zzk(zzavVar.zzb, ((Integer) entry.getValue()).intValue());
                    break;
                case BOOL:
                    zzdvVar.zzl(zzavVar.zzb, ((Boolean) entry.getValue()).booleanValue());
                    break;
                case STRING:
                    zzdvVar.zzm(zzavVar.zzb, (String) entry.getValue());
                    break;
                case GROUP:
                    zzdvVar.zzs(zzavVar.zzb, entry.getValue(), zzcp.zza().zzb(entry.getValue().getClass()));
                    break;
                case MESSAGE:
                    zzdvVar.zzr(zzavVar.zzb, entry.getValue(), zzcp.zza().zzb(entry.getValue().getClass()));
                    break;
                case BYTES:
                    zzdvVar.zzn(zzavVar.zzb, (zzt) entry.getValue());
                    break;
                case UINT32:
                    zzdvVar.zzo(zzavVar.zzb, ((Integer) entry.getValue()).intValue());
                    break;
                case ENUM:
                    zzdvVar.zzi(zzavVar.zzb, ((Integer) entry.getValue()).intValue());
                    break;
                case SFIXED32:
                    zzdvVar.zzb(zzavVar.zzb, ((Integer) entry.getValue()).intValue());
                    break;
                case SFIXED64:
                    zzdvVar.zzd(zzavVar.zzb, ((Long) entry.getValue()).longValue());
                    break;
                case SINT32:
                    zzdvVar.zzp(zzavVar.zzb, ((Integer) entry.getValue()).intValue());
                    break;
                case SINT64:
                    zzdvVar.zzq(zzavVar.zzb, ((Long) entry.getValue()).longValue());
                    break;
            }
        }
        zzdt zzdtVar2 = zzdt.DOUBLE;
        switch (zzavVar.zzc) {
            case DOUBLE:
                zzcv.zzb(zzavVar.zzb, (List) entry.getValue(), zzdvVar, zzavVar.zze);
                break;
            case FLOAT:
                zzcv.zzc(zzavVar.zzb, (List) entry.getValue(), zzdvVar, zzavVar.zze);
                break;
            case INT64:
                zzcv.zzd(zzavVar.zzb, (List) entry.getValue(), zzdvVar, zzavVar.zze);
                break;
            case UINT64:
                zzcv.zze(zzavVar.zzb, (List) entry.getValue(), zzdvVar, zzavVar.zze);
                break;
            case INT32:
                zzcv.zzi(zzavVar.zzb, (List) entry.getValue(), zzdvVar, zzavVar.zze);
                break;
            case FIXED64:
                zzcv.zzg(zzavVar.zzb, (List) entry.getValue(), zzdvVar, zzavVar.zze);
                break;
            case FIXED32:
                zzcv.zzl(zzavVar.zzb, (List) entry.getValue(), zzdvVar, zzavVar.zze);
                break;
            case BOOL:
                zzcv.zzo(zzavVar.zzb, (List) entry.getValue(), zzdvVar, zzavVar.zze);
                break;
            case STRING:
                zzcv.zzp(zzavVar.zzb, (List) entry.getValue(), zzdvVar);
                break;
            case GROUP:
                List list = (List) entry.getValue();
                if (list != null && !list.isEmpty()) {
                    zzcv.zzs(zzavVar.zzb, (List) entry.getValue(), zzdvVar, zzcp.zza().zzb(list.get(0).getClass()));
                    break;
                }
                break;
            case MESSAGE:
                List list2 = (List) entry.getValue();
                if (list2 != null && !list2.isEmpty()) {
                    zzcv.zzr(zzavVar.zzb, (List) entry.getValue(), zzdvVar, zzcp.zza().zzb(list2.get(0).getClass()));
                    break;
                }
                break;
            case BYTES:
                zzcv.zzq(zzavVar.zzb, (List) entry.getValue(), zzdvVar);
                break;
            case UINT32:
                zzcv.zzj(zzavVar.zzb, (List) entry.getValue(), zzdvVar, zzavVar.zze);
                break;
            case ENUM:
                zzcv.zzi(zzavVar.zzb, (List) entry.getValue(), zzdvVar, zzavVar.zze);
                break;
            case SFIXED32:
                zzcv.zzm(zzavVar.zzb, (List) entry.getValue(), zzdvVar, zzavVar.zze);
                break;
            case SFIXED64:
                zzcv.zzh(zzavVar.zzb, (List) entry.getValue(), zzdvVar, zzavVar.zze);
                break;
            case SINT32:
                zzcv.zzk(zzavVar.zzb, (List) entry.getValue(), zzdvVar, zzavVar.zze);
                break;
            case SINT64:
                zzcv.zzf(zzavVar.zzb, (List) entry.getValue(), zzdvVar, zzavVar.zze);
                break;
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzak
    final Object zzh(zzaj zzajVar, zzcf zzcfVar, int i) {
        return zzajVar.zzb(zzcfVar, i);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzak
    final void zzi(zzcs zzcsVar, Object obj, zzaj zzajVar, zzao zzaoVar) throws IOException {
        zzaw zzawVar = (zzaw) obj;
        zzaoVar.zze(zzawVar.zzb, zzcsVar.zzo(zzawVar.zza.getClass(), zzajVar));
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzak
    final void zzj(zzt zztVar, Object obj, zzaj zzajVar, zzao zzaoVar) throws IOException {
        zzaw zzawVar = (zzaw) obj;
        zzce zzceVarNewBuilderForType = zzawVar.zza.newBuilderForType();
        zzx zzxVarZzj = zztVar.zzj();
        zzceVarNewBuilderForType.mergeFrom(zzxVarZzj, zzajVar);
        zzaoVar.zze(zzawVar.zzb, zzceVarNewBuilderForType.buildPartial());
        zzxVarZzj.zzb(0);
    }
}
