package com.miui.common.tool;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public abstract class RxMultiAsyncTask<T> {
    Disposable disposable;
    private final Scheduler mBackScheduler;
    protected List<Observable<T>> mObservables = new ArrayList();
    private final Scheduler mPostScheduler;
    private Observable<T> mTask;

    protected abstract T doInBackground();

    protected void onError(Throwable th) {
    }

    protected abstract void onPost(T t);

    public RxMultiAsyncTask(Scheduler scheduler, Scheduler scheduler2) {
        this.mBackScheduler = scheduler;
        this.mPostScheduler = scheduler2;
        com.miui.notes.base.utils.Logger.INSTANCE.d("RxMultiAsyncTask", "new RxMultiAsyncTask " + this);
    }

    protected void onPrepare(List<Observable<T>> list) {
        this.mTask = Observable.merge(list).subscribeOn(this.mBackScheduler).observeOn(this.mPostScheduler);
    }

    public void execute() {
        Observable<T> observable = this.mTask;
        if (observable == null) {
            return;
        }
        observable.subscribe((Observer<? super T>) new Observer<T>() { // from class: com.miui.common.tool.RxMultiAsyncTask.1
            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                RxMultiAsyncTask.this.disposable = disposable;
            }

            @Override // io.reactivex.Observer
            public void onNext(T t) {
                if (RxMultiAsyncTask.this.disposable.isDisposed()) {
                    return;
                }
                RxMultiAsyncTask.this.onPost(t);
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
                if (RxMultiAsyncTask.this.disposable.isDisposed()) {
                    return;
                }
                RxMultiAsyncTask.this.disposable.dispose();
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                if (RxMultiAsyncTask.this.disposable.isDisposed()) {
                    return;
                }
                RxMultiAsyncTask.this.disposable.dispose();
            }
        });
    }

    public void cancel() {
        com.miui.notes.base.utils.Logger.INSTANCE.d("RxMultiAsyncTask", "cancel:" + this);
        Disposable disposable = this.disposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.disposable.dispose();
    }
}
