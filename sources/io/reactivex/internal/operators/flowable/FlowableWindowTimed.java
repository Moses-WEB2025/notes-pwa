package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.subscribers.QueueDrainSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.processors.UnicastProcessor;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes5.dex */
public final class FlowableWindowTimed<T> extends AbstractFlowableWithUpstream<T, Flowable<T>> {
    final int bufferSize;
    final long maxSize;
    final boolean restartTimerOnMaxSize;
    final Scheduler scheduler;
    final long timeskip;
    final long timespan;
    final TimeUnit unit;

    public FlowableWindowTimed(Flowable<T> flowable, long j, long j2, TimeUnit timeUnit, Scheduler scheduler, long j3, int i, boolean z) {
        super(flowable);
        this.timespan = j;
        this.timeskip = j2;
        this.unit = timeUnit;
        this.scheduler = scheduler;
        this.maxSize = j3;
        this.bufferSize = i;
        this.restartTimerOnMaxSize = z;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super Flowable<T>> subscriber) {
        SerializedSubscriber serializedSubscriber = new SerializedSubscriber(subscriber);
        if (this.timespan != this.timeskip) {
            this.source.subscribe((FlowableSubscriber) new WindowSkipSubscriber(serializedSubscriber, this.timespan, this.timeskip, this.unit, this.scheduler.createWorker(), this.bufferSize));
        } else if (this.maxSize == Long.MAX_VALUE) {
            this.source.subscribe((FlowableSubscriber) new WindowExactUnboundedSubscriber(serializedSubscriber, this.timespan, this.unit, this.scheduler, this.bufferSize));
        } else {
            this.source.subscribe((FlowableSubscriber) new WindowExactBoundedSubscriber(serializedSubscriber, this.timespan, this.unit, this.scheduler, this.bufferSize, this.maxSize, this.restartTimerOnMaxSize));
        }
    }

    static final class WindowExactUnboundedSubscriber<T> extends QueueDrainSubscriber<T, Object, Flowable<T>> implements FlowableSubscriber<T>, Subscription, Runnable {
        static final Object NEXT = new Object();
        final int bufferSize;
        final Scheduler scheduler;
        volatile boolean terminated;
        final SequentialDisposable timer;
        final long timespan;
        final TimeUnit unit;
        Subscription upstream;
        UnicastProcessor<T> window;

