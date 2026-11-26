package org.apache.commons.lang3.builder;

import java.io.Serializable;
import org.apache.commons.lang3.ArrayUtils;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes6.dex */
public final /* synthetic */ class DiffBuilder$$ExternalSyntheticLambda9 implements DiffBuilder.SerializableSupplier, Serializable {
    public final /* synthetic */ char[] f$0;

    public /* synthetic */ DiffBuilder$$ExternalSyntheticLambda9(char[] cArr) {
        this.f$0 = cArr;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return ArrayUtils.toObject(this.f$0);
    }
}
