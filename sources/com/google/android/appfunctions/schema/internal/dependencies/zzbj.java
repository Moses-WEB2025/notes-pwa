package com.google.android.appfunctions.schema.internal.dependencies;

import defpackage.R2;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzbj {
    static final Charset zza = Charset.forName("UTF-8");
    public static final byte[] zzb;

    static {
        Charset.forName("ISO-8859-1");
        byte[] bArr = new byte[0];
        zzb = bArr;
        ByteBuffer.wrap(bArr);
        int i = zzx.zzf;
        zzx.zzG(bArr, 0, 0, false);
    }

    static Object zza(Object obj, String str) {
        if (obj != null) {
            return obj;
        }
        throw new NullPointerException(str);
    }

    public static int zzb(boolean z) {
        return z ? R2.attr.headerView : R2.attr.helperTextTextAppearance;
    }

    static int zzc(int i, byte[] bArr, int i2, int i3) {
        for (int i4 = i2; i4 < i2 + i3; i4++) {
            i = (i * 31) + bArr[i4];
        }
        return i;
    }
}
