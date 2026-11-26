package org.apache.commons.lang3.builder;

import java.io.Serializable;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes6.dex */
public final /* synthetic */ class DiffBuilder$$ExternalSyntheticLambda35 implements DiffBuilder.SerializableSupplier, Serializable {
    public final /* synthetic */ short f$0;

    public /* synthetic */ DiffBuilder$$ExternalSyntheticLambda35(short s) {
        this.f$0 = s;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return Short.valueOf(this.f$0);
    }
}
