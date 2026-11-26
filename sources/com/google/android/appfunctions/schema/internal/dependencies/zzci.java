package com.google.android.appfunctions.schema.internal.dependencies;

import androidx.hardware.DataSpace;
import com.xiaomi.aicr.vision.VisionAttribute;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import sun.misc.Unsafe;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzci implements zzct {
    private static final int[] zza = new int[0];
    private static final Unsafe zzb = zzdm.zzu();
    private final int[] zzc;
    private final Object[] zzd;
    private final int zze;
    private final int zzf;
    private final zzcf zzg;
    private final boolean zzh;
    private final boolean zzi;
    private final int[] zzj;
    private final int zzk;
    private final int zzl;
    private final zzde zzm;
    private final zzak zzn;

    private zzci(int[] iArr, Object[] objArr, int i, int i2, zzcf zzcfVar, boolean z, int[] iArr2, int i3, int i4, zzcl zzclVar, zzbs zzbsVar, zzde zzdeVar, zzak zzakVar, zzca zzcaVar) {
        this.zzc = iArr;
        this.zzd = objArr;
        this.zze = i;
        this.zzf = i2;
        this.zzi = zzcfVar instanceof zzay;
        boolean z2 = false;
        if (zzakVar != null && zzakVar.zza(zzcfVar)) {
            z2 = true;
        }
        this.zzh = z2;
        this.zzj = iArr2;
        this.zzk = i3;
        this.zzl = i4;
        this.zzm = zzdeVar;
        this.zzn = zzakVar;
        this.zzg = zzcfVar;
    }

    private final int zzA(int i) {
        return this.zzc[i + 1];
    }

    private final int zzB(int i) {
        return this.zzc[i + 2];
    }

    private static int zzC(int i) {
        return (i >>> 20) & 255;
    }

    private static boolean zzD(int i) {
        return (i & VisionAttribute.VISION_TYPE_CLASS_ENTITY) != 0;
    }

    private static boolean zzE(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof zzay) {
            return ((zzay) obj).isMutable();
        }
        return true;
    }

    private static void zzF(Object obj) {
        if (zzE(obj)) {
            return;
        }
        String strValueOf = String.valueOf(obj);
        String.valueOf(strValueOf);
        throw new IllegalArgumentException("Mutating immutable message: ".concat(String.valueOf(strValueOf)));
    }

    private static double zzG(Object obj, long j) {
        return ((Double) zzdm.zzn(obj, j)).doubleValue();
    }

    private static float zzH(Object obj, long j) {
        return ((Float) zzdm.zzn(obj, j)).floatValue();
    }

    private static int zzI(Object obj, long j) {
        return ((Integer) zzdm.zzn(obj, j)).intValue();
    }

    private static long zzJ(Object obj, long j) {
        return ((Long) zzdm.zzn(obj, j)).longValue();
    }

    private static boolean zzK(Object obj, long j) {
        return ((Boolean) zzdm.zzn(obj, j)).booleanValue();
    }

    private final boolean zzL(Object obj, Object obj2, int i) {
        return zzN(obj, i) == zzN(obj2, i);
    }

    private final boolean zzM(Object obj, int i, int i2, int i3, int i4) {
        if (i2 == 1048575) {
            return zzN(obj, i);
        }
        return (i3 & i4) != 0;
    }

    private final boolean zzN(Object obj, int i) {
        int iZzB = zzB(i);
        long j = iZzB & 1048575;
        if (j != 1048575) {
            return ((1 << (iZzB >>> 20)) & zzdm.zzd(obj, j)) != 0;
        }
        int iZzA = zzA(i);
        long j2 = iZzA & 1048575;
        switch (zzC(iZzA)) {
            case 0:
                return Double.doubleToRawLongBits(zzdm.zzl(obj, j2)) != 0;
            case 1:
                return Float.floatToRawIntBits(zzdm.zzj(obj, j2)) != 0;
            case 2:
                return zzdm.zzf(obj, j2) != 0;
            case 3:
                return zzdm.zzf(obj, j2) != 0;
            case 4:
                return zzdm.zzd(obj, j2) != 0;
            case 5:
                return zzdm.zzf(obj, j2) != 0;
            case 6:
                return zzdm.zzd(obj, j2) != 0;
            case 7:
                return zzdm.zzh(obj, j2);
            case 8:
                Object objZzn = zzdm.zzn(obj, j2);
                if (objZzn instanceof String) {
                    return !((String) objZzn).isEmpty();
                }
                if (objZzn instanceof zzt) {
                    return !zzt.zza.equals(objZzn);
                }
                throw new IllegalArgumentException();
            case 9:
                return zzdm.zzn(obj, j2) != null;
            case 10:
                return !zzt.zza.equals(zzdm.zzn(obj, j2));
            case 11:
                return zzdm.zzd(obj, j2) != 0;
            case 12:
                return zzdm.zzd(obj, j2) != 0;
            case 13:
                return zzdm.zzd(obj, j2) != 0;
            case 14:
                return zzdm.zzf(obj, j2) != 0;
            case 15:
                return zzdm.zzd(obj, j2) != 0;
            case 16:
                return zzdm.zzf(obj, j2) != 0;
            case 17:
                return zzdm.zzn(obj, j2) != null;
            default:
                throw new IllegalArgumentException();
        }
    }

    private final void zzO(Object obj, int i) {
        int iZzB = zzB(i);
        long j = 1048575 & iZzB;
        if (j == 1048575) {
            return;
        }
        zzdm.zze(obj, j, (1 << (iZzB >>> 20)) | zzdm.zzd(obj, j));
    }

    private final boolean zzP(Object obj, int i, int i2) {
        return zzdm.zzd(obj, (long) (zzB(i2) & 1048575)) == i;
    }

    private final void zzQ(Object obj, int i, int i2) {
        zzdm.zze(obj, zzB(i2) & 1048575, i);
    }

    private final int zzR(int i) {
        if (i < this.zze || i > this.zzf) {
            return -1;
        }
        return zzS(i, 0);
    }

    private final int zzS(int i, int i2) {
        int[] iArr = this.zzc;
        int length = (iArr.length / 3) - 1;
        while (i2 <= length) {
            int i3 = (length + i2) >>> 1;
            int i4 = i3 * 3;
            int i5 = iArr[i4];
            if (i == i5) {
                return i4;
            }
            if (i < i5) {
                length = i3 - 1;
            } else {
                i2 = i3 + 1;
            }
        }
        return -1;
    }

    private static final void zzT(int i, Object obj, zzdv zzdvVar) throws IOException {
        if (obj instanceof String) {
            zzdvVar.zzm(i, (String) obj);
        } else {
            zzdvVar.zzn(i, (zzt) obj);
        }
    }

    static zzdf zzh(Object obj) {
        zzay zzayVar = (zzay) obj;
        zzdf zzdfVar = zzayVar.unknownFields;
        if (zzdfVar != zzdf.zza()) {
            return zzdfVar;
        }
        zzdf zzdfVarZzb = zzdf.zzb();
        zzayVar.unknownFields = zzdfVarZzb;
        return zzdfVarZzb;
    }

    /* JADX WARN: Code duplicated, block: B:126:0x026d  */
    /* JADX WARN: Code duplicated, block: B:127:0x0270  */
    /* JADX WARN: Code duplicated, block: B:130:0x028a  */
    /* JADX WARN: Code duplicated, block: B:131:0x028d  */
    /* JADX WARN: Code duplicated, block: B:170:0x034e  */
    /* JADX WARN: Code duplicated, block: B:185:0x03a3  */
    /* JADX WARN: Code duplicated, block: B:188:0x03ad  */
    static zzci zzm(Class cls, zzcc zzccVar, zzcl zzclVar, zzbs zzbsVar, zzde zzdeVar, zzak zzakVar, zzca zzcaVar) {
        int i;
        int iCharAt;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int[] iArr;
        int i7;
        int i8;
        char cCharAt;
        int i9;
        char cCharAt2;
        int i10;
        char cCharAt3;
        int i11;
        char cCharAt4;
        int i12;
        char cCharAt5;
        int i13;
        char cCharAt6;
        int i14;
        char cCharAt7;
        int i15;
        char cCharAt8;
        int i16;
        int i17;
        int i18;
        int iObjectFieldOffset;
        char c;
        int iObjectFieldOffset2;
        int i19;
        int i20;
        int i21;
        Field fieldZzn;
        char cCharAt9;
        int i22;
        int i23;
        int i24;
        int i25;
        int i26;
        Object obj;
        Field fieldZzn2;
        int i27;
        Object obj2;
        Field fieldZzn3;
        int i28;
        char cCharAt10;
        int i29;
        char cCharAt11;
        int i30;
        char cCharAt12;
        int i31;
        char cCharAt13;
        if (!(zzccVar instanceof zzcr)) {
            throw null;
        }
        zzcr zzcrVar = (zzcr) zzccVar;
        String strZzd = zzcrVar.zzd();
        int length = strZzd.length();
        char c2 = 55296;
        if (strZzd.charAt(0) >= 55296) {
            int i32 = 1;
            while (true) {
                i = i32 + 1;
                if (strZzd.charAt(i32) < 55296) {
                    break;
                }
                i32 = i;
            }
        } else {
            i = 1;
        }
        int i33 = i + 1;
        int iCharAt2 = strZzd.charAt(i);
        if (iCharAt2 >= 55296) {
            int i34 = iCharAt2 & 8191;
            int i35 = 13;
            while (true) {
                i31 = i33 + 1;
                cCharAt13 = strZzd.charAt(i33);
                if (cCharAt13 < 55296) {
                    break;
                }
                i34 |= (cCharAt13 & 8191) << i35;
                i35 += 13;
                i33 = i31;
            }
            iCharAt2 = i34 | (cCharAt13 << i35);
            i33 = i31;
        }
        if (iCharAt2 == 0) {
            i3 = 0;
            i6 = 0;
            iCharAt = 0;
            i2 = 0;
            i4 = 0;
            i5 = 0;
            iArr = zza;
            i7 = 0;
        } else {
            int i36 = i33 + 1;
            int iCharAt3 = strZzd.charAt(i33);
            if (iCharAt3 >= 55296) {
                int i37 = iCharAt3 & 8191;
                int i38 = 13;
                while (true) {
                    i15 = i36 + 1;
                    cCharAt8 = strZzd.charAt(i36);
                    if (cCharAt8 < 55296) {
                        break;
                    }
                    i37 |= (cCharAt8 & 8191) << i38;
                    i38 += 13;
                    i36 = i15;
                }
                iCharAt3 = i37 | (cCharAt8 << i38);
                i36 = i15;
            }
            int i39 = i36 + 1;
            int iCharAt4 = strZzd.charAt(i36);
            if (iCharAt4 >= 55296) {
                int i40 = iCharAt4 & 8191;
                int i41 = 13;
                while (true) {
                    i14 = i39 + 1;
                    cCharAt7 = strZzd.charAt(i39);
                    if (cCharAt7 < 55296) {
                        break;
                    }
                    i40 |= (cCharAt7 & 8191) << i41;
                    i41 += 13;
                    i39 = i14;
                }
                iCharAt4 = i40 | (cCharAt7 << i41);
                i39 = i14;
            }
            int i42 = i39 + 1;
            int iCharAt5 = strZzd.charAt(i39);
            if (iCharAt5 >= 55296) {
                int i43 = iCharAt5 & 8191;
                int i44 = 13;
                while (true) {
                    i13 = i42 + 1;
                    cCharAt6 = strZzd.charAt(i42);
                    if (cCharAt6 < 55296) {
                        break;
                    }
                    i43 |= (cCharAt6 & 8191) << i44;
                    i44 += 13;
                    i42 = i13;
                }
                iCharAt5 = i43 | (cCharAt6 << i44);
                i42 = i13;
            }
            int i45 = i42 + 1;
            int iCharAt6 = strZzd.charAt(i42);
            if (iCharAt6 >= 55296) {
                int i46 = iCharAt6 & 8191;
                int i47 = 13;
                while (true) {
                    i12 = i45 + 1;
                    cCharAt5 = strZzd.charAt(i45);
                    if (cCharAt5 < 55296) {
                        break;
                    }
                    i46 |= (cCharAt5 & 8191) << i47;
                    i47 += 13;
                    i45 = i12;
                }
                iCharAt6 = i46 | (cCharAt5 << i47);
                i45 = i12;
            }
            int i48 = i45 + 1;
            iCharAt = strZzd.charAt(i45);
            if (iCharAt >= 55296) {
                int i49 = iCharAt & 8191;
                int i50 = 13;
                while (true) {
                    i11 = i48 + 1;
                    cCharAt4 = strZzd.charAt(i48);
                    if (cCharAt4 < 55296) {
                        break;
                    }
                    i49 |= (cCharAt4 & 8191) << i50;
                    i50 += 13;
                    i48 = i11;
                }
                iCharAt = i49 | (cCharAt4 << i50);
                i48 = i11;
            }
            int i51 = i48 + 1;
            int iCharAt7 = strZzd.charAt(i48);
            if (iCharAt7 >= 55296) {
                int i52 = iCharAt7 & 8191;
                int i53 = 13;
                while (true) {
                    i10 = i51 + 1;
                    cCharAt3 = strZzd.charAt(i51);
                    if (cCharAt3 < 55296) {
                        break;
                    }
                    i52 |= (cCharAt3 & 8191) << i53;
                    i53 += 13;
                    i51 = i10;
                }
                iCharAt7 = i52 | (cCharAt3 << i53);
                i51 = i10;
            }
            int i54 = i51 + 1;
            int iCharAt8 = strZzd.charAt(i51);
            if (iCharAt8 >= 55296) {
                int i55 = iCharAt8 & 8191;
                int i56 = 13;
                while (true) {
                    i9 = i54 + 1;
                    cCharAt2 = strZzd.charAt(i54);
                    if (cCharAt2 < 55296) {
                        break;
                    }
                    i55 |= (cCharAt2 & 8191) << i56;
                    i56 += 13;
                    i54 = i9;
                }
                iCharAt8 = i55 | (cCharAt2 << i56);
                i54 = i9;
            }
            int i57 = i54 + 1;
            int iCharAt9 = strZzd.charAt(i54);
            if (iCharAt9 >= 55296) {
                int i58 = iCharAt9 & 8191;
                int i59 = 13;
                while (true) {
                    i8 = i57 + 1;
                    cCharAt = strZzd.charAt(i57);
                    if (cCharAt < 55296) {
                        break;
                    }
                    i58 |= (cCharAt & 8191) << i59;
                    i59 += 13;
                    i57 = i8;
                }
                iCharAt9 = i58 | (cCharAt << i59);
                i57 = i8;
            }
            int i60 = iCharAt3 + iCharAt3 + iCharAt4;
            int[] iArr2 = new int[iCharAt9 + iCharAt7 + iCharAt8];
            int i61 = iCharAt7;
            i2 = iCharAt5;
            i3 = i61;
            i4 = iCharAt6;
            i5 = iCharAt9;
            i6 = i60;
            iArr = iArr2;
            i7 = iCharAt3;
            i33 = i57;
        }
        Unsafe unsafe = zzb;
        Object[] objArrZze = zzcrVar.zze();
        Class<?> cls2 = zzcrVar.zzb().getClass();
        int i62 = i5 + i3;
        int i63 = iCharAt + iCharAt;
        int[] iArr3 = new int[iCharAt * 3];
        Object[] objArr = new Object[i63];
        int i64 = i5;
        int i65 = i62;
        int i66 = 0;
        int i67 = 0;
        while (i33 < length) {
            int i68 = i33 + 1;
            int iCharAt10 = strZzd.charAt(i33);
            if (iCharAt10 >= c2) {
                int i69 = iCharAt10 & 8191;
                int i70 = i68;
                int i71 = 13;
                while (true) {
                    i30 = i70 + 1;
                    cCharAt12 = strZzd.charAt(i70);
                    if (cCharAt12 < c2) {
                        break;
                    }
                    i69 |= (cCharAt12 & 8191) << i71;
                    i71 += 13;
                    i70 = i30;
                }
                iCharAt10 = i69 | (cCharAt12 << i71);
                i16 = i30;
            } else {
                i16 = i68;
            }
            int i72 = i16 + 1;
            int iCharAt11 = strZzd.charAt(i16);
            if (iCharAt11 >= c2) {
                int i73 = iCharAt11 & 8191;
                int i74 = i72;
                int i75 = 13;
                while (true) {
                    i29 = i74 + 1;
                    cCharAt11 = strZzd.charAt(i74);
                    if (cCharAt11 < c2) {
                        break;
                    }
                    i73 |= (cCharAt11 & 8191) << i75;
                    i75 += 13;
                    i74 = i29;
                }
                iCharAt11 = i73 | (cCharAt11 << i75);
                i17 = i29;
            } else {
                i17 = i72;
            }
            if ((iCharAt11 & 1024) != 0) {
                iArr[i66] = i67;
                i66++;
            }
            int i76 = iCharAt11 & 255;
            zzcr zzcrVar2 = zzcrVar;
            int i77 = iCharAt11 & 2048;
            if (i76 >= 51) {
                int i78 = i17 + 1;
                int iCharAt12 = strZzd.charAt(i17);
                char c3 = 55296;
                if (iCharAt12 >= 55296) {
                    int i79 = iCharAt12 & 8191;
                    int i80 = i78;
                    int i81 = 13;
                    while (true) {
                        i28 = i80 + 1;
                        cCharAt10 = strZzd.charAt(i80);
                        if (cCharAt10 < c3) {
                            break;
                        }
                        i79 |= (cCharAt10 & 8191) << i81;
                        i81 += 13;
                        i80 = i28;
                        c3 = 55296;
                    }
                    iCharAt12 = i79 | (cCharAt10 << i81);
                    i23 = i28;
                } else {
                    i23 = i78;
                }
                int i82 = i23;
                int i83 = i76 - 51;
                if (i83 == 9 || i83 == 17) {
                    i24 = i6 + 1;
                    int i84 = i67 / 3;
                    objArr[i84 + i84 + 1] = objArrZze[i6];
                } else {
                    if (i83 != 12) {
                        i25 = i77;
                    } else if (zzcrVar2.zzc() == 1 || i77 != 0) {
                        i24 = i6 + 1;
                        int i85 = i67 / 3;
                        objArr[i85 + i85 + 1] = objArrZze[i6];
                    } else {
                        i25 = 0;
                    }
                    i26 = iCharAt12 + iCharAt12;
                    obj = objArrZze[i26];
                    int i86 = i25;
                    if (obj instanceof Field) {
                        fieldZzn2 = (Field) obj;
                    } else {
                        fieldZzn2 = zzn(cls2, (String) obj);
                        objArrZze[i26] = fieldZzn2;
                    }
                    int i87 = i7;
                    iObjectFieldOffset = (int) unsafe.objectFieldOffset(fieldZzn2);
                    i27 = i26 + 1;
                    obj2 = objArrZze[i27];
                    i18 = i87;
                    if (obj2 instanceof Field) {
                        fieldZzn3 = (Field) obj2;
                    } else {
                        fieldZzn3 = zzn(cls2, (String) obj2);
                        objArrZze[i27] = fieldZzn3;
                    }
                    iObjectFieldOffset2 = (int) unsafe.objectFieldOffset(fieldZzn3);
                    strZzd = strZzd;
                    i20 = i86;
                    i17 = i82;
                    i19 = 0;
                    c = 55296;
                }
                i6 = i24;
                i25 = i77;
                i26 = iCharAt12 + iCharAt12;
                obj = objArrZze[i26];
                int i88 = i25;
                if (obj instanceof Field) {
                    fieldZzn2 = (Field) obj;
                } else {
                    fieldZzn2 = zzn(cls2, (String) obj);
                    objArrZze[i26] = fieldZzn2;
                }
                int i89 = i7;
                iObjectFieldOffset = (int) unsafe.objectFieldOffset(fieldZzn2);
                i27 = i26 + 1;
                obj2 = objArrZze[i27];
                i18 = i89;
                if (obj2 instanceof Field) {
                    fieldZzn3 = (Field) obj2;
                } else {
                    fieldZzn3 = zzn(cls2, (String) obj2);
                    objArrZze[i27] = fieldZzn3;
                }
                iObjectFieldOffset2 = (int) unsafe.objectFieldOffset(fieldZzn3);
                strZzd = strZzd;
                i20 = i88;
                i17 = i82;
                i19 = 0;
                c = 55296;
            } else {
                i18 = i7;
                int i90 = i6 + 1;
                Field fieldZzn4 = zzn(cls2, (String) objArrZze[i6]);
                if (i76 == 9 || i76 == 17) {
                    int i91 = i67 / 3;
                    objArr[i91 + i91 + 1] = fieldZzn4.getType();
                } else {
                    if (i76 != 27) {
                        if (i76 == 49) {
                            i6 += 2;
                            i22 = 1;
                        } else if (i76 == 12 || i76 == 30 || i76 == 44) {
                            if (zzcrVar2.zzc() == 1 || i77 != 0) {
                                i6 += 2;
                                int i92 = i67 / 3;
                                objArr[i92 + i92 + 1] = objArrZze[i90];
                            } else {
                                i6 = i90;
                                i77 = 0;
                            }
                        } else if (i76 == 50) {
                            int i93 = i6 + 2;
                            int i94 = i64 + 1;
                            iArr[i64] = i67;
                            int i95 = i67 / 3;
                            int i96 = i95 + i95;
                            objArr[i96] = objArrZze[i90];
                            if (i77 != 0) {
                                objArr[i96 + 1] = objArrZze[i93];
                                i6 += 3;
                                i64 = i94;
                            } else {
                                i6 = i93;
                                i64 = i94;
                                i77 = 0;
                            }
                        }
                        iObjectFieldOffset = (int) unsafe.objectFieldOffset(fieldZzn4);
                        if ((iCharAt11 & 4096) != 0 || i76 > 17) {
                            c = 55296;
                            iObjectFieldOffset2 = 1048575;
                            i19 = 0;
                        } else {
                            int i97 = i17 + 1;
                            int iCharAt13 = strZzd.charAt(i17);
                            if (iCharAt13 >= 55296) {
                                int i98 = iCharAt13 & 8191;
                                int i99 = 13;
                                while (true) {
                                    i21 = i97 + 1;
                                    cCharAt9 = strZzd.charAt(i97);
                                    if (cCharAt9 < 55296) {
                                        break;
                                    }
                                    i98 |= (cCharAt9 & 8191) << i99;
                                    i99 += 13;
                                    i97 = i21;
                                }
                                iCharAt13 = i98 | (cCharAt9 << i99);
                            } else {
                                i21 = i97;
                            }
                            int i100 = i18 + i18 + (iCharAt13 / 32);
                            Object obj3 = objArrZze[i100];
                            if (obj3 instanceof Field) {
                                fieldZzn = (Field) obj3;
                            } else {
                                fieldZzn = zzn(cls2, (String) obj3);
                                objArrZze[i100] = fieldZzn;
                            }
                            int iObjectFieldOffset3 = (int) unsafe.objectFieldOffset(fieldZzn);
                            i19 = iCharAt13 % 32;
                            i17 = i21;
                            c = 55296;
                            iObjectFieldOffset2 = iObjectFieldOffset3;
                        }
                        if (i76 >= 18 && i76 <= 49) {
                            iArr[i65] = iObjectFieldOffset;
                            i65++;
                        }
                        i20 = i77;
                    } else {
                        i22 = 1;
                        i6 += 2;
                    }
                    int i101 = i67 / 3;
                    objArr[i101 + i101 + i22] = objArrZze[i90];
                    iObjectFieldOffset = (int) unsafe.objectFieldOffset(fieldZzn4);
                    if ((iCharAt11 & 4096) != 0) {
                        c = 55296;
                        iObjectFieldOffset2 = 1048575;
                        i19 = 0;
                    } else {
                        c = 55296;
                        iObjectFieldOffset2 = 1048575;
                        i19 = 0;
                    }
                    if (i76 >= 18) {
                        iArr[i65] = iObjectFieldOffset;
                        i65++;
                    }
                    i20 = i77;
                }
                i6 = i90;
                iObjectFieldOffset = (int) unsafe.objectFieldOffset(fieldZzn4);
                if ((iCharAt11 & 4096) != 0) {
                    c = 55296;
                    iObjectFieldOffset2 = 1048575;
                    i19 = 0;
                } else {
                    c = 55296;
                    iObjectFieldOffset2 = 1048575;
                    i19 = 0;
                }
                if (i76 >= 18) {
                    iArr[i65] = iObjectFieldOffset;
                    i65++;
                }
                i20 = i77;
            }
            int i102 = i67 + 1;
            iArr3[i67] = iCharAt10;
            int i103 = i67 + 2;
            iArr3[i102] = ((iCharAt11 & 512) != 0 ? VisionAttribute.VISION_TYPE_CLASS_ENTITY : 0) | ((iCharAt11 & 256) != 0 ? DataSpace.RANGE_LIMITED : 0) | (i20 != 0 ? Integer.MIN_VALUE : 0) | (i76 << 20) | iObjectFieldOffset;
            i67 += 3;
            iArr3[i103] = (i19 << 20) | iObjectFieldOffset2;
            i33 = i17;
            strZzd = strZzd;
            c2 = c;
            zzcrVar = zzcrVar2;
            length = length;
            i7 = i18;
        }
        return new zzci(iArr3, objArr, i2, i4, zzcrVar.zzb(), false, iArr, i5, i62, zzclVar, zzbsVar, zzdeVar, zzakVar, zzcaVar);
    }

    private static Field zzn(Class cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException e) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            String name = cls.getName();
            String string = Arrays.toString(declaredFields);
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 11 + String.valueOf(name).length() + 29 + String.valueOf(string).length());
            sb.append("Field ");
            sb.append(str);
            sb.append(" for ");
            sb.append(name);
            sb.append(" not found. Known fields are ");
            sb.append(string);
            throw new RuntimeException(sb.toString(), e);
        }
    }

    private final void zzo(Object obj, Object obj2, int i) {
        if (zzN(obj2, i)) {
            int iZzA = zzA(i) & 1048575;
            Unsafe unsafe = zzb;
            long j = iZzA;
            Object object = unsafe.getObject(obj2, j);
            if (object == null) {
                int i2 = this.zzc[i];
                String string = obj2.toString();
                StringBuilder sb = new StringBuilder(String.valueOf(i2).length() + 38 + string.length());
                sb.append("Source subfield ");
                sb.append(i2);
                sb.append(" is present but null: ");
                sb.append(string);
                throw new IllegalStateException(sb.toString());
            }
            zzct zzctVarZzq = zzq(i);
            if (!zzN(obj, i)) {
                if (zzE(object)) {
                    Object objZza = zzctVarZzq.zza();
                    zzctVarZzq.zzd(objZza, object);
                    unsafe.putObject(obj, j, objZza);
                } else {
                    unsafe.putObject(obj, j, object);
                }
                zzO(obj, i);
                return;
            }
            Object object2 = unsafe.getObject(obj, j);
            if (!zzE(object2)) {
                Object objZza2 = zzctVarZzq.zza();
                zzctVarZzq.zzd(objZza2, object2);
                unsafe.putObject(obj, j, objZza2);
                object2 = objZza2;
            }
            zzctVarZzq.zzd(object2, object);
        }
    }

    private final void zzp(Object obj, Object obj2, int i) {
        int[] iArr = this.zzc;
        int i2 = iArr[i];
        if (zzP(obj2, i2, i)) {
            int iZzA = zzA(i) & 1048575;
            Unsafe unsafe = zzb;
            long j = iZzA;
            Object object = unsafe.getObject(obj2, j);
            if (object == null) {
                int i3 = iArr[i];
                String string = obj2.toString();
                StringBuilder sb = new StringBuilder(String.valueOf(i3).length() + 38 + string.length());
                sb.append("Source subfield ");
                sb.append(i3);
                sb.append(" is present but null: ");
                sb.append(string);
                throw new IllegalStateException(sb.toString());
            }
            zzct zzctVarZzq = zzq(i);
            if (!zzP(obj, i2, i)) {
                if (zzE(object)) {
                    Object objZza = zzctVarZzq.zza();
                    zzctVarZzq.zzd(objZza, object);
                    unsafe.putObject(obj, j, objZza);
                } else {
                    unsafe.putObject(obj, j, object);
                }
                zzQ(obj, i2, i);
                return;
            }
            Object object2 = unsafe.getObject(obj, j);
            if (!zzE(object2)) {
                Object objZza2 = zzctVarZzq.zza();
                zzctVarZzq.zzd(objZza2, object2);
                unsafe.putObject(obj, j, objZza2);
                object2 = objZza2;
            }
            zzctVarZzq.zzd(object2, object);
        }
    }

    private final zzct zzq(int i) {
        Object[] objArr = this.zzd;
        int i2 = i / 3;
        int i3 = i2 + i2;
        zzct zzctVar = (zzct) objArr[i3];
        if (zzctVar != null) {
            return zzctVar;
        }
        zzct zzctVarZzb = zzcp.zza().zzb((Class) objArr[i3 + 1]);
        objArr[i3] = zzctVarZzb;
        return zzctVarZzb;
    }

    private final Object zzr(int i) {
        int i2 = i / 3;
        return this.zzd[i2 + i2];
    }

    private final zzbe zzs(int i) {
        int i2 = i / 3;
        return (zzbe) this.zzd[i2 + i2 + 1];
    }

    private final Object zzt(Object obj, int i) {
        zzct zzctVarZzq = zzq(i);
        int iZzA = zzA(i) & 1048575;
        if (!zzN(obj, i)) {
            return zzctVarZzq.zza();
        }
        Object object = zzb.getObject(obj, iZzA);
        if (zzE(object)) {
            return object;
        }
        Object objZza = zzctVarZzq.zza();
        if (object != null) {
            zzctVarZzq.zzd(objZza, object);
        }
        return objZza;
    }

    private final void zzu(Object obj, int i, Object obj2) {
        zzb.putObject(obj, zzA(i) & 1048575, obj2);
        zzO(obj, i);
    }

    private final Object zzv(Object obj, int i, int i2) {
        zzct zzctVarZzq = zzq(i2);
        if (!zzP(obj, i, i2)) {
            return zzctVarZzq.zza();
        }
        Object object = zzb.getObject(obj, zzA(i2) & 1048575);
        if (zzE(object)) {
            return object;
        }
        Object objZza = zzctVarZzq.zza();
        if (object != null) {
            zzctVarZzq.zzd(objZza, object);
        }
        return objZza;
    }

    private final void zzw(Object obj, int i, int i2, Object obj2) {
        zzb.putObject(obj, zzA(i2) & 1048575, obj2);
        zzQ(obj, i, i2);
    }

    private final Object zzx(Object obj, int i, Object obj2, zzde zzdeVar, Object obj3) {
        int i2 = this.zzc[i];
        Object objZzn = zzdm.zzn(obj, zzA(i) & 1048575);
        if (objZzn == null || zzs(i) == null) {
            return obj2;
        }
        throw null;
    }

    private static boolean zzy(Object obj, int i, zzct zzctVar) {
        return zzctVar.zzl(zzdm.zzn(obj, i & 1048575));
    }

    private final void zzz(Object obj, int i, zzcs zzcsVar) throws IOException {
        long j = i & 1048575;
        if (zzD(i)) {
            zzdm.zzo(obj, j, zzcsVar.zzn());
        } else if (this.zzi) {
            zzdm.zzo(obj, j, zzcsVar.zzm());
        } else {
            zzdm.zzo(obj, j, zzcsVar.zzs());
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzct
    public final Object zza() {
        return ((zzay) this.zzg).newMutableInstance();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzct
    public final boolean zzb(Object obj, Object obj2) {
        boolean zZzI;
        for (int i = 0; i < this.zzc.length; i += 3) {
            int iZzA = zzA(i);
            long j = iZzA & 1048575;
            switch (zzC(iZzA)) {
                case 0:
                    if (!zzL(obj, obj2, i) || Double.doubleToLongBits(zzdm.zzl(obj, j)) != Double.doubleToLongBits(zzdm.zzl(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 1:
                    if (!zzL(obj, obj2, i) || Float.floatToIntBits(zzdm.zzj(obj, j)) != Float.floatToIntBits(zzdm.zzj(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 2:
                    if (!zzL(obj, obj2, i) || zzdm.zzf(obj, j) != zzdm.zzf(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 3:
                    if (!zzL(obj, obj2, i) || zzdm.zzf(obj, j) != zzdm.zzf(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 4:
                    if (!zzL(obj, obj2, i) || zzdm.zzd(obj, j) != zzdm.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 5:
                    if (!zzL(obj, obj2, i) || zzdm.zzf(obj, j) != zzdm.zzf(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 6:
                    if (!zzL(obj, obj2, i) || zzdm.zzd(obj, j) != zzdm.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 7:
                    if (!zzL(obj, obj2, i) || zzdm.zzh(obj, j) != zzdm.zzh(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 8:
                    if (!zzL(obj, obj2, i) || !zzcv.zzI(zzdm.zzn(obj, j), zzdm.zzn(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 9:
                    if (!zzL(obj, obj2, i) || !zzcv.zzI(zzdm.zzn(obj, j), zzdm.zzn(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 10:
                    if (!zzL(obj, obj2, i) || !zzcv.zzI(zzdm.zzn(obj, j), zzdm.zzn(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 11:
                    if (!zzL(obj, obj2, i) || zzdm.zzd(obj, j) != zzdm.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 12:
                    if (!zzL(obj, obj2, i) || zzdm.zzd(obj, j) != zzdm.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 13:
                    if (!zzL(obj, obj2, i) || zzdm.zzd(obj, j) != zzdm.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 14:
                    if (!zzL(obj, obj2, i) || zzdm.zzf(obj, j) != zzdm.zzf(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 15:
                    if (!zzL(obj, obj2, i) || zzdm.zzd(obj, j) != zzdm.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 16:
                    if (!zzL(obj, obj2, i) || zzdm.zzf(obj, j) != zzdm.zzf(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 17:
                    if (!zzL(obj, obj2, i) || !zzcv.zzI(zzdm.zzn(obj, j), zzdm.zzn(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    zZzI = zzcv.zzI(zzdm.zzn(obj, j), zzdm.zzn(obj2, j));
                    break;
                case 50:
                    zZzI = zzcv.zzI(zzdm.zzn(obj, j), zzdm.zzn(obj2, j));
                    break;
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                case 60:
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                case 68:
                    long jZzB = zzB(i) & 1048575;
                    if (zzdm.zzd(obj, jZzB) != zzdm.zzd(obj2, jZzB) || !zzcv.zzI(zzdm.zzn(obj, j), zzdm.zzn(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                default:
                    continue;
                    break;
            }
            if (!zZzI) {
                return false;
            }
        }
        zzde zzdeVar = this.zzm;
        if (!zzdeVar.zzj(obj).equals(zzdeVar.zzj(obj2))) {
            return false;
        }
        if (!this.zzh) {
            return true;
        }
        zzak zzakVar = this.zzn;
        return zzakVar.zzb(obj).equals(zzakVar.zzb(obj2));
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzct
    public final int zzc(Object obj) {
        int i;
        long jDoubleToLongBits;
        int iFloatToIntBits;
        int i2;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            int[] iArr = this.zzc;
            if (i3 >= iArr.length) {
                int iHashCode = (i4 * 53) + this.zzm.zzj(obj).hashCode();
                return this.zzh ? (iHashCode * 53) + this.zzn.zzb(obj).zza.hashCode() : iHashCode;
            }
            int iZzA = zzA(i3);
            int i5 = 1048575 & iZzA;
            int iZzC = zzC(iZzA);
            int i6 = iArr[i3];
            long j = i5;
            int iHashCode2 = 37;
            switch (iZzC) {
                case 0:
                    i = i4 * 53;
                    jDoubleToLongBits = Double.doubleToLongBits(zzdm.zzl(obj, j));
                    byte[] bArr = zzbj.zzb;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i4 = i + iFloatToIntBits;
                    break;
                case 1:
                    i = i4 * 53;
                    iFloatToIntBits = Float.floatToIntBits(zzdm.zzj(obj, j));
                    i4 = i + iFloatToIntBits;
                    break;
                case 2:
                    i = i4 * 53;
                    jDoubleToLongBits = zzdm.zzf(obj, j);
                    byte[] bArr2 = zzbj.zzb;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i4 = i + iFloatToIntBits;
                    break;
                case 3:
                    i = i4 * 53;
                    jDoubleToLongBits = zzdm.zzf(obj, j);
                    byte[] bArr3 = zzbj.zzb;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i4 = i + iFloatToIntBits;
                    break;
                case 4:
                    i = i4 * 53;
                    iFloatToIntBits = zzdm.zzd(obj, j);
                    i4 = i + iFloatToIntBits;
                    break;
                case 5:
                    i = i4 * 53;
                    jDoubleToLongBits = zzdm.zzf(obj, j);
                    byte[] bArr4 = zzbj.zzb;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i4 = i + iFloatToIntBits;
                    break;
                case 6:
                    i = i4 * 53;
                    iFloatToIntBits = zzdm.zzd(obj, j);
                    i4 = i + iFloatToIntBits;
                    break;
                case 7:
                    i = i4 * 53;
                    iFloatToIntBits = zzbj.zzb(zzdm.zzh(obj, j));
                    i4 = i + iFloatToIntBits;
                    break;
                case 8:
                    i = i4 * 53;
                    iFloatToIntBits = ((String) zzdm.zzn(obj, j)).hashCode();
                    i4 = i + iFloatToIntBits;
                    break;
                case 9:
                    i2 = i4 * 53;
                    Object objZzn = zzdm.zzn(obj, j);
                    if (objZzn != null) {
                        iHashCode2 = objZzn.hashCode();
                    }
                    i4 = i2 + iHashCode2;
                    break;
                case 10:
                    i = i4 * 53;
                    iFloatToIntBits = zzdm.zzn(obj, j).hashCode();
                    i4 = i + iFloatToIntBits;
                    break;
                case 11:
                    i = i4 * 53;
                    iFloatToIntBits = zzdm.zzd(obj, j);
                    i4 = i + iFloatToIntBits;
                    break;
                case 12:
                    i = i4 * 53;
                    iFloatToIntBits = zzdm.zzd(obj, j);
                    i4 = i + iFloatToIntBits;
                    break;
                case 13:
                    i = i4 * 53;
                    iFloatToIntBits = zzdm.zzd(obj, j);
                    i4 = i + iFloatToIntBits;
                    break;
                case 14:
                    i = i4 * 53;
                    jDoubleToLongBits = zzdm.zzf(obj, j);
                    byte[] bArr5 = zzbj.zzb;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i4 = i + iFloatToIntBits;
                    break;
                case 15:
                    i = i4 * 53;
                    iFloatToIntBits = zzdm.zzd(obj, j);
                    i4 = i + iFloatToIntBits;
                    break;
                case 16:
                    i = i4 * 53;
                    jDoubleToLongBits = zzdm.zzf(obj, j);
                    byte[] bArr6 = zzbj.zzb;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i4 = i + iFloatToIntBits;
                    break;
                case 17:
                    i2 = i4 * 53;
                    Object objZzn2 = zzdm.zzn(obj, j);
                    if (objZzn2 != null) {
                        iHashCode2 = objZzn2.hashCode();
                    }
                    i4 = i2 + iHashCode2;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    i = i4 * 53;
                    iFloatToIntBits = zzdm.zzn(obj, j).hashCode();
                    i4 = i + iFloatToIntBits;
                    break;
                case 50:
                    i = i4 * 53;
                    iFloatToIntBits = zzdm.zzn(obj, j).hashCode();
                    i4 = i + iFloatToIntBits;
                    break;
                case 51:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        jDoubleToLongBits = Double.doubleToLongBits(zzG(obj, j));
                        byte[] bArr7 = zzbj.zzb;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 52:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = Float.floatToIntBits(zzH(obj, j));
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 53:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        jDoubleToLongBits = zzJ(obj, j);
                        byte[] bArr8 = zzbj.zzb;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 54:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        jDoubleToLongBits = zzJ(obj, j);
                        byte[] bArr9 = zzbj.zzb;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 55:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = zzI(obj, j);
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 56:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        jDoubleToLongBits = zzJ(obj, j);
                        byte[] bArr10 = zzbj.zzb;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 57:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = zzI(obj, j);
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 58:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = zzbj.zzb(zzK(obj, j));
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 59:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = ((String) zzdm.zzn(obj, j)).hashCode();
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 60:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = zzdm.zzn(obj, j).hashCode();
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 61:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = zzdm.zzn(obj, j).hashCode();
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 62:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = zzI(obj, j);
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 63:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = zzI(obj, j);
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 64:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = zzI(obj, j);
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 65:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        jDoubleToLongBits = zzJ(obj, j);
                        byte[] bArr11 = zzbj.zzb;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 66:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = zzI(obj, j);
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 67:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        jDoubleToLongBits = zzJ(obj, j);
                        byte[] bArr12 = zzbj.zzb;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 68:
                    if (zzP(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = zzdm.zzn(obj, j).hashCode();
                        i4 = i + iFloatToIntBits;
                    }
                    break;
            }
            i3 += 3;
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzct
    public final void zzd(Object obj, Object obj2) {
        zzF(obj);
        obj2.getClass();
        int i = 0;
        while (true) {
            int[] iArr = this.zzc;
            if (i >= iArr.length) {
                zzcv.zzK(this.zzm, obj, obj2);
                if (this.zzh) {
                    zzcv.zzJ(this.zzn, obj, obj2);
                    return;
                }
                return;
            }
            int iZzA = zzA(i);
            int i2 = 1048575 & iZzA;
            int iZzC = zzC(iZzA);
            int i3 = iArr[i];
            long j = i2;
            switch (iZzC) {
                case 0:
                    if (zzN(obj2, i)) {
                        zzdm.zzm(obj, j, zzdm.zzl(obj2, j));
                        zzO(obj, i);
                    }
                    break;
                case 1:
                    if (zzN(obj2, i)) {
                        zzdm.zzk(obj, j, zzdm.zzj(obj2, j));
                        zzO(obj, i);
                    }
                    break;
                case 2:
                    if (zzN(obj2, i)) {
                        zzdm.zzg(obj, j, zzdm.zzf(obj2, j));
                        zzO(obj, i);
                    }
                    break;
                case 3:
                    if (zzN(obj2, i)) {
                        zzdm.zzg(obj, j, zzdm.zzf(obj2, j));
                        zzO(obj, i);
                    }
                    break;
                case 4:
                    if (zzN(obj2, i)) {
                        zzdm.zze(obj, j, zzdm.zzd(obj2, j));
                        zzO(obj, i);
                    }
                    break;
                case 5:
                    if (zzN(obj2, i)) {
                        zzdm.zzg(obj, j, zzdm.zzf(obj2, j));
                        zzO(obj, i);
                    }
                    break;
                case 6:
                    if (zzN(obj2, i)) {
                        zzdm.zze(obj, j, zzdm.zzd(obj2, j));
                        zzO(obj, i);
                    }
                    break;
                case 7:
                    if (zzN(obj2, i)) {
                        zzdm.zzi(obj, j, zzdm.zzh(obj2, j));
                        zzO(obj, i);
                    }
                    break;
                case 8:
                    if (zzN(obj2, i)) {
                        zzdm.zzo(obj, j, zzdm.zzn(obj2, j));
                        zzO(obj, i);
                    }
                    break;
                case 9:
                    zzo(obj, obj2, i);
                    break;
                case 10:
                    if (zzN(obj2, i)) {
                        zzdm.zzo(obj, j, zzdm.zzn(obj2, j));
                        zzO(obj, i);
                    }
                    break;
                case 11:
                    if (zzN(obj2, i)) {
                        zzdm.zze(obj, j, zzdm.zzd(obj2, j));
                        zzO(obj, i);
                    }
                    break;
                case 12:
                    if (zzN(obj2, i)) {
                        zzdm.zze(obj, j, zzdm.zzd(obj2, j));
                        zzO(obj, i);
                    }
                    break;
                case 13:
                    if (zzN(obj2, i)) {
                        zzdm.zze(obj, j, zzdm.zzd(obj2, j));
                        zzO(obj, i);
                    }
                    break;
                case 14:
                    if (zzN(obj2, i)) {
                        zzdm.zzg(obj, j, zzdm.zzf(obj2, j));
                        zzO(obj, i);
                    }
                    break;
                case 15:
                    if (zzN(obj2, i)) {
                        zzdm.zze(obj, j, zzdm.zzd(obj2, j));
                        zzO(obj, i);
                    }
                    break;
                case 16:
                    if (zzN(obj2, i)) {
                        zzdm.zzg(obj, j, zzdm.zzf(obj2, j));
                        zzO(obj, i);
                    }
                    break;
                case 17:
                    zzo(obj, obj2, i);
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    zzbi zzbiVarZzh = (zzbi) zzdm.zzn(obj, j);
                    zzbi zzbiVar = (zzbi) zzdm.zzn(obj2, j);
                    int size = zzbiVarZzh.size();
                    int size2 = zzbiVar.size();
                    if (size > 0 && size2 > 0) {
                        if (!zzbiVarZzh.zza()) {
                            zzbiVarZzh = zzbiVarZzh.zzh(size2 + size);
                        }
                        zzbiVarZzh.addAll(zzbiVar);
                    }
                    if (size > 0) {
                        zzbiVar = zzbiVarZzh;
                    }
                    zzdm.zzo(obj, j, zzbiVar);
                    break;
                case 50:
                    int i4 = zzcv.zza;
                    zzdm.zzo(obj, j, zzca.zzb(zzdm.zzn(obj, j), zzdm.zzn(obj2, j)));
                    break;
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                    if (zzP(obj2, i3, i)) {
                        zzdm.zzo(obj, j, zzdm.zzn(obj2, j));
                        zzQ(obj, i3, i);
                    }
                    break;
                case 60:
                    zzp(obj, obj2, i);
                    break;
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                    if (zzP(obj2, i3, i)) {
                        zzdm.zzo(obj, j, zzdm.zzn(obj2, j));
                        zzQ(obj, i3, i);
                    }
                    break;
                case 68:
                    zzp(obj, obj2, i);
                    break;
            }
            i += 3;
        }
    }

    /* JADX WARN: Code duplicated, block: B:140:0x0384  */
    /* JADX WARN: Code duplicated, block: B:210:0x0548  */
    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzct
    public final int zze(Object obj) {
        int i;
        int iZzx;
        int iZzx2;
        int iZzx3;
        int iZzy;
        int iZzx4;
        int iZzx5;
        int iZzx6;
        int iZzc;
        int iZzx7;
        int iZzE;
        int i2;
        int iZzt;
        int size;
        int iZzu;
        int iZzx8;
        int iZzx9;
        int serializedSize;
        int iZzx10;
        int iZzx11;
        int iZzy2;
        int iZzC;
        int iZzx12;
        int iZzx13;
        int iZzF;
        int iZzx14;
        int iZzx15;
        int iZzc2;
        int iZzx16;
        zzci zzciVar = this;
        Object obj2 = obj;
        Unsafe unsafe = zzb;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 1048575;
        while (true) {
            int[] iArr = zzciVar.zzc;
            if (i3 >= iArr.length) {
                zzde zzdeVar = zzciVar.zzm;
                int iZzs = i5 + zzdeVar.zzs(zzdeVar.zzj(obj2));
                if (!zzciVar.zzh) {
                    return iZzs;
                }
                zzda zzdaVar = zzciVar.zzn.zzb(obj2).zza;
                int iZzc3 = zzdaVar.zzc();
                int iZzl = 0;
                for (int i7 = 0; i7 < iZzc3; i7++) {
                    Map.Entry entryZzd = zzdaVar.zzd(i7);
                    iZzl += zzao.zzl((zzan) ((zzcx) entryZzd).zza(), entryZzd.getValue());
                }
                for (Map.Entry entry : zzdaVar.zze()) {
                    iZzl += zzao.zzl((zzan) entry.getKey(), entry.getValue());
                }
                return iZzs + iZzl;
            }
            int iZzA = zzciVar.zzA(i3);
            int iZzC2 = zzC(iZzA);
            int i8 = iArr[i3];
            int i9 = iArr[i3 + 2];
            int i10 = i9 & 1048575;
            if (iZzC2 <= 17) {
                if (i10 != i6) {
                    i4 = i10 == 1048575 ? 0 : unsafe.getInt(obj2, i10);
                    i6 = i10;
                }
                i = 1 << (i9 >>> 20);
            } else {
                i = 0;
            }
            int i11 = iZzA & 1048575;
            if (iZzC2 >= zzap.DOUBLE_LIST_PACKED.zza()) {
                zzap.SINT64_LIST_PACKED.zza();
            }
            long j = i11;
            switch (iZzC2) {
                case 0:
                    if (zzciVar.zzM(obj2, i3, i6, i4, i)) {
                        iZzx = zzad.zzx(i8 << 3);
                        iZzE = iZzx + 8;
                        i5 += iZzE;
                    }
                    i3 += 3;
                    break;
                case 1:
                    if (zzciVar.zzM(obj2, i3, i6, i4, i)) {
                        iZzx2 = zzad.zzx(i8 << 3);
                        iZzx5 = iZzx2 + 4;
                        i5 += iZzx5;
                    }
                    zzciVar = this;
                    obj2 = obj;
                    i3 += 3;
                    break;
                case 2:
                    if (zzciVar.zzM(obj2, i3, i6, i4, i)) {
                        long j2 = unsafe.getLong(obj2, j);
                        iZzx3 = zzad.zzx(i8 << 3);
                        iZzy = zzad.zzy(j2);
                        i2 = iZzx3 + iZzy;
                        i5 += i2;
                    }
                    zzciVar = this;
                    i3 += 3;
                    break;
                case 3:
                    if (zzciVar.zzM(obj2, i3, i6, i4, i)) {
                        long j3 = unsafe.getLong(obj2, j);
                        iZzx3 = zzad.zzx(i8 << 3);
                        iZzy = zzad.zzy(j3);
                        i2 = iZzx3 + iZzy;
                        i5 += i2;
                    }
                    zzciVar = this;
                    i3 += 3;
                    break;
                case 4:
                    if (zzciVar.zzM(obj2, i3, i6, i4, i)) {
                        long j4 = unsafe.getInt(obj2, j);
                        iZzx3 = zzad.zzx(i8 << 3);
                        iZzy = zzad.zzy(j4);
                        i2 = iZzx3 + iZzy;
                        i5 += i2;
                    }
                    zzciVar = this;
                    i3 += 3;
                    break;
                case 5:
                    if (zzciVar.zzM(obj2, i3, i6, i4, i)) {
                        iZzx4 = zzad.zzx(i8 << 3);
                        iZzx5 = iZzx4 + 8;
                        i5 += iZzx5;
                    }
                    zzciVar = this;
                    obj2 = obj;
                    i3 += 3;
                    break;
                case 6:
                    if (zzciVar.zzM(obj2, i3, i6, i4, i)) {
                        iZzx2 = zzad.zzx(i8 << 3);
                        iZzx5 = iZzx2 + 4;
                        i5 += iZzx5;
                    }
                    zzciVar = this;
                    obj2 = obj;
                    i3 += 3;
                    break;
                case 7:
                    if (zzciVar.zzM(obj2, i3, i6, i4, i)) {
                        iZzx5 = zzad.zzx(i8 << 3) + 1;
                        i5 += iZzx5;
                    }
                    zzciVar = this;
                    obj2 = obj;
                    i3 += 3;
                    break;
                case 8:
                    if (zzciVar.zzM(obj2, i3, i6, i4, i)) {
                        int i12 = i8 << 3;
                        Object object = unsafe.getObject(obj2, j);
                        if (object instanceof zzt) {
                            iZzx6 = zzad.zzx(i12);
                            iZzc = ((zzt) object).zzc();
                            iZzx7 = zzad.zzx(iZzc);
                            i2 = iZzx6 + iZzx7 + iZzc;
                            i5 += i2;
                        } else {
                            iZzx3 = zzad.zzx(i12);
                            iZzy = zzad.zzz((String) object);
                            i2 = iZzx3 + iZzy;
                            i5 += i2;
                        }
                    }
                    zzciVar = this;
                    i3 += 3;
                    break;
                case 9:
                    if (zzciVar.zzM(obj2, i3, i6, i4, i)) {
                        iZzE = zzcv.zzE(i8, unsafe.getObject(obj2, j), zzciVar.zzq(i3));
                        i5 += iZzE;
                    }
                    i3 += 3;
                    break;
                case 10:
                    if (zzciVar.zzM(obj2, i3, i6, i4, i)) {
                        zzt zztVar = (zzt) unsafe.getObject(obj2, j);
                        iZzx6 = zzad.zzx(i8 << 3);
                        iZzc = zztVar.zzc();
                        iZzx7 = zzad.zzx(iZzc);
                        i2 = iZzx6 + iZzx7 + iZzc;
                        i5 += i2;
                    }
                    zzciVar = this;
                    i3 += 3;
                    break;
                case 11:
                    if (zzciVar.zzM(obj2, i3, i6, i4, i)) {
                        int i13 = unsafe.getInt(obj2, j);
                        iZzx3 = zzad.zzx(i8 << 3);
                        iZzy = zzad.zzx(i13);
                        i2 = iZzx3 + iZzy;
                        i5 += i2;
                    }
                    zzciVar = this;
                    i3 += 3;
                    break;
                case 12:
                    if (zzciVar.zzM(obj2, i3, i6, i4, i)) {
                        long j5 = unsafe.getInt(obj2, j);
                        iZzx3 = zzad.zzx(i8 << 3);
                        iZzy = zzad.zzy(j5);
                        i2 = iZzx3 + iZzy;
                        i5 += i2;
                    }
                    zzciVar = this;
                    i3 += 3;
                    break;
                case 13:
                    if (zzciVar.zzM(obj2, i3, i6, i4, i)) {
                        iZzx2 = zzad.zzx(i8 << 3);
                        iZzx5 = iZzx2 + 4;
                        i5 += iZzx5;
                    }
                    zzciVar = this;
                    obj2 = obj;
                    i3 += 3;
                    break;
                case 14:
                    if (zzciVar.zzM(obj2, i3, i6, i4, i)) {
                        iZzx4 = zzad.zzx(i8 << 3);
                        iZzx5 = iZzx4 + 8;
                        i5 += iZzx5;
                    }
                    zzciVar = this;
                    obj2 = obj;
                    i3 += 3;
                    break;
                case 15:
                    if (zzciVar.zzM(obj2, i3, i6, i4, i)) {
                        int i14 = unsafe.getInt(obj2, j);
                        iZzx3 = zzad.zzx(i8 << 3);
                        iZzy = zzad.zzx((i14 >> 31) ^ (i14 + i14));
                        i2 = iZzx3 + iZzy;
                        i5 += i2;
                    }
                    zzciVar = this;
                    i3 += 3;
                    break;
                case 16:
                    if (zzciVar.zzM(obj2, i3, i6, i4, i)) {
                        long j6 = unsafe.getLong(obj2, j);
                        iZzx3 = zzad.zzx(i8 << 3);
                        iZzy = zzad.zzy((j6 >> 63) ^ (j6 + j6));
                        i2 = iZzx3 + iZzy;
                        i5 += i2;
                    }
                    zzciVar = this;
                    i3 += 3;
                    break;
                case 17:
                    if (zzciVar.zzM(obj2, i3, i6, i4, i)) {
                        iZzE = zzcv.zzF(i8, (zzcf) unsafe.getObject(obj2, j), zzciVar.zzq(i3));
                        i5 += iZzE;
                    }
                    i3 += 3;
                    break;
                case 18:
                    iZzE = zzcv.zzD(i8, (List) unsafe.getObject(obj2, j), false);
                    i5 += iZzE;
                    i3 += 3;
                    break;
                case 19:
                    iZzE = zzcv.zzB(i8, (List) unsafe.getObject(obj2, j), false);
                    i5 += iZzE;
                    i3 += 3;
                    break;
                case 20:
                    List list = (List) unsafe.getObject(obj2, j);
                    int i15 = zzcv.zza;
                    if (list.size() == 0) {
                        iZzt = 0;
                    } else {
                        iZzt = zzcv.zzt(list) + (list.size() * zzad.zzx(i8 << 3));
                    }
                    i5 += iZzt;
                    i3 += 3;
                    break;
                case 21:
                    List list2 = (List) unsafe.getObject(obj2, j);
                    int i16 = zzcv.zza;
                    size = list2.size();
                    if (size == 0) {
                        iZzE = 0;
                    } else {
                        iZzu = zzcv.zzu(list2);
                        iZzx8 = zzad.zzx(i8 << 3);
                        iZzy2 = size * iZzx8;
                        iZzE = iZzu + iZzy2;
                    }
                    i5 += iZzE;
                    i3 += 3;
                    break;
                case 22:
                    List list3 = (List) unsafe.getObject(obj2, j);
                    int i17 = zzcv.zza;
                    size = list3.size();
                    if (size == 0) {
                        iZzE = 0;
                    } else {
                        iZzu = zzcv.zzx(list3);
                        iZzx8 = zzad.zzx(i8 << 3);
                        iZzy2 = size * iZzx8;
                        iZzE = iZzu + iZzy2;
                    }
                    i5 += iZzE;
                    i3 += 3;
                    break;
                case 23:
                    iZzE = zzcv.zzD(i8, (List) unsafe.getObject(obj2, j), false);
                    i5 += iZzE;
                    i3 += 3;
                    break;
                case 24:
                    iZzE = zzcv.zzB(i8, (List) unsafe.getObject(obj2, j), false);
                    i5 += iZzE;
                    i3 += 3;
                    break;
                case 25:
                    List list4 = (List) unsafe.getObject(obj2, j);
                    int i18 = zzcv.zza;
                    int size2 = list4.size();
                    if (size2 == 0) {
                        iZzE = 0;
                    } else {
                        iZzE = size2 * (zzad.zzx(i8 << 3) + 1);
                    }
                    i5 += iZzE;
                    i3 += 3;
                    break;
                case 26:
                    List list5 = (List) unsafe.getObject(obj2, j);
                    int i19 = zzcv.zza;
                    int size3 = list5.size();
                    if (size3 == 0) {
                        iZzt = 0;
                    } else {
                        iZzt = zzad.zzx(i8 << 3) * size3;
                        if (list5 instanceof zzbr) {
                            zzbr zzbrVar = (zzbr) list5;
                            for (int i20 = 0; i20 < size3; i20++) {
                                Object objZzc = zzbrVar.zzc();
                                if (objZzc instanceof zzt) {
                                    int iZzc4 = ((zzt) objZzc).zzc();
                                    iZzt += zzad.zzx(iZzc4) + iZzc4;
                                } else {
                                    iZzt += zzad.zzz((String) objZzc);
                                }
                            }
                        } else {
                            for (int i21 = 0; i21 < size3; i21++) {
                                Object obj3 = list5.get(i21);
                                if (obj3 instanceof zzt) {
                                    int iZzc5 = ((zzt) obj3).zzc();
                                    iZzt += zzad.zzx(iZzc5) + iZzc5;
                                } else {
                                    iZzt += zzad.zzz((String) obj3);
                                }
                            }
                        }
                    }
                    i5 += iZzt;
                    i3 += 3;
                    break;
                case 27:
                    List list6 = (List) unsafe.getObject(obj2, j);
                    zzct zzctVarZzq = zzciVar.zzq(i3);
                    int i22 = zzcv.zza;
                    int size4 = list6.size();
                    if (size4 == 0) {
                        iZzx9 = 0;
                    } else {
                        iZzx9 = zzad.zzx(i8 << 3) * size4;
                        for (int i23 = 0; i23 < size4; i23++) {
                            Object obj4 = list6.get(i23);
                            if (obj4 instanceof zzbq) {
                                serializedSize = ((zzbq) obj4).zzb();
                                iZzx10 = zzad.zzx(serializedSize);
                            } else {
                                serializedSize = ((zzc) obj4).getSerializedSize(zzctVarZzq);
                                iZzx10 = zzad.zzx(serializedSize);
                            }
                            iZzx9 += iZzx10 + serializedSize;
                        }
                    }
                    i5 += iZzx9;
                    i3 += 3;
                    break;
                case 28:
                    List list7 = (List) unsafe.getObject(obj2, j);
                    int i24 = zzcv.zza;
                    int size5 = list7.size();
                    if (size5 == 0) {
                        iZzx11 = 0;
                    } else {
                        iZzx11 = size5 * zzad.zzx(i8 << 3);
                        for (int i25 = 0; i25 < list7.size(); i25++) {
                            int iZzc6 = ((zzt) list7.get(i25)).zzc();
                            iZzx11 += zzad.zzx(iZzc6) + iZzc6;
                        }
                    }
                    i5 += iZzx11;
                    i3 += 3;
                    break;
                case 29:
                    List list8 = (List) unsafe.getObject(obj2, j);
                    int i26 = zzcv.zza;
                    size = list8.size();
                    if (size == 0) {
                        iZzE = 0;
                    } else {
                        iZzu = zzcv.zzy(list8);
                        iZzx8 = zzad.zzx(i8 << 3);
                        iZzy2 = size * iZzx8;
                        iZzE = iZzu + iZzy2;
                    }
                    i5 += iZzE;
                    i3 += 3;
                    break;
                case 30:
                    List list9 = (List) unsafe.getObject(obj2, j);
                    int i27 = zzcv.zza;
                    size = list9.size();
                    if (size == 0) {
                        iZzE = 0;
                    } else {
                        iZzu = zzcv.zzw(list9);
                        iZzx8 = zzad.zzx(i8 << 3);
                        iZzy2 = size * iZzx8;
                        iZzE = iZzu + iZzy2;
                    }
                    i5 += iZzE;
                    i3 += 3;
                    break;
                case 31:
                    iZzE = zzcv.zzB(i8, (List) unsafe.getObject(obj2, j), false);
                    i5 += iZzE;
                    i3 += 3;
                    break;
                case 32:
                    iZzE = zzcv.zzD(i8, (List) unsafe.getObject(obj2, j), false);
                    i5 += iZzE;
                    i3 += 3;
                    break;
                case 33:
                    List list10 = (List) unsafe.getObject(obj2, j);
                    int i28 = zzcv.zza;
                    size = list10.size();
                    if (size == 0) {
                        iZzE = 0;
                    } else {
                        iZzu = zzcv.zzz(list10);
                        iZzx8 = zzad.zzx(i8 << 3);
                        iZzy2 = size * iZzx8;
                        iZzE = iZzu + iZzy2;
                    }
                    i5 += iZzE;
                    i3 += 3;
                    break;
                case 34:
                    List list11 = (List) unsafe.getObject(obj2, j);
                    int i29 = zzcv.zza;
                    size = list11.size();
                    if (size == 0) {
                        iZzE = 0;
                    } else {
                        iZzu = zzcv.zzv(list11);
                        iZzx8 = zzad.zzx(i8 << 3);
                        iZzy2 = size * iZzx8;
                        iZzE = iZzu + iZzy2;
                    }
                    i5 += iZzE;
                    i3 += 3;
                    break;
                case 35:
                    iZzC = zzcv.zzC((List) unsafe.getObject(obj2, j));
                    if (iZzC > 0) {
                        iZzx12 = zzad.zzx(i8 << 3);
                        iZzx13 = zzad.zzx(iZzC);
                        iZzx11 = iZzx12 + iZzx13 + iZzC;
                        i5 += iZzx11;
                    }
                    i3 += 3;
                    break;
                case 36:
                    iZzC = zzcv.zzA((List) unsafe.getObject(obj2, j));
                    if (iZzC > 0) {
                        iZzx12 = zzad.zzx(i8 << 3);
                        iZzx13 = zzad.zzx(iZzC);
                        iZzx11 = iZzx12 + iZzx13 + iZzC;
                        i5 += iZzx11;
                    }
                    i3 += 3;
                    break;
                case 37:
                    iZzC = zzcv.zzt((List) unsafe.getObject(obj2, j));
                    if (iZzC > 0) {
                        iZzx12 = zzad.zzx(i8 << 3);
                        iZzx13 = zzad.zzx(iZzC);
                        iZzx11 = iZzx12 + iZzx13 + iZzC;
                        i5 += iZzx11;
                    }
                    i3 += 3;
                    break;
                case 38:
                    iZzC = zzcv.zzu((List) unsafe.getObject(obj2, j));
                    if (iZzC > 0) {
                        iZzx12 = zzad.zzx(i8 << 3);
                        iZzx13 = zzad.zzx(iZzC);
                        iZzx11 = iZzx12 + iZzx13 + iZzC;
                        i5 += iZzx11;
                    }
                    i3 += 3;
                    break;
                case 39:
                    iZzC = zzcv.zzx((List) unsafe.getObject(obj2, j));
                    if (iZzC > 0) {
                        iZzx12 = zzad.zzx(i8 << 3);
                        iZzx13 = zzad.zzx(iZzC);
                        iZzx11 = iZzx12 + iZzx13 + iZzC;
                        i5 += iZzx11;
                    }
                    i3 += 3;
                    break;
                case 40:
                    iZzC = zzcv.zzC((List) unsafe.getObject(obj2, j));
                    if (iZzC > 0) {
                        iZzx12 = zzad.zzx(i8 << 3);
                        iZzx13 = zzad.zzx(iZzC);
                        iZzx11 = iZzx12 + iZzx13 + iZzC;
                        i5 += iZzx11;
                    }
                    i3 += 3;
                    break;
                case 41:
                    iZzC = zzcv.zzA((List) unsafe.getObject(obj2, j));
                    if (iZzC > 0) {
                        iZzx12 = zzad.zzx(i8 << 3);
                        iZzx13 = zzad.zzx(iZzC);
                        iZzx11 = iZzx12 + iZzx13 + iZzC;
                        i5 += iZzx11;
                    }
                    i3 += 3;
                    break;
                case 42:
                    List list12 = (List) unsafe.getObject(obj2, j);
                    int i30 = zzcv.zza;
                    iZzC = list12.size();
                    if (iZzC > 0) {
                        iZzx12 = zzad.zzx(i8 << 3);
                        iZzx13 = zzad.zzx(iZzC);
                        iZzx11 = iZzx12 + iZzx13 + iZzC;
                        i5 += iZzx11;
                    }
                    i3 += 3;
                    break;
                case 43:
                    iZzC = zzcv.zzy((List) unsafe.getObject(obj2, j));
                    if (iZzC > 0) {
                        iZzx12 = zzad.zzx(i8 << 3);
                        iZzx13 = zzad.zzx(iZzC);
                        iZzx11 = iZzx12 + iZzx13 + iZzC;
                        i5 += iZzx11;
                    }
                    i3 += 3;
                    break;
                case 44:
                    iZzC = zzcv.zzw((List) unsafe.getObject(obj2, j));
                    if (iZzC > 0) {
                        iZzx12 = zzad.zzx(i8 << 3);
                        iZzx13 = zzad.zzx(iZzC);
                        iZzx11 = iZzx12 + iZzx13 + iZzC;
                        i5 += iZzx11;
                    }
                    i3 += 3;
                    break;
                case 45:
                    iZzC = zzcv.zzA((List) unsafe.getObject(obj2, j));
                    if (iZzC > 0) {
                        iZzx12 = zzad.zzx(i8 << 3);
                        iZzx13 = zzad.zzx(iZzC);
                        iZzx11 = iZzx12 + iZzx13 + iZzC;
                        i5 += iZzx11;
                    }
                    i3 += 3;
                    break;
                case 46:
                    iZzC = zzcv.zzC((List) unsafe.getObject(obj2, j));
                    if (iZzC > 0) {
                        iZzx12 = zzad.zzx(i8 << 3);
                        iZzx13 = zzad.zzx(iZzC);
                        iZzx11 = iZzx12 + iZzx13 + iZzC;
                        i5 += iZzx11;
                    }
                    i3 += 3;
                    break;
                case 47:
                    iZzC = zzcv.zzz((List) unsafe.getObject(obj2, j));
                    if (iZzC > 0) {
                        iZzx12 = zzad.zzx(i8 << 3);
                        iZzx13 = zzad.zzx(iZzC);
                        iZzx11 = iZzx12 + iZzx13 + iZzC;
                        i5 += iZzx11;
                    }
                    i3 += 3;
                    break;
                case 48:
                    iZzC = zzcv.zzv((List) unsafe.getObject(obj2, j));
                    if (iZzC > 0) {
                        iZzx12 = zzad.zzx(i8 << 3);
                        iZzx13 = zzad.zzx(iZzC);
                        iZzx11 = iZzx12 + iZzx13 + iZzC;
                        i5 += iZzx11;
                    }
                    i3 += 3;
                    break;
                case 49:
                    List list13 = (List) unsafe.getObject(obj2, j);
                    zzct zzctVarZzq2 = zzciVar.zzq(i3);
                    int i31 = zzcv.zza;
                    int size6 = list13.size();
                    if (size6 == 0) {
                        iZzF = 0;
                    } else {
                        iZzF = 0;
                        for (int i32 = 0; i32 < size6; i32++) {
                            iZzF += zzcv.zzF(i8, (zzcf) list13.get(i32), zzctVarZzq2);
                        }
                    }
                    i5 += iZzF;
                    i3 += 3;
                    break;
                case 50:
                    zzbz zzbzVar = (zzbz) unsafe.getObject(obj2, j);
                    if (zzbzVar.isEmpty()) {
                        continue;
                    } else {
                        Iterator it = zzbzVar.entrySet().iterator();
                        if (it.hasNext()) {
                            Map.Entry entry2 = (Map.Entry) it.next();
                            entry2.getKey();
                            entry2.getValue();
                            throw null;
                        }
                    }
                    i3 += 3;
                case 51:
                    if (zzciVar.zzP(obj2, i8, i3)) {
                        iZzx = zzad.zzx(i8 << 3);
                        iZzE = iZzx + 8;
                        i5 += iZzE;
                    }
                    i3 += 3;
                    break;
                case 52:
                    if (zzciVar.zzP(obj2, i8, i3)) {
                        iZzx14 = zzad.zzx(i8 << 3);
                        iZzE = iZzx14 + 4;
                        i5 += iZzE;
                    }
                    i3 += 3;
                    break;
                case 53:
                    if (zzciVar.zzP(obj2, i8, i3)) {
                        long jZzJ = zzJ(obj2, j);
                        iZzu = zzad.zzx(i8 << 3);
                        iZzy2 = zzad.zzy(jZzJ);
                        iZzE = iZzu + iZzy2;
                        i5 += iZzE;
                    }
                    i3 += 3;
                    break;
                case 54:
                    if (zzciVar.zzP(obj2, i8, i3)) {
                        long jZzJ2 = zzJ(obj2, j);
                        iZzu = zzad.zzx(i8 << 3);
                        iZzy2 = zzad.zzy(jZzJ2);
                        iZzE = iZzu + iZzy2;
                        i5 += iZzE;
                    }
                    i3 += 3;
                    break;
                case 55:
                    if (zzciVar.zzP(obj2, i8, i3)) {
                        long jZzI = zzI(obj2, j);
                        iZzu = zzad.zzx(i8 << 3);
                        iZzy2 = zzad.zzy(jZzI);
                        iZzE = iZzu + iZzy2;
                        i5 += iZzE;
                    }
                    i3 += 3;
                    break;
                case 56:
                    if (zzciVar.zzP(obj2, i8, i3)) {
                        iZzx = zzad.zzx(i8 << 3);
                        iZzE = iZzx + 8;
                        i5 += iZzE;
                    }
                    i3 += 3;
                    break;
                case 57:
                    if (zzciVar.zzP(obj2, i8, i3)) {
                        iZzx14 = zzad.zzx(i8 << 3);
                        iZzE = iZzx14 + 4;
                        i5 += iZzE;
                    }
                    i3 += 3;
                    break;
                case 58:
                    if (zzciVar.zzP(obj2, i8, i3)) {
                        iZzE = zzad.zzx(i8 << 3) + 1;
                        i5 += iZzE;
                    }
                    i3 += 3;
                    break;
                case 59:
                    if (zzciVar.zzP(obj2, i8, i3)) {
                        int i33 = i8 << 3;
                        Object object2 = unsafe.getObject(obj2, j);
                        if (object2 instanceof zzt) {
                            iZzx15 = zzad.zzx(i33);
                            iZzc2 = ((zzt) object2).zzc();
                            iZzx16 = zzad.zzx(iZzc2);
                            iZzE = iZzx15 + iZzx16 + iZzc2;
                            i5 += iZzE;
                        } else {
                            iZzu = zzad.zzx(i33);
                            iZzy2 = zzad.zzz((String) object2);
                            iZzE = iZzu + iZzy2;
                            i5 += iZzE;
                        }
                    }
                    i3 += 3;
                    break;
                case 60:
                    if (zzciVar.zzP(obj2, i8, i3)) {
                        iZzE = zzcv.zzE(i8, unsafe.getObject(obj2, j), zzciVar.zzq(i3));
                        i5 += iZzE;
                    }
                    i3 += 3;
                    break;
                case 61:
                    if (zzciVar.zzP(obj2, i8, i3)) {
                        zzt zztVar2 = (zzt) unsafe.getObject(obj2, j);
                        iZzx15 = zzad.zzx(i8 << 3);
                        iZzc2 = zztVar2.zzc();
                        iZzx16 = zzad.zzx(iZzc2);
                        iZzE = iZzx15 + iZzx16 + iZzc2;
                        i5 += iZzE;
                    }
                    i3 += 3;
                    break;
                case 62:
                    if (zzciVar.zzP(obj2, i8, i3)) {
                        int iZzI = zzI(obj2, j);
                        iZzu = zzad.zzx(i8 << 3);
                        iZzy2 = zzad.zzx(iZzI);
                        iZzE = iZzu + iZzy2;
                        i5 += iZzE;
                    }
                    i3 += 3;
                    break;
                case 63:
                    if (zzciVar.zzP(obj2, i8, i3)) {
                        long jZzI2 = zzI(obj2, j);
                        iZzu = zzad.zzx(i8 << 3);
                        iZzy2 = zzad.zzy(jZzI2);
                        iZzE = iZzu + iZzy2;
                        i5 += iZzE;
                    }
                    i3 += 3;
                    break;
                case 64:
                    if (zzciVar.zzP(obj2, i8, i3)) {
                        iZzx14 = zzad.zzx(i8 << 3);
                        iZzE = iZzx14 + 4;
                        i5 += iZzE;
                    }
                    i3 += 3;
                    break;
                case 65:
                    if (zzciVar.zzP(obj2, i8, i3)) {
                        iZzx = zzad.zzx(i8 << 3);
                        iZzE = iZzx + 8;
                        i5 += iZzE;
                    }
                    i3 += 3;
                    break;
                case 66:
                    if (zzciVar.zzP(obj2, i8, i3)) {
                        int iZzI2 = zzI(obj2, j);
                        iZzu = zzad.zzx(i8 << 3);
                        iZzy2 = zzad.zzx((iZzI2 >> 31) ^ (iZzI2 + iZzI2));
                        iZzE = iZzu + iZzy2;
                        i5 += iZzE;
                    }
                    i3 += 3;
                    break;
                case 67:
                    if (zzciVar.zzP(obj2, i8, i3)) {
                        long jZzJ3 = zzJ(obj2, j);
                        iZzu = zzad.zzx(i8 << 3);
                        iZzy2 = zzad.zzy((jZzJ3 >> 63) ^ (jZzJ3 + jZzJ3));
                        iZzE = iZzu + iZzy2;
                        i5 += iZzE;
                    }
                    i3 += 3;
                    break;
                case 68:
                    if (zzciVar.zzP(obj2, i8, i3)) {
                        iZzE = zzcv.zzF(i8, (zzcf) unsafe.getObject(obj2, j), zzciVar.zzq(i3));
                        i5 += iZzE;
                    }
                    i3 += 3;
                    break;
                default:
                    i3 += 3;
                    break;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:7:0x0024  */
    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzct
    public final void zzf(Object obj, zzdv zzdvVar) throws IOException {
        Map.Entry entry;
        Iterator it;
        boolean z;
        int i;
        int i2;
        int i3;
        int i4;
        zzci zzciVar = this;
        if (zzciVar.zzh) {
            zzao zzaoVarZzb = zzciVar.zzn.zzb(obj);
            if (zzaoVarZzb.zza.isEmpty()) {
                entry = null;
                it = null;
            } else {
                Iterator itZzc = zzaoVarZzb.zzc();
                entry = (Map.Entry) itZzc.next();
                it = itZzc;
            }
        } else {
            entry = null;
            it = null;
        }
        int[] iArr = zzciVar.zzc;
        Unsafe unsafe = zzb;
        int i5 = 1048575;
        int i6 = 1048575;
        int i7 = 0;
        int i8 = 0;
        while (i7 < iArr.length) {
            int iZzA = zzciVar.zzA(i7);
            int iZzC = zzC(iZzA);
            int i9 = iArr[i7];
            if (iZzC <= 17) {
                int i10 = iArr[i7 + 2];
                z = true;
                int i11 = i10 & i5;
                if (i11 != i6) {
                    i8 = i11 == i5 ? 0 : unsafe.getInt(obj, i11);
                    i6 = i11;
                }
                i = i6;
                i2 = i8;
                i3 = 1 << (i10 >>> 20);
            } else {
                z = true;
                i = i6;
                i2 = i8;
                i3 = 0;
            }
            while (true) {
                if (entry != null) {
                    zzak zzakVar = zzciVar.zzn;
                    i4 = i5;
                    if (zzakVar.zzf(entry) <= i9) {
                        zzakVar.zzg(zzdvVar, entry);
                        entry = it.hasNext() ? (Map.Entry) it.next() : null;
                        i5 = i4;
                    }
                } else {
                    i4 = i5;
                }
            }
            long j = iZzA & i4;
            switch (iZzC) {
                case 0:
                    if (zzciVar.zzM(obj, i7, i, i2, i3)) {
                        zzdvVar.zzf(i9, zzdm.zzl(obj, j));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 1:
                    if (zzciVar.zzM(obj, i7, i, i2, i3)) {
                        zzdvVar.zze(i9, zzdm.zzj(obj, j));
                    }
                    zzciVar = this;
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 2:
                    if (zzciVar.zzM(obj, i7, i, i2, i3)) {
                        zzdvVar.zzc(i9, unsafe.getLong(obj, j));
                    }
                    zzciVar = this;
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 3:
                    if (zzciVar.zzM(obj, i7, i, i2, i3)) {
                        zzdvVar.zzh(i9, unsafe.getLong(obj, j));
                    }
                    zzciVar = this;
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 4:
                    if (zzciVar.zzM(obj, i7, i, i2, i3)) {
                        zzdvVar.zzi(i9, unsafe.getInt(obj, j));
                    }
                    zzciVar = this;
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 5:
                    if (zzciVar.zzM(obj, i7, i, i2, i3)) {
                        zzdvVar.zzj(i9, unsafe.getLong(obj, j));
                    }
                    zzciVar = this;
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 6:
                    if (zzciVar.zzM(obj, i7, i, i2, i3)) {
                        zzdvVar.zzk(i9, unsafe.getInt(obj, j));
                    }
                    zzciVar = this;
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 7:
                    if (zzciVar.zzM(obj, i7, i, i2, i3)) {
                        zzdvVar.zzl(i9, zzdm.zzh(obj, j));
                    }
                    zzciVar = this;
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 8:
                    if (zzciVar.zzM(obj, i7, i, i2, i3)) {
                        zzT(i9, unsafe.getObject(obj, j), zzdvVar);
                    }
                    zzciVar = this;
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 9:
                    if (zzciVar.zzM(obj, i7, i, i2, i3)) {
                        zzdvVar.zzr(i9, unsafe.getObject(obj, j), zzciVar.zzq(i7));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 10:
                    if (zzciVar.zzM(obj, i7, i, i2, i3)) {
                        zzdvVar.zzn(i9, (zzt) unsafe.getObject(obj, j));
                    }
                    zzciVar = this;
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 11:
                    if (zzciVar.zzM(obj, i7, i, i2, i3)) {
                        zzdvVar.zzo(i9, unsafe.getInt(obj, j));
                    }
                    zzciVar = this;
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 12:
                    if (zzciVar.zzM(obj, i7, i, i2, i3)) {
                        zzdvVar.zzg(i9, unsafe.getInt(obj, j));
                    }
                    zzciVar = this;
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 13:
                    if (zzciVar.zzM(obj, i7, i, i2, i3)) {
                        zzdvVar.zzb(i9, unsafe.getInt(obj, j));
                    }
                    zzciVar = this;
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 14:
                    if (zzciVar.zzM(obj, i7, i, i2, i3)) {
                        zzdvVar.zzd(i9, unsafe.getLong(obj, j));
                    }
                    zzciVar = this;
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 15:
                    if (zzciVar.zzM(obj, i7, i, i2, i3)) {
                        zzdvVar.zzp(i9, unsafe.getInt(obj, j));
                    }
                    zzciVar = this;
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 16:
                    if (zzciVar.zzM(obj, i7, i, i2, i3)) {
                        zzdvVar.zzq(i9, unsafe.getLong(obj, j));
                    }
                    zzciVar = this;
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 17:
                    if (zzciVar.zzM(obj, i7, i, i2, i3)) {
                        zzdvVar.zzs(i9, unsafe.getObject(obj, j), zzciVar.zzq(i7));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 18:
                    zzcv.zzb(iArr[i7], (List) unsafe.getObject(obj, j), zzdvVar, false);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 19:
                    zzcv.zzc(iArr[i7], (List) unsafe.getObject(obj, j), zzdvVar, false);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 20:
                    zzcv.zzd(iArr[i7], (List) unsafe.getObject(obj, j), zzdvVar, false);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 21:
                    zzcv.zze(iArr[i7], (List) unsafe.getObject(obj, j), zzdvVar, false);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 22:
                    zzcv.zzi(iArr[i7], (List) unsafe.getObject(obj, j), zzdvVar, false);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 23:
                    zzcv.zzg(iArr[i7], (List) unsafe.getObject(obj, j), zzdvVar, false);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 24:
                    zzcv.zzl(iArr[i7], (List) unsafe.getObject(obj, j), zzdvVar, false);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 25:
                    zzcv.zzo(iArr[i7], (List) unsafe.getObject(obj, j), zzdvVar, false);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 26:
                    zzcv.zzp(iArr[i7], (List) unsafe.getObject(obj, j), zzdvVar);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 27:
                    zzcv.zzr(iArr[i7], (List) unsafe.getObject(obj, j), zzdvVar, zzciVar.zzq(i7));
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 28:
                    zzcv.zzq(iArr[i7], (List) unsafe.getObject(obj, j), zzdvVar);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 29:
                    zzcv.zzj(iArr[i7], (List) unsafe.getObject(obj, j), zzdvVar, false);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 30:
                    zzcv.zzn(iArr[i7], (List) unsafe.getObject(obj, j), zzdvVar, false);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 31:
                    zzcv.zzm(iArr[i7], (List) unsafe.getObject(obj, j), zzdvVar, false);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 32:
                    zzcv.zzh(iArr[i7], (List) unsafe.getObject(obj, j), zzdvVar, false);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 33:
                    zzcv.zzk(iArr[i7], (List) unsafe.getObject(obj, j), zzdvVar, false);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 34:
                    zzcv.zzf(iArr[i7], (List) unsafe.getObject(obj, j), zzdvVar, false);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 35:
                    zzcv.zzb(iArr[i7], (List) unsafe.getObject(obj, j), zzdvVar, z);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 36:
                    zzcv.zzc(iArr[i7], (List) unsafe.getObject(obj, j), zzdvVar, z);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 37:
                    zzcv.zzd(iArr[i7], (List) unsafe.getObject(obj, j), zzdvVar, z);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 38:
                    zzcv.zze(iArr[i7], (List) unsafe.getObject(obj, j), zzdvVar, z);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 39:
                    zzcv.zzi(iArr[i7], (List) unsafe.getObject(obj, j), zzdvVar, z);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 40:
                    zzcv.zzg(iArr[i7], (List) unsafe.getObject(obj, j), zzdvVar, z);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 41:
                    zzcv.zzl(iArr[i7], (List) unsafe.getObject(obj, j), zzdvVar, z);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 42:
                    zzcv.zzo(iArr[i7], (List) unsafe.getObject(obj, j), zzdvVar, z);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 43:
                    zzcv.zzj(iArr[i7], (List) unsafe.getObject(obj, j), zzdvVar, z);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 44:
                    zzcv.zzn(iArr[i7], (List) unsafe.getObject(obj, j), zzdvVar, z);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 45:
                    zzcv.zzm(iArr[i7], (List) unsafe.getObject(obj, j), zzdvVar, z);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 46:
                    zzcv.zzh(iArr[i7], (List) unsafe.getObject(obj, j), zzdvVar, z);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 47:
                    zzcv.zzk(iArr[i7], (List) unsafe.getObject(obj, j), zzdvVar, z);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 48:
                    zzcv.zzf(iArr[i7], (List) unsafe.getObject(obj, j), zzdvVar, z);
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 49:
                    zzcv.zzs(iArr[i7], (List) unsafe.getObject(obj, j), zzdvVar, zzciVar.zzq(i7));
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 50:
                    if (unsafe.getObject(obj, j) != null) {
                        throw null;
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 51:
                    if (zzciVar.zzP(obj, i9, i7)) {
                        zzdvVar.zzf(i9, zzG(obj, j));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 52:
                    if (zzciVar.zzP(obj, i9, i7)) {
                        zzdvVar.zze(i9, zzH(obj, j));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 53:
                    if (zzciVar.zzP(obj, i9, i7)) {
                        zzdvVar.zzc(i9, zzJ(obj, j));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 54:
                    if (zzciVar.zzP(obj, i9, i7)) {
                        zzdvVar.zzh(i9, zzJ(obj, j));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 55:
                    if (zzciVar.zzP(obj, i9, i7)) {
                        zzdvVar.zzi(i9, zzI(obj, j));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 56:
                    if (zzciVar.zzP(obj, i9, i7)) {
                        zzdvVar.zzj(i9, zzJ(obj, j));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 57:
                    if (zzciVar.zzP(obj, i9, i7)) {
                        zzdvVar.zzk(i9, zzI(obj, j));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 58:
                    if (zzciVar.zzP(obj, i9, i7)) {
                        zzdvVar.zzl(i9, zzK(obj, j));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 59:
                    if (zzciVar.zzP(obj, i9, i7)) {
                        zzT(i9, unsafe.getObject(obj, j), zzdvVar);
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 60:
                    if (zzciVar.zzP(obj, i9, i7)) {
                        zzdvVar.zzr(i9, unsafe.getObject(obj, j), zzciVar.zzq(i7));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 61:
                    if (zzciVar.zzP(obj, i9, i7)) {
                        zzdvVar.zzn(i9, (zzt) unsafe.getObject(obj, j));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 62:
                    if (zzciVar.zzP(obj, i9, i7)) {
                        zzdvVar.zzo(i9, zzI(obj, j));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 63:
                    if (zzciVar.zzP(obj, i9, i7)) {
                        zzdvVar.zzg(i9, zzI(obj, j));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 64:
                    if (zzciVar.zzP(obj, i9, i7)) {
                        zzdvVar.zzb(i9, zzI(obj, j));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 65:
                    if (zzciVar.zzP(obj, i9, i7)) {
                        zzdvVar.zzd(i9, zzJ(obj, j));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 66:
                    if (zzciVar.zzP(obj, i9, i7)) {
                        zzdvVar.zzp(i9, zzI(obj, j));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 67:
                    if (zzciVar.zzP(obj, i9, i7)) {
                        zzdvVar.zzq(i9, zzJ(obj, j));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                case 68:
                    if (zzciVar.zzP(obj, i9, i7)) {
                        zzdvVar.zzs(i9, unsafe.getObject(obj, j), zzciVar.zzq(i7));
                    }
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
                default:
                    i7 += 3;
                    i8 = i2;
                    i5 = i4;
                    i6 = i;
                    entry = entry;
                    break;
            }
        }
        while (entry != null) {
            zzciVar.zzn.zzg(zzdvVar, entry);
            entry = it.hasNext() ? (Map.Entry) it.next() : null;
        }
        zzde zzdeVar = zzciVar.zzm;
        zzdeVar.zzo(zzdeVar.zzj(obj), zzdvVar);
    }

    /* JADX WARN: Code duplicated, block: B:181:0x0571 A[Catch: all -> 0x0599, TRY_LEAVE, TryCatch #7 {all -> 0x0599, blocks: (B:179:0x056c, B:181:0x0571), top: B:219:0x056c }] */
    /* JADX WARN: Code duplicated, block: B:188:0x0583 A[LOOP:1: B:186:0x057f->B:188:0x0583, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:190:0x0591  */
    /* JADX WARN: Code duplicated, block: B:192:0x0595  */
    /* JADX WARN: Code duplicated, block: B:202:0x05a7 A[LOOP:2: B:200:0x05a3->B:202:0x05a7, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:204:0x05b7  */
    /* JADX WARN: Code duplicated, block: B:308:0x057d A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:317:? A[RETURN, SYNTHETIC] */
    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzct
    public final void zzg(Object obj, zzcs zzcsVar, zzaj zzajVar) throws Throwable {
        Object obj2;
        Object objZzk;
        int i;
        Object obj3;
        zzci zzciVar;
        zzak zzakVar;
        zzaj zzajVar2;
        int i2;
        zzde zzdeVar;
        Object objZzL;
        zzci zzciVar2 = this;
        zzaj zzajVar3 = zzajVar;
        zzajVar3.getClass();
        zzF(obj);
        zzde zzdeVar2 = zzciVar2.zzm;
        zzak zzakVar2 = zzciVar2.zzn;
        Throwable th = null;
        Object objZze = null;
        zzao zzaoVarZzc = null;
        while (true) {
            try {
                int iZzb = zzcsVar.zzb();
                int iZzR = zzciVar2.zzR(iZzb);
                if (iZzR >= 0) {
                    zzakVar = zzakVar2;
                    zzciVar = zzciVar2;
                    zzajVar2 = zzajVar3;
                    objZzk = objZze;
                    obj2 = obj;
                    try {
                        int iZzA = zzciVar.zzA(iZzR);
                        try {
                            switch (zzC(iZzA)) {
                                case 0:
                                    zzdm.zzm(obj2, iZzA & 1048575, zzcsVar.zze());
                                    zzciVar.zzO(obj2, iZzR);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 1:
                                    zzdm.zzk(obj2, iZzA & 1048575, zzcsVar.zzf());
                                    zzciVar.zzO(obj2, iZzR);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 2:
                                    zzdm.zzg(obj2, iZzA & 1048575, zzcsVar.zzh());
                                    zzciVar.zzO(obj2, iZzR);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 3:
                                    zzdm.zzg(obj2, iZzA & 1048575, zzcsVar.zzg());
                                    zzciVar.zzO(obj2, iZzR);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 4:
                                    zzdm.zze(obj2, iZzA & 1048575, zzcsVar.zzi());
                                    zzciVar.zzO(obj2, iZzR);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 5:
                                    zzdm.zzg(obj2, iZzA & 1048575, zzcsVar.zzj());
                                    zzciVar.zzO(obj2, iZzR);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 6:
                                    zzdm.zze(obj2, iZzA & 1048575, zzcsVar.zzk());
                                    zzciVar.zzO(obj2, iZzR);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 7:
                                    zzdm.zzi(obj2, iZzA & 1048575, zzcsVar.zzl());
                                    zzciVar.zzO(obj2, iZzR);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 8:
                                    zzciVar.zzz(obj2, iZzA, zzcsVar);
                                    zzciVar.zzO(obj2, iZzR);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 9:
                                    zzcf zzcfVar = (zzcf) zzciVar.zzt(obj2, iZzR);
                                    zzcsVar.zzq(zzcfVar, zzciVar.zzq(iZzR), zzajVar2);
                                    zzciVar.zzu(obj2, iZzR, zzcfVar);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 10:
                                    zzdm.zzo(obj2, iZzA & 1048575, zzcsVar.zzs());
                                    zzciVar.zzO(obj2, iZzR);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 11:
                                    zzdm.zze(obj2, iZzA & 1048575, zzcsVar.zzt());
                                    zzciVar.zzO(obj2, iZzR);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 12:
                                    int iZzu = zzcsVar.zzu();
                                    zzbe zzbeVarZzs = zzciVar.zzs(iZzR);
                                    if (zzbeVarZzs == null || zzbeVarZzs.isInRange(iZzu)) {
                                        zzdm.zze(obj2, iZzA & 1048575, iZzu);
                                        zzciVar.zzO(obj2, iZzR);
                                        objZze = objZzk;
                                    } else {
                                        objZze = zzcv.zzM(obj2, iZzb, iZzu, objZzk, zzdeVar2);
                                    }
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 13:
                                    zzdm.zze(obj2, iZzA & 1048575, zzcsVar.zzv());
                                    zzciVar.zzO(obj2, iZzR);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 14:
                                    zzdm.zzg(obj2, iZzA & 1048575, zzcsVar.zzw());
                                    zzciVar.zzO(obj2, iZzR);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 15:
                                    zzdm.zze(obj2, iZzA & 1048575, zzcsVar.zzx());
                                    zzciVar.zzO(obj2, iZzR);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 16:
                                    zzdm.zzg(obj2, iZzA & 1048575, zzcsVar.zzy());
                                    zzciVar.zzO(obj2, iZzR);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 17:
                                    zzcf zzcfVar2 = (zzcf) zzciVar.zzt(obj2, iZzR);
                                    zzcsVar.zzr(zzcfVar2, zzciVar.zzq(iZzR), zzajVar2);
                                    zzciVar.zzu(obj2, iZzR, zzcfVar2);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 18:
                                    zzcsVar.zzz(zzbs.zza(obj2, iZzA & 1048575));
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 19:
                                    zzcsVar.zzA(zzbs.zza(obj2, iZzA & 1048575));
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 20:
                                    zzcsVar.zzC(zzbs.zza(obj2, iZzA & 1048575));
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 21:
                                    zzcsVar.zzB(zzbs.zza(obj2, iZzA & 1048575));
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 22:
                                    zzcsVar.zzD(zzbs.zza(obj2, iZzA & 1048575));
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 23:
                                    zzcsVar.zzE(zzbs.zza(obj2, iZzA & 1048575));
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 24:
                                    zzcsVar.zzF(zzbs.zza(obj2, iZzA & 1048575));
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 25:
                                    zzcsVar.zzG(zzbs.zza(obj2, iZzA & 1048575));
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 26:
                                    if (zzD(iZzA)) {
                                        ((zzy) zzcsVar).zzH(zzbs.zza(obj2, iZzA & 1048575), true);
                                    } else {
                                        ((zzy) zzcsVar).zzH(zzbs.zza(obj2, iZzA & 1048575), false);
                                    }
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 27:
                                    zzcsVar.zzI(zzbs.zza(obj2, iZzA & 1048575), zzciVar.zzq(iZzR), zzajVar2);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 28:
                                    zzcsVar.zzK(zzbs.zza(obj2, iZzA & 1048575));
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 29:
                                    zzcsVar.zzL(zzbs.zza(obj2, iZzA & 1048575));
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 30:
                                    List listZza = zzbs.zza(obj2, iZzA & 1048575);
                                    zzcsVar.zzM(listZza);
                                    zzdeVar = zzdeVar2;
                                    try {
                                        objZzL = zzcv.zzL(obj2, iZzb, listZza, zzciVar.zzs(iZzR), objZzk, zzdeVar);
                                        zzdeVar2 = zzdeVar;
                                        objZze = objZzL;
                                        zzciVar2 = zzciVar;
                                        zzajVar3 = zzajVar2;
                                        zzakVar2 = zzakVar;
                                    } catch (zzbk unused) {
                                        objZzk = objZzk;
                                        zzdeVar2 = zzdeVar;
                                        zzciVar2 = zzciVar;
                                        objZze = objZzk;
                                        try {
                                            zzdeVar2.zza(zzcsVar);
                                            if (objZze == null) {
                                                objZze = zzdeVar2.zzk(obj2);
                                            }
                                            objZzk = objZze;
                                            try {
                                                if (!zzdeVar2.zzn(objZzk, zzcsVar, 0)) {
                                                    for (i2 = zzciVar2.zzk; i2 < zzciVar2.zzl; i2++) {
                                                        zzciVar2.zzx(obj2, zzciVar2.zzj[i2], objZzk, zzdeVar2, obj);
                                                    }
                                                    obj3 = objZzk;
                                                    if (obj3 != null) {
                                                        zzdeVar2.zzl(obj2, obj3);
                                                    }
                                                }
                                                objZze = objZzk;
                                                zzajVar3 = zzajVar2;
                                                zzakVar2 = zzakVar;
                                                th = null;
                                            } catch (Throwable th2) {
                                                th = th2;
                                            }
                                        } catch (Throwable th3) {
                                            th = th3;
                                            objZzk = objZze;
                                        }
                                    }
                                    break;
                                case 31:
                                    zzcsVar.zzN(zzbs.zza(obj2, iZzA & 1048575));
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 32:
                                    zzcsVar.zzO(zzbs.zza(obj2, iZzA & 1048575));
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 33:
                                    zzcsVar.zzP(zzbs.zza(obj2, iZzA & 1048575));
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 34:
                                    zzcsVar.zzQ(zzbs.zza(obj2, iZzA & 1048575));
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 35:
                                    zzcsVar.zzz(zzbs.zza(obj2, iZzA & 1048575));
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 36:
                                    zzcsVar.zzA(zzbs.zza(obj2, iZzA & 1048575));
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 37:
                                    zzcsVar.zzC(zzbs.zza(obj2, iZzA & 1048575));
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 38:
                                    zzcsVar.zzB(zzbs.zza(obj2, iZzA & 1048575));
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 39:
                                    zzcsVar.zzD(zzbs.zza(obj2, iZzA & 1048575));
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 40:
                                    zzcsVar.zzE(zzbs.zza(obj2, iZzA & 1048575));
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 41:
                                    zzcsVar.zzF(zzbs.zza(obj2, iZzA & 1048575));
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 42:
                                    zzcsVar.zzG(zzbs.zza(obj2, iZzA & 1048575));
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 43:
                                    zzcsVar.zzL(zzbs.zza(obj2, iZzA & 1048575));
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 44:
                                    List listZza2 = zzbs.zza(obj2, iZzA & 1048575);
                                    zzcsVar.zzM(listZza2);
                                    zzdeVar = zzdeVar2;
                                    try {
                                        objZzL = zzcv.zzL(obj2, iZzb, listZza2, zzciVar.zzs(iZzR), objZzk, zzdeVar);
                                        zzdeVar2 = zzdeVar;
                                        objZze = objZzL;
                                        zzciVar2 = zzciVar;
                                        zzajVar3 = zzajVar2;
                                        zzakVar2 = zzakVar;
                                    } catch (zzbk unused2) {
                                        objZzk = objZzk;
                                        zzdeVar2 = zzdeVar;
                                        zzciVar2 = zzciVar;
                                        objZze = objZzk;
                                        zzdeVar2.zza(zzcsVar);
                                        if (objZze == null) {
                                            objZze = zzdeVar2.zzk(obj2);
                                        }
                                        objZzk = objZze;
                                        if (!zzdeVar2.zzn(objZzk, zzcsVar, 0)) {
                                            while (i2 < zzciVar2.zzl) {
                                                zzciVar2.zzx(obj2, zzciVar2.zzj[i2], objZzk, zzdeVar2, obj);
                                            }
                                            obj3 = objZzk;
                                            if (obj3 != null) {
                                                zzdeVar2.zzl(obj2, obj3);
                                            }
                                        }
                                        objZze = objZzk;
                                        zzajVar3 = zzajVar2;
                                        zzakVar2 = zzakVar;
                                        th = null;
                                    }
                                    break;
                                case 45:
                                    zzcsVar.zzN(zzbs.zza(obj2, iZzA & 1048575));
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 46:
                                    zzcsVar.zzO(zzbs.zza(obj2, iZzA & 1048575));
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 47:
                                    zzcsVar.zzP(zzbs.zza(obj2, iZzA & 1048575));
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 48:
                                    zzcsVar.zzQ(zzbs.zza(obj2, iZzA & 1048575));
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 49:
                                    zzcsVar.zzJ(zzbs.zza(obj2, iZzA & 1048575), zzciVar.zzq(iZzR), zzajVar2);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 50:
                                    Object objZzr = zzciVar.zzr(iZzR);
                                    long jZzA = zzciVar.zzA(iZzR) & 1048575;
                                    Object objZzn = zzdm.zzn(obj2, jZzA);
                                    if (objZzn == null) {
                                        objZzn = zzbz.zza().zzc();
                                        zzdm.zzo(obj2, jZzA, objZzn);
                                    } else if (zzca.zza(objZzn)) {
                                        Object objZzc = zzbz.zza().zzc();
                                        zzca.zzb(objZzc, objZzn);
                                        zzdm.zzo(obj2, jZzA, objZzc);
                                        objZzn = objZzc;
                                    }
                                    throw th;
                                case 51:
                                    zzdm.zzo(obj2, iZzA & 1048575, Double.valueOf(zzcsVar.zze()));
                                    zzciVar.zzQ(obj2, iZzb, iZzR);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 52:
                                    zzdm.zzo(obj2, iZzA & 1048575, Float.valueOf(zzcsVar.zzf()));
                                    zzciVar.zzQ(obj2, iZzb, iZzR);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 53:
                                    zzdm.zzo(obj2, iZzA & 1048575, Long.valueOf(zzcsVar.zzh()));
                                    zzciVar.zzQ(obj2, iZzb, iZzR);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 54:
                                    zzdm.zzo(obj2, iZzA & 1048575, Long.valueOf(zzcsVar.zzg()));
                                    zzciVar.zzQ(obj2, iZzb, iZzR);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 55:
                                    zzdm.zzo(obj2, iZzA & 1048575, Integer.valueOf(zzcsVar.zzi()));
                                    zzciVar.zzQ(obj2, iZzb, iZzR);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 56:
                                    zzdm.zzo(obj2, iZzA & 1048575, Long.valueOf(zzcsVar.zzj()));
                                    zzciVar.zzQ(obj2, iZzb, iZzR);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 57:
                                    zzdm.zzo(obj2, iZzA & 1048575, Integer.valueOf(zzcsVar.zzk()));
                                    zzciVar.zzQ(obj2, iZzb, iZzR);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 58:
                                    zzdm.zzo(obj2, iZzA & 1048575, Boolean.valueOf(zzcsVar.zzl()));
                                    zzciVar.zzQ(obj2, iZzb, iZzR);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 59:
                                    zzciVar.zzz(obj2, iZzA, zzcsVar);
                                    zzciVar.zzQ(obj2, iZzb, iZzR);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 60:
                                    zzcf zzcfVar3 = (zzcf) zzciVar.zzv(obj2, iZzb, iZzR);
                                    zzcsVar.zzq(zzcfVar3, zzciVar.zzq(iZzR), zzajVar2);
                                    zzciVar.zzw(obj2, iZzb, iZzR, zzcfVar3);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 61:
                                    zzdm.zzo(obj2, iZzA & 1048575, zzcsVar.zzs());
                                    zzciVar.zzQ(obj2, iZzb, iZzR);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 62:
                                    zzdm.zzo(obj2, iZzA & 1048575, Integer.valueOf(zzcsVar.zzt()));
                                    zzciVar.zzQ(obj2, iZzb, iZzR);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 63:
                                    int iZzu2 = zzcsVar.zzu();
                                    zzbe zzbeVarZzs2 = zzciVar.zzs(iZzR);
                                    if (zzbeVarZzs2 != null && !zzbeVarZzs2.isInRange(iZzu2)) {
                                        objZze = zzcv.zzM(obj2, iZzb, iZzu2, objZzk, zzdeVar2);
                                        zzciVar2 = zzciVar;
                                        zzajVar3 = zzajVar2;
                                        zzakVar2 = zzakVar;
                                    }
                                    zzdm.zzo(obj2, iZzA & 1048575, Integer.valueOf(iZzu2));
                                    zzciVar.zzQ(obj2, iZzb, iZzR);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 64:
                                    zzdm.zzo(obj2, iZzA & 1048575, Integer.valueOf(zzcsVar.zzv()));
                                    zzciVar.zzQ(obj2, iZzb, iZzR);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 65:
                                    zzdm.zzo(obj2, iZzA & 1048575, Long.valueOf(zzcsVar.zzw()));
                                    zzciVar.zzQ(obj2, iZzb, iZzR);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 66:
                                    zzdm.zzo(obj2, iZzA & 1048575, Integer.valueOf(zzcsVar.zzx()));
                                    zzciVar.zzQ(obj2, iZzb, iZzR);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 67:
                                    zzdm.zzo(obj2, iZzA & 1048575, Long.valueOf(zzcsVar.zzy()));
                                    zzciVar.zzQ(obj2, iZzb, iZzR);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                case 68:
                                    zzcf zzcfVar4 = (zzcf) zzciVar.zzv(obj2, iZzb, iZzR);
                                    zzcsVar.zzr(zzcfVar4, zzciVar.zzq(iZzR), zzajVar2);
                                    zzciVar.zzw(obj2, iZzb, iZzR, zzcfVar4);
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                    th = null;
                                    break;
                                default:
                                    if (objZzk == null) {
                                        objZzk = zzdeVar2.zzk(obj2);
                                    }
                                    try {
                                        if (zzdeVar2.zzn(objZzk, zzcsVar, 0)) {
                                            objZze = objZzk;
                                            zzciVar2 = zzciVar;
                                            zzajVar3 = zzajVar2;
                                            zzakVar2 = zzakVar;
                                            th = null;
                                        } else {
                                            int i3 = zzciVar.zzk;
                                            while (i3 < zzciVar.zzl) {
                                                Object obj4 = obj2;
                                                zzciVar.zzx(obj4, zzciVar.zzj[i3], objZzk, zzdeVar2, obj);
                                                i3++;
                                                obj2 = obj4;
                                            }
                                            obj2 = obj2;
                                            obj3 = objZzk;
                                        }
                                    } catch (zzbk unused3) {
                                        obj2 = obj2;
                                        zzciVar2 = zzciVar;
                                        objZze = objZzk;
                                        zzdeVar2.zza(zzcsVar);
                                        if (objZze == null) {
                                            objZze = zzdeVar2.zzk(obj2);
                                        }
                                        objZzk = objZze;
                                        if (!zzdeVar2.zzn(objZzk, zzcsVar, 0)) {
                                            while (i2 < zzciVar2.zzl) {
                                                zzciVar2.zzx(obj2, zzciVar2.zzj[i2], objZzk, zzdeVar2, obj);
                                            }
                                            obj3 = objZzk;
                                            if (obj3 != null) {
                                                zzdeVar2.zzl(obj2, obj3);
                                            }
                                        }
                                        objZze = objZzk;
                                    }
                                    break;
                            }
                        } catch (zzbk unused4) {
                            obj2 = obj2;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        obj2 = obj2;
                        zzciVar2 = zzciVar;
                    }
                } else if (iZzb == Integer.MAX_VALUE) {
                    int i4 = zzciVar2.zzk;
                    while (i4 < zzciVar2.zzl) {
                        Object obj5 = objZze;
                        zzciVar2.zzx(obj, zzciVar2.zzj[i4], obj5, zzdeVar2, obj);
                        i4++;
                        objZze = obj5;
                        zzdeVar2 = zzdeVar2;
                        zzciVar2 = zzciVar2;
                    }
                    obj3 = objZze;
                    zzdeVar2 = zzdeVar2;
                    obj2 = obj;
                } else {
                    Object obj6 = objZze;
                    zzde zzdeVar3 = zzdeVar2;
                    zzciVar = zzciVar2;
                    obj2 = obj;
                    try {
                        Object objZzh = !zzciVar.zzh ? th : zzakVar2.zzh(zzajVar3, zzciVar.zzg, iZzb);
                        if (objZzh != null) {
                            if (zzaoVarZzc == null) {
                                try {
                                    zzaoVarZzc = zzakVar2.zzc(obj2);
                                } catch (Throwable th5) {
                                    th = th5;
                                    obj2 = obj2;
                                    objZzk = obj6;
                                    zzdeVar2 = zzdeVar3;
                                    zzciVar2 = zzciVar;
                                    i = zzciVar2.zzk;
                                    while (i < zzciVar2.zzl) {
                                        zzciVar2.zzx(obj2, zzciVar2.zzj[i], objZzk, zzdeVar2, obj);
                                        i++;
                                        zzciVar2 = this;
                                    }
                                    if (objZzk != null) {
                                        zzdeVar2.zzl(obj2, objZzk);
                                    }
                                    throw th;
                                }
                            }
                            zzao zzaoVar = zzaoVarZzc;
                            try {
                                objZze = zzakVar2.zze(obj2, zzcsVar, objZzh, zzajVar3, zzaoVar, obj6, zzdeVar3);
                                zzaoVarZzc = zzaoVar;
                                zzdeVar2 = zzdeVar3;
                                zzakVar = zzakVar2;
                                zzajVar2 = zzajVar3;
                                zzciVar2 = zzciVar;
                                zzajVar3 = zzajVar2;
                                zzakVar2 = zzakVar;
                            } catch (Throwable th6) {
                                th = th6;
                                obj2 = obj2;
                                objZzk = obj6;
                                zzdeVar2 = zzdeVar3;
                                zzciVar2 = zzciVar;
                                i = zzciVar2.zzk;
                                while (i < zzciVar2.zzl) {
                                    zzciVar2.zzx(obj2, zzciVar2.zzj[i], objZzk, zzdeVar2, obj);
                                    i++;
                                    zzciVar2 = this;
                                }
                                if (objZzk != null) {
                                    zzdeVar2.zzl(obj2, objZzk);
                                }
                                throw th;
                            }
                        } else {
                            zzakVar = zzakVar2;
                            obj2 = obj2;
                            zzajVar2 = zzajVar3;
                            objZzk = obj6;
                            zzdeVar2 = zzdeVar3;
                            try {
                                zzdeVar2.zza(zzcsVar);
                                if (objZzk == null) {
                                    try {
                                        objZzk = zzdeVar2.zzk(obj2);
                                    } catch (Throwable th7) {
                                        th = th7;
                                        zzciVar2 = zzciVar;
                                        i = zzciVar2.zzk;
                                        while (i < zzciVar2.zzl) {
                                            zzciVar2.zzx(obj2, zzciVar2.zzj[i], objZzk, zzdeVar2, obj);
                                            i++;
                                            zzciVar2 = this;
                                        }
                                        if (objZzk != null) {
                                            zzdeVar2.zzl(obj2, objZzk);
                                        }
                                        throw th;
                                    }
                                }
                                if (zzdeVar2.zzn(objZzk, zzcsVar, 0)) {
                                    objZze = objZzk;
                                    zzciVar2 = zzciVar;
                                    zzajVar3 = zzajVar2;
                                    zzakVar2 = zzakVar;
                                } else {
                                    for (int i5 = zzciVar.zzk; i5 < zzciVar.zzl; i5++) {
                                        zzciVar.zzx(obj2, zzciVar.zzj[i5], objZzk, zzdeVar2, obj);
                                    }
                                    obj3 = objZzk;
                                }
                            } catch (Throwable th8) {
                                th = th8;
                            }
                        }
                    } catch (Throwable th9) {
                        th = th9;
                        objZzk = obj6;
                        zzdeVar2 = zzdeVar3;
                        obj2 = obj2;
                        zzciVar2 = zzciVar;
                        i = zzciVar2.zzk;
                        while (i < zzciVar2.zzl) {
                            zzciVar2.zzx(obj2, zzciVar2.zzj[i], objZzk, zzdeVar2, obj);
                            i++;
                            zzciVar2 = this;
                        }
                        if (objZzk != null) {
                            zzdeVar2.zzl(obj2, objZzk);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th10) {
                th = th10;
                obj2 = obj;
            }
            i = zzciVar2.zzk;
            while (i < zzciVar2.zzl) {
                zzciVar2.zzx(obj2, zzciVar2.zzj[i], objZzk, zzdeVar2, obj);
                i++;
                zzciVar2 = this;
            }
            if (objZzk != null) {
                zzdeVar2.zzl(obj2, objZzk);
            }
            throw th;
        }
        if (obj3 != null) {
            zzdeVar2.zzl(obj2, obj3);
        }
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached with updateSeq = 32661. Try increasing type updates limit count.
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:79)
        */
    final int zzi(java.lang.Object r31, byte[] r32, int r33, int r34, int r35, com.google.android.appfunctions.schema.internal.dependencies.zzg r36) throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 3266
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.appfunctions.schema.internal.dependencies.zzci.zzi(java.lang.Object, byte[], int, int, int, com.google.android.appfunctions.schema.internal.dependencies.zzg):int");
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzct
    public final void zzj(Object obj, byte[] bArr, int i, int i2, zzg zzgVar) throws IOException {
        zzi(obj, bArr, i, i2, 0, zzgVar);
    }

    /* JADX WARN: Code duplicated, block: B:26:0x006d  */
    /* JADX WARN: Code duplicated, block: B:28:0x0073  */
    /* JADX WARN: Code duplicated, block: B:41:0x0080 A[SYNTHETIC] */
    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzct
    public final void zzk(Object obj) {
        if (zzE(obj)) {
            if (obj instanceof zzay) {
                zzay zzayVar = (zzay) obj;
                zzayVar.clearMemoizedSerializedSize();
                zzayVar.clearMemoizedHashCode();
                zzayVar.markImmutable();
            }
            int[] iArr = this.zzc;
            for (int i = 0; i < iArr.length; i += 3) {
                int iZzA = zzA(i);
                int i2 = 1048575 & iZzA;
                int iZzC = zzC(iZzA);
                long j = i2;
                if (iZzC != 9) {
                    if (iZzC != 60 && iZzC != 68) {
                        switch (iZzC) {
                            case 17:
                                if (zzN(obj, i)) {
                                    zzq(i).zzk(zzb.getObject(obj, j));
                                }
                                break;
                            case 18:
                            case 19:
                            case 20:
                            case 21:
                            case 22:
                            case 23:
                            case 24:
                            case 25:
                            case 26:
                            case 27:
                            case 28:
                            case 29:
                            case 30:
                            case 31:
                            case 32:
                            case 33:
                            case 34:
                            case 35:
                            case 36:
                            case 37:
                            case 38:
                            case 39:
                            case 40:
                            case 41:
                            case 42:
                            case 43:
                            case 44:
                            case 45:
                            case 46:
                            case 47:
                            case 48:
                            case 49:
                                ((zzbi) zzdm.zzn(obj, j)).zzb();
                                break;
                            case 50:
                                Unsafe unsafe = zzb;
                                Object object = unsafe.getObject(obj, j);
                                if (object != null) {
                                    ((zzbz) object).zzd();
                                    unsafe.putObject(obj, j, object);
                                }
                                break;
                        }
                    } else if (zzP(obj, iArr[i], i)) {
                        zzq(i).zzk(zzb.getObject(obj, j));
                    }
                } else if (zzN(obj, i)) {
                    zzq(i).zzk(zzb.getObject(obj, j));
                }
            }
            this.zzm.zzm(obj);
            if (this.zzh) {
                this.zzn.zzd(obj);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:42:0x008d  */
    /* JADX WARN: Code duplicated, block: B:44:0x009c  */
    /* JADX WARN: Code duplicated, block: B:47:0x00a7  */
    /* JADX WARN: Code duplicated, block: B:50:0x00b2 A[LOOP:1: B:45:0x00a1->B:50:0x00b2, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:67:0x00b1 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:71:0x00c8 A[SYNTHETIC] */
    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzct
    public final boolean zzl(Object obj) {
        int i;
        int i2;
        List list;
        zzct zzctVarZzq;
        int i3;
        int i4 = 0;
        int i5 = 0;
        int i6 = 1048575;
        while (i5 < this.zzk) {
            int[] iArr = this.zzj;
            int[] iArr2 = this.zzc;
            int i7 = iArr[i5];
            int i8 = iArr2[i7];
            int iZzA = zzA(i7);
            int i9 = iArr2[i7 + 2];
            int i10 = i9 & 1048575;
            int i11 = 1 << (i9 >>> 20);
            if (i10 != i6) {
                if (i10 != 1048575) {
                    i4 = zzb.getInt(obj, i10);
                }
                i2 = i4;
                i = i10;
            } else {
                int i12 = i4;
                i = i6;
                i2 = i12;
            }
            if ((268435456 & iZzA) != 0 && !zzM(obj, i7, i, i2, i11)) {
                return false;
            }
            int iZzC = zzC(iZzA);
            if (iZzC == 9 || iZzC == 17) {
                if (zzM(obj, i7, i, i2, i11) && !zzy(obj, iZzA, zzq(i7))) {
                    return false;
                }
            } else if (iZzC == 27) {
                list = (List) zzdm.zzn(obj, iZzA & 1048575);
                if (list.isEmpty()) {
                    continue;
                } else {
                    zzctVarZzq = zzq(i7);
                    for (i3 = 0; i3 < list.size(); i3++) {
                        if (!zzctVarZzq.zzl(list.get(i3))) {
                            return false;
                        }
                    }
                }
            } else if (iZzC == 60 || iZzC == 68) {
                if (zzP(obj, i8, i7) && !zzy(obj, iZzA, zzq(i7))) {
                    return false;
                }
            } else if (iZzC == 49) {
                list = (List) zzdm.zzn(obj, iZzA & 1048575);
                if (list.isEmpty()) {
                    zzctVarZzq = zzq(i7);
                    while (i3 < list.size()) {
                        if (!zzctVarZzq.zzl(list.get(i3))) {
                            return false;
                        }
                    }
                } else {
                    continue;
                }
            } else if (iZzC == 50 && !((zzbz) zzdm.zzn(obj, iZzA & 1048575)).isEmpty()) {
                throw null;
            }
            i5++;
            i6 = i;
            i4 = i2;
        }
        return !this.zzh || this.zzn.zzb(obj).zzg();
    }
}
