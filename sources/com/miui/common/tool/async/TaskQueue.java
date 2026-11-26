package com.miui.common.tool.async;

import com.miui.notes.base.utils.Logger;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import miuix.core.util.concurrent.ConcurrentRingQueue;
import miuix.core.util.concurrent.Queue;

/* JADX INFO: loaded from: classes2.dex */
class TaskQueue implements Queue<Task<?>> {
    private final Queue<Task<?>> mHighQueue;
    private final Queue<Task<?>> mLowQueue;
    private final Queue<Task<?>> mNormalQueue;
    private final TaskManager mTaskManager;
    private final Object pauseLock = new Object();
    private final Semaphore mSemaphore = new Semaphore(0, true);
    private final AtomicBoolean mPause = new AtomicBoolean(false);

    @Override // miuix.core.util.concurrent.Queue
    public int getCapacity() {
        return -1;
    }

    public TaskQueue(TaskManager taskManager, int i) {
        this.mTaskManager = taskManager;
        this.mHighQueue = new ConcurrentRingQueue(i, true, true);
        this.mNormalQueue = new ConcurrentRingQueue(i, true, true);
        this.mLowQueue = new ConcurrentRingQueue(i, true, true);
    }

    /* JADX INFO: renamed from: com.miui.common.tool.async.TaskQueue$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$miui$common$tool$async$Task$Priority;

        static {
            int[] iArr = new int[Task.Priority.values().length];
            $SwitchMap$com$miui$common$tool$async$Task$Priority = iArr;
            try {
                iArr[Task.Priority.Low.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$common$tool$async$Task$Priority[Task.Priority.Normal.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$common$tool$async$Task$Priority[Task.Priority.High.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$common$tool$async$Task$Priority[Task.Priority.RealTime.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    @Override // miuix.core.util.concurrent.Queue
    public boolean put(Task<?> task) {
        boolean zPut;
        int i = AnonymousClass1.$SwitchMap$com$miui$common$tool$async$Task$Priority[task.getPriority().ordinal()];
        if (i == 1) {
            zPut = this.mLowQueue.put(task);
        } else if (i == 2) {
            zPut = this.mNormalQueue.put(task);
        } else if (i != 3) {
            if (i == 4) {
                Logger.INSTANCE.e("async", "Realtime task must NOT be put in Queue");
            }
            zPut = false;
        } else {
            zPut = this.mHighQueue.put(task);
        }
        if (zPut) {
            task.setStatus(Task.Status.Queued, null);
            this.mSemaphore.release();
        }
        return zPut;
    }

    private Task<?> getTask() {
        Task<?> task = this.mHighQueue.get();
        if (task == null) {
            task = this.mNormalQueue.get();
        }
        return task == null ? this.mLowQueue.get() : task;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // miuix.core.util.concurrent.Queue
    public Task<?> get() {
        Task<?> task = null;
        if (this.mTaskManager.isShutdown()) {
            while (this.mSemaphore.tryAcquire() && ((task = getTask()) == null || task.getStatus() == Task.Status.Canceled)) {
            }
            return task;
        }
        while (true) {
            Task<?> task2 = null;
            while (task2 == null) {
                try {
                    this.mSemaphore.acquire();
                    if (this.mPause.get()) {
                        synchronized (this.pauseLock) {
                            while (this.mPause.get()) {
                                try {
                                    try {
                                        this.pauseLock.wait();
                                    } catch (InterruptedException unused) {
                                        this.mSemaphore.release();
                                        return null;
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    }
                    task2 = getTask();
                    if (task2.getStatus() == Task.Status.Canceled) {
                    }
                } catch (InterruptedException unused2) {
                    return null;
                }
            }
            return task2;
        }
    }

    @Override // miuix.core.util.concurrent.Queue
    public boolean remove(Task<?> task) {
        if (!this.mSemaphore.tryAcquire()) {
            return false;
        }
        boolean zRemove = this.mHighQueue.remove(task);
        if (!zRemove) {
            zRemove = this.mNormalQueue.remove(task);
        }
        if (!zRemove) {
            zRemove = this.mLowQueue.remove(task);
        }
        if (!zRemove) {
            this.mSemaphore.release();
        }
        return zRemove;
    }

    @Override // miuix.core.util.concurrent.Queue
    public int remove(Queue.Predicate<Task<?>> predicate) {
        throw new RuntimeException("no support for this method");
    }

    @Override // miuix.core.util.concurrent.Queue
    public int clear() {
        int i = 0;
        while (this.mSemaphore.tryAcquire()) {
            Task<?> task = getTask();
            if (task != null && task.getStatus() != Task.Status.Canceled) {
                i++;
            }
        }
        return i;
    }

    @Override // miuix.core.util.concurrent.Queue
    public boolean isEmpty() {
        return this.mSemaphore.availablePermits() == 0;
    }

    public void pause() {
        synchronized (this.pauseLock) {
            this.mPause.set(true);
        }
    }

    public void resume() {
        synchronized (this.pauseLock) {
            this.mPause.set(false);
            this.pauseLock.notifyAll();
        }
    }
}
