package io.reactivex.internal.schedulers;

import androidx.lifecycle.AtomicReference$$ExternalSyntheticBackportWithForwarding0;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes5.dex */
public final class SchedulerPoolFactory {
    public static final boolean PURGE_ENABLED;
    static final String PURGE_ENABLED_KEY = "rx2.purge-enabled";
    public static final int PURGE_PERIOD_SECONDS;
    static final String PURGE_PERIOD_SECONDS_KEY = "rx2.purge-period-seconds";
    static final AtomicReference<ScheduledExecutorService> PURGE_THREAD = new AtomicReference<>();
    static final Map<ScheduledThreadPoolExecutor, Object> POOLS = new ConcurrentHashMap();

    private SchedulerPoolFactory() {
        throw new IllegalStateException("No instances!");
    }

    static {
        SystemPropertyAccessor systemPropertyAccessor = new SystemPropertyAccessor();
        boolean booleanProperty = getBooleanProperty(true, PURGE_ENABLED_KEY, true, true, systemPropertyAccessor);
        PURGE_ENABLED = booleanProperty;
        PURGE_PERIOD_SECONDS = getIntProperty(booleanProperty, PURGE_PERIOD_SECONDS_KEY, 1, 1, systemPropertyAccessor);
        start();
    }

    public static void start() {
        tryStart(PURGE_ENABLED);
    }

    static void tryStart(boolean z) {
        if (!z) {
            return;
        }
        while (true) {
            AtomicReference<ScheduledExecutorService> atomicReference = PURGE_THREAD;
            ScheduledExecutorService scheduledExecutorService = atomicReference.get();
            if (scheduledExecutorService != null) {
                return;
            }
            ScheduledExecutorService scheduledExecutorServiceNewScheduledThreadPool = Executors.newScheduledThreadPool(1, new RxThreadFactory("RxSchedulerPurge"));
            if (AtomicReference$$ExternalSyntheticBackportWithForwarding0.m(atomicReference, scheduledExecutorService, scheduledExecutorServiceNewScheduledThreadPool)) {
                ScheduledTask scheduledTask = new ScheduledTask();
                int i = PURGE_PERIOD_SECONDS;
                scheduledExecutorServiceNewScheduledThreadPool.scheduleAtFixedRate(scheduledTask, i, i, TimeUnit.SECONDS);
                return;
            }
            scheduledExecutorServiceNewScheduledThreadPool.shutdownNow();
        }
    }

    public static void shutdown() {
        ScheduledExecutorService andSet = PURGE_THREAD.getAndSet(null);
        if (andSet != null) {
            andSet.shutdownNow();
        }
        POOLS.clear();
    }

    static int getIntProperty(boolean z, String str, int i, int i2, Function<String, String> function) {
        if (!z) {
            return i2;
        }
        try {
            String strApply = function.apply(str);
            if (strApply != null) {
                return Integer.parseInt(strApply);
            }
        } catch (Throwable unused) {
        }
        return i;
    }

    static boolean getBooleanProperty(boolean z, String str, boolean z2, boolean z3, Function<String, String> function) {
        if (!z) {
            return z3;
        }
        try {
            String strApply = function.apply(str);
            if (strApply != null) {
                return "true".equals(strApply);
            }
        } catch (Throwable unused) {
        }
        return z2;
    }

    static final class SystemPropertyAccessor implements Function<String, String> {
        SystemPropertyAccessor() {
        }

        @Override // io.reactivex.functions.Function
        public String apply(String str) throws Exception {
            return System.getProperty(str);
        }
    }

    public static ScheduledExecutorService create(ThreadFactory threadFactory) {
        ScheduledExecutorService scheduledExecutorServiceNewScheduledThreadPool = Executors.newScheduledThreadPool(1, threadFactory);
        tryPutIntoPool(PURGE_ENABLED, scheduledExecutorServiceNewScheduledThreadPool);
        return scheduledExecutorServiceNewScheduledThreadPool;
    }

    static void tryPutIntoPool(boolean z, ScheduledExecutorService scheduledExecutorService) {
        if (z && (scheduledExecutorService instanceof ScheduledThreadPoolExecutor)) {
            POOLS.put((ScheduledThreadPoolExecutor) scheduledExecutorService, scheduledExecutorService);
        }
    }

    static final class ScheduledTask implements Runnable {
        ScheduledTask() {
        }

        @Override // java.lang.Runnable
        public void run() {
            for (ScheduledThreadPoolExecutor scheduledThreadPoolExecutor : new ArrayList(SchedulerPoolFactory.POOLS.keySet())) {
                if (scheduledThreadPoolExecutor.isShutdown()) {
                    SchedulerPoolFactory.POOLS.remove(scheduledThreadPoolExecutor);
                } else {
                    scheduledThreadPoolExecutor.purge();
                }
            }
        }
    }
}
