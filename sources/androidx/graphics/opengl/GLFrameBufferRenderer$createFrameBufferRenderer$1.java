package androidx.graphics.opengl;

import android.hardware.HardwareBuffer;
import androidx.graphics.lowlatency.BufferInfo;
import androidx.graphics.lowlatency.BufferTransformer;
import androidx.graphics.opengl.egl.EGLManager;
import androidx.graphics.opengl.egl.EGLSpec;
import androidx.graphics.surface.SurfaceControlCompat;
import androidx.hardware.SyncFenceCompat;
import com.miui.doodle.document.Layer;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: GLFrameBufferRenderer.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00009\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u001a\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\b2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"androidx/graphics/opengl/GLFrameBufferRenderer$createFrameBufferRenderer$1", "Landroidx/graphics/opengl/FrameBufferRenderer$RenderCallback;", Layer.KEY_WIDTH, "", Layer.KEY_HEIGHT, "bufferInfo", "Landroidx/graphics/lowlatency/BufferInfo;", "obtainFrameBuffer", "Landroidx/graphics/opengl/FrameBuffer;", "egl", "Landroidx/graphics/opengl/egl/EGLSpec;", "onDraw", "", "eglManager", "Landroidx/graphics/opengl/egl/EGLManager;", "onDrawComplete", "frameBuffer", "syncFenceCompat", "Landroidx/hardware/SyncFenceCompat;", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class GLFrameBufferRenderer$createFrameBufferRenderer$1 implements FrameBufferRenderer.RenderCallback {
    final /* synthetic */ GLFrameBufferRenderer.Callback $callback;
    final /* synthetic */ FrameBufferPool $frameBufferPool;
    final /* synthetic */ int $inverseTransform;
    final /* synthetic */ SurfaceControlCompat $surfaceControl;
    private final BufferInfo bufferInfo;
    private final int height;
    final /* synthetic */ GLFrameBufferRenderer this$0;
    private final int width;

    GLFrameBufferRenderer$createFrameBufferRenderer$1(BufferTransformer bufferTransformer, GLFrameBufferRenderer gLFrameBufferRenderer, FrameBufferPool frameBufferPool, GLFrameBufferRenderer.Callback callback, SurfaceControlCompat surfaceControlCompat, int i) {
        this.this$0 = gLFrameBufferRenderer;
        this.$frameBufferPool = frameBufferPool;
        this.$callback = callback;
        this.$surfaceControl = surfaceControlCompat;
        this.$inverseTransform = i;
        this.width = bufferTransformer.getLogicalWidth();
        this.height = bufferTransformer.getLogicalHeight();
        BufferInfo bufferInfo = new BufferInfo(0, 0, 0, 7, null);
        bufferInfo.setWidth$libXCEngine_release(bufferTransformer.getBufferWidth());
        bufferInfo.setHeight$libXCEngine_release(bufferTransformer.getBufferHeight());
        this.bufferInfo = bufferInfo;
    }

    @Override // androidx.graphics.opengl.FrameBufferRenderer.RenderCallback
    public FrameBuffer obtainFrameBuffer(EGLSpec egl) {
        Intrinsics.checkNotNullParameter(egl, "egl");
        FrameBuffer frameBuffer = this.this$0.mCurrentFrameBuffer;
        if (this.this$0.mMaxBuffers == 1 && frameBuffer != null) {
            return frameBuffer;
        }
        FrameBuffer frameBufferObtain = this.$frameBufferPool.obtain(egl);
        GLFrameBufferRenderer gLFrameBufferRenderer = this.this$0;
        this.bufferInfo.setFrameBufferId$libXCEngine_release(frameBufferObtain.getFrameBuffer());
        gLFrameBufferRenderer.mCurrentFrameBuffer = frameBufferObtain;
        return frameBufferObtain;
    }

    @Override // androidx.graphics.opengl.FrameBufferRenderer.RenderCallback
    public void onDraw(EGLManager eglManager) {
        Intrinsics.checkNotNullParameter(eglManager, "eglManager");
        FrameBuffer frameBuffer = this.this$0.mCurrentFrameBuffer;
        if (frameBuffer == null || frameBuffer.getIsClosed()) {
            return;
        }
        this.$callback.onDrawFrame(this.width, this.height, this.bufferInfo);
    }

    @Override // androidx.graphics.opengl.FrameBufferRenderer.RenderCallback
    public void onDrawComplete(final FrameBuffer frameBuffer, SyncFenceCompat syncFenceCompat) {
        Intrinsics.checkNotNullParameter(frameBuffer, "frameBuffer");
        if (!this.$surfaceControl.isValid() || frameBuffer.getIsClosed()) {
            return;
        }
        SurfaceControlCompat.Transaction visibility = new SurfaceControlCompat.Transaction().setVisibility(this.$surfaceControl, true);
        if (this.this$0.mMaxBuffers > 1) {
            SurfaceControlCompat surfaceControlCompat = this.$surfaceControl;
            HardwareBuffer hardwareBuffer = frameBuffer.getHardwareBuffer();
            final GLFrameBufferRenderer gLFrameBufferRenderer = this.this$0;
            final FrameBufferPool frameBufferPool = this.$frameBufferPool;
            final GLFrameBufferRenderer.Callback callback = this.$callback;
            visibility.setBuffer(surfaceControlCompat, hardwareBuffer, syncFenceCompat, new Function1() { // from class: androidx.graphics.opengl.GLFrameBufferRenderer$createFrameBufferRenderer$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return GLFrameBufferRenderer$createFrameBufferRenderer$1.onDrawComplete$lambda$3(gLFrameBufferRenderer, frameBufferPool, frameBuffer, callback, (SyncFenceCompat) obj);
                }
            });
        }
        int i = this.$inverseTransform;
        if (i != -1) {
            visibility.setBufferTransform(this.$surfaceControl, i);
        }
        this.$callback.onDrawComplete(this.$surfaceControl, visibility, frameBuffer, syncFenceCompat);
        visibility.commit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onDrawComplete$lambda$3(GLFrameBufferRenderer gLFrameBufferRenderer, FrameBufferPool frameBufferPool, final FrameBuffer frameBuffer, final GLFrameBufferRenderer.Callback callback, final SyncFenceCompat releaseFence) {
        Intrinsics.checkNotNullParameter(releaseFence, "releaseFence");
        if (gLFrameBufferRenderer.mGLRenderer.isRunning()) {
            gLFrameBufferRenderer.mGLRenderer.execute(new Runnable() { // from class: androidx.graphics.opengl.GLFrameBufferRenderer$createFrameBufferRenderer$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    callback.onBufferReleased(frameBuffer, releaseFence);
                }
            });
        }
        if (gLFrameBufferRenderer.mMaxBuffers > 1 || frameBufferPool.isClosed()) {
            frameBufferPool.release(frameBuffer, releaseFence);
        }
        return Unit.INSTANCE;
    }
}