        WindowExactUnboundedSubscriber(Subscriber<? super Flowable<T>> subscriber, long j, TimeUnit timeUnit, Scheduler scheduler, int i) {
            super(subscriber, new MpscLinkedQueue());
            this.timer = new SequentialDisposable();
            this.timespan = j;
            this.unit = timeUnit;
            this.scheduler = scheduler;
            this.bufferSize = i;
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.window = UnicastProcessor.create(this.bufferSize);
                Subscriber<? super V> subscriber = this.downstream;
                subscriber.onSubscribe(this);
                long jRequested = requested();
                if (jRequested != 0) {
                    subscriber.onNext(this.window);
                    if (jRequested != Long.MAX_VALUE) {
                        produced(1L);
                    }
                    if (this.cancelled) {
                        return;
                    }
                    SequentialDisposable sequentialDisposable = this.timer;
                    Scheduler scheduler = this.scheduler;
                    long j = this.timespan;
                    if (sequentialDisposable.replace(scheduler.schedulePeriodicallyDirect(this, j, j, this.unit))) {
                        subscription.request(Long.MAX_VALUE);
                        return;
                    }
                    return;
                }
                this.cancelled = true;
                subscription.cancel();
                subscriber.onError(new MissingBackpressureException("Could not deliver first window due to lack of requests."));
            }
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.terminated) {
                return;
            }
            if (fastEnter()) {
                this.window.onNext(t);
                if (leave(-1) == 0) {
                    return;
                }
            } else {
                this.queue.offer((U) NotificationLite.next(t));
                if (!enter()) {
                    return;
                }
            }
            drainLoop();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            if (enter()) {
                drainLoop();
            }
            this.downstream.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            if (enter()) {
                drainLoop();
            }
            this.downstream.onComplete();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            requested(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // java.lang.Runnable
        public void run() {
            if (this.cancelled) {
                this.terminated = true;
            }
            this.queue.offer((U) NEXT);
            if (enter()) {
                drainLoop();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v0, types: [io.reactivex.processors.UnicastProcessor<T>] */
        /* JADX WARN: Type inference fix 'apply assigned field type' failed
        java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
        	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
        	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
        	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
         */
        void drainLoop() {
            SimpleQueue simpleQueue = this.queue;
            Subscriber<? super V> subscriber = this.downstream;
            UnicastProcessor<T> unicastProcessor = this.window;
            int iLeave = 1;
            while (true) {
                boolean z = this.terminated;
                boolean z2 = this.done;
                Object objPoll = simpleQueue.poll();
                if (z2 && (objPoll == null || objPoll == NEXT)) {
                    break;
                }
                if (objPoll != null) {
                    if (objPoll == NEXT) {
                        unicastProcessor.onComplete();
                        if (!z) {
                            unicastProcessor = (UnicastProcessor<T>) UnicastProcessor.create(this.bufferSize);
                            this.window = unicastProcessor;
                            long jRequested = requested();
                            if (jRequested != 0) {
                                subscriber.onNext(unicastProcessor);
                                if (jRequested != Long.MAX_VALUE) {
                                    produced(1L);
                                }
                            } else {
                                this.window = null;
                                this.queue.clear();
                                this.upstream.cancel();
                                subscriber.onError(new MissingBackpressureException("Could not deliver first window due to lack of requests."));
                                this.timer.dispose();
                                return;
                            }
                        } else {
                            this.upstream.cancel();
                        }
                    } else {
                        unicastProcessor.onNext(NotificationLite.getValue(objPoll));
                    }
                } else {
                    iLeave = leave(-iLeave);
                    if (iLeave == 0) {
                        return;
                    }
                }
            }
            this.window = null;
            simpleQueue.clear();
            Throwable th = this.error;
            if (th != null) {
                unicastProcessor.onError(th);
            } else {
                unicastProcessor.onComplete();
            }
            this.timer.dispose();
        }
    }

    static final class WindowExactBoundedSubscriber<T> extends QueueDrainSubscriber<T, Object, Flowable<T>> implements Subscription {
        final int bufferSize;
        long count;
        final long maxSize;
        long producerIndex;
        final boolean restartTimerOnMaxSize;
        final Scheduler scheduler;
        volatile boolean terminated;
        final SequentialDisposable timer;
        final long timespan;
        final TimeUnit unit;
        Subscription upstream;
        UnicastProcessor<T> window;
        final Scheduler.Worker worker;

        WindowExactBoundedSubscriber(Subscriber<? super Flowable<T>> subscriber, long j, TimeUnit timeUnit, Scheduler scheduler, int i, long j2, boolean z) {
            super(subscriber, new MpscLinkedQueue());
            this.timer = new SequentialDisposable();
            this.timespan = j;
            this.unit = timeUnit;
            this.scheduler = scheduler;
            this.bufferSize = i;
            this.maxSize = j2;
            this.restartTimerOnMaxSize = z;
            if (z) {
                this.worker = scheduler.createWorker();
            } else {
                this.worker = null;
            }
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            Disposable disposableSchedulePeriodicallyDirect;
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                Subscriber<? super V> subscriber = this.downstream;
                subscriber.onSubscribe(this);
                if (this.cancelled) {
                    return;
                }
                UnicastProcessor<T> unicastProcessorCreate = UnicastProcessor.create(this.bufferSize);
                this.window = unicastProcessorCreate;
                long jRequested = requested();
                if (jRequested != 0) {
                    subscriber.onNext(unicastProcessorCreate);
                    if (jRequested != Long.MAX_VALUE) {
                        produced(1L);
                    }
                    ConsumerIndexHolder consumerIndexHolder = new ConsumerIndexHolder(this.producerIndex, this);
                    if (this.restartTimerOnMaxSize) {
                        Scheduler.Worker worker = this.worker;
                        long j = this.timespan;
                        disposableSchedulePeriodicallyDirect = worker.schedulePeriodically(consumerIndexHolder, j, j, this.unit);
                    } else {
                        Scheduler scheduler = this.scheduler;
                        long j2 = this.timespan;
                        disposableSchedulePeriodicallyDirect = scheduler.schedulePeriodicallyDirect(consumerIndexHolder, j2, j2, this.unit);
                    }
                    if (this.timer.replace(disposableSchedulePeriodicallyDirect)) {
                        subscription.request(Long.MAX_VALUE);
                        return;
                    }
                    return;
                }
                this.cancelled = true;
                subscription.cancel();
                subscriber.onError(new MissingBackpressureException("Could not deliver initial window due to lack of requests."));
            }
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.terminated) {
                return;
            }
            if (fastEnter()) {
                UnicastProcessor<T> unicastProcessor = this.window;
                unicastProcessor.onNext(t);
                long j = this.count + 1;
                if (j >= this.maxSize) {
                    this.producerIndex++;
                    this.count = 0L;
                    unicastProcessor.onComplete();
                    long jRequested = requested();
                    if (jRequested != 0) {
                        UnicastProcessor<T> unicastProcessorCreate = UnicastProcessor.create(this.bufferSize);
                        this.window = unicastProcessorCreate;
                        this.downstream.onNext(unicastProcessorCreate);
                        if (jRequested != Long.MAX_VALUE) {
                            produced(1L);
                        }
                        if (this.restartTimerOnMaxSize) {
                            this.timer.get().dispose();
                            Scheduler.Worker worker = this.worker;
                            ConsumerIndexHolder consumerIndexHolder = new ConsumerIndexHolder(this.producerIndex, this);
                            long j2 = this.timespan;
                            this.timer.replace(worker.schedulePeriodically(consumerIndexHolder, j2, j2, this.unit));
                        }
                    } else {
                        this.window = null;
                        this.upstream.cancel();
                        this.downstream.onError(new MissingBackpressureException("Could not deliver window due to lack of requests"));
                        disposeTimer();
                        return;
                    }
                } else {
                    this.count = j;
                }
                if (leave(-1) == 0) {
                    return;
                }
            } else {
                this.queue.offer((U) NotificationLite.next(t));
                if (!enter()) {
                    return;
                }
            }
            drainLoop();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            if (enter()) {
                drainLoop();
            }
            this.downstream.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            if (enter()) {
                drainLoop();
            }
            this.downstream.onComplete();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            requested(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
        }

        public void disposeTimer() {
            this.timer.dispose();
            Scheduler.Worker worker = this.worker;
            if (worker != null) {
                worker.dispose();
            }
        }

        /* JADX WARN: Code duplicated, block: B:33:0x0068  */
        /* JADX WARN: Code duplicated, block: B:35:0x007d  */
        /* JADX WARN: Code duplicated, block: B:37:0x0084  */
        /* JADX WARN: Code duplicated, block: B:59:0x0088 A[SYNTHETIC] */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference fix 'apply assigned field type' failed
        java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
        	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
        	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
        	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
         */
        void drainLoop() {
            UnicastProcessor<T> unicastProcessorCreate;
            long jRequested;
            SimpleQueue simpleQueue = this.queue;
            Subscriber<? super V> subscriber = this.downstream;
            UnicastProcessor<T> unicastProcessor = this.window;
            int iLeave = 1;
            while (!this.terminated) {
                boolean z = this.done;
                Object objPoll = simpleQueue.poll();
                boolean z2 = objPoll == null;
                boolean z3 = objPoll instanceof ConsumerIndexHolder;
                if (z && (z2 || z3)) {
                    this.window = null;
                    simpleQueue.clear();
                    Throwable th = this.error;
                    if (th != null) {
                        ((UnicastProcessor) unicastProcessor).onError(th);
                    } else {
                        ((UnicastProcessor) unicastProcessor).onComplete();
                    }
                    disposeTimer();
                    return;
                }
                if (z2) {
                    iLeave = leave(-iLeave);
                    if (iLeave == 0) {
                        return;
                    }
                } else {
                    int i = iLeave;
                    if (z3) {
                        ConsumerIndexHolder consumerIndexHolder = (ConsumerIndexHolder) objPoll;
                        if (this.restartTimerOnMaxSize) {
                            unicastProcessor = unicastProcessor;
                            if (this.producerIndex == consumerIndexHolder.index) {
                                ((UnicastProcessor) unicastProcessor).onComplete();
                                this.count = 0L;
                                unicastProcessorCreate = UnicastProcessor.create(this.bufferSize);
                                this.window = unicastProcessorCreate;
                                jRequested = requested();
                                if (jRequested != 0) {
                                    subscriber.onNext(unicastProcessorCreate);
                                    unicastProcessor = unicastProcessorCreate;
                                    if (jRequested != Long.MAX_VALUE) {
                                        produced(1L);
                                        unicastProcessor = unicastProcessorCreate;
                                    }
                                } else {
                                    this.window = null;
                                    this.queue.clear();
                                    this.upstream.cancel();
                                    subscriber.onError(new MissingBackpressureException("Could not deliver first window due to lack of requests."));
                                    disposeTimer();
                                    return;
                                }
                            }
                        } else {
                            ((UnicastProcessor) unicastProcessor).onComplete();
                            this.count = 0L;
                            unicastProcessorCreate = UnicastProcessor.create(this.bufferSize);
                            this.window = unicastProcessorCreate;
                            jRequested = requested();
                            if (jRequested != 0) {
                                subscriber.onNext(unicastProcessorCreate);
                                unicastProcessor = unicastProcessorCreate;
                                if (jRequested != Long.MAX_VALUE) {
                                    produced(1L);
                                    unicastProcessor = unicastProcessorCreate;
                                }
                            } else {
                                this.window = null;
                                this.queue.clear();
                                this.upstream.cancel();
                                subscriber.onError(new MissingBackpressureException("Could not deliver first window due to lack of requests."));
                                disposeTimer();
                                return;
                            }
                        }
                    } else {
                        ((UnicastProcessor) unicastProcessor).onNext(NotificationLite.getValue(objPoll));
                        long j = this.count + 1;
                        if (j >= this.maxSize) {
                            this.producerIndex++;
                            this.count = 0L;
                            ((UnicastProcessor) unicastProcessor).onComplete();
                            long jRequested2 = requested();
                            if (jRequested2 != 0) {
                                UnicastProcessor<T> unicastProcessorCreate2 = UnicastProcessor.create(this.bufferSize);
                                this.window = unicastProcessorCreate2;
                                this.downstream.onNext(unicastProcessorCreate2);
                                if (jRequested2 != Long.MAX_VALUE) {
                                    produced(1L);
                                }
                                unicastProcessor = unicastProcessorCreate2;
                                if (this.restartTimerOnMaxSize) {
                                    this.timer.get().dispose();
                                    Scheduler.Worker worker = this.worker;
                                    ConsumerIndexHolder consumerIndexHolder2 = new ConsumerIndexHolder(this.producerIndex, this);
                                    long j2 = this.timespan;
                                    this.timer.replace(worker.schedulePeriodically(consumerIndexHolder2, j2, j2, this.unit));
                                    unicastProcessor = unicastProcessorCreate2;
                                }
                            } else {
                                this.window = null;
                                this.upstream.cancel();
                                this.downstream.onError(new MissingBackpressureException("Could not deliver window due to lack of requests"));
                                disposeTimer();
                                return;
                            }
                        } else {
                            this.count = j;
                            unicastProcessor = unicastProcessor;
                        }
                    }
                    iLeave = i;
                }
            }
            this.upstream.cancel();
            simpleQueue.clear();
            disposeTimer();
        }

        static final class ConsumerIndexHolder implements Runnable {
            final long index;
            final WindowExactBoundedSubscriber<?> parent;

            ConsumerIndexHolder(long j, WindowExactBoundedSubscriber<?> windowExactBoundedSubscriber) {
                this.index = j;
                this.parent = windowExactBoundedSubscriber;
            }

            @Override // java.lang.Runnable
            public void run() {
                WindowExactBoundedSubscriber<?> windowExactBoundedSubscriber = this.parent;
                if (!((WindowExactBoundedSubscriber) windowExactBoundedSubscriber).cancelled) {
                    ((WindowExactBoundedSubscriber) windowExactBoundedSubscriber).queue.offer(this);
                } else {
                    windowExactBoundedSubscriber.terminated = true;
                }
                if (windowExactBoundedSubscriber.enter()) {
                    windowExactBoundedSubscriber.drainLoop();
                }
            }
        }
    }

    static final class WindowSkipSubscriber<T> extends QueueDrainSubscriber<T, Object, Flowable<T>> implements Subscription, Runnable {
        final int bufferSize;
        volatile boolean terminated;
        final long timeskip;
        final long timespan;
        final TimeUnit unit;
        Subscription upstream;
        final List<UnicastProcessor<T>> windows;
        final Scheduler.Worker worker;

        WindowSkipSubscriber(Subscriber<? super Flowable<T>> subscriber, long j, long j2, TimeUnit timeUnit, Scheduler.Worker worker, int i) {
            super(subscriber, new MpscLinkedQueue());
            this.timespan = j;
            this.timeskip = j2;
            this.unit = timeUnit;
            this.worker = worker;
            this.bufferSize = i;
            this.windows = new LinkedList();
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                if (this.cancelled) {
                    return;
                }
                long jRequested = requested();
                if (jRequested != 0) {
                    UnicastProcessor<T> unicastProcessorCreate = UnicastProcessor.create(this.bufferSize);
                    this.windows.add(unicastProcessorCreate);
                    this.downstream.onNext(unicastProcessorCreate);
                    if (jRequested != Long.MAX_VALUE) {
                        produced(1L);
                    }
                    this.worker.schedule(new Completion(unicastProcessorCreate), this.timespan, this.unit);
                    Scheduler.Worker worker = this.worker;
                    long j = this.timeskip;
                    worker.schedulePeriodically(this, j, j, this.unit);
                    subscription.request(Long.MAX_VALUE);
                    return;
                }
                subscription.cancel();
                this.downstream.onError(new MissingBackpressureException("Could not emit the first window due to lack of requests"));
            }
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (fastEnter()) {
                Iterator<UnicastProcessor<T>> it = this.windows.iterator();
                while (it.hasNext()) {
                    it.next().onNext(t);
                }
                if (leave(-1) == 0) {
                    return;
                }
            } else {
                this.queue.offer((U) t);
                if (!enter()) {
                    return;
                }
            }
            drainLoop();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            if (enter()) {
                drainLoop();
            }
            this.downstream.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            if (enter()) {
                drainLoop();
            }
            this.downstream.onComplete();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            requested(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        void complete(UnicastProcessor<T> unicastProcessor) {
            this.queue.offer((U) new SubjectWork(unicastProcessor, false));
            if (enter()) {
                drainLoop();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference incomplete: some casts might be missing */
        void drainLoop() {
            SimpleQueue simpleQueue = this.queue;
            Subscriber<? super V> subscriber = this.downstream;
            List<UnicastProcessor<T>> list = this.windows;
            int iLeave = 1;
            while (!this.terminated) {
                boolean z = this.done;
                Object objPoll = simpleQueue.poll();
                boolean z2 = objPoll == null;
                boolean z3 = objPoll instanceof SubjectWork;
                if (z && (z2 || z3)) {
                    simpleQueue.clear();
                    Throwable th = this.error;
                    if (th != null) {
                        Iterator<UnicastProcessor<T>> it = list.iterator();
                        while (it.hasNext()) {
                            it.next().onError(th);
                        }
                    } else {
                        Iterator<UnicastProcessor<T>> it2 = list.iterator();
                        while (it2.hasNext()) {
                            it2.next().onComplete();
                        }
                    }
                    list.clear();
                    this.worker.dispose();
                    return;
                }
                if (z2) {
                    iLeave = leave(-iLeave);
                    if (iLeave == 0) {
                        return;
                    }
                } else if (z3) {
                    SubjectWork subjectWork = (SubjectWork) objPoll;
                    if (subjectWork.open) {
                        if (!this.cancelled) {
                            long jRequested = requested();
                            if (jRequested != 0) {
                                UnicastProcessor<T> unicastProcessorCreate = UnicastProcessor.create(this.bufferSize);
                                list.add(unicastProcessorCreate);
                                subscriber.onNext(unicastProcessorCreate);
                                if (jRequested != Long.MAX_VALUE) {
                                    produced(1L);
                                }
                                this.worker.schedule(new Completion(unicastProcessorCreate), this.timespan, this.unit);
                            } else {
                                subscriber.onError(new MissingBackpressureException("Can't emit window due to lack of requests"));
                            }
                        }
                    } else {
                        list.remove(subjectWork.w);
                        subjectWork.w.onComplete();
                        if (list.isEmpty() && this.cancelled) {
                            this.terminated = true;
                        }
                    }
                } else {
                    Iterator<UnicastProcessor<T>> it3 = list.iterator();
                    while (it3.hasNext()) {
                        it3.next().onNext(objPoll);
                    }
                }
            }
            this.upstream.cancel();
            simpleQueue.clear();
            list.clear();
            this.worker.dispose();
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // java.lang.Runnable
        public void run() {
            Object subjectWork = new SubjectWork(UnicastProcessor.create(this.bufferSize), true);
            if (!this.cancelled) {
                this.queue.offer((U) subjectWork);
            }
            if (enter()) {
                drainLoop();
            }
        }

        static final class SubjectWork<T> {
            final boolean open;
            final UnicastProcessor<T> w;

            SubjectWork(UnicastProcessor<T> unicastProcessor, boolean z) {
                this.w = unicastProcessor;
                this.open = z;
            }
        }

        final class Completion implements Runnable {
            private final UnicastProcessor<T> processor;

            Completion(UnicastProcessor<T> unicastProcessor) {
                this.processor = unicastProcessor;
            }

            @Override // java.lang.Runnable
            public void run() {
                WindowSkipSubscriber.this.complete(this.processor);
            }
        }
    }
}
