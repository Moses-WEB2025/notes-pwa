package io.reactivex.internal.observers;

import androidx.lifecycle.AtomicReference$$ExternalSyntheticBackportWithForwarding0;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public final class FutureSingleObserver<T> extends CountDownLatch implements SingleObserver<T>, Future<T>, Disposable {
    Throwable error;
    final AtomicReference<Disposable> upstream;
    T value;

    @Override // io.reactivex.disposables.Disposable
    public void dispose() {
    }

    public FutureSingleObserver() {
        super(1);
        this.upstream = new AtomicReference<>();
    }

    @Override // java.util.concurrent.Future
    public boolean cancel(boolean z) {
        Disposable disposable;
        do {
            disposable = this.upstream.get();
            if (disposable == this || disposable == DisposableHelper.DISPOSED) {
                return false;
            }
        } while (!AtomicReference$$ExternalSyntheticBackportWithForwarding0.m(this.upstream, disposable, DisposableHelper.DISPOSED));
        if (disposable != null) {
            disposable.dispose();
        }
        countDown();
        return true;
    }

    @Override // java.util.concurrent.Future
    public boolean isCancelled() {
        return DisposableHelper.isDisposed(this.upstream.get());
    }

    @Override // java.util.concurrent.Future
    public boolean isDone() {
        return getCount() == 0;
    }

    @Override // java.util.concurrent.Future
    public T get() throws ExecutionException, InterruptedException {
        if (getCount() != 0) {
            BlockingHelper.verifyNonBlocking();
            await();
        }
        if (isCancelled()) {
            throw new CancellationException();
        }
        Throwable th = this.error;
        if (th != null) {
            throw new ExecutionException(th);
        }
        return this.value;
    }

    @Override // java.util.concurrent.Future
    public T get(long j, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        if (getCount() != 0) {
            BlockingHelper.verifyNonBlocking();
            if (!await(j, timeUnit)) {
                throw new TimeoutException(ExceptionHelper.timeoutMessage(j, timeUnit));
            }
        }
        if (isCancelled()) {
            throw new CancellationException();
        }
        Throwable th = this.error;
        if (th != null) {
            throw new ExecutionException(th);
        }
        return this.value;
    }

    @Override // io.reactivex.SingleObserver
    public void onSubscribe(Disposable disposable) {
        DisposableHelper.setOnce(this.upstream, disposable);
    }

    @Override // io.reactivex.SingleObserver
    public void onSuccess(T t) {
        Disposable disposable = this.upstream.get();
        if (disposable == DisposableHelper.DISPOSED) {
            return;
        }
        this.value = t;
        AtomicReference$$ExternalSyntheticBackportWithForwarding0.m(this.upstream, disposable, this);
        countDown();
    }

    @Override // io.reactivex.SingleObserver
    public void onError(Throwable th) {
        Disposable disposable;
        do {
            disposable = this.upstream.get();
            if (disposable == DisposableHelper.DISPOSED) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.error = th;
        } while (!AtomicReference$$ExternalSyntheticBackportWithForwarding0.m(this.upstream, disposable, this));
        countDown();
    }

    @Override // io.reactivex.disposables.Disposable
    public boolean isDisposed() {
        return isDone();
    }
}
