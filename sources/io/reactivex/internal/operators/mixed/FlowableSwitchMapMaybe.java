package io.reactivex.internal.operators.mixed;

import androidx.lifecycle.AtomicReference$$ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes5.dex */
public final class FlowableSwitchMapMaybe<T, R> extends Flowable<R> {
    final boolean delayErrors;
    final Function<? super T, ? extends MaybeSource<? extends R>> mapper;
    final Flowable<T> source;

    public FlowableSwitchMapMaybe(Flowable<T> flowable, Function<? super T, ? extends MaybeSource<? extends R>> function, boolean z) {
        this.source = flowable;
        this.mapper = function;
        this.delayErrors = z;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super R> subscriber) {
        this.source.subscribe((FlowableSubscriber) new SwitchMapMaybeSubscriber(subscriber, this.mapper, this.delayErrors));
    }

    static final class SwitchMapMaybeSubscriber<T, R> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        static final SwitchMapMaybeObserver<Object> INNER_DISPOSED = new SwitchMapMaybeObserver<>(null);
        private static final long serialVersionUID = -5402190102429853762L;
        volatile boolean cancelled;
        final boolean delayErrors;
        volatile boolean done;
        final Subscriber<? super R> downstream;
        long emitted;
        final Function<? super T, ? extends MaybeSource<? extends R>> mapper;
        Subscription upstream;
        final AtomicThrowable errors = new AtomicThrowable();
        final AtomicLong requested = new AtomicLong();
        final AtomicReference<SwitchMapMaybeObserver<R>> inner = new AtomicReference<>();

        SwitchMapMaybeSubscriber(Subscriber<? super R> subscriber, Function<? super T, ? extends MaybeSource<? extends R>> function, boolean z) {
            this.downstream = subscriber;
            this.mapper = function;
            this.delayErrors = z;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            SwitchMapMaybeObserver<R> switchMapMaybeObserver;
            SwitchMapMaybeObserver<R> switchMapMaybeObserver2 = this.inner.get();
            if (switchMapMaybeObserver2 != null) {
                switchMapMaybeObserver2.dispose();
            }
            try {
                MaybeSource maybeSource = (MaybeSource) ObjectHelper.requireNonNull(this.mapper.apply(t), "The mapper returned a null MaybeSource");
                SwitchMapMaybeObserver switchMapMaybeObserver3 = new SwitchMapMaybeObserver(this);
                do {
                    switchMapMaybeObserver = this.inner.get();
                    if (switchMapMaybeObserver == INNER_DISPOSED) {
                        return;
                    }
                } while (!AtomicReference$$ExternalSyntheticBackportWithForwarding0.m(this.inner, switchMapMaybeObserver, switchMapMaybeObserver3));
                maybeSource.subscribe(switchMapMaybeObserver3);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.upstream.cancel();
                this.inner.getAndSet((SwitchMapMaybeObserver<R>) INNER_DISPOSED);
                onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.errors.addThrowable(th)) {
                if (!this.delayErrors) {
                    disposeInner();
                }
                this.done = true;
                drain();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            drain();
        }

        void disposeInner() {
            AtomicReference<SwitchMapMaybeObserver<R>> atomicReference = this.inner;
            SwitchMapMaybeObserver<Object> switchMapMaybeObserver = INNER_DISPOSED;
            SwitchMapMaybeObserver<R> andSet = atomicReference.getAndSet((SwitchMapMaybeObserver<R>) switchMapMaybeObserver);
            if (andSet == null || andSet == switchMapMaybeObserver) {
                return;
            }
            andSet.dispose();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            BackpressureHelper.add(this.requested, j);
            drain();
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            this.upstream.cancel();
            disposeInner();
        }

        void innerError(SwitchMapMaybeObserver<R> switchMapMaybeObserver, Throwable th) {
            if (AtomicReference$$ExternalSyntheticBackportWithForwarding0.m(this.inner, switchMapMaybeObserver, null) && this.errors.addThrowable(th)) {
                if (!this.delayErrors) {
                    this.upstream.cancel();
                    disposeInner();
                }
                drain();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        void innerComplete(SwitchMapMaybeObserver<R> switchMapMaybeObserver) {
            if (AtomicReference$$ExternalSyntheticBackportWithForwarding0.m(this.inner, switchMapMaybeObserver, null)) {
                drain();
            }
        }

        void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            Subscriber<? super R> subscriber = this.downstream;
            AtomicThrowable atomicThrowable = this.errors;
            AtomicReference<SwitchMapMaybeObserver<R>> atomicReference = this.inner;
            AtomicLong atomicLong = this.requested;
            long j = this.emitted;
            int iAddAndGet = 1;
            while (!this.cancelled) {
                if (atomicThrowable.get() != null && !this.delayErrors) {
                    subscriber.onError(atomicThrowable.terminate());
                    return;
                }
                boolean z = this.done;
                SwitchMapMaybeObserver<R> switchMapMaybeObserver = atomicReference.get();
                boolean z2 = switchMapMaybeObserver == null;
                if (z && z2) {
                    Throwable thTerminate = atomicThrowable.terminate();
                    if (thTerminate != null) {
                        subscriber.onError(thTerminate);
                        return;
                    } else {
                        subscriber.onComplete();
                        return;
                    }
                }
                if (!z2 && switchMapMaybeObserver.item != null && j != atomicLong.get()) {
                    AtomicReference$$ExternalSyntheticBackportWithForwarding0.m(atomicReference, switchMapMaybeObserver, null);
                    subscriber.onNext(switchMapMaybeObserver.item);
                    j++;
                } else {
                    this.emitted = j;
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                }
            }
        }

        static final class SwitchMapMaybeObserver<R> extends AtomicReference<Disposable> implements MaybeObserver<R> {
            private static final long serialVersionUID = 8042919737683345351L;
            volatile R item;
            final SwitchMapMaybeSubscriber<?, R> parent;

            SwitchMapMaybeObserver(SwitchMapMaybeSubscriber<?, R> switchMapMaybeSubscriber) {
                this.parent = switchMapMaybeSubscriber;
            }

            @Override // io.reactivex.MaybeObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.MaybeObserver
            public void onSuccess(R r) {
                this.item = r;
                this.parent.drain();
            }

            @Override // io.reactivex.MaybeObserver
            public void onError(Throwable th) {
                this.parent.innerError(this, th);
            }

            @Override // io.reactivex.MaybeObserver
            public void onComplete() {
                this.parent.innerComplete(this);
            }

            void dispose() {
                DisposableHelper.dispose(this);
            }
        }
    }
}
