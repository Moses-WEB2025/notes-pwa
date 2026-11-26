package org.apache.commons.lang3.concurrent;

import androidx.lifecycle.AtomicReference$$ExternalSyntheticBackportWithForwarding0;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableSupplier;

/* JADX INFO: loaded from: classes6.dex */
public class AtomicInitializer<T> extends AbstractConcurrentInitializer<T, ConcurrentException> {
    private static final Object NO_INIT = new Object();
    private final AtomicReference<T> reference;

    public static class Builder<I extends AtomicInitializer<T>, T> extends AbstractConcurrentInitializer.AbstractBuilder<I, T, Builder<I, T>, ConcurrentException> {
        @Override // org.apache.commons.lang3.function.FailableSupplier
        public I get() {
            return (I) new AtomicInitializer(getInitializer(), getCloser());
        }
    }

    public static <T> Builder<AtomicInitializer<T>, T> builder() {
        return new Builder<>();
    }

    public AtomicInitializer() {
        this.reference = new AtomicReference<>(getNoInit());
    }

    private AtomicInitializer(FailableSupplier<T, ConcurrentException> failableSupplier, FailableConsumer<T, ConcurrentException> failableConsumer) {
        super(failableSupplier, failableConsumer);
        this.reference = new AtomicReference<>(getNoInit());
    }

    @Override // org.apache.commons.lang3.function.FailableSupplier
    public T get() throws ConcurrentException {
        T tInitialize = this.reference.get();
        if (tInitialize == getNoInit()) {
            tInitialize = initialize();
            if (!AtomicReference$$ExternalSyntheticBackportWithForwarding0.m(this.reference, getNoInit(), tInitialize)) {
                return this.reference.get();
            }
        }
        return tInitialize;
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
