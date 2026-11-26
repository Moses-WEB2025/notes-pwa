package io.reactivex.internal.operators.flowable;

import androidx.lifecycle.AtomicReference$$ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.fuseable.HasUpstreamPublisher;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes5.dex */
public final class FlowablePublish<T> extends ConnectableFlowable<T> implements HasUpstreamPublisher<T>, FlowablePublishClassic<T> {
    static final long CANCELLED = Long.MIN_VALUE;
    final int bufferSize;
    final AtomicReference<PublishSubscriber<T>> current;
    final Publisher<T> onSubscribe;
    final Flowable<T> source;

    public static <T> ConnectableFlowable<T> create(Flowable<T> flowable, int i) {
        AtomicReference atomicReference = new AtomicReference();
        return RxJavaPlugins.onAssembly((ConnectableFlowable) new FlowablePublish(new FlowablePublisher(atomicReference, i), flowable, atomicReference, i));
    }

    private FlowablePublish(Publisher<T> publisher, Flowable<T> flowable, AtomicReference<PublishSubscriber<T>> atomicReference, int i) {
        this.onSubscribe = publisher;
        this.source = flowable;
        this.current = atomicReference;
        this.bufferSize = i;
    }

    @Override // io.reactivex.internal.fuseable.HasUpstreamPublisher
    public Publisher<T> source() {
        return this.source;
    }

    @Override // io.reactivex.internal.operators.flowable.FlowablePublishClassic
    public int publishBufferSize() {
        return this.bufferSize;
    }

    @Override // io.reactivex.internal.operators.flowable.FlowablePublishClassic
    public Publisher<T> publishSource() {
        return this.source;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        this.onSubscribe.subscribe(subscriber);
    }

