package com.google.android.appfunctions.schema.internal.dependencies;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
public enum zzap {
    DOUBLE(0, 1, zzbm.DOUBLE),
    FLOAT(1, 1, zzbm.FLOAT),
    INT64(2, 1, zzbm.LONG),
    UINT64(3, 1, zzbm.LONG),
    INT32(4, 1, zzbm.INT),
    FIXED64(5, 1, zzbm.LONG),
    FIXED32(6, 1, zzbm.INT),
    BOOL(7, 1, zzbm.BOOLEAN),
    STRING(8, 1, zzbm.STRING),
    MESSAGE(9, 1, zzbm.MESSAGE),
    BYTES(10, 1, zzbm.BYTE_STRING),
    UINT32(11, 1, zzbm.INT),
    ENUM(12, 1, zzbm.ENUM),
    SFIXED32(13, 1, zzbm.INT),
    SFIXED64(14, 1, zzbm.LONG),
    SINT32(15, 1, zzbm.INT),
    SINT64(16, 1, zzbm.LONG),
    GROUP(17, 1, zzbm.MESSAGE),
    DOUBLE_LIST(18, 2, zzbm.DOUBLE),
    FLOAT_LIST(19, 2, zzbm.FLOAT),
    INT64_LIST(20, 2, zzbm.LONG),
    UINT64_LIST(21, 2, zzbm.LONG),
    INT32_LIST(22, 2, zzbm.INT),
    FIXED64_LIST(23, 2, zzbm.LONG),
    FIXED32_LIST(24, 2, zzbm.INT),
    BOOL_LIST(25, 2, zzbm.BOOLEAN),
    STRING_LIST(26, 2, zzbm.STRING),
    MESSAGE_LIST(27, 2, zzbm.MESSAGE),
    BYTES_LIST(28, 2, zzbm.BYTE_STRING),
    UINT32_LIST(29, 2, zzbm.INT),
    ENUM_LIST(30, 2, zzbm.ENUM),
    SFIXED32_LIST(31, 2, zzbm.INT),
    SFIXED64_LIST(32, 2, zzbm.LONG),
    SINT32_LIST(33, 2, zzbm.INT),
    SINT64_LIST(34, 2, zzbm.LONG),
    DOUBLE_LIST_PACKED(35, 3, zzbm.DOUBLE),
    FLOAT_LIST_PACKED(36, 3, zzbm.FLOAT),
    INT64_LIST_PACKED(37, 3, zzbm.LONG),
    UINT64_LIST_PACKED(38, 3, zzbm.LONG),
    INT32_LIST_PACKED(39, 3, zzbm.INT),
    FIXED64_LIST_PACKED(40, 3, zzbm.LONG),
    FIXED32_LIST_PACKED(41, 3, zzbm.INT),
    BOOL_LIST_PACKED(42, 3, zzbm.BOOLEAN),
    UINT32_LIST_PACKED(43, 3, zzbm.INT),
    ENUM_LIST_PACKED(44, 3, zzbm.ENUM),
    SFIXED32_LIST_PACKED(45, 3, zzbm.INT),
    SFIXED64_LIST_PACKED(46, 3, zzbm.LONG),
    SINT32_LIST_PACKED(47, 3, zzbm.INT),
    SINT64_LIST_PACKED(48, 3, zzbm.LONG),
    GROUP_LIST(49, 2, zzbm.MESSAGE),
    MAP(50, 4, zzbm.VOID);

    private static final zzap[] zzaa;
    private final int zzZ;

    static {
        zzap[] zzapVarArrValues = values();
        zzaa = new zzap[zzapVarArrValues.length];
        for (zzap zzapVar : zzapVarArrValues) {
            zzaa[zzapVar.zzZ] = zzapVar;
        }
    }

    zzap(int i, int i2, zzbm zzbmVar) {
        this.zzZ = i;
        int i3 = i2 - 1;
        if (i3 == 1 || i3 == 3) {
            zzbmVar.zza();
        }
        if (i2 == 1) {
            zzbm zzbmVar2 = zzbm.VOID;
            zzbmVar.ordinal();
        }
    }

    public final int zza() {
        return this.zzZ;
    }
}
