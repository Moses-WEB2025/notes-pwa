package io.reactivex.internal.observers;

import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: compiled from: QueueDrainObserver.java */
/* JADX INFO: loaded from: classes.dex */
class QueueDrainSubscriberWip extends QueueDrainSubscriberPad0 {
    final AtomicInteger wip = new AtomicInteger();

    QueueDrainSubscriberWip() {
    }
}
