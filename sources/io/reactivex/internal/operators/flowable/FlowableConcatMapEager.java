package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscribers.InnerQueuedSubscriber;
import io.reactivex.internal.subscribers.InnerQueuedSubscriberSupport;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes5.dex */
public final class FlowableConcatMapEager<T, R> extends AbstractFlowableWithUpstream<T, R> {
    final ErrorMode errorMode;
    final Function<? super T, ? extends Publisher<? extends R>> mapper;
    final int maxConcurrency;
    final int prefetch;

    public FlowableConcatMapEager(Flowable<T> flowable, Function<? super T, ? extends Publisher<? extends R>> function, int i, int i2, ErrorMode errorMode) {
        super(flowable);
        this.mapper = function;
        this.maxConcurrency = i;
        this.prefetch = i2;
        this.errorMode = errorMode;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super R> subscriber) {
        this.source.subscribe((FlowableSubscriber) new ConcatMapEagerDelayErrorSubscriber(subscriber, this.mapper, this.maxConcurrency, this.prefetch, this.errorMode));
    }

    static final class ConcatMapEagerDelayErrorSubscriber<T, R> extends AtomicInteger implements FlowableSubscriber<T>, Subscription, InnerQueuedSubscriberSupport<R> {
        private static final long serialVersionUID = -4255299542215038287L;
        volatile boolean cancelled;
        volatile InnerQueuedSubscriber<R> current;
        volatile boolean done;
        final Subscriber<? super R> downstream;
        final ErrorMode errorMode;
        final Function<? super T, ? extends Publisher<? extends R>> mapper;
        final int maxConcurrency;
        final int prefetch;
        final SpscLinkedArrayQueue<InnerQueuedSubscriber<R>> subscribers;
        Subscription upstream;
        final AtomicThrowable errors = new AtomicThrowable();
        final AtomicLong requested = new AtomicLong();

