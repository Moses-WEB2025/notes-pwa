package org.apache.commons.lang3.function;

import java.lang.Throwable;
import java.util.Objects;

/* JADX INFO: loaded from: classes6.dex */
@FunctionalInterface
public interface FailableIntConsumer<E extends Throwable> {
    public static final FailableIntConsumer NOP = new FailableIntConsumer() { // from class: org.apache.commons.lang3.function.FailableIntConsumer$$ExternalSyntheticLambda1
        @Override // org.apache.commons.lang3.function.FailableIntConsumer
        public final void accept(int i) throws Throwable {
            FailableIntConsumer.lambda$static$0(i);
        }
    };

    static /* synthetic */ void lambda$static$0(int i) throws Throwable {
    }

    void accept(int i) throws Throwable;

    static <E extends Throwable> FailableIntConsumer<E> nop() {
        return NOP;
    }

    default FailableIntConsumer<E> andThen(final FailableIntConsumer<E> failableIntConsumer) {
        Objects.requireNonNull(failableIntConsumer);
        return new FailableIntConsumer() { // from class: org.apache.commons.lang3.function.FailableIntConsumer$$ExternalSyntheticLambda0
            @Override // org.apache.commons.lang3.function.FailableIntConsumer
            public final void accept(int i) throws Throwable {
                FailableIntConsumer.lambda$andThen$1(this.f$0, failableIntConsumer, i);
            }
        };
    }

    static /* synthetic */ void lambda$andThen$1(FailableIntConsumer _this, FailableIntConsumer failableIntConsumer, int i) throws Throwable {
        _this.accept(i);
        failableIntConsumer.accept(i);
    }
}
