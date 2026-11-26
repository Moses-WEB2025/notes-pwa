package com.google.android.appfunctions.schema.internal.dependencies;

import com.miui.richeditor.schema.HtmlParser;
import java.io.IOException;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzy implements zzcs {
    private final zzx zza;
    private int zzb;
    private int zzc;
    private int zzd = 0;

    private zzy(zzx zzxVar) {
        zzbj.zza(zzxVar, HtmlParser.InputElement.TAG);
        this.zza = zzxVar;
        zzxVar.zze = this;
    }

    private final void zzR(int i) throws IOException {
        if ((this.zzb & 7) != i) {
            throw new zzbk("Protocol message tag had invalid wire type.");
        }
    }

    private final void zzS(Object obj, zzct zzctVar, zzaj zzajVar) throws IOException {
        zzx zzxVar = this.zza;
        int iZzo = zzxVar.zzo();
        zzxVar.zzH();
        int iZzz = zzxVar.zzz(iZzo);
        zzxVar.zzb++;
        zzctVar.zzg(obj, this, zzajVar);
        zzxVar.zzb(0);
        zzxVar.zzb--;
        zzxVar.zzA(iZzz);
    }

    private final Object zzT(zzct zzctVar, zzaj zzajVar) throws IOException {
        Object objZza = zzctVar.zza();
        zzS(objZza, zzctVar, zzajVar);
        zzctVar.zzk(objZza);
        return objZza;
    }

    private final void zzU(Object obj, zzct zzctVar, zzaj zzajVar) throws IOException {
        int i = this.zzc;
        this.zzc = ((this.zzb >>> 3) << 3) | 4;
        try {
            zzctVar.zzg(obj, this, zzajVar);
            if (this.zzb != this.zzc) {
                throw new zzbl("Failed to parse the message.");
            }
            this.zzc = i;
        } catch (Throwable th) {
            this.zzc = i;
            throw th;
        }
    }

    private final Object zzV(zzct zzctVar, zzaj zzajVar) throws IOException {
        Object objZza = zzctVar.zza();
        zzU(objZza, zzctVar, zzajVar);
        zzctVar.zzk(objZza);
        return objZza;
    }

    private final void zzW(int i) throws IOException {
        if (this.zza.zzC() != i) {
            throw new zzbl("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
        }
    }

    private static final void zzX(int i) throws IOException {
        if ((i & 3) != 0) {
            throw new zzbl("Failed to parse the message.");
        }
    }

    private static final void zzY(int i) throws IOException {
        if ((i & 7) != 0) {
            throw new zzbl("Failed to parse the message.");
        }
    }

    public static zzy zza(zzx zzxVar) {
        Object obj = zzxVar.zze;
        return obj != null ? (zzy) obj : new zzy(zzxVar);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final void zzA(List list) throws IOException {
        int iZza;
        int iZza2;
        if (list instanceof zzaq) {
            zzaq zzaqVar = (zzaq) list;
            int i = this.zzb & 7;
            if (i == 2) {
                zzx zzxVar = this.zza;
                int iZzo = zzxVar.zzo();
                zzX(iZzo);
                int iZzC = zzxVar.zzC() + iZzo;
                do {
                    zzaqVar.zzg(zzxVar.zze());
                } while (zzxVar.zzC() < iZzC);
                return;
            }
            if (i != 5) {
                throw new zzbk("Protocol message tag had invalid wire type.");
            }
            do {
                zzx zzxVar2 = this.zza;
                zzaqVar.zzg(zzxVar2.zze());
                if (zzxVar2.zzB()) {
                    return;
                } else {
                    iZza2 = zzxVar2.zza();
                }
            } while (iZza2 == this.zzb);
        } else {
            int i2 = this.zzb & 7;
            if (i2 == 2) {
                zzx zzxVar3 = this.zza;
                int iZzo2 = zzxVar3.zzo();
                zzX(iZzo2);
                int iZzC2 = zzxVar3.zzC() + iZzo2;
                do {
                    list.add(Float.valueOf(zzxVar3.zze()));
                } while (zzxVar3.zzC() < iZzC2);
                return;
            }
            if (i2 != 5) {
                throw new zzbk("Protocol message tag had invalid wire type.");
            }
            do {
                zzx zzxVar4 = this.zza;
                list.add(Float.valueOf(zzxVar4.zze()));
                if (zzxVar4.zzB()) {
                    return;
                } else {
                    iZza = zzxVar4.zza();
                }
            } while (iZza == this.zzb);
            iZza2 = iZza;
        }
        this.zzd = iZza2;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final void zzB(List list) throws IOException {
        int iZza;
        int iZza2;
        if (list instanceof zzbu) {
            zzbu zzbuVar = (zzbu) list;
            int i = this.zzb & 7;
            if (i != 0) {
                if (i != 2) {
                    throw new zzbk("Protocol message tag had invalid wire type.");
                }
                zzx zzxVar = this.zza;
                int iZzC = zzxVar.zzC() + zzxVar.zzo();
                do {
                    zzbuVar.zzf(zzxVar.zzf());
                } while (zzxVar.zzC() < iZzC);
                zzW(iZzC);
                return;
            }
            do {
                zzx zzxVar2 = this.zza;
                zzbuVar.zzf(zzxVar2.zzf());
                if (zzxVar2.zzB()) {
                    return;
                } else {
                    iZza2 = zzxVar2.zza();
                }
            } while (iZza2 == this.zzb);
        } else {
            int i2 = this.zzb & 7;
            if (i2 != 0) {
                if (i2 != 2) {
                    throw new zzbk("Protocol message tag had invalid wire type.");
                }
                zzx zzxVar3 = this.zza;
                int iZzC2 = zzxVar3.zzC() + zzxVar3.zzo();
                do {
                    list.add(Long.valueOf(zzxVar3.zzf()));
                } while (zzxVar3.zzC() < iZzC2);
                zzW(iZzC2);
                return;
            }
            do {
                zzx zzxVar4 = this.zza;
                list.add(Long.valueOf(zzxVar4.zzf()));
                if (zzxVar4.zzB()) {
                    return;
                } else {
                    iZza = zzxVar4.zza();
                }
            } while (iZza == this.zzb);
            iZza2 = iZza;
        }
        this.zzd = iZza2;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final void zzC(List list) throws IOException {
        int iZza;
        int iZza2;
        if (list instanceof zzbu) {
            zzbu zzbuVar = (zzbu) list;
            int i = this.zzb & 7;
            if (i != 0) {
                if (i != 2) {
                    throw new zzbk("Protocol message tag had invalid wire type.");
                }
                zzx zzxVar = this.zza;
                int iZzC = zzxVar.zzC() + zzxVar.zzo();
                do {
                    zzbuVar.zzf(zzxVar.zzg());
                } while (zzxVar.zzC() < iZzC);
                zzW(iZzC);
                return;
            }
            do {
                zzx zzxVar2 = this.zza;
                zzbuVar.zzf(zzxVar2.zzg());
                if (zzxVar2.zzB()) {
                    return;
                } else {
                    iZza2 = zzxVar2.zza();
                }
            } while (iZza2 == this.zzb);
        } else {
            int i2 = this.zzb & 7;
            if (i2 != 0) {
                if (i2 != 2) {
                    throw new zzbk("Protocol message tag had invalid wire type.");
                }
                zzx zzxVar3 = this.zza;
                int iZzC2 = zzxVar3.zzC() + zzxVar3.zzo();
                do {
                    list.add(Long.valueOf(zzxVar3.zzg()));
                } while (zzxVar3.zzC() < iZzC2);
                zzW(iZzC2);
                return;
            }
            do {
                zzx zzxVar4 = this.zza;
                list.add(Long.valueOf(zzxVar4.zzg()));
                if (zzxVar4.zzB()) {
                    return;
                } else {
                    iZza = zzxVar4.zza();
                }
            } while (iZza == this.zzb);
            iZza2 = iZza;
        }
        this.zzd = iZza2;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final void zzD(List list) throws IOException {
        int iZza;
        int iZza2;
        if (list instanceof zzaz) {
            zzaz zzazVar = (zzaz) list;
            int i = this.zzb & 7;
            if (i != 0) {
                if (i != 2) {
                    throw new zzbk("Protocol message tag had invalid wire type.");
                }
                zzx zzxVar = this.zza;
                int iZzC = zzxVar.zzC() + zzxVar.zzo();
                do {
                    zzazVar.zzg(zzxVar.zzh());
                } while (zzxVar.zzC() < iZzC);
                zzW(iZzC);
                return;
            }
            do {
                zzx zzxVar2 = this.zza;
                zzazVar.zzg(zzxVar2.zzh());
                if (zzxVar2.zzB()) {
                    return;
                } else {
                    iZza2 = zzxVar2.zza();
                }
            } while (iZza2 == this.zzb);
        } else {
            int i2 = this.zzb & 7;
            if (i2 != 0) {
                if (i2 != 2) {
                    throw new zzbk("Protocol message tag had invalid wire type.");
                }
                zzx zzxVar3 = this.zza;
                int iZzC2 = zzxVar3.zzC() + zzxVar3.zzo();
                do {
                    list.add(Integer.valueOf(zzxVar3.zzh()));
                } while (zzxVar3.zzC() < iZzC2);
                zzW(iZzC2);
                return;
            }
            do {
                zzx zzxVar4 = this.zza;
                list.add(Integer.valueOf(zzxVar4.zzh()));
                if (zzxVar4.zzB()) {
                    return;
                } else {
                    iZza = zzxVar4.zza();
                }
            } while (iZza == this.zzb);
            iZza2 = iZza;
        }
        this.zzd = iZza2;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final void zzE(List list) throws IOException {
        int iZza;
        int iZza2;
        if (list instanceof zzbu) {
            zzbu zzbuVar = (zzbu) list;
            int i = this.zzb & 7;
            if (i != 1) {
                if (i != 2) {
                    throw new zzbk("Protocol message tag had invalid wire type.");
                }
                zzx zzxVar = this.zza;
                int iZzo = zzxVar.zzo();
                zzY(iZzo);
                int iZzC = iZzo + zzxVar.zzC();
                do {
                    zzbuVar.zzf(zzxVar.zzi());
                } while (zzxVar.zzC() < iZzC);
                return;
            }
            do {
                zzx zzxVar2 = this.zza;
                zzbuVar.zzf(zzxVar2.zzi());
                if (zzxVar2.zzB()) {
                    return;
                } else {
                    iZza2 = zzxVar2.zza();
                }
            } while (iZza2 == this.zzb);
        } else {
            int i2 = this.zzb & 7;
            if (i2 != 1) {
                if (i2 != 2) {
                    throw new zzbk("Protocol message tag had invalid wire type.");
                }
                zzx zzxVar3 = this.zza;
                int iZzo2 = zzxVar3.zzo();
                zzY(iZzo2);
                int iZzC2 = iZzo2 + zzxVar3.zzC();
                do {
                    list.add(Long.valueOf(zzxVar3.zzi()));
                } while (zzxVar3.zzC() < iZzC2);
                return;
            }
            do {
                zzx zzxVar4 = this.zza;
                list.add(Long.valueOf(zzxVar4.zzi()));
                if (zzxVar4.zzB()) {
                    return;
                } else {
                    iZza = zzxVar4.zza();
                }
            } while (iZza == this.zzb);
            iZza2 = iZza;
        }
        this.zzd = iZza2;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final void zzF(List list) throws IOException {
        int iZza;
        int iZza2;
        if (list instanceof zzaz) {
            zzaz zzazVar = (zzaz) list;
            int i = this.zzb & 7;
            if (i == 2) {
                zzx zzxVar = this.zza;
                int iZzo = zzxVar.zzo();
                zzX(iZzo);
                int iZzC = zzxVar.zzC() + iZzo;
                do {
                    zzazVar.zzg(zzxVar.zzj());
                } while (zzxVar.zzC() < iZzC);
                return;
            }
            if (i != 5) {
                throw new zzbk("Protocol message tag had invalid wire type.");
            }
            do {
                zzx zzxVar2 = this.zza;
                zzazVar.zzg(zzxVar2.zzj());
                if (zzxVar2.zzB()) {
                    return;
                } else {
                    iZza2 = zzxVar2.zza();
                }
            } while (iZza2 == this.zzb);
        } else {
            int i2 = this.zzb & 7;
            if (i2 == 2) {
                zzx zzxVar3 = this.zza;
                int iZzo2 = zzxVar3.zzo();
                zzX(iZzo2);
                int iZzC2 = zzxVar3.zzC() + iZzo2;
                do {
                    list.add(Integer.valueOf(zzxVar3.zzj()));
                } while (zzxVar3.zzC() < iZzC2);
                return;
            }
            if (i2 != 5) {
                throw new zzbk("Protocol message tag had invalid wire type.");
            }
            do {
                zzx zzxVar4 = this.zza;
                list.add(Integer.valueOf(zzxVar4.zzj()));
                if (zzxVar4.zzB()) {
                    return;
                } else {
                    iZza = zzxVar4.zza();
                }
            } while (iZza == this.zzb);
            iZza2 = iZza;
        }
        this.zzd = iZza2;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final void zzG(List list) throws IOException {
        int iZza;
        int iZza2;
        if (list instanceof zzi) {
            zzi zziVar = (zzi) list;
            int i = this.zzb & 7;
            if (i != 0) {
                if (i != 2) {
                    throw new zzbk("Protocol message tag had invalid wire type.");
                }
                zzx zzxVar = this.zza;
                int iZzC = zzxVar.zzC() + zzxVar.zzo();
                do {
                    zziVar.zzg(zzxVar.zzk());
                } while (zzxVar.zzC() < iZzC);
                zzW(iZzC);
                return;
            }
            do {
                zzx zzxVar2 = this.zza;
                zziVar.zzg(zzxVar2.zzk());
                if (zzxVar2.zzB()) {
                    return;
                } else {
                    iZza2 = zzxVar2.zza();
                }
            } while (iZza2 == this.zzb);
        } else {
            int i2 = this.zzb & 7;
            if (i2 != 0) {
                if (i2 != 2) {
                    throw new zzbk("Protocol message tag had invalid wire type.");
                }
                zzx zzxVar3 = this.zza;
                int iZzC2 = zzxVar3.zzC() + zzxVar3.zzo();
                do {
                    list.add(Boolean.valueOf(zzxVar3.zzk()));
                } while (zzxVar3.zzC() < iZzC2);
                zzW(iZzC2);
                return;
            }
            do {
                zzx zzxVar4 = this.zza;
                list.add(Boolean.valueOf(zzxVar4.zzk()));
                if (zzxVar4.zzB()) {
                    return;
                } else {
                    iZza = zzxVar4.zza();
                }
            } while (iZza == this.zzb);
            iZza2 = iZza;
        }
        this.zzd = iZza2;
    }

    public final void zzH(List list, boolean z) throws IOException {
        int iZza;
        int iZza2;
        if ((this.zzb & 7) != 2) {
            throw new zzbk("Protocol message tag had invalid wire type.");
        }
        if ((list instanceof zzbr) && !z) {
            zzbr zzbrVar = (zzbr) list;
            do {
                zzs();
                zzbrVar.zzb();
                zzx zzxVar = this.zza;
                if (zzxVar.zzB()) {
                    return;
                } else {
                    iZza2 = zzxVar.zza();
                }
            } while (iZza2 == this.zzb);
        } else {
            do {
                list.add(z ? zzn() : zzm());
                zzx zzxVar2 = this.zza;
                if (zzxVar2.zzB()) {
                    return;
                } else {
                    iZza = zzxVar2.zza();
                }
            } while (iZza == this.zzb);
            iZza2 = iZza;
        }
        this.zzd = iZza2;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final void zzI(List list, zzct zzctVar, zzaj zzajVar) throws IOException {
        int iZza;
        int i = this.zzb;
        if ((i & 7) != 2) {
            throw new zzbk("Protocol message tag had invalid wire type.");
        }
        do {
            list.add(zzT(zzctVar, zzajVar));
            zzx zzxVar = this.zza;
            if (zzxVar.zzB() || this.zzd != 0) {
                return;
            } else {
                iZza = zzxVar.zza();
            }
        } while (iZza == i);
        this.zzd = iZza;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    @Deprecated
    public final void zzJ(List list, zzct zzctVar, zzaj zzajVar) throws IOException {
        int iZza;
        int i = this.zzb;
        if ((i & 7) != 3) {
            throw new zzbk("Protocol message tag had invalid wire type.");
        }
        do {
            list.add(zzV(zzctVar, zzajVar));
            zzx zzxVar = this.zza;
            if (zzxVar.zzB() || this.zzd != 0) {
                return;
            } else {
                iZza = zzxVar.zza();
            }
        } while (iZza == i);
        this.zzd = iZza;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final void zzK(List list) throws IOException {
        int iZza;
        if ((this.zzb & 7) != 2) {
            throw new zzbk("Protocol message tag had invalid wire type.");
        }
        do {
            list.add(zzs());
            zzx zzxVar = this.zza;
            if (zzxVar.zzB()) {
                return;
            } else {
                iZza = zzxVar.zza();
            }
        } while (iZza == this.zzb);
        this.zzd = iZza;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final void zzL(List list) throws IOException {
        int iZza;
        int iZza2;
        if (list instanceof zzaz) {
            zzaz zzazVar = (zzaz) list;
            int i = this.zzb & 7;
            if (i != 0) {
                if (i != 2) {
                    throw new zzbk("Protocol message tag had invalid wire type.");
                }
                zzx zzxVar = this.zza;
                int iZzC = zzxVar.zzC() + zzxVar.zzo();
                do {
                    zzazVar.zzg(zzxVar.zzo());
                } while (zzxVar.zzC() < iZzC);
                zzW(iZzC);
                return;
            }
            do {
                zzx zzxVar2 = this.zza;
                zzazVar.zzg(zzxVar2.zzo());
                if (zzxVar2.zzB()) {
                    return;
                } else {
                    iZza2 = zzxVar2.zza();
                }
            } while (iZza2 == this.zzb);
        } else {
            int i2 = this.zzb & 7;
            if (i2 != 0) {
                if (i2 != 2) {
                    throw new zzbk("Protocol message tag had invalid wire type.");
                }
                zzx zzxVar3 = this.zza;
                int iZzC2 = zzxVar3.zzC() + zzxVar3.zzo();
                do {
                    list.add(Integer.valueOf(zzxVar3.zzo()));
                } while (zzxVar3.zzC() < iZzC2);
                zzW(iZzC2);
                return;
            }
            do {
                zzx zzxVar4 = this.zza;
                list.add(Integer.valueOf(zzxVar4.zzo()));
                if (zzxVar4.zzB()) {
                    return;
                } else {
                    iZza = zzxVar4.zza();
                }
            } while (iZza == this.zzb);
            iZza2 = iZza;
        }
        this.zzd = iZza2;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final void zzM(List list) throws IOException {
        int iZza;
        int iZza2;
        if (list instanceof zzaz) {
            zzaz zzazVar = (zzaz) list;
            int i = this.zzb & 7;
            if (i != 0) {
                if (i != 2) {
                    throw new zzbk("Protocol message tag had invalid wire type.");
                }
                zzx zzxVar = this.zza;
                int iZzC = zzxVar.zzC() + zzxVar.zzo();
                do {
                    zzazVar.zzg(zzxVar.zzp());
                } while (zzxVar.zzC() < iZzC);
                zzW(iZzC);
                return;
            }
            do {
                zzx zzxVar2 = this.zza;
                zzazVar.zzg(zzxVar2.zzp());
                if (zzxVar2.zzB()) {
                    return;
                } else {
                    iZza2 = zzxVar2.zza();
                }
            } while (iZza2 == this.zzb);
        } else {
            int i2 = this.zzb & 7;
            if (i2 != 0) {
                if (i2 != 2) {
                    throw new zzbk("Protocol message tag had invalid wire type.");
                }
                zzx zzxVar3 = this.zza;
                int iZzC2 = zzxVar3.zzC() + zzxVar3.zzo();
                do {
                    list.add(Integer.valueOf(zzxVar3.zzp()));
                } while (zzxVar3.zzC() < iZzC2);
                zzW(iZzC2);
                return;
            }
            do {
                zzx zzxVar4 = this.zza;
                list.add(Integer.valueOf(zzxVar4.zzp()));
                if (zzxVar4.zzB()) {
                    return;
                } else {
                    iZza = zzxVar4.zza();
                }
            } while (iZza == this.zzb);
            iZza2 = iZza;
        }
        this.zzd = iZza2;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final void zzN(List list) throws IOException {
        int iZza;
        int iZza2;
        if (list instanceof zzaz) {
            zzaz zzazVar = (zzaz) list;
            int i = this.zzb & 7;
            if (i == 2) {
                zzx zzxVar = this.zza;
                int iZzo = zzxVar.zzo();
                zzX(iZzo);
                int iZzC = zzxVar.zzC() + iZzo;
                do {
                    zzazVar.zzg(zzxVar.zzq());
                } while (zzxVar.zzC() < iZzC);
                return;
            }
            if (i != 5) {
                throw new zzbk("Protocol message tag had invalid wire type.");
            }
            do {
                zzx zzxVar2 = this.zza;
                zzazVar.zzg(zzxVar2.zzq());
                if (zzxVar2.zzB()) {
                    return;
                } else {
                    iZza2 = zzxVar2.zza();
                }
            } while (iZza2 == this.zzb);
        } else {
            int i2 = this.zzb & 7;
            if (i2 == 2) {
                zzx zzxVar3 = this.zza;
                int iZzo2 = zzxVar3.zzo();
                zzX(iZzo2);
                int iZzC2 = zzxVar3.zzC() + iZzo2;
                do {
                    list.add(Integer.valueOf(zzxVar3.zzq()));
                } while (zzxVar3.zzC() < iZzC2);
                return;
            }
            if (i2 != 5) {
                throw new zzbk("Protocol message tag had invalid wire type.");
            }
            do {
                zzx zzxVar4 = this.zza;
                list.add(Integer.valueOf(zzxVar4.zzq()));
                if (zzxVar4.zzB()) {
                    return;
                } else {
                    iZza = zzxVar4.zza();
                }
            } while (iZza == this.zzb);
            iZza2 = iZza;
        }
        this.zzd = iZza2;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final void zzO(List list) throws IOException {
        int iZza;
        int iZza2;
        if (list instanceof zzbu) {
            zzbu zzbuVar = (zzbu) list;
            int i = this.zzb & 7;
            if (i != 1) {
                if (i != 2) {
                    throw new zzbk("Protocol message tag had invalid wire type.");
                }
                zzx zzxVar = this.zza;
                int iZzo = zzxVar.zzo();
                zzY(iZzo);
                int iZzC = iZzo + zzxVar.zzC();
                do {
                    zzbuVar.zzf(zzxVar.zzr());
                } while (zzxVar.zzC() < iZzC);
                return;
            }
            do {
                zzx zzxVar2 = this.zza;
                zzbuVar.zzf(zzxVar2.zzr());
                if (zzxVar2.zzB()) {
                    return;
                } else {
                    iZza2 = zzxVar2.zza();
                }
            } while (iZza2 == this.zzb);
        } else {
            int i2 = this.zzb & 7;
            if (i2 != 1) {
                if (i2 != 2) {
                    throw new zzbk("Protocol message tag had invalid wire type.");
                }
                zzx zzxVar3 = this.zza;
                int iZzo2 = zzxVar3.zzo();
                zzY(iZzo2);
                int iZzC2 = iZzo2 + zzxVar3.zzC();
                do {
                    list.add(Long.valueOf(zzxVar3.zzr()));
                } while (zzxVar3.zzC() < iZzC2);
                return;
            }
            do {
                zzx zzxVar4 = this.zza;
                list.add(Long.valueOf(zzxVar4.zzr()));
                if (zzxVar4.zzB()) {
                    return;
                } else {
                    iZza = zzxVar4.zza();
                }
            } while (iZza == this.zzb);
            iZza2 = iZza;
        }
        this.zzd = iZza2;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final void zzP(List list) throws IOException {
        int iZza;
        int iZza2;
        if (list instanceof zzaz) {
            zzaz zzazVar = (zzaz) list;
            int i = this.zzb & 7;
            if (i != 0) {
                if (i != 2) {
                    throw new zzbk("Protocol message tag had invalid wire type.");
                }
                zzx zzxVar = this.zza;
                int iZzC = zzxVar.zzC() + zzxVar.zzo();
                do {
                    zzazVar.zzg(zzxVar.zzs());
                } while (zzxVar.zzC() < iZzC);
                zzW(iZzC);
                return;
            }
            do {
                zzx zzxVar2 = this.zza;
                zzazVar.zzg(zzxVar2.zzs());
                if (zzxVar2.zzB()) {
                    return;
                } else {
                    iZza2 = zzxVar2.zza();
                }
            } while (iZza2 == this.zzb);
        } else {
            int i2 = this.zzb & 7;
            if (i2 != 0) {
                if (i2 != 2) {
                    throw new zzbk("Protocol message tag had invalid wire type.");
                }
                zzx zzxVar3 = this.zza;
                int iZzC2 = zzxVar3.zzC() + zzxVar3.zzo();
                do {
                    list.add(Integer.valueOf(zzxVar3.zzs()));
                } while (zzxVar3.zzC() < iZzC2);
                zzW(iZzC2);
                return;
            }
            do {
                zzx zzxVar4 = this.zza;
                list.add(Integer.valueOf(zzxVar4.zzs()));
                if (zzxVar4.zzB()) {
                    return;
                } else {
                    iZza = zzxVar4.zza();
                }
            } while (iZza == this.zzb);
            iZza2 = iZza;
        }
        this.zzd = iZza2;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final void zzQ(List list) throws IOException {
        int iZza;
        int iZza2;
        if (list instanceof zzbu) {
            zzbu zzbuVar = (zzbu) list;
            int i = this.zzb & 7;
            if (i != 0) {
                if (i != 2) {
                    throw new zzbk("Protocol message tag had invalid wire type.");
                }
                zzx zzxVar = this.zza;
                int iZzC = zzxVar.zzC() + zzxVar.zzo();
                do {
                    zzbuVar.zzf(zzxVar.zzt());
                } while (zzxVar.zzC() < iZzC);
                zzW(iZzC);
                return;
            }
            do {
                zzx zzxVar2 = this.zza;
                zzbuVar.zzf(zzxVar2.zzt());
                if (zzxVar2.zzB()) {
                    return;
                } else {
                    iZza2 = zzxVar2.zza();
                }
            } while (iZza2 == this.zzb);
        } else {
            int i2 = this.zzb & 7;
            if (i2 != 0) {
                if (i2 != 2) {
                    throw new zzbk("Protocol message tag had invalid wire type.");
                }
                zzx zzxVar3 = this.zza;
                int iZzC2 = zzxVar3.zzC() + zzxVar3.zzo();
                do {
                    list.add(Long.valueOf(zzxVar3.zzt()));
                } while (zzxVar3.zzC() < iZzC2);
                zzW(iZzC2);
                return;
            }
            do {
                zzx zzxVar4 = this.zza;
                list.add(Long.valueOf(zzxVar4.zzt()));
                if (zzxVar4.zzB()) {
                    return;
                } else {
                    iZza = zzxVar4.zza();
                }
            } while (iZza == this.zzb);
            iZza2 = iZza;
        }
        this.zzd = iZza2;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final int zzb() throws IOException {
        int iZza = this.zzd;
        if (iZza != 0) {
            this.zzb = iZza;
            this.zzd = 0;
        } else {
            iZza = this.zza.zza();
            this.zzb = iZza;
        }
        if (iZza == 0 || iZza == this.zzc) {
            return Integer.MAX_VALUE;
        }
        return iZza >>> 3;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final int zzc() {
        return this.zzb;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final boolean zzd() throws IOException {
        int i;
        zzx zzxVar = this.zza;
        if (zzxVar.zzB() || (i = this.zzb) == this.zzc) {
            return false;
        }
        return zzxVar.zzc(i);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final double zze() throws IOException {
        zzR(1);
        return this.zza.zzd();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final float zzf() throws IOException {
        zzR(5);
        return this.zza.zze();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final long zzg() throws IOException {
        zzR(0);
        return this.zza.zzf();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final long zzh() throws IOException {
        zzR(0);
        return this.zza.zzg();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final int zzi() throws IOException {
        zzR(0);
        return this.zza.zzh();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final long zzj() throws IOException {
        zzR(1);
        return this.zza.zzi();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final int zzk() throws IOException {
        zzR(5);
        return this.zza.zzj();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final boolean zzl() throws IOException {
        zzR(0);
        return this.zza.zzk();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final String zzm() throws IOException {
        zzR(2);
        return this.zza.zzl();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final String zzn() throws IOException {
        zzR(2);
        return this.zza.zzm();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final Object zzo(Class cls, zzaj zzajVar) throws IOException {
        zzR(2);
        return zzT(zzcp.zza().zzb(cls), zzajVar);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    @Deprecated
    public final Object zzp(Class cls, zzaj zzajVar) throws IOException {
        zzR(3);
        return zzV(zzcp.zza().zzb(cls), zzajVar);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final void zzq(Object obj, zzct zzctVar, zzaj zzajVar) throws IOException {
        zzR(2);
        zzS(obj, zzctVar, zzajVar);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final void zzr(Object obj, zzct zzctVar, zzaj zzajVar) throws IOException {
        zzR(3);
        zzU(obj, zzctVar, zzajVar);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final zzt zzs() throws IOException {
        zzR(2);
        return this.zza.zzn();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final int zzt() throws IOException {
        zzR(0);
        return this.zza.zzo();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final int zzu() throws IOException {
        zzR(0);
        return this.zza.zzp();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final int zzv() throws IOException {
        zzR(5);
        return this.zza.zzq();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final long zzw() throws IOException {
        zzR(1);
        return this.zza.zzr();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final int zzx() throws IOException {
        zzR(0);
        return this.zza.zzs();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final long zzy() throws IOException {
        zzR(0);
        return this.zza.zzt();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcs
    public final void zzz(List list) throws IOException {
        int iZza;
        int iZza2;
        if (list instanceof zzaf) {
            zzaf zzafVar = (zzaf) list;
            int i = this.zzb & 7;
            if (i != 1) {
                if (i != 2) {
                    throw new zzbk("Protocol message tag had invalid wire type.");
                }
                zzx zzxVar = this.zza;
                int iZzo = zzxVar.zzo();
                zzY(iZzo);
                int iZzC = iZzo + zzxVar.zzC();
                do {
                    zzafVar.zzg(zzxVar.zzd());
                } while (zzxVar.zzC() < iZzC);
                return;
            }
            do {
                zzx zzxVar2 = this.zza;
                zzafVar.zzg(zzxVar2.zzd());
                if (zzxVar2.zzB()) {
                    return;
                } else {
                    iZza2 = zzxVar2.zza();
                }
            } while (iZza2 == this.zzb);
        } else {
            int i2 = this.zzb & 7;
            if (i2 != 1) {
                if (i2 != 2) {
                    throw new zzbk("Protocol message tag had invalid wire type.");
                }
                zzx zzxVar3 = this.zza;
                int iZzo2 = zzxVar3.zzo();
                zzY(iZzo2);
                int iZzC2 = iZzo2 + zzxVar3.zzC();
                do {
                    list.add(Double.valueOf(zzxVar3.zzd()));
                } while (zzxVar3.zzC() < iZzC2);
                return;
            }
            do {
                zzx zzxVar4 = this.zza;
                list.add(Double.valueOf(zzxVar4.zzd()));
                if (zzxVar4.zzB()) {
                    return;
                } else {
                    iZza = zzxVar4.zza();
                }
            } while (iZza == this.zzb);
            iZza2 = iZza;
        }
        this.zzd = iZza2;
    }
}
