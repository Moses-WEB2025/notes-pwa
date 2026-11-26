package com.xiaomi.onetrack.c;

import android.os.Handler;
import android.os.HandlerThread;

/* JADX INFO: loaded from: classes4.dex */
public class c {
    private static final String a = "DbExecutor";
    private static String b = "onetrack_db";
    private static Handler c;

    private static void a() {
        if (c == null) {
            synchronized (c.class) {
                if (c == null) {
                    HandlerThread handlerThread = new HandlerThread(b);
                    handlerThread.start();
                    c = new Handler(handlerThread.getLooper());
                }
            }
        }
    }

    public static void a(Runnable runnable) {
        try {
            a();
            c.post(runnable);
        } catch (Throwable th) {
            com.xiaomi.onetrack.util.r.b(a, th.getMessage());
        }
    }

    public static void a(Runnable runnable, long j) {
        try {
            a();
            c.postDelayed(runnable, j);
        } catch (Throwable th) {
            com.xiaomi.onetrack.util.r.b(a, th.getMessage());
        }
    }
}
