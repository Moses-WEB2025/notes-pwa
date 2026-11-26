package io.reactivex.internal.operators.flowable;

import androidx.lifecycle.AtomicReference$$ExternalSyntheticBackportWithForwarding0;
import defpackage.Hello;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes5.dex */
public final class FlowableSwitchMap<T, R> extends AbstractFlowableWithUpstream<T, R> {
    final int bufferSize;
    final boolean delayErrors;
    final Function<? super T, ? extends Publisher<? extends R>> mapper;

    public FlowableSwitchMap(Flowable<T> flowable, Function<? super T, ? extends Publisher<? extends R>> function, int i, boolean z) {
        super(flowable);
        this.mapper = function;
        this.bufferSize = i;
        this.delayErrors = z;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super R> subscriber) {
        if (FlowableScalarXMap.tryScalarXMapSubscribe(this.source, subscriber, this.mapper)) {
            return;
        }
        this.source.subscribe((FlowableSubscriber) new SwitchMapSubscriber(subscriber, this.mapper, this.bufferSize, this.delayErrors));
    }

    static final class SwitchMapSubscriber<T, R> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        static final SwitchMapInnerSubscriber<Object, Object> CANCELLED;
        private static final long serialVersionUID = -3491074160481096299L;
        final int bufferSize;
        volatile boolean cancelled;
        final boolean delayErrors;
        volatile boolean done;
        final Subscriber<? super R> downstream;
        final Function<? super T, ? extends Publisher<? extends R>> mapper;
        volatile long unique;
        Subscription upstream;
        final AtomicReference<SwitchMapInnerSubscriber<T, R>> active = new AtomicReference<>();
        final AtomicLong requested = new AtomicLong();
        final AtomicThrowable error = new AtomicThrowable();

        static {
            SwitchMapInnerSubscriber<Object, Object> switchMapInnerSubscriber = new SwitchMapInnerSubscriber<>(null, -1L, 1);
            CANCELLED = switchMapInnerSubscriber;
            switchMapInnerSubscriber.cancel();
        }

