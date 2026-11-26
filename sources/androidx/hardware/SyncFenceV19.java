package androidx.hardware;

import androidx.graphics.utils.JniVisible;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.Unit;
import miuix.navigation.SplitLayout;

/* JADX INFO: compiled from: SyncFenceV19.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0007\b\u0001\u0018\u0000 \u00192\u00060\u0001j\u0002`\u00022\u00020\u0003:\u0001\u0019B\u000f\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\b\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0017J\b\u0010\u000e\u001a\u00020\u0005H\u0003J\u0010\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\rH\u0016J\b\u0010\u0011\u001a\u00020\u000bH\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0013H\u0004J\u0019\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u0005H\u0083 J\u0011\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0004\u001a\u00020\u0005H\u0083 J\u0011\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005H\u0083 R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Landroidx/hardware/SyncFenceV19;", "Ljava/lang/AutoCloseable;", "Lkotlin/AutoCloseable;", "Landroidx/hardware/SyncFenceImpl;", "fd", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(I)V", "fenceLock", "Ljava/util/concurrent/locks/ReentrantLock;", "isValid", "", "getSignalTimeNanos", "", "dupeFileDescriptor", "await", "timeoutNanos", "awaitForever", SplitLayout.TAG_CLOSE, "", "finalize", "nWait", "timeoutMillis", "nClose", "nDup", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
@JniVisible
public final class SyncFenceV19 implements AutoCloseable, SyncFenceImpl {
    private int fd;
    private final ReentrantLock fenceLock = new ReentrantLock();

    @JniVisible
    private final native void nClose(int fd);

    @JniVisible
    private final native int nDup(int fd);

    @JniVisible
    private final native boolean nWait(int fd, int timeoutMillis);

    public SyncFenceV19(int i) {
        this.fd = i;
    }

    @Override // androidx.hardware.SyncFenceImpl
    public boolean isValid() {
        ReentrantLock reentrantLock = this.fenceLock;
        reentrantLock.lock();
        try {
            return this.fd != -1;
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // androidx.hardware.SyncFenceImpl
    public long getSignalTimeNanos() {
        ReentrantLock reentrantLock = this.fenceLock;
        reentrantLock.lock();
        try {
            return isValid() ? SyncFenceBindings.INSTANCE.nGetSignalTime(this.fd) : -1L;
        } finally {
            reentrantLock.unlock();
        }
    }

    @JniVisible
    private final int dupeFileDescriptor() {
        ReentrantLock reentrantLock = this.fenceLock;
        reentrantLock.lock();
        try {
            return isValid() ? nDup(this.fd) : -1;
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // androidx.hardware.SyncFenceImpl
    public boolean await(long timeoutNanos) {
        ReentrantLock reentrantLock = this.fenceLock;
        reentrantLock.lock();
        try {
            if (isValid()) {
                return nWait(this.fd, timeoutNanos < 0 ? -1 : (int) TimeUnit.NANOSECONDS.toMillis(timeoutNanos));
            }
            return true;
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // androidx.hardware.SyncFenceImpl
    public boolean awaitForever() {
        return await(-1L);
    }

    @Override // java.lang.AutoCloseable, androidx.hardware.SyncFenceImpl
    public void close() {
        ReentrantLock reentrantLock = this.fenceLock;
        reentrantLock.lock();
        try {
            if (isValid()) {
                nClose(this.fd);
                this.fd = -1;
            }
            Unit unit = Unit.INSTANCE;
        } finally {
            reentrantLock.unlock();
        }
    }

    protected final void finalize() {
        close();
    }

    static {
        System.loadLibrary("graphics-core");
    }
}
