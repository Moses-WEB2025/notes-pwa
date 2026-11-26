package org.apache.commons.lang3.concurrent;

import java.lang.Exception;
import java.util.Objects;
import org.apache.commons.lang3.builder.AbstractSupplier;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableSupplier;

/* JADX INFO: loaded from: classes6.dex */
public abstract class AbstractConcurrentInitializer<T, E extends Exception> implements ConcurrentInitializer<T> {
    private final FailableConsumer<? super T, ? extends Exception> closer;
    private final FailableSupplier<? extends T, ? extends Exception> initializer;

    protected abstract E getTypedException(Exception exc);

    protected abstract boolean isInitialized();

    public static abstract class AbstractBuilder<I extends AbstractConcurrentInitializer<T, E>, T, B extends AbstractBuilder<I, T, B, E>, E extends Exception> extends AbstractSupplier<I, B, E> {
        private FailableConsumer<T, ? extends Exception> closer = FailableConsumer.nop();
        private FailableSupplier<T, ? extends Exception> initializer = FailableSupplier.nul();

        public FailableConsumer<T, ? extends Exception> getCloser() {
            return this.closer;
        }

        public FailableSupplier<T, ? extends Exception> getInitializer() {
            return this.initializer;
        }

        public B setCloser(FailableConsumer<T, ? extends Exception> failableConsumer) {
            if (failableConsumer == null) {
                failableConsumer = FailableConsumer.nop();
            }
            this.closer = failableConsumer;
            return asThis();
        }

        public B setInitializer(FailableSupplier<T, ? extends Exception> failableSupplier) {
            if (failableSupplier == null) {
                failableSupplier = FailableSupplier.nul();
            }
            this.initializer = failableSupplier;
            return asThis();
        }
    }

    public AbstractConcurrentInitializer() {
        this(FailableSupplier.nul(), FailableConsumer.nop());
    }

    AbstractConcurrentInitializer(FailableSupplier<? extends T, ? extends Exception> failableSupplier, FailableConsumer<? super T, ? extends Exception> failableConsumer) {
        this.closer = (FailableConsumer) Objects.requireNonNull(failableConsumer, "closer");
        this.initializer = (FailableSupplier) Objects.requireNonNull(failableSupplier, "initializer");
    }

    public void close() throws Throwable {
        if (isInitialized()) {
            try {
                this.closer.accept(get());
            } catch (Exception e) {
                throw new ConcurrentException(ExceptionUtils.throwUnchecked(e));
            }
        }
    }

    protected T initialize() throws Exception {
        try {
            return this.initializer.get();
        } catch (Exception e) {
            ExceptionUtils.throwUnchecked(e);
            Exception typedException = getTypedException(e);
            if (typedException.getClass().isAssignableFrom(e.getClass())) {
                throw e;
            }
            throw typedException;
        }
    }
}
