package com.miui.common.tool.async;

import com.miui.common.tool.Logger;
import com.miui.common.tool.cache.Cache;
import com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment;

/* JADX INFO: loaded from: classes2.dex */
class TaskThread extends Thread {
    private final TaskQueue mQueue;
    private volatile boolean mShutdown = false;
    private final TaskManager mTaskManager;

    public TaskThread(TaskManager taskManager, TaskQueue taskQueue, int i) {
        this.mTaskManager = taskManager;
        this.mQueue = taskQueue;
        setName("TaskThread-" + i);
    }

    public static void runRealTimeTask(final TaskManager taskManager, final Task<?> task) {
        Thread thread = new Thread() { // from class: com.miui.common.tool.async.TaskThread.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                TaskThread.runTask(taskManager, this, task);
            }
        };
        thread.setName("TaskThread-RealTime");
        thread.start();
    }

    public void shutdown() {
        this.mShutdown = true;
        interrupt();
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        Logger.INSTANCE.d("TaskThread", "run");
        while (!this.mShutdown) {
            Task<?> task = this.mQueue.get();
            if (task != null) {
                if (task instanceof BaseNormalNoteEditFragment.ViewNoteLoadTask) {
                    setPriority(10);
                } else {
                    setPriority(5);
                }
                runTask(this.mTaskManager, this, task);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static void runTask(TaskManager taskManager, Thread thread, Task<?> task) {
        Object objDoLoad;
        Logger.INSTANCE.d("TaskThread", "runTask");
        if (taskManager == null || task == 0) {
            return;
        }
        task.setStatus(Task.Status.Executing, null);
        task.setThread(thread);
        try {
            objDoLoad = task.doLoad();
            try {
                task.setStatus(Task.Status.Done, objDoLoad == null ? new NullPointerException("result is null") : objDoLoad);
            } catch (Exception e) {
                e = e;
                task.setStatus(Task.Status.Done, new TaskExecutingException(e));
            }
        } catch (Exception e2) {
            e = e2;
            objDoLoad = null;
        }
        Cache<String, Object> cache = taskManager.getCache();
        if (cache != null && objDoLoad != null && (task instanceof Cacheable)) {
            Cacheable cacheable = (Cacheable) task;
            if (cacheable.getCacheKey() != null) {
                cache.put(cacheable.getCacheKey(), objDoLoad, cacheable.sizeOf(objDoLoad));
            }
        }
        task.setThread(null);
    }
}
