package androidx.hardware;

import android.hardware.SyncFence;
import android.opengl.EGL14;
import android.opengl.EGL15;
import android.opengl.EGLDisplay;
import android.opengl.EGLExt;
import android.opengl.EGLSync;
import android.opengl.GLES20;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SyncFenceCompat.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0001\u0018\u0000 \u00042\u00020\u0001:\u0001\u0004B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0005"}, d2 = {"Landroidx/hardware/SyncFenceCompatVerificationHelper;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class SyncFenceCompatVerificationHelper {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final long[] mEmptyAttributes = {12344};

    /* JADX INFO: compiled from: SyncFenceCompat.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0016\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0006\u001a\u00020\u0007H\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Landroidx/hardware/SyncFenceCompatVerificationHelper$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mEmptyAttributes", "", "createSyncFenceCompatV33", "Landroidx/hardware/SyncFenceCompat;", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final SyncFenceCompat createSyncFenceCompatV33() {
            EGLDisplay eGLDisplayEglGetCurrentDisplay = EGL14.eglGetCurrentDisplay();
            if (Intrinsics.areEqual(eGLDisplayEglGetCurrentDisplay, EGL15.EGL_NO_DISPLAY)) {
                throw new RuntimeException("no EGL display");
            }
            if (EGL14.eglGetError() != 12288) {
                throw new RuntimeException("eglGetPlatformDisplay failed");
            }
            EGLSync eGLSyncEglCreateSync = EGL15.eglCreateSync(eGLDisplayEglGetCurrentDisplay, 12612, SyncFenceCompatVerificationHelper.mEmptyAttributes, 0);
            GLES20.glFlush();
            SyncFence syncFenceEglDupNativeFenceFDANDROID = EGLExt.eglDupNativeFenceFDANDROID(eGLDisplayEglGetCurrentDisplay, eGLSyncEglCreateSync);
            Intrinsics.checkNotNullExpressionValue(syncFenceEglDupNativeFenceFDANDROID, "eglDupNativeFenceFDANDROID(...)");
            SyncFenceCompat syncFenceCompat = new SyncFenceCompat(syncFenceEglDupNativeFenceFDANDROID);
            EGL15.eglDestroySync(eGLDisplayEglGetCurrentDisplay, eGLSyncEglCreateSync);
            return syncFenceCompat;
        }
    }

    private SyncFenceCompatVerificationHelper() {
    }
}
