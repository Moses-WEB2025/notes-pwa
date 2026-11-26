package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.subscribers.BasicFuseableConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableSubscriber;
import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes5.dex */
public final class FlowableFilter<T> extends AbstractFlowableWithUpstream<T, T> {
    final Predicate<? super T> predicate;

    public FlowableFilter(Flowable<T> flowable, Predicate<? super T> predicate) {
        super(flowable);
        this.predicate = predicate;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        if (subscriber instanceof ConditionalSubscriber) {
            this.source.subscribe((FlowableSubscriber) new FilterConditionalSubscriber((ConditionalSubscriber) subscriber, this.predicate));
        } else {
            this.source.subscribe((FlowableSubscriber) new FilterSubscriber(subscriber, this.predicate));
        }
    }

    static final class FilterSubscriber<T> extends BasicFuseableSubscriber<T, T> implements ConditionalSubscriber<T> {
        final Predicate<? super T> filter;

        FilterSubscriber(Subscriber<? super T> subscriber, Predicate<? super T> predicate) {
            super(subscriber);
            this.filter = predicate;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (tryOnNext(t)) {
                return;
            }
            this.upstream.request(1L);
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // io.reactivex.internal.fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.done) {
                return false;
            }
            if (this.sourceMode != 0) {
                this.downstream.onNext(null);
                return true;
            }
            try {
                boolean zTest = this.filter.test(t);
                if (zTest) {
                    this.downstream.onNext((Object) t);
                }
                return zTest;
            } catch (Throwable th) {
                fail(th);
                return true;
            }
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public T poll() throws Exception {
            QueueSubscription<T> queueSubscription = this.qs;
            Predicate<? super T> predicate = this.filter;
            while (true) {
                T tPoll = queueSubscription.poll();
                if (tPoll == null) {
                    return null;
                }
                if (predicate.test(tPoll)) {
                    return tPoll;
                }
                if (this.sourceMode == 2) {
                    queueSubscription.request(1L);
                }
            }
        }
    }

    static final class FilterConditionalSubscriber<T> extends BasicFuseableConditionalSubscriber<T, T> {
        final Predicate<? super T> filter;

        FilterConditionalSubscriber(ConditionalSubscriber<? super T> conditionalSubscriber, Predicate<? super T> predicate) {
            super(conditionalSubscriber);
            this.filter = predicate;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (tryOnNext(t)) {
                return;
            }
            this.upstream.request(1L);
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // io.reactivex.internal.fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.done) {
                return false;
            }
            if (this.sourceMode != 0) {
                return this.downstream.tryOnNext(null);
            }
            try {
                return this.filter.test(t) && this.downstream.tryOnNext((Object) t);
            } catch (Throwable th) {
                fail(th);
                return true;
            }
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public T poll() throws Exception {
            QueueSubscription<T> queueSubscription = this.qs;
            Predicate<? super T> predicate = this.filter;
            while (true) {
                T tPoll = queueSubscription.poll();
                if (tPoll == null) {
                    return null;
                }
                if (predicate.test(tPoll)) {
                    return tPoll;
                }
                if (this.sourceMode == 2) {
                    queueSubscription.request(1L);
                }
            }
        }
    }
}
