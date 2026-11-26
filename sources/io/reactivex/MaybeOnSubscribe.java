package io.reactivex;

/* JADX INFO: loaded from: classes4.dex */
public interface MaybeOnSubscribe<T> {
    void subscribe(MaybeEmitter<T> maybeEmitter) throws Exception;
}
