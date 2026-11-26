package io.reactivex.internal.fuseable;

/* JADX INFO: loaded from: classes.dex */
public interface SimpleQueue<T> {
    void clear();

    boolean isEmpty();

    boolean offer(T t);

    boolean offer(T t, T t2);

    T poll() throws Exception;
}
