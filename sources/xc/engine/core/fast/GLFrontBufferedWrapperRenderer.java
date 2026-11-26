package xc.engine.core.fast;

import android.view.SurfaceView;
import androidx.graphics.lowlatency.GLFrontBufferedRenderer;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.core.fast.data.CurveSegment;

/* JADX INFO: compiled from: GLFrontBufferedWrapperRenderer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\tJ\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\nJ\u0006\u0010\u000f\u001a\u00020\rJ\u0006\u0010\u0010\u001a\u00020\rJ\u0006\u0010\u0011\u001a\u00020\rJ\u0006\u0010\u0012\u001a\u00020\rJ\u0006\u0010\u0013\u001a\u00020\u0014J\u000e\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\u0017J\u0016\u0010\u0018\u001a\u00020\r2\u000e\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\u001aR\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lxc/engine/core/fast/GLFrontBufferedWrapperRenderer;", "", "surfaceView", "Landroid/view/SurfaceView;", "renderCallback", "Lxc/engine/core/fast/GlLowLatencyRenderProcessor;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/view/SurfaceView;Lxc/engine/core/fast/GlLowLatencyRenderProcessor;)V", "mFrontBufferRenderer", "Landroidx/graphics/lowlatency/GLFrontBufferedRenderer;", "Lxc/engine/core/fast/data/CurveSegment;", "getGLFrontBufferedRenderer", "renderOnFrontBuffer", "", "segment", "renderOnMultiBuffer", "commitToMultiBufferedLayer", "cancelRendering", "clearContent", "isValid", "", "execOnGLThread", "runnable", "Ljava/lang/Runnable;", "release", "onReleaseFinished", "Lkotlin/Function0;", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class GLFrontBufferedWrapperRenderer {
    private GLFrontBufferedRenderer<CurveSegment> mFrontBufferRenderer;

    public GLFrontBufferedWrapperRenderer(SurfaceView surfaceView, GlLowLatencyRenderProcessor renderCallback) {
        Intrinsics.checkNotNullParameter(surfaceView, "surfaceView");
        Intrinsics.checkNotNullParameter(renderCallback, "renderCallback");
        this.mFrontBufferRenderer = new GLFrontBufferedRenderer<>(surfaceView, renderCallback, null, 0, 12, null);
    }

    public final GLFrontBufferedRenderer<CurveSegment> getGLFrontBufferedRenderer() {
        return this.mFrontBufferRenderer;
    }

    public final void renderOnFrontBuffer(CurveSegment segment) {
        Intrinsics.checkNotNullParameter(segment, "segment");
        this.mFrontBufferRenderer.renderFrontBufferedLayer(segment);
    }

    public final void renderOnMultiBuffer() {
        this.mFrontBufferRenderer.renderMultiBufferedLayer();
    }

    public final void commitToMultiBufferedLayer() {
        this.mFrontBufferRenderer.commit();
    }

    public final void cancelRendering() {
        this.mFrontBufferRenderer.cancel();
    }

    public final void clearContent() {
        this.mFrontBufferRenderer.clear();
    }

    public final boolean isValid() {
        return this.mFrontBufferRenderer.isValid();
    }

    public final void execOnGLThread(Runnable runnable) {
        Intrinsics.checkNotNullParameter(runnable, "runnable");
        this.mFrontBufferRenderer.execute(runnable);
    }

    public final void release(Function0<Unit> onReleaseFinished) {
        this.mFrontBufferRenderer.release(true, onReleaseFinished);
    }
}