    @Override // io.reactivex.flowables.ConnectableFlowable
    public void connect(Consumer<? super Disposable> consumer) {
        PublishSubscriber<T> publishSubscriber;
        while (true) {
            publishSubscriber = this.current.get();
            if (publishSubscriber != null && !publishSubscriber.isDisposed()) {
                break;
            }
            PublishSubscriber<T> publishSubscriber2 = new PublishSubscriber<>(this.current, this.bufferSize);
            if (AtomicReference$$ExternalSyntheticBackportWithForwarding0.m(this.current, publishSubscriber, publishSubscriber2)) {
                publishSubscriber = publishSubscriber2;
                break;
            }
        }
        boolean z = false;
        if (!publishSubscriber.shouldConnect.get() && publishSubscriber.shouldConnect.compareAndSet(false, true)) {
            z = true;
        }
        try {
            consumer.accept(publishSubscriber);
            if (z) {
                this.source.subscribe((FlowableSubscriber) publishSubscriber);
            }
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    static final class PublishSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Disposable {
        static final InnerSubscriber[] EMPTY = new InnerSubscriber[0];
        static final InnerSubscriber[] TERMINATED = new InnerSubscriber[0];
        private static final long serialVersionUID = -202316842419149694L;
        final int bufferSize;
        final AtomicReference<PublishSubscriber<T>> current;
        volatile SimpleQueue<T> queue;
        int sourceMode;
        volatile Object terminalEvent;
        final AtomicReference<Subscription> upstream = new AtomicReference<>();
        final AtomicReference<InnerSubscriber<T>[]> subscribers = new AtomicReference<>(EMPTY);
        final AtomicBoolean shouldConnect = new AtomicBoolean();

        PublishSubscriber(AtomicReference<PublishSubscriber<T>> atomicReference, int i) {
            this.current = atomicReference;
            this.bufferSize = i;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            InnerSubscriber<T>[] innerSubscriberArr = this.subscribers.get();
            InnerSubscriber<T>[] innerSubscriberArr2 = TERMINATED;
            if (innerSubscriberArr == innerSubscriberArr2 || this.subscribers.getAndSet(innerSubscriberArr2) == innerSubscriberArr2) {
                return;
            }
            AtomicReference$$ExternalSyntheticBackportWithForwarding0.m(this.current, this, null);
            SubscriptionHelper.cancel(this.upstream);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.subscribers.get() == TERMINATED;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this.upstream, subscription)) {
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int iRequestFusion = queueSubscription.requestFusion(7);
                    if (iRequestFusion == 1) {
                        this.sourceMode = iRequestFusion;
                        this.queue = queueSubscription;
                        this.terminalEvent = NotificationLite.complete();
                        dispatch();
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.sourceMode = iRequestFusion;
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
        public void onNext(T t) {
            if (this.sourceMode == 0 && !this.queue.offer(t)) {
                onError(new MissingBackpressureException("Prefetch queue is full?!"));
            } else {
                dispatch();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.terminalEvent == null) {
                this.terminalEvent = NotificationLite.error(th);
                dispatch();
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.terminalEvent == null) {
                this.terminalEvent = NotificationLite.complete();
                dispatch();
            }
        }

        boolean add(InnerSubscriber<T> innerSubscriber) {
            InnerSubscriber<T>[] innerSubscriberArr;
            InnerSubscriber[] innerSubscriberArr2;
            do {
                innerSubscriberArr = this.subscribers.get();
                if (innerSubscriberArr == TERMINATED) {
                    return false;
                }
                int length = innerSubscriberArr.length;
                innerSubscriberArr2 = new InnerSubscriber[length + 1];
                System.arraycopy(innerSubscriberArr, 0, innerSubscriberArr2, 0, length);
                innerSubscriberArr2[length] = innerSubscriber;
            } while (!AtomicReference$$ExternalSyntheticBackportWithForwarding0.m(this.subscribers, innerSubscriberArr, innerSubscriberArr2));
            return true;
        }

        void remove(InnerSubscriber<T> innerSubscriber) {
            InnerSubscriber<T>[] innerSubscriberArr;
            InnerSubscriber[] innerSubscriberArr2;
            do {
                innerSubscriberArr = this.subscribers.get();
                int length = innerSubscriberArr.length;
                if (length == 0) {
                    return;
                }
                int i = 0;
                while (true) {
                    if (i >= length) {
                        i = -1;
                        break;
                    } else if (innerSubscriberArr[i].equals(innerSubscriber)) {
                        break;
                    } else {
                        i++;
                    }
                }
                if (i < 0) {
                    return;
                }
                if (length == 1) {
                    innerSubscriberArr2 = EMPTY;
                } else {
                    InnerSubscriber[] innerSubscriberArr3 = new InnerSubscriber[length - 1];
                    System.arraycopy(innerSubscriberArr, 0, innerSubscriberArr3, 0, i);
                    System.arraycopy(innerSubscriberArr, i + 1, innerSubscriberArr3, i, (length - i) - 1);
                    innerSubscriberArr2 = innerSubscriberArr3;
                }
            } while (!AtomicReference$$ExternalSyntheticBackportWithForwarding0.m(this.subscribers, innerSubscriberArr, innerSubscriberArr2));
        }

        boolean checkTerminated(Object obj, boolean z) {
            int i = 0;
            if (obj != null) {
                if (!NotificationLite.isComplete(obj)) {
                    Throwable error = NotificationLite.getError(obj);
                    AtomicReference$$ExternalSyntheticBackportWithForwarding0.m(this.current, this, null);
                    InnerSubscriber<T>[] andSet = this.subscribers.getAndSet(TERMINATED);
                    if (andSet.length != 0) {
                        int length = andSet.length;
                        while (i < length) {
                            andSet[i].child.onError(error);
                            i++;
                        }
                    } else {
                        RxJavaPlugins.onError(error);
                    }
                    return true;
                }
                if (z) {
                    AtomicReference$$ExternalSyntheticBackportWithForwarding0.m(this.current, this, null);
                    InnerSubscriber<T>[] andSet2 = this.subscribers.getAndSet(TERMINATED);
                    int length2 = andSet2.length;
                    while (i < length2) {
                        andSet2[i].child.onComplete();
                        i++;
                    }
                    return true;
                }
            }
            return false;
        }

        void dispatch() {
            boolean z;
            T tPoll;
            InnerSubscriber<T>[] innerSubscriberArr;
            T tPoll2;
            if (getAndIncrement() != 0) {
                return;
            }
            AtomicReference<InnerSubscriber<T>[]> atomicReference = this.subscribers;
            boolean z2 = true;
            InnerSubscriber<T>[] innerSubscriberArr2 = atomicReference.get();
            int iAddAndGet = 1;
            while (true) {
                Object obj = this.terminalEvent;
                SimpleQueue<T> simpleQueue = this.queue;
                boolean z3 = (simpleQueue == null || simpleQueue.isEmpty()) ? z2 : false;
                if (checkTerminated(obj, z3)) {
                    return;
                }
                if (z3) {
                    z = z2;
                } else {
                    int length = innerSubscriberArr2.length;
                    int i = 0;
                    long jMin = Long.MAX_VALUE;
                    for (InnerSubscriber<T> innerSubscriber : innerSubscriberArr2) {
                        long j = innerSubscriber.get();
                        if (j != Long.MIN_VALUE) {
                            jMin = Math.min(jMin, j - innerSubscriber.emitted);
                        } else {
                            i++;
                        }
                    }
                    long j2 = 1;
                    if (length == i) {
                        Object objError = this.terminalEvent;
                        try {
                            tPoll = simpleQueue.poll();
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            this.upstream.get().cancel();
                            objError = NotificationLite.error(th);
                            this.terminalEvent = objError;
                            tPoll = null;
                        }
                        if (checkTerminated(objError, tPoll == null ? z2 : false)) {
                            return;
                        }
                        if (this.sourceMode != z2) {
                            this.upstream.get().request(1L);
                        }
                        z = z2;
                        innerSubscriberArr = innerSubscriberArr2;
                    } else {
                        int i2 = 0;
                        while (true) {
                            long j3 = i2;
                            if (j3 < jMin) {
                                Object objError2 = this.terminalEvent;
                                try {
                                    tPoll2 = simpleQueue.poll();
                                } catch (Throwable th2) {
                                    Exceptions.throwIfFatal(th2);
                                    this.upstream.get().cancel();
                                    objError2 = NotificationLite.error(th2);
                                    this.terminalEvent = objError2;
                                    tPoll2 = null;
                                }
                                boolean z4 = tPoll2 == null ? z2 : false;
                                if (checkTerminated(objError2, z4)) {
                                    return;
                                }
                                if (z4) {
                                    z3 = z4;
                                } else {
                                    Object value = NotificationLite.getValue(tPoll2);
                                    int length2 = innerSubscriberArr2.length;
                                    int i3 = 0;
                                    boolean z5 = false;
                                    while (i3 < length2) {
                                        long j4 = j2;
                                        InnerSubscriber<T> innerSubscriber2 = innerSubscriberArr2[i3];
                                        long j5 = innerSubscriber2.get();
                                        if (j5 != Long.MIN_VALUE) {
                                            if (j5 != Long.MAX_VALUE) {
                                                innerSubscriber2.emitted += j4;
                                            }
                                            innerSubscriber2.child.onNext(value);
                                        } else {
                                            z5 = true;
                                        }
                                        i3++;
                                        innerSubscriberArr2 = innerSubscriberArr2;
                                        j2 = j4;
                                    }
                                    InnerSubscriber<T>[] innerSubscriberArr3 = innerSubscriberArr2;
                                    long j6 = j2;
                                    i2++;
                                    InnerSubscriber<T>[] innerSubscriberArr4 = atomicReference.get();
                                    if (z5 || innerSubscriberArr4 != innerSubscriberArr3) {
                                        if (i2 != 0 && this.sourceMode != 1) {
                                            this.upstream.get().request(i2);
                                        }
                                        innerSubscriberArr2 = innerSubscriberArr4;
                                        z2 = true;
                                    } else {
                                        innerSubscriberArr2 = innerSubscriberArr3;
                                        z3 = z4;
                                        j2 = j6;
                                        z2 = true;
                                    }
                                }
                            }
                            innerSubscriberArr = innerSubscriberArr2;
                            if (i2 != 0) {
                                z = true;
                                if (this.sourceMode != 1) {
                                    this.upstream.get().request(j3);
                                }
                            } else {
                                z = true;
                            }
                            if (jMin == 0 || z3) {
                            }
                            z2 = z;
                        }
                    }
                    innerSubscriberArr2 = innerSubscriberArr;
                    z2 = z;
                }
                iAddAndGet = addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
                innerSubscriberArr2 = atomicReference.get();
                z2 = z;
            }
        }
    }

    static final class InnerSubscriber<T> extends AtomicLong implements Subscription {
        private static final long serialVersionUID = -4453897557930727610L;
        final Subscriber<? super T> child;
        long emitted;
        volatile PublishSubscriber<T> parent;

        InnerSubscriber(Subscriber<? super T> subscriber) {
            this.child = subscriber;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.addCancel(this, j);
                PublishSubscriber<T> publishSubscriber = this.parent;
                if (publishSubscriber != null) {
                    publishSubscriber.dispatch();
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            PublishSubscriber<T> publishSubscriber;
            if (get() == Long.MIN_VALUE || getAndSet(Long.MIN_VALUE) == Long.MIN_VALUE || (publishSubscriber = this.parent) == null) {
                return;
            }
            publishSubscriber.remove(this);
            publishSubscriber.dispatch();
        }
    }

    static final class FlowablePublisher<T> implements Publisher<T> {
        private final int bufferSize;
        private final AtomicReference<PublishSubscriber<T>> curr;

        FlowablePublisher(AtomicReference<PublishSubscriber<T>> atomicReference, int i) {
            this.curr = atomicReference;
            this.bufferSize = i;
        }

        @Override // org.reactivestreams.Publisher
        public void subscribe(Subscriber<? super T> subscriber) {
            PublishSubscriber<T> publishSubscriber;
            InnerSubscriber<T> innerSubscriber = new InnerSubscriber<>(subscriber);
            subscriber.onSubscribe(innerSubscriber);
            while (true) {
                publishSubscriber = this.curr.get();
                if (publishSubscriber == null || publishSubscriber.isDisposed()) {
                    PublishSubscriber<T> publishSubscriber2 = new PublishSubscriber<>(this.curr, this.bufferSize);
                    if (AtomicReference$$ExternalSyntheticBackportWithForwarding0.m(this.curr, publishSubscriber, publishSubscriber2)) {
                        publishSubscriber = publishSubscriber2;
                    } else {
                        continue;
                    }
                }
                if (publishSubscriber.add(innerSubscriber)) {
                    break;
                }
            }
            if (innerSubscriber.get() == Long.MIN_VALUE) {
                publishSubscriber.remove(innerSubscriber);
            } else {
                innerSubscriber.parent = publishSubscriber;
            }
            publishSubscriber.dispatch();
        }
    }
}
