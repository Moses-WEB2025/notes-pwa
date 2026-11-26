package org.apache.commons.lang3.function;

import java.lang.Throwable;
import java.util.Objects;

/* JADX INFO: loaded from: classes6.dex */
@FunctionalInterface
public interface FailableDoubleConsumer<E extends Throwable> {
    public static final FailableDoubleConsumer NOP = new FailableDoubleConsumer() { // from class: org.apache.commons.lang3.function.FailableDoubleConsumer$$ExternalSyntheticLambda0
        @Override // org.apache.commons.lang3.function.FailableDoubleConsumer
        public final void accept(double d) throws Throwable {
            FailableDoubleConsumer.lambda$static$0(d);
        }
    };

    static /* synthetic */ void lambda$static$0(double d) throws Throwable {
    }

    void accept(double d) throws Throwable;

    static <E extends Throwable> FailableDoubleConsumer<E> nop() {
        return NOP;
    }

    default FailableDoubleConsumer<E> andThen(final FailableDoubleConsumer<E> failableDoubleConsumer) {
        Objects.requireNonNull(failableDoubleConsumer);
        return new FailableDoubleConsumer() { // from class: org.apache.commons.lang3.function.FailableDoubleConsumer$$ExternalSyntheticLambda1
            @Override // org.apache.commons.lang3.function.FailableDoubleConsumer
            public final void accept(double d) throws Throwable {
                FailableDoubleConsumer.lambda$andThen$1(this.f$0, failableDoubleConsumer, d);
            }
        };
    }

    static /* synthetic */ void lambda$andThen$1(FailableDoubleConsumer _this, FailableDoubleConsumer failableDoubleConsumer, double d) throws Throwable {
        _this.accept(d);
        failableDoubleConsumer.accept(d);
    }
}
