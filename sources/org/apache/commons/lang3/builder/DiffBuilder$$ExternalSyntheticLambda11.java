package org.apache.commons.lang3.builder;

import java.io.Serializable;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes6.dex */
public final /* synthetic */ class DiffBuilder$$ExternalSyntheticLambda11 implements DiffBuilder.SerializableSupplier, Serializable {
    public final /* synthetic */ Object[] f$0;

    public /* synthetic */ DiffBuilder$$ExternalSyntheticLambda11(Object[] objArr) {
        this.f$0 = objArr;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return DiffBuilder.lambda$append$dbd7d6e4$1(this.f$0);
    }
}
