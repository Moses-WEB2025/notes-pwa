package io.reactivex.internal.util;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes5.dex */
public final class QueueDrainHelper {
    static final long COMPLETED_MASK = Long.MIN_VALUE;
    static final long REQUESTED_MASK = Long.MAX_VALUE;

    private QueueDrainHelper() {
        throw new IllegalStateException("No instances!");
    }

    public static <T, U> void drainMaxLoop(SimplePlainQueue<T> simplePlainQueue, Subscriber<? super U> subscriber, boolean z, Disposable disposable, QueueDrain<T, U> queueDrain) {
        int iLeave = 1;
        while (true) {
            boolean zDone = queueDrain.done();
            T tPoll = simplePlainQueue.poll();
            boolean z2 = tPoll == null;
            SimplePlainQueue<T> simplePlainQueue2 = simplePlainQueue;
            Subscriber<? super U> subscriber2 = subscriber;
            boolean z3 = z;
            QueueDrain<T, U> queueDrain2 = queueDrain;
            if (checkTerminated(zDone, z2, subscriber2, z3, simplePlainQueue2, queueDrain2)) {
                if (disposable != null) {
                    disposable.dispose();
                    return;
                }
                return;
            }
            if (!z2) {
                long jRequested = queueDrain2.requested();
                if (jRequested != 0) {
                    if (queueDrain2.accept(subscriber2, tPoll) && jRequested != Long.MAX_VALUE) {
                        queueDrain2.produced(1L);
                    }
                } else {
                    simplePlainQueue2.clear();
                    if (disposable != null) {
                        disposable.dispose();
                    }
                    subscriber2.onError(new MissingBackpressureException("Could not emit value due to lack of requests."));
                    return;
                }
            } else {
                iLeave = queueDrain2.leave(-iLeave);
                if (iLeave == 0) {
                    return;
                }
            }
            subscriber = subscriber2;
            z = z3;
            simplePlainQueue = simplePlainQueue2;
            queueDrain = queueDrain2;
        }
    }

    public static <T, U> boolean checkTerminated(boolean z, boolean z2, Subscriber<?> subscriber, boolean z3, SimpleQueue<?> simpleQueue, QueueDrain<T, U> queueDrain) {
        if (queueDrain.cancelled()) {
            simpleQueue.clear();
            return true;
        }
        if (!z) {
            return false;
        }
        if (z3) {
            if (!z2) {
                return false;
            }
            Throwable thError = queueDrain.error();
            if (thError != null) {
                subscriber.onError(thError);
            } else {
                subscriber.onComplete();
            }
            return true;
        }
        Throwable thError2 = queueDrain.error();
        if (thError2 != null) {
            simpleQueue.clear();
            subscriber.onError(thError2);
            return true;
        }
        if (!z2) {
            return false;
        }
        subscriber.onComplete();
        return true;
    }

    public static <T, U> void drainLoop(SimplePlainQueue<T> simplePlainQueue, Observer<? super U> observer, boolean z, Disposable disposable, ObservableQueueDrain<T, U> observableQueueDrain) {
        int iLeave = 1;
        while (true) {
            SimplePlainQueue<T> simplePlainQueue2 = simplePlainQueue;
            Observer<? super U> observer2 = observer;
            boolean z2 = z;
            Disposable disposable2 = disposable;
            ObservableQueueDrain<T, U> observableQueueDrain2 = observableQueueDrain;
            if (checkTerminated(observableQueueDrain.done(), simplePlainQueue.isEmpty(), observer2, z2, simplePlainQueue2, disposable2, observableQueueDrain2)) {
                return;
            }
            while (true) {
                boolean zDone = observableQueueDrain2.done();
                T tPoll = simplePlainQueue2.poll();
                boolean z3 = tPoll == null;
                boolean z4 = z3;
                if (checkTerminated(zDone, z3, observer2, z2, simplePlainQueue2, disposable2, observableQueueDrain2)) {
                    return;
                }
                if (z4) {
                    break;
                } else {
                    observableQueueDrain2.accept(observer2, tPoll);
                }
            }
            iLeave = observableQueueDrain2.leave(-iLeave);
            if (iLeave == 0) {
                return;
            }
            observer = observer2;
            z = z2;
            simplePlainQueue = simplePlainQueue2;
            disposable = disposable2;
            observableQueueDrain = observableQueueDrain2;
        }
    }

