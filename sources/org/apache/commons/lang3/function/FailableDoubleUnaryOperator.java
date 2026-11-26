package org.apache.commons.lang3.function;

import java.lang.Throwable;
import java.util.Objects;

/* JADX INFO: loaded from: classes6.dex */
public interface FailableDoubleUnaryOperator<E extends Throwable> {
    public static final FailableDoubleUnaryOperator NOP = new FailableDoubleUnaryOperator() { // from class: org.apache.commons.lang3.function.FailableDoubleUnaryOperator$$ExternalSyntheticLambda1
        @Override // org.apache.commons.lang3.function.FailableDoubleUnaryOperator
        public final double applyAsDouble(double d) {
            return FailableDoubleUnaryOperator.lambda$static$0(d);
        }
    };

    static /* synthetic */ double lambda$identity$1(double d) throws Throwable {
        return d;
    }

    static /* synthetic */ double lambda$static$0(double d) throws Throwable {
        return 0.0d;
    }

    double applyAsDouble(double d) throws Throwable;

    static <E extends Throwable> FailableDoubleUnaryOperator<E> identity() {
        return new FailableDoubleUnaryOperator() { // from class: org.apache.commons.lang3.function.FailableDoubleUnaryOperator$$ExternalSyntheticLambda0
            @Override // org.apache.commons.lang3.function.FailableDoubleUnaryOperator
            public final double applyAsDouble(double d) {
                return FailableDoubleUnaryOperator.lambda$identity$1(d);
            }
        };
    }

    static <E extends Throwable> FailableDoubleUnaryOperator<E> nop() {
        return NOP;
    }

    default FailableDoubleUnaryOperator<E> andThen(final FailableDoubleUnaryOperator<E> failableDoubleUnaryOperator) {
        Objects.requireNonNull(failableDoubleUnaryOperator);
        return new FailableDoubleUnaryOperator() { // from class: org.apache.commons.lang3.function.FailableDoubleUnaryOperator$$ExternalSyntheticLambda3
            @Override // org.apache.commons.lang3.function.FailableDoubleUnaryOperator
            public final double applyAsDouble(double d) {
                return failableDoubleUnaryOperator.applyAsDouble(this.f$0.applyAsDouble(d));
            }
        };
    }

    default FailableDoubleUnaryOperator<E> compose(final FailableDoubleUnaryOperator<E> failableDoubleUnaryOperator) {
        Objects.requireNonNull(failableDoubleUnaryOperator);
        return new FailableDoubleUnaryOperator() { // from class: org.apache.commons.lang3.function.FailableDoubleUnaryOperator$$ExternalSyntheticLambda2
            @Override // org.apache.commons.lang3.function.FailableDoubleUnaryOperator
            public final double applyAsDouble(double d) {
                return this.f$0.applyAsDouble(failableDoubleUnaryOperator.applyAsDouble(d));
            }
        };
    }
}
