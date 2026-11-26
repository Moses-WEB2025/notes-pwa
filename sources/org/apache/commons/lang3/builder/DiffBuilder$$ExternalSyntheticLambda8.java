package org.apache.commons.lang3.builder;

import java.io.Serializable;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes6.dex */
public final /* synthetic */ class DiffBuilder$$ExternalSyntheticLambda8 implements DiffBuilder.SerializableSupplier, Serializable {
    public final /* synthetic */ long f$0;

    public /* synthetic */ DiffBuilder$$ExternalSyntheticLambda8(long j) {
        this.f$0 = j;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return Long.valueOf(this.f$0);
    }
}
