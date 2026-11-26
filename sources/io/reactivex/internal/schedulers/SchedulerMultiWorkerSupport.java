package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;

/* JADX INFO: loaded from: classes5.dex */
public interface SchedulerMultiWorkerSupport {

    public interface WorkerCallback {
        void onWorker(int i, Scheduler.Worker worker);
    }

    void createWorkers(int i, WorkerCallback workerCallback);
}
