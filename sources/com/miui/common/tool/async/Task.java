package com.miui.common.tool.async;

import com.miui.notes.base.utils.Logger;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.text.Typography;

/* JADX INFO: loaded from: classes2.dex */
public abstract class Task<T> {
    private ArrayList<Listener> mListeners;
    private Priority mPriority;
    private volatile Status mStatus;
    private final Object mSuccessorLock;
    private final AtomicBoolean mSuccessorSync;
    private HashSet<Task<?>> mSuccessorTasks;
    private WeakReference<TaskManager> mTaskManager;
    private Thread mThread;

    enum Delivery {
        Prepare,
        Result,
        Progress,
        Finalize
    }

    public interface Listener {
        void onCanceled(TaskManager taskManager, Task<?> task);

        void onException(TaskManager taskManager, Task<?> task, Exception exc);

        void onFinalize(TaskManager taskManager, Task<?> task);

        void onPrepare(TaskManager taskManager, Task<?> task);

        void onProgress(TaskManager taskManager, Task<?> task, int i, int i2);

        Object onResult(TaskManager taskManager, Task<?> task, Object obj);
    }

    public enum Priority {
        Low,
        Normal,
        High,
        RealTime
    }

    public enum Status {
        New,
        Queued,
        Executing,
        Done,
        Canceled
    }

    public abstract T doLoad() throws Exception;

    public String getDescription() {
        return null;
    }

    public void onCanceled(TaskManager taskManager) {
    }

    public void onException(TaskManager taskManager, Exception exc) {
    }

    public void onFinalize(TaskManager taskManager) {
    }

    public void onPrepare(TaskManager taskManager) {
    }

    public void onProgress(TaskManager taskManager, int i, int i2) {
    }

    public void onResult(TaskManager taskManager, T t) {
    }

    public Task() {
        this(Priority.Normal);
    }

    public Task(Priority priority) {
        this.mSuccessorLock = new Object();
        this.mSuccessorSync = new AtomicBoolean(false);
        this.mStatus = Status.New;
        this.mPriority = priority;
    }

    final void setStatus(Status status, Object obj) {
        int i = AnonymousClass1.$SwitchMap$com$miui$common$tool$async$Task$Status[this.mStatus.ordinal()];
        if (i != 1) {
            if (i == 2) {
                int i2 = AnonymousClass1.$SwitchMap$com$miui$common$tool$async$Task$Status[status.ordinal()];
                if (i2 == 1) {
                    postDelivery(Delivery.Result, null);
                    postDelivery(Delivery.Finalize, null);
                } else if (i2 == 3) {
                    postDelivery(Delivery.Result, obj);
                    postDelivery(Delivery.Finalize, null);
                } else {
                    Logger.INSTANCE.w("async", "Task " + this + " error status change=> " + status);
                    return;
                }
            } else if (i != 3) {
                if (i == 4) {
                    int i3 = AnonymousClass1.$SwitchMap$com$miui$common$tool$async$Task$Status[status.ordinal()];
                    if (i3 == 1) {
                        postDelivery(Delivery.Result, null);
                        postDelivery(Delivery.Finalize, null);
                    } else if (i3 != 2) {
                        Logger.INSTANCE.w("async", "Task " + this + " error status change=> " + status);
                        return;
                    }
                } else if (i == 5) {
                    int i4 = AnonymousClass1.$SwitchMap$com$miui$common$tool$async$Task$Status[status.ordinal()];
                    if (i4 == 1) {
                        postDelivery(Delivery.Result, null);
                        postDelivery(Delivery.Finalize, null);
                    } else if (i4 == 4) {
                        postDelivery(Delivery.Prepare, null);
                    } else {
                        Logger.INSTANCE.w("async", "Task " + this + " error status change=> " + status);
                        return;
                    }
                }
            }
            this.mStatus = status;
            return;
        }
        Logger.INSTANCE.w("async", "Task " + this + " error status change=> " + status);
    }

    public final Status getStatus() {
        return this.mStatus;
    }

    public final boolean isRunning() {
        return this.mStatus == Status.Queued || this.mStatus == Status.Executing;
    }

    private final boolean isCanceled() {
        return this.mStatus == Status.Canceled;
    }

    final void setThread(Thread thread) {
        this.mThread = thread;
        if (thread != null) {
            int i = AnonymousClass1.$SwitchMap$com$miui$common$tool$async$Task$Priority[this.mPriority.ordinal()];
            if (i == 1) {
                thread.setPriority(1);
                return;
            }
            if (i == 2) {
                thread.setPriority(5);
            } else if (i == 3 || i == 4) {
                thread.setPriority(10);
            }
        }
    }

