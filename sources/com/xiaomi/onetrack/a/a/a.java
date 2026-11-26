package com.xiaomi.onetrack.a.a;

import android.os.Handler;
import android.os.HandlerThread;
import com.xiaomi.onetrack.util.r;

/* JADX INFO: loaded from: classes4.dex */
public class a {
    private static final String a = "AdMonitorDbExecutor";
    private static String b = "onetrack_ad_monitor_db";
    private static Handler c;

    private static void a() {
        if (c == null) {
            synchronized (a.class) {
                if (c == null) {
                    HandlerThread handlerThread = new HandlerThread(b);
                    handlerThread.start();
                    c = new Handler(handlerThread.getLooper());
                }
            }
        }
    }

    public static void a(Runnable runnable) {
        a();
        c.post(runnable);
    }

    public static void a(Runnable runnable, long j) {
        try {
            a();
            c.postDelayed(runnable, j);
        } catch (Throwable th) {
            r.b(a, th.getMessage());
        }
    }
}
