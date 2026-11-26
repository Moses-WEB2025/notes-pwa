package com.miui.handwriting.common.tools;

import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes3.dex */
public class ThreadPoolHelper {
    private static final int CORE_POOL_SIZE;
    private static final int CPU_COUNT;
    private static final int MAXIMUM_POOL_SIZE;
    private static ThreadPoolHelper mHelper;
    private ThreadPoolExecutor mExecutor;
    private ThreadPoolExecutor mLowPriorityExecutor;
    private Runnable mExecuteTaskRunnable = new Runnable() { // from class: com.miui.handwriting.common.tools.ThreadPoolHelper.1
        /* JADX INFO: Infinite loop detected, blocks: 8, insns: 0 */
        @Override // java.lang.Runnable
        public void run() {
            while (true) {
                try {
                    ThreadPoolHelper.this.mExecutor.execute((Runnable) ThreadPoolHelper.this.mTasks.take());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private LinkedBlockingQueue<Runnable> mTasks = new LinkedBlockingQueue<>();

    public interface ExecuteCallback {
        void onError(String str);

        void onSuccess();
    }

    static {
        int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        CPU_COUNT = iAvailableProcessors;
        CORE_POOL_SIZE = Math.max(2, Math.min(iAvailableProcessors - 1, 4));
        MAXIMUM_POOL_SIZE = (iAvailableProcessors * 2) + 1;
    }

    public static ThreadPoolHelper getInstance() {
        if (mHelper == null) {
            synchronized (ThreadPoolHelper.class) {
                if (mHelper == null) {
                    mHelper = new ThreadPoolHelper();
                }
            }
        }
        return mHelper;
    }

    private ThreadPoolHelper() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, 30L, TimeUnit.SECONDS, new ArrayBlockingQueue(4), new ThreadFactory() { // from class: com.miui.handwriting.common.tools.ThreadPoolHelper.2
            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable);
                thread.setPriority(Math.min(10, 8));
                return thread;
            }
        }, new RejectedExecutionHandler() { // from class: com.miui.handwriting.common.tools.ThreadPoolHelper.3
            @Override // java.util.concurrent.RejectedExecutionHandler
            public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor2) {
                ThreadPoolHelper.this.addTask(runnable);
            }
        });
        this.mExecutor = threadPoolExecutor;
        threadPoolExecutor.execute(this.mExecuteTaskRunnable);
        this.mLowPriorityExecutor = new ThreadPoolExecutor(1, 1, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue());
    }

    public void addTask(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        try {
            this.mTasks.put(runnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void execute(Runnable runnable) {
        if (runnable != null) {
            addTask(runnable);
        }
    }

    public void executeWithCallback(final Runnable runnable, final ExecuteCallback executeCallback) {
        execute(new Runnable() { // from class: com.miui.handwriting.common.tools.ThreadPoolHelper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ThreadPoolHelper.lambda$executeWithCallback$0(runnable, executeCallback);
            }
        });
    }

    static /* synthetic */ void lambda$executeWithCallback$0(Runnable runnable, final ExecuteCallback executeCallback) {
        try {
            CompletableFuture.runAsync(runnable).get(10L, TimeUnit.SECONDS);
            Objects.requireNonNull(executeCallback);
            MainThreadUtils.runOnUiThread(new Runnable() { // from class: com.miui.handwriting.common.tools.ThreadPoolHelper$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    executeCallback.onSuccess();
                }
            });
        } catch (Exception e) {
            executeCallback.onError(e.getMessage());
        }
    }
}
