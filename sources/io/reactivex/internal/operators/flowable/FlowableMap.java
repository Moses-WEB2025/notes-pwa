package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableSubscriber;
import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes5.dex */
public final class FlowableMap<T, U> extends AbstractFlowableWithUpstream<T, U> {
    final Function<? super T, ? extends U> mapper;

    public FlowableMap(Flowable<T> flowable, Function<? super T, ? extends U> function) {
        super(flowable);
        this.mapper = function;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super U> subscriber) {
        if (subscriber instanceof ConditionalSubscriber) {
            this.source.subscribe((FlowableSubscriber) new MapConditionalSubscriber((ConditionalSubscriber) subscriber, this.mapper));
        } else {
            this.source.subscribe((FlowableSubscriber) new MapSubscriber(subscriber, this.mapper));
        }
    }

    static final class MapSubscriber<T, U> extends BasicFuseableSubscriber<T, U> {
        final Function<? super T, ? extends U> mapper;

        MapSubscriber(Subscriber<? super U> subscriber, Function<? super T, ? extends U> function) {
            super(subscriber);
            this.mapper = function;
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                return;
            }
            if (this.sourceMode != 0) {
                this.downstream.onNext(null);
                return;
            }
            try {
                this.downstream.onNext((Object) ObjectHelper.requireNonNull(this.mapper.apply(t), "The mapper function returned a null value."));
            } catch (Throwable th) {
                fail(th);
            }
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public U poll() throws Exception {
            T tPoll = this.qs.poll();
            if (tPoll != null) {
                return (U) ObjectHelper.requireNonNull(this.mapper.apply(tPoll), "The mapper function returned a null value.");
            }
            return null;
        }
    }

    static final class MapConditionalSubscriber<T, U> extends BasicFuseableConditionalSubscriber<T, U> {
        final Function<? super T, ? extends U> mapper;

        MapConditionalSubscriber(ConditionalSubscriber<? super U> conditionalSubscriber, Function<? super T, ? extends U> function) {
            super(conditionalSubscriber);
            this.mapper = function;
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                return;
            }
            if (this.sourceMode != 0) {
                this.downstream.onNext(null);
                return;
            }
            try {
                this.downstream.onNext((Object) ObjectHelper.requireNonNull(this.mapper.apply(t), "The mapper function returned a null value."));
            } catch (Throwable th) {
                fail(th);
            }
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // io.reactivex.internal.fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.done) {
                return false;
            }
            try {
                return this.downstream.tryOnNext((Object) ObjectHelper.requireNonNull(this.mapper.apply(t), "The mapper function returned a null value."));
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
        public U poll() throws Exception {
            T tPoll = this.qs.poll();
            if (tPoll != null) {
                return (U) ObjectHelper.requireNonNull(this.mapper.apply(tPoll), "The mapper function returned a null value.");
            }
            return null;
        }
    }
}
