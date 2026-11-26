package androidx.graphics;

import android.graphics.HardwareRenderer;
import android.graphics.RenderNode;
import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.util.Log;
import android.view.Surface;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.Layer;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SurfaceTextureRenderer.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0001\u0018\u0000 \u001a2\u00020\u0001:\u0002\u0019\u001aB;\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\n¢\u0006\u0004\b\r\u0010\u000eJ\u0006\u0010\u0017\u001a\u00020\fJ\u0006\u0010\u0018\u001a\u00020\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Landroidx/graphics/SurfaceTextureRenderer;", "", "renderNode", "Landroid/graphics/RenderNode;", Layer.KEY_WIDTH, "", Layer.KEY_HEIGHT, "handler", "Landroid/os/Handler;", "frameAvailable", "Lkotlin/Function1;", "Landroid/graphics/SurfaceTexture;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/graphics/RenderNode;IILandroid/os/Handler;Lkotlin/jvm/functions/Function1;)V", "mIsReleased", "", "mSurfaceTexture", "Landroidx/graphics/SurfaceTextureRenderer$RenderSurfaceTexture;", "mTextureSurface", "Landroid/view/Surface;", "mHardwareRenderer", "Landroid/graphics/HardwareRenderer;", "renderFrame", "release", "RenderSurfaceTexture", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class SurfaceTextureRenderer {
    private static final String TAG = "SurfaceTextureRenderer";
    private final Function1<SurfaceTexture, Unit> frameAvailable;
    private final Handler handler;
    private final HardwareRenderer mHardwareRenderer;
    private boolean mIsReleased;
    private final RenderSurfaceTexture mSurfaceTexture;
    private final Surface mTextureSurface;
    private final RenderNode renderNode;

    /* JADX WARN: Multi-variable type inference failed */
    public SurfaceTextureRenderer(RenderNode renderNode, int i, int i2, Handler handler, Function1<? super SurfaceTexture, Unit> frameAvailable) {
        Intrinsics.checkNotNullParameter(renderNode, "renderNode");
        Intrinsics.checkNotNullParameter(handler, "handler");
        Intrinsics.checkNotNullParameter(frameAvailable, "frameAvailable");
        this.renderNode = renderNode;
        this.handler = handler;
        this.frameAvailable = frameAvailable;
        RenderSurfaceTexture renderSurfaceTexture = new RenderSurfaceTexture(false);
        renderSurfaceTexture.setDefaultBufferSize(i, i2);
        renderSurfaceTexture.setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() { // from class: androidx.graphics.SurfaceTextureRenderer$$ExternalSyntheticLambda0
            @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
            public final void onFrameAvailable(SurfaceTexture surfaceTexture) {
                SurfaceTextureRenderer.mSurfaceTexture$lambda$1$lambda$0(this.f$0, surfaceTexture);
            }
        }, handler);
        this.mSurfaceTexture = renderSurfaceTexture;
        Surface surface = new Surface(renderSurfaceTexture);
        this.mTextureSurface = surface;
        HardwareRenderer hardwareRenderer = new HardwareRenderer();
        hardwareRenderer.setSurface(surface);
        hardwareRenderer.setContentRoot(renderNode);
        hardwareRenderer.start();
        this.mHardwareRenderer = hardwareRenderer;
    }

    /* JADX INFO: compiled from: SurfaceTextureRenderer.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005¨\u0006\u0006"}, d2 = {"Landroidx/graphics/SurfaceTextureRenderer$RenderSurfaceTexture;", "Landroid/graphics/SurfaceTexture;", "singleBufferMode", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Z)V", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private static final class RenderSurfaceTexture extends SurfaceTexture {
        public RenderSurfaceTexture(boolean z) {
            super(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mSurfaceTexture$lambda$1$lambda$0(SurfaceTextureRenderer surfaceTextureRenderer, SurfaceTexture surfaceTexture) {
        Function1<SurfaceTexture, Unit> function1 = surfaceTextureRenderer.frameAvailable;
        Intrinsics.checkNotNull(surfaceTexture);
        function1.invoke(surfaceTexture);
    }

    public final void renderFrame() {
        if (!this.mIsReleased) {
            this.mHardwareRenderer.createRenderRequest().setWaitForPresent(false).syncAndDraw();
        } else {
            Log.w(TAG, "Attempt to renderFrame when SurfaceTextureRenderer has already been released");
        }
    }

    public final void release() {
        if (!this.mIsReleased) {
            this.mHardwareRenderer.stop();
            this.mHardwareRenderer.destroy();
            this.mTextureSurface.release();
            if (!this.mSurfaceTexture.isReleased()) {
                this.mSurfaceTexture.release();
            }
            this.mIsReleased = true;
            return;
        }
        Log.w(TAG, "Attempt to release a SurfaceTextureRenderer that has already been released");
    }
}
