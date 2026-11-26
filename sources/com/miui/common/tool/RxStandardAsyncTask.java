package com.miui.common.tool;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/* JADX INFO: loaded from: classes2.dex */
public abstract class RxStandardAsyncTask<T> {
    private Disposable disposable;
    private final Scheduler mBackScheduler;
    private CompositeDisposable mCompositeDisposable;
    private final Scheduler mPostScheduler;
    private Observable<T> mTask = Observable.create(new ObservableOnSubscribe() { // from class: com.miui.common.tool.RxStandardAsyncTask$$ExternalSyntheticLambda0
        @Override // io.reactivex.ObservableOnSubscribe
        public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
            this.f$0.lambda$new$0(observableEmitter);
        }
    });

    protected abstract T doInBackground();

    protected abstract void onPost(T t);

    public RxStandardAsyncTask(CompositeDisposable compositeDisposable, Scheduler scheduler, Scheduler scheduler2) {
        this.mCompositeDisposable = compositeDisposable;
        this.mBackScheduler = scheduler;
        this.mPostScheduler = scheduler2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(ObservableEmitter observableEmitter) throws Exception {
        T tDoInBackground = doInBackground();
        if (tDoInBackground != null) {
            observableEmitter.onNext(tDoInBackground);
            observableEmitter.onComplete();
        }
    }

    protected void onError(Throwable th) {
        Logger.INSTANCE.d("RxStandardAsyncTask", "onError " + th.toString());
    }

    public void cancel() {
        Disposable disposable = this.disposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.disposable.dispose();
    }

    public void cancelAll() {
        CompositeDisposable compositeDisposable = this.mCompositeDisposable;
        if (compositeDisposable == null || compositeDisposable.isDisposed()) {
            return;
        }
        this.mCompositeDisposable.dispose();
    }

    public void execute() {
        Logger.INSTANCE.d("RXStandardAsyncTask", "execute ");
        this.mTask.subscribeOn(this.mBackScheduler).observeOn(this.mPostScheduler).subscribe(new Observer<T>() { // from class: com.miui.common.tool.RxStandardAsyncTask.1
            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                RxStandardAsyncTask.this.disposable = disposable;
                if (RxStandardAsyncTask.this.mCompositeDisposable != null) {
                    Logger.INSTANCE.d("RxStandardAsyncTask", "onSubscribe");
                    RxStandardAsyncTask.this.mCompositeDisposable.add(RxStandardAsyncTask.this.disposable);
                }
            }

            @Override // io.reactivex.Observer
            public void onNext(T t) {
                if (RxStandardAsyncTask.this.disposable.isDisposed()) {
                    return;
                }
                Logger.INSTANCE.d("RxStandardAsyncTask", "onNext");
                RxStandardAsyncTask.this.onPost(t);
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
                RxStandardAsyncTask.this.onError(th);
                if (!RxStandardAsyncTask.this.disposable.isDisposed()) {
                    RxStandardAsyncTask.this.disposable.dispose();
                }
                if (RxStandardAsyncTask.this.mCompositeDisposable != null) {
                    RxStandardAsyncTask.this.mCompositeDisposable.remove(RxStandardAsyncTask.this.disposable);
                }
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                if (!RxStandardAsyncTask.this.disposable.isDisposed()) {
                    RxStandardAsyncTask.this.disposable.dispose();
                }
                if (RxStandardAsyncTask.this.mCompositeDisposable != null) {
                    RxStandardAsyncTask.this.mCompositeDisposable.remove(RxStandardAsyncTask.this.disposable);
                }
            }
        });
    }
}
