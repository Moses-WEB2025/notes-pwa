package io.reactivex;

/* JADX INFO: loaded from: classes4.dex */
public interface MaybeTransformer<Upstream, Downstream> {
    MaybeSource<Downstream> apply(Maybe<Upstream> maybe);
}
