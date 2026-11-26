package androidx.graphics.lowlatency;

import android.view.SurfaceView;
import androidx.graphics.opengl.FrameBuffer;
import androidx.graphics.opengl.GLFrameBufferRenderer;
import androidx.graphics.opengl.GLRenderer;
import androidx.graphics.surface.SurfaceControlCompat;
import androidx.hardware.SyncFenceCompat;
import com.miui.doodle.document.Layer;
import java.util.function.IntUnaryOperator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: GLFrontBufferedRenderer.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000;\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH\u0016J*\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016J\u001a\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0011H\u0016¨\u0006\u0014"}, d2 = {"androidx/graphics/lowlatency/GLFrontBufferedRenderer$mMultiBufferedRenderCallbacks$1", "Landroidx/graphics/opengl/GLFrameBufferRenderer$Callback;", "onDrawFrame", "", Layer.KEY_WIDTH, "", Layer.KEY_HEIGHT, "bufferInfo", "Landroidx/graphics/lowlatency/BufferInfo;", "onDrawComplete", "targetSurfaceControl", "Landroidx/graphics/surface/SurfaceControlCompat;", "transaction", "Landroidx/graphics/surface/SurfaceControlCompat$Transaction;", "frameBuffer", "Landroidx/graphics/opengl/FrameBuffer;", "syncFence", "Landroidx/hardware/SyncFenceCompat;", "onBufferReleased", "releaseFence", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class GLFrontBufferedRenderer$mMultiBufferedRenderCallbacks$1 implements GLFrameBufferRenderer.Callback {
    final /* synthetic */ GLFrontBufferedRenderer.Callback<T> $callback;
    final /* synthetic */ GLFrontBufferedRenderer<T> this$0;

    GLFrontBufferedRenderer$mMultiBufferedRenderCallbacks$1(GLFrontBufferedRenderer.Callback<T> callback, GLFrontBufferedRenderer<T> gLFrontBufferedRenderer) {
        this.$callback = callback;
        this.this$0 = gLFrontBufferedRenderer;
    }

    @Override // androidx.graphics.opengl.GLFrameBufferRenderer.Callback
    public void onDrawFrame(int width, int height, BufferInfo bufferInfo) {
        Intrinsics.checkNotNullParameter(bufferInfo, "bufferInfo");
        this.$callback.onDrawMultiBufferedLayer(bufferInfo);
    }

    @Override // androidx.graphics.opengl.GLFrameBufferRenderer.Callback
    public void onDrawComplete(SurfaceControlCompat targetSurfaceControl, SurfaceControlCompat.Transaction transaction, FrameBuffer frameBuffer, SyncFenceCompat syncFence) {
        Intrinsics.checkNotNullParameter(targetSurfaceControl, "targetSurfaceControl");
        Intrinsics.checkNotNullParameter(transaction, "transaction");
        Intrinsics.checkNotNullParameter(frameBuffer, "frameBuffer");
        ((GLFrontBufferedRenderer) this.this$0).mFrontBufferSyncStrategy.setVisible(false);
        GLFrontBufferedRenderer<T> gLFrontBufferedRenderer = this.this$0;
        GLFrontBufferedRenderer.Callback<T> callback = this.$callback;
        SurfaceControlCompat surfaceControlCompat = ((GLFrontBufferedRenderer) gLFrontBufferedRenderer).mFrontBufferedLayerSurfaceControl;
        if (surfaceControlCompat != null) {
            transaction.setVisibility(surfaceControlCompat, false);
            SurfaceControlCompat.Transaction.setBuffer$default(transaction, surfaceControlCompat, null, null, null, 12, null);
            callback.onMultiBufferedLayerRenderComplete(surfaceControlCompat, targetSurfaceControl, transaction);
        }
    }

    @Override // androidx.graphics.opengl.GLFrameBufferRenderer.Callback
    public void onBufferReleased(FrameBuffer frameBuffer, SyncFenceCompat releaseFence) {
        GLFrameBufferRenderer gLFrameBufferRenderer;
        Intrinsics.checkNotNullParameter(frameBuffer, "frameBuffer");
        if (this.this$0.isValid()) {
            ((GLFrontBufferedRenderer) this.this$0).mPendingClear.set(true);
            if (!((GLFrontBufferedRenderer) this.this$0).isHaveDraw) {
                ((GLFrontBufferedRenderer) this.this$0).isHaveDraw = true;
                SurfaceView surfaceView = ((GLFrontBufferedRenderer) this.this$0).mSurfaceView;
                if (surfaceView != null) {
                    surfaceView.setBackgroundColor(0);
                }
            }
            if (((GLFrontBufferedRenderer) this.this$0).mCommitCount.updateAndGet(new IntUnaryOperator() { // from class: androidx.graphics.lowlatency.GLFrontBufferedRenderer$mMultiBufferedRenderCallbacks$1$$ExternalSyntheticLambda0
                @Override // java.util.function.IntUnaryOperator
                public final int applyAsInt(int i) {
                    return GLFrontBufferedRenderer$mMultiBufferedRenderCallbacks$1.onBufferReleased$lambda$2(i);
                }
            }) != 0) {
                GLRenderer gLRenderer = ((GLFrontBufferedRenderer) this.this$0).mGLRenderer;
                final GLFrontBufferedRenderer<T> gLFrontBufferedRenderer = this.this$0;
                gLRenderer.execute(new Runnable() { // from class: androidx.graphics.lowlatency.GLFrontBufferedRenderer$mMultiBufferedRenderCallbacks$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        GLFrontBufferedRenderer.access$commitInternal(gLFrontBufferedRenderer);
                    }
                });
            } else {
                if (((GLFrontBufferedRenderer) this.this$0).mPendingRenderCount.get() <= 0 || (gLFrameBufferRenderer = ((GLFrontBufferedRenderer) this.this$0).mFrontBufferedRenderer) == null) {
                    return;
                }
                gLFrameBufferRenderer.render();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int onBufferReleased$lambda$2(int i) {
        return Math.max(i - 1, 0);
    }
}
