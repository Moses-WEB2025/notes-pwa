package com.xiaomi.accountsdk.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes4.dex */
public class PassportExecutors {
    public static ExecutorService newFixedThreadPool(int i, final String str) {
        return Executors.newFixedThreadPool(i, new ThreadFactory() { // from class: com.xiaomi.accountsdk.utils.PassportExecutors.1
            private final AtomicInteger mCount = new AtomicInteger(1);

            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, str + " #" + this.mCount.getAndIncrement());
            }
        });
    }
}
