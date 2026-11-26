package io.reactivex.internal.operators.flowable;

import androidx.lifecycle.AtomicReference$$ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes5.dex */
public final class FlowableFlatMap<T, U> extends AbstractFlowableWithUpstream<T, U> {
    final int bufferSize;
    final boolean delayErrors;
    final Function<? super T, ? extends Publisher<? extends U>> mapper;
    final int maxConcurrency;

    public FlowableFlatMap(Flowable<T> flowable, Function<? super T, ? extends Publisher<? extends U>> function, boolean z, int i, int i2) {
        super(flowable);
        this.mapper = function;
        this.delayErrors = z;
        this.maxConcurrency = i;
        this.bufferSize = i2;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super U> subscriber) {
        if (FlowableScalarXMap.tryScalarXMapSubscribe(this.source, subscriber, this.mapper)) {
            return;
        }
        this.source.subscribe((FlowableSubscriber) subscribe(subscriber, this.mapper, this.delayErrors, this.maxConcurrency, this.bufferSize));
    }

    public static <T, U> FlowableSubscriber<T> subscribe(Subscriber<? super U> subscriber, Function<? super T, ? extends Publisher<? extends U>> function, boolean z, int i, int i2) {
        return new MergeSubscriber(subscriber, function, z, i, i2);
    }

    static final class MergeSubscriber<T, U> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -2117620485640801370L;
        final int bufferSize;
        volatile boolean cancelled;
        final boolean delayErrors;
        volatile boolean done;
        final Subscriber<? super U> downstream;
        final AtomicThrowable errs = new AtomicThrowable();
        long lastId;
        int lastIndex;
        final Function<? super T, ? extends Publisher<? extends U>> mapper;
        final int maxConcurrency;
        volatile SimplePlainQueue<U> queue;
        final AtomicLong requested;
        int scalarEmitted;
        final int scalarLimit;
        final AtomicReference<InnerSubscriber<?, ?>[]> subscribers;
        long uniqueId;
        Subscription upstream;
        static final InnerSubscriber<?, ?>[] EMPTY = new InnerSubscriber[0];
        static final InnerSubscriber<?, ?>[] CANCELLED = new InnerSubscriber[0];