    final boolean setTaskManager(TaskManager taskManager) {
        WeakReference<TaskManager> weakReference = this.mTaskManager;
        if (weakReference != null && weakReference.get() != null) {
            return false;
        }
        this.mTaskManager = new WeakReference<>(taskManager);
        return true;
    }

    private void enqueueSuccessorTasks(TaskManager taskManager) {
        synchronized (this.mSuccessorSync) {
            HashSet<Task<?>> hashSet = this.mSuccessorTasks;
            if (hashSet != null) {
                Iterator<Task<?>> it = hashSet.iterator();
                while (it.hasNext()) {
                    taskManager.add(it.next());
                }
            }
            this.mSuccessorSync.set(true);
        }
    }

    private boolean canRestart() {
        if (this.mStatus == Status.Executing) {
            return false;
        }
        synchronized (this.mSuccessorLock) {
            if (this.mStatus == Status.Done && !this.mSuccessorSync.get()) {
                Logger.INSTANCE.e("async", "Task " + this + " is DONE but successor not done yet");
                return false;
            }
            HashSet<Task<?>> hashSet = this.mSuccessorTasks;
            if (hashSet != null) {
                Iterator<Task<?>> it = hashSet.iterator();
                while (it.hasNext()) {
                    if (!it.next().canRestart()) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    private void postDelivery(Delivery delivery, Object obj) {
        TaskManager taskManager = this.mTaskManager.get();
        if (taskManager != null) {
            taskManager.postDelivery(this, delivery, obj);
        } else {
            Logger.INSTANCE.e("async", "Task has delivery " + delivery + ", but has no task manager");
        }
    }

    /* JADX INFO: renamed from: com.miui.common.tool.async.Task$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$miui$common$tool$async$Task$Delivery;
        static final /* synthetic */ int[] $SwitchMap$com$miui$common$tool$async$Task$Priority;
        static final /* synthetic */ int[] $SwitchMap$com$miui$common$tool$async$Task$Status;

        static {
            int[] iArr = new int[Delivery.values().length];
            $SwitchMap$com$miui$common$tool$async$Task$Delivery = iArr;
            try {
                iArr[Delivery.Prepare.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$common$tool$async$Task$Delivery[Delivery.Finalize.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$common$tool$async$Task$Delivery[Delivery.Result.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$common$tool$async$Task$Delivery[Delivery.Progress.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[Priority.values().length];
            $SwitchMap$com$miui$common$tool$async$Task$Priority = iArr2;
            try {
                iArr2[Priority.Low.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$miui$common$tool$async$Task$Priority[Priority.Normal.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$miui$common$tool$async$Task$Priority[Priority.High.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$miui$common$tool$async$Task$Priority[Priority.RealTime.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            int[] iArr3 = new int[Status.values().length];
            $SwitchMap$com$miui$common$tool$async$Task$Status = iArr3;
            try {
                iArr3[Status.Canceled.ordinal()] = 1;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$miui$common$tool$async$Task$Status[Status.Executing.ordinal()] = 2;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$miui$common$tool$async$Task$Status[Status.Done.ordinal()] = 3;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$miui$common$tool$async$Task$Status[Status.Queued.ordinal()] = 4;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$miui$common$tool$async$Task$Status[Status.New.ordinal()] = 5;
            } catch (NoSuchFieldError unused13) {
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    final void deliver(TaskManager taskManager, Delivery delivery, Object obj) {
        int i = AnonymousClass1.$SwitchMap$com$miui$common$tool$async$Task$Delivery[delivery.ordinal()];
        if (i == 1) {
            onPrepareInternal(taskManager);
            return;
        }
        if (i == 2) {
            onFinalizeInternal(taskManager);
            return;
        }
        if (i != 3) {
            if (i != 4) {
                return;
            }
            int[] iArr = (int[]) obj;
            onProgressInternal(taskManager, iArr[0], iArr[1]);
            return;
        }
        if (obj != 0 && !isCanceled()) {
            if (obj instanceof TaskExecutingException) {
                onExceptionInternal(taskManager, (Exception) ((TaskExecutingException) obj).getCause());
                return;
            }
            try {
                onResultInternal(taskManager, obj);
                enqueueSuccessorTasks(taskManager);
                return;
            } catch (ClassCastException e) {
                Logger.INSTANCE.e("async", "Task " + this + " return result cannot cast to expectation class");
                onExceptionInternal(taskManager, e);
                return;
            }
        }
        onCanceledInternal(taskManager);
    }

    private void onCanceledInternal(TaskManager taskManager) {
        ArrayList<Listener> arrayList = this.mListeners;
        if (arrayList != null) {
            Iterator<Listener> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().onCanceled(taskManager, this);
            }
        }
        onCanceled(taskManager);
    }

    private void onProgressInternal(TaskManager taskManager, int i, int i2) {
        ArrayList<Listener> arrayList = this.mListeners;
        if (arrayList != null) {
            Iterator<Listener> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().onProgress(taskManager, this, i, i2);
            }
        }
        onProgress(taskManager, i, i2);
    }

    private void onResultInternal(TaskManager taskManager, T t) {
        ArrayList<Listener> arrayList = this.mListeners;
        if (arrayList != null) {
            Iterator<Listener> it = arrayList.iterator();
            while (it.hasNext()) {
                t = (T) it.next().onResult(taskManager, this, t);
            }
        }
        onResult(taskManager, t);
    }

    private void onExceptionInternal(TaskManager taskManager, Exception exc) {
        ArrayList<Listener> arrayList = this.mListeners;
        if (arrayList != null) {
            Iterator<Listener> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().onException(taskManager, this, exc);
            }
        }
        onException(taskManager, exc);
    }

    private void onFinalizeInternal(TaskManager taskManager) {
        ArrayList<Listener> arrayList = this.mListeners;
        if (arrayList != null) {
            Iterator<Listener> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().onFinalize(taskManager, this);
            }
        }
        onFinalize(taskManager);
        this.mTaskManager.clear();
    }

    private void onPrepareInternal(TaskManager taskManager) {
        ArrayList<Listener> arrayList = this.mListeners;
        if (arrayList != null) {
            Iterator<Listener> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().onPrepare(taskManager, this);
            }
        }
        onPrepare(taskManager);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        String description = getDescription();
        if (description != null) {
            sb.append(Typography.less);
            sb.append(description);
            sb.append(Typography.greater);
        }
        sb.append(": Status=");
        sb.append(this.mStatus);
        sb.append(", Priority=");
        sb.append(this.mPriority);
        return sb.toString();
    }

    public final void publishProgress(int i, int i2) {
        postDelivery(Delivery.Progress, new int[]{i, i2});
    }

    public final Task<T> addListener(Listener listener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList<>();
        }
        this.mListeners.add(listener);
        return this;
    }

    public final Task<T> removeListener(Listener listener) {
        ArrayList<Listener> arrayList = this.mListeners;
        if (arrayList != null) {
            arrayList.remove(listener);
        }
        return this;
    }

    public final void cancel() {
        TaskManager taskManager;
        if (this.mStatus != Status.Done) {
            if (this.mStatus == Status.Queued && (taskManager = this.mTaskManager.get()) != null) {
                taskManager.remove(this);
            }
            setStatus(Status.Canceled, null);
        }
    }

    public final void depends(Task<T> task) {
        synchronized (this.mSuccessorLock) {
            if (this.mSuccessorSync.get()) {
                if (task.mSuccessorTasks == null) {
                    task.mSuccessorTasks = new HashSet<>();
                }
                task.mSuccessorTasks.add(this);
            } else {
                TaskManager taskManager = this.mTaskManager.get();
                if (taskManager != null) {
                    taskManager.add(this);
                } else {
                    Logger.INSTANCE.e("async", "Task " + this + " is done but has no task manager to execute task " + task);
                }
            }
        }
    }

    public final Priority getPriority() {
        return this.mPriority;
    }

    public final Task<T> setPriority(Priority priority) {
        TaskManager taskManager;
        if (this.mPriority != priority) {
            if (this.mStatus == Status.Queued && (taskManager = this.mTaskManager.get()) != null && taskManager.remove(this)) {
                this.mPriority = priority;
                this.mStatus = Status.New;
                taskManager.add(this);
            }
            if (this.mStatus == Status.Executing) {
                setThread(this.mThread);
            }
            this.mPriority = priority;
        }
        return this;
    }

    public final boolean restart() {
        TaskManager taskManager;
        if (!canRestart()) {
            return false;
        }
        if ((this.mStatus == Status.Queued && ((taskManager = this.mTaskManager.get()) == null || !taskManager.remove(this))) || this.mStatus == Status.Executing) {
            return false;
        }
        synchronized (this.mSuccessorLock) {
            HashSet<Task<?>> hashSet = this.mSuccessorTasks;
            if (hashSet != null) {
                Iterator<Task<?>> it = hashSet.iterator();
                while (it.hasNext()) {
                    it.next().restart();
                }
            }
            this.mSuccessorSync.set(true);
        }
        this.mStatus = Status.New;
        return true;
    }
}
