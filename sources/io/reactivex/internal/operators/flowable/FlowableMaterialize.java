package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Notification;
import io.reactivex.internal.subscribers.SinglePostCompleteSubscriber;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes5.dex */
public final class FlowableMaterialize<T> extends AbstractFlowableWithUpstream<T, Notification<T>> {
    public FlowableMaterialize(Flowable<T> flowable) {
        super(flowable);
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super Notification<T>> subscriber) {
        this.source.subscribe((FlowableSubscriber) new MaterializeSubscriber(subscriber));
    }

    static final class MaterializeSubscriber<T> extends SinglePostCompleteSubscriber<T, Notification<T>> {
        private static final long serialVersionUID = -3740826063558713822L;

        MaterializeSubscriber(Subscriber<? super Notification<T>> subscriber) {
            super(subscriber);
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.produced++;
            this.downstream.onNext(Notification.createOnNext(t));
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            complete(Notification.createOnError(th));
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            complete(Notification.createOnComplete());
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // io.reactivex.internal.subscribers.SinglePostCompleteSubscriber
        public void onDrop(Notification<T> notification) {
            if (notification.isOnError()) {
                RxJavaPlugins.onError(notification.getError());
            }
        }
    }
}