        MergeSubscriber(Subscriber<? super U> subscriber, Function<? super T, ? extends Publisher<? extends U>> function, boolean z, int i, int i2) {
            AtomicReference<InnerSubscriber<?, ?>[]> atomicReference = new AtomicReference<>();
            this.subscribers = atomicReference;
            this.requested = new AtomicLong();
            this.downstream = subscriber;
            this.mapper = function;
            this.delayErrors = z;
            this.maxConcurrency = i;
            this.bufferSize = i2;
            this.scalarLimit = Math.max(1, i >> 1);
            atomicReference.lazySet(EMPTY);
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                if (this.cancelled) {
                    return;
                }
                int i = this.maxConcurrency;
                if (i == Integer.MAX_VALUE) {
                    subscription.request(Long.MAX_VALUE);
                } else {
                    subscription.request(i);
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                return;
            }
            try {
                Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.mapper.apply(t), "The mapper returned a null Publisher");
                if (publisher instanceof Callable) {
                    try {
                        Object objCall = ((Callable) publisher).call();
                        if (objCall != null) {
                            tryEmitScalar(objCall);
                            return;
                        }
                        if (this.maxConcurrency == Integer.MAX_VALUE || this.cancelled) {
                            return;
                        }
                        int i = this.scalarEmitted + 1;
                        this.scalarEmitted = i;
                        int i2 = this.scalarLimit;
                        if (i == i2) {
                            this.scalarEmitted = 0;
                            this.upstream.request(i2);
                            return;
                        }
                        return;
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.errs.addThrowable(th);
                        drain();
                        return;
                    }
                }
                long j = this.uniqueId;
                this.uniqueId = 1 + j;
                InnerSubscriber innerSubscriber = new InnerSubscriber(this, j);
                if (addInner(innerSubscriber)) {
                    publisher.subscribe(innerSubscriber);
                }
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.upstream.cancel();
                onError(th2);
            }
        }

        boolean addInner(InnerSubscriber<T, U> innerSubscriber) {
            InnerSubscriber<?, ?>[] innerSubscriberArr;
            InnerSubscriber[] innerSubscriberArr2;
            do {
                innerSubscriberArr = this.subscribers.get();
                if (innerSubscriberArr == CANCELLED) {
                    innerSubscriber.dispose();
                    return false;
                }
                int length = innerSubscriberArr.length;
                innerSubscriberArr2 = new InnerSubscriber[length + 1];
                System.arraycopy(innerSubscriberArr, 0, innerSubscriberArr2, 0, length);
                innerSubscriberArr2[length] = innerSubscriber;
            } while (!AtomicReference$$ExternalSyntheticBackportWithForwarding0.m(this.subscribers, innerSubscriberArr, innerSubscriberArr2));
            return true;
        }

        /* JADX WARN: Multi-variable type inference failed */
        void removeInner(InnerSubscriber<T, U> innerSubscriber) {
            InnerSubscriber<?, ?>[] innerSubscriberArr;
            InnerSubscriber<?, ?>[] innerSubscriberArr2;
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
                    } else if (innerSubscriberArr[i] == innerSubscriber) {
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
                    InnerSubscriber<?, ?>[] innerSubscriberArr3 = new InnerSubscriber[length - 1];
                    System.arraycopy(innerSubscriberArr, 0, innerSubscriberArr3, 0, i);
                    System.arraycopy(innerSubscriberArr, i + 1, innerSubscriberArr3, i, (length - i) - 1);
                    innerSubscriberArr2 = innerSubscriberArr3;
                }
            } while (!AtomicReference$$ExternalSyntheticBackportWithForwarding0.m(this.subscribers, innerSubscriberArr, innerSubscriberArr2));
        }

        SimpleQueue<U> getMainQueue() {
            SimplePlainQueue<U> spscArrayQueue = this.queue;
            if (spscArrayQueue == null) {
                if (this.maxConcurrency == Integer.MAX_VALUE) {
                    spscArrayQueue = new SpscLinkedArrayQueue<>(this.bufferSize);
                } else {
                    spscArrayQueue = new SpscArrayQueue<>(this.maxConcurrency);
                }
                this.queue = spscArrayQueue;
            }
            return spscArrayQueue;
        }

        void tryEmitScalar(U u) {
            if (get() == 0 && compareAndSet(0, 1)) {
                long j = this.requested.get();
                SimpleQueue<U> mainQueue = this.queue;
                if (j != 0 && (mainQueue == null || mainQueue.isEmpty())) {
                    this.downstream.onNext(u);
                    if (j != Long.MAX_VALUE) {
                        this.requested.decrementAndGet();
                    }
                    if (this.maxConcurrency != Integer.MAX_VALUE && !this.cancelled) {
                        int i = this.scalarEmitted + 1;
                        this.scalarEmitted = i;
                        int i2 = this.scalarLimit;
                        if (i == i2) {
                            this.scalarEmitted = 0;
                            this.upstream.request(i2);
                        }
                    }
                } else {
                    if (mainQueue == null) {
                        mainQueue = getMainQueue();
                    }
                    if (!mainQueue.offer(u)) {
                        onError(new IllegalStateException("Scalar queue full?!"));
                        return;
                    }
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            } else if (!getMainQueue().offer(u)) {
                onError(new IllegalStateException("Scalar queue full?!"));
                return;
            } else if (getAndIncrement() != 0) {
                return;
            }
            drainLoop();
        }

        SimpleQueue<U> getInnerQueue(InnerSubscriber<T, U> innerSubscriber) {
            SimpleQueue<U> simpleQueue = innerSubscriber.queue;
            if (simpleQueue != null) {
                return simpleQueue;
            }
            SpscArrayQueue spscArrayQueue = new SpscArrayQueue(this.bufferSize);
            innerSubscriber.queue = spscArrayQueue;
            return spscArrayQueue;
        }

        void tryEmit(U u, InnerSubscriber<T, U> innerSubscriber) {
            if (get() == 0 && compareAndSet(0, 1)) {
                long j = this.requested.get();
                SimpleQueue<U> innerQueue = innerSubscriber.queue;
                if (j != 0 && (innerQueue == null || innerQueue.isEmpty())) {
                    this.downstream.onNext(u);
                    if (j != Long.MAX_VALUE) {
                        this.requested.decrementAndGet();
                    }
                    innerSubscriber.requestMore(1L);
                } else {
                    if (innerQueue == null) {
                        innerQueue = getInnerQueue(innerSubscriber);
                    }
                    if (!innerQueue.offer(u)) {
                        onError(new MissingBackpressureException("Inner queue full?!"));
                        return;
                    }
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            } else {
                SimpleQueue spscArrayQueue = innerSubscriber.queue;
                if (spscArrayQueue == null) {
                    spscArrayQueue = new SpscArrayQueue(this.bufferSize);
                    innerSubscriber.queue = spscArrayQueue;
                }
                if (!spscArrayQueue.offer(u)) {
                    onError(new MissingBackpressureException("Inner queue full?!"));
                    return;
                } else if (getAndIncrement() != 0) {
                    return;
                }
            }
            drainLoop();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (this.errs.addThrowable(th)) {
                this.done = true;
                if (!this.delayErrors) {
                    for (InnerSubscriber<?, ?> innerSubscriber : this.subscribers.getAndSet(CANCELLED)) {
                        innerSubscriber.dispose();
                    }
                }
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
                drain();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            SimplePlainQueue<U> simplePlainQueue;
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.upstream.cancel();
            disposeAll();
            if (getAndIncrement() != 0 || (simplePlainQueue = this.queue) == null) {
                return;
            }
            simplePlainQueue.clear();
        }

        void drain() {
            if (getAndIncrement() == 0) {
                drainLoop();
            }
        }

        /* JADX WARN: Code duplicated, block: B:100:0x0153  */
        /* JADX WARN: Code duplicated, block: B:114:0x0177  */
        /* JADX WARN: Code duplicated, block: B:118:0x0180  */
        /* JADX WARN: Code duplicated, block: B:120:0x0184  */
        /* JADX WARN: Code duplicated, block: B:135:0x00fd A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:140:0x01b6 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:142:0x01b6 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:144:0x01b6 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:154:0x018c A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:155:0x018f A[EDGE_INSN: B:155:0x018f->B:123:0x018f BREAK  A[LOOP:3: B:65:0x00d9->B:121:0x0185], SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:158:0x0185 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:161:0x00f2 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:162:0x013a A[EDGE_INSN: B:162:0x013a->B:94:0x013a BREAK  A[LOOP:5: B:77:0x00f9->B:85:0x010f], SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:66:0x00db  */
        /* JADX WARN: Code duplicated, block: B:69:0x00e3  */
        /* JADX WARN: Code duplicated, block: B:73:0x00ee  */
        /* JADX WARN: Code duplicated, block: B:76:0x00f7  */
        /* JADX WARN: Code duplicated, block: B:82:0x0104  */
        /* JADX WARN: Code duplicated, block: B:85:0x010f A[LOOP:5: B:77:0x00f9->B:85:0x010f, LOOP_END] */
        /* JADX WARN: Code duplicated, block: B:96:0x013e A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:97:0x0140  */
        /* JADX WARN: Code duplicated, block: B:98:0x0149  */
        /* JADX WARN: Multi-variable type inference failed */
        void drainLoop() {
            boolean z;
            long j;
            long j2;
            boolean z2;
            long j3;
            int i;
            boolean z3;
            int i2;
            InnerSubscriber<T, U> innerSubscriber;
            U uPoll;
            SimpleQueue<U> simpleQueue;
            boolean z4;
            Object obj;
            Subscriber<? super U> subscriber = this.downstream;
            int iAddAndGet = 1;
            while (!checkTerminate()) {
                SimplePlainQueue<U> simplePlainQueue = this.queue;
                long jAddAndGet = this.requested.get();
                boolean z5 = jAddAndGet == Long.MAX_VALUE;
                long j4 = 0;
                if (simplePlainQueue != null) {
                    j = 0;
                    do {
                        long j5 = 0;
                        obj = null;
                        while (true) {
                            if (jAddAndGet == 0) {
                                z = true;
                                break;
                            }
                            z = true;
                            U uPoll2 = simplePlainQueue.poll();
                            if (checkTerminate()) {
                                return;
                            }
                            if (uPoll2 == null) {
                                obj = uPoll2;
                                break;
                            }
                            subscriber.onNext(uPoll2);
                            j++;
                            j5++;
                            jAddAndGet--;
                            obj = uPoll2;
                        }
                        if (j5 != 0) {
                            jAddAndGet = z5 ? Long.MAX_VALUE : this.requested.addAndGet(-j5);
                        }
                        if (jAddAndGet == 0) {
                            break;
                        }
                    } while (obj != null);
                } else {
                    z = true;
                    j = 0;
                }
                boolean z6 = this.done;
                SimplePlainQueue<U> simplePlainQueue2 = this.queue;
                InnerSubscriber<?, ?>[] innerSubscriberArr = this.subscribers.get();
                int length = innerSubscriberArr.length;
                if (z6 && ((simplePlainQueue2 == null || simplePlainQueue2.isEmpty()) && length == 0)) {
                    Throwable thTerminate = this.errs.terminate();
                    if (thTerminate != ExceptionHelper.TERMINATED) {
                        if (thTerminate == null) {
                            subscriber.onComplete();
                            return;
                        } else {
                            subscriber.onError(thTerminate);
                            return;
                        }
                    }
                    return;
                }
                if (length != 0) {
                    long j6 = this.lastId;
                    int i3 = this.lastIndex;
                    if (length > i3) {
                        j3 = 1;
                        if (innerSubscriberArr[i3].id != j6) {
                        }
                        i = i3;
                        z3 = false;
                        i2 = 0;
                        while (true) {
                            if (i2 < length) {
                                innerSubscriberArr = innerSubscriberArr;
                                j2 = j4;
                                break;
                            }
                            if (checkTerminate()) {
                                return;
                            }
                            innerSubscriber = innerSubscriberArr[i];
                            uPoll = null;
                            while (!checkTerminate()) {
                                simpleQueue = innerSubscriber.queue;
                                if (simpleQueue == null) {
                                    innerSubscriberArr = innerSubscriberArr;
                                    j2 = j4;
                                } else {
                                    j2 = j4;
                                    while (jAddAndGet != j2) {
                                        try {
                                            uPoll = simpleQueue.poll();
                                            if (uPoll == null) {
                                                break;
                                            }
                                            subscriber.onNext(uPoll);
                                            if (checkTerminate()) {
                                                return;
                                            }
                                            jAddAndGet -= j3;
                                            j4 += j3;
                                        } catch (Throwable th) {
                                            Exceptions.throwIfFatal(th);
                                            innerSubscriber.dispose();
                                            this.errs.addThrowable(th);
                                            if (!this.delayErrors) {
                                                this.upstream.cancel();
                                            }
                                            if (checkTerminate()) {
                                                return;
                                            }
                                            removeInner(innerSubscriber);
                                            i2++;
                                            innerSubscriberArr = innerSubscriberArr;
                                            z3 = z;
                                        }
                                    }
                                    if (j4 != j2) {
                                        if (z5) {
                                            jAddAndGet = Long.MAX_VALUE;
                                        } else {
                                            jAddAndGet = this.requested.addAndGet(-j4);
                                        }
                                        innerSubscriber.requestMore(j4);
                                    } else {
                                        innerSubscriberArr = innerSubscriberArr;
                                    }
                                    if (jAddAndGet == j2 && uPoll != null) {
                                        innerSubscriberArr = innerSubscriberArr;
                                        j4 = j2;
                                    }
                                }
                                z4 = innerSubscriber.done;
                                SimpleQueue<U> simpleQueue2 = innerSubscriber.queue;
                                if (z4 && (simpleQueue2 == null || simpleQueue2.isEmpty())) {
                                    removeInner(innerSubscriber);
                                    if (checkTerminate()) {
                                        return;
                                    }
                                    j += j3;
                                    z3 = z;
                                }
                                if (jAddAndGet == j2) {
                                    break;
                                }
                                i++;
                                if (i == length) {
                                    i = 0;
                                }
                                i2++;
                                innerSubscriberArr = innerSubscriberArr;
                                j4 = j2;
                            }
                            return;
                        }
                        z2 = z3;
                        this.lastIndex = i;
                        this.lastId = innerSubscriberArr[i].id;
                    } else {
                        j3 = 1;
                    }
                    if (length <= i3) {
                        i3 = 0;
                    }
                    for (int i4 = 0; i4 < length && innerSubscriberArr[i3].id != j6; i4++) {
                        i3++;
                        if (i3 == length) {
                            i3 = 0;
                        }
                    }
                    this.lastIndex = i3;
                    this.lastId = innerSubscriberArr[i3].id;
                    i = i3;
                    z3 = false;
                    i2 = 0;
                    while (true) {
                        if (i2 < length) {
                            innerSubscriberArr = innerSubscriberArr;
                            j2 = j4;
                            break;
                        }
                        if (checkTerminate()) {
                            return;
                        }
                        innerSubscriber = innerSubscriberArr[i];
                        uPoll = null;
                        while (!checkTerminate()) {
                            simpleQueue = innerSubscriber.queue;
                            if (simpleQueue == null) {
                                innerSubscriberArr = innerSubscriberArr;
                                j2 = j4;
                            } else {
                                j2 = j4;
                                while (jAddAndGet != j2) {
                                    uPoll = simpleQueue.poll();
                                    if (uPoll == null) {
                                        break;
                                        break;
                                    }
                                    subscriber.onNext(uPoll);
                                    if (checkTerminate()) {
                                        return;
                                    }
                                    jAddAndGet -= j3;
                                    j4 += j3;
                                }
                                if (j4 != j2) {
                                    if (z5) {
                                        jAddAndGet = this.requested.addAndGet(-j4);
                                    } else {
                                        jAddAndGet = Long.MAX_VALUE;
                                    }
                                    innerSubscriber.requestMore(j4);
                                } else {
                                    innerSubscriberArr = innerSubscriberArr;
                                }
                                if (jAddAndGet == j2) {
                                }
                            }
                            z4 = innerSubscriber.done;
                            SimpleQueue<U> simpleQueue3 = innerSubscriber.queue;
                            if (z4) {
                                removeInner(innerSubscriber);
                                if (checkTerminate()) {
                                    return;
                                }
                                j += j3;
                                z3 = z;
                            }
                            if (jAddAndGet == j2) {
                                break;
                                break;
                            }
                            i++;
                            if (i == length) {
                                i = 0;
                            }
                            i2++;
                            innerSubscriberArr = innerSubscriberArr;
                            j4 = j2;
                        }
                        return;
                    }
                    z2 = z3;
                    this.lastIndex = i;
                    this.lastId = innerSubscriberArr[i].id;
                } else {
                    j2 = 0;
                    z2 = false;
                }
                long j7 = j;
                if (j7 != j2 && !this.cancelled) {
                    this.upstream.request(j7);
                }
                if (!z2 && (iAddAndGet = addAndGet(-iAddAndGet)) == 0) {
                    return;
                }
            }
        }

        boolean checkTerminate() {
            if (this.cancelled) {
                clearScalarQueue();
                return true;
            }
            if (this.delayErrors || this.errs.get() == null) {
                return false;
            }
            clearScalarQueue();
            Throwable thTerminate = this.errs.terminate();
            if (thTerminate != ExceptionHelper.TERMINATED) {
                this.downstream.onError(thTerminate);
            }
            return true;
        }

        void clearScalarQueue() {
            SimplePlainQueue<U> simplePlainQueue = this.queue;
            if (simplePlainQueue != null) {
                simplePlainQueue.clear();
            }
        }

        void disposeAll() {
            InnerSubscriber<?, ?>[] andSet;
            InnerSubscriber<?, ?>[] innerSubscriberArr = this.subscribers.get();
            InnerSubscriber<?, ?>[] innerSubscriberArr2 = CANCELLED;
            if (innerSubscriberArr == innerSubscriberArr2 || (andSet = this.subscribers.getAndSet(innerSubscriberArr2)) == innerSubscriberArr2) {
                return;
            }
            for (InnerSubscriber<?, ?> innerSubscriber : andSet) {
                innerSubscriber.dispose();
            }
            Throwable thTerminate = this.errs.terminate();
            if (thTerminate == null || thTerminate == ExceptionHelper.TERMINATED) {
                return;
            }
            RxJavaPlugins.onError(thTerminate);
        }

        void innerError(InnerSubscriber<T, U> innerSubscriber, Throwable th) {
            if (this.errs.addThrowable(th)) {
                innerSubscriber.done = true;
                if (!this.delayErrors) {
                    this.upstream.cancel();
                    for (InnerSubscriber<?, ?> innerSubscriber2 : this.subscribers.getAndSet(CANCELLED)) {
                        innerSubscriber2.dispose();
                    }
                }
                drain();
                return;
            }
            RxJavaPlugins.onError(th);
        }
    }

    static final class InnerSubscriber<T, U> extends AtomicReference<Subscription> implements FlowableSubscriber<U>, Disposable {
        private static final long serialVersionUID = -4606175640614850599L;
        final int bufferSize;
        volatile boolean done;
        int fusionMode;
        final long id;
        final int limit;
        final MergeSubscriber<T, U> parent;
        long produced;
        volatile SimpleQueue<U> queue;

        InnerSubscriber(MergeSubscriber<T, U> mergeSubscriber, long j) {
            this.id = j;
            this.parent = mergeSubscriber;
            int i = mergeSubscriber.bufferSize;
            this.bufferSize = i;
            this.limit = i >> 2;
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
                    }
                }
                subscription.request(this.bufferSize);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(U u) {
            if (this.fusionMode != 2) {
                this.parent.tryEmit(u, this);
            } else {
                this.parent.drain();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            lazySet(SubscriptionHelper.CANCELLED);
            this.parent.innerError(this, th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            this.parent.drain();
        }

        void requestMore(long j) {
            if (this.fusionMode != 1) {
                long j2 = this.produced + j;
                if (j2 >= this.limit) {
                    this.produced = 0L;
                    get().request(j2);
                } else {
                    this.produced = j2;
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            SubscriptionHelper.cancel(this);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return get() == SubscriptionHelper.CANCELLED;
        }
    }
}
