package okhttp3.internal.concurrent;

import androidx.exifinterface.media.ExifInterface;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal._UtilCommonKt;
import okhttp3.internal._UtilJvmKt;

/* JADX INFO: compiled from: TaskRunner.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\b\u0005\u0018\u0000 -2\u00020\u0001:\u0003+,-B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0015\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0017H\u0000¢\u0006\u0002\b\u001eJ\u0010\u0010\u001f\u001a\u00020\u001c2\u0006\u0010 \u001a\u00020!H\u0002J \u0010\"\u001a\u00020\u001c2\u0006\u0010 \u001a\u00020!2\u0006\u0010#\u001a\u00020\u00122\u0006\u0010$\u001a\u00020\u0010H\u0002J\b\u0010%\u001a\u0004\u0018\u00010!J\b\u0010&\u001a\u00020\u001cH\u0002J\u0006\u0010'\u001a\u00020\u0017J\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00170)J\u0006\u0010*\u001a\u00020\u001cR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0016\u0010\u0004\u001a\u00020\u0005X\u0080\u0004¢\u0006\n\n\u0002\b\f\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006."}, d2 = {"Lokhttp3/internal/concurrent/TaskRunner;", "Lokhttp3/internal/concurrent/Lockable;", "backend", "Lokhttp3/internal/concurrent/TaskRunner$Backend;", "logger", "Ljava/util/logging/Logger;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokhttp3/internal/concurrent/TaskRunner$Backend;Ljava/util/logging/Logger;)V", "getBackend", "()Lokhttp3/internal/concurrent/TaskRunner$Backend;", "getLogger$okhttp", "()Ljava/util/logging/Logger;", "logger$1", "nextQueueName", "", "coordinatorWaiting", "", "coordinatorWakeUpAt", "", "executeCallCount", "runCallCount", "busyQueues", "", "Lokhttp3/internal/concurrent/TaskQueue;", "readyQueues", "runnable", "Ljava/lang/Runnable;", "kickCoordinator", "", "taskQueue", "kickCoordinator$okhttp", "beforeRun", "task", "Lokhttp3/internal/concurrent/Task;", "afterRun", "delayNanos", "completedNormally", "awaitTaskToRun", "startAnotherThread", "newQueue", "activeQueues", "", "cancelAll", "Backend", "RealBackend", "Companion", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class TaskRunner implements Lockable {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final TaskRunner INSTANCE;
    private static final Logger logger;
    private final Backend backend;
    private final List<TaskQueue> busyQueues;
    private boolean coordinatorWaiting;
    private long coordinatorWakeUpAt;
    private int executeCallCount;

    /* JADX INFO: renamed from: logger$1, reason: from kotlin metadata */
    private final Logger logger;
    private int nextQueueName;
    private final List<TaskQueue> readyQueues;
    private int runCallCount;
    private final Runnable runnable;

    /* JADX INFO: compiled from: TaskRunner.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0018\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0003H&J\"\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\f0\u000b\"\u0004\b\u0000\u0010\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\f0\u000bH&J\u0018\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0010H&¨\u0006\u0011À\u0006\u0003"}, d2 = {"Lokhttp3/internal/concurrent/TaskRunner$Backend;", "", "nanoTime", "", "coordinatorNotify", "", "taskRunner", "Lokhttp3/internal/concurrent/TaskRunner;", "coordinatorWait", "nanos", "decorate", "Ljava/util/concurrent/BlockingQueue;", ExifInterface.GPS_DIRECTION_TRUE, "queue", "execute", "runnable", "Ljava/lang/Runnable;", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface Backend {
        void coordinatorNotify(TaskRunner taskRunner);

        void coordinatorWait(TaskRunner taskRunner, long nanos);

        <T> BlockingQueue<T> decorate(BlockingQueue<T> queue);

        void execute(TaskRunner taskRunner, Runnable runnable);

        long nanoTime();
    }

    public TaskRunner(Backend backend, Logger logger2) {
        Intrinsics.checkNotNullParameter(backend, "backend");
        Intrinsics.checkNotNullParameter(logger2, "logger");
        this.backend = backend;
        this.logger = logger2;
        this.nextQueueName = 10000;
        this.busyQueues = new ArrayList();
        this.readyQueues = new ArrayList();
        this.runnable = new Runnable() { // from class: okhttp3.internal.concurrent.TaskRunner$runnable$1
            @Override // java.lang.Runnable
            public void run() {
                Task taskAwaitTaskToRun;
                long jNanoTime;
                Task taskAwaitTaskToRun2;
                TaskRunner taskRunner = this.this$0;
                synchronized (taskRunner) {
                    taskRunner.runCallCount++;
                    taskAwaitTaskToRun = taskRunner.awaitTaskToRun();
                }
                if (taskAwaitTaskToRun == null) {
                    return;
                }
                Thread threadCurrentThread = Thread.currentThread();
                String name = threadCurrentThread.getName();
                while (true) {
                    try {
                        threadCurrentThread.setName(taskAwaitTaskToRun.getName());
                        Logger logger3 = this.this$0.getLogger();
                        TaskQueue queue = taskAwaitTaskToRun.getQueue();
                        Intrinsics.checkNotNull(queue);
                        boolean zIsLoggable = logger3.isLoggable(Level.FINE);
                        if (zIsLoggable) {
                            jNanoTime = queue.getTaskRunner().getBackend().nanoTime();
                            TaskLoggerKt.log(logger3, taskAwaitTaskToRun, queue, "starting");
                        } else {
                            jNanoTime = -1;
                        }
                        try {
                            long jRunOnce = taskAwaitTaskToRun.runOnce();
                            if (zIsLoggable) {
                                TaskLoggerKt.log(logger3, taskAwaitTaskToRun, queue, "finished run in " + TaskLoggerKt.formatDuration(queue.getTaskRunner().getBackend().nanoTime() - jNanoTime));
                            }
                            TaskRunner taskRunner2 = this.this$0;
                            synchronized (taskRunner2) {
                                taskRunner2.afterRun(taskAwaitTaskToRun, jRunOnce, true);
                                taskAwaitTaskToRun2 = taskRunner2.awaitTaskToRun();
                            }
                            if (taskAwaitTaskToRun2 == null) {
                                threadCurrentThread.setName(name);
                                return;
                            }
                            taskAwaitTaskToRun = taskAwaitTaskToRun2;
                        } catch (Throwable th) {
                            if (zIsLoggable) {
                                TaskLoggerKt.log(logger3, taskAwaitTaskToRun, queue, "failed a run in " + TaskLoggerKt.formatDuration(queue.getTaskRunner().getBackend().nanoTime() - jNanoTime));
                            }
                            throw th;
                        }
                    } catch (Throwable th2) {
                        try {
                            TaskRunner taskRunner3 = this.this$0;
                            synchronized (taskRunner3) {
                                Task task = taskAwaitTaskToRun;
                                taskRunner3.afterRun(taskAwaitTaskToRun, -1L, false);
                                Unit unit = Unit.INSTANCE;
                                throw th2;
                            }
                        } catch (Throwable th3) {
                            threadCurrentThread.setName(name);
                            throw th3;
                        }
                    }
                }
            }
        };
    }

    public final Backend getBackend() {
        return this.backend;
    }

    public /* synthetic */ TaskRunner(Backend backend, Logger logger2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(backend, (i & 2) != 0 ? logger : logger2);
    }

    /* JADX INFO: renamed from: getLogger$okhttp, reason: from getter */
    public final Logger getLogger() {
        return this.logger;
    }

    public final void kickCoordinator$okhttp(TaskQueue taskQueue) {
        Intrinsics.checkNotNullParameter(taskQueue, "taskQueue");
        TaskRunner taskRunner = this;
        if (!_UtilJvmKt.assertionsEnabled || Thread.holdsLock(taskRunner)) {
            if (taskQueue.getActiveTask() == null) {
                if (!taskQueue.getFutureTasks$okhttp().isEmpty()) {
                    _UtilCommonKt.addIfAbsent(this.readyQueues, taskQueue);
                } else {
                    this.readyQueues.remove(taskQueue);
                }
            }
            if (this.coordinatorWaiting) {
                this.backend.coordinatorNotify(this);
                return;
            } else {
                startAnotherThread();
                return;
            }
        }
        throw new AssertionError("Thread " + Thread.currentThread().getName() + " MUST hold lock on " + taskRunner);
    }

    private final void beforeRun(Task task) {
        TaskRunner taskRunner = this;
        if (!_UtilJvmKt.assertionsEnabled || Thread.holdsLock(taskRunner)) {
            task.setNextExecuteNanoTime$okhttp(-1L);
            TaskQueue queue = task.getQueue();
            Intrinsics.checkNotNull(queue);
            queue.getFutureTasks$okhttp().remove(task);
            this.readyQueues.remove(queue);
            queue.setActiveTask$okhttp(task);
            this.busyQueues.add(queue);
            return;
        }
        throw new AssertionError("Thread " + Thread.currentThread().getName() + " MUST hold lock on " + taskRunner);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void afterRun(Task task, long delayNanos, boolean completedNormally) {
        TaskRunner taskRunner = this;
        if (!_UtilJvmKt.assertionsEnabled || Thread.holdsLock(taskRunner)) {
            TaskQueue queue = task.getQueue();
            Intrinsics.checkNotNull(queue);
            if (queue.getActiveTask() != task) {
                throw new IllegalStateException("Check failed.");
            }
            boolean cancelActiveTask$okhttp = queue.getCancelActiveTask();
            queue.setCancelActiveTask$okhttp(false);
            queue.setActiveTask$okhttp(null);
            this.busyQueues.remove(queue);
            if (delayNanos != -1 && !cancelActiveTask$okhttp && !queue.getShutdown()) {
                queue.scheduleAndDecide$okhttp(task, delayNanos, true);
            }
            if (queue.getFutureTasks$okhttp().isEmpty()) {
                return;
            }
            this.readyQueues.add(queue);
            if (completedNormally) {
                return;
            }
            startAnotherThread();
            return;
        }
        throw new AssertionError("Thread " + Thread.currentThread().getName() + " MUST hold lock on " + taskRunner);
    }

    public final Task awaitTaskToRun() {
        boolean z;
        TaskRunner taskRunner = this;
        if (!_UtilJvmKt.assertionsEnabled || Thread.holdsLock(taskRunner)) {
            while (!this.readyQueues.isEmpty()) {
                long jNanoTime = this.backend.nanoTime();
                Iterator<TaskQueue> it = this.readyQueues.iterator();
                long jMin = Long.MAX_VALUE;
                Task task = null;
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                    Task task2 = it.next().getFutureTasks$okhttp().get(0);
                    long jMax = Math.max(0L, task2.getNextExecuteNanoTime() - jNanoTime);
                    if (jMax > 0) {
                        jMin = Math.min(jMax, jMin);
                    } else {
                        if (task != null) {
                            z = true;
                            break;
                        }
                        task = task2;
                    }
                }
                if (task != null) {
                    beforeRun(task);
                    if (z || (!this.coordinatorWaiting && !this.readyQueues.isEmpty())) {
                        startAnotherThread();
                    }
                    return task;
                }
                if (this.coordinatorWaiting) {
                    if (jMin < this.coordinatorWakeUpAt - jNanoTime) {
                        this.backend.coordinatorNotify(this);
                    }
                    return null;
                }
                this.coordinatorWaiting = true;
                this.coordinatorWakeUpAt = jNanoTime + jMin;
                try {
                    try {
                        this.backend.coordinatorWait(this, jMin);
                    } catch (InterruptedException unused) {
                        cancelAll();
                    }
                    this.coordinatorWaiting = false;
                } catch (Throwable th) {
                    this.coordinatorWaiting = false;
                    throw th;
                }
            }
            return null;
        }
        throw new AssertionError("Thread " + Thread.currentThread().getName() + " MUST hold lock on " + taskRunner);
    }

    private final void startAnotherThread() {
        TaskRunner taskRunner = this;
        if (!_UtilJvmKt.assertionsEnabled || Thread.holdsLock(taskRunner)) {
            int i = this.executeCallCount;
            if (i > this.runCallCount) {
                return;
            }
            this.executeCallCount = i + 1;
            this.backend.execute(this, this.runnable);
            return;
        }
        throw new AssertionError("Thread " + Thread.currentThread().getName() + " MUST hold lock on " + taskRunner);
    }

    public final TaskQueue newQueue() {
        int i;
        synchronized (this) {
            i = this.nextQueueName;
            this.nextQueueName = i + 1;
        }
        return new TaskQueue(this, "Q" + i);
    }

    public final void cancelAll() {
        TaskRunner taskRunner = this;
        if (!_UtilJvmKt.assertionsEnabled || Thread.holdsLock(taskRunner)) {
            int size = this.busyQueues.size();
            while (true) {
                size--;
                if (-1 >= size) {
                    break;
                } else {
                    this.busyQueues.get(size).cancelAllAndDecide$okhttp();
                }
            }
            for (int size2 = this.readyQueues.size() - 1; -1 < size2; size2--) {
                TaskQueue taskQueue = this.readyQueues.get(size2);
                taskQueue.cancelAllAndDecide$okhttp();
                if (taskQueue.getFutureTasks$okhttp().isEmpty()) {
                    this.readyQueues.remove(size2);
                }
            }
            return;
        }
        throw new AssertionError("Thread " + Thread.currentThread().getName() + " MUST hold lock on " + taskRunner);
    }

    /* JADX INFO: compiled from: TaskRunner.kt */
    @Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010\u0010\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u000bH\u0016J\"\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00140\u0013\"\u0004\b\u0000\u0010\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00140\u0013H\u0016J\u0018\u0010\u0016\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0006\u0010\u0019\u001a\u00020\rR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u001a"}, d2 = {"Lokhttp3/internal/concurrent/TaskRunner$RealBackend;", "Lokhttp3/internal/concurrent/TaskRunner$Backend;", "threadFactory", "Ljava/util/concurrent/ThreadFactory;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/util/concurrent/ThreadFactory;)V", "executor", "Ljava/util/concurrent/ThreadPoolExecutor;", "getExecutor", "()Ljava/util/concurrent/ThreadPoolExecutor;", "nanoTime", "", "coordinatorNotify", "", "taskRunner", "Lokhttp3/internal/concurrent/TaskRunner;", "coordinatorWait", "nanos", "decorate", "Ljava/util/concurrent/BlockingQueue;", ExifInterface.GPS_DIRECTION_TRUE, "queue", "execute", "runnable", "Ljava/lang/Runnable;", "shutdown", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class RealBackend implements Backend {
        private final ThreadPoolExecutor executor;

        @Override // okhttp3.internal.concurrent.TaskRunner.Backend
        public <T> BlockingQueue<T> decorate(BlockingQueue<T> queue) {
            Intrinsics.checkNotNullParameter(queue, "queue");
            return queue;
        }

        public RealBackend(ThreadFactory threadFactory) {
            Intrinsics.checkNotNullParameter(threadFactory, "threadFactory");
            this.executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue(), threadFactory);
        }

        public final ThreadPoolExecutor getExecutor() {
            return this.executor;
        }

        @Override // okhttp3.internal.concurrent.TaskRunner.Backend
        public long nanoTime() {
            return System.nanoTime();
        }

        @Override // okhttp3.internal.concurrent.TaskRunner.Backend
        public void coordinatorNotify(TaskRunner taskRunner) {
            Intrinsics.checkNotNullParameter(taskRunner, "taskRunner");
            taskRunner.notify();
        }

        @Override // okhttp3.internal.concurrent.TaskRunner.Backend
        public void coordinatorWait(TaskRunner taskRunner, long nanos) throws InterruptedException {
            Intrinsics.checkNotNullParameter(taskRunner, "taskRunner");
            TaskRunner taskRunner2 = taskRunner;
            if (_UtilJvmKt.assertionsEnabled && !Thread.holdsLock(taskRunner2)) {
                throw new AssertionError("Thread " + Thread.currentThread().getName() + " MUST hold lock on " + taskRunner2);
            }
            if (nanos > 0) {
                long j = nanos / 1000000;
                long j2 = nanos - (1000000 * j);
                if (j > 0 || nanos > 0) {
                    taskRunner2.wait(j, (int) j2);
                }
            }
        }

        @Override // okhttp3.internal.concurrent.TaskRunner.Backend
        public void execute(TaskRunner taskRunner, Runnable runnable) {
            Intrinsics.checkNotNullParameter(taskRunner, "taskRunner");
            Intrinsics.checkNotNullParameter(runnable, "runnable");
            this.executor.execute(runnable);
        }

        public final void shutdown() {
            this.executor.shutdown();
        }
    }

    /* JADX INFO: compiled from: TaskRunner.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0010\u0010\b\u001a\u00020\t8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lokhttp3/internal/concurrent/TaskRunner$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "logger", "Ljava/util/logging/Logger;", "getLogger", "()Ljava/util/logging/Logger;", "INSTANCE", "Lokhttp3/internal/concurrent/TaskRunner;", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Logger getLogger() {
            return TaskRunner.logger;
        }
    }

    static {
        Logger logger2 = Logger.getLogger(TaskRunner.class.getName());
        Intrinsics.checkNotNullExpressionValue(logger2, "getLogger(...)");
        logger = logger2;
        INSTANCE = new TaskRunner(new RealBackend(_UtilJvmKt.threadFactory(_UtilJvmKt.okHttpName + " TaskRunner", true)), 0 == true ? 1 : 0, 2, 0 == true ? 1 : 0);
    }

    public final List<TaskQueue> activeQueues() {
        List<TaskQueue> listPlus;
        synchronized (this) {
            listPlus = CollectionsKt.plus((Collection) this.busyQueues, (Iterable) this.readyQueues);
        }
        return listPlus;
    }
}
