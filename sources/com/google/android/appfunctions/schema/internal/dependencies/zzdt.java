package com.google.android.appfunctions.schema.internal.dependencies;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
public enum zzdt {
    DOUBLE(zzdu.DOUBLE, 1),
    FLOAT(zzdu.FLOAT, 5),
    INT64(zzdu.LONG, 0),
    UINT64(zzdu.LONG, 0),
    INT32(zzdu.INT, 0),
    FIXED64(zzdu.LONG, 1),
    FIXED32(zzdu.INT, 5),
    BOOL(zzdu.BOOLEAN, 0),
    STRING(zzdu.STRING, 2),
    GROUP(zzdu.MESSAGE, 3),
    MESSAGE(zzdu.MESSAGE, 2),
    BYTES(zzdu.BYTE_STRING, 2),
    UINT32(zzdu.INT, 0),
    ENUM(zzdu.ENUM, 0),
    SFIXED32(zzdu.INT, 5),
    SFIXED64(zzdu.LONG, 1),
    SINT32(zzdu.INT, 0),
    SINT64(zzdu.LONG, 0);

    private final zzdu zzs;

    zzdt(zzdu zzduVar, int i) {
        this.zzs = zzduVar;
    }

    public final zzdu zza() {
        return this.zzs;
    }
}
