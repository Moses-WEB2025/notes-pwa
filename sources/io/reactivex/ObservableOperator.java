package io.reactivex;

/* JADX INFO: loaded from: classes4.dex */
public interface ObservableOperator<Downstream, Upstream> {
    Observer<? super Upstream> apply(Observer<? super Downstream> observer) throws Exception;
}
