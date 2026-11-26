package org.apache.commons.lang3.concurrent;

import androidx.lifecycle.AtomicReference$$ExternalSyntheticBackportWithForwarding0;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableSupplier;

/* JADX INFO: loaded from: classes6.dex */
public class AtomicSafeInitializer<T> extends AbstractConcurrentInitializer<T, ConcurrentException> {
    private static final Object NO_INIT = new Object();
    private final AtomicReference<AtomicSafeInitializer<T>> factory;
    private final AtomicReference<T> reference;

    public static class Builder<I extends AtomicSafeInitializer<T>, T> extends AbstractConcurrentInitializer.AbstractBuilder<I, T, Builder<I, T>, ConcurrentException> {
        @Override // org.apache.commons.lang3.function.FailableSupplier
        public I get() {
            return (I) new AtomicSafeInitializer(getInitializer(), getCloser());
        }
    }

    public static <T> Builder<AtomicSafeInitializer<T>, T> builder() {
        return new Builder<>();
    }

    public AtomicSafeInitializer() {
        this.factory = new AtomicReference<>();
        this.reference = new AtomicReference<>(getNoInit());
    }

    private AtomicSafeInitializer(FailableSupplier<T, ConcurrentException> failableSupplier, FailableConsumer<T, ConcurrentException> failableConsumer) {
        super(failableSupplier, failableConsumer);
        this.factory = new AtomicReference<>();
        this.reference = new AtomicReference<>(getNoInit());
    }

    @Override // org.apache.commons.lang3.function.FailableSupplier
    public final T get() throws ConcurrentException {
        while (true) {
            T t = this.reference.get();
            if (t != getNoInit()) {
                return t;
            }
            if (AtomicReference$$ExternalSyntheticBackportWithForwarding0.m(this.factory, null, this)) {
                this.reference.set(initialize());
            }
        }
    }

    private T getNoInit() {
        return (T) NO_INIT;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.apache.commons.lang3.concurrent.AbstractConcurrentInitializer
    public ConcurrentException getTypedException(Exception exc) {
        return new ConcurrentException(exc);
    }

    @Override // org.apache.commons.lang3.concurrent.AbstractConcurrentInitializer
    public boolean isInitialized() {
        return this.reference.get() != NO_INIT;
    }
}
