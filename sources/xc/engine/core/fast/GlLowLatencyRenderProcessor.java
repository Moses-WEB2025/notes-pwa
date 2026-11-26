package xc.engine.core.fast;

import android.view.SurfaceView;
import androidx.graphics.lowlatency.BufferInfo;
import androidx.graphics.lowlatency.GLFrontBufferedRenderer;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.core.fast.data.CurveSegment;
import xc.engine.core.fast.draw.FrontBufferedMultiDrawer;

/* JADX INFO: compiled from: GlLowLatencyRenderProcessor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00172\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0017B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\nJ\u0006\u0010\u000e\u001a\u00020\fJ\b\u0010\u000f\u001a\u0004\u0018\u00010\u0006J\u000e\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\bJ\u0018\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0002H\u0016J\u0010\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lxc/engine/core/fast/GlLowLatencyRenderProcessor;", "Landroidx/graphics/lowlatency/GLFrontBufferedRenderer$Callback;", "Lxc/engine/core/fast/data/CurveSegment;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mFrontBufferRenderer", "Lxc/engine/core/fast/GLFrontBufferedWrapperRenderer;", "mDrawer", "Lxc/engine/core/fast/draw/FrontBufferedMultiDrawer;", "mSurfaceView", "Landroid/view/SurfaceView;", "initialize", "", "surfaceView", "release", "obtainGLFrontBufferedRenderer", "updateFrontLayerDrawer", "drawer", "onDrawFrontBufferedLayer", "bufferInfo", "Landroidx/graphics/lowlatency/BufferInfo;", "param", "onDrawMultiBufferedLayer", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class GlLowLatencyRenderProcessor implements GLFrontBufferedRenderer.Callback<CurveSegment> {
    private static final String TAG = "GlLowLatencyRenderProcessor";
    private FrontBufferedMultiDrawer mDrawer;
    private GLFrontBufferedWrapperRenderer mFrontBufferRenderer;
    private SurfaceView mSurfaceView;

    public final void initialize(SurfaceView surfaceView) {
        Intrinsics.checkNotNullParameter(surfaceView, "surfaceView");
        this.mFrontBufferRenderer = new GLFrontBufferedWrapperRenderer(surfaceView, this);
        this.mSurfaceView = surfaceView;
    }

    public final void release() {
        this.mSurfaceView = null;
        GLFrontBufferedWrapperRenderer gLFrontBufferedWrapperRenderer = this.mFrontBufferRenderer;
        if (gLFrontBufferedWrapperRenderer != null) {
            gLFrontBufferedWrapperRenderer.release(new Function0() { // from class: xc.engine.core.fast.GlLowLatencyRenderProcessor$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return GlLowLatencyRenderProcessor.release$lambda$0(this.f$0);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit release$lambda$0(GlLowLatencyRenderProcessor glLowLatencyRenderProcessor) {
        FrontBufferedMultiDrawer frontBufferedMultiDrawer = glLowLatencyRenderProcessor.mDrawer;
        if (frontBufferedMultiDrawer != null) {
            frontBufferedMultiDrawer.onDestroy();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: obtainGLFrontBufferedRenderer, reason: from getter */
    public final GLFrontBufferedWrapperRenderer getMFrontBufferRenderer() {
        return this.mFrontBufferRenderer;
    }

    public final void updateFrontLayerDrawer(FrontBufferedMultiDrawer drawer) {
        Intrinsics.checkNotNullParameter(drawer, "drawer");
        this.mDrawer = drawer;
    }

    @Override // androidx.graphics.lowlatency.GLFrontBufferedRenderer.Callback
    public void onDrawFrontBufferedLayer(BufferInfo bufferInfo, CurveSegment param) {
        Intrinsics.checkNotNullParameter(bufferInfo, "bufferInfo");
        Intrinsics.checkNotNullParameter(param, "param");
        FrontBufferedMultiDrawer frontBufferedMultiDrawer = this.mDrawer;
        if (frontBufferedMultiDrawer != null) {
            frontBufferedMultiDrawer.fastDrawingAtFrontNew(null, bufferInfo, param);
        }
    }

    @Override // androidx.graphics.lowlatency.GLFrontBufferedRenderer.Callback
    public void onDrawMultiBufferedLayer(BufferInfo bufferInfo) {
        Intrinsics.checkNotNullParameter(bufferInfo, "bufferInfo");
        FrontBufferedMultiDrawer frontBufferedMultiDrawer = this.mDrawer;
        if (frontBufferedMultiDrawer != null) {
            frontBufferedMultiDrawer.solidifyDrawingAtDoubleBuffer(bufferInfo);
        }
    }
}
