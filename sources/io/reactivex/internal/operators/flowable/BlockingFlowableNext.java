package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Notification;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subscribers.DisposableSubscriber;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;

/* JADX INFO: loaded from: classes5.dex */
public final class BlockingFlowableNext<T> implements Iterable<T> {
    final Publisher<? extends T> source;

    public BlockingFlowableNext(Publisher<? extends T> publisher) {
        this.source = publisher;
    }

    @Override // java.lang.Iterable
    public Iterator<T> iterator() {
        return new NextIterator(this.source, new NextSubscriber());
    }

    static final class NextIterator<T> implements Iterator<T> {
        private Throwable error;
        private boolean hasNext = true;
        private boolean isNextConsumed = true;
        private final Publisher<? extends T> items;
        private T next;
        private boolean started;
        private final NextSubscriber<T> subscriber;

        NextIterator(Publisher<? extends T> publisher, NextSubscriber<T> nextSubscriber) {
            this.items = publisher;
            this.subscriber = nextSubscriber;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            Throwable th = this.error;
            if (th != null) {
                throw ExceptionHelper.wrapOrThrow(th);
            }
            if (this.hasNext) {
                return !this.isNextConsumed || moveToNext();
            }
            return false;
        }

        private boolean moveToNext() {
            try {
                if (!this.started) {
                    this.started = true;
                    this.subscriber.setWaiting();
                    Flowable.fromPublisher(this.items).materialize().subscribe((FlowableSubscriber<? super Notification<T>>) this.subscriber);
                }
                Notification<T> notificationTakeNext = this.subscriber.takeNext();
                if (notificationTakeNext.isOnNext()) {
                    this.isNextConsumed = false;
                    this.next = notificationTakeNext.getValue();
                    return true;
                }
                this.hasNext = false;
                if (notificationTakeNext.isOnComplete()) {
                    return false;
                }
                if (notificationTakeNext.isOnError()) {
                    Throwable error = notificationTakeNext.getError();
                    this.error = error;
                    throw ExceptionHelper.wrapOrThrow(error);
                }
                throw new IllegalStateException("Should not reach here");
            } catch (InterruptedException e) {
                this.subscriber.dispose();
                this.error = e;
                throw ExceptionHelper.wrapOrThrow(e);
            }
        }

        @Override // java.util.Iterator
        public T next() {
            Throwable th = this.error;
            if (th != null) {
                throw ExceptionHelper.wrapOrThrow(th);
            }
            if (hasNext()) {
                this.isNextConsumed = true;
                return this.next;
            }
            throw new NoSuchElementException("No more elements");
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("Read only iterator");
        }
    }

    static final class NextSubscriber<T> extends DisposableSubscriber<Notification<T>> {
        private final BlockingQueue<Notification<T>> buf = new ArrayBlockingQueue(1);
        final AtomicInteger waiting = new AtomicInteger();

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
        }

        NextSubscriber() {
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            RxJavaPlugins.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Notification<T> notification) {
            if (this.waiting.getAndSet(0) == 1 || !notification.isOnNext()) {
                while (!this.buf.offer(notification)) {
                    Notification<T> notificationPoll = this.buf.poll();
                    if (notificationPoll != null && !notificationPoll.isOnNext()) {
                        notification = notificationPoll;
                    }
                }
            }
        }

        public Notification<T> takeNext() throws InterruptedException {
            setWaiting();
            BlockingHelper.verifyNonBlocking();
            return this.buf.take();
        }

        void setWaiting() {
            this.waiting.set(1);
        }
    }
}
