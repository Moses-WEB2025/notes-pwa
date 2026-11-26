package com.google.android.appfunctions.schema.internal.dependencies;

import java.io.IOException;
import java.util.Locale;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzab extends IOException {
    /* JADX WARN: Illegal instructions before constructor call */
    zzab(long j, long j2, int i, Throwable th) {
        String str = String.format(Locale.US, "Pos: %d, limit: %d, len: %d", Long.valueOf(j), Long.valueOf(j2), Integer.valueOf(i));
        String.valueOf(str);
        super("CodedOutputStream was writing to a flat byte array and ran out of space.: ".concat(String.valueOf(str)), th);
    }

    zzab(Throwable th) {
        super("CodedOutputStream was writing to a flat byte array and ran out of space.", th);
    }
}
