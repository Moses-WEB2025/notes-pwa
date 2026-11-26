package androidx.graphics.opengl;

import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.miui.doodle.document.Layer;
import java.util.concurrent.CountDownLatch;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.utils.XLog;

/* JADX INFO: compiled from: GLFrameBufferRenderer.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000'\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J(\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\bH\u0016J\u0010\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u0005H\u0016J\u0010\u0010\r\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u0005H\u0016J\u0018\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0010H\u0016¨\u0006\u0011"}, d2 = {"androidx/graphics/opengl/GLFrameBufferRenderer$SurfaceViewProvider$createSurfaceControl$1$surfaceHolderCallback$1", "Landroid/view/SurfaceHolder$Callback2;", "surfaceCreated", "", "holder", "Landroid/view/SurfaceHolder;", "surfaceChanged", "surfaceFormat", "", Layer.KEY_WIDTH, Layer.KEY_HEIGHT, "surfaceDestroyed", "p0", "surfaceRedrawNeeded", "surfaceRedrawNeededAsync", "drawingFinished", "Ljava/lang/Runnable;", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class GLFrameBufferRenderer$SurfaceViewProvider$createSurfaceControl$1$surfaceHolderCallback$1 implements SurfaceHolder.Callback2 {
    final /* synthetic */ GLFrameBufferRenderer.SurfaceControlProvider.Callback $callback;
    final /* synthetic */ SurfaceView $target;
    final /* synthetic */ GLFrameBufferRenderer.SurfaceViewProvider this$0;

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder holder) {
        Intrinsics.checkNotNullParameter(holder, "holder");
    }

    GLFrameBufferRenderer$SurfaceViewProvider$createSurfaceControl$1$surfaceHolderCallback$1(GLFrameBufferRenderer.SurfaceViewProvider surfaceViewProvider, SurfaceView surfaceView, GLFrameBufferRenderer.SurfaceControlProvider.Callback callback) {
        this.this$0 = surfaceViewProvider;
        this.$target = surfaceView;
        this.$callback = callback;
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder holder, int surfaceFormat, int width, int height) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        if (width > 0 && height > 0) {
            this.this$0.createSurfaceControl$libXCEngine_release(this.$target, this.$callback);
        } else {
            Log.w("SurfaceViewProvider", "Invalid dimensions provided, width and height must be > 0. width: " + width + " height: " + height);
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        this.this$0.destroySurfaceControl(this.$callback);
    }

    @Override // android.view.SurfaceHolder.Callback2
    public void surfaceRedrawNeeded(SurfaceHolder p0) throws InterruptedException {
        Intrinsics.checkNotNullParameter(p0, "p0");
        XLog.v("SurfaceViewProvider", "surfaceRedrawNeeded", new Object[0]);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        this.$callback.requestRender(new Runnable() { // from class: androidx.graphics.opengl.GLFrameBufferRenderer$SurfaceViewProvider$createSurfaceControl$1$surfaceHolderCallback$1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
    }

    @Override // android.view.SurfaceHolder.Callback2
    public void surfaceRedrawNeededAsync(SurfaceHolder holder, Runnable drawingFinished) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(drawingFinished, "drawingFinished");
        XLog.v("SurfaceViewProvider", "surfaceRedrawNeededAsync", new Object[0]);
        this.$callback.requestRender(drawingFinished);
    }
}
