package com.miui.notes.tool;

import com.miui.common.tool.async.TaskManager;

/* JADX INFO: loaded from: classes4.dex */
public class TaskManagerUtils {
    private static final int DEFAULT_QUEUE_SIZE = 10;
    private static final int DEFAULT_THREAD_SIZE = 3;

    private static class TaskManagerHolder {
        static TaskManager INSTANCE = new TaskManager(10, 3, -1);

        private TaskManagerHolder() {
        }
    }

    public static TaskManager getTaskManager() {
        return TaskManagerHolder.INSTANCE;
    }
}
