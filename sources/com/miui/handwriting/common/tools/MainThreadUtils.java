package com.miui.handwriting.common.tools;

import android.os.Handler;
import android.os.Looper;

/* JADX INFO: loaded from: classes3.dex */
public class MainThreadUtils {
    private static final Handler sHandler = new Handler(Looper.getMainLooper());

    private MainThreadUtils() {
    }

    public static Handler getMainThreadHandler() {
        return sHandler;
    }

    public static void runOnUiThread(Runnable runnable) {
        getMainThreadHandler().post(runnable);
    }
}
