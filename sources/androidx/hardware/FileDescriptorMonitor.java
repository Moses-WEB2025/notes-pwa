package androidx.hardware;

import android.util.Log;
import androidx.graphics.utils.HandlerThreadExecutor;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.hybirdeditor.EmptyOverwriteGuard;
import com.xiaomi.mirror.RemoteDeviceInfo;
import com.xiaomi.mirror.synergy.CallMethod;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FileDescriptorMonitor.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000e\b\u0000\u0018\u0000 '2\u00020\u0001:\u0002&'B'\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ\b\u0010\u0017\u001a\u00020\u0011H\u0002J\f\u0010\u0018\u001a\u00020\u0005*\u00020\u0005H\u0002J\b\u0010\u001b\u001a\u00020\u0011H\u0002J\b\u0010\u001c\u001a\u00020\u0011H\u0002J\u0014\u0010\u001d\u001a\u00020\u00112\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010J\u0006\u0010\u001f\u001a\u00020\u0011J\b\u0010 \u001a\u00020\u0011H\u0002J\b\u0010!\u001a\u00020\u0011H\u0002J\u0010\u0010\"\u001a\u00020\u00112\b\b\u0002\u0010#\u001a\u00020\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u000e\u001a\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\u000fj\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u0010`\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0015\u001a\u0012\u0012\u0004\u0012\u00020\u00160\u000fj\b\u0012\u0004\u0012\u00020\u0016`\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010$\u001a\u00020\u00078F¢\u0006\u0006\u001a\u0004\b$\u0010%¨\u0006("}, d2 = {"Landroidx/hardware/FileDescriptorMonitor;", "", "executor", "Landroidx/graphics/utils/HandlerThreadExecutor;", "scheduleMillis", "", "manageExecutor", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroidx/graphics/utils/HandlerThreadExecutor;JZ)V", "mIsMonitoring", "Ljava/util/concurrent/atomic/AtomicBoolean;", "mProcFd", "Ljava/io/File;", "mCleanupCompleteCallbacks", "Ljava/util/ArrayList;", "Lkotlin/Function0;", "", "Lkotlin/collections/ArrayList;", "mIsManagingHandlerThread", "mExecutor", "pendingFileDescriptors", "Landroidx/hardware/FileDescriptorMonitor$FdSignalPair;", "closePendingFileDescriptors", "signalTimeDiffMillis", "mCleanupRunnable", "Ljava/lang/Runnable;", "invokeCleanupCallbacks", "scheduleCleanupTask", "addCleanupCallback", CallMethod.ARG_CALLBACK, "startMonitoring", "initExecutorIfNecessary", "teardownExecutorIfNecessary", "stopMonitoring", "cancelPending", "isMonitoring", "()Z", "FdSignalPair", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class FileDescriptorMonitor {
    public static final int MAX_FD = 100;
    public static final long MONITOR_DELAY = 1000;
    public static final int SIGNAL_TIME_DELTA_MILLIS = 3000;
    public static final String TAG = "FileDescriptorMonitor";
    private final ArrayList<Function0<Unit>> mCleanupCompleteCallbacks;
    private final Runnable mCleanupRunnable;
    private HandlerThreadExecutor mExecutor;
    private final AtomicBoolean mIsManagingHandlerThread;
    private final AtomicBoolean mIsMonitoring;
    private final File mProcFd;
    private final ArrayList<FdSignalPair> pendingFileDescriptors;
    private final long scheduleMillis;

    public FileDescriptorMonitor() {
        this(null, 0L, false, 7, null);
    }

    public FileDescriptorMonitor(HandlerThreadExecutor handlerThreadExecutor, long j, boolean z) {
        this.scheduleMillis = j;
        this.mIsMonitoring = new AtomicBoolean(false);
        this.mProcFd = new File("/proc/self/fd/");
        this.mCleanupCompleteCallbacks = new ArrayList<>();
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        this.mIsManagingHandlerThread = atomicBoolean;
        this.pendingFileDescriptors = new ArrayList<>();
        this.mExecutor = handlerThreadExecutor == null ? new HandlerThreadExecutor("fdcleanup") : handlerThreadExecutor;
        atomicBoolean.set(z);
        this.mCleanupRunnable = new Runnable() { // from class: androidx.hardware.FileDescriptorMonitor$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                FileDescriptorMonitor.mCleanupRunnable$lambda$2(this.f$0);
            }
        };
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ FileDescriptorMonitor(HandlerThreadExecutor handlerThreadExecutor, long j, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        handlerThreadExecutor = (i & 1) != 0 ? null : handlerThreadExecutor;
        this(handlerThreadExecutor, (i & 2) != 0 ? 1000L : j, (i & 4) != 0 ? handlerThreadExecutor != null : z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: FileDescriptorMonitor.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0015"}, d2 = {"Landroidx/hardware/FileDescriptorMonitor$FdSignalPair;", "", "fd", "", "signalTime", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(IJ)V", "getFd", "()I", "getSignalTime", "()J", "component1", "component2", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "toString", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    static final /* data */ class FdSignalPair {
        private final int fd;
        private final long signalTime;

        public static /* synthetic */ FdSignalPair copy$default(FdSignalPair fdSignalPair, int i, long j, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                i = fdSignalPair.fd;
            }
            if ((i2 & 2) != 0) {
                j = fdSignalPair.signalTime;
            }
            return fdSignalPair.copy(i, j);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final int getFd() {
            return this.fd;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final long getSignalTime() {
            return this.signalTime;
        }

        public final FdSignalPair copy(int fd, long signalTime) {
            return new FdSignalPair(fd, signalTime);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof FdSignalPair)) {
                return false;
            }
            FdSignalPair fdSignalPair = (FdSignalPair) other;
            return this.fd == fdSignalPair.fd && this.signalTime == fdSignalPair.signalTime;
        }

        public int hashCode() {
            return (Integer.hashCode(this.fd) * 31) + Long.hashCode(this.signalTime);
        }

        public String toString() {
            return "FdSignalPair(fd=" + this.fd + ", signalTime=" + this.signalTime + ")";
        }

        public FdSignalPair(int i, long j) {
            this.fd = i;
            this.signalTime = j;
        }

        public final int getFd() {
            return this.fd;
        }

        public final long getSignalTime() {
            return this.signalTime;
        }
    }

    private final void closePendingFileDescriptors() {
        ArrayList<FdSignalPair> arrayList = this.pendingFileDescriptors;
        if (arrayList.size() > 1) {
            CollectionsKt.sortWith(arrayList, new Comparator() { // from class: androidx.hardware.FileDescriptorMonitor$closePendingFileDescriptors$$inlined$sortByDescending$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    return ComparisonsKt.compareValues(Long.valueOf(((FileDescriptorMonitor.FdSignalPair) t2).getSignalTime()), Long.valueOf(((FileDescriptorMonitor.FdSignalPair) t).getSignalTime()));
                }
            });
        }
        while (this.pendingFileDescriptors.size() > 100) {
            try {
                int fd = ((FdSignalPair) CollectionsKt.removeLast(this.pendingFileDescriptors)).getFd();
                if (signalTimeDiffMillis(SyncFenceBindings.INSTANCE.nGetSignalTime(fd)) > EmptyOverwriteGuard.USER_EDIT_VALID_WINDOW_MS) {
                    SyncFenceBindings.INSTANCE.nForceClose(fd);
                }
            } catch (Throwable unused) {
            }
        }
    }

    private final long signalTimeDiffMillis(long j) {
        long jNanoTime = System.nanoTime();
        if (j == -1 || j == Long.MAX_VALUE || jNanoTime <= j) {
            return -1L;
        }
        return TimeUnit.NANOSECONDS.toMillis(jNanoTime - j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mCleanupRunnable$lambda$2(FileDescriptorMonitor fileDescriptorMonitor) {
        File[] fileArrListFiles = fileDescriptorMonitor.mProcFd.listFiles();
        if (fileArrListFiles != null) {
            for (File file : fileArrListFiles) {
                try {
                    int i = Integer.parseInt(file.getName());
                    long jNGetSignalTime = SyncFenceBindings.INSTANCE.nGetSignalTime(i);
                    if (fileDescriptorMonitor.signalTimeDiffMillis(jNGetSignalTime) > EmptyOverwriteGuard.USER_EDIT_VALID_WINDOW_MS) {
                        fileDescriptorMonitor.pendingFileDescriptors.add(new FdSignalPair(i, jNGetSignalTime));
                        if (fileDescriptorMonitor.pendingFileDescriptors.size() > 100) {
                            fileDescriptorMonitor.closePendingFileDescriptors();
                        }
                    }
                } catch (NumberFormatException unused) {
                    Log.w(TAG, "Unable to parse fd value from name " + file.getName());
                }
            }
        }
        if (fileDescriptorMonitor.mIsMonitoring.get()) {
            fileDescriptorMonitor.scheduleCleanupTask();
        } else {
            fileDescriptorMonitor.teardownExecutorIfNecessary();
        }
        fileDescriptorMonitor.invokeCleanupCallbacks();
    }

    private final void invokeCleanupCallbacks() {
        synchronized (this.mCleanupCompleteCallbacks) {
            Iterator<Function0<Unit>> it = this.mCleanupCompleteCallbacks.iterator();
            Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
            while (it.hasNext()) {
                Function0<Unit> next = it.next();
                Intrinsics.checkNotNullExpressionValue(next, "next(...)");
                next.invoke();
            }
            this.mCleanupCompleteCallbacks.clear();
            Unit unit = Unit.INSTANCE;
        }
    }

    private final void scheduleCleanupTask() {
        if (this.mExecutor.isRunning()) {
            this.mExecutor.postDelayed(this.mCleanupRunnable, this.scheduleMillis);
        }
    }

    public final void addCleanupCallback(Function0<Unit> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        synchronized (this.mCleanupCompleteCallbacks) {
            this.mCleanupCompleteCallbacks.add(callback);
        }
    }

    public final void startMonitoring() {
        if (this.mIsMonitoring.get()) {
            return;
        }
        initExecutorIfNecessary();
        scheduleCleanupTask();
        this.mIsMonitoring.set(true);
    }

    private final void initExecutorIfNecessary() {
        if (!this.mIsManagingHandlerThread.get() || this.mExecutor.isRunning()) {
            return;
        }
        this.mExecutor = new HandlerThreadExecutor("fdcleanup");
    }

    private final void teardownExecutorIfNecessary() {
        if (this.mIsManagingHandlerThread.get() && this.mExecutor.isRunning()) {
            this.mExecutor.quit();
        }
    }

    public static /* synthetic */ void stopMonitoring$default(FileDescriptorMonitor fileDescriptorMonitor, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        fileDescriptorMonitor.stopMonitoring(z);
    }

    public final void stopMonitoring(boolean cancelPending) {
        if (this.mIsMonitoring.get()) {
            if (cancelPending) {
                this.mExecutor.removeCallbacks(this.mCleanupRunnable);
                teardownExecutorIfNecessary();
            }
            this.mIsMonitoring.set(false);
        }
    }

    public final boolean isMonitoring() {
        return this.mIsMonitoring.get();
    }
}
