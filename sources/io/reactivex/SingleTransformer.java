package io.reactivex;

/* JADX INFO: loaded from: classes4.dex */
public interface SingleTransformer<Upstream, Downstream> {
    SingleSource<Downstream> apply(Single<Upstream> single);
}
