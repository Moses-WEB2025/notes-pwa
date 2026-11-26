package io.reactivex.internal.operators.observable;

import androidx.lifecycle.AtomicReference$$ExternalSyntheticBackportWithForwarding0;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.ResettableConnectable;
import io.reactivex.internal.fuseable.HasUpstreamObservableSource;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.observables.ConnectableObservable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes5.dex */
public final class ObservablePublishAlt<T> extends ConnectableObservable<T> implements HasUpstreamObservableSource<T>, ResettableConnectable {
    final AtomicReference<PublishConnection<T>> current = new AtomicReference<>();
    final ObservableSource<T> source;

    public ObservablePublishAlt(ObservableSource<T> observableSource) {
        this.source = observableSource;
    }

    @Override // io.reactivex.observables.ConnectableObservable
    public void connect(Consumer<? super Disposable> consumer) {
        PublishConnection<T> publishConnection;
        while (true) {
            publishConnection = this.current.get();
            if (publishConnection != null && !publishConnection.isDisposed()) {
                break;
            }
            PublishConnection<T> publishConnection2 = new PublishConnection<>(this.current);
            if (AtomicReference$$ExternalSyntheticBackportWithForwarding0.m(this.current, publishConnection, publishConnection2)) {
                publishConnection = publishConnection2;
                break;
            }
        }
        boolean z = false;
        if (!publishConnection.connect.get() && publishConnection.connect.compareAndSet(false, true)) {
            z = true;
        }
        try {
            consumer.accept(publishConnection);
            if (z) {
                this.source.subscribe(publishConnection);
            }
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super T> observer) {
        PublishConnection<T> publishConnection;
        while (true) {
            publishConnection = this.current.get();
            if (publishConnection != null) {
                break;
            }
            PublishConnection<T> publishConnection2 = new PublishConnection<>(this.current);
            if (AtomicReference$$ExternalSyntheticBackportWithForwarding0.m(this.current, publishConnection, publishConnection2)) {
                publishConnection = publishConnection2;
                break;
            }
        }
        InnerDisposable<T> innerDisposable = new InnerDisposable<>(observer, publishConnection);
        observer.onSubscribe(innerDisposable);
        if (publishConnection.add(innerDisposable)) {
            if (innerDisposable.isDisposed()) {
                publishConnection.remove(innerDisposable);
            }
        } else {
            Throwable th = publishConnection.error;
            if (th != null) {
                observer.onError(th);
            } else {
                observer.onComplete();
            }
        }
    }

    @Override // io.reactivex.internal.disposables.ResettableConnectable
    public void resetIf(Disposable disposable) {
        AtomicReference$$ExternalSyntheticBackportWithForwarding0.m(this.current, (PublishConnection) disposable, null);
    }

    @Override // io.reactivex.internal.fuseable.HasUpstreamObservableSource
    public ObservableSource<T> source() {
        return this.source;
    }

    static final class PublishConnection<T> extends AtomicReference<InnerDisposable<T>[]> implements Observer<T>, Disposable {
        static final InnerDisposable[] EMPTY = new InnerDisposable[0];
        static final InnerDisposable[] TERMINATED = new InnerDisposable[0];
        private static final long serialVersionUID = -3251430252873581268L;
        final AtomicReference<PublishConnection<T>> current;
        Throwable error;
        final AtomicBoolean connect = new AtomicBoolean();
        final AtomicReference<Disposable> upstream = new AtomicReference<>();

        PublishConnection(AtomicReference<PublishConnection<T>> atomicReference) {
            this.current = atomicReference;
            lazySet(EMPTY);
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            getAndSet(TERMINATED);
            AtomicReference$$ExternalSyntheticBackportWithForwarding0.m(this.current, this, null);
            DisposableHelper.dispose(this.upstream);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return get() == TERMINATED;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this.upstream, disposable);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            for (InnerDisposable<T> innerDisposable : get()) {
                innerDisposable.downstream.onNext(t);
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.error = th;
            this.upstream.lazySet(DisposableHelper.DISPOSED);
            for (InnerDisposable<T> innerDisposable : getAndSet(TERMINATED)) {
                innerDisposable.downstream.onError(th);
            }
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.upstream.lazySet(DisposableHelper.DISPOSED);
            for (InnerDisposable<T> innerDisposable : getAndSet(TERMINATED)) {
                innerDisposable.downstream.onComplete();
            }
        }

        public boolean add(InnerDisposable<T> innerDisposable) {
            InnerDisposable<T>[] innerDisposableArr;
            InnerDisposable[] innerDisposableArr2;
            do {
                innerDisposableArr = get();
                if (innerDisposableArr == TERMINATED) {
                    return false;
                }
                int length = innerDisposableArr.length;
                innerDisposableArr2 = new InnerDisposable[length + 1];
                System.arraycopy(innerDisposableArr, 0, innerDisposableArr2, 0, length);
                innerDisposableArr2[length] = innerDisposable;
            } while (!compareAndSet(innerDisposableArr, innerDisposableArr2));
            return true;
        }

        public void remove(InnerDisposable<T> innerDisposable) {
            InnerDisposable<T>[] innerDisposableArr;
            InnerDisposable[] innerDisposableArr2;
            do {
                innerDisposableArr = get();
                int length = innerDisposableArr.length;
                if (length == 0) {
                    return;
                }
                int i = 0;
                while (true) {
                    if (i >= length) {
                        i = -1;
                        break;
                    } else if (innerDisposableArr[i] == innerDisposable) {
                        break;
                    } else {
                        i++;
                    }
                }
                if (i < 0) {
                    return;
                }
                innerDisposableArr2 = EMPTY;
                if (length != 1) {
                    innerDisposableArr2 = new InnerDisposable[length - 1];
                    System.arraycopy(innerDisposableArr, 0, innerDisposableArr2, 0, i);
                    System.arraycopy(innerDisposableArr, i + 1, innerDisposableArr2, i, (length - i) - 1);
                }
            } while (!compareAndSet(innerDisposableArr, innerDisposableArr2));
        }
    }

    static final class InnerDisposable<T> extends AtomicReference<PublishConnection<T>> implements Disposable {
        private static final long serialVersionUID = 7463222674719692880L;
        final Observer<? super T> downstream;

        InnerDisposable(Observer<? super T> observer, PublishConnection<T> publishConnection) {
            this.downstream = observer;
            lazySet(publishConnection);
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            PublishConnection<T> andSet = getAndSet(null);
            if (andSet != null) {
                andSet.remove(this);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return get() == null;
        }
    }
}
