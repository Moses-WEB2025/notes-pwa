package com.miui.notes.tool;

import android.os.Process;
import ch.qos.logback.core.CoreConstants;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes4.dex */
public class PriorityThreadFactory implements ThreadFactory {
    private final String mName;
    private final AtomicInteger mNumber = new AtomicInteger();
    private final int mPriority;

    public PriorityThreadFactory(String str, int i) {
        this.mName = str;
        this.mPriority = i;
    }

    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(Runnable runnable) {
        return new Thread(runnable, this.mName + CoreConstants.DASH_CHAR + this.mNumber.getAndIncrement()) { // from class: com.miui.notes.tool.PriorityThreadFactory.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                Process.setThreadPriority(PriorityThreadFactory.this.mPriority);
                super.run();
            }
        };
    }
}
