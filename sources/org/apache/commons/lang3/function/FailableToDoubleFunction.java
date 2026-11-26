package org.apache.commons.lang3.function;

import java.lang.Throwable;

/* JADX INFO: loaded from: classes6.dex */
@FunctionalInterface
public interface FailableToDoubleFunction<T, E extends Throwable> {
    public static final FailableToDoubleFunction NOP = new FailableToDoubleFunction() { // from class: org.apache.commons.lang3.function.FailableToDoubleFunction$$ExternalSyntheticLambda0
        @Override // org.apache.commons.lang3.function.FailableToDoubleFunction
        public final double applyAsDouble(Object obj) {
            return FailableToDoubleFunction.lambda$static$0(obj);
        }
    };

    static /* synthetic */ double lambda$static$0(Object obj) throws Throwable {
        return 0.0d;
    }

    double applyAsDouble(T t) throws Throwable;

    static <T, E extends Throwable> FailableToDoubleFunction<T, E> nop() {
        return NOP;
    }
}
