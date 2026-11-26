package com.market.sdk;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes2.dex */
public class ThreadExecutors {
    private static final String TAG = "ThreadExecutors";

    private static class MyThreadFactory implements ThreadFactory {
        private static final AtomicInteger mPoolNumber = new AtomicInteger(1);
        private final ThreadGroup mGroup;
        private final String mNamePrefix;
        private final int mPriority;
        private final AtomicInteger mThreadNumber = new AtomicInteger(1);

        public MyThreadFactory(String str, int i) {
            SecurityManager securityManager = System.getSecurityManager();
            this.mGroup = securityManager != null ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
            this.mNamePrefix = "Po" + mPoolNumber.getAndIncrement() + "-" + str + "-";
            this.mPriority = i;
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(this.mGroup, runnable, this.mNamePrefix + this.mThreadNumber.getAndIncrement(), 0L);
            if (thread.isDaemon()) {
                thread.setDaemon(false);
            }
            int priority = thread.getPriority();
            int i = this.mPriority;
            if (priority != i) {
                thread.setPriority(i);
            }
            return thread;
        }
    }

    public static ThreadPoolExecutor newLowPriorityCachedThreadPool(int i, int i2, String str) {
        return newCachedThreadPool(i, i2, 2, str);
    }

    public static ThreadPoolExecutor newCachedThreadPool(int i, int i2, int i3, String str) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(i, i, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue(i2), new MyThreadFactory(str, i3), new ThreadPoolExecutor.DiscardOldestPolicy());
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        return threadPoolExecutor;
    }

    public static ThreadPoolExecutor newFixedThreadPool(int i, String str) {
        return newFixedThreadPool(i, i, 5, str);
    }

    public static ThreadPoolExecutor newFixedThreadPool(int i, int i2, int i3, String str) {
        return new ThreadPoolExecutor(i, i2, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), new MyThreadFactory(str, i3), new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
