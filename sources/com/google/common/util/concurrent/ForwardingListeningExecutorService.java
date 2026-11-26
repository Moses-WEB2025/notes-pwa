package com.google.common.util.concurrent;

import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
public abstract class ForwardingListeningExecutorService extends ForwardingExecutorService implements ListeningExecutorService {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.common.util.concurrent.ForwardingExecutorService, com.google.common.collect.ForwardingObject
    public abstract ListeningExecutorService delegate();

    protected ForwardingListeningExecutorService() {
    }

    @Override // com.google.common.util.concurrent.ForwardingExecutorService, java.util.concurrent.ExecutorService
    public <T> ListenableFuture<T> submit(Callable<T> task) {
        return delegate().submit((Callable) task);
    }

    @Override // com.google.common.util.concurrent.ForwardingExecutorService, java.util.concurrent.ExecutorService
    public ListenableFuture<?> submit(Runnable task) {
        return delegate().submit(task);
    }

    @Override // com.google.common.util.concurrent.ForwardingExecutorService, java.util.concurrent.ExecutorService
    public <T> ListenableFuture<T> submit(Runnable task, @ParametricNullness T result) {
        return delegate().submit(task, (Object) result);
    }
}