    public static <T, U> boolean checkTerminated(boolean z, boolean z2, Observer<?> observer, boolean z3, SimpleQueue<?> simpleQueue, Disposable disposable, ObservableQueueDrain<T, U> observableQueueDrain) {
        if (observableQueueDrain.cancelled()) {
            simpleQueue.clear();
            disposable.dispose();
            return true;
        }
        if (!z) {
            return false;
        }
        if (z3) {
            if (!z2) {
                return false;
            }
            if (disposable != null) {
                disposable.dispose();
            }
            Throwable thError = observableQueueDrain.error();
            if (thError != null) {
                observer.onError(thError);
            } else {
                observer.onComplete();
            }
            return true;
        }
        Throwable thError2 = observableQueueDrain.error();
        if (thError2 != null) {
            simpleQueue.clear();
            if (disposable != null) {
                disposable.dispose();
            }
            observer.onError(thError2);
            return true;
        }
        if (!z2) {
            return false;
        }
        if (disposable != null) {
            disposable.dispose();
        }
        observer.onComplete();
        return true;
    }

    public static <T> SimpleQueue<T> createQueue(int i) {
        if (i < 0) {
            return new SpscLinkedArrayQueue(-i);
        }
        return new SpscArrayQueue(i);
    }

    public static void request(Subscription subscription, int i) {
        subscription.request(i < 0 ? Long.MAX_VALUE : i);
    }

    public static <T> boolean postCompleteRequest(long j, Subscriber<? super T> subscriber, Queue<T> queue, AtomicLong atomicLong, BooleanSupplier booleanSupplier) {
        long j2;
        do {
            j2 = atomicLong.get();
        } while (!atomicLong.compareAndSet(j2, BackpressureHelper.addCap(Long.MAX_VALUE & j2, j) | (j2 & Long.MIN_VALUE)));
        if (j2 != Long.MIN_VALUE) {
            return false;
        }
        postCompleteDrain(j | Long.MIN_VALUE, subscriber, queue, atomicLong, booleanSupplier);
        return true;
    }

    static boolean isCancelled(BooleanSupplier booleanSupplier) {
        try {
            return booleanSupplier.getAsBoolean();
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            return true;
        }
    }

    static <T> boolean postCompleteDrain(long j, Subscriber<? super T> subscriber, Queue<T> queue, AtomicLong atomicLong, BooleanSupplier booleanSupplier) {
        long j2 = j & Long.MIN_VALUE;
        while (true) {
            if (j2 != j) {
                if (isCancelled(booleanSupplier)) {
                    return true;
                }
                T tPoll = queue.poll();
                if (tPoll == null) {
                    subscriber.onComplete();
                    return true;
                }
                subscriber.onNext(tPoll);
                j2++;
            } else {
                if (isCancelled(booleanSupplier)) {
                    return true;
                }
                if (queue.isEmpty()) {
                    subscriber.onComplete();
                    return true;
                }
                j = atomicLong.get();
                if (j == j2) {
                    long jAddAndGet = atomicLong.addAndGet(-(j2 & Long.MAX_VALUE));
                    if ((Long.MAX_VALUE & jAddAndGet) == 0) {
                        return false;
                    }
                    j2 = jAddAndGet & Long.MIN_VALUE;
                    j = jAddAndGet;
                } else {
                    continue;
                }
            }
        }
    }

    public static <T> void postComplete(Subscriber<? super T> subscriber, Queue<T> queue, AtomicLong atomicLong, BooleanSupplier booleanSupplier) {
        long j;
        long j2;
        if (queue.isEmpty()) {
            subscriber.onComplete();
            return;
        }
        if (postCompleteDrain(atomicLong.get(), subscriber, queue, atomicLong, booleanSupplier)) {
            return;
        }
        do {
            j = atomicLong.get();
            if ((j & Long.MIN_VALUE) != 0) {
                return;
            } else {
                j2 = j | Long.MIN_VALUE;
            }
        } while (!atomicLong.compareAndSet(j, j2));
        if (j != 0) {
            postCompleteDrain(j2, subscriber, queue, atomicLong, booleanSupplier);
        }
    }
}
