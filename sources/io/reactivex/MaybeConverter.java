package io.reactivex;

/* JADX INFO: loaded from: classes4.dex */
public interface MaybeConverter<T, R> {
    R apply(Maybe<T> maybe);
}
