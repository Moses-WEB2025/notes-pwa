package androidx.hardware;

import android.hardware.SyncFence;
import android.opengl.EGL14;
import android.opengl.EGLDisplay;
import android.opengl.GLES20;
import android.os.Build;
import androidx.opengl.EGLExt;
import androidx.opengl.EGLSyncKHR;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.navigation.SplitLayout;

/* JADX INFO: compiled from: SyncFenceCompat.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u0000 \u00162\u00060\u0001j\u0002`\u0002:\u0001\u0016B\u0011\b\u0010\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0004\b\u0005\u0010\u0006B\u0011\b\u0011\u0012\u0006\u0010\u0003\u001a\u00020\u0007¢\u0006\u0004\b\u0005\u0010\bJ\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J\u0006\u0010\u0011\u001a\u00020\u000eJ\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0010H\u0007J\u0006\u0010\u0015\u001a\u00020\u000eR\u0014\u0010\t\u001a\u00020\nX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0017"}, d2 = {"Landroidx/hardware/SyncFenceCompat;", "Ljava/lang/AutoCloseable;", "Lkotlin/AutoCloseable;", "syncFence", "Landroidx/hardware/SyncFenceV19;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroidx/hardware/SyncFenceV19;)V", "Landroid/hardware/SyncFence;", "(Landroid/hardware/SyncFence;)V", "mImpl", "Landroidx/hardware/SyncFenceImpl;", "getMImpl$libXCEngine_release", "()Landroidx/hardware/SyncFenceImpl;", "await", "", "timeoutNanos", "", "awaitForever", SplitLayout.TAG_CLOSE, "", "getSignalTimeNanos", "isValid", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class SyncFenceCompat implements AutoCloseable {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final long SIGNAL_TIME_INVALID = -1;
    public static final long SIGNAL_TIME_PENDING = Long.MAX_VALUE;
    private final SyncFenceImpl mImpl;

    @JvmStatic
    public static final SyncFenceCompat createNativeSyncFence() {
        return INSTANCE.createNativeSyncFence();
    }

    /* JADX INFO: renamed from: getMImpl$libXCEngine_release, reason: from getter */
    public final SyncFenceImpl getMImpl() {
        return this.mImpl;
    }

    /* JADX INFO: compiled from: SyncFenceCompat.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0007R\u000e\u0010\u0006\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Landroidx/hardware/SyncFenceCompat$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "createNativeSyncFence", "Landroidx/hardware/SyncFenceCompat;", "SIGNAL_TIME_INVALID", "", "SIGNAL_TIME_PENDING", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final SyncFenceCompat createNativeSyncFence() {
            if (Build.VERSION.SDK_INT >= 33) {
                return SyncFenceCompatVerificationHelper.INSTANCE.createSyncFenceCompatV33();
            }
            EGLDisplay eGLDisplayEglGetDisplay = EGL14.eglGetDisplay(0);
            if (eGLDisplayEglGetDisplay == null) {
                throw new IllegalStateException("No EGL Display available");
            }
            EGLSyncKHR eGLSyncKHREglCreateSyncKHR = EGLExt.INSTANCE.eglCreateSyncKHR(eGLDisplayEglGetDisplay, 12612, null);
            if (eGLSyncKHREglCreateSyncKHR == null) {
                throw new IllegalStateException("Unable to create sync object");
            }
            GLES20.glFlush();
            SyncFenceCompat syncFenceCompatEglDupNativeFenceFDANDROID$libXCEngine_release = EGLExt.INSTANCE.eglDupNativeFenceFDANDROID$libXCEngine_release(eGLDisplayEglGetDisplay, eGLSyncKHREglCreateSyncKHR);
            EGLExt.INSTANCE.eglDestroySyncKHR(eGLDisplayEglGetDisplay, eGLSyncKHREglCreateSyncKHR);
            return syncFenceCompatEglDupNativeFenceFDANDROID$libXCEngine_release;
        }
    }

    public SyncFenceCompat(SyncFenceV19 syncFence) {
        Intrinsics.checkNotNullParameter(syncFence, "syncFence");
        this.mImpl = syncFence;
    }

    public SyncFenceCompat(SyncFence syncFence) {
        Intrinsics.checkNotNullParameter(syncFence, "syncFence");
        this.mImpl = new SyncFenceV33(syncFence);
    }

    public final boolean await(long timeoutNanos) {
        return this.mImpl.await(timeoutNanos);
    }

    public final boolean awaitForever() {
        return this.mImpl.awaitForever();
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.mImpl.close();
    }

    public final long getSignalTimeNanos() {
        return this.mImpl.getSignalTimeNanos();
    }

    public final boolean isValid() {
        return this.mImpl.isValid();
    }
}
