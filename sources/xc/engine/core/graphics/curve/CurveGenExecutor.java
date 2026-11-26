package xc.engine.core.graphics.curve;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes6.dex */
public class CurveGenExecutor {
    public final ExecutorService mExecutorService = Executors.newFixedThreadPool(4, new ThreadFactory() { // from class: xc.engine.core.graphics.curve.CurveGenExecutor.1
        private final AtomicInteger mThreadCounter = new AtomicInteger(0);

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, String.format("fitting_thread_%d", Integer.valueOf(this.mThreadCounter.getAndIncrement())));
        }
    });
}