        SwitchMapSubscriber(Subscriber<? super R> subscriber, Function<? super T, ? extends Publisher<? extends R>> function, int i, boolean z) {
            this.downstream = subscriber;
            this.mapper = function;
            this.bufferSize = i;
            this.delayErrors = z;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            SwitchMapInnerSubscriber<T, R> switchMapInnerSubscriber;
            if (this.done) {
                return;
            }
            long j = this.unique + 1;
            this.unique = j;
            SwitchMapInnerSubscriber<T, R> switchMapInnerSubscriber2 = this.active.get();
            if (switchMapInnerSubscriber2 != null) {
                switchMapInnerSubscriber2.cancel();
            }
            try {
                Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.mapper.apply(t), "The publisher returned is null");
                SwitchMapInnerSubscriber switchMapInnerSubscriber3 = new SwitchMapInnerSubscriber(this, j, this.bufferSize);
                do {
                    switchMapInnerSubscriber = this.active.get();
                    if (switchMapInnerSubscriber == CANCELLED) {
                        return;
                    }
                } while (!AtomicReference$$ExternalSyntheticBackportWithForwarding0.m(this.active, switchMapInnerSubscriber, switchMapInnerSubscriber3));
                publisher.subscribe(switchMapInnerSubscriber3);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.upstream.cancel();
                onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (!this.done && this.error.addThrowable(th)) {
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
            if (this.done) {
                return;
            }
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
                if (this.unique == 0) {
                    this.upstream.request(Long.MAX_VALUE);
                } else {
                    drain();
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.upstream.cancel();
            disposeInner();
        }

        void disposeInner() {
            SwitchMapInnerSubscriber<T, R> andSet;
            SwitchMapInnerSubscriber<T, R> switchMapInnerSubscriber = this.active.get();
            SwitchMapInnerSubscriber<Object, Object> switchMapInnerSubscriber2 = CANCELLED;
            if (switchMapInnerSubscriber == switchMapInnerSubscriber2 || (andSet = this.active.getAndSet((SwitchMapInnerSubscriber<T, R>) switchMapInnerSubscriber2)) == switchMapInnerSubscriber2 || andSet == null) {
                return;
            }
            andSet.cancel();
        }

        void drain() {
            boolean z;
            Hello helloPoll;
            if (getAndIncrement() != 0) {
                return;
            }
            Subscriber<? super R> subscriber = this.downstream;
            int iAddAndGet = 1;
            while (!this.cancelled) {
                if (this.done) {
                    if (this.delayErrors) {
                        if (this.active.get() == null) {
                            if (this.error.get() != null) {
                                subscriber.onError(this.error.terminate());
                                return;
                            } else {
                                subscriber.onComplete();
                                return;
                            }
                        }
                    } else if (this.error.get() != null) {
                        disposeInner();
                        subscriber.onError(this.error.terminate());
                        return;
                    } else if (this.active.get() == null) {
                        subscriber.onComplete();
                        return;
                    }
                }
                SwitchMapInnerSubscriber<T, R> switchMapInnerSubscriber = this.active.get();
                SimpleQueue<R> simpleQueue = switchMapInnerSubscriber != null ? switchMapInnerSubscriber.queue : null;
                if (simpleQueue != null) {
                    if (switchMapInnerSubscriber.done) {
                        if (!this.delayErrors) {
                            if (this.error.get() != null) {
                                disposeInner();
                                subscriber.onError(this.error.terminate());
                                return;
                            } else if (simpleQueue.isEmpty()) {
                                AtomicReference$$ExternalSyntheticBackportWithForwarding0.m(this.active, switchMapInnerSubscriber, null);
                            }
                        } else if (simpleQueue.isEmpty()) {
                            AtomicReference$$ExternalSyntheticBackportWithForwarding0.m(this.active, switchMapInnerSubscriber, null);
                        }
                    }
                    long j = this.requested.get();
                    long j2 = 0;
                    while (true) {
                        z = false;
                        if (j2 != j) {
                            if (!this.cancelled) {
                                boolean z2 = switchMapInnerSubscriber.done;
                                try {
                                    helloPoll = simpleQueue.poll();
                                } catch (Throwable th) {
                                    Exceptions.throwIfFatal(th);
                                    switchMapInnerSubscriber.cancel();
                                    this.error.addThrowable(th);
                                    helloPoll = null;
                                    z2 = true;
                                }
                                boolean z3 = helloPoll == null;
                                if (switchMapInnerSubscriber == this.active.get()) {
                                    if (z2) {
                                        if (this.delayErrors) {
                                            if (z3) {
                                                AtomicReference$$ExternalSyntheticBackportWithForwarding0.m(this.active, switchMapInnerSubscriber, null);
                                            }
                                        } else if (this.error.get() != null) {
                                            subscriber.onError(this.error.terminate());
                                            return;
                                        } else if (z3) {
                                            AtomicReference$$ExternalSyntheticBackportWithForwarding0.m(this.active, switchMapInnerSubscriber, null);
                                        }
                                    }
                                    if (z3) {
                                        break;
                                    }
                                    subscriber.onNext(helloPoll);
                                    j2++;
                                }
                                z = true;
                                break;
                            }
                            return;
                        }
                        break;
                    }
                    if (j2 != 0 && !this.cancelled) {
                        if (j != Long.MAX_VALUE) {
                            this.requested.addAndGet(-j2);
                        }
                        switchMapInnerSubscriber.request(j2);
                    }
                    if (z) {
                        continue;
                    }
                }
                iAddAndGet = addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            }
        }
    }

    static final class SwitchMapInnerSubscriber<T, R> extends AtomicReference<Subscription> implements FlowableSubscriber<R> {
        private static final long serialVersionUID = 3837284832786408377L;
        final int bufferSize;
        volatile boolean done;
        int fusionMode;
        final long index;
        final SwitchMapSubscriber<T, R> parent;
        volatile SimpleQueue<R> queue;

        SwitchMapInnerSubscriber(SwitchMapSubscriber<T, R> switchMapSubscriber, long j, int i) {
            this.parent = switchMapSubscriber;
            this.index = j;
            this.bufferSize = i;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this, subscription)) {
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int iRequestFusion = queueSubscription.requestFusion(7);
                    if (iRequestFusion == 1) {
                        this.fusionMode = iRequestFusion;
                        this.queue = queueSubscription;
                        this.done = true;
                        this.parent.drain();
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.fusionMode = iRequestFusion;
                        this.queue = queueSubscription;
                        subscription.request(this.bufferSize);
                        return;
                    }
                }
                this.queue = new SpscArrayQueue(this.bufferSize);
                subscription.request(this.bufferSize);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(R r) {
            SwitchMapSubscriber<T, R> switchMapSubscriber = this.parent;
            if (this.index == switchMapSubscriber.unique) {
                if (this.fusionMode == 0 && !this.queue.offer(r)) {
                    onError(new MissingBackpressureException("Queue full?!"));
                } else {
                    switchMapSubscriber.drain();
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            SwitchMapSubscriber<T, R> switchMapSubscriber = this.parent;
            if (this.index == switchMapSubscriber.unique && switchMapSubscriber.error.addThrowable(th)) {
                if (!switchMapSubscriber.delayErrors) {
                    switchMapSubscriber.upstream.cancel();
                    switchMapSubscriber.done = true;
                }
                this.done = true;
                switchMapSubscriber.drain();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            SwitchMapSubscriber<T, R> switchMapSubscriber = this.parent;
            if (this.index == switchMapSubscriber.unique) {
                this.done = true;
                switchMapSubscriber.drain();
            }
        }

        public void cancel() {
            SubscriptionHelper.cancel(this);
        }

        public void request(long j) {
            if (this.fusionMode != 1) {
                get().request(j);
            }
        }
    }
}
