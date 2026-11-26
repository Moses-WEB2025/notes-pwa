package org.apache.commons.lang3.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.commons.lang3.exception.UncheckedInterruptedException;

/* JADX INFO: loaded from: classes6.dex */
final class UncheckedFutureImpl<V> extends AbstractFutureProxy<V> implements UncheckedFuture<V> {
    UncheckedFutureImpl(Future<V> future) {
        super(future);
    }

    @Override // org.apache.commons.lang3.concurrent.AbstractFutureProxy, java.util.concurrent.Future
    public V get() {
        try {
            return (V) super.get();
        } catch (InterruptedException e) {
            throw new UncheckedInterruptedException(e);
        } catch (ExecutionException e2) {
            throw new UncheckedExecutionException(e2);
        }
    }

    @Override // org.apache.commons.lang3.concurrent.AbstractFutureProxy, java.util.concurrent.Future
    public V get(long j, TimeUnit timeUnit) {
        try {
            return (V) super.get(j, timeUnit);
        } catch (InterruptedException e) {
            throw new UncheckedInterruptedException(e);
        } catch (ExecutionException e2) {
            throw new UncheckedExecutionException(e2);
        } catch (TimeoutException e3) {
            throw new UncheckedTimeoutException(e3);
        }
    }
}
