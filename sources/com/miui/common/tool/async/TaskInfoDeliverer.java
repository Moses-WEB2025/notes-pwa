package com.miui.common.tool.async;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.miui.notes.NoteApp;
import miuix.core.util.Pools;
import miuix.core.util.concurrent.ConcurrentRingQueue;
import miuix.core.util.concurrent.Queue;

/* JADX INFO: loaded from: classes2.dex */
class TaskInfoDeliverer {
    private static final Pools.Pool<TaskDeliveryInfo> TASK_DELIVERY_INFO_POOL = Pools.createSimplePool(new Pools.Manager<TaskDeliveryInfo>() { // from class: com.miui.common.tool.async.TaskInfoDeliverer.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // miuix.core.util.Pools.Manager
        public TaskDeliveryInfo createInstance() {
            return new TaskDeliveryInfo();
        }

        @Override // miuix.core.util.Pools.Manager
        public void onRelease(TaskDeliveryInfo taskDeliveryInfo) {
            taskDeliveryInfo.clear();
        }
    }, 8);
    private DeliverHandler mHandler = new DeliverHandler(NoteApp.getInstance().getMainLooper());
    private ConcurrentRingQueue<TaskDeliveryInfo> mTaskDeliveryInfoQueue = new ConcurrentRingQueue<>(20, true, true);
    private TaskManager mTaskManager;

    public TaskInfoDeliverer(TaskManager taskManager) {
        this.mTaskManager = taskManager;
    }

    public void setCallbackThread(boolean z) {
        Looper mainLooper = NoteApp.getInstance().getMainLooper();
        if (z && this.mHandler.getLooper() != mainLooper) {
            this.mHandler.getLooper().quit();
            this.mHandler = new DeliverHandler(mainLooper);
        } else {
            if (z || this.mHandler.getLooper() != mainLooper) {
                return;
            }
            Thread thread = new Thread() { // from class: com.miui.common.tool.async.TaskInfoDeliverer.2
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    Looper.prepare();
                    TaskInfoDeliverer.this.mHandler = new DeliverHandler(Looper.myLooper());
                    Looper.loop();
                }
            };
            thread.setName("TaskInfoDeliverer-Callback");
            thread.start();
        }
    }

    public void postDeliver(final Task<?> task, Task.Delivery delivery, Object obj) {
        TaskDeliveryInfo taskDeliveryInfoAcquire = TASK_DELIVERY_INFO_POOL.acquire();
        taskDeliveryInfoAcquire.task = task;
        taskDeliveryInfoAcquire.delivery = delivery;
        taskDeliveryInfoAcquire.info = obj;
        if (delivery == Task.Delivery.Result && obj == null) {
            this.mTaskDeliveryInfoQueue.remove(new Queue.Predicate<TaskDeliveryInfo>() { // from class: com.miui.common.tool.async.TaskInfoDeliverer.3
                @Override // miuix.core.util.concurrent.Queue.Predicate
                public boolean apply(TaskDeliveryInfo taskDeliveryInfo) {
                    return taskDeliveryInfo != null && taskDeliveryInfo.task == task;
                }
            });
        }
        this.mTaskDeliveryInfoQueue.put(taskDeliveryInfoAcquire);
        processAllDelivery();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processAllDelivery() {
        if (Looper.myLooper() == this.mHandler.getLooper()) {
            while (!this.mTaskDeliveryInfoQueue.isEmpty()) {
                TaskDeliveryInfo taskDeliveryInfo = this.mTaskDeliveryInfoQueue.get();
                if (taskDeliveryInfo != null) {
                    taskDeliveryInfo.task.deliver(this.mTaskManager, taskDeliveryInfo.delivery, taskDeliveryInfo.info);
                    TASK_DELIVERY_INFO_POOL.release(taskDeliveryInfo);
                }
            }
            return;
        }
        this.mHandler.post(this);
    }

    private static class DeliverHandler extends Handler {
        static final int CONTINUE = 0;

        public DeliverHandler(Looper looper) {
            super(looper);
        }

        public void post(TaskInfoDeliverer taskInfoDeliverer) {
            obtainMessage(0, taskInfoDeliverer).sendToTarget();
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            ((TaskInfoDeliverer) message.obj).processAllDelivery();
            message.obj = null;
        }
    }

    static class TaskDeliveryInfo {
        public Task.Delivery delivery;
        public Object info;
        public Task<?> task;

        TaskDeliveryInfo() {
        }

        public void clear() {
            this.task = null;
            this.delivery = null;
            this.info = null;
        }
    }
}
