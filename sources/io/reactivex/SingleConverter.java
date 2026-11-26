package io.reactivex;

/* JADX INFO: loaded from: classes4.dex */
public interface SingleConverter<T, R> {
    R apply(Single<T> single);
}
