package com.xiaomi.e2ee.appkey;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/* JADX INFO: loaded from: classes4.dex */
public class AppKeyCallBackPool {
    private static final Queue<OnGetAppKeyCallback> sGetAppKeyCallBackPool = new ConcurrentLinkedQueue();

    public static OnGetAppKeyCallback getBinder() {
        OnGetAppKeyCallback onGetAppKeyCallbackPoll = sGetAppKeyCallBackPool.poll();
        return onGetAppKeyCallbackPoll == null ? new OnGetAppKeyCallback() : onGetAppKeyCallbackPoll;
    }

    public static void recycle(OnGetAppKeyCallback onGetAppKeyCallback) {
        sGetAppKeyCallBackPool.offer(onGetAppKeyCallback);
    }
}
