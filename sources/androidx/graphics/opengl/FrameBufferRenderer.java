package androidx.graphics.opengl;

import android.opengl.EGLConfig;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.util.Log;
import android.view.Surface;
import androidx.graphics.opengl.egl.EGLManager;
import androidx.graphics.opengl.egl.EGLSpec;
import androidx.hardware.SyncFenceCompat;
import androidx.opengl.EGLExt;
import androidx.opengl.EGLSyncKHR;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.Layer;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FrameBufferRenderer.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0007\u0018\u0000 \u001d2\u00020\u0001:\u0002\u001c\u001dB\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0003\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J2\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0016J\u0006\u0010\u0015\u001a\u00020\u0016J\u0010\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\f\u0010\u001a\u001a\u00020\u001b*\u00020\u0019H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Landroidx/graphics/opengl/FrameBufferRenderer;", "Landroidx/graphics/opengl/GLRenderer$RenderCallback;", "frameBufferRendererCallbacks", "Landroidx/graphics/opengl/FrameBufferRenderer$RenderCallback;", "syncStrategy", "Landroidx/graphics/opengl/SyncStrategy;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroidx/graphics/opengl/FrameBufferRenderer$RenderCallback;Landroidx/graphics/opengl/SyncStrategy;)V", "mClear", "Ljava/util/concurrent/atomic/AtomicBoolean;", "onSurfaceCreated", "Landroid/opengl/EGLSurface;", "spec", "Landroidx/graphics/opengl/egl/EGLSpec;", "config", "Landroid/opengl/EGLConfig;", "surface", "Landroid/view/Surface;", Layer.KEY_WIDTH, "", Layer.KEY_HEIGHT, "clear", "", "onDrawFrame", "eglManager", "Landroidx/graphics/opengl/egl/EGLManager;", "supportsNativeAndroidFence", "", "RenderCallback", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class FrameBufferRenderer implements GLRenderer.RenderCallback {
    private static final Companion Companion = new Companion(null);
    private static final String TAG = "FrameBufferRenderer";
    private final RenderCallback frameBufferRendererCallbacks;
    private final AtomicBoolean mClear;
    private final SyncStrategy syncStrategy;

    /* JADX INFO: compiled from: FrameBufferRenderer.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H'J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH&J\u001a\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u00032\b\u0010\f\u001a\u0004\u0018\u00010\rH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000eÀ\u0006\u0001"}, d2 = {"Landroidx/graphics/opengl/FrameBufferRenderer$RenderCallback;", "", "obtainFrameBuffer", "Landroidx/graphics/opengl/FrameBuffer;", "egl", "Landroidx/graphics/opengl/egl/EGLSpec;", "onDraw", "", "eglManager", "Landroidx/graphics/opengl/egl/EGLManager;", "onDrawComplete", "frameBuffer", "syncFenceCompat", "Landroidx/hardware/SyncFenceCompat;", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface RenderCallback {
        FrameBuffer obtainFrameBuffer(EGLSpec egl);

        void onDraw(EGLManager eglManager);

        void onDrawComplete(FrameBuffer frameBuffer, SyncFenceCompat syncFenceCompat);
    }

    @Override // androidx.graphics.opengl.GLRenderer.RenderCallback
    public EGLSurface onSurfaceCreated(EGLSpec spec, EGLConfig config, Surface surface, int width, int height) {
        Intrinsics.checkNotNullParameter(spec, "spec");
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(surface, "surface");
        return null;
    }

    public FrameBufferRenderer(RenderCallback frameBufferRendererCallbacks, SyncStrategy syncStrategy) {
        Intrinsics.checkNotNullParameter(frameBufferRendererCallbacks, "frameBufferRendererCallbacks");
        Intrinsics.checkNotNullParameter(syncStrategy, "syncStrategy");
        this.frameBufferRendererCallbacks = frameBufferRendererCallbacks;
        this.syncStrategy = syncStrategy;
        this.mClear = new AtomicBoolean(false);
    }

    public /* synthetic */ FrameBufferRenderer(RenderCallback renderCallback, SyncStrategy syncStrategy, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(renderCallback, (i & 2) != 0 ? SyncStrategy.ALWAYS : syncStrategy);
    }

    public final void clear() {
        this.mClear.set(true);
    }

    @Override // androidx.graphics.opengl.GLRenderer.RenderCallback
    public void onDrawFrame(EGLManager eglManager) {
        Intrinsics.checkNotNullParameter(eglManager, "eglManager");
        EGLSpec eGLSpec = eglManager.getMEglSpec();
        FrameBuffer frameBufferObtainFrameBuffer = this.frameBufferRendererCallbacks.obtainFrameBuffer(eGLSpec);
        SyncFenceCompat syncFenceCompatCreateSyncFence = null;
        try {
            try {
                frameBufferObtainFrameBuffer.makeCurrent();
                if (this.mClear.getAndSet(false)) {
                    GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                    GLES20.glClear(16384);
                } else {
                    this.frameBufferRendererCallbacks.onDraw(eglManager);
                }
                if (supportsNativeAndroidFence(eglManager)) {
                    syncFenceCompatCreateSyncFence = this.syncStrategy.createSyncFence(eGLSpec);
                } else if (eglManager.isExtensionSupported(EGLExt.EGL_KHR_FENCE_SYNC)) {
                    EGLSyncKHR eGLSyncKHREglCreateSyncKHR = eGLSpec.eglCreateSyncKHR(12537, null);
                    if (eGLSyncKHREglCreateSyncKHR != null) {
                        GLES20.glFlush();
                        int iEglClientWaitSyncKHR = eGLSpec.eglClientWaitSyncKHR(eGLSyncKHREglCreateSyncKHR, 1, -1L);
                        if (iEglClientWaitSyncKHR != 12534) {
                            Log.w(TAG, "warning waiting on sync object: " + iEglClientWaitSyncKHR);
                        }
                    } else {
                        Log.w(TAG, "Unable to create EGLSync");
                        GLES20.glFinish();
                    }
                } else {
                    Log.w(TAG, "Device does not support creation of any fences");
                    GLES20.glFinish();
                }
            } catch (Exception e) {
                Log.w(TAG, "Error attempting to render to frame buffer: " + e.getMessage());
            }
        } finally {
            this.frameBufferRendererCallbacks.onDrawComplete(frameBufferObtainFrameBuffer, null);
        }
    }

    private final boolean supportsNativeAndroidFence(EGLManager eGLManager) {
        return eGLManager.isExtensionSupported(EGLExt.EGL_KHR_FENCE_SYNC) && eGLManager.isExtensionSupported(EGLExt.EGL_ANDROID_NATIVE_FENCE_SYNC);
    }

    /* JADX INFO: compiled from: FrameBufferRenderer.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0082\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Landroidx/graphics/opengl/FrameBufferRenderer$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
