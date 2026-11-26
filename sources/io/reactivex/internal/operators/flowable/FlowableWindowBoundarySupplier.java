package io.reactivex.internal.operators.flowable;

import androidx.lifecycle.AtomicReference$$ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.processors.UnicastProcessor;
import io.reactivex.subscribers.DisposableSubscriber;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes5.dex */
public final class FlowableWindowBoundarySupplier<T, B> extends AbstractFlowableWithUpstream<T, Flowable<T>> {
    final int capacityHint;
    final Callable<? extends Publisher<B>> other;

    public FlowableWindowBoundarySupplier(Flowable<T> flowable, Callable<? extends Publisher<B>> callable, int i) {
        super(flowable);
        this.other = callable;
        this.capacityHint = i;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super Flowable<T>> subscriber) {
        this.source.subscribe((FlowableSubscriber) new WindowBoundaryMainSubscriber(subscriber, this.capacityHint, this.other));
    }

    static final class WindowBoundaryMainSubscriber<T, B> extends AtomicInteger implements FlowableSubscriber<T>, Subscription, Runnable {
        static final WindowBoundaryInnerSubscriber<Object, Object> BOUNDARY_DISPOSED = new WindowBoundaryInnerSubscriber<>(null);
        static final Object NEXT_WINDOW = new Object();
        private static final long serialVersionUID = 2233020065421370272L;
        final int capacityHint;
        volatile boolean done;
        final Subscriber<? super Flowable<T>> downstream;
        long emitted;
        final Callable<? extends Publisher<B>> other;
        Subscription upstream;
        UnicastProcessor<T> window;
        final AtomicReference<WindowBoundaryInnerSubscriber<T, B>> boundarySubscriber = new AtomicReference<>();
        final AtomicInteger windows = new AtomicInteger(1);
        final MpscLinkedQueue<Object> queue = new MpscLinkedQueue<>();
        final AtomicThrowable errors = new AtomicThrowable();
        final AtomicBoolean stopWindows = new AtomicBoolean();
        final AtomicLong requested = new AtomicLong();

        WindowBoundaryMainSubscriber(Subscriber<? super Flowable<T>> subscriber, int i, Callable<? extends Publisher<B>> callable) {
            this.downstream = subscriber;
            this.capacityHint = i;
            this.other = callable;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                this.queue.offer(NEXT_WINDOW);
                drain();
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.queue.offer(t);
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            disposeBoundary();
            if (this.errors.addThrowable(th)) {
                this.done = true;
                drain();
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            disposeBoundary();
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.stopWindows.compareAndSet(false, true)) {
                disposeBoundary();
                if (this.windows.decrementAndGet() == 0) {
                    this.upstream.cancel();
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            BackpressureHelper.add(this.requested, j);
        }

        void disposeBoundary() {
            AtomicReference<WindowBoundaryInnerSubscriber<T, B>> atomicReference = this.boundarySubscriber;
            WindowBoundaryInnerSubscriber<Object, Object> windowBoundaryInnerSubscriber = BOUNDARY_DISPOSED;
            WindowBoundaryInnerSubscriber<T, B> andSet = atomicReference.getAndSet((WindowBoundaryInnerSubscriber<T, B>) windowBoundaryInnerSubscriber);
            if (andSet == null || andSet == windowBoundaryInnerSubscriber) {
                return;
            }
            andSet.dispose();
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.windows.decrementAndGet() == 0) {
                this.upstream.cancel();
            }
        }

        void innerNext(WindowBoundaryInnerSubscriber<T, B> windowBoundaryInnerSubscriber) {
            AtomicReference$$ExternalSyntheticBackportWithForwarding0.m(this.boundarySubscriber, windowBoundaryInnerSubscriber, null);
            this.queue.offer(NEXT_WINDOW);
            drain();
        }

        void innerError(Throwable th) {
            this.upstream.cancel();
            if (this.errors.addThrowable(th)) {
                this.done = true;
                drain();
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        void innerComplete() {
            this.upstream.cancel();
            this.done = true;
            drain();
        }

        void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            Subscriber<? super Flowable<T>> subscriber = this.downstream;
            MpscLinkedQueue<Object> mpscLinkedQueue = this.queue;
            AtomicThrowable atomicThrowable = this.errors;
            long j = this.emitted;
            int iAddAndGet = 1;
            while (this.windows.get() != 0) {
                UnicastProcessor<T> unicastProcessor = this.window;
                boolean z = this.done;
                if (z && atomicThrowable.get() != null) {
                    mpscLinkedQueue.clear();
                    Throwable thTerminate = atomicThrowable.terminate();
                    if (unicastProcessor != null) {
                        this.window = null;
                        unicastProcessor.onError(thTerminate);
                    }
                    subscriber.onError(thTerminate);
                    return;
                }
                Object objPoll = mpscLinkedQueue.poll();
                boolean z2 = objPoll == null;
                if (z && z2) {
                    Throwable thTerminate2 = atomicThrowable.terminate();
                    if (thTerminate2 == null) {
                        if (unicastProcessor != null) {
                            this.window = null;
                            unicastProcessor.onComplete();
                        }
                        subscriber.onComplete();
                        return;
                    }
                    if (unicastProcessor != null) {
                        this.window = null;
                        unicastProcessor.onError(thTerminate2);
                    }
                    subscriber.onError(thTerminate2);
                    return;
                }
                if (!z2) {
                    if (objPoll != NEXT_WINDOW) {
                        unicastProcessor.onNext((T) objPoll);
                    } else {
                        if (unicastProcessor != null) {
                            this.window = null;
                            unicastProcessor.onComplete();
                        }
                        if (!this.stopWindows.get()) {
                            if (j != this.requested.get()) {
                                UnicastProcessor<T> unicastProcessorCreate = UnicastProcessor.create(this.capacityHint, this);
                                this.window = unicastProcessorCreate;
                                this.windows.getAndIncrement();
                                try {
                                    Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.other.call(), "The other Callable returned a null Publisher");
                                    WindowBoundaryInnerSubscriber windowBoundaryInnerSubscriber = new WindowBoundaryInnerSubscriber(this);
                                    if (AtomicReference$$ExternalSyntheticBackportWithForwarding0.m(this.boundarySubscriber, null, windowBoundaryInnerSubscriber)) {
                                        publisher.subscribe(windowBoundaryInnerSubscriber);
                                        j++;
                                        subscriber.onNext(unicastProcessorCreate);
                                    }
                                } catch (Throwable th) {
                                    Exceptions.throwIfFatal(th);
                                    atomicThrowable.addThrowable(th);
                                    this.done = true;
                                }
                            } else {
                                this.upstream.cancel();
                                disposeBoundary();
                                atomicThrowable.addThrowable(new MissingBackpressureException("Could not deliver a window due to lack of requests"));
                                this.done = true;
                            }
                        }
                    }
                } else {
                    this.emitted = j;
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                }
            }
            mpscLinkedQueue.clear();
            this.window = null;
        }
    }

    static final class WindowBoundaryInnerSubscriber<T, B> extends DisposableSubscriber<B> {
        boolean done;
        final WindowBoundaryMainSubscriber<T, B> parent;

        WindowBoundaryInnerSubscriber(WindowBoundaryMainSubscriber<T, B> windowBoundaryMainSubscriber) {
            this.parent = windowBoundaryMainSubscriber;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(B b) {
            if (this.done) {
                return;
            }
            this.done = true;
            dispose();
            this.parent.innerNext(this);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
            } else {
                this.done = true;
                this.parent.innerError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.parent.innerComplete();
        }
    }
}
