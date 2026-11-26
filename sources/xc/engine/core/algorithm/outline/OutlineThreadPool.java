package xc.engine.core.algorithm.outline;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes6.dex */
public class OutlineThreadPool {
    public ThreadPoolExecutor mExecutor;

    public static class INSTANCE {
        public static final OutlineThreadPool POOL = new OutlineThreadPool();
    }

    public class OutlineThreadFactory implements ThreadFactory {
        public OutlineThreadFactory() {
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName("Outline-" + hashCode());
            return thread;
        }
    }

    public OutlineThreadPool() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 3, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), new OutlineThreadFactory());
        this.mExecutor = threadPoolExecutor;
        threadPoolExecutor.allowCoreThreadTimeOut(true);
    }
}
