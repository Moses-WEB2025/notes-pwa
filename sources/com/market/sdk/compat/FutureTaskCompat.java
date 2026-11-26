package com.market.sdk.compat;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes2.dex */
public class FutureTaskCompat<V> extends FutureTask<V> {
    private static final String TAG = "FutureTaskCompat";

    public FutureTaskCompat(Callable<V> callable) {
        super(callable);
    }

    public FutureTaskCompat() {
        super(new Callable<V>() { // from class: com.market.sdk.compat.FutureTaskCompat.1
            @Override // java.util.concurrent.Callable
            public V call() throws Exception {
                throw new IllegalStateException("this should never be called");
            }
        });
    }

    @Override // java.util.concurrent.FutureTask
    public void set(V v) {
        super.set(v);
    }

    public V get(long j, V v) {
        try {
            return (V) super.get(j, TimeUnit.MILLISECONDS);
        } catch (Exception unused) {
            return v;
        }
    }

    @Override // java.util.concurrent.FutureTask, java.util.concurrent.Future
    public V get() {
        try {
            return (V) super.get();
        } catch (Exception unused) {
            return null;
        }
    }
}
