package org.apache.commons.lang3.function;

import java.lang.Throwable;

/* JADX INFO: loaded from: classes6.dex */
@FunctionalInterface
public interface FailableSupplier<T, E extends Throwable> {
    public static final FailableSupplier NUL = new FailableSupplier() { // from class: org.apache.commons.lang3.function.FailableSupplier$$ExternalSyntheticLambda0
        @Override // org.apache.commons.lang3.function.FailableSupplier
        public final Object get() {
            return FailableSupplier.lambda$static$0();
        }
    };

    static /* synthetic */ Object lambda$static$0() throws Throwable {
        return null;
    }

    T get() throws Throwable;

    static <T, E extends Exception> FailableSupplier<T, E> nul() {
        return NUL;
    }
}
