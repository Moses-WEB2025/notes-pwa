package com.google.common.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
public interface ListeningScheduledExecutorService extends ScheduledExecutorService, ListeningExecutorService {
    @Override // java.util.concurrent.ScheduledExecutorService
    ListenableScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit);

    @Override // java.util.concurrent.ScheduledExecutorService
    <V> ListenableScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit);

    @Override // java.util.concurrent.ScheduledExecutorService
    ListenableScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit);

    @Override // java.util.concurrent.ScheduledExecutorService
    ListenableScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit);
}