        ConcatMapEagerDelayErrorSubscriber(Subscriber<? super R> subscriber, Function<? super T, ? extends Publisher<? extends R>> function, int i, int i2, ErrorMode errorMode) {
            this.downstream = subscriber;
            this.mapper = function;
            this.maxConcurrency = i;
            this.prefetch = i2;
            this.errorMode = errorMode;
            this.subscribers = new SpscLinkedArrayQueue<>(Math.min(i2, i));
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                int i = this.maxConcurrency;
                subscription.request(i == Integer.MAX_VALUE ? Long.MAX_VALUE : i);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            try {
                Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.mapper.apply(t), "The mapper returned a null Publisher");
                InnerQueuedSubscriber<R> innerQueuedSubscriber = new InnerQueuedSubscriber<>(this, this.prefetch);
                if (this.cancelled) {
                    return;
                }
                this.subscribers.offer(innerQueuedSubscriber);
                publisher.subscribe(innerQueuedSubscriber);
                if (this.cancelled) {
                    innerQueuedSubscriber.cancel();
                    drainAndCancel();
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.upstream.cancel();
                onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.errors.addThrowable(th)) {
                this.done = true;
                drain();
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.upstream.cancel();
            drainAndCancel();
        }

        void drainAndCancel() {
            if (getAndIncrement() == 0) {
                do {
                    cancelAll();
                } while (decrementAndGet() != 0);
            }
        }

        void cancelAll() {
            InnerQueuedSubscriber<R> innerQueuedSubscriber = this.current;
            this.current = null;
            if (innerQueuedSubscriber != null) {
                innerQueuedSubscriber.cancel();
            }
            while (true) {
                InnerQueuedSubscriber<R> innerQueuedSubscriberPoll = this.subscribers.poll();
                if (innerQueuedSubscriberPoll == null) {
                    return;
                } else {
                    innerQueuedSubscriberPoll.cancel();
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
                drain();
            }
        }

        @Override // io.reactivex.internal.subscribers.InnerQueuedSubscriberSupport
        public void innerNext(InnerQueuedSubscriber<R> innerQueuedSubscriber, R r) {
            if (innerQueuedSubscriber.queue().offer(r)) {
                drain();
            } else {
                innerQueuedSubscriber.cancel();
                innerError(innerQueuedSubscriber, new MissingBackpressureException());
            }
        }

        @Override // io.reactivex.internal.subscribers.InnerQueuedSubscriberSupport
        public void innerError(InnerQueuedSubscriber<R> innerQueuedSubscriber, Throwable th) {
            if (this.errors.addThrowable(th)) {
                innerQueuedSubscriber.setDone();
                if (this.errorMode != ErrorMode.END) {
                    this.upstream.cancel();
                }
                drain();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // io.reactivex.internal.subscribers.InnerQueuedSubscriberSupport
        public void innerComplete(InnerQueuedSubscriber<R> innerQueuedSubscriber) {
            innerQueuedSubscriber.setDone();
            drain();
        }

        @Override // io.reactivex.internal.subscribers.InnerQueuedSubscriberSupport
        public void drain() {
            InnerQueuedSubscriber<R> innerQueuedSubscriberPoll;
            long j;
            long j2;
            boolean z;
            SimpleQueue<R> simpleQueueQueue;
            if (getAndIncrement() != 0) {
                return;
            }
            InnerQueuedSubscriber<R> innerQueuedSubscriber = this.current;
            Subscriber<? super R> subscriber = this.downstream;
            ErrorMode errorMode = this.errorMode;
            int iAddAndGet = 1;
            while (true) {
                long j3 = this.requested.get();
                if (innerQueuedSubscriber != null) {
                    innerQueuedSubscriberPoll = innerQueuedSubscriber;
                } else {
                    if (errorMode != ErrorMode.END && this.errors.get() != null) {
                        cancelAll();
                        subscriber.onError(this.errors.terminate());
                        return;
                    }
                    boolean z2 = this.done;
                    innerQueuedSubscriberPoll = this.subscribers.poll();
                    if (z2 && innerQueuedSubscriberPoll == null) {
                        Throwable thTerminate = this.errors.terminate();
                        if (thTerminate != null) {
                            subscriber.onError(thTerminate);
                            return;
                        } else {
                            subscriber.onComplete();
                            return;
                        }
                    }
                    if (innerQueuedSubscriberPoll != null) {
                        this.current = innerQueuedSubscriberPoll;
                    }
                }
                if (innerQueuedSubscriberPoll == null || (simpleQueueQueue = innerQueuedSubscriberPoll.queue()) == null) {
                    j = 0;
                    j2 = 0;
                    z = false;
                } else {
                    j2 = 0;
                    while (true) {
                        if (j2 == j3) {
                            j = 0;
                        } else {
                            if (this.cancelled) {
                                cancelAll();
                                return;
                            }
                            if (errorMode == ErrorMode.IMMEDIATE && this.errors.get() != null) {
                                this.current = null;
                                innerQueuedSubscriberPoll.cancel();
                                cancelAll();
                                subscriber.onError(this.errors.terminate());
                                return;
                            }
                            boolean zIsDone = innerQueuedSubscriberPoll.isDone();
                            j = 0;
                            try {
                                R rPoll = simpleQueueQueue.poll();
                                boolean z3 = rPoll == null;
                                if (zIsDone && z3) {
                                    this.current = null;
                                    this.upstream.request(1L);
                                    innerQueuedSubscriberPoll = null;
                                    z = true;
                                    break;
                                }
                                if (!z3) {
                                    subscriber.onNext(rPoll);
                                    j2++;
                                    innerQueuedSubscriberPoll.requestOne();
                                }
                            } catch (Throwable th) {
                                Exceptions.throwIfFatal(th);
                                this.current = null;
                                innerQueuedSubscriberPoll.cancel();
                                cancelAll();
                                subscriber.onError(th);
                                return;
                            }
                        }
                        z = false;
                        break;
                    }
                    if (j2 == j3) {
                        if (this.cancelled) {
                            cancelAll();
                            return;
                        }
                        if (errorMode == ErrorMode.IMMEDIATE && this.errors.get() != null) {
                            this.current = null;
                            innerQueuedSubscriberPoll.cancel();
                            cancelAll();
                            subscriber.onError(this.errors.terminate());
                            return;
                        }
                        boolean zIsDone2 = innerQueuedSubscriberPoll.isDone();
                        boolean zIsEmpty = simpleQueueQueue.isEmpty();
                        if (zIsDone2 && zIsEmpty) {
                            this.current = null;
                            this.upstream.request(1L);
                            innerQueuedSubscriberPoll = null;
                            z = true;
                        }
                    }
                }
                if (j2 != j && j3 != Long.MAX_VALUE) {
                    this.requested.addAndGet(-j2);
                }
                if (!z && (iAddAndGet = addAndGet(-iAddAndGet)) == 0) {
                    return;
                } else {
                    innerQueuedSubscriber = innerQueuedSubscriberPoll;
                }
            }
        }
    }
}
