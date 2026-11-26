package androidx.graphics.lowlatency;

import android.hardware.HardwareBuffer;
import android.view.SurfaceView;
import androidx.graphics.opengl.FrameBuffer;
import androidx.graphics.opengl.GLFrameBufferRenderer;
import androidx.graphics.surface.SurfaceControlCompat;
import androidx.hardware.SyncFenceCompat;
import com.miui.doodle.document.Layer;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntUnaryOperator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: GLFrontBufferedRenderer.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00009\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH\u0016J*\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016¨\u0006\u0012"}, d2 = {"androidx/graphics/lowlatency/GLFrontBufferedRenderer$mFrontBufferedCallbacks$1", "Landroidx/graphics/opengl/GLFrameBufferRenderer$Callback;", "onDrawFrame", "", Layer.KEY_WIDTH, "", Layer.KEY_HEIGHT, "bufferInfo", "Landroidx/graphics/lowlatency/BufferInfo;", "onDrawComplete", "targetSurfaceControl", "Landroidx/graphics/surface/SurfaceControlCompat;", "transaction", "Landroidx/graphics/surface/SurfaceControlCompat$Transaction;", "frameBuffer", "Landroidx/graphics/opengl/FrameBuffer;", "syncFence", "Landroidx/hardware/SyncFenceCompat;", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class GLFrontBufferedRenderer$mFrontBufferedCallbacks$1 implements GLFrameBufferRenderer.Callback {
    final /* synthetic */ GLFrontBufferedRenderer.Callback<T> $callback;
    final /* synthetic */ GLFrontBufferedRenderer<T> this$0;

    GLFrontBufferedRenderer$mFrontBufferedCallbacks$1(GLFrontBufferedRenderer<T> gLFrontBufferedRenderer, GLFrontBufferedRenderer.Callback<T> callback) {
        this.this$0 = gLFrontBufferedRenderer;
        this.$callback = callback;
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // androidx.graphics.opengl.GLFrameBufferRenderer.Callback
    public void onDrawFrame(int width, int height, BufferInfo bufferInfo) {
        GLFrameBufferRenderer gLFrameBufferRenderer;
        Intrinsics.checkNotNullParameter(bufferInfo, "bufferInfo");
        if (((GLFrontBufferedRenderer) this.this$0).mPendingClear.compareAndSet(true, false)) {
            this.this$0.waitForFrontBufferFence();
            this.this$0.clearContents(width, height);
        }
        int iUpdateAndGet = ((GLFrontBufferedRenderer) this.this$0).mPendingRenderCount.updateAndGet(new IntUnaryOperator() { // from class: androidx.graphics.lowlatency.GLFrontBufferedRenderer$mFrontBufferedCallbacks$1$$ExternalSyntheticLambda0
            @Override // java.util.function.IntUnaryOperator
            public final int applyAsInt(int i) {
                return GLFrontBufferedRenderer$mFrontBufferedCallbacks$1.onDrawFrame$lambda$0(i);
            }
        });
        ParamQueue paramQueue = ((GLFrontBufferedRenderer) this.this$0).mActiveSegment;
        GLFrontBufferedRenderer.Callback<T> callback = this.$callback;
        ReentrantLock reentrantLock = paramQueue.mLock;
        reentrantLock.lock();
        try {
            if (paramQueue.mIndex < paramQueue.mParams.size()) {
                ArrayList arrayList = paramQueue.mParams;
                int i = paramQueue.mIndex;
                paramQueue.mIndex = i + 1;
                callback.onDrawFrontBufferedLayer(bufferInfo, (T) arrayList.get(i));
            }
            Unit unit = Unit.INSTANCE;
            reentrantLock.unlock();
            if (!((GLFrontBufferedRenderer) this.this$0).isHaveDraw) {
                ((GLFrontBufferedRenderer) this.this$0).isHaveDraw = true;
                SurfaceView surfaceView = ((GLFrontBufferedRenderer) this.this$0).mSurfaceView;
                if (surfaceView != null) {
                    surfaceView.setBackgroundColor(0);
                }
            }
            if (iUpdateAndGet <= 0 || (gLFrameBufferRenderer = ((GLFrontBufferedRenderer) this.this$0).mFrontBufferedRenderer) == null) {
                return;
            }
            gLFrameBufferRenderer.render();
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int onDrawFrame$lambda$0(int i) {
        return Math.max(i - 1, 0);
    }

    @Override // androidx.graphics.opengl.GLFrameBufferRenderer.Callback
    public void onDrawComplete(SurfaceControlCompat targetSurfaceControl, SurfaceControlCompat.Transaction transaction, FrameBuffer frameBuffer, SyncFenceCompat syncFence) {
        Intrinsics.checkNotNullParameter(targetSurfaceControl, "targetSurfaceControl");
        Intrinsics.checkNotNullParameter(transaction, "transaction");
        Intrinsics.checkNotNullParameter(frameBuffer, "frameBuffer");
        ((GLFrontBufferedRenderer) this.this$0).mFrontBufferSyncStrategy.setVisible(true);
        ((GLFrontBufferedRenderer) this.this$0).mFrontLayerBuffer = frameBuffer;
        transaction.setLayer(targetSurfaceControl, Integer.MAX_VALUE);
        HardwareBuffer hardwareBuffer = frameBuffer.getHardwareBuffer();
        final GLFrontBufferedRenderer<T> gLFrontBufferedRenderer = this.this$0;
        transaction.setBuffer(targetSurfaceControl, hardwareBuffer, syncFence, new Function1() { // from class: androidx.graphics.lowlatency.GLFrontBufferedRenderer$mFrontBufferedCallbacks$1$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return GLFrontBufferedRenderer$mFrontBufferedCallbacks$1.onDrawComplete$lambda$2(gLFrontBufferedRenderer, (SyncFenceCompat) obj);
            }
        });
        this.$callback.onFrontBufferedLayerRenderComplete(targetSurfaceControl, transaction);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onDrawComplete$lambda$2(GLFrontBufferedRenderer gLFrontBufferedRenderer, SyncFenceCompat releaseFence) {
        Intrinsics.checkNotNullParameter(releaseFence, "releaseFence");
        SyncFenceCompat syncFenceCompat = gLFrontBufferedRenderer.mFrontBufferReleaseFence;
        if (syncFenceCompat != null) {
            syncFenceCompat.close();
        }
        gLFrontBufferedRenderer.mFrontBufferReleaseFence = releaseFence;
        return Unit.INSTANCE;
    }
}
