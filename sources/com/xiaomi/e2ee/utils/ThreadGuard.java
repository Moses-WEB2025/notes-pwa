package com.xiaomi.e2ee.utils;

import android.os.Looper;

/* JADX INFO: loaded from: classes4.dex */
public class ThreadGuard {
    private ThreadGuard() {
        throw new IllegalStateException("ThreadGuard class");
    }

    public static void checkNotRunInMainThread(String str) {
        if (Looper.getMainLooper().getThread().equals(Thread.currentThread())) {
            throw new IllegalStateException(str);
        }
    }

    public static void checkRunInMainThread(String str) {
        if (!Looper.getMainLooper().getThread().equals(Thread.currentThread())) {
            throw new IllegalStateException(str);
        }
    }

    public static void checkRunInThread(Thread thread, String str) {
        if (!Thread.currentThread().equals(thread)) {
            throw new IllegalStateException(str);
        }
    }
}
