package com.xiaomi.micloudsdk.utils;

import android.os.Looper;

/* JADX INFO: loaded from: classes4.dex */
public class ThreadUtil {
    public static void ensureMainThread() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalArgumentException("You must call this method on the main thread");
        }
    }

    public static void ensureWorkerThread() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalArgumentException("You must call this method on the worker thread");
        }
    }
}
