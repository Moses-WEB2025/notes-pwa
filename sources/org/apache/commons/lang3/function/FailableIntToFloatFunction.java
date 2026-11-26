package org.apache.commons.lang3.function;

import java.lang.Throwable;

/* JADX INFO: loaded from: classes6.dex */
@FunctionalInterface
public interface FailableIntToFloatFunction<E extends Throwable> {
    public static final FailableIntToFloatFunction NOP = new FailableIntToFloatFunction() { // from class: org.apache.commons.lang3.function.FailableIntToFloatFunction$$ExternalSyntheticLambda0
        @Override // org.apache.commons.lang3.function.FailableIntToFloatFunction
        public final float applyAsFloat(int i) {
            return FailableIntToFloatFunction.lambda$static$0(i);
        }
    };

    static /* synthetic */ float lambda$static$0(int i) throws Throwable {
        return 0.0f;
    }

    float applyAsFloat(int i) throws Throwable;

    static <E extends Throwable> FailableIntToFloatFunction<E> nop() {
        return NOP;
    }
}
