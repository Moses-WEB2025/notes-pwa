package org.apache.commons.lang3.concurrent;

import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableSupplier;

/* JADX INFO: loaded from: classes6.dex */
public class LazyInitializer<T> extends AbstractConcurrentInitializer<T, ConcurrentException> {
    private static final Object NO_INIT = new Object();
    private volatile T object;

    public static class Builder<I extends LazyInitializer<T>, T> extends AbstractConcurrentInitializer.AbstractBuilder<I, T, Builder<I, T>, ConcurrentException> {
        @Override // org.apache.commons.lang3.function.FailableSupplier
        public I get() {
            return (I) new LazyInitializer(getInitializer(), getCloser());
        }
    }

    public static <T> Builder<LazyInitializer<T>, T> builder() {
        return new Builder<>();
    }

    public LazyInitializer() {
        this.object = (T) NO_INIT;
    }

    private LazyInitializer(FailableSupplier<T, ConcurrentException> failableSupplier, FailableConsumer<T, ConcurrentException> failableConsumer) {
        super(failableSupplier, failableConsumer);
        this.object = (T) NO_INIT;
    }

    @Override // org.apache.commons.lang3.function.FailableSupplier
    public T get() throws ConcurrentException {
        T tInitialize;
        T t = this.object;
        Object obj = NO_INIT;
        if (t != obj) {
            return t;
        }
        synchronized (this) {
            tInitialize = this.object;
            if (tInitialize == obj) {
                tInitialize = initialize();
                this.object = tInitialize;
            }
        }
        return tInitialize;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.apache.commons.lang3.concurrent.AbstractConcurrentInitializer
    public ConcurrentException getTypedException(Exception exc) {
        return new ConcurrentException(exc);
    }

    @Override // org.apache.commons.lang3.concurrent.AbstractConcurrentInitializer
    public boolean isInitialized() {
        return this.object != NO_INIT;
    }
}
