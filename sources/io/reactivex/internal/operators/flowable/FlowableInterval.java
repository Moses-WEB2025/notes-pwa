package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.schedulers.TrampolineScheduler;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes5.dex */
public final class FlowableInterval extends Flowable<Long> {
    final long initialDelay;
    final long period;
    final Scheduler scheduler;
    final TimeUnit unit;

    public FlowableInterval(long j, long j2, TimeUnit timeUnit, Scheduler scheduler) {
        this.initialDelay = j;
        this.period = j2;
        this.unit = timeUnit;
        this.scheduler = scheduler;
    }

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
    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super Long> subscriber) {
        IntervalSubscriber intervalSubscriber = new IntervalSubscriber(subscriber);
        subscriber.onSubscribe(intervalSubscriber);
        Scheduler scheduler = this.scheduler;
        if (scheduler instanceof TrampolineScheduler) {
            Scheduler.Worker workerCreateWorker = scheduler.createWorker();
            intervalSubscriber.setResource(workerCreateWorker);
            workerCreateWorker.schedulePeriodically(intervalSubscriber, this.initialDelay, this.period, this.unit);
            return;
        }
        intervalSubscriber.setResource(scheduler.schedulePeriodicallyDirect(intervalSubscriber, this.initialDelay, this.period, this.unit));
    }

    static final class IntervalSubscriber extends AtomicLong implements Subscription, Runnable {
        private static final long serialVersionUID = -2809475196591179431L;
        long count;
        final Subscriber<? super Long> downstream;
        final AtomicReference<Disposable> resource = new AtomicReference<>();

        IntervalSubscriber(Subscriber<? super Long> subscriber) {
            this.downstream = subscriber;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this, j);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            DisposableHelper.dispose(this.resource);
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.resource.get() != DisposableHelper.DISPOSED) {
                if (get() != 0) {
                    Subscriber<? super Long> subscriber = this.downstream;
                    long j = this.count;
                    this.count = j + 1;
                    subscriber.onNext(Long.valueOf(j));
                    BackpressureHelper.produced(this, 1L);
                    return;
                }
                this.downstream.onError(new MissingBackpressureException("Can't deliver value " + this.count + " due to lack of requests"));
                DisposableHelper.dispose(this.resource);
            }
        }

        public void setResource(Disposable disposable) {
            DisposableHelper.setOnce(this.resource, disposable);
        }
    }
}
