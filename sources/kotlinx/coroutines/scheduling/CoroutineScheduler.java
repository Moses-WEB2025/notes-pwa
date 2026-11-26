package kotlinx.coroutines.scheduling;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.locks.LockSupport;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.AbstractTimeSource;
import kotlinx.coroutines.AbstractTimeSourceKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.internal.ResizableAtomicArray;
import kotlinx.coroutines.internal.Symbol;
import miuix.navigation.SplitLayout;

/* JADX INFO: compiled from: CoroutineScheduler.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0010\b\u0000\u0018\u0000 I2\u00020\u00012\u00020\u0002:\u0003IJKB+\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0004\b\n\u0010\u000bJ\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\"\u0010\u0015\u001a\u00020\u00162\n\u0010\u0017\u001a\u00060\u0018R\u00020\u00002\u0006\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u0004J\u0012\u0010\u001b\u001a\u00020\u00102\n\u0010\u0017\u001a\u00060\u0018R\u00020\u0000J\u000e\u0010\u001c\u001a\b\u0018\u00010\u0018R\u00020\u0000H\u0002J\u0014\u0010\u001d\u001a\u00020\u00042\n\u0010\u0017\u001a\u00060\u0018R\u00020\u0000H\u0002J\u0011\u0010!\u001a\u00020\u00042\u0006\u0010&\u001a\u00020\u0007H\u0082\bJ\u0011\u0010'\u001a\u00020\u00042\u0006\u0010&\u001a\u00020\u0007H\u0082\bJ\u0011\u0010$\u001a\u00020\u00042\u0006\u0010&\u001a\u00020\u0007H\u0086\bJ\t\u0010(\u001a\u00020\u0004H\u0082\bJ\t\u0010)\u001a\u00020\u0004H\u0082\bJ\t\u0010*\u001a\u00020\u0007H\u0082\bJ\t\u0010+\u001a\u00020\u0016H\u0082\bJ\t\u0010,\u001a\u00020\u0010H\u0082\bJ\t\u0010-\u001a\u00020\u0007H\u0082\bJ\u0014\u00102\u001a\u00020\u00162\n\u00103\u001a\u000604j\u0002`5H\u0016J\b\u00106\u001a\u00020\u0016H\u0016J\u000e\u00107\u001a\u00020\u00162\u0006\u00108\u001a\u00020\u0007J*\u00109\u001a\u00020\u00162\n\u0010:\u001a\u000604j\u0002`52\f\b\u0002\u0010;\u001a\u00060\u0010j\u0002`<2\b\b\u0002\u0010=\u001a\u00020\u0010J\u001e\u0010>\u001a\u00020\u00122\n\u0010:\u001a\u000604j\u0002`52\n\u0010;\u001a\u00060\u0010j\u0002`<J\u0010\u0010?\u001a\u00020\u00162\u0006\u0010@\u001a\u00020\u0007H\u0002J\u0006\u0010A\u001a\u00020\u0016J\u0012\u0010B\u001a\u00020\u00102\b\b\u0002\u0010&\u001a\u00020\u0007H\u0002J\b\u0010C\u001a\u00020\u0010H\u0002J\b\u0010D\u001a\u00020\u0004H\u0002J$\u0010E\u001a\u0004\u0018\u00010\u0012*\b\u0018\u00010\u0018R\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010=\u001a\u00020\u0010H\u0002J\u000e\u0010F\u001a\b\u0018\u00010\u0018R\u00020\u0000H\u0002J\b\u0010G\u001a\u00020\tH\u0016J\u000e\u0010H\u001a\u00020\u00162\u0006\u0010\u0011\u001a\u00020\u0012R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u00020\t8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u00020\r8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u00020\r8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\t\u0010\u0013\u001a\u00020\u0014X\u0082\u0004R\u001a\u0010\u001e\u001a\f\u0012\b\u0012\u00060\u0018R\u00020\u00000\u001f8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\t\u0010 \u001a\u00020\u0014X\u0082\u0004R\u0015\u0010!\u001a\u00020\u00048Â\u0002X\u0082\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#R\u0015\u0010$\u001a\u00020\u00048Â\u0002X\u0082\u0004¢\u0006\u0006\u001a\u0004\b%\u0010#R\t\u0010.\u001a\u00020/X\u0082\u0004R\u0011\u00100\u001a\u00020\u00108F¢\u0006\u0006\u001a\u0004\b0\u00101¨\u0006L"}, d2 = {"Lkotlinx/coroutines/scheduling/CoroutineScheduler;", "Ljava/util/concurrent/Executor;", "Ljava/io/Closeable;", "corePoolSize", "", "maxPoolSize", "idleWorkerKeepAliveNs", "", "schedulerName", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(IIJLjava/lang/String;)V", "globalCpuQueue", "Lkotlinx/coroutines/scheduling/GlobalQueue;", "globalBlockingQueue", "addToGlobalQueue", "", "task", "Lkotlinx/coroutines/scheduling/Task;", "parkedWorkersStack", "Lkotlinx/atomicfu/AtomicLong;", "parkedWorkersStackTopUpdate", "", "worker", "Lkotlinx/coroutines/scheduling/CoroutineScheduler$Worker;", "oldIndex", "newIndex", "parkedWorkersStackPush", "parkedWorkersStackPop", "parkedWorkersStackNextIndex", "workers", "Lkotlinx/coroutines/internal/ResizableAtomicArray;", "controlState", "createdWorkers", "getCreatedWorkers", "()I", "availableCpuPermits", "getAvailableCpuPermits", "state", "blockingTasks", "incrementCreatedWorkers", "decrementCreatedWorkers", "incrementBlockingTasks", "decrementBlockingTasks", "tryAcquireCpuPermit", "releaseCpuPermit", "_isTerminated", "Lkotlinx/atomicfu/AtomicBoolean;", "isTerminated", "()Z", "execute", "command", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", SplitLayout.TAG_CLOSE, "shutdown", "timeout", "dispatch", "block", "taskContext", "Lkotlinx/coroutines/scheduling/TaskContext;", "fair", "createTask", "signalBlockingWork", "stateSnapshot", "signalCpuWork", "tryCreateWorker", "tryUnpark", "createNewWorker", "submitToLocalQueue", "currentWorker", "toString", "runSafely", "Companion", "Worker", "WorkerState", "kotlinx-coroutines-core"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class CoroutineScheduler implements Executor, Closeable {
    private static final long BLOCKING_MASK = 4398044413952L;
    private static final int BLOCKING_SHIFT = 21;
    private static final int CLAIMED = 0;
    private static final long CPU_PERMITS_MASK = 9223367638808264704L;
    private static final int CPU_PERMITS_SHIFT = 42;
    private static final long CREATED_MASK = 2097151;
    public static final int MAX_SUPPORTED_POOL_SIZE = 2097150;
    public static final int MIN_SUPPORTED_POOL_SIZE = 1;
    private static final int PARKED = -1;
    private static final long PARKED_INDEX_MASK = 2097151;
    private static final long PARKED_VERSION_INC = 2097152;
    private static final long PARKED_VERSION_MASK = -2097152;
    private static final int TERMINATED = 1;
    private volatile /* synthetic */ int _isTerminated$volatile;
    private volatile /* synthetic */ long controlState$volatile;
    public final int corePoolSize;
    public final GlobalQueue globalBlockingQueue;
    public final GlobalQueue globalCpuQueue;
    public final long idleWorkerKeepAliveNs;
    public final int maxPoolSize;
    private volatile /* synthetic */ long parkedWorkersStack$volatile;
    public final String schedulerName;
    public final ResizableAtomicArray<Worker> workers;
    private static final /* synthetic */ AtomicLongFieldUpdater parkedWorkersStack$volatile$FU = AtomicLongFieldUpdater.newUpdater(CoroutineScheduler.class, "parkedWorkersStack$volatile");
    private static final /* synthetic */ AtomicLongFieldUpdater controlState$volatile$FU = AtomicLongFieldUpdater.newUpdater(CoroutineScheduler.class, "controlState$volatile");
    private static final /* synthetic */ AtomicIntegerFieldUpdater _isTerminated$volatile$FU = AtomicIntegerFieldUpdater.newUpdater(CoroutineScheduler.class, "_isTerminated$volatile");
    public static final Symbol NOT_IN_STACK = new Symbol("NOT_IN_STACK");

    /* JADX INFO: compiled from: CoroutineScheduler.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[WorkerState.values().length];
            try {
                iArr[WorkerState.PARKING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[WorkerState.BLOCKING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[WorkerState.CPU_ACQUIRED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[WorkerState.DORMANT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[WorkerState.TERMINATED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX INFO: compiled from: CoroutineScheduler.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\b¨\u0006\t"}, d2 = {"Lkotlinx/coroutines/scheduling/CoroutineScheduler$WorkerState;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;I)V", "CPU_ACQUIRED", "BLOCKING", "PARKING", "DORMANT", "TERMINATED", "kotlinx-coroutines-core"}, k = 1, mv = {2, 1, 0}, xi = 48)
    public enum WorkerState {
        CPU_ACQUIRED,
        BLOCKING,
        PARKING,
        DORMANT,
        TERMINATED;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<WorkerState> getEntries() {
            return $ENTRIES;
        }
    }

    private final int blockingTasks(long state) {
        return (int) ((state & BLOCKING_MASK) >> 21);
    }

    private final int createdWorkers(long state) {
        return (int) (state & 2097151);
    }

    private final /* synthetic */ long getControlState$volatile() {
        return this.controlState$volatile;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final /* synthetic */ AtomicLongFieldUpdater getControlState$volatile$FU() {
        return controlState$volatile$FU;
    }

    private final /* synthetic */ long getParkedWorkersStack$volatile() {
        return this.parkedWorkersStack$volatile;
    }

    private final /* synthetic */ int get_isTerminated$volatile() {
        return this._isTerminated$volatile;
    }

    private final /* synthetic */ void loop$atomicfu$ATOMIC_FIELD_UPDATER$Long(AtomicLongFieldUpdater atomicLongFieldUpdater, Object obj, Function1<? super Long, Unit> function1) {
        while (true) {
            function1.invoke(Long.valueOf(atomicLongFieldUpdater.get(obj)));
        }
    }

    private final /* synthetic */ void setControlState$volatile(long j) {
        this.controlState$volatile = j;
    }

    private final /* synthetic */ void setParkedWorkersStack$volatile(long j) {
        this.parkedWorkersStack$volatile = j;
    }

    private final /* synthetic */ void set_isTerminated$volatile(int i) {
        this._isTerminated$volatile = i;
    }

    public final int availableCpuPermits(long state) {
        return (int) ((state & CPU_PERMITS_MASK) >> 42);
    }

    public CoroutineScheduler(int i, int i2, long j, String str) {
        this.corePoolSize = i;
        this.maxPoolSize = i2;
        this.idleWorkerKeepAliveNs = j;
        this.schedulerName = str;
        if (i < 1) {
            throw new IllegalArgumentException(("Core pool size " + i + " should be at least 1").toString());
        }
        if (i2 < i) {
            throw new IllegalArgumentException(("Max pool size " + i2 + " should be greater than or equals to core pool size " + i).toString());
        }
        if (i2 > 2097150) {
            throw new IllegalArgumentException(("Max pool size " + i2 + " should not exceed maximal supported number of threads 2097150").toString());
        }
        if (j <= 0) {
            throw new IllegalArgumentException(("Idle worker keep alive time " + j + " must be positive").toString());
        }
        this.globalCpuQueue = new GlobalQueue();
        this.globalBlockingQueue = new GlobalQueue();
        this.workers = new ResizableAtomicArray<>((i + 1) * 2);
        this.controlState$volatile = ((long) i) << 42;
    }

    public /* synthetic */ CoroutineScheduler(int i, int i2, long j, String str, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, (i3 & 4) != 0 ? TasksKt.IDLE_WORKER_KEEP_ALIVE_NS : j, (i3 & 8) != 0 ? TasksKt.DEFAULT_SCHEDULER_NAME : str);
    }

    public final void parkedWorkersStackTopUpdate(Worker worker, int oldIndex, int newIndex) {
        AtomicLongFieldUpdater atomicLongFieldUpdater = parkedWorkersStack$volatile$FU;
        while (true) {
            long j = atomicLongFieldUpdater.get(this);
            int iParkedWorkersStackNextIndex = (int) (2097151 & j);
            long j2 = (2097152 + j) & PARKED_VERSION_MASK;
            if (iParkedWorkersStackNextIndex == oldIndex) {
                iParkedWorkersStackNextIndex = newIndex == 0 ? this.parkedWorkersStackNextIndex(worker) : newIndex;
            }
            if (iParkedWorkersStackNextIndex >= 0) {
                CoroutineScheduler coroutineScheduler = this;
                if (parkedWorkersStack$volatile$FU.compareAndSet(coroutineScheduler, j, j2 | ((long) iParkedWorkersStackNextIndex))) {
                    return;
                } else {
                    this = coroutineScheduler;
                }
            }
        }
    }

    public final boolean parkedWorkersStackPush(Worker worker) {
        if (worker.getNextParkedWorker() != NOT_IN_STACK) {
            return false;
        }
        AtomicLongFieldUpdater atomicLongFieldUpdater = parkedWorkersStack$volatile$FU;
        while (true) {
            long j = atomicLongFieldUpdater.get(this);
            int i = (int) (2097151 & j);
            long j2 = (2097152 + j) & PARKED_VERSION_MASK;
            int indexInArray = worker.getIndexInArray();
            if (DebugKt.getASSERTIONS_ENABLED() && indexInArray == 0) {
                throw new AssertionError();
            }
            worker.setNextParkedWorker(this.workers.get(i));
            long j3 = j2 | ((long) indexInArray);
            CoroutineScheduler coroutineScheduler = this;
            if (parkedWorkersStack$volatile$FU.compareAndSet(coroutineScheduler, j, j3)) {
                return true;
            }
            this = coroutineScheduler;
        }
    }

    private final Worker parkedWorkersStackPop() {
        AtomicLongFieldUpdater atomicLongFieldUpdater = parkedWorkersStack$volatile$FU;
        while (true) {
            long j = atomicLongFieldUpdater.get(this);
            Worker worker = this.workers.get((int) (2097151 & j));
            if (worker == null) {
                return null;
            }
            long j2 = (2097152 + j) & PARKED_VERSION_MASK;
            int iParkedWorkersStackNextIndex = this.parkedWorkersStackNextIndex(worker);
            if (iParkedWorkersStackNextIndex >= 0) {
                CoroutineScheduler coroutineScheduler = this;
                if (parkedWorkersStack$volatile$FU.compareAndSet(coroutineScheduler, j, ((long) iParkedWorkersStackNextIndex) | j2)) {
                    worker.setNextParkedWorker(NOT_IN_STACK);
                    return worker;
                }
                this = coroutineScheduler;
            }
        }
    }

    private final int parkedWorkersStackNextIndex(Worker worker) {
        Object nextParkedWorker = worker.getNextParkedWorker();
        while (nextParkedWorker != NOT_IN_STACK) {
            if (nextParkedWorker == null) {
                return 0;
            }
            Worker worker2 = (Worker) nextParkedWorker;
            int indexInArray = worker2.getIndexInArray();
            if (indexInArray != 0) {
                return indexInArray;
            }
            nextParkedWorker = worker2.getNextParkedWorker();
        }
        return -1;
    }

    private final int getCreatedWorkers() {
        return (int) (getControlState$volatile$FU().get(this) & 2097151);
    }

    private final int getAvailableCpuPermits() {
        return (int) ((controlState$volatile$FU.get(this) & CPU_PERMITS_MASK) >> 42);
    }

    private final int incrementCreatedWorkers() {
        return (int) (controlState$volatile$FU.incrementAndGet(this) & 2097151);
    }

    private final int decrementCreatedWorkers() {
        return (int) (getControlState$volatile$FU().getAndDecrement(this) & 2097151);
    }

    private final long incrementBlockingTasks() {
        return controlState$volatile$FU.addAndGet(this, 2097152L);
    }

    private final void decrementBlockingTasks() {
        getControlState$volatile$FU().addAndGet(this, PARKED_VERSION_MASK);
    }

    private final boolean tryAcquireCpuPermit() {
        AtomicLongFieldUpdater controlState$volatile$FU2 = getControlState$volatile$FU();
        while (true) {
            long j = controlState$volatile$FU2.get(this);
            if (((int) ((CPU_PERMITS_MASK & j) >> 42)) == 0) {
                return false;
            }
            CoroutineScheduler coroutineScheduler = this;
            if (getControlState$volatile$FU().compareAndSet(coroutineScheduler, j, j - 4398046511104L)) {
                return true;
            }
            this = coroutineScheduler;
        }
    }

    private final long releaseCpuPermit() {
        return getControlState$volatile$FU().addAndGet(this, 4398046511104L);
    }

    public final boolean isTerminated() {
        return _isTerminated$volatile$FU.get(this) == 1;
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable command) {
        dispatch$default(this, command, false, false, 6, null);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        shutdown(10000L);
    }

    /* JADX WARN: Code duplicated, block: B:31:0x0075  */
    public final void shutdown(long timeout) {
        int i;
        Task taskRemoveFirstOrNull;
        if (_isTerminated$volatile$FU.compareAndSet(this, 0, 1)) {
            Worker workerCurrentWorker = currentWorker();
            synchronized (this.workers) {
                i = (int) (getControlState$volatile$FU().get(this) & 2097151);
            }
            if (1 <= i) {
                int i2 = 1;
                while (true) {
                    Worker worker = this.workers.get(i2);
                    Intrinsics.checkNotNull(worker);
                    Worker worker2 = worker;
                    if (worker2 != workerCurrentWorker) {
                        while (worker2.getState() != Thread.State.TERMINATED) {
                            LockSupport.unpark(worker2);
                            worker2.join(timeout);
                        }
                        if (DebugKt.getASSERTIONS_ENABLED() && worker2.state != WorkerState.TERMINATED) {
                            throw new AssertionError();
                        }
                        worker2.localQueue.offloadAllWorkTo(this.globalBlockingQueue);
                    }
                    if (i2 == i) {
                        break;
                    } else {
                        i2++;
                    }
                }
            }
            this.globalBlockingQueue.close();
            this.globalCpuQueue.close();
            while (true) {
                if (workerCurrentWorker != null) {
                    taskRemoveFirstOrNull = workerCurrentWorker.findTask(true);
                    if (taskRemoveFirstOrNull == null) {
                        taskRemoveFirstOrNull = this.globalCpuQueue.removeFirstOrNull();
                        if (taskRemoveFirstOrNull == null) {
                            continue;
                        }
                    } else {
                        continue;
                    }
                } else {
                    taskRemoveFirstOrNull = this.globalCpuQueue.removeFirstOrNull();
                    if (taskRemoveFirstOrNull == null && (taskRemoveFirstOrNull = this.globalBlockingQueue.removeFirstOrNull()) == null) {
                        break;
                    }
                }
                runSafely(taskRemoveFirstOrNull);
            }
            if (workerCurrentWorker != null) {
                workerCurrentWorker.tryReleaseCpu(WorkerState.TERMINATED);
            }
            if (DebugKt.getASSERTIONS_ENABLED() && ((int) ((controlState$volatile$FU.get(this) & CPU_PERMITS_MASK) >> 42)) != this.corePoolSize) {
                throw new AssertionError();
            }
            parkedWorkersStack$volatile$FU.set(this, 0L);
            controlState$volatile$FU.set(this, 0L);
        }
    }

    public static /* synthetic */ void dispatch$default(CoroutineScheduler coroutineScheduler, Runnable runnable, boolean z, boolean z2, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        if ((i & 4) != 0) {
            z2 = false;
        }
        coroutineScheduler.dispatch(runnable, z, z2);
    }

    public final void dispatch(Runnable block, boolean taskContext, boolean fair) {
        AbstractTimeSource abstractTimeSource = AbstractTimeSourceKt.timeSource;
        if (abstractTimeSource != null) {
            abstractTimeSource.trackTask();
        }
        Task taskCreateTask = createTask(block, taskContext);
        boolean z = taskCreateTask.taskContext;
        long jAddAndGet = z ? controlState$volatile$FU.addAndGet(this, 2097152L) : 0L;
        Task taskSubmitToLocalQueue = submitToLocalQueue(currentWorker(), taskCreateTask, fair);
        if (taskSubmitToLocalQueue != null && !addToGlobalQueue(taskSubmitToLocalQueue)) {
            throw new RejectedExecutionException(this.schedulerName + " was terminated");
        }
        if (z) {
            signalBlockingWork(jAddAndGet);
        } else {
            signalCpuWork();
        }
    }

    public final Task createTask(Runnable block, boolean taskContext) {
        long jNanoTime = TasksKt.schedulerTimeSource.nanoTime();
        if (block instanceof Task) {
            Task task = (Task) block;
            task.submissionTime = jNanoTime;
            task.taskContext = taskContext;
            return task;
        }
        return TasksKt.asTask(block, jNanoTime, taskContext);
    }

    private final void signalBlockingWork(long stateSnapshot) {
        if (tryUnpark() || tryCreateWorker(stateSnapshot)) {
            return;
        }
        tryUnpark();
    }

    public final void signalCpuWork() {
        if (tryUnpark() || tryCreateWorker$default(this, 0L, 1, null)) {
            return;
        }
        tryUnpark();
    }

    static /* synthetic */ boolean tryCreateWorker$default(CoroutineScheduler coroutineScheduler, long j, int i, Object obj) {
        if ((i & 1) != 0) {
            j = controlState$volatile$FU.get(coroutineScheduler);
        }
        return coroutineScheduler.tryCreateWorker(j);
    }

    private final boolean tryCreateWorker(long state) {
        if (RangesKt.coerceAtLeast(((int) (2097151 & state)) - ((int) ((state & BLOCKING_MASK) >> 21)), 0) < this.corePoolSize) {
            int iCreateNewWorker = createNewWorker();
            if (iCreateNewWorker == 1 && this.corePoolSize > 1) {
                createNewWorker();
            }
            if (iCreateNewWorker > 0) {
                return true;
            }
        }
        return false;
    }

    private final boolean tryUnpark() {
        Worker workerParkedWorkersStackPop;
        do {
            workerParkedWorkersStackPop = parkedWorkersStackPop();
            if (workerParkedWorkersStackPop == null) {
                return false;
            }
        } while (!Worker.workerCtl$volatile$FU.compareAndSet(workerParkedWorkersStackPop, -1, 0));
        LockSupport.unpark(workerParkedWorkersStackPop);
        return true;
    }

    private final int createNewWorker() {
        synchronized (this.workers) {
            if (isTerminated()) {
                return -1;
            }
            long j = controlState$volatile$FU.get(this);
            int i = (int) (j & 2097151);
            int iCoerceAtLeast = RangesKt.coerceAtLeast(i - ((int) ((j & BLOCKING_MASK) >> 21)), 0);
            if (iCoerceAtLeast >= this.corePoolSize) {
                return 0;
            }
            if (i >= this.maxPoolSize) {
                return 0;
            }
            int i2 = ((int) (getControlState$volatile$FU().get(this) & 2097151)) + 1;
            if (i2 <= 0 || this.workers.get(i2) != null) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            Worker worker = new Worker(this, i2);
            this.workers.setSynchronized(i2, worker);
            if (i2 != ((int) (2097151 & controlState$volatile$FU.incrementAndGet(this)))) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            int i3 = iCoerceAtLeast + 1;
            worker.start();
            return i3;
        }
    }

    private final Task submitToLocalQueue(Worker worker, Task task, boolean z) {
        if (worker == null || worker.state == WorkerState.TERMINATED || (!task.taskContext && worker.state == WorkerState.BLOCKING)) {
            return task;
        }
        worker.mayHaveLocalTasks = true;
        return worker.localQueue.add(task, z);
    }

    private final Worker currentWorker() {
        Thread threadCurrentThread = Thread.currentThread();
        Worker worker = threadCurrentThread instanceof Worker ? (Worker) threadCurrentThread : null;
        if (worker == null || !Intrinsics.areEqual(CoroutineScheduler.this, this)) {
            return null;
        }
        return worker;
    }

    public String toString() {
        ArrayList arrayList = new ArrayList();
        int iCurrentLength = this.workers.currentLength();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 1; i6 < iCurrentLength; i6++) {
            Worker worker = this.workers.get(i6);
            if (worker != null) {
                int size$kotlinx_coroutines_core = worker.localQueue.getSize$kotlinx_coroutines_core();
                int i7 = WhenMappings.$EnumSwitchMapping$0[worker.state.ordinal()];
                if (i7 == 1) {
                    i3++;
                } else if (i7 == 2) {
                    i2++;
                    arrayList.add(new StringBuilder().append(size$kotlinx_coroutines_core).append('b').toString());
                } else if (i7 == 3) {
                    i++;
                    arrayList.add(new StringBuilder().append(size$kotlinx_coroutines_core).append('c').toString());
                } else if (i7 == 4) {
                    i4++;
                    if (size$kotlinx_coroutines_core > 0) {
                        arrayList.add(new StringBuilder().append(size$kotlinx_coroutines_core).append('d').toString());
                    }
                } else {
                    if (i7 != 5) {
                        throw new NoWhenBranchMatchedException();
                    }
                    i5++;
                }
            }
        }
        long j = controlState$volatile$FU.get(this);
        StringBuilder sb = new StringBuilder();
        sb.append(this.schedulerName).append('@').append(DebugStringsKt.getHexAddress(this)).append("[Pool Size {core = ").append(this.corePoolSize).append(", max = ").append(this.maxPoolSize).append("}, Worker States {CPU = ").append(i).append(", blocking = ").append(i2).append(", parked = ").append(i3).append(", dormant = ").append(i4).append(", terminated = ").append(i5).append("}, running workers queues = ").append(arrayList).append(", global CPU queue size = ").append(this.globalCpuQueue.getSize()).append(", global blocking queue size = ").append(this.globalBlockingQueue.getSize());
        sb.append(", Control State {created workers= ").append((int) (2097151 & j)).append(", blocking tasks = ").append((int) ((BLOCKING_MASK & j) >> 21)).append(", CPUs acquired = ").append(this.corePoolSize - ((int) ((CPU_PERMITS_MASK & j) >> 42))).append("}]");
        return sb.toString();
    }

    public final void runSafely(Task task) {
        try {
            task.run();
            AbstractTimeSource abstractTimeSource = AbstractTimeSourceKt.timeSource;
            if (abstractTimeSource != null) {
                abstractTimeSource.unTrackTask();
            }
        } catch (Throwable th) {
            try {
                Thread threadCurrentThread = Thread.currentThread();
                threadCurrentThread.getUncaughtExceptionHandler().uncaughtException(threadCurrentThread, th);
            } finally {
                AbstractTimeSource abstractTimeSource2 = AbstractTimeSourceKt.timeSource;
                if (abstractTimeSource2 != null) {
                    abstractTimeSource2.unTrackTask();
                }
            }
        }
    }

    /* JADX INFO: compiled from: CoroutineScheduler.kt */
    @Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0000\b\u0080\u0004\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003B\u0011\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0002\u0010\u0006J\b\u0010#\u001a\u00020$H\u0002J\u000e\u0010%\u001a\u00020$2\u0006\u0010&\u001a\u00020\u0016J\b\u0010'\u001a\u00020(H\u0016J\b\u0010*\u001a\u00020(H\u0002J\u0006\u0010+\u001a\u00020\u001aJ\u0006\u0010,\u001a\u00020$J\b\u0010-\u001a\u00020(H\u0002J\b\u0010.\u001a\u00020$H\u0002J\u0010\u0010/\u001a\u00020(2\u0006\u00100\u001a\u00020\u0014H\u0002J\u000e\u00101\u001a\u00020\u00052\u0006\u00102\u001a\u00020\u0005J\b\u00103\u001a\u00020(H\u0002J\b\u00104\u001a\u00020(H\u0002J\u0010\u00105\u001a\u0004\u0018\u00010\u00142\u0006\u0010)\u001a\u00020$J\n\u00106\u001a\u0004\u0018\u00010\u0014H\u0002J\n\u00107\u001a\u0004\u0018\u00010\u0014H\u0002J\u0012\u00108\u001a\u0004\u0018\u00010\u00142\u0006\u00109\u001a\u00020$H\u0002J\n\u0010:\u001a\u0004\u0018\u00010\u0014H\u0002J\u0016\u0010;\u001a\u0004\u0018\u00010\u00142\n\u0010<\u001a\u00060\u0005j\u0002`=H\u0002R$\u0010\u0007\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u0012\u0010\f\u001a\u00020\r8Æ\u0002¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0010\u0010\u0010\u001a\u00020\u00118\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0012\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00140\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0015\u001a\u00020\u00168\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0006\u0010\u0017\u001a\u00020\u0018R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u000e\u0010!\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010)\u001a\u00020$8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000¨\u0006>"}, d2 = {"Lkotlinx/coroutines/scheduling/CoroutineScheduler$Worker;", "Ljava/lang/Thread;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lkotlinx/coroutines/scheduling/CoroutineScheduler;)V", "index", "", "(Lkotlinx/coroutines/scheduling/CoroutineScheduler;I)V", "indexInArray", "getIndexInArray", "()I", "setIndexInArray", "(I)V", "scheduler", "Lkotlinx/coroutines/scheduling/CoroutineScheduler;", "getScheduler", "()Lkotlinx/coroutines/scheduling/CoroutineScheduler;", "localQueue", "Lkotlinx/coroutines/scheduling/WorkQueue;", "stolenTask", "Lkotlin/jvm/internal/Ref$ObjectRef;", "Lkotlinx/coroutines/scheduling/Task;", "state", "Lkotlinx/coroutines/scheduling/CoroutineScheduler$WorkerState;", "workerCtl", "Lkotlinx/atomicfu/AtomicInt;", "terminationDeadline", "", "nextParkedWorker", "", "getNextParkedWorker", "()Ljava/lang/Object;", "setNextParkedWorker", "(Ljava/lang/Object;)V", "minDelayUntilStealableTaskNs", "rngState", "tryAcquireCpuPermit", "", "tryReleaseCpu", "newState", "run", "", "mayHaveLocalTasks", "runWorker", "runSingleTask", "isIo", "tryPark", "inStack", "executeTask", "task", "nextInt", "upperBound", "park", "tryTerminateWorker", "findTask", "findBlockingTask", "findCpuTask", "findAnyTask", "scanLocalQueue", "pollGlobalQueues", "trySteal", "stealingMode", "Lkotlinx/coroutines/scheduling/StealingMode;", "kotlinx-coroutines-core"}, k = 1, mv = {2, 1, 0}, xi = 48)
    public final class Worker extends Thread {
        private static final /* synthetic */ AtomicIntegerFieldUpdater workerCtl$volatile$FU = AtomicIntegerFieldUpdater.newUpdater(Worker.class, "workerCtl$volatile");
        private volatile int indexInArray;
        public final WorkQueue localQueue;
        public boolean mayHaveLocalTasks;
        private long minDelayUntilStealableTaskNs;
        private volatile Object nextParkedWorker;
        private int rngState;
        public WorkerState state;
        private final Ref.ObjectRef<Task> stolenTask;
        private long terminationDeadline;
        private volatile /* synthetic */ int workerCtl$volatile;

        public final /* synthetic */ int getWorkerCtl$volatile() {
            return this.workerCtl$volatile;
        }

        public final /* synthetic */ void setWorkerCtl$volatile(int i) {
            this.workerCtl$volatile = i;
        }

        private Worker() {
            setDaemon(true);
            setContextClassLoader(CoroutineScheduler.this.getClass().getClassLoader());
            this.localQueue = new WorkQueue();
            this.stolenTask = new Ref.ObjectRef<>();
            this.state = WorkerState.DORMANT;
            this.nextParkedWorker = CoroutineScheduler.NOT_IN_STACK;
            int iNanoTime = (int) System.nanoTime();
            this.rngState = iNanoTime == 0 ? 42 : iNanoTime;
        }

        public final int getIndexInArray() {
            return this.indexInArray;
        }

        public final void setIndexInArray(int i) {
            setName(CoroutineScheduler.this.schedulerName + "-worker-" + (i == 0 ? "TERMINATED" : String.valueOf(i)));
            this.indexInArray = i;
        }

        public Worker(CoroutineScheduler coroutineScheduler, int i) {
            this();
            setIndexInArray(i);
        }

        public final CoroutineScheduler getScheduler() {
            return CoroutineScheduler.this;
        }

        public final Object getNextParkedWorker() {
            return this.nextParkedWorker;
        }

        public final void setNextParkedWorker(Object obj) {
            this.nextParkedWorker = obj;
        }

        private final boolean tryAcquireCpuPermit() {
            long j;
            if (this.state == WorkerState.CPU_ACQUIRED) {
                return true;
            }
            CoroutineScheduler coroutineScheduler = CoroutineScheduler.this;
            AtomicLongFieldUpdater controlState$volatile$FU = CoroutineScheduler.getControlState$volatile$FU();
            do {
                j = controlState$volatile$FU.get(coroutineScheduler);
                if (((int) ((CoroutineScheduler.CPU_PERMITS_MASK & j) >> 42)) == 0) {
                    return false;
                }
            } while (!CoroutineScheduler.getControlState$volatile$FU().compareAndSet(coroutineScheduler, j, j - 4398046511104L));
            this.state = WorkerState.CPU_ACQUIRED;
            return true;
        }

        public final boolean tryReleaseCpu(WorkerState newState) {
            WorkerState workerState = this.state;
            boolean z = workerState == WorkerState.CPU_ACQUIRED;
            if (z) {
                CoroutineScheduler.getControlState$volatile$FU().addAndGet(CoroutineScheduler.this, 4398046511104L);
            }
            if (workerState != newState) {
                this.state = newState;
            }
            return z;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            runWorker();
        }

        private final void runWorker() {
            loop0: while (true) {
                boolean z = false;
                while (true) {
                    if (CoroutineScheduler.this.isTerminated() || this.state == WorkerState.TERMINATED) {
                        break loop0;
                    }
                    Task taskFindTask = findTask(this.mayHaveLocalTasks);
                    if (taskFindTask != null) {
                        this.minDelayUntilStealableTaskNs = 0L;
                        executeTask(taskFindTask);
                        break;
                    }
                    this.mayHaveLocalTasks = false;
                    if (this.minDelayUntilStealableTaskNs == 0) {
                        tryPark();
                    } else {
                        if (z) {
                            tryReleaseCpu(WorkerState.PARKING);
                            Thread.interrupted();
                            LockSupport.parkNanos(this.minDelayUntilStealableTaskNs);
                            this.minDelayUntilStealableTaskNs = 0L;
                            break;
                        }
                        z = true;
                    }
                }
            }
            tryReleaseCpu(WorkerState.TERMINATED);
        }

        public final long runSingleTask() {
            Task taskFindBlockingTask;
            WorkerState workerState = this.state;
            boolean z = workerState == WorkerState.CPU_ACQUIRED;
            if (z) {
                taskFindBlockingTask = findCpuTask();
            } else {
                taskFindBlockingTask = findBlockingTask();
            }
            if (taskFindBlockingTask == null) {
                long j = this.minDelayUntilStealableTaskNs;
                if (j == 0) {
                    return -1L;
                }
                return j;
            }
            CoroutineScheduler.this.runSafely(taskFindBlockingTask);
            if (!z) {
                CoroutineScheduler.getControlState$volatile$FU().addAndGet(CoroutineScheduler.this, CoroutineScheduler.PARKED_VERSION_MASK);
            }
            if (!DebugKt.getASSERTIONS_ENABLED() || this.state == workerState) {
                return 0L;
            }
            throw new AssertionError();
        }

        public final boolean isIo() {
            return this.state == WorkerState.BLOCKING;
        }

        private final void tryPark() {
            if (!inStack()) {
                CoroutineScheduler.this.parkedWorkersStackPush(this);
                return;
            }
            workerCtl$volatile$FU.set(this, -1);
            while (inStack() && workerCtl$volatile$FU.get(this) == -1 && !CoroutineScheduler.this.isTerminated() && this.state != WorkerState.TERMINATED) {
                tryReleaseCpu(WorkerState.PARKING);
                Thread.interrupted();
                park();
            }
        }

        private final boolean inStack() {
            return this.nextParkedWorker != CoroutineScheduler.NOT_IN_STACK;
        }

        private final void executeTask(Task task) {
            this.terminationDeadline = 0L;
            if (this.state == WorkerState.PARKING) {
                if (DebugKt.getASSERTIONS_ENABLED() && !task.taskContext) {
                    throw new AssertionError();
                }
                this.state = WorkerState.BLOCKING;
            }
            if (task.taskContext) {
                if (tryReleaseCpu(WorkerState.BLOCKING)) {
                    CoroutineScheduler.this.signalCpuWork();
                }
                CoroutineScheduler.this.runSafely(task);
                CoroutineScheduler.getControlState$volatile$FU().addAndGet(CoroutineScheduler.this, CoroutineScheduler.PARKED_VERSION_MASK);
                WorkerState workerState = this.state;
                if (workerState != WorkerState.TERMINATED) {
                    if (DebugKt.getASSERTIONS_ENABLED() && workerState != WorkerState.BLOCKING) {
                        throw new AssertionError();
                    }
                    this.state = WorkerState.DORMANT;
                    return;
                }
                return;
            }
            CoroutineScheduler.this.runSafely(task);
        }

        public final int nextInt(int upperBound) {
            int i = this.rngState;
            int i2 = i ^ (i << 13);
            int i3 = i2 ^ (i2 >> 17);
            int i4 = i3 ^ (i3 << 5);
            this.rngState = i4;
            int i5 = upperBound - 1;
            return (i5 & upperBound) == 0 ? i5 & i4 : (Integer.MAX_VALUE & i4) % upperBound;
        }

        private final void park() {
            if (this.terminationDeadline == 0) {
                this.terminationDeadline = System.nanoTime() + CoroutineScheduler.this.idleWorkerKeepAliveNs;
            }
            LockSupport.parkNanos(CoroutineScheduler.this.idleWorkerKeepAliveNs);
            if (System.nanoTime() - this.terminationDeadline >= 0) {
                this.terminationDeadline = 0L;
                tryTerminateWorker();
            }
        }

        private final void tryTerminateWorker() {
            ResizableAtomicArray<Worker> resizableAtomicArray = CoroutineScheduler.this.workers;
            CoroutineScheduler coroutineScheduler = CoroutineScheduler.this;
            synchronized (resizableAtomicArray) {
                if (coroutineScheduler.isTerminated()) {
                    return;
                }
                if (((int) (CoroutineScheduler.getControlState$volatile$FU().get(coroutineScheduler) & 2097151)) <= coroutineScheduler.corePoolSize) {
                    return;
                }
                if (workerCtl$volatile$FU.compareAndSet(this, -1, 1)) {
                    int i = this.indexInArray;
                    setIndexInArray(0);
                    coroutineScheduler.parkedWorkersStackTopUpdate(this, i, 0);
                    int andDecrement = (int) (CoroutineScheduler.getControlState$volatile$FU().getAndDecrement(coroutineScheduler) & 2097151);
                    if (andDecrement != i) {
                        Worker worker = coroutineScheduler.workers.get(andDecrement);
                        Intrinsics.checkNotNull(worker);
                        Worker worker2 = worker;
                        coroutineScheduler.workers.setSynchronized(i, worker2);
                        worker2.setIndexInArray(i);
                        coroutineScheduler.parkedWorkersStackTopUpdate(worker2, andDecrement, i);
                    }
                    coroutineScheduler.workers.setSynchronized(andDecrement, null);
                    Unit unit = Unit.INSTANCE;
                    this.state = WorkerState.TERMINATED;
                }
            }
        }

        public final Task findTask(boolean mayHaveLocalTasks) {
            return tryAcquireCpuPermit() ? findAnyTask(mayHaveLocalTasks) : findBlockingTask();
        }

        private final Task findBlockingTask() {
            Task taskPollBlocking = this.localQueue.pollBlocking();
            return (taskPollBlocking == null && (taskPollBlocking = CoroutineScheduler.this.globalBlockingQueue.removeFirstOrNull()) == null) ? trySteal(1) : taskPollBlocking;
        }

        private final Task findCpuTask() {
            Task taskPollCpu = this.localQueue.pollCpu();
            return (taskPollCpu == null && (taskPollCpu = CoroutineScheduler.this.globalBlockingQueue.removeFirstOrNull()) == null) ? trySteal(2) : taskPollCpu;
        }

        private final Task findAnyTask(boolean scanLocalQueue) {
            Task taskPollGlobalQueues;
            Task taskPollGlobalQueues2;
            if (scanLocalQueue) {
                boolean z = nextInt(CoroutineScheduler.this.corePoolSize * 2) == 0;
                if (z && (taskPollGlobalQueues2 = pollGlobalQueues()) != null) {
                    return taskPollGlobalQueues2;
                }
                Task taskPoll = this.localQueue.poll();
                if (taskPoll != null) {
                    return taskPoll;
                }
                if (!z && (taskPollGlobalQueues = pollGlobalQueues()) != null) {
                    return taskPollGlobalQueues;
                }
            } else {
                Task taskPollGlobalQueues3 = pollGlobalQueues();
                if (taskPollGlobalQueues3 != null) {
                    return taskPollGlobalQueues3;
                }
            }
            return trySteal(3);
        }

        private final Task pollGlobalQueues() {
            if (nextInt(2) == 0) {
                Task taskRemoveFirstOrNull = CoroutineScheduler.this.globalCpuQueue.removeFirstOrNull();
                return taskRemoveFirstOrNull != null ? taskRemoveFirstOrNull : CoroutineScheduler.this.globalBlockingQueue.removeFirstOrNull();
            }
            Task taskRemoveFirstOrNull2 = CoroutineScheduler.this.globalBlockingQueue.removeFirstOrNull();
            return taskRemoveFirstOrNull2 != null ? taskRemoveFirstOrNull2 : CoroutineScheduler.this.globalCpuQueue.removeFirstOrNull();
        }

        private final Task trySteal(int stealingMode) {
            int i = (int) (CoroutineScheduler.getControlState$volatile$FU().get(CoroutineScheduler.this) & 2097151);
            if (i < 2) {
                return null;
            }
            int iNextInt = nextInt(i);
            CoroutineScheduler coroutineScheduler = CoroutineScheduler.this;
            long jMin = Long.MAX_VALUE;
            for (int i2 = 0; i2 < i; i2++) {
                iNextInt++;
                if (iNextInt > i) {
                    iNextInt = 1;
                }
                Worker worker = coroutineScheduler.workers.get(iNextInt);
                if (worker != null && worker != this) {
                    long jTrySteal = worker.localQueue.trySteal(stealingMode, this.stolenTask);
                    if (jTrySteal == -1) {
                        Task task = this.stolenTask.element;
                        this.stolenTask.element = null;
                        return task;
                    }
                    if (jTrySteal > 0) {
                        jMin = Math.min(jMin, jTrySteal);
                    }
                }
            }
            if (jMin == Long.MAX_VALUE) {
                jMin = 0;
            }
            this.minDelayUntilStealableTaskNs = jMin;
            return null;
        }
    }

    private final boolean addToGlobalQueue(Task task) {
        if (task.taskContext) {
            return this.globalBlockingQueue.addLast(task);
        }
        return this.globalCpuQueue.addLast(task);
    }
}
