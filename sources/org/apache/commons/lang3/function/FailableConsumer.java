package org.apache.commons.lang3.function;

import java.lang.Throwable;
import java.util.Objects;
import java.util.function.Function;

/* JADX INFO: loaded from: classes6.dex */
@FunctionalInterface
public interface FailableConsumer<T, E extends Throwable> {
    public static final FailableConsumer NOP;

    void accept(T t) throws Throwable;

    static {
        final Function functionIdentity = Function.identity();
        Objects.requireNonNull(functionIdentity);
        NOP = new FailableConsumer() { // from class: org.apache.commons.lang3.function.FailableConsumer$$ExternalSyntheticLambda0
            @Override // org.apache.commons.lang3.function.FailableConsumer
            public final void accept(Object obj) {
                functionIdentity.apply(obj);
            }
        };
    }

    static <T, E extends Throwable> FailableConsumer<T, E> nop() {
        return NOP;
    }

    default FailableConsumer<T, E> andThen(final FailableConsumer<? super T, E> failableConsumer) {
        Objects.requireNonNull(failableConsumer);
        return new FailableConsumer() { // from class: org.apache.commons.lang3.function.FailableConsumer$$ExternalSyntheticLambda1
            @Override // org.apache.commons.lang3.function.FailableConsumer
            public final void accept(Object obj) throws Throwable {
                FailableConsumer.lambda$andThen$0(this.f$0, failableConsumer, obj);
            }
        };
    }

    static /* synthetic */ void lambda$andThen$0(FailableConsumer _this, FailableConsumer failableConsumer, Object obj) throws Throwable {
        _this.accept(obj);
        failableConsumer.accept(obj);
    }
}
